$(document).ready(function() {

	$("#dob").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd: 2100,
		onShow: function () {
			  $("#dob").val("");
		}
	 
	});

	$("#dateOfRetirement").datetimepicker({
	lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd: 2100,
		onShow: function () {
			  $("#dateOfRetirement").val("");
		}
	 
	});

	$("#dateOfCom_join").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd: 2100,
		onShow: function () {
			  $("#dateOfCom_join").val("");
		},
		onSelectDate:function(ct){
			
			var currentYear = (new Date).getFullYear();
		
			if (ct.getFullYear() == currentYear) {
				$("#isLtcAvailPreviousYearY").prop("checked", true);
				$("#ltcAvailabilityPre").hide();
				$("#ltcAvailabilityTd").hide();
			}else if (ct.getFullYear() > currentYear) {
				$("#isLtcAvailPreviousYearY").prop("checked", true);
				$("#ltcAvailabilityPre").hide();
				$("#ltcAvailabilityTd").hide();
				$("#isLtcAvailCurrentYearY").prop("checked", true);
				$("#ltcAvailabilityCurr").hide();
				$("#ltcAvailabilityCurrTd").hide();
				
			}else{
				$("#isLtcAvailCurrentYearN").prop("checked", true);
				$("#ltcAvailabilityCurr").show();
				$("#ltcAvailabilityCurrTd").show();
				$("#isLtcAvailPreviousYearN").prop("checked", true);
				$("#ltcAvailabilityPre").show();
				$("#ltcAvailabilityTd").show();
			}
				
		}
	 
	});
	if ($("#locationName").val() == 'Peace') {
		chkForPeaceLoc(true);
	} else {
		chkForPeaceLoc(false);
	}

	$("#alternateService").change(function() {
		getCategoryIdOnTraveller();
		setDoIINo();
	});

	$('#categoryId').on('change', function() {
		getLevelDetails();
		setPaoOffice();
	});

//	calendar1 = new Epoch('cal1','popup',document.getElementById('calendar1_container'),false);
//	new Epoch('cal2','popup',document.getElementById('dob'),false);
//	new Epoch('cal2','popup',document.getElementById('dateOfCom_join'),false);
//	new Epoch('cal2','popup',document.getElementById('dateOfRetirement'),false);


  $("#mobNo").keyup(function() {
	  
        var mob = this.value;
      
        
        var iChars = "0123456789";
	   if (mob != "") {

		for (var i = 0; i < mob.length; i++) {
			if (iChars.indexOf(mob.charAt(i)) == -1) {
				alert("Please Enter Digits Only");
				this.value="";
				return false;
			}

		}
	}
    });
    
    
    
    //check for block no in case of pbor
		// ******************************* \\
		
		

	 	
 		
 		
 	
 		
		 $('#payAcOff').change(function()
		 {
		var serviceName=$('#alternateService option:selected').text();
        var categoryName=$('#categoryId option:selected').text();
		 
		 	 $('#persnBlock').val('true');
			 var payAcOff=$('#payAcOff').val();
			 
			 if(serviceName == 'Army')
			 {
				if(categoryName == 'PBOR')
				{
					 var persnlNo= $("#personalNo").val();
					
					 var response="";
		     		$.ajax({
		      					url: $("#context_path").val() + "mb/validateBlockOfPersnlNo",
		      					type: "post",
		      					data: "paoCode="+payAcOff+"&persnlNo="+persnlNo,
		      					dataType: "text",
		      					success: function(msg){
		                			if(msg=="false"){
		                				alert("Personal No doesn't exists between specified block of Personal No. Please recheck either personal number or Pay Account Office");
		                				$("#persnBlock").val(msg);
		                			}
				          		}
				          	});
					 }
				}
		 });

	$("#saveArmyTravelerForm").submit(function(event) {
		event.preventDefault();
		validateTravellerProfile(event);
	});

});

