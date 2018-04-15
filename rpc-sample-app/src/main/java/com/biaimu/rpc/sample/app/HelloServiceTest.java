package com.biaimu.rpc.sample.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.biaimu.rpc.client.RpcProxy;
import com.biaimu.rpc.simple.client.HelloService;
import com.biaimu.rpc.simple.client.Person;
public class HelloServiceTest {

	private static ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");

	public static void main(String[] args){

		RpcProxy rpcProxy = (RpcProxy)ac.getBean("rpcProxy");
		/*HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
		System.out.println("服务端返回结果：");
		System.out.println(result);*/

		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello(new Person("Zhao", "Yihao"));
		System.out.println("服务端返回结果：");
		System.out.println(result);
	}
}
