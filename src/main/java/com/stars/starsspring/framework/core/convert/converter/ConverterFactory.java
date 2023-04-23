package com.stars.starsspring.framework.core.convert.converter;

/**
 * 转换器工厂——接口
 * 类型转换器工厂接口，用于获取将源类型转换为目标类型的类型转换器。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getConverter
 * <p>
 * 编写方法：
 *
 * @param <S> 源类型
 * @param <R> 目标类型
 * @author stars
 */
public interface ConverterFactory<S, R> {

    /**
     * 获取转换器（类对象）
     * 获取将源类型转换为目标类型的类型转换器。
     *
     * @param targetType 目标类型
     * @param <T>        目标类型的泛型
     * @return 类型转换器
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
