package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValue;
import com.stars.starsspring.framework.beans.PropertyValues;
import com.stars.starsspring.framework.beans.factory.*;
import com.stars.starsspring.framework.beans.factory.config.*;
import com.stars.starsspring.framework.core.convert.ConversionService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 抽象自动胜任Bean工厂——抽象类
 * 提供一些关于Bean的核心功能。
 * <p>
 * <p>
 * 属性字段：
 * instantiationStrategy
 * <p>
 * 重写方法：
 * createBean
 * applyBeanPostProcessorsBeforeInitialization
 * applyBeanPostProcessorsAfterInitialization
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * doCreateBean
 * getEarlyBeanReference
 * applyBeanPostProcessorsAfterInstantiation
 * applyBeanPostProcessorsBeforeApplyingPropertyValues
 * resolveBeforeInstantiation
 * applyBeanPostProcessorsBeforeInstantiation
 * registerDisposableBeanIfNecessary
 * createBeanInstance
 * applyPropertyValues
 * initializeBean
 * invokeInitMethods
 * getInstantiationStrategy
 * setInstantiationStrategy
 *
 * @author stars
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    // 实例化策略对象
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建Bean（Bean的名称，Bean定义对象，构造参数）
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     * @param args           用于构造Bean对象的参数
     * @return 创建的Bean实例
     * @throws BeansException 如果创建过程中出现异常，则抛出BeansException异常
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        // 判断是否返回代理Bean对象
        Object bean = this.resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        // 执行创建Bean对象
        return this.doCreateBean(beanName, beanDefinition, args);
    }

    /**
     * 执行创建Bean（Bean的名称，Bean定义对象，构造参数）
     * 用于实例化Bean对象、处理循环依赖、填充属性、初始化Bean对象等操作。
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     * @param args           创建Bean对象时传入的参数
     * @return 创建的Bean对象
     * @throws BeansException 如果创建Bean过程中发生异常，抛出BeansException异常
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        try {
            // 1、实例化Bean对象
            bean = this.createBeanInstance(beanDefinition, beanName, args);
            // 2、处理循环依赖，将实例化后的Bean对象提前放入缓存中曝光
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                this.addSingletonFactory(beanName, () -> this.getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }
            // 3、实例化后判断
            boolean continueWithPropertyPopulation = this.applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            // 4、在设置Bean对象属性之前，允许Bean扩展处理器对象修改属性值
            this.applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 5、给Bean对象填充属性
            this.applyPropertyValues(beanName, bean, beanDefinition);
            // 6、执行Bean对象的初始化方法和Bean扩展处理器对象的前置和后置处理方法
            bean = this.initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 7、注册实现了DisposableBean接口的Bean对象
        this.registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        // 8、判断SCOPE_SINGLETON、SCOPE_PROTOTYPE，获取代理对象
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            exposedObject = this.getSingleton(beanName);
            this.registerSingleton(beanName, exposedObject);
        }
        return exposedObject;
    }

    /**
     * 获取早期Bean引用（Bean的名称，Bean定义对象，Bean对象）
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     * @param bean           Bean对象
     * @return 早期的Bean引用
     */
    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        // 初始时，早期引用就是bean对象
        Object exposedObject = bean;
        // 遍历所有的BeanPostProcessor对象
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            // 检查是否是InstantiationAwareBeanPostProcessor类型的后置处理器
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                // 调用后置处理器的getEarlyBeanReference方法，以获取早期引用
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .getEarlyBeanReference(exposedObject, beanName);
                // 如果返回的引用为null，说明后置处理器可能已经销毁了该Bean对象，直接结束循环
                if (exposedObject == null) {
                    break;
                }
            }
        }
        return exposedObject;
    }

    /**
     * Bean实例化之后应用扩展处理器（Bean的名称，Bean对象）
     * 在Bean对象实例化后应用所有的InstantiationAwareBeanPostProcessor后置处理器。
     * 通过调用postProcessAfterInstantiation方法来决定是否继续属性填充操作。
     *
     * @param beanName Bean的名称
     * @param bean     Bean对象
     * @return 如果返回true，表示继续属性填充操作，否则返回false
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        // 遍历所有的BeanPostProcessor对象
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            // 检查是否是InstantiationAwareBeanPostProcessor类型的后置处理器
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                // 调用postProcessAfterInstantiation方法，返回值表示是否继续属性填充操作
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    /**
     * 应用属性值集之前应用Bean扩展处理器（Bean的名称，Bean对象，Bean定义对象）
     * 在应用Bean对象属性值之前，通过遍历所有的InstantiationAwareBeanPostProcessor后置处理器，
     * 调用它们的postProcessPropertyValues方法，以修改或处理Bean对象的属性值。
     *
     * @param beanName       Bean的名称
     * @param bean           Bean对象
     * @param beanDefinition Bean定义对象
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            // 检查是否是InstantiationAwareBeanPostProcessor类型的后置处理器
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                // 调用postProcessPropertyValues方法，获取修改后的PropertyValues对象
                PropertyValues propertyValues = instantiationAwareBeanPostProcessor.postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (propertyValues != null) {
                    // 将修改后的PropertyValues对象添加到Bean定义对象中
                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 实例化之前表决（Bean的名称，Bean定义对象）
     *
     * @param beanName       Bean的名称
     * @param beanDefinition Bean定义对象
     * @return 处理后的Bean对象
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        // 应用BeanPostProcessor对象的postProcessBeforeInstantiation方法
        Object bean = this.applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            // 应用BeanPostProcessor对象的postProcessAfterInitialization方法
            bean = this.applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 实例化之前应用Bean扩展处理器（Bean的类对象，Bean的名称）
     * 在Bean对象实例化之前应用InstantiationAwareBeanPostProcessor的postProcessBeforeInstantiation方法。
     *
     * @param beanClass Bean的类对象
     * @param beanName  Bean的名称
     * @return 处理后的Bean对象，如果没有处理则返回null
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            // 检查BeanPostProcessor对象是否是InstantiationAwareBeanPostProcessor的实例
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                // 调用postProcessBeforeInstantiation方法，传递Bean对象类型和名称
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    // 如果处理器返回非空对象，则立即返回该对象
                    return result;
                }
            }
        }
        // 如果没有处理器对Bean对象进行处理，则返回null
        return null;
    }

    /**
     * 注册销毁Bean如果必要（Bean的名称，Bean对象，Bean定义对象）
     * 如果需要销毁的话，注册Bean对象的销毁操作。
     *
     * @param beanName       Bean的名称
     * @param bean           Bean对象
     * @param beanDefinition Bean定义对象，包含了配置信息
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非单例类型的Bean对象不执行销毁方法
        if (!beanDefinition.isSingleton()) {
            return;
        }
        // 如果Bean对象实现了DisposableBean接口，或者配置了自定义的销毁方法，则注册销毁操作
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            // 注册Bean对象的销毁适配器
            this.registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 创建Bean实例（Bean定义对象，Bean的名称，构造参数）
     *
     * @param beanDefinition 要创建的Bean定义对象，包含了Bean的类信息和构造函数等配置
     * @param beanName       Bean的名称
     * @param args           构造函数参数，用于指定创建Bean对象时所需的参数
     * @return 创建的Bean对象
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        // 创建一个变量来存储将要使用的构造函数
        Constructor constructorToUse = null;
        // 获取Bean的类对象
        Class<?> beanClass = beanDefinition.getBeanClass();
        // 获取Bean类中声明的所有构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        // 循环遍历所有的构造函数
        for (Constructor ctor : declaredConstructors) {
            // 检查是否传递了构造函数参数并且参数数量与当前构造函数匹配
            // todo 源码中还判断参数类型一致性，而这里仅做简单处理，即只判断参数数量一致性
            if (args != null && ctor.getParameterTypes().length == args.length) {
                // 如果匹配，则选择这个构造函数作为要使用的构造函数
                constructorToUse = ctor;
                break;
            }
        }
        // 使用实例化策略来创建Bean对象，并返回它
        return this.getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 应用属性值集（Bean的名字，Bean对象，Bean定义对象）
     * 将给定的属性值应用到指定的Bean实例中。
     *
     * @param beanName       Bean的名称
     * @param bean           要应用属性的Bean对象
     * @param beanDefinition Bean定义对象，包含了属性值的配置
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            // 获取BeanDefinition中定义的属性值
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    // 如果属性值是BeanReference类型，表示这个属性是一个引用其他Bean的依赖
                    BeanReference beanReference = (BeanReference) value;
                    // 获取被引用Bean的实例
                    value = getBean(beanReference.getBeanName());
                } else {
                    // 如果属性值不是BeanReference类型，需要进行类型转换，确保属性值的类型与目标属性的类型一致
                    Class<?> sourceType = value.getClass();
                    Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), name);
                    ConversionService conversionService = getConversionService();
                    if (conversionService != null) {
                        if (conversionService.canConvert(sourceType, targetType)) {
                            // 使用转换服务进行类型转换
                            value = conversionService.convert(value, targetType);
                        }
                    }
                }
                // 反射设置属性值
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    /**
     * 初始化Bean（Bean的名称，Bean对象，Bean定义对象）
     *
     * @param beanName       Bean的名称
     * @param bean           要初始化的Bean对象
     * @param beanDefinition Bean定义对象
     * @return 初始化后的Bean对象
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1、处理Aware接口回调，让Bean对象可以感知容器
        if (bean instanceof Aware) {
            // 如果Bean对象实现了BeanFactoryAware接口，将Bean工厂对象注入
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            // 如果Bean对象实现了BeanClassLoaderAware接口，将类加载器注入
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            // 如果Bean对象实现了BeanNameAware接口，将Bean的名称注入
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        // 2、执行BeanPostProcessor对象的前置处理
        Object wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        // 3、执行Bean对象的初始化方法
        try {
            this.invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }
        // 4、执行BeanPostProcessor对象的后置处理
        wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        // 返回初始化后的Bean对象
        return wrappedBean;
    }

    /**
     * 调用初始化方法（Bean的名称，Bean对象，Bean定义对象）
     *
     * @param beanName       Bean的名称
     * @param bean           要初始化的Bean对象
     * @param beanDefinition Bean定义对象
     * @throws Exception 如果在初始化方法调用过程中发生异常，则抛出Exception异常
     */
    @SuppressWarnings("unchecked")
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1、如果Bean对象实现了InitializingBean接口，调用其afterPropertiesSet方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        // 2、调用配置的init-method方法
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            // 获取配置的初始化方法
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            // 调用初始化方法
            initMethod.invoke(bean);
        }
    }

    /**
     * 前置处理器（已存在的Bean对象，Bean的名称）
     * 在Bean初始化之前调用BeanPostProcessors接口实现类的postProcessBeforeInitialization方法。
     *
     * @param existingBean 已存在的Bean对象
     * @param beanName     Bean的名称
     * @return 初始化前的Bean对象
     * @throws BeansException 如果在初始化前执行过程中发生异常，则抛出BeansException异常
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }

    /**
     * 后置处理器（已存在的Bean对象，Bean的名称）
     * 在Bean初始化之后调用BeanPostProcessors接口实现类的postProcessorsAfterInitialization方法。
     *
     * @param existingBean 已存在的Bean对象
     * @param beanName     Bean的名称
     * @return 初始化后的Bean对象
     * @throws BeansException 如果在初始化后执行过程中发生异常，则抛出BeansException异常
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
