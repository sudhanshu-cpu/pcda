var attNo=0;
var partyDependentNo=0;

function getBrowserInfo()
{
	var useragent = navigator.userAgent;
	var bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}


function testForAlphaNumericKeyForTravelRequest(e){
	var myBrow=getBrowserInfo();
	document.getElementById("getTravelerInfoBtn").value=false;
	if(myBrow.indexOf('Netscape')> -1)
	{ 	
		var k;
		if (e.which == 13) {
			return false;
		}
		if ((e.which >=65 && e.which <=90) || (e.which >=97 && e.which <=122)||(e.which >=48 && e.which <=57) || (e.which == 8) || (e.which == 0))
		{
	  		return true;
		}else{
			alert("Please Enter Only Aplha Numeric");
			return false;
		}
	}else{
		return true;	
	}
}


$( document ).ready(function() {
   
   $('#personalNo').bind("cut copy paste",function(e) {
     e.preventDefault();
   });
   
   $('#getVisitor').click(function()
	{ 
		var prsnlNo=$("#personalNo").val().trim();
		var reqType=$('#reqType').val();
		var serviceType=$('#serviceType').val();
		var groupId=$('#groupId').val();
		
		if(prsnlNo==""){
	  	 alert("Please Enter Traveler Personal No.");
	 	 return false;
	    }
			
			$.ajax(
			{
				url: $("#context_path").val()+"mb/getTravellerDetailsBulkBkg",
				type: "post",
		      	data: "personalNo="+prsnlNo+"&reqType="+ reqType +"&serviceType="+serviceType+"&groupId="+groupId,
		      	dataType: "json",
		      
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
					
					if(msg.message !=null || msg.userID=="" || msg.userID==null){
				  			alert(msg.message);
							return false;
					}else if  (msg.office=="" || msg.office==null){
							alert("No Rail Account Office Is Assigned To Traveler,Complete The Traveler Profile First");
							return false;
					}else if  (msg.airOffice=="" || msg.airOffice==null){
							alert("No Air Account Office Is Assigned To Traveler,Complete The Traveler Profile First");
							return false;
					}else if(msg.serviceName=="" || msg.serviceName==null){
							alert("No Service Is Assigned To Traveler,Complete The Traveler Profile First");
							return false;
					}else if(msg.categoryName=="" || msg.categoryName==null){
							alert("No Category Is Assigned To Traveler,Complete The Traveler Profile First");
							return false;
					}
					
					else if (msg.message == 'editMode') {
						
						alert("Personal No You Have Entered Is Under Edit Process");
						window.location.reload();
					return false;
					
					}
					
					$("#category_id").val(msg.categoryId);
					$("#service_id").val(msg.serviceId);
					
					$("#personalNo").attr('readonly','readonly');
					$("#reqType").css("pointer-events", "none");
					setTravelerInformation(msg);
					$("#getVisitor").hide();
					
		      	}
				
				
			});
			
	});
	
});
function getTravelRuleByGroup(value)
{
	var travelType=$('#TravelTypeDD').val();
	var serviceType=$('#serviceType').val();
	var service_id=$("#service_id").val();
	var category_id=$("#category_id").val();
	var requestType=$("#reqType").val();	
	var trDtls="";
	var trdtlsLink="";
	var TRRule=""
	var trRuleNews="";
	var travelReqStatus;
	
	$.ajax({
      
	      url: $("#context_path").val()+"mb/getAllTRforTravelGroupBulkBkg",
	      type: "get",
	      data: "travelType="+travelType+"&enumCode="+value+"&serviceType="+serviceType+"&serviceId="+service_id+"&categoryId="+category_id+"&requestType="+requestType ,
	      dataType: "json",
	      beforeSend: function(){
	        	var calHeight=document.body.offsetHeight+20;
			    $("#screen-freeze").css({"height":calHeight + "px"} );
			    $("#screen-freeze").css("display", "block");
		  },
	 
	  	  complete: function(){
	  		    $("#screen-freeze").css("display", "none");
	    	},
	      success: function(msg)
	      {
    	        var options = '<option value="">Select<\/option>';
				var trvlTRdtls='';
				
				$.each(msg, function(index,obj){
				
				    var id = obj.trRuleId;
					var name = obj.trRuleNumber;
					var trDesc = obj.trRuleDesc;
					var trTitle = obj.trRuleTitle;
					trRuleNews=obj.trRuleNews;
					travelReqStatus=obj.travelReqStatus;
								
					trvlTRdtls+='<input type="hidden" name="'+id+'" id="'+id+'" value="'+travelReqStatus+"::"+trRuleNews+'">';		
				    options += '<option value="' + id + '">' +name + '<\/option>';
					if(index==0){
						TRRule=id
                     }
					});
					$("#trNews").html(trvlTRdtls);
					$("#prevTrRule").val(TRRule);	
					$("#TRRule").html(options);
					isRequestAllowed();
			   
         	}
         });
 
}


function isRequestAllowed(){
   	    
   	    var trRule=$('#TRRule :selected').val();
   	    
   	    if(trRule!=undefined && trRule!=""){
	    	var trRuleDetails=$('#'+trRule).val();
	    	var splt = trRuleDetails.split("::");
	    	var trvlreqsts=splt[0];
	    	var trnews=splt[1];
	   
	    	if(trvlreqsts==0){
	    		$("#getVisitor").show();
		   	}else{
		   		$("#getVisitor").hide();
				alert(trnews);
			}
   	    }	   
	 }

function viewTRRuleDtls(){
	
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	var TRRule=$('#TRRule').val();
    if(TRRule == ''){
    	alert("Please Select Rule");
    	return false;
    }
	ajaxRequestFace = new LightFace.Request(
	{
			width: 800,
			height: 400,
			url: $("#context_path").val()+'mb/getTravelRuleDetailsBulkBkg',
			buttons:[
						{ title: 'Close', event: function() { this.close(); } }
					],
			request:{ 
						data:{ 
							trRuleID: TRRule
						},
						method: 'post'
					},
					title: 'Travel Rule View'
	 });
  	ajaxRequestFace.open();
}

