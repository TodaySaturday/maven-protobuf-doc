package com.hjfruit.plugin.service;

import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import com.hjfruit.plugin.domain.enums.ProtoProcess;
import com.hjfruit.plugin.domain.utils.FileUtils;

import java.io.IOException;

/**
 * @author xianping
 * 2022/7/1322:35
 */
public class ProtoReadme {

    private ProtoReadme() {
    }

    public static void protoReadme() throws IOException {
        if (null != ProtoDocMojo.getProperties().getReadmePath()) {
            ProtoDocMojo.getLogger().info(ProtoProcess.PROCESS_README.getProcess());
            final String readmeContent = FileUtils.fileRead(ProtoDocMojo.getProperties().getReadmePath());
            final DocUpload docUpload = new DocUpload();
            docUpload.setPage_title(Constant.VERSION_README);
            docUpload.setPage_content(readmeContent);
            docUpload.setApi_token(ProtoDocMojo.getProperties().getApiToken());
            docUpload.setApi_token(ProtoDocMojo.getProperties().getApiToken());
            ProtoUpload.upload(docUpload);
        }
    }
}
