package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;

import java.util.Map;

/**
 * 列表Bean工厂——接口
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getBeansOfType
 * getBeanDefinitionNames
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取Bean依据类型（Bean的类对象）
     * 按照给定的类型返回所有符合条件的Bean对象。
     *
     * @param type Bean的类对象
     * @param <T>  Bean的类型
     * @return 包含所有符合条件的Bean对象的Map，其中键是Bean的名称，值是Bean对象
     * @throws BeansException 如果发生任何Bean相关的异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 获取所有Bean定义名称
     * 返回该工厂中所有Bean定义对象的名称。
     *
     * @return 包含所有Bean定义对象名称的数组
     */
    String[] getBeanDefinitionNames();
}
