package com.example.Cloud.Gateway.Handler;

import com.example.Cloud.Gateway.Configuration.GatewayRoutesRefresher;
import com.example.Cloud.Gateway.Entity.ApiRoute;
import com.example.Cloud.Gateway.Service.RouteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class ApiRouteHandler {
    private final RouteService routeService;

    private final RouteLocator routeLocator;
    
    private static final Logger logger = LoggerFactory.getLogger(ApiRouteHandler.class);

    private final GatewayRoutesRefresher gatewayRoutesRefresher;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<ApiRoute> apiRoute = serverRequest.bodyToMono(ApiRoute.class);
        return apiRoute.flatMap(route ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(routeService.create(route), ApiRoute.class));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        final String apiId = serverRequest.pathVariable("routeId");
        Mono<ApiRoute> apiRoute = routeService.getById(apiId);
        return apiRoute.flatMap(route -> ServerResponse.ok()
                        .body(Mono.just(route), ApiRoute.class))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }


	public Mono<ServerResponse> refreshRoutes(ServerRequest serverRequest) {
        gatewayRoutesRefresher.refreshRoutes();
        
        return ServerResponse.ok().body(BodyInserters.fromObject("Routes reloaded successfully"));
    }
}