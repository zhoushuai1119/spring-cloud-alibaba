package com.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><日志工具类>
 * <p><日志工具类>
 * <p>
 */
public class LogUtil {

    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public final static String DEBUG_LEVEL = "debug";
    public final static String TRACE_LEVEL = "trace";
    public final static String WARN_LEVEL = "warn";
    public final static String INFO_LEVEL = "info";
    public final static String ERROR_LEVEL = "error";

    /**
     *
     * @param msg
     * @param level trace error info warn debug
     * @param e
     */
    public static void logger(String msg, String level, Throwable e) {
        if (ERROR_LEVEL.equals(level)) {
            if (e == null) {
                logger.error(msg);
            } else {
                logger.error(msg, e);
            }
        } else if (DEBUG_LEVEL.equals(level)) {
            if (e == null) {
                logger.debug(msg);
            } else {
                logger.debug(msg, e);
            }
        } else if (WARN_LEVEL.equals(level)) {
            if (e == null) {
                logger.warn(msg);
            } else {
                logger.warn(msg, e);
            }
        } else if (TRACE_LEVEL.equals(level)) {
            if (e == null) {
                logger.trace(msg);
            } else {
                logger.trace(msg, e);
            }
        } else {
            if (e == null) {
                logger.info(msg);
            } else {
                logger.info(msg, e);
            }
        }
    }
}
