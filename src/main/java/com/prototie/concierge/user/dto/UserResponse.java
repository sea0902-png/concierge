package com.prototie.concierge.user.dto;

import com.prototie.concierge.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

	private Long id;
	private String email;
	private String name;
	private String role;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public UserResponse(Long id, String email, String name, String role,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.role(user.getRole())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
