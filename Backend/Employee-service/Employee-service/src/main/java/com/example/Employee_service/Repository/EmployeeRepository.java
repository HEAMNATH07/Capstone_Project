package com.example.Employee_service.Repository;

import com.example.Employee_service.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	public Optional<Employee> findEmployeeByEventId(String eventId);
	public List<Employee> findByEventId(String eventId);
	public List<Employee> findByDietaryPreference(String dietaryPreference);

}