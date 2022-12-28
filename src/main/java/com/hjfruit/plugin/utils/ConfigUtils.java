package com.hjfruit.plugin.utils;

import com.hjfruit.plugin.constant.Constant;
import com.hjfruit.plugin.enums.MessageStr;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author xianping
 * 2022/7/1018:40
 */
public class ConfigUtils {

    private ConfigUtils() {
    }

    public static String propertyValue(String key) throws IOException {
        final URL url = ConfigUtils.class.getResource(Constant.PROPERTIES_NAME);
        if (null == url) {
            throw new IOException(MessageStr.NOT_FOUND_PROPERTIES.getMessage());
        }
        try (InputStream inputStream = new BufferedInputStream(url.openStream())) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new IOException(String.format(MessageStr.ERROR_PROPERTIES.getMessage(), e.getMessage()));
        }
    }
}
