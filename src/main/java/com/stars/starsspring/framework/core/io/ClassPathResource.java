package com.stars.starsspring.framework.core.io;

import com.stars.starsspring.framework.util.ClassUtils;
import cn.hutool.core.lang.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类路径资源——类
 * 获取类路径下的资源的输入流。
 * <p>
 * <p>
 * 属性字段：
 * path
 * classLoader
 * <p>
 * 重写方法：
 * getInputStream
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * ClassPathResource
 * ClassPathResource
 *
 * @author stars
 */
public class ClassPathResource implements Resource {

    // 资源路径
    private final String path;
    // 类加载器对象
    private ClassLoader classLoader;

    /**
     * 有参构造函数（资源路径）
     * 使用默认类加载器加载资源。
     *
     * @param path 资源路径
     */
    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    /**
     * 有参构造函数（资源路径，类加载器对象）
     * 指定类加载器加载资源。
     *
     * @param path        资源路径
     * @param classLoader 类加载器对象
     */
    public ClassPathResource(String path, ClassLoader classLoader) {
        // 检查资源路径是否为空
        Assert.notNull(path, "Path must not be null");
        // 如果传递的类加载器不为空，则使用传递的类加载器，否则使用默认类加载器
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    /**
     * 获取输入流
     * 用于读取资源的内容。
     *
     * @return 资源的输入流
     * @throws IOException 如果获取输入流时出现IO异常，则抛出IOException异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        // 使用类加载器获取资源的输入流
        InputStream is = this.classLoader.getResourceAsStream(this.path);
        // 如果输入流为空，抛出文件未找到的异常
        if (is == null) {
            throw new FileNotFoundException(
                    this.path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
