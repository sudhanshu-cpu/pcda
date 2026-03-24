function resetClusterFieldOnStnChange()
{
	$("#clusteredRoute").attr('checked', false);
	$("#clusteredDiv").hide();
  	$('#clusterValidate').val('false')
	$("#clusteredInfoDiv").html("");
	$('#clusterTrainNoList').val('');
  	$("#clusterStation").val("");
		
}


function resetReturnClusterFieldOnStnChange()
{
	$("#returnClusteredRoute").attr('checked', false);
	$("#returnClusteredDiv").hide();
  	$('#returnClusterValidate').val('false')
	$("#returnClusteredInfoDiv").html("");
	$('#returnClusterTrainNoList').val('');
  	$("#returnClusterStation").val("");
		
}

function isClusteredSelected(chk)
{
	$("#clusterStation").val("");

	if(chk.checked==true)
	{
		if(validateClusteredFields())
		{
			$("#clusteredDiv").show();
		
		}else{
			//alert("Inside else");
			chk.checked=false;
		}
	}
	else
	{
		resetClusterField();
		$("#clusteredDiv").hide();
	}
	
}

function isReturnClusteredSelected(chk)
{

	$("#returnClusterStation").val("");

	if(chk.checked==true)
	{
		if(validateReturnClusteredFields())
		{
			$("#returnClusteredDiv").show();
		
		}else{
			//alert("Inside else");
			chk.checked=false;
		}
	}
	else
	{
		resetReturnClusterField();
		$("#returnClusteredDiv").hide();
	}
	
}

function validateClusteredFields()
{
		//alert("Inside validateClusteredFields");	
		var check="";
        
        if(document.getElementById("viaRoute")!=null )
        {
	        if(document.getElementById("viaRoute").checked==true)
	        {
	        	alert("You can Either select Via Route or Clustered Route")
	        	document.getElementById("viaRoute").checked=false;
	        	$("#viaDiv").hide();
	        	return false;
	       	}
        }
        
          if(document.getElementById("breakJourney")!=null )
        {
	        if(document.getElementById("breakJourney").checked==true)
	        {
	        	alert("You can Either select Break Journey or Clustered Route");
	        	$("#selectedTrainNo").val("");
				$("#fromStation").prop('disabled', false);
				$("#toStation").prop('disabled', false);
				$("input#fromStation").prop("readonly", false);
				$("input#toStation").prop("readonly", false);
	        	document.getElementById("breakJourney").checked=false;
	        	$("#breakJrnyDiv").hide();
	        	return false;
	       	}
        }
        
       if(document.getElementById("journeyDate").value=="dd/mm/yyyy" || document.getElementById("journeyDate").value=="" )
	   	{
			alert("Please Enter Journey Date")
			document.getElementById("journeyDate").focus();
        	document.getElementById("clusteredRoute").checked=false;
	    	return false;
	   	}
        
      	if(document.getElementById("fromStation").value=="")
	  	{
	   		alert("Please Enter From Station And To Station Fields");
	   		document.getElementById("clusteredRoute").checked=false;
		    return false;
	   	}
	   	if(document.getElementById("toStation").value=="")
	   	{
	   		alert("Please Enter From Station And To Station Fields");
		    document.getElementById("clusteredRoute").checked=false;
		    return false;
	   	}
	   	
	   	check=validate_required(document.getElementById("entitledClass"),"Please Select Entitled Class");
	   	if(!check)
	   		document.getElementById("clusteredRoute").checked=false;
	   		
	   	return check;
	   	
} 	


function validateReturnClusteredFields()
{     	
		//alert("Inside validateClusteredFields");	
		var check="";
        
        if(document.getElementById("returnViaRoute")!=null )
        {
	        if(document.getElementById("returnViaRoute").checked==true)
	        {
	        	alert("You can Either select Via Route or Clustered Route")
	        	document.getElementById("returnViaRoute").checked=false;
	        	return false;
	       	}
        }
        
	if (document.getElementById("returnBreakJourney") != null) {
		if (document.getElementById("returnBreakJourney").checked == true) {
			alert("You can Either select Break Journey or Clustered Route");
			$("#returnSelectedTrainNo").val("");
			$("#returnFromStation").prop('disabled', false);
			$("#returnToStation").prop('disabled', false);
			$("input#returnFromStation").prop("readonly", false);
			$("input#returnToStation").prop("readonly", false);
			document.getElementById("returnBreakJourney").checked = false;
			$("#returnBreakJrnyDiv").hide();
			return false;
		}
	}
        
        if(document.getElementById("return_JourneyDate").value=="dd/mm/yyyy" || document.getElementById("return_JourneyDate").value=="" )
	   	{
			alert("Please Enter Return Journey Date")
			document.getElementById("return_JourneyDate").focus();
        	document.getElementById("returnViaRoute").checked=false;
	    	return false;
	   	}
      	if(document.getElementById("returnFromStation").value=="")
	  	{
	   		alert("Please Enter Return From Station And To Station Fields");
	   		document.getElementById("returnViaRoute").checked=false;
		    return false;
	   	}
	   	if(document.getElementById("returnToStation").value=="")
	   	{
	   		alert("Please Enter Return From Station And To Station Fields");
		    document.getElementById("returnViaRoute").checked=false;
		    return false;
	   	}
	   	
	   	check=validate_required(document.getElementById("returnEntitledClass"),"Please Select Return Entitled Class");
	   	if(!check)
	   		document.getElementById("returnClusteredRoute").checked=false;
	   		
	   	return check;
	   	
} 	


