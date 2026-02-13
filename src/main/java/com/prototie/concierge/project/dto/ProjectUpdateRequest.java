package com.prototie.concierge.project.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateRequest {

	private static final int NAME_MAX_LENGTH = 200;
	private static final int DESCRIPTION_MAX_LENGTH = 1000;
	private static final int STATUS_MAX_LENGTH = 50;

	@Size(max = NAME_MAX_LENGTH)
	private String name;

	@Size(max = DESCRIPTION_MAX_LENGTH)
	private String description;

	@Size(max = STATUS_MAX_LENGTH)
	private String status;

	@Builder
	public ProjectUpdateRequest(String name, String description, String status) {
		this.name = name;
		this.description = description;
		this.status = status;
	}
}
