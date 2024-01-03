package com.example.jwt.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
  
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users { 
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@NotBlank(message = "username cannot be empty")
	@Column(name="username")
    private String username;
	@NotBlank(message = "firstname cannot be empty")
	@Column(name="firstname")
    private String firstname; 
	@NotBlank(message = "password cannot be empty")
	@Column(name="password")
    private String password;
	@NotBlank(message = "lastname cannot be empty")
	@Column(name="lastname")
    private String lastname;
	@NotBlank(message = "role cannot be empty")
	@Column(name="role")
    private String role; 
  
} 