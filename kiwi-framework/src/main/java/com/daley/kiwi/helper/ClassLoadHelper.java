package com.daley.kiwi.helper;

import com.daley.kiwi.annotation.Controller;
import com.daley.kiwi.annotation.Service;
import com.daley.kiwi.utils.ClassUtil;
import com.daley.kiwi.utils.PropsUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类加载助手
 * 加载所有指定包下的类
 *
 * @author daley
 * @date 2018/8/10
 */
public final class ClassLoadHelper {
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceSet = new HashSet<>();
        for (Class<?> cla : CLASS_SET) {
            if (cla.isAnnotationPresent(Service.class)) {
                serviceSet.add(cla);
            }
        }
        return serviceSet;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerSet = new HashSet<>();
        for (Class<?> cla : CLASS_SET) {
            if (cla.isAnnotationPresent(Controller.class)) {
                controllerSet.add(cla);
            }
        }
        return controllerSet;
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> set = new HashSet<>();
        set.addAll(getServiceClassSet());
        set.addAll(getControllerClassSet());
        return set;
    }

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
