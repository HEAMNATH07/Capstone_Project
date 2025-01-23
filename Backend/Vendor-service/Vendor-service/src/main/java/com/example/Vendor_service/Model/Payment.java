package com.example.Vendor_service.Model;

import java.util.Date;

import com.example.Vendor_service.Enum.PaymentStatus;
import com.example.Vendor_service.Enum.PaymentType;
import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	private String id;   
	private String vendorId;
	private Double amount;
	private String referenceNumber;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "dd-MM-yyyy")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	Date paymentDate;  
	
	PaymentStatus paymentStatus;
	PaymentType paymentType;
}