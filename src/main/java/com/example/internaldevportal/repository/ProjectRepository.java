package com.example.internaldevportal.repository;

import com.example.internaldevportal.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
    List<Project> findByStatus(String status);
    List<Project> findByTeamId(Long teamId);
}
