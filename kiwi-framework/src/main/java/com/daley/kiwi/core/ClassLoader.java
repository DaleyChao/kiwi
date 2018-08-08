package com.daley.kiwi.core;

import com.daley.kiwi.helper.BeanHelper;
import com.daley.kiwi.helper.ClassLoadHelper;
import com.daley.kiwi.helper.ControllerHelper;
import com.daley.kiwi.helper.IocHelper;
import com.daley.kiwi.utils.ClassUtil;

/**
 * @author daley
 * @date 2018/8/11
 */
public final class ClassLoader {

    public static void init() {
        Class<?>[] classes = {
                ClassLoadHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class
        };
        for (Class<?> cls : classes) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
