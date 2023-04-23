package com.stars.starsspring.framework.context;

import com.stars.starsspring.framework.beans.factory.HierarchicalBeanFactory;
import com.stars.starsspring.framework.beans.factory.ListableBeanFactory;
import com.stars.starsspring.framework.core.io.ResourceLoader;

/**
 * 应用上下文——接口
 * 是Spring中应用程序组件之间通信的中央接口。
 * <p>
 * <p>
 * 属性字段：
 * <p>
 * 重写方法：
 * <p>
 * 定义方法：
 * <p>
 * 编写方法：
 *
 * @author stars
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
