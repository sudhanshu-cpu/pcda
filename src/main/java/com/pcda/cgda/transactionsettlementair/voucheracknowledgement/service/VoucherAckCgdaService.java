package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.AccountOfficeAckCgdaResponse;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.AckVoucherCgdaFormModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.AckVoucherCgdaSearchResponse;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.GetAckSearchCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCgdaChildModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCgdaResponse;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.VoucherAckCgdaHistoryModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.VoucherAckCgdaHistoryResponse;

import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.FileUploadConstant;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.Part;

@Service
public class VoucherAckCgdaService {

@Autowired

private RestTemplate restTemplate;

//Search Data
public List<GetAckSearchCgdaParentModel> getAckSearchData(AckVoucherCgdaFormModel ackModel) {
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class,
				"## ACK VOUCHER FORM MODEL :::"+ackModel);
	List<GetAckSearchCgdaParentModel> parentModelList=new ArrayList<>();
	AckVoucherCgdaSearchResponse response=null;
	String url =PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL +
 "/getVoucherAcknowledgementData?fromDate={fromDate}&toDate={toDate}&fromTxnDate={fromTxnDate}&toTxnDate={toTxnDate}&invoiceNo={invoiceNo}&voucherNo={voucherNo}&personalNo={personalNo}&travelType={travelType}&accountOffice={accountOffice}&statusType={statusType}&serviceProvider={serviceProvider}";
	Map<String,String> param =  new HashMap<>();
	param.put("fromDate", ackModel.getFromDate());
	param.put("toDate", ackModel.getToDate());
	param.put("fromTxnDate", ackModel.getFromTxnDate());
	param.put("toTxnDate", ackModel.getToTxnDate());
	param.put("invoiceNo", ackModel.getInvoiceNo());
	param.put("voucherNo", ackModel.getVoucherNo());
	param.put("personalNo", ackModel.getPersonalNo());
	param.put("travelType", ackModel.getTravelType());
	param.put("accountOffice", ackModel.getAccountOffice());
	param.put("statusType", ackModel.getStatusType().toString());
	param.put("serviceProvider", ackModel.getServiceProvider().toString());
	
	try {
		
	response = restTemplate.getForObject(url, AckVoucherCgdaSearchResponse.class, param);
	
	if(response !=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		parentModelList = response.getResponseList();
	
	
		
	}
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## ACK VOUCHER DATA LIST :::"+parentModelList.size());
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	
	 return parentModelList;
}

// Histroy
public List<VoucherAckCgdaHistoryModel> getVoucherAckHistory( String voucherNo) {
	DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## voucherNo :::" +voucherNo);
	
	String url =PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL +"/getVoucherAcknowledgementHistory/"+voucherNo;
	List<VoucherAckCgdaHistoryModel> historyModelList = new ArrayList<>();
	try {
	ResponseEntity<VoucherAckCgdaHistoryResponse> entity = restTemplate.getForEntity(url, VoucherAckCgdaHistoryResponse.class);
	VoucherAckCgdaHistoryResponse response = entity.getBody();
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		historyModelList = response.getResponseList();
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class,
				" ## VOUCHER ACK HISTORY MODEL LIST :::"+historyModelList.size());
	return historyModelList;
}

public PostVoucherAckCgdaResponse getSaveVoucherAck(PostVoucherAckCgdaParentModel  parentModel ,MultipartHttpServletRequest request) {
	
	PostVoucherAckCgdaResponse ackResponse =null;
	List<PostVoucherAckCgdaChildModel> childList=new ArrayList<>();
	PostVoucherAckCgdaChildModel  model = new PostVoucherAckCgdaChildModel();
	
	//int checkBoxlength = Integer.parseInt(request.getParameter("NoOfCheckBox"));
	
	String voucherNo= parentModel.getVoucherNo();
	
	try {
		String[] paymentIds = request.getParameterValues("payment_ids");
		
		for(String paymentId:paymentIds) {
		if(request.getParameter("paymentId_"+voucherNo+"_"+paymentId)!=null) {
	model.setPaymentId(Integer.parseInt(request.getParameter("paymentId_"+voucherNo+"_"+paymentId)));
		}
		if(request.getParameter("ack_"+voucherNo+"_"+paymentId)!=null) {
	model.setAckNo(request.getParameter("ack_"+voucherNo+"_"+paymentId));
		}
		if(request.getParameter("ack_date_"+voucherNo+"_"+paymentId)!=null) {
	String ackDate=request.getParameter("ack_date_"+voucherNo+"_"+paymentId);
	Date date = CommonUtil.formatString(ackDate,"dd-MM-yyyy");
	model.setAckDate(date);
		}
		if(request.getPart("file_"+voucherNo+"_"+paymentId)!=null) {
	    Part file =  request.getPart("file_"+voucherNo+"_"+paymentId);
	    
	    String filePath =   uplaodFile( file,voucherNo);
	     model.setFilePath(filePath);
		}
	if(model.getPaymentId()!=null && model.getAckNo()!=null && model.getAckDate()!=null) {
		childList.add(model);
	}
	
	}
	if(!childList.isEmpty() && childList!=null) {
		
		parentModel.setVoucherAckDetails(childList); 
	}
	
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class,
				" ## POST SAVR MODEL :::"+parentModel+" ## POST CHILD MODEL :: "+parentModel.getVoucherAckDetails());
	ackResponse =
	restTemplate.postForObject(PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL+"/saveVoucherAcknowledgementDetails", parentModel, PostVoucherAckCgdaResponse.class);
	
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## ackResponse :::" +ackResponse);
	return ackResponse;
}
	

//File Upload
	public String uplaodFile(Part file,String voucherNo) {

		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## file  ##" +file +" voucherNo ##"+ voucherNo);
		Path uploadPath = Paths.get(FileUploadConstant.FILE_UPLOAD + voucherNo+FileUploadConstant.SLASH+Calendar.getInstance().getTimeInMillis() + FileUploadConstant.SLASH);
		String savePath = "";
		try (InputStream inputStream = file.getInputStream()) {
			Files.createDirectories(uploadPath);
			Path filePath = uploadPath.resolve(file.getName()+".pdf");
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			savePath = filePath.toString();
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## savePath  :::" +savePath);
		return savePath;
	}
	
	// get a/c office List
	public Map<String,String> getAccountOfficeListMap(String officeType,String groupId){
		AccountOfficeAckCgdaResponse response=null;
		Map<String,String> responseMap=null;
		try {
			 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
						"/getMappedAccountOffice/";
			 response = restTemplate.getForObject(url+officeType+"/"+groupId, AccountOfficeAckCgdaResponse.class);
			 if(response!=null && response.getErrorCode()==200) {
				 responseMap=response.getResponseList();
				 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaService.class," ## responseMap  :::" +responseMap.size());
				 return responseMap;
			 }
		}catch(Exception e) {
			DODLog.printStackTrace(e, VoucherAckCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
		
		return new HashMap<>();
	}
}

