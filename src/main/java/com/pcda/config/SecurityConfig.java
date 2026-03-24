package com.pcda.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.pcda.util.PcdaConstant;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable)
			 .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			                  .invalidSessionUrl("/login") .maximumSessions(1).maxSessionsPreventsLogin(false)
			                  .expiredUrl("/login?invalid-session=true").sessionRegistry(sessionRegistry())) 
			 .logout(logout-> logout.deleteCookies("JSESSIONID").logoutUrl("/logout")
		                  .logoutSuccessUrl("/login").invalidateHttpSession(true))
			
		     .formLogin(form->form.loginPage("/login").defaultSuccessUrl("/home",true).failureUrl("/login?error=true").permitAll())
		     .authorizeHttpRequests(auth->auth
		     .requestMatchers(mvc.pattern("/"),mvc.pattern("/css/**"),mvc.pattern("/js/**"),mvc.pattern("/images/**"),mvc.pattern("/accessDenied"),mvc.pattern("/fpwd/**"),mvc.pattern("/error")).permitAll()
		     .requestMatchers(mvc.pattern("/com/**"),mvc.pattern("/home"),mvc.pattern("/getPcdaQuestion"),mvc.pattern("/getPcdaAnswerCheck"),mvc.pattern("/getContactInfo")
		    		 ,mvc.pattern("/updateOrSkipInfo"),mvc.pattern("/pcdaLogin"),mvc.pattern("/getFirstPcdaLogin"),mvc.pattern("/firstLoginChangePwd"),
		    		 mvc.pattern("/authenticateFirstQuestions"),mvc.pattern("/saveQuestionAns"),mvc.pattern("/getChangePasswrdInLogin"),
		    		 mvc.pattern("/saveChangePasswordLogin"),mvc.pattern("/getQuesWithAnswer")).authenticated()
		     .requestMatchers(mvc.pattern("/reports/**")).hasAnyAuthority(PcdaConstant.MASTER_BOOKER.toString(),PcdaConstant.COMMANDING_OFFICER.toString())
		     .requestMatchers(mvc.pattern("/mb/**")).hasAuthority(PcdaConstant.MASTER_BOOKER.toString())
		     .requestMatchers(mvc.pattern("/co/**")).hasAuthority(PcdaConstant.COMMANDING_OFFICER.toString())
		     .requestMatchers(mvc.pattern("/pao/**")).hasAuthority(PcdaConstant.PAO_USER.toString())
		     .requestMatchers(mvc.pattern("/cda/**")).hasAuthority(PcdaConstant.CDA_USER.toString())
		     .requestMatchers(mvc.pattern("/cgda/**")).hasAuthority(PcdaConstant.CGDA_USER.toString())
		     .requestMatchers(mvc.pattern("/adg/**")).hasAuthority(PcdaConstant.ADG_MOV_USER.toString())
		     .requestMatchers(mvc.pattern("/sao/**")).hasAuthority(PcdaConstant.SAO_USER.toString())
		     .requestMatchers(mvc.pattern("/serpov/**")).hasAuthority(PcdaConstant.AIR_SERVICE_PROVIDER_USER.toString())
		     .anyRequest().denyAll()
		     )
		     .exceptionHandling(ex->ex.accessDeniedPage("/logout")) 
		
		     ;
		
		return httpSecurity.build();
	}
	
	@Bean
 	public WebSecurityCustomizer webSecurityCustomizer(MvcRequestMatcher.Builder mvc) {
 		return web -> web.ignoring()
 				.requestMatchers(mvc.pattern("/css/**"),mvc.pattern("/js/**"),mvc.pattern("/images/**"));
 	}
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector){
		return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}
}
