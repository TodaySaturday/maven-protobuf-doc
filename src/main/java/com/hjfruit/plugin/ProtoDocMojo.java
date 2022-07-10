package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.service.ProtoBuild;
import com.hjfruit.plugin.service.ProtoHandle;
import com.hjfruit.plugin.service.ProtoRead;
import com.hjfruit.plugin.service.ProtoUpload;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author xianping
 * 2022/7/1019:02
 */
@Mojo(name = "protobuf-doc")
public class ProtoDocMojo extends AbstractMojo {

    @Parameter
    private DocProperties properties;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final ProtoRead protoRead = new ProtoRead("");
            final ProtoHandle protoHandle = new ProtoHandle(protoRead.getDocPath());
            final ProtoBuild protoBuild = new ProtoBuild(properties, protoHandle);
            ProtoUpload.upload(properties, protoBuild.getDocUploads());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
