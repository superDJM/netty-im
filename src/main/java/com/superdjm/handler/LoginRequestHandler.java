package com.superdjm.handler;

import com.alibaba.fastjson.JSON;
import com.superdjm.Attributes;
import com.superdjm.Session;
import com.superdjm.potocol.LoginRequestProtocol;
import com.superdjm.potocol.LoginResultProtocol;
import com.superdjm.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录请求handler
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestProtocol> {

    private static final Map<String, Integer> userMap = new HashMap<>();

    static {
        userMap.put("001", 1);
        userMap.put("002", 2);
    }

    @Override protected void channelRead0(ChannelHandlerContext ctx, LoginRequestProtocol msg) throws Exception {
        LoginResultProtocol loginResultProtocol = new LoginResultProtocol();
        System.out.println(new Date() + ": 登录请求 -> " + msg);

        if (!userMap.containsKey(msg.getUserName())) {
            loginResultProtocol.fillFailure("用户名不存在");
        }

        loginResultProtocol.fillSucc();
        loginResultProtocol.setUserId(userMap.get(msg.getUserName()));
        loginResultProtocol.setUserName(msg.getUserName());

        ctx.channel().attr(Attributes.LOGIN).set(true);
        Session session = new Session();
        session.setUserId(loginResultProtocol.getUserId());
        session.setUserName(loginResultProtocol.getUserName());
        SessionUtil.bindSession(session, ctx.channel());

        System.out.println(new Date() + ": 登录返回 -> " + loginResultProtocol);
        ctx.channel().write(loginResultProtocol);
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端下线" + JSON.toJSONString(SessionUtil.getSession(ctx.channel())));
        SessionUtil.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }
}
