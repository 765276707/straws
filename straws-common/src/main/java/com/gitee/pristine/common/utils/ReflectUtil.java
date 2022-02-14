package com.gitee.pristine.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * @author Pristine Xu
 * @date 2021/12/19 17:41
 * @description:
 */
public class ReflectUtil {

    /**
     * 获取类及其父类的所有属性名
     * @param clazz 目标类
     * @return
     */
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        // 当目标类的父类为null，则说明其已经是 Object.class
        while (clazz!=null && clazz.getSuperclass()!=null) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                fieldList.addAll(CollectionUtils.arrayToList(fields));
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 获取字段的属性值
     * @param field 属性
     * @param object 目标对象
     * @return
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Field field, Object object) throws IllegalAccessException {
        Object value = null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
            value = field.get(object);
            field.setAccessible(false);
        } else {
            value =  field.get(object);
        }
        return value;
    }

    /**
     * 获取字段的属性值
     * @param fieldName 属性名
     * @param object 目标对象
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object getFieldValue(String fieldName, Object object)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        Object value = null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
            value = field.get(object);
            field.setAccessible(false);
        } else {
            value =  field.get(object);
        }
        return value;
    }


    /**
     * 设置属性值
     * @param field 属性
     * @param object 目标对象
     * @param value 值
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Field field, Object object, Object value) throws IllegalAccessException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } else {
            field.set(object, value);
        }
    }

    /**
     * 设置属性值
     * @param fieldName 属性名
     * @param object 目标对象
     * @param value 值
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static void setFieldValue(String fieldName, Object object, Object value)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } else {
            field.set(object, value);
        }
    }

    /**
     * 对象转换成目标类的对象
     * @param sourceObject 源对象
     * @param targetClass 目标类
     * @return R
     */
    public static <R> R convertClassType(Object sourceObject, Class<R> targetClass) {
        // 源对象 和 目标类为null 则返回null
        if (sourceObject==null || targetClass==null) {
            return null;
        }
        // 进行转换
        try {
            R targetObject = targetClass.newInstance();
            BeanUtils.copyProperties(sourceObject, targetObject);
            return targetObject;
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }
}
