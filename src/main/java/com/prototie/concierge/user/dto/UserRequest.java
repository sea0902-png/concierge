package com.prototie.concierge.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

	private static final int EMAIL_MAX_LENGTH = 255;
	private static final int PASSWORD_MIN_LENGTH = 8;
	private static final int PASSWORD_MAX_LENGTH = 100;
	private static final int NAME_MAX_LENGTH = 100;
	private static final int ROLE_MAX_LENGTH = 50;

	@NotBlank(message = "이메일은 필수입니다")
	@Email(message = "올바른 이메일 형식이 아닙니다")
	@Size(max = EMAIL_MAX_LENGTH)
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다")
	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "비밀번호는 8자 이상 100자 이하여야 합니다")
	private String password;

	@Size(max = NAME_MAX_LENGTH)
	private String name;

	@Size(max = ROLE_MAX_LENGTH)
	private String role;

	@Builder
	public UserRequest(String email, String password, String name, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}
}
