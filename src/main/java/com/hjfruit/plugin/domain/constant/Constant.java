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

    public static final String PROPERTIES_URL_FORMAT = "apiUrlFormat";

    public static final String PROTO_SUFFIX = ".proto";
}
