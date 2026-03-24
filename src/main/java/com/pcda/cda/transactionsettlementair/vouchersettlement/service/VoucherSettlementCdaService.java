package com.pcda.cda.transactionsettlementair.vouchersettlement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.service.OutVoucherCdaService;
import com.pcda.cda.transactionsettlementair.voucheracknowledgement.model.AccountOfficeAckResponse;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.AfterSaveCdaResponse;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetDataListCdaResponse;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetDataVoucherNoCdaParentModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetVoucherNoCdaResponse;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetVoucherSetListDataCdaModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.PostFormFieldCdaModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.PostVoucherCdaSaveModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class VoucherSettlementCdaService {

@Autowired
 private RestTemplate restTemplate;
	
	public List<GetVoucherSetListDataCdaModel> getDataList(PostFormFieldCdaModel  fieldModel) {
		
		 DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class," fieldModel :::"+fieldModel);
		 List<GetVoucherSetListDataCdaModel> dataModelList = new ArrayList<>();
		
try {
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getVoucherSettlementData?fromBookingDate={fromBookingDate}&toBookingDate={toBookingDate}&voucherNo={voucherNo}&invoiceNo={invoiceNo}&statusType={statusType}&accountOffice={accountOffice}&serviceProvider={serviceProvider}";
					 
					 Map<String, String> params = new HashMap<>();
					 
					 
					 params.put("fromBookingDate", fieldModel.getFromBookingDate());
					 params.put("toBookingDate", fieldModel.getToBookingDate());
					 params.put("voucherNo", fieldModel.getVoucherNo());
					 params.put("invoiceNo", fieldModel.getInvoiceNo());
					 params.put("statusType", fieldModel.getStatusType().toString());
				     params.put("accountOffice", fieldModel.getAccountOffice());
					 params.put("serviceProvider", fieldModel.getServiceProvider().toString());
					 
					 GetDataListCdaResponse response = restTemplate.getForObject(url, GetDataListCdaResponse.class, params);
		if(response != null && response.getErrorCode()== 200 && null!=response.getResponseList()) {
			dataModelList=response.getResponseList();
		}
}catch(Exception e) {
	 DODLog.printStackTrace(e, VoucherSettlementCdaService.class, LogConstant.CDA_VOUCHER_LOG_FILE);
}
		DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class," dataModelList :::"+dataModelList.size());
						 return dataModelList;
					 }
	
	
	
	
	public GetDataVoucherNoCdaParentModel getVocherNodata(String voucherNo) {
		DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class," voucherNo :::"+voucherNo);
		GetDataVoucherNoCdaParentModel noModel = null;
		
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getVoucherDetailsForSettlement/"+voucherNo;
		 try {
		 GetVoucherNoCdaResponse response = restTemplate.getForObject(url, GetVoucherNoCdaResponse.class); 
		if(response!=null && response.getErrorCode()==200) {
		 noModel = response.getResponse(); 
		 return noModel;
		}
		else {
			return new GetDataVoucherNoCdaParentModel();
		}
		 }catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementCdaService.class, LogConstant.CDA_VOUCHER_LOG_FILE);
		 }
		 
	return new GetDataVoucherNoCdaParentModel();
	}
	
	
	public AfterSaveCdaResponse saveSettlement(PostVoucherCdaSaveModel saveModel) {
		
		AfterSaveCdaResponse response=new AfterSaveCdaResponse();
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/saveVoucherSettlementDetails";
		 Date date=CommonUtil.formatString(saveModel.getTrDate(), "dd-MM-yyyy");
		 saveModel.setUtrDate(date);
		 DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class,
					" ## VOUCHER SAVE MODEL IN SERVICE :::"+saveModel);
		 try {
			
			ResponseEntity<AfterSaveCdaResponse> entity = restTemplate.postForEntity(url, saveModel, AfterSaveCdaResponse.class);
			response = entity.getBody();
			
		}catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementCdaService.class, LogConstant.CDA_VOUCHER_LOG_FILE);
		}
		 DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class," response :::"+response);
		 return response;
	}
	
	// get a/c office List
	public Map<String,String> getAccountOfficeListMap(String officeType,String groupId){
		AccountOfficeAckResponse response;
		Map<String,String> responseMap=new HashMap<>();
		try {
			 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
						"/getMappedAccountOffice/";
			 response = restTemplate.getForObject(url+officeType+"/"+groupId, AccountOfficeAckResponse.class);
			 if(response!=null && response.getErrorCode()==200) {
				 responseMap=response.getResponseList();
				 
			 }
		}catch(Exception e) {
			DODLog.printStackTrace(e, OutVoucherCdaService.class, LogConstant.CDA_VOUCHER_LOG_FILE);
		}
		DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaService.class," responseMap :::"+responseMap.size());
		return responseMap;
	}
	
}
