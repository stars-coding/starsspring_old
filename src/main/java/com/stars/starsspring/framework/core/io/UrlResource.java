package com.stars.starsspring.framework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL资源——类
 * 获取URL中资源的输入流。
 * <p>
 * <p>
 * 属性字段：
 * url
 * <p>
 * 重写方法：
 * getInputStream
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * UrlResource
 *
 * @author stars
 */
public class UrlResource implements Resource {

    // URL对象
    private final URL url;

    /**
     * 有参构造函数（URL对象）
     * 使用URL创建URL资源
     *
     * @param url URL对象
     */
    public UrlResource(URL url) {
        // 检查URL是否为空
        Assert.notNull(url, "URL must not be null");
        this.url = url;
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
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        } catch (IOException ex) {
            // 如果是HttpURLConnection类型，断开连接以释放资源
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }
}
