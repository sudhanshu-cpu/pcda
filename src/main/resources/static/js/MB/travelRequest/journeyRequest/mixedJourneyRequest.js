function showMixedRequestPage()
{
	
	resetMixedJourney();
	var mixedPreference=$("#mixedPreference").val();
	if(mixedPreference==-1){
		document.getElementById("mixedRequestXform").style.display="none";
	}else{
		document.getElementById("mixedRequestXform").style.display="block";
		setMixedModeJourneyClassAndOtherData();
		populateSatationList();
		resetMixedBreakJourney();
		
	}
	
}


function showReturnMixedRequestPage()
{
	
	resetReturnMixedJourney();
	var mixedPreference=$("#returnMixedPreference").val();
	if(mixedPreference==-1){
		document.getElementById("mixedReturnRequestXform").style.display="none";
	}else{
		document.getElementById("mixedReturnRequestXform").style.display="block";
		setReturnMixedModeJourneyClassAndOtherData();
		populateReturnSatationList();
		resetReturnMixedBreakJourney();
		
	}
	
}

function resetMixedJourney(){

	$("#beakMixedJrnyDiv").html("<lable/>");		
	$("#beakMixedJrnyDiv").hide();
	$("#addRmvJrnyBtnDiv").hide();
	mixedRowCount=0;
	
}


function resetReturnMixedJourney(){

	$("#returnBeakMixedJrnyDiv").html("<lable/>");		
	$("#returnBeakMixedJrnyDiv").hide();
	$("#returnAddRmvJrnyBtnDiv").hide();
	mixedRowCount=0;
	
}

function setMixedModeJourneyClassAndOtherData(){
	
	
	var msg = $.parseJSON($('#travelerObj').val());
	
	var options = '<option value="">Select</option>'; 
	$.each(msg.entiltedClass,function(id, name){ 
		options += '<option value="' + id + '">' +name + '<\/option>';
	});
	 
	var airClassOptions = '<option value="">Select</option>';
	$.each(msg.airEntiltedClass,function(id, name){ 
		airClassOptions += '<option value="' + id + '">' +name + '<\/option>';
	});
	
	$("#mixedRailEntitledClass").html(options);
	$("#mixedAirEntitledClass").html(airClassOptions);
	
}

function setReturnMixedModeJourneyClassAndOtherData(){
	
	var msg = $.parseJSON($('#travelerObj').val());
	
	var options = '<option value="">Select</option>'; 
	$.each(msg.entiltedClass,function(id, name){ 
		options += '<option value="' + id + '">' +name + '<\/option>';
	});
	 
	var airClassOptions = '<option value="">Select</option>';
	$.each(msg.airEntiltedClass,function(id, name){ 
		airClassOptions += '<option value="' + id + '">' +name + '<\/option>';
	});
	
	$("#returnMixedRailEntitledClass").html(options);
	$("#returnMixedAirEntitledClass").html(airClassOptions);
	
}


function populateSatationList()
{
	//alert("inside populateSatationList:");
	
	var mixedPreference=$("#mixedPreference").val();
	
	var ramBaanCheck=false;
	var reqType=$("#reqType").val();
	if(reqType=='ramBaanBooking'){
		ramBaanCheck=true;
	}else{
		ramBaanCheck=false;
	}
	
	var taggCheck=$('#taggCheck').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var jrnyType=0;
	
	
	var orgOptions="";
	var destOptions="";
	
	$("#fixedOrigin").val(""); 
	$("#fixedDestination").val(""); 
	$("#trfixedOrgStn").html("<label/>"); 
	$("#trfixedDestStn").html("<label/>"); 
	$("#fixedOrgContainer").hide();
	$("#fixedDestContainer").hide();
	
	var authorityNo="";
	var authorityDate="";
	var isMixedModeRequest="NO";
	var checkRadio=false
	var ltcYear="";
	var destStn="";
		
	setFamilyDetailsMixed('');
	if(ramBaanCheck!=true)
	{
		
	   var oldReq=document.getElementsByName("oldRequest")
	   for(var i=0;i<oldReq.length;i++)
       {
	         if(oldReq[i].checked==true)
	         {
	         	 var auth=oldReq[i].value;
	         	 var authArr=auth.split("~");
	         	 authorityDate=authArr[0];
		         authorityNo=authArr[1];
		         destStn=authArr[2];
		         ltcYear=authArr[3];
		         isMixedModeRequest=authArr[5];
		         checkRadio=true
		    }
	   }
	  
		if(mixedPreference==0){
			//alert("Inside Case Of Rail-Air-jrnyType-"+jrnyType);
			orgOptions=getRailFromStnOption(jrnyType);
			destOptions=getAirToAirportOption(jrnyType);	
		}
	
		if(mixedPreference==1){
			//alert("Inside Case Of Air-Rail");
			orgOptions=getAirFromAirportOption(jrnyType);
			destOptions=getRailToStnOption(jrnyType);
		}
		
		if(mixedPreference==2){
			//alert("Inside Case Of Rail-Air-Rail");
			orgOptions=getRailFromStnOption(jrnyType);
			destOptions=getRailToStnOption(jrnyType);
		}
		
		$("#fixedOrgStnDropDown").html(orgOptions);
        $("#fixedDestStnDropDown").html(destOptions);  
      
        $("#fixedOrgStnDropDown").show();       
       	$("#fixedDestStnDropDown").show();
      
     
    				  
	}	// End of if(ramBaanCheck!="true")		  

}


function populateReturnSatationList()
{
	//alert("inside populateSatationList:");
	
	var mixedPreference=$("#returnMixedPreference").val();
	
	var ramBaanCheck=false;
	var reqType=$("#reqType").val();
	if(reqType=='ramBaanBooking'){
		ramBaanCheck=true;
	}else{
		ramBaanCheck=false;
	}
	
	var taggCheck=$('#taggCheck').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var jrnyType=1;
	
	
	var orgOptions="";
	var destOptions="";
	
	$("#returnFixedOrigin").val(""); 
	$("#returnFixedDestination").val(""); 
	$("#returnTrfixedOrgStn").html("<label/>"); 
	$("#returnTrfixedDestStn").html("<label/>"); 
	$("#returnFixedOrgContainer").hide();
	$("#returnFixedDestContainer").hide();
	
	var authorityNo="";
	var authorityDate="";
	var isMixedModeRequest="NO";
	var checkRadio=false
	var ltcYear="";
	var destStn="";
		
	setReturnFamilyDetailsMixed('');
	if(ramBaanCheck!=true)
	{
		
	   var oldReq=document.getElementsByName("oldRequest")
	   for(var i=0;i<oldReq.length;i++)
       {
	         if(oldReq[i].checked==true)
	         {
	         	 var auth=oldReq[i].value;
	         	 var authArr=auth.split("~");
	         	 authorityDate=authArr[0];
		         authorityNo=authArr[1];
		         destStn=authArr[2];
		         ltcYear=authArr[3];
		         isMixedModeRequest=authArr[5];
		         checkRadio=true
		    }
	   }
	  
		if(mixedPreference==0){
			//alert("Inside Case Of Rail-Air-jrnyType-"+jrnyType);
			orgOptions=getRailFromStnOption(jrnyType);
			destOptions=getAirToAirportOption(jrnyType);	
		}
	
		if(mixedPreference==1){
			//alert("Inside Case Of Air-Rail");
			orgOptions=getAirFromAirportOption(jrnyType);
			destOptions=getRailToStnOption(jrnyType);
		}
		
		if(mixedPreference==2){
			//alert("Inside Case Of Rail-Air-Rail");
			orgOptions=getRailFromStnOption(jrnyType);
			destOptions=getRailToStnOption(jrnyType);
		}
		
		
		$("#returnFixedOrgStnDropDown").html(orgOptions);
        $("#returnFixedDestStnDropDown").html(destOptions);  
      
        $("#returnFixedOrgStnDropDown").show();       
       	$("#returnFixedDestStnDropDown").show(); 
      
       
    				  
	}	// End of if(ramBaanCheck!="true")		  

}


function setFamilyDetailsMixed(msg)
{
	
	//alert("Inside setFamilyDetailsMixed");
	
	var taggCheck=$('#taggCheck').val();
	var oldDutyStn=$("#oldDutyStn").val(); 
	var newDutyStn=$("#newDutyStn").val(); 
	var oldSprNRS=$("#oldSprNRS").val(); 
	var newSprNRS=$("#newSprNRS").val(); 
	
	//alert("setFamilyDetailsMixed:taggCheck-"+taggCheck+"|oldDutyStn-"+oldDutyStn+"|newDutyStn-"+newDutyStn+"|oldSprNRS-"+oldSprNRS+"|newSprNRS-"+newSprNRS);
	
	var taggedLtcYear='';
	if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
	if(msg=="")
	{
		msg=$('#travelerObj').val();
	}
	
	 msg = $.parseJSON(msg);
	
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();

     $.each(msg.familyDetail, function(i,obj){
	
			var JourneyCheck;
			var JourneyCheckRet;
		    
		    if($('#removeFrmList'+i).is(":checked"))
		    {
				var relation=document.getElementById("relationCheck"+i).value;
				if(relation=="Self")
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
			
		    //alert("setFamilyDetailsMixed:travelTypeText.indexOf('LTC')["+travelTypeText.indexOf('LTC')+"] taggCheck-"+taggCheck+"||taggedLtcYear-"+taggedLtcYear);
			
			//if(travelTypeText.indexOf('LTC')>-1  && taggCheck!="true")
			if(travelTypeText.indexOf('LTC')>-1)
			{
				var k=0;
				$.each(obj.yearWise, function(index,objYear){
			
					var xmlLtcYear=objYear.year;
			
					if(taggedLtcYear!='')
					{
						if(xmlLtcYear==taggedLtcYear)
						{
							//alert(xmlLtcYear+","+taggedLtcYear+" when xmlLtcYear = taggedLtcYear ");
							JourneyCheck = objYear.journeyCheck;
							JourneyCheckRet = objYear.journeyCheckRet;
							
								if(JourneyCheck=='true')
								{
									//alert(jrnyType+","+JourneyCheck+" when 0 and true ");
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
 									$("#reasonDiv"+i).hide();
								}
								else
								{
									//alert(jrnyType+","+JourneyCheck+" when 0 and not true ");
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"  id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#reasonDiv"+i).show();
								}
						
		  				}
				
					} //End of if(taggedLtcYear!='')
					else
					{
						//alert(xmlLtcYear+","+taggedLtcYear+" when taggedLtcYear is not present k="+k);
						if(k==1)	
						{
							JourneyCheck = objYear.journeyCheck;
							JourneyCheckRet = objYear.journeyCheckRet;
							
								if(JourneyCheck=='true')
								{
									//alert(jrnyType+","+JourneyCheck+" when 1 and true ");
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
				     				$("#reasonDiv"+i).hide();
								}
								else
								{
									//alert(jrnyType+","+JourneyCheck+" when 0 and not true ");
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"  id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#reasonDiv"+i).show();
								}
									
						}
					} //End of else case
			
					k=k+1;
			
				}); //End of YearWise loop
			
		 } // end of LTC Case		
		 else
		 {
				
				JourneyCheck=obj.journeyCheck;
				//alert("["+i+"]inside the else non ltc case JourneyCheck-"+JourneyCheck);
				
				if(JourneyCheck=='true')
				{
						//alert("["+i+"]inside the else case true-"+$("#reasonDiv"+i));
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>');
		     			$("#reasonDiv"+i).hide();
				}
				else
				{
					//alert("["+i+"]inside the else case false-"+$("#reasonDiv"+i));
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false" />');
 						$("#reasonDiv"+i).show();
				}
		 }
				 
	}); //End of FamilyDetailSeq loop
	
	
}



function setReturnFamilyDetailsMixed(msg)
{
	
	//alert("Inside setFamilyDetailsMixed");
	
	var taggCheck=$('#taggCheck').val();
	var oldDutyStn=$("#oldDutyStn").val(); 
	var newDutyStn=$("#newDutyStn").val(); 
	var oldSprNRS=$("#oldSprNRS").val(); 
	var newSprNRS=$("#newSprNRS").val(); 
	
	//alert("setFamilyDetailsMixed:taggCheck-"+taggCheck+"|oldDutyStn-"+oldDutyStn+"|newDutyStn-"+newDutyStn+"|oldSprNRS-"+oldSprNRS+"|newSprNRS-"+newSprNRS);
	
	var taggedLtcYear='';
	if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
	var jrnyType=1;
	
	if(msg=="")
	{
		msg=$('#travelerObj').val();
	}
	msg=$.parseJSON(msg);
	
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();

     $.each(msg.familyDetail, function(i,obj){
	
			var JourneyCheck;
			var JourneyCheckRet;
			var relation=document.getElementById("returnRelationCheck"+i).value
		    
		    if($('#returnRemoveFrmList'+i).is(":checked"))
		    {
				if(relation=="Self")
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
			
		  
			//if(travelTypeText.indexOf('LTC')>-1  && taggCheck!="true")
			if(travelTypeText.indexOf('LTC')>-1)
			{
				var k=0;
				$.each(obj.yearWise, function(index,objYear){
			
				var xmlLtcYear=objYear.year;
			
					if(taggedLtcYear!='')
					{
						if(xmlLtcYear==taggedLtcYear)
						{
							//alert(xmlLtcYear+","+taggedLtcYear+" when xmlLtcYear = taggedLtcYear ");
							JourneyCheck = objYear.journeyCheck;
							JourneyCheckRet = objYear.journeyCheckRet;
							
								if(JourneyCheckRet=='true')
								{
									//alert(jrnyType+","+JourneyCheck+" when 1 and true ");
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>')
									$("#returnReasonDiv"+i).hide();
								}
								else
								{
									//alert(jrnyType+","+JourneyCheck+" when 1 and not true ");
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#returnReasonDiv"+i).show();
								}
					
		  				}
				
					} //End of if(taggedLtcYear!='')
					else
					{
						//alert(xmlLtcYear+","+taggedLtcYear+" when taggedLtcYear is not present k="+k);
						if(k==1)	
						{
							JourneyCheck = objYear.journeyCheck;
							JourneyCheckRet = objYear.journeyCheckRet;
							
					
								if(JourneyCheckRet=='true')
								{
									//alert(jrnyType+","+JourneyCheck+" when 1 and true ");
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>')
									$("#returnReasonDiv"+i).hide();
								}
								else
								{
									//alert(jrnyType+","+JourneyCheck+" when 1 and not true ");
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#returnReasonDiv"+i).show();
								}
							
						}
					} //End of else case
			
					k=k+1;
			
				}); //End of YearWise loop
			
		 } // end of LTC Case		
		 else
		 {
				
				JourneyCheck=obj.journeyCheck;
				//alert("["+i+"]inside the else non ltc case JourneyCheck-"+JourneyCheck);
				
				if(JourneyCheck=='true')
				{
						//alert("["+i+"]inside the else case true-"+$("#reasonDiv"+i));
						$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>');
		     			$("#returnReasonDiv"+i).hide();
				}
				else
				{
					//alert("["+i+"]inside the else case false-"+$("#reasonDiv"+i));
						$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false" />');
 						$("#returnReasonDiv"+i).show();
				}
		 }
				 
	}); //End of FamilyDetailSeq loop
	
	
}


function getRailFromStnOption(value)
{
	//alert("Inside getRailFromStnOption");
 	var options="";
 	var oldDutyStn=$("#oldDutyStn").val(); 
	var newDutyStn=$("#newDutyStn").val(); 
	var oldSprNRS=$("#oldSprNRS").val(); 
	var newSprNRS=$("#newSprNRS").val(); 
	
	var msg = $.parseJSON($('#travelerObj').val());
	
	//alert("getRailFromStnOption-"+msg);
 	if(value == 0){
 		options += '<select id="fixedOrgStationDD" name="fixedOrgStationDD" class="combo" onchange="setfixedOrgStnValue(this.value,0)"><option value="">Select</option>'
 	} else if(value == 1){
 		options += '<select id="returnFixedOrgStationDD" name="returnFixedOrgStationDD" class="combo" onchange="setReturnFixedOrgStnValue(this.value,0)"><option value="">Select</option>'
 	}
 	
 	$.each(msg.fromStationList, function(index,obj){
	 if(obj.jrnyType==value){
	
		var FromStation = obj.fromStation;
		var FromStationType = obj.fromStationType;
		
		if(FromStationType=="New Duty Station" && newDutyStn!=undefined)
			options += '<option value="' + newDutyStn + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="New SPR" && newSprNRS!=undefined)
			options += '<option value="' + newSprNRS + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="Old SPR" && oldSprNRS!=undefined)
			options += '<option value="' + oldSprNRS + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="Old Duty Station" && oldDutyStn!=undefined)
			options += '<option value="' + oldDutyStn + '">' +FromStationType + '<\/option>';
		else
			options += '<option value="' + FromStation + '">' +FromStationType + '<\/option>';
			
		}	
	}); 
	
	if(options=="<select>"){
		options = 'No From Station Exists In Profile';
	}else{
      options += '</select>'
	}    
	//alert(options);
 	return options;	
}


function getAirToAirportOption(value)
{
 	var options="";
 	var oldDutyNap=$("#oldDutyNap").val(); 
	var newDutyNap=$("#newDutyNap").val(); 
	var oldSprNap=$("#oldSprNap").val(); 
	var newSprNap=$("#newSprNap").val(); 
 	
 	var msg = $.parseJSON($('#travelerObj').val());
 	
	
	if(value == 0){
		options += '<select id="fixedDestStationDD" name="fixedDestStationDD" class="combo" onchange="setfixedDestStnValue(this.value,1)"><option value="">Select</option>'
	}else if(value == 1){
		options += '<select id="returnFixedDestStationDD" name="returnFixedDestStationDD" class="combo" onchange="setReturnFixedDestStnValue(this.value,1)"><option value="">Select</option>'
	}
	
	$.each(msg.airStationListTo, function(index,obj){
	 if(obj.jrnyType==value){
	
		var ToStation = obj.airToStation;
		var ToStationType = obj.airToStationType;
		
		if(ToStationType=="New Duty Station" && newDutyNap!=undefined)
		options += '<option value="' + newDutyNap + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="New SPR" && newSprNap!=undefined)
		options += '<option value="' + newSprNap + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="Old SPR" && oldSprNap!=undefined)
		options += '<option value="' + oldSprNap + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="Old Duty Station" && oldDutyNap!=undefined)
		options += '<option value="' + oldDutyNap + '">' +ToStationType + '<\/option>';
		else
		options += '<option value="' + ToStation + '">' +ToStationType + '<\/option>';
		
		}
 	}); 

 	if(options=="<select>"){
		options = 'No TO Station Exists In Profile';
 	}else{
    	options +="</select>"
 	}
 	return options;	
}

