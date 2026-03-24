package com.pcda.mb.grievance.complaintRedressal.service;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.pcda.common.model.GrievanceCategoryBean;
import com.pcda.mb.grievance.complaintRedressal.model.ComplaintRedressalModel;
import com.pcda.mb.grievance.complaintRedressal.model.ComplaintRedressalResponse;
import com.pcda.mb.grievance.complaintRedressal.model.FaqResponse;
import com.pcda.mb.grievance.complaintRedressal.model.GrievanceCategoryResponse;
import com.pcda.mb.grievance.complaintRedressal.model.GrievanceFAQViewBean;
import com.pcda.util.DODLog;
import com.pcda.util.FileUploadConstant;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.validation.Valid;

@Service
public class GrievanceService {

	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<GrievanceCategoryBean> getGrievanceCategory() {
	    List<GrievanceCategoryBean> dataList = new ArrayList<>(); 
	    try {
	        String url = PcdaConstant.COMMON_GREIVANCE_URL + "/getGrCategory";
	        
	        ResponseEntity<GrievanceCategoryResponse> responseEntity =
	                restTemplate.getForEntity(url, GrievanceCategoryResponse.class);
	        
	        GrievanceCategoryResponse response = responseEntity.getBody();
	        
	        if (response != null && response.getErrorCode() == 200 && response.getResponseList() != null) {
	            dataList = response.getResponseList();
	        }

	    } catch (Exception e) {
	    	DODLog.printStackTrace(e, GrievanceService.class,LogConstant.COMMON_LOG);
	    }

	    return dataList;
	}

	public  ComplaintRedressalResponse saveGrievance(@Valid ComplaintRedressalModel complaintRedressalModel) {
		ComplaintRedressalResponse response=null;
		try {
			ResponseEntity<ComplaintRedressalResponse> postForEntity = restTemplate.postForEntity(PcdaConstant.COMMON_GREIVANCE_URL + "/save", complaintRedressalModel,ComplaintRedressalResponse.class);
			 response = postForEntity.getBody();
			 if(response!=null) {
				  DODLog.info(LogConstant.COMMON_LOG, GrievanceService.class, "Save grievance successful ::: " + response);
			 }
			 else {
				 DODLog.error(LogConstant.COMMON_LOG, GrievanceService.class, "Save grievance returned null response!");
			 }
			DODLog.info(LogConstant.COMMON_LOG, GrievanceService.class," save response :::: " + response);
		} catch (Exception e) {
			DODLog.printStackTrace(e, GrievanceService.class,LogConstant.COMMON_LOG);
		}
		return response;
	}

	public String uploadFile(MultipartFile file) {
		String time = String.valueOf(new Date().getTime());
		Path uploadPath = Paths.get(FileUploadConstant.FILE_UPLOAD_GRIEVANCE + FileUploadConstant.SLASH);
		String savePath = "";

	    try {
	      
	        if (file == null || file.isEmpty()) {
	            DODLog.info(LogConstant.COMMON_LOG, GrievanceService.class, "No file uploaded, skipping file save.");
	            return ""; 
	        }	        
			String originalFileName = file.getOriginalFilename();
	        if (originalFileName == null || originalFileName.isEmpty()) {
	            throw new RuntimeException("Invalid or missing file name.");
	        }

			int index = originalFileName.lastIndexOf('.');
	        String fileName;
	        String extension;

	        if (index == -1) {
	            fileName = originalFileName;
	            extension = "";
	        } else {
	            fileName = originalFileName.substring(0, index);
	            extension = originalFileName.substring(index);
	        }
	        String newFileName = fileName + "_" + time + extension;
			Files.createDirectories(uploadPath);
			Path filePath = uploadPath.resolve(newFileName);

	        try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        }

			savePath = filePath.toString();

		} catch (Exception e) {
			DODLog.printStackTrace(e, GrievanceService.class, LogConstant.COMMON_LOG);
		}

		return savePath;
	}


	public List<GrievanceFAQViewBean>getfaqByCategoryId(BigInteger grievanceCatId) {
		List<GrievanceFAQViewBean> responseFaq= new ArrayList<>();
		try {
			String url=PcdaConstant.COMMON_GREIVANCE_URL + "/getGrFAQByCatId?grievanceCatId=" +grievanceCatId;
			ResponseEntity<FaqResponse> forEntity = restTemplate.getForEntity(url, FaqResponse.class);
			FaqResponse response= forEntity.getBody();
			if(response!=null && response.getErrorCode()==200 && response.getResponseList()!=null) {
				responseFaq=response.getResponseList();
			}
			DODLog.info(LogConstant.COMMON_LOG, GrievanceService.class," Grievance FAQ response:::: " + responseFaq);
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, GrievanceService.class, LogConstant.COMMON_LOG);
		}
		return responseFaq;
		
	}

	public String validatePersonalNo(String personalNo, String groupId) {
		try {
					
			String url=PcdaConstant.COMMON_GREIVANCE_URL + "/validatePersonalNo?personalNo=" +personalNo +"&groupId="+ groupId;
			
			String forObject = restTemplate.getForObject(url,String.class);
			return forObject;
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, GrievanceService.class, LogConstant.COMMON_LOG);
		}
		return null;
		
	
	}
	
}

