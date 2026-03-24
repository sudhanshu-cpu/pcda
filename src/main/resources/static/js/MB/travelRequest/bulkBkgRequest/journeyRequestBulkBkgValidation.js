

function validateSaveFields(theForm){
	
	var form = document.forms[theForm];
	var onwardsFlag=false;
	var returnFlag=false;
	
	 if (!$('#journeyType_0').is(":checked") && !$('#journeyType_1').is(":checked") ){
    	alert("Please Select Journey Type.");
		return false;
    }
	
	if ($('#journeyType_0').is(":checked")){
        onwardsFlag=onwardsFieldsValidation(form);
        if(!onwardsFlag){
        	return false;
        }
    }

	if ($('#journeyType_1').is(":checked")){
	    returnFlag=returnFieldsValidation(form);
	    if(!returnFlag){
        	return false;
        }
	}

	/*---------Manangement Question Check Block Start--------*/

   
        var ansArray = document.getElementsByName("answer");
        for (i = 0; i < ansArray.length; i++) 
        {
            var givenAnswer = "";
            var answerCheck = document.getElementsByName("answerCheck" + i);
            if (answerCheck[0].checked)
                givenAnswer = 0;
            if (answerCheck[1].checked)
                givenAnswer = 1;
            if (givenAnswer != "0" && givenAnswer != "1") {
                alert("Please give The Answers of all the given question");
                return false;
            }

            var storedAns = document.getElementsByName("answer")[i].value;
            if (!(givenAnswer == storedAns)) {
                alert("Your Answer doesn't match with the database so booking has been blocked");
                return false;
            }
        }
   
    /*---------Manangement Question Check Block End--------*/

   var calHeight=document.body.offsetHeight+20;
   $("#screen-freeze").css({"height":calHeight + "px"} );
			       
    if ($('#journeyType_0').is(":checked") || $('#journeyType_1').is(":checked")){
    	
    	$("#screen-freeze").css("display", "block");
    	
		var saveBtn = document.getElementById("saveBtnId");
		saveBtn.value = "Request is being created.Please wait...";
	    saveBtn.disabled = true;
		$(form).attr("action", $("#context_path").val() + "mb/saveJourneyBulkBkgRequest");
	    $(form).submit();
	}else{
		
		alert("Please Select Journey Type.");
		return false;
	}
	
}

