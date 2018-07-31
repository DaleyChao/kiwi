package com.daley.kiwi.bean;

/**
 * 返回数据对象
 *
 * @author daley
 * @date 2018/8/11
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
