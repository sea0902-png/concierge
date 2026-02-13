package com.prototie.concierge.user.controller;

import com.prototie.concierge.user.dto.UserRequest;
import com.prototie.concierge.user.dto.UserResponse;
import com.prototie.concierge.user.dto.UserUpdateRequest;
import com.prototie.concierge.user.service.UserService;
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

@Tag(name = "User", description = "사용자 CRUD API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "사용자 생성", description = "새 사용자를 생성합니다. 비밀번호는 BCrypt로 암호화되어 저장됩니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "생성 성공",
			content = @Content(schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 검증 실패 또는 이메일 중복")
	})
	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
		try {
			UserResponse response = userService.create(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Operation(summary = "사용자 단건 조회", description = "ID로 사용자를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "404", description = "사용자 없음")
	})
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(
		@Parameter(description = "사용자 ID") @PathVariable Long id) {
		return userService.findById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "사용자 목록 조회", description = "전체 사용자 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공",
		content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
	@GetMapping
	public ResponseEntity<List<UserResponse>> findAll() {
		List<UserResponse> list = userService.findAll();
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "사용자 수정", description = "기존 사용자를 수정합니다. 비밀번호를 보내면 암호화되어 저장됩니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 검증 실패 또는 이메일 중복"),
		@ApiResponse(responseCode = "404", description = "사용자 없음")
	})
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(
		@Parameter(description = "사용자 ID") @PathVariable Long id,
		@Valid @RequestBody UserUpdateRequest request
	) {
		try {
			return userService.update(id, request)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "삭제 성공"),
		@ApiResponse(responseCode = "404", description = "사용자 없음")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
		@Parameter(description = "사용자 ID") @PathVariable Long id) {
		return userService.deleteById(id)
			? ResponseEntity.noContent().build()
			: ResponseEntity.notFound().build();
	}
}
