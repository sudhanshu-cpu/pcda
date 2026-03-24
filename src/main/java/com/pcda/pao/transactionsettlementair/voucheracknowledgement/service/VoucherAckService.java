package com.pcda.pao.transactionsettlementair.voucheracknowledgement.service;

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

import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.AckVoucherFormModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.AckVoucherSearchResponse;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.GetAckSearchParentModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckChildModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckParentModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckResponse;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.VoucherAckHistoryModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.VoucherAckHistoryResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.FileUploadConstant;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.Part;

@Service
public class VoucherAckService {

@Autowired

private RestTemplate restTemplate;

//Search Data
public List<GetAckSearchParentModel> getAckSearchData(AckVoucherFormModel ackModel) {
	 DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckService.class," ACK VOUCHER FORM MODEL :::"+ackModel);
	List<GetAckSearchParentModel> parentModelList=new ArrayList<>();
	AckVoucherSearchResponse response=null;
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
	param.put("statusType", ackModel.getStatusType());
	param.put("serviceProvider", ackModel.getServiceProvider());
	
	try {
		
	response = restTemplate.getForObject(url, AckVoucherSearchResponse.class, param);
	
	if(response !=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		parentModelList = response.getResponseList();
	}
	
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
	}
	DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckService.class,
			"ACK VOUCHER DATA LIST :::"+parentModelList.size());
	return parentModelList;
}

public List<VoucherAckHistoryModel> getVoucherAckHistory( String voucherNo) {
	DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckService.class,"getVoucherAckHistory() voucherNo :::"+voucherNo);
	String url =PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL +"/getVoucherAcknowledgementHistory/"+voucherNo;
	List<VoucherAckHistoryModel> historyModelList = new ArrayList<>();
	try {
	ResponseEntity<VoucherAckHistoryResponse> entity = restTemplate.getForEntity(url, VoucherAckHistoryResponse.class);
	VoucherAckHistoryResponse response = entity.getBody();
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		historyModelList = response.getResponseList();
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
	}
	 DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckService.class,
				"VOUCHER ACK HISTORY MODEL LIST :::"+historyModelList.size());
	return historyModelList;
}

public PostVoucherAckResponse getSaveVoucherAck(PostVoucherAckParentModel parentModel ,MultipartHttpServletRequest request) {
	
	PostVoucherAckResponse ackResponse =null;
	List<PostVoucherAckChildModel> childList=new ArrayList<>();
	PostVoucherAckChildModel  model = new PostVoucherAckChildModel();
	

	
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
	
	 DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckService.class,
				" POST SAVE MODEL :::"+parentModel+" ## POST CHILD MODEL :: "+parentModel.getVoucherAckDetails());
	ackResponse =
	restTemplate.postForObject(PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL+"/saveVoucherAcknowledgementDetails", parentModel, PostVoucherAckResponse.class);
	if(ackResponse!=null && ackResponse.getErrorCode()==200) {
		return ackResponse;
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, VoucherAckService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
	}
	return new PostVoucherAckResponse();
}
	

//File Upload
	public String uplaodFile(Part file,String voucherNo) {

		Path uploadPath = Paths.get(FileUploadConstant.FILE_UPLOAD + voucherNo+FileUploadConstant.SLASH+Calendar.getInstance().getTimeInMillis() + FileUploadConstant.SLASH);
		String savePath = "";
		try (InputStream inputStream = file.getInputStream()) {
			Files.createDirectories(uploadPath);
			Path filePath = uploadPath.resolve(file.getName()+".pdf");
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			savePath = filePath.toString();
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
		}
		return savePath;
	}
}

