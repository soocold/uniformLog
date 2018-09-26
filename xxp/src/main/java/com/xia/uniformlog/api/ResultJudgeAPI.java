package com.xia.uniformlog.api;

import java.lang.reflect.Type;

/**
 * 用于方法调用结果是否成功的判定，默认方法抛出异常为失败，其他情况为true
 *
 * @author xia
 * @since 2017/12/10 20:29
 */
public interface ResultJudgeAPI {
    /**
     * 用于判定方法调用是否成功
     *
     * @param methodResult LogMonitor注解代理的方法返回值
     * @param returnType   返回值类型
     * @param returnClass  返回值对象
     * @return 方法调用是否成功
     */
    boolean methodResultJudge(Object methodResult, Type returnType, Class<?> returnClass);
}
