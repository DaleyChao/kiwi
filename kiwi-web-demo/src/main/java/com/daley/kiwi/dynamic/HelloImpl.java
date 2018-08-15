package com.daley.kiwi.dynamic;

/**
 * @author daley
 * @date 2018/8/18
 */
public class HelloImpl implements Hello {
    @Override
    public void say(String name) {
        System.out.print(name);
    }
}
