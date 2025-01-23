package com.example.Employee_service.Model;

import com.example.Employee_service.Enum.DietaryPreference;
import com.example.Employee_service.Enum.RSVPStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("employee")
public class Employee { 
	@Id
	private String id; 
	private String name, contactEmail; 
	private DietaryPreference dietaryPreference;
	private RSVPStatus rsvpStatus;
	private String eventId; 
}
