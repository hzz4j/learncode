/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rpc.protocol.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.URLBuilder;
import org.apache.dubbo.common.config.ConfigurationUtils;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.serialize.support.SerializableClassRegistry;
import org.apache.dubbo.common.serialize.support.SerializationOptimizer;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.Transporter;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.remoting.exchange.ExchangeHandler;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.protocol.AbstractProtocol;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.INTERFACE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.apache.dubbo.rpc.Constants.LAZY_CONNECT_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.ON_CONNECT_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.ON_DISCONNECT_KEY;
import static org.apache.dubbo.remoting.Constants.DEFAULT_HEARTBEAT;
import static org.apache.dubbo.remoting.Constants.HEARTBEAT_KEY;
import static org.apache.dubbo.remoting.Constants.CHANNEL_READONLYEVENT_SENT_KEY;
import static org.apache.dubbo.remoting.Constants.CLIENT_KEY;
import static org.apache.dubbo.remoting.Constants.CODEC_KEY;
import static org.apache.dubbo.remoting.Constants.DEFAULT_REMOTING_CLIENT;
import static org.apache.dubbo.remoting.Constants.SERVER_KEY;
import static org.apache.dubbo.rpc.Constants.DEFAULT_REMOTING_SERVER;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.CALLBACK_SERVICE_KEY;
import static org.apache.dubbo.remoting.Constants.CONNECTIONS_KEY;
import static org.apache.dubbo.rpc.Constants.DEFAULT_STUB_EVENT;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.IS_CALLBACK_SERVICE;
import static org.apache.dubbo.rpc.Constants.IS_SERVER_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.OPTIMIZER_KEY;
import static org.apache.dubbo.rpc.Constants.STUB_EVENT_KEY;
import static org.apache.dubbo.rpc.Constants.STUB_EVENT_METHODS_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.SHARE_CONNECTIONS_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.DEFAULT_SHARE_CONNECTIONS;


/**
 * dubbo protocol support.
 */
public class DubboProtocol extends AbstractProtocol {

    public static final String NAME = "dubbo";

    public static final int DEFAULT_PORT = 20880;
    private static final String IS_CALLBACK_SERVICE_INVOKE = "_isCallBackServiceInvoke";
    private static DubboProtocol INSTANCE;

    /**
     * <host:port,Exchanger>
     */
    private final Map<String, ExchangeServer> serverMap = new ConcurrentHashMap<>();
    /**
     * <host:port,Exchanger>
     */
    private final Map<String, List<ReferenceCountExchangeClient>> referenceClientMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> locks = new ConcurrentHashMap<>();
    private final Set<String> optimizers = new ConcurrentHashSet<>();
    /**
     * consumer side export a stub service for dispatching event
     * servicekey-stubmethods
     */
    private final ConcurrentMap<String, String> stubServiceMethodsMap = new ConcurrentHashMap<>();

