package com.college.erp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.college.erp.model.UserModel;
import com.college.erp.repository.UserRepository;
import com.college.erp.request.LoginRequest;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	
	public UserModel saveUser(UserModel user) {
		return userRepository.save(user);
	}


	public Map<String, Object> loginUser(LoginRequest request) throws Exception {
	   
		Optional<UserModel> response = userRepository.findById(request.getUserName());
		
		if(response.isPresent()) {
			UserModel user =	response.get();
			if(user.getPassword().equals(request.getPassword())) {
				System.out.println("User login Successfull!");
				Map<String, Object> responseApi= new HashMap<>();
				responseApi.put("result", "login Successfull!");
				return responseApi;
			}else {
			    throw new Exception("Wrong Password");
			}
		}
		else {
			throw new Exception ("This email id is not registered.");
		}
	}


	public UserModel getUserByEmailId(String email) throws Exception {
		
		Optional<UserModel> response =	userRepository.findById(email);
		
		if(response.isPresent())
		{
			UserModel user= response.get();
			user.setPassword("");
			user.setVerificationCode("");
			return user;
		}
		else {
			throw new Exception("User Not Found!");
		}
	}
}
