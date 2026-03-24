package com.pcda.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pcda.MailProperties;

import lombok.AllArgsConstructor;

@EnableAutoConfiguration
@AllArgsConstructor
public class MailSenderConfiguration {
	@Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getHost());
       mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties javaMailProperties = mailSender.getJavaMailProperties();
        javaMailProperties.put("mail.smtp.auth", mailProperties.getSmtpAuth());
        javaMailProperties.put("mail.transport.protocol", mailProperties.getTransportProtocol());
        javaMailProperties.put("mail.smtp.starttls.enable", mailProperties.getEnableSmtpStartTls());
        javaMailProperties.put("mail.smtp.ssl.trust", mailProperties.getSmtpSslTrust());
        
        javaMailProperties.put("mail.smtp.socketFactory.class", mailProperties.getSslSocketFactory());
        javaMailProperties.put("mail.debug", "true");
        
        return mailSender;
    }

    
//    @Bean
//	public SimpleMailMessage emailTemplate()
//	{
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo("somebody@gmail.com");
//		message.setFrom("admin@gmail.com");
//	    message.setText("FATAL - Application crash. Save your job !!");
//	    return message;
//	}
//    
    
}