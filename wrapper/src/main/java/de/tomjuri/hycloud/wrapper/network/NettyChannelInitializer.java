package de.tomjuri.hycloud.wrapper.network;

import io.netty5.channel.Channel;
import io.netty5.channel.ChannelInitializer;

public class NettyChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline().addLast("channel-handler", new NettyChannelHandler());
    }
}
