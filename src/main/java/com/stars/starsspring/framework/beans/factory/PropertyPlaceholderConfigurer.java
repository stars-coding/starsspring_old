package com.stars.starsspring.framework.beans.factory;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValue;
import com.stars.starsspring.framework.beans.PropertyValues;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.stars.starsspring.framework.core.io.DefaultResourceLoader;
import com.stars.starsspring.framework.core.io.Resource;
import com.stars.starsspring.framework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 属性占位符配置——类
 * 是Spring框架中的一个Bean工厂后处理器，用于处理属性占位符的替换。
 * 该后处理器负责将这些占位符替换为真实的属性值。
 * <p>
 * <p>
 * 属性字段：
 * DEFAULT_PLACEHOLDER_PREFIX
 * DEFAULT_PLACEHOLDER_SUFFIX
 * location
 * <p>
 * 重写方法：
 * postProcessBeanFactory
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * resolvePlaceholder
 * setLocation
 *
 * @author stars
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    // 默认站位符前缀
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    // 默认站位符后缀
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    // 属性文件的位置
    private String location;

    /**
     * 扩展处理Bean工厂（配置列表Bean工厂对象）
     * 在所有的Bean定义对象加载完成后，实例化Bean对象之前，提供修改Bean定义对象属性的机制。
     *
     * @param beanFactory 可配置的Bean工厂对象，包含了已加载的Bean定义对象
     * @throws BeansException 如果在处理过程中发生异常，则抛出BeansException异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // 加载属性文件
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(this.location);
            // 占位符替换属性值
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            // 获取所有Bean的定义对象
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                // 获取Bean对象的属性值
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }
                    value = this.resolvePlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                }
            }
            // 向容器中添加字符串解析器，供解析@Value注解使用
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    /**
     * 解析占位符（含占位符字符串，属性对象）
     * 解析属性占位符并替换为真实的属性值。
     *
     * @param value      包含属性占位符的字符串
     * @param properties 包含属性占位符和对应属性值的属性对象
     * @return 替换占位符后的字符串
     */
    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 占位符解析字符串值解析器——内部类
     * 用于解析占位符的内部字符串值解析器。
     * <p>
     * <p>
     * 属性字段：
     * properties
     * <p>
     * 重写方法：
     * resolveStringValue
     * <p>
     * 定义方法：
     * <p>
     * 编写方法：
     * PlaceholderResolvingStringValueResolver
     *
     * @author stars
     */
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        // 占位符对象
        private final Properties properties;

        /**
         * 有参构造函数（占位符对象）
         *
         * @param properties 占位符对象
         */
        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        /**
         * 解析字符串值（字符串）
         * 解析包含占位符的字符串并替换为实际的字符串值。
         *
         * @param strVal 包含占位符的字符串
         * @return 替换占位符后的字符串
         */
        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, this.properties);
        }
    }
}
