package com.hjfruit.plugin.domain.dto.handle;

import java.util.Collection;

/**
 * @author xianping
 * 2022/7/422:43
 */
public class ProtoEnum {
    private String name;
    private String longName;
    private String fullName;
    private String description;
    private Collection<ProtoEnumValue> values;

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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ProtoEnumValue> getValues() {
        return values;
    }

    public void setValues(Collection<ProtoEnumValue> values) {
        this.values = values;
    }
}
