package com.hjfruit.plugin.domain.utils;

/**
 * @author xianping
 * 2022/7/820:52
 */
public class StrExpUtils {

    private StrExpUtils() {
    }

    public static <T extends String> T isNonNullValue(T val1, T val2) {
        return null != val1 ? val1 : val2;
    }

    public static <T extends String> T isNotBlankValue(T val1, T val2) {
        return null != val1 && val1.length() > 0 ? val1 : val2;
    }
}
