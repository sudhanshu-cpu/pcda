package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetBkgcfg {

    @JsonProperty("seniorCitizenApplicable")
    private String seniorcitizenapplicable;
    
    @JsonProperty("foodChoiceEnabled")
    private String foodchoiceenabled;
    
    @JsonProperty("idRequired")
    private String idrequired;
    
    @JsonProperty("bedRollFlagEnabled")
    private String bedrollflagenabled;
    
    @JsonProperty("maxMasterListPsgn")
    private String maxmasterlistpsgn;
    
    @JsonProperty("maxPassengers")
    private String maxpassengers;
    
    @JsonProperty("maxInfants")
    private String maxinfants;
    
    @JsonProperty("minNameLength")
    private String minnamelength;
    
    @JsonProperty("maxNameLength")
    private String maxnamelength;
    
    @JsonProperty("srctznAge")
    private String srctznage;
    
    @JsonProperty("srctnwAge")
    private String srctnwage;
    
    @JsonProperty("maxARPDays")
    private String maxarpdays;
    
    @JsonProperty("maxRetentionDays")
    private String maxretentiondays;
    
    @JsonProperty("minPassengerAge")
    private String minpassengerage;
    
    @JsonProperty("maxPassengerAge")
    private String maxpassengerage;
    
    @JsonProperty("maxChildAge")
    private String maxchildage;
    
    @JsonProperty("minIdCardLength")
    private String minidcardlength;
    
    @JsonProperty("maxIdCardLength")
    private String maxidcardlength;
    
    @JsonProperty("minPassportLength")
    private String minpassportlength;
    
    @JsonProperty("maxPassportLength")
    private String maxpassportlength;
    
    @JsonProperty("srctzTAge")
    private String srctztage;
    
    @JsonProperty("lowerBerthApplicable")
    private String lowerberthapplicable;
    
    @JsonProperty("newTimeTable")
    private String newtimetable;
    
    @JsonProperty("childBerthMandatory")
    private String childberthmandatory;
    
    @JsonProperty("validIdCardTypes")
    private List<String> valididcardtypes;
    
    @JsonProperty("applicableBerthTypes")
    private List<String> applicableberthtypes;
    
    @JsonProperty("suvidhaTrain")
    private String suvidhatrain;
    
    @JsonProperty("specialTatkal")
    private String specialtatkal;
    
    @JsonProperty("atasEnable")
    private String atasenable;
    
    @JsonProperty("gatimaanTrain")
    private String gatimaantrain;
    
    @JsonProperty("travelInsuranceEnabled")
    private String travelinsuranceenabled;
    
    @JsonProperty("travelInsuranceFareMsg")
    private String travelinsurancefaremsg;
    
    @JsonProperty("uidVerificationPsgnInputFlag")
    private String uidverificationpsgninputflag;
    
    @JsonProperty("uidVerificationMasterListFlag")
    private String uidverificationmasterlistflag;
    
    @JsonProperty("uidMandatoryFlag")
    private String uidmandatoryflag;
    
    @JsonProperty("gstDetailInputFlag")
    private String gstdetailinputflag;
    
    @JsonProperty("gstinPattern")
    private String gstinpattern;
    
    @JsonProperty("forgoConcession")
    private String forgoconcession;
    
    @JsonProperty("twoSSReleaseFlag")
    private String twossreleaseflag;
    
    @JsonProperty("beyondArpBooking")
    private String beyondarpbooking;
    
    @JsonProperty("acuralBooking")
    private String acuralbooking;
    
    @JsonProperty("redemptionBooking")
    private String redemptionbooking;
    
    @JsonProperty("trainsiteId")
    private String trainsiteid;
    
    @JsonProperty("bonafideCountryList")
    private List<String> bonafidecountrylist;
    
    @JsonProperty("pmfInputEnable")
    private String pmfinputenable;
    
    @JsonProperty("pmfInputMandatory")
    private String pmfinputmandatory;
    
    @JsonProperty("pmfInputMaxLength")
    private String pmfinputmaxlength;
    
    @JsonProperty("captureAddress")
    private String captureaddress;

    
    @JsonProperty("foodDetails")
    private List<String> foodDetails;

}
