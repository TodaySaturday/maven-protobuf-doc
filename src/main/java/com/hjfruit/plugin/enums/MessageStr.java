package com.hjfruit.plugin.enums;

/**
 * @author xianping
 * 2022/6/2219:43
 */
public enum MessageStr {

    DIRECTORY_ERROR("请选择Protobuf所在模块Target目录执行！！！"),

    NOT_FOUND_PROTOBUF("未查到到protobuf文件！！！"),

    FINISH("处理完毕！"),

    NO_ITEMS_SELECTED("请指定文档对应项目！"),

    NOT_NULL_FILED("补全必填数据:【%s】"),

    ERROR_FORMAT("%s格式异常！"),

    ERROR_PROPERTIES("配置加载失败：%s"),

    NOT_FOUND_PROPERTIES("未查找到配置文件"),

    CONNECTION_TIMEOUT("连接超时"),

    NONSUPPORT_SYSTEM("暂不支持%s操作系统"),

    ;

    private final String message;

    MessageStr(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
