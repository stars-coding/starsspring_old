package com.stars.starsspring.framework.beans.factory.config;

/**
 * Bean引用——类
 * 表示对另一个Bean对象的引用，通常在属性注入时使用。
 * <p>
 * <p>
 * 属性字段：
 * beanName
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * BeanReference
 * getBeanName
 *
 * @author stars
 */
public class BeanReference {

    // Bean的名称
    private final String beanName;

    /**
     * 有参构造函数（Bean的名称）
     *
     * @param beanName Bean的名称
     */
    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
