package com.pcda.common.model;

import com.pcda.util.ApprovalState;
import com.pcda.util.CityGrade;
import com.pcda.util.Status;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class TaDaExpense{
	
	private String taDaExpenseId;
	private Status status;
	private ApprovalState approvalState;
	private String rankId;
	private String serviceId;
	private String categoryId;
	private String levelId;
	private Double hotelAmount;
	private Double convenceAmount;
	private Double foodAmount;
	private Double miscAmount;
	private Double kilometerRate;
	private long luggageWeight;
	private Double vehicleTransportRate;
	private CityGrade cityGrade;
	private String remark;
	private Double hamount;
	private Double rationMoney;
	private YesOrNo isConveyanceDtls;
	private Double levelAMESSAmount;
	private Double levelBMESSAmount;
	private Double levelCMESSAmount;
	private YesOrNo isFourWheeler;
	
	
}
