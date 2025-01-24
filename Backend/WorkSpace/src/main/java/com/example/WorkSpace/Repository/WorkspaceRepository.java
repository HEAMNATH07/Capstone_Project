package com.example.WorkSpace.Repository;

import com.example.WorkSpace.Model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, Long> {
    List<Workspace> findByIsAvailable(boolean b);
}