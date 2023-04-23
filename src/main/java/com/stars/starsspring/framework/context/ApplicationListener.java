package com.stars.starsspring.framework.context;

import java.util.EventListener;

/**
 * 应用监听器——接口
 * 应用程序事件监听器接口。任何希望监听应用程序事件的类都应该实现此接口，并提供适当的事件处理逻辑。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * onApplicationEvent
 * <p>
 * 编写方法：
 *
 * @param <E> 事件的类型
 * @author stars
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理应用事件（事件对象）
     * 处理应用程序事件的方法。当监听的事件发生时，将调用此方法来执行相应的处理逻辑。
     *
     * @param event 事件对象
     */
    void onApplicationEvent(E event);
}
