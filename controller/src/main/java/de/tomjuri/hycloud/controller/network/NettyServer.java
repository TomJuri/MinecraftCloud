package de.tomjuri.hycloud.controller.network;

import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.EventLoopGroup;
import io.netty5.channel.MultithreadEventLoopGroup;
import io.netty5.channel.epoll.Epoll;
import io.netty5.channel.epoll.EpollHandler;
import io.netty5.channel.epoll.EpollServerSocketChannel;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Future;

public class NettyServer {
    private final EventLoopGroup bossGroup = new MultithreadEventLoopGroup(Epoll.isAvailable() ? EpollHandler.newFactory() : NioHandler.newFactory());
    private final EventLoopGroup workGroup = new MultithreadEventLoopGroup(Epoll.isAvailable() ? EpollHandler.newFactory() : NioHandler.newFactory());

    private Future<Void> future;

    public NettyServer() {
        new ServerBootstrap()
                .channelFactory(Epoll.isAvailable() ? EpollServerSocketChannel::new : NioServerSocketChannel::new)
                .group(bossGroup, workGroup)
                .childHandler(new NettyChannelInitializer())
                .bind("0.0.0.0", 1337)
                .addListener(futures -> {

                });
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
