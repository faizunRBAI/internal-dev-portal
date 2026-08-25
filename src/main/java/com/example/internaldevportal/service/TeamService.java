package com.example.internaldevportal.service;

import com.example.internaldevportal.dto.TeamDto;
import com.example.internaldevportal.entity.Team;
import com.example.internaldevportal.exception.ConflictException;
import com.example.internaldevportal.exception.ResourceNotFoundException;
import com.example.internaldevportal.repository.TeamRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TeamDto findById(Long id) {
        return toDto(teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", id)));
    }

    @Transactional
    public TeamDto create(TeamDto dto) {
        if (teamRepository.existsByName(dto.getName())) {
            throw new ConflictException("Team already exists: " + dto.getName());
        }
        Team team = new Team();
        team.setName(dto.getName());
        team.setDescription(dto.getDescription());
        return toDto(teamRepository.save(team));
    }

    @Transactional
    public TeamDto update(Long id, TeamDto dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", id));
        team.setName(dto.getName());
        team.setDescription(dto.getDescription());
        return toDto(teamRepository.save(team));
    }

    @Transactional
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team", id);
        }
        teamRepository.deleteById(id);
    }

    private TeamDto toDto(Team team) {
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDescription(team.getDescription());
        dto.setCreatedAt(team.getCreatedAt());
        dto.setUpdatedAt(team.getUpdatedAt());
        return dto;
    }
}
