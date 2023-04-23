package com.stars.starsspring.framework.core.convert;

import org.jetbrains.annotations.Nullable;

/**
 * 转换服务——接口
 * 用于执行类型转换的中央接口。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * canConvert
 * convert
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ConversionService {

    /**
     * 是否可转换（源类型类对象，目标类型类对象）
     * 检查是否可以将源类型转换为目标类型。
     *
     * @param sourceType 源类型类对象
     * @param targetType 目标类型类对象
     * @return 如果可以执行类型转换，则返回true；否则返回false
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * 转换（源类型类对象，目标类型类对象）
     * 执行类型转换操作，将源对象从其当前类型转换为目标类型。
     *
     * @param source     源对象类对象
     * @param targetType 目标类型类对象
     * @return 转换后的目标对象
     */
    <T> T convert(Object source, Class<T> targetType);
}
