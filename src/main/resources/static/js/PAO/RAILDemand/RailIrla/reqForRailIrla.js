$(document).ready(function() {
	
	$("#reqToDt").datetimepicker({
	  lang:'en',
		timepicker:false,
		format:'d-m-Y',
		formatDate:'d-m-Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		minDate:$("#fromdate").val(),
		onShow: function() {
			$('#reqToDt').val("");
			var today = new Date();
			today.setDate(today.getDate() - 1);

			$("#reqToDt").datetimepicker("setOptions", {
				maxDate: today
			});
          		    
		}
	});
	
	
	$('#requestType_0').click(function() {
	     $("#selected_period").hide();
	     $("#reqToDt").val("");
	 });
    
    $('#requestType_1').click(function() {
        $("#selected_period").show();
        $("#reqToDt").val("");
	
	
	});
	
});

function disMonthYear(dwnReqId){
	
  $("#dis_month_year_" + dwnReqId).show();
}

function submitAdjustmentProces(dwnReqId) {
	
	$("#dwnRequestIdAdj").val(dwnReqId);
	var reqType = $("#adjustment_" + dwnReqId).is(':checked') ? $("#adjustment_" + dwnReqId).val() : undefined;

	if (reqType != null && reqType == "adjustment") {

		if (confirm("Do you want to perform Adjustment!")) {
			var month = $("#monthName_" + dwnReqId).val();
			var year = $("#yearName_" + dwnReqId).val();
			$("#adjustmentDate").val(month + "-" + year);
			$("#railIRLAAdjustmentForm").submit();
}

		} else {
			alert("Please select radio button.");
			return false;
		}

	}

function saveRailDownloadRequest() {
    
 var frmDt = $("#fromdate").val();
 var toDate=$("#toDate").val();
 var serviceId="";
	if ($("#drop_down").val() == "Yes") {
		if ($("#serviceId").val() == "") {
			alert("Please Select Service !!")
			return false;
		} else {
			serviceId = $("#serviceId").val();
			$("#service_id").val(serviceId);
		}
	}
 
 $("#hide_req_button").hide();
    var reqType = $('input[name="requestType"]:checked').val();
   if(reqType == "0"){
	 
     
      $("#form_date").val(frmDt);
      $("#to_date").val(toDate);
      
      
  }else if(reqType == "1"){
      $("#form_date").val(frmDt);
      var selectedToDate=$("#reqToDt").val();
      
     if(null!=selectedToDate && selectedToDate.trim() == ""){ 
	       alert("Please select To Date.");
	       $("#hide_req_button").show();
	       return false;
      }else{
        $("#to_date").val(selectedToDate);
      }
  }
	
	 var fromDate= $("#form_date").val();
     var toDate = $("#to_date").val();
     
     $.ajax({
   url:  $("#context_path").val()+"pao/getRailIrlaCount",
   type:"GET",
   data: "fromDate="+fromDate+"&toDate="+toDate+"&serviceId="+serviceId,
   dataType: "text",
   async:false,
   success: function(result){		
	 if(parseInt(result)> 0){	 
	     if(confirm("Total number of Records: "+result+"\n Do you wish to submit your request ?")){
		    $("#railIRLADownloadReqForm").submit();
	     }else{
	       $("#hide_req_button").show();
	     } 
		    
	  }else{
			alert("No IRLA data found for given time frame.");
			$("#hide_req_button").show();
			return false;           	 
      }
   }});
     
     
     
	}
	
function submitMasterMissingProcess(reqId, reqStatus) {
	$("#dwnRequestId_mm").val(reqId);
	$("#dwn_RequestStatus_mm").val(reqStatus);
	
	var reqType = $("#masterMissingAndAdj_" + reqId).is(':checked') ? $("#masterMissingAndAdj_" + reqId).val() : undefined;

	var confMess = "";

	if (reqStatus == 3) {
		confMess = "Do you wish to submit more records for Master Missing. \n\n! You cannot edit previously submitted records.";
	} else {
		confMess = "Do you want to perform Master Missing!";
	}
	if (reqType != null && reqType == "masterMissing") {
		if (confirm(confMess)) {
			$("#railIRLAMasterMissingForm").submit();
		}
	} else {
		alert("Please select radio button.");
		return false;
	}


	}


    
    
    



