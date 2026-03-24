package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import lombok.Data;

@Data
public class GetAirDashPassangerDetail implements Comparable<GetAirDashPassangerDetail> {
	private int passangerSeqNo;
	private int passangerNo;
	private String invoiceNo;
	private String onwardPaxId;
	private String returnPaxId;
	private int onwardCancelInt;
	private String onwardCancel;
	private int returnCancelInt;
	private String returnCancel;
	private int onwardIsOfficialInt;
	private String onwardIsOfficial;
	private int onwardIsOnGovtInt;
	private String onwardIsOnGovt;
	private int returnIsOfficialInt;
	private String returnIsOfficial;
	private int returnIsOnGovtInt;
	private String returnIsOnGovt;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String relation;
	private int age;
	private String dob;
	private String mobileNo;
	private String emailId;
	private int passProfileSeq;
	private String gender;
	private String leadPassanger;
	private String ticketBookingStatus;
	private String passangerType;
	private String canSegment;
	private String intendedCan;
	
	@Override
	public int compareTo(GetAirDashPassangerDetail o) {
		
		return o.getPassangerNo()- passangerNo;
	}
	
	
}
