package com.pcda.mb.reports.railtravelreports.model;

import lombok.Data;

@Data
public class TicketsPassangerDetails implements Comparable<TicketsPassangerDetails> {

	private int seqNo;

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
	private Integer diffAmount;

	private Integer concessionAmt;

	@Override
	public int compareTo(TicketsPassangerDetails o) {
		
		return this.seqNo - o.getSeqNo();
	}

}
