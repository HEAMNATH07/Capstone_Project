package com.example.Vendor_service.Model;

import java.util.List;

import com.example.Vendor_service.Enum.VendorType;
import org.springframework.data.mongodb.core.mapping.Document;



import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Vendor { 
	@Id
	private String id; 
	private String name, contactEmail; 
	private VendorType type;
	private List<Payment> payments; 
	private Double totalAmount, pendingAmount;	
	private String eventId;
}
