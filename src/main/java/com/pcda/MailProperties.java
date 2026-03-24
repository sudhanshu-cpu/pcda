package com.pcda;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;


@Data
@Lazy
@Configuration
@PropertySource("classpath:mail.properties")
//@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    @Value("${spring.mail.host}")
    private String host;
   
    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String transportProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String enableSmtpStartTls;

    @Value("${mail.properties.mail.smtp.ssl.trust}")
    private String smtpSslTrust;
 
    @Value("${mail.smtp.socketFactory.class}")
    private String sslSocketFactory;
    
    
}