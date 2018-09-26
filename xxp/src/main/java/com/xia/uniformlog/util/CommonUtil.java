package com.xia.uniformlog.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 通用工具类
 *
 * @author xia
 * @since 2017/12/10 20:50
 */
public class CommonUtil {
    /**
     * 获取本机ip
     */
    public static String getLocalIp() {
        String localIp;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localIp = "Unknown";
        }
        return localIp;
    }
}
