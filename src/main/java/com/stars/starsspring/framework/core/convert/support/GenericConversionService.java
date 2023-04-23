package com.stars.starsspring.framework.core.convert.support;

import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.core.convert.converter.Converter;
import com.stars.starsspring.framework.core.convert.converter.ConverterFactory;
import com.stars.starsspring.framework.core.convert.converter.ConverterRegistry;
import com.stars.starsspring.framework.core.convert.converter.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用转换服务——类
 * <p>
 * <p>
 * 属性字段：
 * converters
 * <p>
 * 重写方法：
 * canConvert
 * convert
 * addConverter
 * addConverterFactory
 * addConverter
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * getRequiredTypeInfo
 * getConverter
 * getClassHierarchy
 *
 * @author stars
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    // 转换器Map，用于存储转换器的映射
    private Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();

    /**
     * 是否可转换（源类型类对象，目标类型类对象）
     * 检查是否可以将源类型转换为目标类型。
     *
     * @param sourceType 源类型类对象
     * @param targetType 目标类型类对象
     * @return 如果可以执行类型转换，则返回true；否则返回false
     */
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = this.getConverter(sourceType, targetType);
        return converter != null;
    }

    /**
     * 转换（源类型类对象，目标类型类对象）
     * 执行类型转换操作，将源对象从其当前类型转换为目标类型。
     *
     * @param source     源对象类对象
     * @param targetType 目标类型类对象
     * @return 转换后的目标对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = this.getConverter(sourceType, targetType);
        return (T) converter.convert(source, sourceType, targetType);
    }

    /**
     * 添加转换（转换器对象）
     * 添加类型转换器，将其注册到类型转换服务中。
     *
     * @param converter 要添加的类型转换器对象
     */
    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair typeInfo = this.getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
        for (GenericConverter.ConvertiblePair convertibleType : converterAdapter.getConvertibleTypes()) {
            this.converters.put(convertibleType, converterAdapter);
        }
    }

    /**
     * 添加转换工厂（转换工厂对象）
     * 添加类型转换器工厂，将其注册到类型转换服务中。
     *
     * @param converterFactory 要添加的类型转换器工厂对象
     */
    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInfo = this.getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInfo, converterFactory);
        for (GenericConverter.ConvertiblePair convertibleType : converterFactoryAdapter.getConvertibleTypes()) {
            this.converters.put(convertibleType, converterFactoryAdapter);
        }
    }

    /**
     * 添加转换（通用转换器对象）
     * 添加通用类型转换器，将其注册到类型转换服务中。
     *
     * @param converter 要添加的通用类型转换器对象
     */
    @Override
    public void addConverter(GenericConverter converter) {
        for (GenericConverter.ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
            this.converters.put(convertibleType, converter);
        }
    }

    /**
     * 获取依赖类型信息（对象）
     * 获取泛型转换器的可转换类型信息。
     *
     * @param object 泛型转换器对象
     * @return 可转换类型的泛型转换器
     */
    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class sourceType = (Class) actualTypeArguments[0];
        Class targetType = (Class) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }

    /**
     * 获取转换器（源类型类对象，目标类型类对象）
     * 获取适用于给定源类型和目标类型的泛型转换器。
     *
     * @param sourceType 源类型类对象
     * @param targetType 目标类型类对象
     * @return 适用的泛型转换器，如果没有匹配的则返回null
     */
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = this.getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = this.getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = this.converters.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    /**
     * 获取类对象分级（类对象）
     * 获取给定类的类层次结构，包括其本身和所有超类。
     *
     * @param clazz 要获取类层次结构的类对象
     * @return 包含类及其所有超类的类对象列表
     */
    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    /**
     * 转换适配器——内部类
     * 用于适配普通类型转换器的内部类。
     * <p>
     * <p>
     * 属性字段：
     * typeInfo
     * converter
     * <p>
     * 重写方法：
     * getConvertibleTypes
     * convert
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * ConverterAdapter
     *
     * @author stars
     */
    private final class ConverterAdapter implements GenericConverter {

        // 类型信息对象
        private final ConvertiblePair typeInfo;
        // 转换对象
        private final Converter<Object, Object> converter;

        /**
         * 有参构造函数（类型信息对象，转换对象）
         *
         * @param typeInfo  可转换类型的信息对象
         * @param converter 要适配的普通类型转换器对象
         */
        @SuppressWarnings("unchecked")
        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        /**
         * 获取转换类型对
         * 获取可转换的类型对。每个可转换的类型对包含源类型类对象和目标类型类对象。
         *
         * @return 可转换的类型对对象
         */
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            // 返回一个包含当前适配器的可转换类型信息的集合
            return Collections.singleton(this.typeInfo);
        }

        /**
         * 转换（源对象，源对象的类对象，目标类对象）
         * 执行类型转换操作，将源对象从源类型转换为目标类型。
         *
         * @param source     源对象
         * @param sourceType 源类型类对象
         * @param targetType 目标类型类对象
         * @return 转换后的目标对象
         */
        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return this.converter.convert(source);
        }
    }

    /**
     * 转换工厂适配器——内部类
     * 用于适配类型转换工厂的内部类。
     * <p>
     * <p>
     * 属性字段：
     * typeInfo
     * converterFactory
     * <p>
     * 重写方法：
     * getConvertibleTypes
     * convert
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * ConverterFactoryAdapter
     *
     * @author stars
     */
    private final class ConverterFactoryAdapter implements GenericConverter {

        // 类型信息对象
        private final ConvertiblePair typeInfo;
        // 转换工厂对象
        private final ConverterFactory<Object, Object> converterFactory;

        /**
         * 有参构造函数（类型信息对象，转换工厂对象）
         *
         * @param typeInfo         可转换类型的信息对象
         * @param converterFactory 要适配的类型转换工厂对象
         */
        @SuppressWarnings("unchecked")
        public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        /**
         * 获取转换类型对
         * 获取可转换的类型对。每个可转换的类型对包含源类型类对象和目标类型类对象。
         *
         * @return 可转换的类型对对象
         */
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(this.typeInfo);
        }

        /**
         * 转换（源对象，源对象的类对象，目标类对象）
         * 执行类型转换操作，将源对象从源类型转换为目标类型。
         *
         * @param source     源对象
         * @param sourceType 源类型类对象
         * @param targetType 目标类型类对象
         * @return 转换后的目标对象
         */
        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Object source, Class sourceType, Class targetType) {
            // 使用类型转换工厂获取适合目标类型的普通类型转换器，然后执行类型转换
            return this.converterFactory.getConverter(targetType).convert(source);
        }
    }
}
