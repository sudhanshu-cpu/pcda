function airLTCViaRoutes(fromStn,tostn){
	
	var viafields="";
	
	if(fromStn.length > 3 && tostn.length > 3){
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
	   viafields+='<tr><td colspan="4" align="left"><b>Air Via Route</b></td></tr>'
	   viafields+='<tr><th align="center" width="50%">From Station</th><th align="center" width="50%">To Station</th></tr>'
	   viafields+='</table>'
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="airLTCViaRouteTable">'
	   viafields+='<tr><td align="center" width="50%">'+ fromStn+'</td>'
	   viafields+='<td align="left"><input type="text" class="txtfldM" id="ltcViaDestination0" name="ltcViaDestination0"  onkeyup="populateLTCViaAirport(this.id,0)" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsltcViaDestination0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListltcViaDestination0"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
      
       viafields+='<tr><td align="center" width="50%" id="ltcViaOrigin1"></td>'
       viafields+='<td align="left"><input type="text" class="txtfldM" id="ltcViaDestination1" name="ltcViaDestination1" value="'+tostn+'" onkeyup="populateLTCViaAirport(this.id,1)" readonly="readonly" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsltcViaDestination1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListltcViaDestination1"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
	   viafields+='</table>';
	  
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addAirLTCViaRow(\'' +tostn +'\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delAirLTCViaRow(\'' +tostn +'\');"/></td></tr></table>'
  
  	   $("#ltcAirConnectingFlightDtls").html(viafields);
  	   $("#ltcAirConnectingFlight").show();
	}
}
	
function populateLTCViaAirport(inputBoxId,index)
{
	var inputValue=$('#'+inputBoxId).val();
	var reqType=$('#reqType').val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
		      url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue+"&reqType="+reqType,
		      dataType: "json",
		      success: function(msg)
		      {
			      
			      $.each(msg, function(index,name){
		          
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillLTCViaAirportValue(\'' +name +'\',\''+inputBoxId+'\','+index+')">' +name+'</li>';
	              });
	              
				  $("#autoSuggestionsList"+inputBoxId).html(airportList);		
	              $('#suggestions'+inputBoxId).show();
	            
		       }
		});
	}
}	

function fillAirLTCViaStn(inputBoxId){
	$("#"+inputBoxId).val("");
}

function fillLTCViaAirportValue(airport,inputBoxId,index){
	
	 $("#"+inputBoxId).val("");
	 $("#"+inputBoxId).val(airport);
	 $('#suggestions'+inputBoxId).hide();
	 $("#ltcViaOrigin"+eval(index+1)).html(airport);
}


