package com.college.erp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.erp.model.UserModel;
import com.college.erp.request.LoginRequest;
import com.college.erp.service.UserService;

@RestController
@RequestMapping("/api/erp/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("save")
	public UserModel saveUser(@RequestBody UserModel user) {
		
		return userService.saveUser(user);
	}

	@PostMapping("login")
	public Map<String, Object> loginUser(@RequestBody LoginRequest request) throws Exception {
		
		return userService.loginUser(request);
	}

	@GetMapping("get/{email}")
	public UserModel getUserByEmailId(@PathVariable String email) throws Exception {
		
		return userService.getUserByEmailId(email);
	}
	
	
}
