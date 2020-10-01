package com.delivery.service;

import com.delivery.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	PasswordEncoder passwordEncoder;


}
