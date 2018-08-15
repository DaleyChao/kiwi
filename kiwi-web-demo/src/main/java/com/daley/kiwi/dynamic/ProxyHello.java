package com.daley.kiwi.dynamic;

/**
 * @author daley
 * @date 2018/8/18
 */
public class ProxyHello implements Hello {
    private Hello hello;

    public ProxyHello() {
        hello = new HelloImpl();
    }

    @Override
    public void say(String name) {
        before();
        hello.say(name);
        after();
    }

    private void before(){
        System.out.println("before");
    }
    private void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        ProxyHello proxyHello = new ProxyHello();
        proxyHello.say("daley");
    }
}
