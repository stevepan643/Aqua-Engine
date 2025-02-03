package com.steve.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    public static Logger getLogger() {
        return LoggerFactory.getLogger(STACK_WALKER.getCallerClass());
    }
}
