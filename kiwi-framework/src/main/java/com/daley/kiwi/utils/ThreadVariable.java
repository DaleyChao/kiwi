package com.daley.kiwi.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程本地变量
 *
 * @author Daniel
 * @date 2018/8/22 15:13
 */
public class ThreadVariable<T> {
    private Map<Thread, T> localVariable = Collections.synchronizedMap(new HashMap<>());

    public void set(T value) {
        localVariable.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread key = Thread.currentThread();
        T value = localVariable.get(key);
        if (value == null && !localVariable.containsKey(key)) {
            value = initialValue();
            localVariable.put(key, value);
        }
        return value;
    }

    public void remove() {
        localVariable.remove(Thread.currentThread());
    }

    public T initialValue() {
        return null;
    }

}
