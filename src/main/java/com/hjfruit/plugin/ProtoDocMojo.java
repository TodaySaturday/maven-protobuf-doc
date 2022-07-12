package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.enums.ProtoProcess;
import com.hjfruit.plugin.domain.utils.ConfigUtils;
import com.hjfruit.plugin.service.ProtoBuild;
import com.hjfruit.plugin.service.ProtoHandle;
import com.hjfruit.plugin.service.ProtoRead;
import com.hjfruit.plugin.service.ProtoUpload;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

/**
 * @author xianping
 * 2022/7/1019:02
 */
@Mojo(name = "protobuf-doc", defaultPhase = LifecyclePhase.COMPILE)
public class ProtoDocMojo extends AbstractMojo {

    @Parameter(required = true)
    private String apiHost;

    @Parameter(required = true)
    private String apiKey;

    @Parameter(required = true)

    private String apiToken;

    @Parameter(defaultValue = "${project.build.directory}")
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
            new ProtoRead();
            final ProtoHandle protoHandle = new ProtoHandle();
            final ProtoBuild protoBuild = new ProtoBuild(protoHandle);
            ProtoUpload.upload(protoBuild.getDocUploads());
//            FileUtils.forceDelete(sourceDirectory.getAbsolutePath() + Constant.DOCS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(ProtoProcess.PROCESS_END.getProcess());
    }

    public void init() throws IOException {
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
