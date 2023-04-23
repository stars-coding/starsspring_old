package com.stars.starsspring.framework.context.annotation;

import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.stereotype.Component;
import cn.hutool.core.util.ClassUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类路径扫描候选Component提供器——类
 * 用于扫描指定基础包中带有@Component注解的类，并将它们封装为候选的Bean定义对象。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * findCandidateComponents
 *
 * @author stars
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 查找所有候选Component
     * 查找候选的组件类并封装为Bean定义对象。
     *
     * @param basePackage 扫描的基础包名
     * @return 候选的Bean定义对象集合
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        // 用于存储候选Bean定义对象的集合
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 通过ClassUtil工具类扫描指定包下带有@Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        // 遍历扫描到的类，将每个类封装为Bean定义对象并添加到候选集合中
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        // 返回候选的Bean定义对象集合
        return candidates;
    }
}
