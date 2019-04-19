package com.superdjm.client;

import com.superdjm.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author jamiedeng
 * @since 2019/4/14
 */

public class Client {

    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new InitClientHandler());
                    ch.pipeline().addLast(new FrameDecoder(Integer.MAX_VALUE, 7, 4));
                    ch.pipeline().addLast(new CodeCHandler());
                    ch.pipeline().addLast(new LoginResultHandler());
                    ch.pipeline().addLast(new SingleMessageHandler());
                    ch.pipeline().addLast(new CommonResponseHandler());
                }
            });

        bootstrap.connect("127.0.0.1", 8080).addListener(
            future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else {
                    System.err.println("连接失败!");
                    // 重新连接
                }
            }
        );
    }

}
