package com.example.internaldevportal.service;

import com.example.internaldevportal.dto.DeploymentDto;
import com.example.internaldevportal.entity.AppUser;
import com.example.internaldevportal.entity.Deployment;
import com.example.internaldevportal.entity.Environment;
import com.example.internaldevportal.entity.Project;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.AppUserRepository;
import com.example.internaldevportal.repository.DeploymentRepository;
import com.example.internaldevportal.repository.EnvironmentRepository;
import com.example.internaldevportal.repository.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;
    private final ProjectRepository projectRepository;
    private final EnvironmentRepository environmentRepository;
    private final AppUserRepository userRepository;

    public DeploymentService(DeploymentRepository deploymentRepository,
            ProjectRepository projectRepository,
            EnvironmentRepository environmentRepository,
            AppUserRepository userRepository) {
        this.deploymentRepository = deploymentRepository;
        this.projectRepository = projectRepository;
        this.environmentRepository = environmentRepository;
        this.userRepository = userRepository;
    }

    public List<DeploymentDto> findAll() {
        return deploymentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DeploymentDto findById(Long id) {
        return toDto(deploymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deployment", id)));
    }

    public List<DeploymentDto> findByProject(Long projectId) {
        return deploymentRepository.findByProjectId(projectId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public DeploymentDto create(DeploymentDto dto) {
        Deployment deployment = new Deployment();
        deployment.setVersion(dto.getVersion());
        deployment.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        deployment.setCommitSha(dto.getCommitSha());
        deployment.setNotes(dto.getNotes());
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project", dto.getProjectId()));
            deployment.setProject(project);
        }
        if (dto.getEnvironmentId() != null) {
            Environment env = environmentRepository.findById(dto.getEnvironmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Environment", dto.getEnvironmentId()));
            deployment.setEnvironment(env);
        }
        if (dto.getDeployedById() != null) {
            AppUser user = userRepository.findById(dto.getDeployedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User", dto.getDeployedById()));
            deployment.setDeployedBy(user);
        }
        return toDto(deploymentRepository.save(deployment));
    }

    @Transactional
    public DeploymentDto updateStatus(Long id, String status) {
        Deployment deployment = deploymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deployment", id));
        deployment.setStatus(status);
        return toDto(deploymentRepository.save(deployment));
    }

    @Transactional
    public void delete(Long id) {
        if (!deploymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Deployment", id);
        }
        deploymentRepository.deleteById(id);
    }

    private DeploymentDto toDto(Deployment d) {
        DeploymentDto dto = new DeploymentDto();
        dto.setId(d.getId());
        dto.setVersion(d.getVersion());
        dto.setStatus(d.getStatus());
        dto.setCommitSha(d.getCommitSha());
        dto.setNotes(d.getNotes());
        dto.setProjectId(d.getProject() != null ? d.getProject().getId() : null);
        dto.setEnvironmentId(d.getEnvironment() != null ? d.getEnvironment().getId() : null);
        dto.setDeployedById(d.getDeployedBy() != null ? d.getDeployedBy().getId() : null);
        dto.setDeployedAt(d.getDeployedAt());
        dto.setCreatedAt(d.getCreatedAt());
        dto.setUpdatedAt(d.getUpdatedAt());
        return dto;
    }
}
