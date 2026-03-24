package com.pcda.co.requestdashboard.claimreqapproval.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ApprovalClaimConyncDtlsBean {

   private List<ApprovalTaDaLocalCon> taDaLocalCon;	
    private int isConveyanceDtls =0; 
    
}
