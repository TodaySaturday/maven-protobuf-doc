package com.hjfruit.plugin.domain.func;

/**
 * @author xianping
 * @date 2022/5/16 14:56
 */
@FunctionalInterface
public interface BranchHandlerFunction {

    static BranchHandlerFunction trueOrFalseHandler(boolean flag) {
        return (trueHandler, falseHandler) -> {
            if (flag) {
                trueHandler.run();
            } else {
                falseHandler.run();
            }
        };
    }

    void trueOrFalseHandler(Runnable trueHandler, Runnable falseHandler);
}
