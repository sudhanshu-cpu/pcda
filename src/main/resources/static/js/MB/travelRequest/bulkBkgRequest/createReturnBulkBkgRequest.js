var returnAttNo=0;
var returnPartyDependentNo=0;

function setReturnSourceDestinationByTrRule() {
	var journeyMode;
	var journeyModeArr=document.getElementsByName("returnJourneyMode");
	
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
			$("#airJrnyTypeDiv"+temp.value).show();
			
		}
		
		setReturnOrgDestStnByTrRule('');
		
	}	
 }
 
 


function clrReturnVaiFileds()
{
	if(document.getElementById("returnViaRelxRoute")!=null)
	{
		document.getElementById("returnViaRelxRoute").checked=false;
		$("#returnSelectRelaxedRoute").hide("fast"); 
	}
 	$("#returnJourneyDate").show("fast");
     
} 

function setReturnFamilyDetails(msg)
{  
   
    if (msg == null || msg == ''){
  		msg = $.parseJSON($('#travelerObj').val());
  	}
   
   var taggedLtcYear='';
   
   if(document.getElementById("ltcYear")!=null)
		taggedLtcYear=document.getElementById("ltcYear").value; 
	
   var passngrDtlsLng=$(msg).find('FamilyDetailSeq').length;
   var familyDetails="";
   $("#returnRelation").val("");
   $("#returnGender").val("");
   $("#returnFamilyName").val("");
   $("#returnDOB").val("");
   $("#returnDepSeqNo").val("");
   $("#returnErsPrntNameVal").val("");
   $("#returnOtherRel").val("");
   $("#returnAge").val("");
    familyDetails+=''
	familyDetails+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt">'
    familyDetails+='<tr align="left"><td colspan="7"><b>Passengers Details</b></td></tr>'
    familyDetails+='<tr><td class="label" width="15%">Name</td>'
    familyDetails+='<td class="label" width="15%">ERS Print Name</td>'
    familyDetails+='<td class="label" width="15%">Relation</td>'
    familyDetails+='<td class="label" width="15%">Gender</td>'
    familyDetails+='<td class="label" width="15%">Date Of Birth</td>'
    familyDetails+='<td class="label" width="15%" id="validReturnIdInfoCol" style="display: none;">Id Proof</td>'        	
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
   			
			$("#returnFamilyName").val($("input#returnFamilyName").val()+","+familyName);		
			$("#returnRelation").val($("input#returnRelation").val()+","+RelationCode);
			$("#returnGender").val($("input#returnGender").val()+","+GenderCode);
			$("#returnDOB").val($("input#returnDOB").val()+","+dob);
			$("#returnDepSeqNo").val($("input#returnDepSeqNo").val()+","+DepSeqNo);
			$("#returnErsPrntNameVal").val($("input#returnErsPrntNameVal").val()+","+ersPrintName);
			$("#returnOtherRel").val($("input#returnOtherRel").val()+",");
			$("#returnAge").val($("input#returnAge").val()+","+age);
   				
			familyDetails+='<div id="returnFamDtlsDiv'+ j+'"><tr><td width="15%"><label >'+ familyName +'</label></td>'
			familyDetails+='<td><label>'+ ersPrintName +'</label></td>'
			familyDetails+='<td><label>'+ relation +'</label></td>'
			familyDetails+='<td><label>'+ gender +'</label></td>'
			familyDetails+='<td><label>'+ dob +'</label></td>'
			
			familyDetails+='<td class="validReturnIdValCol" style="display: none;" id="validReturnIdInfoCol"> <input type="checkbox" disabled="true" name="returnValidIdCardPass" value="'+DepSeqNo+'" onclick="showReturnValidIdAlert('+j+')" id="returnValidIdCardPassCheck' +j+ '"/></td>'        	
        	
				var JourneyCheck = obj.journeyCheck;
				
					if(JourneyCheck=='true')
					{
						familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /><div style="display:none" id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
					}
			    	else
			    	{
						familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" ></div><div id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
					}	
	
			j=j+1;
					 
	    });// end of FamilyDetailSeq loop
	    
		familyDetails+='</table>';
		
        $("#returnFamilyDetails").html(familyDetails);
           	    
}


