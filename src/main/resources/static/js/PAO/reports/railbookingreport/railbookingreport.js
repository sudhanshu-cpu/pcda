 $(document).ready(function() {
    $("#frmBookingDt").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                yearEnd : 2100,
		onShow: function () {
			  $('#frmBookingDt').val("");
		}
				
			});
			
			 $("#toBookingDt").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                yearEnd : 2100,
		onShow: function () {
			  $('#toBookingDt').val("");
		}

				
			});
			 $("#frmJourneyDt").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                yearEnd : 2100,
		onShow: function () {
			  $('#frmJourneyDt').val("");
		}

				
			});
			 $("#toJourneyDt").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                yearEnd : 2100,
		onShow: function () {
			  $('#toJourneyDt').val("");
		}

				
			});
});
 

 function checkValidation()
 {
	 var from = $("#frmBookingDt").val().trim();
	 var to = $("#toBookingDt").val().trim();
	 var fromJourneyDate = $("#frmJourneyDt").val().trim();
	 var toJourneyDate = $("#toJourneyDt").val().trim();
	 var prsnlNo = $("#personalNo").val().trim();
	 var bkgId = $("#bookingId").val().trim();
	 var reqId = $("#requestId").val().trim();
	 var pnrNum = $("#pnrNo").val().trim();
	 var tktNo = $("#ticketNo").val().trim();
	
	 
	if((from == "" && to == "" ) && (fromJourneyDate == "" && toJourneyDate == "") && prsnlNo=="" && bkgId=="" &&reqId==""&&pnrNum==""&&tktNo==""){
		alert("Please provide (from-to date) or (journeyfrom-to date) or personal No")
		return false;
	} 

	 
 if ((from != "" && to != "")) {
		from = from.split("-");
		to = to.split("-");
		
		var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);
		var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);
		

		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toDate").focus();
			return false;
		}
		if (toJrnyDate < fromJrnyDate) {
			alert("To Journey Date Should Be Greater Than From Journey Date");
			return false;
		}
		var daysApart = Math.round((eDate - sDate) / 86400000);
		if (daysApart >= 15) {
			alert("Report Is Available For 15 Days Only.");
			$("#toBookingDt").focus();
			return false;
	}
	
	
		  
	    }
	
	 if ((fromJourneyDate != "" && toJourneyDate != "")) {
		
		fromJourneyDate = fromJourneyDate.split("-");
		toJourneyDate = toJourneyDate.split("-");
		
		var fromJrnyDate = new Date(fromJourneyDate[2] + "," + (fromJourneyDate[1]) + "," + fromJourneyDate[0]);
      	var toJrnyDate = new Date(toJourneyDate[2] + "," + (toJourneyDate[1]) + "," + toJourneyDate[0]);

		
		if (toJrnyDate < fromJrnyDate) {
			alert("To Journey Date Should Be Greater Than From Journey Date");
			return false;
	  }
		
		
		
		var jdaysApart= Math.round((toJrnyDate - fromJrnyDate) / 86400000);
	
	
		if (jdaysApart >= 15) {
			alert("Report Is Available For 15 Days Only.");
			$("#toJourneyDt").focus();
			return false;
		}
	
	    }
	
	


	if (from == "" && !to == "") {
		alert("Please provide From date");
		return false;
	  }
	
	if (!from == "" && to == "") {
		alert("Please provide To Date");
			return false;
	    }
	
		
	if (fromJourneyDate != "" && toJourneyDate == "") {
			alert("Please Enter To Journey Date");
			return false;
	  }
		if (toJourneyDate != "" && fromJourneyDate == "") {
			alert("Please Enter From Journey Date");
			return false;
	    }
		
		
	return true;	
	
	  }
	 
 function generateReport(){    
	
	 var result = checkValidation();
		  
	 if(result){
	  $("#railBookingReport").submit();	
	 }else {
		return false;
	    }
 
	  }
	 
 
 
 function viewBookingReports(bookingId){
  
   if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
 	ajaxBookingFace = new LightFace.Request({
		 			width : 900,
		 			height:400,
					url: $("#context_path").val()+ "pao/tktBkgDtls",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							bookingId: bookingId,
							//event:"view"
						},
						method: 'post'
					},
					title: 'Booking View'
				});
   ajaxBookingFace.open( );
   
 
 }
 
 function viewCancellationReports(bookingId) {
	 
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
  
   
 	ajaxBookingFace = new LightFace.Request({
		 			width : 900,
		 			height:450,
					url: $("#context_path").val()+ "pao/tktCanDtls",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							bookingId: bookingId,
							event:"canView"
						},
						method: 'post'
					},
					title: 'Cancellation View'
				});
   ajaxBookingFace.open();
   
}
