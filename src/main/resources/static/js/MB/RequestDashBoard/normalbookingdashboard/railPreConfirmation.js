$(document).ready(function() {
	
	$('#foodChoiceId').change(function() {
         
		if ($(this).is(':checked')) {

			if (confirm("Food will not be provided for any of the passengers. Do you want to Proceed?")) {
				$('.foodPref').each(function() {

					if ($(this).find('option[value="D"]').length === 0) {
						$(this).append('<option value="D">No Food</option>');
					}

					$('.foodPref').val('D');
				});

				$('.foodPref').css({
					'pointer-events': 'none',
					'background-color': '#f3f4f6',
					'opacity': '0.8'
				});
			} else {
				$(this).prop('checked', false);
			}


		} else {
			$('.foodPref').each(function() {
				$(this).find('option[value="D"]').remove();
			});


			$('.foodPref').css({
				'pointer-events': 'auto',
				'background-color': '',
				'opacity': '1'
			});


			$('.foodPref').val('V');
		}
	});
});




function pinDetails()
{
var journeyType=$("#journeyAddType").val();
if(journeyType=='unitToLeaveStn')
	{
			$("#pinID1").show();	
			$("#pinID2").show();	
		    $("#pinID3").show();
			$("#pinID").hide();	
	}else
	{
		    $("#pinID").show();	
		    $("#pinID1").hide();	
			$("#pinID2").hide();	
		    $("#pinID3").hide();
		
	}	

}


// to get picode dtls
function getPinCodeDtls()
{
	
	
 		var regexn = /^[0-9]*$/;
   		var pincode=$("#pinCode").val().trim();
   		
   		
   		
   		if(pincode!=""&&(!regexn.test(pincode)))
	    {
	    	alert("Pin Code should be numeric characters.");
	    	$("#pinCode").val("");
	    	return false;
	    }
   		if(pincode.length != 6)
   		{
   			alert("Please Enter Valid Pin Code Number ");
   			return false;
   		}
   		else
   		{
   		
   			$.ajax(
			{
				
		      url: $("#context_path").val()+"mb/getPinCodeDtls",
		      type: "post",
		      data: "pinCode="+pincode,
		      dataType: "text",
		      success: function(data)
		      {
			 
			
			var  msg = JSON.parse(data);
			
			var stateName = msg.stateName;
			
			var districtName = msg.district;
			
			var postOfficeName = msg.postOffice;
			
			
			if(stateName=='' && districtName=='' && postOfficeName==''){
				alert("Please Enter Valid pinCode Number");
				return false;
			}
			
			$("#stateName").val(stateName);
			
			 var options = '';
		 		options='<option value="">Select<\/option>' ;
		 		
		     	$.each(districtName,function(index,district)
				{	
				   options += '<option value="'+ district +'">' +district + '<\/option>' ;
				});
					$("#district").html(options);
					
					
				options = '';
		 		options='<option value="">Select<\/option>' ;
		 		
		 		$.each(postOfficeName,function(index,postOff)
				{	
				
						options += '<option value="'+ postOff +'">' +postOff + '<\/option>' ;
				});
				
					$("#postOffice").html(options);
		       }
		  });
   			
	}
}


