package com.stars.starsspring.framework.context.annotation;

import com.stars.starsspring.framework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.stars.starsspring.framework.stereotype.Component;
import cn.hutool.core.util.StrUtil;

import java.util.Set;

/**
 * 类路径Bean定义扫描器——类
 * 用于扫描指定包中的类并注册其对应的Bean定义对象。
 * <p>
 * <p>
 * 属性字段：
 * registry
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ClassPathBeanDefinitionScanner
 * doScan
 * resolveBeanScope
 * determineBeanName
 *
 * @author stars
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    // Bean定义注册表对象
    private BeanDefinitionRegistry registry;

    /**
     * 有参构造函数（Bean定义注册表对象）
     *
     * @param registry Bean定义注册表对象
     */
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 执行扫描（基础包路径）
     * 执行扫描操作，扫描指定的基础包路径并注册Bean定义对象。
     *
     * @param basePackages 要扫描的基础包路径
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = this.findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析Bean对象的作用域singleton、prototype
                String beanScope = this.resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                this.registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        // 注册处理注解的BeanPostProcessor（@Autowired、@Value）
        this.registry.registerBeanDefinition(
                "com.stars.starsspring.framework.context.annotation.internalAutowiredAnnotationProcessor",
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 解析Bean范围（Bean定义对象）
     * 解析Bean对象的作用域。
     *
     * @param beanDefinition 要解析的Bean定义对象
     * @return Bean对象的作用域，如果没有指定则返回空字符串
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) return scope.value();
        return StrUtil.EMPTY;
    }

    /**
     * 确定Bean名称（Bean定义对象）
     * 确定Bean对象的名称。如果指定了@Component注解的value，则使用该值；否则使用类名的首字母小写作为名称。
     *
     * @param beanDefinition 要确定名称的Bean定义对象
     * @return Bean对象的名称
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
