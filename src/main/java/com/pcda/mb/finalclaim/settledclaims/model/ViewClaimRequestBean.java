package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Set;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewClaimRequestBean  {

	    private int claimType;	
	    private String mainClaimId;	
	    private String personalNo;
	    private String tadaClaimId ;
	    private String levelName;
	    private String name;
	    private String unitName;
	    private Double totalSpentAmount = 0.0;
		private Double totalAdvanceAmount = 0.0;
		private Double totalRefundAmount = 0.0;
		private Double totalClaimedAmount = 0.0;
		private Double totalApprovedAmnt =0.0;
		private Double totalApprovdAdvnc = 0.0;
		private Double totalApprovdRfnd = 0.0;
		private Double passedAmount = 0.0;
		private String remarks;
		private String travelTypeName;
		private String travelTypeId;
		private String claimStld;
		private String coApproved;
		private String coApprovedDate;
		private String travelID; 
		private String trRuleName;
		private String moveSanctionNo;
		private String movSanctionDate;
		private String jrnyStrtdFrom;
		private String jrnyStrtdTime;
		private String claimSts;
		private String claimPriority;
		private String travelStartDate;
		private String travelEndDate;
		private String cdaoAcc;
		private String claimLimit;
		private int armedfrc;
		private String designatin;
		private String basicPay;
		private String leaveFrom;
		private String leaveTo;
		private String hometown;
		private String leaveStn;
		private String ltcBlockYr;
		private String ltcCalYr;
		private String payAccntNo;	
		private String trnsfrFrom;
		private String trnsfrTo;
		private String jrnyStrtdTo;
		private String jrnyFnshTime;
		private String moveIssungAuth;				
		private Double offcrPayAmnt =0.0;		
		private String bankAccNumber;
		private String ifscCode;		
	
	private ViewClaimAdvanceDtlsBean claimAdvanceDtls;
	private Set<ViewClaimLeaveDtlsBean> claimLeaveDtls;	
	private ViewTaDaJourneyDetails taDaJourneyDetails;
	private Set<ViewClaimHotelDtlsBean> claimHotelDtls;
	private Set<ViewClaimFoodDtlsBean> claimFoodDtls;
	private ViewClaimConyncDtlsBean claimConyncDtls ;
	private Set<ViewPayDtls> claimpayDtls;
	private Set<ViewClaimStatusDtlsBean> claimStatusDtls;
	private Set<ViewClaimDocDtlsBean> claimDocDtls;
    private Set<ViewClaimPersonalEffectsBean> claimPersonalEffectsBean;
	private Set<ViewClaimCertifyDtlsBean> claimCertifyView;
	

	
}