function setfixedOrgStnValue(stnValue,type)
{
 // alert("Inside setfixedOrgStnValue stnValue="+stnValue+"|type-"+type);
 
 $("#from_Station_Id").val($('#fixedOrgStationDD option:selected').text());
 $("#removeFrmList0").attr('checked', false);
  resetMixedJourney();
  var stnStr="";
  if(stnValue=='AnyStation')
  {
  	
  	var containerHtml="";
   	if(type==0){
   		//rail;
   		containerHtml+='<input type="text" name="fixedOrigin" class="txtfldM" value="" id="fixedOrigin" size="50" autocomplete="off" onkeyup="populateStations(\'fixedOrigin\',\'fixedOrgSuggestion\',\'fixedOrgSuggestionsList\')" onblur="fillFixedOrgStn(\'\');" /> ';
   	}
   	if(type==1){
		containerHtml+='<input type="text" name="fixedOrigin" class="txtfldM" value="" id="fixedOrigin" size="50" autocomplete="off" onkeyup="populateAirport(\'fixedOrigin\',\'fixedOrgSuggestion\',\'fixedOrgSuggestionsList\')" onblur="fillFixedOrgStn(\'\');" /> ';
   	}
	containerHtml+='<div class="suggestionsBox" id="fixedOrgSuggestion" style="display: none;">';
		containerHtml+='<div class="suggestionList" id="fixedOrgSuggestionsList"></div>';
	containerHtml+='</div>';
	
	//alert(containerHtml);
	$("#fixedOrgContainer").html(containerHtml);
   	$("#fixedOrgContainer").show(); 
   	
   	$("#trfixedOrgStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="trfixedOrgStn"><td><input type="hidden" name="fixedOrigin" value="'+stnValue +'" class="txtfldM" id="fixedOrigin"  /><label>'+stnValue +'</label></td></div>'
	   $("#fixedOrigin").val(stnValue);   
	   $("#trfixedOrgStn").html(stnStr); 
	   $("#trfixedOrgStn").show(); 
	   $("#fixedOrgContainer").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="trfixedOrgStn"><td>';
	   stnStr +='<select id="fixedOrigin" name="fixedOrigin" class="comboMixed" onchange="fillfixedOrgStn(this.value);">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#trfixedOrgStn").html(stnStr); 
	   $("#trfixedOrgStn").show(); 
	   $("#fixedOrgContainer").hide();
	  		
	}
  }
	   
}



function setfixedDestStnValue(stnValue,type)
{
 	
  	//alert("Inside setfixedOrgStnValue stnValue="+stnValue+"|type-"+type);
 
    $("#to_Station_Id").val($('#fixedDestStationDD option:selected').text());
  	$("#removeFrmList0").attr('checked', false);
  	resetMixedJourney();
  	
  	var stnStr="";
    if(stnValue=='AnyStation')
    {
    	
    	var containerHtml="";
	   	if(type==0){
	   		//rail;
	   		containerHtml+='<input type="text" name="fixedDestination" class="txtfldM" value="" id="fixedDestination" size="50" autocomplete="off" onkeyup="populateStations(\'fixedDestination\',\'fixedDestSuggestion\',\'fixedDestSuggestionsList\')" onblur="fillFixedDestStn(\'\');" /> ';
	   	}
	   	if(type==1){
			containerHtml+='<input type="text" name="fixedDestination" class="txtfldM" value="" id="fixedDestination" size="50" autocomplete="off" onkeyup="populateAirport(\'fixedDestination\',\'fixedDestSuggestion\',\'fixedDestSuggestionsList\')" onblur="fillFixedDestStn(\'\');" /> ';
	   	}
		containerHtml+='<div class="suggestionsBox" id="fixedDestSuggestion" style="display: none;">';
			containerHtml+='<div class="suggestionList" id="fixedDestSuggestionsList"></div>';
		containerHtml+='</div>';
		
		//alert(containerHtml);
		$("#fixedDestContainer").html(containerHtml);
    	$("#fixedDestContainer").show();
        $("#trfixedDestStn").hide(); 
     
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="trfixedDestStn"><td><input type="hidden" name="fixedDestination" value="'+stnValue +'"  class="txtfldM" id="fixedDestination" /><label>'+stnValue +'</label><td></div>'
	    	$("#fixedDestination").val(stnValue);   
	      	$("#trfixedDestStn").html(stnStr); 
	      	$("#trfixedDestStn").show();
	    	$("#fixedDestContainer").hide(); +
				''
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="trfixedDestStn"><td>';
	   
	   		stnStr +='<select id="fixedDestination" name="fixedDestination" class="comboMixed" onchange="fillfixedDestStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#trfixedDestStn").html(stnStr); 
		    $("#trfixedDestStn").show();
		    $("#fixedDestContainer").hide();
	  	
	  	}
	
	  }
}




function setReturnFixedOrgStnValue(stnValue,type)
{
 // alert("Inside setfixedOrgStnValue stnValue="+stnValue+"|type-"+type);
 
 $("#from_Return_Station_Id").val($('#returnFixedOrgStationDD option:selected').text());
 $("#returnRemoveFrmList0").attr('checked', false);
  resetReturnMixedJourney();
  
  var stnStr="";
  if(stnValue=='AnyStation')
  {
  	
  	var containerHtml="";
   	if(type==0){
   		//rail;
   		containerHtml+='<input type="text" name="returnFixedOrigin" class="txtfldM" value="" id="returnFixedOrigin" size="50" autocomplete="off" onkeyup="populateReturnStations(\'returnFixedOrigin\',\'returnFixedOrgSuggestion\',\'returnFixedOrgSuggestionsList\')" onblur="fillFixedReturnOrgStn(\'\');" /> ';
   	}
   	if(type==1){
		containerHtml+='<input type="text" name="returnFixedOrigin" class="txtfldM" value="" id="returnFixedOrigin" size="50" autocomplete="off" onkeyup="populateReturnAirport(\'returnFixedOrigin\',\'returnFixedOrgSuggestion\',\'returnFixedOrgSuggestionsList\')" onblur="fillFixedReturnOrgStn(\'\');" /> ';
   	}
	containerHtml+='<div class="suggestionsBox" id="returnFixedOrgSuggestion" style="display: none;">';
		containerHtml+='<div class="suggestionList" id="returnFixedOrgSuggestionsList"></div>';
	containerHtml+='</div>';
	
	//alert(containerHtml);
	$("#returnFixedOrgContainer").html(containerHtml);
   	$("#returnFixedOrgContainer").show(); 
   	
   	$("#returnTrfixedOrgStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="returnTrfixedOrgStn"><td><input type="hidden" name="returnFixedOrigin" value="'+stnValue +'" class="txtfldM" id="returnFixedOrigin"  /><label>'+stnValue +'</label></td></div>'
	   $("#returnFixedOrigin").val(stnValue);   
	   $("#returnTrfixedOrgStn").html(stnStr); 
	   $("#returnTrfixedOrgStn").show(); 
	   $("#returnFixedOrgContainer").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="returnTrfixedOrgStn"><td>';
	   stnStr +='<select id="returnFixedOrigin" name="returnFixedOrigin" class="comboMixed" onchange="fillReturnFixedOrgStn(this.value);">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#returnTrfixedOrgStn").html(stnStr); 
	   $("#returnTrfixedOrgStn").show(); 
	   $("#returnFixedOrgContainer").hide();
	  		
	}
  }
	   
}



function setReturnFixedDestStnValue(stnValue,type)
{
	
 	var stnStr="";
  	//alert("Inside setfixedOrgStnValue stnValue="+stnValue+"|type-"+type);
 
    $("#to_Return_Station_Id").val($('#returnFixedDestStationDD option:selected').text());
  	$("#returnRemoveFrmList0").attr('checked', false);
  	resetReturnMixedJourney();
    if(stnValue=='AnyStation')
    {
    	
    	var containerHtml="";
	   	if(type==0){
	   		//rail;
	   		containerHtml+='<input type="text" name="returnFixedDestination" class="txtfldM" value="" id="returnFixedDestination" size="50" autocomplete="off" onkeyup="populateReturnStations(\'returnFixedDestination\',\'returnFixedDestSuggestion\',\'returnFixedDestSuggestionsList\')" onblur="fillFixedReturnDestStn(\'\');" /> ';
	   	}
	   	if(type==1){
			containerHtml+='<input type="text" name="returnFixedDestination" class="txtfldM" value="" id="returnFixedDestination" size="50" autocomplete="off" onkeyup="populateReturnAirport(\'returnFixedDestination\',\'returnFixedDestSuggestion\',\'returnFixedDestSuggestionsList\')" onblur="fillFixedReturnDestStn(\'\');" /> ';
	   	}
		containerHtml+='<div class="suggestionsBox" id="returnFixedDestSuggestion" style="display: none;">';
			containerHtml+='<div class="suggestionList" id="returnFixedDestSuggestionsList"></div>';
		containerHtml+='</div>';
		
		//alert(containerHtml);
		$("#returnFixedDestContainer").html(containerHtml);
    	$("#returnFixedDestContainer").show();
        $("#returnTrfixedDestStn").hide(); 
     
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="returnTrfixedDestStn"><td><input type="hidden" name="returnFixedDestination" value="'+stnValue +'"  class="txtfldM" id="returnFixedDestination" /><label>'+stnValue +'</label><td></div>'
	    	$("#returnFixedDestination").val(stnValue);   
	      	$("#returnTrfixedDestStn").html(stnStr); 
	      	$("#returnTrfixedDestStn").show();
	    	$("#returnFixedDestContainer").hide(); 
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="trfixedDestStn"><td>';
	   
	   		stnStr +='<select id="returnFixedDestination" name="returnFixedDestination" class="comboMixed" onchange="fillReturnFixedDestStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#returnTrfixedDestStn").html(stnStr); 
		    $("#returnTrfixedDestStn").show();
		    $("#returnFixedDestContainer").hide();
	  	
	  	}
	
	  }
}


function resetMixedBreakJourney(){
	$("#breakJourneyClicked").val("NO");
	$("#flightCode").val("");
	$("#flightKey").val("");
	$("#reasonCode").val("");
}

function resetReturnMixedBreakJourney(){
	$("#returnBreakJourneyClicked").val("NO");
	$("#returnFlightCode").val("");
	$("#returnFlightKey").val("");
	$("#returnReasonCode").val("");
}


function isReturnMixedTatkalSet(chk)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
	if(chk.checked==true)
	{
		var tatkalInst=getTatkalTicketInstruction();	
		tbox = new LightFace
	 	({ 
				title: "Tatkal Journey Instruction.",
				width: 600,
				height: 200,
				content: tatkalInst,
				buttons: [
							{ 
								title: 'Close', event: function() 
								{ 
									this.destroy(); 
								} 
							}
						]
		});
		tbox.open();
	
		$("#returnIsTatkalFlagMixed").val("1");
	  	$('.validReturnIdValCol').show();
	   	$('#validReturnIdInfoCol').show();
	   	
	}
	else
	{
		$("#returnIsTatkalFlagMixed").val("0");
		$('.validReturnIdValCol').hide();
	   	$('#validReturnIdInfoCol').hide();
	}
	
}


function isMixedTatkalSet(chk)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
	if(chk.checked==true)
	{
		var tatkalInst=getTatkalTicketInstruction();	
		tbox = new LightFace
	 	({ 
				title: "Tatkal Journey Instruction.",
				width: 600,
				height: 200,
				content: tatkalInst,
				buttons: [
							{ 
								title: 'Close', event: function() 
								{ 
									this.destroy(); 
								} 
							}
						]
		});
		tbox.open();
	
		$("#isTatkalFlagMixed").val("1");
	  	$('.validIdValCol').show();
	   	$('#validIdInfoCol').show();
	   	
	}
	else
	{
		$("#isTatkalFlagMixed").val("0");
		$('.validIdValCol').hide();
	   	$('#validIdInfoCol').hide();
	}
	
}



