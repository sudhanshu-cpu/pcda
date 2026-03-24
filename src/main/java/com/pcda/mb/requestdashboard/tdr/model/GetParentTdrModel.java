package com.pcda.mb.requestdashboard.tdr.model;

import java.util.Date;
import java.util.List;

import com.pcda.util.ApprovalState;
import com.pcda.util.BookingStatus;
import com.pcda.util.RailTicketType;
import com.pcda.util.ReconState;
import com.pcda.util.RefundStatus;
import com.pcda.util.TdrApprovalState;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class GetParentTdrModel {

	
	private Integer sno;
	private String accountOffice;
	private Double baseFare;
	private Date boardingDate;
	
	
	private Date bookingDate;
	
	private String boardingDateStr;
	private String bookingDateStr;

	private String bookingId;
	private Date dateOfBooking;
	private String canceltnReason;
	private Date creationTime;
	private BookingStatus currbookingStatus;
	private RefundStatus currentRefndStatus;
	private Integer distance;
	private String excpCanGroupId;
	private String frmStn;
	private String groupId;
	private Double irctcSerCharge;
	private YesOrNo isGc;
	private Double gcAmount;
	private String gcNumber;
	private Date gcDate;
	private RailTicketType irctcTktType;
	private ApprovalState isCanApproved;
	private TdrApprovalState tdrApprovalState;
	private YesOrNo isTatkal;
	private String journeyClass;
	private String partialCanStatus;
	private String pnrNo;
	private String journeyQuota;
	private ReconState reconStatus;
	private String requestId;
	private Integer requestSeqNo;
	private Integer reservationChoice;
	private String sessionId;
	private String scheduleDepartre;
	private String slipRoute;
	private String ticketNo;
	private String toStn;
	private String trainName;
	private String trainNumber;
	private Double totalFare;
	private String remark;
	private YesOrNo isTdr;
	private YesOrNo tdrGovtInt;
	private YesOrNo tdrOnOfficalGround;
	private YesOrNo isTdrSuccess;
	private TdrApprovalState isTdrApproved;
	private String tdrApproved;
	private String personalNo;
	private String error;
	private String	tdrFiledOrNot;
	private String reasonOfTdr;
	
	List<GetTdrChildModel> passengerList =null;
}
