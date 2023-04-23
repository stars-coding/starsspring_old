package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.DisposableBean;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

/**
 * 销毁Bean适配器——类
 * <p>
 * <p>
 * 属性字段：
 * bean
 * beanName
 * destroyMethodName
 * <p>
 * 重写方法：
 * destroy
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * DisposableBeanAdapter
 *
 * @author stars
 */
public class DisposableBeanAdapter implements DisposableBean {

    // Bean对象
    private final Object bean;
    // Bean的名称
    private final String beanName;
    // 销毁方法名称
    private String destroyMethodName;

    /**
     * 有参构造函数（Bean对象，Bean的名称，Bean定义对象）
     *
     * @param bean           要销毁的Bean对象
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象，包含了配置信息
     */
    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * 销毁
     * 根据配置的销毁方法名称执行销毁操作。
     *
     * @throws Exception 如果在销毁过程中发生异常，则抛出Exception异常
     */
    @Override
    public void destroy() throws Exception {
        // 1、如果Bean对象实现了DisposableBean接口，调用其destroy方法
        if (this.bean instanceof DisposableBean) {
            ((DisposableBean) this.bean).destroy();
        }
        // 2、根据配置的销毁方法名称调用自定义的销毁方法
        if (StrUtil.isNotEmpty(this.destroyMethodName) && !(this.bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            // 获取配置的销毁方法
            Method destroyMethod = this.bean.getClass().getMethod(this.destroyMethodName);
            if (destroyMethod == null) {
                throw new BeansException("Couldn't find a destroy method named '" + this.destroyMethodName + "' on bean with name '" + this.beanName + "'");
            }
            // 调用销毁方法
            destroyMethod.invoke(this.bean);
        }
    }
}
