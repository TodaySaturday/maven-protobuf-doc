package com.hjfruit.plugin.domain.dto;

import com.hjfruit.plugin.domain.constant.Constant;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xianping
 * @date 2022/7/6 19:53
 */
public class ProtoModel {

    Collection<ProtoEnum> extraEnums = new HashSet<>();
    Collection<ProtoMessage> extraMessages = new HashSet<>();
    private String serviceName;
    private String fullServiceName;
    private String serviceDescription;
    private ProtoServiceMethod serviceMethod;
    private ProtoMessage request;
    private ProtoMessage response;

    public Collection<ProtoEnum> getExtraEnums() {
        return extraEnums;
    }

    public void setExtraEnums(Collection<ProtoEnum> extraEnums) {
        this.extraEnums = extraEnums;
    }

    public Collection<ProtoMessage> getExtraMessages() {
        return extraMessages;
    }

    public void setExtraMessages(Collection<ProtoMessage> extraMessages) {
        this.extraMessages = extraMessages;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFullServiceName() {
        return fullServiceName;
    }

    public void setFullServiceName(String fullServiceName) {
        this.fullServiceName = fullServiceName;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public ProtoServiceMethod getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(ProtoServiceMethod serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public ProtoMessage getRequest() {
        return request;
    }

    public void setRequest(ProtoMessage request) {
        this.request = request;
    }

    public ProtoMessage getResponse() {
        return response;
    }

    public void setResponse(ProtoMessage response) {
        this.response = response;
    }

    public String getServiceDescription() {
        //正则表达式
        String regEx = "[\n`~!@#$%^&*()+=\\-_|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(serviceDescription);
        return matcher.replaceAll(Constant.EMPTY).trim();
    }
}
