//airTicketCanRequest.js
// javascript file used to create air ticket cancellation request from master booker and approval work from co



function showAirTicketDetails(bookingTxnId,bookingId,isExpCan)
{
	$("#bookingId").val(bookingId);
	$("#isExpCan").val(isExpCan);
	$("#bookingTxnId").val(bookingTxnId);
	//alert($("#airTicketCanDetail").attr('action'));
	var actionVal=$("#airTicketCanDetail").attr('action');
	actionVal=actionVal.replace('/page', "");
	$("#airTicketCanDetail").attr('action',actionVal);
	$("#airTicketCanDetail").submit();
}


function proceedToCancel(actionType)
{
 
	
	var isOnwardProceed=false;  
	var isRoundTrip=$("#isRoundTrip").val();
 	var onwardCheckList=document.getElementsByName("onwardCheckList");
 	var onwardPassType=document.getElementsByName("onwardPassType");
 	var returnPassType=document.getElementsByName("returnPassType");
 	var returnCheckList=document.getElementsByName("returnCheck");

	var check=true;
	
	for(var i=0;i<onwardCheckList.length;i++)
	{
		var chekBoxObj=onwardCheckList[i];
		if(chekBoxObj.checked){
			isOnwardProceed=true;
		}
	}

	var isReturnProceed=false;  
	
 	if(isRoundTrip=='YES')
 	{
	 	var returnCheckList=document.getElementsByName("returnCheck");	
	 	
	 	for(var j=0;j<returnCheckList.length;j++)
		{
			var chekBoxObj=returnCheckList[j];
			if(chekBoxObj.checked){
				isReturnProceed=true;
			}
		}
 	}
 	
 	//alert("isOnwardProceed-"+isOnwardProceed+"|isReturnProceed-"+isReturnProceed);
 
 
    if(!isOnwardProceed && !isReturnProceed)
    {
    	alert("Please select passenger for cancellation");
    	check=false;
    	return false;
    }

    
    
    if(isRoundTrip=='YES')
 	{
 		 var returnIsOfficial=document.getElementsByName("returnIsOfficial");
    
 		var passengerArrayOnward=new Array();
 		var isChildOrInfantPresentOnward=false;
 		var onlyChildOrInfantOnward=false;
 		
 		var passengerArrayReturn=new Array();
 		var isChildOrInfantPresentReturn=false;
 		var onlyChildOrInfantReturn=false;
 		
	 	for(var i=0;i<onwardPassType.length;i++)
			{
				var chekBoxObj=onwardPassType[i];					
				var passType=chekBoxObj.value;				
					
					if(passType=="Child" || passType=="Infant"){					
					isChildOrInfantPresentOnward=true;
					break;										
					}
			}
			
			if(isChildOrInfantPresentOnward)
			{
				for(var i=0;i<onwardCheckList.length;i++)
					{
						var chekBoxObj=onwardCheckList[i];
						var passengerType=onwardPassType[i].value;
							
							if(!chekBoxObj.checked){	
								passengerArrayOnward.push(passengerType);						
							}
					}
			}	
			
			for(var x=0;x<passengerArrayOnward.length;x++)
				  {				   		
					 if(passengerArrayOnward[x]!="Adult")
					  {
					  	onlyChildOrInfantOnward=true;
 	}else
 	{
					  	onlyChildOrInfantOnward=false;
					  	break;
					  }				  
				  }
				  if(onlyChildOrInfantOnward)
				  {
					  alert("Can not cancel all Adults in Onward Journey, Atleast one Adult should travel along with Child or Infant");
					  check=false;
	 				  return false;
				  }
				  
				  //Onward Journey validation ends
				  
				  for(var i=0;i<returnPassType.length;i++)
			{
				var chekBoxObj=returnPassType[i];					
				var passType=chekBoxObj.value;
					
					if(passType=="Child" || passType=="Infant"){					
					isChildOrInfantPresentReturn=true;
					break;										
					}
			}
			
			if(isChildOrInfantPresentReturn)
			{
				for(var i=0;i<returnCheckList.length;i++)
					{
						var chekBoxObj=returnCheckList[i];													
						var passengerType=returnPassType[i].value;
							
							if(!chekBoxObj.checked){					
																
								passengerArrayReturn.push(passengerType);						
							}
					}
			}	
			
			for(var x=0;x<passengerArrayReturn.length;x++)
				  {				   		
					 if(passengerArrayReturn[x]!="Adult")
					  {
					  	onlyChildOrInfantReturn=true;
					  }else
					  {
					  	onlyChildOrInfantReturn=false;
					  	break;
					  }				  
				  }
				  if(onlyChildOrInfantReturn)
				  {
					  alert("Can not cancel all Adults in Return Journey, Atleast one Adult should travel along with Child or Infant");
					  check=false;
	 				  return false;
				  }
				  //Return Journey Validation Ends
				  
				  
    
 	}else
 	{
 		var passengerArrayOnward=new Array();
 		var isChildOrInfantPresentOnward=false;
 		var onlyChildOrInfantOnward=false;
 		
	 	for(var i=0;i<onwardPassType.length;i++)
			{
				var chekBoxObj=onwardPassType[i];					
				var passType=chekBoxObj.value;				
					if(passType=="Child" || passType=="Infant"){					
					isChildOrInfantPresentOnward=true;
					break;										
					}
			}
			
			if(isChildOrInfantPresentOnward)
			{
				for(var i=0;i<onwardCheckList.length;i++)
					{
						var chekBoxObj=onwardCheckList[i];
						var passengerType=onwardPassType[i].value;
							if(!chekBoxObj.checked){	
								passengerArrayOnward.push(passengerType);						
							}
					}
			}	
			
			for(var x=0;x<passengerArrayOnward.length;x++)
				  {				   		
					 if(passengerArrayOnward[x]!="Adult")
					  {
					  	onlyChildOrInfantOnward=true;
					  }else
					  {					  
					  	onlyChildOrInfantOnward=false;
					  	break;
					  }				  
				  }
				  if(onlyChildOrInfantOnward)
				  {
					  alert("Can not cancel all Adults in Onward Journey, Atleast one Adult should travel along with Child or Infant");
					  check=false;
	 				  return false;
				  }
 		/*
 		for(var i=0;i<onwardCheckList.length;i++)
		{
			var chekBoxObj=onwardCheckList[i];
		
			var onwardIsOfficial=document.getElementsByName("onwardIsOfficial"+chekBoxObj.value);
   			
			if(chekBoxObj.checked)
			{
				var onwardIsOfficialObj=onwardIsOfficial[i];
				var onwardRadioValue="";
				alert("onwardIsOfficialObj.length-"+onwardIsOfficialObj.length);
				if(onwardIsOfficialObj.length>1)
    			{
    				if(onwardIsOfficialObj[0].checked==true)
		    			onwardRadioValue=onwardIsOfficialObj[0].value;
		    		if(onwardIsOfficialObj[1].checked==true)
		    		 	onwardRadioValue=onwardIsOfficialObj[1].value;
    			}
    			alert("onwardRadioValue-"+onwardRadioValue);
    			if(onwardRadioValue==''){
    				alert("Please Select Ground.");
    			    check=false;
    				return flase;
    			}
			}
		}
		*/
 	} 		

	var cancelReasonId=document.getElementById("cancelReason");
 	if(cancelReasonId.value == ' '){
 		alert("Please specify the reason");
 		cancelReasonId.focus();
 		check=false;
 		return false;
 	}
 	
 	$("#actionType").val(actionType);
 	//alert("|actionType="+actionType+"|check-"+check);
 	if(check){

		var actionVal=$("#saveAirTicketCans").attr('action');
		actionVal=actionVal.replace('/page', "");
		$("#saveAirTicketCans").attr('action',actionVal);

		//alert("airTicketDetail ======== "+$("#airTicketDetail").attr('action'));

		$("#saveAirTicketCans").submit();	
 	}
 	
 	
 	return true;
 
 }
 //Ajay Khurmi 2017-05-26 start..
