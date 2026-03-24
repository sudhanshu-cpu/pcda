$( document ).ready(function() {
    $("#fromDate").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                yearEnd :2100,
		onShow: function () {
			  $('#fromDate').val("");
		}

			});
			
			 $("#toDate").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                yearEnd :2100,
		onShow: function () {
			  $('#toDate').val("");
		}

				
			});
});



	 function viewBookingReports(bookingId)
	 {
		 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	      
		 var urlToSet = $("#context_path").val()+ "reports/getTdrBookingDetails";
		 
	 	ajaxBookingFace = new LightFace.Request({
					width : 900,
		 			height:450,
						url: urlToSet,
						buttons: [
							{ title: 'Close', event: function() { this.close(); } }
						],
						request: { 
							data: { 
								bookingId: bookingId,
								event:"view"
							},
							method: 'post'
						},
						title: 'TDR Report View'
					});
	   ajaxBookingFace.open();
	   
	   
	 }
	 function hideExport()
	 {
	     document.getElementById("exportData").style.display='none';
	 }
	 function exportDataToXls()
	 {
	   
	    document.getElementById("exportType").value="bookingExport";
	    document.getElementById("event").value="export";
	    document.bookingRptFilter.submit();	
	   
	 }
	 
	 
	 
function validateDateFilter() {	
	
	var from = $("#fromDate").val();
	var to = $("#toDate").val();
	if (from == "" && to == "") {
		return true;
	}

	if (!from == "" && to == "") {
		alert("Please Fill To Date");
		return false;
	}
	if (!to == "" && from == "") {
		alert("Please Fill From Date");
		return false;
	}

	from = from.split("-");
	to = to.split("-");
	var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);
	var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);

	if (sDate > eDate) {
		alert("To Date Should Be Greater Than From Date");
		return false;
	}
	
	return true;
}
	 
	
	 function generateReport(event)
	 {        
		 var result = validateDateFilter();
	if (result) {
		encryptPNo();
		  $("#tdrForm").submit();
	 }
	else {
		event.preventDefault();
	}  
	 }

	 
	 function encryptPNo(){
	var prsnlNo = $("#personalNo").val().trim();
	
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNo").val(encryptPNo);
}
}