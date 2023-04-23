package com.stars.starsspring.framework.context.event;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.factory.BeanFactory;
import com.stars.starsspring.framework.beans.factory.BeanFactoryAware;
import com.stars.starsspring.framework.context.ApplicationEvent;
import com.stars.starsspring.framework.context.ApplicationListener;
import com.stars.starsspring.framework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 抽象应用事件广播器——抽象类
 * <p>
 * <p>
 * 属性字段：
 * applicationListeners
 * beanFactory
 * <p>
 * 重写方法：
 * addApplicationListener
 * removeApplicationListener
 * setBeanFactory
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * getApplicationListeners
 * supportsEvent
 *
 * @author stars
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    // 应用监听器Map对象
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    // Bean工厂对象
    private BeanFactory beanFactory;

    /**
     * 添加应用监听器（应用监听器对象）
     * 添加一个监听器对象以接收所有事件。
     *
     * @param listener 要添加的监听器对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    /**
     * 移除应用监听器（应用监听器对象）
     * 从通知列表中移除一个监听器对象。
     *
     * @param listener 要移除的监听器对象
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.remove(listener);
    }

    /**
     * 设置Bean工厂（Bean工厂对象）
     *
     * @param beanFactory Bean工厂对象，用于访问容器中的其他Bean对象
     * @throws BeansException 如果设置过程中出现异常，则抛出BeansException异常
     */
    @Override
    public final void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取所有应用监听器（应用事件对象）
     * 获取适用于给定事件的所有应用程序监听器对象。
     *
     * @param event 要处理的应用事件对象
     * @return 包含适用于事件的所有监听器对象的集合
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<ApplicationListener>();
        for (ApplicationListener<ApplicationEvent> listener : this.applicationListeners) {
            // 如果监听器支持处理该事件，将其添加到监听器列表中
            if (this.supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 支持事件（应用监听器对象，上下文事件对象）
     * 检查给定的应用程序监听器对象是否支持处理指定的应用程序事件对象。
     *
     * @param applicationListener 要检查的应用程序监听器对象
     * @param event               要处理的应用程序事件对象
     * @return 如果监听器对象支持处理事件对象，则返回true，否则返回false
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // 获取监听器的类对象
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
        // 根据不同的实例化策略，获取目标类
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        // 获取监听器监听的事件类型
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            // 通过类名获取事件的类对象
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 判定此eventClassName对象所表示的类或接口与指定的event.getClass()参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。
        // 如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
