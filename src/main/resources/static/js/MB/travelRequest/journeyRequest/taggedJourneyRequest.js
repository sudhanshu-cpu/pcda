function getTaggedRequest()
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var serviceType=$('#serviceType').val();
	var travellerUnitService=$('#travellerUnitService').val();
	
	if(travelTypeText.indexOf('LTC')>-1)
	{	if(serviceType=='1' && travellerUnitService!='COAST GUARD'){
			getCivilianTaggedRequestForLtc();
		}else{
			getTaggedRequestForLtc();	
		}	
	}else if(travelTypeText.indexOf('PT')>-1)
	{
		getTaggedRequestForPt();
	}
	else{
		getTaggedRequestCommon();
	}
}


function getCivilianTaggedRequestForLtc()
{
	
	$("#isTaggedORNew").val("false");
	$('#taggRequestCheck').val("true");

	var travelType=$('#TravelTypeDD').val();
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var personalNo=$('#personalNo').val();
	var serviceType=$('#serviceType').val();

	var jrnyTypeArr=document.getElementsByName("journeyType");
	var jrnyType;
	for(var i=0;i<jrnyTypeArr.length;i++)
	{
		if(jrnyTypeArr[i].checked)
		jrnyType=jrnyTypeArr[i].value
	}

	var TRRule=$('#TRRule').val();

	var requestDtls="";
	var authorityNoCheck="";
	var authorityDateCheck="";
	var ltcYearCheck="";
	
	$("#authNoLabel").hide();
	$("#authDateLabel").hide();
	$("#authDateText").show();
	$("#authNoText").show();
	$('#taggCheck').val("false");

	if(reqType!="ramBaanBooking")
	{
	 	setToStnValue("");
	 	setFrmStnValue("");
 		setOrgStnValue("");
		setDestStnValue("");
	}
 
 	if(document.getElementById("ltcYear")!=null)
		document.getElementById("ltcYear").value='';
        
	 
	setFamilyDetails('');
	setReturnFamilyDetails('');
	setSourceDestinationByTrRule();
	setReturnSourceDestinationByTrRule();
					
	$.ajax(
	{
      
      url: $("#context_path").val()+"mb/getTaggedRequests",
      type: "get",
      data: "personalNo="+personalNo+"&TRRule="+TRRule+"&travelType="+travelTypeText ,
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
   
            var options = '';
		    var CurrDutyStn = msg.currDutyStn;
			var CurrSPRNRS = msg.currSPRNRS;
			$("#currDutyStn").val(CurrDutyStn);
			$("#currSprNrs").val(CurrSPRNRS);
			
			var IsLTCAvailedPrev = msg.isLTCAvailedPrev;
			var IsLTCAvailedCurr = msg.isLTCAvailedCurr;
			var IsLTCAvailedNext = msg.isLTCAvailedNext;
			
			var allwdPrevYearOnw = msg.allwdPrevYearOnw;
			var allwdPrevYearRet = msg.allwdPrevYearRet;
			
			var allwdCurrYearOnw = msg.allwdCurrYearOnw;
			var allwdCurrYearRet = msg.allwdCurrYearRet;
			
			var allwdNextYearOnw = msg.allwdNextYearOnw;
			var allwdNextYearRet = msg.allwdNextYearRet;
			
			 $("#noOfReqPrevOnw").val(allwdPrevYearOnw);
			 $("#noOfReqPrevRet").val(allwdPrevYearRet);
            
			 $("#noOfReqOnw").val(allwdCurrYearOnw);
			 $("#noOfReqRet").val(allwdCurrYearRet);
            
             $("#noOfReqNextOnw").val(allwdNextYearOnw);
             $("#noOfReqNextRet").val(allwdNextYearRet);
			
			 $("#IsLTCAvailedPrev").val(IsLTCAvailedPrev);
			 $("#IsLTCAvailedCurr").val(IsLTCAvailedCurr);
			 $("#IsLTCAvailedNext").val(IsLTCAvailedNext);
			
			 var isRailRequestOnwardExist = msg.isRailRequestOnwardExist;		
		     $("#isRailRequestOnwardExist").val(isRailRequestOnwardExist);
		     
		 	 var isRailRequestReturnExist = msg.isRailRequestReturnExist;		
		     $("#isRailRequestReturnExist").val(isRailRequestReturnExist);
		     
		      var isAirRequestOnwardExist = msg.isAirRequestOnwardExist;		
		     $("#isAirRequestOnwardExist").val(isAirRequestOnwardExist);
		     
			 var isAirRequestReturnExist = msg.isAirRequestReturnExist;		
		     $("#isAirRequestReturnExist").val(isAirRequestReturnExist);     
	
			 var isAirOneWayExist = msg.isAirOneWayExist;		
		     $("#isAirOneWayExist").val(isAirOneWayExist);
				
				
			 var colspan="0";				
			 if(msg.taggedRequestDtls==null || msg.taggedRequestDtls.length==0)
			 {
			 	requestDtls+='<div><table width="100%" class="filtersearch"><tbody class="all1"><tr><td colspan="2">'+msg.message+'</td></tr>'
		     }
			 else
			 {
			    colspan="5";
				requestDtls+='<div><table width="100%" class="filtersearch"><tbody class="all1"><tr><td class="label">Select</td><td class="label">Request ID</td>'
				requestDtls+='<td class="label">Journey Mode</td>'
				requestDtls+='<td class="label">Authority No</td>'
				requestDtls+='<td class="label">Authority Date</td>'
				requestDtls+='<td class="label">TR Rule</td>'
				requestDtls+='<td class="label">Journey Type</td>'
				requestDtls+='<td class="label">State</td>'
				requestDtls+='<td class="label">Calender Year</td>'
				requestDtls+='</tr>'
                
                $.each(msg.taggedRequestDtls, function(index,obj){
	
                    	var journeyMode = obj.journeyMode;
                		var travelTypeID = obj.travelTypeID;
						var requestID = obj.requestID;
						var authorityDate = obj.authorityDate;
						var authorityNo = obj.authorityNo;
						var trRuleID = obj.trRuleID;
						var ltcYear = obj.ltcYear;
						var jrnyType = obj.jrnyType;
						var jrnyDate = obj.jrnyDate;
						var oldDutyStn = obj.oldDutyStn;
						var newDutyStn = obj.newDutyStn;
						var oldSPRNRS = obj.oldSPRNRS;
						var newSprNrs = obj.newSprNrs;
						var requestSeqno= obj.requestSeqNo;
						var approvalState = obj.approvalState;
						var desinationStn = obj.desinationStn;
						var spouseNocNo = obj.spouseNocNo;
						var isMixedModeRequest = obj.isMixedModeRequest;
						
						if (jrnyType == 'Return') 
						{
						   desinationStn = obj.fromStn;
   						}
						
						requestDtls+='<tr>'
						
						
						if((authorityNoCheck!=authorityNo || authorityDateCheck!=authorityDate || ltcYearCheck!=ltcYear) )
						{
							var minJryStr = 'year' + ltcYear + 'MinOnwardJrnyDate';
							var minOnwardLtcJrnyDate = msg.minOnwardJrnyDate[minJryStr];
						   
						   requestDtls+='<td><input type="radio" id="oldRequest" name="oldRequest" value="'+authorityDate+"~"+authorityNo+'~'+desinationStn+'~'+ltcYear+'~'+minOnwardLtcJrnyDate+'~'+spouseNocNo+'~'+isMixedModeRequest+'" onclick="setTaggedFields(\'No\')"/></td>'
						}
						else
							requestDtls+='<td></td>'
						if(journeyMode=='AIR'){
							requestDtls+='<td><a href="javascript:void(0);" onclick="popupForAirTaggedRequest(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
						}else{
							requestDtls+='<td><a href="javascript:void(0);" onclick="popupForTaggedRequestNew(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
						}
						requestDtls+='<td><label>'+journeyMode+'</label></td>'
						requestDtls+='<td><label>'+authorityNo+'</label></td>'
						requestDtls+='<td><label>'+authorityDate+'</label></td>'
						requestDtls+='<td><label>'+trRuleID+'</label></td>'
						requestDtls+='<td><label>'+jrnyType+'</label></td>'
						requestDtls+='<td><label>'+approvalState+'</label></td>'
					    requestDtls+='<td><label>'+ltcYear+'</label></td>'
						requestDtls+='</tr>'
						
						authorityNoCheck=authorityNo;
						authorityDateCheck=authorityDate;
						ltcYearCheck=ltcYear;
						
				  });
			 }
			 
			 requestDtls+='<tr><td colspan='+colspan+'>Do You Want To Create New Journey</td>';
			 requestDtls+='<td colspan='+colspan+' align="left"><input type="button" class="butn" id="isNewJrny" value="Yes" name="isNewJrny" onclick="getVisibleRequestYear(this.value)"/></td></tr>'								 	
			 	
			 requestDtls+='</tbody></table></div>'
				
			$("#taggedRqstDiv").html(requestDtls);	
			$("#taggedRqstDiv").show(); 
					
       }
    });
 
}    
    
    function getTaggedRequestForLtc(){
	
	$("#isTaggedORNew").val("false");
	$('#taggRequestCheck').val("true");

	var travelType=$('#TravelTypeDD').val();
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var personalNo=$('#personalNo').val();
	var travelMode=$('#travelMode').val();
	var serviceType=$('#serviceType').val();

	var jrnyTypeArr=document.getElementsByName("journeyType");
	var jrnyType;
	for(var i=0;i<jrnyTypeArr.length;i++)
	{
		if(jrnyTypeArr[i].checked)
		jrnyType=jrnyTypeArr[i].value
	}

	var TRRule=$('#TRRule').val();

	var requestDtls="";
	var authorityNoCheck="";
	var authorityDateCheck=""
	var ltcYearCheck=""

	$("#authNoLabel").hide();
	$("#authDateLabel").hide();
	$("#authDateText").show();
	$("#authNoText").show();
	$('#taggCheck').val("false");

	if(reqType!="ramBaanBooking")
	{
	 	setToStnValue("");
	 	setFrmStnValue("");
	 	setOrgStnValue("");
		setDestStnValue("");
	}
 
 	if(document.getElementById("ltcYear")!=null)
		document.getElementById("ltcYear").value='';
     
	 
	setFamilyDetails('');
	setReturnFamilyDetails('');

	setSourceDestinationByTrRule();
	setReturnSourceDestinationByTrRule();

					
	$.ajax(
	{
      url: $("#context_path").val()+"mb/getTaggedRequests",
      type: "get",
      data: "personalNo="+personalNo+"&TRRule="+TRRule+"&travelType="+travelTypeText ,
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
   
            var options = '';
		    var CurrDutyStn = msg.currDutyStn;
			var CurrSPRNRS = msg.currSPRNRS;
			
			$("#currDutyStn").val(CurrDutyStn);
			$("#currSprNrs").val(CurrSPRNRS);
			
			var IsLTCAvailedPrev = msg.isLTCAvailedPrev;
			var IsLTCAvailedCurr = msg.isLTCAvailedCurr;
			var IsLTCAvailedNext = msg.isLTCAvailedNext;
			var allwdPrevYearOnw = msg.allwdPrevYearOnw;
			var allwdPrevYearRet = msg.allwdPrevYearRet;
			var allwdCurrYearOnw = msg.allwdCurrYearOnw;
			var allwdCurrYearRet = msg.allwdCurrYearRet;
			var allwdNextYearOnw = msg.allwdNextYearOnw;
			var allwdNextYearRet = msg.allwdNextYearRet;
			
			 $("#noOfReqPrevOnw").val(allwdPrevYearOnw);
			 $("#noOfReqPrevRet").val(allwdPrevYearRet);					
			 $("#noOfReqOnw").val(allwdCurrYearOnw);
			 $("#noOfReqRet").val(allwdCurrYearRet);
             $("#noOfReqNextOnw").val(allwdNextYearOnw);
             $("#noOfReqNextRet").val(allwdNextYearRet);
             $("#IsLTCAvailedPrev").val(IsLTCAvailedPrev);
			 $("#IsLTCAvailedCurr").val(IsLTCAvailedCurr);
			 $("#IsLTCAvailedNext").val(IsLTCAvailedNext);
			  
			 var isRailRequestOnwardExist = msg.isRailRequestOnwardExist;		
		     $("#isRailRequestOnwardExist").val(isRailRequestOnwardExist);
		     
		 	 var isRailRequestReturnExist = msg.isRailRequestReturnExist;		
		     $("#isRailRequestReturnExist").val(isRailRequestReturnExist);
		     
		      var isAirRequestOnwardExist = msg.isAirRequestOnwardExist;		
		     $("#isAirRequestOnwardExist").val(isAirRequestOnwardExist);
		     
			 var isAirRequestReturnExist = msg.isAirRequestReturnExist;		
		     $("#isAirRequestReturnExist").val(isAirRequestReturnExist);     
	
			 var isAirOneWayExist = msg.isAirOneWayExist;		
		     $("#isAirOneWayExist").val(isAirOneWayExist);
			
			 if(msg.taggedRequestDtls==null || msg.taggedRequestDtls.length==0)
			{
			 	requestDtls+='<div><table width="100%" class="filtersearch"><tbody class="all1"><tr><td colspan="2">'+msg.message+'</td></tr>'
		    }
			else
			{
				requestDtls+='<div><table width="100%" class="filtersearch"><tbody class="all1">'
				requestDtls+='<tr><td class="label">Select</td>'
				requestDtls+='<td class="label">Request ID</td>'
				requestDtls+='<td class="label">Journey Mode</td>'
				requestDtls+='<td class="label">Authority No</td>'
				requestDtls+='<td class="label">Authority Date</td>'
				requestDtls+='<td class="label">TR Rule</td>'
				requestDtls+='<td class="label">Journey Type</td>'
				requestDtls+='<td class="label">State</td>'
					
					
				if(travelTypeText.indexOf('LTC')>-1)
				{    
                   requestDtls+='<td class="label">Calender Year</td>'
				}
				requestDtls+='</tr>'
                
                $.each(msg.taggedRequestDtls, function(index,obj){
                     
						var journeyMode = obj.journeyMode;
						var travelTypeID = obj.travelTypeID;
						var requestID = obj.requestID;
						var authorityDate = obj.authorityDate;
						var authorityNo = obj.authorityNo;
						var trRuleID = obj.trRuleID;
						var ltcYear = obj.ltcYear;
						var jrnyType = obj.jrnyType;
						var jrnyDate = obj.jrnyDate;
						var requestSeqno= obj.requestSeqNo;
						var approvalState = obj.approvalState;
						var desinationStn = obj.desinationStn;
						
						var isMixedModeRequest = obj.isMixedModeRequest;
						
						if (jrnyType == 'Return') 
						{
						   desinationStn = obj.fromStn;
						}
						 
						requestDtls+='<tr>'
						
						if((authorityNoCheck!=authorityNo || authorityDateCheck!=authorityDate || ltcYearCheck!=ltcYear) )
						{
						   if(travelTypeText.indexOf('LTC')>-1)
						   {
							   var minJryStr = 'year' + ltcYear + 'MinOnwardJrnyDate';
							   var minOnwardLtcJrnyDate = msg.minOnwardJrnyDate[minJryStr];
							    
								requestDtls+='<td><input type="radio" id="oldRequest" name="oldRequest" value="'+authorityDate+"~"+authorityNo+'~'+desinationStn+'~'+ltcYear+'~'+minOnwardLtcJrnyDate+'~'+isMixedModeRequest+'" onclick="setTaggedFields(\'No\')"/></td>'
						   }
						}
						else
						requestDtls+='<td></td>'
						
						if(journeyMode=='AIR'){
							requestDtls+='<td><a href="javascript:void(0);" onclick="popupForAirTaggedRequest(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
						}else{
							requestDtls+='<td><a href="javascript:void(0);" onclick="popupForTaggedRequestNew(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
						}
						
						requestDtls+='<td><label>'+journeyMode+'</label></td>'
						requestDtls+='<td><label>'+authorityNo+'</label></td>'
						requestDtls+='<td><label>'+authorityDate+'</label></td>'
						requestDtls+='<td><label>'+trRuleID+'</label></td>'
						requestDtls+='<td><label>'+jrnyType+'</label></td>'
						requestDtls+='<td><label>'+approvalState+'</label></td>'
						if(travelTypeText.indexOf('LTC')>-1)
						{
					    	requestDtls+='<td><label>'+ltcYear+'</label></td>'
					    }
						requestDtls+='</tr>'
						authorityNoCheck=authorityNo;
						authorityDateCheck=authorityDate;
						ltcYearCheck=ltcYear;
				  });
			 }
					 
			  if(travelTypeText.indexOf('LTC')>-1)
			  {
				requestDtls+='<tr><td>Do You Want To Create New Journey</td><td><input type="button" class="butn" id="isNewJrny" value="Yes" name="isNewJrny" onclick="getVisibleRequestYear(this.value)"/></td></tr>'								 	
			  }
			  else
				requestDtls+='<tr><td style="text-align:right"><a href="javascript:void(0);" onclick="hideRequests()">Hide Requests</a></td></tr>'
				
			  requestDtls+='</tbody></table></div>'
						
			  $("#taggedRqstDiv").html(requestDtls);	
			  $("#taggedRqstDiv").show(); 
					
       }
     });
     
}

