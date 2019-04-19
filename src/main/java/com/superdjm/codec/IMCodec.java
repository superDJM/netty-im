package com.superdjm.codec;

import com.superdjm.potocol.Protocol;
import com.superdjm.serialization.FastJsonSerializer;
import com.superdjm.serialization.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * IM解码器
 *
 * @author jamiedeng
 * @since 2019/4/17
 */
public class IMCodec implements Codec {

    private static final Map<Byte, Class<? extends Protocol>> protocolTypeMap;
    private static final Map<Byte, Serializer>                serializerMap;

    public static final Codec INSTANCE = new IMCodec();

    static {
        protocolTypeMap = new HashMap<>();
        for (Protocol.Op p : Protocol.Op.values()) {
            protocolTypeMap.put(p.getCode(), p.getClz());
        }

        serializerMap = new HashMap<>();
        Serializer serializer = new FastJsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    //魔数
    public static final int MAGIC_NUMBER = 0x12345678;

    @Override public ByteBuf encode(ByteBuf byteBuf, Protocol protocol, Serializer serializer) {
        // 2. 序列化 Java 对象
        byte[] bytes = serializer.serialize(protocol);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(protocol.getVersion());
        byteBuf.writeByte(serializer.getSerializerAlgorithm());
        byteBuf.writeByte(protocol.getOp());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    @Override public Protocol decode(ByteBuf byteBuf) {

        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Protocol> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Protocol> getRequestType(byte command) {

        return protocolTypeMap.get(command);
    }
}
