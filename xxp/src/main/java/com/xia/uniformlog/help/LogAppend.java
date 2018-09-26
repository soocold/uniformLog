package com.xia.uniformlog.help;

import com.xia.uniformlog.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志输出类
 *
 * @author xia
 * @since 2017/12/10 20:36
 */
public class LogAppend {
    /**
     * 外部定义的日志输出loggerName
     */
    private String loggerName;
    /**
     * 日志输出列分割符
     */
    private String split;
    /**
     * slf4j logger
     */
    private static Logger logger;

    public LogAppend(String loggerName, String split) {
        this.loggerName = loggerName;
        this.split = split;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    /**
     * 格式化日志
     *
     * @param logParams
     */
    public void formatLog(LogParams logParams) {
        if (logParams == null) {
            return;
        }
        if (logger == null) {
            synchronized (LogAppend.class) {
                logger = LoggerFactory.getLogger(loggerName);
            }
        }
        logger.error(LogUtil.formatLog(logParams, split));
    }
}