function getTravelGroup() {
  
	var groupCode = "7";
	var name = "Ty Duty";

		if ($("#trGroup").length == 0) {
			var options = '';
			options += '<td class="label" style="border-bottom:1px dotted white">Travel Group<span class="mandatory">*</span></td>';
			options += '<td><select id="trGroup" name="trGroup" onchange="getTravelRuleByGroup(this.value)" class="combo">';
			options += '<option value="">Select</option>';

			options += '<option value="' + groupCode + '">' + name + '<\/option>';
			options += '</select></td>';
			$("#travelGroup").append(options);
		}
	
}
function getTravelId() {
 	
 	 $('#travelID').children('option:not(:first)').remove();
 	 $('#travelID').append($('<option>',{value: "1",text : "New" }));
 		
 }
 

function setTravelerInformation(msg){
	
	  var Category=msg.categoryName; 
	  var LevelName=msg.levelName; 
	  var Service=msg.serviceName; 
	  var travlerName=msg.travlerName; 
	  var Office=msg.office; 
	  var airOffice= msg.airOffice;  
	  var cdaoAccNo= msg.cdaoAccNo; 
	  var reqType=$('#reqType').val(); 
	
	  var travelerInfo='<tr><td class="label" style="border-bottom:1px dotted white">Name</td><td>'+travlerName+'</td><td class="label" style="border-bottom:1px dotted white">Service</td><td>'+Service+'</td></tr>' +
		             '<tr><td class="label" style="border-bottom:1px dotted white">Category</td><td>'+Category+'</td><td class="label" style="border-bottom:1px dotted white">Level</td><td>'+LevelName+'</td></tr>'+
		             '<tr><td class="label" style="border-bottom:1px dotted white">Rail Account Office</td><td>'+Office+'</td><td class="label" style="border-bottom:1px dotted white">Air Account Office</td><td>'+airOffice+'</td></tr>'+
		              '<tr><td class="label" style="border-bottom:1px dotted white">CDAO Account No</td><td>'+cdaoAccNo+'</td><td></td><td></td></tr>'+
				     '<tr><td colspan="5">&#160;</td></tr>';
				     
	  var travelTypeOptions='<select class="combo" style="width:120px;" id="TravelTypeDD" name="TravelTypeDD" onchange="getTravelGroup();getTravelId(this.value);">';
	      travelTypeOptions += '<option value="" selected="selected">Select<\/option>';	
	   
	   $.each(msg.travelType,function(TravelTypeID, TravelTypeName){                
		
		 if(reqType="airBulkBkg"&& TravelTypeID=="100002"){
		 	travelTypeOptions += '<option value="' + TravelTypeID + '">' +TravelTypeName + '<\/option>';
		 }
	 	 
	  });
	  travelTypeOptions += '</select>';
	  
	   travelerInfo += '<tr id="travelGroup"><td class="label" style="border-bottom:1px dotted white">Travel Type<span class="mandatory">*</span></td>';
	   travelerInfo += '<td>'+travelTypeOptions+'</td> </tr>';
	   
	   travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">TR Rule<span class="mandatory">*</span></td>'+
	                   '<td><select class="combo" style="width:120px;" id="TRRule" name="TRRule">'+
					   '<option value="" selected="selected">Select</option>'+
					   '</select>&#160;&#160;&#160;<a href="javascript:void(0);" onclick="viewTRRuleDtls();" style="text-decoration: none;color: #00ff80;">View Rule</a></td>' +
					   '<td class="label" style="border-bottom:1px dotted white">Travel Id<span class="mandatory">*</span></td>'+
					   '<td><select class="combo" style="width:143px;" id="travelID" name="travelID">'+ 
					   '<option value="" selected="selected">Select</option></select>'+					   
					   '</td></tr>';
					   
		travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">Travel Start Date<span class="mandatory">*</span></td>'+
				        '<td><input type="text" name="travelStartDate" id="travelStartDate" readonly="readonly"/></td>'+
                        '<td class="label" style="border-bottom:1px dotted white">Travel End Date<span class="mandatory">*</span></td>'+
   				        '<td><input type="text" name="travelEndDate" id="travelEndDate" readonly="readonly" /></td></tr>';
   				          				        
		travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">Authority No<span class="mandatory">*</span></td>'+
				        '<td><input type="text" name="authorityNo" id="authorityNo" autocomplete="off"/></td>' +
				        '<td class="label" style="border-bottom:1px dotted white">Authority Date<span class="mandatory">*</span></td>' +
				        '<td><input type="text" name="authorityDate" id="authorityDate" readonly="readonly"/></td></tr>';   
				        			        
		travelerInfo += '<tr><td colspan="2" align="left">' +
						'<input type="button" class="butn" id="taggedReqDiv" name="taggedReqDiv" value="Tagged Requests" style="vertical-align:middle;display:none;" onclick="getTaggedRequest();"/> ' +
						'</td><td colspan="2" style="text-align:right"><input type="button" class="butn" id="fillTravelInfoId" name="fillTravelInfoId" value="Proceed" style="vertical-align:middle;" onclick="fillTravelInformation();"/>' +
						'</td></tr>';	                 
		             
      $("#travelerInfoId").append(travelerInfo);
      
      $("#travelStartDate").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
			scrollMonth : false,
    		scrollInput : false,

		yearEnd: 2100,

		onShow: function() {
				
				$("#travelStartDate").val("");
				if($('#travelEndDate').val()!=""){
				endDate = $('#travelEndDate').val();
				$("#travelStartDate").datetimepicker("setOptions", {
					maxDate: endDate
				});

		       	}else{
				$("#travelStartDate").datetimepicker("setOptions", {
					maxDate: false
				});
		       	}
		       	
		       
				var today = new Date();
				$("#travelStartDate").datetimepicker("setOptions", {
					minDate: today
				});
		       	
				
			},
    onSelectDate: function(input) {
				
		
        var selectedDate = new Date(input);
        $("#travelEndDate").datetimepicker("setOptions", {
            defaultDate: selectedDate,
            viewDate: selectedDate
        });

       
			}
	    });	
	    
	     
	    $("#travelEndDate").datetimepicker({
			
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
			scrollMonth : false,
    		scrollInput : false,
		yearEnd: 2100,
				
    onShow: function() {
				$("#travelEndDate").val("");
				let endDate=0;
		       	if($('#travelStartDate').val()!=""){
				 endDate=$('#travelStartDate').val();
   				 $("#travelEndDate").datetimepicker("setOptions", {
            minDate: endDate
        });
		       	}$("#travelEndDate").datetimepicker("setOptions", {
          		  minDate: endDate
          		    });
   				 
      
			}
			
		   });
	   
	   
		$("#authorityDate").datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
			scrollMonth : false,
    		scrollInput : false,
		yearEnd: 2100,
			maxDate:0,
		
		onShow: function() {
				$("#authorityDate").val("");
			}
			 
	   });	             
}


