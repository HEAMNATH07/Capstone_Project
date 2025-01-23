package com.example.Cloud.Gateway.Repository;

import com.example.Cloud.Gateway.Entity.ApiRoute;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepositroy extends ReactiveCrudRepository<ApiRoute, String> {

}