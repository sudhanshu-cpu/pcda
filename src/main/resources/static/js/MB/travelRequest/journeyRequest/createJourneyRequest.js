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
				url: $("#context_path").val()+"mb/getTravellerDetails",
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


function getTravelGroup(){
	
		var travelType=$('#TravelTypeDD').val();
		var TRRule=""
		var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
		var requestType=$("#reqType").val(); 
		var serviceType=$('#serviceType').val();
		var service_id=$("#service_id").val();
		var category_id=$("#category_id").val();
		var trRuleNews=""
		var travelReqStatus;
		$("#TRRule").html("<option value=''>Select<\/option>");
	
		if(travelTypeText=="TD")
		{

			$.ajax(
			{
				  url: $("#context_path").val()+"mb/getTravelGroup",
	      		  type: "get",
	      	 	  data: "travelType="+travelType+"&serviceType="+serviceType,
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
					  
                  	   var options = '';
        options += '<td class="label" style="border-bottom:1px dotted white">Travel Group<span class="mandatory">*</span></td>';
        options += '<td><select id="trGroup" name="trGroup" onchange="getTravelRuleByGroup(this.value)" class="combo">';
        options += '<option value="">Select</option>';
        $.each(msg, function (groupCode, name) {
            options += '<option value="' + groupCode + '">' + name + '<\/option>';
						});

        options += '</select></td>';
						$("#travelGroup").append(options);
					
       				}
       		 });
       
		}else
		{

			$.ajax(
			{

			      url: $("#context_path").val()+"mb/getAllTRforTravelType",
			      type: "get",
			      data: "travelType="+travelType+"&serviceType="+serviceType+"&serviceId="+service_id+"&categoryId="+category_id+"&requestType="+requestType,
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
						var trvlTRdtls="";	
						
                        $.each(msg, function(index,obj){
                        
                        		var id = obj.trRuleId;
								var name = obj.trRuleNumber;
								var trDesc = obj.trRuleDesc;
								var trTitle = obj.trRuleTitle;
								trRuleNews=obj.trRuleNews;
								travelReqStatus=obj.travelReqStatus;
								trvlTRdtls+='<input type="hidden" name="'+id+'" id="'+id+'" value="'+travelReqStatus+"::"+trRuleNews+'">';
									options += '<option value="' + id + '">' +name + '<\/option>';
								
								if(i==0)
								{
									TRRule=id
								}
						});
						
						$("#trNews").html(trvlTRdtls);
						$("#prevTrRule").val(TRRule);
						$("#TRRule").html(options);
						
						if($("#travelGroup > td").length == 4){
							var i=0;
							while (i<2) {
							  $("#travelGroup td:last-child").remove();
							  i++;
							}
						}
						
						isRequestAllowed();
				  }
			});
       		
		}
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
			url: $("#context_path").val()+'mb/getTravelRuleDetails',
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
      
	      url: $("#context_path").val()+"mb/getAllTRforTravelGroup",
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
		
		 if(reqType=='ramBaanBooking'){
		 	if(TravelTypeID == "100002"){
	 	travelTypeOptions += '<option value="' + TravelTypeID + '">' +TravelTypeName + '<\/option>';
		 	}
		 }else if(reqType=='international'){
		 	if(TravelTypeID == "100001" || TravelTypeID == "100002"){
		 		travelTypeOptions += '<option value="' + TravelTypeID + '">' +TravelTypeName + '<\/option>';
		 	}
		 }else{
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
					   '<td><select class="combo" style="width:143px;" id="travelID" name="travelID" onchange="getTravelRequestIds();">'+ 
					   '<option value="" selected="selected">Select</option></select>'+
					   '</td></tr>';
					   
		travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">Travel Start Date<span class="mandatory">*</span></td>'+
				        '<td><input type="text" name="travelStartDate" id="travelStartDate" readonly="readonly"/></td>'+
                        '<td class="label" style="border-bottom:1px dotted white">Travel End Date<span class="mandatory">*</span></td>'+
   				        '<td><input type="text" name="travelEndDate" id="travelEndDate" readonly="readonly" /></td></tr>';
   				        
   		if(reqType=='international'){
   		travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">GSL No<span class="mandatory">*</span></td>'+
				        '<td><input type="text" name="authorityNo" id="authorityNo" autocomplete="off"/></td>' +
				        '<td class="label" style="border-bottom:1px dotted white">GSL Date<span class="mandatory">*</span></td>' +
				        '<td><input type="text" name="authorityDate" id="authorityDate" readonly="readonly"/></td></tr>'; 
   			
   		}else{		        
		travelerInfo += '<tr><td class="label" style="border-bottom:1px dotted white">Authority No<span class="mandatory">*</span></td>'+
				        '<td><input type="text" name="authorityNo" id="authorityNo" autocomplete="off"/></td>' +
				        '<td class="label" style="border-bottom:1px dotted white">Authority Date<span class="mandatory">*</span></td>' +
				        '<td><input type="text" name="authorityDate" id="authorityDate" readonly="readonly"/></td></tr>';   
				        
   		}		         
				        
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
		       	
		       	if($("#reqType").val()=='offlinebooking'){
				var minDate = new Date();
				minDate.setDate(minDate.getDate() - 180);

				$("#travelStartDate").datetimepicker("setOptions", {
					minDate: minDate
				});
				
				$("#travelStartDate").datetimepicker("setOptions", {
					maxDate: 0
				});
				
				
		       	}else{
				var today = new Date();
				$("#travelStartDate").datetimepicker("setOptions", {
					minDate: today
				});
		       	}
				
			},
    onSelectDate: function(input) {
				
				var TravelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
       
        var selectedDate = new Date(input);
        $("#travelEndDate").datetimepicker("setOptions", {
            defaultDate: selectedDate,
            viewDate: selectedDate
        });

        if (TravelTypeText === 'FORMD' || TravelTypeText === 'FORMG' || TravelTypeText === 'CV') {
            if (TravelTypeText === 'FORMD' || TravelTypeText === 'FORMG') {
                var dateFormat = new Date(selectedDate.setDate(selectedDate.getDate() + 5));
						 var endDate =$.datepicker.formatDate('dd/mm/yy', dateFormat);
                         $("#travelEndDate").val(endDate);
						$("#travelEndDate").css("pointer-events", "none");
					}else{
					 $("#travelEndDate").datetimepicker("setOptions", {
                    minDate: selectedDate
        });
					}
				}
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
	
	$("#advanceDA").hide();
	$("#comTransfer").hide();
		
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
		
		if(TravelTypeText=='TD' && trGroup=='MEDICAL'){
				isAttAll=true;
		}
		if(TravelTypeText=='TD' && trGroup=='PARTY GROUP'){
			isPartyBookingParameter=true;
		}
		
		var StateOptions="<option value=''>Select</option>";
		var CityOptions="";
		
		$.ajax(
			{
				url: $("#context_path").val()+"mb/fillTravelInfo",
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
					var dateOfCommisioning=msg.dateOfCommisioning;
					
					$("#tadaPermission").val(tadaPermission);
					$("#ruletadaPermission").val(ruletadaPermission);
					$("#travellerUnitId").val(travellerUnitId);
					$("#airAccountOffice").val(airOffice);
					$("#railPAOId").val(railPAOId);
					$("#airPAOId").val(airPAOId);
					$("#railOffice").val(office);
					$("#travellerUnitService").val(unitService);
                    $("#paoChanged").val(1);
                     $("#dateOfCommisioning").val(dateOfCommisioning);
                    $("#isHospitalUnit").val(msg.hospitalUnit);
                    
					if(TravelTypeText=='FORMD'){
						var FormDYear=msg.formDAndGYear.length;
						var FormDYearS="";
						FormDYearS+='<table  border="0" cellpadding="0" cellspacing="0" width="100%" class="formtxt">'
						FormDYearS+='<tr align="left"><td><b>Select Year For Which You Are Availing Form D </b>'
							    
						if(FormDYear>0){
							var formDCount=0;
							$.each(msg.formDAndGYear, function(i,obj){
							
								var year = obj.Year;
								var availCount = obj.AvailReq;
								if(i==0){
								FormDYearS+='<input type="radio" name="formDYear" style="margin:5px;" value='+year+' checked="true" onclick="setFormDAndGYear(' +year +','+availCount+'); "/> '+year+''
								$("#formDAndGYear").val(year);
								formDCount=availCount;
								}
								else{
									FormDYearS+='<input type="radio" name="formDYear" style="margin:5px;" value='+year+' onclick="setFormDAndGYear(' +year +','+availCount+');" /> '+year+ ''
								}
    
							});
							FormDYearS+='</td><td><b>Available Form D Count :</b><b id="Avail_Form_Count" style="margin:5px;"></b></td></tr></table>'
							 $("#FormDAndGYear").html(FormDYearS);
							 $("#Avail_Form_Count").html(formDCount);
							 $("#FormDAndGYear").show();
						}
					}
					
					if(TravelTypeText=='FORMG'){
						var FormGYear=msg.formDAndGYear.length;;
						var FormGYearS="";
						FormGYearS+='<table  border="0" cellpadding="0" cellspacing="0" width="100%" class="formtxt">'
						FormGYearS+='<tr align="left"><td><b>Select Year For Which You Are Availing Form G </b>'
							    
						if(FormGYear>0){
							var formGCount=0;
							$.each(msg.formDAndGYear, function(i,obj){
							
							    var year = obj.Year;
								var availCount = obj.AvailReq;
								
								if(i==0){
								FormGYearS+='<input type="radio" name="formGYear" style="margin:5px;" value='+year+' checked="true" onclick="setFormDAndGYear(' +year +','+availCount+'); "/> '+year+''
								$("#formDAndGYear").val(year);
								formGCount=availCount;
								}
								else{
									FormGYearS+='<input type="radio" name="formGYear" style="margin:5px;" value='+year+' onclick="setFormDAndGYear(' +year +','+availCount+');" /> '+year+ ''
								}
    
							});
							FormGYearS+='</td><td><b>Available Form G Count :</b><b id="Avail_Form_Count" style="margin:5px;"></b></td></tr></table>'
							 $("#FormDAndGYear").html(FormGYearS);
							 $("#Avail_Form_Count").html(formGCount);
							 $("#FormDAndGYear").show();
						}
					}
					
		      		
					var masterTravlerUnit=msg.masterTravelerUnitName;
					
					if(reqType=="exceptionalBooking" || reqType=='international'){
					   var UnitName = msg.unitName;
					}
					 
					setJournyTypeInfo();
					
					setJournyDetailsForm(0, msg);  //Onwards 
					
					if(TravelTypeText=='FORMD' || TravelTypeText=='FORMG'){
						$("#journeyType_1").css("pointer-events", "none");
						$("#journeyType_1").attr('checked', false);
					}else{	
					setJournyDetailsForm(1, msg);  //Return 
					}
					
					if(msg.trQuestion!=null){
					  	questions(msg);
					}
					
					if(msg.requisiteDtls!=null){
						requisiteDtls(msg);
					}
					
					
					if(TravelTypeText.indexOf('PT')>-1){
                        $("#allwdJrny").val(allowedTravels);
                        $("#noOfReqCreated").val(msg.requestCreated);
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

					if((reqType=='' || reqType=='ramBaanBooking') && TravelTypeText=='TD' && tadaPermission==true && ruletadaPermission==true){
						$("#advanceDA").show();
					}else{
						$("#advanceDA").hide();
					}
				    
					if((reqType=='' || reqType=='ramBaanBooking') && TravelTypeText=='PT' && tadaPermission==true && ruletadaPermission==true){
						$("#comTransfer").show();
					}else{
						$("#comTransfer").hide();
					}
				    
				
					$("#getTravelerInfoBtn").val("true");
					
					$("#questionInfoDiv").show();
					$("#journeyInfoDiv").show();
					$("#familyDetails").show();
					$("#returnFamilyDetails").show();
					$("#buttonDiv").show();
					$("#journeyModeDivId").show();
					$("#returnJourneyModeDivId").show();
					$("#jrnyInfoDiv").show();
					
					if(TravelTypeText.indexOf('PT')>-1 || TravelTypeText.indexOf('LTC')>-1){
						$("#taggedReqDiv").show();
					 	getTaggedRequest();
					}
					
					$("#saveButtonDiv").show();
				     
					var requestType=$("#reqType").val(); 
					if(requestType=='offlinebooking'){
						$("#warrantInfoDiv").show();
							$("#warrantIssueDate").datepicker({dateFormat:'dd/mm/yy', maxDate: 0,
		    	beforeShow : function () {
						 $(this).val("");
					 }
			  });
						
					}else{
						$("#warrantInfoDiv").hide();
					}
					 
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


function setJournyTypeInfo(){
	
	var journyTypeHTML='<td class="label">Journey type</td><td colspan="3">' ;
	
	journyTypeHTML +='<input type="checkbox" value="0" name="journeyType" checked="true" id="journeyType_0" onclick="getJournyTypeDtls(0);"/>' +
	 	 		     '&#160;Onwards&#160;' +
	 	 		     '<input type="checkbox" value="1" name="journeyType" checked="true" id="journeyType_1" onclick="getJournyTypeDtls(1);"/>' +
	 	 		     '&#160;Return' ;
	journyTypeHTML +='</td>';
	
	$("#journey_type_id").html(journyTypeHTML);				
}

function getJournyTypeDtls(type){
	
	    var msg = $.parseJSON($('#travelerObj').val());
   		
   		if(type == 0){
   			if($('#journeyType_0').is(":checked")){
   				setJournyDetailsForm(0, msg);
   				$("#journeyModeDivId").show();
   				$("#familyDetails").show();
   				
   				if($("#reqType").val()=="ramBaanBooking"){
   					setToFrmStatnByTrRule('','true');
		    		setOrgDestStnByTrRule('','true');
   				}else{
   					setSourceDestinationByTrRule();
   				}
   			}else{
   				$("#onward_booking_header_id").hide();
	    		$("#onward_booking_id").html("");
   			}
   		}else if(type == 1){
   			if($('#journeyType_1').is(":checked")){
   				setJournyDetailsForm(1, msg);
   				$("#returnJourneyModeDivId").show();
   				$("#returnFamilyDetails").show();
   				
   				if($("#reqType").val()=="ramBaanBooking"){
   					setReturnToFrmStatnByTrRule('','true');
	    			setReturnOrgDestStnByTrRule('','true');
   				}else{
   					setReturnSourceDestinationByTrRule();
   				}
   			}else{
   				 $("#return_booking_header_id").hide();
    			 $("#return_booking_id").html("");
   			}
   		}
 					
}

function setSourceDestinationByTrRule() {
 	
 	var TravelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
    var tadaPermission=$("#tadaPermission").val().trim();
    var ruletadaPermission=$("#ruletadaPermission").val().trim();
    
	var ramBaanCheck=false;
	var reqType=$("#reqType").val();
	if(reqType=='ramBaanBooking'){
		ramBaanCheck=true;
	}else{
		ramBaanCheck=false;
	}
	
	var journeyMode;
	var journeyModeArr=document.getElementsByName("journeyMode");
	
	for(var i=0;i<journeyModeArr.length;i++)
	{
		if(journeyModeArr[i].checked)
		journeyMode=journeyModeArr[i].value
	}
	if(journeyMode=='')journeyMode=0;
	
	if(journeyMode==0){
		setToFrmStatnByTrRule('',ramBaanCheck);
	}else if(journeyMode==1)
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
		
	}else if(journeyMode==2)
	{
		showMixedRequestPage();
		populateSatationList();
	}
 	
 }
 
 
 function setToStnValue(stnValue)
{
	
	  resetBreakJrnyChecbox();
      resetViaRouteCheckBox();
	var stnStr="";
  	$("#removeFrmList0").attr('checked', false);
  	$("#to_Station_Id").val($('#toStationDD option:selected').text());
  	
    if(stnValue=='AnyStation')
    {
    	$("#container1").show();
        $("#trToStn").hide(); 
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="trToStn"><td><input type="hidden" name="toStation" value="'+stnValue +'"  class="txtfldM" id="toStation" onmouseup="clrVaiFileds(this.value);"/><label>'+stnValue +'</label><td></div>'
	    	$("#toStation").val(stnValue);   
	      	$("#trToStn").html(stnStr); 
	      	$("#trToStn").show();
	    	$("#container1").hide();
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="trToStn"><td>';
	   
	   		stnStr +='<select id="toStation" name="toStation" class="combo" onchange="clrVaiFileds(this.value); fillToStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#trToStn").html(stnStr); 
		    $("#trToStn").show();
		    $("#container1").hide();
	  	
	  	}
	
	  }
}


function setFrmStnValue(stnValue)
{
	
  $("#removeFrmList0").attr('checked', false);
  $("#from_Station_Id").val($('#fromStationDD option:selected').text());
  
  resetClusterFieldOnStnChange();
  resetBreakJrnyChecbox();
  resetViaRouteCheckBox();
  
  var stnStr="";
  if(stnValue=='AnyStation')
  {
   	$("#container").show(); 
   	$("#trFrmStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="trFrmStn"><td><input type="hidden" name="fromStation" value="'+stnValue +'" class="txtfldM" id="fromStation" onmouseup="clrVaiFileds(this.value);" /><label>'+stnValue +'</label></td></div>'
	   $("#fromStation").val(stnValue);   
	   $("#trFrmStn").html(stnStr); 
	   $("#trFrmStn").show(); 
	   $("#container").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="trFrmStn"><td>';
	   stnStr +='<select id="fromStation" name="fromStation" class="combo" onchange="clrVaiFileds(this.value); fillFrmStn(this.value);">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#trFrmStn").html(stnStr); 
	   $("#trFrmStn").show(); 
	   $("#container").hide();
	  		
	}
  }
	   
}

function clrVaiFileds(value)
{
	if(document.getElementById("viaRelxRoute")!=null)
	{
		document.getElementById("viaRelxRoute").checked=false;
		$("#selectRelaxedRoute").hide("fast"); 
	}
 	$("#journeyDate").show("fast");
     
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
			var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
			
			if(travelTypeText.indexOf('LTC')>-1)
			{
				
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
        		
				var k=0;
			
			     $.each(obj.yearWise, function(idx,objYear){
				
					var xmlLtcYear=objYear.year;
			
					if(taggedLtcYear!='')
					{
						if(xmlLtcYear==taggedLtcYear)
						{
							var JourneyCheck = objYear.journeyCheck;
							var JourneyCheckRet = objYear.journeyCheckRet;
							Reason = objYear.reason;
			
							if(JourneyCheckRet!='')
							{
								if(JourneyCheck=='true' && JourneyCheckRet=='true')
								{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /><div style="display:none" id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
		    					else
		    					{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /></div><div id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}
							}
							else
							{
								if(JourneyCheck=='true')
								{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /><div style="display:none" id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
			    				else
			    				{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /></div><div id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}	
							}
							
						} //End of xmlLtcYear==taggedLtcYear
					}	
					
					else
					{
						if(k==1)	
						{
							
			
							var JourneyCheck = objYear.journeyCheck;
							var JourneyCheckRet = objYear.journeyCheckRet;
				
							if(JourneyCheckRet!='')
							{
								if(JourneyCheck=='true' && JourneyCheckRet=='true')
								{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /><div style="display:none" id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
					    		else
					    		{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /></div><div id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}
							}
							else
							{
								if(JourneyCheck=='true')
								{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /><div style="display:none" id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
				   			 	else
				   			 	{
									familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +j+ '" id="dobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="familyName' +j+ '" id="familyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="relationCode' +j+ '" id="relationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="nameCheck' +j+ '" id="nameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +j+ '"/><div id="removeFrmListDiv' +j+ '" name="removeFrmListDiv' +j+ '" /></div><div id="reasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}	
							}
						}
					} //End of else case
					
					k=k+1;
				});// end of YearWise loop
					//End of LTC Case
			}
			else if(travelTypeText.indexOf('FORMD')>-1 || travelTypeText.indexOf('FORMG')>-1)
			{
				
				var formDAndGYear=document.getElementById("formDAndGYear").value; 
				var YearWiseFormDAndG = obj.yearWiseFormDAndG;
				
				var JourneyCheck = obj.journeyCheck;
				
				
			if(formDAndGYear==YearWiseFormDAndG){	
				
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
			
			familyDetails+='<td class="validIdValCol" style="display: none;" id="validIdValCol"> <input type="checkbox" disabled="true" name="validIdCardPass" value="'+DepSeqNo+'" onclick="showValidIdAlert('+x+')" id="validIdCardPassCheck' +x+ '"/></td>'        	
        	
        				
					if(JourneyCheck=='true')
					{
						familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +x+ '" id="dobCheck' +x+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +x+ '"/><div id="removeFrmListDiv' +x+ '" name="removeFrmListDiv' +x+ '" /><div style="display:none" id="reasonDiv' +x+ '"><a href="#" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
					}
			    	else
			    	{
						familyDetails+='<td><input type="hidden" name="passPassportNo" id="passPassportNo'+j+'"/><input type="hidden" name="passExpdate" id="passExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="dobCheck' +x+ '" id="dobCheck' +x+ '"/><input type="hidden" value="'+relation+'" id="relationCheck' +x+ '"/><div id="removeFrmListDiv' +x+ '" name="removeFrmListDiv' +x+ '" ></div><div id="reasonDiv' +x+ '"><a href="#" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
					}	
					
				x=x+1;	
				}
				
			}
			else
			{
					
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
					
			}
			
			j=j+1;
					 
	    });// end of FamilyDetailSeq loop
	    
		familyDetails+='</table>';
		
        $("#familyDetails").html(familyDetails);
           	    
}


function IsAgeRelaxtion(){
	
  	alert("Travel request for retired personnel is not allowed");
  	return false;

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


function setTravelMode(msg)
{
	var isExist = msg.isExist;
	var trRuleID = $('#TRRule').val();
	var journeyType_0 = $('#journeyType_0').val();
	var isAirOnly=false;var isMixedOnly=false;var isRailOnly=false; 
	var isRailExist=false;var isAirExist=false; var isMixedExist=false; 
	if(isExist=='YES')
	{
		var isMixApplicable = msg.isMixApplicable;	
	
		var jrnyInfoTxt="";
		jrnyInfoTxt+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch">';
		jrnyInfoTxt+='<tr align="left" bgcolor="#fbfac8">';
			jrnyInfoTxt+='<td width="100%" colspan="2" class="d-flex" style="column-gap:0.2rem">';
						jrnyInfoTxt+='Select Journey Mode:';
						     $.each(msg.travelMode, function(intCode,value){
							
						  		if(intCode==0){
									isRailExist=true;
								}else if(intCode==1){
									isAirExist=true;
								}	
								else if(intCode==2){
									isMixedExist=true;	
								}
						  	});	
						  	
						  	if($("#reqType").val()=='exceptionalBooking'){
								  							  
								 isAirExist=checkForAirExistForHospitalUnit(isAirExist);
						  		isMixedExist=false;
						  	}
						  	
						  	
						  	if(isRailExist && !isAirExist && !isMixedExist)isRailOnly=true; 
						  	if(isAirExist && !isRailExist && !isMixedExist)isAirOnly=true; 
						  	if(isMixedExist && !isAirExist && !isRailExist)isMixedOnly=true; 
							if(isRailExist)
						  		jrnyInfoTxt+='<input type="radio" value="0" name="journeyMode" id="journeyMode" onClick="showRail();" checked="true" />Rail';
						  	if(isAirExist)
						  	{
						  		if(isAirOnly || !isRailExist)
							  		jrnyInfoTxt+='<input type="radio" value="1" name="journeyMode" id="journeyMode" onClick="showAir();" checked="true" />Air';
						  		else 
						  			jrnyInfoTxt+='<input type="radio" value="1" name="journeyMode" id="journeyMode" onClick="showAir();" />Air';
						  	} 
						  	if(isMixedExist && isMixApplicable=='YES' )
						  	{
						  		if(isMixedOnly)
						  			jrnyInfoTxt+='&nbsp;<input type="radio" value="2" name="journeyMode" id="journeyMode" onClick="showMixed();" checked="true" />Mixed';
						  		else
								  	jrnyInfoTxt+='&nbsp;<input type="radio" value="2" name="journeyMode" id="journeyMode" onClick="showMixed();" />Mixed';
						  	}
			if((trRuleID=='TR100233' || trRuleID=='TR100234' || trRuleID=='TR100118')&& journeyType_0=='0'){
			jrnyInfoTxt+='</td>';
			jrnyInfoTxt+='<td width="50%" colspan="2">';
				jrnyInfoTxt+='<div id="mixedPreferenceDiv" style="display:none">Select Preference';
					jrnyInfoTxt+='<select id="mixedPreference" name="mixedPreference" class="combo" onChange="showMixedRequestPage();">';
						jrnyInfoTxt+='<option value="-1" >--select--</option>';
						jrnyInfoTxt+='<option value="1" >Air-Rail</option>';
					jrnyInfoTxt+='</select>';
				jrnyInfoTxt+='</div>';	    
			jrnyInfoTxt+='</td>';
			}else{
	  		jrnyInfoTxt+='</td>';
			jrnyInfoTxt+='<td width="50%" colspan="2">';
				jrnyInfoTxt+='<div id="mixedPreferenceDiv" style="display:none">Select Preference';
					jrnyInfoTxt+='<select id="mixedPreference" name="mixedPreference" class="combo" onChange="showMixedRequestPage();">';
						jrnyInfoTxt+='<option value="-1" >--select--</option>';
						jrnyInfoTxt+='<option value="0" >Rail-Air</option>';
						jrnyInfoTxt+='<option value="1" >Air-Rail</option>';
						jrnyInfoTxt+='<option value="2" >Rail-Air-Rail</option>';
					jrnyInfoTxt+='</select>';
				jrnyInfoTxt+='</div>';	    
			jrnyInfoTxt+='</td>';}
 		jrnyInfoTxt+='</tr>';
 		
 		jrnyInfoTxt+='</table>';

		$("#journeyModeDivId").html(jrnyInfoTxt);
		
		
		if(isRailExist)
	  		showRail();
	  	if(isAirExist)
	  	{
	  		if(isAirOnly || !isRailExist)
		  		showAir();
	  		
	  	} 
	  	if(isMixedExist && isMixApplicable=='YES' )
	  	{
	  		if(isMixedOnly)
	  			showMixed();
	  	}
	  
	}else{
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


   
function setAttendedAllowedDiv()
{
	if($("#TRRule").val() == "TR1000281"){
		$("#airsrchDiv").show();
	}
    var attendedDetails="";  
    attendedDetails+='<style>.no-dropdown { pointer-events: none;}</style>'
  
    var relOptions=getRelationFromResponse();
    var genOptions=getGenderFromResponse();
    
   	attNo++;  	
	
	attendedDetails+=''
	attendedDetails+='<table width="100%" cellpadding="0" cellspacing="0" class="bgborder">'
	attendedDetails+='<tr><td>'
	
		attendedDetails+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt">'
		attendedDetails+='<tr align="left"><td colspan="6"><b>Attendent Details</b></td></tr>'
		
		var isTatkalFlagValue=0;
		var travelMode=$('#travelMode').val();
		
		if(travelMode==0){
			isTatkalFlagValue=document.getElementById("isTatkalFlag").value;	
		}
		if(travelMode==2){
			isTatkalFlagValue=document.getElementById("isTatkalFlagMixed").value;	
		}
		if(isTatkalFlagValue==1)
		{
			attendedDetails+='<tr><td class="label" width="30%">Name</td>'
		    attendedDetails+='<td class="label" width="30%">ERS Print Name</td>'
		    attendedDetails+='<td class="label" width="15%">Gender</td>'
	    	attendedDetails+='<td class="label" width="15%">Date Of Birth</td>'
			attendedDetails+='<td class="label" width="10%">Id Proof</td></tr>'
		}else{
			attendedDetails+='<tr><td class="label" width="30%">Name</td>'
		    attendedDetails+='<td class="label" width="30%">ERS Print Name</td>'
		    attendedDetails+='<td class="label" width="15%">Gender</td>'
	    	attendedDetails+='<td class="label" width="15%">Date Of Birth</td>'
	    	if($("#TRRule").val() == "TR1000281"){
	    	attendedDetails+='<td class="label" width="10%">Physically Handicap</td></tr>'	
			}
		}
		
		attendedDetails+='</table>'
	
	attendedDetails+='</td></tr>'
	
    attendedDetails+='<tr><td>'
	    attendedDetails+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" id="attDetailsTable" name="attDetailsTable">'
		
		if(isTatkalFlagValue==1)
		{
			attendedDetails+='<tr><td width="30%" class="lablevalue"><input class="txtfldM" type="text" id="attName'+attNo+'" name="attName" onblur="setPrintERSName(this.value,'+attNo+')"/></td>'
			attendedDetails+='<td width="30%" class="lablevalue"><input type="text" class="txtfldM" id="attErsName'+attNo+'" name="attErsName" readonly/></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><select class="comboauto" id="attGender'+attNo+'" name="attGender">'+genOptions+'</select></td>'

			attendedDetails+='<td width="15%" class="lablevalue"><input type="text" class="txtfldM" id="attDob'+attNo+'" name="attDob"/>'
			attendedDetails+='<td width="10%" class="lablevalue"><input type="checkbox" class="validIdCardAttCheck" id="validIdCardAttCheck'+attNo+'" value="'+attNo+'" name="validIdCardAttCheck"/></tr>'
		}else{
			attendedDetails+='<tr><td width="30%" class="lablevalue"><input class="txtfldM" type="text" id="attName'+attNo+'" name="attName" onblur="setPrintERSName(this.value,'+attNo+')"/></td>'
			attendedDetails+='<td width="30%" class="lablevalue"><input type="text" class="txtfldM" id="attErsName'+attNo+'" name="attErsName" readonly/></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><select class="comboauto" id="attGender'+attNo+'" name="attGender">'+genOptions+'</select></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><input type="text" class="txtfldM" id="attDob'+attNo+'" name="attDob"/></td>'
			if($("#TRRule").val() == "TR1000281"){
			attendedDetails+='<td width="10%" class="lablevalue"><input type="checkbox"  id="phsclyHandicpd" name="phsicallyHandicpd"/></td></tr>'
				}
		}
		attendedDetails+='</table>'

    attendedDetails+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" >'
	attendedDetails+='<tr><td colspan="5" align="right"><input type="button" value="Add Row" class="butn" onclick="addAttendentRow();"/> &nbsp;<input type="button" class="butn" value="Delete Row" onclick="delAttRow();"/></td></tr>'
 	attendedDetails+='</table>'
 	
 	attendedDetails+='</td></tr>'
	attendedDetails+='</table>';
                    
    $("#attendedDetails").html(attendedDetails);
    $("#finalAttendentCount").val(attNo);
    $("#attDob"+attNo).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		maxDate:0,
		    	onShow : function () {
						 $("#attDob"+attNo).val("");
					 }
			 });
	
} 

function addAttendentRow()
{
	 var msg = $.parseJSON($('#travelerObj').val());
	 
     var maxAttCount=$("#maxattCount").val();		
    
     if(maxAttCount<=attNo){
	     alert("Only "+maxAttCount+ " Attendants Are Allowed To Do Journey On This TR Rule")
	     return false;
     }
     
	if (($("#TRRule").val() == "TR1000281") && ($("#attGender" + attNo).val() != 1) && !$("#phsclyHandicpd").is(":checked")) {

		if (!calAge()) {
			alert("Second Attendant is not allowed according to your above input.");
			return false;
		}
					
	}
	
	if(($("#TRRule").val() == "TR1000281")){

	    $("#attName" + attNo).prop("readonly", true).css("background-color", "#eeeedd");

		$("#attErsName" + attNo).prop("readonly", true).css("background-color", "#eeeedd");

		$("#attGender" + attNo).addClass("no-dropdown").css("background-color", "#eeeedd");

		$("#attDob" + attNo).addClass("no-dropdown").css("background-color", "#eeeedd");

	$("#phsclyHandicpd").prop("disabled", true);
	
	}						

     attNo++;
     
     var tbl = document.getElementById('attDetailsTable');
  	 var lastRow = tbl.rows.length;
	 var row=tbl.insertRow(lastRow);
	 
	 var cellRight = row.insertCell(0);
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.style.width = "54%";
	 el.style.fontSize = "100%";
	 el.id = 'attName'+attNo;
	 el.name = 'attName';
	 el.className="txtfldM";
	 el.setAttribute("onblur","setPrintERSName(this.value,"+attNo+")");
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(1);
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.style.width = "54%";
	 el.style.fontSize = "100%";
	 el.id = 'attErsName'+attNo;
	 el.name = 'attErsName';
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 cellRight.appendChild(el);
	 
	 var i=0
	 var cellRight = row.insertCell(2);
	 var el = document.createElement('select');
	 el.style.width = "100%";
	 el.style.fontSize = "100%";
	 el.id = 'attGender'+attNo;
	 el.name = 'attGender';
	 el.options[i]=new Option("Select","");
	 el.className="comboauto";
	 $.each(msg.gender,function(id, name){
	    i=i+1;
		el.options[i]=new Option(name,id);
						
	  });	
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.style.width = "100%";
	 el.style.fontSize = "100%";
	 el.id = 'attDob'+attNo;
	 el.name = 'attDob';
	 el.className="txtfldM";
	 cellRight.appendChild(el);
	 
	 
	var isTatkalFlagValue=0;
	var travelMode=$('#travelMode').val();
	
	if(travelMode==0){
		isTatkalFlagValue=document.getElementById("isTatkalFlag").value;	
	}
	if(travelMode==2){
		isTatkalFlagValue=document.getElementById("isTatkalFlagMixed").value;	
	}
		
	if(isTatkalFlagValue==1)
	{
		 var cellRight = row.insertCell(4);
		 var el = document.createElement('input');
		 el.type = 'checkbox';
		 el.id = 'validIdCardAttCheck'+attNo;
		 el.name = 'validIdCardAttCheck';
		 el.value =attNo;
		 el.className="validIdCardAttCheckClass";
		 cellRight.appendChild(el);
		
	}
	 
	 
	 $("#finalAttendentCount").val(attNo);
	  $("#attDob"+attNo).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		maxDate:0,
		    	onShow : function () {
						 $("#attDob"+attNo).val("");
					 }
			 });
	
}

function calAge(){
	var attendntDob = $("#attDob" + attNo).val();

	var arrDob = attendntDob.split("/");

	var arrDate = arrDob[0];
	var arrMonth = arrDob[1];
	var arrYear = arrDob[2];

	var dob = new Date(arrYear+"-"+arrMonth+"-"+arrDate);
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1; 
    var day = currentDate.getDate();
    
    let ntoday = new Date(year+"-"+month+"-"+day);

    var age = (ntoday-dob) / (365.25 * 24 * 60 * 60 * 1000);

	return age>60;
}



function delAttRow()
{
	var rowCount = $('#attDetailsTable tr').length;
	
  	attNo--;
    if(rowCount==1)
    {
    	attNo=0;
     	$("#finalAttendentCount").val(attNo);
     	$("#attendedDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Attendant" class="butn" id="" onclick="setAttendedAllowedDiv();"/> </td></tr></table>');
    }
    else
    {
		if(($("#TRRule").val() == "TR1000281")){	
					
		$("#attName" + attNo).removeAttr("readonly").css("background-color", "#fff");
					
		$("#attErsName" + attNo).removeAttr("readonly").css("background-color", "#fff");

		$("#attGender" + attNo).removeClass("no-dropdown").css("background-color", "#fff");

		$("#attDob" + attNo).removeClass("no-dropdown").css("background-color", "#fff");
			
							$("#phsclyHandicpd").prop("disabled", false);

		 }
							
    	$('#attDetailsTable tr:last').remove();
     	$("#finalAttendentCount").val(attNo);
   		
 
    }
}

function setJournyDetailsForm(jrnyType, msg){
	
	var reqType=$("#reqType").val();
	var serviceType=$("#serviceType").val();
	var AttendentCount = msg.attendentCount;
	var isPartyBooking = msg.isPartyBooking;
	
	var trRuleId=$('#TRRule :selected').val();
	var options = '<option value="">Select</option>'; 
	$.each(msg.entiltedClass,function(id, name){ 
		options += '<option value="' + id + '">' +name + '<\/option>';
	});
	
	var airClassOptions = '<option value="">Select</option>';
	$.each(msg.airEntiltedClass,function(id, name){ 
		airClassOptions += '<option value="' + id + '">' +name + '<\/option>';
	});
					

	$.ajax({
			  url: $("#context_path").val()+"mb/journyDtlsAjax",
      		  type: "get",
      	 	  data: "jrnyType="+jrnyType+"&reqType="+reqType+"&serviceType="+serviceType,
      		  dataType: "text",
      		  async :false,
		      success: function(msgHtml)
		      {
		      	if(parseInt(jrnyType)==0){
		      		
				     $("#onward_booking_id").html(msgHtml);
				     $("#onward_booking_header_id").show();
				     $("#trnsrchDiv").show();
				
				     if(msg.familyDetail!=null){
						setFamilyDetails(msg);
					 }
				
					 if(msg.travelMode!=null){
						setTravelMode(msg);
					 }
				
					 if(AttendentCount!="" && AttendentCount>0){
					 	
						$("#isAttendentBooking").val("Yes");	
						$("#maxattCount").val(AttendentCount);		
						
						attNo=0;
						
						var addAttDetails='<input type="button" value="Add Attendant" class="butn" id="" onclick="setAttendedAllowedDiv();"/>'
						$("#attendedDetails").html(addAttDetails);
						$("#attendedDetails").show();
						
						
					 }
				
					if(isPartyBooking!="" && isPartyBooking=='Yes'){
						
						partyDependentNo=0;
						
						var higherClassAllowed = msg.depHigherClassAllowed;
						
						$("#isPartyBooking").val(isPartyBooking); 
						$("#depHigherClassAllowed").val(higherClassAllowed); 
						
					
						var partyDetailsHtml='<input type="button" value="Add Party Dependent" class="butn" id="" onclick="setPartyDependentAllowedDiv();"/>'
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
				   
		      		
						if (trRuleId == 'TR100043') {
							var breakJrnyDivHtml = '<td>Break Journey</td><td colspan="3"><input type="checkbox" name="breakJourney" id="breakJourney" onclick="this.checked ? trainListPopupForBreakJrny():handleUncheckToHideDiv();"/></td>';
							$("#breakJourneyDivId").append(breakJrnyDivHtml);
						}
		      		
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
		
					  if(AttendentCount!="" && AttendentCount>0){
					  	
						$("#isAttendentBooking").val("Yes");	
						$("#maxattCount").val(AttendentCount);		
						
						returnAttNo=0;
						var addReturnAttDetails='<input type="button" value="Add Attendant" class="butn" id="" onclick="setReturnAttendedAllowedDiv(\'' +msg +'\');"/>'
						$("#returnAttendedDetails").html(addReturnAttDetails);
						$("#returnAttendedDetails").show();
					  }
		
					 if(isPartyBooking!="" && isPartyBooking=='Yes'){
						
						returnPartyDependentNo=0;
						
						var higherClassAllowed = msg.depHigherClassAllowed;
						
						$("#isPartyBooking").val(isPartyBooking); 
						$("#depHigherClassAllowed").val(higherClassAllowed); 
						
					
						var partyDetailsHtml='<input type="button" value="Add Party Dependent" class="butn" id="" onclick="setReturnPartyDependentAllowedDiv(\'' +msg +'\');"/>'
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
		
					if (trRuleId == 'TR100043') {
					
							var returnBreakJrnyDivHtml = '<td>Break Journey</td><td colspan="3"><input type="checkbox" name="returnBreakJourney" id="returnBreakJourney" onclick="this.checked ? ReturnTrainListPopupForBreakJrny():handleReturnUncheckToHideDiv();"/>';
							$("#returnBreakJourneyDivId").append(returnBreakJrnyDivHtml);
						}	
		
		      	}
            
       	 }
     });
}

function ViewReason(Reason){
  alert(Reason);
}


function getTatkalTicketInstruction()
{
	
	var tatkalInfoHtml="";
	tatkalInfoHtml+="<table width='100%' class='formtxt'";
	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="<b>Instructions For Tatkal Tickets</b>";
	tatkalInfoHtml+="</td>";
	tatkalInfoHtml+="</tr>";
	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="<font color='blue'>Please Read Instructions Carefully:-";
	tatkalInfoHtml+="</font>";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";
	
	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="1:-Tatkal ticket will be booked  only for <b>Four</b> passenger.";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";

	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="2:-Tatkal  request will be divided based out four passenger in which at least one passenger holds a valid identity proof.";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";	
	
	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="3:-Booking for tatkal request will take input for Valid Identity Proof.";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";	
	
	
	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="4:-Passenger(s) should carry Valid Identity Proof during journey.";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";	

	tatkalInfoHtml+="<tr>";
	tatkalInfoHtml+="<td align='left' colspan='2'>";
	tatkalInfoHtml+="5:-Tatkal ARP reduced to one day instead of two days.";
	tatkalInfoHtml+="</td>"; 
	tatkalInfoHtml+="</tr>";	
	
	
	tatkalInfoHtml+="</table>";
		
	return tatkalInfoHtml;	
}


 function fillFrmStn(thisValue) {
	 $('#fromStation').val(thisValue);
	 setTimeout("$('#suggestions').hide();", 200);
 }

function fillToStn(thisValue) {
	let stnToVal=thisValue;
	 $('#toStation').val(stnToVal);
	 setTimeout("$('#suggestionsTo').hide();", 200);
 
 }
 
 function fillDAStrn(thisValue,grade){
	 
	 $('#destinationCity').val(thisValue);
	 $('#destinationCityGrade').val(grade);
	 setTimeout("$('#suggestionsDestCity').hide();", 200);
 }
 
 
function setToFrmStatnByTrRule(msg,ramBaanCheck)
{
	var TravelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var tadaPermission=$("#tadaPermission").val().trim();
	var ruletadaPermission=$("#ruletadaPermission").val().trim();
    
   
	var reqType=$("#reqType").val();
	if(reqType=='ramBaanBooking'){
		ramBaanCheck=true;
	}else{
		ramBaanCheck=false;
	}
	
	var frmoptions="";
	var tooptions="";
	var destStn="";
	
	var taggCheck=$('#taggCheck').val();
	var oldDutyStn=$("#oldDutyStn").val(); 
	var newDutyStn=$("#newDutyStn").val(); 
	var oldSprNRS=$("#oldSprNRS").val(); 
	var newSprNRS=$("#newSprNRS").val(); 
	
	//alert("setToFrmStatnByTrRule:taggCheck-"+taggCheck+"|oldDutyStn-"+oldDutyStn+"|newDutyStn-"+newDutyStn+"|oldSprNRS-"+oldSprNRS+"|newSprNRS-"+newSprNRS);
	
	var taggedLtcYear='';
	if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 

	if(msg=="")
	{
		$("#fromStation").val(""); 
		$("#toStation").val(""); 
		$("#trFrmStn").html("<label/>"); 
		$("#trToStn").html("<label/>"); 
		msg = $.parseJSON($('#travelerObj').val());
	}
	
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();

    var x=0;
     $.each(msg.familyDetail, function(i,obj){
	
		
			
			if(travelTypeText.indexOf('LTC')>-1)
			{
			var JourneyCheck;
			var JourneyCheckRet;
		    
		    if($('#removeFrmList'+i).is(":checked"))
		    {
				
					$("#requisiteDiv").show();
					$("#reqstCheck").val("true")
				
			}
			
				if(taggCheck!="true")
				{
					var k=0;
					 $.each(obj.yearWise, function(index,objYear){
					
				
						var xmlLtcYear=objYear.year;
				
						if(taggedLtcYear!='')
						{
							if(xmlLtcYear==taggedLtcYear)
							{
								
								JourneyCheck = objYear.journeyCheck;
								JourneyCheckRet = objYear.journeyCheckRet;
								//alert("xmlLtcYear==taggedLtcYear K["+k+"] JourneyCheck-"+JourneyCheck+",JourneyCheckRet-"+JourneyCheckRet+",value-"+value+"||xmlLtcYear-"+xmlLtcYear+"||taggedLtcYear-"+taggedLtcYear);
								
					
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
					
					} //End of if(taggedLtcYear!='')
					else
					{
						if(k==1)	
						{
							JourneyCheck = objYear.journeyCheck; 
							JourneyCheckRet = objYear.journeyCheckRet; 
							//alert("K["+k+"] JourneyCheck-"+JourneyCheck+",JourneyCheckRet-"+JourneyCheckRet+",value-"+value);
							
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
					} //End of else case
				
					k=k+1;
				
				}); //End of YearWise loop
				
			}// end of if(taggCheck!="true")
			{
				//alert ("else of if(taggCheck!=true)");
				var k=0;
				 $.each(obj.yearWise, function(index,objYear){
				
					var xmlLtcYear=objYear.year;
					
					if(taggedLtcYear!='')
					{
						if(xmlLtcYear==taggedLtcYear)
						{
							JourneyCheck = objYear.journeyCheck;
							JourneyCheckRet = objYear.journeyCheckRet;
							//alert("xmlLtcYear==taggedLtcYear K["+k+"]taggCheck["+taggCheck+"]JourneyCheck-"+JourneyCheck+",JourneyCheckRet-"+JourneyCheckRet+",value-"+value+"||xmlLtcYear-"+xmlLtcYear+"||taggedLtcYear-"+taggedLtcYear);
							
								if(JourneyCheck=='true')
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="1" onclick="setValue('+ i+')"/>')
	 								$("#reasonDiv"+i).hide();
								}else
								{
									$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"  id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#reasonDiv"+i).show();
								}
							
							
						}
					
					} //End of if(taggedLtcYear!='')
					
				
					k=k+1;
				
				}); //End of YearWise loop
				
			}
				
		 } // end of LTC Case if(travelTypeText.indexOf('LTC')>-1  && taggCheck!="true")		
		else if(travelTypeText.indexOf('FORMD')>-1 || travelTypeText.indexOf('FORMG')>-1)
			{
				var formDAndGYear=document.getElementById("formDAndGYear").value; 
				var YearWiseFormDAndG = obj.yearWiseFormDAndG;
				
				if(formDAndGYear==YearWiseFormDAndG)
					{
						
							var JourneyCheck;
							var JourneyCheckRet;
						    
						    if($('#removeFrmList'+x).is(":checked"))
						    {
								
									$("#requisiteDiv").show();
									$("#reqstCheck").val("true")
							
							}
				 
				 
						//alert("["+i+"]inside the When journey is not ltc and tagcheck-"+taggCheck);
						JourneyCheck=obj.journeyCheck;
						//alert("["+i+"]inside the else case JourneyCheck-"+JourneyCheck);
						
						if(JourneyCheck=='true')
						{
								//alert("["+i+"]inside the else case true-"+$("#reasonDiv"+i));
								$("#removeFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="removeFrmList' +x+ '" name="removeFrmList' +x+ '" value="1" onclick="setValue('+ x+')"/>');
				     			$("#reasonDiv"+x).hide();
						}
						else
						{
								//alert("["+i+"]inside the else case false-"+$("#reasonDiv"+i));
								$("#removeFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="removeFrmList' +x+ '" name="removeFrmList' +x+ '" value="0" onclick="return false" onkeyup="return false" />');
		 						$("#reasonDiv"+x).show();
								//$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
						}
						x=x+1;
					}
		 }		
		 else
		 {
				var JourneyCheck;
				var JourneyCheckRet;
			    
			    if($('#removeFrmList'+i).is(":checked"))
			    {
					
						$("#requisiteDiv").show();
						$("#reqstCheck").val("true")
					
				}
							
				//alert("["+i+"]inside the When journey is not ltc and tagcheck-"+taggCheck);
				JourneyCheck=obj.journeyCheck;
				//alert("["+i+"]inside the else case JourneyCheck-"+JourneyCheck);
				
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
						//$("#removeFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="removeFrmList' +i+ '" name="removeFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
				}
		 }
				 
	}); //End of FamilyDetailSeq loop

	//if(ramBaanCheck!="true")
	if(ramBaanCheck!=true)
	{
	
       var oldReq=document.getElementsByName("oldRequest")

       //alert("ramBaanCheck!=true oldReq="+oldReq+"oldReq.length-"+oldReq.length);

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
		         checkRadio=true
		         //alert("i["+i+"]When any Tagged Request Selected||authorityNo="+authorityNo+"||authority date="+authorityDate+"||ltcYear="+ltcYear+"||destStn="+destStn)
	        }
	   }
	   

		frmoptions += '<select id="fromStationDD" name="fromStationDD" onchange="setFrmStnValue(this.value)" class="combo"><option value="">Select</option>'
		
		 $.each(msg.fromStationList, function(index,obj){
			if(obj.jrnyType==0){
		
			var FromStation = obj.fromStation;
			var FromStationType = obj.fromStationType;
			//alert("FromStation="+FromStation+",FromStationType-"+FromStationType);
			
			if(FromStationType=="New Duty Station" && newDutyStn!=undefined)
			frmoptions += '<option value="' + newDutyStn + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="New SPR" && newSprNRS!=undefined)
			frmoptions += '<option value="' + newSprNRS + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="Old SPR" && oldSprNRS!=undefined)
			frmoptions += '<option value="' + oldSprNRS + '">' +FromStationType + '<\/option>';
			else if(FromStationType=="Old Duty Station" && oldDutyStn!=undefined)
			frmoptions += '<option value="' + oldDutyStn + '">' +FromStationType + '<\/option>';
			else
			frmoptions += '<option value="' + FromStation + '">' +FromStationType + '<\/option>';
			}
		}); 
		
		//alert("From Station List for Value="+value+"--"+frmoptions)
		if(frmoptions=='<select class="combo">'){
			frmoptions = 'No From Station Exists In Profile';
		}else{
          frmoptions += '</select>'
		}              
        $("#frmStnDropDown").html(frmoptions);
       
       
        if(!(frmoptions.indexOf('AnyStation') > -1))
        {
       
       		$("#frmStnDropDown").show();       
       
             
        }else{
       		$("#frmStnDropDown").show();       
       	}
             
        
        tooptions += '<select id="toStationDD" name="toStationDD" onchange="setToStnValue(this.value)" class="combo"><option value="">Select</option>'
        
         $.each(msg.toStationList, function(index,obj){
			if(obj.jrnyType==0){
			
			var ToStation = obj.toStation;
			var ToStationType = obj.toStationType;
			//alert("ToStation="+ToStation+",ToStationType-"+ToStationType);
			//if(ToStation!="" && ToStation!=null)
			if(ToStationType=="New Duty Station" && newDutyStn!=undefined)
			tooptions += '<option value="' + newDutyStn + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="New SPR" && newSprNRS!=undefined)
			tooptions += '<option value="' + newSprNRS + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="Old SPR" && oldSprNRS!=undefined)
			tooptions += '<option value="' + oldSprNRS + '">' +ToStationType + '<\/option>';
			else if(ToStationType=="Old Duty Station" && oldDutyStn!=undefined)
			tooptions += '<option value="' + oldDutyStn + '">' +ToStationType + '<\/option>';
			else
			tooptions += '<option value="' + ToStation + '">' +ToStationType + '<\/option>';
			}
	 	}); 
			
	
		//alert("To Station List for Value="+value+"--"+tooptions)
	
	 	if(tooptions=='<select class="combo">'){
 			tooptions = 'No TO Station Exists In Profile';
     	}else{
        	tooptions +="</select>"
     	}
     	
      	$("#toStnDropDown").html(tooptions);  
      
        if(!(tooptions.indexOf('AnyStation') > -1))
        {      
      	if(travelTypeText.indexOf('LTC-AI')>-1  && taggCheck!="true"){
      	 	setToStnValue(destStn)
          	$("#toStnDropDown").hide();
      	}else{
       		$("#toStnDropDown").show();
	  	}
		}else{
       		$("#toStnDropDown").show();
	  	}
					  
	}	// End of if(ramBaanCheck!="true")		  
	 

}


 
 function setPartyDependentAllowedDiv()
{
     let flag = validateFieldsOnwardPartyBkgRequest(); /* Party Booking Validation */
	    if(flag){
		partyDependentNo++;
    
     	var partyDetailsInnerHtml="";
  		var isTatkalFlagValue=0;
		var travelMode=$('#travelMode').val();
		
		if(travelMode==0){
			isTatkalFlagValue=document.getElementById("isTatkalFlag").value;	
		}
		if(travelMode==2){
			isTatkalFlagValue=document.getElementById("isTatkalFlagMixed").value;	
		}
		if(isTatkalFlagValue==1)
		{
			partyDetailsInnerHtml+='<table  border="0" cellpadding="4" cellspacing="1" width="100%%" class="formtxt">'
			partyDetailsInnerHtml+='<tr align="left"><td colspan="6"><b>Party Group Details</b></td></tr>'
		}else{
			partyDetailsInnerHtml+='<table  border="0" cellpadding="4" cellspacing="1" width="100%%" class="formtxt">'
			partyDetailsInnerHtml+='<tr align="left"><td colspan="5"><b>Party Group Details</b></td></tr>'
		}
		
		partyDetailsInnerHtml+='<tr>'
		partyDetailsInnerHtml+='<td  align="left" width="20%">Personal Number</td>'
		partyDetailsInnerHtml+='<td  align="left" width="25%">Name</td>'
        partyDetailsInnerHtml+='<td  align="left" width="25%">ERS Name</td>'
        partyDetailsInnerHtml+='<td  align="left" width="10%">Gender</td>'
			      	
		if(isTatkalFlagValue==1)
		{
			partyDetailsInnerHtml+='<td  align="left" width="10%">Date Of Birth</td>'
			partyDetailsInnerHtml+='<td  align="left" width="10%">Id Proof</td></tr></table>'
			
		}else
		{
			partyDetailsInnerHtml+='<td  align="left" width="20%">Date Of Birth</td></tr></table>'
		}
		
        partyDetailsInnerHtml+='<div id="partyDependentValueDiv">'
        partyDetailsInnerHtml+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" id="attPartyDetailsTable" name="attPartyDetailsTable">'
        partyDetailsInnerHtml+='<tr><td width="20%" align="left" class="lablevalue"><input class="txtfldM" type="text" size="20" id="dependentPersonalNo'+partyDependentNo+'" name="dependentPersonalNo" onchange="setGetPersonalDetails(this.value,'+partyDependentNo+',0)"/></td>'
        partyDetailsInnerHtml+='<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="partyDepName'+partyDependentNo+'" name="partyDepName" readonly/></td>'
		partyDetailsInnerHtml+='<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="partyDepErsName'+partyDependentNo+'" name="partyDepErsName" readonly/></td>'
		partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input  type="text" size="5"  id="partyDepGender'+partyDependentNo+'" name="partyDepGender" readonly></td>'
					
        if(isTatkalFlagValue==1)
		{
			partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input  type="text" size="10"  id="partyDepDOB'+partyDependentNo+'" name="partyDepDOB" readonly></td>'
			partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input type="checkbox" class="validIdCardPartyGroupCheck" id="validIdCardPartyGroupCheck'+partyDependentNo+'" value="'+partyDependentNo+'" name="validIdCardPartyGroupCheck"/></td></tr>'
			
		}else{
			partyDetailsInnerHtml+='<td width="20%" align="left" class="lablevalue"><input  type="text" size="10"  id="partyDepDOB'+partyDependentNo+'" name="partyDepDOB" readonly></td></tr>'
		}
                 
	    partyDetailsInnerHtml+='</table>'
	    partyDetailsInnerHtml+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" >'
	    partyDetailsInnerHtml+='<tr><td colspan="4" align="right"><input type="button" value="Add Row" class="butn" onclick="addPartyDependentRows();"/>&nbsp;<input type="button" class="butn" value="Delete Row" onclick="delPartyDependentRow();"/></td></tr>'
	    partyDetailsInnerHtml+='</table></div>'
	   
	    
	    $("#partyDependentDetails").html(partyDetailsInnerHtml);
	    $("#partyMemberCount").val(partyDependentNo);

	    }

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
	
	//var isTatkalFlagValue=document.getElementById("isTatkalFlag").value;
	var isTatkalFlagValue=0;
	var travelMode=$('#travelMode').val();
	
	if(travelMode==0){
		isTatkalFlagValue=document.getElementById("isTatkalFlag").value;	
	}
	if(travelMode==2){
		isTatkalFlagValue=document.getElementById("isTatkalFlagMixed").value;	
	}
			
	if(isTatkalFlagValue==1)
	{
	 var cellRight = row.insertCell(5);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'checkbox';
	 el.id = 'validIdCardPartyGroupCheck'+partyDependentNo;
	 el.name= 'validIdCardPartyGroupCheck';
	 el.setAttribute("readonly","readonly");
	 el.size = 10;
	 el.value =partyDependentNo;
	 el.className="validIdCardPartyGroupCheck";
	 cellRight.appendChild(el);
	 
	}
	
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
     	$("#partyDependentDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Party Dependent" class="butn" id="" onclick="setPartyDependentAllowedDiv();"/></td></tr></table>');
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
			selEntitledClass=$('#entitledClass').val();
		}else{
			selEntitledClass=$('#returnEntitledClass').val();
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
			return false;
		}
		
		
		
		$.ajax({
			
	      url: $("#context_path").val()+"mb/getPartyVisitor",
	      type: "get",
	      data: "personalNo="+personalNo+"&isPartyHigherClassAllowed="+isPartyHigherClassAllowed+"&selEntitledClass="+selEntitledClass+"&travelerUnitName="+travelerUnitName,
	      dataType: "json",
	      success: function(msg){
	 
	   	  if(msg.userID==null || msg.userID=="")
	   	  {
			alert($(msg).text());
			
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
	  		document.getElementById("partyDepName"+rowIndex).value=travelerName;
	  		document.getElementById("partyDepErsName"+rowIndex).value=travelerErsPrintName;
	  		document.getElementById("partyDepGender"+rowIndex).value=travelerGender;
	  		document.getElementById("partyDepDOB"+rowIndex).value=travelerDOB;
	  		}else{
			document.getElementById("returnPartyDepName"+rowIndex).value=travelerName;
	  		document.getElementById("returnPartyDepErsName"+rowIndex).value=travelerErsPrintName;
	  		document.getElementById("returnPartyDepGender"+rowIndex).value=travelerGender;
	  		document.getElementById("returnPartyDepDOB"+rowIndex).value=travelerDOB;
			}
	  		
	  	}
	  	

     }});

}


