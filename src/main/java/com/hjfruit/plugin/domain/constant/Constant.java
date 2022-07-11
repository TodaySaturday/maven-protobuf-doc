package com.hjfruit.plugin.domain.constant;

/**
 * @author xianping
 * 2022/6/1117:20
 */
public abstract class Constant {

    private Constant() {
    }

    public static final String SLASH = "\\";

    public static final String EMPTY = "";

    public static final String DOCS = SLASH + "docs";

    public static final String DOCS_JSON = DOCS + SLASH + "json";

    public static final String DOCS_MD = DOCS + SLASH + "md";

    public static final String CLASSES = SLASH + "classes";

    public static final String PROTOC_DEPENDENCIES = SLASH + "protoc-dependencies";

    public static final String EXCLUDE_SERVICE_PACKAGE = "google.longrunning";

    public static final String CMD_PROTO_PATH = " --proto_path=";

    public static final String CMD_FORMAT = "protoc --doc_out=%s --doc_opt=json,%s.json %s\\*.proto";

    public static final String TEMPLATE_DIRECTORY = "/template";

    public static final String TEMPLATE_PATH = "protobuf.ftl";

    public static final String PROPERTIES_NAME = "/config/showdoc.properties";

    public static final String PROPERTIES_URL = "apiUrl";

    public static final String PROTO_SUFFIX = ".proto";

    public static final String PROTO_MD = ".md";

    public static final String TARGET = "target";

    public static final String POPUP_READ = "读取protobuf……";

    public static final String POPUP_HANDLE = "处理protobuf……";

    public static final String POPUP_BUILD = "构建文档……";

    public static final String POPUP_UPLOAD = "上传文档……";
}
