var lightFaceObject = null;
var lightFaceObjectOld = null;
var lightFaceReportObject = null;
function showRail()
{
	//alert("Inside show Rail");
	document.getElementById("railTab").style.display="block";
	document.getElementById("railTab").className="slectdomhome";
	document.getElementById("airTab").style.display="none";
	document.createRequestForm.travelMode.value="0";
	
	document.getElementById("railRequestXform").style.display="block";
	document.getElementById("airRequestXform").style.display="none";
	document.getElementById("homepagetabs").style.display="block";
	document.getElementById("homepagetabs1").style.display="none";
	 
	
	document.getElementById("mixedRequestXform").style.display="none";
	document.getElementById("mixedPreferenceDiv").style.display="none";
	setSourceDestinationByTrRule();
}

function showAir()
{
	//alert("Inside show Air");
	var airAccountOffice = $("#airAccountOffice").val();
	if(airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0){
	   alert("Pay account office for air travel is not assigned so air travel request can not be created.")
	   $("#airAccountOffice").focus();
    }
    
	document.getElementById("railTab").style.display="none";
	document.getElementById("airTab").className="slectinthome";
	document.getElementById("airTab").style.display="block";
	document.createRequestForm.travelMode.value="1";
	document.getElementById("railRequestXform").style.display="none";
	document.getElementById("airRequestXform").style.display="block";
	document.getElementById("homepagetabs").style.display="block";
	document.getElementById("homepagetabs1").style.display="none";
	
	document.getElementById("mixedRequestXform").style.display="none";
	document.getElementById("mixedPreferenceDiv").style.display="none";
	
	setSourceDestinationByTrRule();
	
}

function showMixed()
{
//	alert("Inside show Mixed");
	if(confirm("Rescheduling is not possible in Mixed Mode as of now, without cancellation of the whole journey for one side. Please make sure the date of journey is confirmed before booking") == true)
	{ 
		document.createRequestForm.travelMode.value="2";
	    document.getElementById("railRequestXform").style.display="none";
		document.getElementById("airRequestXform").style.display="none";
		document.getElementById("mixedPreferenceDiv").style.display="block";
		document.getElementById("mixedRequestXform").style.display="none";
		$("#searchDiv").hide();	
	}
	else
	{
		return false;
	}	
}


function hideShowTabGroup(value)
{
	if(value=="0")
	{
		document.getElementById("homepagetabs").style.display="block";
		document.getElementById("homepagetabs1").style.display="none";
		document.getElementById("railRequestXform").style.display="block";
		document.getElementById("airRequestXform").style.display="none";
	}
	else
	{
		document.getElementById("homepagetabs1").style.display="block";
		document.getElementById("homepagetabs").style.display="none";
		document.getElementById("railRequestXform").style.display="none";
		document.getElementById("airRequestXform").style.display="block";
	}
		
}

function showHideReturnDate()
{
	var airTypeObj=document.getElementById("airJourneyType");
	//alert("airTypeObj-"+airTypeObj+"-"+airTypeObj.checked);
	if(airTypeObj.checked)
	{
		$("#returnDateRow").show();
		document.getElementById('tripType').value=1;
	}else{
		$("#returnDateRow").hide();
		document.getElementById('tripType').value=0;
	}	
}

function showHideReturnDate(obj)
{
	//var airTypeObj=document.getElementById("airJourneyType");
	//alert("airTypeObj-"+airTypeObj+"-"+airTypeObj.checked);

	if(obj != null && obj.value == 0){
		$("#returnDateRow").hide();
		document.getElementById('tripType').value = 0;
	}else if(obj != null && obj.value == 1){
		$("#returnDateRow").show();
		document.getElementById('tripType').value = 1;
	}else if(obj != null && obj.value == 2){
		$("#returnDateRow").show();
		document.getElementById('tripType').value = 2;
	}	
}
  
function showAirReturnDate(tripValue)
{
	
	$("#returnDateRow").show();
	document.getElementById('tripWay').value=tripValue;
	document.getElementById('tripType').value=1;

}
function hideAirReturnDate(tripValue)
{
	$("#returnDateRow").hide();
	//document.getElementById('returnDateRow').style.display="none";
    document.getElementById('tripWay').value=tripValue;
    document.getElementById('tripType').value=0;
}

function clickRailTab()
{
	document.getElementById("railTab").className="slectdomhome";
	document.getElementById("airTab").className="inthome";
	document.getElementById("railRequestXform").style.display="block";
	document.getElementById("airRequestXform").style.display="none";
}

function clickAirTab()
{
	document.getElementById("airTab").className="slectinthome";
	document.getElementById("railTab").className="domhome";
	document.getElementById("railRequestXform").style.display="none";
	document.getElementById("airRequestXform").style.display="block";
}


function clickRailTab1()
{
	document.getElementById("railTab1").className="slectdomhome";
	document.getElementById("airTab1").className="inthome";
	document.getElementById("railRequestXform").style.display="block";
	document.getElementById("airRequestXform").style.display="none";
}

function clickAirTab1()
{
	document.getElementById("airTab1").className="slectinthome";
	document.getElementById("railTab1").className="domhome";
	document.getElementById("railRequestXform").style.display="none";
	document.getElementById("airRequestXform").style.display="block";
}


function getEntitledAndOtherData()
{

		var mealOptions = "";
		var seatOptions = "";
		var airEntitledClassOption = "";
		var preferAirlineOption = "";
		var idd="All";
		var namee="All Flight";
		preferAirlineOption += '<option value="' + idd + '">' +namee + '<\/option>';

		$.ajax({
		      url: "/pcda/af/pcda/air/AirRequestAjax.do",
		      type: "get",
		      data: "requestCondition=AirRequestInfo",
		      dataType: "text",
		      success: function(successMsg)
		      {
    			  	//msg=parseXMLData(successMsg);  
    			  	msg=parseXML(successMsg);  
					
					$(msg).find('CABIN_CLASS').each(function(){
						var id = $(this).find('INT_CODE').text();
						var name = $(this).find('VALUE').text();
						airEntitledClassOption += '<option value="' + id + '">' +name + '<\/option>';
					});
					
					$(msg).find('AIRLINE').each(function(){
						var id = $(this).find('FLIGHT_CODE').text();
						var name = $(this).find('FLIGHT_NAME').text();
						preferAirlineOption += '<option value="' + id + '">' +name + '<\/option>';
					});
				
					//$("#airEntitledClass").html(airEntitledClassOption);
					//$("#preferAirline").html(preferAirlineOption);

            	}
            });
}

function parseXMLData( xml ) {
	if( window.ActiveXObject && window.GetObject ) {
	var dom = new ActiveXObject( 'Microsoft.XMLDOM' );
	dom.loadXML( xml );
	return dom;
	}
	if( window.DOMParser )
	throw new Error( 'No XML parser available' );
}

function airSearch()
{
 	airListPopup();
}

function getTravellerCount()
{
	var dobStr = document.getElementById("dob").value;
    var totalTravellerArr = dobStr.split(",");

    var authorityNo=document.getElementById("authorityNo").value;
    var authorityDate=document.getElementById("authorityDate").value;    
    var airTypeObj=document.getElementById("airJourneyType");
	
	var onwordJourneyDate=document.getElementById("onwordJourneyDate").value;
	var	orgStationDD=document.getElementById("orgStationDD").value;
	var	destStationDD=document.getElementById("destStationDD").value;
	var origin=document.getElementById("origin").value;
	var destination=document.getElementById("destination").value;
	var airEntitledClass=document.getElementById("airEntitledClass").value;
	var returnJourneyDate=document.getElementById("returnJourneyDate").value;
	
	
	if(authorityNo=="")
	{
		alert("Please Enter Authority Number");
		document.getElementById("authorityNo").focus();
		return false;
	}
	if(authorityDate=="dd/mm/yyyy"){
		alert("Please Select Authority Date");
		document.getElementById("authorityDate").focus();
		return false;
	}
	if(onwordJourneyDate==""){
		alert("Please Select Journey Date");
		document.getElementById("onwordJourneyDate").focus();
		return false;
	}
	if(airEntitledClass=="-1" || airEntitledClass=="" ){
		alert("Please Select Entitled Class");
		document.getElementById("airEntitledClass").focus();
		return false;
	}
	/*if(airTypeObj.checked)
	{
		var returnJourneyDate=document.getElementById("returnJourneyDate").value;
			if(returnJourneyDate==""){
				alert("Please Select Return Journey Date");
				document.getElementById("returnJourneyDate").focus();
				return false;
			}		
	}*/
	
	var tripNodeList = document.getElementsByName("airJourneyType");
	for(i = 0; i < tripNodeList.length; i++)
	{
		var temp = tripNodeList.item(i);
		if(temp.checked){
			if(Number(temp.value) > 0){
				if(returnJourneyDate==""){
	 					alert("Please Select Return Journey Date");
						document.getElementById("returnJourneyDate").focus();
						return false;
				}
	 		} 
		}
	 }
	
	
	/*
	if(orgStationDD==""){
		alert("Please Select Origin Station");
		document.getElementById("orgStationDD").focus();
		return false;
	}
	if(destStationDD==""){
		alert("Please Select Destination Station");
		document.getElementById("destStationDD").focus();
		return false;
	}
	*/
	
	if(origin==""){
			alert("Please Enter Origin Station Airport");
			document.getElementById("origin").focus();
			return false;
	}
	if(destination==""){
		alert("Please Enter Destination Station Airport");
		document.getElementById("destination").focus();
		return false;
	}
	
	
	

	var tempCount = 0;
	var dobArr = new Array();
	for(var i=0; i<totalTravellerArr.length; i++)
	{
		if($('#removeFrmList'+i).is(":checked"))
		{
			//alert( $('#dobCheck'+i).val());
			dobArr[tempCount] = $('#dobCheck'+i).val();
			tempCount++;
		}
	}
    
    // alert("dateOfBirthArray " + dobArr + " " + dobArr.length);
    //alert(" first iteration = " + dobArr);
    //alert("Inside getTravellerCount:relationStr:"+ relationStr+"|dobStr-"+dobStr);
    //alert("Inside getTravellerCount:dobArr.length:"+ dobArr.length+"|relArr.lenght-"+relArr.length);
   	
   
	if ($("#isAttendentBooking").val() == 'Yes') 
	{
		  var attendentCount = $("#finalAttendentCount").val();
          for (i = 1; i <= attendentCount; i++) 
          {
     			 if (document.getElementById("attDob" + i).value != '') 
     			 {
     			 	dobArr[tempCount]=$('#attDob'+i).val();
     			 	tempCount++;
                 }        	
          }
	}
	
	if ($("#isPartyBooking").val() == 'Yes') 
    {

    	  var partyCount = $("#partyMemberCount").val();
    	  for (i = 1; i <= partyCount; i++) 
          {
          		dobArr[tempCount]=$('#partyDepDOB'+i).val();
     			tempCount++;
          }
          
    }
    
	
	if(tempCount>0)
	{
		return calculateAge(dobArr);
	}
	else
	{
		alert("Please check atleast one travller");
		return false;
	}
}
  
  
  
