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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            this.getLog().info("自定义插件开始执行");
            final DocProperties properties = properties(sourceDirectory.getAbsolutePath());
            final ProtoRead protoRead = new ProtoRead(properties);
            final ProtoHandle protoHandle = new ProtoHandle(protoRead.getDocJsonPath());
            final ProtoBuild protoBuild = new ProtoBuild(properties, protoHandle);
            ProtoUpload.upload(properties, protoBuild.getDocUploads());
            FileUtils.fileDelete(sourceDirectory.getAbsolutePath() + Constant.DOCS);
        } catch (IOException | InterruptedException e) {
            this.getLog().error(e);
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private DocProperties properties(String path) {
        DocProperties properties = new DocProperties();
        properties.setPath(path);
        properties.setApiUrl(apiUrl);
        properties.setApiKey(apiKey);
        properties.setApiToken(apiToken);
        return properties;
    }
}
