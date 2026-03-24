$(document).ready(function(){
var toDate= $('#to_date').val();
var fromDate= $("#form_date").val();

 $('#to_date').datetimepicker({
	lang:'en',
	timepicker:false,
	format:'d-m-Y',
	formatDate:'d-m-Y',
	scrollInput:false,
	yearEnd : 2100,
	maxDate : toDate,
	minDate : fromDate,
		onShow: function () {
			  $('#to_date').val("");
		}
	});  
	
});


function disSettlementInput(dwnReqId){
	var today = new Date();
 $("#dis_Settlement"+dwnReqId).show();
  $('#utrDate_'+dwnReqId).datetimepicker({
	lang:'en',
	timepicker:false,
	format:'d-m-Y',
	formatDate:'d-m-Y',
	scrollInput:false,
	yearEnd : 2100,
	maxDate : today,
		onShow: function () {
			  $('#utrDate_'+dwnReqId).val("");
		}
	});  
}

function submitVoucherSettlement(dwnReqId){
	  $("#dwnRequestId_adj").val(dwnReqId);
	  var utrDate=$("#utrDate_"+dwnReqId).val();
	  var utrNumber=$("#utrNumber_"+dwnReqId).val();
	  
	    if(null!=utrNumber && utrNumber.trim() == ""){
		       alert("UTR Number can not be empty");
		       return false;
	      }else{
	        $("#utrNumber").val(utrNumber);
	      }
	      
	    if(null!=utrDate && utrDate.trim() == ""){ 
	       alert("Please select UTR date");
	       return false;
	    }else{
	    $("#utrDate").val(utrDate);
	   }
	   if(confirm("Do you want to settle this voucher!")){
	        var formAction = $("#settleVoucherForm")[0].action;
			$("#settleVoucherForm").submit();
	    }
	}
	
function submitDemandRequest(dwnReqId){
	  $("#dwnRequestId_dem").val(dwnReqId);
	  
	   if(confirm("Do you want to generate demand!")){
			$("#generateDemandForm").submit();
	    }
	}


function downloadVoucher(voucherReqId,requestFor){
	 if(voucherReqId != null && requestFor != null) {
		confirm("Do you want to download this file");
	} 	
	$("#dwnReqId").val(voucherReqId);
	$("#voucher").val(requestFor);
	$("#downloadVoucher").submit();	
}



function saveVoucherDwnRequest(){
    var selectedToDate=$("#to_date").val();
    var serviceProvider=$("#serviceProvider").val();
    $("#service_id").val($("#serviceId").val());
    $("#travel_type").val($("#travelType").val());
    $("#pao_id").val($("#paoId").val());
   if(null!=selectedToDate && selectedToDate.trim() == ""){ 
	       alert("Please select To Date.");
	       return false;
	       
    }else{
      $("#to_date").val(selectedToDate);
    }
    
     if(null!=serviceProvider && serviceProvider.trim() == ""){ 
	       alert("Please select Vendor.");
	       return false;
    }else{
      $("#service_provider").val(serviceProvider);
    }
    
//$("#hide_req_button").hide();    

    
var fromDate= $("#form_date").val();
var toDate = $("#to_date").val();
var serviceProvider= $("#serviceProvider").val();
var serviceId = $("#serviceId").val();
var travelType= $("#travelType").val();
var paoId = $("#paoId").val();


 $.ajax({
	   url:$("#context_path").val()+"cgda/checkCount",
	   type :"post",
	   data: {fromDate :fromDate, toDate:toDate, serviceProvider:serviceProvider, serviceId:serviceId, travelType:travelType, paoId:paoId},
	   dataType: "json",
	   async:false,
	   success: function(data){
	
	 if(data.count> 0 && data.amount > 0){	
		     if(confirm("Total number of Records: "+data.count+"\nVoucher Amount: "+data.amount+"\n Do you wish to submit your request ?")){
			  $("#voucherDwnReqForm").submit();
		     }else{
		       $("#hide_req_button").show();
		     } 
			    
	}else if(data.amount < 0.0){
				alert("Voucher is negative for given time frame, Please increase To Date.");
				$("#hide_req_button").show();   
				return false;           	 
	      }else{
	            alert("No demand register data found for given time frame.");
	            $("#hide_req_button").show();   
				return false;
	      }
	   }});
	  
	}
