package com.stars.starsspring.framework.core.convert.converter;

/**
 * 转换注册——接口
 * 类型转换器注册接口，用于注册自定义的类型转换器和类型转换器工厂。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * addConverter
 * addConverter
 * addConverterFactory
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ConverterRegistry {

    /**
     * 添加转换（转换器对象）
     * 添加类型转换器，将其注册到类型转换服务中。
     *
     * @param converter 要添加的类型转换器对象
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加转换（通用转换器对象）
     * 添加通用类型转换器，将其注册到类型转换服务中。
     *
     * @param converter 要添加的通用类型转换器对象
     */
    void addConverter(GenericConverter converter);

    /**
     * 添加转换工厂（转换工厂对象）
     * 添加类型转换器工厂，将其注册到类型转换服务中。
     *
     * @param converterFactory 要添加的类型转换器工厂对象
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
