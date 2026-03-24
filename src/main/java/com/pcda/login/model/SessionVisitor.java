package com.pcda.login.model;

import java.io.Serializable;

import org.springframework.web.context.annotation.SessionScope;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SessionScope
public class SessionVisitor implements Serializable{

	private static final long serialVersionUID = -6198705901494630443L;

	private LoginUser loginUser;
	private String role;
	
	public static SessionVisitor getInstance(HttpSession session) {
		
		Class<SessionVisitor> clazz=SessionVisitor.class;
		String className=clazz.getName();
		SessionVisitor sessionVisitor= (SessionVisitor)session.getAttribute(className);
		if(null==sessionVisitor) {
			sessionVisitor=getInstance(session,clazz);
		}else {
			if(clazz.isInstance(sessionVisitor)) {
				return sessionVisitor;
			}
		}
		
		return sessionVisitor;
	}

	private static synchronized SessionVisitor getInstance(HttpSession session, Class<SessionVisitor> clazz) { 
		
		String className=clazz.getName();
		SessionVisitor sessionVisitor= (SessionVisitor)session.getAttribute(className);
		if(sessionVisitor==null) {
			sessionVisitor=new SessionVisitor();
			session.setAttribute(className, sessionVisitor); 
		}
		
		return sessionVisitor;
	}
}
