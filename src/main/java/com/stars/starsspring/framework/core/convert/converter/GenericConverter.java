package com.stars.starsspring.framework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 * 通用转换器——接口
 * 用于执行自定义的类型转换操作。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getConvertibleTypes
 * convert
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface GenericConverter {

    /**
     * 获取转换类型对
     * 获取可转换的类型对。每个可转换的类型对包含源类型类对象和目标类型类对象。
     *
     * @return 可转换的类型对对象
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 转换（源对象，源对象的类对象，目标类对象）
     * 执行类型转换操作，将源对象从源类型转换为目标类型。
     *
     * @param source     源对象
     * @param sourceType 源类型类对象
     * @param targetType 目标类型类对象
     * @return 转换后的目标对象
     */
    Object convert(Object source, Class sourceType, Class targetType);

    /**
     * 转换对——内部类
     * 表示可转换的类型对，包括源类型和目标类型。
     * <p>
     * <p>
     * 属性字段：
     * sourceType
     * targetType
     * <p>
     * 重写方法：
     * equals
     * hashCode
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * ConvertiblePair
     * getSourceType
     * getTargetType
     *
     * @author stars
     */
    final class ConvertiblePair {

        // 源类型类对象
        private final Class<?> sourceType;
        // 目标类型类对象
        private final Class<?> targetType;

        /**
         * 有参构造函数（源类型类对象，目标类型类对象）
         *
         * @param sourceType 源类型类对象
         * @param targetType 目标类型类对象
         */
        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
