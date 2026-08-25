package com.example.internaldevportal.controller;

import com.example.internaldevportal.dto.EnvironmentDto;
import com.example.internaldevportal.service.EnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/environments")
@Tag(name = "Environments", description = "Environment management CRUD")
@SecurityRequirement(name = "bearerAuth")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping
    @Operation(summary = "List all environments, optionally filtered by project")
    public ResponseEntity<List<EnvironmentDto>> findAll(
            @RequestParam(required = false) Long projectId) {
        if (projectId != null) {
            return ResponseEntity.ok(environmentService.findByProject(projectId));
        }
        return ResponseEntity.ok(environmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get environment by ID")
    public ResponseEntity<EnvironmentDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(environmentService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new environment")
    public ResponseEntity<EnvironmentDto> create(@Valid @RequestBody EnvironmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(environmentService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing environment")
    public ResponseEntity<EnvironmentDto> update(
            @PathVariable Long id, @Valid @RequestBody EnvironmentDto dto) {
        return ResponseEntity.ok(environmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an environment")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        environmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
