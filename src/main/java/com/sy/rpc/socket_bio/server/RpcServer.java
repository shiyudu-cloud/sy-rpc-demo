package com.sy.rpc.socket_bio.server;

import java.io.IOException;

/**
 * Rpc 调用服务端
 * @Author: dushiyu
 * @Date: 2019-07-22 10:39
 * @Version 1.0
 */
public interface RpcServer {

     void stop();

     void start() throws IOException;

     void register(Class serviceInterface ,Class impl);

     boolean isRunning();

     int getPort();

}
