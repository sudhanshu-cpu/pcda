package com.pcda.login.controller;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.SecurityQuestionModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.GetSessionModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.login.model.VisitorResponse;
import com.pcda.login.service.LoginService;
import com.pcda.mb.home.service.MBHomeService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@Autowired
    private OfficesService  officesService;
	
	@Autowired
	private MBHomeService mbHomeService;
	
	private String content = "contentData";
	private List<String> contentNameList = List.of("LATEST_NEWS", "DTS_HELPLINE", "ARCHIEVE", "DISCLAIMER", "QUALITY_POLICY", "MISSION_STATEMENT");

	@GetMapping("/")
	public String popupPage(Model model) {
		return "login/login"; 
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		try {
		model.addAttribute("contentData", loginService.getConetntData());
		} catch (Exception e) {
			DODLog.printStackTrace(e, LoginController.class, LogConstant.VISITOR_LOGIN_LOG); 
		}
		return "login/index"; 
	}

	@GetMapping("/accessDenied")
	public String accessDenied(HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().invalidate();
		return "login/accessDenied";
	}
	
	@GetMapping("/home")
	public String pcdaHome(Model model ,HttpServletRequest request,RedirectAttributes redirect) {
		
		HttpSession session= request.getSession();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
		LoginUser loginUser = sessionVisitor.getLoginUser();

		
		
		  boolean isFirstTimeLogin=loginService.isFirstTimeLogin(loginUser);	
		  DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class,":isFirstTimeLogin():isFirstTimeLogin "+isFirstTimeLogin); 
		 if(!isFirstTimeLogin) { 
			Set<SecurityQuestionModel> securityQuestions = loginUser.getSecurityQuestions();
			
			
			if(securityQuestions==null || securityQuestions.isEmpty()) {
				return "redirect:logout";
			}else {
				return "redirect:getPcdaQuestion";
			}
		 }else {
			 return "redirect:getFirstPcdaLogin";
		 }
	
	}

// Not first login	
@GetMapping("/getPcdaQuestion")
public String getNotFirstLogin(Model model,HttpServletRequest request){
	 DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class,":  inside getPcdaQuestion"); 
	try {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	SecurityQuestionModel securityQuestions = loginService.getSpecificQuestion(loginUser.getSecurityQuestions());
	session.removeAttribute("secAnswer");
	session.setAttribute("secAnswer", Base64Coder.decodeString(securityQuestions.getAnswer()));
	model.addAttribute("securityQuestion",securityQuestions.getQuestion());
    String certificateMessage =	loginService.getCertificateExpiredMessage(loginUser);
    model.addAttribute("certificateMessage",certificateMessage);
	}catch(Exception e ) {
		DODLog.printStackTrace(e, LoginController.class, LogConstant.VISITOR_LOGIN_LOG);
		return "redirect:logout";
	}
	 return "login/notFirstSecurityQues";
}

// check PCDA Answer not first login
@PostMapping("/getPcdaAnswerCheck")
public String getPcdaAnswerCheck(Model model,HttpServletRequest request,@RequestParam String event, @RequestParam String answerByUser) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	String secret = "Hidden Pass";
	String answerdecrypt = loginService.getDecryptText(secret, answerByUser);
	String	decodedanswer= (String) session.getAttribute("secAnswer");
	String answer="";
	if(decodedanswer instanceof String) {
	answer=decodedanswer;
	}
	session.removeAttribute("secAnswer");
	
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class,":getPcdaAnswerCheck decrypt answer "+answerdecrypt + " | answer :: " + answer); 
	
	if(event.equalsIgnoreCase("cancel")) {
		return "redirect:logout";
	}else if(answer.equals(answerdecrypt)) {
		
		boolean flag= loginService.checkLastModDate(loginUser.getContactInfoModDate());
		if(flag) {
			return "redirect:pcdaLogin";
		}else{
	       return "redirect:getContactInfo";
	       }
	}else {
		// update loginAttempt before logout
		loginService.updateLoginAttempt(loginUser.getUserId());
		model.addAttribute("contentData", loginService.getConetntData());
		model.addAttribute("error","Security Answer Not Matched, Please Try Again");
		 return "login/index";
	}
	
}

//  contact info page not first login 
@GetMapping("/getContactInfo")
public String getContactInfo(Model model ,HttpServletRequest request) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	model.addAttribute("userInfo", loginUser);
	 return "login/ContactInfoLogin";
}

// update contact info or skip not first login
@PostMapping("/updateOrSkipInfo")
public String updateOrSkipInfo(Model model,@RequestParam BigInteger userId,
	@RequestParam String requestType ,@RequestParam String email,@RequestParam String contactNumber,HttpServletRequest request) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	 int sessionStatus=0;
	 String sessionId = request.getSession().getId();
	 
	Optional<OfficeModel> officeModel=officesService.getOfficeByUserId(loginUser.getUserId());
    String groupId="";
	if(officeModel.isPresent()) {
		groupId=officeModel.get().getGroupId();
	}
	
	if(requestType.equalsIgnoreCase("skip")) {
		
		return "redirect:pcdaLogin";
	}
	
	if(requestType.equalsIgnoreCase("update")) {
		loginService.updateContactInfo(userId,email,contactNumber);
		loginService.preventProcessUpdate(loginUser.getUserId(),"GET_PERSONAL_INFO","1",groupId,"logout");
		 List<GetSessionModel> sessionList =loginService.getSessions(loginUser.getUserId(),groupId);
		 if(sessionList.isEmpty()) {
			 loginService.insertSessionRow(loginUser.getUserId(),groupId,sessionId,sessionStatus);
		 }else {
			 int sessionState=sessionList.get(0).getSessionState();
			 
			 if(sessionState==0) {
				 ServletContext servletContext = session.getServletContext();
				 HttpSession oldSessionObj=(HttpSession)servletContext.getAttribute(sessionList.get(0).getSessionId());
				 oldSessionObj.invalidate();
				 loginService.updateSession(loginUser.getUserId(),groupId,sessionId,sessionStatus);
			 }else if(sessionState==1) {
				 loginService.updateSession(loginUser.getUserId(),groupId,sessionId,sessionStatus);
			 }
		 }
		return "redirect:pcdaLogin";
	}
	
	return "redirect:logout";
}



