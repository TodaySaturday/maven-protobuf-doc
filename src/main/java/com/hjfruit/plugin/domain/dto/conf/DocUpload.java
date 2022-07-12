package com.hjfruit.plugin.domain.dto.conf;

/**
 * @author xianping
 * 2022/6/1622:08
 */
public class DocUpload {

    private String api_key;

    private String api_token;

    private String cat_name;

    private String page_title;

    private String page_content;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getCat_name() {
        if (null == this.cat_name) {
            return "";
        }
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }
}
