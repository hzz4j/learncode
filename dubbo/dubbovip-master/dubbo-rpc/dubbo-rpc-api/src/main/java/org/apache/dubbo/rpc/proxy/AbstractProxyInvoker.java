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
package org.apache.dubbo.rpc.proxy;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.AsyncContextImpl;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * InvokerWrapper
 */
public abstract class AbstractProxyInvoker<T> implements Invoker<T> {
    Logger logger = LoggerFactory.getLogger(AbstractProxyInvoker.class);

    private final T proxy;

    private final Class<T> type;

    private final URL url;

    public AbstractProxyInvoker(T proxy, Class<T> type, URL url) {
        if (proxy == null) {
            throw new IllegalArgumentException("proxy == null");
        }
        if (type == null) {
            throw new IllegalArgumentException("interface == null");
        }
        if (!type.isInstance(proxy)) {
            throw new IllegalArgumentException(proxy.getClass().getName() + " not implement interface " + type);
        }
        this.proxy = proxy;
        this.type = type;
        this.url = url;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void destroy() {
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        try {
            // 执行服务，得到一个接口，可能是一个CompletableFuture(表示异步调用)，可能是一个正常的服务执行结果（同步调用）
            // 如果是同步调用会阻塞，如果是异步调用不会阻塞
            Object value = doInvoke(proxy, invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());

            // 将同步调用的服务执行结果封装为CompletableFuture类型
            CompletableFuture<Object> future = wrapWithFuture(value, invocation);

            // 异步RPC结果
            AsyncRpcResult asyncRpcResult = new AsyncRpcResult(invocation);

            //设置一个回调，如果是异步调用，那么服务执行完成后将执行这里的回调
            future.whenComplete((obj, t) -> {
                // 当服务执行完后，将服务之后将结果或异常设置到AsyncRpcResult中
                // 如果是异步服务，那么服务之后的异常会在此处封装到AppResponse中然后返回
                // 如果是同步服务出异常了，则会在下面将异常封装到AsyncRpcResult中
                AppResponse result = new AppResponse();
                if (t != null) {
                    if (t instanceof CompletionException) {
                        result.setException(t.getCause());
                    } else {
                        result.setException(t);
                    }
                } else {
                    result.setValue(obj);
                }
                // 将服务执行完之后的结果设置到异步RPC结果对象中
                asyncRpcResult.complete(result);
            });

            // 返回异步RPC结果
            return asyncRpcResult;
        } catch (InvocationTargetException e) {
            // 假设抛的NullPointException，那么会把这个异常包装为一个Result对象
            if (RpcContext.getContext().isAsyncStarted() && !RpcContext.getContext().stopAsync()) {
                logger.error("Provider async started, but got an exception from the original method, cannot write the exception back to consumer because an async result may have returned the new thread.", e);
            }
            // 同步服务执行时如何出异常了，会在此处将异常信息封装为一个AsyncRpcResult然后返回
            return AsyncRpcResult.newDefaultAsyncResult(null, e.getTargetException(), invocation);
        } catch (Throwable e) {
            // 执行服务后的所有异常都会包装为RpcException进行抛出
            throw new RpcException("Failed to invoke remote proxy method " + invocation.getMethodName() + " to " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    private CompletableFuture<Object> wrapWithFuture (Object value, Invocation invocation) {
        if (RpcContext.getContext().isAsyncStarted()) {
            return ((AsyncContextImpl)(RpcContext.getContext().getAsyncContext())).getInternalFuture();
        } else if (value instanceof CompletableFuture) {
            return (CompletableFuture<Object>) value;
        }
        return CompletableFuture.completedFuture(value);
    }

    protected abstract Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable;

    @Override
    public String toString() {
        return getInterface() + " -> " + (getUrl() == null ? " " : getUrl().toString());
    }


}
