package com.tcs.tcsinterns.repository;

import com.tcs.tcsinterns.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
