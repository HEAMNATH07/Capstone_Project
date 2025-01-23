package com.example.Cloud.Gateway.Configuration;

import com.example.Cloud.Gateway.Service.ApiRouteLocatorImpl;
import com.example.Cloud.Gateway.Service.RouteService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteService routeService, RouteLocatorBuilder routeLocationBuilder) {
        return new ApiRouteLocatorImpl(routeLocationBuilder, routeService);
    } 
//    @Bean
//    public ServerCodecConfigurer serverCodecConfigurer() {
//       return ServerCodecConfigurer.create();
//    }
}