function airListPopupAfterEdit()
{
		$("#reqMode").val("");
		$("#reasonCod").val("");
		var adultCount = $("#adultCount").val();
		var childCount = $("#childCount").val();
		var infantCount = $("#infantCount").val();
		
		var orgStr=$('#origin').val();
		var destStr=$('#destination').val();
		
		var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
		var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
		
		var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
		var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
		
		if(origin==destination){
			alert("Origin And Destination Airport Can Not Be Same.");
			return false;
		}
		//alert("origin:"+origin+"||originName-"+originName+"||journeyType-"+journeyType);
		//alert("destination:"+destination+"||destinationName-"+destinationName);
		
		var journeyDate=$('#onwordJourneyDate').val();
	 	var returnJourneyDate=$('#returnJourneyDate').val();
	 	var entitledClass=$('#airEntitledClass').val();
	 	//var preferAirline=$('#preferAirline').val();
	 	
	 	
	 	 var message = "";
	 	
	 	
		var journeyDate=$('#onwordJourneyDate').val();
	 	var returnJourneyDate=$('#returnJourneyDate').val();
	 	var entitledClass=$('#airEntitledClass').val();
	 	var preferAirline='All';
	 	var tripWayValue=$('#tripWay').val();
	 	
	 	var travelTypeId =$('#TravelTypeDD').val();
	 	var serviceType=$('#serviceType').val();
	 	var journeyType=$('#journeyType').val();//getJourneyType();
	 	var TRRule=$("#TRRule").val();
	 	
	 	if(tripWayValue=="0"){
	 	tripWayValue="OneWay";
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 		
	 	}else if(tripWayValue=="1"){
	 		tripWayValue = "RoundTrip";
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	
	 	}else if(tripWayValue=="2"){
	 		tripWayValue = "specialRoundtrip"
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	
	 	}
	 	
	 	//alert("tripWayValue >>> "+tripWayValue);
	 	//alert("originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount);
	 	var xy = getScrollXY();
		var x = xy[0];
		var y = xy[1];
 		$.ajax({
			      url: '/pcda/af/page/pcda/air/AirSrchRsltAjax.do',
			      type: "get",
			      /*data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&searchFrom=Edit",*/
			      data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&travelTypeId="+travelTypeId+"&journeyType="+journeyType+"&serviceType="+serviceType+"&searchFrom=Edit&TRRule="+TRRule,
			      dataType: "text",
			      
			      beforeSend: function()
			      {
			        	imageurltitle='/pcda/images/fbloader.gif';
			       	 	box = new LightFace
			       	 	({ 
			        			title: "<img src='"+imageurltitle+"'> Loading ...",
								width: 500,
								height: 200,
								content: message,
								buttons: [
											{ 
												title: 'Close', event: function() 
												{ 
													lightFaceObjectOld = null;
													this.destroy(); 
												} 
											}
										]
						});
						lightFaceObjectOld = box;
				   		box.open();
	  			  },
	  			  complete: function()
	  			  {
	  			  	box.destroy(); 
				  },
			      success: function(successMsg)
			      {
			      		lightFaceObjectOld.destroy();
						box1 = new LightFace
						({
							    title: 'Flight List', 
								width: 1000,
								height: 500,
								content: successMsg,
								buttons: [
											{ 
												title: 'Close', event: function() 
												{ 
													this.destroy(); 
													lightFaceObject = null;
													$('#parentBody').off('wheel.modal mousewheel.modal');
													$('#parentBody').css({'overflow-y':'visible'});
													$(window).scrollTop(y);
												} 
											}
										],
						});
				   		box1.open();
				   		lightFaceObject = box1;
				   		$(window).scrollTop(430);
				   		
				   		if(tripWayValue=='OneWay')
				   		{
				   			 showTabContent();
				   			//onloadOneWayAirSrchRsltPage();
				   		}
						else
						{
							 showTabContent();
							//onloadRoundTripAirSrchRsltPage();
						}
						$('#childBody').on('wheel.modal mousewheel.modal', function () {return false;});
						$('#parentBody').css({'overflow-y':'hidden'});
						$('#AITable').tablesorter(); 						
						$('#OtherTable').tablesorter(); 
						
						if(tripWayValue=='RoundTrip'){
						$('#AIReturnTable').tablesorter();						
						$('#OtherReturnTable').tablesorter();
						
						var fare = 0.00;
						var cells = $("#ai-onward-0 > td").clone(true);
						copiedTo = $("#onward-ribbon tr");						
						$(cells).each(function(index){							
							
							if(index == 0){
						    	var tempKey = $(this).find("input[type=radio]").val();
						    	$("#onwardKey").text(tempKey);
						    
						    }							
							if(index > 0 && index < 5){
								$(copiedTo).append($(this));
							}
							
							if(index == 5){
							//alert(fare);
								fare = Number($(this).text());
								$("#onwardPrice").text(fare);
								//alert("after = " + fare);
							}						
						});
						
						cells = $("#ai-return-0 > td").clone(true);
						copiedTo = $("#return-ribbon tr");						
						$(cells).each(function(index){
						   if(index == 0){
						    	var tempKey = $(this).find("input[type=radio]").val();
						    	$("#returnKey").text(tempKey);
						    
						    }							
							if(index > 0 && index < 5){
								$(copiedTo).append($(this));
							}
							if(index == 5){
							//alert("return before = " + fare);
								var rtFare = Number($(this).text());
								fare = fare + rtFare;
								$("#returnPrice").text(rtFare);
								//alert("return after = " + fare);
							}						
						});
						
						$("#fare").text("Rs. " + fare);						
						
						}
	              }
	        });
	
}



function showTabContent()
{ 
         //alert("inside function");

		 var isNonAirValue = document.getElementById('isNonAirIndianSector').value;
		 
		 var isAirIndiaAvailable = document.getElementById('isAIFlightsAvailable').value;
		
			//alert("value of isNonAir "+isNonAirValue);
			
			//isNonAirValue = 'Yes';
			
			var confirmVal = false;
			
            //Active tab selection after page load       
            $('#tabs').each(function(){
           
            // For each set of tabs, we want to keep track of
            // which tab is active and it's associated content
            var $active, $content, $links = $(this).find('a');
           
            // If the location.hash matches one of the links, use that as the active tab.
            // If no match is found, use the first link as the initial active tab.
			
			if(isNonAirValue == 'Yes'){
				confirmVal = confirm("This is non-Operational Air India Sector please check OA flights");
			}else{
				
				if(isAirIndiaAvailable == 'Yes'){
					confirmVal = true;
					alert("Please select AI as Applicable.");
				}else if(isAirIndiaAvailable == 'No'){
					confirmVal = confirm("Flight not available in Air India on this date, please check for OA flights OR change date of journey.");
				}else{
					confirmVal = true;
					alert("Please select AI as Applicable.");
				}
			}
			
			
			if(isNonAirValue == 'Yes')
			{
				if(confirmVal)
				{
					$active = $($links.filter('[href="'+location.hash+'"]')[1]
							  || $links[1]);
					
					$active.parent().addClass('active');
				   
					$content = $($active.attr('href'));
					$content.show();
				}else{
					lightFaceObject.destroy();
				}
			}
			else{
				 
				if(isAirIndiaAvailable == 'No')
				{
					if(confirmVal)
					{ 
					$active = $($links.filter('[href="'+location.hash+'"]')[1]
							  || $links[1]);
					
					$active.parent().addClass('active');
				   
					$content = $($active.attr('href'));
					$content.show();
					}else{
						lightFaceObject.destroy();
					}
				}else{
					if(confirmVal)
					{ 
					$active = $($links.filter('[href="'+location.hash+'"]')[0]
							  || $links[0]);
					
					$active.parent().addClass('active');
				   
					$content = $($active.attr('href'));
					$content.show();
					}
				}
			}
        });
       
       if(confirmVal)
		{
         $("#tabs li").click(function() {

			 // First remove class "active" from currently active tab
             $("#tabs li").removeClass('active');
        
             // Now add class "active" to the selected/clicked tab
             $(this).addClass("active");
                
             // Hide all tab content
             $(".tab_content").hide();

             // Here we get the href value of the selected tab
             var selected_tab = $(this).find("a").attr("href");      

             var starting = selected_tab.indexOf("#");
             var sub = selected_tab.substring(starting);
               
             //write active tab into cookie
                
             //$(sub).show();
                 $(sub).fadeIn();
             // At the end, we add return false so that the click on the
            // link is not executed
             return false;
          });
		}
        }
        

function airListPopup()
{
	//alert("inside airListPopup");
	
	$("#selectedFlightKey").val("");
	var result = getTravellerCount();
	//alert("Inside airListPopup:result:"+result);
	if(result!=false)
	{
		var adultCount = result[0];
		var childCount = result[1];
		var infantCount = result[2];
		var orgStr=$('#origin').val();
		var destStr=$('#destination').val();
		
		var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
		var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
		
		var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
		var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
		
		if(origin==destination){
			alert("Origin And Destination Airport Can Not Be Same.");
			return false;
		}
		
		//alert("origin:"+origin+"||originName-"+originName+"||journeyType-"+journeyType);
		//alert("destination:"+destination+"||destinationName-"+destinationName);
		var travelTypeId =$('#TravelTypeDD').val();
		var journeyDate=$('#onwordJourneyDate').val();
	 	var returnJourneyDate=$('#returnJourneyDate').val();
	 	var entitledClass=$('#airEntitledClass').val();
	 	//var preferAirline=$('#preferAirline').val();
	 	var preferAirline='All';
	 	var tripWayValue=$('#tripWay').val();
	 	var jTypeObj=document.getElementById("airJourneyType");
	 	//alert("hello === " + tripWayValue + " " + jTypeObj.checked);
	 	var serviceType=$('#serviceType').val();
	 	var journeyType=getJourneyType();
	 	var TRRule=$("#TRRule").val();
	 	//alert("travelTypeId:"+travelTypeId+"||serviceType-"+serviceType+"||journeyType-"+journeyType);
	 	
	 	var message = "";
	 	var tripNodeList = document.getElementsByName("airJourneyType");
	 	var airJourneyType = "";
	 	for(i = 0; i < tripNodeList.length; i++){
	 		var temp = tripNodeList.item(i);
	 		if(temp.checked){
	 			airJourneyType = temp.value;
	 		}
	 	}
	 	
	 	//alert("airJourneyType = " + airJourneyType);
	 	if(airJourneyType == 0){
	 		tripWayValue="OneWay";
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 		
	 	}else if(airJourneyType == 1){
	 		tripWayValue = "RoundTrip"
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	
	 	}else if(airJourneyType == 2){
	 		tripWayValue = "specialRoundtrip"
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	
	 	}
	 
	 	/*if(jTypeObj.checked){
	 		tripWayValue = "RoundTrip"
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and return on " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	}else{
	 	tripWayValue="OneWay";
	 	message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 	}*/
	 	//alert("originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount);
	 	var xy = getScrollXY();
		var x = xy[0];
		var y = xy[1];
				
		
		
		
		
 		$.ajax({
			      url: '/pcda/af/page/pcda/air/AirSrchRsltAjax.do',
			      type: "get",
			      data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&travelTypeId="+travelTypeId+"&journeyType="+journeyType+"&serviceType="+serviceType+"&searchFrom=Create&TRRule="+TRRule,
			      dataType: "text",
			      
			      beforeSend: function()
			      {
			        	imageurltitle='/pcda/images/fbloader.gif';
			       	 	box = new LightFace
			       	 	({ 
			        			title: "<img src='"+imageurltitle+"'> Loading ...",
								width: 500,
								height: 200,
								content: message,
								buttons: [
											{ 
												title: 'Close', event: function() 
												{ 
													lightFaceObjectOld = null;
													this.destroy(); 
												} 
											}
										]
						});
						lightFaceObjectOld = box;
				   		box.open();
	  			  },
	  			  complete: function()
	  			  {
	  			  	box.destroy(); 
				  },
			      success: function(successMsg)
			      {
			      
			      		lightFaceObjectOld.destroy();
						box1 = new LightFace
						({
							    title: 'Flight List', 
								width: 1000,
								height: 500,
								content: successMsg,
								buttons: [
											{ 
												title: 'Close', event: function() 
												{ 
													this.destroy(); 
													lightFaceObject = null;
													$('#parentBody').off('wheel.modal mousewheel.modal');
													$('#parentBody').css({'overflow-y':'visible'});
													$(window).scrollTop(y);
												} 
											}
										],
						});
				   		box1.open();
				   		lightFaceObject = box1;
				   		$(window).scrollTop(430);
				   		
				   		if(tripWayValue=='OneWay')
				   		{
				   		 	showTabContent();
				   			//onloadOneWayAirSrchRsltPage();
							
				   		}
						else
						{
							 showTabContent();
							//onloadRoundTripAirSrchRsltPage();
						}
						$('#childBody').on('wheel.modal mousewheel.modal', function () {return false;});
						$('#parentBody').css({'overflow-y':'hidden'});
						$('#AITable').tablesorter(); 
						$('#OtherTable').tablesorter(); 
							
						if(tripWayValue=='RoundTrip'){
						$('#AIReturnTable').tablesorter();	
						$('#OtherReturnTable').tablesorter();
						
						var fare = 0.00;
						
						
						var isNonAirVal = document.getElementById('isNonAirIndianSector').value;
						var isAirIndiaAvailableVal = document.getElementById('isAIFlightsAvailable').value;
						
						
						if(isNonAirVal == 'No' || isAirIndiaAvailableVal == 'Yes')
						{
							if(isAirIndiaAvailableVal == 'Yes')
							{
								var cells = $("#ai-onward-0 > td").clone(true);
								copiedTo = $("#onward-ribbon tr");						
								$(cells).each(function(index){							
									
									if(index == 0){
								    	var tempKey = $(this).find("input[type=radio]").val();
								    	$("#onwardKey").text(tempKey);
								    
								    }							
									if(index > 0 && index < 5){
										$(copiedTo).append($(this));
									}
									
									if(index == 5){
									//alert(fare);
										fare = Number($(this).text());
										$("#onwardPrice").text(fare);
										//alert("after = " + fare);
									}						
								});
								
								cells = $("#ai-return-0 > td").clone(true);
								copiedTo = $("#return-ribbon tr");						
								$(cells).each(function(index){
								   if(index == 0){
								    	var tempKey = $(this).find("input[type=radio]").val();
								    	$("#returnKey").text(tempKey);
								    
								    }							
									if(index > 0 && index < 5){
										$(copiedTo).append($(this));
									}
									if(index == 5){
									//alert("return before = " + fare);
										var rtFare = Number($(this).text());
										fare = fare + rtFare;
										$("#returnPrice").text(rtFare);
										//alert("return after = " + fare);
									}						
								});
								
								$("#fare").text("Rs. " + fare);
							}else{
								
							var cells = $("#other-onward-0 > td").clone(true);
							copiedTo = $("#onward-ribbon tr");						
							$(cells).each(function(index){							
								
								if(index == 0){
							    	var tempKey = $(this).find("input[type=radio]").val();
							    	$("#onwardKey").text(tempKey);
							    
							    }							
								if(index > 0 && index < 5){
									$(copiedTo).append($(this));
								}
								
								if(index == 5){
								//alert(fare);
									fare = Number($(this).text());
									$("#onwardPrice").text(fare);
									//alert("after = " + fare);
								}						
							});
							
							cells = $("#other-return-0 > td").clone(true);
							copiedTo = $("#return-ribbon tr");						
							$(cells).each(function(index){
							   if(index == 0){
							    	var tempKey = $(this).find("input[type=radio]").val();
							    	$("#returnKey").text(tempKey);
							    
							    }							
								if(index > 0 && index < 5){
									$(copiedTo).append($(this));
								}
								if(index == 5){
								//alert("return before = " + fare);
									var rtFare = Number($(this).text());
									fare = fare + rtFare;
									$("#returnPrice").text(rtFare);
									//alert("return after = " + fare);
								}						
							});
							
							$("#fare").text("Rs. " + fare);
								
							}
							
						} //Vishal End of if(isNonAirVal == 'No' || isAirIndiaAvailableVal == 'Yes')
						else{
							var cells = $("#other-onward-0 > td").clone(true);
							copiedTo = $("#onward-ribbon tr");						
							$(cells).each(function(index){							
								
								if(index == 0){
							    	var tempKey = $(this).find("input[type=radio]").val();
							    	$("#onwardKey").text(tempKey);
							    
							    }							
								if(index > 0 && index < 5){
									$(copiedTo).append($(this));
								}
								
								if(index == 5){
								//alert(fare);
									fare = Number($(this).text());
									$("#onwardPrice").text(fare);
									//alert("after = " + fare);
								}						
							});
							
							cells = $("#other-return-0 > td").clone(true);
							copiedTo = $("#return-ribbon tr");						
							$(cells).each(function(index){
							   if(index == 0){
							    	var tempKey = $(this).find("input[type=radio]").val();
							    	$("#returnKey").text(tempKey);
							    
							    }							
								if(index > 0 && index < 5){
									$(copiedTo).append($(this));
								}
								if(index == 5){
								//alert("return before = " + fare);
									var rtFare = Number($(this).text());
									fare = fare + rtFare;
									$("#returnPrice").text(rtFare);
									//alert("return after = " + fare);
								}						
							});
							
							$("#fare").text("Rs. " + fare);
						}
						
	              }
	             
	              }
	             
	        });
	}
	
}


