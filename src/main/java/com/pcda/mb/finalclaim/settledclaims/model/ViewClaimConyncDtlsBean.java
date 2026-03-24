package com.pcda.mb.finalclaim.settledclaims.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ViewClaimConyncDtlsBean {

   private List<TaDaLocalCon> taDaLocalCon;	
    private int isConveyanceDtls =0; 
    
}