    private ExchangeHandler requestHandler = new ExchangeHandlerAdapter() {

        @Override
        public CompletableFuture<Object> reply(ExchangeChannel channel, Object message) throws RemotingException {

            if (!(message instanceof Invocation)) {
                throw new RemotingException(channel, "Unsupported request: "
                        + (message == null ? null : (message.getClass().getName() + ": " + message))
                        + ", channel: consumer: " + channel.getRemoteAddress() + " --> provider: " + channel.getLocalAddress());
            }

            // 转成Invocation对象，要开始用反射执行方法了
            Invocation inv = (Invocation) message;
            Invoker<?> invoker = getInvoker(channel, inv);  // 服务实现者

            // need to consider backward-compatibility if it's a callback
            if (Boolean.TRUE.toString().equals(inv.getAttachments().get(IS_CALLBACK_SERVICE_INVOKE))) {
                String methodsStr = invoker.getUrl().getParameters().get("methods");
                boolean hasMethod = false;
                if (methodsStr == null || !methodsStr.contains(",")) {
                    hasMethod = inv.getMethodName().equals(methodsStr);
                } else {
                    String[] methods = methodsStr.split(",");
                    for (String method : methods) {
                        if (inv.getMethodName().equals(method)) {
                            hasMethod = true;
                            break;
                        }
                    }
                }
                if (!hasMethod) {
                    logger.warn(new IllegalStateException("The methodName " + inv.getMethodName()
                            + " not found in callback service interface ,invoke will be ignored."
                            + " please update the api interface. url is:"
                            + invoker.getUrl()) + " ,invocation is :" + inv);
                    return null;
                }
            }
            // 这里设置了，service中才能拿到remoteAddress
            RpcContext.getContext().setRemoteAddress(channel.getRemoteAddress());
            // 执行服务，得到结果
            Result result = invoker.invoke(inv);
            // 返回一个CompletableFuture
            return result.completionFuture().thenApply(Function.identity());
        }

        @Override
        public void received(Channel channel, Object message) throws RemotingException {
            if (message instanceof Invocation) {
                // 这是服务端接收到Invocation时的处理逻辑
                reply((ExchangeChannel) channel, message);

            } else {
                super.received(channel, message);
            }
        }

        @Override
        public void connected(Channel channel) throws RemotingException {
            invoke(channel, ON_CONNECT_KEY);
        }

        @Override
        public void disconnected(Channel channel) throws RemotingException {
            if (logger.isDebugEnabled()) {
                logger.debug("disconnected from " + channel.getRemoteAddress() + ",url:" + channel.getUrl());
            }
            invoke(channel, ON_DISCONNECT_KEY);
        }

        private void invoke(Channel channel, String methodKey) {
            Invocation invocation = createInvocation(channel, channel.getUrl(), methodKey);
            if (invocation != null) {
                try {
                    received(channel, invocation);
                } catch (Throwable t) {
                    logger.warn("Failed to invoke event method " + invocation.getMethodName() + "(), cause: " + t.getMessage(), t);
                }
            }
        }

        private Invocation createInvocation(Channel channel, URL url, String methodKey) {
            String method = url.getParameter(methodKey);
            if (method == null || method.length() == 0) {
                return null;
            }

            RpcInvocation invocation = new RpcInvocation(method, new Class<?>[0], new Object[0]);
            invocation.setAttachment(PATH_KEY, url.getPath());
            invocation.setAttachment(GROUP_KEY, url.getParameter(GROUP_KEY));
            invocation.setAttachment(INTERFACE_KEY, url.getParameter(INTERFACE_KEY));
            invocation.setAttachment(VERSION_KEY, url.getParameter(VERSION_KEY));
            if (url.getParameter(STUB_EVENT_KEY, false)) {
                invocation.setAttachment(STUB_EVENT_KEY, Boolean.TRUE.toString());
            }

            return invocation;
        }
    };

    public DubboProtocol() {
        INSTANCE = this;
    }

    public static DubboProtocol getDubboProtocol() {
        if (INSTANCE == null) {
            // load
            ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(DubboProtocol.NAME);
        }

        return INSTANCE;
    }

    public Collection<ExchangeServer> getServers() {
        return Collections.unmodifiableCollection(serverMap.values());
    }

    public Collection<Exporter<?>> getExporters() {
        return Collections.unmodifiableCollection(exporterMap.values());
    }

    Map<String, Exporter<?>> getExporterMap() {
        return exporterMap;
    }

    private boolean isClientSide(Channel channel) {
        InetSocketAddress address = channel.getRemoteAddress();
        URL url = channel.getUrl();
        return url.getPort() == address.getPort() &&
                NetUtils.filterLocalHost(channel.getUrl().getIp())
                        .equals(NetUtils.filterLocalHost(address.getAddress().getHostAddress()));
    }

