package com.example.internaldevportal.service;

import com.example.internaldevportal.dto.EnvironmentDto;
import com.example.internaldevportal.entity.Environment;
import com.example.internaldevportal.entity.Project;
import com.example.internaldevportal.exception.ConflictException;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.EnvironmentRepository;
import com.example.internaldevportal.repository.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository,
            ProjectRepository projectRepository) {
        this.environmentRepository = environmentRepository;
        this.projectRepository = projectRepository;
    }

    public List<EnvironmentDto> findAll() {
        return environmentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public EnvironmentDto findById(Long id) {
        return toDto(environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Environment", id)));
    }

    public List<EnvironmentDto> findByProject(Long projectId) {
        return environmentRepository.findByProjectId(projectId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public EnvironmentDto create(EnvironmentDto dto) {
        if (dto.getProjectId() != null
                && environmentRepository.existsByNameAndProjectId(dto.getName(), dto.getProjectId())) {
            throw new ConflictException(
                    "Environment '" + dto.getName() + "' already exists for this project");
        }
        Environment env = new Environment();
        env.setName(dto.getName());
        env.setType(dto.getType() != null ? dto.getType() : "DEV");
        env.setBaseUrl(dto.getBaseUrl());
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project", dto.getProjectId()));
            env.setProject(project);
        }
        return toDto(environmentRepository.save(env));
    }

    @Transactional
    public EnvironmentDto update(Long id, EnvironmentDto dto) {
        Environment env = environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Environment", id));
        env.setName(dto.getName());
        env.setType(dto.getType() != null ? dto.getType() : env.getType());
        env.setBaseUrl(dto.getBaseUrl());
        return toDto(environmentRepository.save(env));
    }

    @Transactional
    public void delete(Long id) {
        if (!environmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Environment", id);
        }
        environmentRepository.deleteById(id);
    }

    private EnvironmentDto toDto(Environment e) {
        EnvironmentDto dto = new EnvironmentDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setType(e.getType());
        dto.setBaseUrl(e.getBaseUrl());
        dto.setProjectId(e.getProject() != null ? e.getProject().getId() : null);
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        return dto;
    }
}