function getTaggedRequestForPt()
{
	$("#isTaggedORNew").val("false");
	$('#taggRequestCheck').val("true");
	$("#newstnFieldsDiv").html("");

	var travelType=$('#TravelTypeDD').val();
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var personalNo=$('#personalNo').val();

	var jrnyTypeArr=document.getElementsByName("journeyType");
	var jrnyType;
	for(var i=0;i<jrnyTypeArr.length;i++)
	{
		if(jrnyTypeArr[i].checked)
		jrnyType=jrnyTypeArr[i].value
	}

	var TRRule=$('#TRRule').val();

	var requestDtls="";
	var authorityNoCheck="";
	var authorityDateCheck=""
	var ltcYearCheck=""

	$("#oldDutyStn").val(""); 
	$("#newDutyStn").val(""); 
	$("#oldSprNRS").val(""); 
	$("#newSprNRS").val(""); 
	
	$("#oldDutyNAP").val(""); 
	$("#newDutyNAP").val(""); 
	$("#oldSprNAP").val(""); 
	$("#newSprNAP").val(""); 
	$("#taggedReqId").val("");
	
	$("#authNoLabel").hide();
	$("#authDateLabel").hide();
	$("#authDateText").show();
	$("#authNoText").show();
	$('#taggCheck').val("false");

	if(reqType!="ramBaanBooking")
	{
	 	setToStnValue("");
	 	setFrmStnValue("");
	 	setOrgStnValue("");
		setDestStnValue("");
	}
 
 	if(document.getElementById("ltcYear")!=null)
		document.getElementById("ltcYear").value='';
     
	setFamilyDetails('');
	setReturnFamilyDetails('');
	setSourceDestinationByTrRule();
	setReturnSourceDestinationByTrRule();
	
					
	$.ajax({
      url: $("#context_path").val()+"mb/getTaggedRequests",
      type: "get",
      data: "personalNo="+personalNo+"&TRRule="+TRRule+"&travelType="+travelTypeText ,
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
   
                    var options = '';
				    var CurrDutyStn = msg.currDutyStn;
					var CurrSPRNRS = msg.currSPRNRS;
					
					var CurrDutyNAP = msg.currDutyNAP;
					var CurrSPRNAP = msg.currSPRNAP;
					
					$("#currDutyStn").val(CurrDutyStn);
					$("#currSprNrs").val(CurrSPRNRS);
					
					$("#currDutyNAP").val(CurrDutyNAP);
					$("#currSprNAP").val(CurrSPRNAP);
					
					var IsLTCAvailedCurr = msg.isLTCAvailedCurr;
					var IsLTCAvailedNext = msg.isLTCAvailedNext;
					var allwdCurrYearOnw = msg.allwdCurrYearOnw;
					var allwdCurrYearRet = msg.allwdCurrYearRet;
					var allwdNextYearOnw = msg.allwdNextYearOnw;
					var allwdNextYearRet = msg.allwdNextYearRet;
								
					 $("#noOfReqOnw").val(allwdCurrYearOnw);
					 $("#noOfReqRet").val(allwdCurrYearRet);
                     $("#noOfReqNextOnw").val(allwdNextYearOnw);
                     $("#noOfReqNextRet").val(allwdNextYearRet);
					 $("#IsLTCAvailedCurr").val(IsLTCAvailedCurr);
					 $("#IsLTCAvailedNext").val(IsLTCAvailedNext);
							
					 if(msg.taggedRequestDtls==null || msg.taggedRequestDtls.length==0)		
					 {
						requestDtls+='<table width="100%" class="filtersearch"><tbody class="all1"><tr><td colspan="2">'+msg.message+'</td></tr>'
						requestDtls+='<tr><td><label>Do You Want To Create New Journey</label></td><td><input type="button" class="butn" id="isNewJrny" value="Yes" name="isNewJrny" onclick="setTaggedFields(this.value)"/></td></tr>'
				     }
					 else
					 {
						requestDtls+='<table width="100%" class="filtersearch"><tbody class="all1"><tr><td class="label">Select</td><td class="label">Request ID</td>'
						requestDtls+='<td class="label">Journey Mode</td>'
						requestDtls+='<td class="label">Authority No</td>'
						requestDtls+='<td class="label">Authority Date</td>'
						requestDtls+='<td class="label">TR Rule</td>'
						requestDtls+='<td class="label">Journey Type</td>'
						requestDtls+='<td class="label">State</td>'
						requestDtls+='</tr>'
                        
                        $.each(msg.taggedRequestDtls, function(index,obj){
								
                            	var journeyMode = obj.journeyMode;
								var travelTypeID = obj.travelTypeID;
								var requestID = obj.requestID;
								var authorityDate = obj.authorityDate;
								var authorityNo = obj.authorityNo;
								var trRuleID = obj.trRuleID;
								var ltcYear = obj.ltcYear;
								var jrnyType = obj.jrnyType;
								var jrnyDate = obj.jrnyDate;
								
								var oldDutyStn = obj.oldDutyStn;
								var newDutyStn = obj.newDutyStn;
								var oldSPRNRS = obj.oldSPRNRS;
								var newSprNrs = obj.newSprNrs;
								
								var oldDutyNap = obj.oldDutyNap;
								var newDutyNap = obj.newDutyNap;
								var oldSprNap = obj.oldSprNap;
								var newSprNap = obj.newSprNap;
								
								var requestSeqno= obj.requestSeqNo;
								
								var approvalState = obj.approvalState;
								var desinationStn = obj.desinationStn;
								
								
								if (jrnyType == 'Return') 
								{
								   desinationStn = obj.fromStn;
	   							}
								 
								requestDtls+='<tr>'
								
								
								if((authorityNoCheck!=authorityNo || authorityDateCheck!=authorityDate || ltcYearCheck!=ltcYear) )
								{
								   if(travelTypeText.indexOf('PT')>-1)
								   {
								   		var minOnwardJrnyDate = msg.minOnwardJrnyDate.minOnwardJrnyDate;
								   		requestDtls+='<td><input type="radio" id="oldRequest" name="oldRequest" value="'+authorityDate+"~"+authorityNo+'~'+desinationStn+'~'+ltcYear+'~'+oldDutyStn+'~'+newDutyStn+'~'+oldSPRNRS+'~'+newSprNrs+'~'+oldDutyNap+'~'+newDutyNap+'~'+oldSprNap+'~'+newSprNap+'~'+minOnwardJrnyDate+'~'+requestID+'" onclick="setTaggedFields(\'No\')"/></td>'
								   }
								}
								else
								requestDtls+='<td></td>'
								if(journeyMode=='AIR'){
									requestDtls+='<td><a href="javascript:void(0);" onclick="popupForAirTaggedRequest(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
								}else{
									requestDtls+='<td><a href="javascript:void(0);" onclick="popupForTaggedRequestNew(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
								}
								
								requestDtls+='<td><label>'+journeyMode+'</label></td>'
								requestDtls+='<td><label>'+authorityNo+'</label></td>'
								requestDtls+='<td><label>'+authorityDate+'</label></td>'
								requestDtls+='<td><label>'+trRuleID+'</label></td>'
								requestDtls+='<td><label>'+jrnyType+'</label></td>'
								requestDtls+='<td><label>'+approvalState+'</label></td>'
								requestDtls+='</tr>'
								authorityNoCheck=authorityNo;
								authorityDateCheck=authorityDate;
								ltcYearCheck=ltcYear;
						  });
						  
						  requestDtls+='<tr><td>Do You Want To Create New Journey</td><td><input type="button" class="butn" id="isNewJrny" value="Yes" name="isNewJrny" onclick="setTaggedFields(this.value)"/></td></tr>'
					 }
					 requestDtls+='</tbody></table>'
						
					$("#taggedRqstDiv").html(requestDtls);	
					$("#taggedRqstDiv").show(); 
					
       }
      
      });
     
 }
 
 function getTaggedRequestCommon()
{
	
	var travelType=$('#TravelTypeDD').val();
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var personalNo=$('#personalNo').val();

	var jrnyTypeArr=document.getElementsByName("journeyType");
	var jrnyType;
	for(var i=0;i<jrnyTypeArr.length;i++)
	{
		if(jrnyTypeArr[i].checked)
		jrnyType=jrnyTypeArr[i].value
	}
	var requestDtls="";
	var TRRule=$('#TRRule').val();
				
	$.ajax(
	{
      url: $("#context_path").val()+"mb/getTaggedRequests",
      type: "get",
      data: "personalNo="+personalNo+"&TRRule="+TRRule+"&travelType="+travelTypeText ,
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
     
        var options = '';
	    var CurrDutyStn = msg.currDutyStn;
		var CurrSPRNRS = msg.currSPRNRS;
		
		$("#currDutyStn").val(CurrDutyStn);
		$("#currSprNrs").val(CurrSPRNRS);
		
		var IsLTCAvailedCurr = msg.isLTCAvailedCurr;
		var IsLTCAvailedNext = msg.isLTCAvailedNext;
		var allwdCurrYearOnw = msg.allwdCurrYearOnw;
		var allwdCurrYearRet = msg.allwdCurrYearRet;
		var allwdNextYearOnw = msg.allwdNextYearOnw;
		var allwdNextYearRet = msg.allwdNextYearRet;
								
		
		requestDtls+='<table width="100%" class="filtersearch"><tbody class="all1"><tr><td class="label">Request ID</td>'
		requestDtls+='<td class="label">Journey Mode</td>'
		requestDtls+='<td class="label">Authority No</td>'
		requestDtls+='<td class="label">Authority Date</td>'
		requestDtls+='<td class="label">TR Rule</td>'
		requestDtls+='<td class="label">Journey Type</td>'
		requestDtls+='<td class="label">Status</td>'
		requestDtls+='</tr>'
            
        $.each(msg.taggedRequestDtls, function(index,obj){    
       
            var journeyMode = obj.journeyMode;     
			var travelTypeID = obj.travelTypeID;
			var requestID = obj.requestID;
			var authorityDate = obj.authorityDate;
			var authorityNo = obj.authorityNo;
			var trRuleID = obj.trRuleID;
			var ltcYear = obj.ltcYear;
			var jrnyType = obj.jrnyType;
			var jrnyDate = obj.jrnyDate;
			var oldDutyStn = obj.oldDutyStn;
			var newDutyStn = obj.newDutyStn;
			var oldSPRNRS = obj.oldSPRNRS;
			var newSprNrs = obj.newSprNrs;
			var requestSeqno= obj.requestSeqNo;
			var approvalState = obj.approvalState;
			var desinationStn = obj.desinationStn;
			if (jrnyType == 'Return') 
			{
			   desinationStn = obj.fromStn;
			}
					 
			requestDtls+='<tr>'
			if(journeyMode=='AIR'){
				requestDtls+='<td><a href="javascript:void(0);" onclick="popupForAirTaggedRequest(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
			}else{
				requestDtls+='<td><a href="javascript:void(0);" onclick="popupForTaggedRequestNew(\'' +requestID +'\',\''+requestSeqno+'\')">'+requestID+'</a></td>'	
			}
			
			requestDtls+='<td><label>'+journeyMode+'</label></td>'
			requestDtls+='<td><label>'+authorityNo+'</label></td>'
			requestDtls+='<td><label>'+authorityDate+'</label></td>'
			requestDtls+='<td><label>'+trRuleID+'</label></td>'
			requestDtls+='<td><label>'+jrnyType+'</label></td>'
			requestDtls+='<td><label>'+approvalState+'</label></td>'
			requestDtls+='</tr>'
		});
		requestDtls+='<tr><td align="right" colspan="7"><a href="javascript:void(0);" onclick="hideRequests()">Hide Requests</a></td></tr>'
		requestDtls+='</tbody></table>'
			
		$("#taggedRqstDiv").html(requestDtls);	
		$("#taggedRqstDiv").show(); 
					
       }
     
     });
    
}


