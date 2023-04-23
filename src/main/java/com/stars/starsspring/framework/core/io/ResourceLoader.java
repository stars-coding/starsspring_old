package com.stars.starsspring.framework.core.io;

/**
 * 资源加载器——接口
 * 对不同方式的资源加载进行统一封装。把不同方式的资源加载，集中统一至该接口下进行处理，外部用户仅需传递资源地址即可。
 * 这里不会让外部调用方知道过多的细节，而是仅关心具体调用结果即可。
 * <p>
 * <p>
 * 属性字段：
 * CLASSPATH_URL_PREFIX
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getResource
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ResourceLoader {

    // 类路径URL前缀，用于指示资源位于类路径中。
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源（资源位置）
     *
     * @param location 资源位置，可以是类路径、文件系统路径、URL等
     * @return 资源对象
     */
    Resource getResource(String location);
}
