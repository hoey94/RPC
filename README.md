目录说明
rpc-client : 客户端框架核心代码
rpc-server : 服务端框架核心代码
rpc-common : 框架工具
rpc-registry : 里面有两个核心功能,
		1.注册服务 - ServiceDiscovery
		2.发现服务 - ServiceRegistry
rpc-sample-app : 客户端程序 (调用被发布的服务,执行对应业务代码)
rpc-sample-server : 服务区程序 (用来发布RPC服务)

1.先把zookeeper跑起来 (我这边搞得是zookeeper集群,可以单台zookeeper跑也没问题)
2.把项目导入到IDEA或者Eclipse（注意配maven）
3.启动rpc-sample-server 将helloService发布成服务
4.启动rpc-sample-app 调用HelloService
