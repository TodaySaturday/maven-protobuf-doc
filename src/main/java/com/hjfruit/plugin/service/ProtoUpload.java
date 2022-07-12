package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import com.hjfruit.plugin.domain.enums.ProtoProcess;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Collection;

/**
 * @author xianping
 * 2022/6/1622:02
 */
public class ProtoUpload {

    private ProtoUpload() {
    }

    public static void upload(Collection<DocUpload> docUploads) throws IOException {
        for (DocUpload docUpload : docUploads) {
            ProtoDocMojo.getLogger().info(String.format(ProtoProcess.PROCESS_UPLOAD.getProcess(), docUpload.getPage_title()));
            final String jsonParam = JSON.toJSONString(docUpload);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParam);
            Request request = new Request.Builder()
                    .post(body)
                    .url(ProtoDocMojo.getProperties().getApiUrl()).
                    build();
            client.newCall(request).execute();
        }
    }
}
