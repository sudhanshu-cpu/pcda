package com.pcda.common.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.ForgotPasswdResponse;
import com.pcda.common.model.SecurityQuestionModel;
import com.pcda.common.model.UserStringResponse;
import com.pcda.login.service.LoginService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ForgetPasswordService {
	
@Autowired
private RestTemplate restTemplate;
	

	public ForgotPasswdResponse sendPno( String pNo) {
		
		try {
		return restTemplate.getForObject(PcdaConstant.USER_BASE_URL+"/getForgotPersonalNo?personalNo="+pNo,ForgotPasswdResponse.class);
		
			}catch (Exception e) {
			DODLog.printStackTrace(e, ForgetPasswordService.class, LogConstant.VISITOR_LOGIN_LOG);
		}
		
		return null;
	}
	
	
	// get Specific Security Question

	public SecurityQuestionModel getSpecificQuestion(Set<SecurityQuestionModel> securityQuestionSet) { 
		SecurityQuestionModel question = new SecurityQuestionModel();
		int questionNo = generateRandomNumber();
		List<SecurityQuestionModel> questionList = new  ArrayList<>();
		questionList.addAll(securityQuestionSet);
		SecurityQuestionModel randomQuestion = questionList.get(questionNo-1);
		question.setAnswer(randomQuestion.getAnswer());
		question.setQuestion(Base64Coder.decodeString(randomQuestion.getQuestion()));
		
		return question;
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
	
	public UserStringResponse sendChangeNewPassReq(BigInteger userId) {
		try {
		return restTemplate.getForObject(PcdaConstant.USER_BASE_URL+"/changeToNewPassword?userId="+userId,UserStringResponse.class);
		}catch (Exception e) {
			DODLog.printStackTrace(e, ForgetPasswordService.class, LogConstant.VISITOR_LOGIN_LOG);
		}
		return null;
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
