package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetPreConfirmParentModel {
	private GetSearchRequestBean searchReqDtls;
	private GetItinaryRequestBean itinaryReq;
	private GetBkgcfg bookingConfig;

}