function fillTravelInformation(){
	
	var personalNo=$('#personalNo').val();
	var TRRule=$('#TRRule').val();
	var reqType=$('#reqType').val();
	var serviceType=$('#serviceType').val();
	var groupId=$('#groupId').val();
	var journeyDate=$("#travelEndDate").val();
	
	attNo=0;var isAttAll=false;
	partyDependentNo=0;	var isPartyBookingParameter=false;
	returnAttNo=0;returnPartyDependentNo=0;
	var check=validateVisitorFields();
	
	if(check){
		$("#fillTravelInfoId").hide();
		var TravelTypeDD=$('#TravelTypeDD').val();
		var TravelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
		var trGroup=$('#trGroup :selected').text().toUpperCase();
		
		$("#TravelTypeDD").css("pointer-events", "none");
	    $("#TRRule").css("pointer-events", "none");
	    $("#travelID").css("pointer-events", "none");
	    $("#travelStartDate").css("pointer-events", "none");
	    $("#travelEndDate").css("pointer-events", "none");
	    
		
		if(TravelTypeText=='TD'){
			$("#trGroup").css("pointer-events", "none");
		}	
			
		var StateOptions="<option value=''>Select</option>";
		var CityOptions="";
		
		$.ajax(
			{
				url: $("#context_path").val()+"mb/fillTravelInfoBulkBkg",
				type: "get",
		      	data: "personalNo="+personalNo+"&tRRule="+ TRRule +"&travelTypeDD="+ TravelTypeDD +"&reqType="+ reqType +"&isAttAll="+isAttAll+"&isPartyBookingParameter="+isPartyBookingParameter+"&serviceType="+serviceType+"&groupId="+groupId+"&journeyDate="+ journeyDate,
		      	dataType: "json",
		      	beforeSend: function() 
		      	{
		        	var calHeight=document.body.offsetHeight+20;
			        $("#screen-freeze").css({"height":calHeight + "px"} );
			        $("#screen-freeze").css("display", "block");
			    },
			  	success: function(msg)
		      	{
		      		
		      		
				  	var servOption="";
		      		if(msg.userID=="" || msg.userID==null){
				  			alert(msg.message);
				  			$("#screen-freeze").css("display", "none");
				  			window.location.reload();
							return false;
					}
					if(msg.codeHead=="" || msg.codeHead==null){
						
							alert("Code Head for Rail is not assigned. Please contact DTS Helpline");
							$("#screen-freeze").css("display", "none");	
							window.location.reload();
							return false;
					}
					
					if(msg.airCodeHead=="" || msg.airCodeHead==null){
						alert("Code Head for Air is not assigned. Please contact DTS Helpline");	
						$("#screen-freeze").css("display", "none");
						window.location.reload();
						return false;
				     }
				   
				   if(msg.checkRtrmntAge=="NOTALLOW" || msg.checkRtrmntAge==null){
	                   alert("Booking can't be done after retirement date"); 
	                   $("#screen-freeze").css("display", "none");
	                   window.location.reload();
	                   return false;
	                }
				   
					if(msg.checkRtrmntAge=="false"){
					   	if(!IsAgeRelaxtion()){
					   		$("#screen-freeze").css("display", "none");
					   		window.location.reload();
					   		return false;
					   	}
					   	
					}
					
					$('#travelerObj').val(JSON.stringify(msg));
					
					var firstName =msg.firstName;
					var middleName = msg.middleName;
					var lastname = msg.lastName;
					var service = msg.service;
					var unitService = msg.unitService;
					var category = msg.category;
					var rankName = msg.rankName;
					var rankID = msg.rankID;
					var codeHead = msg.codeHead;
					var office = msg.office;
					var airOffice = msg.airOffice;
					var officeID = msg.officeID;
					var dateOfBirth = msg.dateOfBirth;
					var Address = msg.address;
					var allowedTravels = msg.allowedTravels;
					var tadaPermission = msg.tadaPermission;
					var ruletadaPermission = msg.ruleTadaPermission;
					var hotelAllow = msg.ruleTadaHotelPermission;
					var ruleDAAdvanceDay = msg.ruleDaAdvanceDay;
					var accountDtlsUpdated = msg.accountDtlsUpdate;
					var bankAccountNo = msg.travlerBankAccountNumber;
					var bankIFSCCode = msg.travlerIfscCode;
					var conveyanceAllow = msg.ruleTadaConveyancePermission;
					var foodAllow = msg.ruleTadaFoodPermission;
					var isBgtAllowed=msg.isBgtAllowed;
					var isBkgAllowedAfterBgt=msg.isBkgAllowedAfterBgt;
					var cfyPermissionStatus=msg.cfyPermissionStatus;
					var travellerUnitId=msg.travellerOfficeId;
					var civilianService=msg.civilianService;
					var railPAOId = msg.railPAOId;
					var airPAOId = msg.airPAOId;
					var airCodeHead = msg.airCodeHead;
					var category_id=msg.categoryId;
					var service_id=msg.serviceId;	
					var LevelName=msg.levelName;
					var LevelId=msg.levelId;
					
					$("#tadaPermission").val(tadaPermission);
					$("#ruletadaPermission").val(ruletadaPermission);
					$("#travellerUnitId").val(travellerUnitId);
					$("#airAccountOffice").val(airOffice);
					$("#railPAOId").val(railPAOId);
					$("#airPAOId").val(airPAOId);
					$("#railOffice").val(office);
					$("#travellerUnitService").val(unitService);
                    $("#paoChanged").val(1);
                    	      		
					var masterTravlerUnit=msg.masterTravelerUnitName;					 
					setJournyTypeInfo();
					
					setJournyDetailsForm(0, msg);  //Onwards 
						
					setJournyDetailsForm(1, msg);  //Return 
				
					
					if(msg.trQuestion!=null){
					  	questions(msg);
					}
					
					if(msg.requisiteDtls!=null){
						requisiteDtls(msg);
					}
							                    
                    $("#masterTravelerUnitName").val(masterTravlerUnit);                
					$("#firstName").val(firstName);		
					$("#middleName").val(middleName);
					$("#lastName").val(lastname);
					$("#service").val(service);
					$("#category").val(category);						
					$("#rank").val(rankID);
					$("#codeHead").val(codeHead);
					$("#office").val(officeID);
					$("#dateOfBirth").val(dateOfBirth);
					$("#airAccountOffice").val(airOffice);
					$("#category_id").val(category_id);
					$("#service_id").val(service_id);
                    $("#airCodeHead").val(airCodeHead);	
                    $("#hotelAllowForTR").val(hotelAllow);
                    $("#conveyanceAllowForTR").val(conveyanceAllow);
                    $("#foodAllowForTR").val(foodAllow);
					$("#noOfDays").val(ruleDAAdvanceDay);
					$("#userAccDtlsUpdated").val(accountDtlsUpdated);
					$(".bank_account_number").html(bankAccountNo);
					$(".bank_ifsc_code").html(bankIFSCCode);
					$("#rail_code_dis").html(codeHead);
					$("#air_code_dis").html(airCodeHead);	
					$("#getTravelerInfoBtn").val("true");					
					$("#questionInfoDiv").show();
					$("#journeyInfoDiv").show();
					$("#familyDetails").show();
					$("#returnFamilyDetails").show();
					$("#buttonDiv").show();
					$("#journeyModeDivId").show();
					$("#returnJourneyModeDivId").show();
					$("#jrnyInfoDiv").show();														
					$("#saveButtonDiv").show();				 
					$("#travel_Info_Id").show();	
					$("#screen-freeze").css("display", "none");
		      	},//success block end
				
				error: function(xhr, textStatus, errorThrown) {
              console.error('Error:', textStatus, errorThrown);
                }
			});
			// ajax call end to fetch user details 
	  }
}


