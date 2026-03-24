package com.pcda.login.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.ContentResponse;
import com.pcda.common.model.SecurityQuestionModel;
import com.pcda.common.model.VisitorModel;
import com.pcda.login.model.GetSessionModel;
import com.pcda.login.model.GetSessionsResponse;
import com.pcda.login.model.GetUpdateOrSaveResponse;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.PostPreventProcessUpdate;
import com.pcda.login.model.PostQuestionSaveModel;
import com.pcda.login.model.PostSaveSessionDataModel;
import com.pcda.login.model.PostUpdateSessionModel;
import com.pcda.login.model.PreventProcessUpdateResponse;
import com.pcda.login.model.SaveQuestionResponse;
import com.pcda.login.model.VisitorResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {

	@Autowired
	private RestTemplate restTemplate;

	

	public Map<String, String> getConetntData() {
		Map<String, String> resultMap = new HashMap<>();
		ContentResponse contentResponse = restTemplate.getForObject(PcdaConstant.CONTENT_URL + "/getAllContent", ContentResponse.class);
		if (contentResponse != null && contentResponse.getErrorCode() == 200 && contentResponse.getResponse() != null) {
			resultMap.putAll(contentResponse.getResponse());
		}
		return resultMap;
	}

	
	public int getDaysBetween (Calendar d1,Calendar d2) {
		if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
	        d1 = d2;
	        d2 = swap;
	    } 
	    int days = d2.get(Calendar.DAY_OF_YEAR) -
	               d1.get(Calendar.DAY_OF_YEAR);
	    int y2 = d2.get(Calendar.YEAR);
	    if (d1.get(Calendar.YEAR) != y2) {
	        d1 = (Calendar) d1.clone();
	        do {
	            days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
	            d1.add(Calendar.YEAR, 1);
	        } while (d1.get(Calendar.YEAR) != y2);
	    }
	    return days;
	}



// is first time login
	public boolean isFirstTimeLogin(LoginUser loginUser) {
	
			try {
				int numberLogin=Optional.ofNullable(loginUser.getNumberLogin()).orElse(0);
				DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginService.class,":isFirstTimeLogin():Login Count "+numberLogin); 
				if(numberLogin !=0){
					return false;
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
				return false;
			}
			return true;
		
		
	}
	
	public int generateRandomNumber(){
		int randomNumber=0; 
		int start=1;
		int end=8;
		try {
			Random randomGenerator = new Random();
			long range = (long)end - (long)start + 1;
			long fraction = (long)(range * randomGenerator.nextDouble());
			randomNumber =  (int)(fraction + start);  
			DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginService.class,"():Question Number = "+randomNumber); 			
		} catch (Exception e) {
			DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
		}
		return randomNumber; 
	}	
	


// certificate
public String getCertificateExpiredMessage(LoginUser loginUser){
	String message="";
	
		try {
			if(loginUser.getDigiCertDetails()!=null && loginUser.getDigiCertDetails().length()>10)
	    	{
				InputStream inStream = new ByteArrayInputStream(loginUser.getDigiCertDetails().getBytes());
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				X509Certificate certificate = (X509Certificate) cf.generateCertificate(inStream);

	    			if(certificate!=null)
	    			{
	    				Calendar calendar=Calendar.getInstance();
	    				calendar.add(Calendar.DAY_OF_MONTH, 60);
	    				Date today=calendar.getTime();
	    				if(certificate.getNotAfter().before(today)){
	    					message="Dear User, Your Token is going to expire on "+CommonUtil.formatDate(certificate.getNotAfter(), "dd-MM-yyyy")+". Please get it renewed from DTS Office.";
	    				}
	    			}
	    	}
		} catch (Exception e) {
			DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
		}
		
		return message; 
	}

// get Specific Security Question

public SecurityQuestionModel getSpecificQuestion(Set<SecurityQuestionModel> securityQuestionSet) { 
	
	SecurityQuestionModel question = new SecurityQuestionModel();
	try {
	int questionNo = generateRandomNumber();
	List<SecurityQuestionModel> questionList = new  ArrayList<>();
	questionList.addAll(securityQuestionSet);
	SecurityQuestionModel randomQuestion = questionList.get(questionNo-1);
	question.setAnswer(randomQuestion.getAnswer());
	question.setQuestion(Base64Coder.decodeString(randomQuestion.getQuestion()));
	}catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	return question;
}