function getCategories(obj) {
	$('#categoryId').find('option').not(':first').remove();
	$('#levelId').find('option').not(':first').remove();
	let serviceId = $(obj).val();

	if (serviceId != "") {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/travelerCategoryBasedOnService",
			data: "serviceId=" + serviceId,
			datatype: "application/json",
			success: function(data) {
	
				$.each(data, function(key, value) {
					$('#categoryId').append(new Option(value, key));
				});
	
			}
		});
	}
}

function getLevel(obj) {
	let serviceId = $("#alternateService").val();
	let categoryId = $(obj).val();

	$("#rank").empty();
	$("#rankId").val("");
	$('#levelId').find('option').not(':first').remove();

	if (categoryId != "") {
			$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getTravelerLevel",
			data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
			datatype: "application/json",
	
			success: function(data) {
	
				$.each(data, function(key, value) {
					$('#levelId').append(new Option(value[0], key + ":" + value[1]));
				});
	
			}
		});
	}

	$('#alphaNoId').find('option').not(':first').remove();
	$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getTravelerPersonalNoPrefix",
		data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
		datatype: "application/json",

		success: function(data) {

			$.each(data, function(key, value) {
				$('#alphaNoId').append(new Option(value, value));
			});

		}
	});
}

function setRankLevel(obj) {
	if ($(obj).val() != "-1") {
		let serviceId = $("#alternateService").val();

		let strVal = $(obj).find(":selected").val();

		let rankId = strVal.split(":")[1];
		$("#rankId").val(rankId);

		let levelId = strVal.split(":")[0];
		$("#level").val(levelId);
	
		$('#rank').empty();
	
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getTravelerGradePayRank",
			data: "rankId=" + rankId,
			datatype: "application/json",
			success: function(data) {
				$('#rank').append(data);
			}
		});

		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getRetirementAge",
			data: "serviceId=" + serviceId + "&levelId=" + levelId+"&rankId="+rankId,
			datatype: "application/json",
			success: function(data) {
				$('#retireAge').val(data);
			}
		});
	} else {
		$("#rankId").val("");
		$('#rank').empty();
		$("#level").val("");
		$('#retireAge').val("");
	}
}

function setPrintERSName() {
	var fName = $("#fName").val();
	var mName = $("#mName").val();
	var lname = $("#lName").val();

	var name = "";

	var flag = false;
	if (fName.trim().length > 0) {
		name = name + fName;
		flag = true;
	}

	if (mName.trim().length > 0) {
		if (flag) {
			name = name + " " + mName;
		} else {
			name = name + mName;
			flag = true;
		}
	}

	if (lname.trim().length > 0) {
		if (flag) {
			name = name + " " + lname;
		} else {
			name = name + lname;
			flag = true;
		}
	}

	if (name.length > 15) {
		name = name.substring(0, 15);
	}
	$("#ersPrntName").val(name);
	
	if (lname.trim().length == 0 && mName.trim().length>0){
	$("#nameOnBoardingPass").val(fName+" "+mName+" "+fName);
	}else if(lname.trim().length == 0 && mName.trim().length == 0){
	$("#nameOnBoardingPass").val(fName+" "+fName);	
	}else if(lname.trim().length>0 && mName.trim().length == 0 && fName.trim().length > 0){
	$("#nameOnBoardingPass").val(lname+" "+fName);	
	}
	else if(lname.trim().length == 1 && mName.trim().length == 0){
		$("#nameOnBoardingPass").val(fName+" "+lname+" "+fName);
	}else if(lname.trim().length == 1 && mName.trim().length>0){
		$("#nameOnBoardingPass").val(fName+" "+lname+" "+mName+" "+fName);
	}
	else{
	$("#nameOnBoardingPass").val(lname+" "+mName+" "+fName);
	}

}





var httpRes;
var isCallForCategoryId=false;
var isCallForLevelId=false;
var isCallForGradePay=false;