function airListPopupOLD()
{
	var result = getTravellerCount();
	//alert("Inside airListPopupOLD:result:"+result);
	//alert("Org:"+$('#origin').val()+"||Dest-"+$('#destination').val());
	//alert("onwordJourneyDate:"+$('#onwordJourneyDate').val()+"||airEntitledClass-"+$('#airEntitledClass').val());
	//alert("preferAirline:"+$('#preferAirline').val());
	
	if(result!="false")
	{
		var adultCount = result[0];
		var childCount = result[1];
		var infantCount = result[2];
	
		var orgStr=$('#origin').val();
		var destStr=$('#destination').val();
		
		var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
		var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
		
		var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
		var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
		
		var journeyType=getJourneyType();
		

	 	var journeyDate=$('#onwordJourneyDate').val();
	 	var entitledClass=$('#airEntitledClaorgStrss').val();
	 	var preferAirline=$('#preferAirline').val();
	 	var TRRule=$("#TRRule").val();

	 	//var returnJourneyDate=$('#returnJourneyDate').val();
	 	var tripWayValue=$('#tripWay').val();
	 	var returnJourneyDate="";
	 	var tripWayValue="OneWay";
	 	
	 	var xy = getScrollXY();
		var x = xy[0];
		var y = xy[1];
 		$.ajax({
			      url: '/pcda/af/page/pcda/air/AirSrchRsltAjax.do',
			      type: "get",
			      data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&TRRule="+TRRule,
			      dataType: "text",
			      
			      beforeSend: function()
			      {
			        	imageurltitle='/pcda/images/fbloader.gif';
			      },
	  			  complete: function()
	  			  {
	  			  	//box2.destroy();   
				  },
			      success: function(successMsg)
			      {
			      		
			      		//lightFaceObjectOld.destroy();
						box1 = new LightFace
						({
							    title: 'Flight List', 
								width: 1000,
								height: 500,
								content: successMsg,
								buttons: [
											{ 
												title: 'Close', event: function() 
												{ 
													box1.destroy(); 
													lightFaceObject = null;
													$('#parentBody').off('wheel.modal mousewheel.modal');
													$('#parentBody').css({'overflow-y':'visible'});
													$(window).scrollTop(y);
												} 
											}
										],
						});
				   		box1.open();
				   		lightFaceObject = box1;
				   		$(window).scrollTop(430);
				   		
				   		if(tripWayValue=='OneWay')
				   		{
				   			onloadOneWayAirSrchRsltPage();
							
				   		}
						else
						{
							onloadRoundTripAirSrchRsltPage();
						}
						$('#childBody').on('wheel.modal mousewheel.modal', function () {return false;});
						$('#parentBody').css({'overflow-y':'hidden'});
						
	              }
	        });
	}
	
}

function onloadOneWayAirSrchRsltPage()
{
	var firstIndex = $("#firstIndex").val();
	var flightKey = $("#firstKey").val();
	var flightCarrier = $("#firstCarrier").val();
	//alert("firstIndex-"+firstIndex+":flightKey-"+flightKey+":flightCarrier-"+flightCarrier);
	getSelectedOneWayFlightDetail(firstIndex,flightKey,flightCarrier);
	
}

function getSelectedOneWayFlightDetail(indx,flightKey,flightCarrier)
{
	if(flightKey!="")
	{
		$("#onwordFlightKey").val(flightKey);
		$("#carrierCode").val(flightCarrier);
	}
	var carrierList = document.getElementsByName("carrierName"+indx);
	var carrierImgList = document.getElementsByName("carrierImg"+indx);
	var originList = document.getElementsByName('origin'+indx);
	var deptTimeList = document.getElementsByName('deptTime'+indx);
	var destinationList = document.getElementsByName('destination'+indx);
	var arrivalTimeList = document.getElementsByName('arrivalTime'+indx);
	var totalNet = document.getElementsByName('totalNet'+indx);
	var carrierClassList = document.getElementsByName('carrierClass'+indx);
	var durationList = document.getElementsByName('duration'+indx);
	//alert("carrierList-"+carrierList.length);
	var returnInfo = "";
	returnInfo+='<table style="position: fixed;" width="71%" border="0">';
	returnInfo+='<tr bgcolor="#D8EAF8" style="height: 30px;">';
	returnInfo+='<td width="60%">';
	returnInfo+='<table border="0">';
	for (i = 0; i < carrierList.length; i++)
	{
	//alert("carrierList value-"+carrierList[i].value);
		returnInfo+='<tr style="height: 30px; width: 100%">';
		returnInfo+='<td align="left" width="9%"><div class="'+carrierImgList[i].value+'"></div></td>';
		returnInfo+='<td align="left" width="8%">'+carrierList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+originList[i].value+'&#160;'+deptTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+destinationList[i].value+'&#160;'+arrivalTimeList[i].value+'</td>';
		
		returnInfo+='</tr>';
	}
	returnInfo+='</table>';
	returnInfo+='</td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%">'+durationList[0].value+'</td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%">'+totalNet[0].value+'</td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%"><input type="button" class="butn" onClick="getSelectedRowValueOneWay();" name="rowValue" value="Save"/> </td>';
	returnInfo+='</tr>';
	returnInfo+='</table>';
	$('#selectedFlightDiv').html("");
	$('#selectedFlightDiv').html(returnInfo);
}

function getSelectedOneWayFlightDetailOther(indx,flightKey,flightCarrier)
{
	if(flightKey!="")
	{
		$("#onwordFlightKey").val(flightKey);
		$("#carrierCode").val(flightCarrier);
	}
	var carrierList = document.getElementsByName("othercarrierName"+indx);
	var carrierImgList = document.getElementsByName("othercarrierImg"+indx);
	var originList = document.getElementsByName('otherorigin'+indx);
	var deptTimeList = document.getElementsByName('otherdeptTime'+indx);
	var destinationList = document.getElementsByName('otherdestination'+indx);
	var arrivalTimeList = document.getElementsByName('otherarrivalTime'+indx);
	var totalNet = document.getElementsByName('othertotalNet'+indx);
	var carrierClassList = document.getElementsByName('othercarrierClass'+indx);
	var durationList = document.getElementsByName('otherduration'+indx);
	var returnInfo = "";
	returnInfo+='<table style="position: fixed;" width="71%" border="0">';
	returnInfo+='<tr bgcolor="#D8EAF8" style="height: 30px;">';
	returnInfo+='<td width="60%">';
	returnInfo+='<table border="0">';
	for (i = 0; i < carrierList.length; i++)
	{
	//alert("carrierList value-"+carrierList[i].value);
		returnInfo+='<tr style="height: 30px; width: 100%">';
		returnInfo+='<td align="left" width="9%"><div class="'+carrierImgList[i].value+'"></div></td>';
		returnInfo+='<td align="left" width="8%">'+carrierList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+originList[i].value+'&#160;'+deptTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+destinationList[i].value+'&#160;'+arrivalTimeList[i].value+'</td>';
		
		returnInfo+='</tr>';
	}
	returnInfo+='</table>';
	returnInfo+='</td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%">'+durationList[0].value+'</td>';
		returnInfo += '<td align="center" style="vertical-align: middle" width="6%">'+totalNet[0].value+'     </td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%"><input type="button" class="butn" onClick="getSelectedRowValueOneWay();" name="rowValue" value="Save"/> </td>';
	returnInfo+='</tr>';
	returnInfo+='</table>';    
	$('#selectedFlightDiv').html("");
	$('#selectedFlightDiv').html(returnInfo);
}

function onloadRoundTripAirSrchRsltPage()
{
	$("#selectedOnwordIndex").val($("#firstOnwordIndex").val());
	$("#selectedReturnIndex").val($("#firstReturnIndex").val());
	$("#selectedOnwordKey").val($("#firstOnwordKey").val());
	$("#selectedReturnKey").val($("#firstReturnKey").val());
	var flightCarrier = $("#firstCarrier").val();
	//alert(flightCarrier);
	getSelectedRoundTripFlightDetail(flightCarrier);
}

