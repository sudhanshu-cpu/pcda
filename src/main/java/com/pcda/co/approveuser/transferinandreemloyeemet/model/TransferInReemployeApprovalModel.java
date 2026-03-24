package com.pcda.co.approveuser.transferinandreemloyeemet.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class TransferInReemployeApprovalModel {
	private BigInteger userId;
	private String userAlias;
	private String firstName;
	private String middleName;
	private String lastName;
	private int seqNo;
	private String oldProfileAtrribute;
	private String profileChangeAtrribute;
	private String serviceName;
	private String   categoryName;
	private String ame;
	private String rankName;
	private String accountOffice;
	private String createdBy;
	private String[] profileChangeAtrributeSplit;
	
	private String profileAttributeName;
	
	
	
	private List<String>  profileChangeAttributeList;
	
	
	

}
