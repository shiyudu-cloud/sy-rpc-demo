package com.sy.rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 14:02
 * @Version 1.0
 */
public class Consumer {

    private static ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClient client;

    /**
     * 创建一个代理对象
     */
    public Object createProxy(final Class<?> serviceClass,
                              final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    if (client == null) {
                        initClient();
                    }
                    // 设置参数
                    client.setParam(providerName + args[0]);
                    return executor.submit(client).get();
                });
    }

    /**
     * 初始化客户端
     */
    private static void initClient() {
        client = new NettyClient();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(client);
                    }
                });
        try {
            b.connect("localhost", 8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
