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


function doGenerate()
{
	var result =validateDateFilter();
	
	if(result)
	{
		encryptPNo();
		$("#requestRptFilter").submit();	
		
	}
	else{
		event.preventDefault();
	}
	
}

 function validateDateFilter(){

	var from = $("#frmBookingDt").val();
	
	
	var to = $("#toBookingDt").val();
	

   
	if(from=="" && to=="")
	{
		return true;
	}
	
	if(from=="" && !to=="")
	{
		alert("From Date Should Not Empty");
		
		return false;
	}
	if(to=="" && !from=="")
	{
		alert("To Date Should Not Empty");
		
		return false;
	}
	
   if(!to=="" && !from=="")
	{
  	from = from.split("-");
  	to = to.split("-");
 
  	var sDate = new Date(from[2]+","+(from[1])+","+from[0]);
   
  	var eDate = new Date(to[2]+","+(to[1])+","+to[0]);
  	//alert("from-Date :: "+sDate+" "+"  :: to-Date ::"+eDate); 
  	  
  	if(sDate > eDate )
  	{
		  
  	   alert("To Date Should Be Greater Than From Date");
  	 
  	   return false;
  	}
  	
  	var daysApart = Math.round((eDate-sDate)/86400000);
    if(daysApart > 14)
    {   
		  //alert("Report Is Available For 15 Days Only.");
		  //document.getElementById('toBookingDt').focus();
		  //return false;
	}
	
	return true;
	}
}


function viewRequestRailReports(requestId)
 {
	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
    ajaxRequestFace = new LightFace.Request(
    {
		width: 850,
		height: 400,
		url:  $("#context_path").val()+"reports/getRailReqIdData",
		
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		request: { 
			data: { 
				requestId: requestId,
				
			},
			method: 'post'
		},
		title: 'Request View'
	});
    ajaxRequestFace.open();
 }
function encryptPNo(){

	var prsnlNo = $("#personalNo").val().trim();
	
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNo").val(encryptPNo);
}
}
