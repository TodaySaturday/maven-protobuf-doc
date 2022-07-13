package com.hjfruit.plugin.domain.enums;

/**
 * @author xianping
 * 2022/7/1123:05
 */
public enum ProtoProcess {

    PROCESS_START("---------------< com.hjfruit.plugin.maven-protobuf-doc:1.0.0-SNAPSHOT START>----------------"),

    PROCESS_READ("读取protobuf:%s"),

    PROCESS_HANDLE("处理protobuf:%s"),

    PROCESS_BUILD("构建文档:%s"),

    PROCESS_UPLOAD("上传文档:%s"),

    PROCESS_README("构建版本变更记录"),

    PROCESS_END("---------------< com.hjfruit.plugin.maven-protobuf-doc:1.0.0-SNAPSHOT END>----------------"),

    ;

    private final String process;

    ProtoProcess(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }
}