function initReturnMixedJourneyRow()
{
	var mixedJrnySource=$('#returnFixedOrigin').val();
	var mixedJrnyDestination=$('#returnFixedDestination').val();
	//alert(mixedJrnySource+"|"+mixedJrnyDestination);
	var isFiledSet=true;
	if(mixedJrnySource=="" || mixedJrnySource===undefined)
	{
	   alert("Please Enter From Station.");
	   $('#returnFixedOrigin').focus();
	   isFiledSet=false;
	   return;
	}
	if(mixedJrnyDestination=="" || mixedJrnyDestination===undefined)
	{
	   alert("Please Enter To Station.");
	   $('#returnFixedDestination').focus();
	   isFiledSet=false;
	   return;
	}
	
	var entitledClass=$('#returnMixedRailEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Rail Entitled Class")
		$('#returnMixedRailEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	var entitledAirClass=$('#returnMixedAirEntitledClass').val();

	if(entitledAirClass=="" )
	{
		alert("Please Choose Air Entitled Class")
		$('#returnMixedAirEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
	
		var mixedJrnyDtlsTableTxt="";
		mixedJrnyDtlsTableTxt+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch" id="returnMixedJrnyDtlsTable" name="returnMixedJrnyDtlsTable">';
			mixedJrnyDtlsTableTxt+='<tr>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="10%">Journey Mode</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="30%">From</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="30%">To</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="15%">Journey Date</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="10%">Via Route</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="5%"></td>';
			mixedJrnyDtlsTableTxt+='</tr>';
		mixedJrnyDtlsTableTxt+='</table>';
		$("#returnBeakMixedJrnyDiv").html(mixedJrnyDtlsTableTxt);		
		$("#returnBeakMixedJrnyDiv").show();
		addReturnMixedJourneyRow();
		$("#returnBreakJourneyClicked").val("YES");
	}
}



function initMixedJourneyRow()
{
	var mixedJrnySource=$('#fixedOrigin').val();
	var mixedJrnyDestination=$('#fixedDestination').val();
	//alert(mixedJrnySource+"|"+mixedJrnyDestination);
	var isFiledSet=true;
	if(mixedJrnySource=="" || mixedJrnySource===undefined)
	{
	   alert("Please Enter From Station.");
	   $('#fixedOrigin').focus();
	   isFiledSet=false;
	   return;
	}
	if(mixedJrnyDestination=="" || mixedJrnyDestination===undefined)
	{
	   alert("Please Enter To Station.");
	   $('#fixedDestination').focus();
	   isFiledSet=false;
	   return;
	}
	
	var entitledClass=$('#mixedRailEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Rail Entitled Class")
		$('#mixedRailEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	var entitledAirClass=$('#mixedAirEntitledClass').val();

	if(entitledAirClass=="" )
	{
		alert("Please Choose Air Entitled Class")
		$('#mixedAirEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
	
		var mixedJrnyDtlsTableTxt="";
		mixedJrnyDtlsTableTxt+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch" id="mixedJrnyDtlsTable" name="mixedJrnyDtlsTable">';
			mixedJrnyDtlsTableTxt+='<tr>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="10%">Journey Mode</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="30%">From</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="30%">To</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="15%">Journey Date</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="10%">Via Route</td>';
			mixedJrnyDtlsTableTxt+='<td class="label" width="5%"></td>';
			mixedJrnyDtlsTableTxt+='</tr>';
		mixedJrnyDtlsTableTxt+='</table>';
		$("#beakMixedJrnyDiv").html(mixedJrnyDtlsTableTxt);		
		$("#beakMixedJrnyDiv").show();
		addMixedJourneyRow();
		$("#breakJourneyClicked").val("YES");
	}
}


function addMixedJourneyRow()
{
	 var mixedJrnySource="";var mixedJrnyDestination="";
     var mixedPreference=$("#mixedPreference").val();
     var mixedJrnyMode="";
     
     //alert("addMixedJourneyRow-mixedPreference-"+mixedPreference+"||mixedRowCount-"+mixedRowCount+"|jrnyType="+jrnyType);
     	
     var tbl = document.getElementById('mixedJrnyDtlsTable');
	 var lastRow = tbl.rows.length;
	 var isFiledSet=true;
	 
     if(mixedPreference==0)
     {
     		 var mixedJrnySource=$('#fixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="10%"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode0" value="0" >Rail</td>';
			 rowHtmlTxt+='<td width="30%"><input type="hidden" name="mixedJrnySource" id="mixedJrnySourceOne0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 
			 rowHtmlTxt+='<td width="30%">' +
					'<input type="text" name="mixedJrnyDestination" class="txtfldM" value="" id="mixedJrnyDestinationOne0" autocomplete="off" onkeyup="populateStations(\'mixedJrnyDestinationOne0\',\'mixedJrnyDestSuggestionOne0\',\'mixedJrnyDestSuggestionsListOne0\')"  /> ';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnyDestSuggestionOne0" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnyDestSuggestionsListOne0"></div>';
			 rowHtmlTxt+='</div></td>';
			
			rowHtmlTxt+='<td width="15%"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDateOne0" value="" size="11" readonly  autocomplete="off"></td>';
			rowHtmlTxt+='<td width="10%"><input type="checkbox" name="viaRouteMixed" id="viaRouteMixedOne0" onClick="validateViaCheck(this,\'mixedJrnySourceOne0\',\'mixedJrnyDestinationOne0\',\'mixedJrnyJourneyDateOne0\',\'viaRouteMixedOne0\',\'One\') "/></td>';
			rowHtmlTxt+='<td width="5%" nowrap="true" >' +
						'<input type="button" value="Search" class="butn" onClick="showTrainList(\'mixedJrnySourceOne0\',\'mixedJrnyDestinationOne0\',\'mixedJrnyJourneyDateOne0\')"/>' +
						'</td>';
		
			row.innerHTML=rowHtmlTxt;

			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="6"><div width="100%" align="left"  id="mixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
			
			var mixedJrnyDestination=$('#fixedDestination').val();
     		lastRow = tbl.rows.length;
			var row2=tbl.insertRow(lastRow);
			rowHtmlTxt="";
			rowHtmlTxt+='<td width="15%"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode1" value="1" >Air</td>';
			
			 rowHtmlTxt+='<td width="35%">' +
					  '<div id="mixedJrnyAirDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%"><input type="hidden" name="mixedJrnyDestination" id="mixedJrnyDestination1" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 rowHtmlTxt+='<td width="15%"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDate1" onblur="isDateChange(this.id);" value="" size="11" readonly  autocomplete="off"></td>';
			 rowHtmlTxt+='<td width="10%"></td>';
			 rowHtmlTxt+='<td width="5%"><input type="button"  value="Search" class="butn" onClick="showFlightList(\'mixedJrnySourceAir\',\'mixedJrnyDestination1\',\'mixedJrnyJourneyDate1\')"/></td>';
			
			 row2.innerHTML=rowHtmlTxt;
			 
			lastRow = tbl.rows.length;
			var airViaDivRow =tbl.insertRow(lastRow);
			airViaDivRow.id='mixedLTCAirConnectingFlight';
			airViaDivRow.style='display:none';
			airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="mixedLTCAirConnectingFlightDtls"></div></td>';
			 
		$("#mixedJrnyJourneyDateOne0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			
			onShow: function() {
				$("#mixedJrnyJourneyDateOne0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#mixedJrnyJourneyDateOne0").datetimepicker("setOptions", {
					minDate: minData
				});

				$("#mixedJrnyJourneyDateOne0").datetimepicker("setOptions", {
					maxDate: maxData
				});
			},
			 onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#mixedJrnyJourneyDate1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate
				});
				$("#mixedJrnyJourneyDate1").datetimepicker("setOptions", {
					minDate: selected
				});
					 }
		});
				
				
			$("#mixedJrnyJourneyDate1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#mixedJrnyJourneyDate1").val("");
					 }
			  });
     }
     
     if(mixedPreference==1)
     {
     	
     		 var mixedJrnySource=$('#fixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode0" value="1" >Air</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="mixedJrnySource" id="mixedJrnySource0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 
			 var orgTextValue=$('#fixedOrgStationDD option:selected').text();
	 	 	 if(orgTextValue=='Fixed'){
	 			// get All Fixed From Station 
	 			 rowHtmlTxt+='<td width="35%" class="lablevalue">';
	 			 rowHtmlTxt+='<select name="mixedJrnyDestination" id="mixedJrnyDestination0" class="comboMixed" onChange="fillStaionValue(this.options[this.selectedIndex].value,\'Fixed\',\'\')" >';
	 			 var airportOption=populateFixedDestAirportOption(0);
	 			 rowHtmlTxt+=airportOption;
	 			 rowHtmlTxt+='</select>';
				 rowHtmlTxt+='</td>';
			 }else
	 	 	 {
		 	 	 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
						'<input type="text" name="mixedJrnyDestination" class="txtfldM" value="" id="mixedJrnyDestination0" autocomplete="off" onkeyup="populateAirport(\'mixedJrnyDestination0\',\'mixedJrnyDestSuggestion0\',\'mixedJrnyDestSuggestionsList0\')"  /> ';		
				 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnyDestSuggestion0" style="display: none;">';
				 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnyDestSuggestionsList0"></div>';
				 rowHtmlTxt+='</div></td>';
				 	
	 	 	 }
			
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDate0" onblur="isDateChange(this.id);" value="" size="11" readonly></td>';
			rowHtmlTxt+='<td width="10%" class="lablevalue"></td>';
			rowHtmlTxt+='<td width="5%" class="lablevalue"><input type="button" value="Search" class="butn" onClick="showFlightList(\'mixedJrnySource0\',\'mixedJrnyDestination0\',\'mixedJrnyJourneyDate0\')"/></td>';
			
			row.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var airViaDivRow =tbl.insertRow(lastRow);
			airViaDivRow.id='mixedLTCAirConnectingFlight';
			airViaDivRow.style='display:none';
			airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="mixedLTCAirConnectingFlightDtls"></div></td>';
	
	 		var mixedJrnyDestination=$('#fixedDestination').val();
	 		 
	 		lastRow = tbl.rows.length;
     		var row2=tbl.insertRow(lastRow);
			rowHtmlTxt="";
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode1" value="0" >Rail</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					 '<div id="mixedJrnyRailDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="mixedJrnyDestination" id="mixedJrnyDestinationOne1" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDateOne1" value="" size="11" readonly></td>';
			 rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="viaRouteMixed" id="viaRouteMixedOne1" onClick="validateViaCheck(this,\'mixedJrnySourceOne1\',\'mixedJrnyDestinationOne1\',\'mixedJrnyJourneyDateOne1\',\'viaRouteMixedOne1\',\'One\') "/></td>';
			 rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showTrainList(\'mixedJrnySourceOne1\',\'mixedJrnyDestinationOne1\',\'mixedJrnyJourneyDateOne1\')"/>' +
						'</td>';
		
			row2.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="6"><div width="100%" align="left"  id="mixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
			
	    $("#mixedJrnyJourneyDate0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			onShow: function() {
				$("#mixedJrnyJourneyDate0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#mixedJrnyJourneyDate0").datetimepicker("setOptions", {
					minDate: minData
				});
			
				$("#mixedJrnyJourneyDate0").datetimepicker("setOptions", {
					maxDate: maxData
				});
		    },	
		    onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#mixedJrnyJourneyDateOne1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate
				});
				$("#mixedJrnyJourneyDateOne1").datetimepicker("setOptions", {
					minDate: selected
				});
					 }
		});
			
			
			$("#mixedJrnyJourneyDateOne1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#mixedJrnyJourneyDateOne1").val("");
					 }
			  });
		    
     
    	
     }
     
     if(mixedPreference==2)
     {
     	
     		 var mixedJrnySource=$('#fixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode0" value="0" >Rail</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="mixedJrnySource" id="mixedJrnySourceOne0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					'<input type="text" name="mixedJrnyDestination" class="txtfldM" value="" id="mixedJrnyDestinationOne0" autocomplete="off" onkeyup="populateStations(\'mixedJrnyDestinationOne0\',\'mixedJrnyDestSuggestionOne0\',\'mixedJrnyDestSuggestionsListOne0\')"  /> ';		
			rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnyDestSuggestionOne0" style="display: none;">';
			rowHtmlTxt+='<div class="suggestionList" id="mixedJrnyDestSuggestionsListOne0"></div>';
			rowHtmlTxt+='</div></td>';
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDateOne0" value="" size="11" readonly></td>';
			rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="viaRouteMixed" id="viaRouteMixedOne0" onClick="validateViaCheck(this,\'mixedJrnySourceOne0\',\'mixedJrnyDestinationOne0\',\'mixedJrnyJourneyDateOne0\',\'viaRouteMixedOne0\',\'One\') "/></td>';
			rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showTrainList(\'mixedJrnySourceOne0\',\'mixedJrnyDestinationOne0\',\'mixedJrnyJourneyDateOne0\')"/>' +
						'</td>';
		
			row.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="5"><div width="100%" align="left"  id="mixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
		
			 lastRow = tbl.rows.length;
     		 var row2=tbl.insertRow(lastRow);
			 rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode1" value="1" >Air</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
			 		 '<div id="mixedJrnyAirDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					'<input type="text" name="mixedJrnyDestination" class="txtfldM" value="" id="mixedJrnyDestination1" autocomplete="off" onkeyup="populateAirport(\'mixedJrnyDestination1\',\'mixedJrnyDestSuggestion1\',\'mixedJrnyDestSuggestionsList1\')"  /> ';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnyDestSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnyDestSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			 
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDate1" onblur="isDateChange(this.id);" value="" size="11" readonly></td>';
			 rowHtmlTxt+='<td width="10%" class="lablevalue"></td>';
			 rowHtmlTxt+='<td width="5%" class="lablevalue"><input type="button" value="Search" class="butn" onClick="showFlightList(\'mixedJrnySourceAir\',\'mixedJrnyDestination1\',\'mixedJrnyJourneyDate1\')"/></td>';
			 row2.innerHTML=rowHtmlTxt;
		    
			 lastRow = tbl.rows.length;
			 var airViaDivRow =tbl.insertRow(lastRow);
			 airViaDivRow.id='mixedLTCAirConnectingFlight';
			 airViaDivRow.style='display:none';
			 airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="mixedLTCAirConnectingFlightDtls"></div></td>';
     	
     	 	 var mixedJrnyDestination=$('#fixedDestination').val();
    	 	 lastRow = tbl.rows.length;
     		 var row3=tbl.insertRow(lastRow);
			 rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="mixedJrnyMode" id="mixedJrnyMode2" value="0" >Rail</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					 '<div id="mixedJrnyRailDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="mixedJrnySrcSuggestionTwo2" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="mixedJrnySrcSuggestionsListTwo2"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="mixedJrnyDestination" id="mixedJrnyDestinationTwo2" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="mixedJrnyJourneyDate" id="mixedJrnyJourneyDateTwo2" value="" size="11" readonly></td>';
  		     rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="viaRouteMixed" id="viaRouteMixedTwo2" onClick="validateViaCheck(this,\'mixedJrnySourceOne1\',\'mixedJrnyDestinationTwo2\',\'mixedJrnyJourneyDateTwo2\',\'viaRouteMixedTwo2\',\'Two\') "/></td>';
		     rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showTrainList(\'mixedJrnySourceOne1\',\'mixedJrnyDestinationTwo2\',\'mixedJrnyJourneyDateTwo2\')"/>' +
						'</td>';
		
			 row3.innerHTML=rowHtmlTxt;
			 lastRow = tbl.rows.length;
			 var viadivRow =tbl.insertRow(lastRow);
			 viadivRow.innerHTML='<td colspan="5"><div width="100%" align="left"  id="mixedViaDivTwo" ></div></td>';
	    $("#mixedJrnyJourneyDateOne0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			onShow: function() {
				$("#mixedJrnyJourneyDateOne0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#mixedJrnyJourneyDateOne0").datetimepicker("setOptions", {
					minDate: minData,
					maxDate: maxData
				});
			},
			onSelectDate: function(selected) {
				var selectedDate = new Date(selected);
			
				$("#mixedJrnyJourneyDate1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate,
					minDate: selected
				});
					 }
		});
			
		    	
		
		$("#mixedJrnyJourneyDate1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		maxDate: $('#travelEndDate').val(),
		minDate: $('#travelStartDate').val(),
		yearEnd: 2100,
			onShow: function() {
				$(mixedJrnyJourneyDate1).val("");
			}
			, onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#mixedJrnyJourneyDateTwo2").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate,
					minDate: selected
				});
					 }
		});
			  
			$("#mixedJrnyJourneyDateTwo2").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		maxDate: $('#travelEndDate').val(),
		minDate: $('#travelStartDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#mixedJrnyJourneyDateTwo2").val("");
					 }
			  });
	
     }	
     
	
}