function hideRequests(){
	$("#taggedRqstDiv").hide();
}

function popupForTaggedRequestNew(requestId,requestSeqNo) 
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
	ajaxFace = new LightFace.Request(
	{
		width: 800,
		height: 400,
		url: $("#context_path").val()+'mb/railTaggedRequestView',
		buttons:[
					{ title: 'Close', event: function() { this.close(); } }
				],
		request:{ 
					data: { 
							requestID: requestId,
							requestSeqNo:requestSeqNo
						},
						method: 'post'
				},
				title: 'Request View'
	});
	ajaxFace.open();
}

function popupForAirTaggedRequest(requestId,requestSeqNo) 
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
	ajaxFace = new LightFace.Request(
	{
		width: 800,
		height: 400,
		url: $("#context_path").val()+'mb/airTaggedRequestView',
		buttons:[
					{ title: 'Close', event: function() { this.close(); } }
				],
		request:{ 
					data: { 
							requestViewId: requestId
						},
						method: 'post'
				},
				title: 'Air Request View'
	});
	ajaxFace.open();
}


function setTaggedFields(value)
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var serviceType=$('#serviceType').val();
	var travellerUnitService=$('#travellerUnitService').val();
	
	if(travelTypeText.indexOf('LTC')>-1)
	{	
		if(serviceType=='1' && travellerUnitService=='COAST GUARD'){
			setTaggedFieldsForLtcCivilian(value,'');
		}else{
			setTaggedFieldsForLtc(value);
		}
		
	}else if(travelTypeText.indexOf('PT')>-1)
	{
		setTaggedFieldsForPt(value);
	}
	else{
		setTaggedFieldsCommon(value);
	}
 }
 
 function setTaggedFieldsForLtc(value)
 {
	 var reqType = $('#reqType').val();
	 var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();
	 var authorityNo = "";
	 var authorityDate = "";
	 var isMixedModeRequest = "NO";
	var d = new Date();
	var curr_year = d.getFullYear();
	 var next_year = curr_year + 1;
	 var previous_year = curr_year - 1;

	 var YearCurrent = $("#YearCurrent").val();
	 var YearNext = $("#YearNext").val();
	 var yearPrevious = $("#YearPrevious").val();

	curr_year = YearCurrent;
	 next_year = YearNext;
	 previous_year = yearPrevious;
	 var destStn = "";
	 var dutyField = ""
	 var ltcYear = "";
	 var jrnyDate = "";

	 var noOfReqOnw = $("#noOfReqOnw").val();
	 var noOfReqNextOnw = $("#noOfReqNextOnw").val();
	 var IsLTCAvailedCurr = $("#IsLTCAvailedCurr").val();
	 var IsLTCAvailedNext = $("#IsLTCAvailedNext").val();

	 var noOfReqPrevOnw = $("#noOfReqPrevOnw").val().trim();
	 var isLTCAvailedPrev = $("#IsLTCAvailedPrev").val().trim();
	 var dateOfCommisioning=$("#dateOfCommisioning").val();
	 
	  var parts = dateOfCommisioning.split("-"); 
      var yearOfCommisioning = parts[2];
      var category_id=$("#category_id").val();
      if((category_id=='100019' || category_id=='100040' || category_id=='100041')  && parseInt(yearOfCommisioning)==parseInt(curr_year)){
		yearOfCommisioning = yearOfCommisioning-1;
	  }
	
    
	 if (value == 'Yes') {

        $("#authNoLabel").hide();
        $("#authDateLabel").hide();
        $("#authDateText").show();
        $("#authNoText").show();
        $("#toStnDropDown").show();
        $("#frmStnDropDown").show();

		 if (travelTypeText.indexOf('LTC') > -1) {              


			 if (IsLTCAvailedCurr == 'true' && IsLTCAvailedNext == 'true' && isLTCAvailedPrev == 'true') {
				 if (travelTypeText == 'LTC-HT') {
					 alert("You Have Already Availed LTC For Previous, Current Year And Next Year. So, Cannot Avail LTC-HT")
			}
				 if (travelTypeText == 'LTC-AI') {
					 alert("You Have Already Availed LTC For Previous, Current Year And Next Year. So, Cannot Avail LTC-AI")
		       }
				 return false;
		       }
			 if (IsLTCAvailedCurr == 'true' && IsLTCAvailedNext == 'true' && travelTypeText == 'LTC-SPL') {

				 alert("You Have Already Availed LTC-SPL For Current Year And Next Year. So, Cannot Avail LTC-SPL")
		       	return false;
		       }


			 if ((IsLTCAvailedCurr == 'true' && isLTCAvailedPrev == 'true') && (next_year == "")) {
				 if (travelTypeText == 'LTC-HT') {
		       		alert("You Have Already Availed LTC For Previous And Current Year, Cannot Avail LTC-HT")
		       }
				 if (travelTypeText == 'LTC-AI') {
		       		alert("You Have Already Availed LTC For Previous And Current Year , Cannot Avail LTC-AI")
		     }
		       return false;
		       }
			 if ((IsLTCAvailedCurr == 'true' && travelTypeText == 'LTC-SPL') && (next_year == "")) {
				 alert("You Have Already Availed LTC For Current Year , Cannot Avail LTC-SPL");
		       return false;
		     }


			 if (noOfReqPrevOnw == 'true' && noOfReqOnw == 'true' && noOfReqNextOnw == 'true' || ((noOfReqPrevOnw == 'true' || isLTCAvailedPrev == 'true') && (noOfReqOnw == 'true' || IsLTCAvailedCurr == 'true') && (noOfReqNextOnw == 'true' || IsLTCAvailedNext == 'true'))) {
				 alert("You Have Already Availed All Onward LTC For the Year " + previous_year + " And " + curr_year + " And " + next_year + ". So, Cannot Create New Request.")
      			return false;
       		}

			 dutyField += '<table><tr><td>Select Year For Which You Are Going To Apply for LTC</td>'

		    $('#taggCheck').val("true");
		    $("#isTaggedORNew").val("true");

			 if ((noOfReqPrevOnw == 'false') && (isLTCAvailedPrev == 'false') && (travelTypeText == 'LTC-HT' || travelTypeText == 'LTC-AI')  && parseInt(yearOfCommisioning) < parseInt(previous_year)) {
				 
				 if(previous_year=='2024'){
					 dutyField += '<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="' + previous_year + '" onchange="showAlertForLTC(this)" ><label>' + previous_year + '</label></td>'
				 }else{
					 dutyField += '<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="' + previous_year + '"><label>' + previous_year + '</label></td>'
				 }

				 
				}
				
			 if ((noOfReqOnw == 'false') && IsLTCAvailedCurr == 'false'&& parseInt(yearOfCommisioning) < parseInt(curr_year)) {
					dutyField += '<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="' + curr_year + '"><label>' + curr_year + '</label></td>'
				}  
			 if ((noOfReqNextOnw == 'false') && IsLTCAvailedNext == 'false') {
				if (next_year != "") {
					 dutyField += '<td><input type="radio"  class="txtfldM" id="ltcYear" name="ltcYear" value="' + next_year + '"><label>' + next_year + '</label></td>'
				 }   
					}

			 dutyField += '</tr></table>'

					}

     $("#taggedRqstDiv").html(dutyField);

     setSourceDestinationByTrRule();
     setReturnSourceDestinationByTrRule();

	 }
   
   if(value=='No')
   {
      $('#taggCheck').val("false");
	  $("#isTaggedORNew").val("true");
   
       var oldReq=document.getElementsByName("oldRequest")
        
       for(var i=0;i<oldReq.length;i++)
       {
         if(oldReq[i].checked==true)
         {
	          var auth=oldReq[i].value
	          var authArr=auth.split("~");
	          authorityDate=authArr[0];
	          authorityNo=authArr[1];
	          destStn=authArr[2];
	          ltcYear=authArr[3];
	          jrnyDate=authArr[4];
	          isMixedModeRequest=authArr[5];
	          checkRadio=true;
	      }
          
        }
        
        
        $("#authorityNo").val(authorityNo);
        $("#authorityDate").val(authorityDate);
        $("#authNoLabel").html('<label class="lablevalue">'+authorityNo+'</label>')
        $("#authNoLabel").show();
        $("#authDateLabel").html('<label class="lablevalue">'+authorityDate+'</label>')
        $("#authDateLabel").show();
        $("#authDateText").hide();
        $("#authNoText").hide();
        
        if(travelTypeText.indexOf('LTC')>-1)
         {
         	$("#ltcYearDiv").html('<input type="hidden" id="ltcYear" name="ltcYear" value="'+ltcYear+'"/>');
        	if(travelTypeText.indexOf('LTC-AI')>-1)
         	{
         	   if(reqType!='ramBaanBooking' && isMixedModeRequest=='NO')
             	{
		           		$("#frmStnDropDown").show();
			            $("#toStnDropDown").hide();  	
			            setToStnValue(destStn);
			            setDestStnValue(destStn);
		          
	            }
      		 } 
         
          	 setFamilyDetails('');
          	 setReturnFamilyDetails('');
          	 setSourceDestinationByTrRule();
          	 setReturnSourceDestinationByTrRule();
         
            $("#jrnyDateTagged").val(jrnyDate);  
            	
          } //end LTC 
         
          $("#taggedRqstDiv").hide();	
       
   	  } // end value=No case
}

