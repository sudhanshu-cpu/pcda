package com.pcda.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter(urlPatterns = {"/*"}) 
public class LoginUserValidateFilter extends OncePerRequestFilter{

	private static final List<String> EXCLUDE_URL_PATTERNS=Arrays.asList("/css/","/js/","/images/","/login","/logout","/mb/crisBookingResponse","/error","/accessDenied","/fpwd/");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "Browser: "+request.getHeader("user-agent")+" | Request IP: "+request.getRemoteAddr()); 
		DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "ServletPath: "+request.getServletPath()); 
		
		if(request.getServletPath().equals("/")) {
			filterChain.doFilter(request, response);
		}
		
		if(request.getCookies()==null || request.getCookies().length==0) {
			DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "Cookies is not present in request: "+request.getSession());
			request.getRequestDispatcher("/accessDenied").forward(request, response);
			return;
		}
		
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser= sessionVisitor.getLoginUser();
		
		if(null!=loginUser) {
			
		//	filterChain.doFilter(request, response);//remove this line when Certificate validation need to start
			
			DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "Login User: "+loginUser.getUserAlias());
			if(loginUser.getDigiCertDetails()==null || loginUser.getDigiCertDetails().isBlank()) {
				request.getRequestDispatcher("/logout");
				return;
			}
			
			if(validateTokenCertificate(loginUser, request)) { 
				DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "Request token validate");
				filterChain.doFilter(request, response);
			}else {
				DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class, "Request token not validate");
				request.getRequestDispatcher("/logout");
			}
			
		
		}else {
			request.getRequestDispatcher("/logout");
		}
		
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return EXCLUDE_URL_PATTERNS.stream().anyMatch(e->request.getServletPath().contains(e));  
	}

	
	private boolean validateTokenCertificate(LoginUser loginUser,HttpServletRequest request){
			
		DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"-------------------------------------------------------------------------- ");
		DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"-----validateTokenCertificte For Visitor ["+loginUser.getUserAlias()+"]----- ");
		DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"-------------------------------------------------------------------------- ");
		     boolean isTokenValid=false;

	     try
	     {
	    	
	    	 InputStream inStream = new ByteArrayInputStream(loginUser.getDigiCertDetails().getBytes());
			 CertificateFactory cf = CertificateFactory.getInstance("X.509");
			 X509Certificate certificate = (X509Certificate) cf.generateCertificate(inStream);
	         X509Certificate[] requestCert=  (X509Certificate []) request.getAttribute ("jakarta.servlet.request.X509Certificate");
	       	
	       	   
       	   	    if(!requestCert[0].getPublicKey().equals(certificate.getPublicKey()))
       	   	    {
       	   	    	DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"Certificate in request and Profile Certifcate doesnot match");
       	   	    }else{
       	   	    	isTokenValid=true;
       	   	    }	
    	
	       }catch (Exception e1)
	       {
	    	  DODLog.printStackTrace(e1, LoginUserValidateFilter.class, LogConstant.LOGIN_FILTER_LOG); 
	        	
	   	   }
	       
	     DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"validateTokenCertificte::isTokenValid="+isTokenValid);
	     DODLog.info(LogConstant.LOGIN_FILTER_LOG, LoginUserValidateFilter.class,"---------------------------------------------------- ");
	   
		return isTokenValid;
	}

}