function resetClusterField()
{
	//alert("Inside resetClusterField");
	$('#clusterValidate').val('false')
	$('#clusterTrainNoList').val('');
  	$("#clusteredInfoDiv").html("");
	$("#clusteredInfoDiv").hide();
}

function resetReturnClusterField()
{
	//alert("Inside resetClusterField");
	$('#returnClusterValidate').val('false')
	$('#returnClusterTrainNoList').val('');
  	$("#returnClusteredInfoDiv").html("");
	$("#returnClusteredInfoDiv").hide();
}


function validateClusterStation()
{
	if(validateClusteredFields())
	{
		if($("#clusterStation").val()=="")
	  	{
	   		alert("Please Enter Cluster Station Field");
	   		$("#clusterStation").val("");
	   		$("#clusterStation").focus();
		    return false;
	   	}
	   	var isClusterStationExist=false;
	   	
	   	var frmStn=$('#fromStation').val();
	   	var clusterStation=$('#clusterStation').val();
		var journeyDate=$('#journeyDate').val();
		var reqType=$('#reqType').val();
		var entitledClass=$('#entitledClass :selected').val();
		frmStn=getStationCode(frmStn);
		clusterStation=getStationCode(clusterStation);
		journeyDate=changeDateFormat(journeyDate);
		//alert("frmStn-"+frmStn+",clusterStation-"+clusterStation+",journeyDate-"+journeyDate+",entitledClass-"+entitledClass);
	   	$("#clusteredInfoDiv").show();
	   	
	   	$.ajax(
		{
		  url: $("#context_path").val()+"mb/getTrainListDetails",
	      type: "get",
	      data: "frmStation="+ frmStn +"&toStation=" + clusterStation +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass  ,
	      dataType: "json",
	      async: false,
	      beforeSend: function() 
	      {
	      		$("#clusteredInfoDiv").html("Please Wait We Are Validating Cluster Station ["+ clusterStation +"]...");
	      		var calHeight=document.body.offsetHeight+20;
			    $("#screen-freeze").css({"height":calHeight + "px"} );
			    $("#screen-freeze").css("display", "block");
	      },
	      complete: function()
	      {
	      	   //alert("After Complete");
	      	   if($('#clusterValidate').val()=='false')
	      	   {
	  		  		$("#clusteredInfoDiv").html("");
	  		  		$('#clusterTrainNoList').val('');
	      	   }
	      	   
	      	   $("#screen-freeze").css("display", "none");	
	      },
	 	
	      success: function(msg)
	      {
	      	 
	          var errorDescptrn=msg.errorMessage;
	          var errordesc="";
	          
	          if(errorDescptrn!=null)
	             errordesc=msg.errorMessage.toUpperCase();
	          var errorCode=msg.errorMessage;
	         
	          if(errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004'  || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
	          {
		         alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
	          {
	          	 alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
	          {
		         alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else{
				  $.each(msg.trainBtwnStnsList, function(i,obj){
	          	
					var trainNo=obj.trainNumber;

					//alert(trainNo+"~"+trainName+"~"+originStnCode+"~"+sourceStnCode+"~"+destStnCode);

					if(isClusterFoundInTrain(trainNo,frmStn,clusterStation,journeyDate,0)){
						isClusterStationExist=true;
						var clusterTrainNoListValue=$('#clusterTrainNoList').val();
						clusterTrainNoListValue=clusterTrainNoListValue+trainNo+",";
						$('#clusterTrainNoList').val(clusterTrainNoListValue);
					}
				});
				
				//alert("isClusterStationExist~"+isClusterStationExist);
				
				if(!isClusterStationExist){
					alert("Cluster Station["+clusterStation+"] Is Not Valid, Please Try Another.")
					$('#clusterValidate').val("false");
					$('#clusterStation').val("");
					$('#clusterStation').focus();
				}else{
					alert("Cluster Station["+clusterStation+"] Is Valid.")
					$('#clusterValidate').val("true");
					var fStn=$('#fromStation').val();
					var tStn=$('#toStation').val();
					var cStn=$('#clusterStation').val();
					
					var viafields="";
					   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
					   viafields+='<tr><td colspan="4" align="left"><b>Cluster Route</b></td></tr>'
					   viafields+='<tr><td align="left" class="label">From Station</td><td align="left" class="label">To Station</td><td align="left" class="label">Date Of Journey</td><td class="label"></td></tr>'
					  
					   viafields+='<tr><td align="left"><input type="text" class="txtfldM" id="frmStation0" name="frmStation0" value="'+fStn+'" readonly autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="text" class="txtfldM" id="toStation0" name="toStation0"  value="'+ cStn+'" readonly autocomplete="off"></td>'
				       viafields+='<td align="left"><input type="text" class="txtfldM" id="journeyDate0" name="journeyDate0" autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(0)"/></td></tr>'
				      
				       viafields+='<tr><td align="left"><input type="text" class="txtfldM" id="frmStation1" name="frmStation1" value="'+ cStn+'" readonly autocomplete="off"></td>'
				       viafields+='<td align="left"><input type="text" class="txtfldM" value="'+tStn+'" id="toStation1" name="toStation1"  autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="text" class="txtfldM" id="journeyDate1" name="journeyDate1" autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainSearch(1)"/></td></tr>'
					   viafields+='</table>';
					   
					//alert(viafields);
					$("#clusteredInfoDiv").html(viafields);	   
					$("#clusteredInfoDiv").show();	
					$("#journeyDate0").datepicker(
					{dateFormat:'dd/mm/yy',
					  	 minDate:0	,
						 showOtherMonths:true,
						 selectOtherMonths:true,
						 numberOfMonths:1,
		    			 beforeShow : function () {
						 $(this).val("");
							 },
						 onSelect: function(selected) 
						 {
							 var dateString = selected.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
							 var dtMax = new Date( dateString[3], dateString[2]-1, dateString[1] );
						     dtMax.setDate(dtMax.getDate() + 2);
							 var dd = dtMax.getDate();
						     var mm = dtMax.getMonth() + 1;
						     var y = dtMax.getFullYear();
						     var dtFormatted = dd +'/'+mm+'/'+ y;
						     $("#journeyDate0").datepicker("option","minDate", $('#journeyDate').val());
							 $("#journeyDate1").datepicker("option","minDate", selected);
							 $("#journeyDate1").datepicker("option", "maxDate", dtFormatted);
						 } 
					});
   					$("#journeyDate1").datepicker({dateFormat:'dd/mm/yy',minDate:$('#travelStartDate').val(),showOtherMonths:true,selectOtherMonths:true,numberOfMonths:1,
		    		beforeShow : function () {
						 $(this).val("");
					 }
			 });
     				
				}
	          	  
	          }
	      	  //return check;
	      }
	      
	   });
	   
	}else{
		$("#clusteredDiv").hide();
		$("#clusterStation").val("");
		$("#clusteredRoute").attr('checked', false);
	}
}


function validateReturnClusterStation()
{
	if(validateReturnClusteredFields())
	{
		if($("#returnClusterStation").val()=="")
	  	{
	   		alert("Please Enter Cluster Station Field");
	   		$("#returnClusterStation").val("");
	   		$("#returnClusterStation").focus();
		    return false;
	   	}
	   	var isClusterStationExist=false;
	   	
	   	var frmStn=$('#returnFromStation').val();
	   	var clusterStation=$('#returnClusterStation').val();
		var journeyDate=$('#return_JourneyDate').val();
		var reqType=$('#reqType').val();
		var entitledClass=$('#returnEntitledClass :selected').val();
		frmStn=getStationCode(frmStn);
		clusterStation=getStationCode(clusterStation);
		journeyDate=changeDateFormat(journeyDate)
		//alert("frmStn-"+frmStn+",clusterStation-"+clusterStation+",journeyDate-"+journeyDate+",entitledClass-"+entitledClass);
	   	$("#returnClusteredInfoDiv").show();
	   	
	   	$.ajax(
		{
		  url: $("#context_path").val()+"mb/getTrainListDetails",	
	      type: "get",
	      data: "frmStation="+ frmStn +"&toStation=" + clusterStation +"&dep_date=" + journeyDate + "&journeyclass="+ entitledClass ,
	      dataType: "json",
	      async: false,
	      beforeSend: function() 
	      {
	      		$("#returnClusteredInfoDiv").html("Please Wait We Are Validating Cluster Station ["+ clusterStation +"]...");
	      		var calHeight=document.body.offsetHeight+20;
			    $("#screen-freeze").css({"height":calHeight + "px"} );
			    $("#screen-freeze").css("display", "block");
	      },
	      complete: function()
	      {
	      	   //alert("After Complete");
	      	   if($('#returnClusterValidate').val()=='false')
	      	   {
	  		  		$("#returnClusteredInfoDiv").html("");
	  		  		$('#returnClusterTrainNoList').val('');
	      	   }
	      	   
	      	   $("#screen-freeze").css("display", "none");	
	      },

	      success: function(msg)
	      {
	      	 
	          var errorDescptrn=msg.errorMessage;
	          var errordesc="";
	          
	          if(errorDescptrn!=null)
	             errordesc=msg.errorMessage;
	          var errorCode=msg.errorMessage;
	         
	          if(errordesc.indexOf('NO MATCHING TRAINS FOUND')>-1 || errordesc.indexOf("NO TRAINS EXISTS FOR THE GIVEN CLASS ON THIS ROUTE")>-1 || errordesc.indexOf("FROM AND TO STATIONS ARE NOT IN THE ROUTE OF THE TRAIN")>-1 || errorCode=='50'  || errorCode=='ENTF4' || errorCode=='BKT048' || errorCode=='801898A' || errorCode=='ET004'  || errordesc.indexOf("NO TRAINS AVAILABLE FOR THIS COMBINATION")>-1 || reqType=="ramBaanBooking")
	          {
		         alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else if(errordesc.indexOf('NO DIRECT TRAINS FOUND....')>-1 || reqType=="ramBaanBooking")
	          {
	          	 alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else if(msg.trainBtwnStnsList==null || msg.trainBtwnStnsList.length==0)
	          {
		         alert("Cluster Booking Is Not Permmited As Train Does Not Exists Between This Route");
	          }else
	          {
				   $.each(msg.trainBtwnStnsList, function(i,obj){
	          	
					var trainNo=obj.trainNumber;
					
					//alert(trainNo+"~"+trainName+"~"+originStnCode+"~"+sourceStnCode+"~"+destStnCode);
					
					if(isClusterFoundInTrain(trainNo,frmStn,clusterStation,journeyDate,1)){
						isClusterStationExist=true;
						var clusterTrainNoListValue=$('#returnClusterTrainNoList').val();
						clusterTrainNoListValue=clusterTrainNoListValue+trainNo+",";
						$('#returnClusterTrainNoList').val(clusterTrainNoListValue);
					}
				});
				
				//alert("isClusterStationExist~"+isClusterStationExist);
				
				if(!isClusterStationExist){
					alert("Cluster Station["+clusterStation+"] Is Not Valid, Please Try Another.")
					$('#returnClusterValidate').val("false");
					$('#returnClusterStation').val("");
					$('#returnClusterStation').focus();
				}else{
					alert("Cluster Station["+clusterStation+"] Is Valid.")
					$('#returnClusterValidate').val("true");
					var fStn=$('#returnFromStation').val();
					var tStn=$('#returnToStation').val();
					var cStn=$('#returnClusterStation').val();
					
					var viafields="";
					   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
					   viafields+='<tr><td colspan="4" align="left"><b>Cluster Route</b></td></tr>'
					   viafields+='<tr><td align="left" class="label">From Station</td><td align="left" class="label">To Station</td><td align="left" class="label">Date Of Journey</td><td class="label"></td></tr>'
					  
					   viafields+='<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation0" name="returnFrmStation0" value="'+fStn+'" readonly  autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="text" class="txtfldM" id="returnToStation0" name="returnToStation0"  value="'+ cStn+'" readonly autocomplete="off"></td>'
				       viafields+='<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate0" name="return_JourneyDate0" autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="button"  class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearch(0)"/></td></tr>'
				      
				       viafields+='<tr><td align="left"><input type="text" class="txtfldM" id="returnFrmStation1" name="returnFrmStation1" value="'+ cStn+'" readonly autocomplete="off"></td>'
				       viafields+='<td align="left"><input type="text" class="txtfldM" value="'+tStn+'" id="returnToStation1" name="returnToStation1"  autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="text" class="txtfldM" id="return_JourneyDate1" name="return_JourneyDate1" autocomplete="off"></td>'
					   viafields+='<td align="left"><input type="button" class="butn" value="Train Search" id="viaTrnStn" onclick="trainReturnSearch(1)"/></td></tr>'
					   viafields+='</table>';
					   
					//alert(viafields);
					$("#returnClusteredInfoDiv").html(viafields);	   
					$("#returnClusteredInfoDiv").show();	
					$("#return_JourneyDate0").datepicker(
					{dateFormat:'dd/mm/yy',
					  	 minDate:0	,
						 showOtherMonths:true,
						 selectOtherMonths:true,
						 numberOfMonths:1,
		    			 beforeShow : function () {
						 $(this).val("");
						 },
						 onSelect: function(selected) 
						 {
							 var dateString = selected.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
							 var dtMax = new Date( dateString[3], dateString[2]-1, dateString[1] );
						     dtMax.setDate(dtMax.getDate() + 2);
							 var dd = dtMax.getDate();
						     var mm = dtMax.getMonth() + 1;
						     var y = dtMax.getFullYear();
						     var dtFormatted = dd +'/'+mm+'/'+ y;
						     $("#return_JourneyDate0").datepicker("option","minDate", $('#return_JourneyDate').val());
						     $("#return_JourneyDate0").datepicker("option","maxDate", $('#travelEndDate').val());
							 $("#return_JourneyDate1").datepicker("option","minDate", selected);
							 $("#return_JourneyDate1").datepicker("option", "maxDate", dtFormatted);
						 } 
					});
   					$("#return_JourneyDate1").datepicker({dateFormat:'dd/mm/yy',minDate:$('#travelStartDate').val(),showOtherMonths:true,selectOtherMonths:true,numberOfMonths:1,
		    	beforeShow : function () {
						 $(this).val("");
					 }
			 });
     				
				}
	          	  
	          }
	      	  //return check;
	      }
	      
	   });
	   
	}else{
		$("#returnClusteredDiv").hide();
		$("#returnClusterStation").val("");
		$("#returnClusteredRoute").attr('checked', false);
	}
}


function isClusterFoundInTrain(trainNo,sourceStn,clusterStn,journeyDate,jrnyType)
{
	//alert("Inside isClusterFoundInTrain -trainNo="+trainNo+",sourceStn-"+sourceStn+"|clusterStn-"+clusterStn+",journeyDate-"+journeyDate);
	var isClusterFound=false;
	
	$.ajax(
	{
		  url: $("#context_path").val()+"mb/clusterStationSearch",
	      type: "get",
	      data: "srcStation="+ sourceStn +"&journeyDate=" + journeyDate + "&trainNo="+ trainNo +"&clusterStn="+ clusterStn ,
	      dataType: "json",
	      async: false,
	      beforeSend: function() 
	      {
	      		var calHeight=document.body.offsetHeight+20;
			    $("#screen-freeze").css({"height":calHeight + "px"} );
			    $("#screen-freeze").css("display", "block");
	      },
	      complete: function(){
	  		 	 $("#screen-freeze").css("display", "none");	
	      },
	 	
	      success: function(clusterMsg)
	      {
	      	
	      	// Get Cluster Station Xml and set station code in the dropdown for break journey
	      	
	        
	        if(clusterMsg!=null)
	      	{
      	 	 	 var errorMsg=clusterMsg.errorMessage;
	      	 	 if(errorMsg!=null && errorMsg.length>0){
	      	 	 	//alert(errorMsg);
	      	 	 }else
	      	 	 {
	      			var toStn="";
	      			if(jrnyType == 0){
	      				toStn=$('#toStation').val();
	      			}else if(jrnyType == 1){
	      				toStn=$('#returnToStation').val();
	      			}
	      	 	 	
	      	 		toStn=getStationCode(toStn);
	    		
		      	 	$.each(clusterMsg.clusterStationList, function(index,obj){
						   
						var enRoutePoint=obj.enRoutePoint;
						var stnNameCode=obj.stnNameCode;
						stnNameCode=stnNameCode.substring(stnNameCode.indexOf("-")+1, stnNameCode.length);
						stnNameCode=new String(stnNameCode);stnNameCode=stnNameCode.trim();
						//alert("enRoutePoint-"+enRoutePoint+"|clusterStn-"+clusterStn+"|stnNameCode-"+stnNameCode+"|toStn-"+toStn);
						if(enRoutePoint==clusterStn && stnNameCode==toStn)
						{
							isClusterFound=true;
						}
				 	});
	      	 	 }
	      	 }
	      	
	      	 
	        // alert("isClusterFound-"+isClusterFound);
	      	
	      }
	      
	   });
	return isClusterFound;
}