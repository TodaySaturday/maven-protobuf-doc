package com.hjfruit.plugin.constant;

import java.io.File;

/**
 * @author xianping
 * 2022/6/1117:20
 */
public abstract class Constant {

    private Constant() {
    }

    public static final String SLASH = File.separator;

    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static final String DOCS = SLASH + "docs";

    public static final String DOCS_JSON = DOCS + SLASH + "json";

    public static final String DOCS_MD = DOCS + SLASH + "md";

    public static final String CLASSES = SLASH + "classes";

    public static final String PROTOC_DEPENDENCIES = SLASH + "protoc-dependencies";

    public static final String EXCLUDE_SERVICE_PACKAGE = "google.longrunning";

    public static final String VERSION_README_NAME = "README";

    public static final String VERSION_README_DIRECTORY = "版本变更记录";

    public static final String CMD_PROTO_PATH = " --proto_path=";

    public static final String CMD_FORMAT = "%s --plugin=protoc-gen-doc=%s --doc_out=%s --doc_opt=json,%s.json %s" + SLASH + "*.proto";

    public static final String PLUGIN_DIRECTORY = "/plugin/";

    public static final String TEMPLATE_FILE_NAME = "protobuf.ftl";

    public static final String TEMPLATE_DIRECTORY = "/template/" + TEMPLATE_FILE_NAME;

    public static final String PROPERTIES_NAME = "/config/showdoc.properties";

    public static final String PROPERTIES_URL_FORMAT = "apiUrlFormat";

    public static final String PROTO_SUFFIX = ".proto";

    public static final String PROTOC = "PROTOC";

    public static final String PROTOC_GEN_DOC = "PROTOC_GEN_DOC";

    public static final String WINDOWS = "Windows";

    public static final String LINUX = "Linux";
}
