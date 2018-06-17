package com.daley.kiwi.helper;

import com.daley.kiwi.annotation.Inject;
import com.daley.kiwi.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现注入
 *
 * @author Daniel
 * @date 2018/6/25 15:27
 */
public final class IocHelper {
    static {

        //获得所有bean
        Map<Class<?>, Object> beanCollection = BeanHelper.getBeanMap();
        if (!beanCollection.isEmpty()) {
            for (Map.Entry<Class<?>, Object> entry : beanCollection.entrySet()) {

                //bean和bean实例
                Class<?> cls = entry.getKey();
                Object obj = entry.getValue();

                //获得所有声明的成员变量 包括private
                Field[] fields = cls.getDeclaredFields();
                if(ArrayUtils.isEmpty(fields)){
                    continue;
                }
                for (Field field : fields) {

                    //查找有 @Inject 注解的成员
                    if (field.isAnnotationPresent(Inject.class)) {
                        Class<?> type = field.getType();
                        if (beanCollection.containsKey(type)) {
                            Object beanFieldInstance = beanCollection.get(type);
                            if (beanFieldInstance != null) {

                                //初始化成员 实现注入
                                ReflectionUtil.setField(obj, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
