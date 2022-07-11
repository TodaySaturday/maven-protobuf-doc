package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
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

    public static void upload(DocProperties properties, Collection<DocUpload> docUploads) throws IOException {
        for (DocUpload docUpload : docUploads) {
            final String jsonParam = JSON.toJSONString(docUpload);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParam);
            Request request = new Request.Builder()
                    .post(body)
                    .url(properties.getApiUrl()).
                    build();
            client.newCall(request).execute();
        }
    }
}
