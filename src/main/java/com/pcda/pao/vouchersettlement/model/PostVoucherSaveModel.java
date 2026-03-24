package com.pcda.pao.vouchersettlement.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherSaveModel {

	private String voucherNo;
	@NotBlank(message = "Amount To be Settles Cannot be Blank")
	private double amountToSettle;
	private BigInteger loginUserId;
	@NotBlank(message = "UTR No. Cannot Be Blank")
	private String utrNumber;
	private Date utrDate;
	private List<String> txnIds;
	@NotBlank(message = "UTR Date Cannot Be Blank")
    private String trDate;

}
