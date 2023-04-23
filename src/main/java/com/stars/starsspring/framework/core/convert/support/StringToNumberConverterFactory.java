package com.stars.starsspring.framework.core.convert.support;

import com.stars.starsspring.framework.core.convert.converter.Converter;
import com.stars.starsspring.framework.core.convert.converter.ConverterFactory;
import com.stars.starsspring.framework.util.NumberUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 字符串转数字转换工厂——类
 * 用于将字符串转换为数字类型的转换器工厂。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * Converter
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    /**
     * 获取转换器（类对象）
     * 获取将源类型转换为目标类型的类型转换器。
     *
     * @param targetType 目标类型类对象
     * @param <T>        目标类型的泛型
     * @return 类型转换器
     */
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    /**
     * 字符串转数字——内部类
     * 实现了将字符串转换为目标数字类型的Converter接口。
     * <p>
     * <p>
     * 属性字段：
     * targetType
     * <p>
     * 重写方法：
     * convert
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * StringToNumber
     *
     * @param <T> 目标数字类型
     * @author stars
     */
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {

        // 目标类型类对象
        private final Class<T> targetType;

        /**
         * 有参构造函数（目标类型类对象）
         *
         * @param targetType 目标类型类对象
         */
        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        /**
         * 转换（源对象）
         * 将源类型的对象转换为目标类型的对象。
         *
         * @param source 源对象
         * @return 转换后的目标对象
         */
        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }
}
