package com.stars.starsspring.framework.beans.factory;

/**
 * Bean类加载器感知——接口
 * 用于让Bean对象感知其加载它的类加载器。
 * 当一个Bean对象实现了这个接口，容器在初始化该Bean对象时，会调用它的setBeanClassLoader方法，
 * 从而将加载它的类加载器传递给它，使得该Bean对象可以在运行时获取到它的类加载器。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * setBeanClassLoader
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * 设置Bean类加载器（类加载器）
     *
     * @param classLoader 加载该Bean对象的类加载器
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
