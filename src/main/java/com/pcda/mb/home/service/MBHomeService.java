package com.pcda.mb.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.ContentResponse;
import com.pcda.util.PcdaConstant;

@Service
public class MBHomeService {

	@Autowired
	private RestTemplate restTemplate;

	public Map<String, String> getAllConetntData() {
		Map<String, String> resultMap = new HashMap<>();
		ContentResponse contentResponse = restTemplate.getForObject(PcdaConstant.CONTENT_URL + "/getAllContent", ContentResponse.class);
		if (contentResponse != null && contentResponse.getErrorCode() == 200 && contentResponse.getResponse() != null) {
			resultMap.putAll(contentResponse.getResponse());
		}
		return resultMap;
	}

	public Map<String, String> getContent(List<String> contentName, String selectedContentName, String heading) {
		Map<String, String> resultMap = getAllConetntData();
		Map<String, String> contentMap = new HashMap<>();
		contentName.forEach(e -> contentMap.put(e, resultMap.get(e)));
		contentMap.put("selectedContentName", resultMap.get(selectedContentName));
		contentMap.put("heading", heading);
		return contentMap;
	}

}