    Invoker<?> getInvoker(Channel channel, Invocation inv) throws RemotingException {
        boolean isCallBackServiceInvoke = false;
        boolean isStubServiceInvoke = false;
        int port = channel.getLocalAddress().getPort();
        String path = inv.getAttachments().get(PATH_KEY);

        // if it's callback service on client side
        isStubServiceInvoke = Boolean.TRUE.toString().equals(inv.getAttachments().get(STUB_EVENT_KEY));
        if (isStubServiceInvoke) {
            port = channel.getRemoteAddress().getPort();
        }

        //callback
        isCallBackServiceInvoke = isClientSide(channel) && !isStubServiceInvoke;
        if (isCallBackServiceInvoke) {
            path += "." + inv.getAttachments().get(CALLBACK_SERVICE_KEY);
            inv.getAttachments().put(IS_CALLBACK_SERVICE_INVOKE, Boolean.TRUE.toString());
        }

        // 从请求中拿到serviceKey，从exporterMap中拿到已经导出了的服务
        String serviceKey = serviceKey(port, path, inv.getAttachments().get(VERSION_KEY), inv.getAttachments().get(GROUP_KEY));
        DubboExporter<?> exporter = (DubboExporter<?>) exporterMap.get(serviceKey);

        if (exporter == null) {
            throw new RemotingException(channel, "Not found exported service: " + serviceKey + " in " + exporterMap.keySet() + ", may be version or group mismatch " +
                    ", channel: consumer: " + channel.getRemoteAddress() + " --> provider: " + channel.getLocalAddress() + ", message:" + inv);
        }

        // 拿到服务对应的Invoker
        return exporter.getInvoker();
    }

    public Collection<Invoker<?>> getInvokers() {
        return Collections.unmodifiableCollection(invokers);
    }

    @Override
    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        URL url = invoker.getUrl();

