package org.hzz.config;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.hzz.interceptor.LogGrpcInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {
    @GrpcGlobalServerInterceptor
    ServerInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }
}