// validate password
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


// first time login change password
public VisitorResponse getFirstChangePasswd(String newPassword, BigInteger userId,String oldPassword) {
	VisitorResponse response = new VisitorResponse();
	VisitorModel visitModel =  new VisitorModel();
	 
	boolean validPass = validatePassword(newPassword);
	if(validPass) {
	 visitModel.setPassword(newPassword);
	 visitModel.setUserId(userId);
	 visitModel.setOldPassword(oldPassword);
		DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class,":Change Pass Visitor Model : "+visitModel); 

		
	try {
		String url= PcdaConstant.USER_BASE_URL+"/changePassword";
		HttpEntity<VisitorModel> requestEntity = new HttpEntity<>(visitModel);
		
		ResponseEntity<VisitorResponse> responseEntity = restTemplate.exchange(
				url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<VisitorResponse>() {
				});
		
		response = 	responseEntity.getBody();	
		return response;
	}
	catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	
	}else {
		
		response.setErrorMessage("Password Is Not Valid As Per PCDA Password Policy");
	}
	
	
	return response;
}

// save first login Security Question
public SaveQuestionResponse saveSecurityQuestion(HttpServletRequest request, BigInteger userId) {
	SaveQuestionResponse response = new SaveQuestionResponse();
	Map<String,String> questionMap = new HashMap<>();
	PostQuestionSaveModel visitModel = new PostQuestionSaveModel();
	try {
		String secret = "Hidden Pass";
		for(int i=1;i<9;i++) {
			String question = request.getParameter("question"+i);
			String answer =request.getParameter("answer"+i);
			answer=getDecryptText(secret, answer);
			
			questionMap.put(question, answer);
			}
		visitModel.setLoginUserId(userId);
		visitModel.setAnswer(questionMap);
		DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CustomAuthenticationProvider.class,":Save Questions Visitor Model : "+visitModel); 
		String url = PcdaConstant.USER_BASE_URL+"/securityAnswer";
		
		
      HttpEntity<PostQuestionSaveModel> requestEntity = new HttpEntity<>(visitModel);
		
		ResponseEntity<SaveQuestionResponse> responseEntity = restTemplate.exchange(
				url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<SaveQuestionResponse>() {
				});
		
		response = 	responseEntity.getBody();	
		
	}catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	return response;
}


// update Login User Contact info

public VisitorResponse updateContactInfo(BigInteger userId,String email, String contactNumber) {
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginService.class, "userId :: "+userId + " | email :: " + email + " | contactNumber :: "+contactNumber);
	
	VisitorResponse response = new VisitorResponse();
	VisitorModel visitModel =  new VisitorModel();
	visitModel.setUserId(userId);
	visitModel.setEmail(email);
	visitModel.setMobileNo(contactNumber);
	visitModel.setActionType("contectInfo");
	try {
		String url= PcdaConstant.USER_BASE_URL+"/updateByUserId";
	  HttpEntity<VisitorModel> requestEntity = new HttpEntity<>(visitModel);
			
			ResponseEntity<VisitorResponse> responseEntity = restTemplate.exchange(
					url, HttpMethod.PUT, requestEntity,
					new ParameterizedTypeReference<VisitorResponse>() {
					});
			
			response = 	responseEntity.getBody();
			
		return response;
	}
	catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}

	return response;
}


// check last mod contact details

public boolean checkLastModDate(Date modDate) {
	boolean flag= true;
	  Calendar calendar=Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, -15);
      Date date = calendar.getTime();
       if(null==modDate || modDate.before(date)){  
    	  flag= false;
      }
	return flag;
}






// update Login Attempt
public void updateLoginAttempt(BigInteger userId) {
	VisitorModel visitModel = new VisitorModel();
	visitModel.setUserId(userId);
	visitModel.setActionType("loginAttemt");
	
	try {
		String url =  PcdaConstant.USER_BASE_URL+"/updateByUserId";
		 restTemplate.put(url,visitModel);
	}
	catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
}



