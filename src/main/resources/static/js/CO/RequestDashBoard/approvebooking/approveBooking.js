$(document).ready(function(){

   $("#disApproveDiv").hide();

 $(".blockNegative").keydown(function (event) {
            if (event.shiftKey) {
                event.preventDefault();
            }

            if (event.keyCode == 46 || event.keyCode == 8) {
            }
            else {
                if (event.keyCode < 95) {
                    if (event.keyCode < 48 || event.keyCode > 57) {
                        event.preventDefault();
                    }
                }
                else {
                    if (event.keyCode < 96 || event.keyCode > 105) {
                        event.preventDefault();
                    }
                }
            }
        }); 
        
     
     

});



// da aproved radio button
function updateDAAmountValue(requestId,type,travelTypeId){
  
  if(travelTypeId=='100001'){
	  if(type == 0){
	   $("#modCTGAmount_"+requestId).val($("#adv_ctg_amount_"+requestId).val());
	   $("#modLuggageAmount_"+requestId).val($("#adv_LuggTran_amount_"+requestId).val());
	   $("#modVehicleAmount_"+requestId).val($("#adv_VehicleTran_amount_"+requestId).val());
	   $("#modDAAmount_"+requestId).val($("#da_advance_amount_"+requestId).val());
	  }else if(type == 1){
	   $("#modCTGAmount_"+requestId).val(0);
	   $("#modLuggageAmount_"+requestId).val(0);
	   $("#modVehicleAmount_"+requestId).val(0);
	   $("#modDAAmount_"+requestId).val(0);
	  
	  }
  }
  
   if(travelTypeId=='100002'){
  if(type == 0){
   $("#modHotelAmount_"+requestId).val($("#da_hotel_amount_"+requestId).val());
   $("#modConveyanceAmount_"+requestId).val($("#da_conveyance_amount_"+requestId).val());
   $("#modFoodAmount_"+requestId).val($("#da_food_amount_"+requestId).val());
   $("#modDAAmount_"+requestId).val($("#da_advance_amount_"+requestId).val());
  }else if(type == 1){
   $("#modHotelAmount_"+requestId).val(0);
   $("#modConveyanceAmount_"+requestId).val(0);
   $("#modFoodAmount_"+requestId).val(0);
   $("#modDAAmount_"+requestId).val(0);
  
  }
  }
  
}
// total amount logic when change the other amount

 function calDAAdvanceAmt(reqId,travelTypeId){
 
 var totalAdv=0
 
 if(travelTypeId=='100001'){
 
 totalAdv=parseInt($("#modCTGAmount_"+reqId).val())+parseInt($("#modLuggageAmount_"+reqId).val())+parseInt($("#modVehicleAmount_"+reqId).val())
 
 }
 if(travelTypeId=='100002'){
 
  totalAdv=parseInt($("#modHotelAmount_"+reqId).val())+parseInt($("#modConveyanceAmount_"+reqId).val())+parseInt($("#modFoodAmount_"+reqId).val());
 
 }
  $("#modDAAmount_"+reqId).val(totalAdv);
 }
 

// tatkal select radio functionality
function tatkalSelected(chk,requestid)
{
if(chk=='0')
		{
			var tatkalRefDiv=document.getElementById("tatkalRefundDiv"+requestid);
			tatkalRefDiv.style.display='';
			document.getElementById("tatkal").value="";
		var tatkalDisApproDiv=document.getElementById("tatkalDisApproveDiv"+requestid);
		tatkalDisApproDiv.style.display='none';;
		}
	else{
	
		var tatkalRefDiv=document.getElementById("tatkalRefundDiv"+requestid);
		tatkalRefDiv.style.display='none'; 
		document.getElementById("tatkal").value="";
		
		var tatkalDisApproDiv=document.getElementById("tatkalDisApproveDiv"+requestid);
		tatkalDisApproDiv.style.display='';
	}

} 


