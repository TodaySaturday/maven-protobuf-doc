package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.domain.conf.DocUpload;
import com.hjfruit.plugin.domain.http.HttpResp;
import com.hjfruit.plugin.enums.ProtoProcess;
import com.hjfruit.plugin.utils.HttpUtils;

import java.net.SocketTimeoutException;
import java.util.Collection;

/**
 * @author xianping
 * 2022/6/1622:02
 */
public class ProtoUpload {

    private ProtoUpload() {
    }

    public static void upload(Collection<DocUpload> docUploads) throws SocketTimeoutException {
        for (DocUpload docUpload : docUploads) {
            upload(docUpload);
        }
    }

    public static HttpResp upload(DocUpload docUpload) throws SocketTimeoutException {
        if (null != docUpload.getPage_title()) {
            ProtoDocMojo.getLogger().info(String.format(ProtoProcess.PROCESS_UPLOAD.getProcess(), docUpload.getPage_title()));
        }
        final String jsonParam = JSON.toJSONString(docUpload);
        return HttpUtils.doPost(ProtoDocMojo.getProperties().getApiUrl(), jsonParam, HttpResp.class);
    }
}
