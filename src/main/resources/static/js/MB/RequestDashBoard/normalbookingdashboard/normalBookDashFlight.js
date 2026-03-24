	$(document).ready(function(){ 
        $("#AITable").tablesorter({sortList: [[7,0]]});
        var reqTimeSlot = $("#reqTimeSlot").val();
         $("#timeBand"+reqTimeSlot).prop("checked", true);
         
		
		filterTimeBands();
	});


function filterTimeBands()
{
	 var objTime= $('input[name="timeBand"]');

     var obj=[]; 
     var objZero =$("#zero").val();
     var objOne =$("#one").val();
     var objOnePlus =$("#one-plus").val();
  
   obj.push(objZero);
   obj.push(objOne);
   obj.push(objOnePlus)


	for(var i=0; i < objTime.length; i++)
    {   
		
        if(objTime[i].checked)
        {
         var  time=objTime[i].value;  
           
         
           for(var j=0; j < 3; j++) {
			  
			 if ($("#"+obj[j]).is(':checked') == true){ 
	   
             $('#AITable .AI_'+obj[j]+''+'.TimeBand_'+time).show(); 
             }
              else {
	   
       $('#AITable .AI_'+obj[j]+''+'.TimeBand_'+time).hide();
       
        }
       }
       
        } 
          else
        {
         	     var  time=objTime[i].value; 
         	    $('#AITable .AI_'+obj[0]+''+'.TimeBand_'+time).hide();
         	    $('#AITable .AI_'+obj[1]+''+'.TimeBand_'+time).hide();
         	    $('#AITable .AI_'+obj[2]+''+'.TimeBand_'+time). hide();
         	
        }          
    }
}


function filterFlightNew()
{
	  var obj=[]; 
  var objTime =  $('input[name="timeBand"]:checked').val(); 
 
  
  var objZero =$("#zero").val();
  var objOne =$("#one").val();
  var objOnePlus =$("#one-plus").val();
  
   obj.push(objZero);
   obj.push(objOne);
   obj.push(objOnePlus);
   
   for(var i=0; i < 3; i++) {
   if ($("#"+obj[i]).is(':checked') == true){ 
	   
   $('#AITable .AI_'+obj[i]+''+'.TimeBand_'+objTime).show(); 
   } else {
	   
     $('#AITable .AI_'+obj[i]+''+'.TimeBand_'+objTime).hide();
  }
  }
  
}



function viewAirFareRule(flightKey,serviceProvider,domInt){

var serpov = serviceProvider;


if(serpov=='ATT'){
	
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
		
		$.ajax({
		type: "POST",
		url:$("#context_path").val()+"mb/getATTFareRule",
		data: "flightKey="+flightKey,
		dataType: "text",
		
		beforeSend: function()
		      {
//		        	imageurltitle='/pcda/docroot/pcda/images/fbloader.gif';
		       	 	box = new LightFace
		       	 	({ 
		        			title: "Loading ...",
							width: 600,
							height: 400,
							content: "Fare Rule information is being populated... Please wait ...",
							buttons: [
										{ 
											title: 'Close', event: function() 
											{ 
												this.destroy(); 
											} 
										}
									]
							
					});
			   		box.open();
  			  },
  			  complete: function()
  			  {
  			  	box.destroy(); 
			  },
		      success: function(successMsg)
		      {
					box1 = new LightFace
					({
						    title: 'View Fare Rule', 
							width: 600,
							height: 400,
							content: successMsg,
							buttons: [
										{ 
											title: 'Close', event: function() 
											{ 
												this.destroy(); 
												lightFaceObject = null;
											} 
										}
									],
					});
			   		box1.open();
			   		lightFaceObject = box1;
              },
              error:function(jqXHR, textStatus) 
              {
				alert( "Request failed: " + textStatus );
			  }
		
		
	 });


}
if(serpov=='BL'){

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

		$.ajax({
		type: "POST",
		url:$("#context_path").val()+"mb/getBLFareRule",
		data: {flightKey : flightKey,domInt :domInt},
		dataType: "text",
		
		beforeSend: function()
		      {
//		        	imageurltitle='/pcda/docroot/pcda/images/fbloader.gif';
		       	 	box = new LightFace
		       	 	({ 
		        			title: "Loading ...",
							width: 600,
							height: 400,
							content: "Fare Rule information is being populated... Please wait ...",
							buttons: [
										{ 
											title: 'Close', event: function() 
											{ 
												this.destroy(); 
											} 
										}
									]
							
					});
			   		box.open();
  			  },
  			  complete: function()
  			  {
  			  	box.destroy(); 
			  },
		      success: function(successMsg)
		      {
					box1 = new LightFace
					({
						    title: 'View Fare Rule', 
							width: 600,
							height: 400,
							content: successMsg,
							buttons: [
										{ 
											title: 'Close', event: function() 
											{ 
												this.destroy(); 
												lightFaceObject = null;
											} 
										}
									],
					});
			   		box1.open();
			   		lightFaceObject = box1;
              },
              error:function(jqXHR, textStatus) 
              {
				alert( "Request failed: " + textStatus );
			  }
		
		
	 });

}

}