function iticketMsg()
{
		var flag=false;
flag = confirm("Facility of booking of E-Ticket and I-Ticket has been provided for in Defence Travel System. Option of I-Tickets is made available only when confirmed E-Ticket is not available. The option is to be availed only if traveler intends to travel by exploring all facilities available to physical ticket holder issued by PRS. The Railways provides equal opportunity of confirmation to both E-Tickets & I-Tickets up to chart preparation.\n\nInformation regarding Booking\n\n1.	Whenever an I-Ticket is booked from DTS, a Passenger Reservation Slip is generated from DTS System.\n2.	It is mandatory to collect physical copy of I-Ticket from PRS counter by producing Passenger Reservation Slip generated from DTS System along with a valid ID proofin original before boarding a train.Without the printed copy (on Railway Stationary) passenger/s would be treated as without ticket and would be liable to pay the charges as claimed by Railway Staff.\n3.	Do not book waitlisted I-ticket on premium trains, like Rajdhani, Shatabdi, etc in which unreserved coach is not available. As per Indian Railway policies, a passenger cannot board a reserved coach with waitlisted ticket. In such trains passengers may opt for E-Ticket only.\n\nInformation regarding Cancellation\n\n4.	Printout of I-ticket has not been taken: I-ticket booked through DTS can be cancelled from DTS portal before collection of printout from PRS Counter.\n5.	Printout of I-ticket hasbeen taken: following two step process is to be followed:\nI.	In case print out of I-ticket has been collected from PRS counter, the I-ticket can only be cancelled either at nearby PRS counter or online (https://www.operations.irctc.co.in/ctcan/SystemTktCanLogin.jsf) by entering the PNR Number, Train number and other information as required. \nII.	The original journey ticket is required to be surrendered for processing of refund.\nCounter Ticket Cancellation Procedure & Rules are provided on the above link for necessary action. Re-booking in lieu of cancelled ticket will be possible only after the details of surrendered tickets are made available to DTS by Railways which may take up to 04 days.\n6.	In case of Train Cancellation also, by Indian Railways, traveler must submit their printout of I-Ticket to the PRS Counter for refund to be processed.\n7.	Refund against cancelled I-ticket is credited to Account of the DTS. Traveler is not to approach concerned zonal Railways for refund against cancelled I-tickets.\n");
		if(!flag){
			document.getElementById("ticketIticket").checked = false;
			 
		}
	 return flag;
}


function enableCoachId(coachSelectedFlag)
{	
	if(coachSelectedFlag.checked){
		
		document.getElementById("coachId").readOnly=false;
	}else{
		
		document.getElementById("coachId").value='';
		document.getElementById("coachId").readOnly=true;
	}		
	
}



function confirmBooking(formName){	
var result=validateForm();
	if(!result){
		return false;
	}else{
		$("#bookingConfrmForm").submit();
	}
	
}


var prevPsnDetail="";


