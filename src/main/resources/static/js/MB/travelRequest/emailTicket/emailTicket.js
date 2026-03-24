$(document).ready(function(){
	
getFldFunction();
});	


function getFldFunction()
 {
	  var travelType = $("#travelType").val();
	  
	  if(travelType=='air')
	  {
	  	$("#air").show();
	  	$("#airPnr").show();
	  	$("#fbOrPnr").show();
	  	$("#rail").hide();
	  	$("#airlinePnr").show();
	  	
	  	
	  }
	  
	if (travelType == 'rail') {
	  	$("#rail").show();
	  	$("#air").hide();
	    $("#airPnr").hide();
	    $("#airlinePnr").hide();
	    
	   
	  }
  
}

function getTktData(event) {
	var fbOrPnr = $("#fbOrPnr").val().trim();
	 var personalSearch = $("#personalSearch").val().trim();
	 var airlinePnr=$("#airlinePnr").val().trim();

	if ((airlinePnr ==''&& fbOrPnr == '') && personalSearch == '') {
		alert("Please provide Personal No. or FB No or Air Line PNR");
		 	event.preventDefault();
		}else{
		 if(personalSearch != ''){
		var encryptPNo = CryptoJS.AES.encrypt(personalSearch,"Hidden Pass");
		$("#personalSearch").val(encryptPNo);
		  }
		
		  $("#emailTktForm").submit();
	  }
	 

}
		 
 function sendAirTicketMail(transactionId,personalNo,oldMailId,sNo)
 {
 	var isUpdateMail='NOT';
 	var varMail=$("#mailId").val()+sNo;
 	var mailId= varMail;
 	
 	if(mailId!='')
 	{
		if(!checkemail(mailId))
		{
			// document.getElementById(varMail).focus();
			 return false;
	 	}
	}
	
	if(mailId=='')
 	{
 		 alert("Please enter a valid email address");
		// document.getElementById(varMail).focus();
		 return false;
	}
	
 	if(mailId!=oldMailId)
 	{
 	 	var falg = confirm("Do you want to update this email id in the User’s Profile as well?");
	 	if(falg){
	 	isUpdateMail="YES";
	 	}
 	}
 	
 	$("#loader").style.display='block';
	
	 	var response="";
		$.ajax({
			url:$("context_path").val()+ "mb/sendAirTicketMail",
			type: "post",
			data : {
				//	userAction : "sendAirTicketMail",
					optTxnId:transactionId,
					mailId:mailId,
				//	serviceProvider:serviceProvider,
					personalNo:personalNo,
					isUpdateMail:isUpdateMail
				},
			dataType: "text",
			
			success: function(msg)
			{
				$("#loader").style.display='none';
				alert(msg);
			}
	});
 }
		
 
 
 function sendRailTicketMail(bkpnr,personalNo,oldMailId,sNo,bookingId)
 {
 	//tktNo=passwordEny(tktNo);
 //	bkpnr=passwordEny(bkpnr);
 	var isUpdateMail='NOT';
 	var varMail=$("#mailId").val()+sNo;
 	var mailId= varMail;
 	
 	if(mailId!='')
 	{
		if(!checkemail(mailId))
		{
			 return false;
	 	}
	}
	
	if(mailId=='')
 	{
 		 alert("Please enter a valid email address")
		 return false;
	}
	
 	if(mailId!=oldMailId)
 	{
 	 	var falg = confirm("Do you want to update this email id in the User’s Profile as well?");
	 	if(falg){
	 	isUpdateMail="YES";
	 	}
 	}
 	
 	$("#loader").style.display='block';

	 	var response="";
		$.ajax({
			url:$("#context_path").val()+ "mb/sendEmailticket",
			type: "post",
			data : {
					userAction : "sendRailTicketMail",
					bkpnr:bkpnr,
					bookinId:bookingId,
					mailId:mailId,
					personalNo:personalNo,
					isUpdateMail:isUpdateMail
				},
			dataType: "text",
			
			success: function(msg)
			{
				$("#loader").style.display='none';
				alert(msg);
			}
	});
 }
 
function printAirTicket(transactionId)
{
 	window.open("/pcda/af/page/pcda/air/AirTicketPrint.do?optTxnId="+transactionId);
 	$("#").submit();				  
} 

function printRailTicket(ticket,bookingId)
{
	ticket=passwordEny(ticket);
	var bookingIdVal=passwordEny(bookingId); 
}

function checkemail(str)
{
 
 var filter=/[a-z0-9]+@[a-z]+\.[a-z]{2,3}/
 if (filter.test(str))
    return true
	 else 
	 {
	    alert("Kindly check your mail id & try again.")
	    return false
	}
}
 