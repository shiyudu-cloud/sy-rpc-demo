package com.sy.rpc.netty.server.start;

import com.sy.rpc.netty.server.NettyService;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 13:56
 * @Version 1.0
 */
public class NettyStarter {

    public static void main(String[] args) {
        NettyService.startServer0("localhost",8080);
    }
}
