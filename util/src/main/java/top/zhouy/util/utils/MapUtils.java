package top.zhouy.util.utils;

import java.util.HashMap;

/**
 * MapUtils Map工具类
 */
public class MapUtils extends HashMap<String,Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key,value);
        return this;
    }
}