function setIndexAndKeyValue(onwordIndex,onwordKey,returnIndex,returnKey)
{
	if(onwordIndex!='' && onwordKey!='')
	{
		$("#selectedOnwordIndex").val(onwordIndex);
		$("#selectedOnwordKey").val(onwordKey);
	}
	else if(returnIndex!='' && returnKey!='')
	{
		$("#selectedReturnIndex").val(returnIndex);
		$("#selectedReturnKey").val(returnKey);
	}
}

function getSelectedRoundTripFlightDetail(flightCarrier)
{
	var onwordIndex = $("#selectedOnwordIndex").val();
	var returnIndex = $("#selectedReturnIndex").val();
	$("#carrierCode").val(flightCarrier);
	
	var onwordCarrierList = document.getElementsByName("carrierName"+onwordIndex);
	
	var onwordCarrierImgList = document.getElementsByName("carrierImg"+onwordIndex);
	var onwordOriginList = document.getElementsByName('origin'+onwordIndex);
	var onwordDeptTimeList = document.getElementsByName('deptTime'+onwordIndex);
	var onwordDestinationList = document.getElementsByName('destination'+onwordIndex);
	var onwordArrivalTimeList = document.getElementsByName('arrivalTime'+onwordIndex);
	var onwordTotalNet = document.getElementsByName('totalNet'+onwordIndex);
	var onwordCarrierClassList = document.getElementsByName('carrierClass'+onwordIndex);
	var onwordDurationList = document.getElementsByName('duration'+onwordIndex);
	
	
	var returnCarrierList = document.getElementsByName("returnCarrierName"+returnIndex);
	var returnCarrierImgList = document.getElementsByName("returnCarrierImg"+returnIndex);
	var returnOriginList = document.getElementsByName('returnOrigin'+returnIndex);
	var returnDeptTimeList = document.getElementsByName('returnDeptTime'+returnIndex);
	var returnDestinationList = document.getElementsByName('returnDestination'+returnIndex);
	var returnArrivalTimeList = document.getElementsByName('returnArrivalTime'+returnIndex);
	var returnTotalNet = document.getElementsByName('returnTotalNet'+returnIndex);
	var returnDurationList = document.getElementsByName('returnDuration'+returnIndex);
	
	var totalAmount = parseFloat(onwordTotalNet[0].value) + parseFloat(returnTotalNet[0].value);
	
	var returnInfo = "";
	returnInfo+='<table style="position: fixed;" width="71%" border="0">';
	returnInfo+='<tr bgcolor="#D8EAF8" style="height: 30px;">';
	
	returnInfo+='<td width="1%">';
	returnInfo += '<div class="onward_icon"> </div>';
	returnInfo+='</td>';
	
	returnInfo+='<td width="60%">';
	returnInfo+='<table border="0">';
	for (i = 0; i < onwordCarrierList.length; i++)
	{
		returnInfo+='<tr style="height: 30px; width: 100%">';
		returnInfo+='<td align="left" width="9%"><div class="'+onwordCarrierImgList[i].value+'"></div></td>';
		returnInfo+='<td align="left" width="8%">'+onwordCarrierList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+onwordOriginList[i].value+'&#160;'+onwordDeptTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+onwordDestinationList[i].value+'&#160;'+onwordArrivalTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+onwordDurationList[i].value+'</td>';
		returnInfo+='</tr>';
	}
	returnInfo+='</table>';
	returnInfo+='</td>';
	
	returnInfo+='<td width="1%">';
	returnInfo += '<div class="return_icon"> </div>';
	returnInfo+='</td>';
	
	returnInfo+='<td width="60%">';
	returnInfo+='<table border="0">';
	for (i = 0; i < returnCarrierList.length; i++)
	{
		returnInfo+='<tr style="height: 30px; width: 100%">';
		returnInfo+='<td align="left" width="9%"><div class="'+returnCarrierImgList[i].value+'"></div></td>';
		returnInfo+='<td align="left" width="8%">'+returnCarrierList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+returnOriginList[i].value+'&#160;'+returnDeptTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+returnDestinationList[i].value+'&#160;'+returnArrivalTimeList[i].value+'</td>';
		returnInfo+='<td  align="center" width="12%">'+returnDurationList[i].value+'</td>';
		returnInfo+='</tr>';
	}
	returnInfo+='</table>';
	returnInfo+='</td>';
	
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%">Rs.&#160;'+totalAmount+'/-</td>';
	returnInfo+='<td align="center" style="vertical-align: middle" width="6%"><input type="button" class="butn" onClick="getSelectedRowValueRoundTrip();" name="rowValue" value="Save"/> </td>';
	returnInfo+='</tr>';
	returnInfo+='</table>';
	$('#selectedFlightDiv').html("");
	$('#selectedFlightDiv').html(returnInfo);
	
}


function getSelectedRowValueOneWay()
{
	var flightKey = $("#onwordFlightKey").val();
	$("#selectedFlightKey").val(flightKey);
	if(lightFaceObject!=null)
	{
		var isClose = beforeClosePopup();
		if(isClose==true)
		{
			lightFaceObject.destroy();
			lightFaceObject = null;
			$('#parentBody').css({'overflow-y':'visible'});
		}
	}
}

function getSelectedRowValueRoundTrip()
{
	var onwrdFlightKey = $("#selectedOnwordKey").val();
	var returnFlightKey = $("#selectedReturnKey").val();
	$("#selectedFlightKey").val(onwrdFlightKey);
	//$("#returnFlightKeyParent").val(returnFlightKey);
	if(lightFaceObject!=null)
	{
		var isClose = beforeClosePopup();
		if(isClose==true)
		{
		lightFaceObject.destroy();
		lightFaceObject = null;
		$('#parentBody').css({'overflow-y':'visible'});
	}
}
}

function getScrollXY() {
    var x = 0, y = 0;
    if( typeof( window.pageYOffset ) == 'number' ) {
        // Netscape
        x = window.pageXOffset;
        y = window.pageYOffset;
    } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
        // DOM
        x = document.body.scrollLeft;
        y = document.body.scrollTop;
    } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
        // IE6 standards compliant mode
        x = document.documentElement.scrollLeft;
        y = document.documentElement.scrollTop;
    }
    return [x, y];
}

function getAgeByDOB(dob)
{
	var birthday = dob.split('-').reverse().join('-');
	var now = new Date();
    var past = new Date(birthday);
    var nowYear = now.getFullYear();
    var pastYear = past.getFullYear();
    var age = nowYear - pastYear;
    return age;
}
 

function calculateAge(dobArr)
{
	var adult = 0;
	var child = 0;
	var infant = 0;
	var travellerTypeArr = new Array();
	if(dobArr.length>0)
	{
		for(var i=0; i<dobArr.length; i++)
		{
			var birthday = dobArr[i].split('-').reverse().join('-');
			var now = new Date();
		    var past = new Date(birthday);
		    var nowYear = now.getFullYear();
		    var pastYear = past.getFullYear();
		    var age = nowYear - pastYear;
		    
		    if(age>=12)
		    {
		    	adult++;
		    }
		    else if(age<2)
		    {
		    	infant++;
		    }
		    else
		    {
		    	child++;
		    }	
		}
	}
	travellerTypeArr[0] = adult;
	travellerTypeArr[1] = child;
	travellerTypeArr[2] = infant;
    return travellerTypeArr;
    
}

function viewAirRequestReport(requestId)
{
	if(lightFaceReportObject!=null)
	{
		lightFaceReportObject.destroy();
		lightFaceReportObject = null;
	}
	document.getElementById("requestViewId").value="";
   	document.getElementById("requestViewId").value=requestId;
   	ajaxRequestFace = new LightFace.Request
   	({
 		 	width: 950,
			height: 350,
		url: '/pcda/af/page/pcda/air/AirRequestAjax.do',
		buttons: [{ title: 'Close', event: function() { this.close(); } }],
		request: { 
					data: { 
							requestViewId: requestId,
							requestCondition: 'savedRequestDetail'
						  },
					method: 'post'
				  },
		title: 'Air Request View'
	});
  lightFaceReportObject = ajaxRequestFace;
  ajaxRequestFace.open();
}


//----------------------------------From To Validation Of Airport On The Basis Of Rule-------------------
/* Method commented on dated 2017-01-11 to block round trip in air */
/*
function hideRoundTrip()
{
	//alert("hideRoundTrip travelMode-"+travelMode+",travelTypeText-"+travelTypeText+",jrnyType-"+jrnyType);
	$('#airJrnyTypeDiv0').show();
	if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
	if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
	if($('#returnDateRow')!=null)$('#returnDateRow').hide();
}
*/
/* Method open on dated 2017-01-11 to block round trip in air */


function hideRoundTrip()
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var jrnyType=getJourneyType();
	var travelMode=$('#travelMode').val();
	var isBothModeRequestTaken=$('#isBothModeRequestTaken').val();
	var isRailRequestOnwardExist = $("#isRailRequestOnwardExist").val();		
	var isRailRequestReturnExist = $("#isRailRequestReturnExist").val();		
	var isAirRequestOnwardExist = $("#isAirRequestOnwardExist").val();		
	var isAirRequestReturnExist = $("#isAirRequestReturnExist").val();		
	var isAirOneWayExist=$('#isAirOneWayExist').val();
	
	//alert("hideRoundTrip travelMode-"+travelMode+",travelTypeText-"+travelTypeText+",jrnyType-"+jrnyType);
	if(travelTypeText.indexOf('LTC')>-1 && travelMode==1)
	{
		
		$('#airJrnyTypeDiv0').show();
		if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
		if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
		if($('#returnDateRow')!=null)$('#returnDateRow').hide();
		
		//commented to show only onward journey for ltc on dated 2015-10-12
	}else
	{
		$('#airJrnyTypeDiv0').show();
		if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').show();
		if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').show();
		//if($('#returnDateRow')!=null)$('#returnDateRow').show();
	}
	
}

  
 /*
function hideRoundTrip_ORIG()
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var jrnyType=getJourneyType();
	var travelMode=$('#travelMode').val();
	var isBothModeRequestTaken=$('#isBothModeRequestTaken').val();
	var isRailRequestOnwardExist = $("#isRailRequestOnwardExist").val();		
	var isRailRequestReturnExist = $("#isRailRequestReturnExist").val();		
	var isAirRequestOnwardExist = $("#isAirRequestOnwardExist").val();		
	var isAirRequestReturnExist = $("#isAirRequestReturnExist").val();		
	var isAirOneWayExist=$('#isAirOneWayExist').val();
	
	//alert("hideRoundTrip travelMode-"+travelMode+",travelTypeText-"+travelTypeText+",jrnyType-"+jrnyType);
	if(travelTypeText.indexOf('LTC')>-1 && travelMode==1)
	{
		
		$('#airJrnyTypeDiv0').show();
		if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
		if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
		if($('#returnDateRow')!=null)$('#returnDateRow').hide();
		
		//		 commented to show only onward journey for ltc on dated 2015-10-12
		 
		 
		if(isRailRequestOnwardExist=='YES' || isRailRequestReturnExist=='YES')
		{
			$('#airJrnyTypeDiv0').show();
			if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
			if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
			if($('#returnDateRow')!=null)$('#returnDateRow').hide();
		}else if(isAirRequestOnwardExist=='YES')
		{
			$('#airJrnyTypeDiv0').show();
			if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
			if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
			if($('#returnDateRow')!=null)$('#returnDateRow').hide();
		}
		else if(isAirRequestOnwardExist=='NO' && isAirRequestReturnExist=='YES')
		{
			if(jrnyType==0)
			{
				$('#airJrnyTypeDiv0').show();
				if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
				if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
				if($('#returnDateRow')!=null)$('#returnDateRow').hide();
			}else
			{
				$('#airJrnyTypeDiv0').hide();
				if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').show();
				//if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').show();
				if($('#returnDateRow')!=null)$('#returnDateRow').show();
			}	
			
		}else
		{
			if(jrnyType==0)
			{
				$('#airJrnyTypeDiv0').show();
				if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').hide();
				if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
				//if($('#returnDateRow')!=null)$('#returnDateRow').hide();
			}else
			{
				$('#airJrnyTypeDiv0').hide();
				if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').show();
				//if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').show();
				//if($('#returnDateRow')!=null)$('#returnDateRow').show();
			}	
		}
		
		
		
	}else
	{
		$('#airJrnyTypeDiv0').show();
		if($('#airJrnyTypeDiv1')!=null)$('#airJrnyTypeDiv1').show();
		if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').show();
		//if($('#returnDateRow')!=null)$('#returnDateRow').show();
	}
	
	if(travelTypeText.indexOf('LTC')>-1)
	{
		if($('#airJrnyTypeDiv2')!=null)$('#airJrnyTypeDiv2').hide();
	}
}
*/
  
