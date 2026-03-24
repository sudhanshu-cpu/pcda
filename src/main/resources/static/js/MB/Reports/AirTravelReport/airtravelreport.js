
$(document).ready(function() {
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
			 $("#frmJourneyDt").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                yearEnd :2100,
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
                scrollInput:false,
                yearEnd :2100,
		onShow: function () {
			  $('#toJourneyDt').val("");
		}

				
			});
});

function viewBookingReports(bookingId)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	ajaxBookingFace = new LightFace.Request({
					width : 850,
		 			height:400,
					url:$("#context_path").val() + "reports/getAirTravelData",
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
					title: 'Air Booking Report View'
				});
  ajaxBookingFace.open();
  
  
}

 
  
 
 function generateReport()
 {
 var bookingId=$("#bookingId").val();
 var requestId=$("#requestId").val();
 var fromBookingDate=$("#frmBookingDt").val();
 var toBookingDate=$("#toBookingDt").val();
 var fromJourneyDate=$("#frmJourneyDt").val();
 var toJourneyDate=$("#toJourneyDt").val();
 var personalNumber=$("#personalNo1").val().trim();
 var travelType=$("#travelType").val();
 var bookingStatus=$("#bookingStatus").val();

 var validFlag="true";
 
  if( bookingId!= "" || requestId !=""  || personalNumber!=""){ 
  validFlag ="false";
  } 
  
  if(( personalNumber!="") && (fromBookingDate=="") && (toBookingDate=="")){
   alert("Please enter Booking Date ");
	 return false;
  }
  
  
    if((fromBookingDate=="") && (toBookingDate=="") && (fromJourneyDate =="") &&( toJourneyDate=="") && validFlag=="true"){ 
   alert("Please enter Booking date or Journey date");
   return false;
	 
  }
  if(fromBookingDate!='' && toBookingDate!=''){
  
  	var from = fromBookingDate.split("-");
  	var to = toBookingDate.split("-");
  	var bookingSDate = new Date(from[2]+","+(from[1])+","+from[0]);
  	var bookingEDate = new Date(to[2]+","+(to[1])+","+to[0]);
  	
  	if(bookingSDate  > bookingEDate )
  	{
  	   alert(" To Booking Date Should Be Greater Than From Booking Date");
  	    return false;
  	   
  	}
  	var daysApart = Math.round((bookingEDate-bookingSDate)/86400000);
    if((daysApart >90) && ( personalNumber=="")){ 
  
		  alert("Report Is Available For 90 Days Only.");
		  return false;
	}
	}
 
 if(fromJourneyDate!='' && toJourneyDate!=''){

 
 	var jFrom = fromJourneyDate.split("-");
  	var jTo = toJourneyDate.split("-");

  	var journeySDate = new Date(jFrom[2]+","+(jFrom[1])+","+jFrom[0]);
  	var journeyEDate = new Date(jTo[2]+","+(jTo[1])+","+jTo[0]);
 
  	if(journeySDate  > journeyEDate)
  	{
  	   alert(" To Journey Date Should Be Greater Than From Journey Date");
  	  return false;
  	   
  	}
  	var daysApart1 = Math.round((journeyEDate-journeySDate)/86400000);
    if(daysApart1 > 90)
    {   
		  alert("Report Is Available For 90 Days Only.");
		  return false;
	}
}
    
     if($("#frmBookingDt").val()!="")
	  {
	    if($("#toBookingDt").val()=="")
	    {
	      alert("Please enter to booking date");
	      return false;
	      
	    }
	  }
	  if($("#toBookingDt").val()!="")
	  {
	    if($("#frmBookingDt").val()=="")
	    {
	      alert("Please enter from booking date");
	      return false;
	    }
	  }
	  if($("#frmJourneyDt").val()!="")
	  {
	    if($("#toJourneyDt").val()=="")
	    {
	      alert("Please enter to journey date");
	      return false;
	      
	    }
	  }
	  if($("#toJourneyDt").val()!="")
	  {
	    if($("#frmJourneyDt").val()=="")
	    {
	      alert("Please enter from journey date");
	    return false;
	      
	    }
	  }
	  
	  if(personalNumber!=''){
		var encryptPNo = CryptoJS.AES.encrypt(personalNumber,"Hidden Pass");
		$("#personalNo1").val(encryptPNo);
}
	  $("#bookingRptFilter").submit();
	  
	  
	  
	  
//	   document.getElementById("event").value="next";
//	  
//	   var actionOriginal=document.bookingRptFilter.action;
//	   document.bookingRptFilter.action=actionOriginal.replace('/page', "");
//	   document.bookingRptFilter.submit();	
 }