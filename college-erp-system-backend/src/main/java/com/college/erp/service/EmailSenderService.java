package com.college.erp.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.college.erp.model.UserModel;
import com.college.erp.repository.UserRepository;
import com.college.erp.request.MailRequest;
import com.college.erp.request.OTPVerificationRequest;
import com.college.erp.request.UpdatePasswordRequest;





@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Map<String, Object> sendEmail(MailRequest request) throws Exception {
		
		
		boolean userStatus = userRepository.existsById(request.getToEmail());
		if(!userStatus) {
			throw new Exception("This mail is not registered.");
		}
		String textOTP = getTextMessage();
		String emailSubject = "One Time Password From College.";
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromMail);
		message.setTo(request.getToEmail());
		message.setText("Please don't share verification code with anyone, This is your verification code: "+textOTP);
		message.setSubject(emailSubject);
		
		 Map<String, Object> response = new HashMap<>();
		 
		mailSender.send(message);
		Optional<UserModel> userModel = userRepository.findById(request.getToEmail());
		if(userModel.isPresent()) {
			UserModel user = userModel.get();
			user.setUpdationDate(new Date());
			user.setVerificationCode(textOTP);
			userRepository.save(user);
		}
		
		
		
		response.put("result", "Mail Sended Successfully...");
		return response;
		
	}
	
	private String getTextMessage() {
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
	
	public Map<String, Object> OTPVerification(OTPVerificationRequest verificationRequest) throws Exception{
		Optional<UserModel> userModel = userRepository.findById(verificationRequest.getToEmail());
		if(!userModel.isPresent()) {
			throw new Exception ("User Not Found!");
		}
		UserModel user = userModel.get();
		if(user.getVerificationCode()!= null && user.getVerificationCode().equals(verificationRequest.getVerificationCode())) {
			
			Map<String, Object> response = new HashMap<>();
			response.put("result", "Verification Successfully!");
			user.setUpdationDate(new Date());
			user.setVerificationCode(null);
			userRepository.save(user);
			return response;
		}
		else {
			throw new Exception ("Invalid OTP , Please Enter Correct OTP!");
		}
		
	}

	public Map<String, Object> updatePassword(UpdatePasswordRequest request) throws Exception {
		Optional<UserModel> userModel = userRepository.findById(request.getToEmail());
		if(!userModel.isPresent()) {
			throw new Exception ("User Not Found!");
		}
		UserModel user = userModel.get();
		
		Map<String, Object> response = new HashMap<>();
		response.put("result", "Updated Successfully!");
		user.setUpdationDate(new Date());
		user.setPassword(request.getNewPassword());
		userRepository.save(user);
		return response;
		
	}
		
	

}
