package com.deadshot.labrage_v3.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deadshot.labrage_v3.InputModel.OtpCheck;
import com.deadshot.labrage_v3.TableOtpGenerator.OtpGenerator;
import com.deadshot.labrage_v3.TableOtpGenerator.OtpGeneratorRepository;
import com.deadshot.labrage_v3.TableUserDetails.UserDetailsRepository;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/otp")
public class OtpController {

	private final int otpTimeLimit = 300;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	OtpGeneratorRepository otpGeneratorRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DeleteController deleteController;
	
	@Autowired
	AddUpdateController addUpdateController;
	
	@Autowired
	OtpController otpController;
	
	//service to send otp to mobile number
	@RequestMapping(method = RequestMethod.POST, value = "/send")
	public int OTPsend(@RequestBody String mob) {

		// 01 - otp sent
		// 90 - database issue
		
		System.out.println("otp_OTPsend -> sending otp " + mob);

		try {
			int randomInt = ThreadLocalRandom.current().nextInt(1000, 9999);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
			
			otpGeneratorRepository.save(new OtpGenerator(mob, String.valueOf(randomInt), dateTime));
			System.out.println("OTP generated for mobile " + mob + " " + randomInt);
			otpController.sendmail(randomInt);
			return 1;
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: OtpController -> OTPsend");
			System.out.println("return code : 90");
			return 90;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/check")
	public int OTPCheck(@RequestBody OtpCheck otp) {
		
		// 00 - mobile number not found in db
		// 01 - otp is correct & use is deleted
		// 02 - otp is wrong
		// 03 - otp is expired
		// 90 - database issue
		
		System.out.println("otp_OTPCheck -> checking otp with db " + otp.getOutputUser().getUserDetails().getMobile_primary() + "," + otp.getOtp_number());
		
		
		try {
			for(OtpGenerator otpRepo: otpGeneratorRepository.findAll()) {
				if(otpRepo.getMobile_number().equals(otp.getOutputUser().getUserDetails().getMobile_primary())) {
					LocalDateTime limit = LocalDateTime.now().minusSeconds(otpTimeLimit);
					if(otpRepo.getCreated_ts().isAfter(limit)) {
						if(otpRepo.getOtp_number().equals(otp.getOtp_number())) {
							System.out.println("command : " + otp.getCommand());
							if(otp.getCommand().equals("delete")) {
								int del_rc = deleteController.deleteUser(otp.getOutputUser().getUserDetails().getMobile_primary());
								System.out.println("return code : " + del_rc);
								return del_rc;
							}else if(otp.getCommand().equals("add") || otp.getCommand().equals("update")) {
								int add_rc = addUpdateController.newUser(otp);
								System.out.println("return code : " + add_rc);
								return add_rc;
							}
						}else {
							System.out.println("return code : 2");
							return 2;
						}
					}else {
						System.out.println("return code : 3");
						return 3;
					}
				}
			}
			
			System.out.println("return code : 0");
			return 0;
			
		}catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Error: OtpController -> OTPCheck");
			System.out.println("return code : 90");
			return 90;
		}
		

	}
	
	 private void sendmail(int randomInt1) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("tekbus29@gmail.com", "kishoresudar");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("tekbus29@gmail.com", false));
	
		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tekbus29@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());
		   msg.setText(String.valueOf(randomInt1));
		   Transport.send(msg);   
		}

}