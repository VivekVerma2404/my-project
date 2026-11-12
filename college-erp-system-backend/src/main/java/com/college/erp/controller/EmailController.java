package com.college.erp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.erp.request.MailRequest;
import com.college.erp.request.OTPVerificationRequest;
import com.college.erp.request.UpdatePasswordRequest;
import com.college.erp.service.EmailSenderService;




@RestController
@RequestMapping("/api/erp/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {
	
	
	@Autowired
	private EmailSenderService senderService;
	@PostMapping("/sendMail")
	public Map<String, Object> sendMail(@RequestBody MailRequest request) throws Exception {
		
		
		return senderService.sendEmail(request);
	}
	
	@PostMapping("/otp-verification")
	public Map<String, Object> OTPVerification(@RequestBody OTPVerificationRequest verificationRequest) throws Exception{
		
		return senderService.OTPVerification(verificationRequest);

	}
	
	@PostMapping("/update-password")
	public Map<String, Object> updatePassword(@RequestBody UpdatePasswordRequest request) throws Exception{
		
		return senderService.updatePassword(request);
	}
}
