package com.example.jwt.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.backend.entity.Users;
import com.example.jwt.backend.service.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessagesController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@CrossOrigin(origins = "http://localhost:3007")  
	@GetMapping("/messages")
	public ResponseEntity<List<String>> messages() {
		ArrayList responseArray = new ArrayList(Arrays.asList("Welcome","Login to see protected content."));
		System.out.println("responseArray => " + responseArray);
		return ResponseEntity.ok(responseArray);
	}

	@CrossOrigin(origins = "http://localhost:3007")  
	@GetMapping("/api/messages")
	public ResponseEntity<Object> listAllHeaders(@RequestHeader Map<String, String> headers) {
//	    headers.forEach((key, value) -> {
//	    	log.info(String.format("Header '%s' = %s", key, value));
//	    });
	    
//	    System.out.println("authorization ========> " + headers.get("authorization"));
		
		String[] token = headers.get("authorization").split(" ");
	    if(jwtService.validateToken(token[1])) {	    	
	    	ArrayList responseArray = new ArrayList(Arrays.asList("first","last"));
	    	System.out.println("responseArray => " + responseArray);
	    	return ResponseEntity.ok(responseArray);
	    }
		
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorObject("Token is not valid", true));
	}
	
	@CrossOrigin(origins = "http://localhost:3007")  
	@GetMapping("/login")
	public ResponseEntity<Object> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		Users user = userService.findByUsername(username);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorObject("User not found", true));
		} else {
			String userPassword = user.getPassword();
			if(userPassword.equals(password)) {
                String tok = jwtService.generateToken(username, password);
                System.out.println("token =>>>> " + tok);
                Map<Object, Object> finalUserWithToken = new HashMap<>();
                finalUserWithToken.put("id", user.getId());
                finalUserWithToken.put("username", user.getUsername());
                finalUserWithToken.put("password", user.getPassword());
                finalUserWithToken.put("firstname", user.getFirstname());
                finalUserWithToken.put("lastname", user.getLastname());
                finalUserWithToken.put("role", user.getRole());
                finalUserWithToken.put("token", tok);          
				return ResponseEntity.ok(finalUserWithToken);
			} else {				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorObject("Password does not match", true));
			}
		}
	}
	
	@CrossOrigin(origins = "http://localhost:3007") 
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody Users users) {
		Users userExists = userService.findByUsername(users.getUsername());
		System.out.println("uerss in request body is ===> " + userExists);
		if(userExists == null) {			
			Users user = userService.saveUserAndFlush(users);
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorObject("Username exists", true));
	}
	
	

	public Map<Object, Object> getErrorObject(String message, boolean isError) {
		 Map<Object, Object> errorObj = new HashMap<>();
		 errorObj.put("error", isError);
		 errorObj.put("message", message);
		 return errorObj;
	}
}