@GetMapping("/pcdaLogin")
public String getFinalLogin(HttpServletRequest request,Model model) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	loginService.setFinanLogin(loginUser.getUserId());
	model.addAttribute(content, mbHomeService.getContent(contentNameList, "WELCOME", "Welcome To Defence Travel System"));
	
		return "login/welcome"; 
	}


// --------- first time login --------


// first login change password page
@GetMapping("/getFirstPcdaLogin")
public String getFirstPcdaLogin(Model model,HttpServletRequest request) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	model.addAttribute("userAlias", loginUser.getUserAlias());
	model.addAttribute("userId",loginUser.getUserId());
	return "login/ChangePasswordPage";

}
//------------INSIDE LOGIN  CHANGE PASSWORD ----------------------
// inside login change password
@GetMapping("/getChangePasswrdInLogin")
public String getChangePasswrdInLogin(Model model,HttpServletRequest request) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	model.addAttribute("userAlias", loginUser.getUserAlias());
	model.addAttribute("userId",loginUser.getUserId());
	return "login/ChangePasswrdInLogin";

}

// save changes for change Password inside Login
@PostMapping("/saveChangePasswordLogin")
public String saveChangePasswordLogin(Model model,HttpServletRequest request,
@RequestParam String newPassword,@RequestParam BigInteger userId,@RequestParam String oldPassword) {
	 
	String secret = "Hidden Pass";
	String oldPassDec = loginService.getDecryptText(secret, oldPassword);
	String newPassdec = loginService.getDecryptText(secret, newPassword);
	
	VisitorResponse response   = loginService.getFirstChangePasswd(newPassdec,userId,oldPassDec);
	if(response!=null && response.getErrorCode()!=200) {
		model.addAttribute("contentData", loginService.getConetntData());
		model.addAttribute("error", response.getErrorMessage());
		 return "login/index";
		
	}else if(response!=null && response.getErrorCode()==200){
		
		return "redirect:getQuesWithAnswer";
	}
		
	return "redirect:logout";
}

@GetMapping("/getQuesWithAnswer")
public String getQuesWithAnswer(Model model,HttpServletRequest request){
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	Set<SecurityQuestionModel> securityQues = loginUser.getSecurityQuestions();
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class,":##### ENCODED SECURITY QUESTIONS SET ########## : "+securityQues);
	Set<SecurityQuestionModel> decodeSecQuestion = new HashSet<>();

	for (SecurityQuestionModel securityQuestionModel : securityQues) {
		String question = securityQuestionModel.getQuestion();
		
		securityQuestionModel.setQuestion(Base64Coder.decodeString(question));
		securityQuestionModel.setAnswer(Base64Coder.decodeString(securityQuestionModel.getAnswer()));
		decodeSecQuestion.add(securityQuestionModel);
		
	}
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class,":##### DECODE SECURITY QUESTIONS SET ########## : "+decodeSecQuestion); 
	model.addAttribute("userId",loginUser.getUserId());
	model.addAttribute("securityQuestions",decodeSecQuestion);
	 return "login/ChngePassSecQues";
}
//--------------------------------------------


// save change password for  first login
@PostMapping("/firstLoginChangePwd")
public String getChangePassword(Model model,HttpServletRequest request,
@RequestParam String newPassword,@RequestParam String userId,@RequestParam String oldPassword,RedirectAttributes attributes) {
	 
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class," String encry  pass::"+oldPassword+ newPassword); 
	String secret = "Hidden Pass";
	String oldPassDec = loginService.getDecryptText(secret, oldPassword);
	String newPassdec = loginService.getDecryptText(secret, newPassword);
	String decUserId = loginService.getDecryptText(secret, userId);
	Long longUserId = Long.valueOf(decUserId);
	BigInteger bigUserId = BigInteger.valueOf(longUserId);
	 
	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, LoginController.class," String  decry::"+oldPassDec+ newPassdec); 
	VisitorResponse response   = loginService.getFirstChangePasswd(newPassdec,bigUserId,oldPassDec);
	if(response!=null && response.getErrorCode()!=200) {

		attributes.addFlashAttribute("error", response.getErrorMessage());
		 return "redirect:getFirstPcdaLogin";
		
	}else if(response!=null && response.getErrorCode()==200){
		
		return "redirect:authenticateFirstQuestions";
	}
		
	return "redirect:logout";
}

// authenticate Question page for first time  

@GetMapping("/authenticateFirstQuestions")
public String getFirstLoginQuestions(Model model ,HttpServletRequest request) {
	HttpSession session= request.getSession();
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(session);
	LoginUser loginUser = sessionVisitor.getLoginUser();
	model.addAttribute("userId", loginUser.getUserId());
	 return "login/authenticateFirsrtQuestions";
}

 // save Question first time login also In change password question
@PostMapping("/saveQuestionAns")
public String saveQuestionAnswer(Model model,HttpServletRequest request,@RequestParam BigInteger userId) {
	loginService.saveSecurityQuestion(request,userId);
	return "redirect:logout";
}
// ------------------------------------------------
}
