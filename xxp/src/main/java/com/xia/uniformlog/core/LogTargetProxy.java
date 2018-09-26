package com.xia.uniformlog.core;

import com.xia.uniformlog.annotion.LogMonitor;
import com.xia.uniformlog.help.LogAppend;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;

/**
 * TODO LogTargetProxy类的描述
 *
 * @author xia
 * @since 2017/12/10 21:46
 */
public class LogTargetProxy extends AbstractAutoProxyCreator {
    private static final long serialVersionUID = -8667601909801239996L;

    private LogManager logManager;

    public LogTargetProxy() {
        this.setProxyTargetClass(true);
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        if (beanName.startsWith("(inner bean)#") || beanName.startsWith("org.springframework")) {
            return DO_NOT_PROXY;
        }
        boolean isNeedProxy = needProxy(beanClass);
        if (logManager.getPackagePrefixes() != null && !logManager.getPackagePrefixes().isEmpty()) {
            boolean needProxy = false;
            for (String tmp : logManager.getPackagePrefixes()) {
                if (StringUtils.isBlank(tmp)) {
                    continue;
                }
                if (beanClass.toString().contains(tmp + ".")) {
                    needProxy = true;
                    break;
                }
            }
            if (!needProxy) {
                return DO_NOT_PROXY;
            }
        }

        if (!isNeedProxy) {
            if (beanClass.toString().contains("$$EnhanceBySpringCGLIB")) {
                try {
                    String clazzStr = beanClass.toString().split("\\$\\$EnhanceBySpringCGLIB")[0];
                    //Class com.xxx.xxx$$EnhanceBySpring$xxx
                    Class clazz = this.getClass().getClassLoader().loadClass(clazzStr.substring(6));
                    isNeedProxy = needProxy(clazz);
                } catch (Exception e) {
                    //log.error(e.getMessage, e);
                }

            }
        }
        if (isNeedProxy) {
            LogInterceptor li = new LogInterceptor();
            li.setBeanName(beanName);
            li.setResultJudgeAPI(logManager.getResultJudgeAPI());
            li.setLogAppend(new LogAppend(logManager.getLoggerName(), logManager.getSplit()));
            return new LogInterceptor[]{li};
        }
        return DO_NOT_PROXY;
    }

    /**
     * bean是否需要代理
     *
     * @param beanClass beanClass
     * @return
     */
    private boolean needProxy(Class<?> beanClass) {
        Method[] ms = beanClass.getMethods();
        if (ms == null || ms.length == 0) {
            return false;
        }
        for (Method tmp : ms) {
            LogMonitor monitor = tmp.getAnnotation(LogMonitor.class);
            if (monitor != null && monitor.useLogProxy()) {
                return true;
            }
        }
        return false;
    }

    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }
}
