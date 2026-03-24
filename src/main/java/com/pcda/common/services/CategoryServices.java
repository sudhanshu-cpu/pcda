package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.CategoryResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class CategoryServices {

	@Autowired
	private RestTemplate template;
	
	public Optional<Category> getCategory(String serviceId, String categoryId) {
		DODLog.info(LogConstant.COMMON_LOG, CategoryServices.class, "Get Categories with service id: " + serviceId+" | categoryId:"+categoryId);

		Category category=null;
		try {
		ResponseEntity<CategoryResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/category/getCategory/" + serviceId+"/"+categoryId, HttpMethod.GET, null,
				new ParameterizedTypeReference<CategoryResponse>() {});
		CategoryResponse categoryResponse = responseEntity.getBody();

		if (null != categoryResponse && (categoryResponse.getErrorCode() == 200) && null != categoryResponse.getResponse()) {
			category = categoryResponse.getResponse();
			DODLog.info(LogConstant.COMMON_LOG, CategoryServices.class, " category "+category);
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CategoryServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(category);
	}
	
	public List<Category> getCategories(String serviceId) {
		

		List<Category> categoryList = new ArrayList<>();
		try {
		ResponseEntity<CategoryResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/category/getCategories/" + serviceId, HttpMethod.GET, null,
				new ParameterizedTypeReference<CategoryResponse>() {});
		CategoryResponse categoryResponse = responseEntity.getBody();

		if (null != categoryResponse && (categoryResponse.getErrorCode() == 200) && null != categoryResponse.getResponseList()) {
			categoryList.addAll(categoryResponse.getResponseList());
			categoryList=categoryList.stream().filter(e->e.getStatus().equals("ON_LINE")).toList();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CategoryServices.class, LogConstant.COMMON_LOG);
		}
		
		DODLog.info(LogConstant.COMMON_LOG, CategoryServices.class, " categoryList size "+categoryList.size());
		return categoryList;
	}
	
	public Optional<Category> getCategoryByCatId(String categoryId) {
		DODLog.info(LogConstant.COMMON_LOG, CategoryServices.class, "Get Categories with categoryId:"+categoryId);

		Category category=null;
		try {
		ResponseEntity<CategoryResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/category/getCategoryByCatId/"+categoryId, HttpMethod.GET, null,
				new ParameterizedTypeReference<CategoryResponse>() {});
		CategoryResponse categoryResponse = responseEntity.getBody();

		if (null != categoryResponse && (categoryResponse.getErrorCode() == 200) && null != categoryResponse.getResponse()) {
			category = categoryResponse.getResponse();
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CategoryServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(category);
	}
}
