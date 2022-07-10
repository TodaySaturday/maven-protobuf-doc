package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.domain.dto.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
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
        Optional.ofNullable(listFiles)
                .ifPresent(files -> {
                    final List<ProtoFile> protoFiles = Arrays.stream(files)
                            .flatMap(file -> {
                                final String fileContent = FileUtils.fileRead(file.getAbsolutePath());
                                return JSON.parseObject(fileContent, Proto.class).getFiles().stream();
                            })
                            .collect(Collectors.toList());
                    loadEntity(protoFiles);
                });
        FileUtils.forceDelete(docsFile);
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