function addReturnMixedJourneyRow()
{
	 var mixedJrnySource="";var mixedJrnyDestination="";
     var mixedPreference=$("#returnMixedPreference").val();
     var mixedJrnyMode="";
     var trRuleID = $('#TRRule').val();
     //alert("addMixedJourneyRow-mixedPreference-"+mixedPreference+"||mixedRowCount-"+mixedRowCount+"|jrnyType="+jrnyType);
     	
     var tbl = document.getElementById('returnMixedJrnyDtlsTable');
	 var lastRow = tbl.rows.length;
	 var isFiledSet=true;
	 
     if(mixedPreference==0)
     {
     		 var mixedJrnySource=$('#returnFixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="10%"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode0" value="0" >Rail</td>';
			 rowHtmlTxt+='<td width="30%"><input type="hidden" name="returnMixedJrnySource" id="returnMixedJrnySourceOne0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 if(trRuleID=='TR100233' || trRuleID=='TR100234' || trRuleID=='TR100118'){
			if(trRuleID=='TR100118'){
			 rowHtmlTxt+='<select name="returnMixedJrnyDestination" id="returnMixedJrnyDestinationOne0" class="combo" autocomplete="off" onChange="populateReturnMappedAirport(\'returnMixedJrnyAirDiv\',this.options[this.selectedIndex].value)" >';
						rowHtmlTxt+='<option value="" >--select--</option>';
						rowHtmlTxt+='<option value="CDG" >CHANDIGARH(CDG)</option>';
						rowHtmlTxt+='<option value="NDLS" >NEW DELHI(NDLS)</option>';
						rowHtmlTxt+='<option value="JAT" >JAMMU TAWI(JAT)</option>';
						rowHtmlTxt+='<option value="SINA" >SRINAGAR(SINA)</option>';
					rowHtmlTxt+='</select>';
			}else{
			 rowHtmlTxt+='<td width="30%">' +
					'<input type="text" name="returnMixedJrnyDestination" class="txtfldM" value="" id="returnMixedJrnyDestinationOne0" autocomplete="off" /> ';		
				}
			 }else{
			 rowHtmlTxt+='<td width="30%">' +
					'<input type="text" name="returnMixedJrnyDestination" class="txtfldM" value="" id="returnMixedJrnyDestinationOne0" autocomplete="off" onkeyup="populateReturnStations(\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyDestSuggestionOne0\',\'returnMixedJrnyDestSuggestionsListOne0\')"  /> ';		
			 }
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnyDestSuggestionOne0" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnyDestSuggestionsListOne0"></div>';
			 rowHtmlTxt+='</div></td>';
			
			rowHtmlTxt+='<td width="15%"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDateOne0" value="" size="11" readonly="readonly"></td>';
			rowHtmlTxt+='<td width="10%"><input type="checkbox" name="returnViaRouteMixed" id="returnViaRouteMixedOne0" onClick="validateReturnViaCheck(this,\'returnMixedJrnySourceOne0\',\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyJourneyDateOne0\',\'returnViaRouteMixedOne0\',\'One\') "/></td>';
			rowHtmlTxt+='<td width="5%" nowrap="true" >' +
						'<input type="button" value="Search" class="butn" onClick="showReturnTrainList(\'returnMixedJrnySourceOne0\',\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyJourneyDateOne0\')"/>' +
						'</td>';
		
			row.innerHTML=rowHtmlTxt;
			if(trRuleID=='TR100233' || trRuleID=='TR100234'){
			
            $('#returnMixedJrnyDestinationOne0').val('HOWRAH JN(HWH)');
            populateReturnMappedAirport('returnMixedJrnyAirDiv','HWH');
            document.getElementById("returnMixedJrnyDestinationOne0").setAttribute("readonly", true);
            document.getElementById("returnMixedJrnyDestinationOne0").style.backgroundColor ="#eeeedd";
            
            }
			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="6"><div width="100%" align="left"  id="returnMixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
			
			var mixedJrnyDestination=$('#returnFixedDestination').val();
     		lastRow = tbl.rows.length;
			var row2=tbl.insertRow(lastRow);
			rowHtmlTxt="";
			rowHtmlTxt+='<td width="15%"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode1" value="1" >Air</td>';
			
			 rowHtmlTxt+='<td width="35%">' +
					  '<div id="returnMixedJrnyAirDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%"><input type="hidden" name="returnMixedJrnyDestination" id="returnMixedJrnyDestination1" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 rowHtmlTxt+='<td width="15%"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDate1" onblur="isDateChange(this.id);" value="" size="11" readonly="readonly"></td>';
			 rowHtmlTxt+='<td width="10%"></td>';
			 rowHtmlTxt+='<td width="5%"><input type="button"  value="Search" class="butn" onClick="showReturnFlightList(\'returnMixedJrnySourceAir\',\'returnMixedJrnyDestination1\',\'returnMixedJrnyJourneyDate1\')"/></td>';
			 row2.innerHTML=rowHtmlTxt;
			
			 lastRow = tbl.rows.length;
			 var airViaDivRow =tbl.insertRow(lastRow);
			 airViaDivRow.id='returnMixedLTCAirConnectingFlight';
			 airViaDivRow.style='display:none';
			 airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="returnMixedLTCAirConnectingFlightDtls"></div></td>';
			
		$("#returnMixedJrnyJourneyDateOne0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			
			onShow: function() {
				$("#returnMixedJrnyJourneyDateOne0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#returnMixedJrnyJourneyDateOne0").datetimepicker("setOptions", {
					minDate: minData,
					maxDate: maxData
				});

			},
			onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#returnMixedJrnyJourneyDate1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate
				});
				$("#returnMixedJrnyJourneyDate1").datetimepicker("setOptions", {
					minDate: selected
				});
					 }
		});
			
			
			
			$("#returnMixedJrnyJourneyDate1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#returnMixedJrnyJourneyDate1").val("");
					 }
			  });
			
     }
     
     if(mixedPreference==1)
     {
     	
     		 var mixedJrnySource=$('#returnFixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode0" value="1" >Air</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="returnMixedJrnySource" id="returnMixedJrnySource0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 
			 var orgTextValue=$('#returnFixedOrgStationDD option:selected').text();
	 	 	 if(orgTextValue=='Fixed'){
	 			 
	 			 rowHtmlTxt+='<td width="35%" class="lablevalue">';
	 			 rowHtmlTxt+='<select name="returnMixedJrnyDestination" id="returnMixedJrnyDestination0" class="comboMixed" onChange="fillReturnStaionValue(this.options[this.selectedIndex].value,\'Fixed\',\'\')" >';
	 			 var airportOption=populateFixedDestAirportOption(1);
	 			 rowHtmlTxt+=airportOption;
	 			 rowHtmlTxt+='</select>';
				 rowHtmlTxt+='</td>';
			 }else
	 	 	 {
		 	 	 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
						'<input type="text" name="returnMixedJrnyDestination" class="txtfldM" value="" id="returnMixedJrnyDestination0" autocomplete="off" onkeyup="populateReturnAirport(\'returnMixedJrnyDestination0\',\'returnMixedJrnyDestSuggestion0\',\'returnMixedJrnyDestSuggestionsList0\')"  /> ';		
				 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnyDestSuggestion0" style="display: none;">';
				 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnyDestSuggestionsList0"></div>';
				 rowHtmlTxt+='</div></td>';
				 	
	 	 	 }
			
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDate0" onblur="isDateChange(this.id);" value="" size="11" readonly></td>';
			rowHtmlTxt+='<td width="10%" class="lablevalue"></td>';
			rowHtmlTxt+='<td width="5%" class="lablevalue"><input type="button" value="Search" class="butn" onClick="showReturnFlightList(\'returnMixedJrnySource0\',\'returnMixedJrnyDestination0\',\'returnMixedJrnyJourneyDate0\')"/></td>';
			row.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var airViaDivRow =tbl.insertRow(lastRow);
			airViaDivRow.id='returnMixedLTCAirConnectingFlight';
			airViaDivRow.style='display:none';
			airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="returnMixedLTCAirConnectingFlightDtls"></div></td>';
			
	 		var mixedJrnyDestination=$('#returnFixedDestination').val();
	 		 
	 		lastRow = tbl.rows.length;
     		var row2=tbl.insertRow(lastRow);
			rowHtmlTxt="";
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode1" value="0" >Rail</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					 '<div id="returnMixedJrnyRailDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="returnMixedJrnyDestination" id="returnMixedJrnyDestinationOne1" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDateOne1" value="" size="11" readonly="readonly"></td>';
			 rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="returnViaRouteMixed" id="returnViaRouteMixedOne1" onClick="validateReturnViaCheck(this,\'returnMixedJrnySourceOne1\',\'returnMixedJrnyDestinationOne1\',\'returnMixedJrnyJourneyDateOne1\',\'returnViaRouteMixedOne1\',\'One\') "/></td>';
			 rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showReturnTrainList(\'returnMixedJrnySourceOne1\',\'returnMixedJrnyDestinationOne1\',\'returnMixedJrnyJourneyDateOne1\')"/>' +
						'</td>';
		
			row2.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="6"><div width="100%" align="left"  id="returnMixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
			
		$("#returnMixedJrnyJourneyDate0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			
			onShow: function() {
				$("#returnMixedJrnyJourneyDate0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#returnMixedJrnyJourneyDate0").datetimepicker("setOptions", {
					minDate: minData,
					maxDate: maxData
				});

			},
			onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#returnMixedJrnyJourneyDateOne1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate
				});
				$("#returnMixedJrnyJourneyDateOne1").datetimepicker("setOptions", {
					minDate: selected
				});
					 }
		});
			
			$("#returnMixedJrnyJourneyDateOne1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#returnMixedJrnyJourneyDateOne1").val("");
					 }
			  });
		    
     }
     
     if(mixedPreference==2)
     {
     	
     		 var mixedJrnySource=$('#returnFixedOrigin').val();
     	
     		 var row=tbl.insertRow(lastRow);
			 var rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode0" value="0" >Rail</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="returnMixedJrnySource" id="returnMixedJrnySourceOne0" value="'+mixedJrnySource+'" >'+mixedJrnySource+'</td>';
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					'<input type="text" name="returnMixedJrnyDestination" class="txtfldM" value="" id="returnMixedJrnyDestinationOne0" autocomplete="off" onkeyup="populateReturnStations(\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyDestSuggestionOne0\',\'returnMixedJrnyDestSuggestionsListOne0\')"  /> ';		
			rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnyDestSuggestionOne0" style="display: none;">';
			rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnyDestSuggestionsListOne0"></div>';
			rowHtmlTxt+='</div></td>';
			rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDateOne0" value="" size="11" readonly="readonly"></td>';
			rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="returnViaRouteMixed" id="returnViaRouteMixedOne0" onClick="validateReturnViaCheck(this,\'returnMixedJrnySourceOne0\',\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyJourneyDateOne0\',\'returnViaRouteMixedOne0\',\'One\') "/></td>';
			rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showReturnTrainList(\'returnMixedJrnySourceOne0\',\'returnMixedJrnyDestinationOne0\',\'returnMixedJrnyJourneyDateOne0\')"/>' +
						'</td>';
		
			row.innerHTML=rowHtmlTxt;
			
			lastRow = tbl.rows.length;
			var viadivRow =tbl.insertRow(lastRow);
			viadivRow.innerHTML='<td colspan="6"><div width="100%" align="left"  id="returnMixedViaDivOne" class="datagrid" style="display: none;"></div></td>';
		
			 lastRow = tbl.rows.length;
     		 var row2=tbl.insertRow(lastRow);
			 rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode1" value="1" >Air</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
			 		 '<div id="returnMixedJrnyAirDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnySrcSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnySrcSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					'<input type="text" name="returnMixedJrnyDestination" class="txtfldM" value="" id="returnMixedJrnyDestination1" autocomplete="off" onkeyup="populateReturnAirport(\'returnMixedJrnyDestination1\',\'returnMixedJrnyDestSuggestion1\',\'returnMixedJrnyDestSuggestionsList1\')"  /> ';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnyDestSuggestion1" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnyDestSuggestionsList1"></div>';
			 rowHtmlTxt+='</div></td>';
			 
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDate1" onblur="isDateChange(this.id);" value="" size="11" readonly></td>';
			 rowHtmlTxt+='<td width="10%" class="lablevalue"></td>';
			 rowHtmlTxt+='<td width="5%" class="lablevalue"><input type="button" value="Search" class="butn" onClick="showReturnFlightList(\'returnMixedJrnySourceAir\',\'returnMixedJrnyDestination1\',\'returnMixedJrnyJourneyDate1\')"/></td>';
			 row2.innerHTML=rowHtmlTxt;
			
			 lastRow = tbl.rows.length;
			 var airViaDivRow =tbl.insertRow(lastRow);
			 airViaDivRow.id='returnMixedLTCAirConnectingFlight';
			 airViaDivRow.style='display:none';
			 airViaDivRow.innerHTML='<td colspan="6"><div class="datagrid" id="returnMixedLTCAirConnectingFlightDtls"></div></td>';
			
     	 	 var mixedJrnyDestination=$('#returnFixedDestination').val();
    	 	 lastRow = tbl.rows.length;
     		 var row3=tbl.insertRow(lastRow);
			 rowHtmlTxt="";
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="hidden" name="returnMixedJrnyMode" id="returnMixedJrnyMode2" value="0" >Rail</td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue">' +
					 '<div id="returnMixedJrnyRailDiv"></div>';		
			 rowHtmlTxt+='<div class="suggestionsBox" id="returnMixedJrnySrcSuggestionTwo2" style="display: none;">';
			 rowHtmlTxt+='<div class="suggestionList" id="returnMixedJrnySrcSuggestionsListTwo2"></div>';
			 rowHtmlTxt+='</div></td>';
			
			 rowHtmlTxt+='<td width="35%" class="lablevalue"><input type="hidden" name="returnMixedJrnyDestination" id="returnMixedJrnyDestinationTwo2" value="'+mixedJrnyDestination+'" >'+mixedJrnyDestination+'</td>';
			 
			 rowHtmlTxt+='<td width="15%" class="lablevalue"><input type="text" name="returnMixedJrnyJourneyDate" id="returnMixedJrnyJourneyDateTwo2" value="" size="11" readonly></td>';
  		     rowHtmlTxt+='<td width="10%" class="lablevalue"><input type="checkbox" name="returnViaRouteMixed" id="returnViaRouteMixedTwo2" onClick="validateReturnViaCheck(this,\'returnMixedJrnySourceOne1\',\'returnMixedJrnyDestinationTwo2\',\'returnMixedJrnyJourneyDateTwo2\',\'returnViaRouteMixedTwo2\',\'Two\') "/></td>';
		     rowHtmlTxt+='<td width="5%" class="lablevalue" nowrap="true">' +
						'<input type="button" value="Search" class="butn" onClick="showReturnTrainList(\'returnMixedJrnySourceOne1\',\'returnMixedJrnyDestinationTwo2\',\'returnMixedJrnyJourneyDateTwo2\')"/>' +
						'</td>';
		
			 row3.innerHTML=rowHtmlTxt;
			 lastRow = tbl.rows.length;
			 var viadivRow =tbl.insertRow(lastRow);
			 viadivRow.innerHTML='<td colspan="5"><div width="100%" align="left"  id="returnMixedViaDivTwo" class="datagrid"></div></td>';
			 
		
		$("#returnMixedJrnyJourneyDateOne0").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
			onShow: function() {
				$("#returnMixedJrnyJourneyDateOne0").val("");
				minData = $('#travelStartDate').val();
				maxData = $('#travelEndDate').val();
				$("#returnMixedJrnyJourneyDateOne0").datetimepicker("setOptions", {
					minDate: minData,
					maxDate: maxData
				});
			},
			onSelectDate: function(selected) {
				var selectedDate = new Date(selected);
			
				$("#returnMixedJrnyJourneyDate1").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate,
					minDate: selected
				});
					 }
		});
			
		    	
		
			$("#returnMixedJrnyJourneyDate1").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
			onShow: function() {
				$("#returnMixedJrnyJourneyDate1").val("");
			}
			, onSelectDate: function(selected) {
				var selectedDate = new Date(selected);

				$("#returnMixedJrnyJourneyDateTwo2").datetimepicker("setOptions", {
					defaultDate: selectedDate,
					viewDate: selectedDate,
					minDate: selected
				});
					 }
		});
			
		$("#returnMixedJrnyJourneyDateTwo2").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $("#returnMixedJrnyJourneyDateTwo2").val("");
					 }
			  });
	
			 
     }	
     
	
}


function populateStations(inputBoxId,divSuggBoxId,divSuggListId,isVai,fromBoxId)
{
	var inputValue=$('#'+inputBoxId).val();
	if(inputValue.length>1)
	{
		//alert("populateStations:inputBoxId-"+inputBoxId+"||divSuggBoxId-"+divSuggBoxId+"||divSuggListId-"+divSuggListId+"||isVai-"+isVai+"||fromBoxId-"+fromBoxId);
	
		var stationList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getStationList",
			  type: "get",
			  data: "stationName="+inputValue,
			  dataType: "json",
		      success: function(msg)
		      {
			      $.each(msg, function(index,name){
		        
					if(name=="Station Name Not Exist")
	                	stationList+='<li>' +name+'</li>';
	                else{
	                	if(isVai){
	                		stationList+='<li onClick="fillStaionValueForViaRoute(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\',\''+fromBoxId+'\')">' +name+'</li>';	
	                	}else{
		                	stationList+='<li onClick="fillStaionValue(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\')">' +name+'</li>';	
	                	}
	                }
	                    
	              });
	              
				  $("#"+divSuggListId).html(stationList);		
	              $('#'+divSuggBoxId).show();
	            
		       }
		});
	}
}


function populateReturnStations(inputBoxId,divSuggBoxId,divSuggListId,isVai,fromBoxId)
{
	var inputValue=$('#'+inputBoxId).val();
	if(inputValue.length>1)
	{
		//alert("populateStations:inputBoxId-"+inputBoxId+"||divSuggBoxId-"+divSuggBoxId+"||divSuggListId-"+divSuggListId+"||isVai-"+isVai+"||fromBoxId-"+fromBoxId);
	
		var stationList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getStationList",
			  type: "get",
			  data: "stationName="+inputValue,
			  dataType: "json",
		      success: function(msg)
		      {
		         $.each(msg, function(index,name){
	             
					if(name=="Station Name Not Exist")
	                	stationList+='<li>' +name+'</li>';
	                else{
	                	if(isVai){
	                		stationList+='<li onClick="fillStaionValueForViaRoute(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\',\''+fromBoxId+'\')">' +name+'</li>';	
	                	}else{
		                	stationList+='<li onClick="fillReturnStaionValue(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\')">' +name+'</li>';	
	                	}
	                }
	                    
	              });
	              
				  $("#"+divSuggListId).html(stationList);		
	              $('#'+divSuggBoxId).show();
	            
		       }
		});
	}
}


function fillStaionValue(thisValue,inputBoxId,divSuggBoxId) 
{
	 if(inputBoxId=='Fixed'){}else{
	 	$('#'+inputBoxId).val(thisValue);
	 	setTimeout("$('#"+divSuggBoxId+"').hide();", 200);
	 }
	 	 
	 var mixedPreference=-1;
	 if($("#mixedPreference")!=null)
	 mixedPreference=$("#mixedPreference").val();
	 //alert("fillStaionValue:mixedPreference-"+mixedPreference+"|thisValue-"+thisValue+"inputBoxId-"+inputBoxId);
	
	 var stnCode=getStationCode(thisValue);
	 
	// alert("fillStaionValue:mixedPreference-"+mixedPreference+"|stnCode-"+stnCode+"|thisValue-"+thisValue+"divSuggBoxId-"+divSuggBoxId);
	 if(mixedPreference==0)
     {
     	var destTextValue=$('#fixedDestStationDD option:selected').text();
	 	if(destTextValue=='Fixed')
	 	{
	 		var fixAirportList="";
	 		fixAirportList+='<select name="mixedJrnySource" id="mixedJrnySourceAir" class="combo" >';
	 		var opt=populateFixedOrgAirportOption(0);
	 		fixAirportList+=opt;
	 		fixAirportList+='</select>';
			$("#mixedJrnyAirDiv").html(fixAirportList);
	 	}else
	 	{
	 		populateMappedAirport('mixedJrnyAirDiv',stnCode);	
	 	}
     	
     }else if(mixedPreference==1)
     {
	 	 populateMappedStations('mixedJrnyRailDiv',stnCode);
     }else if(mixedPreference==2)
     {
     	if(divSuggBoxId=="mixedJrnyDestSuggestion1"){
	     	populateMappedStations('mixedJrnyRailDiv',stnCode);	
     	}else
     	{
     		populateMappedAirport('mixedJrnyAirDiv',stnCode);
		}
	 	 
     }	
}

function fillReturnStaionValue(thisValue,inputBoxId,divSuggBoxId) 
{
	 if(inputBoxId=='Fixed'){}else{
	 	$('#'+inputBoxId).val(thisValue);
	 	setTimeout("$('#"+divSuggBoxId+"').hide();", 200);
	 }
		 
	 var mixedPreference=-1;
	 if($("#returnMixedPreference")!=null)
	 mixedPreference=$("#returnMixedPreference").val();
	 //alert("fillStaionValue:mixedPreference-"+mixedPreference+"|thisValue-"+thisValue+"inputBoxId-"+inputBoxId);
	
	 var stnCode=getStationCode(thisValue);
	 
	// alert("fillStaionValue:mixedPreference-"+mixedPreference+"|stnCode-"+stnCode+"|thisValue-"+thisValue+"divSuggBoxId-"+divSuggBoxId);
	 if(mixedPreference==0)
     {
     	var destTextValue=$('#returnFixedDestStationDD option:selected').text();
	 	if(destTextValue=='Fixed')
	 	{
	 		var fixAirportList="";
	 		fixAirportList+='<select name="returnMixedJrnySource" id="returnMixedJrnySourceAir" class="combo" >';
	 		var opt=populateFixedOrgAirportOption(1);
	 		fixAirportList+=opt;
	 		fixAirportList+='</select>';
			$("#returnMixedJrnyAirDiv").html(fixAirportList);
	 	}else
	 	{
	 		populateReturnMappedAirport('returnMixedJrnyAirDiv',stnCode);	
	 	}
     	
     }else if(mixedPreference==1)
     {
	 	 populateReturnMappedStations('returnMixedJrnyRailDiv',stnCode);
     }else if(mixedPreference==2)
     {
     	if(divSuggBoxId=="returnMixedJrnyDestSuggestion1"){
	     	populateReturnMappedStations('returnMixedJrnyRailDiv',stnCode);	
     	}else
     	{
     		populateReturnMappedAirport('returnMixedJrnyAirDiv',stnCode);
		}
	 	 
     }	
}

function populateFixedOrgAirportOption(value)
{
 	var msg = $.parseJSON($('#travelerObj').val());
	var airportOption="";
	$.each(msg.airStationListFrom, function(index,obj){
		if(obj.jrnyType==value){
		var FromStation = obj.airFromStation;
		var FromStationType = obj.airFromStationType;
		if(FromStationType=="Fixed")
		{
			var fromStationList=FromStation.split("::");
			airportOption +='<option value="">Select</option>'
            for(var i=0;i<fromStationList.length;i++)
            {
                airportOption+='<option value="' +fromStationList[i] +'" >' +fromStationList[i]+'</option>';
            }
       }
       }
	});
	return airportOption;
}

function populateMappedAirport(divId,railStn)
{
	//var railStn=$('#'+stnTxtBoxId).val();
	
	if(railStn.length>=2)
	{
		
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getMappedAirportList",
		      type: "get",
		      data: "railStn="+railStn,
		      dataType: "json",
		      success: function(msg)
		      {
		          airportList+='<select name="mixedJrnySource" id="mixedJrnySourceAir" class="combo" >';
		           $.each(msg, function(index,name){
	              
					if(name=="Airport Not Exist")
	                	airportList+='<option value="">' +name+'</li>';
	                else
	                    airportList+='<option value="' +name +'" >' +name+'</option>';
	              });
	              airportList+='</select>';
				  $("#"+divId).html(airportList);		
	           }
		});
	}
	
}





function populateMappedStations(divId,airportCode)
{
	//var airportCode=$('#'+stnTxtBoxId).val();
	if(airportCode.length>2)
	{
		//alert("populateMappedStations:inputBoxId-"+inputBoxId+"||divSuggBoxId-"+divSuggBoxId+"||divSuggListId-"+divSuggListId+"||isVai-"+isVai+"||fromBoxId-"+fromBoxId);
	
		var stationList="";
		$.ajax(
		{
			 url: $("#context_path").val()+"mb/getMappedStationList",
			  type: "get",
			  data: "airportCode="+airportCode,
			  dataType: "json",
		      success: function(msg)
		      {
		          
		          stationList+='<select name="mixedJrnySource" id="mixedJrnySourceOne1" class="comboMixed" >';
		          $.each(msg, function(index,name){
	              
					if(name=="Station Name Not Exist")
	                	stationList+='<option value="">' +name+'</li>';
	                else
	                    stationList+='<option value="' +name +'" >' +name+'</option>';
	              });
	              stationList+='</select>';
				  $("#"+divId).html(stationList);	
				 
		       }
		});
	}
} 