function setReturnTravelMode(msg) {

	var isExist = msg.isExist;
	if (isExist == 'YES') {
		

		var returnJrnyInfoTxt = "";
		returnJrnyInfoTxt += '<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch">';
		returnJrnyInfoTxt += '<tr align="left" bgcolor="#fbfac8">';
		returnJrnyInfoTxt += '<td width="100%" colspan="2" class="d-flex" style="column-gap:0.2rem">';
		returnJrnyInfoTxt += 'Select Journey Mode:';
		returnJrnyInfoTxt += '<input type="radio" value="1" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnAir();" checked="true" />Air';
		returnJrnyInfoTxt += '</td>';
		returnJrnyInfoTxt += '</tr>';
		returnJrnyInfoTxt += '</table>';

		$("#returnJourneyModeDivId").html(returnJrnyInfoTxt);
		showReturnAir();

	} else {
		alert("Any travel mode is not associated with selected travle rule.");
	}
}



function fillReturnFrmStn(thisValue) {
	 $('#returnFromStation').val(thisValue);
	 setTimeout("$('#returnSuggestions').hide();", 200);
 }

function fillReturnToStn(thisValue) {
	 $('#returnToStation').val(thisValue);
	 setTimeout("$('#returnSuggestionsTo').hide();", 200);
 
 }
 