function fromStationList()	{
	
		var fromStation=$('#fromStation').val();
	
		if(fromStation.length>1)
		{
			var response="";
			var StationList="";
			
			$.ajax(
			{
				 url: $("#context_path").val()+"mb/getStationList",
			      type: "get",
			      data: "stationName="+fromStation,
			      dataType: "json",
			      success: function(msg){
				   $.each(msg, function(index,name){
			      	
					if(name=="Station Name Not Exist")
	                	StationList+='<li>' +name+'</li>';
	                else
	                    StationList+='<li onClick="fillFrmStn(\'' +name +'\')">' +name+'</li>';
	              });
	              
				  $("#autoSuggestionsList").html(StationList);		
	              $('#suggestions').show();
	                       
			    }
			});
		}
}



function toStationList(){ 
	

		var toStation=$('#toStation').val();
	
		if(toStation.length>1)
		{
			var response="";
			var StationList="";
	
			$.ajax(
			{
			  url: $("#context_path").val()+"mb/getStationList",
	    	  type: "get",
	      	  data: "stationName="+toStation,
	      	  dataType: "json",
		      success: function(msg)
		      {
	                  $.each(msg, function(index,name){
	                      if(name=="Station Name Not Exist")
		                  StationList+='<li>' +name+'</li>';
		                  else
		                  StationList+='<li onClick="fillToStn(\'' +name +'\')">' +name+'</li>';
	                  });
	                
					 $("#autoSuggestionsListTo").html(StationList);		
	                 $('#suggestionsTo').show();
	                       
	      	}
	  	  });
		}
	}
	

