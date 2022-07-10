package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import com.hjfruit.plugin.domain.utils.HttpUtil;

import java.util.Collection;

/**
 * @author xianping
 * 2022/6/1622:02
 */
public class ProtoUpload {

    private ProtoUpload() {
    }

    public static void upload(DocProperties properties, Collection<DocUpload> docUploads) {
        for (DocUpload docUpload : docUploads) {
            HttpUtil.post(properties.getUrl(), JSON.toJSONString(docUpload));
        }
    }
}
