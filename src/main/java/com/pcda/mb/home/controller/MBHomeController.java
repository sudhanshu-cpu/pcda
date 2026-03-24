package com.pcda.mb.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.mb.home.service.MBHomeService;

@Controller
@RequestMapping("/mb")
public class MBHomeController {

	@Autowired
	private MBHomeService mbHomeService;

	private String content = "contentData";
	private String homePath = "MB/home/home";
	private List<String> contentNameList = List.of("LATEST_NEWS", "DTS_HELPLINE", "ARCHIEVE", "DISCLAIMER", "QUALITY_POLICY", "MISSION_STATEMENT");

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "WELCOME", "Welcome To Defence Travel System"));
		return homePath;
	}

	@GetMapping("/trArmedForcesHome")
	public String trArmedForcesHome(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "TR_ARMED_FORCES", "Travel Rules For Armed Forces"));
		return homePath;
	}

	@GetMapping("/trCivHome")
	public String trCivHome(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "TR_CIVILIANS", "Travel Rules For Civilians"));
		return homePath;
	}

	@GetMapping("/downloadsHome")
	public String downloadsHome(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "DOWNLOADS", "Downloads"));
		return homePath;
	}

	@GetMapping("/instructionsHome")
	public String instructionsHome(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "GOVT_ORDER", "Gov't Order & Instructions"));
		return homePath;
	}

	@GetMapping("/feedBackSuggestion")
	public String feedBackSuggestion(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "FEEDBACK", "Feedback / Suggestions"));
		return homePath;
	}

	@GetMapping("/usefulLink")
	public String usefulLink(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "USEFUL_LINKS", "Useful Links"));
		return homePath;
	}

	@GetMapping("/contactHome")
	public String contactHome(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "CONTACT_US", "Contact List"));
		return homePath;
	}

	@GetMapping("/faqs")
	public String faqs(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "FAQ", "Frequently Asked Questions"));
		return homePath;
	}

	@GetMapping("/eTutorial")
	public String eTutorial(Model model) {
		model.addAttribute(content, mbHomeService.getContent(contentNameList, "E-TUTORIALS", "e Tutorials Links"));
		return homePath;
	}

}
