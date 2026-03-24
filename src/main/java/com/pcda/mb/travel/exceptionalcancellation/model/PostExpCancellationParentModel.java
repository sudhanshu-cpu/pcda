package com.pcda.mb.travel.exceptionalcancellation.model;

import java.math.BigInteger;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostExpCancellationParentModel {

private List<PostExpCanChildModel>passengerList;	


private String cancellationString="";
private String bookingId;
private String ticketNo;
private String check="";
private String groupId;


@NotNull
@Pattern(message = "Spaces are not Allowed", regexp = "^[a-z A-Z 0-9]*$")
private String cancelReason  ;
private String frmReq="";
 private String dodBookingRefNo="";
 private BigInteger	loginUserId	=BigInteger.ZERO;

}