var orgTypeString = [];
var destTypeString = [];

function setOrgDestStnByTrRule(msg,value,ramBaanCheck)
{
	//alert("airTravelRequest.js:1574:setOrgDestStnByTrRule-value-"+value);
	var orgOptions="";
	var destOptions="";
	var destStn="";
	
	// Hide Round Trip Block For LTC Return Journey
	hideRoundTrip();
	
	
	var taggCheck=$('#taggCheck').val();
	var oldDutyNap=$("#oldDutyNap").val(); 
	var newDutyNap=$("#newDutyNap").val(); 
	var oldSprNap=$("#oldSprNap").val(); 
	var newSprNap=$("#newSprNap").val(); 
	
	var taggedLtcYear='';
	if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
	$("#origin").val(""); 
	$("#destination").val(""); 
	$("#trOrgStn").html("<label/>"); 
	$("#trDestStn").html("<label/>"); 
	$("#orgContainer").hide();
	$("#destContainer").hide();
	
	if(msg=="")
	{
		msg=$('#travelerXML').val();
	}
	
	msg=parseXML(msg);

	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
     var x=0;
	$(msg).find('FamilyDetailSeq').each(function(i)
	{
	
			
			
			if(travelTypeText.indexOf('LTC')>-1  && taggCheck!="true")
			{
			var JourneyCheck;
			var JourneyCheckRet;
			var relation=document.getElementById("relationCheck"+i).value
		    
		    if($('#removeFrmList'+i).is(":checked"))
		    {
				if(relation=="Self" && value=="1")
				{
					$("#requisiteDiv").hide();
					$("#reqstCheck").val("false")
				}
				else
				{
					$("#requisiteDiv").show();
					$("#reqstCheck").val("true")
				}
			}
			
				var k=0;
				$(this).find('YearWise').each(function()
				{
			
					var xmlLtcYear=$(this).find('Year').text();
			
					if(taggedLtcYear!='')
					{
						if(xmlLtcYear==taggedLtcYear)
						{
							JourneyCheck = $(this).find('JourneyCheck').text();
							JourneyCheckRet = $(this).find('JourneyCheckRet').text();
			
							if(value==0)
							{
				
								if(JourneyCheck=='true')
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
 									$("#reasonDiv"+i).hide();
				
								}
								else
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"  id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#reasonDiv"+i).show();
								}
							}
						
							if(value==1)
							{ 
				
								if(JourneyCheckRet=='true')
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
									$("#reasonDiv"+i).hide();
								}
								else
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#reasonDiv"+i).show();
								}
							}	
					
		  				}
				
				} //End of if case
				else
				{
					if(k==1)	
					{
						JourneyCheck = $(this).find('JourneyCheck').text();
						JourneyCheckRet = $(this).find('JourneyCheckRet').text();
				
						if(value==0)
						{
				
							if(JourneyCheck=='true')
							{
								$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
			     				$("#reasonDiv"+i).hide();
								
							}
							else
							{
								$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"  id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
								$("#reasonDiv"+i).show();
							}
						}
					
						if(value==1)
						{
				
							if(JourneyCheckRet=='true')
							{
								$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
								$("#reasonDiv"+i).hide();
							}
							else
							{
								$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
								$("#reasonDiv"+i).show();
							}
						}
					}
				} //End of else case
			
				k=k+1;
			
			}); //End of YearWise loop
			
		 } // end of LTC Case		

		 else if(travelTypeText.indexOf('FORMD')>-1)
			{
				var formDYear=document.getElementById("formDYear").value; 
				var YearWiseFormD = $(this).find('YearWiseFormD').text();

				if(formDYear==YearWiseFormD)
					{
						
						var JourneyCheck;
						var JourneyCheckRet;
						var relation=document.getElementById("relationCheck"+x).value

					    if($('#removeFrmList'+x).is(":checked"))
					    {
							if(relation=="Self" && value=="1")
							{
								$("#requisiteDiv").hide();
								$("#reqstCheck").val("false")
							}
							else
							{
								$("#requisiteDiv").show();
								$("#reqstCheck").val("true")
							}
						}
				
						JourneyCheck=$(this).find('JourneyCheck').text();
						if(JourneyCheck=='true')
						{
								$("#removeFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="removeFrmList' +x+ '" name="removeFrmList' +x+ '" value="1" onclick="setValue('+ x+')"/>');
				     			$("#reasonDiv"+x).hide();
						}
						else
						{
							if(travelTypeText=='FORMD' || travelTypeText=='OTHERS')
							{
								$("#removeFrmListDiv"+x).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +x+ '" name="removeFrmList' +x+ '" value="0" onclick="return false" onkeyup="return false"/>');
							}else
							{
								$("#removeFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="removeFrmList' +x+ '" name="removeFrmList' +x+ '" value="1" onclick="setValue('+ x+')"/>');
							}
		 						$("#reasonDiv"+x).show();
						}
					x=x+1;
				}	

			}	
		 else
		 {
			 	var JourneyCheck;
				var JourneyCheckRet;
				var relation=document.getElementById("relationCheck"+i).value
	
			    if($('#removeFrmList'+i).is(":checked"))
			    {
					if(relation=="Self" && value=="1")
					{
						$("#requisiteDiv").hide();
						$("#reqstCheck").val("false")
					}
		 else
		 {
						$("#requisiteDiv").show();
						$("#reqstCheck").val("true")
					}
				}
			
				JourneyCheck=$(this).find('JourneyCheck').text();
				if(JourneyCheck=='true')
				{
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>');
		     			$("#reasonDiv"+i).hide();
				}
				else
				{
					if(travelTypeText=='FORMD' || travelTypeText=='OTHERS'){
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>');
					}else{
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>');
					}
 						$("#reasonDiv"+i).show();
				}
		 }
				 
	}); //End of FamilyDetailSeq loop

	if(ramBaanCheck!="true")
	{
	
		
       var oldReq=document.getElementsByName("oldRequest")
       for(var i=0;i<oldReq.length;i++)
       {
	         if(oldReq[i].checked==true)
	         {
	         	 var auth=oldReq[i].value;
	         	 var authArr=auth.split("~");
		         authorityNo=authArr[1];
		         authorityDate=authArr[0];
		         ltcYear=authArr[3];
		         checkRadio=true
		         destStn=authArr[2];
		         // alert("airTravelRequest.js:1695:setOrgDestStnByTrRule:i["+i+"]When any Tagged Request Sselected||authorityNo="+authorityNo+"||authority date="+authorityDate+"||ltcYear="+ltcYear+"||destStn="+destStn)
	        }
	   }

		orgOptions += '<select id="orgStationDD" name="orgStationDD" onchange="setOrgStnValue(this.value)" class="combo"><option value="">Select</option>'
	    $(msg).find('AirStationListFrom'+value).each(function()
	    {
			var FromStation = $(this).find('AirFromStation').text();
			var FromStationType = $(this).find('AirFromStationType').text();
			var FromStationTypeInt = $(this).find('AirFromStationTypeInt').text();
			orgTypeString.push({id:FromStationTypeInt,name:FromStationType});	


			//alert("setOrgDestStnByTrRule:FromStation-"+FromStation+"|FromStationType-"+FromStationType);	
			
			if(FromStationType=="New Duty Station" && newDutyNap!=undefined)
				orgOptions += '<option value="' + newDutyNap + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="New SPR" && newSprNap!=undefined)
				orgOptions += '<option value="' + newSprNap + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="Old SPR" && oldSprNap!=undefined)
				orgOptions += '<option value="' + oldSprNap + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="Old Duty Station" && oldDutyNap!=undefined)
				orgOptions += '<option value="' + oldDutyNap + '">' +FromStationType + '<\/option>';
			else
				orgOptions += '<option value="' + FromStation + '">' +FromStationType + '<\/option>';
		}); 
		//alert("From Station List for Value="+value+"--"+orgOptions)
		
		if(orgOptions=="<select>"){
			orgOptions = 'No From Station Exists In Profile';
		}else{
          orgOptions += '</select>'
		}              
        $("#orgStnDropDown").html(orgOptions);
       
        //alert("Org Option orgOptions.indexOf(AnyStation) cond="+orgOptions.indexOf('AnyStation') +" | travelTypeText.indexOf(LTC-AI) cond="+travelTypeText.indexOf('LTC-AI')+"|value="+value+"|taggCheck="+taggCheck);
        	
        if(!(orgOptions.indexOf('AnyStation') > -1))
        {
        	//AJAY KHURMI START 25-05-2017
        	if(travelTypeText.indexOf('LTC-AI')>-1 && value==1 && taggCheck!="true" )
        	//if(travelTypeText.indexOf('LTC-AI')>-1 && value==0 && taggCheck!="true" )
        	//AJAY KHURMI END 25-05-2017
        	{
             setOrgStnValue(destStn)
             $("#orgStnDropDown").hide();                           
       	}else{
       		$("#orgStnDropDown").show();       
       	}
        }else{
       		$("#orgStnDropDown").show();       
       	}
       	
    
    	destOptions += '<select id="destStationDD" name="destStationDD" onchange="setDestStnValue(this.value)" class="combo"><option value="">Select</option>'
	  	$(msg).find('AirStationListTo'+value).each(function()
      	{
			var ToStation = $(this).find('AirToStation').text();
			var ToStationType = $(this).find('AirToStationType').text();
			var ToStationTypeInt = $(this).find('AirToStationTypeInt').text();
			//destTypeString+=ToStationTypeInt+":"+ToStationType+",";
			destTypeString.push({id:ToStationTypeInt,name:ToStationType});	
			
			if(ToStationType=="New Duty Station" && newDutyNap!=undefined)
			destOptions += '<option value="' + newDutyNap + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="New SPR" && newSprNap!=undefined)
			destOptions += '<option value="' + newSprNap + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="Old SPR" && oldSprNap!=undefined)
			destOptions += '<option value="' + oldSprNap + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="Old Duty Station" && oldDutyNap!=undefined)
			destOptions += '<option value="' + oldDutyNap + '">' +ToStationType + '<\/option>';
			else
			destOptions += '<option value="' + ToStation + '">' +ToStationType + '<\/option>';
	 	}); 
			
	
		//alert("To Station List for Value="+value+"--"+destOptions)
	
	 	if(destOptions=="<select>"){
 			destOptions = 'No TO Station Exists In Profile';
     	}else{
        	destOptions +="</select>"
     	}
     	
      	$("#destStnDropDown").html(destOptions);  
      
        //alert("dest Option destOptions.indexOf(AnyStation) cond="+destOptions.indexOf('AnyStation') +" | travelTypeText.indexOf(LTC-AI) cond="+travelTypeText.indexOf('LTC-AI')+"|value="+value+"|taggCheck="+taggCheck);
        	
    
        if(!(destOptions.indexOf('AnyStation') > -1))  
        {  
        //AJAY KHURMI START 25-05-2017
      	if(travelTypeText.indexOf('LTC-AI')>-1 && value==0 && taggCheck!="true")
      	//if(travelTypeText.indexOf('LTC-AI')>-1 && value==1 && taggCheck!="true")
      	//AJAY KHURMI END 25-05-2017
      	{
      	 	setDestStnValue(destStn)
          	$("#destStnDropDown").hide();
      	}else{
       		$("#destStnDropDown").show();
	  	}
		}else
		{
       		$("#destStnDropDown").show();
	  	}			  
	}	// End of if(ramBaanCheck!="true")		  
	 

}

