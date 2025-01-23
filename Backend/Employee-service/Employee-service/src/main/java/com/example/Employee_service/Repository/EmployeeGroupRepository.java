package com.example.Employee_service.Repository;

import com.example.Employee_service.Model.EmployeeGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeGroupRepository extends MongoRepository<EmployeeGroup, String> {
    Optional<EmployeeGroup> findEmployeeGroupByEventId(String eventId);
    List<EmployeeGroup> findByEventId(String eventId);
}
