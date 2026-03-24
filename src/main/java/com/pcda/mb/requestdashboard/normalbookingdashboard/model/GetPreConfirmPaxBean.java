package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetPreConfirmPaxBean implements Comparable<GetPreConfirmPaxBean> {
	private Integer reqPxnNo;
	private String reqPxnName;
	private Integer reqPxnAge;
	private Integer pxnGender;
	private Integer pxnType;
	private String isMasterPass;
	private Integer passNo;
	@Override
	public int compareTo(GetPreConfirmPaxBean o) {
		
		return passNo - o.getPassNo();
	}

}
