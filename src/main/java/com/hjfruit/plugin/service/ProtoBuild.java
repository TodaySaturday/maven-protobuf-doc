package com.hjfruit.plugin.service;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.dto.*;
import com.hjfruit.plugin.domain.dto.conf.DocProperties;
import com.hjfruit.plugin.domain.dto.conf.DocUpload;
import com.hjfruit.plugin.domain.enums.MessageStr;
import com.hjfruit.plugin.domain.utils.StrExpressionUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xianping
 * 2022/7/521:44
 */
public class ProtoBuild {

    private final DocProperties docProperties;

    private final Map<String, ProtoEnum> enumsMap;

    private final Map<String, ProtoMessage> messagesMap;

    private final Collection<DocUpload> docUploads = new HashSet<>();

    public ProtoBuild(DocProperties properties, ProtoHandle protoHandle) {
        this.docProperties = properties;
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
        final String directory = StrExpressionUtil.isNotBlankValue(model.getServiceDescription(), model.getServiceName());
        final String fileName = StrExpressionUtil.isNotBlankValue(model.getServiceMethod().getDescription(), model.getServiceMethod().getName());
        // 第五步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        try (Writer out = new FileWriter(fileName)) {
            // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是FreeMarker对于的版本号。
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 第二步：设置模板文件所在的路径。
            configuration.setDirectoryForTemplateLoading(new File(Constant.TEMPLATE_DIRECTORY));
            // 第三步：设置模板文件使用的字符集。一般就是utf-8.
            configuration.setDefaultEncoding("utf-8");
            // 第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate(Constant.TEMPLATE_PATH);
            // 第六步：调用模板对象的process方法输出文件。
            template.process(model, out);
            final DocUpload docUpload = new DocUpload();
            docUpload.setApi_key(docProperties.getApiKey());
            docUpload.setApi_token(docProperties.getApiToken());
            docUpload.setCat_name(directory);
            docUpload.setPage_title(fileName);
            // TODO 返回json内容
//        docUpload.setPage_content(content);
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
        // 构建额外数据
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
            if (Objects.nonNull(protoEnum)) {
                protoModel.getExtraEnums().add(protoEnum);
                continue;
            }
            final ProtoMessage protoMessage = messagesMap.get(type);
            protoModel.getExtraMessages().add(protoMessage);
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

    public Collection<DocUpload> getDocUploads() {
        return docUploads;
    }
}