function setOrgTypeValue(){
	
	var stnTextValue=$('#orgStationDD option:selected').text();
	if(stnTextValue=='Select')$("#originType").val('');  		
	//alert("stnTextValue-"+stnTextValue);
	for(i=0;i<orgTypeString.length;i++)
	{
		//alert("ID:- "  + orgTypeString[i].id + " Name:- "  + orgTypeString[i].name );
		if(orgTypeString[i].name==stnTextValue){
			$("#originType").val(orgTypeString[i].id);  		
		}
		
	}
	
}
function setDestTypeValue(){
	var stnTextValue=$('#destStationDD option:selected').text();
	if(stnTextValue=='Select')$("#destinationType").val('');  		
	//alert("stnTextValue-"+stnTextValue);
	for(i=0;i<destTypeString.length;i++)
	{
		//alert("ID:- "  + destTypeString[i].id + " Name:- "  + destTypeString[i].name );
		if(destTypeString[i].name==stnTextValue){
			$("#destinationType").val(destTypeString[i].id);  		
		}
		
	}
}

function setOrgStnValue(stnValue)
{
  //alert("Inside setOrgStnValue stnValue="+stnValue);
  
  $("#from_Station_Id").val($('#orgStationDD option:selected').text());
  $("#removeFrmList0").attr('checked', false);
  setOrgTypeValue();
  
  var stnStr="";
  if(stnValue=='AnyStation')
  {
   	$("#orgContainer").show(); 
   	$("#trOrgStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="trOrgStn"><td><input type="hidden" name="origin" value="'+stnValue +'" class="txtfldM" id="origin"  /><label>'+stnValue +'</label></td></div>'
	   $("#origin").val(stnValue);   
	   $("#trOrgStn").html(stnStr); 
	   $("#trOrgStn").show(); 
	   $("#orgContainer").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="trOrgStn"><td>';
	   stnStr +='<select id="origin" name="origin" width="325px" onchange="fillOrgStn(this.value);">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#trOrgStn").html(stnStr); 
	   $("#trOrgStn").show(); 
	   $("#orgContainer").hide();
	  		
	}
  }
	   
}


function setDestStnValue(stnValue)
{
	setDestTypeValue();
 	var stnStr="";
  	//alert("Inside setDestStnValue stnValue="+stnValue);
  	
  	$("#to_Station_Id").val($('#destStationDD option:selected').text());
  	$("#removeFrmList0").attr('checked', false);
    if(stnValue=='AnyStation')
    {
    	$("#destContainer").show();
        $("#trDestStn").hide(); 
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="trDestStn"><td><input type="hidden" name="destination" value="'+stnValue +'"  class="txtfldM" id="destination" /><label>'+stnValue +'</label><td></div>'
	    	$("#destination").val(stnValue);   
	      	$("#trDestStn").html(stnStr); 
	      	$("#trDestStn").show();
	    	$("#destContainer").hide();
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="trDestStn"><td>';
	   
	   		stnStr +='<select id="destination" name="destination" width="325px" onchange="fillDestStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#trDestStn").html(stnStr); 
		    $("#trDestStn").show();
		    $("#destContainer").hide();
	  	
	  	}
	
	  }
	  
}


function fillOrgStn(thisValue) {
	 $('#origin').val(thisValue);
	 setTimeout("$('#orgSuggestion').hide();", 200);
 }

function fillDestStn(thisValue) {
	 $('#destination').val(thisValue);
	 setTimeout("$('#destSuggestion').hide();", 200);
 
 }
 


function initAirportList(){
	
	//alert("Inside initAirportList ");
	
	$('#origin').keyup(function() 
	{
		var origin=$('#origin').val();
		
		if(origin.length>1)
		{
			
			var StationList="";
			
			$.ajax(
			{
			      url: "/pcda/af/pcda/travelReq/travelRequestAjax.do",
			      type: "get",
			      data: "stationName="+origin+"&ajaxCallType=getAirportList",
			      dataType: "text",
			      success: function(msg)
			      {
			          msg= parseXML(msg);
		              $(msg).find('StationSeq').each(function()
		              {
		              	var name=$(this).find('StationName').text()
						if(name=="Airport Not Exist")
		                	StationList+='<li>' +name+'</li>';
		                else
		                    StationList+='<li onClick="fillOrgStn(\'' +name +'\')">' +name+'</li>';
		              });
		              
					  $("#orgSuggestionsList").html(StationList);		
		              $('#orgSuggestion').show();
		              beforeSend:$('#test').html("Getting response from server....");
			       }
			});
		}
	});

	$('#destination').keyup(function() 
	{

		var destination=$('#destination').val();
	
		if(destination.length>1)
		{
			
			var StationList="";
	
			$.ajax(
			{
			      url: "/pcda/af/pcda/travelReq/travelRequestAjax.do",
		    	  type: "get",
		      	  data: "stationName="+destination+"&ajaxCallType=getAirportList",
		      	  dataType: "text",
			      success: function(msg)
			      {
		                  msg= parseXML(msg);
		                  $(msg).find('StationSeq').each(function()
		                  {
		                  	  var name=$(this).find('StationName').text()
		                      if(name=="Airport Not Exist")
			                  	StationList+='<li>' +name+'</li>';
			                  else
			                  	StationList+='<li onClick="fillDestStn(\'' +name +'\')">' +name+'</li>';
		                  });
		                
						  $("#destSuggestionsList").html(StationList);		
		                  $('#destSuggestion').show();
		                       
		      		 	 beforeSend:$('#test').html("Getting response from server....");
		           
		  		 }
	  	  	});
		}
	});
}

function clickAirIndiaTab()
{
	$("#currentSelectTab").val('AirIndia');
	document.getElementById("airIndiaTab").className="slectdomhome";
	document.getElementById("otherTab").className="inthome";
	$(".AirIndia").show();
	$(".OtherFlight").hide();
	$(".OtherFlightTab").hide();
	$(".AllFlightTab").hide();
	$(".AllFlightTab").hide();
	$(".reasonTr").hide();
	$("#reasonCode").val("");
	$("#selectedFlightDiv").show();
	
	
	var firstIndex = $("#firstIndex").val();
	var flightKey = $("#firstKey").val();
	var flightCarrier = $("#firstCarrier").val();
	//alert("firstIndex-"+firstIndex+":flightKey-"+flightKey+":flightCarrier-"+flightCarrier);
	getSelectedOneWayFlightDetail(firstIndex,flightKey,flightCarrier);
	
}
function clickOtherTab()
{
	$("#currentSelectTab").val('OtherFlight');
	document.getElementById("otherTab").className="slectinthome";
	document.getElementById("airIndiaTab").className="domhome";
	$(".AirIndia").hide();
	$("#selectedFlightDiv").hide();
	var requestMode = $("#requestMode").val();
	var preRequestMode = $("#preRequestMode").val();// for edit time that's need
	if(requestMode!=1)
	{
		if(preRequestMode!=2)
		{
			$(".reasonTr").show();
		}
		else
		{
			$("#selectedFlightDiv").show();
			$(".OtherFlight").show();
			$(".OtherFlightTab").show();
			$(".AllFlightTab").show();
			$(".reasonTr").hide();
		}
	}
	else
	{
		$(".OtherFlight").show();
		$(".OtherFlightTab").show();
		$(".AllFlightTab").show();
	}
	
	
	var frm = document.getElementsByName("airLine");
	for (var i = 0; i < frm.length; i++)
    {
    	if(frm[i].value!="AI")
    	{
    		$("#airLine_"+frm[i].value).prop("checked", true);
    	}
    	else
    	{
    		$("#airLine_"+frm[i].value).prop("checked", false);
    	}
      //  document.getElementById('tab_'+frm[i].value).className="flightbox";
    }
}
function afterSelectReason(value)
{

	if(value.length>0 && value!="undefined")
	{
		$(".OtherFlight").show();
		$(".OtherFlightTab").show();
		$(".AllFlightTab").show();
		$("#selectedFlightDiv").show();
	}
	else
	{
		$(".OtherFlight").hide();
		$(".OtherFlightTab").hide();
		$(".AllFlightTab").hide();
		$(".AllFlightTab").hide();
		$("#selectedFlightDiv").hide();
	}
	
	var otherfirstIndex = $("#otherfirstIndex").val();
	var otherflightKey = $("#otherfirstKey").val();
	var otherflightCarrier = $("#otherfirstCarrier").val();
	//alert("firstIndex-"+otherfirstIndex+":flightKey-"+otherflightKey+":flightCarrier-"+otherflightCarrier);
	getSelectedOneWayFlightDetailOther(otherfirstIndex,otherflightKey,otherflightCarrier);
	
}

function beforeClosePopup()
{
	/* var requestMode = $("#requestMode").val();
	var carrierCode = $("#carrierCode").val();
	var value = $("#reasonCode").val();
	$("#reqMode").val(requestMode);
	$("#reasonCod").val(value);
	$(".notifyTRR").hide();
	var preRequestMode = $("#preRequestMode").val();// for edit time that's need
	if(requestMode!=1 && carrierCode!='AI')
	{
		if(value.length>0 && value!="undefined")
		{
			if(preRequestMode!=2)
			{
				$(".notifyTRR").show();
				$("#reqMode").val(2);
				return true;
			}
			else
			{
				$("#reqMode").val(2);
				return true;
			}
			
		}
		else
		{
			if(preRequestMode!=2)
			{
				$("#reqMode").val("");
				$("#reasonCod").val("");
				alert("Please select reason or select air india flight");
				return false;
			}
			else
			{
				$("#reqMode").val(2);
				return true;
			}
		}
		
	}
	else
	{
		return true;
	} */
	
	return true;
}



function clickAirIndiaRoundTripTab()
{
	document.getElementById("airIndiaTab").className="slectdomhome";
	document.getElementById("otherTab").className="inthome";

	$(".airIndiaOnword").show();
	$(".otherFlightOnword").hide();
	$(".airIndiaReturn").show();
	$(".otherReturnFlight").hide();
	$(".reasonTr").hide();
	$("#selectedFlightDiv").show();
}
function clickOtherRoundTripTab()
{
	document.getElementById("otherTab").className="slectinthome";
	document.getElementById("airIndiaTab").className="domhome";

	$(".airIndiaOnword").hide();
	$(".otherFlightOnword").hide();
	$(".airIndiaReturn").hide();
	$(".otherReturnFlight").hide();
	$(".reasonTr").show();
	$("#selectedFlightDiv").hide();
}

function afterSelectReasonOnRoundTrip(value)
{
	if(value.length>0 && value!="undefined")
	{
		$(".otherFlightOnword").show();
		$(".otherReturnFlight").show();
		$("#selectedFlightDiv").show();
	}
	else
	{
		$(".otherFlightOnword").hide();
		$(".otherReturnFlight").hide();
		$("#selectedFlightDiv").hide();
	}
}


function selectRadioFlightRequestData(flightKey, Carrier) {
  	if(Carrier == 'AI'){
  		document.getElementById("SelectedAIFlightKey").value = flightKey;
  	}
  	else{
  		document.getElementById("SelectedOtherFlightKey").value = flightKey;
  	}
  }
 
  function setFlightRequestData(flightKey, Carrier)
	{
		//alert("flightKey = "+flightKey+" Carrier = "+Carrier);
			
		if(Carrier == 'AI'){
		
			if(document.getElementById("SelectedAIFlightKey").value != flightKey)
			{
				alert("Can't save you have selected another flight, please select correct flight");
				return false;
			}
		}
		else{
			if(document.getElementById("SelectedOtherFlightKey").value != flightKey)
			{
				alert("Can't save you have selected another flight, please select correct flight");
				return false;
			}
		}
		
		if(Carrier != 'AI'){
			
			 var isNonAirVal = 'No';
			 
			 if(document.getElementById('isNonAirIndianSector')!=null){
			  isNonAirVal = document.getElementById('isNonAirIndianSector').value;
			 }
			 
			if(document.getElementById("searchFromVal").value == 'Create')
			{
				if(isNonAirVal != 'Yes')
				{
					if(document.getElementById("reasonCodeSel").value == '-1')
					{
							alert("Please select reason as you have not selected Air India flight");
							return false;
					}
					document.getElementById("reasonCode").value = document.getElementById("reasonCodeSel").value;
				}
			}
		}
		
			
		document.getElementById("flightCode").value = Carrier;
		document.getElementById("flightKey").value = flightKey;

	if(lightFaceObject!=null)
	{
		var isClose = beforeClosePopup();
		if(isClose==true)
		{
			lightFaceObject.destroy();
			lightFaceObject = null;
			$('#parentBody').css({'overflow-y':'visible'});
		}
	}
}
	       
