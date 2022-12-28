package com.hjfruit.plugin.domain.conf;

import com.hjfruit.plugin.constant.Constant;
import com.hjfruit.plugin.enums.MessageStr;
import com.hjfruit.plugin.utils.FileUtils;

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

    public void setPath(String path) throws IOException, InterruptedException {
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

    private void setProtocFilePath() throws IOException, InterruptedException {
        final String filePath = Constant.PLUGIN_DIRECTORY + getPluginName(Constant.PROTOC);
        final String pluginPath = copyPropertyFile(filePath, getPluginName(Constant.PROTOC));
        this.protocFilePath = docPath + File.separator + getPluginName(Constant.PROTOC);
        settingPermission(pluginPath);
    }

    private void settingPermission(String pluginPath) throws IOException, InterruptedException {
        if (!getSystem().contains(Constant.LINUX)) {
            return;
        }
        final Runtime runtime = Runtime.getRuntime();
        String command = "chmod 770 " + pluginPath;
        Process process = runtime.exec(command);
        process.waitFor();
        process.exitValue();
    }

    public String getProtocGenDocFilePath() {
        return protocGenDocFilePath;
    }

    private void setProtocGenDocFilePath() throws IOException, InterruptedException {
        final String filePath = Constant.PLUGIN_DIRECTORY + getPluginName(Constant.PROTOC_GEN_DOC);
        final String pluginPath = copyPropertyFile(filePath, getPluginName(Constant.PROTOC_GEN_DOC));
        this.protocGenDocFilePath = docPath + File.separator + getPluginName(Constant.PROTOC_GEN_DOC);
        settingPermission(pluginPath);
    }

    private String copyPropertyFile(String filePath, String fileName) throws IOException {
        final InputStream inputStream = DocProperties.class.getResourceAsStream(filePath);
        if (null == inputStream) {
            throw new IOException(String.format(MessageStr.ERROR_PROPERTIES.getMessage(), filePath));
        }
        final File file = new File(this.docPath + File.separator + fileName);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file.getAbsolutePath();
    }

    private String getPluginName(String pluginName) throws IOException {
        final String osName = getSystem();
        final Map<String, Map<String, String>> pluginsMap = new HashMap<>();
        final HashMap<String, String> windowsPluginMap = new HashMap<>();
        windowsPluginMap.put(Constant.PROTOC, "protoc-3.19.3-windows-x86_64.exe");
        windowsPluginMap.put(Constant.PROTOC_GEN_DOC, "protoc-gen-doc_1.5.1_windows_amd64.exe");
        final HashMap<String, String> linuxPluginMap = new HashMap<>();
        linuxPluginMap.put(Constant.PROTOC, "protoc-3.19.3-linux-x86_64");
        linuxPluginMap.put(Constant.PROTOC_GEN_DOC, "protoc-gen-doc_1.5.1_linux_amd64");
        pluginsMap.put(Constant.WINDOWS, windowsPluginMap);
        pluginsMap.put(Constant.LINUX, linuxPluginMap);
        final String systemKey = pluginsMap.keySet()
                .stream()
                .filter(osName::contains)
                .findFirst()
                .orElseThrow(() -> new IOException(String.format(MessageStr.NONSUPPORT_SYSTEM.getMessage(), osName)));
        final Map<String, String> pluginMap = pluginsMap.get(systemKey);
        return pluginMap.get(pluginName);
    }

    private String getSystem() {
        return System.getProperty("os.name");
    }
}
