package com.hjfruit.plugin.service;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.enums.MessageStr;
import com.hjfruit.plugin.domain.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author xianping
 * @date 2022/7/3 10:49
 */
public class ProtoRead {

    private final String docPath;

    private final String classesPath;

    private final String protocDependenciesPath;

    private final Set<String> protobufPaths = new HashSet<>();

    private final Set<String> protocDependenciesPaths = new HashSet<>();

    public ProtoRead(String filePath) throws IOException, InterruptedException {
        classesPath = filePath + Constant.CLASSES;
        docPath = filePath + Constant.DOCS;
        protocDependenciesPath = filePath + Constant.PROTOC_DEPENDENCIES;
        protocDependenciesPaths.add(classesPath);
        final File[] readFiles = new File(filePath).listFiles();
        Optional.ofNullable(readFiles)
                .ifPresent(this::loadProtobufPaths);
        if (protobufPaths.isEmpty()) {
            throw new IOException(MessageStr.NOT_FOUND_PROTOBUF.getMessage());
        }
        buildProtobufJson();
    }

    private void buildProtobufJson() throws IOException, InterruptedException {
        final StringBuilder protoPath = new StringBuilder();
        for (String dependenciesPath : protocDependenciesPaths) {
            protoPath.append(Constant.CMD_PROTO_PATH).append(dependenciesPath);
        }
        FileUtil.mkdir(docPath);
        for (String protobufPath : protobufPaths) {
            final String folderName = protobufPath.replace(classesPath + Constant.SLASH, Constant.EMPTY)
                    .replace(protocDependenciesPath + Constant.SLASH, Constant.EMPTY)
                    .replace(Constant.SLASH, "_");
            String cmd = (String.format(Constant.CMD_FORMAT, docPath, folderName, protobufPath)) + protoPath;
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            process.waitFor();
        }
    }

    private void loadProtobufPaths(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                loadProtobufPaths(Objects.requireNonNull(file.listFiles()));
            }
            if (file.getName().endsWith(Constant.PROTO_SUFFIX)) {
                addProtobufPaths(file.getParent());
            }
        }
    }

    private void addProtobufPaths(String path) {
        if (path.startsWith(protocDependenciesPath)) {
            final String childPath = path.substring(protocDependenciesPath.length() + 1);
            protocDependenciesPaths.add(protocDependenciesPath + Constant.SLASH + childPath.substring(0, childPath.indexOf(Constant.SLASH)));
        }
        protobufPaths.add(path);
    }

    public String getDocPath() {
        return docPath;
    }
}
