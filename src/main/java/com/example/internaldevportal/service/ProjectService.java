package com.example.internaldevportal.service;

import com.example.internaldevportal.dto.ProjectDto;
import com.example.internaldevportal.entity.Project;
import com.example.internaldevportal.entity.Team;
import com.example.internaldevportal.exception.ConflictException;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.ProjectRepository;
import com.example.internaldevportal.repository.TeamRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    public ProjectService(ProjectRepository projectRepository, TeamRepository teamRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
    }

    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProjectDto findById(Long id) {
        return toDto(projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id)));
    }

    @Transactional
    public ProjectDto create(ProjectDto dto) {
        if (projectRepository.existsByName(dto.getName())) {
            throw new ConflictException("Project already exists: " + dto.getName());
        }
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setRepository(dto.getRepository());
        project.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", dto.getTeamId()));
            project.setTeam(team);
        }
        return toDto(projectRepository.save(project));
    }

    @Transactional
    public ProjectDto update(Long id, ProjectDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setRepository(dto.getRepository());
        if (dto.getStatus() != null) {
            project.setStatus(dto.getStatus());
        }
        if (dto.getTeamId() != null) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", dto.getTeamId()));
            project.setTeam(team);
        }
        return toDto(projectRepository.save(project));
    }

    @Transactional
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", id);
        }
        projectRepository.deleteById(id);
    }

    private ProjectDto toDto(Project p) {
        ProjectDto dto = new ProjectDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setRepository(p.getRepository());
        dto.setStatus(p.getStatus());
        dto.setTeamId(p.getTeam() != null ? p.getTeam().getId() : null);
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        return dto;
    }
}
