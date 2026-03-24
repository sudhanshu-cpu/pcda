function validateCompositeTransferAmt() 
{
	if(!validateTransferType()) return false;
    
    if(!validateBasicPayAmt())return false;
    
    if(!validateTotalCTGAmt())return false;
    
    if(!validateDistanceInKms())return false;
    
    if(!validateApproxLuggWeight())return false;
    
    if(!validateLuggageAmt())return false;
    
    if(!validateConveyanceAmt())return false;

    return true;

}

function totalTDAdvanceAmount(){
	
	var totalAmt=Number($("#totalCtg").val())+Number($("#luggageAmt").val())+Number($("#conveyance").val());
	$("#ptAdvanceAmountClaimed").val(parseInt(totalAmt));
}

function validateConveyanceAmt(){
	
	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
	var conveyance = $("#conveyance").val();
	var vehicletransAmt=0;
	var personalNo=$('#personalNo').val();
	var distanceInKms = $("#distanceInKms").val();
	 
	if(conveyance==""){
   	    alert("Please Enter Amount for vehicle Transfer.");
    	$("#conveyance").focus();
   		return false;
     }
    
     if(conveyance!=""&&(!regexn.test(conveyance)))
      {
    	alert("Amount for vehicle Transfer should be Numeric Characters.");
    	$("#conveyance").val("");
    	$("#conveyance").focus();
    	return false;
      }
      
      $.ajax({
	    url: $("#context_path").val()+"mb/compositeTransferAmount",
        type: "get",
        async: false,
        data: "personalNo=" + personalNo ,
        dataType: "json",
        success: function(msg) 
        {
	        kmRate = msg.kmRate;
	        weight = msg.weight;
	        vehicletransAmt =msg.vehicleAmt;
           
        }
    });
    
     var vehicleAmt=Number(vehicletransAmt)*Number(distanceInKms);
    
    if(parseInt(conveyance) > vehicleAmt){
    	alert("Amount for vehicle Transfer should not be more then "+vehicleAmt);
    	$("#conveyance").val("");
    	$("#conveyance").focus();
   		return false;
     }
     return true;
}

function validateLuggageAmt(){

	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
    var luggageAmt = $("#luggageAmt").val();
	var kmRate=0;
    var distanceInKms = $("#distanceInKms").val();
    var approxLuggWeight = $("#approxLuggWeight").val();
	var weight=0;
	var personalNo=$('#personalNo').val();
   
  if(luggageAmt==""){
     	alert("Please Enter Amount for Luggage Transport.");
    	$("#luggageAmt").focus();
   		return false;
   }

    if(luggageAmt!=""&&(!regexn.test(luggageAmt))){
    	alert("Amount for Luggage Transport should be Numeric Characters.");
    	$("#luggageAmt").val("");
    	$("#luggageAmt").focus();
    	return false;
    }
    
    $.ajax({
	    url: $("#context_path").val()+"mb/compositeTransferAmount",
        type: "get",
        async: false,
        data: "personalNo=" + personalNo ,
        dataType: "json",
        success: function(msg) 
        {
	        kmRate = msg.kmRate;
	        weight = msg.weight;
	        vehicletransAmt =msg.vehicleAmt;
	      
   }
    });
    
     var maxLuggWeightAmt=parseInt((Number(kmRate)*Number(distanceInKms))*(Number(approxLuggWeight)/Number(weight)));
    
    if(parseInt(luggageAmt) > maxLuggWeightAmt){
    	alert("Amount for Luggage should not be more then "+maxLuggWeightAmt);
    	$("#luggageAmt").val("");
    	$("#luggageAmt").focus();
   		return false;
    }
    
    return true;

   }
    
function validateApproxLuggWeight(){
	
	var approxLuggWeight = $("#approxLuggWeight").val();
	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
	var weight=0;
	var personalNo=$('#personalNo').val();
    
	 if(approxLuggWeight==""){
   		alert("Please Enter Luggage Weight.");
    	$("#approxLuggWeight").focus();
   		return false;
     }
    
    if(approxLuggWeight!=""&&(!regexn.test(approxLuggWeight))){
    	alert("Luggage Weight should be Numeric Characters.");
    	$("#approxLuggWeight").val("");
    	$("#approxLuggWeight").focus();
    	return false;
    }

    $.ajax({
	    url: $("#context_path").val()+"mb/compositeTransferAmount",
        type: "get",
        async: false,
        data: "personalNo=" + personalNo ,
        dataType: "json",
        success: function(msg) 
        {
	
	        kmRate = msg.kmRate;
	        weight = msg.weight;
	        vehicletransAmt =msg.vehicleAmt;
	       
        }
    });

     if(parseInt(approxLuggWeight) > weight){
    	alert("Luggage Weight should not be more then "+weight+" Kg" );
    	$("#approxLuggWeight").val("");
    	$("#approxLuggWeight").focus();
   		return false;
     }
     
     return true;
}

function validateDistanceInKms(){
	
	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
	var distanceInKms = $("#distanceInKms").val();
	var maxDistance=3500;
	
     if(distanceInKms==""){
   		alert("Please Enter Distance Of Travel.");
    	$("#distanceInKms").focus();
   		return false;
     }

    if(distanceInKms!=""&&(!regexn.test(distanceInKms))){
    	alert("Distance Of Travel should be Numeric Characters.");
    	$("#distanceInKms").val("");
    	$("#distanceInKms").focus();
    	return false;
    }
    
     if(parseInt(distanceInKms) > maxDistance){
    	alert("Distance Of Travel should not be more then "+maxDistance+" Km" );
    	$("#distanceInKms").val("");
    	$("#distanceInKms").focus();
   		return false;
    }
    
    return true;
}

function validateTotalCTGAmt(){
    
	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
	var totalCtg = $("#totalCtg").val();
	var basicPay = $("#basicPay").val();
	
	if(!validateTransferType()){ 
		$("#totalCtg").val("");
		return false;
     }

	if(totalCtg==""){
   		alert("Please Enter CTG Advance amount.");
    	$("#totalCtg").focus();
    	return false;
    }
    
    if(totalCtg!=""&&(!regexn.test(totalCtg))){
    	alert("CTG Advance should be Numeric Characters.");
    	$("#totalCtg").val("");
    	$("#totalCtg").focus();
    	return false;
     }
    
    var maxctg=0;
    var trnsType=$('input[type="radio"][name="ptTransferType"]:checked').val();
    
    if(trnsType=="1"){
    	maxctg=parseInt(parseInt(basicPay)*0.8);
    }else if(trnsType=="2"){
    	maxctg=parseInt(basicPay);
    }else if(trnsType=="3"){
    	maxctg=parseInt((parseInt(basicPay)*0.8)/3);
    }else{}
    
    if(parseInt(totalCtg) > maxctg){
    	alert("CTG Advance should not be more then "+maxctg );
    	$("#totalCtg").val("");
    	$("#totalCtg").focus();
   		return false;
     }
     
     return true;
    
    }
    
function validateBasicPayAmt(){
	
	var basicPay = $("#basicPay").val();
	var maxBasicPay=250000;
	var regexn = /^\s*-?[0-9]\d*(\.\d{1,2})?\s*$/;
    
	if(!validateTransferType()){
		$("#basicPay").val("");
		return false;
     }
    
    if(basicPay==""){
   		alert("Please Enter Your Basic Pay.");
    	$("#basicPay").focus();
   		return false;
     }
    
    if(basicPay!=""&&(!regexn.test(basicPay))){
    	alert("Basic Pay should be Numeric Characters.");
    	$("#basicPay").val("");
    	$("#basicPay").focus();
    	return false;
      }
    
    if(parseInt(basicPay) > maxBasicPay){
    	alert("Your Basic Pay should not be more then "+maxBasicPay );
    	$("#basicPay").val("");
    	$("#basicPay").focus();
   		return false;
     }
    return true;
}

function validateTransferType(){
	if($("input[type='radio'][name='ptTransferType']:checked").length ==0){
    	alert("Please select any one of the transfer scenario given above.");
    	return false;
}
   return true; 
}

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
	
	/* VALIDATE TEZPUR AIRPORT */
	
	var oldorigin= $('#oldAirport').val();

	var returnOldOrigin= $('#returnOldAirport').val();

	
		if(oldorigin=='TEZ'){
			
			if(!confirm("Your Request and booking will be done from Itanagar Airport(Assam) instead of Tezpur Airport(Assam) being non-operational !!")){
			return false;
		}
		}
		
			if(returnOldOrigin=='TEZ'){
			
			if(!confirm("Your Request and booking will be done from Itanagar Airport(Assam) instead of Tezpur Airport(Assam) being non-operational !!")){
			return false;
		}
		}
	
	
	/* VALIDATE TEZPUR AIRPORT */
	
	/*---------Form D And Form G calendar year Block Start--------*/
	
	var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
	
	if(travelTypeText.indexOf('FORMD')>-1 || travelTypeText.indexOf('FORMG')>-1){
		
   	 var formDYear=document.getElementById("formDAndGYear").value;
   	 var journeyDates = document.querySelectorAll('[id^="journeyDate"]');
   	
	 for (i = 0; i < journeyDates.length; i++) 
	 { 
	 	var date = journeyDates[i].id;
   	 	var journeyDate=document.getElementById(date).value;
   	 
	   	 if(journeyDate !='' && journeyDate !="")
	   	  {
		   	  var formDDate = journeyDate.split("/");
		      var fDYear =formDDate[2];
		      if(formDYear!=fDYear)
		      {
		      	 alert("Please select journey date according to Form-D/Form-G calendar year. The journey date must be in the same year as that of calendar year");
		      	 return false;
		      }
	   	  }
	 }
   	 
   }
	
	/*---------Form D And Form G calendar year Block End--------*/
	
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
    
    var requestType = $('#reqType').val();
    if (requestType == 'offlinebooking') {
    	
    	if ($('#journeyType_0').is(":checked") && $('#journeyType_1').is(":checked") ){
    		 var journeyMode = $("input:radio[name=journeyMode]:checked").val();
   			 var returnJourneyMode = $("input:radio[name=returnJourneyMode]:checked").val();
    		if (journeyMode != 1 && returnJourneyMode != 1) {
    	    	var flag=validateWarrantInfo(form);
    	    	if(!flag){
    	    		return false;
    	    	}
		    }
    	}else if($('#journeyType_0').is(":checked") ){
    		 var journeyMode = $("input:radio[name=journeyMode]:checked").val();
    		if (journeyMode != 1) {
    	    	var flag=validateWarrantInfo(form);
    	    	if(!flag){
    	    		return false;
    	    	}
		    }
    	}else if($('#journeyType_1').is(":checked") ){
    		 var returnJourneyMode = $("input:radio[name=returnJourneyMode]:checked").val();
    		if (returnJourneyMode != 1) {
    	    	var flag=validateWarrantInfo(form);
    	    	if(!flag){
    	    		return false;
    	    	}
		    }
    	}
    	
    }
   
    
   var calHeight=document.body.offsetHeight+20;
   $("#screen-freeze").css({"height":calHeight + "px"} );
			       
    if ($('#journeyType_0').is(":checked") || $('#journeyType_1').is(":checked")){
    	
    	$("#screen-freeze").css("display", "block");
    	
		var saveBtn = document.getElementById("saveBtnId");
		saveBtn.value = "Request is being created.Please wait...";
	    saveBtn.disabled = true;
		$(form).attr("action", $("#context_path").val() + "mb/saveJourneyRequest");
	    $(form).submit();
	}else{
		
		alert("Please Select Journey Type.");
		return false;
	}
	
}

function validateWarrantInfo(form){
   if (form.warrantIssueDate.value == "dd/mm/yyyy" || form.warrantIssueDate.value == "") {
        alert("Please Select Warrant Issue Date");
        form.warrantIssueDate.focus();
        return false;
    }
    if (!isGreaterThanTodayDate(document.getElementById("warrantIssueDate"))) {
	    alert("Warrant Issue Date must be Equal to OR less than Today's Date");
	    document.getElementById("warrantIssueDate").focus();
        return false;
    }
  
    if (form.warrantReason.value == " " || form.warrantReason.value == "") {
	    alert("Please Enter Warrant Issue Reason");
	    form.warrantReason.focus();
	    return false;
   }
   return true;
}