function getCategoryIdOnTraveller() {
	isCallForCategoryId=true;
	isCallForGradePay=false;
	isCallForLevelId=false;
	
	$("#airForceCadetNo").val("");
	$("#cadetNo").val("");
	$("#cadetChkAlpha").val("");
	$("#courseSerialNo").val("");
	
	if($("#cdaAccNo")!=null || $("#cdaAccNo")!=undefined){
		$("#cdaAccNo").hide();
	}

	var serviceName = $("#alternateService");
	var chkForService = $(serviceName).find(":selected").text();
	var alphaNoId=document.getElementById("alphaNoId");

	if(chkForService.indexOf('COAST GUARD') > -1) 
	{
		// disble alpho no
		var alphaNoId=document.getElementById("alphaNoId");
    	if(alphaNoId!=null || alphaNoId!=undefined){
			alphaNoId.value='';
			alphaNoId.style.display='none';
		}
		// disble cv/formD column
		if(document.getElementById("cv_fd_used")!=null || document.getElementById("cv_fd_used")!=undefined){
			document.getElementById("cv_fd_used").value=0;
			document.getElementById("cv_fd_used").style.display='none';
		}
	}else
	{	
		var alphaNoId=document.getElementById("alphaNoId");
    	if(alphaNoId!=null || alphaNoId!=undefined){
			alphaNoId.value='';
			alphaNoId.style.display='';
		}
	
		if(document.getElementById("cv_fd_used")!=null || document.getElementById("cv_fd_used")!=undefined){
			document.getElementById("cv_fd_used").value='';
			document.getElementById("cv_fd_used").style.display='';
		}
	}

	var serviceId = $("#alternateService").find(":selected").val();
	var loginUnitSerName='';
	if(document.getElementById("serviceNameId")!=null || document.getElementById("serviceNameId")!=undefined){
			loginUnitSerName=document.getElementById("serviceNameId").value;	
		}


//	getHttpURLResponse(url);
	getCategories($("#alternateService"));
	return true;
}

function getHttpURLResponse(url){
 	if (window.XMLHttpRequest) {
        httpRes = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        httpRes = new ActiveXObject("Microsoft.XMLHTTP");
    }
 
    httpRes.onreadystatechange=getURLResponse;
    httpRes.open("GET", url, true);
    httpRes.onreadystatechange=getURLResponse;
 	httpRes.send(null);
}

function getURLResponse()
{	
	if (httpRes.readyState == 4) 
	{
	    if (httpRes.status == 200) 
	    {
	    	if(isCallForCategoryId){
	    		getCategoryResponse(httpRes);
	    	}
	    	if(isCallForLevelId){
	    			getLevelResponse(httpRes);
	    	}
	    	 if(isCallForGradePay){
	    			getGradePayResponse(httpRes);
	    	}
	    	
	    
		}
	}
}

function getGradePayResponse(httpRes){
	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	var retirementAge=$(doc).find("RETIREMENT_AGE").text();
	var rankId=$(doc).find("RANK_ID").text();
	var rankName=$(doc).find("RANK_NAME").text();
	$("#rankNameInner").html(rankName);
	$("#rankId").val(rankId);
	if(parseInt(retirementAge) > 0){
	$("#retireAge").val(retirementAge);
	}
}

function getLevelResponse(httpRes){
	
	$("#rankNameInner").html("");
	$("#rankId").val("");
	$("#retireAge").val("");
	
	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	var doc=getXML(respText);
	
	var levels = $("#levelId");
	levels.children('option:not(:first)').remove();
    $(doc).find("LEVELS").each(function(){
 	   var levelOption = document.createElement("option");
 		levelOption.value = $(this).find("LEVEL_ID").text();
 		levelOption.text = $(this).find("LEVEL_NAME").text();
 	    levels.append(levelOption);
    });
	
	
	/* Code Block To Set Personal Number Prefix Start */
	
	var alphaNoId=$("#alphaNoId");
	alphaNoId.children('option:not(:first)').remove();
	$(doc).find("PREFIX_DTLS").each(function(){
 	   var prefixOption = document.createElement("option");
 		prefixOption.value = $(this).find("PREFIX").text();
 		prefixOption.text = $(this).find("PREFIX").text();
 	    alphaNoId.append(prefixOption);
    });
    /* Code Block To Set Personal Number Prefix End */
    
    /* Code to show pao office on the basis of service and category*/
    
    if(typeOfUser=='traveller')
    {
    	setPaoOffice();
    }
}

