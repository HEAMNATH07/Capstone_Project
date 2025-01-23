package com.example.Event_service.Controller;

import com.example.Event_service.Model.Event;
import com.example.Event_service.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/event")
public class EventController { 
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/test")
	public String test() {
		return "Hello";
	}

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        return eventService.create(event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable String id) {
        return eventService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return eventService.getAll();
    }  
    
    @GetMapping("/guests/{eventId}/{totalEmployees}")
    public ResponseEntity<Event> updateEventEmployee(
        @PathVariable String eventId,
        @PathVariable int totalEmployees
    ) {
        return eventService.updateEventEmployees(eventId, totalEmployees);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable String id, @RequestBody Event event) {
        return eventService.update(id, event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return eventService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> findByUserId(@PathVariable String userId) {
        return eventService.findByUserId(userId);
    } 
    
    @GetMapping("/tasks/{eventId}/{taskCompleted}/{totalTask}")
    public ResponseEntity<Event> updateTaskCounts(
        @PathVariable String eventId,
        @PathVariable int taskCompleted, 
        @PathVariable int totalTask
    ) {
        return eventService.updateTaskCounts(eventId, taskCompleted, totalTask);
    }
}