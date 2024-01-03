package com.example.jwt.backend.repository;



import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.example.jwt.backend.entity.Users;

import java.util.Optional; 
  
@Repository
public interface UserRepository extends JpaRepository<Users, Long> { 
  Users findByUsername(String name);
  Users saveAndFlush(Users user); 
}