function validateVisitorFields(){
 	
 	if($("#TravelTypeDD").val()==""){
 		alert("Please Select Travel Type.");
 		return false;
 	}else if($('#TravelTypeDD :selected').text().toUpperCase()=="TD" && $("#trGroup").val()==""){
 		alert("Please Select Travel Group.");
 		return false;
 	}else if($("#TRRule").val()==""){
 		alert("Please Select TR Rule.");
 		return false;
 	}else if($("#travelID").val()==""){
 		alert("Please Select Travel Id.");
 		return false;
 	}else if($("#travelStartDate").val()==""){
 		alert("Please Enter Travel Start Date.");
 		$("#travelStartDate").focus();
 		return false;
 	}else if($("#travelEndDate").val()==""){
 		alert("Please Enter Travel End Date.");
 		$("#travelEndDate").focus();
 		return false;
 	}else if($("#authorityNo").val().trim()==""){
 		alert("Please Enter Authority Number.");
 		$("#authorityNo").focus();
 		return false;
 	}else if($("#authorityDate").val()==""){
 		alert("Please Enter Authority Date.");
 		$("#authorityDate").focus();
 		return false; 	
 	}else{
		  
			 return true;
		 
 		
 	}
 }
 
 
 function setJournyTypeInfo(){
	
	var journyTypeHTML='<td class="label">Journey type</td><td colspan="3">' ;
	
	journyTypeHTML +='<input type="checkbox" value="0" name="journeyType" checked="true" id="journeyType_0" onclick="getJournyTypeDtls(0);"/>' +
	 	 		     '&#160;Onwards&#160;' +
	 	 		     '<input type="checkbox" value="1" name="journeyType" checked="true" id="journeyType_1" onclick="getJournyTypeDtls(1);"/>' +
	 	 		     '&#160;Return' ;
	journyTypeHTML +='</td>';
	
	$("#journey_type_id").html(journyTypeHTML);				
}

function setJournyDetailsForm(jrnyType, msg){
	
	var reqType=$("#reqType").val();
	var serviceType=$("#serviceType").val();
	var AttendentCount = msg.attendentCount;
	var isPartyBooking = 'Yes';

	var options = '<option value="">Select</option>'; 
	$.each(msg.entiltedClass,function(id, name){ 
		options += '<option value="' + id + '">' +name + '<\/option>';
	});
	
	var airClassOptions = '<option value="">Select</option>';
	$.each(msg.airEntiltedClass,function(id, name){ 
		airClassOptions += '<option value="' + id + '">' +name + '<\/option>';
	});
					

	$.ajax({
			  url: $("#context_path").val()+"mb/journyDtlsBulkBkgAjax",
      		  type: "get",
      	 	  data: "jrnyType="+jrnyType+"&reqType="+reqType+"&serviceType="+serviceType,
      		  dataType: "text",
      		  async :false,
		      success: function(msgHtml)
		      {
		      	if(parseInt(jrnyType)==0){
		      		
				     $("#onward_booking_id").html(msgHtml);
				     $("#onward_booking_header_id").show();				     				    
				     if(msg.familyDetail!=null){
						setFamilyDetails(msg);
					 }
				
					 if(msg.travelMode!=null){
						setTravelMode(msg);
					 }
			
					if(reqType!="" && reqType=='airBulkBkg'){
						
						partyDependentNo=0;
						
						var higherClassAllowed = msg.depHigherClassAllowed;
						
						$("#isPartyBooking").val(isPartyBooking); 
						$("#depHigherClassAllowed").val(higherClassAllowed); 
						
					
						var partyDetailsHtml='<input type="button" value="Add Traveller" class="butn" id="" onclick="setPartyDependentAllowedDiv();"/>'
						$("#partyDependentDetails").html(partyDetailsHtml);
						$("#partyMemberCount").val("0"); 
						$("#partyDependentDetails").show(); 
						
						
						
					}else{
						
						$("#isPartyBooking").val("");
						$("#depHigherClassAllowed").val("");
						$("#partyMemberCount").val("0");
					}
				
					$("#entitledClass").html(options);
					$("#airEntitledClass").html(airClassOptions);
				   
		      		
		      	}else if(parseInt(jrnyType)==1){
		      		
				      $("#return_booking_id").html(msgHtml);
				      $("#return_booking_header_id").show();
				      $("#returnTrnsrchDiv").show();
		
					  if(!($(msg).find('FamilyDetailSeq')=="")){
						setReturnFamilyDetails(msg);
					  }
		
					  if(!($(msg).find('TravelModeXml')=="")){
						setReturnTravelMode(msg);
					  }
						
		
					 if(reqType!="" && reqType=='airBulkBkg'){
						
						returnPartyDependentNo=0;
						
						var higherClassAllowed = msg.depHigherClassAllowed;
						
						$("#isPartyBooking").val(isPartyBooking); 
						$("#depHigherClassAllowed").val(higherClassAllowed); 
						
					
						var partyDetailsHtml='<input type="button" value="Add Traveller" class="butn" id="" onclick="setReturnPartyDependentAllowedDiv(\'' +msg +'\');"/>'
						$("#returnPartyDependentDetails").html(partyDetailsHtml);
						$("#returnPartyMemberCount").val("0"); 
						$("#returnPartyDependentDetails").show(); 
						
					 }else{
					 	
						$("#isPartyBooking").val("");
						$("#depHigherClassAllowed").val("");
						$("#returnPartyMemberCount").val("0");
					 }
		
					$("#returnEntitledClass").html(options);
					$("#returnAirEntitledClass").html(airClassOptions);		
		
		      	}
            
       	 }
     });
}