function destStationList(){ 
	

		var destCity=$('#destinationCity').val();
	
		if(destCity.length>1)
		{
			var response="";
			var cityList="";
	
			$.ajax(
			{
			  url: $("#context_path").val()+"mb/getCityList",
	    	  type: "get",
	      	  data: "city="+destCity,
	      	  dataType: "json",
		      success: function(msg)
		      {
	                  $.each(msg, function(index,name){
						
						
						var cityName = name.cityName;
						var cityGrade = name.cityGradeInt;
						
	                      if(cityName=="City Name Not Exist")
		                  cityList+='<li>' +cityName+'</li>';
		                  else
		                  cityList+='<li onClick="fillDAStrn(\'' +cityName +'\',\'' +cityGrade +'\')">' +cityName+'</li>';
	                  });
	                
					 $("#autoSuggestionsListforDestCity").html(cityList);		
	                 $('#suggestionsDestCity').show();
	                       
	      	}
	  	  });
		}
	}


function isTatkalSelected(chk)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
	if(chk.checked==true)
	{
		var tText=getTatkalTicketInstruction();
		var tatkalBox =  new LightFace({ 
		    width: "auto", 
		    height: 200, 
		    draggable: true, 
		    title: "Tatkal Instruction", 
		    content: tText, 
		    buttons: [ 
		        { title: "Close", event: function() { this.close(); }, color: "blue" } 
		    ], 
		    resetOnScroll: true 
		}); 
    	tatkalBox.open();
    	
		document.getElementById("isTatkalFlag").value="1";
	   	$('.validIdValCol').show();
	   	$('#validIdInfoCol').show();
	   	
	}
	else
	{
		document.getElementById("isTatkalFlag").value="0";
		$('.validIdValCol').hide();
	   	$('#validIdInfoCol').hide();
	   	
		 
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

function getStationCode(stnName)
{
	if(stnName.indexOf("(")!=(-1))
		stnCode=stnName.substring(stnName.indexOf("(")+1, stnName.indexOf(")"));
	else
		stnCode=stnName;
	
	return stnCode;
}

function changeDateFormat(date)
{
 	var dateArr=date.split("/");
 	return dateArr[0]+"-"+dateArr[1]+"-"+dateArr[2];
}


/* Function For Via-Route checked Stations  */
function viaRoutes() {
		//alert("viaRoute clicked");
    var viafields = ""
    var fromStn = "";
    var tostn = ""
    fromStn = $("input#fromStation").val()
    if (fromStn == undefined)
        fromStn = $("select#fromStation").val()
    tostn = $("input#toStation").val()
    if (tostn == undefined)
        tostn = $("select#toStation").val()
		$("#viaDiv").show();
    if (validateViaFields()) {
        if ($('#viaRoute').is(":checked")) {
            //alert("Via-route checkec :::: ");
            if (!validateViaRouteStations(fromStn, tostn)) {
                if (validateViaBooking()) {
                    $("#fromStation").attr('disabled', 'disabled');
                    $("#toStation").attr('disabled', 'disabled');

		    		//alert("vai route validation is passed");
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
                    viafields += '<tr><td colspan="4" class="lablevalue">Via Route</td></tr>'
                    viafields += '<tr><td align="center" style="width:27.75%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%"></td></tr>'
                    viafields += '</table>'
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="routeTable">'
                    viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation0" name="frmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
                    viafields += '<td align="left"><input type="text" class="txtfldM" id="toStation0" name="toStation0" autocomplete="off" onkeyup="getStationList(this.value,this.id,0)" />'
                    viafields += '<div class="suggestionsBox" id="suggestionstoStation0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListtoStation0"></div></div></td>'
                    viafields += '</td>'
                    viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate0" name="journeyDate0" autocomplete="off"></td>'
                    viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(0)"/></td></tr>'
                    viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation1" name="frmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
                    viafields += '<div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
                    viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="toStation1" name="toStation1" readonly autocomplete="off"  /><div class="suggestionsBox" id="suggestionstoStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListtoStation1"></div></div></td>'
                    viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate1" name="journeyDate1" autocomplete="off"></td>'
                    viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(1)"/></td></tr>'
                    viafields += '</table>';
                    viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addRow(\'' + tostn + '\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delRow(\'' + tostn + '\');"/></td></tr></table></div>'
		  		   $("#viaDiv").show("fast");
		   		   $("#viaDiv").html(viafields);
	    $('#journeyDate0').datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        scrollInput: false,
                        viewDate: $('#travelStartDate').val(),
                        defaultDate: $('#travelStartDate').val(),
		yearEnd: 2100,
                        onShow: function(input, inst) {
			$('#journeyDate0').val("");     
			
	 	
                            minData = $('#journeyDate').val();
                            maxData = $('#travelEndDate').val();
			 $("#journeyDate0").datetimepicker("setOptions", {
                                minDate: minData
                            });
					$("#journeyDate0").datetimepicker("setOptions", {
                                maxDate: maxData
                            });
					 }
				  });
		$('#journeyDate1').datetimepicker({
                        lang: 'en',
                        timepicker: false,
                        format: 'd/m/Y',
                        formatDate: 'd/m/Y',
                        scrollMonth: false,
                        scrollInput: false,
                        viewDate: $('#travelStartDate').val(),
                        defaultDate: $('#travelStartDate').val(),
		yearEnd: 2100,
                        onShow: function() {
			$('#journeyDate1').val("");
                            minData = $('#journeyDate').val();
                            maxData = $('#travelEndDate').val();
			 $("#journeyDate1").datetimepicker("setOptions", {
                                minDate: minData
                            });
					$("#journeyDate1").datetimepicker("setOptions", {
                                maxDate: maxData
                            });
					 }
				   });
				}
            } else {
                $('#viaRoute').attr('checked', false);
					 $('#viaValidate').val("true");
		 		}
        } else {
            //alert("Via-route un-checked :::: ");
            $("#fromStation").prop('disabled', false);
            $("#toStation").prop('disabled', false);
			}
    } else {
			$("#viaDiv").hide("fast");
		}
    if (!$('#viaRoute').is(":checked")) {
        //alert("Via-route un-checked under if condition :::: ");
			$("input#fromStation").prop("readonly", false);
			$("input#toStation").prop("readonly", false);
			$("#viaDiv").hide("fast");
    }
}




