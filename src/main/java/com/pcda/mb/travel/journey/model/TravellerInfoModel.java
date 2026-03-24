package com.pcda.mb.travel.journey.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data 
public class TravellerInfoModel {

	private String message;
	private int allowedRequestCountInBlock;
	private int allowedRequestCountInSubBlock;
	private int blockExtendableYear;
	private int subBlockExtendableYear;
	private String currentBlockYear;
	private String nextBlockYear;
	private int onwardRequestCountInCurrentBlock;
	private int returnRequestCountInCurrentBlock;
	private int onwardRequestCountInNextBlock;
	private int returnRequestCountInNextBlock;
	private String isSchemeApplied;
	private String currentSubBlockYear;
	private String nextSubBlockYear;
	private int onwardRequestCountInPreviousSubBlock;
	private int returnRequestCountInPreviousSubBlock;
	private int onwardRequestCountInCurrentSubBlock;
	private int returnRequestCountInCurrentSubBlock;
	private int onwardRequestCountInNextSubBlock;
	private int returnRequestCountInNextSubBlock;
	private String bothBlockAreSame;
	private long allowedTravels;
	private String spousePanNumber;
	private int isOnSuspension;
	private String isOnSuspensionStr;
	private int noOFRequestsOnward;
	private int noOFRequestsReturn;
	private int noOFRequestsNextOnward;
	private int noOFRequestsNextReturn;
	private int requestCreated;
	private String checkRtrmntAge;
	private long attendentCount;
	private Map<Integer, String> retationType;
	private Map<Integer, String> gender;
	private String isPartyBooking;
	private String depHigherClassAllowed;
	private List<StationListFromModel> fromStationList;
	private List<StationListToModel> toStationList;
	private List<TRQuestionsModel> trQuestion;
	private List<AirStationFromModel> airStationListFrom;
	private List<AirStationToModel> airStationListTo;
	private List<String> requisiteDtls;
	private String unitName;
	private String masterTravelerUnitName;
	private String railPAOId;
	private String airPAOId;
	private String office;
	private String airOffice;
	private String officeID;
	private String civilianService;
	private BigInteger userID;
	private String firstName;
	private String middleName;
	private String lastName;
	private String service;
	private String unitService;
	private String serviceId;
	private String category;
	private String categoryId;
	private String codeHead;
	private String airCodeHead;
	private String levelId;
	private String levelName;
	private String travellerOfficeId;
	private String rankName;
	private String rankID;
	private Map<Integer, String> entiltedClass;
	private String airRankName;
	private String airRankID;
	private Map<Integer, String> airEntiltedClass;
	private boolean tadaPermission;
	private boolean ruleTadaPermission;
	private boolean ruleTadaHotelPermission;
	private boolean ruleTadaConveyancePermission;
	private boolean ruleTadaFoodPermission;
	private int ruleDaAdvanceDay;
	private boolean isAccountDtlsUpdate;
	private String travlerBankAccountNumber;
	private String travlerIfscCode;
	private String dateOfBirth;
	private String dateOfRetire;
	private String isExist;
	private String isMixApplicable;
	private String dateOfCommisioning;
	private Integer hospitalUnit;
	private Map<Integer, String> travelMode;
	
	private List<FamilyDetailModel> familyDetail;
	
	private List<Map<String, String>> formDAndGYear;
	
}
