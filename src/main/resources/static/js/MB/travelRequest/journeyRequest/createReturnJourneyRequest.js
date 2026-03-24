var returnAttNo=0;
var returnPartyDependentNo=0;

function setReturnSourceDestinationByTrRule() {
 	
 	var TravelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
    var tadaPermission=$("#tadaPermission").val().trim();
    
	var ramBaanCheck=false;
	var reqType=$("#reqType").val();
	if(reqType=='ramBaanBooking'){
		ramBaanCheck=true;
	}else{
		ramBaanCheck=false;
	}
	
	
	var journeyMode;
	var journeyModeArr=document.getElementsByName("returnJourneyMode");
	
	for(var i=0;i<journeyModeArr.length;i++)
	{
		if(journeyModeArr[i].checked)
		journeyMode=journeyModeArr[i].value
	}
	if(journeyMode=='')journeyMode=0;
	
	if(journeyMode==0){
		setReturnToFrmStatnByTrRule('',ramBaanCheck);
	}else if(journeyMode==1)
	{
		
		var tripNodeList = document.getElementsByName("airJourneyType");
		for(i = 0; i < tripNodeList.length; i++)
		{
			var temp = tripNodeList.item(i);
			$("#airJrnyTypeDiv"+temp.value).show();
			
		}
		
		setReturnOrgDestStnByTrRule('');
		
	}else if(journeyMode==2)
	{
		showReturnMixedRequestPage();
		populateReturnSatationList();
	}
 	
 }
 
 