function addAirLTCViaRow(toStn){
	
	var rowCount = $('#airLTCViaRouteTable tr').length;
	
	if(rowCount < 3){
    $('#ltcViaDestination'+eval(rowCount-1)).val("");
    $('#ltcViaDestination'+eval(rowCount-1)).removeAttr("readonly");
     
  	var tbl = document.getElementById('airLTCViaRouteTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
     cellRight.id='ltcViaOrigin'+rowCount;
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'ltcViaDestination'+rowCount;
	 el.id = 'ltcViaDestination'+rowCount;
	 el.size = 50;
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 el.setAttribute('onkeyup',"populateLTCViaAirport(this.id,"+rowCount+");");
	 el.setAttribute('onblur',"fillAirLTCViaStn(this.id)");
	 var divEl=document.createElement('div');
	 divEl.id = 'suggestionsltcViaDestination'+rowCount;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListltcViaDestination'+rowCount;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	}
}



function delAirLTCViaRow(tostn){   

     var rowCount = $('#airLTCViaRouteTable tr').length;
   
     if(rowCount>2){
      $('#ltcViaDestination'+eval(rowCount-2)).val(tostn);
      $('#ltcViaDestination'+eval(rowCount-2)).attr("readonly","readonly");
  	  $('#airLTCViaRouteTable tr:last').remove();
  	  
  	  }
}



function airReturnLTCViaRoutes(fromStn,tostn){
	
	var viafields="";
	
	if(fromStn.length > 3 && tostn.length > 3){
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
	   viafields+='<tr><td colspan="4" align="left"><b>Air Via Route</b></td></tr>'
	   viafields+='<tr><th align="center" width="50%">From Station</th><th align="center" width="50%">To Station</th></tr>'
	   viafields+='</table>'
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnAirLTCViaRouteTable">'
	   viafields+='<tr><td align="center" width="50%">'+ fromStn+'</td>'
	   viafields+='<td align="left"><input type="text" class="txtfldM" id="returnLtcViaDestination0" name="returnLtcViaDestination0"  onkeyup="populateReturnLTCViaAirport(this.id,0)" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsreturnLtcViaDestination0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnLtcViaDestination0"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
      
       viafields+='<tr><td align="center" width="50%" id="returnLtcViaOrigin1"></td>'
       viafields+='<td align="left"><input type="text" class="txtfldM" id="returnLtcViaDestination1" name="returnLtcViaDestination1" value="'+tostn+'" onkeyup="populateReturnLTCViaAirport(this.id,1)" readonly="readonly" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsreturnLtcViaDestination1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnLtcViaDestination1"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
	   viafields+='</table>';
	  
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addReturnAirLTCViaRow(\'' +tostn +'\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delReturnAirLTCViaRow(\'' +tostn +'\');"/></td></tr></table>'
  
  	   $("#returnLtcAirConnectingFlightDtls").html(viafields);
  	   $("#returnLtcAirConnectingFlight").show();
	}
}
	
function populateReturnLTCViaAirport(inputBoxId,index)
{
	var inputValue=$('#'+inputBoxId).val();
	var reqType=$('#reqType').val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue+"&reqType="+reqType,
		      dataType: "json",
		      success: function(msg)
		      {
			      $.each(msg, function(index,name){
		          
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillReturnLTCViaAirportValue(\'' +name +'\',\''+inputBoxId+'\','+index+')">' +name+'</li>';
	              });
	              
				  $("#autoSuggestionsList"+inputBoxId).html(airportList);		
	              $('#suggestions'+inputBoxId).show();
	            
		       }
		});
	}
}	

function fillReturnLTCViaAirportValue(airport,inputBoxId,index){
	
	 $("#"+inputBoxId).val("");
	 $("#"+inputBoxId).val(airport);
	 $('#suggestions'+inputBoxId).hide();
	 $("#returnLtcViaOrigin"+eval(index+1)).html(airport);
}


function addReturnAirLTCViaRow(toStn){
	
	var rowCount = $('#returnAirLTCViaRouteTable tr').length;
	
	if(rowCount < 3){
    $('#returnLtcViaDestination'+eval(rowCount-1)).val("");
    $('#returnLtcViaDestination'+eval(rowCount-1)).removeAttr("readonly");
     
  	var tbl = document.getElementById('returnAirLTCViaRouteTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
     cellRight.id='returnLtcViaOrigin'+rowCount;
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnLtcViaDestination'+rowCount;
	 el.id = 'returnLtcViaDestination'+rowCount;
	 el.size = 50;
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 el.setAttribute('onkeyup',"populateReturnLTCViaAirport(this.id,"+rowCount+");");
	 el.setAttribute('onblur',"fillAirLTCViaStn(this.id)");
	 var divEl=document.createElement('div');
	 divEl.id = 'suggestionsreturnLtcViaDestination'+rowCount;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListreturnLtcViaDestination'+rowCount;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	}
}

function delReturnAirLTCViaRow(tostn){   

     var rowCount = $('#returnAirLTCViaRouteTable tr').length;
   
     if(rowCount>2){
      $('#returnLtcViaDestination'+eval(rowCount-2)).val(tostn);
      $('#returnLtcViaDestination'+eval(rowCount-2)).attr("readonly","readonly");
  	  $('#returnAirLTCViaRouteTable tr:last').remove();
  	  
  	  }
}

//-------------------------------------------------------------------------------------------------------------------------------------------------------


function airMixedLTCViaRoutes(fromStn,tostn){
	
	var viafields="";
	
	if(fromStn.length > 3 && tostn.length > 3){
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
	   viafields+='<tr><td colspan="4" align="left"><b>Air Via Route</b></td></tr>'
	   viafields+='<tr><th align="center" width="50%">From Station</th><th align="center" width="50%">To Station</th></tr>'
	   viafields+='</table>'
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="airMixedLTCViaRouteTable">'
	   viafields+='<tr><td align="center" width="50%">'+ fromStn+'</td>'
	   viafields+='<td align="left"><input type="text" class="txtfldM" id="mixedLTCViaDestination0" name="mixedLTCViaDestination0"  onkeyup="populateMixedLTCViaAirport(this.id,0)" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsmixedLTCViaDestination0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListmixedLTCViaDestination0"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
      
       viafields+='<tr><td align="center" width="50%" id="mixedLTCViaOrigin1"></td>'
       viafields+='<td align="left"><input type="text" class="txtfldM" id="mixedLTCViaDestination1" name="mixedLTCViaDestination1" value="'+tostn+'" onkeyup="populateMixedLTCViaAirport(this.id,1)" readonly="readonly" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsmixedLTCViaDestination1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListmixedLTCViaDestination1"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
	   viafields+='</table>';
	  
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addMixedAirLTCViaRow(\'' +tostn +'\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delMixedAirLTCViaRow(\'' +tostn +'\');"/></td></tr></table>'
  
  	   $("#mixedLTCAirConnectingFlightDtls").html(viafields);
  	   $("#mixedLTCAirConnectingFlight").show();
	}
}
	
function populateMixedLTCViaAirport(inputBoxId,index)
{
	var inputValue=$('#'+inputBoxId).val();
	var reqType=$('#reqType').val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
			  url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue+"&reqType="+reqType,
		      dataType: "json",
		      success: function(msg)
		      {
			      $.each(msg, function(index,name){
		          
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillMixedLTCViaAirportValue(\'' +name +'\',\''+inputBoxId+'\','+index+')">' +name+'</li>';
	              });
	              
				  $("#autoSuggestionsList"+inputBoxId).html(airportList);		
	              $('#suggestions'+inputBoxId).show();
	            
		       }
		});
	}
}	

function fillAirLTCViaStn(inputBoxId){
	$("#"+inputBoxId).val("");
}

function fillMixedLTCViaAirportValue(airport,inputBoxId,index){
	
	 $("#"+inputBoxId).val("");
	 $("#"+inputBoxId).val(airport);
	 $('#suggestions'+inputBoxId).hide();
	 $("#mixedLTCViaOrigin"+eval(index+1)).html(airport);
}


function addMixedAirLTCViaRow(toStn){
	
	var rowCount = $('#airMixedLTCViaRouteTable tr').length;
	
	if(rowCount < 3){
    $('#mixedLTCViaDestination'+eval(rowCount-1)).val("");
    $('#mixedLTCViaDestination'+eval(rowCount-1)).removeAttr("readonly");
     
  	var tbl = document.getElementById('airMixedLTCViaRouteTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
	 cellRight.align='left';
	 cellRight.id='mixedLTCViaOrigin'+rowCount;
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'mixedLTCViaDestination'+rowCount;
	 el.id = 'mixedLTCViaDestination'+rowCount;
	 el.size = 50;
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 el.setAttribute('onkeyup',"populateMixedLTCViaAirport(this.id,"+rowCount+");");
	 el.setAttribute('onblur',"fillAirLTCViaStn(this.id)");
	 var divEl=document.createElement('div');
	 divEl.id = 'suggestionsmixedLTCViaDestination'+rowCount;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListmixedLTCViaDestination'+rowCount;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	}
}



function delMixedAirLTCViaRow(tostn){   

     var rowCount = $('#airMixedLTCViaRouteTable tr').length;
   
     if(rowCount>2){
      $('#mixedLTCViaDestination'+eval(rowCount-2)).val(tostn);
      $('#mixedLTCViaDestination'+eval(rowCount-2)).attr("readonly","readonly");
  	  $('#airMixedLTCViaRouteTable tr:last').remove();
  	  
  	  }
}



function airReturnMixedLTCViaRoutes(fromStn,tostn){
	
	var viafields="";
	
	if(fromStn.length > 3 && tostn.length > 3){
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt">'
	   viafields+='<tr><td colspan="4" align="left"><b>Air Via Route</b></td></tr>'
	   viafields+='<tr><th align="center" width="50%">From Station</th><th align="center" width="50%">To Station</th></tr>'
	   viafields+='</table>'
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt" id="returnAirMixedLTCViaRouteTable">'
	   viafields+='<tr><td align="center" width="50%">'+ fromStn+'</td>'
	   viafields+='<td align="left"><input type="text" class="txtfldM" id="returnMixedLtcViaDestination0" name="returnMixedLtcViaDestination0"  onkeyup="populateReturnMixedLTCViaAirport(this.id,0)" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsreturnMixedLtcViaDestination0" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnMixedLtcViaDestination0"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
      
       viafields+='<tr><td align="center" width="50%" id="returnMixedLtcViaOrigin1"></td>'
       viafields+='<td align="left"><input type="text" class="txtfldM" id="returnMixedLtcViaDestination1" name="returnMixedLtcViaDestination1" value="'+tostn+'" onkeyup="populateReturnMixedLTCViaAirport(this.id,1)" readonly="readonly" size="50" onblur="fillAirLTCViaStn(this.id);"/>'
       viafields+='<div class="suggestionsBox" id="suggestionsreturnMixedLtcViaDestination1" style="display: none;" ><div  class="suggestionList"  id="autoSuggestionsListreturnMixedLtcViaDestination1"></div></div>'
       viafields+='</td>'
       viafields+='</tr>'
	   viafields+='</table>';
	  
	   viafields+='<table width="100%" cellpadding="4" cellspacing="1" class="formtxt"><tr><td align="right"><input type="button" class="butn" value="Add Row" onclick="addReturnMixedAirLTCViaRow(\'' +tostn +'\')"/>&nbsp;<input class="butn" type="button" value="Delete Row" onclick="delReturnMixedAirLTCViaRow(\'' +tostn +'\');"/></td></tr></table>'
  
  	   $("#returnMixedLTCAirConnectingFlightDtls").html(viafields);
  	   $("#returnMixedLTCAirConnectingFlight").show();
	}
}
	
function populateReturnMixedLTCViaAirport(inputBoxId,index)
{
	var inputValue=$('#'+inputBoxId).val();
	var reqType=$('#reqType').val();
	if(inputValue.length>1)
	{
		var airportList="";
		$.ajax(
		{
			 url: $("#context_path").val()+"mb/getAirportList",
		      type: "get",
		      data: "stationName="+inputValue+"reqType="+reqType,
		      dataType: "json",
		      success: function(msg)
		      {
			     $.each(msg, function(index,name){
		         
					if(name=="Airport Not Exist")
	                	airportList+='<li>' +name+'</li>';
	                else
	                    airportList+='<li onClick="fillReturnMixedLTCViaAirportValue(\'' +name +'\',\''+inputBoxId+'\','+index+')">' +name+'</li>';
	              });
	              
				  $("#autoSuggestionsList"+inputBoxId).html(airportList);		
	              $('#suggestions'+inputBoxId).show();
	            
		       }
		});
	}
}	

function fillReturnMixedLTCViaAirportValue(airport,inputBoxId,index){
	
	 $("#"+inputBoxId).val("");
	 $("#"+inputBoxId).val(airport);
	 $('#suggestions'+inputBoxId).hide();
	 $("#returnMixedLtcViaOrigin"+eval(index+1)).html(airport);
}


function addReturnMixedAirLTCViaRow(toStn){
	
	var rowCount = $('#returnAirMixedLTCViaRouteTable tr').length;
	
	if(rowCount < 3){
    $('#returnMixedLtcViaDestination'+eval(rowCount-1)).val("");
    $('#returnMixedLtcViaDestination'+eval(rowCount-1)).removeAttr("readonly");
     
  	var tbl = document.getElementById('returnAirMixedLTCViaRouteTable');
  	var lastRow = tbl.rows.length;
	var row=tbl.insertRow(lastRow);
	
	 var cellRight = row.insertCell(0);
     cellRight.align='left';
     cellRight.id='returnMixedLtcViaOrigin'+rowCount;
	 
	 var cellRight = row.insertCell(1);
	 cellRight.align='left';
	 var el = document.createElement('input');
	 el.type = 'text';
	 el.name = 'returnMixedLtcViaDestination'+rowCount;
	 el.id = 'returnMixedLtcViaDestination'+rowCount;
	 el.size = 50;
	 el.setAttribute("readonly","readonly");
	 el.className="txtfldM";
	 el.setAttribute('onkeyup',"populateReturnMixedLTCViaAirport(this.id,"+rowCount+");");
	 el.setAttribute('onblur',"fillAirLTCViaStn(this.id)");
	 var divEl=document.createElement('div');
	 divEl.id = 'suggestionsreturnMixedLtcViaDestination'+rowCount;
	 divEl.className='suggestionsBox';
	 el.value=toStn
	 var divSugg=document.createElement('div')
	 divSugg.className='suggestionList';
	 divSugg.id = 'autoSuggestionsListreturnMixedLtcViaDestination'+rowCount;
	 divEl.style.display='none'
	 divEl.appendChild(divSugg);
	 
	 cellRight.appendChild(el);
	 cellRight.appendChild(divEl);
	}
}

function delReturnMixedAirLTCViaRow(tostn){   

     var rowCount = $('#returnAirMixedLTCViaRouteTable tr').length;
   
     if(rowCount>2){
      $('#returnMixedLtcViaDestination'+eval(rowCount-2)).val(tostn);
      $('#returnMixedLtcViaDestination'+eval(rowCount-2)).attr("readonly","readonly");
  	  $('#returnAirMixedLTCViaRouteTable tr:last').remove();
  	  
  	  }
}
