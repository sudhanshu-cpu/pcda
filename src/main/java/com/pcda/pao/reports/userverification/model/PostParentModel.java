package com.pcda.pao.reports.userverification.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostParentModel {

	private BigInteger loginUser;
	private List<PostVerifyMissDeModel> postVerifyMissDeModels; 
	private int chekArr;	
	private String userAction;
	
}
