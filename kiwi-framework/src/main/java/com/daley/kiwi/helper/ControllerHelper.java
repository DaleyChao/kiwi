package com.daley.kiwi.helper;

import com.daley.kiwi.annotation.Action;
import com.daley.kiwi.bean.Handler;
import com.daley.kiwi.bean.Request;
import com.daley.kiwi.utils.ClassUtil;
import com.daley.kiwi.utils.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author daley
 * @date 2018/8/11
 */
public final class ControllerHelper {
    public static final Map<Request, Handler> HANDLER_MAP = new HashMap<>();

    static {
        //得到所有controller类
        Set<Class<?>> classSet = ClassLoadHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(classSet)) {
            for (Class<?> controllerClass : classSet) {
                Method[] methods = controllerClass.getDeclaredMethods();

                //找出带@Action的方法
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {

                            //@Action 被标注
                            Action action = method.getDeclaredAnnotation(Action.class);
                            String mapping = action.value();

                            //验证URL规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);

                                    //初始化HANDLER_MAP
                                    HANDLER_MAP.put(request, handler);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return HANDLER_MAP.get(request);
    }
}
