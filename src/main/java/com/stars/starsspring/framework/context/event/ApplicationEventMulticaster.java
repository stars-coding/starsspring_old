package com.stars.starsspring.framework.context.event;

import com.stars.starsspring.framework.context.ApplicationEvent;
import com.stars.starsspring.framework.context.ApplicationListener;

/**
 * 应用事件广播器——接口
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * addApplicationListener
 * removeApplicationListener
 * multicastEvent
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加应用监听器（应用监听器对象）
     * 添加一个监听器对象以接收所有事件对象。
     *
     * @param listener 要添加的监听器对象
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除应用监听器（应用监听器对象）
     * 从通知列表中移除一个监听器对象。
     *
     * @param listener 要移除的监听器对象
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件（应用事件对象）
     * 广播给定的应用事件对象给适当的监听器对象。
     *
     * @param event 要广播的事件对象
     */
    void multicastEvent(ApplicationEvent event);
}
