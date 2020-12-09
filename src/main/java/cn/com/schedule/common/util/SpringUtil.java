package cn.com.schedule.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @DESC: 文件介绍
 * @Auther: Levi.Ding
 * @Date: 2020/12/9 11:18
 */
public class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    /**
     * 获取容器中的实例
     *
     * @param beanId 注入在Spring容器中的bean的ID 默认为类名首字母小写
     * @param clazz  获取的bean的实际的类的class
     */
    public static <T> T getBean(String beanId, Class<T> clazz) {
        return context.getBean(beanId, clazz);
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
