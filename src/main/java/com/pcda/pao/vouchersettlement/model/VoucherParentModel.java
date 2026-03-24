package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

import java.util.List;

@Data
public class VoucherParentModel {

    private String voucherNo;
    private String creationTime;
    private String voucherStatus;
    private Double totalAmount;
    private Double balanceTotalAmount;
    private Double outstandingAmount;
    private Double balanceOutstandingAmount;
    private String remark;
    private String accountOffice;
    private String serviceProvider;
    private int pdfGenerated;
    private String filePath;
    private String balanceAvailable;
    private List<VoucherChildModel> voucherDetails;
    private double calSettleAmount;
    private double calPendingAmount;


}
