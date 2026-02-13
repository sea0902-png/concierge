package com.prototie.concierge.project.repository;

import com.prototie.concierge.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
