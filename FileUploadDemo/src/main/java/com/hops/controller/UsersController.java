package com.hops.controller;

import java.util.List;
import java.util.Random;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.hops.model.Users;
import com.hops.repository.UsersRepository;
import com.hops.service.EmailServiceImpl;
import com.hops.service.SequenceGeneratedService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class UsersController {
	
	Random random = new Random();
	
	int generateOtp;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private SequenceGeneratedService service;
	
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	//Users us1;
	
	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody Users user)
	{
		System.out.println(user);
		
		Users u1 = this.userRepository.save(user);
		
		//user.setId(service.getSequenceNumber(Users.SEQUENCE_NAME));
		
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> getUsers(@RequestBody Users user,HttpSession session)
	{
		Users us1 = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		System.out.println("this "+us1);
		
		if(us1.getEmail().equals(user.getEmail()) && us1.getPassword().equals(user.getPassword()))
		{
		
			generateOtp =100000+ random.nextInt(999999);
			System.out.println("OTP " + generateOtp);
			
//			String subject = "Otp from scm";
//			String msgBody= "  OTP = "+generateOtp+" ";
//			String to="karanshah2312@gmail.com";         ////this line is created by me here tb7 tha
//			
//				this.emailServiceImpl.sendSimpleMail(subject,msgBody,to);
//				
//				session.setAttribute("myotp", generateOtp);
//			
//				//this line is created by me
//				userRepository.save(user);
			
			this.emailServiceImpl.sendSimpleMail(us1.getEmail(), generateOtp);
//				
			
			return ResponseEntity.ok(HttpStatus.OK);    
			
			
		}
		
		
		
		else {
			return ResponseEntity.ok(null);
		}
		
		
		
	}
	
	
//	@PostMapping("/verify/{otp}")
//	public ResponseEntity<HttpStatus> otpverify(@RequestParam("otp") int otp,HttpSession session)
//	{
//		int myOTP=(int) session.getAttribute("myotp");
//		
//		String email=(String) session.getAttribute("email");
//		
//		if(myOTP==otp)
//		{
//			return ResponseEntity.ok(HttpStatus.OK);
//		}
//		else {
//			session.setAttribute("message", "You have entered wrong otp");
//			return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
//		}
//		
//		
//	}
	

	@GetMapping("/verify/{getotp}")
	public ResponseEntity verifyOtp(@PathVariable int getotp)
	{
		
		System.out.println(getotp);
		
		
		
		if(generateOtp==getotp)
		{
			System.out.println(generateOtp);
			
			return new ResponseEntity(HttpStatus.OK).ok(generateOtp);
			
		}
		else {
			return new  ResponseEntity(HttpStatus.BAD_REQUEST);
			
		}
		
	
		
	}
	@GetMapping("/sendmail/{email}")
	public ResponseEntity<HttpStatus> sendemail(@PathVariable String email)
	{
		generateOtp =100000+ random.nextInt(999999);
		System.out.println("OTP " + generateOtp);

			this.emailServiceImpl.sendSimpleMail(email, generateOtp);
			
		//	session.setAttribute("myotp", generateOtp);
			
		//i create
			//userRepository.save(us1);
			
		return ResponseEntity.ok(HttpStatus.ACCEPTED);
		
	}
	
	
	
	
	
	

}
