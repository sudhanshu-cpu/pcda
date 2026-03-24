
function setOrgStnValue(stnValue)
{
 
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
	   stnStr +='<select id="origin" name="origin" width="325px" class="combo" onchange="fillOrgStn(this.value);" style="margin:0;padding:5px;border-radius:5px;background:#fff;color:#363738;">';
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


function setReturnOrgStnValue(stnValue)
{
 
  $("#from_Return_Station_Id").val($('#returnOrgStationDD option:selected').text());
  $("#returnRemoveFrmList0").attr('checked', false);
  setReturnOrgTypeValue();
  
  var stnStr="";
  if(stnValue=='AnyStation')
  {
   	$("#returnOrgContainer").show(); 
   	$("#returnTrOrgStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="returnTrOrgStn"><td><input type="hidden" name="returnOrigin" value="'+stnValue +'" class="txtfldM" id="returnOrigin"  /><label>'+stnValue +'</label></td></div>'
	   $("#returnOrigin").val(stnValue);   
	   $("#returnTrOrgStn").html(stnStr); 
	   $("#returnTrOrgStn").show(); 
	   $("#returnOrgContainer").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="returnTrOrgStn"><td>';
	   stnStr +='<select id="returnOrigin" name="returnOrigin" class="combo" width="325px" onchange="fillReturnOrgStn(this.value);" style="margin:0;padding:5px;border-radius:5px;background:#fff;color:#363738;">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#returnTrOrgStn").html(stnStr); 
	   $("#returnTrOrgStn").show(); 
	   $("#returnOrgContainer").hide();
	  		
	}
  }
	   
}

function setOrgTypeValue(){
	
	var stnTextValue=$('#orgStationDD option:selected').text();
	if(stnTextValue=='Select')$("#originType").val('');  		
	
	for(i=0;i<orgTypeString.length;i++)
	{
		if(orgTypeString[i].name==stnTextValue){
			$("#originType").val(orgTypeString[i].id);  		
		}
		
	}
	
}


function setReturnOrgTypeValue(){
	
	var stnTextValue=$('#returnOrgStationDD option:selected').text();
	if(stnTextValue=='Select')$("#returnOriginType").val('');  		
	
	for(i=0;i<orgTypeString.length;i++)
	{
		if(orgTypeString[i].name==stnTextValue){
			$("#returnOriginType").val(orgTypeString[i].id);  		
		}
		
	}
	
}

function setDestStnValue(stnValue)
{
	setDestTypeValue();
 	var stnStr="";
  	
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
	   
	   		stnStr +='<select id="destination" name="destination" class="combo" width="325px" onchange="fillDestStn(this.value);">';
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

function setReturnDestStnValue(stnValue)
{
	setReturnDestTypeValue();
 	var stnStr="";
  	
  	$("#to_Return_Station_Id").val($('#returnDestStationDD option:selected').text());
  	$("#returnRemoveFrmList0").attr('checked', false);
    if(stnValue=='AnyStation')
    {
    	$("#returnDestContainer").show();
        $("#returnTrDestStn").hide(); 
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="returnTrDestStn"><td><input type="hidden" name="returnDestination" value="'+stnValue +'"  class="txtfldM" id="returnDestination" /><label>'+stnValue +'</label><td></div>'
	    	$("#returnDestination").val(stnValue);   
	      	$("#returnTrDestStn").html(stnStr); 
	      	$("#returnTrDestStn").show();
	    	$("#returnDestContainer").hide();
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="returnTrDestStn"><td>';
	   
	   		stnStr +='<select id="returnDestination" name="returnDestination" class="combo" width="325px" onchange="fillReturnDestStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#returnTrDestStn").html(stnStr); 
		    $("#returnTrDestStn").show();
		    $("#returnDestContainer").hide();
	  	
	  	}
	
	  }
	  
}

function setDestTypeValue(){
	var stnTextValue=$('#destStationDD option:selected').text();
	if(stnTextValue=='Select')$("#destinationType").val('');  		
	
	for(i=0;i<destTypeString.length;i++)
	{
		if(destTypeString[i].name==stnTextValue){
			$("#destinationType").val(destTypeString[i].id);  		
		}
	}
}

function setReturnDestTypeValue(){
	var stnTextValue=$('#returnDestStationDD option:selected').text();
	if(stnTextValue=='Select')$("#returnDestinationType").val('');  		
	
	for(i=0;i<destTypeString.length;i++)
	{
		if(destTypeString[i].name==stnTextValue){
			$("#returnDestinationType").val(destTypeString[i].id);  		
		}
	}
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



function showAir()
{
	var airAccountOffice = $("#airAccountOffice").val();
	if(airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0){
	   alert("Pay account office for air travel is not assigned so air travel request can not be created.")
	   $("#airAccountOffice").focus();
    }
    
	$("#travelMode").val(1);	
	$("#airRequestXform").show();
	$("#mixedPreferenceDiv").hide();
	setSourceDestinationByTrRule();

}


function showReturnAir()
{
	var airAccountOffice = $("#airAccountOffice").val();
	if(airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0){
	   alert("Pay account office for air travel is not assigned so air travel request can not be created.")
	   $("#airAccountOffice").focus();
    }
    
    
    $("#returnTravelMode").val(1);

	
	$("#airReturnRequestXform").show();
	

	
	setReturnSourceDestinationByTrRule();
	
	
}



function fillOrgStn(thisValue) {
	 $('#origin').val(thisValue);
	 setTimeout("$('#orgSuggestion').hide();", 200);
 }

function fillDestStn(thisValue) {
	 $('#destination').val(thisValue);
	 setTimeout("$('#destSuggestion').hide();", 200);
 
 }
 
 function fillReturnOrgStn(thisValue) {
	 $('#returnOrigin').val(thisValue);
	 setTimeout("$('#returnOrgSuggestion').hide();", 200);
 }

function fillReturnDestStn(thisValue) {
	 $('#returnDestination').val(thisValue);
	 setTimeout("$('#returnDestSuggestion').hide();", 200);
 
 }
 
 function fillFixedOrgStn(thisValue) {
	 $('#fixedOrigin').val(thisValue);
	 setTimeout("$('#fixedOrgSuggestion').hide();", 200);
 }

function fillFixedDestStn(thisValue) {
	 $('#fixedDestination').val(thisValue);
	 setTimeout("$('#fixedDestSuggestion').hide();", 200);
 
 }
 
 function fillFixedReturnOrgStn(thisValue) {
	 $('#returnFixedOrigin').val(thisValue);
	 setTimeout("$('#returnFixedOrgSuggestion').hide();", 200);
 }

function fillFixedReturnDestStn(thisValue) {
	 $('#returnFixedDestination').val(thisValue);
	 setTimeout("$('#returnFixedDestSuggestion').hide();", 200);
 
 }
 
function showHideReturnDate(obj)
{
	
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

function showReturnHideReturnDate(obj)
{
	
	if(obj != null && obj.value == 0){
		$("#returnReturnDateRow").hide();
		document.getElementById('returnTripType').value = 0;
	}else if(obj != null && obj.value == 1){
		$("#returnReturnDateRow").show();
		document.getElementById('returnTripType').value = 1;
	}else if(obj != null && obj.value == 2){
		$("#returnReturnDateRow").show();
		document.getElementById('returnTripType').value = 2;
	}	
}

var orgTypeString = [];
var destTypeString = [];

function setOrgDestStnByTrRule(msg,ramBaanCheck)
{
	
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
		msg = $.parseJSON($('#travelerObj').val());
	}
	
	msg = $.parseJSON($('#travelerObj').val());

	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var trRuleID=$('#TRRule :selected').val();
     var x=0;
	$.each(msg.familyDetail, function(i,obj){
					 
		 
			 	var JourneyCheck;
				var JourneyCheckRet;
				var relation=document.getElementById("relationCheck"+i).value
	
			    if($('#removeFrmList'+i).is(":checked"))
			    {
			
						$("#requisiteDiv").show();
						$("#reqstCheck").val("true")
					
				}
			
				JourneyCheck=obj.journeyCheck;
				if(JourneyCheck=='true')
				{
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>');
		     			$("#reasonDiv"+i).hide();
				}
				else
				{
					
						$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>');
						$("#reasonDiv"+i).hide();
					
						
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

		orgOptions += '<select id="orgStationDD" name="orgStationDD" onchange="setOrgStnValue(this.value)" class="combo"><option value="">Select</option>';
		
		 $.each(msg.airStationListFrom, function(index,obj){
	   
		if(obj.jrnyType==0){
			var FromStation = obj.airFromStation;
			var FromStationType = obj.airFromStationType;
			var FromStationTypeInt = obj.airFromStationTypeInt;
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
				
		  }		
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
        	
       		$("#orgStnDropDown").show();       
       
        }else{
       		$("#orgStnDropDown").show();       
       	}
       	
    
    	destOptions += '<select id="destStationDD" name="destStationDD" onchange="setDestStnValue(this.value)" class="combo"><option value="">Select</option>';
    	
    	$.each(msg.airStationListTo, function(index,obj){
	  	
	  	if(obj.jrnyType==0){
			var ToStation = obj.airToStation;
			var ToStationType = obj.airToStationType;
			var ToStationTypeInt = obj.airToStationTypeInt;
			
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
			
			}
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
        	$("#destStnDropDown").show();
	  	
		}else
		{
       		$("#destStnDropDown").show();
	  	}			  
	}	// End of if(ramBaanCheck!="true")		  
	 

}



var orgReturnTypeString = [];
var destReturnTypeString = [];

function setReturnOrgDestStnByTrRule(msg,ramBaanCheck)
{
	//alert("airTravelRequest.js:1574:setOrgDestStnByTrRule-value-"+value);
	var orgOptions="";
	var destOptions="";
	var destStn="";
	
	// Hide Round Trip Block For LTC Return Journey
	hideReturnRoundTrip();
	
	
	var taggCheck=$('#taggCheck').val();
	var oldDutyNap=$("#oldDutyNap").val();
	var newDutyNap=$("#newDutyNap").val();
	var oldSprNap=$("#oldSprNap").val();
	var newSprNap=$("#newSprNap").val();
	
	var taggedLtcYear='';
	if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
	$("#returnOrigin").val(""); 
	$("#returnDestination").val(""); 
	$("#returnTrOrgStn").html("<label/>"); 
	$("#returnTrDestStn").html("<label/>"); 
	$("#returnOrgContainer").hide();
	$("#returnDestContainer").hide();
	
	if(msg=="")
	{
		msg = $.parseJSON($('#travelerObj').val());
	}
	
	msg = $.parseJSON($('#travelerObj').val());

	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var trRuleID=$('#TRRule :selected').val();
     var x=0;
			
	$.each(msg.familyDetail, function(i, obj) {
		var JourneyCheck;
		var JourneyCheckRet;
		var relation = document.getElementById("returnRelationCheck" + i).value

		if ($('#returnRemoveFrmList' + i).is(":checked")) {
			if (relation == "Self") {
				$("#requisiteDiv").hide();
				$("#reqstCheck").val("false")
			}
			else {
				$("#requisiteDiv").show();
				$("#reqstCheck").val("true")
			}
		}

		JourneyCheck = obj.journeyCheck;
		if (JourneyCheck == 'true') {
			$("#returnRemoveFrmListDiv" + i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' + i + '" name="returnRemoveFrmList' + i + '" value="1" onclick="setReturnValue(' + i + ')"/>');
			$("#returnReasonDiv" + i).hide();
		}
		else {
			
				$("#returnRemoveFrmListDiv" + i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' + i + '" name="returnRemoveFrmList' + i + '" value="1" onclick="setReturnValue(' + i + ')"/>');
				$("#returnReasonDiv" + i).hide();
			

		}


	}); //End of FamilyDetailSeq loop

	if (ramBaanCheck != "true") {

		var oldReq = document.getElementsByName("oldRequest")
		for (var i = 0; i < oldReq.length; i++) {
			if (oldReq[i].checked == true) {
				var auth = oldReq[i].value;
				var authArr = auth.split("~");
				authorityNo = authArr[1];
				authorityDate = authArr[0];
				ltcYear = authArr[3];
				checkRadio = true
				destStn = authArr[2];
				// alert("airTravelRequest.js:1695:setOrgDestStnByTrRule:i["+i+"]When any Tagged Request Sselected||authorityNo="+authorityNo+"||authority date="+authorityDate+"||ltcYear="+ltcYear+"||destStn="+destStn)
			}
		}

		orgOptions += '<select id="returnOrgStationDD" name="returnOrgStationDD" class="combo" onchange="setReturnOrgStnValue(this.value)"><option value="">Select</option>';

		$.each(msg.airStationListFrom, function(index, obj) {

			if (obj.jrnyType == 1) {
				var FromStation = obj.airFromStation;
				var FromStationType = obj.airFromStationType;
				var FromStationTypeInt = obj.airFromStationTypeInt;
				orgReturnTypeString.push({ id: FromStationTypeInt, name: FromStationType });


				//alert("setOrgDestStnByTrRule:FromStation-"+FromStation+"|FromStationType-"+FromStationType);	

				if (FromStationType == "New Duty Station" && newDutyNap != undefined)
					orgOptions += '<option value="' + newDutyNap + '">' + FromStationType + '<\/option>';
				else if (FromStationType == "New SPR" && newSprNap != undefined)
					orgOptions += '<option value="' + newSprNap + '">' + FromStationType + '<\/option>';
				else if (FromStationType == "Old SPR" && oldSprNap != undefined)
					orgOptions += '<option value="' + oldSprNap + '">' + FromStationType + '<\/option>';
				else if (FromStationType == "Old Duty Station" && oldDutyNap != undefined)
					orgOptions += '<option value="' + oldDutyNap + '">' + FromStationType + '<\/option>';
				else
					orgOptions += '<option value="' + FromStation + '">' + FromStationType + '<\/option>';

			}
		});
		//alert("From Station List for Value="+value+"--"+orgOptions)

		if (orgOptions == "<select>") {
			orgOptions = 'No From Station Exists In Profile';
		} else {
			orgOptions += '</select>'
		}
		$("#returnOrgStnDropDown").html(orgOptions);

		//alert("Org Option orgOptions.indexOf(AnyStation) cond="+orgOptions.indexOf('AnyStation') +" | travelTypeText.indexOf(LTC-AI) cond="+travelTypeText.indexOf('LTC-AI')+"|value="+value+"|taggCheck="+taggCheck);

		if (!(orgOptions.indexOf('AnyStation') > -1)) {
		  
				$("#returnOrgStnDropDown").show();
			
		} else {
			$("#returnOrgStnDropDown").show();
		}


		destOptions += '<select id="returnDestStationDD" name="returnDestStationDD" onchange="setReturnDestStnValue(this.value)" class="combo"><option value="">Select</option>';

		$.each(msg.airStationListTo, function(index, obj) {

			if (obj.jrnyType == 1) {
				var ToStation = obj.airToStation;
				var ToStationType = obj.airToStationType;
				var ToStationTypeInt = obj.airToStationTypeInt;
				//destTypeString+=ToStationTypeInt+":"+ToStationType+",";
				destReturnTypeString.push({ id: ToStationTypeInt, name: ToStationType });

				if (ToStationType == "New Duty Station" && newDutyNap != undefined)
					destOptions += '<option value="' + newDutyNap + '">' + ToStationType + '<\/option>';
				else if (ToStationType == "New SPR" && newSprNap != undefined)
					destOptions += '<option value="' + newSprNap + '">' + ToStationType + '<\/option>';
				else if (ToStationType == "Old SPR" && oldSprNap != undefined)
					destOptions += '<option value="' + oldSprNap + '">' + ToStationType + '<\/option>';
				else if (ToStationType == "Old Duty Station" && oldDutyNap != undefined)
					destOptions += '<option value="' + oldDutyNap + '">' + ToStationType + '<\/option>';
				else
					destOptions += '<option value="' + ToStation + '">' + ToStationType + '<\/option>';

			}
		});


		//alert("To Station List for Value="+value+"--"+destOptions)

		if (destOptions == "<select>") {
			destOptions = 'No TO Station Exists In Profile';
		} else {
			destOptions += "</select>"
		}

		$("#returnDestStnDropDown").html(destOptions);

		//alert("dest Option destOptions.indexOf(AnyStation) cond="+destOptions.indexOf('AnyStation') +" | travelTypeText.indexOf(LTC-AI) cond="+travelTypeText.indexOf('LTC-AI')+"|value="+value+"|taggCheck="+taggCheck);


		if (!(destOptions.indexOf('AnyStation') > -1)) {

			$("#returnDestStnDropDown").show();
		} else {
			$("#returnDestStnDropDown").show();
		}
	}	// End of if(ramBaanCheck!="true")		  
	 

}
  
  
  function hideRoundTrip()
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	
	var travelMode=$('#travelMode').val();
	var isBothModeRequestTaken=$('#isBothModeRequestTaken').val();
	var isRailRequestOnwardExist = $("#isRailRequestOnwardExist").val();		
	var isRailRequestReturnExist = $("#isRailRequestReturnExist").val();		
	var isAirRequestOnwardExist = $("#isAirRequestOnwardExist").val();		
	var isAirRequestReturnExist = $("#isAirRequestReturnExist").val();		
	var isAirOneWayExist=$('#isAirOneWayExist').val();
	
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
	}
	
}

function hideReturnRoundTrip()
{
		$('#returnAirJrnyTypeDiv0').show();
		if($('#returnAirJrnyTypeDiv1')!=null)$('#returnAirJrnyTypeDiv1').show();
		if($('#returnAirJrnyTypeDiv2')!=null)$('#returnAirJrnyTypeDiv2').show();

}

function initAirportList(){

	$('#origin').keyup(function() {
		var origin = $('#origin').val();
		var reqType = $('#reqType').val();

		if (origin.length > 1) {

			var StationList = "";

			$.ajax(
				{
					url: $("#context_path").val() + "mb/getAirportBulkBkgList",
					type: "get",
					data: "stationName=" + origin + "&reqType=" + reqType,
					dataType: "json",
					success: function(msg) {
						$.each(msg, function(index, name) {

							if (name == "Airport Not Exist")
								StationList += '<li>' + name + '</li>';
							else
								StationList += '<li onClick="fillOrgStn(\'' + name + '\')">' + name + '</li>';
						});

						$("#orgSuggestionsList").html(StationList);
						$('#orgSuggestion').show();
						beforeSend: $('#test').html("Getting response from server....");
					}
				});
		}
	});

	$('#destination').keyup(function() {
		var destination = $('#destination').val();
		var reqType = $('#reqType').val();

		if (destination.length > 1) {

			var StationList = "";

			$.ajax(
				{
					url: $("#context_path").val() + "mb/getAirportBulkBkgList",
					type: "get",
					data: "stationName=" + destination + "&reqType=" + reqType,
					dataType: "json",
					success: function(msg) {

						$.each(msg, function(index, name) {

							if (name == "Airport Not Exist")
								StationList += '<li>' + name + '</li>';
							else
								StationList += '<li onClick="fillDestStn(\'' + name + '\')">' + name + '</li>';
						});

						$("#destSuggestionsList").html(StationList);
						$('#destSuggestion').show();

						beforeSend: $('#test').html("Getting response from server....");

					}
				});
		}
	});
	
	$('#returnOrigin').keyup(function() {
		var origin = $('#returnOrigin').val();
		var reqType = $('#reqType').val();

		if (origin.length > 1) {

			var StationList = "";

			$.ajax(
				{
					url: $("#context_path").val() + "mb/getAirportBulkBkgList",
					type: "get",
					data: "stationName=" + origin + "&reqType=" + reqType,
					dataType: "json",
					success: function(msg) {
						$.each(msg, function(index, name) {

							if (name == "Airport Not Exist")
								StationList += '<li>' + name + '</li>';
							else
								StationList += '<li onClick="fillReturnOrgStn(\'' + name + '\')">' + name + '</li>';
						});

						$("#returnOrgSuggestionsList").html(StationList);
						$('#returnOrgSuggestion').show();

					}
				});
		}
	});

	$('#returnDestination').keyup(function() {

		var destination = $('#returnDestination').val();
		var reqType = $('#reqType').val();

		if (destination.length > 1) {

			var StationList = "";

			$.ajax(
				{
					url: $("#context_path").val() + "mb/getAirportBulkBkgList",
					type: "get",
					data: "stationName=" + destination + "&reqType=" + reqType,
					dataType: "json",
					success: function(msg) {
						$.each(msg, function(index, name) {
							if (name == "Airport Not Exist")
								StationList += '<li>' + name + '</li>';
							else
								StationList += '<li onClick="fillReturnDestStn(\'' + name + '\')">' + name + '</li>';
						});

						$("#returnDestSuggestionsList").html(StationList);
						$('#returnDestSuggestion').show();

					}
				});
		}
	});
	
	
	$('#destinationCity').keyup(function() {
		var city = $('#destinationCity').val();
		if (city.length > 2) {
			var response = "";
			var cityList = "";

			$.ajax({

				url: $("#context_path").val() + "mb/getCityBulkBkgList",
				type: "get",
				data: "city=" + city,
				dataType: "json",
				success: function(msg) {
					$.each(msg, function(index, obj) {

						var name = obj.cityName;
						var cityGradeStr = obj.cityGradeStr;
						var cityGradeInt = obj.cityGradeInt;

						if (name == "City Name Not Exist")
							cityList += '<li>' + name + '</li>';
						else
							cityList += '<li onClick="fillCity(\'' + name + '\',\'' + cityGradeInt + '\')">' + name + '</li>';
					});

					$("#autoSuggestionsListforDestCity").html(cityList);
					$('#suggestionsDestCity').show();
				}
			});
		}
	});
}


function airListPopup()
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var result = getTravellerCount();
	if(result!=false)
	{
		var adultCount = result[0];
		var childCount = result[1];
		var infantCount = result[2];
		var orgStr=$('#origin').val();
		var destStr=$('#destination').val();
		var jrType=0;		
		var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
		var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));		
		var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
		var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
		if(origin==destination){
			alert("Origin And Destination Airport Can Not Be Same.");
			return false;
		}
		var travelTypeId =$('#TravelTypeDD').val();
		var journeyDate=$('#onwordJourneyDate').val();
	 	var returnJourneyDate=$('#returnJourneyDate').val();
	 	var entitledClass=$('#airEntitledClass').val();
	 	var preferAirline='All';
	 	var tripWayValue=$('#tripWay').val();
	 	var jTypeObj=document.getElementById("airJourneyType");
	 	var serviceType=$('#serviceType').val();
	 	var TRRule=$("#TRRule").val();
	 	var personalNo=$("#personalNo").val();
	    var airlineType='';
	    var otherAirlineType='';
	 	var message = "";
	 	var tripNodeList = document.getElementsByName("airJourneyType");
	 	var airJourneyType = "";
	 	for(i = 0; i < tripNodeList.length; i++){
	 		var temp = tripNodeList.item(i);
	 		if(temp.checked){
	 			airJourneyType = temp.value;
	 		}
	 	}
	 	if(airJourneyType == 0){
	 		tripWayValue="OneWay";
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 		
	 	}
	 
		var xy = getScrollXY();
		var x = xy[0];
		var y = xy[1];

		var airViaCount = $('#airLTCViaRouteTable tr').length;
		var connectRoute = "";
		if (airViaCount > 0) {
			for (i = 0; i < airViaCount; i++) {
				var viaString = $("#ltcViaDestination" + i).val();
				var viaStationCode = viaString.substring(viaString.lastIndexOf("(") + 1, viaString.lastIndexOf(")"));
				if (viaStationCode == "") {
					alert("Please Enter Air Via Route for Onward Journey.");
					return false;
				}
				else {
					if (i == 0) {
						connectRoute = connectRoute + viaStationCode;
					} else {
						connectRoute = connectRoute + "~" + viaStationCode;
					}
				}
			}


		}
		
		$.ajax({

			url: $("#context_path").val() + "mb/airBulkBkgSrchRsltAjax",
			type: "get",
			data: "originCode=" + origin + "&destinationCode=" + destination + "&dtpickerDepart=" + journeyDate + "&dtpickerReturn=" + returnJourneyDate + "&classType=" + entitledClass + "&tripWay=" + tripWayValue + "&airlinePreference=" + preferAirline + "&noOfAdult=" + adultCount + "&noOfChild=" + childCount + "&noOfInfant=" + infantCount + "&travelTypeId=" + travelTypeId + "&journeyType=" + jrType + "&serviceType=" + serviceType + "&searchFrom=Create&trRule=" + TRRule + "&airlineType=" + airlineType + "&otherAirlineType=" + otherAirlineType + "&connectRoute=" + connectRoute + "&personalNo=" + personalNo + "&airViaCount=" + airViaCount,
			dataType: "text",

			beforeSend: function() {
				imageurltitle = '/pcda/images/fbloader.gif';
				box = new LightFace
					({
						title: "<img src='" + imageurltitle + "'> Loading ...",
						width: 500,
						height: 200,
						content: message,
						buttons: [
							{
								title: 'Close', event: function() {
									lightFaceObjectOld = null;
									this.destroy();
								}
							}
						]
					});
				lightFaceObjectOld = box;
				box.open();
			},
			complete: function() {
				if (box != null) {
					box.destroy();
				}
			},
			success: function(successMsg) {

				lightFaceObjectOld.destroy();
				box1 = new LightFace
					({
						title: 'Flight List',
						width: 1000,
						height: 500,
						content: successMsg,
						buttons: [
							{
								title: 'Close', event: function() {
									this.destroy();
									lightFaceObject = null;
									$('#parentBody').off('wheel.modal mousewheel.modal');
									$('#parentBody').css({ 'overflow-y': 'visible' });
									$(window).scrollTop(y);
								}
							}
						],
					});
				box1.open();
				lightFaceObject = box1;
				$(window).scrollTop(430);

				if (tripWayValue == 'OneWay') {
					showTabContent(jrType, orgStr, destStr, '');
				}
				else {
					showTabContent(jrType, orgStr, destStr, '');
				}
				$('#childBody').on('wheel.modal mousewheel.modal', function() { return false; });
				$('#parentBody').css({ 'overflow-y': 'hidden' });


			}

		});
	}

}



function airReturnListPopup()
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var result = getReturnTravellerCount();
	//alert("Inside airListPopup:result:"+result);
	if(result!=false)
	{
		var adultCount = result[0];
		var childCount = result[1];
		var infantCount = result[2];
		var orgStr=$('#returnOrigin').val();
		var destStr=$('#returnDestination').val();
		var jrType=1;
		
		var originName=orgStr.substring(0, orgStr.lastIndexOf("("));
		var origin=orgStr.substring(orgStr.lastIndexOf("(")+1, orgStr.lastIndexOf(")"));
		
		var destinationName =destStr.substring(0, destStr.lastIndexOf("("));
		var destination=destStr.substring(destStr.lastIndexOf("(")+1, destStr.lastIndexOf(")"));
		
		if(origin==destination){
			alert("Origin And Destination Airport Can Not Be Same.");
			return false;
		}	
		var travelTypeId =$('#TravelTypeDD').val();
		var journeyDate=$('#returnOnwordJourneyDate').val();
	 	var returnJourneyDate=$('#returnReturnJourneyDate').val();
	 	var entitledClass=$('#returnAirEntitledClass').val();
	 	//var preferAirline=$('#preferAirline').val();
	 	var preferAirline='All';
	 	var tripWayValue=$('#tripWay').val();
	 	
	 	var airlineType=$('#returnAirlineType').val();
	 	var otherAirlineType=$('#returnOtherAirlineType').val();
	 		 	
	 	var serviceType=$('#serviceType').val();
	 	var TRRule=$("#TRRule").val();
	 	var personalNo=$("#personalNo").val();
	 	
	 	var message = "";
	 	var tripNodeList = document.getElementsByName("returnAirJourneyType");
	 	var airJourneyType = "";
	 	for(i = 0; i < tripNodeList.length; i++){
	 		var temp = tripNodeList.item(i);
	 		if(temp.checked){
	 			airJourneyType = temp.value;
	 		}
	 	}
	 	
	 	if(airJourneyType == 0){
	 		tripWayValue="OneWay";
	 		message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
	 		
	 	}
	 
	 	var xy = getScrollXY();
		var x = xy[0];
		var y = xy[1];
		
		var airViaCount = $('#returnAirLTCViaRouteTable tr').length;
		var connectRoute="";
		if(airViaCount > 0){
			for(i = 0; i < airViaCount; i++){
				var viaString=$("#returnLtcViaDestination"+i).val();
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
		
		$.ajax({
			url: $("#context_path").val() + "mb/airBulkBkgSrchRsltAjax",
			type: "get",
			data: "originCode=" + origin + "&destinationCode=" + destination + "&dtpickerDepart=" + journeyDate + "&dtpickerReturn=" + returnJourneyDate + "&classType=" + entitledClass + "&tripWay=" + tripWayValue + "&airlinePreference=" + preferAirline + "&noOfAdult=" + adultCount + "&noOfChild=" + childCount + "&noOfInfant=" + infantCount + "&travelTypeId=" + travelTypeId + "&journeyType=" + jrType + "&serviceType=" + serviceType + "&searchFrom=Create&trRule=" + TRRule + "&airlineType=" + airlineType + "&otherAirlineType=" + otherAirlineType + "&connectRoute=" + connectRoute + "&personalNo=" + personalNo + "&airViaCount=" + airViaCount,
			dataType: "text",

			beforeSend: function() {
				imageurltitle = '/pcda/images/fbloader.gif';
				box = new LightFace
					({
						title: "<img src='" + imageurltitle + "'> Loading ...",
						width: 500,
						height: 200,
						content: message,
						buttons: [
							{
								title: 'Close', event: function() {
									lightFaceObjectOld = null;
									this.destroy();
								}
							}
						]
					});
				lightFaceObjectOld = box;
				box.open();
			},
			complete: function() {
				box.destroy();
			},
			success: function(successMsg) {

				lightFaceObjectOld.destroy();
				box1 = new LightFace
					({
						title: 'Flight List',
						width: 1000,
						height: 500,
						content: successMsg,
						buttons: [
							{
								title: 'Close', event: function() {
									this.destroy();
									lightFaceObject = null;
									$('#parentBody').off('wheel.modal mousewheel.modal');
									$('#parentBody').css({ 'overflow-y': 'visible' });
									$(window).scrollTop(y);
								}
							}
						],
					});
				box1.open();
				lightFaceObject = box1;
				$(window).scrollTop(430);

				if (tripWayValue == 'OneWay') {
					showTabContent(jrType, orgStr, destStr, '');

				}
				else {
					showTabContent(jrType, orgStr, destStr, '');
				}
				$('#childBody').on('wheel.modal mousewheel.modal', function() { return false; });
				$('#parentBody').css({ 'overflow-y': 'hidden' });
				$('#AITable').tablesorter();
				$('#OtherTable').tablesorter();

			}

		});
	}

}


function getTravellerCount() {
	var dobStr = document.getElementById("dob").value;
	var totalTravellerArr = dobStr.split(",");

	var authorityNo = document.getElementById("authorityNo").value;
	var authorityDate = document.getElementById("authorityDate").value;
	var airTypeObj = document.getElementById("airJourneyType");

	var onwordJourneyDate = document.getElementById("onwordJourneyDate").value;
	var orgStationDD = document.getElementById("orgStationDD").value;
	var destStationDD = document.getElementById("destStationDD").value;
	var origin = document.getElementById("origin").value;
	var destination = document.getElementById("destination").value;
	var airEntitledClass = document.getElementById("airEntitledClass").value;
	var returnJourneyDate = document.getElementById("returnJourneyDate").value;


	if (authorityNo == "") {
		alert("Please Enter Authority Number");
		document.getElementById("authorityNo").focus();
		return false;
	}
	if (authorityDate == "dd/mm/yyyy") {
		alert("Please Select Authority Date");
		document.getElementById("authorityDate").focus();
		return false;
	}
	if (onwordJourneyDate == "") {
		alert("Please Select Journey Date");
		document.getElementById("onwordJourneyDate").focus();
		return false;
	}
	if (airEntitledClass == "-1" || airEntitledClass == "") {
		alert("Please Select Entitled Class");
		document.getElementById("airEntitledClass").focus();
		return false;
	}


	var tripNodeList = document.getElementsByName("airJourneyType");
	for (i = 0; i < tripNodeList.length; i++) {
		var temp = tripNodeList.item(i);
		if (temp.checked) {
			if (Number(temp.value) > 0) {
				if (returnJourneyDate == "") {
					alert("Please Select Return Journey Date");
					document.getElementById("returnJourneyDate").focus();
					return false;
				}
			}
		}
	}



	if (origin == "") {
		alert("Please Enter Origin Station Airport");
		document.getElementById("origin").focus();
		return false;
	}
	if (destination == "") {
		alert("Please Enter Destination Station Airport");
		document.getElementById("destination").focus();
		return false;
	}




	var tempCount = 0;
	var dobArr = new Array();
	for (var i = 0; i < totalTravellerArr.length; i++) {
		if ($('#removeFrmList' + i).is(":checked")) {
			//alert( $('#dobCheck'+i).val());
			dobArr[tempCount] = $('#dobCheck' + i).val();
			tempCount++;
		}
	}

	if ($("#isPartyBooking").val() == 'Yes') {

		var partyCount = $("#partyMemberCount").val();
		for (i = 1; i <= partyCount; i++) {
			dobArr[tempCount] = $('#partyDepDOB' + i).val();
			tempCount++;
		}

	}


	if (tempCount > 0) {
		return calculateAge(dobArr, onwordJourneyDate);
	}
	else {
		alert("Please check atleast one travller");
		return false;
	}
}

function getReturnTravellerCount()
{
	var dobStr = document.getElementById("returnDOB").value;
    var totalTravellerArr = dobStr.split(",");

    var authorityNo=document.getElementById("authorityNo").value;
    var authorityDate=document.getElementById("authorityDate").value;    
    var airTypeObj=document.getElementById("returnAirJourneyType");
	
	var onwordJourneyDate=document.getElementById("returnOnwordJourneyDate").value;
	var	orgStationDD=document.getElementById("returnOrgStationDD").value;
	var	destStationDD=document.getElementById("returnDestStationDD").value;
	var origin=document.getElementById("returnOrigin").value;
	var destination=document.getElementById("returnDestination").value;
	var airEntitledClass=document.getElementById("returnAirEntitledClass").value;
	var returnJourneyDate=document.getElementById("returnReturnJourneyDate").value;
	
	
	if(authorityNo=="")
	{
		alert("Please Enter Authority Number");
		document.getElementById("authorityNo").focus();
		return false;
	}
	if(authorityDate=="dd/mm/yyyy" || authorityDate==""){
		alert("Please Select Authority Date");
		document.getElementById("authorityDate").focus();
		return false;
	}
	if(onwordJourneyDate==""){
		alert("Please Select Journey Date");
		document.getElementById("returnOnwordJourneyDate").focus();
		return false;
	}
	if(airEntitledClass=="-1" || airEntitledClass=="" ){
		alert("Please Select Entitled Class");
		document.getElementById("returnAirEntitledClass").focus();
		return false;
	}
	
	var tripNodeList = document.getElementsByName("returnAirJourneyType");
	for(i = 0; i < tripNodeList.length; i++)
	{
		var temp = tripNodeList.item(i);
		if(temp.checked){
			if(Number(temp.value) > 0){
				if(returnJourneyDate==""){
	 					alert("Please Select Return Journey Date");
						document.getElementById("returnReturnJourneyDate").focus();
						return false;
				}
	 		} 
		}
	 }
	
	
	if(origin==""){
			alert("Please Enter Origin Station Airport");
			document.getElementById("returnOrigin").focus();
			return false;
	}
	if(destination==""){
		alert("Please Enter Destination Station Airport");
		document.getElementById("returnDestination").focus();
		return false;
	}
	
	
	var tempCount = 0;
	var dobArr = new Array();
	for(var i=0; i<totalTravellerArr.length; i++)
	{
		if($('#returnRemoveFrmList'+i).is(":checked"))
		{
			dobArr[tempCount] = $('#returnDobCheck'+i).val();
			tempCount++;
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
		return calculateAge(dobArr,onwordJourneyDate);
	}
	else
	{
		alert("Please check atleast one travller");
		return false;
	}
}

function calculateAge(dobArr, dateOfJr) {
	var adult = 0;
	var child = 0;
	var infant = 0;
	var travellerTypeArr = new Array();
	dateOfJr = dateOfJr.split('/').reverse().join('-');

	if (dobArr.length > 0) {
		for (var i = 0; i < dobArr.length; i++) {
			var birthday = dobArr[i].split('-').reverse().join('-');
			var now = new Date();
			var past = new Date(birthday);
			var nowYear = now.getFullYear();
			var pastYear = past.getFullYear();
			var age = nowYear - pastYear;
			if (dateOfJr != '') {
				age = getAge(birthday, dateOfJr);
			}

			if (age >= 12) {
				adult++;
			}
			else if (age < 2) {
				infant++;
			}
			else {
				child++;
			}
		}
	}
	travellerTypeArr[0] = adult;
	travellerTypeArr[1] = child;
	travellerTypeArr[2] = infant;
	return travellerTypeArr;

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

function showTabContent(journeyType,origin,destination,jrMode) {

		 var airlineType="";
		
		 if($("#searchErrorMessage").val()== "LTCDirectFlightNotAvailable"){
		 	box1.destroy();
		 	if(confirm("USER AGREEMENT: Direct flights on proposed date of journey do not exist on this route. If you want to book connecting flights, " +
		 			"Please fill in the authorized connecting route.")){
		 	 if(journeyType == "0"){
		 	 	if(jrMode=='Mixed'){
		 		airMixedLTCViaRoutes(origin,destination);
		 		}else{
		 	    airLTCViaRoutes(origin,destination);
		 		}
		 	    return false;
			 }else if(journeyType == "1"){
			 	if(jrMode=='Mixed'){
		 		airReturnMixedLTCViaRoutes(origin,destination);
		 		}else{
			 	airReturnLTCViaRoutes(origin,destination);
		 		}
			 	return false;
			 }
		  }else{
			 	return false;
			 }
		 }
		 
		 if($("#searchErrorMessage").val()!= ""){
		 	alert($("#searchErrorMessage").val());
		 }
		
        }
        
function setFlightRequestData(flightKey, Carrier, journeyType,departureTime) {
		//alert("departureTime = "+departureTime);
			
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
		
		if(journeyType == '0'){
		document.getElementById("flightCode").value = Carrier;
		document.getElementById("flightKey").value = flightKey;
			document.getElementById("airDepartureTime").value = departureTime;
		}else if(journeyType == '1'){
		document.getElementById("returnFlightCode").value = Carrier;
		document.getElementById("returnFlightKey").value = flightKey;
			document.getElementById("returnAirDepartureTime").value = departureTime;
		}	
		
		

	if(lightFaceObject!=null)
	{
		
			lightFaceObject.destroy();
			lightFaceObject = null;
			$('#parentBody').css({'overflow-y':'visible'});
		
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
  

function getAge(birthDate,jrDate) {
  function isLeap(year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
  }
   jrDate=new Date(jrDate); 
    birthDate=new Date(birthDate); 
  var days = Math.floor((jrDate.getTime() - birthDate.getTime())/1000/60/60/24);
  var age = 0;
  for (var y = birthDate.getFullYear(); y <= jrDate.getFullYear(); y++){
    var daysInYear = isLeap(y) ? 366 : 365;
    if (days >= daysInYear){
      age++;
    }
	 days -= daysInYear;
  }
  return age;
}




function viewAirFareRule(flightKey,serviceProvider,domInt){



	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

var serpov = serviceProvider;

if(serpov=='ATT'){

	$.ajax({
		type: "POST",
		url: $("#context_path").val() + "mb/jrnyBulkBkgATTFareRule",
		data: "flightKey=" + flightKey,
		dataType: "text",

		beforeSend: function() {
			//		        	imageurltitle='/pcda/docroot/pcda/images/fbloader.gif';
			box = new LightFace
				({
					title: "Loading ...",
					width: 600,
					height: 400,
					content: "Fare Rule information is being populated... Please wait ...",
					buttons: [
						{
							title: 'Close', event: function() {
								this.destroy();
							}
						}
					]

				});
			box.open();
		},
		complete: function() {
			box.destroy();
		},
		success: function(successMsg) {
			box1 = new LightFace
				({
					title: 'View Fare Rule',
					width: 600,
					height: 400,
					content: successMsg,
					buttons: [
						{
							title: 'Close', event: function() {
								this.destroy();
								lightFaceObject = null;
							}
						}
					],
				});
			box1.open();
			lightFaceObject = box1;
		},
		error: function(jqXHR, textStatus) {
			alert("Request failed: " + textStatus);
		}


	});
	 

}
if(serpov=='BL'){

		$.ajax({
		type: "POST",
		url:$("#context_path").val()+"mb/jrnyBulkBkgBLFareRule",
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

