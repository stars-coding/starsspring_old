package com.stars.starsspring.framework.beans.factory.xml;

import com.stars.starsspring.framework.beans.BeansException;
import com.stars.starsspring.framework.beans.PropertyValue;
import com.stars.starsspring.framework.beans.factory.config.BeanDefinition;
import com.stars.starsspring.framework.beans.factory.config.BeanReference;
import com.stars.starsspring.framework.beans.factory.support.AbstractBeanDefinitionReader;
import com.stars.starsspring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.stars.starsspring.framework.context.annotation.ClassPathBeanDefinitionScanner;
import com.stars.starsspring.framework.core.io.Resource;
import com.stars.starsspring.framework.core.io.ResourceLoader;
import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * XmlBean定义读取器——类
 * 用于从XML文件中读取并加载Bean定义对象的读取器。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * loadBeanDefinitions
 * loadBeanDefinitions
 * loadBeanDefinitions
 * loadBeanDefinitions
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * XmlBeanDefinitionReader
 * XmlBeanDefinitionReader
 * doLoadBeanDefinitions
 * scanPackage
 *
 * @author stars
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /**
     * 有参构造函数（Bean定义注册表对象）
     *
     * @param registry Bean定义注册表对象，用于注册Bean定义对象
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 有参构造函数（Bean定义注册表对象，资源加载器对象）
     *
     * @param registry       Bean定义注册表对象，用于注册Bean定义对象
     * @param resourceLoader 资源加载器对象，用于加载Bean对象配置资源
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 加载Bean定义（资源对象）
     * 从单个资源中加载Bean定义对象。
     * 加载Bean定义对象的最终方法。
     *
     * @param resource Bean对象配置资源
     * @throws BeansException 如果加载过程中出现异常，则抛出BeansException异常
     */
    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                this.doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    /**
     * 加载Bean定义（资源数组）
     * 从多个资源中加载Bean定义对象。
     *
     * @param resources Bean对象配置资源数组
     * @throws BeansException 如果加载过程中出现异常，则抛出BeansException异常
     */
    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        // 循环调用从单个资源中加载Bean定义对象的方法
        for (Resource resource : resources) {
            this.loadBeanDefinitions(resource);
        }
    }

    /**
     * 加载Bean定义（资源位置）
     * 从单个指定位置的资源中加载Bean定义对象。
     *
     * @param location 资源位置
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        this.loadBeanDefinitions(resource);
    }

    /**
     * 加载Bean定义（资源位置数组）
     * 从多个指定位置的资源中加载Bean定义对象。
     *
     * @param locations 资源位置数组
     * @throws BeansException 如果加载过程中发生错误，则抛出BeansException异常
     */
    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        // 循环调用从单个指定位置加载Bean定义对象的方法
        for (String location : locations) {
            this.loadBeanDefinitions(location);
        }
    }

    /**
     * 真正加载Bean定义（输入流对象）
     * 真正的加载Bean定义对象的方法，从输入流中读取XML配置并解析为Bean定义对象。
     *
     * @param inputStream 包含Bean配置的输入流对象
     * @throws ClassNotFoundException 如果找不到对应的类，则抛出ClassNotFoundException异常
     * @throws DocumentException      如果XML解析过程中出现异常，则抛出DocumentException异常
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        // 创建SAXReader对象，用于XML解析
        SAXReader reader = new SAXReader();
        // 解析XML文档
        Document document = reader.read(inputStream);
        // 获取XML文档的根元素
        Element root = document.getRootElement();
        // 解析context:component-scan标签，扫描指定包中的类并提取相关信息，用于创建Bean定义对象
        Element componentScan = root.element("component-scan");
        if (componentScan != null) {
            // 获取属性扫描路径
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            // 根据扫描路径执行扫描
            this.scanPackage(scanPath);
        }
        // 获取所有bean元素放入List集合中
        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {
            // 解析bean元素中的属性
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");
            // 获取Bean对象对应的Class对象，用于进一步创建Bean定义对象
            Class<?> clazz = Class.forName(className);
            // 选择Bean的名称，优先级：id > name > 简单类名首字母小写
            String beanName = StrUtil.isNotEmpty(id) ? id : (StrUtil.isNotEmpty(name) ? name : StrUtil.lowerFirst(clazz.getSimpleName()));
            // 创建Bean定义对象
            BeanDefinition beanDefinition = new BeanDefinition(clazz, null);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }
            // 解析bean元素中的property子元素，用于读取Bean对象的属性信息
            List<Element> propertyList = bean.elements("property");
            // 遍历属性并填充Bean定义对象
            for (Element property : propertyList) {
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 根据属性类型选择值对象，可以是引用对象或者基本类型的值
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性值对象
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 检查是否已经存在相同名称的Bean定义对象
            if (this.getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册Bean定义对象到Bean定义注册表对象中
            this.getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    /**
     * 包扫描器（扫描路径）
     * 扫描指定包路径中的类文件，并将符合条件的类注册为Bean定义对象。
     *
     * @param scanPath 要扫描的包路径，可以包含多个路径，以逗号分隔
     */
    private void scanPackage(String scanPath) {
        // 将逗号分隔的多个包路径拆分成数组
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        // 创建类路径Bean定义扫描器，用于扫描并注册Bean定义对象
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.getRegistry());
        // 执行扫描
        scanner.doScan(basePackages);
    }
}
