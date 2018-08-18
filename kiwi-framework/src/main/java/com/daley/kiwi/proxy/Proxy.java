package com.daley.kiwi.proxy;

/**
 * @author daley
 * @date 2018/8/18
 */
public interface Proxy {
    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
