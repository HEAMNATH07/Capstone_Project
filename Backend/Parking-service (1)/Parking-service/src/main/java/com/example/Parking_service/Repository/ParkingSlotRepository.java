package com.example.Parking_service.Repository;

import com.example.Parking_service.Model.ParkingSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends MongoRepository<ParkingSlot, Long> {
}