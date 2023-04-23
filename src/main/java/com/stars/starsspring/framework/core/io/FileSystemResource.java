package com.stars.starsspring.framework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统资源——类
 * 获取文件系统中资源的输入流。
 * <p>
 * <p>
 * 属性字段：
 * file
 * path
 * <p>
 * 重写方法：
 * getInputStream
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 * FileSystemResource
 * FileSystemResource
 * getPath
 *
 * @author stars
 */
public class FileSystemResource implements Resource {

    // 文件对象
    private final File file;
    // 资源路径
    private final String path;

    /**
     * 有参构造函数（文件对象）
     * 使用文件对象创建文件系统资源。
     *
     * @param file 文件对象
     */
    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    /**
     * 有参构造函数（资源路径）
     * 使用文件路径创建文件系统资源。
     *
     * @param path 文件路径
     */
    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
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
        return new FileInputStream(this.file);
    }

    /**
     * 获取资源的路径
     *
     * @return 资源的路径
     */
    public final String getPath() {
        return path;
    }
}