        // export service.
        String key = serviceKey(url);
        // 构造一个Exporter进行服务导出
        DubboExporter<T> exporter = new DubboExporter<T>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);

        //export an stub service for dispatching event
        Boolean isStubSupportEvent = url.getParameter(STUB_EVENT_KEY, DEFAULT_STUB_EVENT);
        Boolean isCallbackservice = url.getParameter(IS_CALLBACK_SERVICE, false);
        if (isStubSupportEvent && !isCallbackservice) {
            String stubServiceMethods = url.getParameter(STUB_EVENT_METHODS_KEY);
            if (stubServiceMethods == null || stubServiceMethods.length() == 0) {
                if (logger.isWarnEnabled()) {
                    logger.warn(new IllegalStateException("consumer [" + url.getParameter(INTERFACE_KEY) +
                            "], has set stubproxy support event ,but no stub methods founded."));
                }

            } else {
                // 服务的stub方法
                stubServiceMethodsMap.put(url.getServiceKey(), stubServiceMethods);
            }
        }

        // 开启NettyServer
        openServer(url);  //请求--->invocation--->服务key--->exporterMap.get(key)--->exporter--->invoker--->invoker.invoke(invocation)-->执行服务

        // 特殊的一些序列化机制，比如kryo提供了注册机制来注册类，提高序列化和反序列化的速度
        optimizeSerialization(url);

        return exporter;
    }

    private void openServer(URL url) {
        // find server.
        String key = url.getAddress(); // 获得ip地址和port， 192.168.40.17:20880

        // NettyClient, NettyServer
        //client can export a service which's only for server to invoke
        boolean isServer = url.getParameter(IS_SERVER_KEY, true);
        if (isServer) {
            // 缓存Server对象
            ExchangeServer server = serverMap.get(key);

            // DCL，Double Check Lock
            if (server == null) {
                synchronized (this) {
                    server = serverMap.get(key);
                    if (server == null) {
                        // 创建Server，并进行缓存
                        serverMap.put(key, createServer(url));
                    }
                }
            } else {
                // server supports reset, use together with override
                // 服务重新导出时，就会走这里
                server.reset(url);
            }
        }
    }

    private ExchangeServer createServer(URL url) {
        url = URLBuilder.from(url)
                // send readonly event when server closes, it's enabled by default
                .addParameterIfAbsent(CHANNEL_READONLYEVENT_SENT_KEY, Boolean.TRUE.toString())
                // enable heartbeat by default
                .addParameterIfAbsent(HEARTBEAT_KEY, String.valueOf(DEFAULT_HEARTBEAT))
                .addParameter(CODEC_KEY, DubboCodec.NAME)
                .build();

        // 协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等，默认为netty
        String str = url.getParameter(SERVER_KEY, DEFAULT_REMOTING_SERVER);

        if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)) {
            throw new RpcException("Unsupported server type: " + str + ", url: " + url);
        }

        // 通过url绑定端口，和对应的请求处理器
        ExchangeServer server;
        try {
            // requestHandler是请求处理器，类型为ExchangeHandler
            // 表示从url的端口接收到请求后，requestHandler来进行处理
            server = Exchangers.bind(url, requestHandler);
        } catch (RemotingException e) {
            throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
        }

        // 协议的客户端实现类型，比如：dubbo协议的mina,netty等
        str = url.getParameter(CLIENT_KEY);
        if (str != null && str.length() > 0) {
            Set<String> supportedTypes = ExtensionLoader.getExtensionLoader(Transporter.class).getSupportedExtensions();
            if (!supportedTypes.contains(str)) {
                throw new RpcException("Unsupported client type: " + str);
            }
        }

        return server;
    }

    private void optimizeSerialization(URL url) throws RpcException {
        String className = url.getParameter(OPTIMIZER_KEY, "");
        if (StringUtils.isEmpty(className) || optimizers.contains(className)) {
            return;
        }

        logger.info("Optimizing the serialization process for Kryo, FST, etc...");

        try {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            if (!SerializationOptimizer.class.isAssignableFrom(clazz)) {
                throw new RpcException("The serialization optimizer " + className + " isn't an instance of " + SerializationOptimizer.class.getName());
            }

            SerializationOptimizer optimizer = (SerializationOptimizer) clazz.newInstance();

            if (optimizer.getSerializableClasses() == null) {
                return;
            }

            for (Class c : optimizer.getSerializableClasses()) {
                SerializableClassRegistry.registerClass(c);
            }

            // 特殊的一些序列化机制，比如kryo提供了注册机制来注册类，提高序列化和反序列化的速度
            optimizers.add(className);

        } catch (ClassNotFoundException e) {
            throw new RpcException("Cannot find the serialization optimizer class: " + className, e);

        } catch (InstantiationException e) {
            throw new RpcException("Cannot instantiate the serialization optimizer class: " + className, e);

        } catch (IllegalAccessException e) {
            throw new RpcException("Cannot instantiate the serialization optimizer class: " + className, e);
        }
    }

    @Override
    public <T> Invoker<T> protocolBindingRefer(Class<T> serviceType, URL url) throws RpcException {

        optimizeSerialization(url);

        // create rpc invoker.
        // clients很重要，为什么一个DubboInvoker会有多个clients，为了提高效率，因为每个client和server之间都会有一个socket, 多个client连的是同一个server
        // 在DubboInvoker发送请求时会轮询clients去发送数据
        DubboInvoker<T> invoker = new DubboInvoker<T>(serviceType, url, getClients(url), invokers);
        invokers.add(invoker);

        return invoker;
    }

    private ExchangeClient[] getClients(URL url) {
        // whether to share connection
        // 获取client    new NettyClient()  socket
        // DUbboInvoker.invoke 3NettyClient

        boolean useShareConnect = false;

        // connections表示对当前服务提供者建立connections个socket连接
        // 消费者应用引用了两个服务A和B，这两个服务都部署在了应用C上，如果connections为2，那么消费者应用会与应用C建立4个Socket连接
        int connections = url.getParameter(CONNECTIONS_KEY, 0);

        List<ReferenceCountExchangeClient> shareClients = null;

        // if not configured, connection is shared, otherwise, one connection for one service
        // 如果没有配置connections，那么则取shareConnectionsStr（默认为1），表示共享socket连接个数
        // 消费者应用引用了两个服务A和B，这两个服务都部署在了应用C上，如果shareConnectionsStr为2，那么消费者应用会与应用C建立2个Socket连接
        if (connections == 0) {
            useShareConnect = true;

            /**
             * The xml configuration should have a higher priority than properties.
             */
            String shareConnectionsStr = url.getParameter(SHARE_CONNECTIONS_KEY, (String) null);
            connections = Integer.parseInt(StringUtils.isBlank(shareConnectionsStr) ? ConfigUtils.getProperty(SHARE_CONNECTIONS_KEY,
                    DEFAULT_SHARE_CONNECTIONS) : shareConnectionsStr);
            shareClients = getSharedClient(url, connections);
        }

        ExchangeClient[] clients = new ExchangeClient[connections];
        for (int i = 0; i < clients.length; i++) {
            // 如果使用共享的，则利用shareClients
            if (useShareConnect) {
                clients[i] = shareClients.get(i);

            } else {
                // 不然就初始化，在初始化client时会去连接服务端
                clients[i] = initClient(url);
            }
        }

        return clients;
    }

    /**
     * Get shared connection
     *
     * @param url
     * @param connectNum connectNum must be greater than or equal to 1
     */
    private List<ReferenceCountExchangeClient> getSharedClient(URL url, int connectNum) {
        // 这个方法返回的是可以共享的client，要么已经生成过了，要么需要重新生成
        // 对于已经生成过的client,都会存在referenceClientMap中，key为所调用的服务IP+PORT

        String key = url.getAddress();
        List<ReferenceCountExchangeClient> clients = referenceClientMap.get(key);

        // 根据当前引入的服务对应的ip+port，看看是否已经存在clients了，
        if (checkClientCanUse(clients)) {
            // 如果每个client都可用，那就对每个client的计数+1，表示这些client被引用了多少次
            batchClientRefIncr(clients);
            return clients;
        }

        locks.putIfAbsent(key, new Object());
        synchronized (locks.get(key)) {
            clients = referenceClientMap.get(key);
            // dubbo check
            if (checkClientCanUse(clients)) {
                batchClientRefIncr(clients);
                return clients;
            }

            // connectNum must be greater than or equal to 1
            // 至少一个
            connectNum = Math.max(connectNum, 1);

            // If the clients is empty, then the first initialization is
            if (CollectionUtils.isEmpty(clients)) {
                // 如果clients为空，则按指定的connectNum生成client
                clients = buildReferenceCountExchangeClientList(url, connectNum);
                referenceClientMap.put(key, clients);
            } else {
                // 如果clients不为空，则遍历这些client，对于不可用的client，则重新生成一个client
                for (int i = 0; i < clients.size(); i++) {
                    ReferenceCountExchangeClient referenceCountExchangeClient = clients.get(i);
                    // If there is a client in the list that is no longer available, create a new one to replace him.
                    if (referenceCountExchangeClient == null || referenceCountExchangeClient.isClosed()) {
                        clients.set(i, buildReferenceCountExchangeClient(url));
                        continue;
                    }

                    referenceCountExchangeClient.incrementAndGetCount();
                }
            }

            /**
             * I understand that the purpose of the remove operation here is to avoid the expired url key
             * always occupying this memory space.
             */
            locks.remove(key);

            return clients;
        }
    }

    /**
     * Check if the client list is all available
     *
     * @param referenceCountExchangeClients
     * @return true-available，false-unavailable
     */
    private boolean checkClientCanUse(List<ReferenceCountExchangeClient> referenceCountExchangeClients) {
        if (CollectionUtils.isEmpty(referenceCountExchangeClients)) {
            return false;
        }

        for (ReferenceCountExchangeClient referenceCountExchangeClient : referenceCountExchangeClients) {
            // As long as one client is not available, you need to replace the unavailable client with the available one.
            if (referenceCountExchangeClient == null || referenceCountExchangeClient.isClosed()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Increase the reference Count if we create new invoker shares same connection, the connection will be closed without any reference.
     *
     * @param referenceCountExchangeClients
     */
    private void batchClientRefIncr(List<ReferenceCountExchangeClient> referenceCountExchangeClients) {
        if (CollectionUtils.isEmpty(referenceCountExchangeClients)) {
            return;
        }

        for (ReferenceCountExchangeClient referenceCountExchangeClient : referenceCountExchangeClients) {
            if (referenceCountExchangeClient != null) {
                referenceCountExchangeClient.incrementAndGetCount();
            }
        }
    }

    /**
     * Bulk build client
     *
     * @param url
     * @param connectNum
     * @return
     */
    private List<ReferenceCountExchangeClient> buildReferenceCountExchangeClientList(URL url, int connectNum) {
        List<ReferenceCountExchangeClient> clients = new ArrayList<>();

        for (int i = 0; i < connectNum; i++) {
            clients.add(buildReferenceCountExchangeClient(url));
        }

        return clients;
    }

    /**
     * Build a single client
     *
     * @param url
     * @return
     */
    private ReferenceCountExchangeClient buildReferenceCountExchangeClient(URL url) {
        // 生成一个ExchangeClient
        ExchangeClient exchangeClient = initClient(url);

        // 包装成ReferenceCountExchangeClient
        return new ReferenceCountExchangeClient(exchangeClient);
    }

    /**
     * Create new connection
     *
     * @param url
     */
    private ExchangeClient initClient(URL url) {

        // client type setting.
        // 拿设置的client，默认为netty
        String str = url.getParameter(CLIENT_KEY, url.getParameter(SERVER_KEY, DEFAULT_REMOTING_CLIENT));

        // 编码方式
        url = url.addParameter(CODEC_KEY, DubboCodec.NAME);

        // enable heartbeat by default
        // 心跳， 默认60 * 1000,60秒一个心跳
        url = url.addParameterIfAbsent(HEARTBEAT_KEY, String.valueOf(DEFAULT_HEARTBEAT));

        // BIO is not allowed since it has severe performance issue.
        // 如果没有指定的client扩展，则抛异常
        if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)) {
            throw new RpcException("Unsupported client type: " + str + "," +
                    " supported client type is " + StringUtils.join(ExtensionLoader.getExtensionLoader(Transporter.class).getSupportedExtensions(), " "));
        }

        ExchangeClient client;
        try {
            // connection should be lazy
            if (url.getParameter(LAZY_CONNECT_KEY, false)) {
                client = new LazyConnectExchangeClient(url, requestHandler);

            } else {
                // client是在refer的时候生成的，这个时候就已经建立好连接了？
                // 答案是就是会去建立连接，也是能够理解了，只有连接建立好了才有client和server之分
                // 先建立连接，在调用方法时再基于这个连接去发送数据
                client = Exchangers.connect(url, requestHandler);  // connect
            }

        } catch (RemotingException e) {
            throw new RpcException("Fail to create remoting client for service(" + url + "): " + e.getMessage(), e);
        }

        return client;
    }

    @Override
    public void destroy() {
        for (String key : new ArrayList<>(serverMap.keySet())) {
            ExchangeServer server = serverMap.remove(key);

            if (server == null) {
                continue;
            }

            try {
                if (logger.isInfoEnabled()) {
                    logger.info("Close dubbo server: " + server.getLocalAddress());
                }

                server.close(ConfigurationUtils.getServerShutdownTimeout());

            } catch (Throwable t) {
                logger.warn(t.getMessage(), t);
            }
        }

        for (String key : new ArrayList<>(referenceClientMap.keySet())) {
            List<ReferenceCountExchangeClient> clients = referenceClientMap.remove(key);

            if (CollectionUtils.isEmpty(clients)) {
                continue;
            }

            for (ReferenceCountExchangeClient client : clients) {
                closeReferenceCountExchangeClient(client);
            }
        }

        stubServiceMethodsMap.clear();
        super.destroy();
    }

    /**
     * close ReferenceCountExchangeClient
     *
     * @param client
     */
    private void closeReferenceCountExchangeClient(ReferenceCountExchangeClient client) {
        if (client == null) {
            return;
        }

        try {
            if (logger.isInfoEnabled()) {
                logger.info("Close dubbo connect: " + client.getLocalAddress() + "-->" + client.getRemoteAddress());
            }

            client.close(ConfigurationUtils.getServerShutdownTimeout());

            // TODO
            /**
             * At this time, ReferenceCountExchangeClient#client has been replaced with LazyConnectExchangeClient.
             * Do you need to call client.close again to ensure that LazyConnectExchangeClient is also closed?
             */

        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
    }
}
