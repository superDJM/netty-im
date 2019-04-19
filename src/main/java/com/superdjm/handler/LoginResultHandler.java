package com.superdjm.handler;

import com.superdjm.Session;
import com.superdjm.potocol.LoginResultProtocol;
import com.superdjm.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jamiedeng
 * @since 2019/4/18
 */
public class LoginResultHandler extends SimpleChannelInboundHandler<LoginResultProtocol> {
    @Override protected void channelRead0(ChannelHandlerContext ctx, LoginResultProtocol msg) throws Exception {
        if (msg.getIsSuccess()) {
            System.out.println(new Date() + ": 登录成功 -> " + msg);
            Session session = new Session();
            session.setUserId(msg.getUserId());
            session.setUserName(msg.getUserName());
            SessionUtil.bindSession(session, ctx.channel());
        }
    }

}
