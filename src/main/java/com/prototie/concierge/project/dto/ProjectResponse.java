package com.prototie.concierge.project.dto;

import com.prototie.concierge.project.entity.Project;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectResponse {

	private Long id;
	private String name;
	private String description;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public ProjectResponse(Long id, String name, String description, String status,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static ProjectResponse from(Project project) {
		return ProjectResponse.builder()
			.id(project.getId())
			.name(project.getName())
			.description(project.getDescription())
			.status(project.getStatus())
			.createdAt(project.getCreatedAt())
			.updatedAt(project.getUpdatedAt())
			.build();
	}
}
