package com.pcda.mb.finalclaim.settledclaims.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewPayDtls {

	
	   private Integer paymnetMade;  
	    private String paymnetTo;
	    private Double amount; 
	    
	    private String  utrNumber;
	    private String  utrDate;
	    private String  ifscCode;
	    private String  accountNumber;
	  

  
    
}
