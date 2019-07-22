package com.sy.rpc.netty.server.handler;

import com.sy.rpc.netty.service.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author moriseisakai
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       if (msg.toString() != null) {
           String s = new HelloServiceImpl().sayHi(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
           ctx.writeAndFlush(s);
       }
    }
}
