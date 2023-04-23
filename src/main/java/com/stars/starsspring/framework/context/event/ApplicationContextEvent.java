package com.stars.starsspring.framework.context.event;

import com.stars.starsspring.framework.context.ApplicationContext;
import com.stars.starsspring.framework.context.ApplicationEvent;

/**
 * 应用上下文事件——类
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ApplicationContextEvent
 * getApplicationContext
 *
 * @author stars
 */
public class ApplicationContextEvent extends ApplicationEvent {

    /**
     * 有参构造函数（源对象）
     *
     * @param source 事件的源对象
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取应用上下文
     * 获取引发事件的应用上下文对象。
     *
     * @return 事件的应用上下文对象
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) this.getSource();
    }
}
