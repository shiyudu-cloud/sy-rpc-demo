package com.sy.rpc.service.impl;

import com.sy.rpc.service.HelloService;

/**
 * @Author: dushiyu
 * @Date: 2019-07-22 10:41
 * @Version 1.0
 */
public class HelloServiceImpl implements HelloService {



    @Override
    public String sayHi(String name) {
        return "Hi : "+ name +"! Welcome to SyHouse";
    }
}
