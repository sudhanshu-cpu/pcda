package com.pcda.common.controller;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.ForgotPasswdResponse;
import com.pcda.common.model.GetForgotPassPnoInfo;
import com.pcda.common.model.SecurityQuestionModel;
import com.pcda.common.model.User;
import com.pcda.common.model.UserStringResponse;
import com.pcda.common.services.ForgetPasswordService;
import com.pcda.common.services.UserServices;
import com.pcda.login.service.CaptchaService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/fpwd")
public class ForgetPasswordController {
	
@Autowired 
private ForgetPasswordService forgetPasswordService;
	
@Autowired
private UserServices userServices;

@Autowired
private CaptchaService captchaService;

@GetMapping("/forgotPassword")
public String getForwardPassWord(HttpServletRequest request) {
 	

   return "/common/forgetPassword";	
	
}

@PostMapping("getForgotPasswrdPno")
public String getUserId(HttpServletRequest request,Model model,@RequestParam String personalNo,RedirectAttributes attributes) {
	
	String secret = "Hidden Pass";
	String pno=forgetPasswordService.getDecryptText(secret, personalNo);
	
	DODLog.info(LogConstant.LOGIN_FILTER_LOG, ForgetPasswordController.class, "Personal No :: " +pno);
	GetForgotPassPnoInfo infoModel=null;
	ForgotPasswdResponse response = forgetPasswordService.sendPno(pno);
	if(response!=null && response.getErrorCode()==200 && response.getErrorMessage().equals("OK")) {
		 infoModel = response.getResponse();
		 SecurityQuestionModel question = forgetPasswordService.getSpecificQuestion(infoModel.getSecurityQuestions());
	  
		
		 
		 model.addAttribute("question",question.getQuestion());
	     model.addAttribute("answer", Base64Coder.decodeString(question.getAnswer()));
		 model.addAttribute("userId",infoModel.getUserId());
		 
	}else if(response!=null&& response.getErrorCode()!=200) {
		attributes.addFlashAttribute("error",response.getErrorMessage());
		return "redirect:forgotPassword";
	}else {
		return "/common/errorPage";
	}
	
return "/common/forgotPswdVerify";
}

@PostMapping("forgotPswdVerify")
public String verifyUser(HttpServletRequest request,Model model,RedirectAttributes attributes,
		@RequestParam String regAnswer,@RequestParam String answer,@RequestParam String email,@RequestParam String userId ) {
	
	DODLog.info(LogConstant.LOGIN_FILTER_LOG, ForgetPasswordController.class, "securityQues :: " +regAnswer +" ::answer :: "+answer+" ::email ::"+email);
	
	String secret = "Hidden Pass";
	
	
	String regisAnswer = forgetPasswordService.getDecryptText(secret, regAnswer);
	String regUserId = forgetPasswordService.getDecryptText(secret, userId);
	
	String inpEmail = forgetPasswordService.getDecryptText(secret, email);
	
	
	BigInteger userIdd = BigInteger.valueOf(Long.valueOf(regUserId));
	String message="";
	
	
	String regisEmail ="";
	Optional<User> mappedUser = userServices.getUser(userIdd);
	
	DODLog.info(LogConstant.LOGIN_FILTER_LOG, ForgetPasswordController.class, " boolean"+mappedUser.isPresent() );
	if(mappedUser.isPresent()) {
		regisEmail=mappedUser.get().getEmail();
	}else {
		
		message="User is not pcda user";	
		attributes.addFlashAttribute("error", message);
		return "redirect:forgotPassword";
	}
	
if(regisAnswer!=null && !regisAnswer.isEmpty() && !regisAnswer.equals(answer)) {
		
		message="Entered Answer is incorrect";	
		attributes.addFlashAttribute("error", message);
		 return "redirect:forgotPassword";
		
	}
	
	if(regisEmail!=null && !regisEmail.isEmpty() && !regisEmail.equalsIgnoreCase(inpEmail)) {
		
		message="Entered Email is incorrect";	
		attributes.addFlashAttribute("error", message);
		 return "redirect:forgotPassword";
		
	}

 
 if(regisEmail!=null && regisAnswer!=null && regisAnswer.equals(answer) && regisEmail.equalsIgnoreCase(inpEmail)) {
	 
	 UserStringResponse response = forgetPasswordService.sendChangeNewPassReq(userIdd);
	 DODLog.info(LogConstant.LOGIN_FILTER_LOG, ForgetPasswordController.class, "Personal response :: " +response);
	 
	 if(response!=null) {
		 attributes.addFlashAttribute("error",response.getErrorMessage());
			return "redirect:forgotPassword";
	 }else {
		 return "/common/errorPage";
	 }
	 
 }
	
 return "/common/errorPage";
	
	
}

// ---captcha code---
@GetMapping("/captcha")
@ResponseBody
public void getCaptcha(HttpServletRequest request,HttpServletResponse response){
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CaptchaService.class,": inside captcha : "); 
   CompletableFuture<String> captchaText = captchaService.generateCaptchaText();
   try {
    String captcha = captchaText.get();
    HttpSession session = request.getSession();
    session.removeAttribute("captchaText");
    session.setAttribute("captchaText", captcha);
    captchaService.generateCaptchaImage(captchaText.get(),response);
   }catch (InterruptedException ie) {
       DODLog.printStackTrace(ie, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
       Thread.currentThread().interrupt();
   } catch (Exception ee) {
       DODLog.printStackTrace(ee, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
    
}


@PostMapping("/validateCaptcha")
public ResponseEntity<Boolean> validateCaptcha(@RequestParam String captchaInput,HttpServletRequest request) {
	HttpSession session = request.getSession();
  String captchaText=(String) session.getAttribute("captchaText");
	String genCaptcha="";
	if(captchaText instanceof String) {
		genCaptcha=captchaText;
	session.removeAttribute("captchaText");
	}
	Boolean flag = null;
	try {
	 flag=captchaService.validateCaptcha(captchaInput, genCaptcha).get();
}catch (InterruptedException ie) {
    DODLog.printStackTrace(ie, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
    Thread.currentThread().interrupt();
} catch (Exception ee) {
    DODLog.printStackTrace(ee, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
	}
	return ResponseEntity.ok(flag);
  
}
//----------

}
