package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.service.ProtoBuild;
import com.hjfruit.plugin.service.ProtoHandle;
import com.hjfruit.plugin.service.ProtoRead;
import com.hjfruit.plugin.service.ProtoUpload;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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
    private String apiUrl;

    @Parameter(required = true)
    private String apiKey;

    @Parameter(required = true)
    private String apiToken;

    @Parameter(defaultValue = "${project.build.directory}")
    private File sourceDirectory;

    public static DocProperties properties;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            this.getLog().info("自定义插件开始执行");
            buildProperties();
            final ProtoRead protoRead = new ProtoRead(sourceDirectory.getAbsolutePath());
            final ProtoHandle protoHandle = new ProtoHandle(protoRead.getDocJsonPath());
            final ProtoBuild protoBuild = new ProtoBuild(sourceDirectory.getAbsolutePath(), properties, protoHandle);
            ProtoUpload.upload(properties, protoBuild.getDocUploads());
            FileUtils.fileDelete(sourceDirectory.getAbsolutePath() + Constant.DOCS);
        } catch (IOException | InterruptedException e) {
            this.getLog().error(e);
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private void buildProperties() {
        final DocProperties docProperties = new DocProperties();
        docProperties.setApiUrl(this.apiUrl);
        docProperties.setApiKey(this.apiKey);
        docProperties.setApiToken(this.apiToken);
        ProtoDocMojo.properties = docProperties;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }
}
