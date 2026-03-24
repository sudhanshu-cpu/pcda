package com.pcda.cgda.airtranscation.voucherdownload.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.cgda.airtranscation.voucherdownload.model.GenerateDemandPostModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.GenerateDemandPostResponse;
import com.pcda.cgda.airtranscation.voucherdownload.model.GetCountAmountModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VocherDowResponse;
import com.pcda.cgda.airtranscation.voucherdownload.model.VocuherPostResponseModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherDownloadFormsResponse;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherPostModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherReqModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherResponse;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherSettlePostModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherSettlePostResponseModel;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.EnumTypeServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.Status;

@Service
public class VoucherDownloadService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TravelTypeServices travelTypeServices;

	@Autowired
	private OfficesService paoServices;

	@Autowired
	private MasterServices masterServices;

	@Autowired
	private  EnumTypeServices enumTypeServices;

	public List<DODServices> getAllServices(String approvalType) {
		List<DODServices> services = masterServices.getServicesByApprovalState(approvalType);
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," Get approval type: " + approvalType);
		if (!services.isEmpty()) {
			services=services.stream().filter(e -> e.getStatus().equals(Status.ON_LINE.toString())).toList();
		}
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," services List : " + services.size());
		return services;
	}

	public List<TravelType> getAllTravelType(Integer approvalType) {

		List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class,
				"  TravelType List Size : " + travelTypeList.size());
		return travelTypeList;
	}

 	    // Method that returns all PAO which is 1
		public List<OfficeModel> getAllPao() {
			List<OfficeModel> paoList = paoServices.getOffices("PAO", "1");
		
			return paoList;
		}

	   // Method that returns All Vendors
	public List<EnumType> getVendorList(String enumType) {
	    	
	    	return enumTypeServices.getEnumType(enumType);
	    }

	public VoucherReqModel getAllVoucher(int pageNumber, int pageSize,String serviceProviderType) {
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," pageNumber " + pageNumber +" pageSize "+pageSize +" serviceProviderType "+serviceProviderType);
		VoucherReqModel voucherRequestModel = new VoucherReqModel();
		try {
			Map<String, String> params = new HashMap<>();
			params.put("pageNumber", String.valueOf(pageNumber));
			params.put("pageSize", String.valueOf(pageSize));
			if (serviceProviderType != null && !serviceProviderType.isEmpty()) {
				params.put("serviceProviderType", serviceProviderType);
			}
			String baseUrl = PcdaConstant.AIR_SERVICE_VOUCHER + "/getVoucherDownloadRequests?";
			String url = params.entrySet().stream()
					.filter(entry -> !entry.getValue().isEmpty())
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining("&", baseUrl, ""));

			ResponseEntity<VocherDowResponse> response = restTemplate.exchange(url, HttpMethod.GET,null,VocherDowResponse.class);
				VocherDowResponse voucherResponse = response.getBody();
            
            if (voucherResponse!=null &&  voucherResponse.getResponse() != null && voucherResponse.getErrorCode() == 200 ) {
            	
				voucherRequestModel = voucherResponse.getResponse();
			
				if (voucherRequestModel.getStartDate() == null) {
					voucherRequestModel.setStartDateFormat("");
				}else {
					voucherRequestModel.setStartDateFormat(CommonUtil.formatDate(voucherRequestModel.getStartDate(), "dd-MM-yyyy"));
				}
				
				if(voucherRequestModel.getVoucherRequestList()!=null && !voucherRequestModel.getVoucherRequestList().isEmpty()) {
					voucherRequestModel.getVoucherRequestList().forEach(e ->{
								if(e.getFromDate()!=null) {
									e.setFormatFromDate(CommonUtil.formatString(e.getFromDate(),"dd-MM-yyyy"));
								}else {
									e.setFormatFromDate(new Date());
				}
							} );
            
				}
            
				}
            
			} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
			}
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," voucherRequestModel " + voucherRequestModel);
			return voucherRequestModel;
		}

	// Method to create Voucher for airTranscation
		public VocuherPostResponseModel createVoucher(VoucherPostModel voucherPostModel) {
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," voucherPostModel " + voucherPostModel);
			VocuherPostResponseModel voucherPostModelResponse=null;
			try {
				HttpEntity<VoucherPostModel> createVoucher = new HttpEntity<>(voucherPostModel);
				ResponseEntity<VocuherPostResponseModel> response = restTemplate.exchange(
						PcdaConstant.AIR_SERVICE_VOUCHER + "/saveVoucherRequest", HttpMethod.POST, createVoucher,
						VocuherPostResponseModel.class);
				voucherPostModelResponse  = response.getBody();
			
			} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
			}
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," voucherPostModelResponse " + voucherPostModelResponse);	
			return voucherPostModelResponse;
		}

	// Service Method to getRecordCount using AJAX call
		public GetCountAmountModel getRecordCount(VoucherPostModel postModel) {
			String url = null;
			GetCountAmountModel model = null;
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class,"get Record Count postModel " + postModel);
			try {
			if (postModel.getFromDate().equals("")) {
				url = PcdaConstant.AIR_SERVICE_VOUCHER
						+ "/checkVoucherCount?fromDate=''&toDate={toDate}&serviceProvider={serviceProvider}&serviceId={serviceId}&travelType={travelType}&paoId={paoId}";
			} else {
				url = PcdaConstant.AIR_SERVICE_VOUCHER
						+ "/checkVoucherCount?fromDate={fromDate}&toDate={toDate}&serviceProvider={serviceProvider}&serviceId={serviceId}&travelType={travelType}&paoId={paoId}";
			}
			Map<String, String> params = new HashMap<>();
			params.put("fromDate", postModel.getFromDate());
			params.put("toDate", postModel.getToDate());
			params.put("serviceProvider", postModel.getServiceProvider().toString());
			params.put("serviceId", postModel.getServiceId());
			params.put("travelType", postModel.getTravelType());
			params.put("paoId", postModel.getPaoId());
			
			VoucherResponse response = restTemplate.getForObject(url, VoucherResponse.class, params);
			if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
				model = response.getResponse();
			}
			} catch (Exception e) {
				DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
				}
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," model " + model);
			return model;
		}

	// Method to get the path of the file
		public String downloadVoucherForms(String voucherReqId, String requestFor) {
			String path = "";
			try {

				ResponseEntity<VoucherDownloadFormsResponse> response = restTemplate
					.exchange(PcdaConstant.AIR_SERVICE_VOUCHER + "/dwnDRFile?dwnReqId=" + voucherReqId + "&requestFor="
							+ requestFor, HttpMethod.GET, null, VoucherDownloadFormsResponse.class);
		
				VoucherDownloadFormsResponse responseBody = response.getBody();
				if (responseBody != null && responseBody.getErrorCode() == 200 && responseBody.getResponse() != null) {
					path = responseBody.getResponse();
			}
			
			} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
			}
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadService.class," path " + path);
			return path;
			
			
		}

		

	// Method to settle voucher
		public String settleVoucher(VoucherSettlePostModel settlePostModel) {
			String settleModel = null;
			try {
				HttpEntity<VoucherSettlePostModel> settleVoucher = new HttpEntity<>(settlePostModel);
				ResponseEntity<VoucherSettlePostResponseModel> response = restTemplate.exchange(
						PcdaConstant.AIR_SERVICE_VOUCHER + "/settleVoucher", HttpMethod.POST, settleVoucher,
						VoucherSettlePostResponseModel.class);
				VoucherSettlePostResponseModel voucherSettlePostModelResponse  = response.getBody();

			if (voucherSettlePostModelResponse != null && voucherSettlePostModelResponse.getErrorMessage() != null) {

				settleModel = voucherSettlePostModelResponse.getErrorMessage();
			} else {
					  settleModel = "Error while settling voucher";
				  }

			} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
				return "Error while settling voucher";
		}
			return settleModel;

		}

	// Method to GenerateDemand
		public String demandGen(GenerateDemandPostModel generateDemand, String dwnRequestId) {
		String demandGenResponseMsg = null;
			try {
				HttpEntity<GenerateDemandPostModel> genDemandVoucher = new HttpEntity<>(generateDemand);
				ResponseEntity<GenerateDemandPostResponse> response = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_VOUCHER + "/generateDemand?dwnRequestId=" + dwnRequestId, HttpMethod.POST,
					genDemandVoucher, GenerateDemandPostResponse.class);
				GenerateDemandPostResponse generateDemandPostModelResponse  = response.getBody();

			if (generateDemandPostModelResponse != null && generateDemandPostModelResponse.getErrorMessage() != null) {
				demandGenResponseMsg = generateDemandPostModelResponse.getErrorMessage();
			} else {
				demandGenResponseMsg = "Error while generating Demand";
					  }

			} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherDownloadService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
				return "Error While Generating Demand";
		}
			return demandGenResponseMsg;
	}
}
