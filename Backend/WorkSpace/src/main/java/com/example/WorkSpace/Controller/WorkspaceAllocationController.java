package com.example.WorkSpace.Controller;


import com.example.WorkSpace.Model.Workspace;
import com.example.WorkSpace.Response.MessageResponse;
import com.example.WorkSpace.Service.WorkspaceService;
import com.example.WorkSpace.Service.WorkspaceServiceAllocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/workspaces")
public class WorkspaceAllocationController {

    @Autowired
    private WorkspaceServiceAllocation workspaceServiceAllocation;

    @Autowired
    private WorkspaceService workspaceService;

    // Create a new workspace
    @PostMapping("/admin")
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        return workspaceService.createWorkspace(workspace);
    }
    // Get a workspace by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable Long id) {
        return workspaceService.getWorkspaceById(id);
    }

    // Get all workspaces
    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    // Update a workspace
    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable Long id, @RequestBody Workspace workspace) {
        return workspaceService.updateWorkspace(id, workspace);
    }

    // Delete a workspace by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id) {
        return workspaceService.deleteWorkspace(id);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Workspace>> getAvailableWorkspaces() {
        List<Workspace> availableWorkspaces = workspaceService.getAvailableWorkspaces();
        return ResponseEntity.ok(availableWorkspaces);
    }

    // Endpoint to get unavailable workspaces
    @GetMapping("/unavailable")
    public ResponseEntity<List<Workspace>> getUnavailableWorkspaces() {
        List<Workspace> unavailableWorkspaces = workspaceService.getUnavailableWorkspaces();
        return ResponseEntity.ok(unavailableWorkspaces);
    }
    // Allocate Workspace
    @PostMapping("/allocate/{id}")
    public ResponseEntity<MessageResponse> allocateWorkspace(@PathVariable Long id,String email) {
        return (ResponseEntity<MessageResponse>) workspaceServiceAllocation.allocateWorkspace(id,email);
    }
    // Release Workspace
    @PostMapping("/release/{id}")
    public ResponseEntity<MessageResponse> releaseWorkspace(@PathVariable Long id) {
        return (ResponseEntity<MessageResponse>) workspaceServiceAllocation.releaseWorkspace(id);
    }
}
