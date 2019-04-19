package com.superdjm.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * fastjson 序列化器
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
public class FastJsonSerializer implements Serializer {
    @Override public byte getSerializerAlgorithm() {
        return 1;
    }

    @Override public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes, clazz);
    }
}
