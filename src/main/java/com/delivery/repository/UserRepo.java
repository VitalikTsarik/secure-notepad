package com.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByLogin(String login);
	Optional<User> findByOpenRSAkey(String openRsaKey);

	Boolean existsByLogin(String login);
}
