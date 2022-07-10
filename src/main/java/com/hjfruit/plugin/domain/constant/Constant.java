package com.hjfruit.plugin.domain.constant;

/**
 * @author xianping
 * 2022/6/1117:20
 */
public interface Constant {

    String SLASH = "\\";

    String EMPTY = "";

    String DOCS = SLASH + "docs";

    String CLASSES = SLASH + "classes";

    String PROTOC_DEPENDENCIES = SLASH + "protoc-dependencies";

    String EXCLUDE_SERVICE_PACKAGE = "google.longrunning";

    String CMD_PROTO_PATH = " --proto_path=";

    String CMD_FORMAT = "protoc --doc_out=%s --doc_opt=json,%s.json %s\\*.proto";

    String TEMPLATE_DIRECTORY = "E:\\Projects\\maven-protobuf-doc\\src\\main\\resources\\template";

    String TEMPLATE_PATH = "protobuf.ftl";

    String PROPERTIES_NAME = "/config/showdoc.properties";

    String PROPERTIES_URL = "apiUrl";

    String PROTO_SUFFIX = ".proto";

    String TARGET = "target";

    String POPUP_READ = "读取protobuf……";

    String POPUP_HANDLE = "处理protobuf……";

    String POPUP_BUILD = "构建文档……";

    String POPUP_UPLOAD = "上传文档……";
}