function closeAirInfoModal(){
	var modal = document.getElementById("myModal");
	modal.style.display = "none";
	$("#inner_content").html("");
}

function submitAirBookRequestForm(requestId,sequenceNo,formAction,event,ticketSeqNo,reqType,reqId){
	
	$("#inner_content").html('<input type="button" onclick="submitRequestForm(\''+requestId+'\',\''+sequenceNo+'\',\''+formAction+'\',\''+event+'\',\''+ticketSeqNo+'\',\''+reqType+'\',\''+reqId+'\');" class="butn" value="I Confirm"/>');
	var modal = document.getElementById("myModal");
	modal.style.display = "block";
}	       

function submitRequestForm(requestId,sequenceNo,formAction,event,ticketSeqNo,reqType,reqId,clusterFlag)
{
	
	
	var submitForm=document.createElement("form");
	document.body.appendChild(submitForm);
	submitForm.method = "POST";
		
	if(event=="cancel"){
		document.getElementById("cancelRequestID").value=requestId;
		document.getElementById("cancelRequestType").value=reqType;
		
		document.getElementById("loginForm").style.display='none';
		document.getElementById("cancelDiv").style.display='block';
		
		return true;
	}
	var message = "";
	 
	 
	if(event=="airbook")
	{
		closeAirInfoModal();
		var originStr=$('#originAirport_'+reqId).val();
		var destinationStr=$('#destinationAirport_'+reqId).val();
		
		var originName=originStr.substring(0, originStr.lastIndexOf("("));
		
		var destinationName =destinationStr.substring(0, destinationStr.lastIndexOf("("));
		
		var journeyDate=$('#journeyDate_'+reqId).val();
		var returnJourneyDate=$('#returnJourneyDate_'+reqId).val();
		
		var tripTypeVal = $('#tripType_'+reqId).val();
		
		if(tripTypeVal == 'Normal Round Trip'){
			
			var isOnwardBooked = $('#isOnwardBooked_'+reqId).val();
			var isReturnBooked = $('#isReturnBooked_'+reqId).val();
			
			if(isOnwardBooked != '' && isOnwardBooked == 'Not Booked' && isReturnBooked != '' && isReturnBooked == 'Booked'){
				returnJourneyDate = "";
			}
			
			if(isOnwardBooked != '' && isOnwardBooked == 'Booked' && isReturnBooked != '' && isReturnBooked == 'Not Booked'){
				
				journeyDate = returnJourneyDate;
				returnJourneyDate = "";
				
				var tempPlace =  destinationName;
				
				destinationName = originName;
				
				originName = tempPlace;
			}
		}
		
		
		
		var imageurltitle='/pcda/images/fbloader.gif';
		
		
		if(returnJourneyDate!=""){
				message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
				}
				else{
			message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
		}
   	 	box = new LightFace({ 
    			title: "<img src='"+imageurltitle+"'> Loading ...",
				width: 410,
				content: message,
		});
		lightFaceObjectOld = box;
   		box.open();
   		
   		var calHeight=document.body.offsetHeight+20;
   		$("#screen-freeze").css({"height":calHeight + "px"} );
   		$("#screen-freeze").show();
   	}
  
	var hiddenElement =document.createElement('input');
	hiddenElement.name="requestId";
	hiddenElement.id="requestId";
	hiddenElement.type="hidden";
	hiddenElement.value =requestId;
	submitForm.appendChild(hiddenElement);
	
	var hiddenSequenceNo =document.createElement('input');
	hiddenSequenceNo.name="sequenceNo";
	hiddenSequenceNo.id="sequenceNo";
	hiddenSequenceNo.type="hidden";
	hiddenSequenceNo.value =sequenceNo;
	submitForm.appendChild(hiddenSequenceNo);
	
	var hiddenEeven =document.createElement('input');
	hiddenEeven.name="event";
	hiddenEeven.id="event";
	hiddenEeven.type="hidden";
	hiddenEeven.value =event;
	submitForm.appendChild(hiddenEeven);
	
	var ticketSeqNum =document.createElement('input');
	ticketSeqNum.name="tktSeqNo";
	ticketSeqNum.id="tktSeqNo";
	ticketSeqNum.type="hidden";
	ticketSeqNum.value =ticketSeqNo;
	submitForm.appendChild(ticketSeqNum);
	
	var clusterFlagElement =document.createElement('input');
	clusterFlagElement.name="clusterFlag";
	clusterFlagElement.id="clusterFlag";
	clusterFlagElement.type="hidden";
	clusterFlagElement.value =clusterFlag;
	submitForm.appendChild(clusterFlagElement);
	/*
	if(clusterFlag==0){
		alert("Due to technical reason,cluster booking facility will not be available till next intimation.");
		return false;
	}else
	{
		submitForm.action=formAction.replace('/page', "");
		submitForm.submit();
	}
	*/

	
	submitForm.action=formAction.replace('/page', "");
	submitForm.submit();
	
}


	function change(key, id, isOnward, CarrierTab){
		//alert(key + " " + id  + " " + isOnward + " " + requestMode);	
		
		document.getElementById("isOnwardLtcFlight").value = "";
		document.getElementById("isReturnLtcFlight").value = "";
			
		if(isOnward){
			var onwardFlightFare = 0.00;
			var returnFlightFare = 0.00;
			var totalFare = 0.00;
 			var cloneofClickedRow = $("#"+id +" > td").clone(true);		
			var updatableCells = $("#onward-selectedRibbon").find('td');			
			var row = $("#onward-ribbon tr");
			
			$(row).each(function(i){		
				$(row).find('td').remove();	
			});
			$(cloneofClickedRow).each(function(i){				
			
			if(i === 0){				
			    	var tempKey = $(this).find("input[type=radio]").val().split("#");
			    	var radioCol = $(this).find("input[type=radio]");
			    	var classSet = $(radioCol).attr('class').split(" ");
			    	$("#onwardKey").text(tempKey[0]);
			    	$("#onward_ServiceProvide").text(tempKey[1]);
			    	$("#onward_sessionIdentifier").text(tempKey[2]);
			    	$("#isOnwardRefundable").text(classSet[0]);
			    	$("#isOnwardDirect").text(classSet[1]);			
			    		
			    	if(CarrierTab === 'AI'){
			    		$("#isonwdltc").text(classSet[2]);		
			    	}else{
			    		$("#isonwdltc").text('no');	
			    	}	
			}			
				if(i > 0 && i < 5){
					$(row).append($(this));
				}
				
				
				
				if(i == 5){
					onwardFlightFare = Number($(this).text());
					//alert("onwardFlightFare = " + onwardFlightFare);
					$("#onwardPrice").text(onwardFlightFare);
					returnFlightFare = Number($("#returnPrice").text()) 
					totalFare = onwardFlightFare + returnFlightFare;
					//alert("totalFare = " + totalFare);
					$("#fare").text(totalFare);
				}
			});
			
			//$("#onwardKey").text(key);
			$("#carrierType").text(CarrierTab);
			
		
		}else{
		
		var cloneofClickedRow = $("#"+id + " > td").clone(true);		
		var updatableCells = $("#return-selectedRibbon").find('td');
		var row = $("#return-ribbon tr");
		
		
		$(row).each(function(i){		
				$(row).find('td').remove();	
			});
		
		$(cloneofClickedRow).each(function(i){
				if(i === 0){				
			    	var tempKey = $(this).find("input[type=radio]").val().split("#");
			    	var radioCol = $(this).find("input[type=radio]");
			    	var classSet = $(radioCol).attr('class').split(" ");
			    	$("#returnKey").text(tempKey[0]);
			    	$("#return_ServiceProvide").text(tempKey[1]);
			    	$("#return_sessionIdentifier").text(tempKey[2]);
			    	$("#isReturnRefundable").text(classSet[0]);
			    	$("#isReturnDirect").text(classSet[1]);			
			    		 	
			    	if(CarrierTab === 'AI'){
			    		$("#isrtnltc").text(classSet[2]);		
			    	}else{
			    		$("#isrtnltc").text('no');
			    		}		
				}	
				//alert(i);
				if(i > 0 && i < 5){
					$(row).append($(this));
				}
				if(i == 5){
					returnFlightFare = Number($(this).text());
					//alert("returnFlightFare = " + returnFlightFare);
					$("#returnPrice").text(returnFlightFare);
					onwardFlightFare = Number($("#onwardPrice").text())
					//alert("onwardFlightFare = " + onwardFlightFare);
					totalFare = onwardFlightFare + returnFlightFare;
					$("#fare").text(totalFare);
				}
			});
			
			
			//$("#returnKey").text(key);
			$("#carrierType").text(CarrierTab);
		
		}
	
		}
		
		
	
function setNormalRoundTripFlightRequestData(flightType){
	

	var Carrier = document.getElementById('carrierType').value;

	if(Carrier != 'AI'){

			 var isNonAirVal = 'No';

			 if(document.getElementById('isNonAirIndianSector')!=null){
			  isNonAirVal = document.getElementById('isNonAirIndianSector').value;
			 }
		
			if(document.getElementById("searchFromVal").value == 'Create')
			{
				if(isNonAirVal != 'Yes')
				{
					if(document.getElementById("reasonCodeSel").value == '-1')
					{
							alert("Please select reason as you have not selected Air India flight");
							return false;
					}
					document.getElementById("reasonCode").value = document.getElementById("reasonCodeSel").value;
				}
			}
	}
	
			
		document.getElementById("flightCode").value = Carrier;
		document.getElementById("flightKey").value = flightKey;
	
	if(lightFaceObject != null){
		var isClose = beforeClosePopup();
		if(isClose==true){
			lightFaceObject.destroy();
			lightFaceObject = null;
			$('#parentBody').css({'overflow-y':'visible'});
		}
	}
}


function initRibbon(tabMode){
	var onwardSelectedFlight = null;
	var returnSelectedFlight = null;
	var selectedRow = null;
	//alert('under js '+ tabMode);
	if(tabMode === 'AI'){
		onwardSelectedFlight = $("input[name='aiOnwrdFlight']:checked");
		selectedRow =  $(onwardSelectedFlight).parents("tr");		
		selectedRow.each(function(index){
			if($(this).attr('id') != null && $(this).attr('id') != "undefined"){
				var rowId = new String($(this).attr('id'));
				var dyid = new String("ai-onward-" + $(onwardSelectedFlight).attr('id'));
				//alert(rowId + " " + dyid);
				if(dyid.valueOf() === rowId.valueOf()){									
					change($(onwardSelectedFlight).val(), rowId.valueOf(), true, tabMode);
				}
			}
		});

		returnSelectedFlight = $("input[name='aiReturnFlight']:checked");
		selectedRow =  $(returnSelectedFlight).parents("tr");		
		selectedRow.each(function(index){
			if($(this).attr('id') != null && $(this).attr('id') != "undefined"){
				var rowId = new String($(this).attr('id'));
				var dyid = new String("ai-return-" + $(returnSelectedFlight).attr('id'));
				//alert(rowId + " " + dyid);
				if(dyid.valueOf() === rowId.valueOf()){					
					change($(returnSelectedFlight).val(), rowId.valueOf(), false, tabMode);
				}
			
			}
			
		
		});
	
		document.getElementById('carrierType').value='AI';

	}else{
		onwardSelectedFlight = $("input[name='otherOnwrdFlight']:checked");
		selectedRow =  $(onwardSelectedFlight).parents("tr");		
		selectedRow.each(function(index){
			if($(this).attr('id') != null && $(this).attr('id') != "undefined"){
				var rowId = new String($(this).attr('id'));
				var dyid = new String("other-onward-" + $(onwardSelectedFlight).attr('id'));
				//alert(rowId + " " + dyid);
				if(dyid.valueOf() === rowId.valueOf()){					
					change($(onwardSelectedFlight).val(), rowId.valueOf(), true, tabMode);
				}
			}
		
		});
		
		returnSelectedFlight = $("input[name='otherReturnFlight']:checked");	
		selectedRow =  $(returnSelectedFlight).parents("tr");		
		selectedRow.each(function(index){
			if($(this).attr('id') != null && $(this).attr('id') != "undefined"){
				var rowId = new String($(this).attr('id'));
				var dyid = new String("other-return-" + $(returnSelectedFlight).attr('id'));
				//alert(rowId + " " + dyid);
				if(dyid.valueOf() === rowId.valueOf()){					
					change($(returnSelectedFlight).val(), rowId.valueOf(), false, tabMode);
				}
			}
		
		});

		document.getElementById('carrierType').value='Other';
	}
	
}



