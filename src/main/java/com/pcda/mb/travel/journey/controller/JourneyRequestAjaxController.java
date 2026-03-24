package com.pcda.mb.travel.journey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.journey.model.PtStationValidationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.CitySearchModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.mb.adduser.changeservice.controller.ChangeServiceController;
import com.pcda.mb.travel.journey.model.ClusterStationSearchResponseBean;
import com.pcda.mb.travel.journey.model.CompositeTransferData;
import com.pcda.mb.travel.journey.model.DAAdvanceAmountData;
import com.pcda.mb.travel.journey.model.MaxDAAdvanceModel;
import com.pcda.mb.travel.journey.model.PartyVisitorModel;
import com.pcda.mb.travel.journey.model.TRRulesData;
import com.pcda.mb.travel.journey.model.TaggedRequestModel;
import com.pcda.mb.travel.journey.model.TrainSearchResponseBean;
import com.pcda.mb.travel.journey.model.TravelIdDetails;
import com.pcda.mb.travel.journey.model.TravelIdsData;
import com.pcda.mb.travel.journey.model.TravellerDetails;
import com.pcda.mb.travel.journey.model.TravellerInfoModel;
import com.pcda.mb.travel.journey.model.VisibleYearModel;
import com.pcda.mb.travel.journey.service.AirListService;
import com.pcda.mb.travel.journey.service.DAAdvanceService;
import com.pcda.mb.travel.journey.service.JourneyRequestAjaxService;
import com.pcda.mb.travel.journey.service.TaggedRequestService;
import com.pcda.mb.travel.journey.service.TrainListService;
import com.pcda.mb.travel.journey.service.TravelInfoService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class JourneyRequestAjaxController {

	@Autowired
	private JourneyRequestAjaxService requestAjaxService;
	
	@Autowired
	private TravelInfoService travelInfoService;
	
	@Autowired
	private TrainListService trainListService;
	
	@Autowired
	private DAAdvanceService advanceService;
	
	@Autowired
	private TaggedRequestService taggedRequestService;
	
	@Autowired
	private AirListService  airListService;
	
	private final String path = "MB/TravelRequest/journeyRequest/";
	
	@ResponseBody
	@PostMapping("/getTravellerDetails")
	public TravellerDetails getTravellerDetails(HttpServletRequest request) {
		
		
		TravellerDetails travelObj =null;
		try {
		travelObj=	requestAjaxService.getTravellerDetails(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		return travelObj;
	}
	
	@ResponseBody
	@GetMapping("/getTravelGroup")
	public Map<Integer, String> getTravelGroup(HttpServletRequest request) {
		
		
		Map<Integer, String> map =null;
		try {
			map=	requestAjaxService.getTravelGroup(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return map;
	}
	
	@ResponseBody
	@GetMapping("/getAllTRforTravelType")
	public List<TRRulesData> getAllTRforTravelType(HttpServletRequest request) {
		
		
		List<TRRulesData> ruleDataList =null;
		try {
			ruleDataList=	requestAjaxService.getAllTRforTravelType(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return ruleDataList;
	}
	
	@ResponseBody
	@GetMapping("/getAllTRforTravelGroup")
	public List<TRRulesData> getAllTRforTravelGroup(HttpServletRequest request) {
		
		
		List<TRRulesData> ruleDataList =null;
		try {
			ruleDataList=	requestAjaxService.getAllTRforTravelGroup(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return ruleDataList;
	}
	
	@ResponseBody
	@GetMapping("/getTravelIDs")
	public List<TravelIdsData> getTravelIDs(HttpServletRequest request) {
		
		
		List<TravelIdsData> idDataList =null;
		try {
			idDataList=	requestAjaxService.getTravelIDs(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return idDataList;
	}
	
	@ResponseBody
	@GetMapping("/getTravelRequestID")
	public TravelIdDetails getTravelRequestID(HttpServletRequest request) {
		
		
		TravelIdDetails idDetails =null;
		try {
			idDetails=	requestAjaxService.getTravelRequestID(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return idDetails;
	}
	
	
	@PostMapping("/getTravelRuleDetails")
	public String getTravelRuleDetails(Model model, HttpServletRequest request) {
		model.addAttribute("ruleDtls", requestAjaxService.getTravelRuleDetails(request));
		
		return path + "trRuleDetails";
	}
	
	@ResponseBody
	@GetMapping("/fillTravelInfo")
	public TravellerInfoModel fillTravelInfo(HttpServletRequest request) {
		
		
		TravellerInfoModel info =null;
		try {
			info=	travelInfoService.getTravelInfo(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return info;
	}
	
	@GetMapping("/journyDtlsAjax")
	public String journyDtlsAjax(Model model, HttpServletRequest request) {
		
		model.addAttribute("jrnyType", Optional.ofNullable(request.getParameter("jrnyType")).orElse("")); 
		model.addAttribute("reqType", Optional.ofNullable(request.getParameter("reqType")).orElse("")); 
		model.addAttribute("serviceType", Optional.ofNullable(request.getParameter("serviceType")).orElse("")); 
		
		return path + "journeyDtls";
	}
	
	@ResponseBody
	@GetMapping("/getPartyVisitor")
	public PartyVisitorModel getPartyVisitor(HttpServletRequest request) {
		
		
		PartyVisitorModel info =null;
		try {
			info=	requestAjaxService.getPartyVisitor(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return info;
	}
	
	@ResponseBody
	@GetMapping("/getTrainListDetails")
	public TrainSearchResponseBean getTrainListDetails(HttpServletRequest request) {
		
		
		TrainSearchResponseBean info =null;
		try {
			info=	trainListService.getTrainListDetails(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return info;
	}
	
	@ResponseBody
	@GetMapping("/getStationList")
	public List<String> getStationList(HttpServletRequest request) {
		
		
		List<String> strList =null;
		try {
			strList=	trainListService.getStationList(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return strList;
	}

	@PostMapping("/irSrchRsltPage")
	public String irSrchRsltPage(Model model, HttpServletRequest request) {
		
		model.addAttribute("responseBean", trainListService.searchTrainList(request));
		return path + "railSearchRslt";
	}
	
	@PostMapping("/daAdvDetails")
	public String getDAAdvanceDetails(Model model, HttpServletRequest request) {
		
		model.addAttribute("daData", advanceService.getDAAdvanceDetails(request)); 
		
		return path + "daAdvDetails";
	}
	
	@ResponseBody
	@GetMapping("/getTaggedReqAdvanceCount")
	public boolean getTaggedReqAdvanceCount(Model model, HttpServletRequest request) {
		
		
		
		Boolean flag =null;
		try {
			flag=	advanceService.getTaggedReqAdvanceCount(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return flag;
	}
	
	
	@ResponseBody
	@GetMapping("/calMaxDAAmount")
	public MaxDAAdvanceModel calMaxDAAmount(Model model, HttpServletRequest request) {
		
		
		MaxDAAdvanceModel advncModel =null;
		try {
			advncModel=	advanceService.calMaxDAAmount(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return advncModel;
	}
	
	@ResponseBody
	@GetMapping("/getTaggedRequests")
	public TaggedRequestModel getTaggedRequests(Model model, HttpServletRequest request) {
		
		
		TaggedRequestModel tagModel =null;
		try {
			tagModel=	 taggedRequestService.getTaggedRequests(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return tagModel;
	}
	
	@ResponseBody
	@GetMapping("/getVisibleRequestYear")
	public VisibleYearModel getVisibleRequestYear(Model model, HttpServletRequest request) {
		
		
		
		VisibleYearModel visibleModel =null;
		try {
			visibleModel=	 requestAjaxService.getVisibleRequestYear(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return visibleModel;
	}
	
	@PostMapping("/airTaggedRequestView")
	public String airTaggedRequestView(Model model, HttpServletRequest request) {
		
		model.addAttribute("requestData", taggedRequestService.getAirTaggedRequestDetails(request)); 
		
		return path + "airTaggedRequestView";
	}
	
	@PostMapping("/railTaggedRequestView")
	public String railTaggedRequestView(Model model, HttpServletRequest request) {
		
		model.addAttribute("requestData", taggedRequestService.getRailTaggedRequestDetails(request)); 
		
		return path + "railTaggedRequestView";
	}
	
	@ResponseBody
	@GetMapping("/clusterStationSearch")
	public ClusterStationSearchResponseBean clusterStationSearch(HttpServletRequest request) {
		
		
		ClusterStationSearchResponseBean clusterBean =null;
		try {
			clusterBean=	 trainListService.clusterStationSearch(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		return clusterBean;
	}
	
	@ResponseBody
	@GetMapping("/getAirportList")
	public List<String> getAirportList(HttpServletRequest request) {
		
		
		List<String> strList =null;
		try {
			strList=airListService.getAirportList(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		return strList;
	}
	
	@ResponseBody
	@GetMapping("/getCityList")
	public List<CitySearchModel> getCityList(HttpServletRequest request) {
		
		
		List<CitySearchModel> cityList =null;
		try {
			cityList=requestAjaxService.getCityList(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return cityList;
	}
	
	@GetMapping("/airSrchRsltAjax")
	public String getAirSearchResult(Model model, HttpServletRequest request) {
		model.addAttribute("airBookParent", airListService.getAirSearchResult(request)); 
		
		return path + "airSearchResultView";
	}
	
	@ResponseBody
	@GetMapping("/getMappedAirportList")
	public List<String> getMappedAirportList(HttpServletRequest request) {
		
		
		List<String> strList =null;
		try {
			strList=airListService.getMappedAirportList(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		
		return strList;
	}
	
	@ResponseBody
	@GetMapping("/getMappedStationList")
	public List<String> getMappedStationList(HttpServletRequest request) {
		
		
		List<String> strList =null;
		try {
			strList=trainListService.getMappedStationList(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return strList;
	}
	
	@ResponseBody
	@GetMapping("/getTravelerJournayDate")
	public String getTravelerJournayDate(HttpServletRequest request) {
		
		
		
		String str =null;
		try {
			str=requestAjaxService.getTravelerJournayDate(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return str;
	}
	
	@ResponseBody
	@GetMapping("/compositeTransferAmount")
	public CompositeTransferData compositeTransferAmount(HttpServletRequest request) {
		
		
		CompositeTransferData dataModel =null;
		try {
			dataModel=advanceService.getCompositeTransferAmount(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return dataModel;
	}
	
	@ResponseBody
	@GetMapping("/calculateDAAmount")
	public DAAdvanceAmountData calculateDAAmount(HttpServletRequest request) {
		
		
		DAAdvanceAmountData dataModel =null;
		try {
			dataModel=advanceService.calculateDAAmount(request).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		return dataModel;
	}
	
	// Ajax Call For ATT Fair Rule 
	@PostMapping("/jrnyATTFareRule")
	@ResponseBody
	public String getATTFairRule(@RequestParam String flightKey){
		
		String str =null;
		try {
			str=requestAjaxService.getATTFareRule(flightKey).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
	return str;
	}

	// AJAX Call For BL Fair Rule
	@PostMapping("/jrnyBLFareRule")
	@ResponseBody
	public String getBLFairRule(@RequestParam String flightKey , @RequestParam String domInt  ) {

		
		String str =null;
		try {
			str=requestAjaxService.getBLFareRule(flightKey, domInt).get();
		}catch (InterruptedException ie) {
		    DODLog.printStackTrace(ie, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		    Thread.currentThread().interrupt();
		} catch (Exception ee) {
		    DODLog.printStackTrace(ee, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		
		
		return str;
	}


	/* AJAX FOR GETTING PAO  */
	@PostMapping("/getPayAccountOfc")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPayAccountOffice(@RequestParam String serviceId, @RequestParam String categoryId, HttpServletRequest request) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,
				"SERVICE ID AND CATEGORY ID FOR PAO BASED ON SERVICE AND CATEGORY :: " + serviceId+"," + categoryId);
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		return ResponseEntity.ok(taggedRequestService.getPayAccountOffice(serviceId, categoryId,loginUser));
	}

	/* AJAX FOR VALIDATING SETTING NEW FIELDS ON PT TRAVEL  */
	@PostMapping("/validateVisitorNewFields")
	public ResponseEntity<String> validatingNewFildsOnPTTravel(PtStationValidationModel ptStationValidationModel) {
		
		String message = null;
        try {
            message = travelInfoService.validateVisitorNewStnsFields(ptStationValidationModel);
        } catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
        }
        return ResponseEntity.ok(message);
	}

	/* AJAX FOR THE SEARCHING THE VIA-STATION ROUTE AVAILABILITY */
	@GetMapping("/getAvailableViaRouteStations")
	public ResponseEntity<List<String>> getViaRouteStations(@RequestParam String fromStation, @RequestParam String toStation) {
		
		List<String> viaRouteStations = new ArrayList<>();
		try {
			viaRouteStations = requestAjaxService.getViaRouteStationsAvailablity(fromStation, toStation);
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxController.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return ResponseEntity.ok(viaRouteStations);
	}
	
	/* AJAX FOR THE GETTING THE SINGLE TRAIN ROUTE FOR BREAK JOURNEY */
	@PostMapping("/getSingleTrainRouteBreakJrny")
	@ResponseBody
	public Map<String, String> getTrainAllRouteBreakJrny(@RequestParam String trainNumber,
			@RequestParam String fromStationCode, @RequestParam String toStationCode, @RequestParam String journeyDate) {
		return requestAjaxService.getSingleTrainRoute(trainNumber, fromStationCode, toStationCode, journeyDate);

	}
	
	/* AJAX FOR THE GETTING THE SEARCH RSLT PAGE FOR BREAK JOURNEY */
	@PostMapping("/irSrchRsltPageForBreakJrny")
	public String irSrchRsltPageForBreakJrny(Model model, HttpServletRequest request ) {
		model.addAttribute("responseBean", trainListService.searchTrainList(request));
	
			return path + "breakJourneyRailSearchRslt";
		
	}
	/* AJAX FOR THE GETTING THE SEARCH RSLT PAGE FOR RETURN BREAK JOURNEY */
	@PostMapping("/irSrchRsltPageForReturnBreakJrny")
	public String irSrchRsltPageForReturnBreakJrny(Model model, HttpServletRequest request ) {
		model.addAttribute("responseBean", trainListService.searchTrainList(request));
	
			return path + "returnBreakJourneyRailSearchRslt";
		
	}


}
