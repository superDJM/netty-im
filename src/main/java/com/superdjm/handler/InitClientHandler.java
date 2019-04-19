package com.superdjm.handler;

import com.superdjm.Session;
import com.superdjm.codec.IMCodec;
import com.superdjm.potocol.LoginRequestProtocol;
import com.superdjm.potocol.SingleMessageProtocol;
import com.superdjm.serialization.Serializer;
import com.superdjm.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

/**
 * @author jamiedeng
 * @since 2019/4/18
 */
public class InitClientHandler extends ChannelInboundHandlerAdapter {

    @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        super.channelRead(ctx, msg);
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //登录
        startConsoleThread(ctx.channel());
        super.channelActive(ctx);
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                Scanner sc = new Scanner(System.in);
                if (SessionUtil.hasLogin(channel)) {
                    Session session = SessionUtil.getSession(channel);
                    SingleMessageProtocol messageProtocol = new SingleMessageProtocol();
                    System.out.println("发消息格式（userId msg） ");

                    messageProtocol.setToUserId(sc.nextInt());

                    messageProtocol.setMsg(sc.next());
                    messageProtocol.setMsgClazzType(String.class);
                    messageProtocol.setFromUserId(session.getUserId());
                    ByteBuf byteBuf = IMCodec.INSTANCE
                        .encode(channel.alloc().ioBuffer(), messageProtocol, Serializer.DEFAULT);
                    channel.writeAndFlush(byteBuf);
                } else {
                    System.out.println("登录--用户名：");

                    LoginRequestProtocol loginRequestProtocol = new LoginRequestProtocol();
                    loginRequestProtocol.setUserName(sc.nextLine());

                    ByteBuf byteBuf = IMCodec.INSTANCE
                        .encode(channel.alloc().ioBuffer(), loginRequestProtocol, Serializer.DEFAULT);
                    channel.writeAndFlush(byteBuf);
                    waitForLoginResponse();
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

}
