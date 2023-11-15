package de.tomjuri.hycloud.controller.network;

import io.netty5.channel.Channel;
import io.netty5.channel.ChannelInitializer;

public class NettyChannelInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("channel-handler", new NettyChannelHandler());
    }
}
