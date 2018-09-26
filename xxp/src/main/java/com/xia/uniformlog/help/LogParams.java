package com.xia.uniformlog.help;

import java.io.Serializable;

/**
 * 日志输出参数
 *
 * @author xia
 * @since 2017/12/10 20:41
 */
public class LogParams implements Serializable {
    private static final long serialVersionUID = 6675208352989285750L;
    /**
     * bean name
     */
    private String beanName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * cost方法调用耗时
     */
    private Long cost;
    /**
     * 输入参数
     */
    private Object[] inputParams;
    /**
     * 本机ip
     */
    private String localIp;
    /**
     * 业务类型，便于统计归类
     */
    private String bizType;
    /**
     * 方法调用是否成功
     */
    private boolean isSuccess;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Object[] getInputParams() {
        return inputParams;
    }

    public void setInputParams(Object[] inputParams) {
        this.inputParams = inputParams;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        isSuccess = isSuccess;
    }
}
