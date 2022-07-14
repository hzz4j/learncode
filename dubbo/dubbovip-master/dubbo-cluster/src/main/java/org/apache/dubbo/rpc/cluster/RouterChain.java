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
package org.apache.dubbo.rpc.cluster;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Router chain
 */
public class RouterChain<T> {

    // full list of addresses from registry, classified by method name.
    private List<Invoker<T>> invokers = Collections.emptyList();

    // containing all routers, reconstruct every time 'route://' urls change.
    private volatile List<Router> routers = Collections.emptyList();

    // Fixed router instances: ConfigConditionRouter, TagRouter, e.g., the rule for each instance may change but the
    // instance will never delete or recreate.
    private List<Router> builtinRouters = Collections.emptyList();

    public static <T> RouterChain<T> buildChain(URL url) {
        return new RouterChain<>(url);
    }

    private RouterChain(URL url) {
        // 拿到RouterFactory接口有哪些扩展实现类，比如默认情况下就有四个：
        // 0 = {MockRouterFactory@2880}
        // 1 = {TagRouterFactory@2881}      // 标签路由
        // 2 = {AppRouterFactory@2882}      // 应用条件路由
        // 3 = {ServiceRouterFactory@2883}  // 服务条件路由
        List<RouterFactory> extensionFactories = ExtensionLoader.getExtensionLoader(RouterFactory.class)
                .getActivateExtension(url, (String[]) null);

        // 然后利用RouterFactory根据url生成各个类型的Router
        // 这里生产的routers已经是真实可用的了，但是有个比较特殊的：
        // 对于应用条件路由和服务条件路由对于的Router对象，对象内部已经有真实可用的数据了（数据已经从配置中心得到了）
        // 但是对于标签路由则没有，它暂时还相当于一个没有内容的对象（还没有从配置中心获取标签路由的数据）
        List<Router> routers = extensionFactories.stream()
                .map(factory -> factory.getRouter(url))
                .collect(Collectors.toList());

        // 把routers按priority进行排序
        initWithRouters(routers);
    }

    /**
     * the resident routers must being initialized before address notification.
     * FIXME: this method should not be public
     */
    public void initWithRouters(List<Router> builtinRouters) {
        this.builtinRouters = builtinRouters;
        this.routers = new ArrayList<>(builtinRouters);
        this.sort();
    }

    /**
     * If we use route:// protocol in version before 2.7.0, each URL will generate a Router instance, so we should
     * keep the routers up to date, that is, each time router URLs changes, we should update the routers list, only
     * keep the builtinRouters which are available all the time and the latest notified routers which are generated
     * from URLs.
     *
     * @param routers routers from 'router://' rules in 2.6.x or before.
     */
    public void addRouters(List<Router> routers) {
        List<Router> newRouters = new ArrayList<>();
        newRouters.addAll(builtinRouters);
        newRouters.addAll(routers);
        CollectionUtils.sort(newRouters);
        this.routers = newRouters;
    }

    private void sort() {
        // Router接口中有一个默认方法compareTo，会按照priority进行排序
        Collections.sort(routers);
    }

    /**
     *
     * @param url
     * @param invocation
     * @return
     */
    public List<Invoker<T>> route(URL url, Invocation invocation) {
        List<Invoker<T>> finalInvokers = invokers;
        // 使用路由对服务提供者进行过滤
        for (Router router : routers) {
            finalInvokers = router.route(finalInvokers, url, invocation);
        }
        return finalInvokers;
    }

    /**
     * Notify router chain of the initial addresses from registry at the first time.
     * Notify whenever addresses in registry change.
     */
    public void setInvokers(List<Invoker<T>> invokers) {
        this.invokers = (invokers == null ? Collections.emptyList() : invokers);
        routers.forEach(router -> router.notify(this.invokers));
    }
}
