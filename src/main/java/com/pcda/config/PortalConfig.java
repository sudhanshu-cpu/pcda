package com.pcda.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcda.common.model.ServerDetails;
import com.pcda.common.model.ServiceDetails;
import com.pcda.util.DODLog;
import com.pcda.util.PcdaConstant;

@Configuration
public class PortalConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	 
		PoolingHttpClientConnectionManager connectionManager=new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(500); 
		connectionManager.setDefaultMaxPerRoute(500); 
		
		HttpClient  httpClient=HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.build();
				                     
		
		return builder
			.setConnectTimeout(Duration.ofMinutes(10))
			.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
			.build();
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource=
				    new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasenames("classpath:/messages"); 
		
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	
	public static ServiceDetails serviceDetails() {
		ServiceDetails serviceDetails=new ServiceDetails();
		String home = System.getenv("JBOSS_HOME");
		 DODLog.info(0, PcdaConstant.class, ":: home::" +  home);
		 try {
			  byte[] jsonData = Files.readAllBytes(Paths.get(home+"/standalone/serviceDetails.json"));
			  DODLog.info(0, PcdaConstant.class, ":: jsonData::" + jsonData);
				ObjectMapper objectMapper = new ObjectMapper();
				ServerDetails emp = objectMapper.readValue(jsonData, ServerDetails.class);
				
				serviceDetails.setServiceHost(emp.getServer());
				DODLog.info(0, PcdaConstant.class, ":: ServiceHost::" + serviceDetails.getServiceHost());
		    } catch (Exception e) {
			
			DODLog.printStackTrace(e, PcdaConstant.class, 0);
		} 
	    return serviceDetails;
	}

}


