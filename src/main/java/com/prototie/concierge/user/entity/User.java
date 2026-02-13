package com.prototie.concierge.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	private static final int EMAIL_MAX_LENGTH = 255;
	private static final int NAME_MAX_LENGTH = 100;
	private static final int ROLE_MAX_LENGTH = 50;
	private static final String DEFAULT_ROLE = "USER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = EMAIL_MAX_LENGTH)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(length = NAME_MAX_LENGTH)
	private String name;

	@Column(nullable = false, length = ROLE_MAX_LENGTH)
	private String role;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Builder
	public User(String email, String password, String name, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role != null ? role : DEFAULT_ROLE;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void update(String email, String password, String name, String role) {
		if (email != null) {
			this.email = email;
		}
		if (password != null) {
			this.password = password;
		}
		if (name != null) {
			this.name = name;
		}
		if (role != null) {
			this.role = role;
		}
		this.updatedAt = LocalDateTime.now();
	}
}
