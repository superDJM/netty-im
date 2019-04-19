package com.superdjm.codec;

import com.superdjm.potocol.Protocol;
import com.superdjm.serialization.Serializer;
import io.netty.buffer.ByteBuf;

/**
 * @author jamiedeng
 * @since 2019/4/17
 */

public interface Codec {

    ByteBuf encode(ByteBuf byteBuf, Protocol protocol, Serializer serializer);

    Protocol decode(ByteBuf byteBuf);
}
