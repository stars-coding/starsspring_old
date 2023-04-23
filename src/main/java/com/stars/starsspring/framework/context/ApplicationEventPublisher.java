package com.stars.starsspring.framework.context;

/**
 * 应用事件发布器——接口
 * 是整个一个事件的发布接口，所有的事件都需要从这个接口发布出去。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * publishEvent
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件（应用事件对象）
     * 通知所有已注册到此应用程序的监听器有一个应用程序事件。
     *
     * @param event 要发布的事件对象
     */
    void publishEvent(ApplicationEvent event);
}
