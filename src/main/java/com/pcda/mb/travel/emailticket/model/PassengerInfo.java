package com.pcda.mb.travel.emailticket.model;

import lombok.Data;

@Data
public class PassengerInfo implements Comparable<PassengerInfo>{

  private	String ticketNumber;
  private	String title;
  
  
  private	String firstName;
  private	String middleName;

  private	String lastName;
  private	String passCategory;
  
  private	String mobileNo;
  private	String emailId;

  private	String invoiceNo;
  private	int passangerNo;
  private	int leadPassanger;
@Override
public int compareTo(PassengerInfo o) {
	return  passangerNo-o.getPassangerNo();
}  
  
  
}
