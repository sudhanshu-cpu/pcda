$(document).ready(function(){
	
	$("#frmBookingDt").datetimepicker({
	  lang:'en',
		timepicker:false,
		format:'d-m-Y',
		formatDate:'d-m-Y',
		scrollMonth : false,
		scrollInput:false,
        yearEnd :2100,
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
		scrollInput:false,
        yearEnd :2100,
		onShow: function () {
			  $('#toBookingDt').val("");
		}
	});
});


function doGenerate() {


	var result =validateDateFilter();
	
	if(result)
	{
			encryptPNumber();
		$("#requestAirCancellationReport").submit();	
		
	}
	else{
		event.preventDefault();
	}
	
}

function validateDateFilter()

 {
	
	 var bookingId = $("#bookingId").val().trim();
	 var fb = $("#fbNumber").val().trim();
	 var personalNo = $("#personalNo").val().trim();
	 var fromDate = $("#frmBookingDt").val().trim();
	 var toDate = $("#toBookingDt").val().trim();
	 var fc = $("#fcNumber").val().trim();
	 
	
	if(fromDate == "" && toDate == "" && personalNo==""  && bookingId=="" && fb=="" && fc==""){
		alert("Please provide valid input");
		return false;
	} 
	
	 if ((fromDate != "" && toDate != "")) {
		fromDate =  fromDate.split("-"); 
		toDate = toDate.split("-");
		
		var eDate = new Date(toDate[2] + "," + (toDate[1]) + "," + toDate[0]);
		var sDate = new Date(fromDate[2] + "," + (fromDate[1]) + "," + fromDate[0]);
		

		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toBookingDt").focus();
			return false;
		}
		var daysApart = Math.round((eDate - sDate) / 86400000);
		if (daysApart > 90) {
			alert("Report Is Available For 90 Days Only.");
			$("#toBookingDt").focus();
			return false;
	}  
	    }
	 

	
	if (fromDate == "" && toDate != "") {
		alert("Please provide From date");
		return false;
	  }
	
	if (fromDate != "" && toDate == "") {
		alert("Please provide To Date");
			return false;
	    }
	    return true;
	    
	    
	    
	}    
	    
function encryptPNumber(){
	
	var prsnlNo = $("#personalNo").val().trim();
	
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNo").val(encryptPNo);
}
	
	}


