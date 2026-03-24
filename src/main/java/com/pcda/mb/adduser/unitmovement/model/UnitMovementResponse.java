package com.pcda.mb.adduser.unitmovement.model;




import java.util.List;

import lombok.Data;
@Data
public class UnitMovementResponse {


	private String errorMessage;

	private int errorCode;

	private UnitMovementModel response;

	private List<UnitMovementModel> responseList;

}
