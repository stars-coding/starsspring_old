package com.stars.starsspring.framework.util;

/**
 * 字符串值解析器——接口
 * 是Spring框架中用于解析字符串值的核心接口。
 * 主要职责是将包含占位符的字符串解析并替换为实际的字符串值。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * resolveStringValue
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface StringValueResolver {

    /**
     * 解析字符串值（字符串）
     * 解析包含占位符的字符串并替换为实际的字符串值。
     *
     * @param strVal 包含占位符的字符串
     * @return 替换占位符后的字符串
     */
    String resolveStringValue(String strVal);
}
