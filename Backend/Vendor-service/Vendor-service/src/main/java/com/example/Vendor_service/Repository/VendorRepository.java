package com.example.Vendor_service.Repository;

import com.example.Vendor_service.Model.Payment;
import com.example.Vendor_service.Model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String> {
	public List<Vendor> findByEventId(String eventId);
	public List<Payment> findPaymentsById(String id);
}

