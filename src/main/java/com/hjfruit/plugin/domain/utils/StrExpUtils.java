package com.hjfruit.plugin.domain.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author xianping
 * 2022/7/820:52
 */
public class StrExpUtils {

    private StrExpUtils() {
    }

    public static <T extends String> T isNonNullValue(T val1, T val2) {
        return Objects.nonNull(val1) ? val1 : val2;
    }

    public static <T extends String> T isNotBlankValue(T val1, T val2) {
        return Objects.nonNull(val1) && !val1.isEmpty() && Objects.equals(val1, StringUtils.EMPTY) ? val1 : val2;
    }
}