function setPartyDependentAllowedDiv() {

	partyDependentNo++;
	var partyDetailsInnerHtml = "";

	partyDetailsInnerHtml += '<table  border="0" cellpadding="4" cellspacing="1" width="100%%" class="formtxt">'
	partyDetailsInnerHtml += '<tr align="left"><td colspan="5"><b>Party Group Details</b></td></tr>'
	partyDetailsInnerHtml += '<tr>'
	partyDetailsInnerHtml += '<td  align="left" width="20%">Personal Number</td>'
	partyDetailsInnerHtml += '<td  align="left" width="25%">Name</td>'
	partyDetailsInnerHtml += '<td  align="left" width="25%">ERS Name</td>'
	partyDetailsInnerHtml += '<td  align="left" width="10%">Gender</td>'
	partyDetailsInnerHtml += '<td  align="left" width="20%">Date Of Birth</td></tr></table>'
	partyDetailsInnerHtml += '<div id="partyDependentValueDiv">'
	partyDetailsInnerHtml += '<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" id="attPartyDetailsTable" name="attPartyDetailsTable">'
	partyDetailsInnerHtml += '<tr><td width="20%" align="left" class="lablevalue"><input class="txtfldM" type="text" size="20" autocomplete="off" id="dependentPersonalNo' + partyDependentNo + '" name="dependentPersonalNo" onkeyup="this.value = this.value.toUpperCase()" onchange="setGetPersonalDetails(this.value,' + partyDependentNo + ',0)"/></td>'
	partyDetailsInnerHtml += '<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="partyDepName' + partyDependentNo + '" name="partyDepName" readonly/></td>'
	partyDetailsInnerHtml += '<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="partyDepErsName' + partyDependentNo + '" name="partyDepErsName" readonly/></td>'
	partyDetailsInnerHtml += '<td width="10%" align="left" class="lablevalue"><input  type="text" size="5"  id="partyDepGender' + partyDependentNo + '" name="partyDepGender" readonly></td>'
	partyDetailsInnerHtml += '<td width="20%" align="left" class="lablevalue"><input  type="text" size="10"  id="partyDepDOB' + partyDependentNo + '" name="partyDepDOB" readonly></td></tr>'
	partyDetailsInnerHtml += '</table>'
	partyDetailsInnerHtml += '<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" >'
	partyDetailsInnerHtml += '<tr><td colspan="4" align="right"><input type="button" value="Add Row" class="butn" onclick="addPartyDependentRows();"/>&nbsp;<input type="button" class="butn" value="Delete Row" onclick="delPartyDependentRow();"/></td></tr>'
	partyDetailsInnerHtml += '</table></div>'


	$("#partyDependentDetails").html(partyDetailsInnerHtml);
	$("#partyMemberCount").val(partyDependentNo);

}  

function addPartyDependentRows()
{
	
     partyDependentNo++;
     var tbl = document.getElementById('attPartyDetailsTable');
  	 var lastRow = tbl.rows.length;
	 var row=tbl.insertRow(lastRow);
	 
	 var cellRight = row.insertCell(0);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'dependentPersonalNo'+partyDependentNo;
	 el.name= 'dependentPersonalNo';
	 el.size = 20;
	 el.className="txtfldM";	
	 el.setAttribute("autocomplete","off");
	 el.setAttribute("onkeyup","this.value = this.value.toUpperCase()");
	 el.setAttribute("onchange","setGetPersonalDetails(this.value,"+partyDependentNo+",0)");	
	
	 cellRight.appendChild(el);	
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'partyDepName'+partyDependentNo;
	 el.name= 'partyDepName';
	 el.setAttribute("readonly","readonly");
	 el.size = 15;
	 cellRight.appendChild(el);
	 
	
	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'partyDepErsName'+partyDependentNo;
	 el.name = 'partyDepErsName';
	 el.setAttribute("readonly","readonly");
	 el.size = 15;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'partyDepGender'+partyDependentNo;
	 el.name = 'partyDepGender';
	 el.setAttribute("readonly","readonly");
	 el.size = 5;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(4);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'partyDepDOB'+partyDependentNo;
	 el.name= 'partyDepDOB';
	 el.setAttribute("readonly","readonly");
	 el.size = 10;
	 cellRight.appendChild(el);				
	 $("#partyMemberCount").val(partyDependentNo);

}


