package com.stars.starsspring.framework.beans.factory.support;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.DisposableBean;
import com.stars.starsspring.framework.beans.factory.ObjectFactory;
import com.stars.starsspring.framework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认单例Bean注册——类
 * 这个类负责管理单例对象的缓存以及销毁已注册的DisposableBean对象。
 * <p>
 * <p>
 * 属性字段：
 * NULL_OBJECT
 * singletonObjects
 * earlySingletonObjects
 * singletonFactories
 * disposableBeans
 * <p>
 * 重写方法：
 * getSingleton
 * registerSingleton
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * addSingletonFactory
 * registerDisposableBean
 * destroySingletons
 *
 * @author stars
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 空对象，用于替代null值，单例对象的内部标记值，用作并发Map的标记值（不支持null值）。
    protected static final Object NULL_OBJECT = new Object();

    // 一级缓存，存放普通单例对象（单例对象）
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 二级缓存，提前曝光对象，存放未完全初始化的对象（早期单例对象）
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();
    // 三级缓存，存放代理对象（单例工厂对象）
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    // 销毁BeanMap，存放DisposableBean对象
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    /**
     * 获取单例（Bean的名称）
     *
     * @param beanName Bean的名称
     * @return 单例Bean对象，如果不存在则返回null
     */
    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存中获取
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 一级缓存中未获取到，则从二级缓存中获取
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 二级缓存中未获取到，则从三级缓存中获取
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    // 已从三级缓存中获取到
                    singletonObject = singletonFactory.getObject();
                    // 放入二级缓存
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    // 放入一级缓存
                    this.singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    /**
     * 注册单例（Bean的名称，单例对象）
     *
     * @param beanName        Bean的名称
     * @param singletonObject 单例Bean对象
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        // 放入一级缓存
        this.singletonObjects.put(beanName, singletonObject);
        // 移除二、三级缓存
        this.earlySingletonObjects.remove(beanName);
        this.singletonFactories.remove(beanName);
    }

    /**
     * 添加单例工厂（Bean的名称，单例工厂对象）
     *
     * @param beanName         Bean的名称
     * @param singletonFactory 单例工厂对象，用于创建单例对象
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        // 如果一级缓存中不存在该Bean对象，则将singletonFactory放入三级缓存中
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            // 移除二级缓存中的Bean对象，原因在于Bean对象还未完全初始化
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 注册销毁Bean（Bean的名称，销毁Bean对象）
     *
     * @param beanName Bean的名称
     * @param bean     可销毁的Bean对象
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        // 将可销毁的Bean对象存储到DisposableBeans中
        this.disposableBeans.put(beanName, bean);
    }

    /**
     * 销毁所有单例
     * 销毁所有已注册的可销毁的单例Bean对象。
     */
    public void destroySingletons() {
        // 获取所有已注册的可销毁Bean的名称
        Set<String> beanNames = this.disposableBeans.keySet();
        // 倒序遍历可销毁Bean的名称
        for (String beanName : beanNames) {
            DisposableBean disposableBean = this.disposableBeans.remove(beanName);
            try {
                // 销毁单例Bean对象
                disposableBean.destroy();
            } catch (Exception e) {
                // 如果销毁过程中发生异常，包装为BeansException并抛出
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
