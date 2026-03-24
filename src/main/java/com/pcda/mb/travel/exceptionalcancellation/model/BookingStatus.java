package com.pcda.mb.travel.exceptionalcancellation.model;

public enum BookingStatus {

	 NOT_BOOKED ,                                                                                                 
	 IGNORE_BOOKING  ,                                                                                            
	 BOOKING_REQUESTED , 
	 BOOKED,                                                                                                     
	 REQUESTED_ACCOMODATION_NOT_AVAILABLE  , 
	 PRINTED,                                                                                                   
	 DISPATCHED  ,                                                                                                
	 DELIVERED ,                                                                                                  
	 RE_PRINTED   ,                                                                                               
	 CANCELLATION_PENDING_FOR_CO_APPROVAL  ,
	 CANCELLATION_APPROVED_BY_CO    ,                                                                             
	 CANCELLED_BY_PCDA  ,                                                                                         
	 RESERVATION_FAILED ,                                                                                         
	 NOT_ABLE_TO_DELIVER , 
	 MODIFIED   ,                                                                                                 
	 CANCELLED  ,                                                                                                 
	 HOLD_STATUS  ,                                                                                               
	 FINAL_STATUS  ,                                                                                              
	 CANCEL_STATUS  ,   
	 PARTIAL_BOOKED_STATUS ,                                                                                      
	 FOR_MODIFICATION ,                                                                                           
	 REISSUED  ,                                                                                                  
	 PENDING ,                                                                                                    
	 QUEUED ,                                                                                                     
	 ROLLBACK ,                                                                                                   
	 EXCEPTION ,                                                                                                  
	 BLANK_RESPONSE  , 
	 CANCELLATION_ACCEPTED_BY_SERVICE_PROVIDER ;

}
