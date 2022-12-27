package com.hjfruit.plugin.domain.dto.conf;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.enums.MessageStr;
import com.hjfruit.plugin.domain.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xianping
 * 2022/6/2122:44
 */
public class DocProperties {

    private String path;

    private String classesPath;

    private String docPath;

    private String docJsonPath;

    private String docMdPath;

    private String protocDependenciesPath;

    private String protocFilePath;

    private String protocGenDocFilePath;

    private String apiUrl;

    private String apiKey;

    private String apiToken;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) throws IOException {
        this.path = path;
        this.classesPath = path + Constant.CLASSES;
        this.docPath = path + Constant.DOCS;
        this.docJsonPath = path + Constant.DOCS_JSON;
        this.docMdPath = path + Constant.DOCS_MD;
        this.protocDependenciesPath = path + Constant.PROTOC_DEPENDENCIES;
        // 模板目录
        this.setTemplateDirectory();
        // proto执行文件
        this.setProtocFilePath();
        this.setProtocGenDocFilePath();
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getClassesPath() {
        return classesPath;
    }

    public String getDocJsonPath() {
        return docJsonPath;
    }

    public String getProtocDependenciesPath() {
        return protocDependenciesPath;
    }

    public String getDocMdPath() {
        return docMdPath;
    }

    private void setTemplateDirectory() throws IOException {
        copyPropertyFile(Constant.TEMPLATE_DIRECTORY, Constant.TEMPLATE_FILE_NAME);
    }

    public String getDocPath() {
        return docPath;
    }

    public String getProtocFilePath() {
        return protocFilePath;
    }

    private void setProtocFilePath() throws IOException {
        final String filePath = Constant.PLUGIN_DIRECTORY + getPluginName(Constant.PROTOC);
        copyPropertyFile(filePath, getPluginName(Constant.PROTOC));
        this.protocFilePath = docPath + File.separator + getPluginName(Constant.PROTOC);
    }

    public String getProtocGenDocFilePath() {
        return protocGenDocFilePath;
    }

    private void setProtocGenDocFilePath() throws IOException {
        final String filePath = Constant.PLUGIN_DIRECTORY + getPluginName(Constant.PROTOC_GEN_DOC);
        copyPropertyFile(filePath, getPluginName(Constant.PROTOC_GEN_DOC));
        this.protocGenDocFilePath = docPath + File.separator + getPluginName(Constant.PROTOC_GEN_DOC);
    }

    private void copyPropertyFile(String filePath, String fileName) throws IOException {
        final InputStream inputStream = DocProperties.class.getResourceAsStream(filePath);
        if (null == inputStream) {
            throw new IOException(String.format(MessageStr.ERROR_PROPERTIES.getMessage(), filePath));
        }
        FileUtils.copyInputStreamToFile(inputStream, new File(this.docPath + File.separator + fileName));
    }

    private String getPluginName(String pluginName) throws IOException {
        final String osName = System.getProperty("os.name");
        final Map<String, Map<String, String>> pluginsMap = new HashMap<>();
        final HashMap<String, String> windowsPluginMap = new HashMap<>();
        windowsPluginMap.put(Constant.PROTOC, "protoc-3.19.3-windows-x86_64.exe");
        windowsPluginMap.put(Constant.PROTOC_GEN_DOC, "protoc-gen-doc_1.5.1_windows_amd64.exe");
        final HashMap<String, String> linuxPluginMap = new HashMap<>();
        linuxPluginMap.put(Constant.PROTOC, "protoc-3.19.3-linux-x86_64");
        linuxPluginMap.put(Constant.PROTOC_GEN_DOC, "protoc-gen-doc_1.5.1_linux_amd64");
        pluginsMap.put("Windows", windowsPluginMap);
        pluginsMap.put("Linux", linuxPluginMap);
        final String systemKey = pluginsMap.keySet()
                .stream()
                .filter(osName::contains)
                .findFirst()
                .orElseThrow(() -> new IOException(String.format(MessageStr.NONSUPPORT_SYSTEM.getMessage(), osName)));
        final Map<String, String> pluginMap = pluginsMap.get(systemKey);
        return pluginMap.get(pluginName);
    }
}
