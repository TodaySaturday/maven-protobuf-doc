package com.hjfruit.plugin;

import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.service.ProtoBuild;
import com.hjfruit.plugin.service.ProtoHandle;
import com.hjfruit.plugin.service.ProtoRead;
import com.hjfruit.plugin.service.ProtoUpload;

import java.io.IOException;

/**
 * @author xianping
 * 2022/7/1021:38
 */
public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        final DocProperties properties = new DocProperties();
        properties.setApiUrl("http://192.168.10.235:10000//server/index.php?s=/api/item/updateByApi");
        properties.setApiKey("7779c8fd3b95d9b9bf212557033ce047403372927");
        properties.setApiToken("f658ba37c60c473fc841822194de61d81317616368");
        final String filePath = "E:\\WorkProject\\hongjiu-fruits\\mdm\\huanglong-wms\\huanglong-wms-grpc-api\\target";
        final ProtoRead protoRead = new ProtoRead(filePath);
        final ProtoHandle protoHandle = new ProtoHandle(protoRead.getDocJsonPath());
        final ProtoBuild protoBuild = new ProtoBuild(filePath, properties, protoHandle);
        ProtoUpload.upload(properties, protoBuild.getDocUploads());
    }
}
