package com.delivery.service;

import com.delivery.entity.User;
import com.delivery.repository.SessionsRepo;
import com.delivery.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SessionsRepo sessionRepo;

	public User signUp(String login, String password, String sessionKey) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		String openRsaKey = sessionRepo.getRsaKeysBySecretKey().get(sessionKey);
		user.setOpenRSAkey(openRsaKey);

		Optional<User> opt = userRepo.findByLogin(login);
		if (opt.isPresent()) {
			throw new AccessDeniedException("User with this login already registered");
		}
		opt = userRepo.findByOpenRSAkey(openRsaKey);
		if (opt.isPresent()) {
			throw new AccessDeniedException("User with this rsa already registered");
		}

		return userRepo.save(user);
	}

	public User signIn(String login, String password) throws IllegalAccessException {
		User user = userRepo.findByLogin(login).orElseThrow(() -> new NoSuchElementException("USer not found"));
		if (!user.getPassword().equals(password)) {
			throw new IllegalAccessException("Password is wrong");
		}

		return user;
	}
}