function showAlertForLTC(obj) {
	var msg = "Kindly ensure that the LTC for the year 2024 has not already been availed during the same year. The entitlement and claim details are subject to verification at the PAO’s end. Any misuse or false claim detected during the verification process will invite appropriate administrative and disciplinary action as per extant rules"
	if (!confirm(msg) && obj.checked) {

		obj.checked = false;
	}
}


function setTaggedFieldsForLtcCivilian(value,msg)
{
	
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	var serviceType=$('#serviceType').val();
	
	var bothBlockAreSame=msg.bothBlockAreSame;
	var previousSubBlockYear=msg.previousSubBlockYear;
	var currentSubBlockYear=msg.currentSubBlockYear;
	var nextSubBlockYear=msg.nextSubBlockYear;
	
	var previousBlockYear=msg.previousBlockYear;
	var currentBlockYear=msg.currentBlockYear;
	var nextBlockYear=msg.nextBlockYear;
	var schemeType=msg.schemeType;
	
	var schemeAppliedDate=msg.schemeAppliedDate;
	
	var authorityNo="";
	var authorityDate="";
	var checkRadio=false
	var destStn="";
	var dutyField="";
	var ltcYear="";
	var jrnyDate="";
	var spouseNocNo="";
	var isMixedModeRequest="NO";
	
	var ltcYearStr=$("#ltcYearStr").val();
	
	
	var noOfReqPrevOnw=$("#noOfReqPrevOnw").val();
	var noOfReqPrevRet=$("#noOfReqPrevRet").val();
	
	var noOfReqRet=$("#noOfReqRet").val();
	var noOfReqOnw=$("#noOfReqOnw").val();
	
	var noOfReqNextRet=$("#noOfReqNextRet").val();
	var noOfReqNextOnw=$("#noOfReqNextOnw").val();
	
	var IsLTCAvailedPrev=$("#IsLTCAvailedPrev").val();
	var IsLTCAvailedCurr=$("#IsLTCAvailedCurr").val();
	var IsLTCAvailedNext=$("#IsLTCAvailedNext").val();
	
	var allwdJrny=$("#allwdJrny").val();
	var noOfRequests=$("#noOfReqCreated").val();
	var currDutyStn=$("#currDutyStn").val();
	var currSprNrs=$("#currSprNrs").val();

	var jrnyTypeArr=document.getElementsByName("journeyType");
	
  	if(value=='Yes')
  	{
  		
       // $("#authorityNo").val("");
       // $("#authorityDate").val("");
        $("#authNoLabel").hide();
        $("#authDateLabel").hide();
        $("#authDateText").show();
        $("#authNoText").show();
        $("#toStnDropDown").show();
        $("#frmStnDropDown").show();
   
    	if(travelTypeText.indexOf('LTC')>-1)
    	{
    	
    		if(nextSubBlockYear=='' && IsLTCAvailedCurr=='true' )
    		{
    		  alert("You Have Already Availed "+travelTypeText+" For Current Year. So, Cannot Avail "+travelTypeText+ " For Next Year, Please Tag Existing Request.")
		      return false;
    		}
    
     		if(IsLTCAvailedPrev=='true' && IsLTCAvailedCurr=='true' && IsLTCAvailedNext=='true')
     		{
		       if(travelTypeText=='LTC-HT')
		       {
		       		alert("You Have Already Availed LTC For Previous Year , Current Year And Next Year. So, Cannot Avail LTC-HT")
		       }
		       if(travelTypeText=='LTC-AI')
		       {
		       		alert("You Have Already Availed LTC For Previous Year , Current Year And Next Year. So, Cannot Avail LTC-AI")
		       }
       
		       if(travelTypeText=='LTC-SPL')
		       {
		       		alert("You Have Already Availed LTC-SPL For Previous Year ,Current Year And Next Year. So, Cannot Avail LTC-SPL")
		       }
       
       			return false;
      	    }
   
   		
   		
		   	 if((IsLTCAvailedPrev=='true') && (IsLTCAvailedCurr=='true') && (nextSubBlockYear=="") )
		   	 {
		       if(travelTypeText=='LTC-HT'){
		       		alert("You Have Already Availed LTC For Previous Year and Current Year, Cannot Avail LTC-HT")
		       }
		       if(travelTypeText=='LTC-AI'){
		       		alert("You Have Already Availed LTC For Previous Year and Current Year , Cannot Avail LTC-AI")
		       }
		       if(travelTypeText=='LTC-SPL'){
		       		alert("You Have Already Availed LTC For Current Year , Cannot Avail LTC-SPL")
		       }
		       return false;
		     }
   
        
       		if(noOfReqOnw=='true' && noOfReqNextOnw=='true' ||(  (noOfReqOnw=='true' || IsLTCAvailedCurr=='true')  && (noOfReqNextOnw=='true' || IsLTCAvailedNext=='true')))
       		{
       			alert("You Have Already Availed All Onward LTC For Year ["+currentSubBlockYear+"] And ["+nextSubBlockYear+"] So, Cannot Create New Request.")
      			return false;
       		}
       		
       		dutyField+='<table width="100%"><tr><td width="100%" align="left">Select Block Year For Which You Are Going To Apply LTC</td></tr>'
       		
      		dutyField+='<tr><td width="100%" align="left">'
      		dutyField+='<table ><tr>';
		    
		    $('#taggCheck').val("true");
		    $("#isTaggedORNew").val("true");
	       
	       
	       if(schemeType=='Yearly')
		   {}else
		   {
		        if((noOfReqPrevOnw=='false') && IsLTCAvailedPrev=='false' && previousSubBlockYear!='')
		        {
		         	dutyField+='<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="'+previousSubBlockYear+'"><label>'+previousSubBlockYear+'</label></td>'
		        }
	        }	
	
	       
	        if((noOfReqOnw=='false') && IsLTCAvailedCurr=='false')
	        {
	         	dutyField+='<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="'+currentSubBlockYear+'"><label>'+currentSubBlockYear+'</label></td>'
	        }
	        if((noOfReqNextOnw=='false') && IsLTCAvailedNext=='false')
	        {
	        	if(nextSubBlockYear!="")
	        	{
	         		dutyField+='<td><input type="radio" class="txtfldM" id="ltcYear" name="ltcYear" value="'+nextSubBlockYear+'"><label>'+nextSubBlockYear+'</label></td>'
			 	}
			}
			dutyField+='</tr></table>';
			dutyField+='</td></tr>';
		dutyField+='</table>'
		
      }// end LTC Case
      
     $("#taggedRqstDiv").html(dutyField);
     
     setSourceDestinationByTrRule();
     setReturnSourceDestinationByTrRule();
	 	
    } //end if(valuw=yes) case
   
   if(value=='No')
   {
   
      $('#taggCheck').val("false");
	  $("#isTaggedORNew").val("true");
   
      var oldReq=document.getElementsByName("oldRequest")
        
       for(var i=0;i<oldReq.length;i++)
       {
         if(oldReq[i].checked==true)
         {
              var auth=oldReq[i].value
	          var authArr=auth.split("~");
	          checkRadio=true
	          authorityDate=authArr[0];
	          authorityNo=authArr[1];
	          destStn=authArr[2];	          
	          ltcYear=authArr[3];
	          jrnyDate=authArr[4];
	          spouseNocNo=authArr[5];
	          isMixedModeRequest=authArr[6];
	      }
          
        }
        
        
        $("#authorityNo").val(authorityNo);
        $("#authorityDate").val(authorityDate);
        $("#authNoLabel").html('<label class="lablevalue">'+authorityNo+'</label>')
        $("#authNoLabel").show();
        $("#authDateLabel").html('<label class="lablevalue">'+authorityDate+'</label>')
        $("#authDateLabel").show();
        $("#authDateText").hide();
        $("#authNoText").hide();
        $("#spouseNocNo").val(spouseNocNo);
        
     
       	$("#ltcYearDiv").html('<input type="hidden" id="ltcYear" name="ltcYear" value="'+ltcYear+'"/>');
    	
    	if(travelTypeText.indexOf('LTC-AI')>-1 && isMixedModeRequest=='NO')
     	{
         	if(reqType!='ramBaanBooking'){
              
	           		$("#frmStnDropDown").show();
		            $("#toStnDropDown").hide();  	
		            setToStnValue(destStn)
	           
            }
  		 } 
         
        setFamilyDetails('');
        setReturnFamilyDetails('');
        setSourceDestinationByTrRule(); 
        setReturnSourceDestinationByTrRule(); 
       
        $("#jrnyDateTagged").val(jrnyDate);  
        $("#taggedRqstDiv").hide();	
       
   	  } // end value=No case
}


