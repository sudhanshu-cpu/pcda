package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.service;

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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.AckVoucherFormSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.AckVoucherSearchSPResponse;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.GetAckSearchParentSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckChildSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckParentSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckSPResponse;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.VoucherAckHistorySPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.VoucherAckHistorySPResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.FileUploadConstant;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.Part;

@Service
public class VoucherAckSPService {

	@Autowired

	private RestTemplate restTemplate;

	@Autowired

	private OfficesService officeservice;

//Search Data
	public List<GetAckSearchParentSPModel> getAckSearchData(AckVoucherFormSPModel ackModel) {
		DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class," ACK VOUCHER FORM MODEL :::" + ackModel);
		List<GetAckSearchParentSPModel> parentModelList =  new ArrayList<>();
		AckVoucherSearchSPResponse response = null;
		String url = PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL
				+ "/getVoucherAcknowledgementData?fromDate={fromDate}&toDate={toDate}&fromTxnDate={fromTxnDate}&toTxnDate={toTxnDate}&invoiceNo={invoiceNo}&voucherNo={voucherNo}&personalNo={personalNo}&travelType={travelType}&accountOffice={accountOffice}&statusType={statusType}&serviceProvider={serviceProvider}";
		Map<String, String> param = new HashMap<>();
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

			response = restTemplate.getForObject(url, AckVoucherSearchSPResponse.class, param);

			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				parentModelList = response.getResponseList();
			}
			DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class,
					" ACK VOUCHER DATA LIST :::" + parentModelList.size());
		
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckSPService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
		}
		
		return parentModelList;
	}

// Histroy
	public List<VoucherAckHistorySPModel> getVoucherAckHistory(String voucherNo) {
		
		DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class," voucherNo:::" + voucherNo);
		String url = PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL + "/getVoucherAcknowledgementHistory/"
				+ voucherNo;
		List<VoucherAckHistorySPModel> historyModelList = new ArrayList<>();
try {
		ResponseEntity<VoucherAckHistorySPResponse> entity = restTemplate.getForEntity(url,
				VoucherAckHistorySPResponse.class);
		VoucherAckHistorySPResponse response = entity.getBody();
		if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
			historyModelList = response.getResponseList();
		}
		DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class,"VOUCHER ACK HISTORY MODEL LIST :::" + historyModelList.size());
		
      }catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckSPService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
		}
		return historyModelList;
	}

	public PostVoucherAckSPResponse getSaveVoucherAck(PostVoucherAckParentSPModel parentModel,
			MultipartHttpServletRequest request) {

		PostVoucherAckSPResponse ackResponse = null;
		List<PostVoucherAckChildSPModel> childList = new ArrayList<>();
		PostVoucherAckChildSPModel model = new PostVoucherAckChildSPModel();

		String voucherNo = parentModel.getVoucherNo();

		try {
			String[] paymentIds = request.getParameterValues("payment_ids");
			
			
			for(String paymentId:paymentIds) {
				if (request.getParameter("paymentId_" + voucherNo + "_" + paymentId) != null) {
					model.setPaymentId(Integer.parseInt(request.getParameter("paymentId_" + voucherNo + "_" + paymentId)));
				}
				if (request.getParameter("ack_" + voucherNo + "_" + paymentId) != null) {
					model.setAckNo(request.getParameter("ack_" + voucherNo + "_" + paymentId));
				}
				if (request.getParameter("ack_date_" + voucherNo + "_" + paymentId) != null) {
					String ackDate = request.getParameter("ack_date_" + voucherNo + "_" + paymentId);
					Date date = CommonUtil.formatString(ackDate, "dd-MM-yyyy");
					model.setAckDate(date);
				}
				if (request.getPart("file_" + voucherNo + "_" + paymentId) != null) {
					Part file = request.getPart("file_" + voucherNo + "_" + paymentId);

					String filePath = uplaodFile(file, voucherNo);
					model.setFilePath(filePath);
				}
				if (model.getPaymentId() != null && model.getAckNo() != null && model.getAckDate() != null) {
					childList.add(model);
				}

			}
			if (!childList.isEmpty() && childList != null) {

				parentModel.setVoucherAckDetails(childList);
			}

			DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class,
					"POST SAVR MODEL :::" + parentModel);
			ackResponse = restTemplate.postForObject(
					PcdaConstant.VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL + "/saveVoucherAcknowledgementDetails",
					parentModel, PostVoucherAckSPResponse.class);
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckSPService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
		}
		DODLog.info(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckSPService.class," ackResponse:::" + ackResponse);
		return ackResponse;
	}

//File Upload
	public String uplaodFile(Part file, String voucherNo) {

		Path uploadPath = Paths.get(FileUploadConstant.FILE_UPLOAD + voucherNo + FileUploadConstant.SLASH
				+ Calendar.getInstance().getTimeInMillis() + FileUploadConstant.SLASH);
		String savePath = "";
		try (InputStream inputStream = file.getInputStream()) {
			Files.createDirectories(uploadPath);
			Path filePath = uploadPath.resolve(file.getName()+".pdf");
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			savePath = filePath.toString();
		} catch (Exception e) {
			DODLog.printStackTrace(e, VoucherAckSPService.class, LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE);
		}
		return savePath;
	}

	public Map<String, String> getApprovedPao(String office, String approvalType) {
	
		List<OfficeModel> officeModelList = officeservice.getOffices(office, approvalType);

		Map<String, String>	paoMap = officeModelList.stream().collect(Collectors.toMap(OfficeModel::getGroupId, OfficeModel::getName));

		return paoMap;
	}
}
