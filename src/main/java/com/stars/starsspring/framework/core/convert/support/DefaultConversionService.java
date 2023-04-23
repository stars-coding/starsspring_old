package com.stars.starsspring.framework.core.convert.support;

import com.stars.starsspring.framework.core.convert.converter.ConverterRegistry;

/**
 * 默认转换服务——类
 * Spring框架中默认的类型转换服务实现。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * DefaultConversionService
 * addDefaultConverters
 *
 * @author stars
 */
public class DefaultConversionService extends GenericConversionService {

    /**
     * 无参构造函数
     * 添加默认的类型转换器。
     */
    public DefaultConversionService() {
        DefaultConversionService.addDefaultConverters(this);
    }

    /**
     * 有参构造函数（转换注册对象）
     * 添加默认的类型转换器到给定的转换注册对象中。
     *
     * @param converterRegistry 用于注册类型转换器的转换注册对象
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂对象
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
