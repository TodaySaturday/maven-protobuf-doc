package com.hjfruit.plugin.domain.dto;

/**
 * @author xianping
 * 2022/7/422:47
 */
public class ProtoMessageField {
    private String name;
    private String description;
    private String label;
    private String type;
    private String longType;
    private String fullType;
    private boolean ismap;
    private boolean isoneof;
    private String oneofdecl;
    private String defaultValue;

    public boolean customMessage() {
        return fullType.contains(".");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongType() {
        return longType;
    }

    public void setLongType(String longType) {
        this.longType = longType;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public boolean isIsmap() {
        return ismap;
    }

    public void setIsmap(boolean ismap) {
        this.ismap = ismap;
    }

    public boolean isIsoneof() {
        return isoneof;
    }

    public void setIsoneof(boolean isoneof) {
        this.isoneof = isoneof;
    }

    public String getOneofdecl() {
        return oneofdecl;
    }

    public void setOneofdecl(String oneofdecl) {
        this.oneofdecl = oneofdecl;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
