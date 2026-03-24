$(document).ready(function() {


	$("#alternateService").change(function() {
		getCategories($(this));
	});

	$('#categoryId').on('change', function() {
		getLevel($(this));
		setPaoOffice();
	});

	$("#dob").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d-m-Y',
		formatDate:'Y-m-d',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#dob").val("");
		}
	 
	});

	$("#dateOfRetirement").datetimepicker({
	lang:'en',
		timepicker:false,
		format:'Y-m-d',
		formatDate:'Y-m-d',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#dateOfRetirement").val("");
		}
	 
	});

	$("#dateOfCom_join").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d-m-Y',
		formatDate:'Y-m-d',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#dateOfCom_join").val("");
		}
	 
	});

});

function getCategories(obj) {
	$('#categoryId').find('option').not(':first').remove();
	$('#levelId').find('option').not(':first').remove();
	let serviceId = $(obj).val();

	if (serviceId != "") {
		$.ajax({
			type: "post",
			url: $("#context_path").val()+"mb/ChangePersonalCategoryBasedOnService",
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

	$('#levelId').find('option').not(':first').remove();

	if (categoryId != "") {
			$.ajax({
			type: "post",
			url: $("#context_path").val()+"mb/getChangePersonalLevel",
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
		url: $("#context_path").val()+"mb/getChangePersonalPersonalNoPrefix",
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
	let strVal = $(obj).find(":selected").val();
	let rankId = strVal.split(":")[1];
	$("#rankId").val(rankId);
	$("#level").val(strVal.split(":")[0]);

	$('#rank').empty();

	$.ajax({
		type: "post",
		url: $("#context_path").val()+"mb/getChangePersonalGradePayRank",
		data: "rankId=" + rankId,
		datatype: "application/json",
		success: function(data) {
			$('#rank').append(data);
		}
	});
}

function setPaoOffice(){

     var categoryId=$('#categoryId :selected').val();
	 var categoryName=$('#categoryId :selected').text();
	
	 var serviceId=$('#serviceId :selected').val();
	 var serviceName=$('#serviceId :selected').text();
	
	 var alternateServiceId=$('#alternateService :selected').val();
	 var alternateServiceName=$('#alternateService :selected').text();
	
	 var paoOffId=$('#paoOffId');
	 var airOffId=$('#airOffId');
	 
	 if(alternateServiceName!="select"){
	 	serviceId=alternateServiceId;
	 	serviceName=alternateServiceName.toUpperCase();
	 }
	 	
	
	 var rail_options="";var air_options="";
	

	 var tempChkForCategory=categoryName.toUpperCase();

        //alert("ServiceId="+serviceId+"||serviceName="+serviceName+"alternateServiceId="+alternateServiceId+"||alternateServiceName="+alternateServiceName+"categoryId="+categoryId+"||categoryName="+categoryName+"|tempChkForCategory-"+tempChkForCategory); 
	 
	 if (serviceName.indexOf('AIRFORCE') > -1 || serviceName.indexOf('AIR FORCE') > -1) 
	 {
	   	$("#DoIINo").html('POR NO');
	  	$("#DoIIDate").html('POR Date');
	 }
	 if (serviceName.indexOf('NAVY')>-1) 
	 {
	 	$("#DoIINo").html('GX NO');
	  	$("#DoIIDate").html('GX Date');
	 }
 
	 if(tempChkForCategory=='OFFICER' || tempChkForCategory=='NCC WTLO')
	 {
		$("#isDateOfCommission").html('Date of Commissioning <span class="mandatory">*</span>');
		
		if(serviceName.indexOf('MNS')>-1){
			$("#isFormD").html('Form G Used<span class="mandatory">*</span>');
		}else{
		$("#isFormD").html('Form D Used <span class="mandatory">*</span>');
		}
		
	 
		if (serviceName.indexOf('ARMY')>-1 || serviceName.indexOf('MNS')>-1) {
			$("#cdaAccNo").show(); 
		}
		else{
			$("#cdaAccNo").hide(); 
		}
	 }else{
		$("#isDateOfCommission").html('Date of Enrollment <span class="mandatory">*</span>');
		$("#isFormD").html('Concession Voucher Used <span class="mandatory">*</span>');
		$("#cdaAccNo").hide(); 
	 }
	
	//Check for Airforce	 

	if (categoryId != "") {
			$.ajax({
	      	url: $("#context_path").val()+"mb/getPAOPno",
		    type: "POST",
	      	data: "serviceId="+serviceId+"&categoryId="+categoryId,
	      	dataType: "text", 
  		  	success: function(data){
  		  		var obj = JSON.parse(data);

				var railCount;
				var RailVisitorGroup;
				var airCount;
				var AirVisitorGroup;

  		  		$.each(obj, function(i, v) {
					if (i == 0) {
						railCount = $(v).length;
						RailVisitorGroup = $(v);
					}
					if (i == 1) {
						airCount = $(v).length;
						AirVisitorGroup = $(v);
					}
				});

  				if(railCount != 1){
					rail_options += '<option value="-1">select<\/option>';
  				}

 				$(RailVisitorGroup).each(function(index) {
					var id = RailVisitorGroup[index].acuntoficeId;
					var name = RailVisitorGroup[index].name;

					rail_options += '<option value="' + id + '">' +name + '<\/option>';
		 		});

		 		if(airCount != 1){
		 			air_options += '<option value="-1">select<\/option>';
		 		}

		 		$(AirVisitorGroup).each(function(index){
					var id = AirVisitorGroup[index].acuntoficeId;
					var name = AirVisitorGroup[index].name;

						air_options += '<option value="' + id + '">' +name + '<\/option>';
		 		});

		 		$("#payAcOff").html(rail_options);
		  		$("#airAcOff").html(air_options);
			}//END SUCCESS
	    });
	}

}
    

function setPrintERSName() {
	var fName = $("#fName").val();
	var mName = $("#mName").val();
	var lname = $("#lName").val();

	var name = "";

	var flag = false;
	if (fName.length > 0) {
		name = name + fName;
		flag = true;
	}

	if (mName.length > 0) {
		if (flag) {
			name = name + " " + mName;
		} else {
			name = name + mName;
			flag = true;
		}
	}

	if (lname.length > 0) {
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
}





var httpRes;
var isCallForCategoryId=false;
var isCallForLevelId=false;
var isCallForGradePay=false;


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
