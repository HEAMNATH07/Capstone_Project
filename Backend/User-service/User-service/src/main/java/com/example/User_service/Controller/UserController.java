package com.example.User_service.Controller;

import com.example.User_service.Model.EventUser;
import com.example.User_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserController {  
	
	@Autowired
	private UserService userService;

	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
	@PostMapping("/auth/create")
    public ResponseEntity<EventUser> create(@RequestBody EventUser eventUser) {
        return userService.create(eventUser);
    }
	
	@PostMapping("/auth/login") 
	public ResponseEntity<AuthResponse> login(@RequestBody EventUserRequest authRequest){
		 Authentication authenticate = authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                authRequest.getUsername(), 
		                authRequest.getPassword()
		            )
		        );
		        
		        if (authenticate.isAuthenticated()) {
		            String token = userService.generateToken(authRequest.getUsername());
		            return ResponseEntity.ok(new AuthResponse(token));
		        } else {
		            throw new RuntimeException("invalid access");
		        }
	}

    @GetMapping("/{id}")
    public ResponseEntity<EventUser> getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<EventUser>> getAll() {
        return userService.getAll();
    } 
    
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token is valid";
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventUser> update(@PathVariable String id, @RequestBody EventUser eventUser) {
        return userService.update(id, eventUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return userService.delete(id);
    } 
    @GetMapping("/byName/{name}")
    public ResponseEntity<EventUser> getByName(@PathVariable String name) {
        return userService.getByName(name);
    }



}

