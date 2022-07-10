package com.hjfruit.plugin.domain.func;

import java.util.Collection;

/**
 * @author xianping
 * @date 2022/6/18 15:14
 */
@FunctionalInterface
public interface ConditionHandlerFunction {

    static ConditionHandlerFunction isNotEmpty(Collection<?> collection) {
        return runnable -> {
            if (!collection.isEmpty()) {
                runnable.run();
            }
        };
    }

    static ConditionHandlerFunction isTrue(boolean flag) {
        return runnable -> {
            if (flag) {
                runnable.run();
            }
        };
    }

    void execute(Runnable runnable);
}
