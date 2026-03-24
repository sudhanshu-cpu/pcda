package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherTxnCgdaModel {

	List<GetOutVoucherGenCgdaDataModel> dataModels;
}