function validateForm(){

// Birt Preference Validation 
	let selectedCount = $(".berth-preference option:selected");

	let lowerCount = 0;
	let middleCount = 0;
	let upperCount = 0;
	let sideLowerCount = 0;
	let sideUpperCount = 0;
	let windowCount = 0;
	let cabinCount = 0;
	let coupeCount = 0;
	let sideMdCount = 0;
	

	
		for (var i = 1; i <= selectedCount.length; i++) {
			let seat = $("#berthPrefence_" + i + " option:selected").val();

        

			if (seat == 'LB') {
				lowerCount++;
			}
			if (seat == 'MB') {
				middleCount++;
			}
			if (seat == 'UB') {
				upperCount++;
			}
			if (seat == 'SL') {
				sideLowerCount++;
			}
			if (seat == 'SU') {
				sideUpperCount++;
			}
			if (seat == 'WS') {
				windowCount++;
			}
			if (seat == 'CB') {
				cabinCount++;
			}
			if (seat == 'CP') {
				coupeCount++;
			}
			if (seat == 'SM') {
				sideMdCount++;
			}

		}
	
	
	
		if (lowerCount > 2) {
			alert("More than two Lower berth are not allowed");
			return false;
		}

		if (middleCount > 2) {
			alert("More than two Middle berth are not allowed");
			return false;
		}
		if (upperCount > 2) {
			alert("More than two Upper berth are not allowed");
			return false;
		}
		if (sideLowerCount > 2) {
			alert("More than two Side Lower berth are not allowed");
			return false;
		}
		if (sideUpperCount > 2) {
			alert("More than two Side Upper berth are not allowed");
			return false;
		}
		if (windowCount > 2) {
			alert("More than two Window berth are not allowed");
			return false;
		}
		if (cabinCount > 2) {
			alert("More than two Cabin berth are not allowed");
			return false;
		}
		if (coupeCount > 2) {
			alert("More than two Coupe berth are not allowed");
			return false;
		}
		if (sideMdCount > 2) {
			alert("More than two Side Middle berth are not allowed");
			return false;
		}

	
	var brdStnCode=document.getElementById("boardingPointStnCode");
	var resStnCode=document.getElementById("reservationUptoStnCode");
	
		
	var ticketChoice = document.getElementsByName('ticketChoice');
	var choiceValue="";
	for(var i = 0; i < ticketChoice.length; i++){
	    if(ticketChoice[i].checked){
	        choiceValue = ticketChoice[i].value;
	    }
	}
	
	if (choiceValue=="")
	 {
	  	alert("Please Choose Your Ticket Type");
        return false;
	 }
	 
	 if($("#isCoachChoiceOpted").is(":checked")){
		
    var coachid=$("#coachId");
	var coachidvalue=coachid.val();
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
    var len=coachidvalue.length;
  
	if(len==0){
		alert("Please Enter Coach Id While Coach Choice Opted");
		return false;
	}else if(len<2)
	{
		alert("Please enter at least two character as a coach id i.e.B1,S4 etc");
		return false;
	}
	else
	{
		var regularExpression = /[a-zA-Z]/;
		var valid = regularExpression.test(coachidvalue);
		if(!regularExpression.test(coachidvalue.charAt(0)))
		{
			alert("Please fill the alphanumeric value and start with an alphabet");
            return false;
		}
		for (var j = 0; j < len; j++) 
		{
             if (iChars.indexOf(coachidvalue.charAt(j)) !=-1) 
	         {
		         alert("Special charecter are not allowed");
                 return false;
		     }
             if (coachidvalue.charAt(j)==" ")
			 {
			  	alert("Space are not allowed");
                return false;
			 }
		}
		if(len>4){
		  alert("Give only less then 4 character as coach id");
          return false;
		}
	}


	}
	
	if(brdStnCode.value==''){
		alert("Please select the boarding point station");
		return false;
	}
	
	if(resStnCode.value==''){
		alert("Please select the reservation upto point station");
		return false;
	}
	
	/*  ---------------------Tatkal Field Check Start---------------------*/
	var jrnyQuotaId=$("#jrnyQuotaId");
	
	if(jrnyQuotaId.val()=="on")
	{
		
		var totalNoOfPxn=$("#totalNoOfPxn").val();
		var totalSelectedIdCardType=0;
		var totalSelectedPassStr=0;
		var totalSelectedIdCardNo=0;
		//alert("Inside tatkal validation totalNoOfPxn="+totalNoOfPxn);
		
		
		for(var i=1;i<=parseInt(totalNoOfPxn);i++)
		{
			var idCardTypeElenemt=$("#idCardType_"+i);
			if(idCardTypeElenemt!=null)
			{
				var idCardType=$("#idCardType_"+i).val();
				if(idCardType==-1){
				}else
				{
					totalSelectedIdCardType=(parseInt(totalSelectedIdCardType)+1);
					totalSelectedPassStr=totalSelectedPassStr+i+",";
				}
				
			}
			
			var isIdProofValidElenemt=$("#idCardNumber_"+i);
			if(isIdProofValidElenemt!=null)
			{
				var idProofValue=$("#idCardNumber_"+i).val();
				if(idProofValue.length>0)
				{
					totalSelectedIdCardNo=(parseInt(totalSelectedIdCardNo)+1);
				}
			}
		
		}
		
		
		if(totalSelectedIdCardType==0)
		{
			alert("Please Provide Identity Details For At Least One Passenger")
			return false;
		}
		
		
		if(totalSelectedIdCardType!=totalSelectedIdCardNo)
		{
			
			alert("Please Enter Values For Equal Identity Card Type and Identity Card Number")
			return false;
			
				
		}else
		{
			var totalSelectedPassStrArr = new Array();
			totalSelectedPassStrArr=totalSelectedPassStr.split(",");
			
			for(var j=0;j<totalSelectedPassStrArr.length;j++)
			{
					var idCardTypeElenemt=$("#idCardType_"+totalSelectedPassStrArr[j]);
				
					if(idCardTypeElenemt!=null)
					{
						var idCardType=$("#idCardType_"+totalSelectedPassStrArr[j]).val();
						if(idCardType==-1)
						{
							alert("Please Select Valid Id Card Type");
							$("#idCardType_"+totalSelectedPassStrArr[j]).val()="-1";
							$("#idCardType_"+totalSelectedPassStrArr[j]).focus();
							return false;
						}
						
					}
					
					var isIdProofValidElenemt=$("#idCardNumber_"+totalSelectedPassStrArr[j]);
					if(isIdProofValidElenemt!=null)
					{
						var idProofValue=$("#idCardNumber_"+totalSelectedPassStrArr[j]).val();
						var isIdProofValid=chkValidateIdProof(idProofValue);
						if(!isIdProofValid)
						{
							$("#idCardNumber_"+totalSelectedPassStrArr[j]).val()="";
							$("#idCardNumber_"+totalSelectedPassStrArr[j]).focus();
							return false;
						}
					}
				
			}
		}
		
		
		
	}
	/*  ---------------------Tatkal Field Check Start---------------------*/
	
    $("#outmostT").hide();
	$("#innerwait").show();
	
	
	return true;
	
	
return true;
}

