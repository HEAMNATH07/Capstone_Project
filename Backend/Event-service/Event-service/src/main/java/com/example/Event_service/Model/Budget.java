package com.example.Event_service.Model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Budget { 
	@Id
	private String id;
	private Double allocatedAmount, spentAmount; 
	private Map<String, Double> categoryAllocations;
}