function delPartyDependentRow()
{
	var partyRowCount = $('#attPartyDetailsTable tr').length;
	partyDependentNo--;
    if(partyRowCount==1)
    {
    	 partyDependentNo=0;
     	$("#partyMemberCount").val(partyDependentNo);
     	$("#partyDependentDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Traveller" class="butn" id="" onclick="setPartyDependentAllowedDiv();"/></td></tr></table>');
    }
    else
    {
    	$('#attPartyDetailsTable tr:last').remove();
    	$("#partyMemberCount").val(partyDependentNo);
    }
 
}

function setGetPersonalDetails(personalNo, rowIndex, travelType)
{
	
	
		var isAlreadyAdded=false;
		var selEntitledClass="";
		
		var isPartyHigherClassAllowed=$('#depHigherClassAllowed').val();
		
		var travelerUnitName=$('#masterTravelerUnitName').val();
		
		if(parseInt(travelType)==0){
			selEntitledClass=$('#airEntitledClass').val();
		}else{
			selEntitledClass=$('#returnAirEntitledClass').val();
		}
		
		personalNo=personalNo.toUpperCase();
		personalNo=personalNo.trim();
		
		var masterPersonalNo=$('#personalNo').val();
		
		masterPersonalNo=masterPersonalNo.toUpperCase();
		masterPersonalNo=masterPersonalNo.trim();
		
		//Remove comment in order to restrict master traveler as dependent.
		
		if(masterPersonalNo==personalNo)
		{
			alert("Party Dependent Traveler Personal Number And Master Traveler Personal Number Should Not Same");
			$('#dependentPersonalNo' + rowIndex).val('');
			$('#returnDependentPersonalNo' + rowIndex).val('');		
			return false;
		} 
		
		
		var allAddedPersonalNumber;
		
		if(parseInt(travelType)==0){
			allAddedPersonalNumber=document.getElementsByName("dependentPersonalNo");
		}else{
			allAddedPersonalNumber=document.getElementsByName("returnDependentPersonalNo");
		}
		
		for(var k=1; k<=allAddedPersonalNumber.length;k++)
		{
			if(k!=rowIndex)
			{
				var userAlias=allAddedPersonalNumber[k-1].value.toUpperCase();
				userAlias=userAlias.trim();
				if(userAlias==personalNo)
				{
					isAlreadyAdded=true;
					break;
				}
			}
		}
		
		
		if(isAlreadyAdded)
		{
			alert(personalNo+" Personal Number Has Been Already Added");
			$('#dependentPersonalNo' + rowIndex).val('');
			$('#returnDependentPersonalNo' + rowIndex).val('');		
			return false;
		}
		
		
		
		$.ajax({
			
	      url: $("#context_path").val()+"mb/getBulkBkgPartyVisitor",
	      type: "get",
	      data: "personalNo="+personalNo+"&isPartyHigherClassAllowed="+isPartyHigherClassAllowed+"&selEntitledClass="+selEntitledClass+"&travelerUnitName="+travelerUnitName,
	      dataType: "json",
	      success: function(msg){
		
	   	  if(msg.message!=null||msg.userID==null || msg.userID=="")
	   	  {
			alert((msg.message));
			$('#dependentPersonalNo' + rowIndex).val('');
			$('#returnDependentPersonalNo' + rowIndex).val('');		
			if(parseInt(travelType)==0){
			document.getElementById("partyDepName"+rowIndex).value="";
		  	document.getElementById("partyDepErsName"+rowIndex).value="";
		  	document.getElementById("partyDepGender"+rowIndex).value="";
		  	document.getElementById("partyDepDOB"+rowIndex).value="";
			}else{
			document.getElementById("returnPartyDepName"+rowIndex).value="";
		  	document.getElementById("returnPartyDepErsName"+rowIndex).value="";
		  	document.getElementById("returnPartyDepGender"+rowIndex).value="";
		  	document.getElementById("returnPartyDepDOB"+rowIndex).value="";
			}
			return false;
		  }
		  
		  if(msg.service==null || msg.service=="")
		  {
			alert("No Service Is Assigned To Party Dependent");
			if(parseInt(travelType)==0){
			document.getElementById("partyDepName"+rowIndex).value="";
		  	document.getElementById("partyDepErsName"+rowIndex).value="";
		  	document.getElementById("partyDepGender"+rowIndex).value="";
		  	document.getElementById("partyDepDOB"+rowIndex).value="";
		  	 }else{
			document.getElementById("returnPartyDepName"+rowIndex).value="";
		  	document.getElementById("returnPartyDepErsName"+rowIndex).value="";
		  	document.getElementById("returnPartyDepGender"+rowIndex).value="";
		  	document.getElementById("returnPartyDepDOB"+rowIndex).value="";
			}	
			return false;
		 }
	   	 if(msg.category==null || msg.category=="")
	   	 {
			alert("No Category Is Assigned To Party Dependent");
			if(parseInt(travelType)==0){
			document.getElementById("partyDepName"+rowIndex).value="";
	  		document.getElementById("partyDepErsName"+rowIndex).value="";
	  	 	document.getElementById("partyDepGender"+rowIndex).value="";
	  	 	document.getElementById("partyDepDOB"+rowIndex).value="";
  	        }else{
			document.getElementById("returnPartyDepName"+rowIndex).value="";
	  		document.getElementById("returnPartyDepErsName"+rowIndex).value="";
	  	 	document.getElementById("returnPartyDepGender"+rowIndex).value="";
	  	 	document.getElementById("returnPartyDepDOB"+rowIndex).value="";
			}
			return false;
		}
	  	if(msg.rankId==null || msg.rankId=="")
	  	{
			alert("No Rank Is Assigned To Party Dependent");
			if(parseInt(travelType)==0){
			document.getElementById("partyDepName"+rowIndex).value="";
	  	 	document.getElementById("partyDepErsName"+rowIndex).value="";
	  	 	document.getElementById("partyDepGender"+rowIndex).value="";
	  	 	document.getElementById("partyDepDOB"+rowIndex).value="";	
	  	 	}else{
			document.getElementById("returnPartyDepName"+rowIndex).value="";
	  	 	document.getElementById("returnPartyDepErsName"+rowIndex).value="";
	  	 	document.getElementById("returnPartyDepGender"+rowIndex).value="";
	  	 	document.getElementById("returnPartyDepDOB"+rowIndex).value="";
			}
			return false;
		}
		if(msg.checkRtrmntAge)
	  	{
	     	if(!IsAgeRelaxtion())
	     	{
	     		if(parseInt(travelType)==0){
			     document.getElementById("partyDepName"+rowIndex).value="";
			  	 document.getElementById("partyDepErsName"+rowIndex).value="";
			  	 document.getElementById("partyDepGender"+rowIndex).value="";
			  	 document.getElementById("partyDepDOB"+rowIndex).value="";
			  	 }else{
				 document.getElementById("returnPartyDepName"+rowIndex).value="";
			  	 document.getElementById("returnPartyDepErsName"+rowIndex).value="";
			  	 document.getElementById("returnPartyDepGender"+rowIndex).value="";
			  	 document.getElementById("returnPartyDepDOB"+rowIndex).value="";
			     }
			     return false;
	     	}
	  	}
	  	else
	  	{
	  		var travelerName=msg.name;
	  		var travelerErsPrintName=msg.ersPrintName;
	  		var travelerGender=msg.gender;
	  		var travelerDOB=msg.dateOfBirth;
	  		if(parseInt(travelType)==0){
			document.getElementById("dependentPersonalNo" + rowIndex).readOnly = true;
	  		document.getElementById("partyDepName"+rowIndex).value=travelerName;
	  		document.getElementById("partyDepErsName"+rowIndex).value=travelerErsPrintName;
	  		document.getElementById("partyDepGender"+rowIndex).value=travelerGender;
	  		document.getElementById("partyDepDOB"+rowIndex).value=travelerDOB;
	  		}else{
			document.getElementById("returnDependentPersonalNo" + rowIndex).readOnly = true;
			document.getElementById("returnPartyDepName"+rowIndex).value=travelerName;
	  		document.getElementById("returnPartyDepErsName"+rowIndex).value=travelerErsPrintName;
	  		document.getElementById("returnPartyDepGender"+rowIndex).value=travelerGender;
	  		document.getElementById("returnPartyDepDOB"+rowIndex).value=travelerDOB;
			}
	  		
	  	}
	  	

     }});

}
function ViewReason(Reason){
  alert(Reason);
}


function setFamilyDetails(msg)
{  
  if (msg == null || msg == ''){
  	msg = $.parseJSON($('#travelerObj').val());
  }
   
   var taggedLtcYear='';
   
   if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
   
   var familyDetails="";
   $("#relation").val("")
   $("#gender").val("")
   $("#familyName").val("")
   $("#dob").val("")
   $("#depSeqNo").val("")
   $("#ersPrntNameVal").val("")
   $("#otherRel").val("")
   $("#age").val("")
    familyDetails+=''
	familyDetails+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt">'
    familyDetails+='<tr align="left"><td colspan="7"><b>Passengers Details</b></td></tr>'
    familyDetails+='<tr><td class="label" width="15%">Name</td>'
    familyDetails+='<td class="label" width="15%">ERS Print Name</td>'
    familyDetails+='<td class="label" width="15%">Relation</td>'
    familyDetails+='<td class="label" width="15%">Gender</td>'
    familyDetails+='<td class="label" width="15%">Date Of Birth</td>'
    familyDetails+='<td class="label" width="15%" id="validIdInfoCol" style="display: none;">Id Proof</td>'        	
    familyDetails+='<td class="label" width="15%">Add To List</td></tr>'
   
    var j=0;
    var x=0;
	
		 $.each(msg.familyDetail, function(index,obj){
			 
		    var familyName = obj.name;
			var relation = obj.relationShip;
			var ersPrintName = obj.ersPrintFamilyName;					
			var dob = obj.dob;
			var GenderCode = obj.genderCode;
			var gender = obj.gender;
			var RelationCode = obj.relationCode;
			var Reason = obj.reason;
			var DepSeqNo = obj.depSeqNo;
			var age = getAgeByDOB(dob);
			
   			
			$("#familyName").val($("input#familyName").val()+","+familyName);		
			$("#relation").val($("input#relation").val()+","+RelationCode);
			$("#gender").val($("input#gender").val()+","+GenderCode);
			$("#dob").val($("input#dob").val()+","+dob);
			$("#depSeqNo").val($("input#depSeqNo").val()+","+DepSeqNo);
			$("#ersPrntNameVal").val($("input#ersPrntNameVal").val()+","+ersPrintName);
			$("#otherRel").val($("input#otherRel").val()+",")
			$("#age").val($("input#age").val()+","+age);
   				
			familyDetails+='<div id="famDtlsDiv'+ j+'"><tr><td width="15%"><label >'+ familyName +'</label></td>'
			familyDetails+='<td><label>'+ ersPrintName +'</label></td>'
			familyDetails+='<td><label>'+ relation +'</label></td>'
			familyDetails+='<td><label>'+ gender +'</label></td>'
			familyDetails+='<td><label>'+ dob +'</label></td>'
			
			familyDetails+='<td class="validIdValCol" style="display: none;" id="validIdValCol"> <input type="checkbox" disabled="true" name="validIdCardPass" value="'+DepSeqNo+'" onclick="showValidIdAlert('+j+')" id="validIdCardPassCheck' +j+ '"/></td>'        	
        	
				var JourneyCheck = obj.journeyCheck;
				
					if(JourneyCheck=='true')
					{
						familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /><div style="display:none" id="reasonDiv' +j+ '"><a href="#" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
					}
			    	else
			    	{
						familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" ></div><div id="reasonDiv' +j+ '"><a href="#" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
					}	
					
			
			
			j=j+1;
					 
	    });// end of FamilyDetailSeq loop
	    
		familyDetails+='</table>';
		
        $("#familyDetails").html(familyDetails);
           	    
}

function questions(msg)
{
	var questions="";
    questions+='<table width="100%" border="0" cellpadding="4" cellspacing="1"  class="formtxt"><tr><td colspan="3" align="left" ><b>Questions Asked</b></td></tr><tr align="left">'
    questions+='<td class="lablevalue" width="15%">Question</td>'
    questions+='<td class="lablevalue" width="60%">Question Description</td>'
    questions+='<td class="lablevalue" width="15%">Answer</td></tr>'
    var i=0;  
     $.each(msg.trQuestion, function(index,obj){
  	
		var question = obj.question;
		var questionDesc = obj.questionDesc;
		var answer = obj.answer;
		questions+='<tr align="left"><td width="15%"><label>'+ question +'</label></td>'
		questions+='<td><label>'+ questionDesc +'</label></td>'
		questions+='<input type="hidden" id="questionCheck' +i+ '" name="question" value="' + questionDesc + '"/>'
		questions+='<input type="hidden" id="answer" name="answer" value="' + answer + '"/>'
		questions+='<td class="d-flex" style="column-gap:0.2rem"><input type="radio" id="answerCheck' +i+ '" name="answerCheck' +i+ '" value="0"/>Yes<input type="radio" id="answerCheck' +i+ '" name="answerCheck' +i+ '" value="1"/>No</td></tr>'
		i++;
	
	});	
		
	questions+='</table>';
	$("#questionInfoDiv").html(questions);
	$("#questionInfoDiv").hide();
}


function setTravelMode(msg) {
	var isExist = msg.isExist;
	if (isExist == 'YES') {

		var jrnyInfoTxt = "";
		jrnyInfoTxt += '<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch">';
		jrnyInfoTxt += '<tr align="left" bgcolor="#fbfac8">';
		jrnyInfoTxt += '<td width="100%" colspan="2" class="d-flex" style="column-gap:0.2rem">';
		jrnyInfoTxt += 'Select Journey Mode:';
		jrnyInfoTxt += '<input type="radio" value="1" name="journeyMode" id="journeyMode" onClick="showAir();" checked="true" />Air';
		jrnyInfoTxt += '</td>';
		jrnyInfoTxt += '</tr>';
		jrnyInfoTxt += '</table>';
		$("#journeyModeDivId").html(jrnyInfoTxt);
		showAir();

	} else {
		alert("Any travel mode is not associated with selected travle rule.");
	}
}


function requisiteDtls(msg)
{
	//alert("Inside requisiteDtls");
	var requisiteDtls="";
    requisiteDtls+='<div><div class="panel">'
	requisiteDtls+='<br/><table width="100%" border="0" cellpadding="0" cellspacing="0" class="bgborder"><tr><td><table width="100%" border="0" cellpadding="4" cellspacing="1"  class="formtxt"><tr><td colspan="3" class="heading">IS Requisite Approved</td></tr><tr align="left">'
    requisiteDtls+='<td width="15%">Requisite Desc</td>'
    requisiteDtls+='<td width="15%">Is Approved</td>'
    requisiteDtls+='</tr>'
    var i=0;  
     $.each(msg.requisiteDtls, function(index,obj){
  	
		var Requisite = obj;
		requisiteDtls+='<tr align="left"><td width="15%"><label class="lablevalue">'+ Requisite +'</label></td>'
		requisiteDtls+='<td><input type="checkbox" class="chkbox" id="reqsAproved" name="reqsAproved" value="0" /></td>'
		requisiteDtls+='</tr>'
		i++;
	
	});	
	requisiteDtls+='</table></td></tr></table></div></div>';
	$("#requisiteDiv").html(requisiteDtls);
	$("#requisiteDiv").hide();
	
}


function getJournyTypeDtls(type){
	
	    var msg = $.parseJSON($('#travelerObj').val());
   		
   		if(type == 0){
   			if($('#journeyType_0').is(":checked")){
   				setJournyDetailsForm(0, msg);
   				$("#journeyModeDivId").show();
   				$("#familyDetails").show();
   				setSourceDestinationByTrRule();
   				
   			}else{
   				$("#onward_booking_header_id").hide();
	    		$("#onward_booking_id").html("");
   			}
   		}else if(type == 1){
   			if($('#journeyType_1').is(":checked")){
   				setJournyDetailsForm(1, msg);
   				$("#returnJourneyModeDivId").show();
   				$("#returnFamilyDetails").show();
   				setReturnSourceDestinationByTrRule();
   			}else{
   				 $("#return_booking_header_id").hide();
    			 $("#return_booking_id").html("");
   			}
   		}
 					
}

function setSourceDestinationByTrRule() {
 	
	var journeyMode;
	var journeyModeArr=document.getElementsByName("journeyMode");
	
	for(var i=0;i<journeyModeArr.length;i++)
	{
		if(journeyModeArr[i].checked)
		journeyMode=journeyModeArr[i].value
	}
	if(journeyMode=='')journeyMode=0;
	
	 if(journeyMode==1)
	{
		
		var tripNodeList = document.getElementsByName("airJourneyType");
		for(i = 0; i < tripNodeList.length; i++)
		{
			var temp = tripNodeList.item(i);
			
				if(Number(temp.value) > 0)
				{
					$("#airJrnyTypeDiv"+temp.value).hide();
			 	}
				$("#returnDateRow").hide();			
		}		
		setOrgDestStnByTrRule('');
		
	}	
 }



function setValue(j)
{   
	//alert("Inside setValue");
   
    var relation=document.getElementById("relationCheck"+j).value;
    var travelMode=$('#travelMode').val();

    if($('#removeFrmList'+j).is(":checked"))
    {
		if(relation=="Self")
		{
			$("#requisiteDiv").show();
			$("#reqstCheck").val("true")
		}
		document.getElementById("removeFrmList"+j).value=0;
		var famDtlsDiv=document.getElementById("famDtlsDiv"+j)
		famDtlsDiv.setAttribute("class" ,"familyDtlsBack");
	}
	else
	{
		document.getElementById("removeFrmList"+j).value=1;
		$("#reqstCheck").val("false")
		if(!document.getElementById("removeFrmList0").checked)
		{
			$("#requisiteDiv").hide();
		}
	
	}
		
	if(travelMode==1){
		if($("#airsrchDiv")!=null){
			$("#airsrchDiv").show();
		}
	}	
}


function isDateChange(id){
  var $myText = $("#"+id);
  $myText.data("value", $myText.val());
  setInterval(function() {
        var data = $myText.data("value"),
        val = $myText.val();

    if (data !== val) {

        $myText.data("value", val);
         $("#flightKey").val("");
		 $("#flightCode").val("");
    }
}, 100);
}

 function filterTimeBands()
{
	 var objTime= $('input[name="timeBand"]');

     var obj=[]; 
     var objZero ='zero';
     var objOne ='one';
     var objOnePlus ='one-plus';
  
   obj.push(objZero);
   obj.push(objOne);
   obj.push(objOnePlus)


	for(var i=0; i < objTime.length; i++)
    {   
		
        if(objTime[i].checked)
        {
         var  time=objTime[i].value;  
           
            for(var j=0; j < 3; j++) {
			 $('#AITable .AI_'+obj[j]+''+'.TimeBand_'+time).show(); 
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


function validate_required(field,alerttxt){
	
  with (field)
  {
  	if (value==null||value=="" || value=="-1")
    {
    	alert(alerttxt);
    	field.focus();
    	return false;
    }else
    {
    	return true;
    }
  }
}

function IsAgeRelaxtion(){
	
  	alert("Travel request for retired personnel is not allowed");
  	return false;

}	

function setDefaultValueForParty(msgg)
{
	var selEntitledClass=$('#airEntitledClass').val();	
	
	var isPartyBooking=$('#isPartyBooking').val();	
	
	if(isPartyBooking=='Yes')
	{
		partyDependentNo=0;
		$("#partyMemberCount").val("0");
		var partyDetailsHtml='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Traveller" class="btn" id="" onclick="setPartyDependentAllowedDiv();"/></td></tr></table>'
		$("#partyDependentDetails").html(partyDetailsHtml);
	}

}