function populateReturnMappedAirport(divId,railStn)
{
	//var railStn=$('#'+stnTxtBoxId).val();
	
	if(railStn.length>=2)
	{
		
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getMappedAirportList",
		      type: "get",
		      data: "railStn="+railStn,
		      dataType: "json",
		      success: function(msg)
		      {
		          airportList+='<select name="returnMixedJrnySource" id="returnMixedJrnySourceAir" class="combo" >';
		          $.each(msg, function(index,name){
	             
					if(name=="Airport Not Exist")
	                	airportList+='<option value="">' +name+'</li>';
	                else
	                    airportList+='<option value="' +name +'" >' +name+'</option>';
	              });
	              airportList+='</select>';
				  $("#"+divId).html(airportList);		
	           }
		});
	}
	
}





function populateReturnMappedStations(divId,airportCode)
{
	//var airportCode=$('#'+stnTxtBoxId).val();
	if(airportCode.length>2)
	{
		//alert("populateMappedStations:inputBoxId-"+inputBoxId+"||divSuggBoxId-"+divSuggBoxId+"||divSuggListId-"+divSuggListId+"||isVai-"+isVai+"||fromBoxId-"+fromBoxId);
	
		var stationList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getMappedStationList",
			  type: "get",
			  data: "airportCode="+airportCode,
			  dataType: "json",
		      success: function(msg)
		      {
		          stationList+='<select name="returnMixedJrnySource" id="returnMixedJrnySourceOne1" class="comboMixed" >';
		          
		           $.each(msg, function(index,name){
		          
					if(name=="Station Name Not Exist")
	                	stationList+='<option value="">' +name+'</li>';
	                else
	                    stationList+='<option value="' +name +'" >' +name+'</option>';
	              });
	              stationList+='</select>';
				  $("#"+divId).html(stationList);	
				 
		       }
		});
	}
}  

function populateAirport(inputBoxId,divSuggBoxId,divSuggListId)
{
	var inputValue=$('#'+inputBoxId).val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue,
		      dataType: "json",
		      success: function(msg)
		      {
		         
		         $.each(msg, function(index,name){
	             
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillStaionValue(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\')">' +name+'</li>';
	              });
	              
				  $("#"+divSuggListId).html(airportList);		
	              $('#'+divSuggBoxId).show();
	            
		       }
		});
	}
}

function populateReturnAirport(inputBoxId,divSuggBoxId,divSuggListId)
{
	var inputValue=$('#'+inputBoxId).val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue,
		      dataType: "json",
		      success: function(msg)
		      {
			      $.each(msg, function(index,name){
		         
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillReturnStaionValue(\'' +name +'\',\''+inputBoxId+'\',\''+divSuggBoxId+'\')">' +name+'</li>';
	              });
	              
				  $("#"+divSuggListId).html(airportList);		
	              $('#'+divSuggBoxId).show();
	            
		       }
		});
	}
}

/* Function to check via-validation on mixed-onward Journey */
function validateViaCheck(chk, frmStnId, toStnId, jrnyDateId, viaRouteFlagId, vsIndex) {

    var fromStation = $("#" + frmStnId).val();
    var toStation = $("#" + toStnId).val();
    var viaRouteCheckboxId = chk.id;
			
     if (fromStation == undefined)
         fromStation = $("select#"+frmStnId).val();
			
     if (toStation == undefined)
         toStation = $("select#"+toStnId).val();   

    $("#mixedViaDiv" + vsIndex).show();

    if (validateMixedViaFields(frmStnId, toStnId, jrnyDateId, viaRouteFlagId)) {
        if (chk.checked == true) {
            $("#viaRute" + vsIndex).val("YES");
            if (!validateMixedViaRouteStations(fromStation, toStation, vsIndex, viaRouteCheckboxId)) {
                if (validateViaBookingMixed(frmStnId, toStnId, jrnyDateId, "mixedViaDiv" + vsIndex, viaRouteFlagId)) {
                    $("#" + frmStnId).prop("readonly", true);
                    $("#" + toStnId).prop("readonly", true);
                    var viafields = "";
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
                    viafields += '<tr><td colspan="4" class="lablevalue">Via Route Journey Details</td></tr>'
                    viafields += '<tr><td align="left" class="lablevalue" style="width:25%">From Station</td><td align="left"  class="lablevalue" style="width:25%">To Station</td><td align="left"  class="lablevalue" style="width:25%">Date Of Journey</td><td style="width:25%"></td></tr>'
                    viafields += '</table>'
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="routeTable' + vsIndex + '">'
                    viafields += '<tr>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="frmStation' + vsIndex + '0" name="frmStation' + vsIndex + '" value="' + fromStation + '" readonly autocomplete="off">'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="toStation' + vsIndex + '0" name="toStation' + vsIndex + '"  onkeyup="populateStations(\'toStation' + vsIndex + '0\',\'suggStation' + vsIndex + '0\',\'autoSuggList' + vsIndex + '0\',true,\'frmStation' + vsIndex + '1\')" autocomplete="off">'
                    viafields += '<div class="suggestionsBox" id="suggStation' + vsIndex + '0" style="display: none;" >'
                    viafields += '<div  class="suggestionList"  id="autoSuggList' + vsIndex + '0"></div></div>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="journeyDate' + vsIndex + '0" name="journeyDate' + vsIndex + '" readonly="readonly" size="10" autocomplete="off"></td>'
                    viafields += '<td align="left" width="25%"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearchMixed(\'' + vsIndex + '0\')"/></td></tr>'
                    viafields += '<tr>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="frmStation' + vsIndex + '1" name="frmStation' + vsIndex + '"  readonly autocomplete="off">'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" value="' + toStation + '" id="toStation' + vsIndex + '1" readonly="readonly" name="toStation' + vsIndex + '"  autocomplete="off">'
                    viafields += '<div class="suggestionsBox" id="suggStation' + vsIndex + '1" style="display: none;" >'
                    viafields += '<div  class="suggestionList"  id="autoSuggList' + vsIndex + '1"></div></div>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="journeyDate' + vsIndex + '1" name="journeyDate' + vsIndex + '" readonly="readonly" size="10" autocomplete="off"></td>'
                    viafields += '<td align="left" width="25%"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearchMixed(\'' + vsIndex + '1\')"/></td></tr>'
                    viafields += '</table>';
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="+ Row" onclick="addViaRow(\'' + toStation + '\',\'routeTable' + vsIndex + '\',\'' + vsIndex + '\')"/>&nbsp;<input class="butn" type="button" value="- Row" onclick="delViaRow(\'' + toStation + '\',\'routeTable' + vsIndex + '\');"/></td></tr></table></div>'
                    $("#mixedViaDiv" + vsIndex).show("fast");
                    $("#mixedViaDiv" + vsIndex).html(viafields);
                    $("#journeyDate" + vsIndex + "0").datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        scrollInput: false,
                        minDate: $('#travelStartDate').val(),
                        maxDate: $('#travelEndDate').val(),
                        yearEnd: 2100,
                        beforeShow: function() {
                            $(this).val("");
                        }
                    });
                    $("#journeyDate" + vsIndex + "1").datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        scrollInput: false,
                        minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
                        beforeShow: function() {
						 $(this).val("");
					 }
			  });
                } else {
                    $('#' + viaRouteFlagId).attr('checked', false);
                    $('#viaValidate').val("true");
                    $("#viaRute" + vsIndex).val("NO");
                }
            }
        } else {
            $("#" + frmStnId).prop("readonly", false);
            $("#" + toStnId).prop("readonly", false);
            $("#mixedViaDiv" + vsIndex).hide("fast");
            $("#viaRute" + vsIndex).val("NO");
        }
    } else {
        $('#' + viaRouteFlagId).attr('checked', false);
        $("#mixedViaDiv" + vsIndex).hide("fast");
        $("#viaRute" + vsIndex).val("NO");
    }
}


/* Function value to check mixed-mode Via-Route Available */
function validateMixedViaRouteStations(fromStn, tostn, vsIndex,viaRouteCheckboxId) {
    var validationFlag = false;

    $.ajax({
        url: $("#context_path").val() + "mb/getAvailableViaRouteStations",
        type: "GET",
        data: {
            fromStation: fromStn,
            toStation: tostn
        },
        dataType: "json",
        async: false,
        success: function(msg) {
            if (msg.length === 0) {
               // alert("No stations found for the selected via-route.");
                var viaRouteCheckBox = "#"+viaRouteCheckboxId;
                $(viaRouteCheckBox).attr('checked', false);
                validationFlag = false;
            } else {
                validationFlag = true;
                //$("#" + fromStn).prop("readonly", true);
                //$("#" + tostn).prop("readonly", true);

                var viafields = "";
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
                viafields += '<tr><td colspan="4" class="lablevalue">Via Route Journey Details</td></tr>'
                viafields += '<tr><td align="left" class="lablevalue" style="width:25%">From Station</td><td align="left"  class="lablevalue" style="width:25%">To Station</td><td align="left"  class="lablevalue" style="width:25%">Date Of Journey</td><td style="width:25%"></td></tr>'
                viafields += '</table>'
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="routeTable' + vsIndex + '">'
                viafields += '<tr>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" id="frmStation' + vsIndex + '0" name="frmStation' + vsIndex + '" value="' + fromStn + '" readonly autocomplete="off">'
                viafields += '</td>'
                viafields += '<td align="left" width="25%" id="viaMixedModeStationsDropDwn' +vsIndex+ '">'
                viafields += '</td>'
                viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="journeyDate' + vsIndex + '0" name="journeyDate' + vsIndex + '" readonly="readonly" size="10" autocomplete="off"></td>'
                viafields += '<td align="left" width="25%"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearchMixed(\'' + vsIndex + '0\')"/></td></tr>'
                viafields += '<tr>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" id="frmStation' + vsIndex + '1" name="frmStation' + vsIndex + '"  readonly autocomplete="off">'
                viafields += '</td>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" value="' + tostn + '" id="toStation' + vsIndex + '1" readonly="readonly" name="toStation' + vsIndex + '"  autocomplete="off">'
                viafields += '<div class="suggestionsBox" id="suggStation' + vsIndex + '1" style="display: none;" >'
                viafields += '<div  class="suggestionList"  id="autoSuggList' + vsIndex + '1"></div></div>'
                viafields += '</td>'
                viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="journeyDate' + vsIndex + '1" name="journeyDate' + vsIndex + '" readonly="readonly" size="10" autocomplete="off"></td>'
                viafields += '<td align="left" width="25%"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearchMixed(\'' + vsIndex + '1\')"/></td></tr>'
                viafields += '</table>';
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="+ Row" onclick="addViaRow(\'' + tostn + '\',\'routeTable' + vsIndex + '\',\'' + vsIndex + '\')"/>&nbsp;<input class="butn" type="button" value="- Row" onclick="delViaRow(\'' + tostn + '\',\'routeTable' + vsIndex + '\');"/></td></tr></table></div>'

                $("#mixedViaDiv" + vsIndex).show("fast");
                $("#mixedViaDiv" + vsIndex).html(viafields);
                $("#journeyDate" + vsIndex + "0").datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
                    scrollInput: false,
                    minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
                    beforeShow: function() {
						 $(this).val("");
					 }
			  });
                $("#journeyDate" + vsIndex + "1").datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
                    scrollInput: false,
                    minDate: $('#travelStartDate').val(),
                    maxDate: $('#travelEndDate').val(),
                    yearEnd: 2100,
                    beforeShow: function() {
                        $(this).val("");
			}
                });
                var options = '';
                options += '<select id="toStation' + vsIndex + '0" name="toStation' + vsIndex + '" onchange = fillMixedModeFromStation(this.value,\'' + vsIndex + '\')>';
                options += '<option value="">Select</option>';
                $.each(msg, function(index, value) {
                    options += `<option value="${value}">${value}</option>`;
                });
                options += '</select>';
                var viaMixedModeStationsDropDwn = "#viaMixedModeStationsDropDwn"+vsIndex;
                $(viaMixedModeStationsDropDwn).html(options);
			}
        },
        error: function(xhr, status, error) {
            $("#"+viaRouteCheckboxId).prop('checked', false);
            alert("Failed to fetch via route stations. Please try again.");
	}
    });
    return validationFlag;
}



/*  Function to check Mixed FillStation Value */
function fillMixedModeFromStation(stationValue,vsIndex) {
   var viaMixedToStation = "#toStation"+vsIndex+"0";
   var viaMixedFromStation = "#frmStation"+vsIndex+"1";  //frmStationTwo1
   var viaToStation  = $(viaMixedToStation).val();
   var viaFromStation = $(viaMixedFromStation).val();

   if(viaToStation != '' && viaToStation != undefined){
   $(viaMixedFromStation).val('');
   $(viaMixedFromStation).val(viaToStation);
   $(viaMixedFromStation).attr("readonly","readonly");
   }
}






/* Function to check via-Route Validation on Return Journey-Request */
function validateReturnViaCheck(chk, frmStnId, toStnId, jrnyDateId, viaRouteFlagId, vsIndex) {
    var fromStation = $("#" + frmStnId).val();
    var toStation = $("#" + toStnId).val();
			
     if (fromStation == undefined)
         fromStation = $("select#"+frmStnId).val();
			
     if (toStation == undefined)
         toStation = $("select#"+toStnId).val();  
			
    var viaRouteRtnCheckboxId = chk.id;
    $("#returnMixedViaDiv" + vsIndex).show();
    if (validateMixedViaFields(frmStnId, toStnId, jrnyDateId, viaRouteFlagId)) {
        if (chk.checked == true) {
            $("#returnViaRute" + vsIndex).val("YES");
            if (!validateReturnMixedViaRouteStations(fromStation, toStation, vsIndex, viaRouteRtnCheckboxId)) {
                if (validateReturnViaBookingMixed(frmStnId, toStnId, jrnyDateId, "returnMixedViaDiv" + vsIndex, viaRouteFlagId)) {
                    $("#" + frmStnId).prop("readonly", true);
                    $("#" + toStnId).prop("readonly", true);
                   // alert("Inside via validateViaBookingMixed.");
                    var viafields = "";
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
                    viafields += '<tr><td colspan="4" class="lablevalue">Via Route Journey Details</td></tr>'
                    viafields += '<tr><td align="left" style="width:25%" class="lablevalue">From Station</td><td align="left" style="width:25%" class="lablevalue">To Station</td><td align="left" style="width:25%" class="lablevalue">Date Of Journey</td><td style="width:25%" class="lablevalue"></td></tr>'
                    viafields += '</table>'
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnRouteTable' + vsIndex + '">'
                    viafields += '<tr>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="returnFrmStation' + vsIndex + '0" name="returnFrmStation' + vsIndex + '" value="' + fromStation + '" readonly/>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="returnToStation' + vsIndex + '0" name="returnToStation' + vsIndex + '"  onkeyup="populateStations(\'returnToStation' + vsIndex + '0\',\'returnSuggStation' + vsIndex + '0\',\'returnAutoSuggList' + vsIndex + '0\',true,\'returnFrmStation' + vsIndex + '1\')" />'
                    viafields += '<div class="suggestionsBox" id="returnSuggStation' + vsIndex + '0" style="display: none;" >'
                    viafields += '<div  class="suggestionList"  id="returnAutoSuggList' + vsIndex + '0"></div></div>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="return_JourneyDate' + vsIndex + '0" name="return_JourneyDate' + vsIndex + '" readonly="readonly" size="10"/></td>'
                    viafields += '<td align="left" width="25%"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearchMixed(\'' + vsIndex + '0\')"/></td></tr>'
                    viafields += '<tr>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" id="returnFrmStation' + vsIndex + '1" name="returnFrmStation' + vsIndex + '"  readonly/>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%">'
                    viafields += '<input type="text" class="txtfldM" value="' + toStation + '" id="returnToStation' + vsIndex + '1" readonly="readonly" name="returnToStation' + vsIndex + '"  />'
                    viafields += '<div class="suggestionsBox" id="returnSuggStation' + vsIndex + '1" style="display: none;" >'
                    viafields += '<div  class="suggestionList"  id="returnAutoSuggList' + vsIndex + '1"></div></div>'
                    viafields += '</td>'
                    viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="return_JourneyDate' + vsIndex + '1" name="return_JourneyDate' + vsIndex + '" readonly="readonly" size="10"/></td>'
                    viafields += '<td align="left" width="25%"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearchMixed(\'' + vsIndex + '1\')"/></td></tr>'
                    viafields += '</table>';
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="+ Row" onclick="addReturnViaRow(\'' + toStation + '\',\'returnRouteTable' + vsIndex + '\',\'' + vsIndex + '\')"/>&nbsp;<input class="butn" type="button" value="- Row" onclick="delReturnViaRow(\'' + toStation + '\',\'returnRouteTable' + vsIndex + '\');"/></td></tr></table></div>'
                    $("#returnMixedViaDiv" + vsIndex).show("fast");
                    $("#returnMixedViaDiv" + vsIndex).html(viafields);
                    $("#return_JourneyDate" + vsIndex + "0").datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        minDate: $('#travelStartDate').val(),
                        maxDate: $('#travelEndDate').val(),
                        scrollInput: false,
                        yearEnd: 2100,
                        onShow: function() {
                            $(this).val("");
                        }
                    });
                    $("#return_JourneyDate" + vsIndex + "1").datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        minDate: $('#travelStartDate').val(),
                        maxDate: $('#travelEndDate').val(),
                        scrollInput: false,
                        yearEnd: 2100,
                        onShow: function() {
                            $(this).val("");
                        }
                    });
                } else {
                    $("#" + frmStnId).prop("readonly", false);
                    $("#" + toStnId).prop("readonly", false);
                    $('#' + viaRouteFlagId).attr('checked', false);
                    $('#returnViaValidate').val("true");
                    $("#returnViaRute" + vsIndex).val("NO");
                }
            }
        } else {
        $("#" + frmStnId).prop("readonly", false);
        $("#" + toStnId).prop("readonly", false);
         $("#returnMixedViaDiv" + vsIndex).hide("fast");
         $("#returnViaRute" + vsIndex).val("NO");
        }
    } else {
        $('#' + viaRouteFlagId).attr('checked', false);
        $("#returnMixedViaDiv" + vsIndex).hide("fast");
        $("#returnViaRute" + vsIndex).val("NO");
    }
}





