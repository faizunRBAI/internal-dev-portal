package com.example.internaldevportal.unit;

import com.example.internaldevportal.dto.TeamDto;
import com.example.internaldevportal.entity.Team;
import com.example.internaldevportal.exception.ConflictException;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.TeamRepository;
import com.example.internaldevportal.service.TeamService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Platform");
        team.setDescription("Platform engineering");
    }

    @Test
    void findAll_returnsMappedDtos() {
        when(teamRepository.findAll()).thenReturn(List.of(team));
        List<TeamDto> result = teamService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Platform");
    }

    @Test
    void findById_found_returnsDto() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamDto dto = teamService.findById(1L);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Platform");
    }

    @Test
    void findById_notFound_throwsException() {
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> teamService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_newName_savesAndReturns() {
        when(teamRepository.existsByName("Platform")).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        TeamDto dto = new TeamDto();
        dto.setName("Platform");
        dto.setDescription("Platform engineering");
        TeamDto result = teamService.create(dto);
        assertThat(result.getName()).isEqualTo("Platform");
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    void create_duplicateName_throwsConflict() {
        when(teamRepository.existsByName("Platform")).thenReturn(true);
        TeamDto dto = new TeamDto();
        dto.setName("Platform");
        assertThatThrownBy(() -> teamService.create(dto))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void delete_notFound_throwsException() {
        when(teamRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> teamService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