$(function(){
 
 $('#cancelReason').keyup(function()
	{
		var yourInput = $(this).val();
		re = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi;
		var isSplChar = re.test(yourInput);
		if(isSplChar)
		{
			var no_spl_char = yourInput.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
			$(this).val(no_spl_char);
		}
	});
 
});
 //Ajay Khurmi 2017-05-26 end..
 
function proceedToApprove(role,event)
{
 
	//alert("Inside proceedToApprove :role-"+role+"|event-"+event);
	var isOnwardProceed=false;  
	var isRoundTrip=$("#isRoundTrip").val();
 
 	var onwardCheckList=document.getElementsByName("onwardCheckList");

	var check=true;
	
	for(var i=0;i<onwardCheckList.length;i++)
	{
		var chekBoxObj=onwardCheckList[i];
		if(chekBoxObj.checked){
			isOnwardProceed=true;
		}
	}

	var isReturnProceed=false;  
 	if(isRoundTrip=='YES')
 	{
	 	var returnCheckList=document.getElementsByName("returnCheck");	
	 	
	 	for(var j=0;j<returnCheckList.length;j++)
		{
			var chekBoxObj=returnCheckList[j];
			if(chekBoxObj.checked){
				isReturnProceed=true;
			}
		}
 	}
 	
 	//alert("isOnwardProceed-"+isOnwardProceed+"|isReturnProceed-"+isReturnProceed);
 
 
    if(!isOnwardProceed && !isReturnProceed)
    {
    	alert("Please select passenger for cancellation approval");
    	check=false;
    	return false;
    }

    
    var onwardIsOfficial=document.getElementsByName("onwardIsOfficial");
    var returnIsOfficial=document.getElementsByName("returnIsOfficial");
    
    if(isRoundTrip=='YES')
 	{
 		
 	}else
 	{
 		/*
 		for(var i=0;i<onwardCheckList.length;i++)
		{
			var chekBoxObj=onwardCheckList[i];
			if(chekBoxObj.checked)
			{
				var onwardIsOfficialObj=onwardIsOfficial[i];
				var onwardRadioValue="";
				alert("onwardIsOfficialObj.length-"+onwardIsOfficialObj.length);
				if(onwardIsOfficialObj.length>1)
    			{
    				if(onwardIsOfficialObj[0].checked==true)
		    			onwardRadioValue=onwardIsOfficialObj[0].value;
		    		if(onwardIsOfficialObj[1].checked==true)
		    		 	onwardRadioValue=onwardIsOfficialObj[1].value;
    			}
    			alert("onwardRadioValue-"+onwardRadioValue);
    			if(onwardRadioValue==''){
    				alert("Please Select Ground.");
    			    check=false;
    				return flase;
    			}
			}
		}
		* */
 	} 		

	$("#actionType").val(role);
	$("#event").val(event);
	
 	//alert("|actionType="+role+"|event-"+event);
 	if(check){

		var actionVal=$("#airTicketDetail").attr('action');
		actionVal=actionVal.replace('/page', "");
		$("#airTicketDetail").attr('action',actionVal);
		$("#airTicketDetail").submit();	
 	}
 	
 	return true;
 
 }


 	
