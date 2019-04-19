package com.superdjm.handler;

import com.superdjm.codec.IMCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拆包粘包处理器
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    public FrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != IMCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