function otherRadioOff(idx){
	var checkbox=document.getElementsByName("flightCheckBox");
	
	var checkLen=checkbox.length;
	
	for(var i=0;i<checkLen;i++){
		if(i==idx){
		$("#checkBox_"+idx).checked;
	}
	}
}


function submitBooking(filghtkey,serPov,reqId,domInt,index,intendedTravelFlag){
 
  var bookingType=$("#booking_type").val();
   
if(serPov=='ATT'){
	if($("#checkBox_"+index).is(':checked')){
		 $("#flightKey").val(filghtkey);	
         $("#requestId").val(reqId);	
         $("#domint").val(domInt);
          $("#serviceProvider").val(serPov);	
         
       
         if(intendedTravelFlag == 'Yes')
			{
				var MsgStr = '<b><font size="2">Reason for booking flight within less than 72 hours of intended travel</font></b>';
				document.getElementById('flightReason').innerHTML = MsgStr;
				pop('popDiv');
				return false;
			}else if(bookingType==5){
				 
				 $("#AttBookingForm").attr("action", $("#context_path").val() + 'mb/bulkBkgATTFlight');
				 $("#AttBookingForm").submit();
					
				}else{
         
         $("#AttBookingForm").submit();
         }
	}else{
		alert("Please Select CheckBox");
	}
	
}

if(serPov=='BL'){
	if($("#checkBox_"+index).is(':checked')){
		 $("#blFlightKey").val(filghtkey);	
         $("#blRequestId").val(reqId);	
         $("#blDomint").val(domInt);
          $("#serviceProvider").val(serPov);	
        
       
         if(intendedTravelFlag == 'Yes')
			{
				var MsgStr = '<b><font size="2">Reason for booking flight within less than 72 hours of intended travel</font></b>';
				document.getElementById('flightReason').innerHTML = MsgStr;
				pop('popDiv');
				return false;
			}else if(bookingType==5){
				$("#BLBookingForm").attr("action", $("#context_path").val() + 'mb/bulkBkgBLFLlight');
				 $("#BLBookingForm").submit();
			}else{	
         
         $("#BLBookingForm").submit();
         }
	}else{
		alert("Please Select CheckBox");
	}
}
	
}


function pop(div) {
				$("#"+div).show();
			}


	function hide(div) {
				$("#"+div).hide();
			}
function proceedBooking(event)
{
	
       var serviceProvider= $("#serviceProvider").val();
       var bookingType=$("#booking_type").val();
		
		$("#isValidatedCase").val("Yes");
		 $("#blIsValidatedCase").val("Yes");
		 
		if($("#intended").val().trim() == ''){
			alert("Please provide reason for booking flight within less than 72 hours of intended travel");
			event.preventDefault();
		}
		
		$("#validatedReason").val($("#intended").val());
			$("#blValidatedReason").val($("#intended").val());
		hide('popDiv');
	
				
				   $("#screen-freeze").show();
				
					if(serviceProvider == 'BL'){
					if(bookingType==5){
						
				 $("#BLBookingForm").attr("action", $("#context_path").val() + 'mb/bulkBkgBLFLlight');
				 $("#BLBookingForm").submit();
			    }else{
						
					 $("#BLBookingForm").submit();
					 }
					}else if(serviceProvider == 'ATT'){
						
					if(bookingType==5){
						
				 $("#BLBookingForm").attr("action", $("#context_path").val() + 'mb/bulkBkgBLFLlight');
				 $("#BLBookingForm").submit();
			    }else{
						
					 $("#BLBookingForm").submit();
					 }
					}else{
						$("#screen-freeze").hide();
						alert("Service Provider not available for this flight.");
						return false;
					}
			  	
		  		
	}


