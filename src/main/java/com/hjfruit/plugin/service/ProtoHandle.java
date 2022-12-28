package com.hjfruit.plugin.service;

import com.alibaba.fastjson.JSON;
import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.domain.handle.*;
import com.hjfruit.plugin.enums.MessageStr;
import com.hjfruit.plugin.enums.ProtoProcess;
import com.hjfruit.plugin.utils.FileUtils;

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

    public ProtoHandle() throws IOException {
        final File docsFile = new File(ProtoDocMojo.getProperties().getDocJsonPath());
        final File[] listFiles = docsFile.listFiles();
        if (null == listFiles) {
            throw new IOException(MessageStr.NOT_FOUND_PROTOBUF.getMessage());
        }
        final List<ProtoFile> protoFiles = new ArrayList<>();
        for (File file : listFiles) {
            ProtoDocMojo.getLogger().info(String.format(ProtoProcess.PROCESS_HANDLE.getProcess(), file.getAbsolutePath()));
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
