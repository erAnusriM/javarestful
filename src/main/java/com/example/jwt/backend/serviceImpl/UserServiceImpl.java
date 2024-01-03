package com.example.jwt.backend.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.backend.entity.Users;
import com.example.jwt.backend.repository.UserRepository;
import com.example.jwt.backend.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
    @Autowired
    private UserRepository userRepository; 
  
    @Override
	public Users findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    
    @Override
    public Users saveUserAndFlush(Users user) {
    	return userRepository.saveAndFlush(user);
    }

}
