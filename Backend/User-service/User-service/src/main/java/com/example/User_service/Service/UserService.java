package com.example.User_service.Service;

import com.example.User_service.Model.EventUser;
import com.example.User_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private JwtService jwtService; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<EventUser> create(EventUser eventUser) {
    	eventUser.setPassword(passwordEncoder.encode(eventUser.getPassword()));
        EventUser savedEventUser = userRepository.save(eventUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEventUser);
    }
    public ResponseEntity<EventUser> getById(String id) {
        Optional<EventUser> eventUserOpt = userRepository.findById(id);
        return eventUserOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<EventUser>> getAll() {
        List<EventUser> eventUsers = userRepository.findAll();
        return ResponseEntity.ok(eventUsers);
    }

    public ResponseEntity<EventUser> update(String id, EventUser eventUser) {
        Optional<EventUser> existingEventUserOpt = userRepository.findById(id);
        if (existingEventUserOpt.isPresent()) {
            EventUser existingEventUser = existingEventUserOpt.get();
            existingEventUser.setName(eventUser.getName());

            EventUser updatedEventUser = userRepository.save(existingEventUser);
            return ResponseEntity.ok(updatedEventUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> delete(String id) {
        Optional<EventUser> eventUserOpt = userRepository.findById(id);
        if (eventUserOpt.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    } 
    

    public ResponseEntity<EventUser> getByName(String name) {
        Optional<EventUser> user = userRepository.findByName(name);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
