package com.example.Employee_service.Model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGroup { 
	@Id
	private String id; 
	private String name; 
	List<Employee> employees;
	private String eventId;
}