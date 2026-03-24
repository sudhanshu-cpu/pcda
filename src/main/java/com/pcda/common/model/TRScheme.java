package com.pcda.common.model;

import java.util.Date;

import lombok.Data;

@Data
public class TRScheme {

	private Integer maxYearsPerBlock;
	private Integer maxYearsPerSubblock;
	private Integer maxCountAllowedPerBlock;
	private Integer maxCountAllowedPerSubblock;
	private Integer blockExtendableYears;
	private Integer subblockExtendableYears;
	private Integer blockStartYear;
	private Integer blockEndYear;
	private Date schemeAppliedDate;
}
