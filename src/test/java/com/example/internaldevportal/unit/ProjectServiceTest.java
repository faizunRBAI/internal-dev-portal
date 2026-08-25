package com.example.internaldevportal.unit;

import com.example.internaldevportal.dto.ProjectDto;
import com.example.internaldevportal.entity.Project;
import com.example.internaldevportal.exception.ConflictException;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.ProjectRepository;
import com.example.internaldevportal.repository.TeamRepository;
import com.example.internaldevportal.service.ProjectService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("IDP Core");
        project.setStatus("ACTIVE");
    }

    @Test
    void findAll_returnsList() {
        when(projectRepository.findAll()).thenReturn(List.of(project));
        assertThat(projectService.findAll()).hasSize(1);
    }

    @Test
    void findById_found_returnsDto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        ProjectDto dto = projectService.findById(1L);
        assertThat(dto.getName()).isEqualTo("IDP Core");
    }

    @Test
    void findById_missing_throwsNotFound() {
        when(projectRepository.findById(5L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> projectService.findById(5L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_duplicate_throwsConflict() {
        when(projectRepository.existsByName("IDP Core")).thenReturn(true);
        ProjectDto dto = new ProjectDto();
        dto.setName("IDP Core");
        assertThatThrownBy(() -> projectService.create(dto))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void create_valid_savesProject() {
        when(projectRepository.existsByName("New Project")).thenReturn(false);
        Project saved = new Project();
        saved.setId(2L);
        saved.setName("New Project");
        saved.setStatus("ACTIVE");
        when(projectRepository.save(any(Project.class))).thenReturn(saved);
        ProjectDto dto = new ProjectDto();
        dto.setName("New Project");
        ProjectDto result = projectService.create(dto);
        assertThat(result.getId()).isEqualTo(2L);
    }
}
