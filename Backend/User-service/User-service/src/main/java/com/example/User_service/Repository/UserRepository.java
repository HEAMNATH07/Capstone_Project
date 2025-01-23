package com.example.User_service.Repository;

import com.example.User_service.Model.EventUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<EventUser, String> {
    Optional<EventUser> findByName(String username);
}