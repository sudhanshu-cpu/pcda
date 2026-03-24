package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class ViaJourneyDetailModel {

	private String requestId;
	private List<ViaJourneyDetailBean> viajourneyDetailList;
}
  