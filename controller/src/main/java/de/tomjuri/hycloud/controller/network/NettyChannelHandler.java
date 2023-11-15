package de.tomjuri.hycloud.controller.network;

import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.SimpleChannelInboundHandler;

public class NettyChannelHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {

    }
}