function setReturnPartyDependentAllowedDiv()
{
	
		returnPartyDependentNo++;
    
     	var partyDetailsInnerHtml="";
  		var isTatkalFlagValue=0;
		var travelMode=$('#returnTravelMode').val();
		
		if(travelMode==0){
			isTatkalFlagValue=document.getElementById("returnIsTatkalFlag").value;	
		}
		if(travelMode==2){
			isTatkalFlagValue=document.getElementById("returnIsTatkalFlagMixed").value;	
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
        partyDetailsInnerHtml+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" id="returnAttPartyDetailsTable" name="returnAttPartyDetailsTable">'
        partyDetailsInnerHtml+='<tr><td width="20%" align="left" class="lablevalue"><input class="txtfldM" type="text" size="20" id="returnDependentPersonalNo'+returnPartyDependentNo+'" name="returnDependentPersonalNo" autocomplete="off" onkeyup="this.value = this.value.toUpperCase()" onchange="setGetPersonalDetails(this.value,'+returnPartyDependentNo+',1)"/></td>'
        partyDetailsInnerHtml+='<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="returnPartyDepName'+returnPartyDependentNo+'" name="returnPartyDepName" readonly/></td>'
		partyDetailsInnerHtml+='<td width="25%" align="left" class="lablevalue"><input type="text" size="15" id="returnPartyDepErsName'+returnPartyDependentNo+'" name="returnPartyDepErsName" readonly/></td>'
		partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input  type="text" size="5"  id="returnPartyDepGender'+returnPartyDependentNo+'" name="returnPartyDepGender" readonly></td>'
					
        if(isTatkalFlagValue==1)
		{
			partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input  type="text" size="10"  id="returnPartyDepDOB'+returnPartyDependentNo+'" name="returnPartyDepDOB" readonly></td>'
			partyDetailsInnerHtml+='<td width="10%" align="left" class="lablevalue"><input type="checkbox" class="validIdCardPartyGroupCheck" id="returnValidIdCardPartyGroupCheck'+returnPartyDependentNo+'" value="'+returnPartyDependentNo+'" name="returnValidIdCardPartyGroupCheck"/></td></tr>'
			
		}else{
			partyDetailsInnerHtml+='<td width="20%" align="left" class="lablevalue"><input  type="text" size="10"  id="returnPartyDepDOB'+returnPartyDependentNo+'" name="returnPartyDepDOB" readonly></td></tr>'
		}
                 
	    partyDetailsInnerHtml+='</table>'
	    partyDetailsInnerHtml+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" >'
	    partyDetailsInnerHtml+='<tr><td colspan="4" align="right"><input type="button" value="Add Row" class="butn" onclick="addReturnPartyDependentRows();"/>&nbsp;<input type="button" class="butn" value="Delete Row" onclick="delReturnPartyDependentRow();"/></td></tr>'
	    partyDetailsInnerHtml+='</table></div>'
	   
	    
	    $("#returnPartyDependentDetails").html(partyDetailsInnerHtml);
	    $("#returnPartyMemberCount").val(returnPartyDependentNo);
         
} 


function addReturnPartyDependentRows()
{
     returnPartyDependentNo++;
    
     var tbl = document.getElementById('returnAttPartyDetailsTable');
  	 var lastRow = tbl.rows.length;
	 var row=tbl.insertRow(lastRow);
	 
	 var cellRight = row.insertCell(0);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'returnDependentPersonalNo'+returnPartyDependentNo;
	 el.name= 'returnDependentPersonalNo';
	 el.size = 20;
	 el.className="txtfldM";
	 el.setAttribute("autocomplete","off");
	 el.setAttribute("onkeyup","this.value = this.value.toUpperCase()");
	 el.setAttribute("onchange","setGetPersonalDetails(this.value,"+returnPartyDependentNo+",1)");
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'returnPartyDepName'+returnPartyDependentNo;
	 el.name= 'returnPartyDepName';
	 el.setAttribute("readonly","readonly");
	 el.size = 15;
	 cellRight.appendChild(el);
	 
	
	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'returnPartyDepErsName'+returnPartyDependentNo;
	 el.name = 'returnPartyDepErsName';
	 el.setAttribute("readonly","readonly");
	 el.size = 15;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'returnPartyDepGender'+returnPartyDependentNo;
	 el.name = 'returnPartyDepGender';
	 el.setAttribute("readonly","readonly");
	 el.size = 5;
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(4);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.id = 'returnPartyDepDOB'+returnPartyDependentNo;
	 el.name= 'returnPartyDepDOB';
	 el.setAttribute("readonly","readonly");
	 el.size = 10;
	 cellRight.appendChild(el);
	
	var isTatkalFlagValue=0;
	var travelMode=$('#returnTravelMode').val();
	
	if(travelMode==0){
		isTatkalFlagValue=document.getElementById("returnIsTatkalFlag").value;	
	}
	if(travelMode==2){
		isTatkalFlagValue=document.getElementById("returnIsTatkalFlagMixed").value;	
	}
			
	if(isTatkalFlagValue==1)
	{
	 var cellRight = row.insertCell(5);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'checkbox';
	 el.id = 'returnValidIdCardPartyGroupCheck'+returnPartyDependentNo;
	 el.name= 'returnValidIdCardPartyGroupCheck';
	 el.setAttribute("readonly","readonly");
	 el.size = 10;
	 el.value =returnPartyDependentNo;
	 el.className="validIdCardPartyGroupCheck";
	 cellRight.appendChild(el);
	 
	}
	
	$("#returnPartyMemberCount").val(returnPartyDependentNo);

} 


function delReturnPartyDependentRow()
{
	var partyRowCount = $('#returnAttPartyDetailsTable tr').length;
	returnPartyDependentNo--;
    if(partyRowCount==1)
    {
    	 returnPartyDependentNo=0;
     	$("#returnPartyMemberCount").val(returnPartyDependentNo);
     	$("#returnPartyDependentDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Traveller" class="butn" id="" onclick="setReturnPartyDependentAllowedDiv();"/></td></tr></table>');
    }
    else
    {
    	$('#returnAttPartyDetailsTable tr:last').remove();
    	$("#returnPartyMemberCount").val(returnPartyDependentNo);
    }
 
}


function returnFromStationList()	{
	
		var fromStation=$('#returnFromStation').val();
	
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
	                    StationList+='<li onClick="fillReturnFrmStn(\'' +name +'\')">' +name+'</li>';
	              });
	              
	              
				  $("#returnAutoSuggestionsList").html(StationList);		
	              $('#returnSuggestions').show();
	                       
			    }
			});
		}
}

	
function returnToStationList(){ 
	

		var toStation=$('#returnToStation').val();
	
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
		                  StationList+='<li onClick="fillReturnToStn(\'' +name +'\')">' +name+'</li>';
	                  });
	                
					 $("#returnAutoSuggestionsListTo").html(StationList);		
	                 $('#returnSuggestionsTo').show();
	           
	  		}
	  	  });
		}
}	


