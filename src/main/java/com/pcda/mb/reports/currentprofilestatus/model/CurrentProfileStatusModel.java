package com.pcda.mb.reports.currentprofilestatus.model;



import java.math.BigInteger;


import com.pcda.util.ApprovalState;
import com.pcda.util.UserEditType;

import lombok.Data;

@Data
public class CurrentProfileStatusModel {

	private String name;
	private BigInteger userId;
	private String userAlias;
	private String remark;
	private ApprovalState profileStatus;
	private UserEditType userEditType;
}
