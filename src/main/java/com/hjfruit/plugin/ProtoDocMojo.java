package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import com.hjfruit.plugin.domain.dto.http.HttpResp;
import com.hjfruit.plugin.domain.enums.ProtoProcess;
import com.hjfruit.plugin.domain.utils.ConfigUtils;
import com.hjfruit.plugin.domain.utils.FileUtils;
import com.hjfruit.plugin.service.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @author xianping
 * 2022/7/1019:02
 */
@Mojo(name = "protobuf-doc")
public class ProtoDocMojo extends AbstractMojo {

    /**
     * showdoc服务地址
     */
    @Parameter(required = true)
    private String apiHost;

    /**
     * 项目对应apiKey
     */
    @Parameter(required = true)
    private String apiKey;

    /**
     * 项目对应apiToken
     */
    @Parameter(required = true)
    private String apiToken;

    /**
     * 项目README文件路径，非必填，填写则会上传README文件作为项目版本变更记录
     */
    @Parameter
    private File readmePath;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File sourceDirectory;

    private static Log logger;

    private static DocProperties properties;

    public ProtoDocMojo() {
        setLogger(this.getLog());
    }

    @Override
    public void execute() {
        logger.info(ProtoProcess.PROCESS_START.getProcess());
        try {
            init();
            if (!httpDebug()) {
                return;
            }
            new ProtoRead();
            final ProtoHandle protoHandle = new ProtoHandle();
            final ProtoBuild protoBuild = new ProtoBuild(protoHandle);
            ProtoUpload.upload(protoBuild.getDocUploads());
            ProtoReadme.protoReadme(readmePath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            FileUtils.forceDelete(sourceDirectory.getAbsolutePath() + Constant.DOCS);
        }
        logger.info(ProtoProcess.PROCESS_END.getProcess());
    }

    private boolean httpDebug() {
        final DocUpload docUpload = new DocUpload();
        docUpload.setApi_key(getProperties().getApiKey());
        docUpload.setApi_token(getProperties().getApiToken());
        final HttpResp resp;
        try {
            resp = ProtoUpload.upload(docUpload);
        } catch (SocketTimeoutException e) {
            logger.error(e.getMessage());
            return false;
        }
        if (resp.getErrorCode() != 200 && resp.getErrorCode() != 10101) {
            logger.error(resp.getErrorMessage());
            return false;
        }
        return true;
    }

    private void init() throws IOException, InterruptedException {
        DocProperties docProperties = new DocProperties();
        docProperties.setPath(sourceDirectory.getAbsolutePath());
        final String formatStr = ConfigUtils.propertyValue(Constant.PROPERTIES_URL_FORMAT);
        docProperties.setApiUrl(String.format(formatStr, apiHost));
        docProperties.setApiKey(apiKey);
        docProperties.setApiToken(apiToken);
        setProperties(docProperties);

    }

    public static Log getLogger() {
        return logger;
    }

    public static void setLogger(Log logger) {
        ProtoDocMojo.logger = logger;
    }

    public static DocProperties getProperties() {
        return properties;
    }

    public static void setProperties(DocProperties properties) {
        ProtoDocMojo.properties = properties;
    }
}