/* Function value to check onward Via-Route Available */
function validateViaRouteStations(fromStn, tostn) {
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
          // alert(" ::: Station not available :::: JSON Object ::::: "+msg);
            if (msg.length === 0) {
                //alert("No stations found for the selected via-route.");
                $('#viaRoute').attr('checked', false);
                validationFlag = false;
            } else {
                validationFlag = true;
                $("#fromStation").attr('disabled', 'disabled');
                $("#toStation").attr('disabled', 'disabled');


                var viafields = '';
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
                viafields += '<tr><td colspan="4" class="lablevalue">Via Route</td></tr>'
                viafields += '<tr><td align="center" style="width:27.75%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%"></td></tr>'
                viafields += '</table>'
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="routeTable">'
                viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation0" name="frmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
                viafields += '<td align="left" id="viaStationsDropDwn">'
                viafields += '</td>'
                viafields += '</td>'
                viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate0" name="journeyDate0" autocomplete="off"></td>'
                viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(0)"/></td></tr>'
                viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation1" name="frmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
                viafields += '<div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
                viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="toStation1" name="toStation1" readonly autocomplete="off"  /><div class="suggestionsBox" id="suggestionstoStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListtoStation1"></div></div></td>'
                viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate1" name="journeyDate1" autocomplete="off"></td>'
                viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(1)"/></td></tr>'
                viafields += '</table>';
                viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addRow(\'' + tostn + '\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delRow(\'' + tostn + '\');"/></td></tr></table></div>'
                $("#viaDiv").show("fast");
                $("#viaDiv").html(viafields);
                $('#journeyDate0').datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
                    scrollInput: false,
                    viewDate: $('#travelStartDate').val(),
                    defaultDate: $('#travelStartDate').val(),
                    yearEnd: 2100,
                    onShow: function(input, inst) {
                        $('#journeyDate0').val("");
                        minData = $('#travelStartDate').val();
                        maxData = $('#travelEndDate').val();
                        $("#journeyDate0").datetimepicker("setOptions", {
                            minDate: minData
                        });
                        $("#journeyDate0").datetimepicker("setOptions", {
                            maxDate: maxData
                        });
                    }
                });
                $('#journeyDate1').datetimepicker({
                    lang: 'en',
                    timepicker: false,
                    format: 'd/m/Y',
                    formatDate: 'd/m/Y',
                    scrollMonth: false,
                    scrollInput: false,
                    viewDate: $('#travelStartDate').val(),
                    defaultDate: $('#travelStartDate').val(),
                    yearEnd: 2100,
                    onShow: function() {
                        $('#journeyDate1').val("");
                        minData = $('#travelStartDate').val();
                        maxData = $('#travelEndDate').val();
                        $("#journeyDate1").datetimepicker("setOptions", {
                            minDate: minData
                        });
                        $("#journeyDate1").datetimepicker("setOptions", {
                            maxDate: maxData
                        });
                    }
                });
                var options = '';
                options += '<select id="toStation0" class="combo" name="toStation0" onchange = fillFromStation(this.value)>';
                options += '<option value="">Select</option>';
                $.each(msg, function(index, value) {
                    console.log("Adding option:", value);
                    options += `<option value="${value}">${value}</option>`;
                });
                options += '</select>';
                $("#viaStationsDropDwn").html(options);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error fetching data:", error);
            alert("Failed to fetch via route stations. Please try again.");
        }
    });
    return validationFlag;
}



