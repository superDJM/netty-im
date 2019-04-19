package com.superdjm.handler;

import com.superdjm.potocol.SingleMessageProtocol;
import com.superdjm.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 请求handler
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
public class SingleMessageServerHandler extends SimpleChannelInboundHandler<SingleMessageProtocol> {

    @Override protected void channelRead0(ChannelHandlerContext ctx, SingleMessageProtocol msg) throws Exception {
        System.out.println(new Date() + ": 单聊请求 -> " + msg);
        Integer targetUserId = msg.getToUserId();

        Channel channel = SessionUtil.getChannel(targetUserId);
        channel.writeAndFlush(msg);
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
