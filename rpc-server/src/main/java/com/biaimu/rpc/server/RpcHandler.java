package com.biaimu.rpc.server;

import com.biaimu.rpc.common.RpcRequest;
import com.biaimu.rpc.common.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理具体的业务调用
 * 通过构造时传入的“业务接口及实现”handlerMap，来调用客户端所请求的业务方法
 * 并将业务方法返回值封装成response对象写入下一个handler（即编码handler——RpcEncoder）
 * java d动态代理
 * scoket
 * @author blackcoder
 *
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RpcHandler.class);

	private final Map<String, Object> handlerMap;

	public RpcHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	/**
	 * 接收消息，处理消息，返回结果
	 */
	@Override
	public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request)
			throws Exception {
		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		try {
			Object result = handle(request);
			response.setResult(result);
		} catch (Throwable t) {
			response.setError(t);
		}
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * 根据request来处理具体的业务调用
	 * 调用是通过反射的方式来完成
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	private Object handle(RpcRequest request) throws Throwable {
		String className = request.getClassName(); // helloService
		
		//拿到实现类对象
		Object serviceBean = handlerMap.get(className); //helloServiceImpl
		
		//拿到要调用的方法名、参数类型、参数值
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();
		
		//拿到接口类
		Class<?> forName = Class.forName(className);
		
		//调用实现类对象的指定方法并返回结果
		Method method = forName.getMethod(methodName, parameterTypes);
		return method.invoke(serviceBean, parameters);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOGGER.error("server caught exception", cause);
		ctx.close();
	}
}
