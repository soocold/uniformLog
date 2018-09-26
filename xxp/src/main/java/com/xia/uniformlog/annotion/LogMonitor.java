package com.xia.uniformlog.annotion;

import java.lang.annotation.*;

/**
 * 日志代理注解，需要代理输出统一格式的日志使用此注解即可
 *
 * @author xia
 * @since 2017/12/10 20:22
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogMonitor {
    /**
     * 日志归属的业务类型
     */
    String bizType() default "commonType";
    /**
     * 需要处理的参数，默认将方法入参全部序列化输出，可指定入参对象的部分字段，如$1.name,$1.sex,$2.xxx
     */
    String params() default "";
    /**
     * 是否使用代理，false表示注解不会被代理
     */
    boolean useLogProxy() default true;
}
