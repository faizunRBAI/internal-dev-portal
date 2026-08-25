package com.example.internaldevportal.repository;

import com.example.internaldevportal.entity.Deployment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {
    List<Deployment> findByProjectId(Long projectId);
    List<Deployment> findByEnvironmentId(Long environmentId);
    List<Deployment> findByStatus(String status);
}
