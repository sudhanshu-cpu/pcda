function showAirTicketDetails(bookingId, isExpCan) {
	
	$("#bookingId").val(bookingId);
	$("#isExpCan").val(isExpCan);
	
	var actionVal = $("#airTicketCanDetail").attr('action');
	actionVal = actionVal.replace('/page', "");
	$("#airTicketCanDetail").attr('action', actionVal);
	$("#airTicketCanDetail").submit();
}


function submitDisapprove(){
	var reason =$("#disaproveReason").val().trim();
	if(reason==''){
		alert("Please Enter DisApprove Reason");
		return false;
	}else{
	   $("#airCanApprove").submit();
	}
}


function proceedToApprove(role, event) {


	var isOnwardProceed = false;
	 var isReturnProceed = false;
	var isRoundTrip = $("#isRoundTrip").val();
	 var onwardCheckList =   document.getElementsByName("onwardCheck");
     var returnCheckList =  document.getElementsByName("returnCheck");
	var check = true;

	for (var i = 0; i < onwardCheckList.length; i++) {
		var chekBoxObj = onwardCheckList[i];
		if (chekBoxObj.checked) {
			isOnwardProceed = true;
		
		}
	}

	
	if (isRoundTrip == 'YES') {
		for (var j = 0; j < returnCheckList.length; j++) {
			var chekBoxObj = returnCheckList[j];
			if (chekBoxObj.checked) {
				isReturnProceed = true;
				
			}
		}
	}

	


	if (isOnwardProceed == false && isReturnProceed == false) {

        alert("Please select passenger for cancellation  approval");
		check = false;
		
	}

	if (check==true && event=='approve') {
            $("#onwardCheckLength").val(onwardCheckList.length);
	        $("#returnCheckLength").val(returnCheckList.length);
	        $("#apDiss").val(event);
	        $("#airCanApprove").submit();
	}else if(check==true && event=='disapprove'){
		$("#onwardCheckLength").val(onwardCheckList.length);
	    $("#returnCheckLength").val(returnCheckList.length);
	    $("#apDiss").val(event);
		$("#mainFormBody").hide();
		$("#disApproveDiv").show();
	}

	

}

