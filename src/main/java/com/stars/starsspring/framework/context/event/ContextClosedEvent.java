package com.stars.starsspring.framework.context.event;

/**
 * 上下文关闭事件——类
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ContextClosedEvent
 *
 * @author stars
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    /**
     * 有参构造函数（源对象）
     *
     * @param source 事件的源对象
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
