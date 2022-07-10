package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.service.ProtoBuild;
import com.hjfruit.plugin.service.ProtoHandle;
import com.hjfruit.plugin.service.ProtoRead;
import com.hjfruit.plugin.service.ProtoUpload;

import java.io.IOException;

/**
 *
 * @author xianping
 * 2022/7/1021:38
 */
public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        final DocProperties properties = new DocProperties();
        properties.setProject("仓储中台");
        properties.setApiKey("9c0b53b0590f0fc6314fb9b916a7b5c21471594649");
        properties.setApiToken("b28b74e87390455c456de7fa80a0f5621391689416");
        properties.setUrl("http://192.168.99.99:4999/server/index.php?s=/api/item/updateByApi");
        final ProtoRead protoRead = new ProtoRead("E:\\Projects\\hongjiu\\hongjiu-mdm\\huanglong-wms\\huanglong-wms-grpc-api\\target");
        final ProtoHandle protoHandle = new ProtoHandle(protoRead.getDocPath());
        final ProtoBuild protoBuild = new ProtoBuild(properties, protoHandle);
        ProtoUpload.upload(properties, protoBuild.getDocUploads());
    }
}
