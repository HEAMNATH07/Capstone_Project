package com.example.Employee_service.Controller;

import com.example.Employee_service.Model.Employee;
import com.example.Employee_service.Model.EmployeeGroup;
import com.example.Employee_service.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController { 
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/test")
	public String test() { 
		return "Employee Service";
	}

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        return employeeService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return employeeService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable String id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return employeeService.delete(id);
    }
    
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Employee>> findByEventId(@PathVariable String eventId) {
        return employeeService.findByEventId(eventId);
    }
    
    @GetMapping("/diet/{dietaryPreference}")
    public ResponseEntity<List<Employee>> findGuestsByDietaryPreference(@PathVariable String dietaryPreference) {
        return employeeService.findEmployeesByDietaryPreference(dietaryPreference);
    }

//    @GetMapping("/rsvp/{rsvpStatus}")
//    public ResponseEntity<List<Employee>> findEmployeesByRSVPStatus(@PathVariable RSVPStatus rsvpStatus) {
//        return employeeService.findEmployeesByRSVPStatus(rsvpStatus);
//    }

    // GuestGroup Endpoints
    @PostMapping("/group")
    public ResponseEntity<EmployeeGroup> createEmployeeGroup(@RequestBody EmployeeGroup employeeGroup) {
        return employeeService.createEmployeeGroup(employeeGroup);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<EmployeeGroup> getEmployeeGroupById(@PathVariable String id) {
        return employeeService.getEmployeeGroupById(id);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<EmployeeGroup>> getAllEmployeeGroups() {
        return employeeService.getAllEmployeeGroups();
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<EmployeeGroup> updateEmployeeGroup(@PathVariable String id, @RequestBody EmployeeGroup employeeGroup) {
        return employeeService.updateEmployeeGroup(id, employeeGroup);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteEmployeeGroup(@PathVariable String id) {
        return employeeService.deleteEmployeeGroup(id);
    }

    @GetMapping("/group/event/{eventId}")
    public ResponseEntity<List<EmployeeGroup>> findEmployeeGroupsByEventId(@PathVariable String eventId) {
        return employeeService.findEmployeeGroupsByEventId(eventId);
    }
}