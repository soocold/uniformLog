package com.xia.uniformlog.util;

import com.xia.uniformlog.help.LogParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * 日志工具类
 *
 * @author xia
 * @since 2017/12/10 20:52
 */
public class LogUtil {
    /**
     * 反射生成的方法缓存，LRU规则，最大缓存最近1000个
     */
    private static Map<String, Method> methodCache = Collections.synchronizedMap(new LRUCache<String, Method>());

    public static Object[] parseInputParams(String paramConfig, Method method, Object[] params) {
        if (StringUtils.isBlank(paramConfig)) {
            return params;
        }
        String[] configs = paramConfig.split(",");
        int configSize = configs != null && configs.length > 0 ? configs.length : method.getParameterTypes().length;
        Object[] realParameters = new Object[configSize];

        Object[] useParameters = null;
        if (configs != null && configs.length > 0) {
            for (int i = 0; i < configs.length; i++) {
                String p = StringUtils.trimToEmpty(configs[i]);
                if (p.contains(".")) {
                    String[] props = p.split("\\.");
                    try {
                        int idx = Integer.valueOf(StringUtils.trimToEmpty(props[0].substring(1)));
                        Object val = null;
                        for (int j = 1; j < props.length; j++) {
                            Object obj = null;
                            if (j == 1) {
                                obj = params[idx];
                            } else {
                                obj = val;
                            }

                            if (obj != null) {
                                String prop = StringUtils.trimToEmpty(props[j]);
                                if (StringUtils.isNotEmpty(prop)) {
                                    String methodName = "get" + prop.substring(0, 1).toUpperCase() + (prop.length() <= 1 ? "" : prop.substring(1));
                                    Method m = getMethodFromCache(methodName, obj.getClass());
                                    boolean isCache = false;
                                    if (m == null) {
                                        m = BeanUtils.findMethod(obj.getClass(), methodName, null);
                                    } else {
                                        isCache = true;
                                    }
                                    if (m != null) {
                                        if (isCache == false) {
                                            putMethodCache(methodName, obj.getClass(), m);
                                        }
                                        val = m.invoke(obj, null);
                                    }
                                }

                                if (val == null) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        realParameters[i] = val;
                    } catch (Exception e) {

                    }
                }
            }
        }
        return useParameters;
    }

    /**
     * 从缓存里面读取方法
     *
     * @param methodName 方法名
     * @param clazz      类名
     * @return
     */
    public static Method getMethodFromCache(String methodName, Class<?> clazz) {
        return methodCache.get(clazz.toString() + "_" + methodName);
    }

    /**
     * 把方法放入缓存
     *
     * @param methodName 方法名
     * @param clazz      类名
     * @param m          方法
     */
    public static void putMethodCache(String methodName, Class<?> clazz, Method m) {
        methodCache.put(clazz.toString() + "_" + methodName, m);
    }

    /**
     * 日志格式化输出
     * @param logParams 日志参数
     * @param split 分隔符
     * @return
     */
    public static String formatLog(LogParams logParams, String split) {
        if(logParams == null){
            return null;
        }
        if(StringUtils.isEmpty(split)) {
            split = "|";
        }
        return getFormatLog(logParams,split);
    }

    private static String getFormatLog(LogParams logParams, String split){
        StringBuilder sb= new StringBuilder();
        sb.append(logParams.getBeanName()).append(split);
        sb.append(logParams.getMethodName()).append(split);
        sb.append(logParams.getCost()).append(split);
        sb.append(logParams.getLocalIp()).append(split);
        sb.append(logParams.getBizType()).append(split);
        sb.append(SerialzeUtil.serizlzeParams(logParams.getInputParams())).append(split);
        sb.append(logParams.getIsSuccess());

        return sb.toString();
    }
}