function ValidateAppBookingForm(requestId,event,chekboxrequestid,isTatkalRequest,travelMode,personalNo,IsDaAdvance,travelType)
{
	
   var modHotelAmount=0;
   var modConveyanceAmount=0;
   var modFoodAmount=0;

   if(travelType=='TD'){
    modHotelAmount=$("#modHotelAmount_"+chekboxrequestid).val();
	if(typeof modHotelAmount === "undefined" || modHotelAmount == null || modHotelAmount == "" || parseFloat(modHotelAmount) <=0 ){
	 modHotelAmount=0;
	}
	
	var daHotelAmount= $("#da_hotel_amount_"+chekboxrequestid).val();
	
	if(IsDaAdvance == 0 && parseFloat(modHotelAmount) >0 && parseFloat(modHotelAmount) > parseFloat(daHotelAmount)){
	  alert("Hotel DA amount must be less than OR equal to requested amount.");
	  return false;
	}
	
	modConveyanceAmount=$("#modConveyanceAmount_"+chekboxrequestid).val();
if(typeof modConveyanceAmount === "undefined" || modConveyanceAmount == null || modConveyanceAmount == "" || parseFloat(modConveyanceAmount) <=0 ){
	 modConveyanceAmount=0;
	}
	
	var daConveyanceAmount= $("#da_conveyance_amount_"+chekboxrequestid).val();
	
if(IsDaAdvance == 0 && parseFloat(modConveyanceAmount) >0 && parseFloat(modConveyanceAmount) > parseFloat(daConveyanceAmount)){
	  alert("Approved Conveyance amount must be less than OR equal to requested amount.");
	  return false;
	}
	
	modFoodAmount=$("#modFoodAmount_"+chekboxrequestid).val();
	if(typeof modFoodAmount === "undefined" || modFoodAmount == null || modFoodAmount == "" || parseFloat(modFoodAmount) <=0 ){
	 modFoodAmount=0;
	}
	
	var daFoodAmount= $("#da_food_amount_"+chekboxrequestid).val();
	
	if(IsDaAdvance == 0 && parseFloat(modFoodAmount) >0 && parseFloat(modFoodAmount) > parseFloat(daFoodAmount)){
	  alert("Approved DA amount must be less than OR equal to requested DA amount.");
	  return false;
	}
	
	}
	
	var modCTGAmount=0;
	var modLuggageAmount=0;
	var modVehicleAmount=0;
	
	if(travelType=='PT'){
	
		 modCTGAmount=$("#modCTGAmount_"+chekboxrequestid).val();
	if(typeof modCTGAmount === "undefined" || modCTGAmount == null || modCTGAmount == "" || parseFloat(modCTGAmount) <=0 ){
		 modCTGAmount=0;
		}
		
		var advCTGAmount= $("#adv_ctg_amount_"+chekboxrequestid).val();
		
		if(IsDaAdvance == 0 && parseFloat( modCTGAmount) >0 && parseFloat( modCTGAmount) > parseFloat(advCTGAmount)){
		  alert("Approved CTG Amount must be less than or equal to requested amount.");
		  return false;
		}
		
		 modLuggageAmount=$("#modLuggageAmount_"+chekboxrequestid).val();
		if(typeof modLuggageAmount === "undefined" || modLuggageAmount == null || modLuggageAmount == "" || parseFloat(modLuggageAmount) <=0 ){
		 modLuggageAmount=0;
		}
		
		var advLuggTranAmount= $("#adv_LuggTran_amount_"+chekboxrequestid).val();
		
	if(IsDaAdvance == 0 && parseFloat(modLuggageAmount) >0 && parseFloat(modLuggageAmount) > parseFloat(advLuggTranAmount)){
		  alert("Approved Amount For Luggage Transport must be less than or equal to requested amount.");
		  return false;
		}
		
		var modVehicleAmount=$("#modVehicleAmount_"+chekboxrequestid).val();
		if(typeof modVehicleAmount === "undefined" || modVehicleAmount == null || modVehicleAmount == "" || parseFloat(modVehicleAmount) <=0 ){
		 modVehicleAmount=0;
		}
		
		var advVehicleAmount= $("#adv_VehicleTran_amount_"+chekboxrequestid).val();
		
		if(IsDaAdvance == 0 && parseFloat(modVehicleAmount) >0 && parseFloat(modVehicleAmount) > parseFloat(advVehicleAmount)){
		  alert("Approved Amount For Vehicle Transport must be less than or equal to requested amount.");
		  return false;
		}
		
		
	}
	
    var modDAAmount=$("#modDAAmount_"+chekboxrequestid).val();
	if(typeof modDAAmount === "undefined" || modDAAmount == null || modDAAmount == "" || parseFloat(modDAAmount) <=0 ){
	 modDAAmount=0;
	}else{
	 modDAAmount=$("#modDAAmount_"+chekboxrequestid).val();
	}
	
	var daAdvanceAmount= $("#da_advance_amount_"+chekboxrequestid).val();
	
	if(IsDaAdvance == 0 && parseFloat(modDAAmount) >0 && parseFloat(modDAAmount) > parseFloat(daAdvanceAmount)){
	  alert("Approved DA amount must be less than OR equal to requested DA amount.");
	  return false;
	}
	  
	var isTatkalVal=''; 
	var daAdvanceAvailed = $("#requestPool").find('input[name=daAdvanceAvailed_'+chekboxrequestid+']:checked').val();
	

	if(IsDaAdvance == 0 && daAdvanceAvailed == 1)
	{
		var confirmMsg = confirm("Are you sure you do not want to approve DA Advance?");
		if (confirmMsg == false)
		 {
	        return false;
	     }
	
	}

	var daAdvanceCheck=0;
	
	if(daAdvanceAvailed == 0){
	  daAdvanceCheck=1;
	   
	}
	

	if(isTatkalRequest=='Yes')
	{
			
	var isTatkalTktChoice = document.getElementsByName(chekboxrequestid);
	var isTatkalTktChoiceValue="";
	
	for(var i = 0; i < isTatkalTktChoice.length; i++){
	    if(isTatkalTktChoice[i].checked){
	        isTatkalTktChoiceValue = isTatkalTktChoice[i].value;
	        break;
	    }
	}
	
	 
	
	 
	if (isTatkalTktChoiceValue=="")
	 {
	  	alert("Please Approve or Disapprove Tatkal");
        return false;
		}
			
		}	

	var bookingType=$("#booking_type_"+chekboxrequestid).val();
	
	if(travelMode=="AIR" && bookingType=="International")
	{
	   var confirmMsge = confirm("This is an international travel request. Please verify passport details before approving the request. For passport details, you can click on the Request Id. If Passport details are verified and found correct, please click on OK else click Cancel.");
		if (confirmMsge == false)
		 {
	        return false;
	     }
	
	}

	return true;

}

