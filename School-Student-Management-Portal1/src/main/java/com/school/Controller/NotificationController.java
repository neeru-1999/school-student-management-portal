package com.school.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	  private JavaMailSender emailSender;

	  public void sendEmail(String email, String subject, String text) throws MessagingException {
	    MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    helper.setFrom("neerajaroyal1999@gmail.com");
	    helper.setTo(email);
	    helper.setSubject(subject);
	    helper.setText(text, true);

	    emailSender.send(message);
	  }
}
