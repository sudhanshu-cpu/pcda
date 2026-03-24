package com.pcda.util;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcda.common.model.ServerDetails;
import com.pcda.common.model.ServiceDetails;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PcdaConstant {

	private static ServiceDetails baseURL;
	
	private static ServiceDetails getVariable() {
		if(null==baseURL) {
			baseURL=new ServiceDetails();
			String home = System.getenv("JBOSS_HOME");
			 DODLog.info(0, PcdaConstant.class, ":: home::" +  home);
			 try {
				  byte[] jsonData = Files.readAllBytes(Paths.get(home+"/standalone/serviceDetails.json"));
				  DODLog.info(0, PcdaConstant.class, ":: jsonData::" + jsonData);
					ObjectMapper objectMapper = new ObjectMapper();
					ServerDetails emp = objectMapper.readValue(jsonData, ServerDetails.class);
					baseURL.setServiceHost(emp.getServer());
					DODLog.info(0, PcdaConstant.class, ":: ServiceHost::" + baseURL);
			    } catch (Exception e) {
				
				DODLog.printStackTrace(e, PcdaConstant.class, 0);
			} 
		}
		return baseURL;
	}
	

	public static final String MASTER_BASE_URL = getVariable().getServiceHost()+"/masterService/master/v1";
	public static final String OFFICE_BASE_URL =  getVariable().getServiceHost()+"/userService/offices/v1";
	public static final String USER_BASE_URL =  getVariable().getServiceHost()+"/userService/user/v1";
	public static final String TRAVELLER_URL =  getVariable().getServiceHost()+"/userService/traveller/v1";
	public static final String USER_MAP_URL =  getVariable().getServiceHost()+"/userService/userMap/v1";
	public static final String RAIL_BASE_URL =  getVariable().getServiceHost()+"/masterService/rail/v1";
	public static final String AIR_BASE_URL =  getVariable().getServiceHost()+"/masterService/airmaster/v1";
	public static final String TR_RULE_URL =  getVariable().getServiceHost()+"/travelRuleService/travelRule/v1";
	public static final String TRANSFER_SERVICE_URL= getVariable().getServiceHost()+"/transferService/transfer/v1";
	public static final String BUDGET_BASE_URL =  getVariable().getServiceHost()+"/budgetService/budget/v1";
	public static final String CHANGE_PERSONAL_NO_URL = getVariable().getServiceHost()+"/userService/changePersonalNumber/v1"; 
	public static final String UNIT_MOVEMENT_URL = getVariable().getServiceHost() + "/userService/unitmovement/v1";
	public static final String PREVENT_PROCESS_BASE_URL = getVariable().getServiceHost() + "/requestService/preventprocess/v1";
	public static final String VIA_REQUEST_BASE_URL = getVariable().getServiceHost() + "/requestService/request/v1";
	
        public static final String CHANGE_SERVICE_URL = getVariable().getServiceHost() + "/userService/changeTravellerService/v1"; 
        public static final String EDIT_TRAVELLER_BASE_URL = getVariable().getServiceHost() + "/userService/editTraveller/v1";
        public static final String AIR_SERVICE_BASE_URL=  getVariable().getServiceHost()+ "/airService/air/v1/";
	public static final String FINAL_CLAIM_BASE_URL = getVariable().getServiceHost() + "/claimService/claim/v1";
        public static final String RAIL_CANCELLATION_URL =  getVariable().getServiceHost()+"/railService/railCancellation/v1";
        public static final String EXCEPTIONAL_CANCELLATION_URL =  getVariable().getServiceHost()+"/railService/railCancellation/v1"; 
	
        public static final String ADG_REPORTS_BASE_URL =  getVariable().getServiceHost()+"/reportService";
        public static final String AIR_SERVICE_VOUCHER = getVariable().getServiceHost() + "/airService/voucher/v1";
        
        public static final String OFFLINE_CAN_BASE_URL = getVariable().getServiceHost() + "/requestService/offlinecancellation/v1";
        public static final String REQUEST_BASE_URL =  getVariable().getServiceHost()+"/requestService/request/v1";
        public static final String REQUEST_PREVENT_PROCESS_URL =  getVariable().getServiceHost()+"/requestService/preventprocess/v1";
        
        public static final String TRAVEL_ID_BASE_URL =  getVariable().getServiceHost()+"/requestService/travelId/v1";
        public static final String OUTSTANDING_VOUCHER_GENERATION_URL =  getVariable().getServiceHost()+"/airService/voucher/v1"; 
    public static final String VOUCHER_ACKNOWLEDGEMENT_GENERATION_URL =  getVariable().getServiceHost()+"/airService/voucher/v1"; 
public static final String EMAIL_BASE_URL =  getVariable().getServiceHost()+"/airService/booking/v1";
public static final String RAIL_TICKET_BASE_URL =  getVariable().getServiceHost()+"/railService/railTicket/v1";
	public static final String MASTER_MISED_DASHBOARD_URL =  getVariable().getServiceHost()+"/userService/mastermiseddashboard/v1";
	public static final String TRAVELLER_AJAX_URL = getVariable().getServiceHost() + "/userService/traveller/v1";
    public static final String CLAIM_BASE_URL = getVariable().getServiceHost() +"/claimService/claim/v1";
    public static final String AIR_SERVICE_DEMAND = getVariable().getServiceHost() + "/airService/demand/v1";
    public static final String AIR_BOOKING_UPDATION = getVariable().getServiceHost() + "/airService/airBookingUpdation/v1";    
    public static final String RAIL_BOOKING_URL = getVariable().getServiceHost() + "/railBookingService/"; 
    public static final String COMMON_REPORT_URL = getVariable().getServiceHost() + "/reportService/report/v1";
	public static final String REPORT_SERVICE_URL_ADMIN = getVariable().getServiceHost() + "/reportService/adminReport/v1";
    public static final String MASTER_MISSING_DASHBOARD_BASE_URL =  getVariable().getServiceHost()+"/commonService/mastermissingreqdashboard/v1";
     public static final String TDR_BASE_URL= getVariable().getServiceHost()+"/railService/tdr/v1"; 
     public static final String PAO_USER_REPORT_BASE_URL =  getVariable().getServiceHost()+"/commonService/report/v1";  
     public static final String CONTENT_URL = getVariable().getServiceHost() + "/commonService/content/v1";
     public static final String SESSION_TRACKING_URL = getVariable().getServiceHost() + "/commonService/sessionTracking/v1";
	 public static final String AIR_BOOK_SERVICE= getVariable().getServiceHost() + "/airBookingService/air/v1";
	 public static final String RAIL_BOOK_SERVICE= getVariable().getServiceHost() + "/railBookingService/rail/v1";
	 public static final String RAIL_REQ_IRLA_SERVICE= getVariable().getServiceHost() + "/commonService/irla/v1";
	 public static final String AIR_REQ_DEMAND_SERVICE= getVariable().getServiceHost() + "/commonService/airDemandDwn/v1";
	 public static final String COMMON_GREIVANCE_URL= getVariable().getServiceHost() + "/commonService/grievance/v1";
	 
	public static final BigInteger MASTER_BOOKER=BigInteger.valueOf(10004);
	public static final BigInteger COMMANDING_OFFICER=BigInteger.valueOf(10005);
	public static final BigInteger PAO_USER=BigInteger.valueOf(10006);
	public static final BigInteger CDA_USER=BigInteger.valueOf(10007);
	public static final BigInteger CGDA_USER=BigInteger.valueOf(10008);
	public static final BigInteger ADG_MOV_USER=BigInteger.valueOf(10009);
	public static final BigInteger AIR_SERVICE_PROVIDER_USER=BigInteger.valueOf(10010);
	public static final BigInteger SAO_USER=BigInteger.valueOf(10011);

	public static final String CIVILIAN_SEVICE_ID="100001";
	public static final String ARMY_SEVICE_ID="100002";
	public static final String NAVY_SEVICE_ID="100003";
	public static final String AIRFORCE_SEVICE_ID="100004";
	public static final String COAST_GUARD_SEVICE_ID="100015";
	public static final String COAST_GUARD_SEVICE_NAME="COAST GUARD";
	public static final String MNS_SEVICE_ID="100016";
	public static final String DSC_SEVICE_ID="100014";

    public static final Integer  RETIR_AGE_OTHER=58;
	public static final Integer  RETIR_AGE_PBOR=57;

	public static final String FILE_CONSTANT_PATH = "/data/dod/upload/";
	public static final Map<Integer, String> SERVICE_MAP= Map.of(100003,"Navy", 1000019,"NavyCivilian");
	
	

		 
}