function setTaggedFieldsForPt(value) {

    var reqType = $('#reqType').val();
    var travelTypeText = $('#TravelTypeDD :selected').text().toUpperCase();

    var authorityNo = "";
    var authorityDate = "";
	var d = new Date();
	var curr_year = d.getFullYear();
    var next_year = curr_year + 1;


	curr_year = YearCurrent;
    next_year = YearNext;

    var destStn = "";
    var checkRadio = false
    var dutyField = ""
    var ltcYear = "";


    var oldDutyStn = "";
    var newDutyStn = false
    var oldSprNRS = ""
    var newSprNRS = "";


    var oldDutyNap = "";
    var newDutyNap = "";
    var oldSprNap = "";
    var newSprNap = "";
    var taggedReqId = "";



    var jrnyDate = "";
    var ltcYearStr = $("#ltcYearStr").val();

    var noOfReqRet = $("#noOfReqRet").val();
    var noOfReqOnw = $("#noOfReqOnw").val();
    var noOfReqNextRet = $("#noOfReqNextRet").val();
    var noOfReqNextOnw = $("#noOfReqNextOnw").val();
    var IsLTCAvailedCurr = $("#IsLTCAvailedCurr").val();
    var IsLTCAvailedNext = $("#IsLTCAvailedNext").val();
    var allwdJrny = $("#allwdJrny").val();
    var noOfRequests = $("#noOfReqCreated").val();
    var currDutyStn = $("#currDutyStn").val();
    var currSprNrs = $("#currSprNrs").val();
    var currDutyNap = $("#currDutyNAP").val();
    var currSprNap = $("#currSprNAP").val();


    var jrnyTypeArr = document.getElementsByName("journeyType");
	var jrnyType;

    for (var i = 0; i < jrnyTypeArr.length; i++) {
        if (jrnyTypeArr[i].checked)
            jrnyType = jrnyTypeArr[i].value
	}


    if (value == 'Yes') {

        $("#authNoLabel").hide();
        $("#authDateLabel").hide();
        $("#authDateText").show();
        $("#authNoText").show();
        $("#toStnDropDown").show();
        $("#frmStnDropDown").show();

        if (travelTypeText.indexOf('PT') > -1) {

            if (eval(noOfRequests) >= eval(allwdJrny)) {
        		alert("You Cannot Create A New Journey As You Have Already Availed/Requested Journeys Allowed On This TR Rule")
         		$('#taggCheck').val("");
        		return false;
        	}

       		$('#taggCheck').val("true");
			$("#isTaggedORNew").val("false");

            dutyField += '<table border="0" width="100%" class="formtxt">'
            dutyField += '<tr align="left"><td>Old Duty Station</td>'
            dutyField += '<td><input type="text" class="txtfldM" id="oldDutyStn" autocomplete="off" value="' + currDutyStn + '" name="oldDutyStn" readonly="true" >'

            dutyField += '<div class="suggestionsBox" id="suggestionsoldDutyStn" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListoldDutyStn"></div></div>' +
                '</td>' +
                '</tr>'
            dutyField += '<tr align="left">' +
                '<td>New Duty Station</td>' +
                '<td><input type="text" class="txtfldM" id="newDutyStn"  name="newDutyStn"   onkeyup="getStationList(this.value,this.id,0);"  onblur="showPAOFields();  clearSuggestionsBox(this,this.id);"  autocomplete="off">'
            dutyField += '<div class="suggestionsBox" id="suggestionsnewDutyStn" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListnewDutyStn"></div></div>' +
                '</td>' +
                '</tr>'
            dutyField += '<tr align="left">' +
                '<td>Old SPR NRS</td>' +
                '<td><input type="text"  class="txtfldM" autocomplete="off" id="oldSprNRS" value="' + currSprNrs + '" name="oldSprNRS" readonly="true" >'

            dutyField += '<div class="suggestionsBox" id="suggestionsoldSprNRS" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListoldSprNRS"></div></div>' +
                '</td>' +
                '</tr>'
            dutyField += '<tr align="left">' +
                '<td>New SPR NRS</td>' +
                '<td><input  class="txtfldM" type="text" autocomplete="off" id="newSprNRS" name="newSprNRS"   onblur="clearSuggestionsBox(this,this.id);" onkeyup="getStationList(this.value,this.id,0);" autocomplete="off">'
            dutyField += '<div class="suggestionsBox" id="suggestionsnewSprNRS" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListnewSprNRS"></div></div>' +
                '</td>' +
                '</tr>'
            /* For Air Field 	*/
            dutyField += '<tr align="left">' +
                '<td>Old Duty NAP</td>' +
                '<td><input  class="txtfldM" type="text" autocomplete="off" id="oldDutyNap" name="oldDutyNap" value="' + currDutyNap + '"  readonly="true" >'

            dutyField += '<div class="suggestionsBox" id="suggestionsoldDutyNap" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListoldDutyNap"></div></div>' +
                '</td>' +
                '</tr>'

            dutyField += '<tr align="left">' +
                '<td>New Duty NAP</td>' +
                '<td><input  class="txtfldM" type="text" autocomplete="off" id="newDutyNap" name="newDutyNap"  onblur=" showPAOFields();  clearSuggestionsBox(this,this.id);"   onkeyup="getAirportOnPtTravel(this, this.id)"  autocomplete="off">'
            dutyField += '<div class="suggestionsBox" id="suggestionsnewDutyNap" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListnewDutyNap"></div></div>' +
                '</td>' +
                '</tr>'

            dutyField += '<tr align="left">' +
                '<td>Old SPR NAP</td>' +
                '<td><input  class="txtfldM" type="text" autocomplete="off" id="oldSprNap" name="oldSprNap" value="' + currSprNap + '" readonly="true" >'

            dutyField += '<div class="suggestionsBox" id="suggestionsoldSprNap" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListoldSprNap"></div></div>' +
                '</td>' +
                '</tr>'

            dutyField += '<tr align="left">' +
                '<td>New SPR NAP</td>' +
                '<td><input  class="txtfldM" type="text" autocomplete="off" id="newSprNap" name="newSprNap"  onblur="clearSuggestionsBox(this,this.id);"   onkeyup="getAirportOnPtTravel(this, this.id)" autocomplete="off" >'
            dutyField += '<div class="suggestionsBox" id="suggestionsnewSprNap" style="display: none;" >' +
                '<div  class="suggestionList"  id="autoSuggestionsListnewSprNap"></div></div>' +
                '</td>' +
                '</tr>'

                dutyField+='<tr align="left" >' +
                						'<td>Rail Accounting Office</td>' +
                						'<td id="rail_pao" style="display:none"><select id="railAccountOffice" name="railAccountOffice" class="combo" style="width:18%"><option value="">Select</option>'

                						'</td>' +
                						'</tr>'
                			dutyField+='<tr align="left" >' +
                						'<td>Air Accounting Office</td>' +
                						'<td id="air_pao" style="display:none"><select id="airPaoAcc" name="airPaoAcc" class="combo" style="width:18%"><option value="">Select</option>'
                						'</td>' +
                						'</tr>'



            dutyField += '<tr>' +
                '<td colspan="2"><input type="button" value="Set New Fields" class="butn" onclick="setNewFields(\'newreq\');"/></td>' +
                '</tr>'
            dutyField += '</table>'
    	}
     	$("#taggedRqstDiv").html(dutyField);
     	setSourceDestinationByTrRule();
     	setReturnSourceDestinationByTrRule();

    } //end if(valuw=yes) case

    if (value == 'No') {

      $('#taggCheck').val("true");
	  $("#isTaggedORNew").val("true");

        var oldReq = document.getElementsByName("oldRequest")

        for (var i = 0; i < oldReq.length; i++) {
            if (oldReq[i].checked == true) {
                var auth = oldReq[i].value
                var authArr = auth.split("~");
                authorityNo = authArr[1];
                authorityDate = authArr[0];
                ltcYear = authArr[3];
                checkRadio = true
                destStn = authArr[2];
                oldDutyStn = authArr[4];
                newDutyStn = authArr[5];
                oldSprNRS = authArr[6];
                newSprNRS = authArr[7];
                oldDutyNap = authArr[8];
                newDutyNap = authArr[9];
                oldSprNap = authArr[10];
                newSprNap = authArr[11];
                jrnyDate = authArr[12];
                taggedReqId = authArr[13];
          }
        }


        $("#authorityNo").val(authorityNo);
        $("#authorityDate").val(authorityDate);
        $("#authNoLabel").html('<label class="lablevalue">' + authorityNo + '</label>')
        $("#authNoLabel").show();
        $("#authDateLabel").html('<label class="lablevalue">' + authorityDate + '</label>')
        $("#authDateLabel").show();
        $("#authDateText").hide();
        $("#authNoText").hide();

        if (travelTypeText.indexOf('PT') > -1) {
            var newFields = "";
          	$('#taggCheck').val("true");
          	
            newFields += '<input type="hidden" id="oldDutyStn" name="oldDutyStn" value=""/>'
            newFields += '<input type="hidden" id="newDutyStn" name="newDutyStn" value=""/>'
            newFields += '<input type="hidden" id="oldSprNRS" name="oldSprNRS" value=""/>'
            newFields += '<input type="hidden" id="newSprNRS" name="newSprNRS" value=""/>'

            newFields += '<input type="hidden" id="oldDutyNap" name="oldDutyNap" value=""/>'
            newFields += '<input type="hidden" id="newDutyNap" name="newDutyNap" value=""/>'
            newFields += '<input type="hidden" id="oldSprNap" name="oldSprNap" value=""/>'
            newFields += '<input type="hidden" id="newSprNap" name="newSprNap" value=""/>'
            newFields += '<input type="hidden" id="taggedReqId" name="taggedReqId" value=""/>'

             $("#newstnFieldsDiv").html(newFields)

            $("#oldDutyStn").val(oldDutyStn);
            $("#newDutyStn").val(newDutyStn);
            $("#oldSprNRS").val(oldSprNRS);
            $("#newSprNRS").val(newSprNRS);

            $("#oldDutyNap").val(oldDutyNap);
            $("#newDutyNap").val(newDutyNap);
            $("#oldSprNap").val(oldSprNap);
            $("#newSprNap").val(newSprNap);
            $("#taggedReqId").val(taggedReqId);

            if (reqType != "ramBaanBooking") {
                setNewFields('oldreq');
            }

        } //end PT

        $("#taggedRqstDiv").hide();

   	  } // end value=No case
}


