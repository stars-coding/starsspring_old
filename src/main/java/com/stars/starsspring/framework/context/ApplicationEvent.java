package com.stars.starsspring.framework.context;

import java.util.EventObject;

/**
 * 应用事件——抽象类
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ApplicationEvent
 * getSource
 *
 * @author stars
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * 有参构造函数（源对象）
     *
     * @param source 事件的源对象
     */
    public ApplicationEvent(Object source) {
        super(source);
    }

    /**
     * 获取源对象
     *
     * @return 事件的源对象
     */
    public Object getSource() {
        return source;
    }
}
