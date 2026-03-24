package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoucherTxnCdaModel {

	List<GetOutVoucherGenCdaDataModel> dataModels;
}
