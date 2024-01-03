package com.example.jwt.backend.service;

import java.util.Optional;

import com.example.jwt.backend.entity.Users;

public interface UserService {

	Users findByUsername(String username);
	Users saveUserAndFlush(Users user);

}