package com.example.Cloud.Gateway.Repository;

import com.example.Cloud.Gateway.Entity.ApiRoute;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.util.MongoCompatibilityAdapter.ReactiveMongoDatabaseAdapterBuilder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepositroy extends ReactiveMongoRepository<ApiRoute, String> {

}