function setReturnToStnValue(stnValue)
{
	
	  resetReturnBreakCheckBox();
      resetReturnViaCheckBox();
	var stnStr="";
  	$("#returnRemoveFrmList0").attr('checked', false);
  	$("#to_Return_Station_Id").val($('#returnToStationDD option:selected').text());
  	
    if(stnValue=='AnyStation')
    {
    	$("#returnContainer1").show();
        $("#returnTrToStn").hide(); 
    } 
    else
	{
	  
	 	var toStationList=stnValue.split("::");
	  	
	  	if(toStationList.length==1)
	  	{
	      	stnStr='<div id="returnTrToStn"><td><input type="hidden" name="returnToStation" value="'+stnValue +'"  class="txtfldM" id="returnToStation" onmouseup="clrVaiFileds(this);"/><label>'+stnValue +'</label><td></div>'
	    	$("#returnToStation").val(stnValue);   
	      	$("#returnTrToStn").html(stnStr); 
	      	$("#returnTrToStn").show();
	    	$("#returnContainer1").hide();
	  	}
	  	else if(toStationList.length>1)
	  	{
	  	
	  		stnStr='<div id="returnTrToStn"><td>';
	   
	   		stnStr +='<select id="returnToStation" name="returnToStation" class="combo" onchange="clrVaiFileds(this); fillReturnToStn(this.value);">';
	   		stnStr +='<option value="">Select</option>'
	   		for(var i=0;i<toStationList.length;i++){
	   			stnStr +='<option value="'+ toStationList[i]+'">' + toStationList[i] + '<\/option>';
	   		}
	  		stnStr += '</select>'	
	   		stnStr +='</td></div>'
	   
		    $("#returnTrToStn").html(stnStr); 
		    $("#returnTrToStn").show();
		    $("#returnContainer1").hide();
	  	
	  	}
	
	  }
}
 
 function setReturnFrmStnValue(stnValue)
{
	
  $("#returnRemoveFrmList0").attr('checked', false);
  $("#from_Return_Station_Id").val($('#returnFromStationDD option:selected').text());
  
  resetReturnClusterFieldOnStnChange();
  resetReturnBreakCheckBox();
  resetReturnViaCheckBox();
  var stnStr="";
  if(stnValue=='AnyStation')
  {
   	$("#returnContainer").show(); 
   	$("#returnTrFrmStn").hide();
  }
  else
  {
	var fromStationList=stnValue.split("::");
	if(fromStationList.length==1) 
	{
	   stnStr='<div id="returnTrFrmStn"><td><input type="hidden" name="returnFromStation" value="'+stnValue +'" class="txtfldM" id="returnFromStation" onmouseup="clrReturnVaiFileds(this);" /><label>'+stnValue +'</label></td></div>'
	   $("#returnFromStation").val(stnValue);   
	   $("#returnTrFrmStn").html(stnStr); 
	   $("#returnTrFrmStn").show(); 
	   $("#returnContainer").hide();
	}
	else if(fromStationList.length>1)
	{
	   stnStr='<div id="returnTrFrmStn"><td>';
	   stnStr +='<select id="returnFromStation" class="combo" name="returnFromStation" onchange="clrVaiFileds(this); fillReturnFrmStn(this.value);">';
	   stnStr +='<option value="">Select</option>'
	   for(var i=0;i<fromStationList.length;i++)
	   {
	   	stnStr +='<option value="'+ fromStationList[i]+'">' + fromStationList[i] + '<\/option>';
	   }	
	   stnStr += '</select>'	
	   stnStr +='</td></div>'
	 
	   $("#returnTrFrmStn").html(stnStr); 
	   $("#returnTrFrmStn").show(); 
	   $("#returnContainer").hide();
	  		
	}
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
			
   			
			
			$("#returnFamilyName").val($("input#returnFamilyName").val()+","+familyName);		
			$("#returnRelation").val($("input#returnRelation").val()+","+RelationCode);
			$("#returnGender").val($("input#returnGender").val()+","+GenderCode);
			$("#returnDOB").val($("input#returnDOB").val()+","+dob);
			$("#returnDepSeqNo").val($("input#returnDepSeqNo").val()+","+DepSeqNo);
			$("#returnErsPrntNameVal").val($("input#returnErsPrntNameVal").val()+","+ersPrintName);
			$("#returnOtherRel").val($("input#returnOtherRel").val()+",")
			$("#returnAge").val($("input#returnAge").val()+","+age);
   				
			familyDetails+='<div id="returnFamDtlsDiv'+ j+'"><tr><td width="15%"><label >'+ familyName +'</label></td>'
			familyDetails+='<td><label>'+ ersPrintName +'</label></td>'
			familyDetails+='<td><label>'+ relation +'</label></td>'
			familyDetails+='<td><label>'+ gender +'</label></td>'
			familyDetails+='<td><label>'+ dob +'</label></td>'
			
			familyDetails+='<td class="validReturnIdValCol" style="display: none;" id="validReturnIdValCol"> <input type="checkbox" disabled="true" name="returnValidIdCardPass" value="'+DepSeqNo+'" onclick="showReturnValidIdAlert('+j+')" id="returnValidIdCardPassCheck' +j+ '"/></td>'        	
        		
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
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /><div style="display:none" id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
		    					else
		    					{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /></div><div id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}
							}
							else
							{
								if(JourneyCheck=='true')
								{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /><div style="display:none" id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
			    				else
			    				{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /></div><div id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
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
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /><div style="display:none" id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
					    		else
					    		{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /></div><div id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
								}
							}
							else
							{
								if(JourneyCheck=='true')
								{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /><div style="display:none" id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
								}
				   			 	else
				   			 	{
									familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +j+ '" id="returnDobCheck' +j+ '"/><input type="hidden" value="'+familyName+'" name="returnFamilyName' +j+ '" id="returnFamilyName' +j+ '"/><input type="hidden" value="'+RelationCode+'" name="returnRelationCode' +j+ '" id="returnRelationCode' +j+ '"/><input type="hidden" value="'+ersPrintName+'" name="returnNameCheck' +j+ '" id="returnNameCheck' +j+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +j+ '"/><div id="returnRemoveFrmListDiv' +j+ '" name="returnRemoveFrmListDiv' +j+ '" /></div><div id="returnReasonDiv' +j+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
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
			
			familyDetails+='<td class="validReturnIdValCol" style="display: none;" id="validReturnIdValCol"> <input type="checkbox" disabled="true" name="returnValidIdCardPass" value="'+DepSeqNo+'" onclick="showReturnValidIdAlert('+x+')" id="returnValidIdCardPassCheck' +x+ '"/></td>'        	
        	
        				
					if(JourneyCheck=='true')
					{
						familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +x+ '" id="returnDobCheck' +x+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +x+ '"/><div id="returnRemoveFrmListDiv' +x+ '" name="returnRemoveFrmListDiv' +x+ '" /><div style="display:none" id="returnReasonDiv' +x+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></td></tr></div>'
					}
			    	else
			    	{
						familyDetails+='<td><input type="hidden" name="returnPassPassportNo" id="returnPassPassportNo'+j+'"/><input type="hidden" name="returnPassExpdate" id="returnPassExpdate'+j+'"/><input type="hidden" value="'+dob+'" name="returnDobCheck' +x+ '" id="returnDobCheck' +x+ '"/><input type="hidden" value="'+relation+'" id="returnRelationCheck' +x+ '"/><div id="returnRemoveFrmListDiv' +x+ '" name="returnRemoveFrmListDiv' +x+ '" ></div><div id="returnReasonDiv' +x+ '"><a href="javascript:void(0);" onclick="ViewReason(\'' +Reason +'\')">Reason</a></div></td></tr></div>'
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
			}
			
			j=j+1;
					 
	    });// end of FamilyDetailSeq loop
	    
		familyDetails+='</table>';
		
        $("#returnFamilyDetails").html(familyDetails);
           	    
}


function setReturnTravelMode(msg)
{
	
	var isExist = msg.isExist;
	var trRuleID = $('#TRRule').val();
	var journeyType_1 = $('#journeyType_1').val();
	var isAirOnly=false;var isMixedOnly=false;var isRailOnly=false; 
	var isRailExist=false;var isAirExist=false; var isMixedExist=false; 
	if(isExist=='YES')
	{
		var isMixApplicable = msg.isMixApplicable;	
			
		var returnJrnyInfoTxt="";
		returnJrnyInfoTxt+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="filtersearch">';
		returnJrnyInfoTxt+='<tr align="left" bgcolor="#fbfac8">';
			returnJrnyInfoTxt+='<td width="100%" colspan="2" class="d-flex" style="column-gap:0.2rem">';
						returnJrnyInfoTxt+='Select Journey Mode:';
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
						  		returnJrnyInfoTxt+='<input type="radio" value="0" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnRail();" checked="true" />Rail';
						  	if(isAirExist)
						  	{
						  		if(isAirOnly || !isRailExist)
							  		returnJrnyInfoTxt+='<input type="radio" value="1" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnAir();" checked="true" />Air';
						  		else 
						  			returnJrnyInfoTxt+='<input type="radio" value="1" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnAir();" />Air';
						  	} 
						  	if(isMixedExist && isMixApplicable=='YES' )
						  	{
						  		if(isMixedOnly)
						  			returnJrnyInfoTxt+='&nbsp;<input type="radio" value="2" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnMixed();" checked="true" />Mixed';
						  		else
								  	returnJrnyInfoTxt+='&nbsp;<input type="radio" value="2" name="returnJourneyMode" id="returnJourneyMode" onClick="showReturnMixed();" />Mixed';
						  	}
			 if((trRuleID=='TR100233' || trRuleID=='TR100234' || trRuleID=='TR100118') && journeyType_1=='1'){
			returnJrnyInfoTxt+='</td>';
			returnJrnyInfoTxt+='<td width="50%" colspan="2">';
				returnJrnyInfoTxt+='<div id="returnMixedPreferenceDiv" style="display:none">Select Preference';
					returnJrnyInfoTxt+='<select id="returnMixedPreference" name="returnMixedPreference" class="combo" onChange="showReturnMixedRequestPage();">';
						returnJrnyInfoTxt+='<option value="-1" >--select--</option>';
						returnJrnyInfoTxt+='<option value="0" >Rail-Air</option>';
					returnJrnyInfoTxt+='</select>';
				returnJrnyInfoTxt+='</div>';	    
			returnJrnyInfoTxt+='</td>';
			}else{
	  		returnJrnyInfoTxt+='</td>';
			returnJrnyInfoTxt+='<td width="50%" colspan="2">';
				returnJrnyInfoTxt+='<div id="returnMixedPreferenceDiv" style="display:none">Select Preference';
					returnJrnyInfoTxt+='<select id="returnMixedPreference" name="returnMixedPreference" class="combo" onChange="showReturnMixedRequestPage();">';
						returnJrnyInfoTxt+='<option value="-1" >--select--</option>';
						returnJrnyInfoTxt+='<option value="0" >Rail-Air</option>';
						returnJrnyInfoTxt+='<option value="1" >Air-Rail</option>';
						returnJrnyInfoTxt+='<option value="2" >Rail-Air-Rail</option>';
					returnJrnyInfoTxt+='</select>';
				returnJrnyInfoTxt+='</div>';	    
			returnJrnyInfoTxt+='</td>';}
 		returnJrnyInfoTxt+='</tr>';
 		
 		returnJrnyInfoTxt+='</table>';

		$("#returnJourneyModeDivId").html(returnJrnyInfoTxt);
		
		if(isRailExist)
	  		showReturnRail();
	  	if(isAirExist)
	  	{
	  		if(isAirOnly || !isRailExist)
		  		showReturnAir();
	  		
	  	} 
	  	if(isMixedExist && isMixApplicable=='YES' )
	  	{
	  		if(isMixedOnly)
	  			showReturnMixed();
	  	}
	  
	}else{
		alert("Any travel mode is not associated with selected travle rule.");
	}
}

function setReturnAttendedAllowedDiv()
{
    if($("#TRRule").val() == "TR1000281"){
		$("#returnAirsrchDiv").show();
	}
    
    var attendedDetails="";  
     attendedDetails+='<style>.no-dropdown { pointer-events: none;}</style>'
    var relOptions=getReturnRelationFromResponse();
    var genOptions=getReturnGenderFromResponse();
   
   	returnAttNo++;  	
	
	attendedDetails+=''
	attendedDetails+='<table width="100%" cellpadding="0" cellspacing="0" class="bgborder">'
	attendedDetails+='<tr><td>'
	
		attendedDetails+='<table  border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt">'
		attendedDetails+='<tr align="left"><td colspan="6"><b>Attendent Details</b></td></tr>'
		
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
	    attendedDetails+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" id="returnAttDetailsTable" name="returnAttDetailsTable">'
		
		if(isTatkalFlagValue==1)
		{
			attendedDetails+='<tr><td width="30%" class="lablevalue"><input class="txtfldM" type="text" id="returnAttName'+returnAttNo+'" name="returnAttName" onblur="setReturnPrintERSName(this.value,'+returnAttNo+')"/></td>'
			attendedDetails+='<td width="30%" class="lablevalue"><input type="text" class="txtfldM" id="returnAttErsName'+returnAttNo+'" name="returnAttErsName" readonly/></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><select class="comboauto" id="returnAttGender'+returnAttNo+'" name="returnAttGender">'+genOptions+'</select></td>'

			attendedDetails+='<td width="15%" class="lablevalue"><input type="text" class="txtfldM" id="returnAttDob'+returnAttNo+'" name="returnAttDob"/>'
			attendedDetails+='<td width="10%" class="lablevalue"><input type="checkbox" class="validIdCardAttCheck" id="returnValidIdCardAttCheck'+returnAttNo+'" value="'+returnAttNo+'" name="returnValidIdCardAttCheck"/></tr>'
		}else{
			attendedDetails+='<tr><td width="30%" class="lablevalue"><input class="txtfldM" type="text" id="returnAttName'+returnAttNo+'" name="returnAttName" onblur="setReturnPrintERSName(this.value,'+returnAttNo+')"/></td>'
			attendedDetails+='<td width="30%" class="lablevalue"><input type="text" class="txtfldM" id="returnAttErsName'+returnAttNo+'" name="returnAttErsName" readonly="readonly"/></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><select class="comboauto" id="returnAttGender'+returnAttNo+'" name="returnAttGender">'+genOptions+'</select></td>'
			attendedDetails+='<td width="15%" class="lablevalue"><input type="text" class="txtfldM" id="returnAttDob'+returnAttNo+'" name="returnAttDob"/></td>'
			if($("#TRRule").val() == "TR1000281"){
			attendedDetails+='<td width="10%" class="lablevalue"><input type="checkbox"  id="returnPhsclyHandicpd" name="returnPhsicallyHandicpd"/></td></tr>'
				}
		}
		attendedDetails+='</table>'

    attendedDetails+='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" >'
	attendedDetails+='<tr><td colspan="5" align="right"><input type="button" value="Add Row" class="butn" onclick="addReturnAttendentRow();"/> &nbsp;<input type="button" class="butn" value="Delete Row" onclick="delReturnAttRow();"/></td></tr>'
 	attendedDetails+='</table>'
 	
 	attendedDetails+='</td></tr>'
	attendedDetails+='</table>';
                    
    $("#returnAttendedDetails").html(attendedDetails);
    $("#returnFinalAttendentCount").val(returnAttNo);
    $("#returnAttDob"+returnAttNo).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		maxDate:0,
		    	onShow : function () {
						 $("#returnAttDob"+returnAttNo).val("");
					 }
			 });
	
}   