function validateReturnViaFields()
{     	
			
		var check="";
        
        if(document.getElementById("returnViaRelxRoute")!=null ){
	        if(document.getElementById("returnViaRelxRoute").checked==true)
	        {
	        	alert("You can Either select Via Route or Relaxed Route")
	        	document.getElementById("returnViaRoute").checked=false;
	        	return false;
	       	}
        }
        
       if(document.getElementById("return_JourneyDate").value=="dd/mm/yyyy" || document.getElementById("return_JourneyDate").value=="" )
	   {
			alert("Please Enter Journey Date")
			document.getElementById("return_JourneyDate").focus();
        	document.getElementById("returnViaRoute").checked=false;
	    	return false;
	   }
	  	if(document.getElementById("returnFromStation").value=="")
	  	{
	   		alert("Please Enter From Station And To Station Fields");
	   		document.getElementById("returnViaRoute").checked=false;
		    return false;
	   }
	   if(document.getElementById("returnToStation").value=="")
	   {
	   		alert("Please Enter From Station And To Station Fields");
		    document.getElementById("returnViaRoute").checked=false;
		    return false;
	   }
	   
	   check=validate_required(document.getElementById("returnEntitledClass"),"Please Select Entitled Class");
	   if(!check)
	   document.getElementById("returnViaRoute").checked=false;
	   
	   return check;
	   
} 	


var return_k=2;


function fillReturnStn(thisValue,id,jrnyType) 
{
	//alert("Inside fillStn");	
	var idArr=id.split("returnToStation");
	var idNumber=parseInt(idArr[1])+1;
	$('#'+id).val(thisValue);
	setViaStnFrmToStn(thisValue,"returnFrmStation"+idNumber)
	setTimeout("$('#suggestions"+id+"').hide();", 200);
}


 
function setReturnValue(j)
{   
	var relation=document.getElementById("returnRelationCheck"+j).value;
    var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
    var reqType=$("#reqType").val();
    var travelMode=$('#returnTravelMode').val();
  
    if($('#returnRemoveFrmList'+j).is(":checked"))
    {
		document.getElementById("returnRemoveFrmList"+j).value=0;
		var famDtlsDiv=document.getElementById("returnFamDtlsDiv"+j)
		famDtlsDiv.setAttribute("class" ,"familyDtlsBack");
	}
	else
	{
		document.getElementById("returnRemoveFrmList"+j).value=1;
		$("#returnReqstCheck").val("false")
		if(!document.getElementById("returnRemoveFrmList0").checked)
		{
			$("#returnRequisiteDiv").hide();
		}
	
	}

	if(travelMode==1){
		if($("#returnAirsrchDiv")!=null){
			$("#returnAirsrchDiv").show();
		}
	}

}
 
  	
  	function getReturnRelationFromResponse(){
	
	var msg = $.parseJSON($('#travelerObj').val());
	
           var relOptions="<option value=''>Select</option>";
                  $.each(msg.retationType,function(id, name){
	             
				        if(id!=0)
						relOptions += '<option value="' + id + '">' +name + '<\/option>';
					});
   return 	relOptions;				
					
}

function getReturnGenderFromResponse(){
	
	var msg = $.parseJSON($('#travelerObj').val());
	
      var genOptions="<option value=''>Select</option>";
        
	     $.each(msg.gender,function(id, name){
				genOptions += '<option value="' + id + '">' +name + '<\/option>';
	      });	
	return genOptions;
}  	

function setReturnPrintERSName(name,k){
	name=name.substring(0,15);
	document.getElementById("returnAttErsName"+k).value=name;
}

function setReturnDefaultValueForParty()
{
	var selEntitledClass=$('#returnEntitledClass').val();	
	
	var isPartyBooking=$('#isPartyBooking').val();	
	
	if(isPartyBooking=='Yes')
	{
		partyDependentNo=0;
		$("#returnPartyMemberCount").val("0");
		var partyDetailsHtml='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Party Dependent" class="btn" id="" onclick="setReturnPartyDependentAllowedDiv();"/></td></tr></table>'
		$("#returnPartyDependentDetails").html(partyDetailsHtml);
	}

}


