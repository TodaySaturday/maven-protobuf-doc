package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * @author xianping
 * 2022/6/1622:02
 */
public class ProtoUpload {

    private static CloseableHttpClient httpClient;

    private ProtoUpload() {
    }

    public static void upload(DocProperties properties, Collection<DocUpload> docUploads) {
        if (Objects.isNull(httpClient)) {
            httpClient = HttpClientBuilder.create().build();
        }
        for (DocUpload docUpload : docUploads) {
            try {
                final HttpPost httpPost = new HttpPost(properties.getUrl());
                final StringEntity entity = new StringEntity(JSON.toJSONString(docUpload));
                entity.setContentEncoding("UTF-8");
                //发送json数据需要设置contentType
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
                httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
