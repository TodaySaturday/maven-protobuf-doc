package com.hjfruit.plugin.service;

import com.hjfruit.plugin.ProtoDocMojo;
import com.hjfruit.plugin.constant.Constant;
import com.hjfruit.plugin.domain.conf.DocUpload;
import com.hjfruit.plugin.domain.handle.*;
import com.hjfruit.plugin.enums.MessageStr;
import com.hjfruit.plugin.enums.ProtoProcess;
import com.hjfruit.plugin.utils.FileUtils;
import com.hjfruit.plugin.utils.StrExpUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xianping
 * 2022/7/521:44
 */
public class ProtoBuild {

    private final Map<String, ProtoEnum> enumsMap;

    private final Map<String, ProtoMessage> messagesMap;

    private final Collection<DocUpload> docUploads = new HashSet<>();

    public ProtoBuild(ProtoHandle protoHandle) {
        this.messagesMap = protoHandle.getMessagesMap();
        this.enumsMap = protoHandle.getEnumsMap();
        protoHandle.getServicesCollection()
                .stream()
                .filter(service -> !service.getFullName().contains(Constant.EXCLUDE_SERVICE_PACKAGE))
                .forEach(service -> {
                    final ProtoModel protoModel = new ProtoModel();
                    protoModel.setServiceName(service.getName());
                    protoModel.setFullServiceName(service.getFullName());
                    protoModel.setServiceDescription(service.getDescription());
                    service.getMethods().forEach(method -> buildProtobufMd(loadDetailModel(protoModel, method)));
                });
    }

    private void buildProtobufMd(ProtoModel model) {
        final String directory = StrExpUtils.isNotBlankValue(model.getServiceDescription(), model.getServiceName());
        final String fileName = StrExpUtils.isNotBlankValue(model.getServiceMethod().getDescription(), model.getServiceMethod().getName());
        ProtoDocMojo.getLogger().info(String.format(ProtoProcess.PROCESS_BUILD.getProcess(), fileName));
        FileUtils.mkdir(ProtoDocMojo.getProperties().getDocMdPath() + Constant.SLASH + directory);
        // ????????????????????????Writer????????????????????????FileWriter????????????????????????????????????
        final String filePath = ProtoDocMojo.getProperties().getDocMdPath() + Constant.SLASH + directory + Constant.SLASH + fileName;
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {
            // ????????????????????????Configuration???????????????new??????????????????????????????????????????FreeMarker?????????????????????
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
            // ????????????????????????????????????????????????
            configuration.setDirectoryForTemplateLoading(new File(ProtoDocMojo.getProperties().getDocPath()));
            // ???????????????????????????????????????????????????????????????utf-8.
            configuration.setDefaultEncoding("utf-8");
            // ????????????????????????????????????????????????????????????
            Template template = configuration.getTemplate(Constant.TEMPLATE_FILE_NAME);
            // ?????????????????????????????????process?????????????????????
            template.process(model, out);
            final DocUpload docUpload = new DocUpload();
            docUpload.setApi_key(ProtoDocMojo.getProperties().getApiKey());
            docUpload.setApi_token(ProtoDocMojo.getProperties().getApiToken());
            docUpload.setCat_name(directory);
            docUpload.setPage_title(fileName);
            final String content = FileUtils.fileRead(filePath);
            docUpload.setPage_content(content);
            docUploads.add(docUpload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProtoModel loadDetailModel(ProtoModel protoModel, ProtoServiceMethod method) {
        protoModel.setServiceMethod(null);
        protoModel.setRequest(null);
        protoModel.setResponse(null);
        protoModel.getExtraEnums().clear();
        protoModel.getExtraMessages().clear();
        protoModel.setServiceMethod(method);
        final ProtoMessage requestMessage = Optional.ofNullable(messagesMap.get(method.getRequestFullType()))
                .orElseThrow(() -> new RuntimeException(String.format(MessageStr.ERROR_FORMAT.getMessage(), protoModel.getServiceName())));
        final ProtoMessage responseMessage = Optional.ofNullable(messagesMap.get(method.getResponseFullType()))
                .orElseThrow(() -> new RuntimeException(String.format(MessageStr.ERROR_FORMAT.getMessage(), protoModel.getServiceName())));
        protoModel.setRequest(requestMessage);
        protoModel.setResponse(responseMessage);
        // ??????????????????
        final Set<String> types = Stream.concat(
                        requestMessage.getFields()
                                .stream()
                                .filter(ProtoMessageField::customMessage)
                                .map(ProtoMessageField::getFullType),
                        responseMessage.getFields()
                                .stream()
                                .filter(ProtoMessageField::customMessage)
                                .map(ProtoMessageField::getFullType)
                )
                .collect(Collectors.toSet());
        this.loadExtraModel(protoModel, types);
        return protoModel;
    }

    private void loadExtraModel(ProtoModel protoModel, Set<String> types) {
        for (String type : types) {
            final ProtoEnum protoEnum = enumsMap.get(type);
            if (null != protoEnum) {
                protoModel.getExtraEnums().add(protoEnum);
                continue;
            }
            final ProtoMessage protoMessage = messagesMap.get(type);
            if (protoModel.getExtraMessages().add(protoMessage)) {
                final Set<String> typeSet = protoMessage.getFields()
                        .stream()
                        .filter(ProtoMessageField::customMessage)
                        .map(ProtoMessageField::getFullType)
                        .collect(Collectors.toSet());
                if (!typeSet.isEmpty()) {
                    loadExtraModel(protoModel, typeSet);
                }
            }
        }
    }

    public Collection<DocUpload> getDocUploads() {
        return docUploads;
    }
}
