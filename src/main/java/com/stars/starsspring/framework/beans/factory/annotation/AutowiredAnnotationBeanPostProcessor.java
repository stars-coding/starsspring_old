package com.stars.starsspring.framework.beans.factory.annotation;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValues;
import com.stars.starsspring.framework.beans.factory.BeanFactory;
import com.stars.starsspring.framework.beans.factory.BeanFactoryAware;
import com.stars.starsspring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.stars.starsspring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.util.ClassUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Field;

/**
 * 自动装配注解Bean扩展处理器——类
 * <p>
 * <p>
 * 属性字段：
 * beanFactory
 * <p>
 * 重写方法：
 * postProcessBeforeInstantiation
 * postProcessAfterInstantiation
 * postProcessPropertyValues
 * postProcessBeforeInitialization
 * postProcessAfterInitialization
 * setBeanFactory
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    // 配置列表Bean工厂对象
    private ConfigurableListableBeanFactory beanFactory;

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
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        // 1、处理@Value注解
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                Object value = valueAnnotation.value();
                value = this.beanFactory.resolveEmbeddedValue((String) value);
                // 类型转换
                Class<?> sourceType = value.getClass();
                Class<?> targetType = (Class<?>) TypeUtil.getType(field);
                ConversionService conversionService = this.beanFactory.getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }
        // 2、处理@Autowired注解
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (autowiredAnnotation != null) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (qualifierAnnotation != null) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = this.beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = this.beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return propertyValues;
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
        return null;
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
        return null;
    }

    /**
     * 设置Bean工厂（Bean工厂对象）
     *
     * @param beanFactory Bean工厂对象，用于访问容器中的其他Bean对象
     * @throws BeansException 如果设置过程中出现异常，则抛出BeansException异常
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