function onwardsFieldsValidation(form){
	
	var requestType = $('#reqType').val();
    var journeyMode = $("input:radio[name=journeyMode]:checked").val();
   
    if('exceptionalBooking' == requestType &&  journeyMode == '2' ){
      alert("Onwards exceptional booking  mixed mode is not allowed.");
      return false;
    }
    
    if('exceptionalBooking' == requestType &&  journeyMode == '1' && !checkForAirExistForHospitalUnit(true) ){
      alert("Onwards exceptional booking  Air mode is not allowed.");
      return false;
    }
    
   var travelMode = $('#travelMode').val();
   var serviceType = $('#serviceType').val();
   var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
   var selectedOptionDAAdvanced = $("input:radio[name=DAAdvanced]:checked").val();
	
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
	 		
	 	
	if (requestType != 'offlinebooking' && travelMode==0) 
    {
        if ($('#viaValidate').val() == "false") {
            alert("Please Wait untill we validate via Route....");
            return false;
        }
    }
	/**********************************************************/
	/*     Validation Of Mixed Mode Request Start             */
	/**********************************************************/
	
	if(travelMode==2)
	{
		// Validate Mixed request Request	
		
		var validateStatus=validateMixedModeTravelRequestFields(form);
		
		if(validateStatus) 
		{
	        return true;
	    }else{
	    	return false;
	    }
	}
	    
	/**********************************************************/
	/*     Validation Of Mixed Mode Request End               */
	/**********************************************************/

   
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val();
    

	if (requestType != 'offlinebooking' && travelMode == 0) {
		if (document.getElementById("viaRoute").checked || (document.getElementById("breakJourney")!=null && document.getElementById("breakJourney")!=undefined && document.getElementById("breakJourney").checked)) {
            var tbl = document.getElementById('routeTable');
            var lastRow = tbl.rows.length;
            var noOfRouteDtls = lastRow - 1;
            form.lastRowIndex.value = noOfRouteDtls;
        }

		var breakJourney = document.getElementById("breakJourney");
        var viaRelxRoute = document.getElementById("viaRelxRoute");
		var viaRoute = document.getElementById("viaRoute");
    }
    

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
  
   if (travelTypeText.indexOf('LTC') > -1 && serviceType=='1') {  
    	var ltcYear='';
    	var journeyDate='';
    
    if(taggCheck == 'true'){
   		ltcYear = $("input:radio[name=ltcYear]:checked").val();
      }
    	
    if(ltcYear ==''){
   		ltcYear=$("#ltcYear").val();
      }

      if(ltcYear !=''){
   		ltcYear=ltcYear.split("-")[0];
      }
      if(travelMode=='0'){
       journeyDate=$("#journeyDate").val(); 
      }
   	 
   	  
   	 if(travelMode=='1'){
   		journeyDate=$("#onwordJourneyDate").val();
      }
      
   	 
	   	 if(journeyDate !='' && journeyDate !="")
	   	  {
		   	  var journeyDateArr = journeyDate.split("/");
		      var journeyYear =journeyDateArr[2];
		      if(ltcYear>journeyYear)
		      {
		      	 alert("Please select journey date according to LTC calendar year");
		      	 return false;
		      }
	   	  }
	 
    }


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

     if(!validateTravelDate()){
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

	//alert("travelMode-"+travelMode+"|requestType-"+requestType+"|form.journeyDate.value-"+form.journeyDate.value+"||viaRelxRoute-"+viaRelxRoute);
	
	if(travelMode==0)
	{
		check = validate_required(form.entitledClass, "Please Select Entitled Class");
		if (check == true) 
		{
	        if (requestType == 'offlinebooking') {
	            if (form.journeyDate.value == "dd/mm/yyyy") {
	                alert("Please Select Journey Date")
	                form.journeyDate.focus();
	                check = false;
	            }
                if (selectedOptionDAAdvanced == 0) {
	                if (form.returnDate.value == "dd/mm/yyyy" || form.returnDate.value=="") {
	                    alert("Please Select Expected Return Date")
	                    form.returnDate.focus();
	                    check = false;
	        		}
	        }
            }else {
	           if(viaRelxRoute!=null){
	           	    if (requestType != 'offlinebooking') 
	        {
	           		if (form.journeyDate.value == "dd/mm/yyyy" && (viaRelxRoute.checked == false)) {
		                alert("Please Enter Journey Date")
		                form.journeyDate.focus();
		                check = false;
		            }
	    			}
                    if (selectedOptionDAAdvanced == 0) {
	                    if (form.returnDate.value == "dd/mm/yyyy" && (viaRelxRoute.checked == false)) {
	                        alert("Please Select Expected Return Date")
	                        form.returnDate.focus();
	                        check = false;
	                    }
                    }
               }else {
		             if (requestType != 'offlinebooking'){
		            if (form.journeyDate.value == "dd/mm/yyyy" ) {
	                alert("Please Enter Journey Date")
	                form.journeyDate.focus();
	                check = false;
	            }
	        }
                    if (selectedOptionDAAdvanced == 0) {
	                    if (form.returnDate.value == "dd/mm/yyyy") {
	                        alert("Please Select Expected Return Date")
	                        form.returnDate.focus();
	                        check = false;
	                    }
                    }
	           }
	        }
	    } else {
	        return false;
	    }
	    
	    if (check == true) {
	        if ($("#fromStation").val().trim() == "") {
	            alert("Please Enter From Station");
	            return false;
	        }
	        if ($("#toStation").val().trim() == "") {
	            alert("Please Enter To Station");
	            return false;
	        }
	    }
	
	    if (check == true) {
	        if ($("#fromStation").val().trim() == $("#toStation").val().trim()) {
	            alert("From Station & To Station Cannot Be Same");
	            return false;
	        }
	    }
	    
	    
	    if (requestType == 'offlinebooking') {
	        if (check == true) {
	            check = validate_required(form.journeyDate, "Please Select Journey Date");
	        }
            
        }else{
            if(check == true) {
	            check = validate_required(form.journeyDate, "Please Select Journey Date");
	            if (!getDaysBefore(form.journeyDate.value)) {
	                form.journeyDate.focus();
	                return false;
	            }
	        }
	    }
		//	alert("before no. of days");
	    
        if (requestType != 'offlinebooking') {
	    
            if (check == true) {
	            if (!isLessThanTodayDate(form.journeyDate)) {
	                form.journeyDate.focus();
	                return false;
	            }
	        
	        }
	    }
	   
	    
	     /*  ----------- Cluster Booking Request Check Start --------------*/
	    
	    if (requestType != 'offlinebooking') 
	    {
	    	// case of cluster booking validation
	    	
	    	var clusteredRoute=document.getElementById("clusteredRoute");
	    	
	    	if(clusteredRoute!=null && clusteredRoute!='undefined')
	    	{
	    	if (check == true && (clusteredRoute.checked == true)) 
	    	{
	    		var clusterValidate=$('#clusterValidate').val();
				var clusterTrainNoList=$('#clusterTrainNoList').val();	
				if(clusterValidate=='false')
				{
				 	alert("Please Validate Cluster Station.");
	                return false;
				}
				if(clusterValidate=='true')
				{
				 	var journeyDate0=$("#journeyDate0").val();
				 	var journeyDate1=$("#journeyDate1").val();
				 	if(journeyDate0=='' || journeyDate0=='dd/mm/yyyy')
				 	{
				 		alert("Please Choose Journey Date.");
				 		$("#journeyDate0").focus();
                 	    return false;
				 	}else if(journeyDate1=='' || journeyDate1=='dd/mm/yyyy')
				 	{
				 		alert("Please Choose Journey Date.");
				 		$("#journeyDate1").focus();
	                	return false;
				 	}
				}
	    	}
	    }
	    }

	    /*  ----------- Cluster Booking Request Check End --------------*/
	    var routeCheck = 0;
	
	    if (requestType != 'offlinebooking') 
	    {
	    	if (check == true && ((viaRoute.checked == true) ||(breakJourney!=null && breakJourney.checked==true))) {
	
	
	            for (var i = 0; i <= noOfRouteDtls; i++) {
	                var toStn = $("#toStation" + i);
	                var frmStn = $("#frmStation" + i);
	                var date = document.getElementById("journeyDate" + i);
	                
	                if (toStn.val().trim() == "") {
	                    alert("Please Enter To Station");
	                    toStn.focus();
	                    return false;
	                }
	
					if (frmStn.val().trim() == "") {
	                    alert("Please Enter From Station");
	                    frmStn.focus();
	                    return false;
	                }
	                
	                if (toStn.val().trim() == frmStn.val().trim()) {
	                    alert("From And To Stations Cannot Be Same");
	                    toStn.focus();
	                    return false;
	                }
	
	                if (date.value == "" || date.value == "dd/mm/yyyy") {
	                    alert("Please Enter Journey Date");
	                    date.focus();
	                    return false;
	                }
	                if (!isLessThanTodayDate(date)) {
	                    date.focus();
	                    return false;
	                }
	                if (i != 0) {
	                    var ErlrDate = document.getElementById("journeyDate" + (i - 1));
	
	                    if (!StartEndDate(ErlrDate.value, date.value)) {
	                        alert("Date Should Be Greater Than Or Equal To Earlier Date");
	                        date.focus();
	                        return false;
	                    }
	
	
	                    if (eval(parseInt(days_between(ErlrDate.value, date.value)) > 3)) {
	                        alert("Date Difference Between Two Journeys Should Less Than Or Equal To Three Days");
	                        return false;
	                    }
	
	                }
	
	            }
	        }
	    } //End of check if(requestType!='offlinebooking')
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
	    
	   
	    if(requestType=='international'){	
	    	 if (check == true) {
	    	 	var airViaCount = $('#airLTCViaRouteTable tr').length;
				var connectRoute="";
				if(airViaCount > 0){
					for(i = 0; i < airViaCount; i++){
						var viaString=$("#ltcViaDestination"+i).val();
						var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
			 		  if(viaStationCode== ""){
			 		  	alert("Please Enter Air Via Route for Onward Journey.");
			 		  	return false;
			 		  }
			 		  else{
			 		  	  if(i==0){
			 		  	  	connectRoute=connectRoute+viaStationCode;
			 		  	  }else{
			 		  	  	connectRoute=connectRoute+"#"+viaStationCode;
			 		  	  }
			 		  }
			 	    }
			 	    
				}
				$("#airViaLegCount").val(airViaCount);
	    	 }
	    }
	    
	}
    

    /*---------DA Advance Validation Amount Check Start--------*/
	if (check == true && travelTypeText == 'TD') 
	{
		// --- Calculate the number of days  -----------

	    if (selectedOptionDAAdvanced == 0) {
		    var date1="";
	        if (travelMode == 1) {
	        	 date1 = form.onwordJourneyDate.value;
	        }else{
	        	 date1 = form.journeyDate.value;
	        }
	        //var date1 = form.journeyDate.value;
	        var date2 = form.returnDate.value;
	
	        date1 = date1.split('/');
	        date2 = date2.split('/');
	
	        var diffd = (date2[0] - date1[0]);
	        var leap = [0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	        var nonleap = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	        // alert(date1[2]+" = year = "+date2[2]);
	        if (date1[2] > date2[2]) {
	            alert("Expected Return Journey Date must be greater than the Journey Date ");
	            form.returnDate.focus();
	            diffd = "";
	            return false;
	        } else if (date1[2] == date2[2] && date1[1] > date2[1]) {
	            alert("Expected Return Journey Date must be greater than the Journey Date ");
	            form.returnDate.focus();
	            diffd = "";
	            return false;
	        } else if (date1[1] == date2[1] && date1[0] > date2[0]) {
	            alert("Expected Return Journey Date must be greater than the Journey Date ");
	            form.returnDate.focus();
	            diffd = "";
	            return false;
	        } else {
	            if (date2[2] / 4 == 0) {
	                while (date1[1] < date2[1]) {
	                    diffd = diffd + leap[date1[1] ++];
	                }
	                while (date1[1] > date2[1]) {
	                    diffd = diffd + leap[date1[1] ++];
	                    if (date1[1] == 13)
	                        date1[1] = 1;
	
	                }
	            } else {
	                while (date1[1] < date2[1]) {
	                    diffd = diffd + nonleap[date1[1] ++];
	                }
	                while (date1[1] != date2[1]) {
	                    diffd = diffd + nonleap[date1[1] ++];
	                    if (date1[1] == 13)
	                        date1[1] = 1;
	
	                }
	            }
	           // alert(diffd+1);
	           var noOfdays=diffd+1;
	           //alert(noOfdays);
	           if(noOfdays>180){
	          	 alert("DA is not admissible as per rule.Number of days should be less than 180 days");
	          	 return false;
	           }
	            form.noOfDays.value = diffd+1;
	        }
	      
	    }
	
	
	 	if (selectedOptionDAAdvanced == 0) {
       	 	var validateDAAmountFlag= validateAdvancedAmt();
	   		// alert("validateDAAmountFlag  = " + validateDAAmountFlag);
			if(!validateDAAmountFlag) return false;
		}
    }
    /*---------DA Advance Validation Amount Check  End --------*/

	/* ------------------Composite Transfer Validation----- START ------------ */
	if (check == true && travelTypeText == 'PT') 
	{
	    if (form.transferReq.checked) {
		    	
	   		 if(!validateCompositeTransferAmt()) return false;

	}
	}
	/* ------------------Composite Transfer ----- END ------------ */


    if (check == true && requestType == "ramBaanBooking") {
        if (form.reason.value == "" || form.reason.value == "Enter Reason for Flexi Booking") {
            alert("Please Enter The reason For Flexi Booking");
            return false;
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
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276' && $("#TRRule").val() != 'TR1000281') {
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
    var totalIdProofInReq = 0;

    /*---------For Tatkal Request Start--------*/
    var isTatkalFlagObj = document.getElementById("isTatkalFlag");
    var isTatkalFlagValue = 0;
    if (isTatkalFlagObj != null)
        isTatkalFlagValue = document.getElementById("isTatkalFlag").value;

    if (isTatkalFlagValue == 1) {
        var validIdCheckCount = 0;
        if (document.getElementById("remFrmListCheck").value == "true") {
            for (i = 0; i < relArr.length - 1; i++) {
                var isChecked = document.getElementById("validIdCardPassCheck" + i).checked
                if (isChecked)
                    validIdCheckCount = validIdCheckCount + 1;
            }
        }
    }

    totalPassInReq = totalPassInReq + ischeckCount;
    totalIdProofInReq = totalIdProofInReq + validIdCheckCount;

    //alert("After Relataive::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);
    /*---------For Tatkal Request End--------*/

    var validIdCardAttCheckCount = 0;

    if (check == true) {

        if ($("#isAttendentBooking").val() == 'Yes') {

            var attendentCount = $("#finalAttendentCount").val();
            for (i = 1; i <= attendentCount; i++) {

                if (document.getElementById("attName" + i).value == '') {
                    alert("Please Enter Attendant Name");
                    document.getElementById("attName" + i).focus();
                    return false;
                }

                if (document.getElementById("attErsName" + i).value == '') {
                    alert("Please Enter Attendant ERS Print Name");
                    document.getElementById("attErsName" + i).focus();
                    return false;
                }

                if (document.getElementById("attGender" + i).value == '') {
                    alert("Please Select Attendent Gender");
                    document.getElementById("attGender" + i).focus();
                    return false;
                }

                if (document.getElementById("attDob" + i).value == '') {
                    alert("Please Select Attendent Date of Birth");
                    document.getElementById("attDob" + i).focus();
                    return false;
                }

                if (!isGreaterThanTodayDate(document.getElementById("attDob" + i))) {
                    alert("Please Select Valid Date of Birth");
                    document.getElementById("attDob" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("validIdCardAttCheck" + i).checked
                    if (isChecked)
                        validIdCardAttCheckCount = validIdCardAttCheckCount + 1;
                }


            } //End of attendent loop

            totalPassInReq = parseInt(totalPassInReq) + parseInt(attendentCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardAttCheckCount);
        }
    }  //End of attendent check.

    //alert("After Relataive+Attantent::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);


    /* Start Validation For Party Booking */

    var validIdCardPartyCheckCount = 0;

    if (check == true) 
    {

        if ($("#isPartyBooking").val() == 'Yes') 
        {

            var partyCount = $("#partyMemberCount").val();
            if (partyCount == '0') {
                alert("Please Add Party Dependent, Atleast One Party Member Is Mandatory Or Select Another TD Group");
                return false;
            }

            for (i = 1; i <= partyCount; i++) 
            {

                if (document.getElementById("partyDepName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }

                if (document.getElementById("partyDepErsName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("validIdCardPartyGroupCheck" + i).checked ;
                    if (isChecked){
                        validIdCardPartyCheckCount = validIdCardPartyCheckCount + 1;
                }
                }

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardPartyCheckCount);

        }

    }  //End of Party Booking check.

    //alert("After Relataive+Attantent+PartyBooking::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);

    //   After All Pass Checking Count Of Id Proof


    if (isTatkalFlagValue == 1) 
    {

        var reminder = totalPassInReq % 4;
        var howManyIdReq = 0;
        if (reminder == 0) {
            howManyIdReq = totalPassInReq / 4;
        } else {
            howManyIdReq = parseInt((totalPassInReq / 4)) + 1;
        }

        //alert("required id proof="+howManyIdReq+"||validIdCheckCount = "+totalIdProofInReq+"||Passanger Count "+totalPassInReq);

        if (parseInt(totalIdProofInReq) < parseInt(howManyIdReq)) {
            alert("Minimum " + howManyIdReq + " Valid Id Proof Is Required for This Tatkal Request");
            return false;
        }
       
    }


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
   
    if('exceptionalBooking' == requetType &&  journeyMode == '2' ){
    alert("Exceptional  booking for  mixed mode is not allowed.");
    return false;
    }
    
     if('exceptionalBooking' == requestType &&  journeyMode == '1' && !checkForAirExistForHospitalUnit(true) ){
      alert("Return exceptional booking  Air mode is not allowed.");
    return false;
    }
    
    var requestType = form.reqType.value;
	var travelMode = $('#returnTravelMode').val();
	var serviceType = $('#serviceType').val();
	var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
	
	//alert("validateSaveFields:travelMode-"+travelMode+"|serviceType-"+serviceType);
	 if (serviceType == 1 && requestType=="") 
	 {
	 	if(travelTypeText.indexOf('TD')>-1 || travelTypeText.indexOf('PT')>-1)
	 	 {
	 	  	if (confirm("Please ensure that Budget is available for this Booking.")){
	 	  		document.getElementById("civilBgtAvl").value = "YES";
	 	  	
			}
			else{
				window.location.reload();
				return false;
			}
	 	 }
    	
	 }
	 		
	 	
	if (requestType != 'offlinebooking' && travelMode==0) 
    {
        if ($('#returnViaValidate').val() == "false") {
            alert("Please Wait untill we validate via Route....");
            return false;
        }
    }
	/**********************************************************/
	/*     Validation Of Mixed Mode Request Start             */
	/**********************************************************/
	
	if(travelMode==2)
	{
		// Validate Mixed request Request	
		
		return validateReturnMixedModeTravelRequestFields(form);
		
	}
	    
	/**********************************************************/
	/*     Validation Of Mixed Mode Request End               */
	/**********************************************************/

   
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val();
    

    if (requestType != 'offlinebooking' && travelMode==0) 
    {
        if (document.getElementById("returnViaRoute").checked ||(document.getElementById("returnBreakJourney")!=null && document.getElementById("returnBreakJourney")!=undefined &&document.getElementById("returnBreakJourney").checked )) {
            var tbl = document.getElementById('returnRouteTable');
            var lastRow = tbl.rows.length;
            var noOfRouteDtls = lastRow - 1;
            form.returnLastRowIndex.value = noOfRouteDtls;
        }
        var returnBreakJourney=document.getElementById("returnBreakJourney");
        var viaRelxRoute = document.getElementById("returnViaRelxRoute");
        var viaRoute = document.getElementById("returnViaRoute"); 
    }

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
  
   if (travelTypeText.indexOf('LTC') > -1 && serviceType=='1') {  
    	var ltcYear='';
    	var journeyDate='';
    
    if(taggCheck == 'true'){
   		ltcYear = $("input:radio[name=ltcYear]:checked").val();
      }
    	
    if(ltcYear ==''){
   		ltcYear=$("#ltcYear").val();
      }

      if(ltcYear !=''){
   		ltcYear=ltcYear.split("-")[0];
      }
      if(travelMode=='0'){
       journeyDate=$("#return_JourneyDate").val(); 
      }
   	 
   	  
   	 if(travelMode=='1'){
   		journeyDate=$("#returnOnwordJourneyDate").val();
      }
      
   	 
	   	 if(journeyDate !='' && journeyDate !="")
	   	  {
		   	  var journeyDateArr = journeyDate.split("/");
		      var journeyYear =journeyDateArr[2];
		      if(ltcYear>journeyYear)
		      {
		      	 alert("Please select journey date according to LTC calendar year");
		      	 return false;
		      }
	   	  }
	 
    }

 if (travelTypeText.indexOf('PT') > -1 && taggCheck == 'true') 
    {
        if (document.getElementById("oldDutyStn").value == '') {
            alert("Please Enter Old Duty Station");
            document.getElementById("oldDutyStn").focus();
            return false;
        }
        if (document.getElementById("newDutyStn").value == '') {
            alert("Please Enter New Duty Station");
            document.getElementById("newDutyStn").focus();
            return false;
        }
        if (document.getElementById("oldDutyNap").value == '') {
            alert("Please Enter Old Duty Airport");
            document.getElementById("oldDutyNap").focus();
            return false;
        }
        if (document.getElementById("newDutyNap").value == '') {
            alert("Please Enter New Duty Airport");
            document.getElementById("newDutyNap").focus();
            return false;
        }
    }


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

    if(!validateTravelDate()){
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

	//alert("travelMode-"+travelMode+"|requestType-"+requestType+"|form.journeyDate.value-"+form.journeyDate.value+"||viaRelxRoute-"+viaRelxRoute);
	
	if(travelMode==0)
	{
		check = validate_required(form.returnEntitledClass, "Please Select Entitled Class");
		if (check == true) 
		{
	        if (requestType == 'offlinebooking') {
	            if (form.return_JourneyDate.value == "dd/mm/yyyy") {
	                alert("Please Select Journey Date")
	                form.return_JourneyDate.focus();
	                check = false;
	            }
            
            }else {
	           if(viaRelxRoute!=null){
	           	    if (requestType != 'offlinebooking') 
	                {
		           		if (form.return_JourneyDate.value == "dd/mm/yyyy" && (viaRelxRoute.checked == false)) {
			                alert("Please Enter Journey Date")
			                form.return_JourneyDate.focus();
			                check = false;
			            }
	    			}
                   
               }else {
		             if (requestType != 'offlinebooking'){
		            if (form.return_JourneyDate.value == "dd/mm/yyyy" ) {
	                alert("Please Enter Journey Date")
	                form.return_JourneyDate.focus();
	                check = false;
	            }
	        }
                   
	           }
	        }
	    } else {
	        return false;
	    }
	    
	    if (check == true) {
	        if ($("#returnFromStation").val().trim() == "") {
	            alert("Please Enter From Station");
	            return false;
	        }
	        if ($("#returnToStation").val().trim() == "") {
	            alert("Please Enter To Station");
	            return false;
	        }
	    }
	
	    if (check == true) {
	        if ($("#returnFromStation").val().trim() == $("#returnToStation").val().trim()) {
	            alert("From Station & To Station Cannot Be Same");
	            return false;
	        }
	    }
	    
	    
	    if (requestType == 'offlinebooking') {
	        if (check == true) {
	            check = validate_required(form.return_JourneyDate, "Please Select Journey Date");
	        }
           
        }else{
            if(check == true) {
	            check = validate_required(form.return_JourneyDate, "Please Select Journey Date");
	            if (!getDaysBefore(form.return_JourneyDate.value)) {
	                form.return_JourneyDate.focus();
	                return false;
	            }
	        }
	    }
		//	alert("before no. of days");
	    
        if (requestType != 'offlinebooking') {
	    
            if (check == true) {
	            if (!isLessThanTodayDate(form.return_JourneyDate)) {
	                form.return_JourneyDate.focus();
	                return false;
	            }
	
	            if (travelTypeText.indexOf('LTC') > -1 && taggCheck != 'true') {
	                var dateTagged = $("#jrnyDateTagged").val();
	
	                    if (days_between(form.return_JourneyDate.value, getTravelDateForSelected('1')) > 0) {
	                        alert("Return Journey Date must Be Greater than Onward Journey Date");
	                        return false;
	                    }
	                }
	            }
	        }
	    else 
	    {
	        if (travelTypeText.indexOf('LTC') > -1 && taggCheck != 'true') 
	        {
	            var dateTagged = $("#jrnyDateTagged").val();
	
	                if (days_between(form.return_JourneyDate.value, getTravelDateForSelected('1')) > 0) {
	                    alert("Return Journey Date must Be Greater Than  Onward Journey Date");
	                    return false;
	                }
	            
	        }
	    }
	    
	     /*  ----------- Cluster Booking Request Check Start --------------*/
	    
	    if (requestType != 'offlinebooking') 
	    {
	    	// case of cluster booking validation
	    	
	    	var clusteredRoute=document.getElementById("returnClusteredRoute");
	    	
	    	if(clusteredRoute!=null && clusteredRoute!='undefined')
	    	{
	    	if (check == true && (clusteredRoute.checked == true)) 
	    	{
	    		var clusterValidate=$('#returnClusterValidate').val();
				var clusterTrainNoList=$('#returnClusterTrainNoList').val();	
				if(clusterValidate=='false')
				{
				 	alert("Please Validate Cluster Station.");
	                return false;
				}
				if(clusterValidate=='true')
				{
				 	var journeyDate0=$("#returnJourneyDate0").val();
				 	var journeyDate1=$("#returnJourneyDate1").val();
				 	if(journeyDate0=='' || journeyDate0=='dd/mm/yyyy')
				 	{
				 		alert("Please Choose Journey Date.");
				 		$("#returnJourneyDate0").focus();
                 	    return false;
				 	}else if(journeyDate1=='' || journeyDate1=='dd/mm/yyyy')
				 	{
				 		alert("Please Choose Journey Date.");
				 		$("#returnJourneyDate1").focus();
	                	return false;
				 	}
				}
	    	}
	    }
	    }

	    /*  ----------- Cluster Booking Request Check End --------------*/
	    var routeCheck = 0;
		
	    if (requestType != 'offlinebooking') 
	    {
	    	
	        if (check == true && ((viaRoute.checked == true)||(returnBreakJourney!=null && returnBreakJourney.checked == true))) {
	
	
	            for (var i = 0; i <= noOfRouteDtls; i++) {
	                var toStn = $("#returnToStation" + i)
	                var frmStn = $("#returnFrmStation" + i)
	                var date = document.getElementById("return_JourneyDate" + i)
	                if (toStn.val().trim() == "") {
	                    alert("Please Enter Return Journey To Station");
	                    toStn.focus();
	                    return false;
	                }
	
					if (frmStn.val().trim() == "") {
	                    alert("Please Enter Return Journey From Station");
	                    frmStn.focus();
	                    return false;
	                }
	                
	                if (toStn.val().trim() == frmStn.val().trim()) {
	                    alert("For Return Journey, From And To Stations Cannot Be Same");
	                    toStn.focus();
	                    return false;
	                }
	
	                if (date.value == "" || date.value == "dd/mm/yyyy") {
	                    alert("Please Enter Return Journey Date")
	                    date.focus();
	                    return false;
	                }
	                if (!isLessThanTodayDate(date)) {
	                    date.focus();
	                    return false;
	                }
	                if (i != 0) {
	                    var ErlrDate = document.getElementById("return_JourneyDate" + (i - 1))
	
	                    if (!StartEndDate(ErlrDate.value, date.value)) {
	                        alert("Date Should Be Greater Than Or Equal To Earlier Date");
	                        date.focus();
	                        return false;
	                    }
	
	
	                    if (eval(parseInt(days_between(ErlrDate.value, date.value)) > 3)) {
	                        alert("Date Difference Between Two Journeys Should Less Than Or Equal To Three Days");
	                        return false;
	                    }
	
	                }
	
	            }
	        }
	    } //End of check if(requestType!='offlinebooking')
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
	
	            if (travelTypeText.indexOf('LTC') > -1 && taggCheck != 'true') 
	            {
                    if (days_between(form.returnOnwordJourneyDate.value, getReturnTravelDateForSelected()) > 0) {
                        alert("Return Journey Date must Be Greater Than  Onward Journey Date");
                        return false;
                    }
	            }
	        }else{return false;}
	    }else 
	    {
	        if (travelTypeText.indexOf('LTC') > -1 && taggCheck != 'true') 
	        {
	          
                if (days_between(form.onwordJourneyDate.value, getReturnTravelDateForSelected()) > 0) {
                    alert("Return Journey Date must Be Greater Than  Onward Journey Date");
                    return false;
                }
	            
	        }
	    }
	    
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
	    
	  
	    if(requestType=='international'){		
	    	 if (check == true) {
	    	 	var airViaCount = $('#returnAirLTCViaRouteTable tr').length;
				var connectRoute="";
				if(airViaCount > 0){
					for(i = 0; i < airViaCount; i++){
						var viaString=$("#returnLtcViaDestination"+i).val();
						var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
			 		  if(viaStationCode== ""){
						alert("Please Enter Air Via Route for Return Journey.");
						return false;
					  }
			 		  else{
			 		  	  if(i==0){
			 		  	  	connectRoute=connectRoute+viaStationCode;
			 		  	  }else{
			 		  	  	connectRoute=connectRoute+"#"+viaStationCode;
			 		  	  }
			 		  }
			 	    }
			 	    
				}
				$("#returnAirViaLegCount").val(airViaCount);
	    	 }
	    }
	    
	}
    
    

    if (check == true && requestType == "ramBaanBooking") {
        if (form.returnReason.value == "" || form.returnReason.value == "Enter Reason for Flexi Booking") {
            alert("Please Enter The reason For Flexi Booking");
            return false;
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
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276' && $("#TRRule").val() != 'TR1000281') {
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
    var totalIdProofInReq = 0;

    /*---------For Tatkal Request Start--------*/
    var isTatkalFlagObj = document.getElementById("returnIsTatkalFlag");
    var isTatkalFlagValue = 0;
    if (isTatkalFlagObj != null)
        isTatkalFlagValue = document.getElementById("returnIsTatkalFlag").value;

    if (isTatkalFlagValue == 1) {
        var validIdCheckCount = 0;
        if (document.getElementById("remFrmListCheck").value == "true") {
            for (i = 0; i < relArr.length - 1; i++) {
                var isChecked = document.getElementById("returnValidIdCardPassCheck" + i).checked
                if (isChecked)
                    validIdCheckCount = validIdCheckCount + 1;
            }
        }
    }

    totalPassInReq = totalPassInReq + ischeckCount;
    totalIdProofInReq = totalIdProofInReq + validIdCheckCount;

    //alert("After Relataive::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);
    /*---------For Tatkal Request End--------*/

    var validIdCardAttCheckCount = 0;

    if (check == true) {

        if ($("#isAttendentBooking").val() == 'Yes') {

            var attendentCount = $("#returnFinalAttendentCount").val();
            for (i = 1; i <= attendentCount; i++) {

                if (document.getElementById("returnAttName" + i).value == '') {
                    alert("Please Enter Attendant Name");
                    document.getElementById("returnAttName" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttErsName" + i).value == '') {
                    alert("Please Enter Attendant ERS Print Name");
                    document.getElementById("returnAttErsName" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttGender" + i).value == '') {
                    alert("Please Select Attendent Gender");
                    document.getElementById("returnAttGender" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttDob" + i).value == '') {
                    alert("Please Select Attendent Date of Birth");
                    document.getElementById("returnAttDob" + i).focus();
                    return false;
                }

                if (!isGreaterThanTodayDate(document.getElementById("returnAttDob" + i))) {
                    alert("Please Select Valid Date of Birth");
                    document.getElementById("returnAttDob" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("returnValidIdCardAttCheck" + i).checked
                    if (isChecked)
                        validIdCardAttCheckCount = validIdCardAttCheckCount + 1;
                }


            } //End of attendent loop

            totalPassInReq = parseInt(totalPassInReq) + parseInt(attendentCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardAttCheckCount);
        }
    }  //End of attendent check.

    //alert("After Relataive+Attantent::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);


    /* Start Validation For Party Booking */

    var validIdCardPartyCheckCount = 0;

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

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("returnValidIdCardPartyGroupCheck" + i).checked
                    if (isChecked)
                        validIdCardPartyCheckCount = validIdCardPartyCheckCount + 1;
                }

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardPartyCheckCount);

        }

    }  //End of Party Booking check.

    //alert("After Relataive+Attantent+PartyBooking::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);

    //   After All Pass Checking Count Of Id Proof


    if (isTatkalFlagValue == 1) 
    {

        var reminder = totalPassInReq % 4;
        var howManyIdReq = 0;
        if (reminder == 0) {
            howManyIdReq = totalPassInReq / 4;
        } else {
            howManyIdReq = parseInt((totalPassInReq / 4)) + 1;
        }


        //alert("required id proof="+howManyIdReq+"||validIdCheckCount = "+totalIdProofInReq+"||Passanger Count "+totalPassInReq);

        if (parseInt(totalIdProofInReq) < parseInt(howManyIdReq)) {
            alert("Minimum " + howManyIdReq + " Valid Id Proof Is Required for This Tatkal Request");
            return false;
        }
        

    }


    /* End Validation For Party Booking */

   
    return check;
	
}


function validateMixedModeTravelRequestFields(form) 
{

    //alert("-----------------Inside validateMixedModeTravelRequestFields----------------");
    
    var requestType = form.reqType.value;
    if (requestType != 'offlinebooking') 
    {
        if ($('#viaValidate').val() == "false") {
            alert("Please Wait untill we validate via Route....");
            return false;
        }
    }

	var travelMode = $('#travelMode').val();
	var serviceType = $('#serviceType').val();
	//alert("validateMixedModeTravelRequestFields:travelMode-"+travelMode+"|serviceType-"+serviceType);

    var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val()

    var check = true;
   
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
        alert("Please Click on Yes Button To Create New Request or Select an Existing One");
        return false;
    }

    if (travelTypeText.indexOf('PT') > -1 && taggCheck != 'true') {
        alert("Please Click on Yes Button To Create New Request or Select an Existing One");
        return false;
    }

    if (travelTypeText.indexOf('LTC') > -1 && taggCheck == 'true') 
    {
        var ltcyrCheck = false;
        var ltcYear = document.getElementsByName("ltcYear");
        for (var i = 0; i < ltcYear.length; i++) {
            if (ltcYear[i].checked == true)
                ltcyrCheck = true;
        }
        if (!ltcyrCheck) {
            alert("Please Select Year For Which You Are Applying for LTC");
            return false;
        }
    }
    
     if (travelTypeText.indexOf('PT') > -1 && taggCheck == 'true') 
    {
        if (document.getElementById("oldDutyStn").value == '') {
            alert("Please Enter Old Duty Station");
            document.getElementById("oldDutyStn").focus();
            return false;
        }
        if (document.getElementById("newDutyStn").value == '') {
            alert("Please Enter New Duty Station");
            document.getElementById("newDutyStn").focus();
            return false;
        }
        if (document.getElementById("oldDutyNap").value == '') {
            alert("Please Enter Old Duty Airport");
            document.getElementById("oldDutyNap").focus();
            return false;
        }
        if (document.getElementById("newDutyNap").value == '') {
            alert("Please Enter New Duty Airport");
            document.getElementById("newDutyNap").focus();
            return false;
        }
    }


    var category = document.getElementById("category").value.toUpperCase();
    var travelType = $('#TravelTypeDD :selected').text().toUpperCase();
    if (category.indexOf('OFFICER') > -1 && travelType == 'CV') {
        alert("Officers cannot book Ticket for Travel Type CV");
        return false;
    }
    
    var airlineType=$("#mixedAirlineType").val();
        
        if(airlineType == ""){
        	alert("Please Select Airline Type")
            check = false;
            return false;
        }
        
        if(airlineType == "Others"){
        	var otherAirlineType=$("#mixedOtherAirlineType").val();
        	if(otherAirlineType == ""){
        		alert("Please Select Others Reason");
	            check = false;
	            return false;
        	}
        	if(otherAirlineType == "FAA"){
        		if($("#mixed_FAA_AuthorityNo").val().trim() == ""){
        			alert("Financial Authority No. Can not be blank");
		            check = false;
		            return false;
        		}
        		
        		if($("#mixed_FAA_AuthorityDate").val().trim() == ""){
        			alert("Financial Authority Date Can not be blank");
		            check = false;
		            return false;
        		}
        	}
        }

    if(!validateTravelDate()){
 		return false;
 	 }
 	 
    if (check == true)
        check = validate_required(form.authorityNo, "Please Enter Authority No.");

 	if (check == true) {
        check = validate_required(form.authorityDate, "Please Enter Authority Date");
	 } else {
        return false;
    }
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
        if (!isGreaterThanTodayDate(document.getElementById("authorityDate"))) {
            alert("Authority Date must be Equal to OR less than Today's Date");
            document.getElementById("authorityDate").focus();
            return false;
        }
    }
   	
   	var airAccountOffice = $("#airAccountOffice").val();
	if(check == true && (airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0) ){
	   alert("Pay account office for air travel is not assigned so air travel request can not be created.");
       check = false;
       return false;
    }
        
   	var mixedPreference = document.getElementById("mixedPreference");
	if(mixedPreference!=null && check == true)
	{
		if (mixedPreference.value == "-1") 
		{
            alert("Please Select Preference For Mixed Mode Journey.")
            mixedPreference.focus();
            return false;
        }
        } else {
		return false;
	}
	
	check = validate_required(form.mixedRailEntitledClass, "Please Select Rail Entitled Class");
 	if (check == true) {
      check = validate_required(form.mixedAirEntitledClass, "Please Select Air Entitles Class");
    } else {
        return false;
    }
	
	if (requestType != 'offlinebooking' && check == true ) 
	{
		var fixedOrgStationDD=document.getElementById("fixedOrgStationDD");
		var fixedDestStationDD=document.getElementById("fixedDestStationDD");
		if (fixedOrgStationDD.value == "") {
            alert("Please Select From Place.");
            fixedOrgStationDD.focus();
            return false;
        }

		if (fixedDestStationDD.value == "") {
            alert("Please Select To Place.");
            fixedDestStationDD.focus();
            return false;
        }
		
        var fixedOrigin = document.getElementById("fixedOrigin");
        var fixedDestination = document.getElementById("fixedDestination");
       
        if (fixedOrigin.value == "") {
            alert("Please Enter Origin Station");
            fixedOrigin.focus();
            return false;
        }

		if (fixedDestination.value == "") {
            alert("Please Enter Destination Station");
            fixedDestination.focus();
            return false;
        }
        
        if (fixedOrigin.value == fixedDestination.value) {
            alert("Origin And Destination Stations Cannot Be Same");
            fixedDestination.value="";
            fixedDestination.focus();
            return false;
        }
	               
	}else {
        return false;
    }
	
	if(check == true ) 
	{
		var breakJourneyClicked=$("#breakJourneyClicked").val();
		if(breakJourneyClicked=='NO'){
			alert("Please Click On Break Journey Button.");
			return false;
		}
	}
	
	var mixedPreference=$("#mixedPreference").val();
	if(mixedPreference==0)
    {
    	// case of rail- air preferences
    	
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySourceOne0 = document.getElementById("mixedJrnySourceOne0");
	        var mixedJrnyDestinationOne0 = document.getElementById("mixedJrnyDestinationOne0");
	       	var mixedJrnyJourneyDateOne0 = document.getElementById("mixedJrnyJourneyDateOne0");
	       	
	       	if(null==mixedJrnySourceOne0){
	       		alert("Please Break Onward Journey");
	       		return false;
	       	}
	        
	        if (mixedJrnySourceOne0.value == "") {
	            alert("Please Enter Source Station");
	            mixedJrnySourceOne0.focus();
	            return false;
	        }
	
			if (mixedJrnyDestinationOne0.value == "") {
	            alert("Please Enter Destination Station");
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne0.value == mixedJrnyDestinationOne0.value) {
	            alert("Source And Destination Stations Cannot Be Same")
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDateOne0.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne0.value=="") {
                alert("Please Enter Journey Date")
                mixedJrnyJourneyDateOne0.focus();
                return false;
            }
	            
	    	var viaRuteOne=$("#viaRuteOne").val();
	    	
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("frmStationOne");
	    		var toStationOne=document.getElementsByName("toStationOne");
	    		var journeyDateOne=document.getElementsByName("journeyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			
	    				if (frmStationOne[i].value == "") {
				            alert("Please Enter From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Journey Date")
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}
	    	
	    	var mixedJrnySource1 = document.getElementById("mixedJrnySourceAir");
	        var mixedJrnyDestination1 = document.getElementById("mixedJrnyDestination1");
	       	var mixedJrnyJourneyDate1 = document.getElementById("mixedJrnyJourneyDate1");
	        
	        if (mixedJrnySource1.value == "") {
	            alert("Please Enter Origin Airport");
	            mixedJrnySource1.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination1.value == "") {
	            alert("Please Enter Destination Airport");
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource1.value == mixedJrnyDestination1.value) {
	            alert("Origin And Destination Airport Cannot Be Same");
	            mixedJrnySource1.value="";
	            mixedJrnySource1.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDate1.value == "dd/mm/yyyy" || mixedJrnyJourneyDate1.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDate1.focus();
                return false;
            }
           // alert("days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value)-"+days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value));
            if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value) > 0) 
            {
	            alert("Air Leg Journey Date must Be Greater Than Rail Leg Journey Date");
	            return false;
	        }
	        if(days_between(mixedJrnyJourneyDateOne0.value, mixedJrnyJourneyDate1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDate1.focus();
	            return false;
	        }
	    	
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
    
    if(mixedPreference==1)
    {
    	// case of air-rail preferences
    	//alert("case of air-rail preferences");
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySource0 = document.getElementById("mixedJrnySource0");
	        var mixedJrnyDestination0 = document.getElementById("mixedJrnyDestination0");
	       	var mixedJrnyJourneyDate0 = document.getElementById("mixedJrnyJourneyDate0");
	        
	        if (mixedJrnySource0.value == "") {
	            alert("Please Enter Origin Airport");
	            mixedJrnySource0.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination0.value == "") {
	            alert("Please Enter Destination Airport");
	            mixedJrnyDestination0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource0.value == mixedJrnyDestination0.value) {
	            alert("Origin And Destination Airport Cannot Be Same");
	            mixedJrnyDestination0.value="";
	            mixedJrnyDestination0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDate0.value == "dd/mm/yyyy" || mixedJrnyJourneyDate0.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDate0.focus();
                return false;
            }
            
            var mixedJrnySourceOne1 = document.getElementById("mixedJrnySourceOne1")
	        var mixedJrnyDestinationOne1 = document.getElementById("mixedJrnyDestinationOne1")
	       	var mixedJrnyJourneyDateOne1 = document.getElementById("mixedJrnyJourneyDateOne1")
	        
	        if (mixedJrnySourceOne1.value == "") {
	            alert("Please Enter Source Station");
	            mixedJrnySourceOne1.focus();
	            return false;
	        }
	
			if (mixedJrnyDestinationOne1.value == "") {
	            alert("Please Enter Destination Station");
	            mixedJrnyDestinationOne1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne1.value == mixedJrnyDestinationOne1.value) {
	            alert("Source And Destination Station Cannot Be Same");
	            mixedJrnySourceOne1.value="";
	            mixedJrnySourceOne1.focus();
	            return false;
	        }
	        
	        if (mixedJrnyJourneyDateOne1.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne1.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDateOne1.focus();
                return false;
            }
            //alert("days_between("+mixedJrnyJourneyDateOne1.value+", "+mixedJrnyJourneyDate0.value+")-"+days_between(mixedJrnyJourneyDateOne1.value, mixedJrnyJourneyDate0.value));
            if(days_between(mixedJrnyJourneyDateOne1.value, mixedJrnyJourneyDate0.value) > 0) 
            {
	            alert("Rail Leg Journey Date must Be Greater Than Air Leg Journey Date");
	            return false;
	        }
	        if(days_between(mixedJrnyJourneyDate0.value, mixedJrnyJourneyDateOne1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDateOne1.focus();
	            return false;
	        }
	            
	    	var viaRuteOne=$("#viaRuteOne").val();
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("frmStationOne");
	    		var toStationOne=document.getElementsByName("toStationOne");
	    		var journeyDateOne=document.getElementsByName("journeyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			
	    				if (frmStationOne[i].value == "") {
				            alert("Please Enter From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Journey Date");
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
    
    
    if(mixedPreference==2)
    {
    	
    	// case of rail-air-rail preferences
    	
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySourceOne0 = document.getElementById("mixedJrnySourceOne0");
	        var mixedJrnyDestinationOne0 = document.getElementById("mixedJrnyDestinationOne0");
	       	var mixedJrnyJourneyDateOne0 = document.getElementById("mixedJrnyJourneyDateOne0");
	        
	        if(null==mixedJrnySourceOne0){
	       		alert("Please Break Onward Journey");
	       		return false;
	       	}
	       	
	        if (mixedJrnySourceOne0.value == "") {
	            alert("Please Enter Source Station");
	            return false;
	        }
	
			if (mixedJrnyDestinationOne0.value == "") {
	            alert("Please Enter Destination Station");
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne0.value == mixedJrnyDestinationOne0.value) {
	            alert("Source And Destination Station Cannot Be Same");
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDateOne0.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne0.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDateOne0.focus();
                return false;
            }
            
            // Validating via request for leg one
            
           	var viaRuteOne=$("#viaRuteOne").val();
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("frmStationOne");
	    		var toStationOne=document.getElementsByName("toStationOne");
	    		var journeyDateOne=document.getElementsByName("journeyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			if (frmStationOne[i].value == "") {
				            alert("Please Enter From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Journey Date");
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}// end via root
            
            
            var mixedJrnySource = document.getElementById("mixedJrnySourceAir");
	        var mixedJrnyDestination1 = document.getElementById("mixedJrnyDestination1");
	       	var mixedJrnyJourneyDate1 = document.getElementById("mixedJrnyJourneyDate1");
	        
	        if (mixedJrnySource.value == "") {
	            alert("Please Enter Origin Airport");
	            mixedJrnySource.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination1.value == "") {
	            alert("Please Enter Destination Station");
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource.value == mixedJrnyDestination1.value) {
	            alert("Origin And Destination Airport Cannot Be Same");
	            mixedJrnyDestination1.value="";
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnyJourneyDate1.value == "dd/mm/yyyy" || mixedJrnyJourneyDate1.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDate1.focus();
                return false;
            }
           
            //alert("days_between("+mixedJrnyJourneyDate1.value+", "+mixedJrnyJourneyDateOne0.value+")-"+days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value));
            if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value) > 0) 
            {
	            alert("Air Leg Journey Date must Be Greater Than Rail Leg Journey Date");
	            return false;
	        }
	        
	         if(days_between(mixedJrnyJourneyDateOne0.value, mixedJrnyJourneyDate1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDate1.focus();
	            return false;
	        }
	            
	    	
	    	//Rail Leg
	    	
	    	var mixedJrnySourceTwo2 = document.getElementById("mixedJrnySourceOne1");
	        var mixedJrnyDestinationTwo2 = document.getElementById("mixedJrnyDestinationTwo2");
	       	var mixedJrnyJourneyDateTwo2 = document.getElementById("mixedJrnyJourneyDateTwo2");
	        
	        if (mixedJrnySourceTwo2.value == "") {
	            alert("Please Enter Source Station");
	            return false;
	        }
	
			if (mixedJrnyDestinationTwo2.value == "") {
	            alert("Please Enter Destination Station");
	            return false;
	        }
	        
	        if (mixedJrnySourceTwo2.value == mixedJrnyDestinationTwo2.value) {
	            alert("Source And Destination Station Cannot Be Same");
	            return false;
	        }
	        if (mixedJrnyJourneyDateTwo2.value == "dd/mm/yyyy" || mixedJrnyJourneyDateTwo2.value=="") {
                alert("Please Enter Journey Date");
                mixedJrnyJourneyDateTwo2.focus();
                return false;
            }
            
            // Validating via request for leg one
            
           	var viaRuteTwo=$("#viaRuteTwo").val();
	    	
	    	if(viaRuteTwo=='YES')
	    	{
	    		var frmStationTwo=document.getElementsByName("frmStationTwo");
	    		var toStationTwo=document.getElementsByName("toStationTwo");
	    		var journeyDateTwo=document.getElementsByName("journeyDateTwo");
		     	
		     	if(frmStationTwo!=null && toStationTwo!=null && journeyDateTwo!=null)
		     	{
		     		for(var i=0;i<frmStationTwo.length;i++)
		     		{
		     			if (frmStationTwo[i].value == "") {
				            alert("Please Enter From Station");
				            frmStationTwo[i].focus();
				            return false;
				        }
				
						if (toStationTwo[i].value == "") {
				            alert("Please Enter To Station");
				            toStationTwo[i].focus();
				            return false;
				        }
				        
				        if (frmStationTwo[i].value == toStationTwo[i].value) {
				            alert("From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateTwo[i].value == "dd/mm/yyyy" || journeyDateTwo[i].value=="") {
			                alert("Please Enter Journey Date");
			                journeyDateTwo[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}// end via root
            
            if(days_between(mixedJrnyJourneyDateTwo2.value, mixedJrnyJourneyDate1.value) > 0) 
            {
	            alert("Rail Leg Journey Date must Be Greater Than Air Leg Journey Date");
	            return false;
	        }
	        
	         if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateTwo2.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDateTwo2.focus();
	            return false;
	        }
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
   
   
    if(requestType=='international'){	
	    	 if (check == true) {
	    	 	var airViaCount = $('#airMixedLTCViaRouteTable tr').length;
				var connectRoute="";
				if(airViaCount > 0){
					for(i = 0; i < airViaCount; i++){
						var viaString=$("#mixedLTCViaDestination"+i).val();
						var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
			 		  if(viaStationCode== ""){
			 		  	alert("Please Enter Air Via Route for Onward Journey.");
			 		  	return false;
			 		  }
			 		  else{
			 		  	  if(i==0){
			 		  	  	connectRoute=connectRoute+viaStationCode;
			 		  	  }else{
			 		  	  	connectRoute=connectRoute+"#"+viaStationCode;
			 		  	  }
			 		  }
			 	    }
				}
				$("#airViaLegCount").val(airViaCount);
	    	 }
	    }
   
	if (requestType != 'offlinebooking' && check == true)
	{
		var selectedFlightKey = $("#flightKey").val();
		var selectedFlightCarrier = $("#flightCode").val();
			
		if(selectedFlightKey == '' || selectedFlightCarrier == '')
		{
			alert("Please Search And Select flight for Onward Journey.");
			return false;
		}
	}    
	


    if (check == true && requestType == "ramBaanBooking") {
        if (form.reason.value == "" || form.reason.value == "Enter Reason for Flexi Booking") {
            alert("Please Enter The reason For Flexi Booking");
            return false;
        }
    }

 	var relationStr = document.getElementById("relation").value;
    var relArr = relationStr.split(",");
    var ischeckCount = 0;
    
	if(check == true)
	{
		if (document.getElementById("remFrmListCheck").value == "true")
	    {
	        for (i = 0; i < relArr.length - 1; i++) 
	        {
	            var isChecked = document.getElementById("removeFrmList" + i).checked;
	            if (isChecked)
	                ischeckCount = ischeckCount + 1;
	        }
	    }
	}else{
		return false;
	}

    //alert("relationStr-->"+relationStr+"|reqstCheck-"+reqstCheck+"|ischeckCount-"+ischeckCount);
    
    if (check == true && reqstCheck == 'true') 
    {
        var isReqsApprv = document.getElementsByName("reqsAproved");
        for (i = 0; i < isReqsApprv.length; i++) {
            if (isReqsApprv[i].checked == false) {
                alert("Please Approve The Requisite First");
                return false;
            }
        }
    }
    if (check == true) 
    {
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276') {
            alert("One passenger must be selected");
            return false;
        }
        
        if (check == true) {
			if(getRequestTravellerCount()==false)
				return false;
	 	}
	 	
	 }

    var totalPassInReq = 0;
    var totalIdProofInReq = 0;

    /*---------For Tatkal Request Start--------*/
    var isTatkalFlagObj = document.getElementById("isTatkalFlagMixed");
    var isTatkalFlagValue = 0;
    if (isTatkalFlagObj != null)
        isTatkalFlagValue = isTatkalFlagObj.value;
	
	var validIdCheckCount = 0;
    
    if (isTatkalFlagValue == 1) 
    {
        if (document.getElementById("remFrmListCheck").value == "true") ;
        {
            for (i = 0; i < relArr.length - 1; i++) 
            {
                var isChecked = document.getElementById("validIdCardPassCheck" + i).checked ;
                if (isChecked)
                    validIdCheckCount = validIdCheckCount + 1;
            }
        }
      
    }

    totalPassInReq = totalPassInReq + ischeckCount;
    totalIdProofInReq = totalIdProofInReq + validIdCheckCount;

    //alert("After Relataive::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);
    /*---------For Tatkal Request End--------*/

    var validIdCardAttCheckCount = 0;

    if (check == true) 
    {

        if ($("#isAttendentBooking").val() == 'Yes') 
        {

            var attendentCount = $("#finalAttendentCount").val();
            for (i = 1; i <= attendentCount; i++) {

                if (document.getElementById("attName" + i).value == '') {
                    alert("Please Enter Attendant Name");
                    document.getElementById("attName" + i).focus();
                    return false;
                }

                if (document.getElementById("attErsName" + i).value == '') {
                    alert("Please Enter Attendant ERS Print Name");
                    document.getElementById("attErsName" + i).focus();
                    return false;
                }

                if (document.getElementById("attGender" + i).value == '') {
                    alert("Please Select Attendent Gender");
                    document.getElementById("attGender" + i).focus();
                    return false;
                }

                if (document.getElementById("attDob" + i).value == '') {
                    alert("Please Select Attendent Date of Birth");
                    document.getElementById("attDob" + i).focus();
                    return false;
                }

                if (!isGreaterThanTodayDate(document.getElementById("attDob" + i))) {
                    alert("Please Select Valid Date of Birth");
                    document.getElementById("attDob" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("validIdCardAttCheck" + i).checked;
                    if (isChecked)
                        validIdCardAttCheckCount = validIdCardAttCheckCount + 1;
                }


            } //End of attendent loop

            totalPassInReq = parseInt(totalPassInReq) + parseInt(attendentCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardAttCheckCount);

        }//End of attendent check.

        } else {
            return false;
        }

    //alert("After Relataive+Attantent::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);


    /* Start Validation For Party Booking */

    var validIdCardPartyCheckCount = 0;

    if (check == true) 
    {

        if ($("#isPartyBooking").val() == 'Yes') 
        {

            var partyCount = $("#partyMemberCount").val();
            if (partyCount == '0') {
                alert("Please Add Party Dependent, Atleast One Party Member Is Mandatory Or Select Another TD Group");
                return false;
            }

            for (i = 1; i <= partyCount; i++) 
            {

                if (document.getElementById("partyDepName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }

                if (document.getElementById("partyDepErsName" + i).value == '') {
                    alert("Please Enter Valid Personal Number of Party Dependent");
                    document.getElementById("dependentPersonalNo" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("validIdCardPartyGroupCheck" + i).checked;
                    if (isChecked)
                        validIdCardPartyCheckCount = validIdCardPartyCheckCount + 1;
                }

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardPartyCheckCount);

        }

    }  //End of Party Booking check.

    //alert("After Relataive+Attantent+PartyBooking::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);

    //   After All Pass Checking Count Of Id Proff


    if (isTatkalFlagValue == 1) 
    {

        var reminder = totalPassInReq % 4;
        var howManyIdReq = 0;
        if (reminder == 0) {
            howManyIdReq = totalPassInReq / 4;
        } else {
            howManyIdReq = parseInt((totalPassInReq / 4)) + 1;
        }


        //alert("required id proof="+howManyIdReq+"||validIdCheckCount = "+totalIdProofInReq+"||Passanger Count "+totalPassInReq);

        if (parseInt(totalIdProofInReq) < parseInt(howManyIdReq)) {
            alert("Minimum " + howManyIdReq + " Valid Id Proof Is Required for This Tatkal Request");
            return false;
        }
        if (parseInt(totalIdProofInReq) > parseInt(howManyIdReq)) {
            //alert("Only "+howManyIdReq+" Valid Id Proof Is Required for This Tatkal Request");
            //return false;
        }
    }
	
    //alert("Just Before Saving The Request..:"+check);
    return check;
}	


function validateReturnMixedModeTravelRequestFields(form) 
{

    //alert("-----------------Inside validateMixedModeTravelRequestFields----------------");
    
    var requestType = form.reqType.value;
    if (requestType != 'offlinebooking') 
    {
        if ($('#returnViaValidate').val() == "false") {
            alert("Please Wait untill we validate via Route....");
            return false;
        }
    }

	var travelMode = $('#returnTravelMode').val();
	var serviceType = $('#serviceType').val();
	//alert("validateMixedModeTravelRequestFields:travelMode-"+travelMode+"|serviceType-"+serviceType);

    var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
    var taggReqCheck = $('#taggRequestCheck').val();
    var isTaggedORNew = $('#isTaggedORNew').val();
    var taggCheck = $('#taggCheck').val();
    var reqstCheck = $("#reqstCheck").val()

    var check = true;
   
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

    if ((travelTypeText.indexOf('PT') > -1 || travelTypeText.indexOf('LTC') > -1) && taggReqCheck == 'false') {
        alert("Please Click on Tagged Request Link First");
        return false;
    }

    if (travelTypeText.indexOf('LTC') > -1 && isTaggedORNew == 'false') {
        alert("Please Click on Yes Button To Create New Request or Select an Existing One");
        return false;
    }

    if (travelTypeText.indexOf('PT') > -1 && taggCheck != 'true') {
        alert("Please Click on Yes Button To Create New Request or Select an Existing One");
        return false;
    }

    if (travelTypeText.indexOf('LTC') > -1 && taggCheck == 'true') 
    {
        var ltcyrCheck = false;
        var ltcYear = document.getElementsByName("ltcYear");
        for (var i = 0; i < ltcYear.length; i++) {
            if (ltcYear[i].checked == true)
                ltcyrCheck = true;
        }
        if (!ltcyrCheck) {
            alert("Please Select Year For Which You Are Applying for LTC");
            return false;
        }
    }
    
     if (travelTypeText.indexOf('PT') > -1 && taggCheck == 'true') 
    {
        if (document.getElementById("oldDutyStn").value == '') {
            alert("Please Enter Old Duty Station");
            document.getElementById("oldDutyStn").focus();
            return false;
        }
        if (document.getElementById("newDutyStn").value == '') {
            alert("Please Enter New Duty Station");
            document.getElementById("newDutyStn").focus();
            return false;
        }
        if (document.getElementById("oldDutyNap").value == '') {
            alert("Please Enter Old Duty Airport");
            document.getElementById("oldDutyNap").focus();
            return false;
        }
        if (document.getElementById("newDutyNap").value == '') {
            alert("Please Enter New Duty Airport");
            document.getElementById("newDutyNap").focus();
            return false;
        }
    }


    var airlineType=$("#returnMixedAirlineType").val();
        
        if(airlineType == ""){
        	alert("Please Select Return Airline Type")
            check = false;
            return false;
        }
        
        if(airlineType == "Others"){
        	var otherAirlineType=$("#returnMixedOtherAirlineType").val();
        	if(otherAirlineType == ""){
        		alert("Please Select Return Others Reason");
	            check = false;
	            return false;
        	}
        	if(otherAirlineType == "FAA"){
        		if($("#returnMixedFAA_AuthorityNo").val().trim() == ""){
        			alert("Financial Authority No. Can not be blank");
		            check = false;
		            return false;
        		}
        		
        		if($("#returnMixedFAA_AuthorityDate").val().trim() == ""){
        			alert("Financial Authority Date Can not be blank");
		            check = false;
		            return false;
        		}
        	}
        }

    var category = document.getElementById("category").value.toUpperCase();
    var travelType = $('#TravelTypeDD :selected').text().toUpperCase();
    if (category.indexOf('OFFICER') > -1 && travelType == 'CV') {
        alert("Officers cannot book Ticket for Travel Type CV");
        return false;
    }

    if(!validateTravelDate()){
 		return false;
 	 }
    

    if (check == true)
        check = validate_required(form.authorityNo, "Please Enter Authority No.");

 	if (check == true) {
        check = validate_required(form.authorityDate, "Please Enter Authority Date");
	 } else {
        return false;
    }
    if (check == true) {
        if (form.authorityDate.value == "dd/mm/yyyy") {
            alert("Please Enter Authority Date");
            form.authorityDate.focus();
            return false;
        }
    } else {
        return false;
    }

    if (check == true) {
        if (!isGreaterThanTodayDate(document.getElementById("authorityDate"))) {
            alert("Authority Date must be Equal to OR less than Today's Date");
            document.getElementById("authorityDate").focus();
            return false;
        }
    }
   	
   	var airAccountOffice = $("#airAccountOffice").val();
	if(check == true && (airAccountOffice==null || airAccountOffice=='' || airAccountOffice.length==0) ){
	   alert("Pay account office for air travel is not assigned so air travel request can not be created.");
       check = false;
       return false;
    }
        
   	var mixedPreference = document.getElementById("returnMixedPreference");
	if(mixedPreference!=null && check == true)
	{
		if (mixedPreference.value == "-1") 
		{
            alert("Please Select Preference For Return Mixed Mode Journey.")
            mixedPreference.focus();
            return false;
        }
    } else {
	return false;
    }
	
	check = validate_required(form.returnMixedRailEntitledClass, "Please Select Return Rail Entitled Class");
 	if (check == true) {
      check = validate_required(form.returnMixedAirEntitledClass, "Please Select Return Air Entitles Class");
    } else {
        return false;
    }
	
	if (requestType != 'offlinebooking' && check == true ) 
	{
		var fixedOrgStationDD=document.getElementById("returnFixedOrgStationDD");
		var fixedDestStationDD=document.getElementById("returnFixedDestStationDD");
		if (fixedOrgStationDD.value == "") {
            alert("Please Select Return From Place.");
            fixedOrgStationDD.focus();
            return false;
        }

		if (fixedDestStationDD.value == "") {
            alert("Please Select Return To Place.");
            fixedDestStationDD.focus();
            return false;
        }
		
        var fixedOrigin = document.getElementById("returnFixedOrigin");
        var fixedDestination = document.getElementById("returnFixedDestination");
       
        if (fixedOrigin.value == "") {
            alert("Please Enter Return Origin Station");
            fixedOrigin.focus();
            return false;
        }

		if (fixedDestination.value == "") {
            alert("Please Enter Return Destination Station");
            fixedDestination.focus();
            return false;
        }
        
        if (fixedOrigin.value == fixedDestination.value) {
            alert("For Return Journy, Origin And Destination Stations Cannot Be Same");
            fixedDestination.value="";
            fixedDestination.focus();
            return false;
        }
	               
	}else {
        return false;
    }
	
	if(check == true ) 
	{
		var breakJourneyClicked=$("#returnBreakJourneyClicked").val();
		if(breakJourneyClicked=='NO'){
			alert("Please Click On Break Journey Button.");
			return false;
		}
	}
	
	var mixedPreference=$("#returnMixedPreference").val();
	if(mixedPreference==0)
    {
    	// case of rail- air preferences
    	
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySourceOne0 = document.getElementById("returnMixedJrnySourceOne0");
	        var mixedJrnyDestinationOne0 = document.getElementById("returnMixedJrnyDestinationOne0");
	       	var mixedJrnyJourneyDateOne0 = document.getElementById("returnMixedJrnyJourneyDateOne0");
	        
	        if(null==mixedJrnySourceOne0){
	       		alert("Please Break Return Journey");
	       		return false;
	       	}
	       	
	        if (mixedJrnySourceOne0.value == "") {
	            alert("Please Enter Return Journey Source Station");
	            mixedJrnySourceOne0.focus();
	            return false;
	        }
	
			if (mixedJrnyDestinationOne0.value == "") {
	            alert("Please Enter Return Journey Destination Station");
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne0.value == mixedJrnyDestinationOne0.value) {
	            alert("For Return Journey, Source And Destination Stations Cannot Be Same");
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDateOne0.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne0.value=="") {
                alert("Please Enter Return Journey Date");
                mixedJrnyJourneyDateOne0.focus();
                return false;
            }
	            
	    	var viaRuteOne=$("#returnViaRuteOne").val();
	    	
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("returnFrmStationOne");
	    		var toStationOne=document.getElementsByName("returnToStationOne");
	    		var journeyDateOne=document.getElementsByName("return_JourneyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			
	    				if (frmStationOne[i].value == "") {
				            alert("Please Enter Return Journey From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter Return Journey To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("For Return Journey, From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Return Journey Date");
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}
	    	
	    	var mixedJrnySource1 = document.getElementById("returnMixedJrnySourceAir");
	        var mixedJrnyDestination1 = document.getElementById("returnMixedJrnyDestination1");
	       	var mixedJrnyJourneyDate1 = document.getElementById("returnMixedJrnyJourneyDate1");
	        
	        if (mixedJrnySource1.value == "") {
	            alert("Please Enter Return Journey Origin Airport");
	            mixedJrnySource1.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination1.value == "") {
	            alert("Please Enter Return Journey Destination Airport");
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource1.value == mixedJrnyDestination1.value) {
	            alert("for Return Journey, Origin And Destination Airport Cannot Be Same");
	            mixedJrnySource1.value="";
	            mixedJrnySource1.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDate1.value == "dd/mm/yyyy" || mixedJrnyJourneyDate1.value=="") {
                alert("Please Enter Return Journey Date");
                mixedJrnyJourneyDate1.focus();
                return false;
            }
           // alert("days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value)-"+days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value));
            if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value) > 0) 
            {
	            alert("Air Leg Journey Date must Be Greater Than Rail Leg Journey Date");
	            return false;
	        }
	        if(days_between(mixedJrnyJourneyDateOne0.value, mixedJrnyJourneyDate1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDate1.focus();
	            return false;
	        }
	    	
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
    
    if(mixedPreference==1)
    {
    	// case of air-rail preferences
    	//alert("case of air-rail preferences");
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySource0 = document.getElementById("returnMixedJrnySource0");
	        var mixedJrnyDestination0 = document.getElementById("returnMixedJrnyDestination0");
	       	var mixedJrnyJourneyDate0 = document.getElementById("returnMixedJrnyJourneyDate0");
	        
	        if (mixedJrnySource0.value == "") {
	            alert("Please Enter Return Journey Origin Airport");
	            mixedJrnySource0.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination0.value == "") {
	            alert("Please Enter Return Journey Destination Airport");
	            mixedJrnyDestination0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource0.value == mixedJrnyDestination0.value) {
	            alert("For Return Journey, Origin And Destination Airport Cannot Be Same");
	            mixedJrnyDestination0.value="";
	            mixedJrnyDestination0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDate0.value == "dd/mm/yyyy" || mixedJrnyJourneyDate0.value=="") {
                alert("Please Enter Return Journey Date");
                mixedJrnyJourneyDate0.focus();
                return false;
            }
            
            var mixedJrnySourceOne1 = document.getElementById("returnMixedJrnySourceOne1")
	        var mixedJrnyDestinationOne1 = document.getElementById("returnMixedJrnyDestinationOne1")
	       	var mixedJrnyJourneyDateOne1 = document.getElementById("returnMixedJrnyJourneyDateOne1")
	        
	        if (mixedJrnySourceOne1.value == "") {
	            alert("Please Enter Return Journey Source Station");
	            mixedJrnySourceOne1.focus();
	            return false;
	        }
	
			if (mixedJrnyDestinationOne1.value == "") {
	            alert("Please Enter Return Journey Destination Station");
	            mixedJrnyDestinationOne1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne1.value == mixedJrnyDestinationOne1.value) {
	            alert("For Return Journey, Source And Destination Station Cannot Be Same")
	            mixedJrnySourceOne1.value="";
	            mixedJrnySourceOne1.focus();
	            return false;
	        }
	        
	        if (mixedJrnyJourneyDateOne1.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne1.value=="") {
                alert("Please Enter Return Journey Date")
                mixedJrnyJourneyDateOne1.focus();
                return false;
            }
            //alert("days_between("+mixedJrnyJourneyDateOne1.value+", "+mixedJrnyJourneyDate0.value+")-"+days_between(mixedJrnyJourneyDateOne1.value, mixedJrnyJourneyDate0.value));
            if(days_between(mixedJrnyJourneyDateOne1.value, mixedJrnyJourneyDate0.value) > 0) 
            {
	            alert("Rail Leg Journey Date must Be Greater Than Air Leg Journey Date");
	            return false;
	        }
	        if(days_between(mixedJrnyJourneyDate0.value, mixedJrnyJourneyDateOne1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDateOne1.focus();
	            return false;
	        }
	            
	    	var viaRuteOne=$("#returnViaRuteOne").val();
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("returnFrmStationOne");
	    		var toStationOne=document.getElementsByName("returnToStationOne");
	    		var journeyDateOne=document.getElementsByName("return_JourneyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			
	    				if (frmStationOne[i].value == "") {
				            alert("Please Enter Return Journey From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter Return Journey To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("For Return Journey, From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Return Journey Date");
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
    
    
    if(mixedPreference==2)
    {
    	
    	// case of rail-air-rail preferences
    	
    	if (requestType != 'offlinebooking' && check == true)
	    {
    		var mixedJrnySourceOne0 = document.getElementById("returnMixedJrnySourceOne0");
	        var mixedJrnyDestinationOne0 = document.getElementById("returnMixedJrnyDestinationOne0");
	       	var mixedJrnyJourneyDateOne0 = document.getElementById("returnMixedJrnyJourneyDateOne0");
	       	
	       	if(null==mixedJrnySourceOne0){
	       		alert("Please Break Return Journey");
	       		return false;
	       	}
	        
	        if (mixedJrnySourceOne0.value == "") {
	            alert("Please Enter Return Journey Source Station");
	            return false;
	        }
	
			if (mixedJrnyDestinationOne0.value == "") {
	            alert("Please Enter Return Journey Destination Station");
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        
	        if (mixedJrnySourceOne0.value == mixedJrnyDestinationOne0.value) {
	            alert("For Return Journey, Source And Destination Station Cannot Be Same");
	            mixedJrnyDestinationOne0.value="";
	            mixedJrnyDestinationOne0.focus();
	            return false;
	        }
	        if (mixedJrnyJourneyDateOne0.value == "dd/mm/yyyy" || mixedJrnyJourneyDateOne0.value=="") {
                alert("Please Enter Return Journey  Date");
                mixedJrnyJourneyDateOne0.focus();
                return false;
            }
            
            // Validating via request for leg one
            
           	var viaRuteOne=$("#returnViaRuteOne").val();
	    	if(viaRuteOne=='YES')
	    	{
	    		var frmStationOne=document.getElementsByName("returnFrmStationOne");
	    		var toStationOne=document.getElementsByName("returnToStationOne");
	    		var journeyDateOne=document.getElementsByName("return_JourneyDateOne");
		     	
		     	if(frmStationOne!=null && toStationOne!=null && journeyDateOne!=null)
		     	{
		     		for(var i=0;i<frmStationOne.length;i++)
		     		{
		     			if (frmStationOne[i].value == "") {
				            alert("Please Enter Return Journey From Station");
				            frmStationOne[i].focus();
				            return false;
				        }
				
						if (toStationOne[i].value == "") {
				            alert("Please Enter Return Journey To Station");
				            toStationOne[i].focus();
				            return false;
				        }
				        
				        if (frmStationOne[i].value == toStationOne[i].value) {
				            alert("For Return Journey, From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateOne[i].value == "dd/mm/yyyy" || journeyDateOne[i].value=="") {
			                alert("Please Enter Journey Date");
			                journeyDateOne[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}// end via root
            
            
            var mixedJrnySource = document.getElementById("returnMixedJrnySourceAir");
	        var mixedJrnyDestination1 = document.getElementById("returnMixedJrnyDestination1");
	       	var mixedJrnyJourneyDate1 = document.getElementById("returnMixedJrnyJourneyDate1");
	        
	        if (mixedJrnySource.value == "") {
	            alert("Please Enter Return Journey Origin Airport");
	            mixedJrnySource.focus();
	            return false;
	        }
	
			if (mixedJrnyDestination1.value == "") {
	            alert("Please Enter Return Journey Destination Station");
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnySource.value == mixedJrnyDestination1.value) {
	            alert("For Return Journey, Origin And Destination Airport Cannot Be Same");
	            mixedJrnyDestination1.value="";
	            mixedJrnyDestination1.focus();
	            return false;
	        }
	        
	        if (mixedJrnyJourneyDate1.value == "dd/mm/yyyy" || mixedJrnyJourneyDate1.value=="") {
                alert("Please Enter Return Journey Date");
                mixedJrnyJourneyDate1.focus();
                return false;
            }
           
            //alert("days_between("+mixedJrnyJourneyDate1.value+", "+mixedJrnyJourneyDateOne0.value+")-"+days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value));
            if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateOne0.value) > 0) 
            {
	            alert("Air Leg Journey Date must Be Greater Than Rail Leg Journey Date");
	            return false;
	        }
	        
	         if(days_between(mixedJrnyJourneyDateOne0.value, mixedJrnyJourneyDate1.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDate1.focus();
	            return false;
	        }
	            
	    	
	    	//Rail Leg
	    	
	    	var mixedJrnySourceTwo2 = document.getElementById("returnMixedJrnySourceOne1");
	        var mixedJrnyDestinationTwo2 = document.getElementById("returnMixedJrnyDestinationTwo2");
	       	var mixedJrnyJourneyDateTwo2 = document.getElementById("returnMixedJrnyJourneyDateTwo2");
	        
	        if (mixedJrnySourceTwo2.value == "") {
	            alert("Please Enter Return Journey Source Station");
	            return false;
	        }
	
			if (mixedJrnyDestinationTwo2.value == "") {
	            alert("Please Enter Return Journey Destination Station");
	            return false;
	        }
	        
	        if (mixedJrnySourceTwo2.value == mixedJrnyDestinationTwo2.value) {
	            alert("For Return Journey, Source And Destination Station Cannot Be Same");
	            return false;
	        }
	        if (mixedJrnyJourneyDateTwo2.value == "dd/mm/yyyy" || mixedJrnyJourneyDateTwo2.value=="") {
                alert("Please Enter Return Journey Date");
                mixedJrnyJourneyDateTwo2.focus();
                return false;
            }
            
            // Validating via request for leg one
            
           	var viaRuteTwo=$("#returnViaRuteTwo").val();
	    	
	    	if(viaRuteTwo=='YES')
	    	{
	    		var frmStationTwo=document.getElementsByName("returnFrmStationTwo");
	    		var toStationTwo=document.getElementsByName("returnToStationTwo");
	    		var journeyDateTwo=document.getElementsByName("return_JourneyDateTwo");
		     	
		     	if(frmStationTwo!=null && toStationTwo!=null && journeyDateTwo!=null)
		     	{
		     		for(var i=0;i<frmStationTwo.length;i++)
		     		{
		     			if (frmStationTwo[i].value == "") {
				            alert("Please Enter Return Journey From Station");
				            frmStationTwo[i].focus();
				            return false;
				        }
				
						if (toStationTwo[i].value == "") {
				            alert("Please Enter Return Journey To Station");
				            toStationTwo[i].focus();
				            return false;
				        }
				        
				        if (frmStationTwo[i].value == toStationTwo[i].value) {
				            alert("For Return Journey, From And To Stations Cannot Be Same");
				            mixedJrnyDestinationOne0.value="";
				            mixedJrnyDestinationOne0.focus();
				            return false;
				        }
				        if (journeyDateTwo[i].value == "dd/mm/yyyy" || journeyDateTwo[i].value=="") {
			                alert("Please Enter Return Journey Date");
			                journeyDateTwo[i].focus();
			                return false;
			            }
			            
		     		}
		     	}   
	    	}// end via root
            
            if(days_between(mixedJrnyJourneyDateTwo2.value, mixedJrnyJourneyDate1.value) > 0) 
            {
	            alert("Rail Leg Journey Date must Be Greater Than Air Leg Journey Date");
	            return false;
	        }
	        
	         if(days_between(mixedJrnyJourneyDate1.value, mixedJrnyJourneyDateTwo2.value) > 3){
	        	alert("The difference between two connecting modes of journey cannot be more than 03 days.");
	        	mixedJrnyJourneyDateTwo2.focus();
	            return false;
	        }
	    	
	    	
	    }else {
        	return false;
    	}
    	
    }
   
   
    if(requestType=='international'){	
	    	 if (check == true) {
	    	 	var airViaCount = $('#returnAirMixedLTCViaRouteTable tr').length;
				var connectRoute="";
				if(airViaCount > 0){
					for(i = 0; i < airViaCount; i++){
						var viaString=$("#returnMixedLtcViaDestination"+i).val();
						var viaStationCode=viaString.substring(viaString.lastIndexOf("(")+1, viaString.lastIndexOf(")"));
			 		  if(viaStationCode== ""){
			 		  	alert("Please Enter Air Via Route for Return Journey.");
			 		  	return false;
			 		  }
			 		  else{
			 		  	  if(i==0){
			 		  	  	connectRoute=connectRoute+viaStationCode;
			 		  	  }else{
			 		  	  	connectRoute=connectRoute+"#"+viaStationCode;
			 		  	  }
			 		  }
			 	    }
			 	    
				}
				$("#returnAirViaLegCount").val(airViaCount);
	    	 }
	    }
   
	if (requestType != 'offlinebooking' && check == true)
	{
		var selectedFlightKey = $("#returnFlightKey").val();
		var selectedFlightCarrier = $("#returnFlightCode").val();
			
		if(selectedFlightKey == '' || selectedFlightCarrier == '')
		{
			alert("Please Search And Select flight for Return Journey.");
			return false;
		}
	}    
	


    if (check == true && requestType == "ramBaanBooking") {
        if (form.reason.value == "" || form.reason.value == "Enter Reason for Flexi Booking") {
            alert("Please Enter The reason For Flexi Booking");
            return false;
        }
    }

 	var relationStr = document.getElementById("returnRelation").value;
    var relArr = relationStr.split(",");
    var ischeckCount = 0;
    
	if(check == true)
	{
		if (document.getElementById("remFrmListCheck").value == "true")
	    {
	        for (i = 0; i < relArr.length - 1; i++) 
	        {
	            var isChecked = document.getElementById("returnRemoveFrmList" + i).checked;
	            if (isChecked)
	                ischeckCount = ischeckCount + 1;
	        }
	    }
	}else{
		return false;
	}

    //alert("relationStr-->"+relationStr+"|reqstCheck-"+reqstCheck+"|ischeckCount-"+ischeckCount);
    
    if (check == true && reqstCheck == 'true') 
    {
        var isReqsApprv = document.getElementsByName("returnReqsAproved");
        for (i = 0; i < isReqsApprv.length; i++) {
            if (isReqsApprv[i].checked == false) {
                alert("Please Approve The Requisite First");
                return false;
            }
        }
    }
    if (check == true) 
    {
        if (ischeckCount == 0 && document.getElementById("remFrmListCheck").value == "true" && $("#TRRule").val() != 'TR100275' && $("#TRRule").val() != 'TR100276') {
            alert("One passenger must be selected");
            return false;
        }
        
        if (check == true) {
			if(getReturnRequestTravellerCount()==false)
				return false;
	 	}
	 	
	 }

    var totalPassInReq = 0;
    var totalIdProofInReq = 0;

    /*---------For Tatkal Request Start--------*/
    var isTatkalFlagObj = document.getElementById("returnIsTatkalFlagMixed");
    var isTatkalFlagValue = 0;
    if (isTatkalFlagObj != null)
        isTatkalFlagValue = isTatkalFlagObj.value;
	
	var validIdCheckCount = 0;
    
    if (isTatkalFlagValue == 1) 
    {
        if (document.getElementById("remFrmListCheck").value == "true") 
        {
            for (i = 0; i < relArr.length - 1; i++) 
            {
                var isChecked = document.getElementById("returnValidIdCardPassCheck" + i).checked;
                if (isChecked)
                    validIdCheckCount = validIdCheckCount + 1;
            }
        }
      
    }

    totalPassInReq = totalPassInReq + ischeckCount;
    totalIdProofInReq = totalIdProofInReq + validIdCheckCount;

    //alert("After Relataive::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);
    /*---------For Tatkal Request End--------*/

    var validIdCardAttCheckCount = 0;

    if (check == true) 
    {

        if ($("#isAttendentBooking").val() == 'Yes') 
        {

            var attendentCount = $("#returnFinalAttendentCount").val();
            for (i = 1; i <= attendentCount; i++) {

                if (document.getElementById("returnAttName" + i).value == '') {
                    alert("Please Enter Attendant Name");
                    document.getElementById("returnAttName" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttErsName" + i).value == '') {
                    alert("Please Enter Attendant ERS Print Name");
                    document.getElementById("returnAttErsName" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttGender" + i).value == '') {
                    alert("Please Select Attendent Gender");
                    document.getElementById("returnAttGender" + i).focus();
                    return false;
                }

                if (document.getElementById("returnAttDob" + i).value == '') {
                    alert("Please Select Attendent Date of Birth");
                    document.getElementById("returnAttDob" + i).focus();
                    return false;
                }

                if (!isGreaterThanTodayDate(document.getElementById("returnAttDob" + i))) {
                    alert("Please Select Valid Date of Birth");
                    document.getElementById("returnAttDob" + i).focus();
                    return false;
                }

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("returnValidIdCardAttCheck" + i).checked;
                    if (isChecked)
                        validIdCardAttCheckCount = validIdCardAttCheckCount + 1;
                }


            } //End of attendent loop

            totalPassInReq = parseInt(totalPassInReq) + parseInt(attendentCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardAttCheckCount);

        }//End of attendent check.

        } else {
            return false;
        }

    //alert("After Relataive+Attantent::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);


    /* Start Validation For Party Booking */

    var validIdCardPartyCheckCount = 0;

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

                if (isTatkalFlagValue == 1) {
                    var isChecked = document.getElementById("returnValidIdCardPartyGroupCheck" + i).checked;
                    if (isChecked)
                        validIdCardPartyCheckCount = validIdCardPartyCheckCount + 1;
                }

            } //End of Party Booking loop


            totalPassInReq = parseInt(totalPassInReq) + parseInt(partyCount);
            totalIdProofInReq = parseInt(totalIdProofInReq) + parseInt(validIdCardPartyCheckCount);

        }

    }  //End of Party Booking check.

    //alert("After Relataive+Attantent+PartyBooking::Total Pass="+totalPassInReq+" And Total valid Id Proof="+totalIdProofInReq);

    //   After All Pass Checking Count Of Id Proff


    if (isTatkalFlagValue == 1) 
    {

        var reminder = totalPassInReq % 4;
        var howManyIdReq = 0;
        if (reminder == 0) {
            howManyIdReq = totalPassInReq / 4;
        } else {
            howManyIdReq = parseInt((totalPassInReq / 4)) + 1;
        }


        //alert("required id proof="+howManyIdReq+"||validIdCheckCount = "+totalIdProofInReq+"||Passanger Count "+totalPassInReq);

        if (parseInt(totalIdProofInReq) < parseInt(howManyIdReq)) {
            alert("Minimum " + howManyIdReq + " Valid Id Proof Is Required for This Tatkal Request");
            return false;
        }
        if (parseInt(totalIdProofInReq) > parseInt(howManyIdReq)) {
            //alert("Only "+howManyIdReq+" Valid Id Proof Is Required for This Tatkal Request");
            //return false;
        }
    }
	
    //alert("Just Before Saving The Request..:"+check);
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
		      url: $("#context_path").val()+"mb/getTravelerJournayDate",
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
		      url: $("#context_path").val()+"mb/getTravelerJournayDate",
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

function validateAdvancedAmt() {
    var advanceAmt = $("#advanceAmt").val();
    var category_id = $("#category_id").val();
    var service_id = $("#service_id").val();
    var rank = $("#rank").val();
    
    var noOfDays = $("#noOfDays").val();
    var trRuleID=$("#TRRule").val();
    var hotelAllowForTR=$("input[name=reqForHotelDA]:checked").val();
    var conveyanceAllowForTR=$("input[name=reqForConveyanceDA]:checked").val();
    var foodAllowForTR=$("input[name=reqForFoodDA]:checked").val();
   
   
    var flag = false;
   var regexn = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
   
    
    if(advanceAmt=="")
    {
    	alert("Please Enter The DA Advance Amount");
    	$("#advanceAmt").focus();
    	return false;
    	
    }
    
    if(!regexn.test(advanceAmt))
    {
    	alert("Please Enter Only Numeric Characters.");
    }
 
    $.ajax({
	    url: $("#context_path").val()+"mb/calculateDAAmount",
        type: "get",
        async: false,
        data: "category_id=" + category_id + "&service_id=" + service_id + "&rank=" + rank  + "&noOfDays=" + noOfDays 
              + "&trRuleID="+trRuleID +"&hotelAllowForTR="+hotelAllowForTR
              +"&conveyanceAllowForTR="+conveyanceAllowForTR+"&foodAllowForTR="+foodAllowForTR,
        dataType: "json",
        success: function(msg) {
           
               var totalAmt = msg.totalAmt;
               var infoMsg = msg.msg;
               // alert("totalAmt = " + totalAmt +"| advanceAmt-"+advanceAmt );
           
            if(parseFloat(totalAmt)==-1){
               alert(infoMsg);
               return false;
            }else  if (parseFloat(advanceAmt) > parseFloat(totalAmt)) {
                alert("DA Advance Amount should not be greater than Rs. "+totalAmt);
                flag = false;
            } else
                flag = true;
        }
    });

    return flag;

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
