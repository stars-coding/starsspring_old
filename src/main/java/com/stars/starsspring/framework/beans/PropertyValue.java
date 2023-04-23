package com.stars.starsspring.framework.beans;

/**
 * 属性值封装——类
 * 用于表示对象的属性名和对应的属性值。
 * <p>
 * <p>
 * 属性字段：
 * name
 * value
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * PropertyValue
 * getName
 * getValue
 *
 * @author stars
 */
public class PropertyValue {

    // 属性名
    private final String name;
    // 属性值
    private final Object value;

    /**
     * 有参构造函数（属性名，属性值）
     *
     * @param name  属性名
     * @param value 属性值
     */
    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
