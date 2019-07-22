package com.sy.rpc.netty.test;

import com.sy.rpc.netty.client.Consumer;
import com.sy.rpc.netty.server.NettyService;
import com.sy.rpc.netty.service.HelloService;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 14:12
 * @Version 1.0
 */
public class NettyRunTest {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //启动 netty
        NettyService.startServer0("localhost",8080);
        Consumer consumer = new Consumer();
        // 创建一个代理对象
        HelloService service = (HelloService) consumer
                .createProxy(HelloService.class, providerName);
        for (; ; ) {
            Thread.sleep(1000);
            System.out.println(service.sayHi("are you ok ?"));
        }
    }

}
