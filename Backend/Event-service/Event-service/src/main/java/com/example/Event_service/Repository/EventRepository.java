package com.example.Event_service.Repository;

import com.example.Event_service.Model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
	public List<Event> findByUserId(String eventUserId);
}