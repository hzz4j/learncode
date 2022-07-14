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
package org.apache.dubbo.config;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.URLBuilder;
import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.bytecode.Wrapper;
import org.apache.dubbo.common.config.Environment;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.invoker.DelegateProviderMetaDataInvoker;
import org.apache.dubbo.config.support.Parameter;
import org.apache.dubbo.metadata.integration.MetadataReportService;
import org.apache.dubbo.remoting.Constants;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.cluster.ConfiguratorFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ProviderModel;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.rpc.support.ProtocolUtils;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.apache.dubbo.common.constants.CommonConstants.ANYHOST_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.ANY_VALUE;
import static org.apache.dubbo.common.constants.CommonConstants.COMMA_SPLIT_PATTERN;
import static org.apache.dubbo.common.constants.CommonConstants.DUBBO;
import static org.apache.dubbo.common.constants.CommonConstants.DUBBO_IP_TO_BIND;
import static org.apache.dubbo.common.constants.CommonConstants.LOCALHOST_VALUE;
import static org.apache.dubbo.common.constants.CommonConstants.METHODS_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.MONITOR_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER_SIDE;
import static org.apache.dubbo.common.constants.CommonConstants.REVISION_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.SIDE_KEY;
import static org.apache.dubbo.common.constants.RegistryConstants.DYNAMIC_KEY;
import static org.apache.dubbo.common.utils.NetUtils.getAvailablePort;
import static org.apache.dubbo.common.utils.NetUtils.getLocalHost;
import static org.apache.dubbo.common.utils.NetUtils.isInvalidLocalHost;
import static org.apache.dubbo.common.utils.NetUtils.isInvalidPort;
import static org.apache.dubbo.config.Constants.DUBBO_IP_TO_REGISTRY;
import static org.apache.dubbo.config.Constants.DUBBO_PORT_TO_BIND;
import static org.apache.dubbo.config.Constants.DUBBO_PORT_TO_REGISTRY;
import static org.apache.dubbo.config.Constants.MULTICAST;
import static org.apache.dubbo.config.Constants.PROTOCOLS_SUFFIX;
import static org.apache.dubbo.config.Constants.REGISTER_KEY;
import static org.apache.dubbo.config.Constants.SCOPE_NONE;
import static org.apache.dubbo.rpc.Constants.GENERIC_KEY;
import static org.apache.dubbo.rpc.Constants.LOCAL_PROTOCOL;
import static org.apache.dubbo.rpc.Constants.PROXY_KEY;
import static org.apache.dubbo.rpc.Constants.SCOPE_KEY;
import static org.apache.dubbo.rpc.Constants.SCOPE_LOCAL;
import static org.apache.dubbo.rpc.Constants.SCOPE_REMOTE;
import static org.apache.dubbo.rpc.Constants.TOKEN_KEY;
import static org.apache.dubbo.rpc.cluster.Constants.EXPORT_KEY;

/**
 * ServiceConfig
 *
 * @export
 */
public class ServiceConfig<T> extends AbstractServiceConfig {

    private static final long serialVersionUID = 3033787999037024738L;

    /**
     * The {@link Protocol} implementation with adaptive functionality,it will be different in different scenarios.
     * A particular {@link Protocol} implementation is determined by the protocol attribute in the {@link URL}.
     * For example:
     *
     * <li>when the url is registry://224.5.6.7:1234/org.apache.dubbo.registry.RegistryService?application=dubbo-sample,
     * then the protocol is <b>RegistryProtocol</b></li>
     *
     * <li>when the url is dubbo://224.5.6.7:1234/org.apache.dubbo.config.api.DemoService?application=dubbo-sample, then
     * the protocol is <b>DubboProtocol</b></li>
     * <p>
     * Actually，when the {@link ExtensionLoader} init the {@link Protocol} instants,it will automatically wraps two
     * layers, and eventually will get a <b>ProtocolFilterWrapper</b> or <b>ProtocolListenerWrapper</b>
     */
    private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    /**
     * A {@link ProxyFactory} implementation that will generate a exported service proxy,the JavassistProxyFactory is its
     * default implementation
     */
    private static final ProxyFactory PROXY_FACTORY = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    /**
     * A random port cache, the different protocols who has no port specified have different random port
     */
    private static final Map<String, Integer> RANDOM_PORT_MAP = new HashMap<String, Integer>();

