package de.tomjuri.hycloud.wrapper.network;

import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.EventLoopGroup;
import io.netty5.channel.MultithreadEventLoopGroup;
import io.netty5.channel.epoll.Epoll;
import io.netty5.channel.epoll.EpollHandler;
import io.netty5.channel.epoll.EpollSocketChannel;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    private final EventLoopGroup eventLoopGroup = new MultithreadEventLoopGroup(Epoll.isAvailable() ? EpollHandler.newFactory() : NioHandler.newFactory());

    public void connect(String host, int port) {
        new Bootstrap()
                .group(eventLoopGroup)
                .channelFactory(Epoll.isAvailable() ? EpollSocketChannel::new : NioSocketChannel::new)
                .handler(new NettyChannelInitializer())
                .option(ChannelOption.TCP_NODELAY, true)
                .connect(host, port)
                .addListener(future -> {
                    if(future.isSuccess()) {
                        System.out.println("Connected to " + host + ":" + port);
                    } else {
                        System.out.println("Failed to connect to " + host + ":" + port);
                    }
                });
    }
}
