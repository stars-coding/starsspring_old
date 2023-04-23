package com.stars.starsspring.framework.aop;

/**
 * 类过滤器——接口
 * 用于测试给定的类是否匹配特定的条件。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * matches
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ClassFilter {

    /**
     * 匹配（类对象）
     * 测试给定的类对象是否匹配条件。
     *
     * @param clazz 要测试的类对象
     * @return 如果类对象匹配条件，返回true；否则返回false
     */
    boolean matches(Class<?> clazz);
}