function addReturnAttendentRow()
{
	 var msg = $.parseJSON($('#travelerObj').val());
	 
     var maxAttCount=$("#maxattCount").val();		
    
     if(maxAttCount<=returnAttNo){
	     alert("Only "+maxAttCount+ " Attendants Are Allowed To Do Journey On This TR Rule")
	     return false;
     }
     
    
     if (($("#TRRule").val() == "TR1000281") && ($("#returnAttGender" + returnAttNo).val() != 1) && !$("#returnPhsclyHandicpd").is(":checked")) {

		if (!calAgeForReturnJourney()) {
			alert("Second Attendant is not allowed according to your above input.");
			return false;
		}
					
	}
	
	if(($("#TRRule").val() == "TR1000281")){

		 $("#returnAttName" + returnAttNo).prop("readonly", true).css("background-color", "#eeeedd");

		$("#returnAttErsName" + returnAttNo).prop("readonly", true).css("background-color", "#eeeedd");

		$("#returnAttGender" + returnAttNo).addClass("no-dropdown").css("background-color", "#eeeedd");

		$("#returnAttDob" + returnAttNo).addClass("no-dropdown").css("background-color", "#eeeedd");

		$("#returnPhsclyHandicpd").prop("disabled", true);

	}
     
     returnAttNo++;
     
     var tbl = document.getElementById('returnAttDetailsTable');
  	 var lastRow = tbl.rows.length;
	 var row=tbl.insertRow(lastRow);
	 
	 var cellRight = row.insertCell(0);
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.style.width = "54%";
	 el.style.fontSize = "100%";
	 el.id = 'returnAttName'+returnAttNo;
	 el.name = 'returnAttName';
	 el.className="txtfldM";
	 el.setAttribute("onblur","setReturnPrintERSName(this.value,"+returnAttNo+")");
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(1);
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.style.width = "54%";
	 el.style.fontSize = "100%";
	 el.id = 'returnAttErsName'+returnAttNo;
	 el.name = 'returnAttErsName';
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 cellRight.appendChild(el);
	 
	 var i=0
	 var cellRight = row.insertCell(2);
	 var el = document.createElement('select');
	 el.id = 'returnAttGender'+returnAttNo;
	 el.name = 'returnAttGender';
	 el.options[i]=new Option("Select","");
	 el.style.width = "100%";
	 el.style.fontSize = "100%";
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
	 el.id = 'returnAttDob'+returnAttNo;
	 el.name = 'returnAttDob';
	 el.className="txtfldM";
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
		 var cellRight = row.insertCell(4);
		 var el = document.createElement('input');
		 el.type = 'checkbox';
		 el.id = 'returnValidIdCardAttCheck'+returnAttNo;
		 el.name = 'returnValidIdCardAttCheck';
		 el.value =returnAttNo;
		 el.className="validIdCardAttCheckClass";
		 cellRight.appendChild(el);
		
	}
	 
	 
	 $("#returnFinalAttendentCount").val(returnAttNo);
	 $("#returnAttDob"+returnAttNo).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd:2100,
		maxDate:0,
		    	onShow : function () {
						 $("#returnAttDob"+returnAttNo).val("");
					 }
			 });
	
}

