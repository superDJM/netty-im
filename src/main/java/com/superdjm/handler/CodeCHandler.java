package com.superdjm.handler;

import com.superdjm.codec.IMCodec;
import com.superdjm.potocol.Protocol;
import com.superdjm.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * 借码handler
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
public class CodeCHandler extends ByteToMessageCodec<Protocol> {

    @Override protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        IMCodec.INSTANCE.encode(out, msg, Serializer.DEFAULT);
    }

    @Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(IMCodec.INSTANCE.decode(in));
    }
}
