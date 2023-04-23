package com.stars.starsspring.framework.context.support;

import com.stars.starsspring.framework.beans.factory.FactoryBean;
import com.stars.starsspring.framework.beans.factory.InitializingBean;
import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.core.convert.converter.Converter;
import com.stars.starsspring.framework.core.convert.converter.ConverterFactory;
import com.stars.starsspring.framework.core.convert.converter.ConverterRegistry;
import com.stars.starsspring.framework.core.convert.converter.GenericConverter;
import com.stars.starsspring.framework.core.convert.support.DefaultConversionService;
import com.stars.starsspring.framework.core.convert.support.GenericConversionService;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * 转换服务工厂Bean——类
 * <p>
 * <p>
 * 属性字段：
 * converters
 * conversionService
 * <p>
 * 重写方法：
 * getObject
 * getObjectType
 * isSingleton
 * afterPropertiesSet
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * registerConverters
 * setConverters
 *
 * @author stars
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    // 转换Map对象
    @Nullable
    private Set<?> converters;
    // 通用转换服务对象
    @Nullable
    private GenericConversionService conversionService;

    /**
     * 获取对象
     * 获取工厂Bean对象创建的对象。
     *
     * @return 创建的对象
     * @throws Exception 如果创建对象过程中发生异常，则抛出Exception异常
     */
    @Override
    public ConversionService getObject() throws Exception {
        return this.conversionService;
    }

    /**
     * 获取对象类型
     * 获取创建的对象的类型。
     *
     * @return 对象的类对象
     */
    @Override
    public Class<?> getObjectType() {
        return this.conversionService.getClass();
    }

    /**
     * 是否单例
     * 判断创建的对象是否是单例。
     *
     * @return 如果是单例对象，返回true；否则返回false
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 属性填充后
     * 在属性填充后执行的初始化方法。
     *
     * @throws Exception 如果在初始化过程中发生异常，则抛出Exception异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.conversionService = new DefaultConversionService();
        registerConverters(this.converters, this.conversionService);
    }

    /**
     * 注册所有转换器（转换器Map对象，转换器注册对象）
     * 注册给定的一组转换器对象到转换器注册表对象中。
     *
     * @param converters 要注册的转换器Map对象
     * @param registry   转换器注册表对象
     * @throws IllegalArgumentException 如果提供的转换器对象没有实现Converter、ConverterFactory或GenericConverter接口，则抛出IllegalArgumentException异常
     */
    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("每个转换器对象必须实现 Converter、ConverterFactory 或 GenericConverter 接口");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
