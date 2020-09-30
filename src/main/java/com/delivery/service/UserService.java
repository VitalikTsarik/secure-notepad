package com.delivery.service;

import com.delivery.entity.Role;
import com.delivery.entity.User;
import com.delivery.exception.LoginIsBusyException;
import com.delivery.repository.UserRepository;
import com.delivery.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userRepository
				.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User Not Found with login: " + login)
				);
	}

	public boolean existsByLogin(String login) {
		return userRepository.existsByLogin(login);
	}

	public User signUp(
			String login,
			String password,
			String firstName,
			String middleName,
			String lastName,
			Role role
	) throws LoginIsBusyException {
		if (existsByLogin(login)) {
			throw new LoginIsBusyException();
		}

		User user = new User();
		user.setLogin(login);
		user.setPassword(passwordEncoder.encode(password));
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setRole(role);

		return userRepository.save(user);
	}

	public User signIn(String login, String password) throws AuthenticationException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login, password)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return (User) authentication.getPrincipal();
	}

}
