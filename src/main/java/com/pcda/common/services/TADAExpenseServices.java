package com.pcda.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TADAExpenseResponse;
import com.pcda.common.model.TaDaExpense;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TADAExpenseServices {

	@Autowired
	private RestTemplate template;

	public TaDaExpense getTADAExpenseDataByRank(String serviceId,String categoryId,String rankId,String cityRankId) {
		
		
		TaDaExpense daExpense=null;
		try {
		ResponseEntity<TADAExpenseResponse> serviceEntity =template.getForEntity(PcdaConstant.MASTER_BASE_URL + "/TaDaExpense/getTaDaExpenseByRank?serviceId={serviceId}&categoryId={categoryId}&rank={rank}&cityGrade={cityGrade}"
				, TADAExpenseResponse.class, serviceId,categoryId,rankId,cityRankId);
	

		TADAExpenseResponse expenseResponse = serviceEntity.getBody();
		
		
		if (null != expenseResponse && expenseResponse.getErrorCode() == 200) {
			daExpense=expenseResponse.getResponse();
		}

		}catch(Exception e) {
			DODLog.printStackTrace(e, TADAExpenseServices.class, LogConstant.COMMON_LOG);
		}
		
		return daExpense;
	}
	
	public TaDaExpense getTADAExpenseDataByLevel(String serviceId,String categoryId,String levelId,String cityRankId) {
		
		TaDaExpense daExpense=null;
try {
		ResponseEntity<TADAExpenseResponse> serviceEntity =template.getForEntity(PcdaConstant.MASTER_BASE_URL + "/TaDaExpense/getTaDaExpenseByRank?serviceId={serviceId}&categoryId={categoryId}&level={rank}&cityGrade={cityGrade}"
				, TADAExpenseResponse.class, serviceId,categoryId,levelId,cityRankId);
	

		TADAExpenseResponse expenseResponse = serviceEntity.getBody();
		
		
		if (null != expenseResponse && expenseResponse.getErrorCode() == 200) {
			daExpense=expenseResponse.getResponse();
		}

}catch(Exception e) {
	DODLog.printStackTrace(e, TADAExpenseServices.class, LogConstant.COMMON_LOG);
}
		return daExpense;
	}
	
}