function calAgeForReturnJourney(){

	var returnAttendntDob = $("#returnAttDob" + returnAttNo).val();

	var arrDob = returnAttendntDob.split("/");

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

function delReturnAttRow()
{
	var rowCount = $('#returnAttDetailsTable tr').length;
	//alert("rowCount-"+rowCount+"||attNo-"+attNo);
  	returnAttNo--;
    if(rowCount==1)
    {
    	returnAttNo=0;
     	$("#returnFinalAttendentCount").val(returnAttNo);
     	$("#returnAttendedDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Attendant" class="butn" id="" onclick="setReturnAttendedAllowedDiv();"/> </td></tr></table>');
    }
    else
    {
		if (($("#TRRule").val() == "TR1000281")) {

			$("#returnAttName" + returnAttNo).removeAttr("readonly").css("background-color", "#fff");

			$("#returnAttErsName" + returnAttNo).removeAttr("readonly").css("background-color", "#fff");

			$("#returnAttGender" + returnAttNo).removeClass("no-dropdown").css("background-color", "#fff");

			$("#returnAttDob" + returnAttNo).removeClass("no-dropdown").css("background-color", "#fff");

			$("#returnPhsclyHandicpd").prop("disabled", false);

		}
	
    	$('#returnAttDetailsTable tr:last').remove();
     	$("#returnFinalAttendentCount").val(returnAttNo);
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
 
 


function setReturnToFrmStatnByTrRule(msg,ramBaanCheck)
{
	var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
	
	
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
		$("#returnFromStation").val(""); 
		$("#returnToStation").val(""); 
		$("#returnTrFrmStn").html("<label/>"); 
		$("#returnTrToStn").html("<label/>"); 
		 msg = $.parseJSON($('#travelerObj').val());
	}
	
	
    var x=0;
     $.each(msg.familyDetail, function(i,obj){
			
			if(travelTypeText.indexOf('LTC')>-1)
			{
			var JourneyCheck;
			var JourneyCheckRet;
			var relation=document.getElementById("returnRelationCheck"+i).value
		    
		    if($('#returnRemoveFrmList'+i).is(":checked"))
		    {
				if(relation=="Self" )
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
					
									if(JourneyCheckRet=='true')
									{
										$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>')
										$("#returnReasonDiv"+i).hide();
									}
									else
									{
										$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
										$("#returnReasonDiv"+i).show();
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
						
						
					
								if(JourneyCheckRet=='true')
								{
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>')
									$("#returnReasonDiv"+i).hide();
								}
								else
								{
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#returnReasonDiv"+i).show();
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
						
								if(JourneyCheckRet=='true')
								{
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="1" onclick="setReturnValue('+ i+')"/>')
									$("#returnReasonDiv"+i).hide();
								}
								else
								{
									$("#returnRemoveFrmListDiv"+i).html('<input type="checkbox" class="chkbox"   id="returnRemoveFrmList' +i+ '" name="returnRemoveFrmList' +i+ '" value="0" onclick="return false" onkeyup="return false"/>')
									$("#returnReasonDiv"+i).show();
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
							var relation=document.getElementById("returnRelationCheck"+x).value
						    
						    if($('#returnRemoveFrmList'+x).is(":checked"))
						    {
								if(relation=="Self" )
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
				 
				 
						//alert("["+i+"]inside the When journey is not ltc and tagcheck-"+taggCheck);
						JourneyCheck=obj.journeyCheck;
						//alert("["+i+"]inside the else case JourneyCheck-"+JourneyCheck);
						
						if(JourneyCheck=='true')
						{
								//alert("["+i+"]inside the else case true-"+$("#reasonDiv"+i));
								$("#returnRemoveFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +x+ '" name="returnRemoveFrmList' +x+ '" value="1" onclick="setReturnValue('+ x+')"/>');
				     			$("#returnReasonDiv"+x).hide();
						}
						else
						{
								//alert("["+i+"]inside the else case false-"+$("#reasonDiv"+i));
								$("#returnRemoveFrmListDiv"+x).html('<input type="checkbox" class="chkbox" id="returnRemoveFrmList' +x+ '" name="returnRemoveFrmList' +x+ '" value="0" onclick="return false" onkeyup="return false" />');
		 						$("#returnReasonDiv"+x).show();
								
						}
						x=x+1;
					}
		 }		
		 else
		 {
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
							
				//alert("["+i+"]inside the When journey is not ltc and tagcheck-"+taggCheck);
				JourneyCheck=obj.journeyCheck;
				//alert("["+i+"]inside the else case JourneyCheck-"+JourneyCheck);
				
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
	   

		frmoptions += '<select id="returnFromStationDD" name="returnFromStationDD" class="combo" onchange="setReturnFrmStnValue(this.value)"><option value="">Select</option>'
		
		 $.each(msg.fromStationList, function(index,obj){
			if(obj.jrnyType==1){
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
        $("#returnFrmStnDropDown").html(frmoptions);
       
       
        if(!(frmoptions.indexOf('AnyStation') > -1))
        {
       	if(travelTypeText.indexOf('LTC-AI')>-1  && taggCheck!="true" ){
             setFrmStnValue(destStn)
             $("#returnFrmStnDropDown").hide();                           
       	}else{
       		$("#returnFrmStnDropDown").show();       
       	}
             
        }else{
       		$("#returnFrmStnDropDown").show();       
       	}
             
        
        tooptions += '<select id="returnToStationDD" name="returnToStationDD" class="combo" onchange="setReturnToStnValue(this.value)"><option value="">Select</option>'
	     $.each(msg.toStationList, function(index,obj){
			if(obj.jrnyType==1){
				
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
			
	
		if(tooptions=='<select class="combo">'){
 			tooptions = 'No TO Station Exists In Profile';
     	}else{
        	tooptions +="</select>"
     	}
     	
      	$("#returnToStnDropDown").html(tooptions);  
      
        if(!(tooptions.indexOf('AnyStation') > -1))
        {      
      		$("#returnToStnDropDown").show();
	  	
		}else{
       		$("#returnToStnDropDown").show();
	  	}
					  
	}	// End of if(ramBaanCheck!="true")		  
	 

}


function setReturnPartyDependentAllowedDiv()
{
       let flag = validateFieldsReturnPartyBkgRequest(); /* Party Booking Validation */
       if(flag){
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
        partyDetailsInnerHtml+='<tr><td width="20%" align="left" class="lablevalue"><input class="txtfldM" type="text" size="20" id="returnDependentPersonalNo'+returnPartyDependentNo+'" name="returnDependentPersonalNo" onchange="setGetPersonalDetails(this.value,'+returnPartyDependentNo+',1)"/></td>'
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
     	$("#returnPartyDependentDetails").html('<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt" ><tr><td align="right"><input type="button" value="Add Party Dependent" class="butn" id="" onclick="setReturnPartyDependentAllowedDiv();"/></td></tr></table>');
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

function isReturnTatkalSelected(chk)
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
    	
		document.getElementById("returnIsTatkalFlag").value="1";
	   	$('.validReturnIdValCol').show();
	   	$('#validReturnIdInfoCol').show();
	   	
	}
	else
	{
		document.getElementById("returnIsTatkalFlag").value="0";
		$('.validReturnIdValCol').hide();
	   	$('#validReturnIdInfoCol').hide();
	   	
		 
	}
	
}


 function viaReturnRoute() {

		//alert("viaRoute clicked");
     var viafields = ""
     var fromStn = "";
     var tostn = ""
     fromStn = $("input#returnFromStation").val()
     if (fromStn == undefined)
         fromStn = $("select#returnFromStation").val()
     tostn = $("input#returnToStation").val()
     if (tostn == undefined)
         tostn = $("select#returnToStation").val()
		$("#returnViaDiv").show();
     if (validateReturnViaFields()) {
         if ($('#returnViaRoute').is(":checked")) {
             if (!validateReturnViaRouteStations(fromStn, tostn)) {
             if (validateReturnViaBooking()) {
                 $("#returnFromStation").attr('disabled', 'disabled');
                 $("#returnToStation").attr('disabled', 'disabled');
		    		//alert("vai route validation is passed");
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
                 viafields += '<tr><td colspan="4" class="lablevalue">Via Route</td></tr>'
                 viafields += '<tr><td align="center" style="width:27.7%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%" class="lable"></td></tr>'
                 viafields += '</table>'
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnRouteTable">'
                 viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation0" name="returnFrmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" id="returnToStation0" name="returnToStation0"  onkeyup="getStationList(this.value,this.id,1)" autocomplete="off">'
                 viafields += '<div class="suggestionsBox" id="suggestionsreturnToStation0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnToStation0"></div></div></td>'
                 viafields += '</td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate0" name="return_JourneyDate0" autocomplete="off"></td>'
                 viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="returnViaTrnStn" onclick="trainReturnSearch(0)"/></td></tr>'
                 viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation1" name="returnFrmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="returnSuggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="returnAutoSuggestionsListfrmStation1"></div></div></td>'
                 viafields += '<div class="suggestionsBox" id="suggestionsreturnFrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnFrmStation1"></div></div></td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="returnToStation1" name="returnToStation1" readonly  autocomplete="off"><div class="suggestionsBox" id="suggestionsreturnToStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnToStation1"></div></div></td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate1" name="return_JourneyDate1" autocomplete="off"></td>'
                 viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearch(1)"/></td></tr>'
                 viafields += '</table>';
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addReturnRow(\'' + tostn + '\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delReturnRow(\'' + tostn + '\');"/></td></tr></table></div>'
		  		   $("#returnViaDiv").show("fast");
		   		   $("#returnViaDiv").html(viafields);
		$('#return_JourneyDate0').datetimepicker({
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
			$('#return_JourneyDate0').val("");
                         minData = $('#travelStartDate').val();
                         maxData = $('#travelEndDate').val();
			 $("#return_JourneyDate0").datetimepicker("setOptions", {
                             minDate: minData
                         });
					$("#return_JourneyDate0").datetimepicker("setOptions", {
                             maxDate: maxData
                         });
					 }
			  });
		$('#return_JourneyDate1').datetimepicker({
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
			$('#return_JourneyDate1').val("");
                         minData = $('#travelStartDate').val();
                         maxData = $('#travelEndDate').val();
			 $("#return_JourneyDate1").datetimepicker("setOptions", {
                             minDate: minData
                         });
					$("#return_JourneyDate1").datetimepicker("setOptions", {
                             maxDate: maxData
                         });
					 }
			  });
				}
             } else {
                 //alert("VIA-AJAX Return ROUTE  FOUND ::::::: ");
                 $('#returnViaRoute').attr('checked', false);
					 $('#returnViaValidate').val("true");
		 		}
         }else{
         //alert("Via-route return un-checked :::: ");
         $("#returnFromStation").prop('disabled', false);
         $("#returnToStation").prop('disabled', false);
			}
     } else {
			$("#returnViaDiv").hide("fast");
		}
     if (!$('#returnViaRoute').is(":checked")) {
			$("input#returnFromStation").prop("readonly", false);
			$("input#returnToStation").prop("readonly", false);
			$("#returnViaDiv").hide("fast");
     }
 }



 /* Function value to check return Via-Route Available */
 function validateReturnViaRouteStations(fromStn, tostn) {
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
                 $('#returnViaRoute').attr('checked', false);
                 validationFlag = false;
             } else {
                 validationFlag = true;
                 $("#returnFromStation").attr('disabled', 'disabled');
                 $("#returnToStation").attr('disabled', 'disabled');
                 var viafields = '';
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
                 viafields += '<tr><td colspan="4" class="lablevalue">Via Route</td></tr>'
                 viafields += '<tr><td align="center" style="width:27.7%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%" class="lable"></td></tr>'
                 viafields += '</table>'
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnRouteTable">'
                 viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation0" name="returnFrmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
                 viafields += '<td align="left" id="viaReturnStationsDropDwn">'
                 viafields += '</td>'
                 viafields += '</td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate0" name="return_JourneyDate0" autocomplete="off"></td>'
                 viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="returnViaTrnStn" onclick="trainReturnSearch(0)"/></td></tr>'
                 viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation1" name="returnFrmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="returnSuggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="returnAutoSuggestionsListfrmStation1"></div></div></td>'
                 viafields += '<div class="suggestionsBox" id="suggestionsreturnFrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnFrmStation1"></div></div></td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="returnToStation1" name="returnToStation1" readonly  autocomplete="off"><div class="suggestionsBox" id="suggestionsreturnToStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnToStation1"></div></div></td>'
                 viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate1" name="return_JourneyDate1" autocomplete="off"></td>'
                 viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearch(1)"/></td></tr>'
                 viafields += '</table>';
                 viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addReturnRow(\'' + tostn + '\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delReturnRow(\'' + tostn + '\');"/></td></tr></table></div>'
                 $("#returnViaDiv").show("fast");
                 $("#returnViaDiv").html(viafields);
                 $('#return_JourneyDate0').datetimepicker({
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
                         $('#return_JourneyDate0').val("");
                         minData = $('#travelStartDate').val();
                         maxData = $('#travelEndDate').val();
                         $("#return_JourneyDate0").datetimepicker("setOptions", {
                             minDate: minData
                         });
                         $("#return_JourneyDate0").datetimepicker("setOptions", {
                             maxDate: maxData
                         });
                     }
                 });
                 $('#return_JourneyDate1').datetimepicker({
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
                         $('#return_JourneyDate1').val("");
                         minData = $('#travelStartDate').val();
                         maxData = $('#travelEndDate').val();
                         $("#return_JourneyDate1").datetimepicker("setOptions", {
                             minDate: minData
                         });
                         $("#return_JourneyDate1").datetimepicker("setOptions", {
                             maxDate: maxData
                         });
                     }
                 });
                 var options = '';
                 options += '<select id="returnToStation0" class="combo" name="returnToStation0" onchange = fillReturnViaFromStation(this.value)>';
                 options += '<option value="">Select</option>';
                 $.each(msg, function(index, value) {
                     console.log("Adding option:", value);
                     options += `<option value="${value}">${value}</option>`;
                 });
                 options += '</select>';
                 $("#viaReturnStationsDropDwn").html(options);
             }
         },
         error: function(xhr, status, error) {
             console.error("Error fetching data:", error);
             alert("Failed to fetch via route stations. Please try again.");
         }
     });
     return validationFlag;
 }



 /*  Function to FillReturnStations Value */
 function fillReturnViaFromStation(stationValue) {
    var viaReturnToStation  = $("#returnToStation0").val();
    var viaReturnFromStation = $("#returnFrmStation1").val();

    if(viaReturnToStation != '' && viaReturnToStation != undefined){
    $("#returnFrmStation1").val('');
    $("#returnFrmStation1").val(viaReturnToStation);
    $("#returnFrmStation1").attr("readonly","readonly");
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
        
	if (document.getElementById("returnClusteredRoute") != null) {
		if (document.getElementById("returnClusteredRoute").checked == true) {
			alert("You can Either select Break Journey or Clustered Route");
			$("#returnFromStation").prop('disabled', false);
			$("#returnToStation").prop('disabled', false);
			$("input#returnFromStation").prop("readonly", false);
			$("input#returnToStation").prop("readonly", false);
			document.getElementById("returnClusteredRoute").checked = false;
			document.getElementById("returnBreakJourney").checked = false;
			$("#returnClusteredDiv").hide();
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



function validateReturnViaBooking()
{
	
	 if(document.getElementById("returnBreakJourney")!=null )
        {
	        if(document.getElementById("returnBreakJourney").checked==true)
	        {
	        	alert("You can Either select Break Journey or Via Route");
	        	 $("#returnSelectedTrainNo").val("");
	        	$("#returnFromStation").prop('disabled', false);
				$("#returnToStation").prop('disabled', false);
				$("input#returnFromStation").prop("readonly", false);
				$("input#returnToStation").prop("readonly", false);
	        	document.getElementById("returnBreakJourney").checked=false;
	        	document.getElementById("returnViaRoute").checked=false;
	        	$("#returnBreakJrnyDiv").hide();
	        	return false;
	       	}
        }
	
    var frmStn=$('#returnFromStation').val();
	var toStn=$('#returnToStation').val();
	var journeyDate=$('#return_JourneyDate').val();
	var reqType=$('#reqType').val();
	var entitledClass=$('#returnEntitledClass :selected').val();
	var response="";
    var viaRoute="";
    var check=false;
	frmStn=getStationCode(frmStn);
	toStn=getStationCode(toStn);
	journeyDate=changeDateFormat(journeyDate)
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
      		$("#returnViaDiv").html("Searching Direct Trains From["+ frmStn +"] To ["+ toStn +"]... Please wait ...");
      		var calHeight=document.body.offsetHeight+20;
			$("#screen-freeze").css({"height":calHeight + "px"} );
			$("#screen-freeze").css("display", "block");
	  },
  			 
      complete: function(){
  		 	$("#returnViaDiv").html("");
  		 	$("#screen-freeze").css("display", "none");
      },
	       
	  	
      success: function(msg)
      {
          var errorDescptrn=msg.errorMessage;
          var errordesc="";

          if(errorDescptrn!=undefined)
             errordesc=msg.errorMessage.toUpperCase();

          var errorCode=msg.errorMessage;

         // alert(errorCode+"||errordesc-"+errordesc+"||"+errorDescptrn);
                     
           //--  added by pradeep end 
           if(errordesc.indexOf('THE STATION CODE IS INVALID')>-1 || errorCode=='8018482')
           {
           		check=true;
          		$('#returnViaValidate').val("true");
           	 	alert(errorDescptrn+":: You are using one of Clustered Station in Request");
           	 	return check;
           }
           //--  added by pradeep end 
          
           if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 || errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004' || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
           {
          	
              	check=true;
              	$('#returnViaValidate').val("true");
           }
           
          
           
           //alert(errordesc.indexOf('NO DIRECT TRAINS FOUND....'));
           if(errordesc.indexOf('NO DIRECT TRAINS AVAILABLE')>-1 || errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
           {
          	  	check=true;
              	$('#returnViaValidate').val("true");
           }

           else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
           {
            	alert(errorDescptrn);
            	$('#returnViaValidate').val("true");
           }	
           else
           {
          		alert("Via Booking Is Not Permmited As Train Exists Between This Route");
          		$('#returnViaValidate').val("true");
           }
          	
      	  return check;
      }
      
   });
     
   $('#returnViaValidate').val("true");
       
   return check;
   
} 


var return_k=2;
function addReturnRow(toStn)
{
	
	if(return_k>2){
		alert("Maximum Three Rows Are Allowed For Via Booking");
		return false;
		}
		 
	if(validateReturnViaRouteTrain(return_k-1))
	{
	
    $('#returnToStation'+eval(return_k-1)).val("")
     
    $('#returnToStation' + (return_k - 1)).prop("readonly", false);
var element = document.getElementById('returnToStation' + (return_k - 1)); 
    element.setAttribute('onkeyup', 'getStationList(this.value, this.id, 1);');
     
  	var tbl = document.getElementById('returnRouteTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnFrmStation'+return_k;
	 el.id = 'returnFrmStation'+return_k;
	 el.className="txtfldM";
	 el.setAttribute("readonly","true");
	 
	 el.setAttribute('onkeyup',"getStationList(this.value,this.id,1);");
 	
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionsreturnFrmStation'+return_k;
	 divEl.id = 'suggestionsreturnFrmStation'+return_k;
	 divEl.className='suggestionsBox';
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListreturnFrmStation'+return_k;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnToStation'+return_k;
	 el.id = 'returnToStation'+return_k;
	 el.className="txtfldM";
	 el.setAttribute("readonly","true");
	 //el.setAttribute('onkeyup',"getStationList(this.value,this.id,1);");
	 var divEl=document.createElement('div');
	 divEl.name = 'suggestionsreturnToStation'+return_k;
	 divEl.id = 'suggestionsreturnToStation'+return_k;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListreturnToStation'+return_k;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
 	
 	 var cellRight = row.insertCell(2);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'return_JourneyDate'+return_k;
	 el.id = 'return_JourneyDate'+return_k;
	 el.className="txtfldM";
	 cellRight.appendChild(el);
	 
	 var cellRight = row.insertCell(3);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'button';
	 el.name = 'returnViaTrnSrch'+return_k;
	 el.id = 'returnViaTrnSrch'+return_k;
	 el.setAttribute('value',"Train Search");
	 el.className="butn";
	 el.setAttribute('onclick',"trainReturnSearch('"+return_k+"');");
	 cellRight.appendChild(el);
	
	$("#return_JourneyDate"+return_k).datetimepicker({
		lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d/m/Y',
		scrollMonth : false,
		scrollInput : false,
		viewDate : $('#travelStartDate').val(), 
    	defaultDate : $('#travelStartDate').val(),
		yearEnd: 2100,
	 	
		onShow:function(){
			$("#return_JourneyDate"+return_k).val("");
			minData=$('#travelStartDate').val();
	       	     maxData=$('#travelEndDate').val();
			 $("#journeyDate1").datetimepicker("setOptions", {
					minDate: minData});
					
					$("#journeyDate1").datetimepicker("setOptions", {
					maxDate: maxData});       
					 }
			  });

     return_k++;
}
}
  
  
function delReturnRow(tostn)
{   
   	//alert("Inside delRow for Route Details");
     var rowCount = $('#returnRouteTable tr').length;
     
   
     if(rowCount>2){
     var row1=rowCount-2
     return_k--;
     
     $('#returnToStation'+row1).val(tostn)
     
     $('#returnToStation' + (return_k - 1)).prop("readonly", true);

    var element = document.getElementById('returnToStation' + (return_k - 1)); 
    element.removeAttribute('onkeyup');
     
  	  $('#returnRouteTable tr:last').remove();
  	  
  	  }
  	 else
  	  alert("Minimum Two Rows Required For Via Booking")

}  
  	
 
  
  function validateReturnViaRouteTrain(index)
{
	var toIndex=index-1;
	var check=false;
	var frmStn=$('#returnFrmStation'+index).val();
	var toStn=$('#returnToStation'+index).val();
	var journeyDate0=$('#return_JourneyDate'+toIndex).val();
	var journeyDate=$('#return_JourneyDate'+index).val();
	var reqType=$('#reqType').val();
	var entitledClass=$('#returnEntitledClass :selected').val();
	var response="";
    var viaRoute="";
        
    if(toStn=="")
   {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnToStation'+index).focus();
	    return check;
   }
   
	if(frmStn=="")
	 {
   		alert("Please Enter From Station And To Station Fields");
   		$('#returnFrmStation'+index).focus();
	    return check;
	 }
   
   if(journeyDate0=="dd/mm/yyyy" || journeyDate0=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDate'+toIndex).focus();
    	return check;
   }
   
   if(journeyDate=="dd/mm/yyyy" || journeyDate=="" )
   {
		alert("Please Enter Journey Date")
		$('#return_JourneyDate'+index).focus();
    	return check;
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
          
           if(errordesc.indexOf('COVID')>-1 || errordesc.indexOf('500162') > -1 || errordesc.indexOf('500163') > -1 || errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004'  || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
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
  
  
function fillReturnStn(thisValue,id,jrnyType) 
{
	//alert("Inside fillStn");	
	var idArr=id.split("returnToStation");
	var idNumber=parseInt(idArr[1])+1;
	$('#'+id).val(thisValue);
	setViaStnFrmToStn(thisValue,"returnFrmStation"+idNumber)
	setTimeout("$('#suggestions"+id+"').hide();", 200);
}
  
 
function trainReturnListPopup()
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	//alert("Inside trainListPopup");
	var frmStn=$('#returnFromStation').val();
	var toStn=$('#returnToStation').val();
	if(validateReturnViaFields())
	{
		frmStn=getStationCode(frmStn);
		toStn=getStationCode(toStn);
	 	var journeyDate=$('#return_JourneyDate').val();
	 	journeyDate=changeDateFormat(journeyDate)
	 	var entitledClass=$('#returnEntitledClass').val();
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
 
 
function trainReturnSearch(i)
{
	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	//alert("Inside trainSearch");	
    var frmStn=$('#returnFrmStation'+i).val();
    var toStn=$('#returnToStation'+i).val();
    if(validateReturnViaFieldsForViaSearch(i))
    {
		frmStn=getStationCode(frmStn);
		toStn=getStationCode(toStn);
	 	var journeyDate=$('#return_JourneyDate'+i).val();
	 	journeyDate=changeDateFormat(journeyDate)
	 	var entitledClass=$('#returnEntitledClass').val();
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
 
 
function validateReturnViaFieldsForViaSearch(i)
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
	   
	   check=validate_required(document.getElementById("returnEntitledClass"),"Please Select EntitlesClass");
	   if(!check)
	   document.getElementById("returnViaRoute").checked=false;
	   
	   return check;
	  
} 	

 
function setReturnValue(j)
{   
	var relation=document.getElementById("returnRelationCheck"+j).value;
    var travelTypeText=$('#TravelTypeDD :selected').text().toUpperCase();
    var reqType=$("#reqType").val();
    var travelMode=$('#returnTravelMode').val();
    
    if(travelMode==1 && reqType=='international'){
    	setReturnPassportDetails(j);
    }
    if(relation=="Self" && (travelTypeText=="LTC-AI" || travelTypeText=="LTC-HT")){
    
	    var fromStationDD=document.getElementById("from_Return_Station_Id").value.toUpperCase();
	    var toStationDD=document.getElementById("to_Return_Station_Id").value.toUpperCase();
		   
    	if(toStationDD=="HOME TOWN" || toStationDD=="SPR" )
	    {
	    	alert("Cannot create an 'Onward Journey' from Hometown/SPR or 'Return journey' to Hometown/SPR for self");
	    	$("#returnRemoveFrmList0").prop('checked', false);
	    	document.getElementById("returnRemoveFrmList0").value=1;
	    	return false;
	    }
		    	
    }
    
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
	var isTatkalFlagValue=0;
	
	if(travelMode==0){
		isTatkalFlagValue=document.getElementById("returnIsTatkalFlag").value;	
	}
	if(travelMode==1){
		if($("#returnAirsrchDiv")!=null){
			$("#returnAirsrchDiv").show();
		}
	}
	if(travelMode==2){
		isTatkalFlagValue=document.getElementById("returnIsTatkalFlagMixed").value;	
	}
	
	
	//alert("Inside isTatkalFlagValue-"+isTatkalFlagValue);
	if(isTatkalFlagValue==1)
	{
		if($('#returnRemoveFrmList'+j).is(":checked"))
    	{
    		$("#returnValidIdCardPassCheck"+j).attr("disabled", false);
		}
		else
		{
			$("#returnValidIdCardPassCheck"+j).attr('checked', false);
			$("#returnValidIdCardPassCheck"+j).attr("disabled", true);
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

function setReturnPassportDetails(j){
	
	var innerHTML='';
	innerHTML=innerHTML+'<table width="100%" class="formtxt"><tr><td colspan="4"><h1>Passport Details</h1></td></tr>';
    innerHTML=innerHTML+'<tr><td><b>Passport No.</b></td>';
    innerHTML=innerHTML+'<td><input type="text" maxlength="20" size="20" id="passport_no" onkeyup="this.value = this.value.toUpperCase()"/></td>';
    innerHTML=innerHTML+'<td><b>Passport Expiry Date</b></td><td><input type="text" maxlength="20" size="20" readonly="true" id="passport_exp_date"/></td>';
    innerHTML=innerHTML+'</tr><tr><td align="left" colspan="2"><input type="button" onclick="closeReturnPassportDtlsModal('+j+');" class="btn" value="Cancel"/></td>';
    innerHTML=innerHTML+'<td align="right" colspan="2"><input type="button" onclick="setReturnPassportDtlsModal('+j+');" class="btn" value="OK"/></td>';
    innerHTML=innerHTML+'</tr></table>';
    
    $("#passport_dtls_modal").html(innerHTML);
    $('#passport_exp_date').datepicker({dateFormat:'dd/mm/yy',scrollMonth : false,minDate:0,
		    	beforeShow : function () {
						 $(this).val("");
					 }
			 });
    $("#myModal").show();
}

function closeReturnPassportDtlsModal(j){
	$("#returnRemoveFrmList"+j).attr('checked', false);
	document.getElementById("returnRemoveFrmList"+j).value=1;
	$("#myModal").hide();
}

function setReturnPassportDtlsModal(j){
	
	if($("#passport_no").val().trim()==''){
		alert("Please enter passport number");
		return false;
	}
	if($("#passport_exp_date").val().trim()==''){
		alert("Please enter passport expiry date");
		return false;
	}
	$("#returnPassPassportNo"+j).val($("#passport_no").val());
	$("#returnPassExpdate"+j).val($("#passport_exp_date").val());
	$("#myModal").hide();
	$("#passport_dtls_modal").html("");
}

function showReturnSaveButton(index) {

	var checkbox = document.getElementsByName("returnSelectedTrain");

	var checkLen = checkbox.length;

	for (var i = 0; i < checkLen; i++) {
		if (i == index) {
			$("#returnTrainRadio" + index).checked;
			$("#returnSelectedTrainNo").val($("#returnTrainRadio" + index).val());
		}
	}

}


function getReturnTrainRouteStation(trainNumber, fromStationCode, toStationCode) {

	var tainValue = $("#returnSelectedTrainNo").val();
	var journeyDate = $('#return_JourneyDate').val();
	journeyDate = changeDateFormat(journeyDate);
	var stationList = "";
	$('#returnToStation0').find('option').not(':first').remove();
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
				$("#returnToStation0").html(stationList);

			}
		});

	}
	var breakJourneyEndDate ="";
	var breakJourneyStartDate="";
	var viafields = ""
	var fromStn = "";
	var tostn = ""
	fromStn = $("input#returnFromStation").val()
	if (fromStn == undefined)
		fromStn = $("select#returnFromStation").val()
	tostn = $("input#returnToStation").val()
	if (tostn == undefined)
		tostn = $("select#returnToStation").val()
	$("#returnBreakJrnyDiv").show();
	if (validateReturnBreakJrnyFields()) {
		if ($('#returnBreakJourney').is(":checked")) {
			
				if (validateReturnBreakJrnyFields()) {
					$("#returnFromStation").attr('disabled', 'disabled');
					$("#returnToStation").attr('disabled', 'disabled');

					viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="filtersearch"><tbody class="all1">'
					viafields += '<tr><td colspan="4" class="lablevalue">Break Journey Route</td></tr>'
					viafields += '<tr><td align="center" style="width:27.7%" class="lablevalue">From Station</td><td align="center" style="width:27.8%" class="lablevalue">To Station</td><td align="center" style="width:27.9%" class="lablevalue">Date Of Journey</td><td width="20%" class="lable"></td></tr>'
					viafields += '</table>'
					viafields += '<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnRouteTable">'
					viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation0" name="returnFrmStation0" value="' + fromStn + '" readonly autocomplete="off"></td>'
					viafields += '<td align="left"><select type="text" class="txtfldM" style="width: 154px;" id="returnToStation0" name="returnToStation0"  onChange="setReturnBreakJrnyFromStation(this.value)" autocomplete="off"><option value="-1">Select</option></select>'
					viafields += '<div class="suggestionsBox" id="suggestionsreturnToStation0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnToStation0"></div></div></td>'
					viafields += '</td>'
					viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate0" name="return_JourneyDate0" autocomplete="off"></td>'
					viafields += '<td align="left"><input type="button"  class="butn" value="Train Search" id="returnViaTrnStn" onclick="trainReturnSearch(0)"/></td></tr>'
					viafields += '<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation1" name="returnFrmStation1"  readonly autocomplete="off"><div class="suggestionsBox" id="returnSuggestionsfrmStation1" style="display: none;" ><div  class="suggestionList"  id="returnAutoSuggestionsListfrmStation1"></div></div></td>'
					viafields += '<div class="suggestionsBox" id="suggestionsreturnFrmStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnFrmStation1"></div></div></td>'
					viafields += '<td align="left"><input type="text" class="txtfldM" value="' + tostn + '" id="returnToStation1" name="returnToStation1" readonly autocomplete="off"><div class="suggestionsBox" id="suggestionsreturnToStation1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnToStation1"></div></div></td>'
					viafields += '<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate1" name="return_JourneyDate1" autocomplete="off"></td>'
					viafields += '<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearch(1)"/></td></tr>'
					viafields += '</table>';
					$("#returnBreakJrnyDiv").show("fast");
					$("#returnBreakJrnyDiv").html(viafields);

					$('#return_JourneyDate0').datetimepicker({
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
							$('#return_JourneyDate0').val("");
							minData = $('#return_JourneyDate').val();
							maxData = $('#travelEndDate').val();
							$("#return_JourneyDate0").datetimepicker("setOptions", {
								minDate: minData
							});
							$("#return_JourneyDate0").datetimepicker("setOptions", {
								maxDate: maxData
							});
						},
						onSelectDate:function(ct){
						
						    breakJourneyStartDate=ct;
						    var selectedDateTime=ct.getTime();
							var selectedTime = selectedDateTime+ (72 * 60 * 60 * 1000);
							var travelEndDate=$('#travelEndDate').val();
							var part=travelEndDate.split("/");
						    
							var endDate=new Date(part[2],part[1]-1,part[0]);
							var timeEndTravel=endDate.getTime();
							var flag=selectedTime<timeEndTravel;
							if(flag){
								
								breakJourneyEndDate=new Date(selectedTime);
							}else {
							    breakJourneyEndDate=travelEndDate;
							}
						} 
					});
					$('#return_JourneyDate1').datetimepicker({
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
							$('#return_JourneyDate1').val("");
							
							minData = breakJourneyStartDate
							maxData = breakJourneyEndDate;
							$("#return_JourneyDate1").datetimepicker("setOptions", {
								minDate: minData
							});
							$("#return_JourneyDate1").datetimepicker("setOptions", {
								maxDate: maxData
							});
						}
					});
				}
			} 
		else {
			//alert("Via-route return un-checked :::: ");
			$("#returnFromStation").prop('disabled', false);
			$("#returnToStation").prop('disabled', false);
		}
	} else {
		document.getElementById("returnBreakJourney").checked = false;
		$("#returnBreakJrnyDiv").hide("fast");
		
	}

	ajaxRequestFace.close();


}


function ReturnTrainListPopupForBreakJrny() {

	if (parseInt($("#countdown").val()) < 5) {
		alert("Your Session Has Been Expired. Kindly Re-login");
		return false;
	}
	
	if (document.getElementById("returnViaRoute") != null) {
		if (document.getElementById("returnViaRoute").checked == true) {
			alert("You can Either select Via Route or Break Journey");
			$("#returnFromStation").prop('disabled', false);
			$("#returnToStation").prop('disabled', false);
			$("input#returnFromStation").prop("readonly", false);
			$("input#returnToStation").prop("readonly", false);
			document.getElementById("returnViaRoute").checked = false;
			document.getElementById("returnBreakJourney").checked = false;
			$("#returnViaDiv").hide();
			return false;
		}
	}

	if (document.getElementById("returnClusteredRoute") != null) {
		if (document.getElementById("returnClusteredRoute").checked == true) {
			alert("You can Either select Break Journey or Clustered Route");
			$("#returnFromStation").prop('disabled', false);
			$("#returnToStation").prop('disabled', false);
			$("input#returnFromStation").prop("readonly", false);
			$("input#returnToStation").prop("readonly", false);
			document.getElementById("returnClusteredRoute").checked = false;
			document.getElementById("returnBreakJourney").checked = false;
			$("#returnClusteredDiv").hide();
			return false;
		}
	}
        
	var frmStn = $('#returnFromStation').val();
	var toStn = $('#returnToStation').val();
	if (validateReturnBreakJrnyFields()) {
		frmStn = getStationCode(frmStn);
		toStn = getStationCode(toStn);
		var journeyDate = $('#return_JourneyDate').val();
		journeyDate = changeDateFormat(journeyDate)
		ajaxRequestFace = new LightFace.Request(
			{
				width: 800,
				height: 600,
				url: $("#context_path").val() + "mb/irSrchRsltPageForReturnBreakJrny",


				buttons: [
					{
						title: 'Close', event: function() {
							myCloseReturnFunction();
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

function myCloseReturnFunction() {
	document.getElementById("returnBreakJourney").checked = false;
	$("#returnSelectedTrainNo").val("");
}

function setReturnBreakJrnyFromStation(value) {

	var fromStn = $("input#returnToStation0").val()
	if (fromStn == undefined)
		fromStn = $("select#returnToStation0").val()

	$("#returnFrmStation1").val(value);

}

function validateReturnBreakJrnyFields() {

	var check = "";

	if (document.getElementById("returnViaRelxRoute") != null) {
		if (document.getElementById("returnViaRelxRoute").checked == true) {
			alert("You can Either select Via Route or Relaxed Route");
			document.getElementById("returnBreakJourney").checked = false;
			return false;
		}
	}

	if (document.getElementById("return_JourneyDate").value == "dd/mm/yyyy" || document.getElementById("return_JourneyDate").value == "") {
		alert("Please Enter Journey Date")

		document.getElementById("returnBreakJourney").checked = false;
		document.getElementById("return_JourneyDate").focus();
		return false;
	}
	if (document.getElementById("returnFromStation").value == "") {
		alert("Please Enter From Station And To Station Fields");
		document.getElementById("returnBreakJourney").checked = false;
		return false;
	}
	if (document.getElementById("returnToStation").value == "") {
		alert("Please Enter From Station And To Station Fields");
		document.getElementById("returnBreakJourney").checked = false;
		return false;
	}

	check = validate_required(document.getElementById("returnEntitledClass"), "Please Select Entitled Class");
	if (!check)
		document.getElementById("returnBreakJourney").checked = false;

	return check;

} 	

function handleReturnUncheckToHideDiv() {



	if (!$('#returnBreakJourney').is(":checked")) {
        $("#returnSelectedTrainNo").val("");
		$("#returnFromStation").prop('disabled', false);
		$("#returnToStation").prop('disabled', false);
		$("input#returnFromStation").prop("readonly", false);
		$("input#returnToStation").prop("readonly", false);
		$("#returnBreakJrnyDiv").hide("fast");
	}
}

function resetReturnBreakCheckBox(){
	if(document.getElementById("returnBreakJourney")!=null && document.getElementById("returnBreakJourney").checked==true){
		
		$("#returnBreakJourney").click();
	}
	
	
}
function resetReturnViaCheckBox(){
	
		if(document.getElementById("returnViaRoute")!=null && document.getElementById("returnViaRoute").checked==true){
		
		$("#returnViaRoute").click();
	}
}

  /* PartyBooking Return Add Dependent Validation */
    function validateFieldsReturnPartyBkgRequest() {
        var returnEntitledClass = $('#returnEntitledClass').val();
        var return_JourneyDate = $('#return_JourneyDate').val();
        var returnFromStation = $('#returnFromStation').val();
        var returnToStation = $('#returnToStation').val();
        if (returnEntitledClass != "" && return_JourneyDate != "" && returnFromStation != "" && returnToStation != "") {
            return true;
        } else {
            alert("Please Fill Journey Details Fields properly");
            return false;
        }
    }	



























