package com.example.internaldevportal.controller;

import com.example.internaldevportal.dto.DeploymentDto;
import com.example.internaldevportal.service.DeploymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deployments")
@Tag(name = "Deployments", description = "Deployment management CRUD")
@SecurityRequirement(name = "bearerAuth")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @GetMapping
    @Operation(summary = "List all deployments, optionally filtered by project")
    public ResponseEntity<List<DeploymentDto>> findAll(
            @RequestParam(required = false) Long projectId) {
        if (projectId != null) {
            return ResponseEntity.ok(deploymentService.findByProject(projectId));
        }
        return ResponseEntity.ok(deploymentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get deployment by ID")
    public ResponseEntity<DeploymentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new deployment record")
    public ResponseEntity<DeploymentDto> create(@Valid @RequestBody DeploymentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deploymentService.create(dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update deployment status")
    public ResponseEntity<DeploymentDto> updateStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(deploymentService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a deployment record")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deploymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
