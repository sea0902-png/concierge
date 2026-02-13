package com.prototie.concierge.project.service;

import com.prototie.concierge.project.dto.ProjectRequest;
import com.prototie.concierge.project.dto.ProjectResponse;
import com.prototie.concierge.project.dto.ProjectUpdateRequest;
import com.prototie.concierge.project.entity.Project;
import com.prototie.concierge.project.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

	private static final String DEFAULT_STATUS = "ACTIVE";

	private final ProjectRepository projectRepository;

	@Transactional
	public ProjectResponse create(ProjectRequest request) {
		Project project = Project.builder()
			.name(request.getName())
			.description(request.getDescription())
			.status(request.getStatus() != null ? request.getStatus() : DEFAULT_STATUS)
			.build();
		Project saved = projectRepository.save(project);
		return ProjectResponse.from(saved);
	}

	public Optional<ProjectResponse> findById(Long id) {
		return projectRepository.findById(id)
			.map(ProjectResponse::from);
	}

	public List<ProjectResponse> findAll() {
		return projectRepository.findAll().stream()
			.map(ProjectResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public Optional<ProjectResponse> update(Long id, ProjectUpdateRequest request) {
		return projectRepository.findById(id)
			.map(project -> {
				project.update(
					request.getName(),
					request.getDescription(),
					request.getStatus()
				);
				return ProjectResponse.from(project);
			});
	}

	@Transactional
	public boolean deleteById(Long id) {
		return projectRepository.findById(id)
			.map(project -> {
				projectRepository.delete(project);
				return true;
			})
			.orElse(false);
	}
}
