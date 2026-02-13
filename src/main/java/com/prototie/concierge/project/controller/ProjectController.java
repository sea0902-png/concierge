package com.prototie.concierge.project.controller;

import com.prototie.concierge.project.dto.ProjectRequest;
import com.prototie.concierge.project.dto.ProjectResponse;
import com.prototie.concierge.project.dto.ProjectUpdateRequest;
import com.prototie.concierge.project.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request) {
		ProjectResponse response = projectService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponse> findById(@PathVariable Long id) {
		return projectService.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<ProjectResponse>> findAll() {
		List<ProjectResponse> list = projectService.findAll();
		return ResponseEntity.ok(list);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponse> update(
		@PathVariable Long id,
		@Valid @RequestBody ProjectUpdateRequest request
	) {
		return projectService.update(id, request)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return projectService.deleteById(id)
			? ResponseEntity.noContent().build()
			: ResponseEntity.notFound().build();
	}
}