function setNewFields(param)
{

     if(param=='newreq'){

	     var oldDutyStn=$("#oldDutyStn").val();
	     var newDutyStn=$("#newDutyStn").val();
	     var oldSprNRS=$("#oldSprNRS").val();
	     var newSprNRS=$("#newSprNRS").val();
	     var oldDutyNap=$("#oldDutyNap").val();
	     var newDutyNap=$("#newDutyNap").val();
	     var oldSprNap=$("#oldSprNap").val();
	     var newSprNap=$("#newSprNap").val();
	     var personalNo=$('#personalNo').val();


         if($('#oldDutyStn').val() == '' ){
		    alert("Please Enter Old Duty Station");
		    document.getElementById("oldDutyStn").focus();
		    return false;
		 }
		 if($('#newDutyStn').val() == ''){
		    alert("Please Enter New Duty Station");
		    document.getElementById("newDutyStn").focus();
		    return false;
		 }

		 if($('#oldDutyNap').val() == ''){
		    alert("Please Enter Old Duty Airport");
		    document.getElementById("oldDutyNap").focus();
		    return false;
		 }
		 if($('#newDutyNap').val() == ''){
		    alert("Please Enter New Duty Airport");
		    document.getElementById("newDutyNap").focus();
		    return false;
		 }


		 $.ajax({
         	      	url: $("#context_path").val()+"mb/validateVisitorNewFields",
         	      	type: "POST",
         	      	data: "personalNo="+personalNo+"&oldDutyStn="+oldDutyStn+"&newDutyStn="+newDutyStn+"&oldSprNRS="+oldSprNRS+"&newSprNRS="+newSprNRS+"&oldDutyNap="+oldDutyNap+"&newDutyNap="+newDutyNap+"&oldSprNap="+oldSprNap+"&newSprNap="+newSprNap,
         	      	dataType: "text",
         	      	success: function(msg)
         	      	{
         				if(msg!="")
         				{
         					alert(msg);
         					$('#taggCheck').val("");
          				}
         				$('#taggCheck').val("true");

         		    },error: function(xhr, status, error) {
                                 }
         });
	}

    setSourceDestinationByTrRule();
    setReturnSourceDestinationByTrRule();
	$("#taggedRqstDiv").hide();

}


