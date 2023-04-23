package com.stars.starsspring.framework.beans;

/**
 * Bean异常——异常类
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * BeansException
 * BeansException
 *
 * @author stars
 */
public class BeansException extends RuntimeException {

    /**
     * 有参构造函数（异常消息）
     *
     * @param msg 异常的详细消息
     */
    public BeansException(String msg) {
        super(msg);
    }

    /**
     * 有参构造函数（异常消息，异常原因）
     *
     * @param msg   异常的详细消息
     * @param cause 异常的原因
     */
    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
