package com.xia.uniformlog.core;

import com.xia.uniformlog.api.ResultJudgeAPI;
import com.xia.uniformlog.help.LogAppend;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.List;

/**
 * 日志管理入口，使用时配置此bean
 *
 * @author xia
 * @since 2017/12/10 21:44
 */
public class LogManager implements Serializable, InitializingBean{
    private static final long serialVersionUID = 5538127958202862705L;
    /**
     * 日志列分隔符，列定义见{@link com.xia.uniformlog.help.LogParams}
     */
    private String split;
    /**
     * 希望输出的日志logggerName
     */
    private String loggerName;
    /**
     * 外部实现的方法结果判定bean
     */
    private ResultJudgeAPI resultJudgeAPI;
    /**
     * 日志append
     */
    private LogAppend logAppend;

    private LogTargetProxy logTargetProxy;
    /**
     * 需要代理的包名前缀，如果不在此包名前缀的bean，即使配置了注解也不会被代理
     */
    private List<String> packagePrefixes;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isBlank(loggerName)) {
            throw new Exception("init logManger failed, loggerName should not be null");
        }
        if(split == null) {
            split = "|";
        }
        logAppend = new LogAppend(loggerName, split);
        if(logTargetProxy != null){
            logTargetProxy.setLogManager(this);
        }
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public ResultJudgeAPI getResultJudgeAPI() {
        return resultJudgeAPI;
    }

    public void setResultJudgeAPI(ResultJudgeAPI resultJudgeAPI) {
        this.resultJudgeAPI = resultJudgeAPI;
    }

    public LogAppend getLogAppend() {
        return logAppend;
    }

    public void setLogAppend(LogAppend logAppend) {
        this.logAppend = logAppend;
    }

    public LogTargetProxy getLogTargetProxy() {
        return logTargetProxy;
    }

    public void setLogTargetProxy(LogTargetProxy logTargetProxy) {
        this.logTargetProxy = logTargetProxy;
    }

    public List<String> getPackagePrefixes() {
        return packagePrefixes;
    }

    public void setPackagePrefixes(List<String> packagePrefixes) {
        this.packagePrefixes = packagePrefixes;
    }
}