/*  Function to FillStation Value */
function fillFromStation(stationValue) {
   var viaToStation  = $("#toStation0").val();
   var viaFromStation = $("#frmStation1").val();

   if(viaToStation != '' && viaToStation != undefined){
   $("#frmStation1").val('');
   $("#frmStation1").val(viaToStation);
   $("#frmStation1").attr("readonly","readonly");
   }
}



 
function validateViaFields()
{     	
	
		var check="";
	  if(document.getElementById("clusteredRoute")!=null )
        {
	        if(document.getElementById("clusteredRoute").checked==true)
	        {
	        	alert("You can Either select Via Route or Clustered Route")
	        	document.getElementById("clusteredRoute").checked=false;
	        	document.getElementById("viaRoute").checked=false;
	        	$("#clusteredDiv").hide();
	        	return false;
	       	}
        }
		
        
        if(document.getElementById("viaRelxRoute")!=null ){
	        if(document.getElementById("viaRelxRoute").checked==true)
	        {
	        	alert("You can Either select Via Route or Relaxed Route")
	        	document.getElementById("viaRoute").checked=false;
	        	return false;
	       	}
        }
        
       if(document.getElementById("journeyDate").value=="dd/mm/yyyy" || document.getElementById("journeyDate").value=="" )
	   {
			alert("Please Enter Journey Date")
			document.getElementById("journeyDate").focus();
        	document.getElementById("viaRoute").checked=false;
	    	return false;
	   }
	  	if(document.getElementById("fromStation").value=="")
	  	{
	   		alert("Please Enter From Station And To Station Fields");
	   		document.getElementById("viaRoute").checked=false;
		    return false;
	   }
	   if(document.getElementById("toStation").value=="")
	   {
	   		alert("Please Enter From Station And To Station Fields");
		    document.getElementById("viaRoute").checked=false;
		    return false;
	   }
	    

	   check=validate_required(document.getElementById("entitledClass"),"Please Select Entitled Class");
	   if(!check)
	   document.getElementById("viaRoute").checked=false;
	   
	   return check;
	   
} 


