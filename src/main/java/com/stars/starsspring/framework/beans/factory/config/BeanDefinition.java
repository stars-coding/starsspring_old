package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.PropertyValues;

/**
 * Bean定义——类
 * 定义和描述Spring容器中的Bean对象。
 * 将Bean对象以类似零件的方式被拆解后，存放在Bean定义对象中，相当于把Bean对象解耦的操作。易于Spring对Bean对象细节上的把控。
 * <p>
 * <p>
 * 属性字段：
 * SCOPE_SINGLETON
 * SCOPE_PROTOTYPE
 * beanClass
 * propertyValues
 * initMethodName
 * destroyMethodName
 * SCOPE_SINGLETON
 * singleton
 * prototype
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * BeanDefinition
 * BeanDefinition
 * getBeanClass
 * setBeanClass
 * getPropertyValues
 * setPropertyValues
 * getInitMethodName
 * setInitMethodName
 * getDestroyMethodName
 * setDestroyMethodName
 * setScope
 * isSingleton
 * isPrototype
 *
 * @author stars
 */
public class BeanDefinition {

    // 单例范围
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    // 原型范围
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    // 类对象
    private Class beanClass;
    // 属性值集对象
    private PropertyValues propertyValues;
    // 初始化方法名字
    private String initMethodName;
    // 销毁方法名字
    private String destroyMethodName;
    // 范围
    private String scope = SCOPE_SINGLETON;
    // 单例标志
    private boolean singleton = true;
    // 原型标志
    private boolean prototype = false;

    /**
     * 有参构造函数（Bean的类对象）
     *
     * @param beanClass Bean的类对象
     */
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        // 属性值集默认初始化
        this.propertyValues = new PropertyValues();
    }

    /**
     * 有参构造函数（Bean的类对象，Bean的属性值集对象）
     *
     * @param beanClass      Bean的类对象
     * @param propertyValues Bean的属性值集对象
     */
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        // propertyValues为null，则创建一个propertyValues
        this.propertyValues = (propertyValues != null) ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }
}
