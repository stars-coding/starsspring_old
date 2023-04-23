package com.stars.starsspring.framework.beans.factory.config;

import com.stars.starsspring.framework.beans.factory.HierarchicalBeanFactory;
import com.stars.starsspring.framework.core.convert.ConversionService;
import com.stars.starsspring.framework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * 配置Bean工厂——接口
 * 用于配置和管理Spring中Bean工厂对象的各种功能。
 * <p>
 * <p>
 * 属性字段：
 * SCOPE_SINGLETON
 * SCOPE_PROTOTYPE
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * addBeanPostProcessor
 * destroySingletons
 * addEmbeddedValueResolver
 * resolveEmbeddedValue
 * setConversionService
 * getConversionService
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    // 范围单例，定义了单例作用域的标识符，表示Bean对象是单例的，即在容器中只存在一个实例。
    String SCOPE_SINGLETON = "singleton";
    // 范围原型，定义了原型作用域的标识符，表示Bean对象是原型的，即每次请求都会创建一个新的实例。
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加Bean扩展处理器（Bean扩展处理器对象）
     * 向Bean工厂对象中添加一个Bean扩展处理器对象。
     *
     * @param beanPostProcessor 要添加的Bean扩展处理器对象
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁所有单例
     * 这个方法通常在容器关闭时调用，用于销毁所有单例Bean对象。
     */
    void destroySingletons();

    /**
     * 添加嵌入式值解析器（字符串值解析对象）
     * 添加一个字符串值解析器，用于解析Bean定义对象中的字符串嵌入式值，例如注解属性。
     *
     * @param valueResolver 要添加的值解析器
     * @since 3.0
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 解析嵌入式值（嵌入式值）
     * 解析给定的嵌入式值，例如注解属性。这个方法会将嵌入式值替换为实际的值。
     *
     * @param value 要解析的嵌入式值
     * @return 解析后的值（可能是原始值）
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);

    /**
     * 设置转换服务（转换服务对象）
     * 用于转换属性值。这是一个替代JavaBeans的PropertyEditors的选择。
     *
     * @param conversionService 要设置的转换服务
     * @since 3.0
     */
    void setConversionService(ConversionService conversionService);

    /**
     * 获取转换服务
     * 返回与该工厂关联的转换服务。
     *
     * @return 关联的转换服务对象，如果没有则返回null
     * @since 3.0
     */
    @Nullable
    ConversionService getConversionService();
}
