package com.pcda.pao.vouchersettlement.model;

import lombok.Data;

import java.util.List;

@Data
public class VoucherDetailsResponse {

    private int errorCode;
    private String errorMessage;
    private VoucherParentModel response;
    private List<VoucherParentModel> responseList;

}
