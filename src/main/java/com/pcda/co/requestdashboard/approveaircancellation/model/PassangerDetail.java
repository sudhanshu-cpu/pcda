package com.pcda.co.requestdashboard.approveaircancellation.model;



import lombok.Data;

@Data
public class PassangerDetail implements Comparable<PassangerDetail>{
	
	private Integer passangerSeqNo;
	private Integer passangerNo;
	
	private String invoiceNo;
	private String onwardPaxId;
	
	private String returnPaxId;
	private Integer onwardCancelInt;
	
	private String onwardCancel;
	private Integer returnCancelInt;
	
	private String returnCancel;
	private Integer onwardIsOfficialInt;

	private String onwardIsOnGovt;
	private Integer returnIsOfficialInt;
	
	private String onwardIsOfficial;
	private Integer onwardIsOnGovtInt;
	
	private String returnIsOfficial;
	private Integer returnIsOnGovtInt;
	
	private String returnIsOnGovt;
	private String title;

	private String firstName;
	private String middleName;

	private String lastName;
	private String relation;
	
	private Integer age;
	private String dob;

	private Double mobileNo;
	private String emailId;

	private Integer passProfileSeq;
	private String gender;
	
	private String leadPassanger;

	private String ticketBookingStatus;
	private String passangerType;
	
	
	@Override
	public int compareTo(PassangerDetail o) {
	
		return  passangerNo-o.getPassangerNo();
	}
}

