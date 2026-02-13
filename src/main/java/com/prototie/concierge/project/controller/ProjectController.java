package com.prototie.concierge.project.controller;

import com.prototie.concierge.project.dto.ProjectRequest;
import com.prototie.concierge.project.dto.ProjectResponse;
import com.prototie.concierge.project.dto.ProjectUpdateRequest;
import com.prototie.concierge.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Project", description = "프로젝트 CRUD API")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성", description = "새 프로젝트를 생성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "생성 성공",
			content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 검증 실패")
	})
	@PostMapping
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request) {
		ProjectResponse response = projectService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "프로젝트 단건 조회", description = "ID로 프로젝트를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
		@ApiResponse(responseCode = "404", description = "프로젝트 없음")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponse> findById(
		@Parameter(description = "프로젝트 ID") @PathVariable Long id) {
		return projectService.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "프로젝트 목록 조회", description = "전체 프로젝트 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjectResponse.class))))
	@GetMapping
	public ResponseEntity<List<ProjectResponse>> findAll() {
		List<ProjectResponse> list = projectService.findAll();
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "프로젝트 수정", description = "기존 프로젝트를 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = ProjectResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 검증 실패"),
		@ApiResponse(responseCode = "404", description = "프로젝트 없음")
	})
	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponse> update(
		@Parameter(description = "프로젝트 ID") @PathVariable Long id,
		@Valid @RequestBody ProjectUpdateRequest request
	) {
		return projectService.update(id, request)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "삭제 성공"),
		@ApiResponse(responseCode = "404", description = "프로젝트 없음")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
		@Parameter(description = "프로젝트 ID") @PathVariable Long id) {
		return projectService.deleteById(id)
			? ResponseEntity.noContent().build()
			: ResponseEntity.notFound().build();
	}
}
