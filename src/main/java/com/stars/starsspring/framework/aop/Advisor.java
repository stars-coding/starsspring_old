package com.stars.starsspring.framework.aop;

import org.aopalliance.aop.Advice;

/**
 * 顾问——接口
 * 是一种用于将通知对象绑定到切点对象的结构。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getAdvice
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface Advisor {

    /**
     * 获取通知
     * 获取与此顾问关联的通知对象。
     *
     * @return 通知对象
     */
    Advice getAdvice();
}
