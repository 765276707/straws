package com.gitee.pristine.common.utils;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * 类工具类
 * @author Pristine Xu
 */
public class ClassUtil {

    /**
     * 实例化对象
     * @param clazzRef
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getInstance(String clazzRef) throws Exception {
        Class<?> algClazz = Class.forName(clazzRef);
        Constructor<?> constructor = algClazz.getDeclaredConstructor();
        return (T) constructor.newInstance();
    }

    /**
     * 实例化对象
     * @param algClazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getInstance(Class<?> algClazz) throws Exception {
        Constructor<?> constructor = algClazz.getDeclaredConstructor();
        return (T) constructor.newInstance();
    }

    /**
     * 在指定路径下扫描带有某个注解的所有类
     * @param prefix 路径前缀
     * @param annotation 指定注解
     * @return
     */
    public static Set<Class<?>> getAnnotatedTypes(String prefix, Class<? extends Annotation> annotation) {
        // 扫描并获取所有子类
        Reflections reflections = new Reflections(prefix);
        return reflections.getTypesAnnotatedWith(annotation);
    }

    /**
     * 在指定路径下扫描某个类的所有子类
     * @param prefix 路径前缀
     * @param type 指定父类
     * @param <T>
     * @return
     */
    public static <T> Set<Class<? extends T>> getSubTypes(String prefix, Class<T> type) {
        Reflections reflections = new Reflections(prefix);
        return reflections.getSubTypesOf((Class<T>) type);
    }

}
