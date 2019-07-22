package com.sy.rpc.socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 11:03
 * @Version 1.0
 */
public class RpcClient {

    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface , final InetSocketAddress address){
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket = null;
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                try {
                    //链接远程服务 根据指定地址链接远程服务提供者
                    socket = new Socket();
                    socket.connect(address);
                    //将远程服务调用所需要的接口类、方法、参数列表、等编码后发送给服务者
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeUTF(serviceInterface.getName());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);
                    //同步阻塞等待服务器应答，获取应答后返回
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                }finally {
                    if (socket != null) socket.close();
                    if (output != null) output.close();
                    if (input != null) input.close();
                }

            }
        });

    }
}
