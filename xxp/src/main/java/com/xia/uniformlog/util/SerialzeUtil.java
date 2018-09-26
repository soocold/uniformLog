package com.xia.uniformlog.util;

import com.alibaba.fastjson.JSON;

/**
 * 序列化工具，使用fastjson序列化参数
 *
 * @author xia
 * @since 2017/12/10 21:31
 */
public class SerialzeUtil {

    /**
     * 序列化多个参数
     *
     * @param params 参数
     * @return
     */
    public static String serizlzeParams(Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            sb.append(serialzeParam(params[i]));
            if (i != params.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 序列化单个参数
     *
     * @param param 参数
     * @return
     */
    public static String serialzeParam(Object param) {
        if (param == null) {
            return null;
        }
        if (param instanceof String) {
            return (String) param;
        }
        return JSON.toJSONString(param);
    }
}
