package com.stars.starsspring.framework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认资源加载器——类
 * 对不同方式的资源加载进行统一封装。把不同方式的资源加载，集中统一至该接口下进行处理，外部用户仅需传递资源地址即可。
 * 这里不会让外部调用方知道过多的细节，而是仅关心具体调用结果即可。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * getResource
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public class DefaultResourceLoader implements ResourceLoader {

    /**
     * 获取资源（资源位置）
     * 对外屏蔽掉具体资源位置的区别。
     *
     * @param location 资源位置，可以是类路径、文件系统路径、URL等
     * @return 资源对象
     */
    @Override
    public Resource getResource(String location) {
        // 检查资源位置是否为空
        Assert.notNull(location, "Location must not be null");
        // 如果资源位置以"classpath:"前缀开头，返回ClassPathResource对象
        if (location.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(ResourceLoader.CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 尝试将资源位置解析为URL对象，如果成功则返回UrlResource对象
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 如果解析为URL对象失败，则作为文件系统路径处理，返回FileSystemResource对象
                return new FileSystemResource(location);
            }
        }
    }
}
