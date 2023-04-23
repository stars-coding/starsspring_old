package com.stars.starsspring.framework.aop;

/**
 * 切点——接口
 * 定义了应该在哪些类和方法上应用通知。通常，切点对象用于定义通知应该在哪些连接点上运行。
 * 通常与通知和代理创建一起使用，以确定在哪些连接点上应用通知，并创建代理对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getClassFilter
 * getMethodMatcher
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface Pointcut {

    /**
     * 获取类过滤器
     * 获取与切点关联的类过滤器对象。
     *
     * @return 与切点关联的类过滤器对象
     */
    ClassFilter getClassFilter();

    /**
     * 获取方法匹配器
     * 获取与切点关联的方法匹配器对象。
     *
     * @return 与切点关联的方法匹配器对象
     */
    MethodMatcher getMethodMatcher();
}
