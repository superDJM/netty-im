package com.superdjm.handler;

import com.superdjm.potocol.SingleMessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 请求handler
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
public class SingleMessageHandler extends SimpleChannelInboundHandler<SingleMessageProtocol> {

    @Override protected void channelRead0(ChannelHandlerContext ctx, SingleMessageProtocol msg) throws Exception {
        System.out.println(new Date() + ": 单聊请求 -> " + msg);

        System.out.println(new Date() + ": 收到" + msg.getFromUserId() + "的消息" + msg.getMsg());
    }
}
