package com.example.Employee_service.Service;

import com.example.Employee_service.Model.Employee;
import com.example.Employee_service.Model.EmployeeGroup;
import com.example.Employee_service.Repository.EmployeeGroupRepository;
import com.example.Employee_service.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService { 
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired 
	private EmployeeGroupRepository employeeGroupRepository;

    public ResponseEntity<Employee> create(Employee employee) {
		Optional<EmployeeGroup> optional = employeeGroupRepository.findEmployeeGroupByEventId(employee.getEventId());
		if(optional.isEmpty()) { 
			EmployeeGroup employeeGroup = new EmployeeGroup(null, "group_" + employee.getEventId(), new ArrayList<>(), employee.getEventId());
			optional = Optional.of(employeeGroup);
		}  
		EmployeeGroup employeeGroup = optional.get(); 
		employeeGroup.getEmployees().add(employee); 
		employeeGroupRepository.save(employeeGroup);
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }


    public ResponseEntity<Employee> getById(String id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        return employeeOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    public ResponseEntity<Employee> update(String id, Employee employee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(id);
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            existingEmployee.setName(employee.getName());
            existingEmployee.setContactEmail(employee.getContactEmail());
            existingEmployee.setDietaryPreference(employee.getDietaryPreference());
            existingEmployee.setRsvpStatus(employee.getRsvpStatus());
            existingEmployee.setEventId(employee.getEventId());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> delete(String id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    
    public ResponseEntity<Employee> findEmployeeByEventId(String eventId) {
        Optional<Employee> employee = employeeRepository.findEmployeeByEventId(eventId);
        return employee.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(employee.get());
    }

    public ResponseEntity<List<Employee>> findByEventId(String eventId) {
        List<Employee> employees = employeeRepository.findByEventId(eventId);
        return ResponseEntity.ok(employees);
    } 
    
    public ResponseEntity<List<Employee>> findEmployeesByDietaryPreference(String dietaryPreference) {
        List<Employee> employees = employeeRepository.findByDietaryPreference(dietaryPreference);
        return ResponseEntity.ok(employees);
    }

//    public ResponseEntity<List<Employee>> findEmployeesByRSVPStatus(RSVPStatus rsvpStatus) {
//        List<Employee> employees = employeeRepository.findByRSVPStatus(rsvpStatus);
//        return ResponseEntity.ok(employees);
//    }

    // GuestGroup Methods

    public ResponseEntity<EmployeeGroup> createEmployeeGroup(EmployeeGroup employeeGroup) {
        EmployeeGroup savedGroup = employeeGroupRepository.save(employeeGroup);
        return ResponseEntity.ok(savedGroup);
    }

    public ResponseEntity<EmployeeGroup> getEmployeeGroupById(String id) {
        Optional<EmployeeGroup> employeeGroup = employeeGroupRepository.findById(id);
        return employeeGroup.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<EmployeeGroup>> getAllEmployeeGroups() {
        List<EmployeeGroup> employeeGroupsGroups = employeeGroupRepository.findAll();
        return ResponseEntity.ok(employeeGroupsGroups);
    }

    public ResponseEntity<EmployeeGroup> updateEmployeeGroup(String id, EmployeeGroup employeeGroup) {
        employeeGroup.setId(id);
        EmployeeGroup updatedGroup = employeeGroupRepository.save(employeeGroup);
        return ResponseEntity.ok(updatedGroup);
    }

    public ResponseEntity<Void> deleteEmployeeGroup(String id) {
        employeeGroupRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<EmployeeGroup>> findEmployeeGroupsByEventId(String eventId) {
        List<EmployeeGroup> employeeGroups = employeeGroupRepository.findByEventId(eventId);
        return ResponseEntity.ok(employeeGroups);
    }
}

