package com.pcda.common.model;

import com.pcda.util.ApprovalState;
import com.pcda.util.SectorStation;
import com.pcda.util.Status;

import lombok.Data;   

@Data
public class LtcSpecialSectorDtls {

   private Integer seqNo;
   private String stationCode;    
   private String stationName;
   private SectorStation stationType;
   private ApprovalState approvalState;
   private String remarks;  
   private Status status;

}
