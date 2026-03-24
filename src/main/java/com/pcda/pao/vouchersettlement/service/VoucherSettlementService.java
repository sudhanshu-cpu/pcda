package com.pcda.pao.vouchersettlement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.pao.vouchersettlement.model.AfterSaveResponse;
import com.pcda.pao.vouchersettlement.model.GetDataListResponse;
import com.pcda.pao.vouchersettlement.model.GetDataVoucherNoParentModel;
import com.pcda.pao.vouchersettlement.model.GetVoucherNoResponse;
import com.pcda.pao.vouchersettlement.model.GetVoucherSetListDataModel;
import com.pcda.pao.vouchersettlement.model.PostFormFieldModel;
import com.pcda.pao.vouchersettlement.model.PostVoucherSaveModel;
import com.pcda.pao.vouchersettlement.model.VoucherDetailsResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class VoucherSettlementService {

@Autowired
 private RestTemplate restTemplate;
	
	public List<GetVoucherSetListDataModel> getDataList(PostFormFieldModel  fieldModel) {
		 List<GetVoucherSetListDataModel> dataModelList = new ArrayList<>();
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
					 
					 GetDataListResponse response = restTemplate.getForObject(url, GetDataListResponse.class, params);
		if(response != null && response.getErrorCode()== 200 && null!=response.getResponseList()) {
			
			dataModelList=response.getResponseList();
			
		}
		 }catch(Exception e) {
				DODLog.printStackTrace(e, VoucherSettlementService.class, LogConstant.VOUCHER_SETTLEMENT_LOG_FILE);
		}
						 
		DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementService.class,
				"ACK VOUCHER DATA LIST :::"+dataModelList.size());
		 return dataModelList;
	}
	
	
	
	
	public GetDataVoucherNoParentModel getVocherNodata(String voucherNo) {
	
		GetDataVoucherNoParentModel noModel = new GetDataVoucherNoParentModel();
		
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getVoucherDetailsForSettlement/"+voucherNo;
		 try {
		 GetVoucherNoResponse response = restTemplate.getForObject(url, GetVoucherNoResponse.class); 
		if(response!=null && response.getErrorCode()==200) {
		 noModel = response.getResponse(); 
		
		}
		 }catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementService.class, LogConstant.VOUCHER_SETTLEMENT_LOG_FILE);
		 }
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementService.class," noModel :::"+noModel);
		 return noModel;
	}
	
	
	public AfterSaveResponse saveSettlement(PostVoucherSaveModel saveModel) {
		
		AfterSaveResponse response;
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/saveVoucherSettlementDetails";
		 Date date=CommonUtil.formatString(saveModel.getTrDate(), "dd-MM-yyyy");
		 saveModel.setUtrDate(date);
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementService.class,
					"VOUCHER SAVE MODEL IN SERVICE :::"+saveModel);
		 try {
			
			ResponseEntity<AfterSaveResponse> entity = restTemplate.postForEntity(url, saveModel, AfterSaveResponse.class);
			
			response = entity.getBody();
			if(response !=null && response.getErrorCode()==200) {
				DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementService.class," response :::" +response);
				return response;
			}
			
		}catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementService.class, LogConstant.VOUCHER_SETTLEMENT_LOG_FILE);
		}
		 
		return new AfterSaveResponse();
	}

	//voucher Details Based on Admin API
	public VoucherDetailsResponse getVoucherDetails(String voucherNo) {
		VoucherDetailsResponse response = new VoucherDetailsResponse();
		try {
			String url = PcdaConstant.REPORT_SERVICE_URL_ADMIN + "/getVoucherDetailsForSettlement/" + voucherNo;
			response = restTemplate.getForObject(url, VoucherDetailsResponse.class);
			DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementService.class, "VOUCHER NO. RESPONSE ::" + response);
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherSettlementService.class, LogConstant.VOUCHER_SETTLEMENT_LOG_FILE);
		}
		
		return response;
	}
}