function cancelAirTicket(bookingTxnId,bookingId)
{
	$("#bookingId").val(bookingId);
	$("#bookingTxnId").val(bookingTxnId);
	$("#airBookingCancelForm").attr('action','../air/cancelAirBooking.do');
    $("#airBookingCancelForm").submit();	
 
 }
 
 function submitAirAbortCancel(){
    $("#isAbortCancel").val("true");
         var disaproveReason= $("#disaproveReason").val();
     //  checkNoneChar(disaproveReason);
     
        if(""==$("#disaproveReason").val())
        {
        alert("Please enter Abort Cancel Reason");
        return false;
        }
       var formAir = document.forms['aircancelticket'];
       if(formAir!=''){
         formAir.action="/pcda/af/page/pcda/air/airAppprovedCancellationPool.do?";
      var actionOriginal=formAir.action;
      formAir.action=actionOriginal.replace('/page', "");
      formAir.submit();
       }
       
}
 
function abortCancellation() {
  
         var cancelDiv=document.getElementById("panel");
         cancelDiv.style.display='none';
         var abCancelDiv=document.getElementById("abortCancelDiv");
         var ticketNo=document.getElementsByName("ticketNoAbort")[0].value;
         abCancelDiv.style.display='';
       }
   function reasonValueBlank(){
	document.getElementById("disaproveReason").value="";
}
 
 
function proceedCancellation(serviceProvider){

var intendedTravelFlag=$("#intended_travel_flag").val();
var intendedMsg=$("#intended").val();

if(intendedTravelFlag == 'Yes' && intendedMsg==' ')
	{
		var intendedTravel=document.getElementById("popDiv");
        intendedTravel.style.display = 'block';
        intendedTravel.style.margin = '200px';
        intendedTravel.style.width = '100%';
        intendedTravel.style.height = '100%';
        intendedTravel.style.position = 'absolute';
        intendedTravel.style.top = '0';
		return false;
	}
else{
hide('popDiv');
$("#screen-freeze").show();
var isCancellationAllowed = document.getElementById("is_can_allowed").value;

//open below validation once need to apply cancellation timing restiction.

var airlineType = document.getElementById("airlineType").value;

if(isCancellationAllowed == 'NO'){

if(airlineType == 'AI'){
if(serviceProvider == 0)
alert("Online cancellation for Air India is only allowed before 03 hours of flight departure.You are requested to immediately call Balmer Lawrie Customer Care on 0124-4603500 / 0124-6282500 or the Airlines Customer Care for Offline Cancellation");
if(serviceProvider == 1)
alert("Online cancellation for Air India is only allowed before 03 hours of flight departure.You are requested to immediately call Ashok Travels and Tours Customer Care on 022 48946701/9372818685/8169668269 or the Airlines Customer Care for Offline Cancellation");	
	$("#screen-freeze").hide();
	return false;
}
else{
if(serviceProvider == 0)
alert("Online cancellation for Other flights is only allowed before 03 hours of flight departure.You are requested to call immediately at Balmer Lawrie Customer Care on 0124-4603500 / 0124-6282500 or the Airlines Customer Care for Offline Cancellation.");
if(serviceProvider == 1)
alert("Online cancellation for Other flights is only allowed before 03 hours of flight departure.You are requested to immediately call Ashok Travels and Tours Customer Care on 022 48946701/9372818685/8169668269 or the Airlines Customer Care for Offline Cancellation.");	
	$("#screen-freeze").hide();
return false;
}
}

var event="";

	if(serviceProvider == 0){
	  event='next'; 
	}else if(serviceProvider == 1){
	  event='attCancel';
	}

	if(event != ""){
var form = document.forms["aircancelticket"];
form.event.value=event;
sendAirCancellationTicketMail(serviceProvider);

form.submit();

	}else{
	  alert("Service Provider Not Found.");
	  return false;
	}
  }
}

function sendAirCancellationTicketMail(serviceProvider)
 {
 	var response="";
 	var transactionId = document.getElementById("transactionId").value;
	 $.ajax({
	 
		url: "/pcda/af/pcda/air/SendAirCancellationTicketByMail.do",
		type: "get",
		async: false,
		data: "optTxnId="+transactionId+"&serviceProvider="+serviceProvider,
		dataType: "text",
		success: function(msg)
		{
		}
	 });

 }
 
 function pop(div) {
 
				document.getElementById(div).style.display = 'block';
			}
			
	function hide(div) {
				document.getElementById(div).style.display = 'none';
			}
 
 