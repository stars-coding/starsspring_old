package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;

/**
 * 对象工厂——接口
 * 用于获取特定类型（T）的对象的工厂接口。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getObject
 * <p>
 * 编写方法：
 *
 * @param <T> 要获取的对象类型
 * @author stars
 */
public interface ObjectFactory<T> {

    /**
     * 获取对象
     *
     * @return 返回特定类型（T）的对象
     * @throws BeansException 如果获取对象时出现异常，则抛出BeansException异常
     */
    T getObject() throws BeansException;
}