//update some data before sending to login
public void setFinanLogin(BigInteger userId) {
	VisitorModel visitModel = new VisitorModel();
	visitModel.setUserId(userId);
	visitModel.setActionType("login");
	
	try {
		String url =  PcdaConstant.USER_BASE_URL+"/updateByUserId";
		 restTemplate.put(url,visitModel);
	}
	catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	
}


// prevent process update
public void preventProcessUpdate(BigInteger userId, String process, String processState, String groupId, String actionType) {
PostPreventProcessUpdate postModel = new PostPreventProcessUpdate();
postModel.setLoginUserId(userId);
postModel.setProcess(process);
postModel.setProcessState(processState);
postModel.setGroupId(groupId);
postModel.setActionType(actionType);

try {
	String url= PcdaConstant.REQUEST_PREVENT_PROCESS_URL+"/update";
  HttpEntity<PostPreventProcessUpdate> requestEntity = new HttpEntity<>(postModel);
		
		 restTemplate.exchange(
				url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<PreventProcessUpdateResponse>() {
				});

}
catch(Exception e) {
	DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
}


}

// get sessionsList
public List<GetSessionModel> getSessions(BigInteger userId, String groupId) {
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG,  LoginService.class," sessions ::userId: "+userId + " :: groupId"+groupId); 
	GetSessionsResponse response = new GetSessionsResponse();
	List<GetSessionModel> modelList = new ArrayList<>();
	try {
		String url= PcdaConstant.SESSION_TRACKING_URL+"/getSessions?userId="+userId+"&groupId="+groupId;
		response = restTemplate.getForObject(url, GetSessionsResponse.class);
		if(response!=null && response.getErrorCode()==200) {
			modelList=response.getResponseList();
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG,  LoginService.class," session model list ::"+modelList ); 
	return modelList;
	
}


// insert call
public void insertSessionRow(BigInteger userId, String groupId, String sessionId, int sessionStatus) {
	PostSaveSessionDataModel postSaveModel = new PostSaveSessionDataModel();
	postSaveModel.setUserId(userId);
	postSaveModel.setSessionId(sessionId);
	postSaveModel.setGroupId(groupId);
	postSaveModel.setStatus(sessionStatus);
    try {
		String url= PcdaConstant.SESSION_TRACKING_URL+"/saveSessionData";
		restTemplate.postForObject(url,postSaveModel, GetUpdateOrSaveResponse.class);
		
	}catch(Exception e) {
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
    	}
    	
}


// update session
public void updateSession(BigInteger userId, String groupId, String sessionId, int sessionStatus) {
	PostUpdateSessionModel postUpdateModel = new PostUpdateSessionModel();
	postUpdateModel.setUserId(userId);
	postUpdateModel.setSessionId(sessionId);
	postUpdateModel.setGroupId(groupId);
	postUpdateModel.setStatus(sessionStatus);
	
	
	try {
		String url= PcdaConstant.REQUEST_PREVENT_PROCESS_URL+"/update";
	  HttpEntity<PostUpdateSessionModel> requestEntity = new HttpEntity<>(postUpdateModel);
       
			 restTemplate.exchange(
					url, HttpMethod.PUT, requestEntity,
					new ParameterizedTypeReference<GetUpdateOrSaveResponse>() {
					});

	}
	catch(Exception e) {
    	DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
    }
}

public void removeVerificationMessageFromSession() {
    try {
    	RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();
    	if(requestAttributes!=null) {
    		 HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
    	     HttpSession session = request.getSession();
    	     session.removeAttribute("windowNameToSet");
    	}
    	
       
    } catch (RuntimeException e) {
    	DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
    }
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
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
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
		DODLog.printStackTrace(e, LoginService.class, LogConstant.VISITOR_LOGIN_LOG);
		return new  byte[2][];
	} finally {
        // Clean out temporary data
        Arrays.fill(generatedData, (byte)0);
    }
}



}
