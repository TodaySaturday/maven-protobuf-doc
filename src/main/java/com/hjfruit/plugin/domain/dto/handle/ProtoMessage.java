package com.hjfruit.plugin.domain.dto.handle;

import java.util.Collection;

/**
 * @author xianping
 * 2022/7/422:46
 */
public class ProtoMessage {
    private String name;
    private String longName;
    private String fullName;
    private String description;
    private boolean hasExtensions;
    private boolean hasFields;
    private boolean hasOneofs;
    private Collection<String> extensions;
    private Collection<ProtoMessageField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description.replace("\n", " ");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasExtensions() {
        return hasExtensions;
    }

    public void setHasExtensions(boolean hasExtensions) {
        this.hasExtensions = hasExtensions;
    }

    public boolean isHasFields() {
        return hasFields;
    }

    public void setHasFields(boolean hasFields) {
        this.hasFields = hasFields;
    }

    public boolean isHasOneofs() {
        return hasOneofs;
    }

    public void setHasOneofs(boolean hasOneofs) {
        this.hasOneofs = hasOneofs;
    }

    public Collection<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(Collection<String> extensions) {
        this.extensions = extensions;
    }

    public Collection<ProtoMessageField> getFields() {
        return fields;
    }

    public void setFields(Collection<ProtoMessageField> fields) {
        this.fields = fields;
    }
}
