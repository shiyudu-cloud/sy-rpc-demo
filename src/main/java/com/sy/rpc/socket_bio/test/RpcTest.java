package com.sy.rpc.socket_bio.test;


import com.sy.rpc.socket_bio.client.RpcClient;
import com.sy.rpc.socket_bio.server.RpcServer;
import com.sy.rpc.socket_bio.server.impl.RpcServiceImpl;
import com.sy.rpc.socket_bio.service.HelloService;
import com.sy.rpc.socket_bio.service.impl.HelloServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 11:13
 * @Version 1.0
 */
public class RpcTest {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RpcServer rpcServer = new RpcServiceImpl(8080);
                rpcServer.register(HelloService.class, HelloServiceImpl.class);
                try {
                    rpcServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        HelloService helloService = RpcClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8080));

        System.out.println(helloService.sayHi("Martin"));
    }
}