function onwardsFieldsValidation(form){
	
	var requestType = $('#reqType').val();
	var journeyMode = $("input:radio[name=journeyMode]:checked").val();
	var travelMode = $('#travelMode').val();
	var serviceType = $('#serviceType').val();
	var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
   //var selectedOptionDAAdvanced = $("input:radio[name=DAAdvanced]:checked").val();
	
	//alert("validateSaveFields:travelMode-"+travelMode+"|serviceType-"+serviceType);
	 if (serviceType == 1 && requestType=="") 
	 {
	 	if(travelTypeText.indexOf('TD')>-1 || travelTypeText.indexOf('PT')>-1)
	 	 {
	 	  	if (confirm("Please ensure that Budget is available for this Booking.")){
	 	  		document.getElementById("civilBgtAvl").value = "YES";
	 	  	
			}else{
				window.location.reload();
				return false;
			}
	 	 }
    	
	 }	 		
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val();

    var check = true;

    var relationStr = document.getElementById("relation").value;
    var relArr = relationStr.split(",");
    var ischeckCount = 0;

    if (document.getElementById("getTravelerInfoBtn").value == "false") {
        alert("Please Click on 'Get Traveler Info' Button to get Traveler details");
        return false;
    } else if (document.getElementById("service").value == "") {
        alert("Please Click on 'Get Traveler Info' Button to get Traveler details");
        return false;
    }
    if (form.TRRule.value == "") {
        alert("Please Select Travel Rule");
        return false;
    }
    if (form.personalNo.value == "") {
        alert("Please Enter Traveler Personal No.");
        return false;
    }


    if ((travelTypeText.indexOf('PT') > -1 || travelTypeText.indexOf('LTC') > -1) && taggReqCheck == 'false') {
        alert("Please Click on Tagged Request Link First");
        return false;
    }

    if (travelTypeText.indexOf('LTC') > -1 && isTaggedORNew == 'false') {
        alert("Please Click on Yes Button To Create New Request or Select Existing One");
        return false;
    }

    if (travelTypeText.indexOf('PT') > -1 && taggCheck != 'true') {
        alert("Please Click on Yes Button To Create New Request or Select Existing One");
        return false;
    }
    if (travelTypeText.indexOf('LTC') > -1 && taggCheck == 'true') {
        var ltcyrCheck = false;
        var ltcYear = document.getElementsByName("ltcYear");
        for (var i = 0; i < ltcYear.length; i++) {
            if (ltcYear[i].checked == true)
                ltcyrCheck = true;
        }
        if (!ltcyrCheck) {
            alert("Please Select Year For Which You Are Applying LTC");
            return false;
        }
    }

   var serviceType = $('#serviceType').val();

	//-----------new budget request validation ---------------------START--------------------------------

	var checkBgtReq=$("#checkBgtReq").val();
	if(checkBgtReq=='true')
	{
		var budgetType=$("#budgetType").val();
		var budgetStatus=$("#budgetStatus").val();
		//alert("budgetStatus = "+budgetStatus);
	    if(budgetType=="" || budgetType=='undefined'){
			alert("Please select budget Type");
			return false;
		}
		if(budgetType==1)
		{
			var projectId=$("#project").val();
			if(projectId=="" || projectId=='undefined'){
				alert("Please select Project");
				return false;
			}
		}
		if(budgetStatus!='true'){
			alert("Booking Is Not Allowed");
			return false;
		}
	}
	//------------- Budget Request validation----------end----------------
    var category = document.getElementById("category").value.toUpperCase();
    var travelType = $('#TravelTypeDD :selected').text().toUpperCase();
    if (category.indexOf('OFFICER') > -1 && travelType == 'CV') {
        alert("Officers cannot book Ticket for Travel Type CV");
        return false;
    }
    
    if (check == true)
        check = validate_required(form.authorityNo, "Please Enter Authority No.");

	if (check == true)
        check = validate_required(form.authorityDate, "Please Enter Authority Date");

    if (check == true) {
        if (form.authorityDate.value == "dd/mm/yyyy") {
            alert("Please Enter Authority Date")
            form.authorityDate.focus();
            return false;
        }
    } else {
        return false;
    }

    if (check == true) {
        if (!validateAuthorityDate(document.getElementById("authorityDate"))) {
            alert("Authority Date must be Equal to OR less than Today Date");
            document.getElementById("authorityDate").focus();
            return false;
        }
    }

	if(travelMode==1)
	{
		//alert("Inside case of air travel");
		
		var airAccountOffice = $("#airAccountOffice").val();
		if(airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0){
		   alert("Pay account office for air travel is not assigned so air travel request can not be created.")
           check = false;
           return false;
        }
        
		check = validate_required(form.airEntitledClass, "Please Select Entitled Class");
		if (check == true) 
		{
	       
            if (form.onwordJourneyDate.value == "dd/mm/yyyy") {
                alert("Please Enter Journey Date")
                //form.onwordJourneyDate.focus();
                check = false;
            }
	      
	    } else {
	        return false;
	    }
		
	    check = validate_required(form.onwordJourneyDate, "Please Select Journey Date");
	    
	    if (requestType != 'offlinebooking') 
	    {
	        if (check == true) 
	        {
	            if (!isLessThanTodayDate(form.onwordJourneyDate)) {
	               // form.onwordJourneyDate.focus();
	                return false;
	            }
	
	            
	        }else{return false;}
	    }
	    
	    if (check == true) {
	        if (document.getElementById("origin").value == "") {
	            alert("Please Enter Origin Airport");
	            document.getElementById("origin").focus();
	            return false;
	        }
	        if (document.getElementById("destination").value == "") {
	            alert("Please Enter Destination Airport");
	            document.getElementById("destination").focus();
	            return false;
	        }
	    }else{
	    	return false;
	    }
	
	    if (check == true) {
	        if (document.getElementById("origin").value == document.getElementById("destination").value) {
	            alert("Origin And Destination Cannot Be Same");
	            return false;
	        }
	    }
	    	    
	}
    

    if (document.getElementById("remFrmListCheck").value == "true") {
        for (i = 0; i < relArr.length - 1; i++) {
            var isChecked = document.getElementById("removeFrmList" + i).checked
            if (isChecked)
                ischeckCount = ischeckCount + 1;
        }
    }


    //alert("reqstCheck-->"+reqstCheck)
    if (check == true && reqstCheck == 'true') {
        var isReqsApprv = document.getElementsByName("reqsAproved");
        for (i = 0; i < isReqsApprv.length; i++) {
            if (isReqsApprv[i].checked == false) {
                alert("Please Approve The Requisite First")
                return false;
            }
        }
    }
    if (check == true) {
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276') {
            alert("One passenger must be selected");
            return false;
        }
    }
	
	if(travelMode==1)
	{
	 	if (check == true) {
			if(getRequestTravellerCount()==false)
				return false;
	 	}
	 	
	 	if (check == true && requestType != 'offlinebooking' ) 
		{
			var selectedFlightKey = $("#flightKey").val();
			var selectedFlightCarrier = $("#flightCode").val();
			
			if(selectedFlightKey == '' || selectedFlightCarrier == '')
			{
				alert("Please Search And Select flight for Onward Journey.");
			  	return false;
			}
			
		}
	}
	
    var totalPassInReq = 0;
    /* Start Validation For Party Booking */
    if (check == true) 
    {
        if ($("#isPartyBooking").val() == 'Yes') 
        {
            var partyCount = $("#partyMemberCount").val();
            if (partyCount == '0') {
                alert("Please Add Traveller, Atleast One Traveller Is Mandatory Or Select Another TD Group");
                return false;
            }

            for (i = 1; i <= partyCount; i++) 
            {

                if (document.getElementById("partyDepName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Traveller");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }

                if (document.getElementById("partyDepErsName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Traveller");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }                

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);

        }

    }  //End of Party Booking check.


    /* End Validation For Party Booking */

    /*-- --Code To Disable Save Buttion Start-- --*/

    var journeyDate=""; 
    var journeyMode = $("input:radio[name=journeyMode]:checked").val();
    if('0' == journeyMode)
         journeyDate =  $("#journeyDate").val();
    else if('1'==journeyMode)
       journeyDate=$("#onwordJourneyDate").val();
  
       if (journeyDate!="" && travelTypeText.indexOf('LTC') > -1  ) {

		   if(days_between(getTravelDateForSelected('0'),journeyDate) > 0){ 
		     alert("Onward Journey Date must Be earlier than Return Journey Date");
		     return false;
		    }
	    }

	

    /*-- --Code To Disable Save Buttion End-- --*/

	
    
    return check;
	
}


