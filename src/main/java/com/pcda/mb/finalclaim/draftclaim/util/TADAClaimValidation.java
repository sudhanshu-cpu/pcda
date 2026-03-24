package com.pcda.mb.finalclaim.draftclaim.util;

import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimUpdateBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimConyncDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimFoodDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimJourneyDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimNonDtsJrnyDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimPassDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.UpdateClaimPersonalEffectsBean;
import com.pcda.util.StringValidation;

public class TADAClaimValidation {

	public static String validateClaimData(DraftClaimUpdateBean saveTADAClaimDTO){
		
		String resultMsg="OK";
		
		
		if(StringValidation.isEmpty(saveTADAClaimDTO.getMoveSanctionNo())){
			resultMsg="Move Sanction No should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getMovSanctionDate()){
			resultMsg="Move Sanction Date should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getJrnyStrtdFrom())){
			resultMsg="Journey Commenced From should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getJrnyStrtdTime()){
			resultMsg="Journey Commenced Date should not be blank.";
			return resultMsg;
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()){
			resultMsg="Advance Drawn from PAO should be Numeric.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()>0){
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAODate()){
				resultMsg="Date of Advance Drawn from PAO should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOther()){
			resultMsg="Advance Drawn from Other Sources should be Numeric.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOther()>0){
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOtherVoucherNo())){
				resultMsg="Voucher No for Advance Drawn from Other Sources should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOtherDate()){
				resultMsg="Date of Advance Drawn from Other Sources should not be blank.";
				return resultMsg;
			}
		}

		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()){
			resultMsg="eMRO/MRO/Refund Amount should be Numeric.";
			return resultMsg;
		}

		if(saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()>0){
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getMroNumber())){
				resultMsg="eMRO/MRO/Refund No should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroDate()){
				resultMsg="eMRO/MRO Submission Date/Refund Date should not be blank.";
				return resultMsg;
			}
		}

		String[] certify=saveTADAClaimDTO.getClaimCertify();
		if(null== certify || certify.length==0){
			resultMsg="Please check Certify that.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getServiceType()==1){
			
		}else{
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getCounterPersonalNo())){
				resultMsg="Counter Singing Authority Personal No should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredDate()){
			resultMsg="Date of Claim preferred by the Individual to the Office should not be blank.";
			return resultMsg;
		}
		
		if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			resultMsg="If Date of Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken is not selected";
			return resultMsg;
		}
		if("Yes".equalsIgnoreCase(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionNo())){
				resultMsg="Sanction No of Claim preferred should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionDate()){
				resultMsg="Sanction Date of Claim preferred should not be blank.";
				return resultMsg;
			}
		}

		for(UpdateClaimJourneyDtlsBean journeyDTO: saveTADAClaimDTO.getClaimJourneyDtls()){

			if(null == journeyDTO.getJourneyStartDate()){
				resultMsg="DTS Journey Start Date should not be blank.";
				return resultMsg;
				
			}
			
			if(null == journeyDTO.getJourneyEndDate()){
				resultMsg="DTS Journey End Date should not be blank.";
				return resultMsg;
			}
			if(null == journeyDTO.getJourneyPerformed()){
				resultMsg="DTS Journey Performed should be Numeric.";
				return resultMsg;
			}
			
			if(journeyDTO.getJourneyPerformed()==1){
				
				if(null == journeyDTO.getJrnyCanType()){
					resultMsg="DTS Journey Cancellation Ground should be blank.";
					return resultMsg;
				}
				
				if(journeyDTO.getJrnyCanType()==1){
					
					if(null == journeyDTO.getJrnyCanSanDate()){
						resultMsg="DTS Journey Cancellation Sanction Date should not be blank.";
						return resultMsg;
					}
					
					if(StringValidation.isEmpty(journeyDTO.getJrnyCanSanNo())){
						resultMsg="DTS Journey Cancellation Sanction No should not be blank.";
						return resultMsg;
					}
		        }
		  }
		}
		
		for(UpdateClaimLeaveDtlsBean leavePeriodDTO:saveTADAClaimDTO.getClaimLeaveDtls()){
			
			if(null == leavePeriodDTO.getLeaveDate()){
				resultMsg="Leave Date should not be blank.";
				return resultMsg;
			}
			
			if(null == leavePeriodDTO.getLeaveFullHalf()){
				resultMsg="Full/Half should not be blank.";
				return resultMsg;
			}
			
		}

		for(UpdateClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO:saveTADAClaimDTO.getClaimNonDtsJrnyDtls()){
			
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSFromPlace())){
				resultMsg=" Non DTS Journey From should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSToPlace())){
				resultMsg=" Non DTS Journey From should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSModeOfTravel())){
				resultMsg=" Non DTS Journey Travel Mode should not be blank.";
				return resultMsg;
			}

			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSClassOfTravel())){
				resultMsg="Non DTS Journey Travel Class should not be blank.";
				return resultMsg;
			}
			
			if(null == nonDTSJourneyDTO.getNonDTSJryStartDate()){
				resultMsg="Non DTS Journey Start Date should not be blank.";
				return resultMsg;
			}
			
			if(null == nonDTSJourneyDTO.getNonDTSJryEndDate()){
				resultMsg="Non DTS Journey End Date should not be blank.";
				return resultMsg;
			}
			
			if(null ==nonDTSJourneyDTO.getNonDTSDistanceKM()){
				resultMsg="Non DTS Journey Distance by Road in Km should not be Null.";
				return resultMsg;
			}

			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSTicketNo())){
				resultMsg="Non DTS Bill/PNR/Ticket No should not be blank.";
				return resultMsg;
			}

			if(null == nonDTSJourneyDTO.getNonDTSJryAmount()){
				resultMsg="Non DTS Journey Booking Amount should not be Null.";
				return resultMsg;
			}

			if(null == nonDTSJourneyDTO.getNonDTSJryRefundAmount()){
				resultMsg="Non DTS Journey Refund Amount should not be Null.";
				return resultMsg;
			}

			if(null == nonDTSJourneyDTO.getNonDTSJryPerformed()){
				resultMsg="Non DTS Journey Performed should not be blank.";
				return resultMsg;
			}

			if(nonDTSJourneyDTO.getNonDTSJryPerformed()==1){

				if(null == nonDTSJourneyDTO.getNonDTSJrnyCanType()){
					resultMsg="Non DTS Journey Cancellation Ground should not be blank.";
					return resultMsg;
				}
				if(nonDTSJourneyDTO.getNonDTSJrnyCanType()==1){
					if(null == nonDTSJourneyDTO.getNonDTSJrnyCanSanDate()){
						resultMsg="Non DTS Journey Cancellation Sanction No should not be blank.";
						return resultMsg;
					}

					if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSJrnyCanSanNo())){
						resultMsg="Non DTS Journey Cancellation Sanction Date should not be blank.";
						return resultMsg;
					}
				}
		   }
		}



		for(UpdateClaimHotelDtlsBean hotelDetailsDTO:saveTADAClaimDTO.getClaimHotelDtls()){
			
			if(StringValidation.isEmpty(hotelDetailsDTO.getHotelOrMess())){
				resultMsg="Hotel Stay Location should not be blank.";
				return resultMsg;
			}
			if("Hotel".equalsIgnoreCase(hotelDetailsDTO.getHotelOrMess())){
				/*if(saveTADAClaimDTO.getServiceType()!=1 && StringValidation.isEmpty(hotelDetailsDTO.getHotelNACTaken())){
					resultMsg="Hotel NAC Taken should not be blank.";
					return resultMsg;
				}*/
				if(StringValidation.isEmpty(hotelDetailsDTO.getHotelGSTNo())){
					resultMsg="Hotel GST No should not be blank.";
					return resultMsg;
				}
			}else{
				if(StringValidation.isEmpty(hotelDetailsDTO.getMessLevel())){
					resultMsg="Mess Level should not be blank.";
					return resultMsg;
				}
			}
			
			if(StringValidation.isEmpty(hotelDetailsDTO.getHotelName())){
				resultMsg="Hotel/MESS Name should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(hotelDetailsDTO.getHotelCity())){
				resultMsg="Hotel/MESS location should not be blank.";
				return resultMsg;
			}
			if(null == hotelDetailsDTO.getCheckInDate()){
				resultMsg="Check In Date should not be blank.";
				return resultMsg;
			}
			if(null == hotelDetailsDTO.getCheckOutDate()){
				resultMsg="Check Out Date should not be blank.";
				return resultMsg;
			}
			if(null == hotelDetailsDTO.getHotelNoDays()){
				resultMsg="Hotel Number of Days should not be Null.";
				return resultMsg;
			}
			if(null == hotelDetailsDTO.getHotelAmount()){
				resultMsg="Hotel Bill amount should not be null.";
				return resultMsg;
			}
			if(null == hotelDetailsDTO.getHotelGSTAmount()){
				resultMsg="Hotel Number of Days should not be null.";
				return resultMsg;
			}
		}
		
		for(UpdateClaimFoodDtlsBean foodDetailsDTO:saveTADAClaimDTO.getClaimFoodDtls()){
			
			if(null == foodDetailsDTO.getRationCertiIssue()){
				resultMsg="Food  Non-drawl for Ration Certificate should not be blank.";
				return resultMsg;
			}
			if(null == foodDetailsDTO.getFoodNoDay()){
				resultMsg="Food Number of Days should be numeric.";
				return resultMsg;
			}
			if(null == foodDetailsDTO.getFoodAmount()){
				resultMsg="Food Amount should be numeric.";
				return resultMsg;
			}
			
		}
		
		for(UpdateClaimConyncDtlsBean conveyanceDetailsDTO: saveTADAClaimDTO.getClaimConyncDtls()){
			
			if(conveyanceDetailsDTO.getConveyanceDtls()==0){
				
				if(StringValidation.isEmpty(conveyanceDetailsDTO.getTourVehicleNo())){
					resultMsg="Taxi Vehicle No should not be blank.";
					return resultMsg;
				}
				if(null == conveyanceDetailsDTO.getTourDate()){
					resultMsg="Taxi Date of Travel should not be blank.";
					return resultMsg;
				}
				if(null == conveyanceDetailsDTO.getTourDistance()){
					resultMsg="Taxi Distance should not be null.";
					return resultMsg;
				}
				if(StringValidation.isEmpty(conveyanceDetailsDTO.getTourBillNo())){
					resultMsg="Taxi Bill No should not be blank.";
					return resultMsg;
				}
			}else{
				if(null == conveyanceDetailsDTO.getTourNoOfDays()){
					resultMsg="Taxi Number Of Day should not be null.";
					return resultMsg;
				}
			}
			
			
			if(null == conveyanceDetailsDTO.getTourAmount()){
				resultMsg="Taxi bill amount should not be null.";
				return resultMsg;
			}
		}
		return resultMsg;
	}

	public static String validateLTCClaimData(DraftClaimUpdateBean saveTADAClaimDTO) {

		String resultMsg="OK";

		if(StringValidation.isEmpty(saveTADAClaimDTO.getDesignatin())){
			resultMsg="Designation should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getMoveSanctionNo())){
			resultMsg="Move Sanction No should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getMovSanctionDate()){
			resultMsg="Move Sanction Date should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getLeaveFrom()){
			resultMsg="Leave from details should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getLeaveTo()){
			resultMsg="Leave to details should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getJrnyStrtdTime()){
			resultMsg="Date/Time of start should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getHometown())){
			resultMsg="Home Town should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getLeaveStn())){
			resultMsg="Declared Leave Station should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getLtcCalYr())){
			resultMsg="LTC Calendar Year should not be blank.";
			return resultMsg;
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()){
			resultMsg="Advance Drawn from PAO should not be Null.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()>0){
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAODate()){
				resultMsg="Date of Advance Drawn from PAO should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOther()){
			resultMsg="Advance Drawn from Other Sources should not be Null.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOther()>0){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOtherVoucherNo())){
				resultMsg="Voucher No for Advance Drawn from Other Sources should not be blank.";
				return resultMsg;
			}
			
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromOtherDate()){
				resultMsg="Date of Advance Drawn from Other Sources should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()){
			resultMsg="eMRO/MRO/Refund Amount should not be Null.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()>0){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getMroNumber())){
				resultMsg="eMRO/MRO/Refund No should not be blank.";
				return resultMsg;
			}
			
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroDate()){
				resultMsg="eMRO/MRO Submission Date/Refund Date should not be blank.";
				return resultMsg;
			}
		}
		
		String[] certify=saveTADAClaimDTO.getClaimCertify();
		if(null== certify || certify.length==0){
			resultMsg="Please check Certify that.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getServiceType()==1){
			
		}else{
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getCounterPersonalNo())){
				resultMsg="Counter Singing Authority Personal No should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredDate()){
			resultMsg="Date of Claim preferred by the Individual to the Office should not be blank.";
			return resultMsg;
		}
		
		if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			resultMsg="If Date of Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken is not selected";
			return resultMsg;
		}
		if("Yes".equalsIgnoreCase(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionNo())){
				resultMsg="Sanction No of Claim preferred should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionDate()){
				resultMsg="Sanction Date of Claim preferred should not be blank.";
				return resultMsg;
			}
		}
		
		
		for(UpdateClaimJourneyDtlsBean journeyDTO: saveTADAClaimDTO.getClaimJourneyDtls()){
			
			if(null == journeyDTO.getJourneyStartDate()){
				resultMsg="DTS Journey Start Date should not be blank.";
				return resultMsg;
				
			}
			
			if(null == journeyDTO.getJourneyEndDate()){
				resultMsg="DTS Journey End Date should not be blank.";
				return resultMsg;
			}
			
			for(UpdateClaimPassDtlsBean passengerDTO:journeyDTO.getClaimPassDtls()){
				
				if(null == passengerDTO.getJrnyPerfmd()){
					resultMsg="DTS Journey Performed should not be Null.";
					return resultMsg;
				}
				
				if(passengerDTO.getJrnyPerfmd()==1){
					
					if(null == passengerDTO.getCancellationGrnd()){
						resultMsg="DTS Journey Cancellation Ground should not be Null.";
						return resultMsg;
					}
					
					if(passengerDTO.getCancellationGrnd()==1){
						
						if(null == passengerDTO.getCancellationDate()){
							resultMsg="DTS Journey Cancellation Sanction Date should not be blank.";
							return resultMsg;
						}
						
						if(StringValidation.isEmpty(passengerDTO.getCancellationRmrk())){
							resultMsg="DTS Journey Cancellation Sanction No should not be blank.";
							return resultMsg;
						}
			        }
			  }
			}
			
		}
		
		for(UpdateClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO:saveTADAClaimDTO.getClaimNonDtsJrnyDtls()){
			
			if(null == nonDTSJourneyDTO.getJourneyType()){
				resultMsg="Please select Non DTS Journey Type.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSModeOfTravel())){
				resultMsg="Please select Non DTS Mode of Travel.";
				return resultMsg;
			}
//			if("Flight".equalsIgnoreCase(nonDTSJourneyDTO.getNonDTSModeOfTravel())){
//					
//					if(StringValidation.isEmpty(nonDTSJourneyDTO.getAirlineType())){
//						resultMsg="Non DTS Journey Airline type should not be blank.";
//						return resultMsg;
//					}
//					if(!nonDTSJourneyDTO.getAirlineType().equalsIgnoreCase("AI")){
//						
//						if(StringValidation.isEmpty(nonDTSJourneyDTO.getOtherAirlineType())){
//							resultMsg="Non DTS Journey other airline reason should not be blank.";
//							return resultMsg;
//						}
//					
//						if("FAA".equalsIgnoreCase(nonDTSJourneyDTO.getOtherAirlineType())){
//							
//							if(StringValidation.isEmpty(nonDTSJourneyDTO.getAuthorityNo())){
//								resultMsg="Non DTS Journey other airline Financial Advisor Approval Authority No should not be blank.";
//								return resultMsg;
//							}
//							
//							if(StringValidation.isEmpty(nonDTSJourneyDTO.getAuthorityDate())){
//								resultMsg="Non DTS Journey other airline Financial Advisor Approval Authority Date should not be blank.";
//								return resultMsg;
//							}
//						}
//					}
//				}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSFromPlace())){
				resultMsg="Non DTS Departure Place should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSToPlace())){
				resultMsg="Non DTS Arrival Place should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSClassOfTravel())){
				resultMsg="Please select Non DTS Class of Accomodation.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryStartDate()){
				resultMsg="Non DTS Departure Time should not be blank.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryEndDate()){
				resultMsg="Non DTS Arrival Time should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getSector())){
				resultMsg="Non DTS Sector should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSTicketNo())){
				resultMsg="Non DTS Bill/PNR/Ticket No should not be blank.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSDistanceKM()){
				resultMsg="Non DTS Journey Distance by Road in Km should not be Null.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryAmount()){
				resultMsg="Non DTS Journey Booking Amount should not be Null.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryRefundAmount()){
				resultMsg="Non DTS Journey Refund Amount should not be Null.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryClaimedAmount()){
				resultMsg="Non DTS Journey Claimed Amount should not be Null.";
				return resultMsg;
			}
			
			for(UpdateClaimPassDtlsBean passengerDTO:nonDTSJourneyDTO.getClaimPassDtls()){
				
				if(StringValidation.isEmpty(passengerDTO.getTravellerName())){
					resultMsg="Non DTS Traveller Name should not be blank.";
					return resultMsg;
				}
				if(StringValidation.isEmpty(passengerDTO.getRelation())){
					resultMsg="Non DTS Traveller Relation should not be blank.";
					return resultMsg;
				}
				if(null == passengerDTO.getAge()){
					resultMsg="Non DTS Traveller Age should not be blank.";
					return resultMsg;
				}
				if(StringValidation.isEmpty(passengerDTO.getTicketNo())){
					resultMsg="Non DTS Traveller Ticket No should not be blank.";
					return resultMsg;
				}
				
				if(null == passengerDTO.getJrnyPerfmd()){
					resultMsg="Non DTS Traveller Performed should not be Null.";
					return resultMsg;
				}
				
				if(passengerDTO.getJrnyPerfmd()==1){
					
					if(null == passengerDTO.getCancellationGrnd()){
						resultMsg="Non DTS Traveller Journey Cancellation Ground should not be Null.";
						return resultMsg;
					}
					
					if(passengerDTO.getCancellationGrnd()==1){
						
						if(null == passengerDTO.getCancellationDate()){
							resultMsg="Non DTS Traveller Journey Cancellation Sanction Date should not be blank.";
							return resultMsg;
						}
						
						if(StringValidation.isEmpty(passengerDTO.getCancellationRmrk())){
							resultMsg="Non DTS Traveller Journey Cancellation Sanction No should not be blank.";
							return resultMsg;
						}
			        }
			  }
			  
				if(null == passengerDTO.getBookingAmount()){
					resultMsg="Non DTS Traveller Booking amount should not be Null.";
					return resultMsg;
				}
				if(null == passengerDTO.getRefundAmount()){
					resultMsg="Non DTS Traveller Refund amount should not be Null.";
					return resultMsg;
				}
				if(null == passengerDTO.getClaimedAmount()){
					resultMsg="Non DTS Traveller Claimed amount should not be Null.";
					return resultMsg;
				}
			}
		}		
		return resultMsg;
	}

	public static String validatePTClaimData(DraftClaimUpdateBean saveTADAClaimDTO) {

		String resultMsg="OK";

		if(StringValidation.isEmpty(saveTADAClaimDTO.getPayAccntNo())){
			resultMsg="Pay Account No should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getLevelId())){
			resultMsg="Pay Level should not be blank.";
			return resultMsg;
		}
//		if(StringValidation.isEmpty(saveTADAClaimDTO.getPersonalPayLevel())){
//			resultMsg="Pay Level should not be blank.";
//			return resultMsg;
//		}
		if(!StringValidation.IsNumericString(saveTADAClaimDTO.getBasicPay())){
			resultMsg="Basic Pay should be Numeric.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getTrnsfrFrom())){
			resultMsg="Transferred from should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getTrnsfrTO())){
			resultMsg="Transferred To should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getJrnyStrtdFrom())){
			resultMsg="Departure Place should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getJrnyStrtdTime()){
			resultMsg="Departure Date should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getJrnyStrtdTo())){
			resultMsg="Arrival Place should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getJrnyFnshTime()){
			resultMsg="Arrival Date should not be blank.";
			return resultMsg;
		}
		if(StringValidation.isEmpty(saveTADAClaimDTO.getMoveSanctionNo())){
			resultMsg="Move Sanction No should not be blank.";
			return resultMsg;
		}
		if(null == saveTADAClaimDTO.getMovSanctionDate()){
			resultMsg="Move Sanction Date should not be blank.";
			return resultMsg;
		}
		
		if(StringValidation.isEmpty(saveTADAClaimDTO.getMoveIssungAuth())){
			resultMsg="Move Issuing Authority should not be blank.";
			return resultMsg;
		}
		
		for(UpdateClaimPersonalEffectsBean effectsDTO:saveTADAClaimDTO.getClaimPersonalEffectsBean()){
			
			if(effectsDTO.getType()!=1){
				if(null == effectsDTO.getParticularWeight()){
					resultMsg=effectsDTO.getParticularName()+" Weight should not be Null.";
					return resultMsg;
				}
			}
			if(StringValidation.isEmpty(saveTADAClaimDTO.getMoveIssungAuth())){
				resultMsg="Please select "+effectsDTO.getParticularName()+" Mode of Conveyance.";
				return resultMsg;
			}
			if(null == effectsDTO.getParticularDistance()){
				resultMsg=effectsDTO.getParticularName()+" Distance should not be Null.";
				return resultMsg;
			}
			if(null == effectsDTO.getParticularClaimAmt()){
				resultMsg=effectsDTO.getParticularName()+" Claimed Amount should not be Null.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getConvPartOfLugg()){
			resultMsg="Please select Is Conveyance part of Luggage.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getConvPartOfLugg()==1){
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getConveyanceName())){
				resultMsg="Please select Transportation of Conveyance.";
				return resultMsg;
			}
		}
		
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()){
			resultMsg="Advance Drawn from PAO should not be Null.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAO()>0){
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getAdvFromPAODate()){
				resultMsg="Date of Advance Drawn from PAO should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getPaoAdvLugg()){
				resultMsg="Luggage Advance Drawn from PAO should not be Null.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getPaoAdvConvyance()){
				resultMsg="Coveyance Advance Drawn from PAO should not be Null.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getPaoAdvCtg()){
				resultMsg="CTG Advance Drawn from PAO should not be Null.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()){
			resultMsg="eMRO/MRO/Refund Amount should not be Null.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getClaimAdvanceDtls().getMroRefund()>0){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getMroNumber())){
				resultMsg="eMRO/MRO/Refund No should not be blank.";
				return resultMsg;
			}
			
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getMroDate()){
				resultMsg="eMRO/MRO Submission Date/Refund Date should not be blank.";
				return resultMsg;
			}
		}
		
		String[] certify=saveTADAClaimDTO.getClaimCertify();
		if(null== certify || certify.length==0){
			resultMsg="Please check Certify that.";
			return resultMsg;
		}
		
		if(saveTADAClaimDTO.getServiceType()==1){
			
		}else{
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getCounterPersonalNo())){
				resultMsg="Counter Singing Authority Personal No should not be blank.";
				return resultMsg;
			}
		}
		
		if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredDate()){
			resultMsg="Date of Claim preferred by the Individual to the Office should not be blank.";
			return resultMsg;
		}
		
		if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			resultMsg="If Date of Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken is not selected";
			return resultMsg;
		}
		if("Yes".equalsIgnoreCase(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanction())){
			
			if(StringValidation.isEmpty(saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionNo())){
				resultMsg="Sanction No of Claim preferred should not be blank.";
				return resultMsg;
			}
			if(null == saveTADAClaimDTO.getClaimAdvanceDtls().getClaimPreferredSanctionDate()){
				resultMsg="Sanction Date of Claim preferred should not be blank.";
				return resultMsg;
			}
		}
		
		
		for(UpdateClaimJourneyDtlsBean journeyDTO: saveTADAClaimDTO.getClaimJourneyDtls()){
			
			if(null == journeyDTO.getJourneyStartDate()){
				resultMsg="DTS Journey Start Date should not be blank.";
				return resultMsg;
				
			}
			
			if(null == journeyDTO.getJourneyEndDate()){
				resultMsg="DTS Journey End Date should not be blank.";
				return resultMsg;
			}
			
			for(UpdateClaimPassDtlsBean passengerDTO:journeyDTO.getClaimPassDtls()){
				
				if(null == passengerDTO.getJrnyPerfmd()){
					resultMsg="DTS Journey Performed should be Numeric.";
					return resultMsg;
				}
				
				if(passengerDTO.getJrnyPerfmd()==1){
					
					if(null == passengerDTO.getCancellationGrnd()){
						resultMsg="DTS Journey Cancellation Ground should not be Null.";
						return resultMsg;
					}
					
					if(passengerDTO.getCancellationGrnd()==1){
						
						if(null == passengerDTO.getCancellationDate()){
							resultMsg="DTS Journey Cancellation Sanction Date should not be blank.";
							return resultMsg;
						}
						
						if(StringValidation.isEmpty(passengerDTO.getCancellationRmrk())){
							resultMsg="DTS Journey Cancellation Sanction No should not be blank.";
							return resultMsg;
						}
			        }
			  }
			}
		}
		
		for(UpdateClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO:saveTADAClaimDTO.getClaimNonDtsJrnyDtls()){
			
			if(null == nonDTSJourneyDTO.getJourneyType()){
				resultMsg="Please select Non DTS Journey Type.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSModeOfTravel())){
				resultMsg="Please select Non DTS Mode of Travel.";
				return resultMsg;
			}
//			if("Flight".equalsIgnoreCase(nonDTSJourneyDTO.getNonDTSModeOfTravel())){
//					
//					if(StringValidation.isEmpty(nonDTSJourneyDTO.getAirlineType())){
//						resultMsg="Non DTS Journey Airline type should not be blank.";
//						return resultMsg;
//					}
//					if(!nonDTSJourneyDTO.getAirlineType().equalsIgnoreCase("AI")){
//						
//						if(StringValidation.isEmpty(nonDTSJourneyDTO.getOtherAirlineType())){
//							resultMsg="Non DTS Journey other airline reason should not be blank.";
//							return resultMsg;
//						}
//					
//						if("FAA".equalsIgnoreCase(nonDTSJourneyDTO.getOtherAirlineType())){
//							
//							if(StringValidation.isEmpty(nonDTSJourneyDTO.getAuthorityNo())){
//								resultMsg="Non DTS Journey other airline Financial Advisor Approval Authority No should not be blank.";
//								return resultMsg;
//							}
//							
//							if(StringValidation.isEmpty(nonDTSJourneyDTO.getAuthorityDate())){
//								resultMsg="Non DTS Journey other airline Financial Advisor Approval Authority Date should not be blank.";
//								return resultMsg;
//							}
//						}
//					}
//				}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSFromPlace())){
				resultMsg="Non DTS Departure Place should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSToPlace())){
				resultMsg="Non DTS Arrival Place should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSClassOfTravel())){
				resultMsg="Please select Non DTS Class of Accomodation.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryStartDate()){
				resultMsg="Non DTS Departure Time should not be blank.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryEndDate()){
				resultMsg="Non DTS Arrival Time should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getSector())){
				resultMsg="Non DTS Sector should not be blank.";
				return resultMsg;
			}
			if(StringValidation.isEmpty(nonDTSJourneyDTO.getNonDTSTicketNo())){
				resultMsg="Non DTS Bill/PNR/Ticket No should not be blank.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSDistanceKM()){
				resultMsg="Non DTS Journey Distance by Road in Km should be Numeric.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryAmount()){
				resultMsg="Non DTS Journey Booking Amount should be Numeric.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryRefundAmount()){
				resultMsg="Non DTS Journey Refund Amount should be Numeric.";
				return resultMsg;
			}
			if(null == nonDTSJourneyDTO.getNonDTSJryClaimedAmount()){
				resultMsg="Non DTS Journey Claimed Amount should be Numeric.";
				return resultMsg;
			}
			
			for(UpdateClaimPassDtlsBean passengerDTO:nonDTSJourneyDTO.getClaimPassDtls()){
				
				if(StringValidation.isEmpty(passengerDTO.getTravellerName())){
					resultMsg="Non DTS Traveller Name should not be blank.";
					return resultMsg;
				}
				if(StringValidation.isEmpty(passengerDTO.getRelation())){
					resultMsg="Non DTS Traveller Relation should not be blank.";
					return resultMsg;
				}
				if(null == passengerDTO.getAge()){
					resultMsg="Non DTS Traveller Age should not be blank.";
					return resultMsg;
				}
				if(StringValidation.isEmpty(passengerDTO.getTicketNo())){
					resultMsg="Non DTS Traveller Ticket No should not be blank.";
					return resultMsg;
				}
				
				if(null == passengerDTO.getJrnyPerfmd()){
					resultMsg="Non DTS Traveller Performed should not be Null.";
					return resultMsg;
				}
				
				if(passengerDTO.getJrnyPerfmd()==1){
					
					if(null == passengerDTO.getCancellationGrnd()){
						resultMsg="Non DTS Traveller Journey Cancellation Ground should be Numeric.";
						return resultMsg;
					}
					
					if(passengerDTO.getCancellationGrnd()==1){
						
						if(null == passengerDTO.getCancellationDate()){
							resultMsg="Non DTS Traveller Journey Cancellation Sanction Date should not be blank.";
							return resultMsg;
						}
						
						if(StringValidation.isEmpty(passengerDTO.getCancellationRmrk())){
							resultMsg="Non DTS Traveller Journey Cancellation Sanction No should not be blank.";
							return resultMsg;
						}
			        }
			  }
			  
				if(null == passengerDTO.getBookingAmount()){
					resultMsg="Non DTS Traveller Booking amount should not be Null.";
					return resultMsg;
				}
				if(null == passengerDTO.getRefundAmount()){
					resultMsg="Non DTS Traveller Refund amount should not be Null.";
					return resultMsg;
				}
				if(null == passengerDTO.getClaimedAmount()){
					resultMsg="Non DTS Traveller Claimed amount should not be Null.";
					return resultMsg;
				}
				
			}
			
		}		
		return resultMsg;
	}
}
