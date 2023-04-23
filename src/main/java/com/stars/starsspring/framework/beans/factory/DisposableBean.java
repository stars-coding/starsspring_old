package com.stars.starsspring.framework.beans.factory;

/**
 * 销毁Bean——接口
 * 销毁Bean的回调接口。
 * 开发者可以在此方法中进行自定义销毁操作。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * destroy
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface DisposableBean {

    /**
     * 销毁
     * 在Bean对象销毁时执行的销毁方法。
     *
     * @throws Exception 如果在销毁过程中发生异常，则抛出Exception异常
     */
    void destroy() throws Exception;
}
