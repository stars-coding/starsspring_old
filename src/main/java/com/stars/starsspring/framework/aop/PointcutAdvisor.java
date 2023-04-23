package com.stars.starsspring.framework.aop;

/**
 * 切点顾问——接口
 * 通常用于配置切面，其中切面包含切点和通知。
 * 使用PointcutAdvisor来创建代理对象，以便在满足切点条件的连接点上应用通知。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getPointcut
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * 获取切点
     * 获取与此切点通知器关联的切点对象。
     *
     * @return 与此切点通知器关联的切点对象
     */
    Pointcut getPointcut();
}
