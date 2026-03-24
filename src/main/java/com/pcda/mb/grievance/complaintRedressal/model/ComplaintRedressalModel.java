package com.pcda.mb.grievance.complaintRedressal.model;

import java.math.BigInteger;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplaintRedressalModel {

	private BigInteger loginUserId;
	private String groupId;
	@NotNull(message = "Type - Cannot be blank")
	private String grievanceCategory;
	
	private String personalNo;
	private String mobileNo;
	private String grievanceText;
	private List<FileDetails> fileDetails;
	private Integer raisedFrom=0;

	

	
	
}
