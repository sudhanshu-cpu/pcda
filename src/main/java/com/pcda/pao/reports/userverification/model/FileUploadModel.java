package com.pcda.pao.reports.userverification.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadModel {

	
	private String date;

	private String type;

	private MultipartFile userVerificationFileToUpload;

	private String month;

	private String year;


}
