package com.example.Parking_service.Controller;

import com.example.Parking_service.Model.ParkingSlot;
import com.example.Parking_service.Response.MessageResponse;
import com.example.Parking_service.Service.ParkingSlotService;
import com.example.Parking_service.Service.ParkingSlotServiceAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/parking")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @Autowired
    private ParkingSlotServiceAllocation parkingSlotServiceAllocation;

    @PostMapping
    public ResponseEntity<ParkingSlot> createParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        return parkingSlotService.createParkingSlot(parkingSlot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParkingSlot>> getParkingSlotById(@PathVariable Long id) {
        return parkingSlotService.getParkingSlotById(id);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllParkingSlots() {
        return parkingSlotService.getAllParkingSlots();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateParkingSlot(@PathVariable Long id, @RequestBody ParkingSlot updatedParkingSlot) {
        return parkingSlotService.updateParkingSlot(id, updatedParkingSlot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable Long id) {
        return parkingSlotService.deleteParkingSlot(id);
    }

    @PostMapping("/allocate/{id}")
    public ResponseEntity<MessageResponse> allocateWorkspace(@PathVariable Long id,String email) {
        return (ResponseEntity<MessageResponse>) parkingSlotServiceAllocation.allocateParkingSlot(id,email);
    }


    @PostMapping("/release/{id}")
    public ResponseEntity<MessageResponse> releaseWorkspace(@PathVariable Long id) {
        return (ResponseEntity<MessageResponse>) parkingSlotServiceAllocation.releaseParkingSlot(id);
    }


}