/* Function to check return mixed-mode Via-Route Available */
function validateReturnMixedViaRouteStations(fromStn, tostn, vsIndex, viaRouteCheckboxId) {
    var validationFlag = false;
    $.ajax({
        url: $("#context_path").val() + "mb/getAvailableViaRouteStations",
        type: "GET",
        data: {
            fromStation: fromStn,
            toStation: tostn
        },
        dataType: "json",
        async: false,
        success: function(msg) {
            if (msg.length === 0) {
                //alert("No stations found for the selected via-route.");
                var viaRouteCheckBox = "#" + viaRouteCheckboxId;
                $(viaRouteCheckBox).attr('checked', false);
                validationFlag = false;
            } else {
                validationFlag = true;
                //$("#" + fromStn).prop("readonly", true);
                //$("#" + tostn).prop("readonly", true);
                var viafields = "";
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
                viafields += '<tr><td colspan="4" class="lablevalue">Via Route Journey Details</td></tr>'
                viafields += '<tr><td align="left" style="width:25%" class="lablevalue">From Station</td><td align="left" style="width:25%" class="lablevalue">To Station</td><td align="left" style="width:25%" class="lablevalue">Date Of Journey</td><td style="width:25%" class="lablevalue"></td></tr>'
                viafields += '</table>'
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnRouteTable' + vsIndex + '">'
                viafields += '<tr>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" id="returnFrmStation' + vsIndex + '0" name="returnFrmStation' + vsIndex + '" value="' + fromStn + '" readonly/>'
                viafields += '</td>'
                viafields += '<td align="left" width="25%"  id="viaReturnMixedModeStationsDropDwn' + vsIndex + '">'
                viafields += '</td>'
                viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="return_JourneyDate' + vsIndex + '0" name="return_JourneyDate' + vsIndex + '" readonly="readonly" size="10"/></td>'
                viafields += '<td align="left" width="25%"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearchMixed(\'' + vsIndex + '0\')"/></td></tr>'
                viafields += '<tr>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" id="returnFrmStation' + vsIndex + '1" name="returnFrmStation' + vsIndex + '"  readonly/>'
                viafields += '</td>'
                viafields += '<td align="left" width="25%">'
                viafields += '<input type="text" class="txtfldM" value="' + tostn + '" id="returnToStation' + vsIndex + '1" readonly="readonly" name="returnToStation' + vsIndex + '" />'
                viafields += '<div class="suggestionsBox" id="returnSuggStation' + vsIndex + '1" style="display: none;" >'
                viafields += '<div  class="suggestionList"  id="returnAutoSuggList' + vsIndex + '1"></div></div>'
                viafields += '</td>'
                viafields += '<td align="left" width="25%"><input type="text" class="txtfldM" id="return_JourneyDate' + vsIndex + '1" name="return_JourneyDate' + vsIndex + '" readonly="readonly" size="10"/></td>'
                viafields += '<td align="left" width="25%"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearchMixed(\'' + vsIndex + '1\')"/></td></tr>'
                viafields += '</table>';
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="+ Row" onclick="addReturnViaRow(\'' + tostn + '\',\'returnRouteTable' + vsIndex + '\',\'' + vsIndex + '\')"/>&nbsp;<input class="butn" type="button" value="- Row" onclick="delReturnViaRow(\'' + tostn + '\',\'returnRouteTable' + vsIndex + '\');"/></td></tr></table></div>'
                $("#returnMixedViaDiv" + vsIndex).show("fast");
                $("#returnMixedViaDiv" + vsIndex).html(viafields);
                $("#return_JourneyDate" + vsIndex + "0").datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
                    scrollInput: false,
		yearEnd: 2100,
                    onShow: function() {
						 $(this).val("");
					 }
			  });
                $("#return_JourneyDate" + vsIndex + "1").datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
                    scrollInput: false,
		yearEnd: 2100,
                    onShow: function() {
						 $(this).val("");
					 }
			  });
                var options = '';
                options += '<select id="returnToStation' + vsIndex + '0" name="returnToStation' + vsIndex + '" onchange = fillReturnMixedModeFromStation(this.value,\'' + vsIndex + '\')>';
                options += '<option value="">Select</option>';
                $.each(msg, function(index, value) {
                    options += `<option value="${value}">${value}</option>`;
                });
                options += '</select>';
                var viaReturnMixedModeStationsDropDwn = "#viaReturnMixedModeStationsDropDwn" + vsIndex;
                $(viaReturnMixedModeStationsDropDwn).html(options);
			}
        },
        error: function(xhr, status, error) {
           $("#"+viaRouteCheckboxId).prop('checked', false);
            alert("Failed to fetch via route stations. Please try again.");
			}
    });
    return validationFlag;
}




/*  Function to check return Mixed FillStation Value */
function fillReturnMixedModeFromStation(stationValue,vsIndex) {
   var viaMixedRtnToStation = "#returnToStation"+vsIndex+"0";
   var viaMixedRtnFromStation = "#returnFrmStation"+vsIndex+"1";  //frmStationTwo1
   var viaRtnToStation  = $(viaMixedRtnToStation).val();
   var viaRtnFromStation = $(viaMixedRtnFromStation).val();


   if(viaRtnToStation != '' && viaRtnToStation != undefined){
   $(viaMixedRtnFromStation).val('');
   $(viaMixedRtnFromStation).val(viaRtnToStation);
   $(viaMixedRtnFromStation).attr("readonly","readonly");
	}
}







function validateMixedViaFields(frmStnId,toStnId,jrnyDateId,viaRouteFlagId)
{     	//alert("Inside validateMixedViaFields");	
		var check=true;
        var fromStation=$("#"+frmStnId).val();
        var toStation=$("#"+toStnId).val();
        var journeyDate=$("#"+jrnyDateId).val();
        
         if (fromStation == undefined)
         fromStation = $("select#"+frmStnId).val();
         
     	if (toStation == undefined)
         toStation = $("select#"+toStnId).val();  
        
	  	if(fromStation=="")
	  	{
	   		alert("Please Enter From Station Fields");
	   		$("#"+viaRouteFlagId).prop("disabled",false);
	   		$("#"+frmStnId).focus();
	   		$("#"+viaRouteFlagId).prop('checked', false);
		    return false;
	   }
	   if(toStation=="")
	   {
	   		alert("Please Enter To Station ");
	   		$("#"+viaRouteFlagId).prop("disabled",false);
	   		$("#"+toStnId).focus();
	   		$("#"+viaRouteFlagId).prop('checked', false);
		    return false;
	   }
	   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	   {
			alert("Please Enter Journey Date");
			$("#"+viaRouteFlagId).prop("disabled",false);
	   		$("#"+jrnyDateId).focus();
	   		$("#"+viaRouteFlagId).prop('checked', false);
		    return false;
	   }
	  
	   if(!check){
	  	 $("#"+viaRouteFlagId).prop("disabled",false);
	  	 }
	   return check;
}

function validateViaBookingMixed(frmStnId,toStnId,jrnyDateId,viaDivId,viaRouteFlagId)
{
	var frmStn=$("#"+frmStnId).val();
    var toStn=$("#"+toStnId).val();
    
     if (frmStn == undefined)
         frmStn = $("select#"+frmStnId).val();
         
     if (toStn == undefined)
         toStn = $("select#"+toStnId).val();  
         
    var journeyDate=$("#"+jrnyDateId).val();
    var reqType=$('#reqType').val();
	var entitledClass=$('#mixedRailEntitledClass :selected').val();
	var response="";
    var viaRoute="";
    var check=false;
    
    //alert("validateViaBookingMixed:frmStn-"+frmStn+",toStn-"+toStn+",journeyDate-"+journeyDate+",viaDivId-"+viaDivId);
    
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	
	$("#"+viaDivId).show("fast");
	
	//alert("validateViaBookingMixed:frmStn-"+frmStn+",toStn-"+toStn+",journeyDate-"+journeyDate+",viaDivId-"+viaDivId);
	$('#viaValidate').val("false");

	$.ajax(
	{
	  url: $("#context_path").val()+"mb/getTrainListDetails",
      type: "get",
      data: "frmStation="+ frmStn +"&toStation=" + toStn +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass  ,
      dataType: "json",
      async: false,
     
      beforeSend: function() 
      {
			$('#'+viaDivId).html("Searching Direct Trains From["+ frmStn +"] To ["+ toStn +"]... Please wait ...");
			var calHeight=document.body.offsetHeight+20;
	        $("#screen-freeze").css({"height":calHeight + "px"} );
	        $("#screen-freeze").css("display", "block");
			
      },
  			 
      complete: function(){
  		 	$('#'+viaDivId).html("");
  		 	$("#screen-freeze").css("display", "none");
      },
	       
	  success: function(msg)
      {
          //alert("On Success-"+msg);
		 
          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=null)
             errordesc=msg.errorMessage.toUpperCase();
             
          var errorCode=msg.errorMessage;
          
          if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
          {
           		check=true;
          		$('#viaValidate').val("true");
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
          }
           if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 || errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004' || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
          	
              	check=true;
              	$('#viaValidate').val("true");
           }
          if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
          {
          	  	check=true;
              	$('#viaValidate').val("true");
          }
		  else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
          {
            	alert(errorDescptrn);
            	check=true;
            	$('#viaValidate').val("true");
          }	
          else
          {
          		alert("Via Booking Is Not Permmited As Train Exists Between This Route");
          	
          		$("#"+viaRouteFlagId).prop('checked', false);
          		$("#mixedViaDivOne").hide();
          		$('#viaValidate').val("true");
          }
   		  
      	  return check;

      }
      
      
   });
     
   $('#viaValidate').val("true");
   return check;
   
}


function validateReturnViaBookingMixed(frmStnId,toStnId,jrnyDateId,viaDivId,viaRouteFlagId)
{
	var frmStn=$("#"+frmStnId).val();
    var toStn=$("#"+toStnId).val();
    
    if (frmStn == undefined)
         frmStn = $("select#"+frmStnId).val();
         
    if (toStn == undefined)
         toStn = $("select#"+toStnId).val();  
         
    var journeyDate=$("#"+jrnyDateId).val();
    var reqType=$('#reqType').val();
	var entitledClass=$('#returnMixedRailEntitledClass :selected').val();
	var response="";
    var viaRoute="";
    var check=false;
    
    //alert("validateViaBookingMixed:frmStn-"+frmStn+",toStn-"+toStn+",journeyDate-"+journeyDate+",viaDivId-"+viaDivId);
    
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	
	$("#"+viaDivId).show("fast");
	
	//alert("validateViaBookingMixed:frmStn-"+frmStn+",toStn-"+toStn+",journeyDate-"+journeyDate+",viaDivId-"+viaDivId);
	$('#returnViaValidate').val("false");

	$.ajax(
	{
	  url: $("#context_path").val()+"mb/getTrainListDetails",	
      type: "get",
      data: "frmStation="+ frmStn +"&toStation=" + toStn +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass  ,
      dataType: "json",
      async: false,
     
      beforeSend: function() 
      {
			$('#'+viaDivId).html("Searching Direct Trains From["+ frmStn +"] To ["+ toStn +"]... Please wait ...");
			var calHeight=document.body.offsetHeight+20;
	        $("#screen-freeze").css({"height":calHeight + "px"} );
	        $("#screen-freeze").css("display", "block");
			
      },
  			 
      complete: function(){
  		 	$('#'+viaDivId).html("");
  		 	$("#screen-freeze").css("display", "none");
      },

	  success: function(msg)
      {
          //alert("On Success-"+msg);

          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=null)
             errordesc=msg.errorMessage.toUpperCase();
             
          var errorCode=msg.errorMessage;
          
          if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
          {
           		check=true;
          		$('#returnViaValidate').val("true");
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
          }
          if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 || errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004' || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
          	
              	check=true;
              	$('#returnViaValidate').val("true");
           }
          if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
          {
          	  	check=true;
              	$('#returnViaValidate').val("true");
          }
		  else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
          {
          	
            	alert(errorDescptrn);
            	check=true;
            	$('#returnViaValidate').val("true");
          }	
          else
          {
          		alert("Via Booking Is Not Permitted As Train Exists Between This Route");
          		
          		$("#"+viaRouteFlagId).prop('checked', false);
          		$('#returnViaValidate').val("true");
          }
   		  
      	  return check;
      }
      
      
   });
     
   $('#returnViaValidate').val("true");
   return check;
   
}



var x=2;var xZero=2;var xOne=2;
function addViaRow(toStn,viatableId,vsIndex)
{
		if(xZero>2 && viatableId.indexOf("One")!=-1){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;
		}
		
		if(xOne>2 && viatableId.indexOf("Two")!=-1){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;	
		}
		
 if(validateMixedViaRouteTrain(vsIndex))
	{
	 if(viatableId.indexOf("One")!=-1){
	 	x=xZero;
	 }
	 if(viatableId.indexOf("Two")!=-1){
	 	x=xOne;
	 }
	 
     $('#toStation'+vsIndex+eval(x-1)).val("");
     $('#toStation'+vsIndex+eval(x-1)).removeAttr("readonly");
     var element = document.getElementById('toStation' +vsIndex+eval(x-1));
	 if (x == 2) {
		  
		  element.setAttribute('onkeyup',"populateStations('toStation"+vsIndex+eval(x - 1)+"','suggStation"+vsIndex+eval(x - 1)+"','autoSuggList"+vsIndex+eval(x - 1)+"',true,'frmStation"+vsIndex+x+"');");

	 } else {

		 element.setAttribute('onkeyup',"populateStations('toStation"+vsIndex+eval(x - 1)+"','suggestions"+vsIndex+eval(x - 1)+"','autoSuggestionsList"+vsIndex+eval(x - 1)+"',true,'frmStation"+vsIndex+x+"');");

	 }
     
  	 var tbl =document.getElementById(viatableId);
     
     
  	 var lastRow = tbl.rows.length;
  	 
	 var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'frmStation'+vsIndex;
	 el.id = 'frmStation'+vsIndex+x;
	 el.width = '31%';
	 //el.size = 10;
	 el.className="txtfldM";
	 el.setAttribute("readonly","true");

	// el.setAttribute('onkeyup',"populateStations('frmStation"+vsIndex+x+"','suggestionsfrmStation"+vsIndex+x+"','autoSuggestionsListfrmStation"+vsIndex+x+"',true,'frmStation"+vsIndex+(x+1)+"');");
	 
	 //el.setAttribute('onkeyup',"getStationList(this.value,this.id);");
 	
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionsfrmStation'+vsIndex+x;
	 divEl.id = 'suggestionsfrmStation'+vsIndex+x;
	 divEl.className='suggestionsBox';
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListfrmStation'+vsIndex+x;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'toStation'+vsIndex;
	 el.id = 'toStation'+vsIndex+x;
	 //el.size = 10;
 	 el.width = '31%';
	 el.className="txtfldM";
	 
	 //onkeyup="populateStations('toStation"+viatableId+x+"','suggStationrouteTablemixedViaDiv01','autoSuggListrouteTablemixedViaDiv01',true,'frmStationrouteTablemixedViaDiv02')" ty
	  el.setAttribute("readonly","true");
	// el.setAttribute('onkeyup',"populateStations('toStation"+vsIndex+x+"','suggestions"+vsIndex+x+"','autoSuggestionsList"+vsIndex+x+"',true,'frmStation"+vsIndex+(x+1)+"');");
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionstoStation'+x;
	 divEl.id = 'suggestions'+vsIndex+x;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsList'+vsIndex+x;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
 	
 	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
     el.width = '20%';
	 el.name = 'journeyDate'+vsIndex;
	 el.id = 'journeyDate'+vsIndex+x;
	 el.setAttribute("readonly","true");
	 el.className="txtfldM";
	 el.size = 10;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'button';
     el.width = '25%';
	 el.name = 'viaTrnSrch'+x;
	 el.id = 'viaTrnSrch'+x;
	 el.setAttribute('value',"Train Search");
	 el.className="butn";
	 el.setAttribute('onclick',"trainSearchMixed('"+vsIndex+x+"');");
	 cellRight.appendChild(el);
	
	$("#journeyDate"+vsIndex+x).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
	 //new Epoch('cal2','popup',document.getElementById('journeyDate'+k),false);
		    	beforeShow : function () {
						 $(this).val("");
					 }
			  });
     //x++;
     
     if(viatableId.indexOf("One")!=-1){
	 	xZero++;
	 }
	 if(viatableId.indexOf("Two")!=-1){
	 	xOne++;
	 }
}
}
  


function delViaRow(tostn,viatableId)
{   
   	
   	 var tbl =document.getElementById(viatableId);//$('#'+viatableId);// document.getElementById('routeTable');
  	 var rowCount = tbl.rows.length;
     //var rowCount = $('#'+viatableId+' tr').length;
     
     if(rowCount>2)
     {
   	  	 var row1=rowCount-2
      	 $('#'+viatableId+' tr:last').remove();
  	 	 if(viatableId.indexOf("One")!=-1){
		 	$('#toStationOne'+row1).val(tostn);
		 	$('#toStationOne'+row1).prop("readonly", true);
           
           var element = document.getElementById('toStationOne' + row1); 
             element.removeAttribute('onkeyup');
		 	xZero--;
		 }
		 if(viatableId.indexOf("Two")!=-1){ 
		 	$('#toStationTwo'+row1).val(tostn);
		 	$('#toStationTwo'+row1).prop("readonly", true);
		 	var element = document.getElementById('toStationTwo' + row1); 
             element.removeAttribute('onkeyup');
           
		 	xOne--;
		 }
	  
  	 }
  	 else
  	  	alert("Minimum Two Rows Required For Via Booking")

}