function setTaggedFieldsCommon(value)
{
	var reqType=$('#reqType').val();
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
}


function getVisibleRequestYear(value)
{
	
	var trRuleId=$('#TRRule').val();
	var serviceType=$('#serviceType').val();
	var travellerUnitService=$('#travellerUnitService').val();
	
	$.ajax({
      
      url: $("#context_path").val()+"mb/getVisibleRequestYear",
      type: "get",
      data: "trRuleId="+trRuleId ,
      dataType: "json",
      success: function(msg)
      {
		   
			if(serviceType=='1' && (travellerUnitService=='COAST GUARD' || travellerUnitService=='NavyCivilian' || travellerUnitService=='DRDO' || travellerUnitService=='DAD'))
			{
				setCivilianTaggedFields(value,msg)
				
			}
			
			else{
				var YearCurrent = msg.yearCurrent;
				var YearNext = msg.yearNext;
				var YearPrevious = msg.previousYear;
				$("#YearCurrent").val(YearCurrent);
				$("#YearNext").val(YearNext);
				$("#YearPrevious").val(YearPrevious);
				setTaggedFields(value);
			}		
	  }
   });
  
}


function setCivilianTaggedFields(value,msg)
{
	//alert("Inside setCivilianTaggedFields");
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	
    if(travelTypeText.indexOf('LTC')>-1){
		setTaggedFieldsForLtcCivilian(value,msg);
	}else if(travelTypeText.indexOf('PT')>-1)
	{
		setTaggedFieldsForPt(value);
	}
	else{
		setTaggedFieldsCommon(value);
	}
}
/*  Function to Set Air/Rail PAO on PT Travel  */
function showPAOFields() {
    var newDutyNAP = $("#newDutyNAP").val();
    var newDutyStn = $("#newDutyStn").val();
    var oldDutyNAP = $("#oldDutyNAP").val();
    var oldDutyStn = $("#oldDutyStn").val();
    var serviceId = $("#service_id").val();
    var categoryId = $("#category_id").val();
    var railOffice = $("#railOffice").val();
    var airOffice = $("#airAccountOffice").val();

    var rail_options;
    var air_options;

    if(newDutyStn != ''  || newDutyNAP != ''){
     if (oldDutyStn != newDutyStn || oldDutyNAP != newDutyNAP) {
            $("#rail_pao").show();
            $("#air_pao").show();
            $("#paoChanged").val(0);
        } else {
            $("#rail_pao").hide();
            $("#air_pao").hide();
            $("paoChanged").val(1);
        }
    }

        $.ajax({
            url: $("#context_path").val() + "mb/getPayAccountOfc",
            type: "POST",
            data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
            dataType: "text",
            success: function(data) {
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
                if (railCount != 1) {
                    rail_options += '<option value="-1">select<\/option>';
                }
                $(RailVisitorGroup).each(function(index) {
                    var id = RailVisitorGroup[index].acuntoficeId;
                    var name = RailVisitorGroup[index].name;
                    rail_options += '<option value="' + id + '">' + name + '<\/option>';
                });
                if (airCount != 1) {
                    air_options += '<option value="-1">select<\/option>';
                }
                $(AirVisitorGroup).each(function(index) {
                    var id = AirVisitorGroup[index].acuntoficeId;
                    var name = AirVisitorGroup[index].name;
                    air_options += '<option value="' + id + '">' + name + '<\/option>';
                });
                $("#railAccountOffice").html(rail_options);
                $("#airPaoAcc").html(air_options);
            } //END SUCCESS
        });
}




