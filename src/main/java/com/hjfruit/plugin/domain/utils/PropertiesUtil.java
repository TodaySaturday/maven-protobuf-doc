package com.hjfruit.plugin.domain.utils;

import com.hjfruit.plugin.domain.constant.Constant;
import com.hjfruit.plugin.domain.enums.MessageStr;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

/**
 * @author xianping
 * 2022/7/1018:40
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    public static String getProperty(String key) throws IOException {
        final URL resource = Optional.ofNullable(PropertiesUtil.class.getResource(Constant.PROPERTIES_NAME))
                .orElseThrow(() -> new IOException(MessageStr.NOT_FOUND_PROPERTIES.getMessage()));
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(resource.getPath()))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new IOException(MessageStr.ERROR_PROPERTIES.getMessage());
        }
    }
}
