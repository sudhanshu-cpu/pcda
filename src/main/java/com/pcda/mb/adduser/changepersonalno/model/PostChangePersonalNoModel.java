package com.pcda.mb.adduser.changepersonalno.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostChangePersonalNoModel {

	private String userAlias;
    private BigInteger loginUserId ;
	private String newPersonalNo;
	private String unitServiceId;
	private String travelerServiceId;
	private String category;
	private String rankId;
	private String levelId;
	private String payAcOff;
	private String airAcOff;
	private String oldPersonalNo;
	private String reason;
}
