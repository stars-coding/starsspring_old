package com.stars.starsspring.framework.util;

/**
 * 类工具——类
 * 是一个实用工具类，提供了有关类加载和检查CGLIB代理的方法。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * getDefaultClassLoader
 * isCglibProxyClass
 * isCglibProxyClassName
 * getActualClass
 *
 * @author stars
 */
public class ClassUtils {

    /**
     * 获取默认类加载器
     *
     * @return 默认的类加载器对象
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // 无法访问线程上下文类加载器，则退回到系统类加载器
        }
        if (cl == null) {
            // 没有线程上下文类加载器，则使用该类的类加载器
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 是否为CGLIB代理类（类对象）
     * 检查指定的类是否是由CGLIB生成的类。
     *
     * @param clazz 要检查的类对象
     * @return 如果是CGLIB生成的类，则返回true；否则返回false
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && ClassUtils.isCglibProxyClassName(clazz.getName()));
    }

    /**
     * 是否为CGLIB代理类名（类名）
     * 检查指定的类名是否是由CGLIB生成的类。
     *
     * @param className 要检查的类名
     * @return 如果是CGLIB生成的类，则返回true；否则返回false
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }

    /**
     * 获取组成类（类对象）
     * 获取实际的类，如果是CGLIB代理类，则返回其超类。
     *
     * @param clazz 要获取实际类的类对象
     * @return 实际的类，如果是CGLIB代理类，则返回其超类类对象
     */
    public static Class<?> getActualClass(Class<?> clazz) {
        return ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
    }
}
