package com.hops.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpl 
{
	Random random=new Random(100000);
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender="kpshah1610@gmail.com";

	public int otp;
	
	public int getotp;
	
	
		
	public SimpleMailMessage sendSimpleMail(String email,int otp) {
		
		
			SimpleMailMessage mailMessage=new SimpleMailMessage();
//			
//			mailMessage.setFrom(sender);
//			mailMessage.setTo(details.getRecipient());
//			mailMessage.setText(details.getMsgBody());
//			mailMessage.setSubject(details.getSubject());
			
			mailMessage.setFrom(sender);
			mailMessage.setTo(email);
			mailMessage.setSubject("send mail through otp");
			mailMessage.setText("otp send "+otp);
//			
			javaMailSender.send(mailMessage);
			
			return mailMessage;
			
			
		}
	
		
		public int verifyOtp(int sendotp)
		{
			System.out.println("verify otp service:" +otp);
			
			if(otp==sendotp)
			{
			
				System.out.println("verify the otp"+sendotp);
				return otp;
				
			}
			else {
				System.out.println("incorrect otp");
				return sendotp;
			}
			
			
		}
		
		public void generateOtp()
		{
			otp=random.nextInt(993449);
			System.out.println("OTP" +otp);
		}
	
		public int getOtp()
		{
			System.out.println("true " + otp);
			return otp;
		}


	
	
	}
	


