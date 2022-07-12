package com.hjfruit.plugin.service;

import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.enums.MessageStr;
import com.hjfruit.plugin.domain.enums.ProtoProcess;
import com.hjfruit.plugin.domain.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author xianping
 * @date 2022/7/3 10:49
 */
public class ProtoRead {

    private final Set<String> protobufPaths = new HashSet<>();

    private final Set<String> protocDependenciesPaths = new HashSet<>();

    public ProtoRead() throws IOException, InterruptedException {
        protocDependenciesPaths.add(ProtoDocMojo.getProperties().getClassesPath());
        final File[] readFiles = new File(ProtoDocMojo.getProperties().getPath()).listFiles();
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
        FileUtils.mkdir(ProtoDocMojo.getProperties().getDocJsonPath());
        for (String protobufPath : protobufPaths) {
            ProtoDocMojo.getLogger().info(String.format(ProtoProcess.PROCESS_READ.getProcess(), protobufPath));
            final String folderName = protobufPath.replace(ProtoDocMojo.getProperties().getClassesPath() + Constant.SLASH, Constant.EMPTY)
                    .replace(ProtoDocMojo.getProperties().getProtocDependenciesPath() + Constant.SLASH, Constant.EMPTY)
                    .replace(Constant.SLASH, "_");
            String cmd = (String.format(Constant.CMD_FORMAT, ProtoDocMojo.getProperties().getProtocFilePath(),ProtoDocMojo.getProperties().getProtocGenDocFilePath(),ProtoDocMojo.getProperties().getDocJsonPath(), folderName, protobufPath)) + protoPath;
            ProtoDocMojo.getLogger().info(cmd);
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            process.waitFor();
        }
    }

    private void loadProtobufPaths(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                loadProtobufPaths(file.listFiles());
            }
            if (file.getName().endsWith(Constant.PROTO_SUFFIX)) {
                addProtobufPaths(file.getParent());
            }
        }
    }

    private void addProtobufPaths(String path) {
        if (path.startsWith(ProtoDocMojo.getProperties().getProtocDependenciesPath())) {
            final String childPath = path.substring(ProtoDocMojo.getProperties().getProtocDependenciesPath().length() + 1);
            protocDependenciesPaths.add(ProtoDocMojo.getProperties().getProtocDependenciesPath() + Constant.SLASH + childPath.substring(0, childPath.indexOf(Constant.SLASH)));
        }
        protobufPaths.add(path);
    }
}
