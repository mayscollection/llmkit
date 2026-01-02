package me.maydeng.llmkit.core.util;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;

public class Maps extends HashMap<String, Object> {

    public static Maps of(String key, Object value) {
        Maps maps = new Maps();
        maps.put(key, value);
        return maps;
    }

    public Maps set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Maps setIf(boolean condition, String key, Object value) {
        if (condition) {
            this.put(key, value);
        }
        return this;
    }

    public Maps setIfNotNull(String key, Object value) {
        if (value != null) {
            this.put(key, value);
        }
        return this;
    }

    public Maps setIfNotEmpty(String key, Object value) {
        if (value == null) {
            return this;
        }
        if (value instanceof CharSequence && ((CharSequence) value).length() == 0) {
            return this;
        }
        this.put(key, value);
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
