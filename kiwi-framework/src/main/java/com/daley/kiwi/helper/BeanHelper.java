package com.daley.kiwi.helper;

import com.daley.kiwi.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean管理
 *
 * @author Daniel
 * @date 2018/8/10 11:10
 */
public final class BeanHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassLoadHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Object obj = ReflectionUtil.newInstance(cls);
            if (obj != null) {
                BEAN_MAP.put(cls, obj);
            }
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
       if(BEAN_MAP.containsKey(cls)){
           throw new RuntimeException("bean not exist");
       }
       return (T)BEAN_MAP.get(cls);
    }

}
