package com.hjfruit.plugin.domain.dto.conf;

/**
 * @author xianping
 * 2022/6/2122:44
 */
public class DocProperties {

    private String url;

    private String project;

    private String apiKey;

    private String apiToken;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    @Override
    public String toString() {
        return project;
    }
}
