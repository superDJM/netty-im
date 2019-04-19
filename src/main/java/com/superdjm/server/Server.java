package com.superdjm.server;

import com.superdjm.handler.CodeCHandler;
import com.superdjm.handler.FrameDecoder;
import com.superdjm.handler.LoginRequestHandler;
import com.superdjm.handler.SingleMessageServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jamiedeng
 * @since 2019/4/14
 */
public class Server {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new CodeCHandler());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new SingleMessageServerHandler());
                    }

                });

            ChannelFuture f = b.bind(8080).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
