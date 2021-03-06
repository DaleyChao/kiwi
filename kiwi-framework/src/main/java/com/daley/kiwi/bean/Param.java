package com.daley.kiwi.bean;

import com.daley.kiwi.utils.CastUtil;

import java.util.Map;

/**
 * @author daley
 * @date 2018/8/11
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return this.paramMap;
    }
}
