package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.domain.dto.*;
import com.hjfruit.plugin.domain.enums.MessageStr;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xianping
 * 2022/7/317:49
 */
public class ProtoHandle {

    private Map<String, ProtoEnum> enumsMap;

    private Map<String, ProtoMessage> messagesMap;

    private Collection<ProtoService> servicesCollection;

    public ProtoHandle(String docsPath) throws IOException {
        final File docsFile = new File(docsPath);
        final File[] listFiles = docsFile.listFiles();
        Validate.notNull(listFiles, MessageStr.NOT_FOUND_PROTOBUF.getMessage());
        final List<ProtoFile> protoFiles = new ArrayList<>();
        for (File file : listFiles) {
            final String fileContent = FileUtils.fileRead(file.getAbsolutePath());
            protoFiles.addAll(JSON.parseObject(fileContent, Proto.class).getFiles());
        }
        loadEntity(protoFiles);
    }

    private void loadEntity(List<ProtoFile> protoFiles) {
        this.servicesCollection = protoFiles.stream()
                .flatMap(val -> val.getServices().stream())
                .collect(Collectors.toList());
        this.messagesMap = protoFiles.stream()
                .flatMap(val -> val.getMessages().stream())
                .collect(Collectors.toMap(ProtoMessage::getFullName, Function.identity()));
        this.enumsMap = protoFiles.stream()
                .flatMap(val -> val.getEnums().stream())
                .collect(Collectors.toMap(ProtoEnum::getFullName, Function.identity()));
    }

    public Map<String, ProtoEnum> getEnumsMap() {
        return enumsMap;
    }

    public Map<String, ProtoMessage> getMessagesMap() {
        return messagesMap;
    }

    public Collection<ProtoService> getServicesCollection() {
        return servicesCollection;
    }
}