function getCategoryResponse(httpRes)
{
	$("#rankNameInner").html("");
	$("#rankId").val("");
	$("#retireAge").val("");
	
	var respText=httpRes.responseText;	
	respText = respText.replace(/&/g,"and"); 
	
	var doc=getXML(respText);
	
	var categoryDoc =doc.documentElement;	
	if(typeOfUser=='traveller'){
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');}
	else{
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');
	}
	if(typeOfUser=='allTypeUser'){ 
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');}
	else{
		var categoryIdTd=document.getElementById("categoryIdTd");
		var catIdDoc=categoryDoc.getElementsByTagName('categoryId');
	}
	
	var catNameDoc=categoryDoc.getElementsByTagName('categoryName');
	
		
	var categoryStr="";
	categoryStr+='<select name=\"category\" class=\"combo\" id=\"categoryId\" onchange=\"getLevelDetails();\" style=\"width: 80%;\">';
	categoryStr+='<option value=\"-1\">Select</option>';
	for(var i=0;i < catNameDoc.length ; i++){
		if(catNameDoc[i].firstChild!=null && catIdDoc[i].firstChild!=null)
		categoryStr+='<option value=\"'+catIdDoc[i].firstChild.nodeValue+'\">'+catNameDoc[i].firstChild.nodeValue+'</option>';
	}
	categoryStr+='</select>';	
	categoryIdTd.innerHTML=categoryStr;
	if(typeOfUser=='traveller'){
	setDoIINo();
	}
	
	$("#levelId").children('option:not(:first)').remove();
	
}

function getLevelDetails(){

//	var service      = document.getElementById("alternateService");
    var serviceName  = $("#alternateService").find(":selected").text();
//    var categoryId   = document.getElementById("categoryId");
    var categoryName = $("#categoryId").find(":selected").text();
 
    /* TES CADET CODE */
   
     if((serviceName.toUpperCase() =='ARMY')  && (categoryName.toUpperCase().trim() == 'TES CADET'||categoryName.toUpperCase().trim() == 'NURSING STUDENT'))
    {
		
       $("#chkAlpha").prop('readonly', true);
  
    }
     /* TES CADET CODE END  */
     
    if($('#femaleHide').length){
	    if((serviceName.toUpperCase()=='ARMY')  && (categoryName.toUpperCase() == 'PBOR')) {
	      document.getElementById("male").checked = true;
	      document.getElementById("femaleHide").style.display= '';
	    }
	    else{
	        document.getElementById("femaleHide").style.display= '';
	    }
    }
	isCallForCategoryId=false;
    isCallForLevelId=true;
    isCallForGradePay=false;
	
	

	var transferFlag=true;
	var formObj=$("#transferInFormId").val();
	if(formObj){
		transferFlag=false;
	}
	
	if($("#validatePersonalNo") != null){
		$("#validatePersonalNo").val('false');
	}
	
	if(transferFlag && $("#personalNo") != null){
		$("#personalNo").val('');
	}
	if($("#chkAlpha") != null){
		$("#chkAlpha").val('');
	}

	$("#airForceCadetNo").val("");
	$("#cadetNo").val("");
	$("#cadetChkAlpha").val("");
	$("#courseSerialNo").val("");
	
	if(checkNavyAirforceCadets()){
		showNavyAirforceCadit();
	}else{
		showOldPersonalNumber();
	}
	
//	var categoryName=document.getElementById("categoryId");
//	var alternateServ=document.getElementById("alternateService");
//	var categoryId=categoryName.options[categoryName.options.selectedIndex].value;
	
	getLevel($('#categoryId'));

	return true;
}
