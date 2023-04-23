package com.stars.starsspring.framework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性值集——类
 * 用于管理对象的属性值列表，包括添加属性值、获取属性值等操作。
 * <p>
 * <p>
 * 属性字段：
 * propertyValueList
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * addPropertyValue
 * getPropertyValues
 * getPropertyValue
 *
 * @author stars
 */
public class PropertyValues {

    // 属性值列表
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 添加属性值（属性值对象）
     * 如果属性名已存在于列表中，则覆盖原有的属性值，相当于更新属性值。
     *
     * @param propertyValue 要添加的属性值
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPropertyValue = this.propertyValueList.get(i);
            if (currentPropertyValue.getName().equals(propertyValue.getName())) {
                // 覆盖原有的属性值，也就是更新属性值列表里面对应的元素
                this.propertyValueList.set(i, propertyValue);
                return;
            }
        }
        // 属性名没有重复，则是添加
        this.propertyValueList.add(propertyValue);
    }

    /**
     * 获取所有属性值
     *
     * @return 属性值数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 获取属性值（属性名）
     *
     * @param propertyName 属性名
     * @return 属性值，如果属性名不存在，则返回null
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }
}