var return_x=2;var return_xZero=2;var return_xOne=2;
function addReturnViaRow(toStn,viatableId,vsIndex)
{
	if(return_xZero>2 && viatableId.indexOf("One")!=-1){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;
		}
		
	if(return_xOne>2 && viatableId.indexOf("Two")!=-1){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;
		}
			
 if(validateReturnMixedViaRouteTrain(vsIndex))
	{
	 if(viatableId.indexOf("One")!=-1){
	 	return_x=return_xZero;
	 }
	 if(viatableId.indexOf("Two")!=-1){
	 	return_x=return_xOne;
	 }
	 
     $('#returnToStation'+vsIndex+eval(return_x-1)).val("");
     $('#returnToStation'+vsIndex+eval(return_x-1)).removeAttr("readonly");
     var element = document.getElementById('returnToStation' +vsIndex+eval(return_x-1));
	 if (x == 2) {
     
		  element.setAttribute('onkeyup',"populateReturnStations('returnToStation"+vsIndex+eval(return_x - 1)+"','returnSuggStation"+vsIndex+eval(return_x - 1)+"','returnAutoSuggList"+vsIndex+eval(return_x - 1)+"',true,'returnFrmStation"+vsIndex+return_x+"');");

	 } else {

		 element.setAttribute('onkeyup',"populateReturnStations('returnToStation"+vsIndex+eval(return_x - 1)+"','returnSuggestions"+vsIndex+eval(return_x - 1)+"','returnAutoSuggestionsList"+vsIndex+eval(return_x - 1)+"',true,'returnFrmStation"+vsIndex+return_x+"');");

	 }
     
     
     
     
  	 var tbl =document.getElementById(viatableId);//$('#'+viatableId);// document.getElementById('routeTable');
  	 //alert("Inside addViaRow tbl-"+tbl);
     
  	 var lastRow = tbl.rows.length;
  	 
	 var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnFrmStation'+vsIndex;
	 el.id = 'returnFrmStation'+vsIndex+return_x;
	 el.width = '31%';
	 el.className="txtfldM";
	 el.setAttribute("readonly","true");

	
	 var divEl=document.createElement('div');
	 divEl.name = 'returnSuggestionsfrmStation'+vsIndex+return_x;
	 divEl.id = 'returnSuggestionsfrmStation'+vsIndex+return_x;
	 divEl.className='suggestionsBox';
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'returnAutoSuggestionsListfrmStation'+vsIndex+return_x;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnToStation'+vsIndex;
	 el.id = 'returnToStation'+vsIndex+return_x;
	 el.width = '31%';
	 el.className="txtfldM";
	 
	 el.setAttribute("readonly","true");
	 //el.setAttribute('onkeyup',"populateReturnStations('returnToStation"+vsIndex+return_x+"','returnSuggestions"+vsIndex+return_x+"','returnAutoSuggestionsList"+vsIndex+return_x+"',true,'returnFrmStation"+vsIndex+(return_x+1)+"');");
	 var divEl=document.createElement('div');
	 divEl.name = 'returnSuggestionstoStation'+return_x;
	 divEl.id = 'returnSuggestions'+vsIndex+return_x;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'returnAutoSuggestionsList'+vsIndex+return_x;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
 	
 	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
     el.width = '20%';
	 el.name = 'return_JourneyDate'+vsIndex;
	 el.id = 'return_JourneyDate'+vsIndex+return_x;
	 el.className="txtfldM";
	 el.size = 10;
	 el.setAttribute("readonly","true");
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'button';
     el.width = '25%';
	 el.name = 'returnViaTrnSrch'+return_x;
	 el.id = 'returnViaTrnSrch'+return_x;
	 el.setAttribute('value',"Train Search");
	 el.className="butn";
	 
	 el.setAttribute('onclick',"trainReturnSearchMixed('"+vsIndex+return_x+"');");
	 cellRight.appendChild(el);
	
	$("#return_JourneyDate"+vsIndex+return_x).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		minDate: $('#travelStartDate').val(),
		maxDate: $('#travelEndDate').val(),
		yearEnd: 2100,
		    	onShow : function () {
						 $(this).val("");
					 }
			  });
     
     if(viatableId.indexOf("One")!=-1){
	 	return_xZero++;
	 }
	 if(viatableId.indexOf("Two")!=-1){
	 	return_xOne++;
	 }
}
}
  


function delReturnViaRow(tostn,viatableId)
{   
   	//alert("Inside delRow for Route Details");
   	 var tbl =document.getElementById(viatableId);//$('#'+viatableId);// document.getElementById('routeTable');
  	 var rowCount = tbl.rows.length;
     //var rowCount = $('#'+viatableId+' tr').length;
     
     if(rowCount>2)
     {
   	  	 var row1=rowCount-2
      	 $('#'+viatableId+' tr:last').remove();
  	 	 if(viatableId.indexOf("One")!=-1){
		 	$('#returnToStationOne'+row1).val(tostn);
		 	$('#returnToStationOne'+row1).prop("readonly", true);
		 	 var element = document.getElementById('returnToStationOne' + row1); 
             element.removeAttribute('onkeyup');

		 	return_xZero--;
		 }
		 if(viatableId.indexOf("Two")!=-1){
		 	$('#returnToStationTwo'+row1).val(tostn);
		 	$('#returnToStationTwo'+row1).prop("readonly", true);
		 	 var element = document.getElementById('returnToStationTwo' + row1); 
             element.removeAttribute('onkeyup');

		 	return_xOne--;
		 }
	  
  	 }
  	 else
  	  	alert("Minimum Two Rows Required For Via Booking")

}

function validateReturnMixedViaRouteTrain(viatableId)
{
	var check=false;
	var  index;	
	var toIndex;
	var frmStn;
	var toStn;
	var journeyDate0;
	var journeyDate;
	var reqType=$('#reqType').val();
	var entitledClass;
	var response="";
    var viaRoute="";
	
if(viatableId.indexOf("One")!=-1)
{
	 index=$("input[name='returnFrmStationOne']").last().attr("id").split('returnFrmStationOne')[1];
	 toIndex=index-1;
	 frmStn=$('#returnFrmStationOne'+index).val();
	 toStn=$('#returnToStationOne'+index).val();
	 journeyDate0=$('#return_JourneyDateOne'+toIndex).val();
	 journeyDate=$('#return_JourneyDateOne'+index).val();
	 entitledClass=$('#returnMixedRailEntitledClass :selected').val();
	
       
    
   if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnToStationOne'+toIndex).focus();
	    return check;
	 }
	  
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnToStationOne'+index).focus();
	    return check;
   }
   
	
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDateOne'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDateOne'+index).focus();
    	return check;
   }
   
}
if(viatableId.indexOf("Two")!=-1)
{
	 index=$("input[name='returnFrmStationTwo']").last().attr("id").split('returnFrmStationTwo')[1];
	 toIndex=index-1;
	 frmStn=$('#returnFrmStationTwo'+index).val();
	 toStn=$('#returnToStationTwo'+index).val();
	 journeyDate0=$('#return_JourneyDateTwo'+toIndex).val();
	 journeyDate=$('#return_JourneyDateTwo'+index).val();
	 entitledClass=$('#returnMixedRailEntitledClass :selected').val();
	
       
    
   if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnFrmStationTwo'+toIndex).focus();
	    return check;
	 }
	  
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnToStationTwo'+index).focus();
	    return check;
   }
   
	
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDateTwo'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDateTwo'+index).focus();
    	return check;
   }
   
}
	 
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	 
	$.ajax(
	{
	  url: $("#context_path").val()+"mb/getTrainListDetails",
      type: "get",
      data: "frmStation="+ frmStn +"&toStation=" + toStn +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass ,
      dataType: "json",
      async: false,
     
      beforeSend: function() 
      {
      	var calHeight=document.body.offsetHeight+20;
        $("#screen-freeze").css({"height":calHeight + "px"} );
        $("#screen-freeze").css("display", "block");
      },
	  complete: function(){
  		$("#screen-freeze").css("display", "none");
  	  },
      success: function(msg)
      {
      	 
          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=null)
             errordesc=msg.errorMessage.toUpperCase();
          
           var errorCode=msg.errorMessage;
          
           if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
           {
           		check=true;
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
           }
          
           if(errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004'  || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
              	check=true;
           }
           
           if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
           {
          	  	check=true;
           }

           else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
           {
            	alert(errorDescptrn);
            	check=true;
           }	
           else
           {
          		alert("Via Booking Is Not Permmited As Train Exists Between This Route");
           }
          	
      	  return check;
      }
      
   });
   
  
   return check;
  }

function validateMixedViaRouteTrain(viatableId)
{
	var check=false;
	var  index;	
	var toIndex;
	var frmStn;
	var toStn;
	var journeyDate0;
	var journeyDate;
	var reqType=$('#reqType').val();
	var entitledClass;
	var response="";
    var viaRoute="";
	
if(viatableId.indexOf("One")!=-1)
{
	 index=$("input[name='frmStationOne']").last().attr("id").split('frmStationOne')[1];
	 toIndex=index-1;
	 frmStn=$('#frmStationOne'+index).val();
	 toStn=$('#toStationOne'+index).val();
	 journeyDate0=$('#journeyDateOne'+toIndex).val();
	 journeyDate=$('#journeyDateOne'+index).val();
	 entitledClass=$('#mixedRailEntitledClass :selected').val();
	
       
    
   if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#toStationOne'+toIndex).focus();
	    return check;
	 }
	  
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#toStationOne'+index).focus();
	    return check;
   }
   
	
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDateOne'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDateOne'+index).focus();
    	return check;
   }
   
}
if(viatableId.indexOf("Two")!=-1)
{
	 index=$("input[name='frmStationTwo']").last().attr("id").split('frmStationTwo')[1];
	 toIndex=index-1;
	 frmStn=$('#frmStationTwo'+index).val();
	 toStn=$('#toStationTwo'+index).val();
	 journeyDate0=$('#journeyDateTwo'+toIndex).val();
	 journeyDate=$('#journeyDateTwo'+index).val();
	 entitledClass=$('#mixedRailEntitledClass :selected').val();
	
       
    
   if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#toStationTwo'+toIndex).focus();
	    return check;
	 }
	  
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#toStationTwo'+index).focus();
	    return check;
   }
   
	
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDateTwo'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDateTwo'+index).focus();
    	return check;
   }
   
  }
	 
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	 
	$.ajax(
	{
	  url: $("#context_path").val()+"mb/getTrainListDetails",
      type: "get",
      data: "frmStation="+ frmStn +"&toStation=" + toStn +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass  ,
      dataType: "json",
      async: false,
     
      beforeSend: function() 
      {
      	var calHeight=document.body.offsetHeight+20;
        $("#screen-freeze").css({"height":calHeight + "px"} );
        $("#screen-freeze").css("display", "block");
      },
	  complete: function(){
  		$("#screen-freeze").css("display", "none");
  	  },

      success: function(msg)
      {

          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=null)
             errordesc=msg.errorMessage.toUpperCase();
          
           var errorCode=msg.errorMessage;
          
           if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
           {
           		check=true;
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
           }
          
           if(errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004'  || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
              	check=true;
           }
           
           if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
           {
          	  	check=true;
           }

           else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
           {
            	alert(errorDescptrn);
            	check=true;
           }	
           else
           {
          		alert("Via Booking Is Not Permmited As Train Exists Between This Route");
           }
          	
      	  return check;
      }
      
   });
   
   return check;
  }
  
  function fillStaionValueForViaRoute(thisValue,inputBoxId,divSuggBoxId,fromBoxId) {
	 $('#'+inputBoxId).val(thisValue);
	 $('#'+fromBoxId).val(thisValue);
	 setTimeout("$('#"+divSuggBoxId+"').hide();", 200);
}

function getAirFromAirportOption(value)
{
 	var options="";
 	var oldDutyNap=$("#oldDutyNap").val(); 
	var newDutyNap=$("#newDutyNap").val(); 
	var oldSprNap=$("#oldSprNap").val(); 
	var newSprNap=$("#newSprNap").val(); 
 	
 	var msg = $.parseJSON($('#travelerObj').val());
 	
 	if(value == 0){
	 	options += '<select id="fixedOrgStationDD" name="fixedOrgStationDD" class="combo" onchange="setfixedOrgStnValue(this.value,1)"><option value="">Select</option>'	
 	}else if(value == 1){
 		options += '<select id="returnFixedOrgStationDD" name="returnFixedOrgStationDD" class="combo" onchange="setReturnFixedOrgStnValue(this.value,1)"><option value="">Select</option>'
 	}
 	
	 $.each(msg.airStationListFrom, function(index,obj){
		if(obj.jrnyType==value){
	
		var FromStation = obj.airFromStation;
		var FromStationType = obj.airFromStationType;
		
		if(FromStationType=="New Duty Station" && newDutyNap!=undefined)
			options += '<option value="' + newDutyNap + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="New SPR" && newSprNap!=undefined)
			options += '<option value="' + newSprNap + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="Old SPR" && oldSprNap!=undefined)
			options += '<option value="' + oldSprNap + '">' +FromStationType + '<\/option>';
		else if(FromStationType=="Old Duty Station" && oldDutyNap!=undefined)
			options += '<option value="' + oldDutyNap + '">' +FromStationType + '<\/option>';
		else
			options += '<option value="' + FromStation + '">' +FromStationType + '<\/option>';
			
		}
	}); 
	if(options=="<select>"){
		options = 'No From Station Exists In Profile';
	}else{
      options += '</select>'
	}   
		
 	return options;	
}

function getRailToStnOption(value){
 	
 	var options="";
 	var oldDutyStn=$("#oldDutyStn").val(); 
	var newDutyStn=$("#newDutyStn").val(); 
	var oldSprNRS=$("#oldSprNRS").val(); 
	var newSprNRS=$("#newSprNRS").val(); 
 	
 	var msg = $.parseJSON($('#travelerObj').val());
	
	if(value == 0){
		options += '<select id="fixedDestStationDD" name="fixedDestStationDD" class="combo" onchange="setfixedDestStnValue(this.value,0)"><option value="">Select</option>'
	}else if (value == 1){
		options += '<select id="returnFixedDestStationDD" name="returnFixedDestStationDD" class="combo" onchange="setReturnFixedDestStnValue(this.value,0)"><option value="">Select</option>'
	}
 	
  	$.each(msg.toStationList, function(index,obj){
		if(obj.jrnyType==value){
  	
		var ToStation = obj.toStation;
		var ToStationType = obj.toStationType;
		
		if(ToStationType=="New Duty Station" && newDutyStn!=undefined)
			options += '<option value="' + newDutyStn + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="New SPR" && newSprNRS!=undefined)
			options += '<option value="' + newSprNRS + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="Old SPR" && oldSprNRS!=undefined)
			options += '<option value="' + oldSprNRS + '">' +ToStationType + '<\/option>';
		else if(ToStationType=="Old Duty Station" && oldDutyStn!=undefined)
			options += '<option value="' + oldDutyStn + '">' +ToStationType + '<\/option>';
		else
			options += '<option value="' + ToStation + '">' +ToStationType + '<\/option>';
			
		}
 	}); 
		
	if(options=="<select>"){
		options = 'No TO Station Exists In Profile';
 	}else{
    	options +="</select>"
 	}
 	
 	return options;	
}


