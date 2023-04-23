package com.stars.starsspring.framework.core.convert.converter;

/**
 * 转换器——接口
 * 通用类型转换接口，用于将源类型的对象转换为目标类型的对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * convert
 * <p>
 * 编写方法：
 *
 * @param <S> 源类型
 * @param <T> 目标类型
 * @author stars
 */
public interface Converter<S, T> {

    /**
     * 转换（源对象）
     * 将源类型的对象转换为目标类型的对象。
     *
     * @param source 源对象
     * @return 转换后的目标对象
     */
    T convert(S source);
}
