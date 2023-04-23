package com.stars.starsspring.framework.context.event;

import com.stars.starsspring.framework.beans.factory.BeanFactory;
import com.stars.starsspring.framework.context.ApplicationEvent;
import com.stars.starsspring.framework.context.ApplicationListener;

/**
 * 简单应用事件广播器——类
 * 用于将应用事件对象传播给相应的监听器对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * multicastEvent
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * SimpleApplicationEventMulticaster
 *
 * @author stars
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    /**
     * 有参构造函数（Bean工厂对象）
     *
     * @param beanFactory Bean工厂对象用于解析监听器对象
     */
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        this.setBeanFactory(beanFactory);
    }

    /**
     * 广播事件（应用事件对象）
     * 广播给定的应用事件对象给适当的监听器对象。
     *
     * @param event 要广播的事件对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