function showFlightList(srcStnId,destStnId,jrDateId)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var frmStn=$('#'+srcStnId).val();
	var toStn=$('#'+destStnId).val();
	var journeyDate=$('#'+jrDateId).val();

	var isFiledSet=true;
	if(frmStn=="")
	{
	   alert("Please Enter From Station.");
	   $('#'+srcStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(toStn=="")
	{
	   alert("Please Enter To Station.");
	   $('#'+destStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	{
		alert("Please Enter Journey Date")
		$('#'+jrDateId).focus();
        isFiledSet=false;
        return;
	}
	
	var entitledClass=$('#mixedAirEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Air Entitled Class")
		$('#mixedAirEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
		
		var result = getMixedRequestTravellerCount(journeyDate);
		if(result!=false)
		{
			var adultCount = result[0];
			var childCount = result[1];
			var infantCount = result[2];
			var orgStr=frmStn;
			var destStr=toStn;
			
			var originName=orgStr.substring(0, orgStr.lastIndexOf("("));  
			var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
			
			var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
			var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
			//alert("origin:"+origin+"||originName-"+originName+"||journeyDate-"+journeyDate);
			//alert("destination:"+destination+"||destinationName-"+destinationName);
			if(origin==destination){
				alert("Origin And Destination Airport Can Not Be Same.");
				return false;
			}
			// TezPur Airport code 

			if (origin == 'TEZ' || destination == 'TEZ') {

				if (!confirm("Your Request and booking will be done from Itanagar Airport(Assam) instead of Tezpur Airport(Assam) being non-operational !!")) {
					return false;
				}
			
				
				if (origin == 'TEZ') {
					$('#oldAirport').val('TEZ');
					origin = 'HGI';
					$('#'+srcStnId).val('Donyi Polo Airport,Itanagar,Assam,India(HGI)')
					originName = 'Donyi Polo Airport, Itanagar,Assam,India';




				}
				if (destination == 'TEZ') {
					$('#oldAirport').val('TEZ');
					destination = 'HGI';
					$('#'+destStnId).val('Donyi Polo Airport,Itanagar,Assam,India(HGI)');
					destinationName = 'Donyi Polo Airport, Itanagar,Assam,India';
				}
			}
			// TezPur Airport code end
			
			
			var jrType=0;
			var travelTypeId =$('#TravelTypeDD').val();
			var returnJourneyDate=$('#returnJourneyDate').val();
			var preferAirline='All';
			var tripWayValue=$('#tripWay').val();
	 		tripWayValue="OneWay";
	 		var serviceType=$('#serviceType').val();
	 		var TRRule=$("#TRRule").val();
	 		var airlineType=$('#mixedAirlineType').val();
	 		var otherAirlineType=$('#mixedOtherAirlineType').val();
	 		var personalNo=$("#personalNo").val();
	 		//alert("originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&searchFrom=Create");
	 		var xy = getScrollXY();
			var x = xy[0];
			var y = xy[1];
			
			var airViaCount = $('#airMixedLTCViaRouteTable tr').length;
			var connectRoute="";
			if(airViaCount > 0){
				for(i = 0; i < airViaCount; i++){
					var viaString=$("#mixedLTCViaDestination"+i).val();
					var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
		 		  if(viaStationCode== ""){
		 		  	alert("Please Enter Air Via Route for Return Journey.");
		 		  	return false;
		 		  }else{
		 		  	  if(i==0){
		 		  	  	connectRoute=connectRoute+viaStationCode;
		 		  	  }else{
		 		  	  	connectRoute=connectRoute+"~"+viaStationCode;
		 		  	  }
		 		  }
		 	    }
		 	    
		 	  
			}
			
			$.ajax(
 			{
	          url: $("#context_path").val()+"mb/airSrchRsltAjax",
		      type: "get",
		      data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&travelTypeId="+travelTypeId+"&journeyType="+jrType+"&serviceType="+serviceType+"&searchFrom=Create&trRule="+TRRule+"&airlineType="+airlineType+"&otherAirlineType="+otherAirlineType+"&connectRoute="+connectRoute+"&personalNo="+personalNo+"&airViaCount="+airViaCount,
			  dataType: "text",
	          beforeSend: function()
		      {
		        	imageurltitle='/pcda/images/fbloader.gif';
		       	 	box = new LightFace
		       	 	({ 
		        			title: "<img src='"+imageurltitle+"'> Loading ...",
							width: 500,
							height: 200,
							content: "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For Journey Date- "+journeyDate+"</b> <br/><br/>is being populated... <br/>Please wait ...</center>",
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
			   		 	showTabContent(jrType,orgStr,destStr,'Mixed');
						
			   		}
					else
					{
						 showTabContent(jrType,orgStr,destStr,'Mixed');
					}
					$('#childBody').on('wheel.modal mousewheel.modal', function () {return false;});
					$('#parentBody').css({'overflow-y':'hidden'});
					$('#OtherTable').tablesorter(); 
					$('#AITable').tablesorter(); 
					
              }
	      });
	 
		}
		
	}
}


function showReturnFlightList(srcStnId,destStnId,jrDateId)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var frmStn=$('#'+srcStnId).val();
	var toStn=$('#'+destStnId).val();
	var journeyDate=$('#'+jrDateId).val();

	var isFiledSet=true;
	if(frmStn=="")
	{
	   alert("Please Enter From Station.");
	   $('#'+srcStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(toStn=="")
	{
	   alert("Please Enter To Station.");
	   $('#'+destStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	{
		alert("Please Enter Journey Date")
		$('#'+jrDateId).focus();
        isFiledSet=false;
        return;
	}
	
	var entitledClass=$('#returnMixedAirEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Air Entitled Class")
		$('#returnMixedAirEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
		
		var result = getReturnMixedRequestTravellerCount(journeyDate);
		if(result!=false)
		{
			var adultCount = result[0];
			var childCount = result[1];
			var infantCount = result[2];
			var orgStr=frmStn;
			var destStr=toStn;
			
			var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
			var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
			
			var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
			var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
			//alert("origin:"+origin+"||originName-"+originName+"||journeyDate-"+journeyDate);
			//alert("destination:"+destination+"||destinationName-"+destinationName);
			if(origin==destination){
				alert("Origin And Destination Airport Can Not Be Same.");
				return false;
			}
			
			//Tezpur Airport Change
			if(origin=='TEZ'||destination=='TEZ'){
			
			if(!confirm("Your Request and booking will be done from Itanagar Airport(Assam) instead of Tezpur Airport(Assam) being non-operational !!")){
			return false;
		}
		if(origin=='TEZ'){
			$('#returnOldAirport').val('TEZ');
			origin='HGI';
			$('#'+srcStnId).val('Donyi Polo Airport,Itanagar,Assam,India(HGI)');
			originName='Donyi Polo Airport, Itanagar,Assam,India';

			
		}
			
		
		if(destination=='TEZ'){
			$('#returnOldAirport').val('TEZ');
			destination='HGI';
			$('#'+destStnId).val('Donyi Polo Airport,Itanagar,Assam,India(HGI)');
			destinationName='Donyi Polo Airport, Itanagar,Assam,India';
		}
		}
			//Tezpur Airport Change
			
			var jrType=1;
			var travelTypeId =$('#TravelTypeDD').val();
			var returnJourneyDate=$('#returnReturnJourneyDate').val();
			var preferAirline='All';
			var tripWayValue=$('#tripWay').val();
	 		tripWayValue="OneWay";
	 		var serviceType=$('#serviceType').val();
	 		var TRRule=$("#TRRule").val();
	 		var airlineType=$('#returnMixedAirlineType').val();
	 		var otherAirlineType=$('#returnMixedOtherAirlineType').val();
	 		var personalNo=$("#personalNo").val();
	 		//alert("originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&searchFrom=Create");
	 		var xy = getScrollXY();
			var x = xy[0];
			var y = xy[1];
			
			var airViaCount = $('#returnAirMixedLTCViaRouteTable tr').length;
		    var connectRoute="";
		    if(airViaCount > 0){
			for(i = 0; i < airViaCount; i++){
				var viaString=$("#returnMixedLtcViaDestination"+i).val();
				var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
	 		  if(viaStationCode== ""){
	 		  	alert("Please Enter Air Via Route for Return Journey.");
	 		  	return false;
	 		  }else{
	 		  	  if(i==0){
	 		  	  	connectRoute=connectRoute+viaStationCode;
	 		  	  }else{
	 		  	  	connectRoute=connectRoute+"~"+viaStationCode;
	 		  	  }
	 		  }
	 	    }
	 	    
	 	   
		 }
			
			$.ajax(
 			{
	          url: $("#context_path").val()+"mb/airSrchRsltAjax",
		      type: "get",
		      data: "originCode="+origin+"&destinationCode="+destination+"&dtpickerDepart="+journeyDate+"&dtpickerReturn="+returnJourneyDate+"&classType="+entitledClass+"&tripWay="+tripWayValue+"&airlinePreference="+preferAirline+"&noOfAdult="+adultCount+"&noOfChild="+childCount+"&noOfInfant="+infantCount+"&travelTypeId="+travelTypeId+"&journeyType="+jrType+"&serviceType="+serviceType+"&searchFrom=Create&trRule="+TRRule+"&airlineType="+airlineType+"&otherAirlineType="+otherAirlineType+"&connectRoute="+connectRoute+"&personalNo="+personalNo+"&airViaCount="+airViaCount,
			  dataType: "text",
	          beforeSend: function()
		      {
		        	imageurltitle='/pcda/images/fbloader.gif';
		       	 	box = new LightFace
		       	 	({ 
		        			title: "<img src='"+imageurltitle+"'> Loading ...",
							width: 500,
							height: 200,
							content: "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For Journey Date- "+journeyDate+"</b> <br/><br/>is being populated... <br/>Please wait ...</center>",
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
			   		 	showTabContent(jrType,orgStr,destStr,'Mixed');
						
			   		}
					else
					{
						 showTabContent(jrType,orgStr,destStr,'Mixed');
					}
					$('#childBody').on('wheel.modal mousewheel.modal', function () {return false;});
					$('#parentBody').css({'overflow-y':'hidden'});
					$('#OtherTable').tablesorter(); 
					$('#AITable').tablesorter(); 
					
              }
	      });
	 
		}
		
	}
}

function showTrainList(srcStnId,destStnId,jrDateId)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var frmStn=$('#'+srcStnId).val();
	var toStn=$('#'+destStnId).val();
	var journeyDate=$('#'+jrDateId).val();

	var isFiledSet=true;
	if(frmStn=="")
	{
	   alert("Please Enter From Station.");
	   $('#'+srcStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(toStn=="")
	{
	   alert("Please Enter To Station.");
	   $('#'+destStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	{
		alert("Please Enter Journey Date")
		$('#'+jrDateId).focus();
        isFiledSet=false;
        return;
	}
	
	var entitledClass=$('#mixedRailEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Rail Entitled Class")
		$('#mixedRailEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
		
		var frmStnCode=getStationCode(frmStn);
		var toStnCode=getStationCode(toStn);
	 	journeyDate=changeDateFormat(journeyDate)
	 
	 	ajaxRequestFace = new LightFace.Request(
    	{
    		width: 600,
			height: 400,
			url: $("#context_path").val()+"mb/irSrchRsltPage",
			buttons: [
				{ title: 'Close', event: function() { this.close(); } }
			],
			request: { 
				data: { 
					frmStation: frmStnCode	,
					toStation: toStnCode ,
					dep_date:journeyDate
					
				},
				method: 'post'
			},
			title: 'Train List'
		});
    	ajaxRequestFace.open();
  
 	} 
}


function showReturnTrainList(srcStnId,destStnId,jrDateId)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var frmStn=$('#'+srcStnId).val();
	var toStn=$('#'+destStnId).val();
	var journeyDate=$('#'+jrDateId).val();

	var isFiledSet=true;
	if(frmStn=="")
	{
	   alert("Please Enter From Station.");
	   $('#'+srcStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(toStn=="")
	{
	   alert("Please Enter To Station.");
	   $('#'+destStnId).focus();
	   isFiledSet=false;
	   return;
	}
	if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	{
		alert("Please Enter Journey Date")
		$('#'+jrDateId).focus();
        isFiledSet=false;
        return;
	}
	
	var entitledClass=$('#returnMixedRailEntitledClass').val();

	if(entitledClass=="" )
	{
		alert("Please Choose Rail Entitled Class")
		$('#returnMixedRailEntitledClass').focus();
        isFiledSet=false;
        return;
	}
	
	if(isFiledSet)
	{
		
		var frmStnCode=getStationCode(frmStn);
		var toStnCode=getStationCode(toStn);
	 	journeyDate=changeDateFormat(journeyDate)
	 
	 	ajaxRequestFace = new LightFace.Request(
    	{
    		width: 600,
			height: 400,
			url: $("#context_path").val()+"mb/irSrchRsltPage",
			buttons: [
				{ title: 'Close', event: function() { this.close(); } }
			],
			request: { 
				data: { 
					frmStation: frmStnCode	,
					toStation: toStnCode ,
					dep_date:journeyDate
					
				},
				method: 'post'
			},
			title: 'Train List'
		});
    	ajaxRequestFace.open();
  
 	} 
}


function getMixedRequestTravellerCount(journeyDate)
{
	
	var dobStr = document.getElementById("dob").value;
    var totalTravellerArr = dobStr.split(",");
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
		return calculateAge(dobArr,journeyDate);
	}
	else
	{
		alert("Please check atleast one travller");
		return false;
	}
}


function getReturnMixedRequestTravellerCount(journeyDate)
{
	
	var dobStr = document.getElementById("returnDOB").value;
    var totalTravellerArr = dobStr.split(",");
	var tempCount = 0;
	var dobArr = new Array();
	for(var i=0; i<totalTravellerArr.length; i++)
	{
		if($('#returnRemoveFrmList'+i).is(":checked"))
		{
			//alert( $('#dobCheck'+i).val());
			dobArr[tempCount] = $('#returnDobCheck'+i).val();
			tempCount++;
		}
	}
    

	if ($("#isAttendentBooking").val() == 'Yes') 
	{
		  var attendentCount = $("#returnFinalAttendentCount").val();
          for (i = 1; i <= attendentCount; i++) 
          {
     			 if (document.getElementById("returnAttDob" + i).value != '') 
     			 {
     			 	dobArr[tempCount]=$('#returnAttDob'+i).val();
     			 	tempCount++;
                 }        	
          }
	}
	
	if ($("#isPartyBooking").val() == 'Yes') 
    {

    	  var partyCount = $("#returnPartyMemberCount").val();
    	  for (i = 1; i <= partyCount; i++) 
          {
          		dobArr[tempCount]=$('#returnPartyDepDOB'+i).val();
     			tempCount++;
          }
          
    }
    
	
	if(tempCount>0)
	{
		return calculateAge(dobArr,journeyDate);
	}
	else
	{
		alert("Please check atleast one travller");
		return false;
	}
}


function fillfixedOrgStn(thisValue) {
	 $('#fixedOrigin').val(thisValue);
	 setTimeout("$('#fixedOrgSuggestion').hide();", 200);
 }

function fillfixedDestStn(thisValue) {
	 $('#returnFixedDestination').val(thisValue);
	 setTimeout("$('#returnFixedDestSuggestion').hide();", 200);
 
 }
 
 function fillReturnFixedOrgStn(thisValue) {
	 $('#returnFixedOrigin').val(thisValue);
	 setTimeout("$('#fixedOrgSuggestion').hide();", 200);
 }

function fillReturnFixedDestStn(thisValue) {
	 $('#returnFixedDestination').val(thisValue);
	 setTimeout("$('#returnFixedDestSuggestion').hide();", 200);
 
 }
 
function populateFixedDestAirportOption(value)
{
 	var msg = $.parseJSON($('#travelerObj').val());
	var airportOption="";
	$.each(msg.airStationListTo, function(index,obj){
		if(obj.jrnyType==value){
		var ToStation = obj.airToStation;
		var ToStationType = obj.airToStationType;
		if(ToStationType=="Fixed")
		{
			var toStationList=ToStation.split("::");
			airportOption +='<option value="">Select</option>'
            for(var i=0;i<toStationList.length;i++)
            {
                airportOption+='<option value="' +toStationList[i] +'" >' +toStationList[i]+'</option>';
            }
            airportOption+='</select>';
		}
	  }
	}); 
	return airportOption;
} 

function trainSearchMixed(i)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

	//alert("Inside trainSearch");	
    var frmStn=$('#frmStation'+i).val();
    var toStn=$('#toStation'+i).val();
    if(validateViaFieldsForViaSearchMixed(i))
    {
		frmStn=getStationCode(frmStn);
		toStn=getStationCode(toStn);
	 	var journeyDate=$('#journeyDate'+i).val();
	 	journeyDate=changeDateFormat(journeyDate)
	 	var entitledClass=$('#entitledClass').val();
         var bookingType=$('#TravelTypeDD').val();

		ajaxRequestFace = new LightFace.Request(
		{
		    		width: 800,
					height: 600,
	
					url: $("#context_path").val()+"mb/irSrchRsltPage",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							frmStation: frmStn	,
							toStation: toStn ,
							dep_date:journeyDate
							
						},
						method: 'post'
					},
					title: 'Train List'
		});
   		
   		ajaxRequestFace.open();
    
 	} 
}

function validateViaFieldsForViaSearchMixed(i)
{     	
		//alert("Inside validateViaFieldsForViaSearch");	
		var check="";
        
        var frmStn=$('#frmStation'+i).val();
    	var toStn=$('#toStation'+i).val();
    	var journeyDate=$('#journeyDate'+i).val();
        
      //  alert("frmStn="+frmStn+"||toStn="+toStn+"||journeyDate="+journeyDate);

        
        if(document.getElementById("viaRelxRoute")!=null && document.getElementById("viaRelxRoute").checked==true)
        {
        	alert("You can Either select Via Route or Relaxed Route")
        	document.getElementById("viaRoute").checked=false;
        	return false;
       	}
       if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	   {
			alert("Please Enter Journey Date")
			$('#journeyDate'+i).focus();
        	document.getElementById("viaRoute").checked=false;
	    	return false;
	   }
	  	if(frmStn=="")
	  	{
	   		alert("Please Enter From Station And To Station Fields");
	   		document.getElementById("viaRoute").checked=false;
		    return false;
	   }
	   if(toStn=="")
	   {
	   		alert("Please Enter From Station And To Station Fields");
		    document.getElementById("viaRoute").checked=false;
		    return false;
	   }

	   check=validate_required(document.getElementById("mixedRailEntitledClass"),"Please Select EntitlesClass");
	   if(!check)
	   document.getElementById("viaRoute").checked=false;
	   
	   return check;
	   
} 	


function trainReturnSearchMixed(i) {
	
	//alert("createReturn==mixedrail");
	if (parseInt($("#countdown").val()) < 5) {
		alert("Your Session Has Been Expired. Kindly Re-login");
		return false;
	}

	var frmStn = $('#returnFrmStation' + i).val();
	var toStn = $('#returnToStation' + i).val();
	if (validateReturnViaFieldsForViaSearchMixed(i)) {
		frmStn = getStationCode(frmStn);
		toStn = getStationCode(toStn);
		var journeyDate = $('#return_JourneyDate' + i).val();
		journeyDate = changeDateFormat(journeyDate)
		

		ajaxRequestFace = new LightFace.Request(
			{
				width: 800,
				height: 600,
				url: $("#context_path").val() + "mb/irSrchRsltPage",
				buttons: [
					{ title: 'Close', event: function() { this.close(); } }
				],
				request: {
					data: {
						frmStation: frmStn,
						toStation: toStn,
						dep_date: journeyDate
					},
					method: 'post'
				},
				title: 'Train List'
			});

		ajaxRequestFace.open();

	}
}
function validateReturnViaFieldsForViaSearchMixed(i)
{     	
		//alert("Inside validateViaFieldsForViaSearch");	
		var check="";
        
        var frmStn=$('#returnFrmStation'+i).val();
    	var toStn=$('#returnToStation'+i).val();
    	var journeyDate=$('#return_JourneyDate'+i).val();
        
      //  alert("frmStn="+frmStn+"||toStn="+toStn+"||journeyDate="+journeyDate);
      
        
        if(document.getElementById("returnViaRelxRoute")!=null && document.getElementById("returnViaRelxRoute").checked==true)
        {
        	alert("You can Either select Via Route or Relaxed Route")
        	document.getElementById("returnViaRoute").checked=false;
        	return false;
       	}
        if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
	    {
			alert("Please Enter Journey Date")
			$('#return_JourneyDate'+i).focus();
        	document.getElementById("returnViaRoute").checked=false;
	    	return false;
	    }
	  	if(frmStn=="")
	  	{
	   		alert("Please Enter From Station And To Station Fields");
	   		document.getElementById("returnViaRoute").checked=false;
		    return false;
	   }
	   if(toStn=="")
	   {
	   		alert("Please Enter From Station And To Station Fields");
		    document.getElementById("returnViaRoute").checked=false;
		    return false;
	   }
	   
	   check=validate_required(document.getElementById("returnMixedRailEntitledClass"),"Please Select EntitlesClass");
	   if(!check)
	   document.getElementById("returnViaRoute").checked=false;
	   
	   return check;
	  
} 	