function validateViaBooking()
{
	
     
	     if(document.getElementById("breakJourney")!=null )
        {
	        if(document.getElementById("breakJourney").checked==true)
	        {
	        	alert("You can Either select Break Journey or Via Route");
	        	$("#selectedTrainNo").val("");
				$("#fromStation").prop('disabled', false);
				$("#toStation").prop('disabled', false);
				$("input#fromStation").prop("readonly", false);
				$("input#toStation").prop("readonly", false);
	        	document.getElementById("breakJourney").checked=false;
	        	document.getElementById("viaRoute").checked=false;
	        	
	        	$("#breakJrnyDiv").hide();
	        	return false;
	       	}
        }
     
    var frmStn=$('#fromStation').val();
	var toStn=$('#toStation').val();
	var journeyDate=$('#journeyDate').val();
	var reqType=$('#reqType').val();
	var entitledClass=$('#entitledClass :selected').val();
	var response="";
    var viaRoute="";
    var check=false;
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	$('#viaValidate').val("false");

	//
     
	 
     
	$.ajax({
		
      url: $("#context_path").val()+"mb/getTrainListDetails",
      type: "get",
      data: "frmStation="+ frmStn +"&toStation=" + toStn +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass ,
      dataType: "json",
      async: false,
     
      beforeSend: function() 
      {
      		$("#viaDiv").html("Searching Direct Trains From["+ frmStn +"] To ["+ toStn +"]... Please wait ...");
			var calHeight=document.body.offsetHeight+20;
			$("#screen-freeze").css({"height":calHeight + "px"} );
			$("#screen-freeze").css("display", "block");
      },
  			 
      complete: function(){
  		 //	trainWaitBox.close();
  		 	$("#viaDiv").html("");
  		 	$("#screen-freeze").css("display", "none");
      },

      success: function(msg)
      {

          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=null)
             errordesc=msg.errorMessage.toUpperCase();
          
          var errorCode=msg.errorMessage;
           
         // alert(errorCode+"||errordesc-"+errordesc+"||"+errorDescptrn);
                     
           //--  added by pradeep end 
           if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
           {
           		check=true;
          		$('#viaValidate').val("true");
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
           }
           //--  added by pradeep end 
          
           if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 || errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004' || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
          	
              	check=true;
              	$('#viaValidate').val("true");
           }
           
          
           
           //alert(errordesc.indexOf('NO DIRECT TRAINS FOUND....'));
           if(errordesc.indexOf('NO DIRECT TRAINS AVAILABLE')>-1 || errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
           {
          	  	check=true;
              	$('#viaValidate').val("true");
           }

           else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
           {
            	alert(errorDescptrn);
            	$('#viaValidate').val("true");
          		$("#viaRoute").prop('checked', false);
           }	
           else
           {
          		alert("Via Booking Is Not Permmited As Train Exists Between This Route");
          		$('#viaValidate').val("true");
          		$("#viaRoute").prop('checked', false);
          		check=false;
           }
          	
      	  return check;
      }
      
   });
     
   $('#viaValidate').val("true");
       
   return check;
   
} 


var k=2;
function addRow(toStn)
{
		 
	if(k>2){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;
	}	 
	if(validateViaRouteTrain(k-1))
	{
	
    $('#toStation'+eval(k-1)).val("");
   
   $('#toStation' + (k - 1)).prop("readonly", false);
   



    var element = document.getElementById('toStation' + (k - 1)); 
    element.setAttribute('onkeyup', 'getStationList(this.value, this.id, 0);'); 
    

     
  	var tbl = document.getElementById('routeTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'frmStation'+k;
	 el.id = 'frmStation'+k;
	// el.size = 10;
	 el.className="txtfldM";
	 el.setAttribute("readonly","true");
	 
	 el.setAttribute('onkeyup',"getStationList(this.value,this.id,0);");
 	
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionsfrmStation'+k;
	 divEl.id = 'suggestionsfrmStation'+k;
	 divEl.className='suggestionsBox';
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListfrmStation'+k;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'toStation'+k;
	 el.id = 'toStation'+k;
	// el.size = 10;
	 el.className="txtfldM";
	 
	 el.setAttribute("readonly","true");
	 //el.setAttribute('onkeyup',"getStationList(this.value,this.id,0);");
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionstoStation'+k;
	 divEl.id = 'suggestionstoStation'+k;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListtoStation'+k;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
 	
 	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'journeyDate'+k;
	 el.id = 'journeyDate'+k;
	 el.setAttribute('value',"dd/mm/yyyy");
	 el.className="txtfldM";
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'button';
	 el.name = 'viaTrnSrch'+k;
	 el.id = 'viaTrnSrch'+k;
	 el.setAttribute('value',"Train Search");
	 el.className="butn";
	 el.setAttribute('onclick',"trainSearch('"+k+"');");
	 cellRight.appendChild(el);

	 $("#journeyDate"+k).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput : false,
		yearEnd : 2100, minDate: $('#travelStartDate').val(), maxDate: $('#travelEndDate').val(),
		 beforeShow : function () {
			 $(this).val("");
		 }
	});

     k++;
}
}
  

function delRow(tostn)
{   
   	
     var rowCount = $('#routeTable tr').length;
     
   
     if(rowCount>2){
     var row1=rowCount-2
     k--;
     
     $('#toStation'+row1).val(tostn)
     $('#toStation' + (k - 1)).prop("readonly", true);
    
    var element = document.getElementById('toStation' + (k - 1)); 
    element.removeAttribute('onkeyup');


  	  $('#routeTable tr:last').remove();
  	  
  	  }
  	 else
  	  alert("Minimum Two Rows Required For Via Booking")

}


