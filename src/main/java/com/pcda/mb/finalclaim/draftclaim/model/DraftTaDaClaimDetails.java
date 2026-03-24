package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftTaDaClaimDetails {

    private String personalNo;
    private String tadaClaimId ;
    private String levelName;
    private String levelID;
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
	private Integer armedfrc;
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
	private String travelOid;
	private Integer claimDocAllow;
	private Integer claimPassCount;

	private DraftAdvanceDtlsBean claimAdvanceDtls;
	private DraftLeave draftLeave;
	private List<DraftJourneyDetails> taDaJourneyDetails;
	private DraftNonDtsJrny nonDtsJrny;
	private DraftHotelDtls hotelDtls;
	private DraftFoodDtls foodDtls;
	private DraftConyncDtls conyncDtls;
	private List<DraftCertifyDtlsBean> certifyDetails;
	private List<DraftStatusDtlsBean> statusDetails;
	private List<DraftPersonalEffectsDtlsBean> personalEffects;
	private List<DraftDocDtlsBean> docDetails;

}
