package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateClaimConyncDtlsBean {

    private Integer conveyanceDtls;
    private String tourVehicleNo;   
    private Integer tourNoOfDays;
    private Date tourDate;
    private Integer tourDistance;
    private String tourBillNo; 
    private Double tourAmount;
    private Double actualTourAmount;      

}
