package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * CGLIB子类化实例化策略——类
 * CGLIB实例化，使用CGLIB动态创建Bean的子类实例。
 * 源码中还有CallbackFilter等实现。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * instantiate
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    /**
     * 实例化（Bean定义对象，Bean的名称，构造函数对象，构造函数参数数）
     *
     * @param beanDefinition Bean定义对象
     * @param beanName       Bean的名称
     * @param ctor           构造函数对象，目的是为了拿到符合入参信息相对应的构造函数
     * @param args           构造函数参数
     * @return 实例化的Bean对象
     * @throws BeansException 如果实例化过程中出现异常，则抛出BeansException异常
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        // 创建一个CGLIB的Enhancer对象
        Enhancer enhancer = new Enhancer();
        // 设置父类为Bean的类
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        // 设置回调为NoOp，以禁用任何方法拦截
        enhancer.setCallback(NoOp.INSTANCE);
        // 如果构造函数为空，直接创建Bean的子类实例
        if (ctor == null) {
            return enhancer.create();
        }
        // 否则使用指定的构造函数和参数创建Bean对象的子类实例
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
