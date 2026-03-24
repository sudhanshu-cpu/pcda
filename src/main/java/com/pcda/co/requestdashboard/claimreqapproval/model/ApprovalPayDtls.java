package com.pcda.co.requestdashboard.claimreqapproval.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApprovalPayDtls {

	
	   private Integer paymnetMade;  
	    private String paymnetTo;
	    private Double amount; 
	    
	    private String  utrNumber;
	    private String  utrDate;
	    private String  ifscCode;
	    private String  accountNumber;
	  

  
    
}