    /**
     * A delayed exposure service timer
     */
    private static final ScheduledExecutorService DELAY_EXPORT_EXECUTOR = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("DubboServiceDelayExporter", true));

    /**
     * The urls of the services exported
     */
    private final List<URL> urls = new ArrayList<URL>();

    /**
     * The exported services
     */
    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    /**
     * The interface name of the exported service
     */
    private String interfaceName;

    /**
     * The interface class of the exported service
     */
    private Class<?> interfaceClass;

    /**
     * The reference of the interface implementation
     */
    private T ref;

    /**
     * The service name
     */
    private String path;

    /**
     * The method configuration
     */
    private List<MethodConfig> methods;

    /**
     * The provider configuration
     */
    private ProviderConfig provider;

    /**
     * The providerIds
     */
    private String providerIds;

    /**
     * Whether the provider has been exported
     */
    private transient volatile boolean exported;

    /**
     * The flag whether a service has unexported ,if the method unexported is invoked, the value is true
     */
    private transient volatile boolean unexported;

    /**
     * whether it is a GenericService
     */
    private volatile String generic;

    public ServiceConfig() {
    }

    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
        setMethods(MethodConfig.constructMethodConfig(service.methods()));
    }

    @Deprecated
    private static List<ProtocolConfig> convertProviderToProtocol(List<ProviderConfig> providers) {
        if (CollectionUtils.isEmpty(providers)) {
            return null;
        }
        List<ProtocolConfig> protocols = new ArrayList<ProtocolConfig>(providers.size());
        for (ProviderConfig provider : providers) {
            protocols.add(convertProviderToProtocol(provider));
        }
        return protocols;
    }

    @Deprecated
    private static List<ProviderConfig> convertProtocolToProvider(List<ProtocolConfig> protocols) {
        if (CollectionUtils.isEmpty(protocols)) {
            return null;
        }
        List<ProviderConfig> providers = new ArrayList<ProviderConfig>(protocols.size());
        for (ProtocolConfig provider : protocols) {
            providers.add(convertProtocolToProvider(provider));
        }
        return providers;
    }

    @Deprecated
    private static ProtocolConfig convertProviderToProtocol(ProviderConfig provider) {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(provider.getProtocol().getName());
        protocol.setServer(provider.getServer());
        protocol.setClient(provider.getClient());
        protocol.setCodec(provider.getCodec());
        protocol.setHost(provider.getHost());
        protocol.setPort(provider.getPort());
        protocol.setPath(provider.getPath());
        protocol.setPayload(provider.getPayload());
        protocol.setThreads(provider.getThreads());
        protocol.setParameters(provider.getParameters());
        return protocol;
    }

    @Deprecated
    private static ProviderConfig convertProtocolToProvider(ProtocolConfig protocol) {
        ProviderConfig provider = new ProviderConfig();
        provider.setProtocol(protocol);
        provider.setServer(protocol.getServer());
        provider.setClient(protocol.getClient());
        provider.setCodec(protocol.getCodec());
        provider.setHost(protocol.getHost());
        provider.setPort(protocol.getPort());
        provider.setPath(protocol.getPath());
        provider.setPayload(protocol.getPayload());
        provider.setThreads(protocol.getThreads());
        provider.setParameters(protocol.getParameters());
        return provider;
    }

    private static Integer getRandomPort(String protocol) {
        protocol = protocol.toLowerCase();
        return RANDOM_PORT_MAP.getOrDefault(protocol, Integer.MIN_VALUE);
    }

    private static void putRandomPort(String protocol, Integer port) {
        protocol = protocol.toLowerCase();
        if (!RANDOM_PORT_MAP.containsKey(protocol)) {
            RANDOM_PORT_MAP.put(protocol, port);
            logger.warn("Use random available port(" + port + ") for protocol " + protocol);
        }
    }

    public URL toUrl() {
        return urls.isEmpty() ? null : urls.iterator().next();
    }

    public List<URL> toUrls() {
        return urls;
    }

    @Parameter(excluded = true)
    public boolean isExported() {
        return exported;
    }

    @Parameter(excluded = true)
    public boolean isUnexported() {
        return unexported;
    }

    public void checkAndUpdateSubConfigs() {
        // Use default configs defined explicitly on global configs
        // ServiceConfig中的某些属性如果是空的，那么就从ProviderConfig、ModuleConfig、ApplicationConfig中获取
        // 补全ServiceConfig中的属性
        completeCompoundConfigs();

        // Config Center should always being started first.
        // 从配置中心获取配置，包括应用配置和全局配置
        // 把获取到的配置放入到Environment中的externalConfigurationMap和appExternalConfigurationMap中
        // 并刷新所有的XxConfig的属性（除开ServiceConfig），刷新的意思就是将配置中心的配置覆盖调用XxConfig中的属性
        startConfigCenter();

        checkDefault();

        checkProtocol();

        checkApplication();

        // if protocol is not injvm checkRegistry
        // 如果protocol不是只有injvm协议，表示服务调用不是只在本机jvm里面调用，那就需要用到注册中心
        if (!isOnlyInJvm()) {
            checkRegistry();
        }

        // 刷新ServiceConfig
        this.refresh();

        // 如果配了metadataReportConfig，那么就刷新配置
        checkMetadataReport();

        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("<dubbo:service interface=\"\" /> interface not allow null!");
        }

        // 当前服务对应的实现类是一个GenericService，表示没有特定的接口
        if (ref instanceof GenericService) {
            interfaceClass = GenericService.class;
            if (StringUtils.isEmpty(generic)) {
                generic = Boolean.TRUE.toString();
            }
        } else {
            // 加载接口
            try {
                interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                        .getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            // 刷新MethodConfig，并判断MethodConfig中对应的方法在接口中是否存在
            checkInterfaceAndMethods(interfaceClass, methods);
            // 实现类是不是该接口类型
            checkRef();
            generic = Boolean.FALSE.toString();
        }
        // local和stub一样，不建议使用了
        if (local != null) {
            // 如果本地存根为true，则存根类为interfaceName + "Local"
            if (Boolean.TRUE.toString().equals(local)) {
                local = interfaceName + "Local";
            }
            // 加载本地存根类
            Class<?> localClass;
            try {
                localClass = ClassUtils.forNameWithThreadContextClassLoader(local);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            if (!interfaceClass.isAssignableFrom(localClass)) {
                throw new IllegalStateException("The local implementation class " + localClass.getName() + " not implement interface " + interfaceName);
            }
        }
        // 本地存根
        if (stub != null) {
            // 如果本地存根为true，则存根类为interfaceName + "Stub"
            if (Boolean.TRUE.toString().equals(stub)) {
                stub = interfaceName + "Stub";
            }
            Class<?> stubClass;
            try {
                stubClass = ClassUtils.forNameWithThreadContextClassLoader(stub);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            if (!interfaceClass.isAssignableFrom(stubClass)) {
                throw new IllegalStateException("The stub implementation class " + stubClass.getName() + " not implement interface " + interfaceName);
            }
        }
        // 检查local和stub
        checkStubAndLocal(interfaceClass);
        // 检查mock
        checkMock(interfaceClass);
    }

    /**
     * Determine if it is injvm
     *
     * @return
     */
    private boolean isOnlyInJvm() {
        return getProtocols().size() == 1 && LOCAL_PROTOCOL.equalsIgnoreCase(getProtocols().get(0).getName());
    }

    public synchronized void export() {
        checkAndUpdateSubConfigs();

        // 检查服务是否需要导出
        if (!shouldExport()) {
            return;
        }

        // 检查是否需要延迟发布
        if (shouldDelay()) {
            DELAY_EXPORT_EXECUTOR.schedule(this::doExport, getDelay(), TimeUnit.MILLISECONDS);
        } else {
            // 导出服务
            doExport();
        }
    }

    private boolean shouldExport() {
        Boolean export = getExport();
        // default value is true
        return export == null ? true : export;
    }

    @Override
    public Boolean getExport() {
        return (export == null && provider != null) ? provider.getExport() : export;
    }

    private boolean shouldDelay() {
        Integer delay = getDelay();
        return delay != null && delay > 0;
    }

    @Override
    public Integer getDelay() {
        return (delay == null && provider != null) ? provider.getDelay() : delay;
    }

    protected synchronized void doExport() {
        if (unexported) {
            throw new IllegalStateException("The service " + interfaceClass.getName() + " has already unexported!");
        }
        // 已经导出了，就不再导出了
        if (exported) {
            return;
        }
        exported = true;

        if (StringUtils.isEmpty(path)) {
            path = interfaceName;
        }
        doExportUrls();
    }

    private void checkRef() {
        // reference should not be null, and is the implementation of the given interface
        if (ref == null) {
            throw new IllegalStateException("ref not allow null!");
        }
        if (!interfaceClass.isInstance(ref)) {
            throw new IllegalStateException("The class "
                    + ref.getClass().getName() + " unimplemented interface "
                    + interfaceClass + "!");
        }
    }

    public synchronized void unexport() {
        if (!exported) {
            return;
        }
        if (unexported) {
            return;
        }
        if (!exporters.isEmpty()) {
            for (Exporter<?> exporter : exporters) {
                try {
                    exporter.unexport();
                } catch (Throwable t) {
                    logger.warn("Unexpected error occured when unexport " + exporter, t);
                }
            }
            exporters.clear();
        }
        unexported = true;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void doExportUrls() {
        // registryURL 表示一个注册中心
        List<URL> registryURLs = loadRegistries(true);

        for (ProtocolConfig protocolConfig : protocols) {

            // pathKey = group/contextpath/path:version
            // 例子：myGroup/user/org.apache.dubbo.demo.DemoService:1.0.1
            String pathKey = URL.buildKey(getContextPath(protocolConfig).map(p -> p + "/" + path).orElse(path), group, version);

            // ProviderModel中存在服务提供者访问路径，实现类，接口，以及接口中的各个方法对应的ProviderMethodModel
            // ProviderMethodModel表示某一个方法，方法名，所属的服务的，
            ProviderModel providerModel = new ProviderModel(pathKey, ref, interfaceClass);

            // ApplicationModel表示应用中有哪些服务提供者和引用了哪些服务
            ApplicationModel.initProviderModel(pathKey, providerModel);

            // 重点
            doExportUrlsFor1Protocol(protocolConfig, registryURLs);
        }
    }

    private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
        // protocolConfig表示某个协议，registryURLs表示所有的注册中心

        // 如果配置的某个协议，没有配置name，那么默认为dubbo
        String name = protocolConfig.getName();
        if (StringUtils.isEmpty(name)) {
            name = DUBBO;
        }

        // 这个map表示服务url的参数
        Map<String, String> map = new HashMap<String, String>();
        map.put(SIDE_KEY, PROVIDER_SIDE);

        appendRuntimeParameters(map);

        // 监控中心参数
        appendParameters(map, metrics);
        // 应用相关参数
        appendParameters(map, application);
        // 模块相关参数
        appendParameters(map, module);
        // remove 'default.' prefix for configs from ProviderConfig
        // appendParameters(map, provider, Constants.DEFAULT_KEY);

        // 提供者相关参数
        appendParameters(map, provider);

        // 协议相关参数
        appendParameters(map, protocolConfig);

        // 服务本身相关参数
        appendParameters(map, this);

        // 服务中某些方法参数
        if (CollectionUtils.isNotEmpty(methods)) {
            for (MethodConfig method : methods) {
                // 某个方法的配置参数，注意有prefix
                appendParameters(map, method, method.getName());
                String retryKey = method.getName() + ".retry";

                // 如果某个方法配置存在xx.retry=false，则改成xx.retry=0
                if (map.containsKey(retryKey)) {
                    String retryValue = map.remove(retryKey);
                    if (Boolean.FALSE.toString().equals(retryValue)) {
                        map.put(method.getName() + ".retries", "0");
                    }
                }
                List<ArgumentConfig> arguments = method.getArguments();
                if (CollectionUtils.isNotEmpty(arguments)) {
                    // 遍历当前方法配置中的参数配置
                    for (ArgumentConfig argument : arguments) {

                        // 如果配置了type，则遍历当前接口的所有方法，然后找到方法名和当前方法名相等的方法，可能存在多个
                        // 如果配置了index,则看index对应位置的参数类型是否等于type,如果相等，则向map中存入argument对象中的参数
                        // 如果没有配置index，那么则遍历方法所有的参数类型，等于type则向map中存入argument对象中的参数
                        // 如果没有配置type,但配置了index,则把对应位置的argument放入map
                        // convert argument type
                        if (argument.getType() != null && argument.getType().length() > 0) {
                            Method[] methods = interfaceClass.getMethods();
                            // visit all methods
                            if (methods != null && methods.length > 0) {
                                for (int i = 0; i < methods.length; i++) {
                                    String methodName = methods[i].getName();
                                    // target the method, and get its signature
                                    if (methodName.equals(method.getName())) {
                                        Class<?>[] argtypes = methods[i].getParameterTypes();
                                        // one callback in the method
                                        if (argument.getIndex() != -1) {
                                            if (argtypes[argument.getIndex()].getName().equals(argument.getType())) {
                                                appendParameters(map, argument, method.getName() + "." + argument.getIndex());
                                            } else {
                                                throw new IllegalArgumentException("Argument config error : the index attribute and type attribute not match :index :" + argument.getIndex() + ", type:" + argument.getType());
                                            }
                                        } else {
                                            // multiple callbacks in the method
                                            for (int j = 0; j < argtypes.length; j++) {
                                                Class<?> argclazz = argtypes[j];
                                                if (argclazz.getName().equals(argument.getType())) {
                                                    appendParameters(map, argument, method.getName() + "." + j);
                                                    if (argument.getIndex() != -1 && argument.getIndex() != j) {
                                                        throw new IllegalArgumentException("Argument config error : the index attribute and type attribute not match :index :" + argument.getIndex() + ", type:" + argument.getType());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (argument.getIndex() != -1) {
                            appendParameters(map, argument, method.getName() + "." + argument.getIndex());
                        } else {
                            throw new IllegalArgumentException("Argument config must set index or type attribute.eg: <dubbo:argument index='0' .../> or <dubbo:argument type=xxx .../>");
                        }

                    }
                }
            } // end of methods for
        }

        if (ProtocolUtils.isGeneric(generic)) {
            map.put(GENERIC_KEY, generic);
            map.put(METHODS_KEY, ANY_VALUE);
        } else {
            String revision = Version.getVersion(interfaceClass, version);
            if (revision != null && revision.length() > 0) {
                map.put(REVISION_KEY, revision);
            }

            // 通过接口对应的Wrapper，拿到接口中所有的方法名字
            String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
            if (methods.length == 0) {
                logger.warn("No method found in service interface " + interfaceClass.getName());
                map.put(METHODS_KEY, ANY_VALUE);
            } else {
                map.put(METHODS_KEY, StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
            }
        }

        // Token是为了防止服务被消费者直接调用（伪造http请求）
        if (!ConfigUtils.isEmpty(token)) {
            if (ConfigUtils.isDefault(token)) {
                map.put(TOKEN_KEY, UUID.randomUUID().toString());
            } else {
                map.put(TOKEN_KEY, token);
            }
        }

        // export service
        // 通过该host和port访问该服务
        String host = this.findConfigedHosts(protocolConfig, registryURLs, map);
        Integer port = this.findConfigedPorts(protocolConfig, name, map);
        // 服务url
        URL url = new URL(name, host, port, getContextPath(protocolConfig).map(p -> p + "/" + path).orElse(path), map);
        // url：http://192.168.40.17:80/org.apache.dubbo.demo.DemoService?anyhost=true&application=dubbo-demo-annotation-provider&bean.name=ServiceBean:org.apache.dubbo.demo.DemoService&bind.ip=192.168.40.17&bind.port=80&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=org.apache.dubbo.demo.DemoService&methods=sayHello&pid=285072&release=&side=provider&timestamp=1585206500409

        // 可以通过ConfiguratorFactory，对服务url再次进行配置
        if (ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
                .hasExtension(url.getProtocol())) {
            url = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
                    .getExtension(url.getProtocol()).getConfigurator(url).configure(url);
        }

        String scope = url.getParameter(SCOPE_KEY); // scope可能为null，remote, local,none
        // don't export when none is configured
        if (!SCOPE_NONE.equalsIgnoreCase(scope)) {
            // 如果scope为none,则不会进行任何的服务导出，既不会远程，也不会本地

            // export to local if the config is not remote (export to remote only when config is remote)
            if (!SCOPE_REMOTE.equalsIgnoreCase(scope)) {
                // 如果scope不是remote,则会进行本地导出，会把当前url的protocol改为injvm，然后进行导出
                exportLocal(url);
            }
            // export to remote if the config is not local (export to local only when config is local)
            if (!SCOPE_LOCAL.equalsIgnoreCase(scope)) {
                // 如果scope不是local,则会进行远程导出

                if (CollectionUtils.isNotEmpty(registryURLs)) {
                    // 如果有注册中心，则将服务注册到注册中心
                    for (URL registryURL : registryURLs) {

                        //if protocol is only injvm ,not register
                        // 如果是injvm，则不需要进行注册中心注册
                        if (LOCAL_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
                            continue;
                        }

                        // 该服务是否是动态，对应zookeeper上表示是否是临时节点，对应dubbo中的功能就是静态服务
                        url = url.addParameterIfAbsent(DYNAMIC_KEY, registryURL.getParameter(DYNAMIC_KEY));

                        // 拿到监控中心地址
                        URL monitorUrl = loadMonitor(registryURL);

                        // 当前服务连接哪个监控中心
                        if (monitorUrl != null) {
                            url = url.addParameterAndEncoded(MONITOR_KEY, monitorUrl.toFullString());
                        }

                        // 服务的register参数，如果为true，则表示要注册到注册中心
                        if (logger.isInfoEnabled()) {
                            if (url.getParameter(REGISTER_KEY, true)) {
                                logger.info("Register dubbo service " + interfaceClass.getName() + " url " + url + " to registry " + registryURL);
                            } else {
                                logger.info("Export dubbo service " + interfaceClass.getName() + " to url " + url);
                            }
                        }

                        // For providers, this is used to enable custom proxy to generate invoker
                        // 服务使用的动态代理机制，如果为空则使用javassit
                        String proxy = url.getParameter(PROXY_KEY);
                        if (StringUtils.isNotEmpty(proxy)) {
                            registryURL = registryURL.addParameter(PROXY_KEY, proxy);
                        }

                        // 生成一个当前服务接口的代理对象
                        // 使用代理生成一个Invoker，Invoker表示服务提供者的代理，可以使用Invoker的invoke方法执行服务
                        // 对应的url为 registry://127.0.0.1:2181/org.apache.dubbo.registry.RegistryService?application=dubbo-demo-annotation-provider&dubbo=2.0.2&export=http%3A%2F%2F192.168.40.17%3A80%2Forg.apache.dubbo.demo.DemoService%3Fanyhost%3Dtrue%26application%3Ddubbo-demo-annotation-provider%26bean.name%3DServiceBean%3Aorg.apache.dubbo.demo.DemoService%26bind.ip%3D192.168.40.17%26bind.port%3D80%26deprecated%3Dfalse%26dubbo%3D2.0.2%26dynamic%3Dtrue%26generic%3Dfalse%26interface%3Dorg.apache.dubbo.demo.DemoService%26methods%3DsayHello%26pid%3D19472%26release%3D%26side%3Dprovider%26timestamp%3D1585207994860&pid=19472&registry=zookeeper&timestamp=1585207994828
                        // 这个Invoker中包括了服务的实现者、服务接口类、服务的注册地址（针对当前服务的，参数export指定了当前服务）
                        // 此invoker表示一个可执行的服务，调用invoker的invoke()方法即可执行服务,同时此invoker也可用来导出
                        Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(EXPORT_KEY, url.toFullString()));
                        // invoker.invoke(Invocation)

                        // DelegateProviderMetaDataInvoker也表示服务提供者，包括了Invoker和服务的配置
                        DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);

                        // 使用特定的协议来对服务进行导出，这里的协议为RegistryProtocol，导出成功后得到一个Exporter
                        // 1. 先使用RegistryProtocol进行服务注册
                        // 2. 注册完了之后，使用DubboProtocol进行导出
                        // 到此为止做了哪些事情？ ServiceBean.export()-->刷新ServiceBean的参数-->得到注册中心URL和协议URL-->遍历每个协议URL-->组成服务URL-->生成可执行服务Invoker-->导出服务
                        Exporter<?> exporter = protocol.export(wrapperInvoker);
                        exporters.add(exporter);
                    }
                } else {
                    // 没有配置注册中心时，也会导出服务

                    if (logger.isInfoEnabled()) {
                        logger.info("Export dubbo service " + interfaceClass.getName() + " to url " + url);
                    }


                    Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, url);
                    DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);

                    Exporter<?> exporter = protocol.export(wrapperInvoker);
                    exporters.add(exporter);
                }


                /**
                 * @since 2.7.0
                 * ServiceData Store
                 */
                // 根据服务url，讲服务的元信息存入元数据中心
                MetadataReportService metadataReportService = null;
                if ((metadataReportService = getMetadataReportService()) != null) {
                    metadataReportService.publishProvider(url);
                }
            }
        }
        this.urls.add(url);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    /**
     * always export injvm
     */
    private void exportLocal(URL url) {
        URL local = URLBuilder.from(url)
                .setProtocol(LOCAL_PROTOCOL)
                .setHost(LOCALHOST_VALUE)
                .setPort(0)
                .build();
        Exporter<?> exporter = protocol.export(
                PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, local));
        exporters.add(exporter);
        logger.info("Export dubbo service " + interfaceClass.getName() + " to local registry url : " + local);
    }

    private Optional<String> getContextPath(ProtocolConfig protocolConfig) {
        String contextPath = protocolConfig.getContextpath();
        if (StringUtils.isEmpty(contextPath) && provider != null) {
            contextPath = provider.getContextpath();
        }
        return Optional.ofNullable(contextPath);
    }

    protected Class getServiceClass(T ref) {
        return ref.getClass();
    }

    /**
     * Register & bind IP address for service provider, can be configured separately.
     * Configuration priority: environment variables -> java system properties -> host property in config file ->
     * /etc/hosts -> default network address -> first available network address
     * 为服务提供者绑定ip地址，比如启动Tomcat时可以绑定到特定的ip
     *
     * @param protocolConfig
     * @param registryURLs
     * @param map
     * @return
     */
    private String findConfigedHosts(ProtocolConfig protocolConfig, List<URL> registryURLs, Map<String, String> map) {
        boolean anyhost = false;

        // 从系统环境变量中获取指定的ip进行绑定
        String hostToBind = getValueFromConfig(protocolConfig, DUBBO_IP_TO_BIND);
        if (hostToBind != null && hostToBind.length() > 0 && isInvalidLocalHost(hostToBind)) {
            throw new IllegalArgumentException("Specified invalid bind ip from property:" + DUBBO_IP_TO_BIND + ", value:" + hostToBind);
        }

        // if bind ip is not found in environment, keep looking up
        if (StringUtils.isEmpty(hostToBind)) {
            // 获取protocolConfig中指定的host
            hostToBind = protocolConfig.getHost();
            if (provider != null && StringUtils.isEmpty(hostToBind)) {
                hostToBind = provider.getHost();
            }
            // 如果是无效的ip，localhost, 0.0.0.0都属于无效，localhost表示只有本机能访问，0.0.0.0表示任何主机都能访问
            if (isInvalidLocalHost(hostToBind)) {
                anyhost = true;
                try {
                    logger.info( "No valid ip found from environment, try to find valid host from DNS.");
                    // 拿到本机网卡中ip地址
                    hostToBind = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    logger.warn(e.getMessage(), e);
                }

                // 如果还是不合法的
                if (isInvalidLocalHost(hostToBind)) {
                    if (CollectionUtils.isNotEmpty(registryURLs)) {
                        for (URL registryURL : registryURLs) {
                            // 如果注册中心是MULTICAST，
                            if (MULTICAST.equalsIgnoreCase(registryURL.getParameter("registry"))) {
                                // skip multicast registry since we cannot connect to it via Socket
                                continue;
                            }
                            try (Socket socket = new Socket()) {
                                // 通过一个socket去连注册中心，为什么要这么做？连了才能拿到本机ip
                                SocketAddress addr = new InetSocketAddress(registryURL.getHost(), registryURL.getPort());
                                socket.connect(addr, 1000);
                                hostToBind = socket.getLocalAddress().getHostAddress();
                                break;
                            } catch (Exception e) {
                                logger.warn(e.getMessage(), e);
                            }
                        }
                    }
                    // 如果还不合法，那就去127.0.0.1
                    if (isInvalidLocalHost(hostToBind)) {
                        hostToBind = getLocalHost();
                    }
                }
            }
        }

        map.put(Constants.BIND_IP_KEY, hostToBind);

        // registry ip is not used for bind ip by default
        String hostToRegistry = getValueFromConfig(protocolConfig, DUBBO_IP_TO_REGISTRY);
        if (hostToRegistry != null && hostToRegistry.length() > 0 && isInvalidLocalHost(hostToRegistry)) {
            throw new IllegalArgumentException("Specified invalid registry ip from property:" + DUBBO_IP_TO_REGISTRY + ", value:" + hostToRegistry);
        } else if (StringUtils.isEmpty(hostToRegistry)) {
            // bind ip is used as registry ip by default
            hostToRegistry = hostToBind;
        }

        map.put(ANYHOST_KEY, String.valueOf(anyhost));

        return hostToRegistry;
    }


    /**
     * Register port and bind port for the provider, can be configured separately
     * Configuration priority: environment variable -> java system properties -> port property in protocol config file
     * -> protocol default port
     *
     * @param protocolConfig
     * @param name
     * @return
     */
    private Integer findConfigedPorts(ProtocolConfig protocolConfig, String name, Map<String, String> map) {
        Integer portToBind = null;

        // parse bind port from environment
        String port = getValueFromConfig(protocolConfig, DUBBO_PORT_TO_BIND);
        portToBind = parsePort(port);

        // if there's no bind port found from environment, keep looking up.
        if (portToBind == null) {
            portToBind = protocolConfig.getPort();
            if (provider != null && (portToBind == null || portToBind == 0)) {
                portToBind = provider.getPort();
            }
            final int defaultPort = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(name).getDefaultPort();
            if (portToBind == null || portToBind == 0) {
                portToBind = defaultPort;
            }
            if (portToBind <= 0) {
                portToBind = getRandomPort(name);
                if (portToBind == null || portToBind < 0) {
                    portToBind = getAvailablePort(defaultPort);
                    putRandomPort(name, portToBind);
                }
            }
        }

        // save bind port, used as url's key later
        map.put(Constants.BIND_PORT_KEY, String.valueOf(portToBind));

        // registry port, not used as bind port by default
        String portToRegistryStr = getValueFromConfig(protocolConfig, DUBBO_PORT_TO_REGISTRY);
        Integer portToRegistry = parsePort(portToRegistryStr);
        if (portToRegistry == null) {
            portToRegistry = portToBind;
        }

        return portToRegistry;
    }

    private Integer parsePort(String configPort) {
        Integer port = null;
        if (configPort != null && configPort.length() > 0) {
            try {
                Integer intPort = Integer.parseInt(configPort);
                if (isInvalidPort(intPort)) {
                    throw new IllegalArgumentException("Specified invalid port from env value:" + configPort);
                }
                port = intPort;
            } catch (Exception e) {
                throw new IllegalArgumentException("Specified invalid port from env value:" + configPort);
            }
        }
        return port;
    }

    private String getValueFromConfig(ProtocolConfig protocolConfig, String key) {
        String protocolPrefix = protocolConfig.getName().toUpperCase() + "_";
        String port = ConfigUtils.getSystemProperty(protocolPrefix + key);
        if (StringUtils.isEmpty(port)) {
            port = ConfigUtils.getSystemProperty(key);
        }
        return port;
    }

    private void completeCompoundConfigs() {
        // 如果配置了provider，那么则从provider中获取信息赋值其他属性，在这些属性为空的情况下
        if (provider != null) {
            if (application == null) {
                setApplication(provider.getApplication());
            }
            if (module == null) {
                setModule(provider.getModule());
            }
            if (registries == null) {
                setRegistries(provider.getRegistries());
            }
            if (monitor == null) {
                setMonitor(provider.getMonitor());
            }
            if (protocols == null) {
                setProtocols(provider.getProtocols());
            }
            if (configCenter == null) {
                setConfigCenter(provider.getConfigCenter());
            }
        }
        // 如果配置了module，那么则从module中获取信息赋值其他属性，在这些属性为空的情况下
        if (module != null) {
            if (registries == null) {
                setRegistries(module.getRegistries());
            }
            if (monitor == null) {
                setMonitor(module.getMonitor());
            }
        }
        // 如果配置了application，那么则从application中获取信息赋值其他属性，在这些属性为空的情况下
        if (application != null) {
            if (registries == null) {
                setRegistries(application.getRegistries());
            }
            if (monitor == null) {
                setMonitor(application.getMonitor());
            }
        }
    }

    private void checkDefault() {
        createProviderIfAbsent();
    }

    private void createProviderIfAbsent() {
        if (provider != null) {
            return;
        }
        setProvider(
                ConfigManager.getInstance()
                        .getDefaultProvider()
                        .orElseGet(() -> {
                            ProviderConfig providerConfig = new ProviderConfig();
                            providerConfig.refresh();
                            return providerConfig;
                        })
        );
    }

    private void checkProtocol() {
        if (CollectionUtils.isEmpty(protocols) && provider != null) {
            setProtocols(provider.getProtocols());
        }
        convertProtocolIdsToProtocols();
    }

    private void convertProtocolIdsToProtocols() {

        if (StringUtils.isEmpty(protocolIds) && CollectionUtils.isEmpty(protocols)) {
            List<String> configedProtocols = new ArrayList<>();

            // 从配置中心的全局配置获取dubbo.protocols.的配置项值
            configedProtocols.addAll(getSubProperties(Environment.getInstance()
                    .getExternalConfigurationMap(), PROTOCOLS_SUFFIX));

            // 从配置中心的应用配置获取dubbo.protocols.的配置项值
            configedProtocols.addAll(getSubProperties(Environment.getInstance()
                    .getAppExternalConfigurationMap(), PROTOCOLS_SUFFIX));

            // 用，号join所有的protocol
            protocolIds = String.join(",", configedProtocols);
        }

        if (StringUtils.isEmpty(protocolIds)) {
            // 如果配置中心没有配置协议，就取默认的协议
            if (CollectionUtils.isEmpty(protocols)) {
                setProtocols(
                        ConfigManager.getInstance().getDefaultProtocols()
                                .filter(CollectionUtils::isNotEmpty)
                                .orElseGet(() -> {
                                    ProtocolConfig protocolConfig = new ProtocolConfig();
                                    protocolConfig.refresh();
                                    return new ArrayList<>(Arrays.asList(protocolConfig));
                                })
                );
            }
        } else {
            // 如果配置了
            String[] arr = COMMA_SPLIT_PATTERN.split(protocolIds);
            List<ProtocolConfig> tmpProtocols = CollectionUtils.isNotEmpty(protocols) ? protocols : new ArrayList<>();

            // 把从配置中心配置的协议添加到服务的协议列表中去
            Arrays.stream(arr).forEach(id -> {
                if (tmpProtocols.stream().noneMatch(prot -> prot.getId().equals(id))) {
                    tmpProtocols.add(ConfigManager.getInstance().getProtocol(id).orElseGet(() -> {
                        ProtocolConfig protocolConfig = new ProtocolConfig();
                        protocolConfig.setId(id);
                        protocolConfig.refresh();
                        return protocolConfig;
                    }));
                }
            });
            if (tmpProtocols.size() > arr.length) {
                throw new IllegalStateException("Too much protocols found, the protocols comply to this service are :" + protocolIds + " but got " + protocols
                        .size() + " registries!");
            }
            setProtocols(tmpProtocols);
        }
    }

    public Class<?> getInterfaceClass() {
        if (interfaceClass != null) {
            return interfaceClass;
        }
        if (ref instanceof GenericService) {
            return GenericService.class;
        }
        try {
            if (interfaceName != null && interfaceName.length() > 0) {
                this.interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                        .getContextClassLoader());
            }
        } catch (ClassNotFoundException t) {
            throw new IllegalStateException(t.getMessage(), t);
        }
        return interfaceClass;
    }

    /**
     * @param interfaceClass
     * @see #setInterface(Class)
     * @deprecated
     */
    public void setInterfaceClass(Class<?> interfaceClass) {
        setInterface(interfaceClass);
    }

    public String getInterface() {
        return interfaceName;
    }

    public void setInterface(Class<?> interfaceClass) {
        if (interfaceClass != null && !interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
        this.interfaceClass = interfaceClass;
        setInterface(interfaceClass == null ? null : interfaceClass.getName());
    }

    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
        if (StringUtils.isEmpty(id)) {
            id = interfaceName;
        }
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    @Parameter(excluded = true)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        checkPathName(PATH_KEY, path);
        this.path = path;
    }

    public List<MethodConfig> getMethods() {
        return methods;
    }

    // ======== Deprecated ========

    @SuppressWarnings("unchecked")
    public void setMethods(List<? extends MethodConfig> methods) {
        this.methods = (List<MethodConfig>) methods;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        ConfigManager.getInstance().addProvider(provider);
        this.provider = provider;
    }

    @Parameter(excluded = true)
    public String getProviderIds() {
        return providerIds;
    }

    public void setProviderIds(String providerIds) {
        this.providerIds = providerIds;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        if (StringUtils.isEmpty(generic)) {
            return;
        }
        if (ProtocolUtils.isValidGenericValue(generic)) {
            this.generic = generic;
        } else {
            throw new IllegalArgumentException("Unsupported generic type " + generic);
        }
    }

    @Override
    public void setMock(Boolean mock) {
        throw new IllegalArgumentException("mock doesn't support on provider side");
    }

    @Override
    public void setMock(String mock) {
        throw new IllegalArgumentException("mock doesn't support on provider side");
    }

    public List<URL> getExportedUrls() {
        return urls;
    }

    /**
     * @deprecated Replace to getProtocols()
     */
    @Deprecated
    public List<ProviderConfig> getProviders() {
        return convertProtocolToProvider(protocols);
    }

    /**
     * @deprecated Replace to setProtocols()
     */
    @Deprecated
    public void setProviders(List<ProviderConfig> providers) {
        this.protocols = convertProviderToProtocol(providers);
    }

    @Override
    @Parameter(excluded = true)
    public String getPrefix() {
        return DUBBO + ".service." + interfaceName;
    }
}
