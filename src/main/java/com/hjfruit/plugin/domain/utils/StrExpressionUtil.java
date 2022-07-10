package com.hjfruit.plugin.domain.utils;

import java.util.Objects;

/**
 * @author xianping
 * 2022/7/820:52
 */
public class StrExpressionUtil {

    private StrExpressionUtil() {
    }

    public static <T extends String> T isNonNullValue(T val1, T val2) {
        return Objects.nonNull(val1) ? val1 : val2;
    }

    public static <T extends String> T isNotBlankValue(T val1, T val2) {
        return Objects.nonNull(val1) && !val1.isBlank() ? val1 : val2;
    }
}
