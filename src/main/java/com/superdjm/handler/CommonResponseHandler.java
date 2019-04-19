package com.superdjm.handler;

import com.superdjm.potocol.CommonResponseProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jamiedeng
 * @since 2019/4/18
 */
public class CommonResponseHandler extends SimpleChannelInboundHandler<CommonResponseProtocol> {
    @Override protected void channelRead0(ChannelHandlerContext ctx, CommonResponseProtocol msg) throws Exception {
        System.out.println(msg);
    }

    /**
     * 读数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        super.channelReadComplete(ctx);
    }
}
