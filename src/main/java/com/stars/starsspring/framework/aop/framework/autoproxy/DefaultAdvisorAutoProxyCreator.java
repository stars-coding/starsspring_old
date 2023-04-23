package com.stars.starsspring.framework.aop.framework.autoproxy;

import com.stars.starsspring.framework.aop.*;
import com.stars.starsspring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.stars.starsspring.framework.aop.framework.ProxyFactory;
import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValues;
import com.stars.starsspring.framework.beans.factory.BeanFactory;
import com.stars.starsspring.framework.beans.factory.BeanFactoryAware;
import com.stars.starsspring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.stars.starsspring.framework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 默认顾问自动代理创造器——类
 * <p>
 * <p>
 * 属性字段：
 * earlyProxyReferences
 * beanFactory
 * <p>
 * 重写方法：
 * setBeanFactory
 * postProcessBeforeInstantiation
 * postProcessAfterInstantiation
 * postProcessBeforeInitialization
 * postProcessAfterInitialization
 * postProcessPropertyValues
 * getEarlyBeanReference
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * isInfrastructureClass
 * wrapIfNecessary
 *
 * @author stars
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    // 早期代理引用Map对象，用于存储提前引用的代理对象
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    // 默认列表Bean工厂对象
    private DefaultListableBeanFactory beanFactory;

    /**
     * 设置Bean工厂（Bean工厂对象）
     *
     * @param beanFactory Bean工厂对象，用于访问容器中的其他Bean对象
     * @throws BeansException 如果设置过程中出现异常，则抛出BeansException异常
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 实例化之前扩展处理（Bean的类对象，Bean的名称）
     * 在Bean对象执行实例化方法之前执行，可以返回一个代理对象来替代原始的Bean对象。
     *
     * @param beanClass 目标Bean的类对象
     * @param beanName  目标Bean的名称
     * @return 返回的对象可以是原始Bean对象或代理对象
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 实例化之后扩展处理（Bean对象，Bean的名称）
     * 在Bean对象执行实例化方法之后，属性注入之前执行。
     *
     * @param bean     目标Bean对象
     * @param beanName 目标Bean的名称
     * @return 返回true表示继续属性注入，返回false表示取消属性注入
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 对否为基础设施类（类对象）
     * 判断给定的类是否属于基础设施类，通常用于判断是否为AOP相关的类。
     *
     * @param beanClass Bean的类对象
     * @return 如果是基础设施类，返回true，否则返回false
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    /**
     * 初始化之前扩展处理（Bean对象，Bean的名称）
     * 前置处理器
     * 在Bean对象执行初始化方法之前，执行此方法。
     *
     * @param bean     当前Bean对象
     * @param beanName 当前Bean的名称
     * @return 修改后的Bean对象，可以是原始对象或修改后的代理对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化之后扩展处理（Bean对象，Bean的名称）
     * 后置处理器
     * 在Bean对象执行初始化方法之后，执行此方法。
     *
     * @param bean     当前Bean对象
     * @param beanName 当前Bean的名称
     * @return 修改后的Bean对象，可以是原始对象或修改后的代理对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!this.earlyProxyReferences.contains(beanName)) {
            return this.wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    /**
     * 根据切面配置将需要代理的 Bean 进行代理。
     *
     * @param bean     Bean对象
     * @param beanName Bean的名称
     * @return 如果需要代理，返回代理对象；否则返回原始的Bean对象
     */
    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (this.isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors =
                this.beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);
            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    /**
     * 扩展处理属性值集（属性值集对象，Bean对象，Bean的名称）
     * 在属性注入之前，对属性值进行后处理，例如根据一些条件检查属性的值是否合法。
     *
     * @param propertyValues 当前Bean的属性值集对象
     * @param bean           目标Bean对象
     * @param beanName       目标Bean的名称
     * @return 返回处理后的属性值集对象
     * @throws BeansException 如果处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName)
            throws BeansException {
        return propertyValues;
    }

    /**
     * 获取早期Bean引用（Bean对象，Bean的名称）
     * 获取早期引用的Bean对象。
     * 在Spring中，由SmartInstantiationAwareBeanPostProcessor对象的getEarlyBeanReference方法提供支持。
     * 通常情况下，直接返回原始的Bean对象。
     *
     * @param bean     目标Bean对象
     * @param beanName 目标Bean的名称
     * @return 返回的对象通常是原始Bean对象
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        this.earlyProxyReferences.add(beanName);
        return this.wrapIfNecessary(bean, beanName);
    }
}
