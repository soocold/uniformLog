package com.xia.uniformlog.core;

import com.xia.uniformlog.annotion.LogMonitor;
import com.xia.uniformlog.api.ResultJudgeAPI;
import com.xia.uniformlog.help.LogAppend;
import com.xia.uniformlog.help.LogParams;
import com.xia.uniformlog.util.CommonUtil;
import com.xia.uniformlog.util.LogUtil;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 日志代理方法拦截器
 *
 * @author xia
 * @since 2017/12/10 20:33
 */
public class LogInterceptor implements MethodInterceptor, Advisor {
    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    private String beanName;
    private LogAppend logAppend;
    private ResultJudgeAPI resultJudgeAPI;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LogMonitor monitor = invocation.getMethod().getAnnotation(LogMonitor.class);
        Object result = handleLog(monitor, invocation);
        return result;
    }

    /**
     * 处理日志输出逻辑
     *
     * @param monitor
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object handleLog(LogMonitor monitor, MethodInvocation invocation) throws Throwable {
        if (monitor == null) {
            return invocation.proceed();
        }
        Object result = null;
        Method method = invocation.getMethod();
        Type returnType = method.getGenericReturnType();
        Class<?> returnClass = method.getReturnType();
        LogParams logParams = instanceFromMethodInfo(monitor, method, invocation);
        Long startTime = System.currentTimeMillis();
        boolean isSucess = true;
        try {
            result = invocation.proceed();
            try {
                if (resultJudgeAPI != null) {
                    isSucess = resultJudgeAPI.methodResultJudge(result, returnType, returnClass);
                }
            } catch (Throwable e) {
                LOG.error("method name {} result judge failed", method.getName(), e);
            }
            return result;
        } catch (Throwable t) {
            isSucess = false;
            throw t;
        } finally {
            logParams.setCost(System.currentTimeMillis() - startTime);
            logParams.setIsSuccess(isSucess);
            logAppend.formatLog(logParams);
        }

    }

    private LogParams instanceFromMethodInfo(LogMonitor monitor, Method method, MethodInvocation invocation) {
        LogParams logParams = new LogParams();
        logParams.setInputParams(LogUtil.parseInputParams(monitor.params(), method, invocation.getArguments()));
        logParams.setBeanName(beanName);
        logParams.setBizType(monitor.bizType());
        logParams.setLocalIp(CommonUtil.getLocalIp());
        logParams.setMethodName(method.getName());

        return logParams;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public LogAppend getLogAppend() {
        return logAppend;
    }

    public void setLogAppend(LogAppend logAppend) {
        this.logAppend = logAppend;
    }

    public ResultJudgeAPI getResultJudgeAPI() {
        return resultJudgeAPI;
    }

    public void setResultJudgeAPI(ResultJudgeAPI resultJudgeAPI) {
        this.resultJudgeAPI = resultJudgeAPI;
    }
}