/* **************** FILLING AIRPORT ON PT TRAVEL ********** */
function getAirportOnPtTravel(obj, idx) {
    var officeNRS = $(obj).val();
    var airPortList = "";

    if (officeNRS.length > 1) {

        $.ajax({
            type: "post",
            url: $("#context_path").val() + "mb/getNearestAirport",
            data: "airPortName=" + officeNRS,
            datatype: "application/json",
            success: function(data) {
                $.each(data, function(index, name) {
                    if(name == "Airport Name Not Exist") {
                        airPortList += '<li>' + name + '</li>';
                    } else {
                        airPortList += '<li onClick="fillAirportOnPTTravel(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
                    }
                });

                $('#autoSuggestionsList' + idx).html(airPortList);
                $('#suggestions' + idx).show();
            }
        });
    }
}


function fillAirportOnPTTravel(thisValue, idx) {
   
    if (idx == 'newDutyNap') {
        $('#newDutyNap').val(thisValue);
    } else if (idx == 'newSprNap') {
        $('#newSprNap').val(thisValue);
    }

    setTimeout(() => {
            $('#suggestions' + idx).hide();
    }, 200);
}



/* Clear Suggestions on Blur Event */
function clearSuggestionsBox(ob, idx) {
    $(ob).val('');
    setTimeout(() => {
            $('#suggestions' +idx).hide();
        }, 200);
}















