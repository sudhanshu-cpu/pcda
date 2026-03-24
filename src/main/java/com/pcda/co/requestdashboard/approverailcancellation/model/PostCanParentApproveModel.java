package com.pcda.co.requestdashboard.approverailcancellation.model;



import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCanParentApproveModel {

	private String ticketNo;
	private String bookingId;
	private Integer totalPessnager;
	private BigInteger loginUserID = BigInteger.ZERO;
	List<PostCanChildApproveModel> cancelPsngrList;
	
}
