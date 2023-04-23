package com.stars.starsspring.framework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源——接口
 * 获取资源的输入流。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * getInputStream
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface Resource {

    /**
     * 获取输入流
     * 用于读取资源的内容。
     *
     * @return 资源的输入流
     * @throws IOException 如果获取输入流时出现IO异常，则抛出IOException异常
     */
    InputStream getInputStream() throws IOException;
}
