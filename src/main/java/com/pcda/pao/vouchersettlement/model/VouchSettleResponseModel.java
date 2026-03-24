package com.pcda.pao.vouchersettlement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class VouchSettleResponseModel {

    VoucherParentModel voucherParentModel;
    PostFormFieldModel postFormFieldModel;
    List<GetVoucherSetListDataModel> dataModelList;

}
