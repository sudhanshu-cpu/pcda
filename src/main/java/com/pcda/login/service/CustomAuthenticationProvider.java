package com.pcda.login.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pcda.common.model.UserRole;
import com.pcda.login.model.LoginModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.LoginUserResponse;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		ServletRequestAttributes requestAttributes= (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request= requestAttributes.getRequest();
		HttpSession session= request.getSession();
		
		DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Client ip Address:"+getClientId(request));
		
		String userName=String.valueOf(authentication.getName());
		String password=String.valueOf(authentication.getCredentials());
		
		if(null==userName || userName.equals("") || null==password || password.equals("")) {
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Login details not found");
			throw new BadCredentialsException(messageSource.getMessage("login.failed", null, Locale.ENGLISH));
		}
		
	//	password=Base64Coder.decodeString(getStringByHex(password)); 
		
		String secretText="Hidden Pass";
		password=getDecryptText(secretText,password);
		
		validateUserName(userName);
		
		   if(!validatePassword(password)) {
			  DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Password Validation");
				throw new BadCredentialsException(messageSource.getMessage("error.pcda.login.passwordnotvalidperpcda", null, Locale.ENGLISH));
		  }
		
		 
		
		LoginUser loginUser=getLoginVisitorDetails(userName);
		
			if(loginUser==null) {
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "User Details not found:"+userName);
				throw new BadCredentialsException(messageSource.getMessage("login.failed", null, Locale.ENGLISH));
			}
		
			if(request.isSecure()) {
				
				isCertificateValidation(loginUser, request, session);
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "User certificate valid");
				
			}else {
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Request Is Not Secure, Unable To Login");
				session.invalidate();
				throw new BadCredentialsException(messageSource.getMessage("error.certificate.nosecurerequest", null, Locale.ENGLISH));
			}
			
			validateUser(loginUser,session);
			
			boolean isValid=isUserPasswordValid(userName, password, session);
			
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "User::"+loginUser.getUserAlias()+" | authenticate "+isValid);
			
			if(isValid) {
				
				setSessionData(session,loginUser);
				
				Set<String> roles=new HashSet<>();
				
				Optional<UserRole> userRole=loginUser.getUserRole().stream().findFirst();
				if(userRole.isPresent()) {
					roles.add(Optional.ofNullable(userRole.get().getRoleId().toString()).orElse(""));
				}
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "userRole:::::::::::"+userRole);
				
				List<String> allowRoles=new ArrayList<>();
				allowRoles.add(PcdaConstant.MASTER_BOOKER.toString());
				allowRoles.add(PcdaConstant.COMMANDING_OFFICER.toString());
				allowRoles.add(PcdaConstant.PAO_USER.toString());
				allowRoles.add(PcdaConstant.CDA_USER.toString());
				allowRoles.add(PcdaConstant.CGDA_USER.toString());
				allowRoles.add(PcdaConstant.ADG_MOV_USER.toString());
				allowRoles.add(PcdaConstant.SAO_USER.toString());
				allowRoles.add(PcdaConstant.AIR_SERVICE_PROVIDER_USER.toString());
				
				
				if(roles.stream().anyMatch(allowRoles::contains)){
				
				return new UsernamePasswordAuthenticationToken(loginUser.getUserAlias(), null, roles
						.stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
			}else {
					DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "unauthorized failed.");
					session.invalidate();
					throw new BadCredentialsException(messageSource.getMessage("login.unauthorized.failed", null, Locale.ENGLISH));
				}
				 
				
				
			}else {
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Login validation failed.");
				session.invalidate();
				throw new BadCredentialsException(messageSource.getMessage("login.failed", null, Locale.ENGLISH));
			}
			
		}
		
	private void validateUserName(String userName) {
		
		if(userName!=null && !userName.isBlank()) {
			String regex =  "^[a-zA-Z0-9]+$";
			boolean flag = userName.matches(regex);
			if(!flag) {
				throw new BadCredentialsException(messageSource.getMessage("special.character", null, Locale.ENGLISH));
			}
		}
		
	}
	
	
	
	private boolean validatePassword(String pwd){
		try {
		
			char[] pwdChar;
			boolean isNumber=false;
			boolean isSmallChar=false;
			boolean isCapitalChar=false;
			boolean isSplChar=false;
			int passwordLength=pwd.length();
			if(passwordLength<8){
				return false;
			}else{
				pwdChar=pwd.toCharArray();
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class,":validatePassword():Password characters = "+pwdChar.length);
				for(int i=0;i<pwdChar.length;i++){
					
					int asciiCode=pwd.charAt(i);
					
					if(asciiCode == 126 || asciiCode == 33 || asciiCode == 46 || asciiCode == 36 || asciiCode == 40 || asciiCode == 41 || asciiCode == 92){
						isSplChar=true;  
					}else if(asciiCode >= 48 && asciiCode <= 57){
						isNumber=true;
					}else if(asciiCode >= 65 && asciiCode <= 90){
						isCapitalChar=true;
					}else if(asciiCode >= 97 && asciiCode <= 122){
						isSmallChar=true;
					}
				}
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class,":validatePassword():isNumber = "+isNumber+" isSmallChar = "+isSmallChar+" isCapitalChar = "+isCapitalChar+" isSplChar = "+isSplChar); 
				if(!isNumber||!isSmallChar||!isCapitalChar||!isSplChar){
					return false;
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
			return false;
		}return true;
	}

	private void validateUser(LoginUser loginUser, HttpSession session) {
		
		// check for site user
				boolean isSiteUser = isSiteUser(loginUser);
					if(!isSiteUser) {
						session.invalidate();
						throw new BadCredentialsException(messageSource.getMessage("error.pc.usernotsiteuser", null, Locale.ENGLISH));
					}
				
				
			// check user approved or not 	
				int approvalState =loginUser.getApprovalState().ordinal();
				if(approvalState!=1) {
					session.invalidate();
					throw new BadCredentialsException(messageSource.getMessage("error.pc.usernotapproved", null, Locale.ENGLISH));
				}
				
			// 	check User Blocked or Not
				boolean canLogin= canLogin(loginUser);
				if(!canLogin) {
					session.invalidate();
					throw new BadCredentialsException(messageSource.getMessage("error.pcda.login.user.deactivated", null, Locale.ENGLISH));
				}
				
				Optional<UserRole> userRole=loginUser.getUserRole().stream().findFirst();
				
				if(userRole.isPresent() && Optional.ofNullable(userRole.get().getRoleId().toString()).orElse("").equals(PcdaConstant.COMMANDING_OFFICER.toString())) {
						
					boolean coActive =	isCoActive(loginUser);
					if(!coActive) {
						session.invalidate();
						throw new BadCredentialsException(messageSource.getMessage("error.user.notactive", null, Locale.ENGLISH));
					}
				}
		
				
		
	}
	
	// site user	
				private boolean isSiteUser(LoginUser loginUser){
					boolean isSiteUser=false;
					try {
						int userType= loginUser.getUserType().ordinal();
						if(userType== DODDATAConstants.ENUM_TRAVELER){
							return isSiteUser;
						}else isSiteUser=true; 
					} catch (Exception e) {
						DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
					}
					return isSiteUser;
				}	
				
				
		
	      // Can login or not
				private boolean canLogin(LoginUser loginUser){
					try {  
						int loginAttempt=Optional.ofNullable(loginUser.getLoginAttempt()).orElse(0);
						
						if(loginAttempt>=DODDATAConstants.LOGIN_ATTEMPT){  
							DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginService.class, "LGIN ATTEMPTS IN CAN LOGIN :"+ loginAttempt);
							return false;
						}  
					} catch (Exception e) {
						DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
						return false;
					}
					return true;
				}		
				
	// Co-Active	
	private boolean isCoActive(LoginUser loginUser) {			
		boolean isActive=false;
	try {	
		if(loginUser.getCoActive().getDisplayValue().equals("Yes")&&loginUser.getCoActive().ordinal()==0 ) {
			isActive=true;
		}
		
	}catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
		return isActive;
	}
				

	private LoginUser getLoginVisitorDetails(String userName) {
		
		LoginUser loginUser=null;
		
		ResponseEntity<LoginUserResponse> loginUserResponse= 
				restTemplate.getForEntity(PcdaConstant.USER_BASE_URL+"/completeUserByUserAlias/"+userName, LoginUserResponse.class);
		
		LoginUserResponse userResponse = loginUserResponse.getBody();
		
		if (userResponse != null && userResponse.getErrorCode() == 200 && userResponse.getResponse() != null) {
			
			loginUser= userResponse.getResponse();
		}
		
		return loginUser;
	}

	private void setSessionData(HttpSession session, LoginUser loginUser) {
		
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(session);
		sessionVisitor.setLoginUser(loginUser);
		
		Optional<UserRole> userRole=loginUser.getUserRole().stream().findFirst();
		if(userRole.isPresent()) {
			sessionVisitor.setRole(Optional.ofNullable(userRole.get().getRoleId().toString()).orElse("")); 
	}

		session.setAttribute("loginUser", loginUser.getName()); 
		
		String windowName=loginUser.getUserId()+"_"+Calendar.getInstance().getTimeInMillis();
		session.setAttribute("windowName", windowName);
		session.setAttribute("windowNameToSet", windowName);
	}

	private String getClientId(HttpServletRequest request) {
		
	 String ip = request.getHeader("X-FORWARDED-FOR");
       if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
         
        return ip;
	}

	private void isCertificateValidation(LoginUser loginUser, HttpServletRequest request, HttpSession session) {
		
		String digiCert=loginUser.getDigiCertDetails();
		
		if(null==digiCert || digiCert.equals("")) {
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "No digiCert Found For User:"+loginUser.getUserAlias());
			session.invalidate();
			throw new BadCredentialsException(messageSource.getMessage("error.certificate.notassignedtovisitor", null, Locale.ENGLISH));
		}
		
	    try {
			InputStream inStream = new ByteArrayInputStream(digiCert.getBytes());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) cf.generateCertificate(inStream);
			if(null==certificate) {
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "No digiCert Object created For User:"+loginUser.getUserAlias());
				session.invalidate();
				throw new BadCredentialsException(messageSource.getMessage("error.no.certificate.inrequest", null, Locale.ENGLISH));
			}
			 
			X509Certificate[] requestCert=  (X509Certificate []) request.getAttribute ("jakarta.servlet.request.X509Certificate");
			if(null==requestCert) {
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "No digiCert Object For request User:"+loginUser.getUserAlias());
				session.invalidate();
				throw new BadCredentialsException(messageSource.getMessage("error.no.certificate.inrequest", null, Locale.ENGLISH));
			}
			
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, certificate.getSigAlgName()+"  "+certificate.getVersion()+"  "+certificate.getSerialNumber());
			
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, " @@@@@ "+requestCert[0].getPublicKey()+" $$$$$$$ "+certificate.getPublicKey());
			
			
			if(!requestCert[0].getPublicKey().equals(certificate.getPublicKey())) {
				
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, "Digital certificate not match for User:"+loginUser.getUserAlias());
				session.invalidate();
				throw new BadCredentialsException(messageSource.getMessage("error.certificate.notmatched", null, Locale.ENGLISH));
			}
			
		} catch (CertificateException e) {
			DODLog.printStackTrace(e, CustomAuthenticationProvider.class, LogConstant.VISITOR_LOGIN_LOG);
			session.invalidate();
			throw new BadCredentialsException(messageSource.getMessage("error.certificate.notmatched", null, Locale.ENGLISH));
		} 
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class); 
	}
	
	public boolean isUserPasswordValid(String userName, String password, HttpSession session) {
		
		LoginModel loginModel=new LoginModel();
		loginModel.setUserAlias(userName);
		loginModel.setPassword(password);
		
		ResponseEntity<LoginUserResponse> loginUserResponse= 
				restTemplate.postForEntity(PcdaConstant.USER_BASE_URL+"/authenticate", loginModel, LoginUserResponse.class);
		
		LoginUserResponse userResponse = loginUserResponse.getBody();
		
		boolean authenticate=false;
		if (userResponse != null && userResponse.getErrorCode() == 200) {
		
			authenticate  = Boolean.valueOf(userResponse.getErrorMessage());
			session.setAttribute("jsToken", userResponse.getCustomMessage());
		}
		
		
		return authenticate;
	}
	
	private String getStringByHex (String txtInHex) { 
			String txt="";
			try{
				byte [] txtInByte = new byte [txtInHex.length() / 2];
				int j = 0;
				for (int i = 0; i < txtInHex.length(); i += 2)
				{
					txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
				}
				txt = new String(txtInByte);
			
			}catch (Exception e) {
				DODLog.error(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class, e.getMessage());
		}
		return txt;
	}

	
	public String getDecryptText(String secret,String cipherText) {
		String decryptedText ="";
		try {
			
		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = generateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
		
		}catch(Exception e ) {
			DODLog.printStackTrace(e, CustomAuthenticationProvider.class, LogConstant.VISITOR_LOGIN_LOG);
		}
		return decryptedText;
		
	}


	public static byte[][] generateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch(Exception e ) {
			DODLog.printStackTrace(e, CustomAuthenticationProvider.class, LogConstant.VISITOR_LOGIN_LOG);
			return new  byte[2][];
		} finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}
	
	
}
