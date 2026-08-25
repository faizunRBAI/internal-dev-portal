package com.example.internaldevportal.repository;

import com.example.internaldevportal.entity.Environment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    List<Environment> findByProjectId(Long projectId);
    boolean existsByNameAndProjectId(String name, Long projectId);
}
