package com.hjfruit.plugin.service;

import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.constant.Constant;
import com.hjfruit.plugin.domain.conf.DocUpload;
import com.hjfruit.plugin.enums.ProtoProcess;
import com.hjfruit.plugin.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author xianping
 * 2022/7/1322:35
 */
public class ProtoReadme {

    private ProtoReadme() {
    }

    public static void protoReadme(File readmePath) throws IOException {
        if (null != readmePath) {
            ProtoDocMojo.getLogger().info(ProtoProcess.PROCESS_README.getProcess());
            final String readmeContent = FileUtils.fileRead(readmePath.getAbsolutePath());
            final DocUpload docUpload = new DocUpload();
            docUpload.setPage_title(Constant.VERSION_README_NAME);
            docUpload.setCat_name(Constant.VERSION_README_DIRECTORY);
            docUpload.setPage_content(readmeContent);
            docUpload.setApi_key(ProtoDocMojo.getProperties().getApiKey());
            docUpload.setApi_token(ProtoDocMojo.getProperties().getApiToken());
            ProtoUpload.upload(docUpload);
        }
    }
}