// validate address

function validateAddressDtls(){

var regexn = /^[0-9]*$/;
var alphaNumRegEx = /^[a-zA-Z0-9 ]*$/;
var journeyType=$("#journeyAddType").val().trim();
var mobileNo=$("#addMobileNo").val().trim();

  if(journeyType==""){
	alert("Please Select Journey Type");
	return false;
  }

  if(mobileNo==""){
    alert("Mobile No. should not be blank");
    return false;
  }
  
  if(!regexn.test(mobileNo)){
    alert("Mobile No. should be numeric characters");
    return false;
  }
  
  if(mobileNo.length != 10){
  	alert("Please put 10 digit mobile number");
    return false;
  }

if(journeyType=="unitToLeaveStn"){
  
  var houseNo=$("#houseNo").val().trim();
  var street=$("#street").val().trim();
  var village=$("#village").val().trim();
  var pinCode=$("#pinCode").val().trim();
  var district=$("#district").val().trim();
  var postOffice=$("#postOffice").val().trim();
  
  if(houseNo == ""){
    alert("House No. should not be blank");
    return false;
  }
  
   if(street == ""){
    alert("Street should not be blank");
    return false;
  }
  
  if(!alphaNumRegEx.test(street)){
   
   alert("Street should only alphabet and numeric");
    return false; 
  }
  
  if(village == ""){
    alert("Village should not be blank");
    return false;
  }
  
  if(!alphaNumRegEx.test(village)){
   alert("Village should only alphabet and numeric");
    return false; 
  }
  
   if(pinCode == ""){
    alert("pinCode should not be blank");
    return false;
  }
  
  if(!regexn.test(pinCode)){
   	alert("Pin Code should be numeric characters");
   	return false;
  }
  
  if(pinCode.length != 6){
   alert("Please put 6 digit Pin Code Number ");
   return false;
  }
   
  if(district == ""){
    alert("Please Select District");
    return false;
  }
  if(postOffice == ""){
    alert("Please Select post Office");
    return false;
  }
  	
  	
}else{
    
    var unitName=$("#addUnitName").val().trim();
  	var addressType=$("#addressType").val();
  	var unitPin=$("#unitPin").val().trim();
  	
  if(unitName == ""){
    alert("Unit Name should not be blank");
    return false;
  }
  
  if(!alphaNumRegEx.test(unitName)){
   alert("Unit Name should only alphabet and numeric");
    return false; 
  }
  	
  if(addressType == ""){
    alert("Please Select Address Type");
    return false;
  }	
  	
  if(unitPin == ""){
    alert("Unit Pin  should not be blank");
    return false;
  }
  
  if(!regexn.test(unitPin)){
   	alert("Unit Pin should be numeric characters");
   	return false;
  }
  
  if(unitPin.length != 6){
   alert("Please put 6 digit Unit Pin Number ");
   return false;
  }

}

return true;
}
