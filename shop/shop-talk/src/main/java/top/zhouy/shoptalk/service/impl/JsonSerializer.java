package top.zhouy.shoptalk.service.impl;

import com.alibaba.fastjson.JSON;
import top.zhouy.shoptalk.service.Serializer;

import static top.zhouy.shoptalk.bean.Constant.SERIALIZER_JSON;

/**
 * @description: json序列化方式
 * @author: zhouy
 * @create: 2020-12-25 11:55:00
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SERIALIZER_JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
