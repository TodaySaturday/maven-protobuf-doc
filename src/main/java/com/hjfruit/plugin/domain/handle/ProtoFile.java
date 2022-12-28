package com.hjfruit.plugin.domain.handle;

import java.util.Collection;

/**
 * @author xianping
 * 2022/7/422:40
 */
public class ProtoFile {
    private String name;
    private String description;
    private String packageName;
    private boolean hasEnums;
    private boolean hasExtensions;
    private boolean hasMessages;
    private boolean hasServices;
    private Collection<ProtoEnum> enums;
    private Collection<String> extensions;
    private Collection<ProtoMessage> messages;
    private Collection<ProtoService> services;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isHasEnums() {
        return hasEnums;
    }

    public void setHasEnums(boolean hasEnums) {
        this.hasEnums = hasEnums;
    }

    public boolean isHasExtensions() {
        return hasExtensions;
    }

    public void setHasExtensions(boolean hasExtensions) {
        this.hasExtensions = hasExtensions;
    }

    public boolean isHasMessages() {
        return hasMessages;
    }

    public void setHasMessages(boolean hasMessages) {
        this.hasMessages = hasMessages;
    }

    public boolean isHasServices() {
        return hasServices;
    }

    public void setHasServices(boolean hasServices) {
        this.hasServices = hasServices;
    }

    public Collection<ProtoEnum> getEnums() {
        return enums;
    }

    public void setEnums(Collection<ProtoEnum> enums) {
        this.enums = enums;
    }

    public Collection<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(Collection<String> extensions) {
        this.extensions = extensions;
    }

    public Collection<ProtoMessage> getMessages() {
        return messages;
    }

    public void setMessages(Collection<ProtoMessage> messages) {
        this.messages = messages;
    }

    public Collection<ProtoService> getServices() {
        return services;
    }

    public void setServices(Collection<ProtoService> services) {
        this.services = services;
    }
}
