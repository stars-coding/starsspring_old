package com.stars.starsspring.framework.aop;

import com.stars.starsspring.framework.util.ClassUtils;

/**
 * 目标源——类
 * 用于封装目标对象和目标类的信息。
 * 通过这个类，可以获取目标对象的类以及目标对象本身。
 * <p>
 * <p>
 * 属性字段：
 * target
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * TargetSource
 * getTargetClass
 * getTarget
 *
 * @author stars
 */
public class TargetSource {

    // 目标对象
    private final Object target;

    /**
     * 有参构造函数（目标对象）
     *
     * @param target 目标对象
     */
    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取目标类
     * 获取目标对象的接口。
     * 如果目标对象是CGLIB代理类，返回其父类的接口。否则返回目标对象的直接接口。
     *
     * @return 目标对象的接口数组
     */
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        // 如果是CGLIB代理类，获取其父类
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        // 返回类的接口
        return clazz.getInterfaces();
    }

    /**
     * 获取目标
     * 获取目标对象。
     *
     * @return 目标对象
     */
    public Object getTarget() {
        return this.target;
    }
}
