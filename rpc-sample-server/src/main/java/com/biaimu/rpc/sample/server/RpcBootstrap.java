package com.biaimu.rpc.sample.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 用户系统服务端的启动入口
 * 其意义是启动springcontext，从而构造框架中的RpcServer
 * 亦即：将用户系统中所有标注了RpcService注解的业务发布到RpcServer中
 * @author
 *
 */
public class RpcBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