function validateViaRouteTrain(index)
{
	var toIndex=index-1;
	var check=false;
	var frmStn=$('#frmStation'+index).val();
	var toStn=$('#toStation'+index).val();
	var journeyDate0=$('#journeyDate'+toIndex).val();
	var journeyDate=$('#journeyDate'+index).val();
	var reqType=$('#reqType').val();
	var entitledClass=$('#entitledClass :selected').val();
	var response="";
    var viaRoute="";
        
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#toStation'+index).focus();
	    return check;
   }
   
	if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#frmStation'+index).focus();
	    return check;
	 }
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDate'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#journeyDate'+index).focus();
    	return check;
   }
    
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
	 
	$.ajax({
		
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
	  complete: function()
	  {
	    $("#screen-freeze").css("display", "none");	
	  },
      success: function(msg)
      {
          var errorDescptrn=msg.errorMessage;
          var errordesc="";
          
          if(errorDescptrn!=undefined)
             errordesc=msg.errorMessage.toUpperCase();
          
           var errorCode=msg.errorMessage;
           if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
           {
           		check=true;
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
           }
          
           if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 ||  errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004' || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
              	check=true;
           }
           
           else if(errordesc.indexOf('NO DIRECT TRAINS AVAILABLE')>-1 || errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
           {
          	  	check=true;
           }
           else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
           {
            	alert(errorDescptrn);
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
  
function getStationList(value,id,jrnyType){
	if(value.length>1)
	{
		var response="";
		var StationList="";
		$.ajax(
		{
			url: $("#context_path").val()+"mb/getStationList",
      		type: "get",
       		data: "stationName="+value+"&ajaxCallType=getStationList",
      		dataType: "json",
      		success: function(msg)
      		{
                    $.each(msg, function(index,name){
					if(name=="Station Name Not Exist")
	                  StationList+='<li>' +name+'</li>';
	                else{
	                	if(jrnyType == 0){
	                	if(id == 'newDutyStn' || id == 'newSprNRS'){
	                	StationList+='<li onClick="fillStnOnPtTravel(\'' +name +'\',\'' +id +'\',\'' +jrnyType +'\')">' +name+'</li>';
	                	}else{
	                	StationList+='<li onClick="fillStn(\'' +name +'\',\'' +id +'\',\'' +jrnyType +'\')">' +name+'</li>';
	                	}
	                	}else if(jrnyType == 1){
	                	StationList+='<li onClick="fillReturnStn(\'' +name +'\',\'' +id +'\',\'' +jrnyType +'\')">' +name+'</li>';
	                	}
	                }

                  });

				    $('#autoSuggestionsList'+id).html(StationList);
                    $('#suggestions'+id).show();

  			},

  		});
  			}
}


function fillStnOnPtTravel(thisValue, idx, jrnyType) {
   if( idx == 'newDutyStn'){
   $('#newDutyStn').val(thisValue);
   }else if(idx == 'newSprNRS'){
   $('#newSprNRS').val(thisValue);
   }else{
	}
	setTimeout(() => {
		$('#suggestions' + idx).hide();
	}, 5);
}


function fillStn(thisValue,id,jrnyType) 
{
	//alert("Inside fillStn");	
	var idArr=id.split("toStation");
	var idNumber=parseInt(idArr[1])+1;
	$('#'+id).val(thisValue);
	setViaStnFrmToStn(thisValue,"frmStation"+idNumber)
	setTimeout("$('#suggestions"+id+"').hide();", 200);
}
function setViaStnFrmToStn(toStnVal,frmStnName)
{
	//alert("Inside setViaStnFrmToStn");	
   $('#'+frmStnName).val(toStnVal);
   $("#"+frmStnName).attr("readonly","readonly");
}

function trainListPopup()
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	//alert("Inside trainListPopup");
	var frmStn=$('#fromStation').val();
	var toStn=$('#toStation').val();
	if(validateViaFields())
	{
		frmStn=getStationCode(frmStn);
		toStn=getStationCode(toStn);
	 	var journeyDate=$('#journeyDate').val();
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



function trainSearch(i)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	//alert("Inside trainSearch");	
    var frmStn=$('#frmStation'+i).val();
    var toStn=$('#toStation'+i).val();
    if(validateViaFieldsForViaSearch(i))
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

function validateViaFieldsForViaSearch(i)
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
	  
	   check=validate_required(document.getElementById("entitledClass"),"Please Select EntitlesClass");
	   if(!check)
	   document.getElementById("viaRoute").checked=false;
	   
	   return check;
	   
} 	


function setValue(j)
{   
	//alert("Inside setValue");
   
    var relation=document.getElementById("relationCheck"+j).value;
    var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
    var reqType=$("#reqType").val();
    var travelMode=$('#travelMode').val();
    
    if(travelMode==1 && reqType=='international'){
		var onwordJourneyDate=$("#onwordJourneyDate").val();
    	if(onwordJourneyDate==""){
    		alert("Please select Journey Date");
    		$("#removeFrmList"+j).attr('checked', false);
    		return false;
    	}
    	setPassportDetails(j);
    }
    
    if(relation=="Self" && (travelTypeText=="LTC-AI" || travelTypeText=="LTC-HT")){
    
	    var fromStationDD=document.getElementById("from_Station_Id").value.toUpperCase();
	    var toStationDD=document.getElementById("to_Station_Id").value.toUpperCase();
		    
	    if(fromStationDD=="HOME TOWN" || fromStationDD=="SPR")
	    {
	    	alert("Cannot create an 'Onward Journey' from Hometown/SPR or 'Return journey' to Hometown/SPR for self");
	    	$("#removeFrmList0").prop('checked', false);
	    	document.getElementById("removeFrmList0").value=1;
	    	return false;
	    }
		    	
    }
    
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
	var isTatkalFlagValue=0;
	
	if(travelMode==0){
		isTatkalFlagValue=document.getElementById("isTatkalFlag").value;	
	}
	if(travelMode==1){
		if($("#airsrchDiv")!=null){
			$("#airsrchDiv").show();
		}
	}
	if(travelMode==2){
		isTatkalFlagValue=document.getElementById("isTatkalFlagMixed").value;	
	}
	
	
	//alert("Inside isTatkalFlagValue-"+isTatkalFlagValue);
	if(isTatkalFlagValue==1)
	{
		if($('#removeFrmList'+j).is(":checked"))
    	{
    		$("#validIdCardPassCheck"+j).attr("disabled", false);
		}
		else
		{
			$("#validIdCardPassCheck"+j).attr('checked', false);
			$("#validIdCardPassCheck"+j).attr("disabled", true);
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


function showDAPermissionQues(event){
  
      document.getElementById("lrc_check0").checked="";
      document.getElementById("lrc_check1").checked="";
  
	  if(document.getElementById("DAAdvanced").checked==true)
	  {
	  		  	
		   if(!$('#DA_Advanced_Confirm_One').is(':checked')){
		  	alert("Please confirm, no DA has been availed for this journey from any other source/Pay Accounts office.");
			    event.preventDefault();
			    return false;
		   }
		   
//		    if(!$('#DA_Advanced_Confirm_Two').is(':checked')){
//		  	alert("Please confirm your Bank Account Details and IFSC Code above are correct. If not, kindly contact your Pay Account Office to update the same.");
//			    event.preventDefault();
//			    return false;
//		   }
		  	 
	  	   //if($("#userAccDtlsUpdated").val()=="true"){
		 		$("#da_permission").show();
		 		$("#da_permission_ans").show();
//	 		}else{
//			    alert("Please contact your Pay Account Office to update your bank account details in DTS to get DA Advance.");
//			    event.preventDefault();
//		 	}
		 	
		 	if($("#travelID").val()!='1' && parseInt($("#travelDaCounts").val())>0){
		 		
			      $("#DAAdvanced1"). prop("checked", true);
			      $("#da_permission").hide();
			 	  $("#da_permission_ans").hide();
			 	  alert("You have already claimed DA Advanced, no more DA is allowed for this Travel ID");
			 	  event.preventDefault();
			      return false; 
		      }
	 		
		      $("#DA_Advanced_Confirm_One").css("pointer-events", "none");
		      $("#DA_Advanced_Confirm_Two").css("pointer-events", "none");
	 		
 	  }else{
	 		$("#da_permission").hide();
	 		$("#da_permission_ans").hide();
	 		$("#rtnDate_Div").hide();
	 		$("#dest_Div").hide();
	 		$("#rtnDate_ValueTD").hide();
	 		$("#daAdvanceAmt_Div").hide();
	 		$("#optforHotelDa").hide();
 			$("#optforConveyanceDa").hide();
			$("#optforFoodDa").hide();
 			$("#GPSGHouse").hide();
 			$("#hotelAmount").val(0);
 			$("#conveyanceAmount").val(0);
 			$("#foodAmount").val(0);
 			$("#advanceAmt").val(0);
 			$("#DA_Advanced_Confirm_One").css("pointer-events", "");
		    $("#DA_Advanced_Confirm_Two").css("pointer-events", "");
 	  }
 	 
  }
  
  
 function showTypesOfDa() {
 	
	//alert(document.getElementById("lrc_check1").value);
	
 	if(document.getElementById("DAAdvanced").checked==true)
 	{
 		if(document.getElementById("lrc_check1").checked==true){
 		$("#optforHotelDa").show();
 		$("#optforConveyanceDa").show();
	    $("#optforFoodDa").show();
 		showNewDADetailsField();
 		}
 		else{
 			$("#optforHotelDa").hide();
 			$("#optforConveyanceDa").hide();
			$("#optforFoodDa").hide();
 			$("#rtnDate_Div").hide();
	 		$("#dest_Div").hide();
	 		$("#rtnDate_ValueTD").hide();
	 		$("#daAdvanceAmt_Div").hide();
	 		alert("You are not entitled for DA");
	 		$("#DAAdvanced1").click();
 		}
 		
 	}
 	else
 	{
 		$("#optforHotelDa").hide();
 		$("#optforConveyanceDa").hide();
		$("#optforFoodDa").hide();
 		$("#rtnDate_Div").hide();
 		$("#dest_Div").hide();
 		$("#rtnDate_ValueTD").hide();
 		$("#daAdvanceAmt_Div").hide();
 	}
 	
 }
 
 
 function showNewDADetailsField() {
 	
 	if(document.getElementById("DAAdvanced").checked==true)
 	{
 		if(document.getElementById("lrc_check1").checked==true){
 		$("#rtnDate_Div").show();
 		$("#dest_Div").show();
 		$("#rtnDate_ValueTD").show();
 		$("#daAdvanceAmt_Div").show();
 		$("#GPSGHouse").hide();
 		}
 		else{
	 		$("#rtnDate_Div").hide();
	 		$("#dest_Div").hide();
	 		$("#rtnDate_ValueTD").hide();
	 		$("#daAdvanceAmt_Div").hide();
 		}
 		
 	}
 	else
 	{
 		$("#rtnDate_Div").hide();
 		$("#dest_Div").hide();
 		$("#rtnDate_ValueTD").hide();
 		$("#daAdvanceAmt_Div").hide();
 	}
 	
 }
 
 
 function viewDAAdvanceDetails() {
  
	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
  
  	var category_id = $("#category_id").val();
    var service_id = $("#service_id").val();
    var rank = $("#rank").val();
    var trRuleID=$("#TRRule").val();
    var cityRankId=$("#destinationCityGrade").val();
    var hotelAllowForTR=$("input[name=reqForHotelDA]:checked").val();
     var conveyanceAllowForTR=$("input[name=reqForConveyanceDA]:checked").val();
    var foodAllowForTR=$("input[name=reqForFoodDA]:checked").val();
    //alert("category_id="+category_id+"|service_id="+service_id+"|rank-"+rank+"|destinationCityGrade-"+destinationCityGrade);
    
   	if(document.getElementById("DAAdvanced").checked==true)
 	{
 		if(document.getElementById("lrc_check1").checked==true)
 		{
 			
     			var urlToSet=$("#context_path").val()+"mb/daAdvDetails";       

			 	ajaxUserFace = new LightFace.Request({
 	 				width: 500,
					height: 250,
 	
					url: urlToSet,
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							category_id: category_id,
							service_id:service_id,
							rank:rank,
							trRuleID:trRuleID,
							cityRankId:cityRankId,
							hotelAllowForTR:hotelAllowForTR,
							conveyanceAllowForTR:conveyanceAllowForTR,
							foodAllowForTR:foodAllowForTR
						},
						method: 'post'
					},
					title: 'Advance Details'
				});
   				ajaxUserFace.open();
 	  }
   
   }
 }
 
  function showCompositeTransfer(event) {
 
	//alert(document.getElementById("transferReq").checked);
  	
	  if(document.getElementById("transferReq").checked==true)
	  {
 		if(!$('#PT_Advanced_Confirm_One').is(':checked')){
	  	 	alert("Please confirm above details. If your Bank Account Details are not correct, kindly contact your Pay Account Office to update the same.");
		    event.preventDefault();
		    return false;
	  	 }
//	  	 if($("#userAccDtlsUpdated").val()=="false"){
//	 	    alert("Please contact your Pay Account Office to update your bank account details in DTS to get DA Advance.");
//		    event.preventDefault();
// 	  }
	 	if($("#travelID").val()!='1' && parseInt($("#travelDaCounts").val())>0){
 	 
		      alert("You have already claimed Advanced, no more Advanced is allowed for this Travel ID");
		 	  event.preventDefault();
		      return false; 
	    }
	    if($("#taggCheck").val()=='true'){
	    	if($('#taggedReqId').length){
				
				var taggedCountFlag='';
				
				$.ajax({
				url: $("#context_path").val()+"mb/getTaggedReqAdvanceCount",
				type: "get",
				async: false,
		      	data: "requestId="+$('#taggedReqId').val(),
		      	dataType: "text",
		      
		      	beforeSend: function() 
		      	{
		        	var calHeight=document.body.offsetHeight+20;
			        $("#screen-freeze").css({"height":calHeight + "px"} );
			        $("#screen-freeze").css("display", "block");
			    },
  			 
  			  	complete: function(){
  			  		$("#screen-freeze").css("display", "none");
			  	},
			  	
			  	success: function(msg1)
		      	{
		      		taggedCountFlag=msg1.trim();
					
		      	}
				
				
				});
				
				if(taggedCountFlag=='' || taggedCountFlag=='true'){
					alert("You have already claimed Advanced, no more Advanced is allowed for tagged request Travel ID");
					event.preventDefault();
	      			return false; 
				}
			}
	    }else{
	    	alert("Please select tagged request option.");
		 	  event.preventDefault();
		      return false; 
  }
  
	     var innerHtml='';
	     innerHtml=innerHtml+'<tr><td colspan="4"><input type="radio" name="ptTransferType" value="1" onclick="validatePTTransferType(event)" />';
		 innerHtml=innerHtml+'&#160;&#160;<b>Transfer involving a change of station located at a distance of or more than 20 kms from each other.</b></td>';
		 innerHtml=innerHtml+'</tr><tr><td colspan="4"><input type="radio" name="ptTransferType" value="2" onclick="validatePTTransferType(event)" />';
		 innerHtml=innerHtml+'&#160;&#160;<b>Transfer to and from the Island territories of Andaman, Nicobar &amp; Lakshadweep.</b></td>';
		 innerHtml=innerHtml+'</tr><tr><td colspan="4"><input type="radio" name="ptTransferType" value="3" onclick="validatePTTransferType(event)" />';
		 innerHtml=innerHtml+'&#160;&#160;<b>Transfer to stations which are at a distance of less than 20 kms from the old station and of transfer within the same city, provided a change of residence is actually involved.</b></td></tr>';
	     innerHtml=innerHtml+'<tr><td>Enter Your Basic Pay<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input id="basicPay" type="text" class="txtfldM"  name="basicPay" maxlength="8" onblur="validateBasicPayAmt();"/></td>';
		 innerHtml=innerHtml+'</tr><tr><td>CTG Advance<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input id="totalCtg" type="text" class="txtfldM"  name="totalCtg" maxlength="8"  onblur="validateTotalCTGAmt();totalTDAdvanceAmount();"/></td>';
		 innerHtml=innerHtml+'</tr><tr><td>Distance Of Travel(in Kms)<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input id="distanceInKms" type="text" class="txtfldM"  name="distanceInKms"  maxlength="6" onblur="validateDistanceInKms();"/></td>';
		 innerHtml=innerHtml+'</tr><tr><td>Approximate Luggage Weight(in Kg)<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input id="approxLuggWeight" type="text" class="txtfldM"  name="approxLuggWeight" maxlength="6" onblur="validateApproxLuggWeight();"/></td>';
		 innerHtml=innerHtml+'</tr><tr align="left" bgcolor="#fbfac8"><td>Amount for Luggage Transport<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input type="text" name="luggageAmt" class="txtfldM" id="luggageAmt" maxlength="8" onblur="validateLuggageAmt();totalTDAdvanceAmount();"/>';
		 innerHtml=innerHtml+'</td></tr><tr><td>Amount for vehicle Transfer<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input type="text" name="conveyance" class="txtfldM" id="conveyance" maxlength="8" onblur="validateConveyanceAmt();totalTDAdvanceAmount();"/>';
		 innerHtml=innerHtml+'</td></tr><tr><td>Total Advance Amount Claimed<span class="mandatory">*</span></td>';
		 innerHtml=innerHtml+'<td colspan="3"><input type="text" name="ptAdvanceAmountClaimed" class="txtfldM" id="ptAdvanceAmountClaimed" value="0" readonly="true"/><br/>';
		 innerHtml=innerHtml+'<font color="red">Please note that Advance against a PT can only be taken once from DTS.</font></td></tr> ';
 	
	     $("#composite_transfer_dtls").html(innerHtml); 

 	}
 	else
 	{
 		 $("#composite_transfer_dtls").html(""); 
 	}
 	
 	}
 
 function validatePTTransferType(event){
 	
 	if(!confirm("Are you sure, you have selected correct transfer type?")){
	  event.preventDefault();
     }else{
     	$('input[type="radio"][name="ptTransferType"]').each(function() {
			if(!$(this).is(':checked')){
				 $(this).attr("disabled","disabled");
			}
		});
     }
 	
 }
 
  function fillCity(thisValue,cityGrade) {
  	
 	$('#destinationCity').val(thisValue);
 	$('#destinationCityGrade').val(cityGrade);
 	
 	setTimeout("$('#suggestionsDestCity').hide();", 200);
 	
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
 	}else if(!validateTravelDate()){
 		return false;
 	}else{
		 
			 return true;
		 
 		
 	}
 }
 
 function showValidIdAlert(index){

	//alert("Inside showValidIdAlert");
	if($('#validIdCardPassCheck'+index).is(":checked"))
    {
    	var checkRlaxnAge=confirm("Are you sure, selected passenger has Valid Identity Proof?");
		if (checkRlaxnAge== true)
		 {
			 $("#validIdCardPassCheck"+index).attr('checked', true);
			
		 }
		else
		 {
			 $("#validIdCardPassCheck"+index).attr('checked', false);
		 }
  
    }

}

function showReturnValidIdAlert(index){

	//alert("Inside showValidIdAlert");
	if($('#returnValidIdCardPassCheck'+index).is(":checked"))
    {
    	var checkRlaxnAge=confirm("Are you sure, selected passenger has Valid Identity Proof?");
		if (checkRlaxnAge== true)
		 {
			 $("#returnValidIdCardPassCheck"+index).attr('checked', true);
		 }
		else
		 {
			 $("#returnValidIdCardPassCheck"+index).attr('checked', false);
		 }
  
    }
 }
 
 
 
 function isHotelAllowForThisTR(event){
  
  if($("#hotelAllowForTR").val()=="true"){
    var reqForHotelDA=$("input[name=reqForHotelDA]:checked").val();
    $("#requestForHotelDA").val(reqForHotelDA);
    if(reqForHotelDA == "0"){
    	$("#hotelAmount").attr("disabled", false);	
    }else{
      $("#hotelAmount").val(0);
      $("#hotelAmount").attr("disabled", true);	
    }
    
     calDAAdvanceAmt();
     
  }else{
    alert("Hotel Advance Not Allow For This Travel Rule");
    event.preventDefault();
    return false;
  }
  
 }
 
 function isConveyanceAllowForThisTR(event){
  
  if($("#conveyanceAllowForTR").val()=="true"){
    var reqForConveyancelDA=$("input[name=reqForConveyanceDA]:checked").val();
    $("#requestForConveyanceDA").val(reqForConveyancelDA);
    if(reqForConveyancelDA == "0"){
    	$("#conveyanceAmount").attr("disabled", false);	
    }else{
      $("#conveyanceAmount").val(0);
      $("#conveyanceAmount").attr("disabled", true);	
    }
    
     calDAAdvanceAmt();
     
  }else{
    alert("Conveyance Advance Not Allow For This Travel Rule");
    event.preventDefault();
    return false;
  }
  
 }
 
 function isFoodAllowForThisTR(event){
  
  if($("#foodAllowForTR").val()=="true"){
    var reqForFoodDA=$("input[name=reqForFoodDA]:checked").val();
    $("#requestForFoodDA").val(reqForFoodDA);
    if(reqForFoodDA == "0"){
    	$("#foodAmount").attr("disabled", false);	
    }else{
      $("#foodAmount").val(0);
      $("#foodAmount").attr("disabled", true);	
    }
    
    calDAAdvanceAmt();
    
  }else{
    alert("Food Advance Not Allow For This Travel Rule");
    event.preventDefault();
    return false;
  }
  
 }
 
 
 function calDAAdvanceAmt(){
 	$("#advanceAmt").val(parseInt($("#hotelAmount").val())+parseInt($("#conveyanceAmount").val())+parseInt($("#foodAmount").val()));
 }
 
 function setFormDAndGYear(year,availCount){
 	
 	$("#formDAndGYear").val(year);
 	$("#Avail_Form_Count").html(availCount);

 	var msg = $.parseJSON($('#travelerObj').val());
 	setFamilyDetails(msg);
 	setToFrmStatnByTrRule("",0,false);
 	
 }
 
 function checkMaxAdvanceAmt(theForm,action){
	
 	var flag=setNoOfDaysDARequired(theForm);
 	if(!flag){
 		$("#hotelAmount").val(0);
 		$("#conveyanceAmount").val(0);
 		$("#foodAmount").val(0);
 		return false;
 	}
	
	var category_id = $("#category_id").val();
    var service_id = $("#service_id").val();
    var rank = $("#rank").val();
    var noOfDays = $("#noOfDays").val();
    var trRuleID=$("#TRRule").val();
    var hotelAllowForTR=1;
    var conveyanceAllowForTR=1;
    var foodAllowForTR=1;
    var amount=0;
    if(action=='hotel'){
    	amount=$("#hotelAmount").val();
    	hotelAllowForTR=$("input[name=reqForHotelDA]:checked").val();
    }else if(action=='conveyance'){
    	amount=$("#conveyanceAmount").val();
    	conveyanceAllowForTR=$("input[name=reqForConveyanceDA]:checked").val();
    }else if(action=='food'){
    	amount=$("#foodAmount").val();
    	foodAllowForTR=$("input[name=reqForFoodDA]:checked").val();
    }
    
		
		$.ajax({
		url: $("#context_path").val()+"mb/calMaxDAAmount",	
        type: "get",
        async: false,
        data: "category_id=" + category_id + "&service_id=" + service_id + "&rank=" + rank  + "&noOfDays=" + noOfDays 
               + "&trRuleID="+trRuleID +"&hotelAllowForTR="+hotelAllowForTR
              +"&conveyanceAllowForTR="+conveyanceAllowForTR+"&foodAllowForTR="+foodAllowForTR+"&amount="+amount,
        dataType: "json",
        success: function(msg) {
           
            var infoMsg=msg.msg;
            var flag=msg.flag;
            
            if(flag=='false'){
               alert(infoMsg);
                if(action=='hotel'){
			    	$("#hotelAmount").val(0);
			    }else if(action=='conveyance'){
			    	$("#conveyanceAmount").val(0);
			    }else if(action=='food'){
			    	$("#foodAmount").val(0);
			    }
               return false;
            } else{
            	 flag = true;
            }
               
        }
    });

    calDAAdvanceAmt();
    
}

function setDefaultValueForParty(msgg)
{
	var selEntitledClass=$('#entitledClass').val();	
	
	var isPartyBooking=$('#isPartyBooking').val();	
	
	if(isPartyBooking=='Yes')
	{
		partyDependentNo=0;
		$("#partyMemberCount").val("0");
		var partyDetailsHtml='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Party Dependent" class="btn" id="" onclick="setPartyDependentAllowedDiv();"/></td></tr></table>'
		$("#partyDependentDetails").html(partyDetailsHtml);
	}

}

function setNoOfDaysDARequired(theForm){
	 
	        var form = document.forms[theForm];
	        var travelMode = $('#travelMode').val();
		    var date1="";
	        if (travelMode == 1) {
	        	 date1 = form.onwordJourneyDate.value;
	        }else{
	        	 date1 = form.journeyDate.value;
	        }
	       
	        var date2 = form.returnDate.value;
	        
	        if(date1==null || date1==''){
	        	alert("Please Select Journey Date");
	        	return false;
	        }
	        if(date2==null || date2==''){
	        	alert("Please Select DA required till Date.");
	        	return false;
	        }
	
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
	   return true;   
}

function getRelationFromResponse(){
	
	 var msg = $.parseJSON($('#travelerObj').val());
	 
     var relOptions="<option value=''>Select</option>";
     $.each(msg.retationType,function(id, name){
    
        if(id!=0)
		relOptions += '<option value="' + id + '">' +name + '<\/option>';
	});
     return relOptions;				
					
}

function getGenderFromResponse(msg){
	
	var msg = $.parseJSON($('#travelerObj').val());
	
       var genOptions="<option value=''>Select</option>";
       $.each(msg.gender,function(id, name){
      		genOptions += '<option value="' + id + '">' +name + '<\/option>';
	   });	
	return genOptions;
}

function setPrintERSName(name,k){
name=name.substring(0,15);
document.getElementById("attErsName"+k).value=name;
}

function setPassportDetails(j){
	
	var innerHTML='';
	innerHTML=innerHTML+'<table width="100%" class="formtxt"><tr><td colspan="4"><h1>Passport Details</h1></td></tr>';
    innerHTML=innerHTML+'<tr><td><b>Passport No.</b></td>';
    innerHTML=innerHTML+'<td><input type="text" maxlength="20" size="20" id="passport_no" onkeyup="this.value = this.value.toUpperCase()"/></td>';
    innerHTML=innerHTML+'<td><b>Passport Expiry Date</b></td><td><input type="text" maxlength="20" size="20" readonly="true" id="passport_exp_date"/></td>';
    innerHTML=innerHTML+'</tr><tr><td align="left" colspan="2"><input type="button" onclick="closePassportDtlsModal('+j+');" class="btn" value="Cancel"/></td>';
    innerHTML=innerHTML+'<td align="right" colspan="2"><input type="button" onclick="setPassportDtlsModal('+j+');" class="btn" value="OK"/></td>';
    innerHTML=innerHTML+'</tr></table>';
    
    var date=$("#onwordJourneyDate").val().split("/");
    var minDate=0
    if(date.length==3){
    	minDate = new Date(date[2],date[1],date[0]);
    	minDate.setDate(minDate.getDate() + 180);
    }
    
    $("#passport_dtls_modal").html(innerHTML);
    $('#passport_exp_date').datepicker({dateFormat:'dd/mm/yy',scrollMonth : false,minDate:0,
		    	beforeShow : function () {
						 $(this).val("");
					 }
			 });
    $("#myModal").show();
}

function closePassportDtlsModal(j){
	$("#removeFrmList"+j).attr('checked', false);
	document.getElementById("removeFrmList"+j).value=1;
	$("#myModal").hide();
}

function setPassportDtlsModal(j){
	
	if($("#passport_no").val().trim()==''){
		alert("Please enter passport number");
		return false;
	}
	if(!validatePassport($("#passport_no").val())){
		alert("Please enter valid passport number");
		return false;
	}
	if($("#passport_exp_date").val().trim()==''){
		alert("Please enter passport expiry date");
		return false;
	}
	$("#passPassportNo"+j).val($("#passport_no").val());
	$("#passExpdate"+j).val($("#passport_exp_date").val());
	$("#myModal").hide();
	$("#passport_dtls_modal").html("");
}	

function validatePassport(textValue){
	var numberRegEx = /^[A-PR-WY][1-9]\d\s?\d{4}[1-9]$/;
	
	return numberRegEx.test(textValue)
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

function showSaveButton(index) {

	var checkbox = document.getElementsByName("selectedTrain");

	var checkLen = checkbox.length;

	for (var i = 0; i < checkLen; i++) {
		if (i == index) {
			$("#trainRadio" + index).checked;
			$("#selectedTrainNo").val($("#trainRadio" + index).val());
		}
	}

}


function getTrainRouteStation(trainNumber, fromStationCode, toStationCode) {

	var tainValue = $("#selectedTrainNo").val();
	var journeyDate = $('#journeyDate').val();
	var breakJourneyEndDate = "";
	var breakJourneyStartDate = "";
	var viafields = ""
	var fromStn = "";
	var tostn = "";
	
	journeyDate = changeDateFormat(journeyDate);
	var stationList = "";
	
	$('#toStation0').find('option').not(':first').remove();
	if (parseInt(tainValue) != parseInt(trainNumber)) {
		alert("You have selected the another train, please select the selected checbox train");
		return false;
	} else {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getSingleTrainRouteBreakJrny",
			data: "trainNumber=" + trainNumber + "&fromStationCode=" + fromStationCode + "&toStationCode=" + toStationCode + "&journeyDate=" + journeyDate,
			datatype: "application/json",
			beforeSend: function() {
				var calHeight = document.body.offsetHeight + 20;
				$("#screen-freeze").css({ "height": calHeight + "px" });
				$("#screen-freeze").css("display", "block");
			},

			complete: function() {
				$("#screen-freeze").css("display", "none");
			},
			success: function(data) {
				stationList += '<option value="">select<\/option>';
				$.each(data, function(key, value) {

					stationList += '<option value="' + value + '(' + key + ')' + '">' + value + '(' + key + ')' + '<\/option>';

				});
				$("#toStation0").html(stationList);

			}
		});

	}
	
	fromStn = $("input#fromStation").val();
	if (fromStn == undefined)
		fromStn = $("select#fromStation").val();
	tostn = $("input#toStation").val();
	if (tostn == undefined)
		tostn = $("select#toStation").val();
//	$("#breakJrnyDiv").show();
	if (validateBreakJrnyFields()) {
		if ($('#breakJourney').is(":checked")) {

			$("#fromStation").attr('disabled', 'disabled');
			$("#toStation").attr('disabled', 'disabled');

			viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
			viafields += '<tr><td colspan="4" class="lablevalue">Break Journey Route</td></tr>'
			viafields += '<tr><td align="center" style="width:27.75%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%"></td></tr>'
			viafields += '</table>'
			viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="routeTable">'
			viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation0" name="frmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
			viafields += '<td align="left"><select type="text" class="txtfldM" style="width: 154px;" id="toStation0" name="toStation0" autocomplete="off" onChange="setBreakJrnyFromStation(this.value)"><option value="">Select</option></select>'
			viafields += '<div class="suggestionsBox" id="suggestionstoStation0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListtoStation0"></div></div></td>'
			viafields += '</td>'
			viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate0" name="journeyDate0" autocomplete="off"></td>'
			viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(0)"/></td></tr>'
			viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="frmStation1" name="frmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
			viafields += '<div class="suggestionsBox" id="suggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListfrmStation1"></div></div></td>'
			viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="toStation1" name="toStation1" readonly autocomplete="off"/><div class="suggestionsBox" id="suggestionstoStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListtoStation1"></div></div></td>'
			viafields += '<td align="left"><input type="text" class="txtfldM" id="journeyDate1" name="journeyDate1" autocomplete="off"></td>'
			viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(1)"/></td></tr>'
			viafields += '</table>';

			$("#breakJrnyDiv").show("fast");
			$("#breakJrnyDiv").html(viafields);

			$('#journeyDate0').datetimepicker({
				lang: 'en',
				timepicker: false,
				format: 'd/m/Y',
				formatDate: 'd/m/Y',
				scrollMonth: false,
				scrollInput: false,
				viewDate: $('#travelStartDate').val(),
				defaultDate: $('#travelStartDate').val(),
				yearEnd: 2100,
				onShow: function(input, inst) {
					$('#journeyDate0').val("");
					minData = $('#journeyDate').val();
					maxData = $('#travelEndDate').val();
					$("#journeyDate0").datetimepicker("setOptions", {
						minDate: minData
					});
					$("#journeyDate0").datetimepicker("setOptions", {
						maxDate: maxData
					});
				},
				onSelectDate: function(ct) {
					  
					breakJourneyStartDate = ct;
					var selectedDateTime = ct.getTime();
					var selectedTime = selectedDateTime + (72 * 60 * 60 * 1000);
					var travelEndDate = $('#travelEndDate').val();
					var part = travelEndDate.split("/");

					var endDate = new Date(part[2], part[1] - 1, part[0]);
					var timeEndTravel = endDate.getTime();
					var flag = selectedTime < timeEndTravel;
					if (flag) {

						breakJourneyEndDate = new Date(selectedTime);
					} else {
						breakJourneyEndDate = travelEndDate;
					}
				}

			});



			$('#journeyDate1').datetimepicker({
				lang: 'en',
				timepicker: false,
				format: 'd/m/Y',
				formatDate: 'd/m/Y',
				scrollMonth: false,
				scrollInput: false,
				viewDate: $('#travelStartDate').val(),
				defaultDate: $('#travelStartDate').val(),
				yearEnd: 2100,
				onShow: function() {
					$('#journeyDate1').val("");
					minData = breakJourneyStartDate;
					maxData = breakJourneyEndDate;
					$("#journeyDate1").datetimepicker("setOptions", {
						minDate: minData
					});
					$("#journeyDate1").datetimepicker("setOptions", {
						maxDate: maxData
					});
				}
			});

		} else {
			//alert("Via-route un-checked :::: ");
			$("#fromStation").prop('disabled', false);
			$("#toStation").prop('disabled', false);
		}
	} else {
		$("#breakJrnyDiv").hide("fast");
	}

	ajaxRequestFace.close();
}

function setBreakJrnyFromStation(value) {
	var fromStn = $("input#toStation0").val()
	if (fromStn == undefined)
		fromStn = $("select#toStation0").val()

	$("#frmStation1").val(value);

}

function validateBreakJrnyFields() {
	var check = "";

	if (document.getElementById("viaRelxRoute") != null) {
		if (document.getElementById("viaRelxRoute").checked == true) {
			alert("You can Either select Via Route or Relaxed Route")
			document.getElementById("breakJourney").checked = false;
			return false;
		}
	}

	if (document.getElementById("journeyDate").value == "dd/mm/yyyy" || document.getElementById("journeyDate").value == "") {
		alert("Please Enter Journey Date")
		document.getElementById("journeyDate").focus();
		document.getElementById("breakJourney").checked = false;
		return false;
	}
	if (document.getElementById("fromStation").value == "") {
		alert("Please Enter From Station And To Station Fields");
		document.getElementById("breakJourney").checked = false;
		return false;
	}
	if (document.getElementById("toStation").value == "") {
		alert("Please Enter From Station And To Station Fields");
		document.getElementById("breakJourney").checked = false;
		return false;
	}


	check = validate_required(document.getElementById("entitledClass"), "Please Select Entitled Class");
	if (!check)
		document.getElementById("breakJourney").checked = false;

	return check;

}

function handleUncheckToHideDiv() {

	if (!$('#breakJourney').is(":checked")) {
		$("#selectedTrainNo").val("");
		$("#fromStation").prop('disabled', false);
		$("#toStation").prop('disabled', false);
		$("input#fromStation").prop("readonly", false);
		$("input#toStation").prop("readonly", false);
		$("#breakJrnyDiv").hide("fast");
	}
}

function trainListPopupForBreakJrny() {

	if (parseInt($("#countdown").val()) < 5) {
		alert("Your Session Has Been Expired. Kindly Re-login");
		return false;
	}
	if (document.getElementById("viaRoute") != null) {
		if (document.getElementById("viaRoute").checked == true) {
			alert("You can Either select Via Route or Break Journey")
			$("#fromStation").prop('disabled', false);
			$("#toStation").prop('disabled', false);
			$("input#fromStation").prop("readonly", false);
			$("input#toStation").prop("readonly", false);
			document.getElementById("viaRoute").checked = false;
			document.getElementById("breakJourney").checked = false;
			$("#viaDiv").hide();
			return false;
		}
	}

	if (document.getElementById("clusteredRoute") != null) {
		if (document.getElementById("clusteredRoute").checked == true) {
			alert("You can Either select Break Journey or Clustered Route");
			$("#fromStation").prop('disabled', false);
			$("#toStation").prop('disabled', false);
			$("input#fromStation").prop("readonly", false);
			$("input#toStation").prop("readonly", false);
			document.getElementById("clusteredRoute").checked = false;
			document.getElementById("breakJourney").checked = false;
			$("#clusteredDiv").hide();
			return false;
		}
	}


	var frmStn = $('#fromStation').val();
	var toStn = $('#toStation').val();
	if (validateBreakJrnyFields()) {
		frmStn = getStationCode(frmStn);
		toStn = getStationCode(toStn);
		var journeyDate = $('#journeyDate').val();
		journeyDate = changeDateFormat(journeyDate)
		ajaxRequestFace = new LightFace.Request(
			{
				width: 800,
				height: 600,
				url: $("#context_path").val() + "mb/irSrchRsltPageForBreakJrny",


				buttons: [
					{
						title: 'Close', event: function() {
							myCloseFunction();
							this.close();
						}
					}
				],
				request: {
					data: {
						frmStation: frmStn,
						toStation: toStn,
						dep_date: journeyDate,

					},
					method: 'post'
				},
				title: 'Train List'
			});
		ajaxRequestFace.open();

	}

}
function myCloseFunction() {
	document.getElementById("breakJourney").checked = false;
	$("#selectedTrainNo").val("");
}

function resetBreakJrnyChecbox(){
	
	if (document.getElementById("breakJourney")!=null && document.getElementById("breakJourney").checked == true) {
		$("#breakJourney").click(); 
	}
}

function resetViaRouteCheckBox(){
	if (document.getElementById("viaRoute")!=null && document.getElementById("viaRoute").checked == true) {
		$("#viaRoute").click();
	}
}

			
  /* PartyBooking Onward Add Dependent Validation */
  function validateFieldsOnwardPartyBkgRequest() {
      var entitledClass = $('#entitledClass').val();
      var journeyDate = $('#journeyDate').val();
      var fromStation = $('#fromStation').val();
      var toStation = $('#toStation').val();
      if (entitledClass != "" && journeyDate != "" && fromStation != "" && toStation != "") {
          return true;
      }else {
           alert("Please Fill Journey Details Fields properly");
          return false;
      }
  }			
			
function checkForAirExistForHospitalUnit(isAirExist) {

	const trRuleIds = ["TR100180", "TR100025", "TR100026", "TR100027", "TR100028", "TR100029", "TR100030", "TR100031", "TR100032", "TR100033", "TR100034", "TR100035", "TR100036", "TR100037", "TR100038", "TR100053"];

	var hospitalUnit = $('#isHospitalUnit').val();
	var trRuleID = $('#TRRule').val();
	
	if (isAirExist && hospitalUnit == 0 && (trRuleIds.indexOf(trRuleID.trim()) > -1)) {

		return true;

	}
	return false;

}	
			
			
			
			
			
			
