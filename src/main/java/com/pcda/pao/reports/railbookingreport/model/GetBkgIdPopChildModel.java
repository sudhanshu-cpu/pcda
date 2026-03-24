package com.pcda.pao.reports.railbookingreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBkgIdPopChildModel {

	private String name;
	private String gender;

	private Integer age;
	private String coach;

	private String berth;
	private String seat;

	private Integer basefare;
	private Integer reservationCharge;

	private Integer superFastCharge;
	private Integer otherCharge;

	private Integer concessionAmt;
}
