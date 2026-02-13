package com.prototie.concierge.user.service;

import com.prototie.concierge.user.dto.UserRequest;
import com.prototie.concierge.user.dto.UserResponse;
import com.prototie.concierge.user.dto.UserUpdateRequest;
import com.prototie.concierge.user.entity.User;
import com.prototie.concierge.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private static final String DEFAULT_ROLE = "USER";

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UserResponse create(UserRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + request.getEmail());
		}
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		User user = User.builder()
			.email(request.getEmail())
			.password(encodedPassword)
			.name(request.getName())
			.role(request.getRole() != null ? request.getRole() : DEFAULT_ROLE)
			.build();
		User saved = userRepository.save(user);
		return UserResponse.from(saved);
	}

	public Optional<UserResponse> findById(Long id) {
		return userRepository.findById(id)
			.map(UserResponse::from);
	}

	public Optional<UserResponse> findByEmail(String email) {
		return userRepository.findByEmail(email)
			.map(UserResponse::from);
	}

	public List<UserResponse> findAll() {
		return userRepository.findAll().stream()
			.map(UserResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public Optional<UserResponse> update(Long id, UserUpdateRequest request) {
		return userRepository.findById(id)
			.map(user -> {
				if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())
					&& userRepository.existsByEmail(request.getEmail())) {
					throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + request.getEmail());
				}
				String encodedPassword = request.getPassword() != null
					? passwordEncoder.encode(request.getPassword())
					: null;
				user.update(
					request.getEmail(),
					encodedPassword,
					request.getName(),
					request.getRole()
				);
				return UserResponse.from(user);
			});
	}

	@Transactional
	public boolean deleteById(Long id) {
		return userRepository.findById(id)
			.map(user -> {
				userRepository.delete(user);
				return true;
			})
			.orElse(false);
	}
}