function returnFieldsValidation(form){
	
	

    //alert("-----------------Inside validateSaveFields----------------");

   
    var requetType = $('#reqType').val();
    var journeyMode = $("input:radio[name=returnJourneyMode]:checked").val();
   
    if('exceptionalBooking' == requetType &&  journeyMode != '0' ){
    alert("Exceptional  booking for air and mixed mode is not allowed.");
    return false;
    }
    
    var requestType = form.reqType.value;
	var travelMode = $('#returnTravelMode').val();
	var serviceType = $('#serviceType').val();
	var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
 		
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val();
    
    var check = true;

    var relationStr = document.getElementById("returnRelation").value
    var relArr = relationStr.split(",");
    var ischeckCount = 0;

    if (document.getElementById("getTravelerInfoBtn").value == "false") {
        alert("Please Click on 'Get Traveler Info' Button to get Traveler details")
        return false;
    } else if (document.getElementById("service").value == "") {
        alert("Please Click on 'Get Traveler Info' Button to get Traveler details")
        return false;
    }
    if (form.TRRule.value == "") {
        alert("Please Select Travel Rule");
        return false;
    }
    if (form.personalNo.value == "") {
        alert("Please Enter Traveler Personal No.");
        return false;
    } 


    var serviceType = $('#serviceType').val();
    var category = document.getElementById("category").value.toUpperCase();
    var travelType = $('#TravelTypeDD :selected').text().toUpperCase();
    if (category.indexOf('OFFICER') > -1 && travelType == 'CV') {
        alert("Officers cannot book Ticket for Travel Type CV");
        return false;
    }
  
	
    if (check == true)
        check = validate_required(form.authorityNo, "Please Enter Authority No.");

	if (check == true)
        check = validate_required(form.authorityDate, "Please Enter Authority Date");

    if (check == true) {
        if (form.authorityDate.value == "dd/mm/yyyy") {
            alert("Please Enter Authority Date")
            form.authorityDate.focus();
            return false;
        }
    } else {
        return false;
    }

    if (check == true) {
        if (!validateAuthorityDate(document.getElementById("authorityDate"))) {
            alert("Authority Date must be Equal to OR less than Today Date");
            document.getElementById("authorityDate").focus();
            return false;
        }
    }

	if(travelMode==1)
	{
		//alert("Inside case of air travel");
		
		var airAccountOffice = $("#airAccountOffice").val();
		if(airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0){
		   alert("Pay account office for air travel is not assigned so air travel request can not be created.");
           check = false;
           return false;
        }
		
		var airlineType=$("#returnAirlineType").val();
        
        if(airlineType == ""){
        	alert("Please Select Airline Type");
            check = false;
            return false;
        }
        
        if(airlineType == "Others"){
        	var otherAirlineType=$("#returnOtherAirlineType").val();
        	if(otherAirlineType == ""){
        		alert("Please Select Others Reason");
	            check = false;
	            return false;
        	}
        	if(otherAirlineType == "FAA"){
        		if($("#returnFAA_AuthorityNo").val().trim() == ""){
        			alert("Financial Authority No. Can not be blank");
		            check = false;
		            return false;
        		}
        		
        		if($("#returnFAA_AuthorityDate").val().trim() == ""){
        			alert("Financial Authority Date Can not be blank");
		            check = false;
		            return false;
        		}
        	}
        }
        
		check = validate_required(form.returnAirEntitledClass, "Please Select Entitled Class");
		if (check == true) 
		{
	       
            if (form.returnOnwordJourneyDate.value == "dd/mm/yyyy") {
                alert("Please Enter Journey Date");
                check = false;
            }
	      
	    } else {
	        return false;
	    }
		
	    check = validate_required(form.returnOnwordJourneyDate, "Please Select Journey Date");
	    
	    if (requestType != 'offlinebooking') 
	    {
	        if (check == true) 
	        {
	            if (!isLessThanTodayDate(form.returnOnwordJourneyDate)) {
	                return false;
	            }	        
	        }else{return false;}
	    }else 	    	        	    
	    if (check == true) {
	        if (document.getElementById("returnOrigin").value == "") {
	            alert("Please Enter Return Origin Airport");
	            document.getElementById("returnOrigin").focus();
	            return false;
	        }
	        if (document.getElementById("returnDestination").value == "") {
	            alert("Please Enter Return Destination Airport");
	            document.getElementById("returnDestination").focus();
	            return false;
	        }
	    }else{
	    	return false;
	    }
	
	    if (check == true) {
	        if (document.getElementById("returnOrigin").value == document.getElementById("returnDestination").value) {
	            alert("Return Origin And Destination Cannot Be Same");
	            return false;
	        }
	    }
    
	}

    if (document.getElementById("remFrmListCheck").value == "true") {
        for (i = 0; i < relArr.length - 1; i++) {
            var isChecked = document.getElementById("returnRemoveFrmList" + i).checked
            if (isChecked)
                ischeckCount = ischeckCount + 1;
        }
    }


    //alert("reqstCheck-->"+reqstCheck)
    if (check == true && reqstCheck == 'true') {
        var isReqsApprv = document.getElementsByName("returnReqsAproved");
        for (i = 0; i < isReqsApprv.length; i++) {
            if (isReqsApprv[i].checked == false) {
                alert("Please Approve The Requisite First");
                return false;
            }
        }
    }
    if (check == true) {
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276') {
            alert("One passenger must be selected");
            return false;
        }
    }
	
	if(travelMode==1)
	{
	 	if (check == true) {
			if(getReturnRequestTravellerCount()==false)
				return false;
	 	}
	 	
	 	if (check == true && requestType != 'offlinebooking' ) 
		{
			var selectedFlightKey = $("#returnFlightKey").val();
			var selectedFlightCarrier = $("#returnFlightCode").val();
			
			if(selectedFlightKey == '' || selectedFlightCarrier == '')
			{
				alert("Please Search And Select flight for Return Journey.");
			  	return false;
			}
			
		}
	}
	
    var totalPassInReq = 0;
    /* Start Validation For Party Booking */
    if (check == true) 
    {

        if ($("#isPartyBooking").val() == 'Yes') 
        {

            var partyCount = $("#returnPartyMemberCount").val();
            if (partyCount == '0') {
                alert("Please Add Party Dependent, Atleast One Party Member Is Mandatory Or Select Another TD Group");
                return false;
            }

            for (i = 1; i <= partyCount; i++) 
            {

                if (document.getElementById("returnPartyDepName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("returnDependentPersonalNo" + i).focus();
                    return false;
                }

                if (document.getElementById("returnPartyDepErsName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("returnDependentPersonalNo" + i).focus();
                    return false;
                }
           

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);
        }

    }  //End of Party Booking check.
    /* End Validation For Party Booking */

   
    return check;
	
}

function getRequestTravellerCount()
{
	var dobStr = document.getElementById("dob").value;
    var totalTravellerArr = dobStr.split(",");
	
	var tempCount = 0;
	var dobArr = new Array();
	for(var i=0; i<totalTravellerArr.length; i++)
	{
		if($('#removeFrmList'+i).is(":checked"))
		{
			dobArr[tempCount] = $('#dobCheck'+i).val();
			tempCount++;
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
		var travellerTypeArr=calculateAge(dobArr,'');
		var adult=travellerTypeArr[0];
		var child=travellerTypeArr[1]  ;
		var infant=travellerTypeArr[2]  ;
		//alert("adult-"+adult+"|child-"+child+"|infant-"+infant);
		if(adult==0){
			alert("Atleast One Adult Traveler Should Be In The Request.");
			return false;
		}
	}
	else
	{
		alert("Please check atleast one traveler");
		return false;
	}
}


function getReturnRequestTravellerCount()
{
	var dobStr = document.getElementById("returnDOB").value;
    var totalTravellerArr = dobStr.split(",");
	
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
		var travellerTypeArr=calculateAge(dobArr,'');
		var adult=travellerTypeArr[0];
		var child=travellerTypeArr[1]  ;
		var infant=travellerTypeArr[2]  ;
		//alert("adult-"+adult+"|child-"+child+"|infant-"+infant);
		if(adult==0){
			alert("Atleast One Adult Traveler Should Be In The Request.");
			return false;
		}
	}
	else
	{
		alert("Please check atleast one traveler");
		return false;
	}
}

function getTravelDateForSelected(journeyType){

    var ltcYear=document.getElementById("ltcYear").value;
    var dobStr = "";
    if(journeyType==0){
    	dobStr = document.getElementById("dob").value;
    }else if(journeyType==1){
    	dobStr = document.getElementById("returnDOB").value;
    }
    
    var totalTravellerArr = dobStr.split(",");
	var personalNo=$('#personalNo').val();
	var travelType=$('#TravelTypeDD').val();
	var tempCount = 0;
	var dobArr = new Array();
	var nameArr=new Array();
	var familyName=new Array();
	var relationCodeArr=new Array();
   
	for(var i=0; i<totalTravellerArr.length; i++)
	{
	  if(journeyType==0){
		if($('#removeFrmList'+i).is(":checked"))
		{
			dobArr[tempCount] = $('#dobCheck'+i).val();
			nameArr[tempCount]="'"+$('#nameCheck'+i).val().replace(/\s/g,'')+"'";
			familyName[tempCount]="'"+$('#familyName'+i).val().replace(/\s/g,'')+"'";
			relationCodeArr[tempCount]=$('#relationCode'+i).val();
			tempCount++;
			
		}
	  }else if(journeyType==1){
    	if($('#returnRemoveFrmList'+i).is(":checked"))
		{
			dobArr[tempCount] = $('#returnDobCheck'+i).val();
			nameArr[tempCount]="'"+$('#returnNameCheck'+i).val().replace(/\s/g,'')+"'";
			familyName[tempCount]="'"+$('#returnFamilyName'+i).val().replace(/\s/g,'')+"'";
			relationCodeArr[tempCount]=$('#returnRelationCode'+i).val();
			tempCount++;
			
		}
    }
	}
  var journayDate ="";
	$.ajax({
		      url: $("#context_path").val()+"mb/getTravelerBulkBkgJournayDate",
		      type: "get",
		      data: "personalNo="+personalNo+"&travelType="+travelType+"&familyName="+familyName+"&nameArr="+nameArr+"&relationCodeArr="+relationCodeArr+"+&journeyType="+journeyType+"&ltcYear="+ltcYear ,
		      dataType: "json",
		      async:false,
		      success: function(msg)
		      {
		      
		     journayDate= msg.trim()
		      	
		  	  }
		 });
return journayDate;   
}

function getReturnTravelDateForSelected(){

    var ltcYear=document.getElementById("ltcYear").value;
    var dobStr = document.getElementById("returnDOB").value;
    var totalTravellerArr = dobStr.split(",");
	var personalNo=$('#personalNo').val();
	var travelType=$('#TravelTypeDD').val();
	var tempCount = 0;
	var dobArr = new Array();
	var nameArr=new Array();
	var familyName=new Array();
	var relationCodeArr=new Array();
    var journeyType = 1; 
	for(var i=0; i<totalTravellerArr.length; i++)
	{
		if($('#returnRemoveFrmList'+i).is(":checked"))
		{
			dobArr[tempCount] = $('#returnDobCheck'+i).val();
			nameArr[tempCount]="'"+$('#returnNameCheck'+i).val().replace(/\s/g,'')+"'";
			familyName[tempCount]="'"+$('#returnFamilyName'+i).val().replace(/\s/g,'')+"'";
			relationCodeArr[tempCount]=$('#returnRelationCode'+i).val();
			tempCount++;
			
		}
	}
  var journayDate ="";
	$.ajax({
		      url: $("#context_path").val()+"mb/getTravelerBulkBkgJournayDate",
		      type: "get",
		      data: "personalNo="+personalNo+"&travelType="+travelType+"&familyName="+familyName+"&nameArr="+nameArr+"&relationCodeArr="+relationCodeArr+"+&journeyType="+journeyType+"&ltcYear="+ltcYear ,
		      dataType: "text",
		      async:false,
		      success: function(msg)
		      {
		      
		     journayDate= msg.trim()
		      	
		  	  }
		 });
return journayDate;   
}

function validateAuthorityDate(ctl) {

    var cal = new Array();
    cal.JAN = "January";
    cal.FEB = "February";
    cal.MAR = "March";
    cal.APR = "April";
    cal.MAY = "May";
    cal.JUN = "June";
    cal.JUL = "July";
    cal.AUG = "August";
    cal.SEP = "September";
    cal.OCT = "October";
    cal.NOV = "November";
    cal.DEC = "December";

    var sysDate = new Date();
    var effDateChar = ctl.value; // P_EFFECTIVE_DATE
   
    var bufArray = effDateChar.split("/");
    var effDateMonth = bufArray[1];
    var effDateDay = bufArray[0];
    var effDateYear = bufArray[2];

    var myDate = new Date();
    myDate.setFullYear(effDateYear, effDateMonth - 1, effDateDay);
    myDate.setHours(0, 0, 0, 0);
      
    if (myDate > sysDate) {
        ctl.focus();
        return false;
    }
    return true;
   
}

function getDaysBefore(sdate)
{
	var currentDatee=new Date()
	var month = currentDatee.getMonth() + 1
	var day = currentDatee.getDate()
	var year = currentDatee.getFullYear()
	var currentDate=day + "/" + month + "/" + year;	
	
	var sysDate=getDtObject(currentDate,"/");
	var jrnyDate=getDtObject(sdate,"/");
	
	var no=daysElapsed(jrnyDate, sysDate);

	if(no > 125)
	{
			alert("Please Check The Journey Date. Request is permitted for 125 days in advance with respect " +
    		"to train starting station(journey date not to be counted)");
			return false;
	}
	
	return true;
}  

function getDtObject(dateStr,del){
	var splitedDate=dateStr.split(del);
	var dtDay=splitedDate[0];
	var dtMnth=splitedDate[1];
	var dtYr=splitedDate[2];
	var dt=new Date();
	dt.setFullYear(dtYr,dtMnth-1,dtDay);
	return dt;
}

function daysElapsed(date1,date2){
	var d1=Date.UTC(y2k(date1.getYear()),date1.getMonth(),date1.getDate(),0,0,0);
	var d2=Date.UTC(y2k(date2.getYear()),date2.getMonth(),date2.getDate(),0,0,0);
    var difference =Number(d1)-Number(d2);
    difference=difference/1000/60/60/24;
    return difference;
}

function y2k(number){ 
	return (number<1000)?number+1900:number; 
}

function days_between(date1, date2) {

    var x = date1.split("/");
    var y = date2.split("/");
    var one_day = 1000 * 60 * 60 * 24;

    var date1 = new Date(x[2], (x[1] - 1), (x[0]));
    var date2 = new Date(y[2], (y[1] - 1), (y[0]))
    var month1 = x[1] - 1;
    var month2 = y[1] - 1;

    var Diff = Math.ceil((date2.getTime() - date1.getTime()) / (one_day));

    return (Diff);

}


function isLessThanTodayDate(ctl)
 {

   var cal = new Array();
   cal.JAN = "January";
   cal.FEB = "February";
   cal.MAR = "March";
   cal.APR = "April";
   cal.MAY = "May";
   cal.JUN = "June";
   cal.JUL = "July";
   cal.AUG = "August";
   cal.SEP = "September";
   cal.OCT = "October";
   cal.NOV = "November";
   cal.DEC = "December";

   var sysDate = new Date();
   var effDateChar = ctl.value; // P_EFFECTIVE_DATE
   var bufArray = effDateChar.split("/");
   var effDateMonth = bufArray[1];
   var effDateDay = bufArray[0];
   var effDateYear = bufArray[2];
   var effDateDate = new Date(effDateMonth+" "+ effDateDay +
                       ", "+effDateYear+" 23:59:59");
   
   var myDate=new Date();
   myDate.setFullYear(effDateYear,effDateMonth-1,effDateDay);	          
   if (myDate < sysDate)
   {
   		alert("The Journey date must be today's date or a later date.");
      	ctl.focus();
	    return false;
	}
	return true;	
}

function isGreaterThanTodayDate(ctl) 
{

   var cal = new Array();
   cal.JAN = "January";
   cal.FEB = "February";
   cal.MAR = "March";
   cal.APR = "April";
   cal.MAY = "May";
   cal.JUN = "June";
   cal.JUL = "July";
   cal.AUG = "August";
   cal.SEP = "September";
   cal.OCT = "October";
   cal.NOV = "November";
   cal.DEC = "December";

   var sysDate = new Date();
   var effDateChar = ctl.value; // P_EFFECTIVE_DATE
   var bufArray = effDateChar.split("/");
   var effDateMonth = bufArray[1];
   var effDateDay = bufArray[0];
   var effDateYear = bufArray[2];
   
   var myDate=new Date();
   myDate.setFullYear(effDateYear,effDateMonth-1,effDateDay);
   
   if (myDate > sysDate)
   {
      ctl.focus();
      return false;
   }
   return true;
}  



function StartEndDate(SDate, EDate) {

    var SdateArr = SDate.split("/");
    var EdateArr = EDate.split("/");
    var startDate = new Date(SdateArr[2], (SdateArr[1] - 1), SdateArr[0]);
    var endDate = new Date(EdateArr[2], (EdateArr[1] - 1), EdateArr[0]);

    if (SDate != '' && EDate != '' && startDate > endDate) {
        return false;
    }
    return true;
}