function submitRequestForm(requestId,event,chekboxrequestid,isTatkalRequest,travelMode,personalNo,IsDaAdvance,travelType){
	
	var result = ValidateAppBookingForm(requestId,event,chekboxrequestid,isTatkalRequest,travelMode,personalNo,IsDaAdvance,travelType);

	if(result){
		if(event=='approve'){
	
//	var checkPAOStatus=$("#checkPAOStatus_"+chekboxrequestid).val();
//	var railPaoId;
//	var airPaoId;
//	if(checkPAOStatus=='Yes')
//	{
//		if($("#railPaoId_"+chekboxrequestid)!=null){
//			railPaoId=$("#railPaoId_"+chekboxrequestid).val();
//			}else{
//				railPaoId="";
//			}
//		if($("#airPaoId_"+chekboxrequestid)!=null){	
//			airPaoId=$("#airPaoId_"+chekboxrequestid).val();
//			}else{
//				airPaoId="";
//			}
//	}else{
//		railPaoId="";
//		airPaoId="";
//	}
//	$("#railPao").val(railPaoId);
//	$("#airPao").val(airPaoId);
//	
	if(travelMode=='RAIL'){
	if(isTatkalRequest=='Yes'){
		
		if($("#tatkalYes_"+chekboxrequestid).is(":checked")){
			
			$("#isTatkal").val("YES");
		}
		if($("#tatkalNo_"+chekboxrequestid).is(":checked")){
			
			$("#isTatkal").val("NO");
			
		}
	}else{
		$("#isTatkal").val("NO");
	}
$("#isdaAdvanceAvail").val($("#railDaAdvanceAvail_"+chekboxrequestid).val());

	}else{
		$("#isdaAdvanceAvail").val($("#airAdvanceAvail_"+chekboxrequestid).val());
		$("#isTatkal").val("NO");
		
	}
	
	
	if(travelType=='TD'){
	$("#modifiedHotelAmount").val(parseFloat($("#modHotelAmount_"+chekboxrequestid).val()));
	$("#modifiedConveyanceAmount").val(parseFloat($("#modConveyanceAmount_"+chekboxrequestid).val()));
	$("#modifiedFoodAmount").val(parseFloat($("#modFoodAmount_"+chekboxrequestid).val()));
	$("#modifiedCTGAmount").val(parseFloat(0.0));
	$("#modifiedLuggAmount").val(parseFloat(0.0));
	$("#modifiedVehicleAmount").val(parseFloat(0.0));
	}
	
	if(travelType=='PT'){
	$("#modifiedHotelAmount").val(parseFloat(0.0));
	$("#modifiedConveyanceAmount").val(parseFloat(0.0));
	$("#modifiedFoodAmount").val(parseFloat(0.0));
	$("#modifiedCTGAmount").val(parseFloat($("#modCTGAmount_"+chekboxrequestid).val()));
	$("#modifiedLuggAmount").val(parseFloat($("#modLuggageAmount_"+chekboxrequestid).val()));
	$("#modifiedVehicleAmount").val(parseFloat($("#modVehicleAmount_"+chekboxrequestid).val()));
	}
	
	if(travelType!='TD'&& travelType!='PT'){
	$("#modifiedHotelAmount").val(parseFloat(0.0));
	$("#modifiedConveyanceAmount").val(parseFloat(0.0));
	$("#modifiedFoodAmount").val(parseFloat(0.0));
	$("#modifiedCTGAmount").val(parseFloat(0.0));
	$("#modifiedLuggAmount").val(parseFloat(0.0));
	$("#modifiedVehicleAmount").val(parseFloat(0.0));
	}
	
	$("#event").val("approve");
	$("#personalNo").val(personalNo);
	$("#postReqId").val(chekboxrequestid);
	$("#travelMode").val(travelMode);
	$("#checkPAOStatus").val($("#checkPAOStatus_"+chekboxrequestid).val());
	$("#modifiedDAAmount").val(parseFloat($("#modDAAmount_"+chekboxrequestid).val()));
	$("#requestPool").submit();
		}
		if(event=="disapprove")
	    {
		$("#disApprovedRequest").val(chekboxrequestid);
		$("#travelModeStr").val(travelMode);
		$("#requestPoolDiv").hide();
		$("#requestDataDiv").hide();
		$("#disApproveDiv").show();
		 
	   }
	}
	
}




