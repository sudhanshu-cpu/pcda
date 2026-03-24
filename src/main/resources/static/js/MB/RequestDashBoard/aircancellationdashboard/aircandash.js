$(document).ready(function(){
	 $("#panel").show();
	 $("#popDiv").hide();
	 $("#abortCancelDiv").hide();
});


function getCancelPage(bookingId)
{
	$("#bookingId").val(bookingId);
    $("#airCanDashPage").submit();	
 
 }
 
 function abortCancellation(){
	 
	 $("#panel").hide();
	 $("#popDiv").hide();
	 $("#abortCancelDiv").show();
 }
 
 
 function submitAirAbortCancel(){
	 
   var reason = $("#abortReason").val().trim();
	var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
     
        if(""==reason)
        {
        alert("Please enter Abort Cancel Reason");
        preventDefault();
        }else{
			if(alphaNumRegEx.test(reason)){
				$('#airCanDashBkgIdPage').attr('action', $("#context_path").val()+'mb/abortAirDashCancel');
                $('#airCanDashBkgIdPage').submit();
			}else{
				alert("Special Characters Are Not Allowed");
				 preventDefault();
			}
 }
 
 }
 
 
 function preProceedCancel(serviceProvider){
	 var intendedTravelFlag=$("#intendedCanFlag").val();
	  $("#intended").val("");
	
	if(intendedTravelFlag == 'Yes')
	{
		var intendedTravel=$("#popDiv");
        intendedTravel.show();
        intendedTravel.style.margin = '200px';
        intendedTravel.style.width = '100%';
        intendedTravel.style.height = '100%';
        intendedTravel.style.position = 'absolute';
        intendedTravel.style.top = '0';
        preventDefault();
		return false;
	}else{
		proceedCancellation(serviceProvider);
	} 
 }
 
 
function proceedCancellation(serviceProvider){
var intendedTravelFlag=$("#intendedCanFlag").val();
var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
var intendedMsg=$("#intended").val().trim();


if(intendedTravelFlag == 'Yes')
	{
		if(intendedMsg==''){
			alert("Please Enter Reason For Cancellation");
			preventDefault();
		}
     if(intendedMsg!=''){
	if(!alphaNumRegEx.test(intendedMsg)){
		alert("Special Characters Are Not Allowed");
		preventDefault();
	}
 }
 }
hide('popDiv');
$("#screen-freeze").show();
 
var isCancellationAllowed = $("#isCanAllowed").val();

//open below validation once need to apply cancellation timing restiction.



if(isCancellationAllowed == 'NO'){


if(serviceProvider == 'BL'){
alert("Online cancellation for flights is only allowed before 06 hours of flight departure.You are requested to call immediately at Balmer Lawrie Customer Care on 0124-4603500 / 0124-6282500 or the Airlines Customer Care for Offline Cancellation.");
$("#screen-freeze").hide();
return false;
 }
if(serviceProvider == 'ATT'){
alert("Online cancellation for flights is only allowed before 06 hours of flight departure.You are requested to immediately call Ashok Travels and Tours Customer Care on 7304542848/7738687401 or the Airlines Customer Care for Offline Cancellation.");	
$("#screen-freeze").hide();
return false;
 }	
  $("#screen-freeze").hide();
 preventDefault();
   return false;

}

var event="";

	if(serviceProvider == 'BL'){
	  event='next'; 
	}else if(serviceProvider == 'ATT'){
	  event='attCancel';
	}

	if(event != ""){
$("#airCanDashBkgIdPage").submit();

	}else{
	  alert("Service Provider Not Found.");
	  return false;
	}
  }

 
 
 		
	function hide(div) {
				document.getElementById(div).style.display = 'none';
				
			}
 