function viewAirFareRule(contextpath,selectedFlight,serviceProvider,sessionIdentifier,bookingClass,fareBaseCode){

	var domInt = $('#domOrInt').val();
	$.ajax({
		type: "GET",
		url:contextpath+"/af/pcda/air/GetFareRule.do",
		cache:false,		
		data: {domInt: domInt,selectedFlight:selectedFlight,serviceProvider:serviceProvider,sessionIdentifier:sessionIdentifier,bookingClass:bookingClass,fareBaseCode:fareBaseCode},
		dataType: "text",
		
		beforeSend: function()
		      {
		        	imageurltitle='/pcda/images/fbloader.gif';
		       	 	box = new LightFace
		       	 	({ 
		        			title: "<img src='"+imageurltitle+"'/> Loading ...",
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




function setTabeInfo(tabType)
{	 selectedTab = tabType;
	var bookingRequestMode = document.getElementById("requestMode").value;
	
	//alert("value of tabType = "+tabType+"  bookingRequestMode "+bookingRequestMode);
	
	if(bookingRequestMode == '0')
	{
		if(tabType == 'Other'){
			$("#reApprovalBtn").show();
			$("#ai-filter").hide();
	        $("#other-filter").show();
	        $("#ref-vs-not-ref-ai").hide();
            $("#ref-vs-not-ref-other").show();
            $("#serviceProvider-ai").hide();
        	$("#serviceProvider-other").show();
        	$("#stopFilter-ai").hide();
        	$("#stopFilter-other").show();
		}else{
			$("#reApprovalBtn").hide();
			$("#ai-filter").show();
	        $("#other-filter").hide();
	        $("#ref-vs-not-ref-ai").show();
            $("#ref-vs-not-ref-other").hide();
            $("#serviceProvider-ai").show();
        	$("#serviceProvider-other").hide();
        	$("#stopFilter-ai").show();
        	$("#stopFilter-other").hide();
		}
		
	}
	
	if(bookingRequestMode == '1' || bookingRequestMode == '2')
	{
		var otherFlightSize =  document.getElementById("otherFlightSize").value;
		
			if(otherFlightSize == '0')
			{
				$("#reApprovalBtn").show();
			}
		
			if(tabType == 'Other'){
				
				$("#ai-filter").hide();
	        	$("#other-filter").show();
				$("#ref-vs-not-ref-ai").hide();
            	$("#ref-vs-not-ref-other").show();
            	$("#serviceProvider-ai").hide();
	        	$("#serviceProvider-other").show();
	        	$("#stopFilter-ai").hide();
	        	$("#stopFilter-other").show();
			}else{
				$("#ai-filter").show();
	        	$("#other-filter").hide();
				$("#ref-vs-not-ref-ai").show();
            	$("#ref-vs-not-ref-other").hide();
            	$("#serviceProvider-ai").show();
	        	$("#serviceProvider-other").hide();
	        	$("#stopFilter-ai").show();
	        	$("#stopFilter-other").hide();
			}
		}
}


function filterFlights(ele){

var name=$(ele).attr('name');
	
  $('input:checkbox[name='+name+']').each(function() {   
	if($(this).is(':checked')){
	    $(".AI_"+$(this).val()).each(function(index,element) {
	       if($(element).is(":visible")){}
           else{$(element).show();}
        });
    }else{
        $(".AI_"+$(this).val()).each(function(index,element) {
           if($(element).is(":visible")){$(element).hide();}
           else{}
        });
    }
      
  });
	
	
}


function showHideFlight(flightClass, value){
//alert(flightClass + " " + value.checked);	
		if(!value.checked){
			var trs = $(flightClass);
			$(trs).each(function(index){				
				$(this).hide();
			
			});
		
		}else{
			var trs = $(flightClass);
			$(trs).each(function(index){				
				$(this).show();
			
			});
		}
		
		
		
	}




function change(id){
  //alert(id);	
	var cloneofClickedRow = $("#"+id).clone(true);		
	var updatableCells = $("#selectedRibbon").find('td');
	var totalCol = $(cloneofClickedRow).find('td').length;
	var row = $("#ribbon tr");
	$(row).each(function(i){		
		$(row).find('td').remove();	
	});
	//alert($(cloneofClickedRow).find('td').length);
	$(row).each(function(i){
		$(cloneofClickedRow).find('td').each(function(i){
			if(i > 0){
				if(totalCol == (i + 1)){
					var tempTd = $('<td></td>');
					$(tempTd).append($(this).find('span:first'));
					$(tempTd).append($('<span class="ribbon-button"></span>').text('Save'));
					$(row).append(tempTd);
				}else{
					$(row).append($(this));
				}
				
			}
		});
		
	
	});
	
}



 function filterFlight(prefAirlines, selClass, eleId){ 
 	//alert(eleId + " " +selClass + " " + prefAirlines); 	
 	//alert(eleId.checked + " " +selClass);
 	var idsContains = ""; 
 	var flightName = "";
 	var selectedTr = "";
 	var checedkAiFilter = null;
 	var trs = null;
 	if(prefAirlines === "airIndia"){
 		checedkAiFilter = $("#ai-filter input[type=checkbox]:checked");
 		trs = $("#tabairindia  table tbody tr"+selClass);
 	}else{
 		checedkAiFilter = $("#other-filter input[type=checkbox]:checked");
 		trs = $("#tabothers table tbody tr"+selClass);
 	}
 	//alert(checedkAiFilter);
 	//alert(checedkAiFilter.length);
 	
 	$(checedkAiFilter).each(function(index){ 		
 		flightName += $(this).val();
				});
 	flightName = new String(flightName);
	//alert(flightName);
	//alert(trs.length);
 		if(!eleId.checked){ 			
			$(trs).each(function(index){
				idsContains = new String($(this).attr('id'));
				//alert(idsContains);
				if(idsContains.search('ai') != -1){
					selectedTr = new String($(this).attr("class")).split(" ");
					if(flightName.search(selectedTr[0]) != -1){
						//alert("flight checked " +  selectedTr[0] + " " + flightName);
						$(this).hide();
			} else {
						
					}
					
				}else if(idsContains.search('other') != -1){
					selectedTr = new String($(this).attr("class")).split(" ");
					if(flightName.search(selectedTr[0]) != -1){
						//alert("flight checked " +  selectedTr[0] + " " + flightName);
						$(this).hide();
					}else{
						
					}
				}
			
				});
		
		}else{
			
			$(trs).each(function(index){
			idsContains = new String($(this).attr('id'));
			//alert(idsContains);
				if(idsContains.search('ai') != -1){
					//$(this).show();
					selectedTr = new String($(this).attr("class")).split(" ");
					if(flightName.search(selectedTr[0]) != -1){
						//alert("flight checked " +  selectedTr[0] + " " + flightName);
						$(this).show();
					}else{
						
					}
				}else if(idsContains.search('other') != -1){
					selectedTr = new String($(this).attr("class")).split(" ");
					if(flightName.search(selectedTr[0]) != -1){
						//alert("flight checked " +  selectedTr[0] + " " + flightName);
						$(this).show();
					}else{
						
			}
				}
			
		});
		}	
 }




function stopOverFilterFlight(element){
 	//alert(element);
 	//alert($(element).attr('class')); 
 	//alert(" selectedTab= " + selectedTab + " " + $(element).attr('id') + " " + $(element).val());
 	var currentClass = new String($(element).attr('class'));
 	
 	var checedkAiFilter = null;
 	var checkedRefundFilter = null;
 	var trs = null;
 	var selClass = null;
 	var checkedFlight = "";
 	var checkedRef = "";
 	var trClasses = "";
 	if(selectedTab === "Other"){
 		selClass = $(element).attr('class'); 
 		//alert(selClass + " " + selectedTab);
 		checedkAiFilter = $("#other-filter input[type=checkbox]:checked");
 		checkedRefundFilter =  $("#ref-vs-not-ref-other input[type=checkbox]:checked");
 		$(checedkAiFilter).each(function(index){ 		
 			checkedFlight += $(this).val() + " ";
 		});
 		
 		$(checkedRefundFilter).each(function(index){
 			checkedRef += $(this).val() + " ";
 		});
 		checkedFlight = new String(checkedFlight);
 		
 		//alert(checkedFlight + " " + checkedRef);
 		trs = $("#tabothers table tbody tr."+selClass); 
 		$(trs).each(function(index){
 			trClasses = $(this).attr("class").split(" ");;
 			//alert(trClasses[0] + " " + trClasses[1]);
 			
 			if(!element.checked){
 				if(checkedFlight.search(trClasses[0]) != -1 && checkedRef.search(trClasses[1]) != -1){
 					$(this).hide();
 				}else{
 				
 				}
 				
 			}else{
 				if(checkedFlight.search(trClasses[0]) != -1 && checkedRef.search(trClasses[1]) != -1){
 					$(this).show();
 				}else{
 				
 				}
 			}
 			
 		});
 	}else if(selectedTab === "AI"){  		
 		selClass = $(element).attr('class'); 
 		//alert(selClass + " " + selectedTab);
 		checedkAiFilter = $("#ai-filter input[type=checkbox]:checked");
 		checkedRefundFilter =  $("#ref-vs-not-ref-ai input[type=checkbox]:checked");
 		$(checedkAiFilter).each(function(index){ 		
 			checkedFlight += $(this).val() + " ";
 		});
 		
 		$(checkedRefundFilter).each(function(index){
 			checkedRef += $(this).val() + " ";
 		});
 		checkedFlight = new String(checkedFlight);
 		
 		//alert(checkedFlight + " " + checkedRef);
 		trs = $("#tabairindia table tbody tr."+selClass); 
 		$(trs).each(function(index){
 			trClasses = $(this).attr("class").split(" ");;
 			//alert(trClasses[0] + " " + trClasses[1]);
 			
 			if(!element.checked){
 				if(checkedFlight.search(trClasses[0]) != -1 && checkedRef.search(trClasses[1]) != -1){
 					$(this).hide();
 				}else{
 				
 				}
 				
 			}else{
 				if(checkedFlight.search(trClasses[0]) != -1 && checkedRef.search(trClasses[1]) != -1){
 					$(this).show();
 				}else{
 				
 				}
 			}
 			
 		});
 				
 	}
 	
 } 


function filterTimeBands()
{
	var objTime= document.getElementById("timeBand");
	var time;
	for(var i=0; i < objTime.options.length; i++)
    {   
        if(objTime.options[i].selected)
        {
            time=objTime.options[i].value;  
            $('#AITable .TimeBand_'+time).show();     
        } 
        else
        {
         	time=objTime.options[i].value;  
         	$('#AITable .TimeBand_'+time).hide();
        }          
    }
}

function filterFlightNew(checkId)
{
 var objCheckId = document.getElementById(checkId);
 
 if (objCheckId.checked == true){
    $('#AITable .AI_'+checkId).show(); 
        filterTimeBands();
  } else {
     $('#AITable .AI_'+checkId).hide();
  }
}