function submitDisapprove(event)
{

	var disapproveDetail=$("#disaproveReason").val();
	
	if(disapproveDetail==' ' || disapproveDetail=='')
	{
		alert("Please Enter Reason for Disapproval of Request");
		$("#disaproveReason").focus();
		event.preventDefault();
	}
    $("#postReqId").val($("#disApprovedRequest").val());
	$("#travelMode").val($("#travelModeStr").val());
	$("#modifiedHotelAmount").val(parseFloat(0.0));
	$("#modifiedConveyanceAmount").val(parseFloat(0.0));
	$("#modifiedFoodAmount").val(parseFloat(0.0));
	$("#modifiedCTGAmount").val(parseFloat(0.0));
	$("#modifiedLuggAmount").val(parseFloat(0.0));
	$("#modifiedVehicleAmount").val(parseFloat(0.0));
	$("#modifiedDAAmount").val(parseFloat(0.0));
	$("#event").val("disapprove");
	$("#requestPool").submit();
}



function tatkalSelected(chk,requestid)
{
if(chk=='0')
		{
			$("#tatkalRefundDiv"+requestid).show();
		  $("#tatkalDisApproveDiv"+requestid).hide();
			
		}
	else{
	
				$("#tatkalRefundDiv"+requestid).hide();
			$("#tatkalDisApproveDiv"+requestid).show();
		
	}

}  




// for personal no pop-up
 function viewUserReports(personalNo)
 {

 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
  
    ajaxUserFace = new LightFace.Request(
    {
 				width: 850,
				height: 400,
				url:  $("#context_path").val()+"co/getAppPersonalNoView",
		
				buttons: [
					{ title: 'Close', event: function() { this.close(); } }
				],
				request: { 
					data: { 
						personalNo: personalNo
					},
					method: 'post'
				},
				title: 'User View'
	});
   	ajaxUserFace.open();
  
 }

 
// for rail request-id pop-up
function viewRequestReports(requestId)
 {
    if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
    
    ajaxRequestFace = new LightFace.Request(
    {
		width: 650,
		height: 400,
		url:  $("#context_path").val()+"co/getAppRailBookReqIdView",
		
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		request: { 
			data: { 
				requestViewId: requestId,
				
			},
			method: 'post'
		},
		title: 'Request View'
	});
    ajaxRequestFace.open();
 }
 
var lightFaceReportObject = null;

// for air request-id pop-up
function viewAirRequestReport(requestId)
{

 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
   	ajaxRequestFace = new LightFace.Request
   	({
   		width: 950,
		height: 400,
		url:  $("#context_path").val()+"co/getAppAirBookReqIdView",
		
		buttons: [{ title: 'Close', event: function() { this.close(); } }],
		request: { 
					data: { 
							requestViewId: requestId,
							
						  },
					method: 'post'
				  },
		title: 'Request View'
	});
  lightFaceReportObject = ajaxRequestFace;
  ajaxRequestFace.open();
} 
 
 // pop-up for DA-Advance
 function viewDAAdvance(requestId)
 {
   
    if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
 	ajaxUserFace = new LightFace.Request({
 	 				width: 500,
					height: 250,
 	
					url:  $("#context_path").val()+"co/getAppDADetails",
		         
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							daRequestId: requestId,
							
						},
						method: 'post'
					},
					title: 'Advance Details'
				});
   ajaxUserFace.open();
   
 }
 

 
