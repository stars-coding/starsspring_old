package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认列表Bean工厂——类
 * <p>
 * <p>
 * 属性字段：
 * beanDefinitionMap
 * <p>
 * 重写方法：
 * registerBeanDefinition
 * containsBeanDefinition
 * getBeansOfType
 * getBeanDefinitionNames
 * getBeanDefinition
 * preInstantiateSingletons
 * getBean
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    // Bean定义Map对象，容器的Bean定义注册表对象
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 注册Bean定义（Bean的名称，Bean定义对象）
     * 向容器的注册表中注册一个BeanDefinition对象。
     *
     * @param beanName       要注册的Bean的名称
     * @param beanDefinition Bean定义，包含了Bean的配置信息
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        // 将Bean定义对象放入容器中
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 是否包含Bean定义（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return 如果包含对应名称的Bean定义对象，则返回true，否则返回false
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 获取Bean依据类型（Bean的类对象）
     * 按照给定的类型返回所有符合条件的Bean对象。
     *
     * @param type Bean的类对象
     * @param <T>  Bean的类型
     * @return 包含所有符合条件的Bean对象的Map，其中键是Bean的名称，值是Bean对象
     * @throws BeansException 如果发生任何Bean相关的异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    /**
     * 获取所有Bean定义名称
     * 返回该工厂中所有Bean定义对象的名称。
     *
     * @return 包含所有Bean定义对象名称的数组
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }

    /**
     * 获取Bean定义（Bean的名称）
     * 根据名称从容器的注册表中获取相应的BeanDefinition对象。
     *
     * @param beanName 要获取BeanDefinition的名称
     * @return BeanDefinition对象，包含了Bean的配置信息
     * @throws BeansException 如果找不到指定名称的BeanDefinition对象，则抛出BeansException异常
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        // 从容器中取出指定名称的Bean定义对象
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    /**
     * 预实例化所有单例
     * 预实例化所有的单例Bean对象。
     * 这将初始化配置中定义的所有单例Bean对象，使它们准备好在需要时使用。
     *
     * @throws BeansException 如果在预实例化过程中发生任何异常，将抛出BeansException异常
     */
    @Override
    public void preInstantiateSingletons() throws BeansException {
        this.beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /**
     * 获取Bean（Bean的类对象）
     *
     * @param requiredType 要求的Bean类型
     * @return 匹配类型的Bean对象
     * @throws BeansException 如果无法找到或创建Bean对象，则抛出BeansException异常
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), requiredType);
        }
        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}
