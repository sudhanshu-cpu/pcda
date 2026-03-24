var hotelRowDelete = "hotelRowDelete";
var foodRowDelete = "foodRowDelete";
var tourRowDelete = "tourRowDelete";
var taRowDelete = "taRowDelete";
var leaveRowDelete = "leaveRowDelete";

var hotelTableIdlen = 0;
var foodTableIdlen = 0;
var tourTableIdlen = 0;
var taTableIdlen = 0;
var leaveTableIdlen = 0;
var ltcJrnyTableIdlen = 0;
var ltcJrnyPassTableIdlen = 0;
var ptJrnyTableIdlen = 0;
var ptJrnyPassTableIdlen = 0;

var validHotelHeaderFlag = false ;
var validFoodHeaderFlag = false ;
var validTourHeaderFlag = false ;
var validTaHeaderFlag = false ;
var validLeaveHeaderFlag = false ;


function claimReturned(){
	$("#claim_return_Comment").show();
	$("#claim_approve_action").hide();
}
function cancelClaimReturn(){
	$("#claim_return_Comment").hide();
	$("#claim_approve_action").show();
}
function submitClaimReturn(){
	var disapproveDetail=document.getElementById("claimReturnComment").value;
    
	if(disapproveDetail.trim()=='')
	{
		alert("Please Enter Reason for claim return");
		document.getElementById("claimReturnComment").focus();
		return false;
	}
	$("#disaproveReasons").val(disapproveDetail);
	$("#claimReturnedForm").submit();
}
function editPAOClaimIdTaDaDetails(claimId)
{
	var calHeight=document.body.offsetHeight+20;
	$("#screen-freeze").css({"height":calHeight + "px"} );
	$("#screen-freeze").css("display", "block");
		
	var urlToSet = "/pcda/af/pcda/request/requestPoolForTADAEdit.do";
  	document.taDaRptFilter.method="post";
  	document.getElementById("selected_claimId").value=claimId;
  	var actionOriginal=document.taDaRptFilter.action;
    document.taDaRptFilter.action=actionOriginal.replace('/page', "");
    document.taDaRptFilter.action=urlToSet;			
    document.taDaRptFilter.submit();	
	
}

function editClaimIdTaDaDetails(claimId){
	var calHeight=document.body.offsetHeight+20;
	$("#screen-freeze").css({"height":calHeight + "px"} );
	$("#screen-freeze").css("display", "block");

  	$("#selected_claimId").value=claimId;

    $("#taDaRptFilter").submit();	
}

 function myCreateFunction(detail){
   
	if(detail=='hotelAddmore')
	{
		if(validateLeaveDetails() && valideteHotelDetails())
		{
		    var table = document.getElementById("hotelTableGrid");
		    var header = table.createTHead();
		    header.className="allp";
		    if(0==hotelTableIdlen || !validHotelHeaderFlag)
			{
				validHotelHeaderFlag = true ;
				
				var row0 = header.insertRow(-1);
			    	row0.className = "lablevaluebg";
			    var cell10 =  row0.insertCell(0);
	   	    		cell10.setAttribute("width","100px;");
	   	    		cell10.className="lablevalue";
			    var cell20 =  row0.insertCell(1);
			   	    cell20.setAttribute("width","70px;");
			   	    cell20.className="lablevalue";
			    var cell30 =  row0.insertCell(2);
			   	    cell30.setAttribute("width","120px;");
			   	    cell30.className="lablevalue";
			    var cell40 =  row0.insertCell(3);
			   	    cell40.setAttribute("width","110px;");
			   	    cell40.className="lablevalue";
			    var cell50 =  row0.insertCell(4);
					cell50.setAttribute("width","110px;");
					cell50.className="lablevalue";
			    var cell60 =  row0.insertCell(5);
					cell60.setAttribute("width","110px;");
					cell60.className="lablevalue";
			    var cell70 =  row0.insertCell(6);
					cell70.setAttribute("width","110px;");
					cell70.className="lablevalue";
				var cell80 =  row0.insertCell(7);
					cell80.setAttribute("width","140px;");
					cell80.className="lablevalue";
				var cell90 =  row0.insertCell(8);
					cell90.setAttribute("width","60px;");
					cell90.className="lablevalue";
					cell90.setAttribute("colspan","2");
				
				 	    
				    cell10.innerHTML = "Hotel/Mess";
				    cell20.innerHTML = "Hotel/MESS Name";
				    cell30.innerHTML = "Hotel/MESS location";
				    cell40.innerHTML = "Hotel GST No";
				    cell50.innerHTML = "Check In Date";
				    cell60.innerHTML = "Check Out Date";
				    cell70.innerHTML = "Number of Days";
				    cell80.innerHTML = "Hotel Bill (Excluding GST)";
				    cell90.innerHTML = "GST Amt";
				    
				    
				    row0.id = "hotelHeaderId";
			}
		    
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 =  row.insertCell(0);
		    	cell1.setAttribute("style","white-space: nowrap;");
		   	    cell1.setAttribute("width","100px;");
		    var cell2 =  row.insertCell(1);
		    	cell2.setAttribute("style","white-space: nowrap;");
		   	    cell2.setAttribute("width","70px;");
		    var cell3 =  row.insertCell(2);
		    	cell3.setAttribute("style","white-space: nowrap;");
		   	    cell3.setAttribute("width","120px;");
		    var cell4 =  row.insertCell(3);
		    	cell4.setAttribute("style","white-space: nowrap;");
		   	    cell4.setAttribute("width","110px;");
		    var cell5 =  row.insertCell(4);
		   	    cell5.setAttribute("style","white-space: nowrap;");
				cell5.setAttribute("width","110px;");
		    var cell6 =  row.insertCell(5);
				cell6.setAttribute("style","white-space: nowrap;");
				cell6.setAttribute("width","110px;");
		    var cell7 =  row.insertCell(6);
				cell7.setAttribute("style","white-space: nowrap;");			
				cell7.setAttribute("width","110px;");
			var cell8 =  row.insertCell(7);
				cell8.setAttribute("style","white-space: nowrap;");			
				cell8.setAttribute("width","140px;");
			var cell9 =  row.insertCell(8);
				cell9.setAttribute("style","white-space: nowrap;");			
				cell9.setAttribute("width","90px;");	
			var cell10 =  row.insertCell(9);
				cell10.setAttribute("style","white-space: nowrap;");			
				cell10.setAttribute("width","60px;");
			
			
		   	    hotelTableIdlen++;
		   	    
		      
			    
			var innerHtmlValue = '' ;
			    innerHtmlValue='<td><select name="hotelOrMess" id="hotelOrMess'+hotelTableIdlen+'" class="combo" style="width:80%;" onchange="takeMessOrNACInfo(this.value,'+hotelTableIdlen+');"><option value="">Select</option><option value="Hotel">Hotel</option><option value="Mess">Mess</option></select></td>' ;
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="hotelNACTaken" id="hotelNACTaken'+hotelTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="messLevel" id="messLevel'+hotelTableIdlen+'" class="txtfldM" />';
			    cell1.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue='';
			    innerHtmlValue='<input type="text" name="hotelName" id="hotelName'+hotelTableIdlen+'"   class="txtfldM" size="15" maxlength="50" style="width:80%;" autocomplete="off"/>';
			    cell2.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<div>'
			    innerHtmlValue=innerHtmlValue+'<input type="text"   name="hotelCity" id="hotelCity'+hotelTableIdlen+'"  onkeyup="fetchCityList('+hotelTableIdlen+',this)" class="txtfldM" size="7" style="width:80%;" autocomplete="off" onblur="fillCity(\'\',\'\',\'' +hotelTableIdlen +'\')"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden" name="destinationCityGrade"  id="destinationCityGrade'+hotelTableIdlen+'"  value=""/>';
			    innerHtmlValue=innerHtmlValue+'<div class="suggestionsBox" id="suggestionsDestCity'+hotelTableIdlen+'" style="display: none;"><div class="suggestionList" id="autoSuggestionsListforDestCity'+hotelTableIdlen+'"></div></div></div>';
			    cell3.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="text"  name="hotelGSTNo" id="hotelGSTNo'+hotelTableIdlen+'" size="12" class="txtfldM" maxlength="20" style="width:80%;" autocomplete="off"/>';
			    cell4.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="text"  readonly="true" name="checkInDate" id="checkInDate'+hotelTableIdlen+'" size="14" class="txtfldM" maxlength="20"  autocomplete="off"/>';
			    cell5.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="text"  readonly="true" name="checkOutDate" id="checkOutDate'+hotelTableIdlen+'" size="14" class="txtfldM" maxlength="20"  autocomplete="off"/>';
			    cell6.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="text"  name="hotelNoDays" id="hotelNoDays'+hotelTableIdlen+'"  size="4" class="txtfldM" onkeypress="return isNumericOnlyKey(event)" onblur="validateHotelDays('+hotelTableIdlen+');" maxlength="4" style="width:80%;" autocomplete="off"/>';
			    cell7.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="hidden"  readonly="true" name="hotelDailyAmount" id="hotelDailyAmount'+hotelTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<input type="text" name="hotelAmount" id="hotelAmount'+hotelTableIdlen+'" onkeypress="return isNumberKey(event)"  onblur="getAmount(1,'+hotelTableIdlen+');" size="7" style="width:80%;" class="txtfldM" maxlength="7" autocomplete="off"/><input type="hidden" name="actualHotelAmount" id="actualHotelAmount'+hotelTableIdlen+'"/></td>';
			    cell8.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue=innerHtmlValue+'<input type="text" name="hotelGSTAmount" id="hotelGSTAmount'+hotelTableIdlen+'" value="0" onkeypress="return isNumberKey(event)"  size="7" class="txtfldM" maxlength="7" style="width:80%;" autocomplete="off"/></td>';
			    cell9.innerHTML = innerHtmlValue;
			   
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="hidden"  readonly="true" name="hotelTableIdlen" value="'+hotelTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+hotelRowDelete+','+hotelTableIdlen+')">' ;
			    cell10.innerHTML = innerHtmlValue;
			    
			   $('#checkInDate'+hotelTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                   scrollInput:false,yearEnd : 2100 ,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateFromDate(1,hotelTableIdlen);},
		onShow: function () {
			  $('#checkInDate'+hotelTableIdlen).val("");
		}
		});
			   $('#checkOutDate'+hotelTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                   scrollInput:false,yearEnd : 2100, defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateDate(1,hotelTableIdlen);},
		onShow: function () {
			  $('#checkOutDate'+hotelTableIdlen).val("");
		}
		});
			  
		}
   	}
   if(detail=='foodAddmore')
   {
	   	if(validateLeaveDetails() && valideteFoodDetails())
		{
		    var table = document.getElementById("foodTableGrid");
		    var header = table.createTHead();
		   	header.className="allp";
		     if(0==foodTableIdlen || !validFoodHeaderFlag)
			 {
			 	validFoodHeaderFlag = true ;
			 	
			 	if($("#userServiceType").val()=='1'){
			 		
			 		var row0 = header.insertRow(-1);
				    	row0.className = "lablevaluebg";
				    var cell20 =  row0.insertCell(0);
				   	    cell20.setAttribute("width","20%;");
				   	    cell20.className="lablevalue";
				    var cell30 =  row0.insertCell(1);
				   	    cell30.setAttribute("width","20%;");
				   	    cell30.className="lablevalue";
				   	    cell30.setAttribute("colspan","2");
				        
				        cell20.innerHTML = "Number of Days";
					    cell30.innerHTML = "Amount";
					    
					    row0.id = "foodHeaderId";
			
			    var row = table.insertRow(-1);
			    	row.className = "lablevaluebg";
			    var cell2 = row.insertCell(0);
			    	cell2.setAttribute("width","20%;");
			    	cell2.setAttribute("style","white-space: nowrap;");
			    var cell3 = row.insertCell(1);
			    	cell3.setAttribute("width","20%;");
			    	cell3.setAttribute("style","white-space: nowrap;");
			    var cell4 = row.insertCell(2);
			    	cell4.setAttribute("width","20%;");
			    	cell4.setAttribute("style","white-space: nowrap;");	
			   
			   	    foodTableIdlen++;
			   	    
			   	var innerHtmlValue='';
			   	    innerHtmlValue='<td ><input type="text" name="foodNoDay" id="foodNoDay'+foodTableIdlen+'"  class="txtfldM" size="10" maxlength="5" style="width:50%" onkeypress="return isNumericOnlyKey(event)"  onblur="calculateFoodAmount('+foodTableIdlen+');" autocomplete="off"/></td>';
				    cell2.innerHTML = innerHtmlValue;
				    
				    innerHtmlValue = '' ;
				    innerHtmlValue=innerHtmlValue+ '<td ><input type="text" name="foodAmount" id="foodAmount'+foodTableIdlen+'" onkeypress="return isNumberKey(event)" style="width:50%" class="txtfldM" size="10" maxlength="10" onblur="validateFoodAmount('+foodTableIdlen+');" autocomplete="off"/><input type="hidden" name="actualFoodAmount" id="actualFoodAmount'+foodTableIdlen+'"/></td>';
				    cell3.innerHTML = innerHtmlValue;
				    
				    innerHtmlValue = '' ;
				    innerHtmlValue='<input type="hidden"  name="nonDrawlCertificate" id="nonDrawlCertificate'+foodTableIdlen+'" value="1" class="txtfldM" />';
				    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="foodTableIdlen" value="'+foodTableIdlen+'" class="txtfldM" />';
				    innerHtmlValue=innerHtmlValue+'<td ><input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+foodRowDelete+','+foodTableIdlen+')"></td>';
				    cell4.innerHTML = innerHtmlValue;
				    
			 		
			 	}else{
			 	
			 	var row0 = header.insertRow(-1);
			    	row0.className = "lablevaluebg";
			    var cell10 =  row0.insertCell(0);
	   	    		cell10.setAttribute("width","50%;");
	   	    		cell10.className="lablevalue";
			    var cell20 =  row0.insertCell(1);
			   	    cell20.setAttribute("width","20%;");
			   	    cell20.className="lablevalue";
			    var cell30 =  row0.insertCell(2);
			   	    cell30.setAttribute("width","20%;");
			   	    cell30.className="lablevalue";
			   	    cell30.setAttribute("colspan","2");
			        
			        cell10.innerHTML = "Is the Non-drawl for Ration Certificate issued by the Quarter Master of the Parent Unit ?";
				    cell20.innerHTML = "Number of Days";
				    cell30.innerHTML = "Amount";
				    
				    row0.id = "foodHeaderId";
		
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 = row.insertCell(0);
		    	cell1.setAttribute("width","40%;");
		    	cell1.setAttribute("style","white-space: nowrap;");
		    var cell2 = row.insertCell(1);
		    	cell2.setAttribute("width","20%;");
		    	cell2.setAttribute("style","white-space: nowrap;");
		    var cell3 = row.insertCell(2);
		    	cell3.setAttribute("width","20%;");
		    	cell3.setAttribute("style","white-space: nowrap;");
		    var cell4 = row.insertCell(3);
		    	cell4.setAttribute("width","20%;");
		    	cell4.setAttribute("style","white-space: nowrap;");	
		   
		   	    foodTableIdlen++;
		   	    
		   	var innerHtmlValue='';
		   	    innerHtmlValue='<select name="nonDrawlCertificate" id="nonDrawlCertificate'+foodTableIdlen+'" style="width:200px;" class="combo"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select>' ;
				cell1.innerHTML = innerHtmlValue;
		   	    
		        innerHtmlValue='';
				    innerHtmlValue='<td ><input type="text" name="foodNoDay" id="foodNoDay'+foodTableIdlen+'"  class="txtfldM" size="10" maxlength="5" style="width:50%" onkeypress="return isNumericOnlyKey(event)"  onblur="calculateFoodAmount('+foodTableIdlen+');" autocomplete="off"/></td>';
			    cell2.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
				    innerHtmlValue=innerHtmlValue+ '<td ><input type="text" name="foodAmount" id="foodAmount'+foodTableIdlen+'" onkeypress="return isNumberKey(event)" style="width:50%" class="txtfldM" size="10" maxlength="10" onblur="validateFoodAmount('+foodTableIdlen+');" autocomplete="off"/><input type="hidden" name="actualFoodAmount" id="actualFoodAmount'+foodTableIdlen+'"/></td>';
			    cell3.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="hidden"  readonly="true" name="foodTableIdlen" value="'+foodTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<td ><input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+foodRowDelete+','+foodTableIdlen+')"></td>';
			    cell4.innerHTML = innerHtmlValue;
			   
			  }   
			  }   
			    
		 } 
   }
   
   if(detail=='tourAddmore')
   {
   		if(validateLeaveDetails() && valideteTourDetails())
		{
		    var table = document.getElementById("tourTableGrid");
		    var header = table.createTHead();
		    header.className="allp";
		   if($("#is_conveyanceDtls").val()==0){ 
		     if(0==tourTableIdlen || !validTourHeaderFlag)
			 {
			 	validTourHeaderFlag = true ;
				var row0 = header.insertRow(-1);
			    	row0.className = "lablevaluebg";
			    var cell10 =  row0.insertCell(0);
		   	    	cell10.setAttribute("width","25%;");
		   	    	cell10.className="lablevalue";
			    var cell20 =  row0.insertCell(1);
			   	    cell20.setAttribute("width","15%;");
			   	    cell20.className="lablevalue";
			    var cell30 =  row0.insertCell(2);
			   	    cell30.setAttribute("width","15%;");
			   	    cell30.className="lablevalue";
			    var cell40 =  row0.insertCell(3);
			   	    cell40.setAttribute("width","15%;");
			   	    cell40.className="lablevalue"; 
			   	var cell50 =  row0.insertCell(4);
			   	    cell50.setAttribute("width","20%;"); 
			   	    cell50.className="lablevalue";
			   	var cell60 =  row0.insertCell(5);
			   	    cell60.setAttribute("width","10%;"); 
			   	    cell60.className="lablevalue";    
			   	         
		
				    cell10.innerHTML = "Vehicle No";
				    cell20.innerHTML = "Date of Travel";
				    cell30.innerHTML = "Distance";
				    cell40.innerHTML = "Bill No";
				    cell50.innerHTML = "Amount";
				    cell60.innerHTML = "";
				    
				    row0.id = "tourHeaderId";
			}
		    
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 = row.insertCell(0);
		    	cell1.setAttribute("style","white-space: nowrap;");
		    	cell1.setAttribute("width","25%;");
		    var cell2 = row.insertCell(1);
		    	cell2.setAttribute("style","white-space: nowrap;");
		   	    cell2.setAttribute("width","15%;");
		    var cell3 = row.insertCell(2);
		    	cell3.setAttribute("width","15%;");
		    	cell3.setAttribute("style","white-space: nowrap;");
		    var cell4 =  row.insertCell(3);
		   	    cell4.setAttribute("style","white-space: nowrap;");
		   	    cell4.setAttribute("width","15%;");
		   	var cell5 =  row.insertCell(4);
		   	    cell5.setAttribute("style","white-space: nowrap;");
		   	    cell5.setAttribute("width","20%;");
		   	var cell6 =  row.insertCell(5);
		   	    cell6.setAttribute("style","white-space: nowrap;");
		   	    cell6.setAttribute("width","10%;");
		   
		   	                
			    tourTableIdlen++;
			    
		    var innerHtmlValue='';
			    innerHtmlValue='<td ><input type="text" name="tourVehicleNo" id="tourVehicleNo'+tourTableIdlen+'"  class="txtfldM" size="20" maxlength="20" autocomplete="off"/></td>';
			    cell1.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td ><input type="text" readonly="true" name="tourDate" id="tourDate'+tourTableIdlen+'" class="txtfldM" size="12" style="width:243px;" maxlength="20" autocomplete="off"/></td>';
			    cell2.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td ><input type="text" name="tourDistance" id="tourDistance'+tourTableIdlen+'" class="txtfldM" size="8" maxlength="10" onkeypress="return isNumericOnlyKey(event)" autocomplete="off"/></td>';
			    cell3.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td ><input type="text" name="tourBillNo" id="tourBillNo'+tourTableIdlen+'" class="txtfldM" size="10" maxlength="30" autocomplete="off"/></td>';
			    cell4.innerHTML = innerHtmlValue;
			    
			 
			    innerHtmlValue = '' ;
			    innerHtmlValue=innerHtmlValue+'<td ><input type="text" name="tourAmount" id="tourAmount'+tourTableIdlen+'" onkeypress="return isNumberKey(event)" onblur="getAmount(3,'+tourTableIdlen+');" class="txtfldM" size="10" maxlength="10" autocomplete="off"/><input type="hidden" name="actualTourAmount" id="actualTourAmount'+tourTableIdlen+'"/></td>';
			    cell5.innerHTML = innerHtmlValue;
			   
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="hidden"  readonly="true" name="tourTableIdlen" value="'+tourTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<td ><input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+tourRowDelete+','+tourTableIdlen+')"></td>';
			    cell6.innerHTML = innerHtmlValue;
			    
			    $("#tourDate"+tourTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:false,format:'d-m-Y',formatDate:'d-m-Y',maxDate:0,disabledDates:getLeaveDates(),
			                               scrollInput:false, yearEnd : 2100,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateFromDate(3,tourTableIdlen);},
		onShow: function () {
			  $("#tourDate"+tourTableIdlen).val("");
		}
		});
		 }else{
		 	 
		     if(0==tourTableIdlen || !validTourHeaderFlag)
			 {
			 	validTourHeaderFlag = true ;
				var row0 = header.insertRow(-1);
			    	row0.className = "lablevaluebg";
			    var cell10 =  row0.insertCell(0);
		   	    	cell10.setAttribute("width","25%;");
		   	    	cell10.className = "lablevalue";
			    var cell20 =  row0.insertCell(1);
			   	    cell20.setAttribute("width","15%;");
			   	    cell20.className = "lablevalue";
			    var cell30 =  row0.insertCell(2);
			   	    cell30.setAttribute("width","15%;");
			   	    cell30.className = "lablevalue";
			    
				    cell10.innerHTML = "Number of Days";
				    cell20.innerHTML = "Amount";
				    cell30.innerHTML = "";
				    
				    row0.id = "tourHeaderId";
			
		    
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 = row.insertCell(0);
		    	cell1.setAttribute("style","white-space: nowrap;");
		    	cell1.setAttribute("width","25%;");
		    var cell2 = row.insertCell(1);
		    	cell2.setAttribute("style","white-space: nowrap;");
		   	    cell2.setAttribute("width","15%;");
		    var cell3 = row.insertCell(2);
		    	cell3.setAttribute("width","15%;");
		    	cell3.setAttribute("style","white-space: nowrap;");
		   
		   	                
			    tourTableIdlen++;
			    
		    var innerHtmlValue='';
			    innerHtmlValue='<td ><input type="text" name="tourNoOfDays" id="tourNoOfDays'+tourTableIdlen+'"  class="txtfldM" size="10" maxlength="5" onkeypress="return isNumericOnlyKey(event)" autocomplete="off"/></td>';
			    cell1.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue=innerHtmlValue+'<td ><input type="text" name="tourAmount" id="tourAmount'+tourTableIdlen+'" onkeypress="return isNumberKey(event)" onblur="getAmount(3,'+tourTableIdlen+');" class="txtfldM" size="10" maxlength="10" autocomplete="off"/><input type="hidden" name="actualTourAmount" id="actualTourAmount'+tourTableIdlen+'"/></td>';
			    cell2.innerHTML = innerHtmlValue;
			   
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<input type="hidden"  readonly="true" name="tourTableIdlen" value="'+tourTableIdlen+'" class="txtfldM" />';
			    innerHtmlValue=innerHtmlValue+'<td ><input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+tourRowDelete+','+tourTableIdlen+')"></td>';
			    cell3.innerHTML = innerHtmlValue;
			   
			 }
		 }
	  }	 
   }
   
   //changes done for vehicle transport
   
   
   
   if(detail=='taAddmore')
   {
   	   if($("#jrnyStartedFrom").val()==''){
			alert("Please enter Journey Commenced From");
			$("#jrnyStartedFrom").focus();
			return false;
		}
			
		if(validateLeaveDetails() && valideteTADetails())
		{
			
		    var table = document.getElementById("taTableGrid");
			var header = table.createTHead();
			header.className="allp";
			if(0==taTableIdlen || !validTaHeaderFlag)
			{
					validTaHeaderFlag = true ;
					var row0 = header.insertRow(-1);
				    	row0.className = "lablevaluebg";
				    var cell10 =  row0.insertCell(0);
						cell10.setAttribute("width","7%;");
						cell10.className="lablevalue";
				    var cell20 =  row0.insertCell(1);
						cell20.setAttribute("width","7%;");
						cell20.className="lablevalue";
				    var cell30 =  row0.insertCell(2);
						cell30.setAttribute("width","8%;");
						cell30.className="lablevalue";
				    var cell40 =  row0.insertCell(3);
						cell40.setAttribute("width","8%;");
						cell40.className="lablevalue";
					var cell50 =  row0.insertCell(4);
						cell50.setAttribute("width","13%;");
						cell50.className="lablevalue";
					var cell60 =  row0.insertCell(5);
						cell60.setAttribute("width","11%;");
						cell60.className="lablevalue";
				    var cell70 =  row0.insertCell(6);
						cell70.setAttribute("width","8%;");
						cell70.className="lablevalue";
					var cell80 =  row0.insertCell(7);
						cell80.setAttribute("width","10%;");
						cell80.className="lablevalue";
					var cell90 =  row0.insertCell(8);
						cell90.setAttribute("width","7%;");
						cell90.className="lablevalue";
					var cell11 =  row0.insertCell(9);
						cell11.setAttribute("width","7%;");
						cell11.className="lablevalue";
					var cell12 =  row0.insertCell(10);
						cell12.setAttribute("width","7%;");
						cell12.className="lablevalue";
					var cell13 =  row0.insertCell(11);
						cell13.setAttribute("width","7%;");	
						cell13.className="lablevalue";					   	    
		
					    cell10.innerHTML = "From";
					    cell20.innerHTML = "To";
					    cell30.innerHTML = "Travel Mode";
					    cell40.innerHTML = "Travel Class";
					    cell50.innerHTML = "Departure Date &amp; Time";
					    cell60.innerHTML = "Arrival Date &amp; Time";
					    cell70.innerHTML = "Distance by Road in Km";
					    cell80.innerHTML = "Bill/PNR/Ticket No.";
					    cell90.innerHTML = "Journey Performed?";
					    cell11.innerHTML = "Booking Amount";
					    cell12.innerHTML = "Refund Amount";
					   
					    row0.id = "taHeaderId";
					   
				}
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 =  row.insertCell(0);
		    	cell1.setAttribute("style","white-space: nowrap;");
		   	    cell1.setAttribute("width","50px;");
		    var cell2 =  row.insertCell(1);
		    	cell2.setAttribute("style","white-space: nowrap;");
		   	    cell2.setAttribute("width","50px");
		    var cell3 =  row.insertCell(2);
		    	cell3.setAttribute("style","white-space: nowrap;");
		   	    cell3.setAttribute("width","50px");
		    var cell4 =  row.insertCell(3);
		    	cell4.setAttribute("style","white-space: nowrap;");
		   	    cell4.setAttribute("width","50px");
		    var cell5 =  row.insertCell(4);
		    	cell5.setAttribute("style","white-space: nowrap;");
		   	    cell5.setAttribute("width","50px");
		    var cell6 =  row.insertCell(5);
			    cell6.setAttribute("style","white-space: nowrap;");
		   	    cell6.setAttribute("width","50px");
	   	    var cell7 =  row.insertCell(6);
			    cell7.setAttribute("style","white-space: nowrap;");
		   	    cell7.setAttribute("width","50px");
		   	var cell8 =  row.insertCell(7);
			    cell8.setAttribute("style","white-space: nowrap;");
		   	    cell8.setAttribute("width","50px"); 
		   	var cel19 =  row.insertCell(8);
			    cel19.setAttribute("style","white-space: nowrap;");
		   	    cel19.setAttribute("width","50px"); 
		   	var cel20 =  row.insertCell(9);
			    cel20.setAttribute("style","white-space: nowrap;");
		   	    cel20.setAttribute("width","50px"); 
		   	var cel21 =  row.insertCell(10);
			    cel21.setAttribute("style","white-space: nowrap;");
		   	    cel21.setAttribute("width","50px");                 
		   	var cel22 =  row.insertCell(11);
			    cel22.setAttribute("style","white-space: nowrap;");
		   	    cel22.setAttribute("width","50px");     
		   	    
		   	    taTableIdlen++;
		    var innerHtmlValue='';
		    
			    innerHtmlValue='<td><input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+taTableIdlen+'"  class="txtfldM" size="15" style="width:90px;" maxlength="50" autocomplete="off"/></td>';
			    cell1.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+taTableIdlen+'"  class="txtfldM" size="15" style="width:90px;" maxlength="50" autocomplete="off"/></td>';
			    cell2.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><select name="nonDTSModeOfTravel" id="nonDTSModeOfTravel'+taTableIdlen+'" class="combo" style="width:120px;" onchange="setClassOfTravel(this.value,'+taTableIdlen+');"><option value="">Select</option></select></td>' ;
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="airlineType" id="airlineType'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="otherAirlineType" id="otherAirlineType'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="FAA_AuthorityNo" id="FAA_AuthorityNo'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="FAA_AuthorityDate" id="FAA_AuthorityDate'+taTableIdlen+'"/>';
			    cell3.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><select name="nonDTSClassOfTravel" id="nonDTSClassOfTravel'+taTableIdlen+'" class="combo"  style="width:140px;"><option value="">Select</option></select></td>';
			    cell4.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text"   name="nonDTSJryStartDate" id="nonDTSJryStartDate'+taTableIdlen+'" size="11" style="width:85%;" class="txtfldM" readonly="true" autocomplete="off"/></td>';
			    cell5.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text" name="nonDTSJryEndDate" id="nonDTSJryEndDate'+taTableIdlen+'" class="txtfldM" style="width:95%;" size="13" maxlength="20" readonly="true" autocomplete="off"/></td>';
			    cell6.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text"   name="nonDTSDistanceKM" id="nonDTSDistanceKM'+taTableIdlen+'" size="5" maxlength="5" style="width:65px;" class="txtfldM" onkeypress="return isNumericOnlyKey(event)" autocomplete="off"/></td>';
			    cell7.innerHTML = innerHtmlValue;
			     
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text" name="nonDTSTicketNo" id="nonDTSTicketNo'+taTableIdlen+'" class="txtfldM" size="10" maxlength="20" style="width:110px;" autocomplete="off"/></td>';
			    cell8.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><select name="nonDTSJryPerformed" id="nonDTSJryPerformed'+taTableIdlen+'" class="combo" style="width:78px;" onchange="takeNonDTSCancellation(this.value,'+taTableIdlen+');"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select></td>';
			    cel19.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text" name="nonDTSJryAmount" id="nonDTSJryAmount'+taTableIdlen+'" class="txtfldM" size="8" maxlength="8" style="width:78px;" onblur="getAmount(6,'+taTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
			    cel20.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><input type="text" name="nonDTSJryRefundAmount" id="nonDTSJryRefundAmount'+taTableIdlen+'" class="txtfldM" value="0" size="8" maxlength="8" style="width:78px;" onblur="calculateFinalAmount();" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
			    cel21.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanType" id="nonDTSJrnyCanType'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanNo" id="nonDTSJrnyCanSanNo'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanDate" id="nonDTSJrnyCanSanDate'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="taTableIdlen" value="'+taTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="button"  value="Row X" class="butn" onclick="deleteCurrentRow(this,'+taRowDelete+','+taTableIdlen+')">' ;
			    cel22.innerHTML = innerHtmlValue;
			    
			    setModeOfTravel(taTableIdlen);
			    setNonDTSFromPlace();
			    
			    
			   $('#nonDTSJryStartDate'+taTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                                scrollInput:false,yearEnd : 2100 ,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateFromDate(6,taTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryStartDate'+taTableIdlen).val("");
		} });
			   $('#nonDTSJryEndDate'+taTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                                scrollInput:false,yearEnd : 2100 ,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateDate(6,taTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryEndDate'+taTableIdlen).val("");
		} });
		 }
   	}
   	
   	
   	 if(detail=='leavePeriodAddmore')
   {
		if(valideteLeavePeriodDetails())
		{
		    var table = document.getElementById("leavePeriodTableGrid");
			var header = table.createTHead();
			header.className="allp";
			if(0==leaveTableIdlen || !validLeaveHeaderFlag)
			{
					validLeaveHeaderFlag = true ;
					var row0 = header.insertRow(-1);
				    	row0.className = "lablevaluebg";
				    var cell10 =  row0.insertCell(0);
						cell10.setAttribute("width","50%;");
						cell10.className="lablevalue";
				    var cell20 =  row0.insertCell(1);
						cell20.setAttribute("width","50px;");
						cell20.className="lablevalue";
						cell20.setAttribute("colspan","2");
				    
					    cell10.innerHTML = "Leave Date";
					    cell20.innerHTML = "Full/Half";
					    
					    row0.id = "leaveHeaderId";
					   
				}
		    var row = table.insertRow(-1);
		    	row.className = "lablevaluebg";
		    var cell1 =  row.insertCell(0);
		    	cell1.setAttribute("style","white-space: nowrap;");
		   	    cell1.setAttribute("width","50px;");
		    var cell2 =  row.insertCell(1);
		    	cell2.setAttribute("style","white-space: nowrap;");
		   	    cell2.setAttribute("width","50px");
		    var cell3 =  row.insertCell(2);
		    	cell3.setAttribute("style","white-space: nowrap;");
		   	    cell3.setAttribute("width","50px");
		        
		   	    leaveTableIdlen++;
		   	    
		    var innerHtmlValue='';
		    
			    innerHtmlValue='<td><input type="text"   id="leavePeriodDate'+leaveTableIdlen+'" size="11" class="txtfldM" style="width:200px" readonly="true" autocomplete="off"/></td>';
			    cell1.innerHTML = innerHtmlValue;
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue='<td><select id="leaveFullHalf'+leaveTableIdlen+'" class="combo" style="width:90px;" onchange="setLeaveFullHalfInfo(this.value,'+leaveTableIdlen+');"><option value="">Select</option><option value="0">Full</option><option value="1">Half</option></select></td>' ;
			    cell2.innerHTML = innerHtmlValue;
			    
			    
			    innerHtmlValue = '' ;
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="leavePeriodDate" id="leavePeriodDate_'+leaveTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="leaveFullHalf" id="leaveFullHalf_'+leaveTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="leaveTableIdlen" value="'+leaveTableIdlen+'"/>';
			    innerHtmlValue=innerHtmlValue+'<input type="button"  value="Row X" class="butn leave_disabled_row" onclick="deleteCurrentRow(this,'+leaveRowDelete+','+leaveTableIdlen+')">' ;
			    cell3.innerHTML = innerHtmlValue;
			    
			   $('#leavePeriodDate'+leaveTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y',formatDate:'d-m-Y',maxDate:0,disabledDates:getSelfLeave(),
			                                                   scrollInput:false,yearEnd : 2100, defaultDate:$("#travel_Start_Date").val(),
		onShow: function () {
			  $('#leavePeriodDate'+leaveTableIdlen).val("");
		}});
			  
		 }
   	}
   	
   
}


function calculateFoodAmount(index){
	
	var jryStartDate=$("#start_date_time").val();
	var taTableIdlen = $("#voucherSettlementConfirmationForm input[name=nonDTSJryEndDate]");
	var maxFoodAmt=$("#maxFoodAmt").val();
	var maxRationAmt=$("#maxRationAmt").val();
	
	if(taTableIdlen.length==0){
		alert("Please add Non DTS Journey Details.") ;
		$("#foodNoDay"+index).val("");
		return false ;
	}
	
	var lastDayDate=taTableIdlen[taTableIdlen.length-1].value;
	
	if(jryStartDate==''){
		alert("Please enter date of start journey.") ;
		$("#foodNoDay"+index).val("");
		return false ;
	}
	if(lastDayDate==''){
		alert("Please enter Arrival Date of Non DTS Journey.") ;
		$("#foodNoDay"+index).val("");
		return false ;
	}
	
	var totalJryDay = calculateNumberOfDays(jryStartDate,lastDayDate);
	var foodNoDay=$("#foodNoDay"+index).val();
	
	if(totalJryDay<0){
		alert("Journey start date must be grater then last Non DTS Journey.") ;
		$("#foodNoDay"+index).val("");
		return false ;
	}
	
	if(Number(foodNoDay)>Number(totalJryDay)){
		alert("Number of Days for food cannot be greater than "+Number(totalJryDay)) ;
		$("#foodNoDay"+index).val("");
		return false ;
	}
	
	var startTime=$("#start_date_time").val().split(' ')[1].replace(':','');
	var lastDayTime=lastDayDate.split(' ')[1].replace(':','');
	
	var firstDayHour=2400-Number(startTime);
	var lastDayHour=Number(lastDayTime);
	
	var firstDayFoodAmount=0;
	var lastDayFoodAmount=0;
	var daysFoodAmount=0;
	var rationMoney=0;
	
	if(firstDayHour<600){
		firstDayFoodAmount=parseInt((maxFoodAmt*30)/100);
	}else if(firstDayHour >= 600 && firstDayHour <= 1200){
		firstDayFoodAmount=parseInt((maxFoodAmt*70)/100);
	}else if(firstDayHour>1200){
		firstDayFoodAmount=parseInt(maxFoodAmt);
	}
	
	if(lastDayHour<600){
		lastDayFoodAmount=parseInt((maxFoodAmt*30)/100);
	}else if(lastDayHour >= 600 && lastDayHour <= 1200){
		lastDayFoodAmount=parseInt((maxFoodAmt*70)/100);
	}else if(lastDayHour>1200){
		lastDayFoodAmount=parseInt(maxFoodAmt);
	}
	
	if(totalJryDay>2){
		daysFoodAmount=Number(maxFoodAmt)*Number(totalJryDay-2);
	}
	
	if($("#nonDrawlCertificate"+index).val()==1){
		rationMoney=Number(maxRationAmt)*Number(totalJryDay);
}

	$("#actualFoodAmount"+index).val(parseInt(firstDayFoodAmount+daysFoodAmount+lastDayFoodAmount-rationMoney));
	
}

function validateFoodAmount(index){
	
	if(!valideteFoodDetails()){$("#foodAmount"+index).val("");return false;}
	var actualFoodAmount=$("#actualFoodAmount"+index).val();
	var foodAmount=$("#foodAmount"+index).val();
	
	if(Number(foodAmount)>Number(actualFoodAmount)){
		alert("Maximum Eligible Food Amount= "+Number(actualFoodAmount)) ;
	}
	
	calculateFinalAmount();
}

function validateHotelDays(index){
	
	var hotelCheckIn = $("#checkInDate"+index).val();
	
	if(hotelCheckIn==''){
		alert("Please Select CheckIn Date.") ;
		return false ;
	}
	
	var hotelcheckOut = $("#checkOutDate"+index).val();
	
	if(hotelcheckOut==''){
		alert("Please Select CheckOut Date.") ;
		return false ;
	}
	
	var day = calculateNumberOfDays(hotelCheckIn,hotelcheckOut);
	var noOfDay=$("#hotelNoDays"+index).val();
	if(Number(noOfDay)>Number(day)){
		alert("Number of Days can not be grater then "+Number(day)) ;
		$("#hotelNoDays"+index).val("")
		return false ;
	}
			
}

function deleteCurrentRow(obj,table_row,table_row_id)
{
	var delRow = obj.parentNode.parentNode;
	var tbl = delRow.parentNode.parentNode;
	var rIndex = delRow.sectionRowIndex;
	var rowArray = new Array(delRow);
	 
	for (var i=0; i<rowArray.length; i++) 
	{
		var rIndex = rowArray[i].sectionRowIndex;
		rowArray[i].parentNode.deleteRow(rIndex);
	}
	
	if('hotelRowDelete'==table_row)
	{
		var hotelAmountArray = document.getElementsByName('hotelAmount');
		 if(0==hotelAmountArray.length)
			{
				$('#hotelHeaderId').remove();
				validHotelHeaderFlag = false;
			}
			addHotelCertifyQuestion();
	}
	
	if('foodRowDelete'==table_row)
	{
	        
		 var foodAmountArray= document.getElementsByName('foodAmount');
		  if(0==foodAmountArray.length)
			 {
			 	$('#foodHeaderId').remove();
			 	validFoodHeaderFlag = false;
			 }
	}
	
	if('tourRowDelete'==table_row)
	{
		 var tourAmountArray = document.getElementsByName('tourAmount');
		    if(0==tourAmountArray.length)
			{
				$('#tourHeaderId').remove();
				validTourHeaderFlag = false;
			}
	}
	
	if('taRowDelete'==table_row)
	{
		 var taAmountArray = document.getElementsByName('nonDTSJryAmount');
	     	
			if(0==taAmountArray.length)
			{	
				$('#taHeaderId').remove();
				validTaHeaderFlag = false;
			}else{
				setNonDTSFromPlace();
			} 
		
	}
	
	if('leaveRowDelete'==table_row)
	{
		 var leaveArray = document.getElementsByName('leavePeriodDate');
	     	
			if(0==leaveArray.length)
			{	
				$('#leaveHeaderId').remove();
				validLeaveHeaderFlag = false;
			} 
		
	}
	
	calculateFinalAmount();
	
}

function calculateFinalAmount(){
	
	var taTableIdlen = $("#voucherSettlementConfirmationForm input[name=taTableIdlen]");
	var hotelTableIdlen = $("#voucherSettlementConfirmationForm input[name=hotelTableIdlen]");
	var foodTableIdlen = $("#voucherSettlementConfirmationForm input[name=foodTableIdlen]");
	var tourTableIdlen = $("#voucherSettlementConfirmationForm input[name=tourTableIdlen]");
	var dtsJourneyDate = $("#voucherSettlementConfirmationForm input[name=dtsJourneyDate]");
	
	
	var totalBookingAmount=0;
	var totalRefundAmount=0;
	var totalActualTaAmount=0;
	var totalActualHotelAmount=0;
	var totalActualFoodAmount=0;
	var totalRationMoneyAmount=0;
	var totalActualTourAmount=0;
	
	var totalClaimedTaAmount=0;
	var totalClaimedTaRefundAmount=0;
	var totalClaimedHotelAmount=0;
	var totalClaimedFoodAmount=0;
	var totalClaimedRationMoneyAmount=0;
	var totalClaimedTourAmount=0;
	var totalClaimedGSTHotelAmount=0;
	
	for(var index=0;index<dtsJourneyDate.length;index++){
		totalBookingAmount=totalBookingAmount+Number($("#dtsJourneyBKGAmount"+index).val());
		totalRefundAmount=totalRefundAmount+Number($("#dtsJourneyRefAmount"+index).val());
	}
	
	for(var index=0;index<taTableIdlen.length;index++){
		var val=taTableIdlen[index].value;
		totalActualTaAmount=totalActualTaAmount+Number($("#actualTaAmount"+val).val());
		totalClaimedTaAmount=totalClaimedTaAmount+Number($("#nonDTSJryAmount"+val).val());
		totalClaimedTaRefundAmount=totalClaimedTaRefundAmount+Number($("#nonDTSJryRefundAmount"+val).val());
	}
	
	for(var index=0;index<hotelTableIdlen.length;index++){
		var val=hotelTableIdlen[index].value;
		totalActualHotelAmount=totalActualHotelAmount+Number($("#actualHotelAmount"+val).val());
		totalClaimedHotelAmount=totalClaimedHotelAmount+Number($("#hotelAmount"+val).val());
		totalClaimedGSTHotelAmount=totalClaimedGSTHotelAmount+Number($("#hotelGSTAmount"+val).val());
	}
	
	for(var index=0;index<foodTableIdlen.length;index++){
		var val=foodTableIdlen[index].value;
		totalActualFoodAmount=totalActualFoodAmount+Number($("#actualFoodAmount"+val).val());
		totalRationMoneyAmount=totalRationMoneyAmount+Number($("#rationMoneyAmount"+val).val());
		totalClaimedFoodAmount=totalClaimedFoodAmount+Number($("#foodAmount"+val).val());
		totalClaimedRationMoneyAmount=totalClaimedRationMoneyAmount+Number($("#rationMoneyAmount"+val).val());
		
	}
	
	for(var index=0;index<tourTableIdlen.length;index++){
		var val=tourTableIdlen[index].value;
		totalActualTourAmount=totalActualTourAmount+Number($("#actualTourAmount"+val).val());
		totalClaimedTourAmount=totalClaimedTourAmount+Number($("#tourAmount"+val).val());
	}

	var recoveryAmount = 0;
	if ($("#irlaRecoveryAmt").length != 0) {
		recoveryAmount = Number($("#irlaRecoveryAmt").val());
	}
	
	var totalSpentAmount=Number(totalBookingAmount)+Number(totalClaimedTaAmount)+Number(totalClaimedHotelAmount)+Number(totalClaimedGSTHotelAmount)+Number(totalClaimedFoodAmount)+
	                     Number(totalClaimedTourAmount)-Number(totalRefundAmount)-Number(totalClaimedTaRefundAmount);
	
	var totalAdvanceTaken=Number(totalBookingAmount)+Number($("#daAdvanceAmount").val())+Number($("#Adv_From_PAO").val())+Number($("#Adv_From_Other").val())
						  -Number(totalRefundAmount);
	                      
	var totalMROAmount=Number($("#Advance_MRO").val());

	var totalClaimedAmount=Number(totalSpentAmount)-Number(totalAdvanceTaken)+Number(totalMROAmount)+Number(recoveryAmount);

	$("#total_spent_amount").html(parseInt(totalSpentAmount));
	$("#total_advance_amount").html(parseInt(totalAdvanceTaken));
	$("#total_refund_amount").html(parseInt(totalMROAmount));
	$("#total_claimed_amount").html(parseInt(totalClaimedAmount));
	
	$("#totalSpentAmount").val(parseInt(totalSpentAmount));
	$("#totalAdvanceAmount").val(parseInt(totalAdvanceTaken));
	$("#totalRefundAmount").val(parseInt(totalMROAmount));
	$("#totalClaimedAmount").val(parseInt(totalClaimedAmount));
}

function getAmount(detail,index)
{
    var totalClaimAmountSum = 0 ;
	var totalEligibleAmountSum = 0 ;
	var totalAdvanceAmount = 0 ;
	var totalDueAmount = 0 ;
	var innerAmountHtmlValue='' ;
	if(detail==1)
	{
		  var flag =valideteHotelDetailsByIndex(index);
		  if(!flag){$("#hotelAmount"+index).val("");}
		  $("#actualHotelAmount"+index).val(Number($("#hotelDailyAmount"+index).val())*Number($("#hotelNoDays"+index).val())); 
	      
	      if(parseInt($("#hotelAmount"+index).val())>parseInt($("#actualHotelAmount"+index).val())){
	      	alert("Maximum Eligible Hotel Amount="+$("#actualHotelAmount"+index).val());
	      }
	      if(parseInt($("#hotelAmount"+index).val())>0){
	      	addHotelCertifyQuestion();
	      }
             
	}
	if(detail==3)
	{
		var flag =valideteTourDetails();
		if(!flag){$("#tourAmount"+index).val("");}
		 if($("#is_conveyanceDtls").val()==0){
		 	$("#actualTourAmount"+index).val(Number($("#maxTourAmt").val()));
		 }else{
	    $("#actualTourAmount"+index).val(Number($("#maxTourAmt").val())*Number($("#tourNoOfDays"+index).val()));
		 }
	    
	    if(parseInt($("#tourAmount"+index).val())>parseInt($("#actualTourAmount"+index).val())){
	    	alert("Maximum Eligible Taxi Charges="+$("#actualTourAmount"+index).val());
	    }
	}
	
	if(detail==6)
	{
		var flag =valideteTADetails();
		if(!flag){$("#nonDTSJryAmount"+index).val("");}
		$("#actualTaAmount"+index).val(Number($("#nonDTSJryAmount"+index).val()));
	}
	
	
	calculateFinalAmount()
   	
   	
}

function validateUpdateClaimDetails(travelType){
	
	var flag=false;
	
	if(travelType=='100001'){
		flag=validatePTClaimForm();
	}else if(travelType=='100002'){
		flag=validateTDClaimForm();
	}else if(travelType=='100005' || travelType=='100006' || travelType=='100007' || travelType=='100008'){
		flag=validateLTCClaimForm();
	}
	
	if(flag){
		var calHeight=document.body.offsetHeight+20;
		$("#screen-freeze").css({"height":calHeight + "px"} );
		$("#screen-freeze").css("display", "block");
		
		$("#voucherSettlementConfirmationForm").submit();
		
	}else{
		tadaClaimFormForEdit();
	}
	
}

function validateTDClaimForm(){
	
	if($("#moveSanctionNo").val().trim()==''){
		alert("Please enter Move Sanction No");
		return false;
	}
	
	if($("#move_Sanction_Date").val().trim()==''){
		alert("Please select Move Sanction Date");
		return false;
	}
	
	if($("#jrnyStartedFrom").val().trim()==''){
		alert("Please enter Journey Commenced From");
		return false;
	}
	
	if($("#start_date_time").val().trim()==''){
		alert("Please select Journey Commenced Date");
		return false;
	}
	
	var dtsJourneyDate = $("#voucherSettlementConfirmationForm input[name=dtsJourneyDate]");
	
	for(var index=0;index<dtsJourneyDate.length;index++){
		if(dtsJourneyDate[index].value==''){
		   alert("Please select DTS Journey Start Date");
		   return false;
		}
		
		if($("#journey_end_date_"+index).val().trim()==''){
			alert("Please select DTS Journey End Date");
		    return false;
		}
		
		if($("#dtsJourneyPerformed"+index).val()==''){
			alert("Please select DTS Journey Performed");
		    return false;
		}
		
	}
	
	if(!valideteLeavePeriodDetails()){return false;}
	if(!valideteTADetails()){return false;}
	if(!valideteHotelDetails()){return false;}
    if(!valideteFoodDetails()){return false;}
    if(!valideteTourDetails()){return false;}
    
    if($("#Adv_From_PAO").val().trim()==''){
    	alert("Please enter Advance Drawn from PAO");
		return false;
    }
    
    if(parseInt($("#Adv_From_PAO").val())>0){
	    if($("#Adv_From_PAO_Date").val().trim()==''){
	    	alert("Please enter Date of Advance Drawn from PAO");
			return false;
	    }
    }
    
     if($("#Adv_From_Other").val().trim()==''){
    	alert("Please enter Advance Drawn from Other Sources");
		return false;
    }
    
    if(parseInt($("#Adv_From_Other").val())>0){
    	
    	if($("#Adv_From_Other_VNo").val().trim()==''){
    	alert("Please enter Voucher No for Advance Drawn from Other Sources");
		return false;
       }
    
	    if($("#Adv_From_Other_Date").val().trim()==''){
	    	alert("Please enter Date of Advance Drawn from Other Sources");
			return false;
	    }
    	
    }
    
     if($("#Advance_MRO").val().trim()==''){
    	alert("Please enter MRO/Refund");
		return false;
    }
    
    if(parseInt($("#Advance_MRO").val())>0){
    	
    	if($("#MRO_Number").val().trim()==''){
    	alert("Please enter eMRO/MRO/Refund No");
		return false;
       }
    
	    if($("#MRO_Date").val().trim()==''){
	    	alert("Please enter eMRO/MRO Submission Date/Refund Date");
			return false;
	    }
	    
    }
    
    var flag=true;
    
    $('input:checkbox[name=claimCertify]').each(function(){
             if($(this).is(':checked')){}
             else{
             	alert("Please check on all the checkboxes in Certify that Tab.");
		        flag= false;
		        return false;
             }
    });
    
    if(flag){
    	
    	if($("#userServiceType").val()=='1'){
    		
    	}else{
    	if($("#counterPersonalNo").val().trim()==''){
    	alert("Please enter Counter Singing Authority Personal No");
		return false;
        }
    	}
        if($("#claimPreferredDate").val().trim()==''){
    	alert("Please enter Date of Claim preferred by the Individual to the Office");
		return false;
        }
        if($("#claimPreferredSanction").val().trim()==''){
    	alert("Please Select Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken");
		return false;
        }
        
        if($("#claimPreferredSanction").val().trim()=='Yes'){
        	if($("#claimPreferredSanctionNo").val().trim()==''){
	    	alert("Please enter Claim preferred Sanction No");
			return false;
	        }
	        if($("#claimPreferredSanctionDate").val().trim()==''){
	    	alert("Please enter Date of Claim preferred Sanction");
			return false;
	        }
        }
    }
     
	
	return flag;
} 

function calculateNumberOfDays(dateFrom,dateTo)
{			
			var dateFromFormat = '' ;
			var dateToFormat = '' ;
			
			var fromDateFormat =dateFrom.split(' ') ;
			var toDateFormat = dateTo.split(' ') ;
			
			if(fromDateFormat.length > 0 && toDateFormat.length > 0)
			{
				dateFromFormat = fromDateFormat[0].split('-');
				dateToFormat = toDateFormat[0].split('-');
			}
			
			var newDateFromFormat = new Date(dateFromFormat[1]+' '+ dateFromFormat[0] +' '+dateFromFormat[2]);
	  		var dateFromMonth = newDateFromFormat.getMonth();
	  		var dateFromDay   = newDateFromFormat.getDate();
	  		var dateFromYear  = newDateFromFormat.getFullYear();
	  		
	  		var newDateFrom = new Date(dateFromYear,dateFromMonth,dateFromDay,0,0,0)

 			var newDateToFormat = new Date(dateToFormat[1]+' '+ dateToFormat[0] +' '+dateToFormat[2]);
 			var dateToMonth = newDateToFormat.getMonth();
  			var dateToDay   = newDateToFormat.getDate();
  			var dateToYear  = newDateToFormat.getFullYear();

			var newDateTo = new Date(dateToYear,dateToMonth,dateToDay,0,0,0);

			var one_day = 1000*60*60*24;
	  		var date1_ms = newDateTo.getTime();
	  		var date2_ms = newDateFrom.getTime();
	  		var difference_ms = date1_ms - date2_ms ;
	    	var diff = Math.round(difference_ms/one_day); 
	    	
	return diff+1 ;
}

function parseXML( xml ) {
	if( window.ActiveXObject && window.GetObject ) {
	var dom = new ActiveXObject( 'Microsoft.XMLDOM' );
	dom.loadXML( xml );
	return dom;
	}
	if( window.DOMParser )
	return new DOMParser().parseFromString( xml, 'text/xml' );
	throw new Error( 'No XML parser available' );
}	




var validHotelDateTo = '' ;
var validFoodDateTo = '' ;
var validTourDateTo = '' ;
var validTaDateTo = '' ;
var validDtsJryDateTo = '' ;

function validateDate(type,index)
{
    var dateFrom = "" ;
	var dateTo = "" ;
	var dateOnwardToTime = '' ;
	var dateReturnToTime = '' ;
 		
	if(1==type)
	{
	 	dateFrom = document.getElementById("checkInDate"+index).value;
	 	dateTo = document.getElementById("checkOutDate"+index).value;
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select check In date first.");
	 		document.getElementById("checkOutDate"+index).value='';
	 		document.getElementById("hotelNoDays"+index).value = '' ;
	 	}
	 	 
		 dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	 
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Check Out date cannot be less than check In date.");
	 		document.getElementById("checkOutDate"+index).value='';
	 		document.getElementById("hotelNoDays"+index).value = '' ;
	 		return false;
	 	}
	 	
	 		document.getElementById("hotelNoDays"+index).value = dateOnwardToTime ;
	 		validHotelDateTo = document.getElementById("checkOutDate"+index).value ;
	         
	 }
	 
	 
	dateFrom = "" ;
	dateTo = "" ;
	dateOnwardToTime = '' ;
    dateReturnToTime = '' ;
    
    if(2==type)
	{
	 	dateFrom = document.getElementById("journey_date_"+index).value;
	 	dateTo = document.getElementById("journey_end_date_"+index).value;
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select Journey Start Date first.");
	 		document.getElementById("journey_end_date_"+index).value='';
	 	}
	 	 
		 dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	 
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Departure date cannot be less than from date.");
	 		document.getElementById("journey_end_date_"+index).value='';
	 	}
	 	
	 		validDtsJryDateTo = document.getElementById("journey_end_date_"+index).value ;
	 }
	
	
	dateFrom = "" ;
	dateTo = "" ;
	dateOnwardToTime = '' ;
    dateReturnToTime = '' ;
    
    if(6==type)
	{
	 	dateFrom = document.getElementById("nonDTSJryStartDate"+index).value;
	 	dateTo = document.getElementById("nonDTSJryEndDate"+index).value;
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select departure date first.");
	 		document.getElementById("nonDTSJryEndDate"+index).value='';
	 		document.getElementById("nonDTSJryStartDate"+index).focus();
	 	}
	 	 
		 dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Journey End Date cannot be less than Journey Start Date.");
	 		document.getElementById("nonDTSJryEndDate"+index).value='';
	 	}
	 	
	 		validTaDateTo = document.getElementById("nonDTSJryEndDate"+index).value ;
	 }
  
	
}

function validateFromDate(type,index)
{
     var dateFrom = "" ;
	 var dateTo = "" ;
	 
	if(1==type)
	{
		dateTo = document.getElementById("checkOutDate"+index).value;
		dateFrom = document.getElementById("checkInDate"+index).value;
	 	
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Check In Date cannot be greater than to Check Out Date.")
				document.getElementById("checkInDate"+index).value='';
				return false;
			}	
			document.getElementById("hotelNoDays"+index).value = calculateNumberOfDays(dateFrom,dateTo) ;
	 	}
	 	
	 	var hotelDateArray = document.getElementsByName('checkInDate');
	 	if(hotelDateArray.length>1)
	 	{
		 	if(undefined!=validHotelDateTo && dateFrom!='')
		 	{
		 		if(calculateNumberOfDays(dateFrom,validHotelDateTo)>1)
				{
					alert("CheckIn date cannot be less than from previous checkOut date.")
					document.getElementById("checkInDate"+index).value='';
					return false;
				}
		 	}
		 }
	 	
	 }
	 
	dateFrom = "" ;
	dateTo = "" ;
	
	if(2==type)
	{
		dateFrom = document.getElementById("journey_date_"+index).value;
		dateTo = document.getElementById("journey_end_date_"+index).value;
		
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Journey Start Date cannot be greater than to Journey End Date.")
				document.getElementById("journey_date_"+index).value='';
				return false;
			}	
	 	}
	 	
	 	var taDateArray = document.getElementsByName('dtsJourneyDate');
	 	if(taDateArray.length>1)
	 	{
		 	if(undefined!=validTaDateTo && dateFrom!='')
		 	{
		 		if(calculateNumberOfDays(dateFrom,validTaDateTo)>1)
				{
					alert("Journey Start Date cannot be less than from previous Journey End Date.")
					document.getElementById("journey_date_"+index).value='';
					return false;
				}
		 	}
		 }
	 	
	 }
	
	dateFrom = "" ;
	dateTo = "" ;
	
	if(6==type)
	{
		dateFrom = document.getElementById("nonDTSJryStartDate"+index).value;
		dateTo = document.getElementById("nonDTSJryEndDate"+index).value;
		
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Arrival Date cannot be greater than to Departure Date.")
				document.getElementById("nonDTSJryStartDate"+index).value='';
				return false;
			}	
	 	}
	 	
	 	var taDateArray = document.getElementsByName('nonDTSJryStartDate');
	 	if(taDateArray.length>1)
	 	{
		 	if(undefined!=validTaDateTo && dateFrom!='')
		 	{
		 		if(calculateNumberOfDays(dateFrom,validTaDateTo)>1)
				{
					alert("Arrived date cannot be less than from previous departure date.")
					document.getElementById("nonDTSJryStartDate"+index).value='';
					return false;
				}
		 	}
		 }
	 	
	 }
	
}

function valideteHotelDetailsByIndex(index) {
	if ($("#hotelOrMess" + index).val() == "") {
			alert("Please Select Hotel/Mess.") ;
			return false ;
	}
	if ($("#hotelName" + index).val() == "") {
			alert("Please Enter Hotel/MESS Name.") ;
			$("#hotelName" + index).focus();
			return false ;
	}
	if ($("#hotelCity" + index).val() == "") {
			alert("Please Enter Hotel location.") ;
			$("#hotelCity" + index).focus();
			return false ;
	}
	if($("#hotelGSTNo" + index).val() == "") {
			alert("Please Enter Hotel GST No.") ;
			$("#hotelCity" + index).focus();
			return false ;
	}
	if($("#checkInDate" + index).val() == "") {
			alert("Please Select Check In Date.") ;
			$("#checkInDate" + index).focus();
			return false ;
	}
	if($("#checkOutDate" + index).val() == "") {
			alert("Please Select Check Out Date.") ;
			$("#checkOutDate" + index).focus();
			return false ;
	}
	if($("#hotelNoDays" + index).val() == "") {
			alert("Please Enter Number of Days.") ;
			$("#hotelNoDays" + index).focus();
			return false ;
	}
	if($("#hotelAmount" + index).val() == "") {
			alert("Please Enter Hotel Amount.") ;
			$("#hotelAmount" + index).focus();
			return false ;
	}
	if($("#hotelGSTAmount" + index).val() == "") {
			alert("Please Enter Hotel GST Amount.") ;
			$("#hotelGSTAmount" + index).focus();
			return false ;
	}
	return true;
}

function valideteHotelDetails(){
	
	    var hotelOrMess = $("#voucherSettlementConfirmationForm select[name=hotelOrMess]");
	   if(hotelOrMess.length==0)
		{
			return true ;
		}
		else{
			
		if(hotelOrMess.length > 0)
		{
			for(var i=0;i<hotelOrMess.length;i++){
				if(hotelOrMess[i].value==''){
					alert("Please Select Hotel/Mess.") ;
					return false ;
				}
			}
		}
		
		var hotelName = $("#voucherSettlementConfirmationForm input[name=hotelName]");	
			
			if(hotelName.length > 0){
				for(var i=0;i<hotelName.length;i++){
					if(hotelName[i].value==''){
						alert("Please Enter Hotel/MESS Name.") ;
						hotelName[i].focus();
						return false ;
					}
				}
			}

		var hotelCity = $("#voucherSettlementConfirmationForm input[name=hotelCity]");
				
				if(hotelCity.length > 0){
					for(var i=0;i<hotelCity.length;i++){
						if(hotelCity[i].value==''){
							alert("Please Enter Hotel location.") ;
							hotelCity[i].focus();
							return false ;
						}
					}
				}			
			
		    var checkInDate = $("#voucherSettlementConfirmationForm input[name=checkInDate]");		
			
			if(checkInDate.length >0 ){
				for(var i=0;i<checkInDate.length;i++){
					if(checkInDate[i].value==''){
						alert("Please Select Check In Date.") ;
						checkInDate[i].focus();
						return false ;
					}
				}
			}
			
			var checkOutDate = $("#voucherSettlementConfirmationForm input[name=checkOutDate]");		
			
			if(checkOutDate.length >0 ){
				for(var i=0;i<checkOutDate.length;i++){
					if(checkOutDate[i].value==''){
						alert("Please Select Check Out Date.") ;
						checkOutDate[i].focus();
						return false ;
					}
				}
			}
			
			 var hotelNoDaysAlert = $("#voucherSettlementConfirmationForm input[name=hotelNoDays]");	
			
			if(hotelNoDaysAlert.length >0 ){
				for(var i=0;i<hotelNoDaysAlert.length;i++){
					if(hotelNoDaysAlert[i].value==''){
						alert("Please Enter Number of Days.") ;
						hotelNoDaysAlert[i].focus();
						return false ;
					}
				}
			}
		
	    	var hotelAmountAlert = $("#voucherSettlementConfirmationForm input[name=hotelAmount]");		
			
			if(hotelAmountAlert.length > 0){
				for(var i=0;i<hotelAmountAlert.length;i++){
					if(hotelAmountAlert[i].value==''){
						alert("Please Enter Hotel Amount.") ;
						hotelAmountAlert[i].focus();
						return false ;
					}
				}
			}
		
		return true ;
	}
}

function valideteFoodDetails()
{
    
    if($("#userServiceType").val()=='1'){
    	
    	var foodNoDay = $("#voucherSettlementConfirmationForm input[name=foodNoDay]");
	
		if(foodNoDay.length==0)
		{
			return true ;
		}
		else
		{
			
			if(foodNoDay.length>0){
				for(var i=0;i<foodNoDay.length;i++){
					if(foodNoDay[i].value==''){
						alert("Please Enter Food Number of Days") ;
						foodNoDay[i].focus();
						return false ;
					}
				}
			}
		    
		 
		   var foodAmount = $("#voucherSettlementConfirmationForm input[name=foodAmount]");	
		
		  if(foodAmount.length>0){
			for(var i=0;i<foodAmount.length;i++){
				if(foodAmount[i].value==''){
						alert("Please Enter Food Amount.") ;
						foodAmount[i].focus();
						return false ;
					}
				}
		  }
		  
		  	
		return true ;
	  }
    }else{
	var nonDrawlCertificate = $("#voucherSettlementConfirmationForm select[name=nonDrawlCertificate]"); 
	
		if(nonDrawlCertificate.length==0)
		{
			return true ;
		}
		else
		{
			if(nonDrawlCertificate.length > 0){
				for(var i=0;i<nonDrawlCertificate.length;i++){
					if(nonDrawlCertificate[i].value==''){
						alert("Please select Ration Certificate status.") ;
						return false ;
					}
				}
			}
			
			var foodNoDay = $("#voucherSettlementConfirmationForm input[name=foodNoDay]");
			
			if(foodNoDay.length>0){
				for(var i=0;i<foodNoDay.length;i++){
					if(foodNoDay[i].value==''){
						alert("Please Enter Food Number of Days") ;
						foodNoDay[i].focus();
						return false ;
					}
				}
			}
		    
		 
		   var foodAmount = $("#voucherSettlementConfirmationForm input[name=foodAmount]");	
		
		  if(foodAmount.length>0){
			for(var i=0;i<foodAmount.length;i++){
				if(foodAmount[i].value==''){
						alert("Please Enter Food Amount.") ;
						foodAmount[i].focus();
						return false ;
					}
				}
		  }
		  
		  	
		return true ;
	}
}
}

function valideteTourDetails()
{
	
	 if($("#is_conveyanceDtls").val()==0){ 
	 	
	 	var tourVehicleNoAlert = $("#voucherSettlementConfirmationForm input[name=tourVehicleNo]");
	
		if(tourVehicleNoAlert.length==0)
			{
				return true ;
		}
		else
		{
			 
			if(tourVehicleNoAlert.length>0){
				for(var i=0;i<tourVehicleNoAlert.length;i++){
					if(tourVehicleNoAlert[i].value==''){
						alert("Please Enter Vehicle Number.") ;
						tourVehicleNoAlert[i].focus();
						return false ;
					}
				}
			}
		var tourDateAlert = $("#voucherSettlementConfirmationForm input[name=tourDate]");	
				
				if(tourDateAlert.length>0){
					for(var i=0;i<tourDateAlert.length;i++){
						if(tourDateAlert[i].value==''){
							alert("Please Select Tour Date.") ;
							tourDateAlert[i].focus();
							return false ;
						}
					}
				}
				
		var tourDistanceAlert = $("#voucherSettlementConfirmationForm input[name=tourDistance]");	
				
				if(tourDistanceAlert.length>0){
					for(var i=0;i<tourDistanceAlert.length;i++){
						if(tourDistanceAlert[i].value==''){
							alert("Please Select Tour Distance.") ;
							tourDistanceAlert[i].focus();
							return false ;
						}
					}
				}
				
		var tourBillNoAlert = $("#voucherSettlementConfirmationForm input[name=tourBillNo]");	
				
				if(tourBillNoAlert.length>0){
					for(var i=0;i<tourBillNoAlert.length;i++){
						if(tourBillNoAlert[i].value==''){
							alert("Please Select Tour Bill number") ;
							tourBillNoAlert[i].focus();
							return false ;
						}
					}
				}				
		
		var tourAmountAlert = $("#voucherSettlementConfirmationForm input[name=tourAmount]");	
		
				if(tourAmountAlert.length>0){
					for(var i=0;i<tourAmountAlert.length;i++){
						if(tourAmountAlert[i].value==''){
							alert("Please Enter Conveyance Amount.") ;
							tourAmountAlert[i].focus();
							return false ;
						}
					}
				}
			
			return true ;
		}
	 	
	 }else{
	 	
	 	var tourNoOfDays = $("#voucherSettlementConfirmationForm input[name=tourNoOfDays]");
	
		if(tourNoOfDays.length==0)
			{
				return true ;
			}
		else
		{
			 
			if(tourNoOfDays.length>0){
				for(var i=0;i<tourNoOfDays.length;i++){
					if(tourNoOfDays[i].value==''){
						alert("Please Enter Taxi Charges Number of Day.") ;
						tourNoOfDays[i].focus();
						return false ;
					}
				}
			}
		
		var tourAmountAlert = $("#voucherSettlementConfirmationForm input[name=tourAmount]");	
		
				if(tourAmountAlert.length>0){
					for(var i=0;i<tourAmountAlert.length;i++){
						if(tourAmountAlert[i].value==''){
							alert("Please Enter Conveyance Amount.") ;
							tourAmountAlert[i].focus();
							return false ;
						}
					}
				}
			
			return true ;
		}
	 	
	 }    
	
}


function valideteTADetails()
{  
	    
		var nonDTSFromPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSFromPlace]");
		if(nonDTSFromPlace.length==0)
		{
			return true ;
		}
		else
		{
			
			if(nonDTSFromPlace.length>0){
				for(var i=0;i<nonDTSFromPlace.length;i++){
					if(nonDTSFromPlace[i].value==''){
						alert("Please Enter Name of From Place.") ;
						nonDTSFromPlace[i].focus();
						return false ;
					}
				}
			}
			
			var nonDTSToPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSToPlace]");
			if(nonDTSToPlace.length>0){
				for(var i=0;i<nonDTSToPlace.length;i++){
					if(nonDTSToPlace[i].value==''){
						alert("Please Enter Name of To Place.") ;
						nonDTSToPlace[i].focus();
						return false ;
					}
				}
			}
			
			
			var modeOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSModeOfTravel]");
			if(modeOfTravel.length>0){
				for(var i=0;i<modeOfTravel.length;i++){
					if(modeOfTravel[i].value==''){
						alert("Please select mode of travel.") ;
						return false ;
					}
				}
			}
			
			var classOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSClassOfTravel]");
			if(classOfTravel.length>0){
				for(var i=0;i<classOfTravel.length;i++){
					if(classOfTravel[i].value==''){
						alert("Please select class of travel.") ;
						return false ;
					}
				}
			}
			
		    var nonDTSJryStartDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryStartDate]");
			if(nonDTSJryStartDate.length>0){
				for(var i=0;i<nonDTSJryStartDate.length;i++){
					if(nonDTSJryStartDate[i].value==''){
						alert("Please select Journey Start Date.") ;
						nonDTSJryStartDate[i].focus();
						return false ;
					}
				}
			}
			
			var nonDTSJryEndDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryEndDate]");
			if(nonDTSJryEndDate.length>0){
				for(var i=0;i<nonDTSJryEndDate.length;i++){
					if(nonDTSJryEndDate[i].value==''){
						alert("Please select Journey End Date.") ;
						nonDTSJryEndDate[i].focus();
						return false ;
					}	
				}
			}
			
			var nonDTSDistanceKM = $("#voucherSettlementConfirmationForm input[name=nonDTSDistanceKM]");
			if(nonDTSDistanceKM.length>0){
				for(var i=0;i<nonDTSDistanceKM.length;i++){
					if(nonDTSDistanceKM[i].value==''){
						alert("Please Enter journey distance.") ;
						nonDTSDistanceKM[i].focus();
						return false ;
					}
				}
			}
			
			var nonDTSTicketNo = $("#voucherSettlementConfirmationForm input[name=nonDTSTicketNo]");
			if(nonDTSTicketNo.length>0){
				for(var i=0;i<nonDTSTicketNo.length;i++){
					if(modeOfTravel[i]=='Flight' || modeOfTravel[i]=='Train' || modeOfTravel[i]=='Taxi'){
					if(nonDTSTicketNo[i].value==''){
						alert("Please Enter Ticket Number.") ;
						nonDTSTicketNo[i].focus();
						return false ;
					}
				}
			}
			}
			
			var nonDTSJryPerformed = $("#voucherSettlementConfirmationForm select[name=nonDTSJryPerformed]");
			if(nonDTSJryPerformed.length>0){
				for(var i=0;i<nonDTSJryPerformed.length;i++){
					if(nonDTSJryPerformed[i].value==''){
						alert("Please select Journey Performed.") ;
						return false ;
					}
				}
			}
			
			var nonDTSJryAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSJryAmount]");
			if(nonDTSJryAmount.length>0){
				for(var i=0;i<nonDTSJryAmount.length;i++){
					if(nonDTSJryAmount[i].value==''){
						alert("Please Enter journey amount.") ;
						nonDTSJryAmount[i].focus();
						return false ;
					}
				}
			}
			
		return true ;
	 }
}

function valideteLeavePeriodDetails(){
	    
		var leavePeriodDate = $("#voucherSettlementConfirmationForm input[name=leavePeriodDate]");
		if(leavePeriodDate.length==0)
		{
			return true ;
		}
		else
		{
			
			if(leavePeriodDate.length>0){
				for(var i=0;i<leavePeriodDate.length;i++){
					if(leavePeriodDate[i].value==''){
						alert("Please select Leave Date.") ;
						return false ;
					}
				}
			}
			
		    var leaveFullHalf = $("#voucherSettlementConfirmationForm input[name=leaveFullHalf]");
		   if(leaveFullHalf.length>0){
				for(var i=0;i<leaveFullHalf.length;i++){
					if(leaveFullHalf[i].value==''){
						alert("Please select leave type.") ;
						return false ;
					}
				}
			}
			
		return true ;
	 }
}

function fetchCityList(index,obj) 
	{     
   	      var city=obj.value;
	       //alert(index+"\test==="+city );
		  if(city.length>2)
		   {
			var response="";
			var cityList="";
		    $.ajax({
			url: $("#context_path").val() + "mb/getCityList",
			type: "post",
			data: "city=" + city,
			datatype: "application/json",
			success: function(msg) {
				$.each(msg, function(i, ob) {
//				$(msg).find('citySeq').each(function() {
					var name = ob.cityName;
					var cityGradeStr = ob.cityGradeStr;
					var cityGradeInt = ob.cityGradeInt;

					if (name == "City Name Not Exist")
						cityList += '<li>' + name + '</li>';
					else
						cityList += '<li onClick="fillCity(\'' + name + '\',\'' + cityGradeInt + '\',\'' + index + '\')">' + name + '</li>';
				});
				$("#autoSuggestionsListforDestCity" + index).html(cityList);
				$('#suggestionsDestCity' + index).show();
			}
		});
	  }else{
	         $("#autoSuggestionsListforDestCity"+index).html("");		
		     $('#suggestionsDestCity'+index).hide();
	  }
	}

function getCounterPersonalInfo(){
	
	 $.ajax({
		url: $("#context_path").val() + "mb/counterPersonalInfo",
		type: "post",
		data: "userAlias=" + $("#counterPersonalNo").val(),
		datatype: "application/json",
		      beforeSend: function() 
		      	{
		        	var calHeight=document.body.offsetHeight+20;
			        $("#screen-freeze").css({"height":calHeight + "px"} );
			        $("#screen-freeze").css("display", "block");
			    },
  			 
  			  	complete: function(){
  			  		$("#screen-freeze").css("display", "none");
			  	},
		success: function(msg) {
//			var msg = parseXML(msg1);
			if (msg.flag == 'true') {
				var pName = msg.name;
				var pLevel = msg.levelName;
				$("#counter_personal_name").html(pName);
				$("#counter_personal_level").html(pLevel);
				$("#counterPersonalName").val(pName);
				$("#counterPersonalLevel").val(pLevel);
			} else {
				$("#counterPersonalNo").val("");
				$("#counter_personal_name").html("");
				$("#counter_personal_level").html("");
				$("#counterPersonalName").val("");
				$("#counterPersonalLevel").val("");
				alert(msg.message);
			}
		}
		 });
}

function addHotelCertifyQuestion(){
	
	 var hotelName = $("#voucherSettlementConfirmationForm input[name=hotelName]");	
	 var checkInDate = $("#voucherSettlementConfirmationForm input[name=checkInDate]");
	 var checkOutDate = $("#voucherSettlementConfirmationForm input[name=checkOutDate]");
	 var hotelCertifyQues=" I stayed from ";
	 
	   if(hotelName.length > 0){
	   	 var flag=true;
				for(var i=0;i<hotelName.length;i++){
					if(hotelName[i].value!='' && checkInDate[i].value!='' && checkOutDate[i].value!=''){
						if(flag){
							flag=false;
					    	hotelCertifyQues=hotelCertifyQues+checkInDate[i].value+" to "+checkOutDate[i].value+" in "+hotelName[i].value	;
						}else{
							hotelCertifyQues=hotelCertifyQues+" and "+checkInDate[i].value+" to "+checkOutDate[i].value+" in "+hotelName[i].value;
						}
					}
				}
				hotelCertifyQues=hotelCertifyQues+" which provided boarding and lodging at schedule tariff.";
				if($("#dy_Certify_Ques").length==0){
				$('#certify_that_tb tbody').append('<tr id="dy_Certify_Ques"><td width="5%"><input type="checkbox" name="claimCertify" value="'+hotelCertifyQues+'" /></td>' +
			    '<td width="95%">'+hotelCertifyQues+'</td></tr>');
			}else{
					$('#dy_Certify_Ques').html('<td width="5%"><input type="checkbox" name="claimCertify" value="'+hotelCertifyQues+'" /></td>' +
				    '<td width="95%">'+hotelCertifyQues+'</td>');
				}
			}else{
				$('#dy_Certify_Ques').remove();
			}
	
}

function toJSDate( dateTime ) {

var dateTime = dateTime.split(" ");

var date = dateTime[0].split("-");
var time = dateTime[1].split(":");

//(year, month, day, hours, minutes, seconds, milliseconds)
//subtract 1 from month because Jan is 0 and Dec is 11
return new Date(date[2], (date[1]-1), date[0], time[0], time[1], 0, 0);

}

function getLeaveDates(){
	
	var leaveDates=new Array();
	var leavePeriodDate = $("#voucherSettlementConfirmationForm input[name=leavePeriodDate]");
	var leaveFullHalf = $("#voucherSettlementConfirmationForm input[name=leaveFullHalf]");		
	if(leavePeriodDate.length>0){
		var index=0;
		for(var i=0;i<leavePeriodDate.length;i++){
			if(leavePeriodDate[i].value!='' && leaveFullHalf[i].value=='0'){
				leaveDates[index]=leavePeriodDate[i].value;
				index++;
			}
		}
	}
	return '['+leaveDates+']';
}

function getSelfLeave(){
	
	var leaveDates=new Array();
	var leavePeriodDate = $("#voucherSettlementConfirmationForm input[name=leavePeriodDate]");
	if(leavePeriodDate.length>0){
		var index=0;
		for(var i=0;i<leavePeriodDate.length;i++){
			if(leavePeriodDate[i].value!=''){
				leaveDates[index]=leavePeriodDate[i].value;
				index++;
			}
		}
	}
	return '['+leaveDates+']';
}

function validateLeaveDetails(){
	
	if(!$("#leave_period_td").is(":hidden")){
	var leavePeriodDate = $("#voucherSettlementConfirmationForm input[name=leavePeriodDate]");
	if(leavePeriodDate.length==0){
		if(confirm("You have not entered any leave details. Are you sure that you don't want to add leave details?")){
			$("#leave_period_td").hide();
			return true;
		}else{
			return false;
	    }
	}else{
		if(confirm("You can not edit leave details?")){
			var leaveTableIdlen = $("#voucherSettlementConfirmationForm input[name=leaveTableIdlen]");
			if(leaveTableIdlen.length>0){
				for(var i=0;i<leaveTableIdlen.length;i++){
					$("#leavePeriodDate"+leaveTableIdlen[i].value).prop("disabled", true);
					$("#leaveFullHalf"+leaveTableIdlen[i].value).prop("disabled", true);
			   }
			}
			
			$("#leave_period_td").hide();
			$(".leave_disabled_row").prop("disabled", true);
			return true;
		   }
	}
  }else{
  	return true;
  }
}

function setLeaveFullHalfInfo(val,index){
	if($("#leavePeriodDate"+index).val()==''){
		alert("Please select Leave Date");
		$("#leavePeriodDate"+index).focus();
		$("#leaveFullHalf"+index).val("");
		return false;
	}
	$("#leavePeriodDate_"+index).val($("#leavePeriodDate"+index).val());
	$("#leaveFullHalf_"+index).val(val);
}

function setNonDTSFromPlace(){
	var taTableIdlen = $("#voucherSettlementConfirmationForm input[name=taTableIdlen]");
	$("#nonDTSFromPlace"+taTableIdlen[0].value).val($("#jrnyStartedFrom").val());
	$("#nonDTSFromPlace"+taTableIdlen[0].value).prop("readonly", true);
	
}

function displayUploadDocDetails(travelType){
	
	var flag=false;
		
	if(travelType=='100001'){
		flag=validatePTClaimForm();
	}	
	if(travelType=='100002'){
		flag=validateTDClaimForm();
	}
	if(travelType=='100005' || travelType=='100006' || travelType=='100007' || travelType=='100008'){
		flag=validateLTCClaimForm();
	}
	
	if(flag){
		$("#claim_documents_form").show();
		$("#claim_input_form").hide();
		$("#save_claim_form").show();
		$("#upload_claim_doc").hide();
	}
}

function ajaxUploadFile(fillName) {
   if ($('#'+fillName).get(0).files.length != 0) {
  
    var data = new FormData();
	var file = $('#'+fillName)[0].files[0];
	var filename = $('#'+fillName).get(0).files[0].name;
	if(filename.includes(' ')){
    		  alert("Space is not allowed in File Name. Kindly re-name file")
    		  event.preventDefault();
    		  return false;
    		  }


	var fileSize = $('#'+fillName).get(0).files[0].size;
	var checkExt = filename.split('.');
 
    if (checkExt[checkExt.length-1] == 'pdf') {
	   var MAX_FILE_SIZE = 8 * 1024 * 1024; // 8MB
		if (fileSize > MAX_FILE_SIZE) {
	             alert("File size must not exceed 8 MB !!!");
	             return false;
	        }else{
		        data.append('file', file);
		        
		          $.ajax({
					type: "POST",
					encType: "multipart/form-data",
					url: $("#context_path").val() + 'com/uplaodFile',
	                cache : false,
	                processData : false,
	                contentType : false,
	                data : data,
					success: function(msg) {
						if (msg != '') {
							$('#' + fillName + 'View').attr("href", $("#context_path").val() + 'com/downloadFile?dwnFilePath=' + msg + '');
							$('#' + fillName + 'View').html('<img src="' + $("#context_path").val() + 'images/pdfImage.png" style="width:25px;height:25px" alt="">');
							$('#' + fillName + 'File').val(msg);
						} else {
							alert("Unable to upload file. Please try again.");
						}

					},
	                error : function(msg) {
	                    alert("Couldn't upload file");
	                }
	            });
	        }
	      }else {
	        alert("Invalid File Type. Kindly upload valid PDF File only!!!")
	       }
	     return false;
   }else{
   	 alert("Please select pdf file for upload");
   }
  
 } 
 
 function validatePAOSaveDetails(travelType){
 	
 	if(travelType=='100002'){
 	if(!validateAuditorPAOEditForm()){return false;}
 	}else if(travelType=='100005' || travelType=='100006' || travelType=='100007' || travelType=='100008'){
 		if(!validateAuditorLTCPAOEditForm()){return false;}
 	}else if(travelType=='100001'){
 		if(!validateAuditorPTPAOEditForm()){return false;}
 	}
 	
 	var calHeight=document.body.offsetHeight+20;
	$("#screen-freeze").css({"height":calHeight + "px"} );
	$("#screen-freeze").css("display", "block");
		
	$("#claimEditPAOForm").submit();
		
 }
 
 function validatePAOEditForm(type,index){
 	
 	var flag=validatePassedAmountData(type,index);
 	
 	calculatePAOEditAmount();
 	return flag;
 }
 
 function validateAuditorPAOEditForm(){
 	
 	var flag=validatePAOEditData();
 	
 	calculatePAOEditAmount();
 	return flag;
 }
 
 
 function validatePAOEditData(){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	var hotelBillSeq = $("#claimEditPAOForm input[name=hotelBillSeq]");
	var foodBillSeq = $("#claimEditPAOForm input[name=foodBillSeq]");
	var localJrySeq = $("#claimEditPAOForm input[name=localJrySeq]");
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var deductionReason=$("#JrnyDeductionReason"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		var jrnyBKGFrom=$("#jrnyBKGFrom"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(jrnyBKGFrom=='NON-DTS' && parseInt(sanAmount)!=parseInt(actualTaAmount) && deductionReason==''){
			alert("Please Select Journey Details Reason for Deduction");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if($("#userServiceType").val()=='1'){
			var isPaymentMade=$("#isPaymentMade"+val).val();
			if(isPaymentMade == 'undefined' || isPaymentMade == null ){}
			else{
				if(isPaymentMade == ''){
					alert("Please Select Journey Details Payment Made Option.");
					return false;
				}
				var jrnyPaymentTo=$("#jrnyPaymentTo"+val).val();
				if(jrnyPaymentTo == ''){
					alert("Please Select Journey Details Payment To Option.");
					return false;
				}
			}
		}
	}
	
	for(var index=0;index<hotelBillSeq.length;index++){
		var val=hotelBillSeq[index].value;
		var sanAmount=$("#hotelBillSanAmount"+val).val();
		var deductionReason=$("#hotelDeductionReason"+val).val();
		var actualTaAmount=$("#actualHotelAmount"+val).val();
		var claimAmount=$("#hotelBillAmount"+val).val();
		var gstAmount=$("#hotelGSTAmount"+val).val();
		var eligibleAmount=0;
		
		claimAmount=parseInt(claimAmount)+parseInt(gstAmount);
		
		if(sanAmount==''){
			alert("Hotel Bill Passed Amount can not be blank");
			$("#hotelBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Hotel Bill Passed Amount can not greater than "+eligibleAmount);
			$("#hotelBillSanAmount"+val).val(0);
			$("#hotelBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount) != parseInt(claimAmount) && deductionReason==''){
			alert("Please Select Hotel Bill Reason for Deduction");
			$("#hotelBillSanAmount"+val).focus();
			return false;
		}
	}
	
	for(var index=0;index<foodBillSeq.length;index++){
		var val=foodBillSeq[index].value;
		var sanAmount=$("#foodBillSanAmount"+val).val();
		var deductionReason=$("#foodDeductionReason"+val).val();
		var actualTaAmount=$("#actualFoodAmount"+val).val();
		var claimAmount=$("#foodBillAmount"+val).val();
		var eligibleAmount=0;
		
		if(sanAmount==''){
			alert("Food Bill Passed Amount can not be blank");
			$("#foodBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Food Bill Passed Amount can not greater than "+eligibleAmount);
			$("#foodBillSanAmount"+val).val(0);
			$("#foodBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount) != parseInt(claimAmount) && deductionReason==''){
			alert("Please Select Food Bill Reason for Deduction");
			$("#foodBillSanAmount"+val).focus();
			return false;
		}
	}
	
	for(var index=0;index<localJrySeq.length;index++){
		var val=localJrySeq[index].value;
		var sanAmount=$("#localJrySanAmount"+val).val();
		var deductionReason=$("#localConDeductionReason"+val).val();
		var actualTaAmount=$("#actualTourAmount"+val).val();
		var claimAmount=$("#tourBillAmount"+val).val();
		var eligibleAmount=0;
		
		if(sanAmount==''){
			alert("Taxi Charges Passed Amount can not be blank");
			$("#localJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Food Bill Passed Amount can not greater than "+eligibleAmount);
			$("#localJrySanAmount"+val).val(0);
			$("#localJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount) != parseInt(claimAmount) && deductionReason==''){
			alert("Please Select Taxi Charges Reason for Deduction");
			$("#localJrySanAmount"+val).focus();
			return false;
		}
		
	}
	
	if($("#sanPAOAdvanceAmt").val()==''){
		alert("Advance Drawn from PAO Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanPAOAdvanceAmt").val()) != parseInt($("#paoAdvanceAmount").val()) && $("#paoAdvDeductionReason").val()==''){
		alert("Please Select Advance Drawn from PAO Reason for Deduction");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if($("#sanOtherAdvanceAmt").val()==''){
		alert("Advance Drawn from Other Sources Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanOtherAdvanceAmt").val()) != parseInt($("#otherAdvanceAmount").val()) && $("#otherAdvDeductionReason").val()==''){
		alert("Please Select Advance Drawn from Other Sources Reason for Deduction");
		$("#sanOtherAdvanceAmt").focus();
		return false;
	}
	
	if($("#isMROReceived").val()==''){
		alert("Please Select MRO Received");
		return false;
	}
	
	if($("#sanMRORefundAmt").val()==''){
		alert("eMRO/MRO/ Refund Passed Amount can not be blank");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
		alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
		$("#sanMRORefundAmt").val(0);
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val()) != parseInt($("#mroRefundAmount").val()) && $("#mroRefundDeductionReason").val()==''){
		alert("Please Select eMRO/MRO/ Refund Amount Reason for Deduction");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if($("#other_Deduction").val()==''){
		alert("Any other Deduction from Officer can not be blank");
		$("#other_Deduction").focus();
		return false;
	}
	
	if(parseInt($("#other_Deduction").val())>0 && $("#other_DeductionReason").val()==''){
		alert("Please enter the reason for other deduction from officer");
		$("#other_DeductionReason").focus();
		return false;
	}
	
	return true;
 }
 
 function validatePassedAmountData(type,index){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	var hotelBillSeq = $("#claimEditPAOForm input[name=hotelBillSeq]");
	var foodBillSeq = $("#claimEditPAOForm input[name=foodBillSeq]");
	var localJrySeq = $("#claimEditPAOForm input[name=localJrySeq]");
	
	var taLength=0;
	var hotelLength=0;
	var foodLength=0;
	var localLength=0;
	
	if(type=='TAJrny'){
		taLength=parseInt(index);
	}
	if(type=='hotel'){
		hotelLength=parseInt(index);
		taLength=tadaJrySeq.length;
	}
	if(type=='food'){
		foodLength=parseInt(index);
		taLength=tadaJrySeq.length;
		hotelLength=hotelBillSeq.length
	}
	if(type=='local'){
		localLength=parseInt(index);
		taLength=tadaJrySeq.length;
		hotelLength=hotelBillSeq.length
		foodLength=foodBillSeq.length;
	}
	
	if(index==''){
		localLength=localJrySeq.length;
		taLength=tadaJrySeq.length;
		hotelLength=hotelBillSeq.length
		foodLength=foodBillSeq.length;
	}
	
	for(var index=0;index<taLength;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
	}
	
	for(var index=0;index<hotelLength;index++){
		var val=hotelBillSeq[index].value;
		var sanAmount=$("#hotelBillSanAmount"+val).val();
		var actualTaAmount=$("#actualHotelAmount"+val).val();
		var claimAmount=$("#hotelBillAmount"+val).val();
		var gstAmount=$("#hotelGSTAmount"+val).val();
		
		claimAmount=parseInt(claimAmount)+parseInt(gstAmount);
		
		var eligibleAmount=0;
		
		if(sanAmount==''){
			alert("Hotel Bill Passed Amount can not be blank");
			$("#hotelBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Hotel Bill Passed Amount can not greater than "+eligibleAmount);
			$("#hotelBillSanAmount"+val).val(0);
			$("#hotelBillSanAmount"+val).focus();
			return false;
		}
		
	}
	
	for(var index=0;index<foodLength;index++){
		var val=foodBillSeq[index].value;
		var sanAmount=$("#foodBillSanAmount"+val).val();
		var actualTaAmount=$("#actualFoodAmount"+val).val();
		var claimAmount=$("#foodBillAmount"+val).val();
		var eligibleAmount=0;
		
		if(sanAmount==''){
			alert("Food Bill Passed Amount can not be blank");
			$("#foodBillSanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Food Bill Passed Amount can not greater than "+eligibleAmount);
			$("#foodBillSanAmount"+val).val(0);
			$("#foodBillSanAmount"+val).focus();
			return false;
		}
		
	}
	
	for(var index=0;index<localLength;index++){
		var val=localJrySeq[index].value;
		var sanAmount=$("#localJrySanAmount"+val).val();
		var actualTaAmount=$("#actualTourAmount"+val).val();
		var claimAmount=$("#tourBillAmount"+val).val();
		var eligibleAmount=0;
		
		if(sanAmount==''){
			alert("Taxi Charges Passed Amount can not be blank");
			$("#localJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(claimAmount)>parseInt(actualTaAmount)){
			eligibleAmount=actualTaAmount;
		}else{
			eligibleAmount=claimAmount;
		}
		
		if(parseInt(sanAmount)>parseInt(eligibleAmount)){
			alert("Food Bill Passed Amount can not greater than "+eligibleAmount);
			$("#localJrySanAmount"+val).val(0);
			$("#localJrySanAmount"+val).focus();
			return false;
		}
		
	}
	
	if(type=='paoAdv'){
	if($("#sanPAOAdvanceAmt").val()==''){
		alert("Advance Drawn from PAO Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	}
	
	if(type=='otherAdv'){
	if($("#sanOtherAdvanceAmt").val()==''){
		alert("Advance Drawn from Other Sources Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	}
	
	if(type=='MRORef'){
	if($("#isMROReceived").val()==''){
		alert("Please Select MRO Received");
		return false;
	}
	
	if($("#sanMRORefundAmt").val()==''){
		alert("eMRO/MRO/ Refund Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
		alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
		$("#sanMRORefundAmt").val(0);
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	}
	
	if(type=='recovery'){
		
		if($("#recoveryPassAmt").val()==''){
			alert("Recovery Passed Amount can not be blank");
			$("#recoveryPassAmt").focus();
			return false;
		}
		
		var totalRecovery=Number($("#recoveryAmt").val())+Number($("#irlaRecoveryAmt").val());
		
		if(Number($("#recoveryPassAmt").val())>Number(totalRecovery)){
			alert("Recovery Passed Amount can not greater than "+totalRecovery);
			$("#recoveryPassAmt").val(0);
			$("#recoveryPassAmt").focus();
			return false;
		}
	
	}
	
	return true;
 }
 
 function calculatePAOEditAmount(){
	
	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	var hotelBillSeq = $("#claimEditPAOForm input[name=hotelBillSeq]");
	var foodBillSeq = $("#claimEditPAOForm input[name=foodBillSeq]");
	var localJrySeq = $("#claimEditPAOForm input[name=localJrySeq]");
	
	var totalDTSBookingAmount=0;
	var totalNonDTSBookingAmount=0;
	var totalClaimedHotelAmount=0;
	var totalClaimedFoodAmount=0;
	var totalClaimedTourAmount=0;
	var totalClaimedHotelGSTAmount=0;
	var journeyAmount=0;
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		if($("#jrnyBKGFrom"+val).val()=='DTS'){
			totalDTSBookingAmount=totalDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}else{
			totalNonDTSBookingAmount=totalNonDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}
		
		if($('#jrnyPaymentTo'+val).length){
			if($('#jrnyPaymentTo'+val).val()!='Officer'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}else{
			if($("#jrnyBKGFrom"+val).val()=='DTS'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}
	}
	
	for(var index=0;index<hotelBillSeq.length;index++){
		var val=hotelBillSeq[index].value;
		totalClaimedHotelAmount=totalClaimedHotelAmount+Number($("#hotelBillSanAmount"+val).val());
		totalClaimedHotelGSTAmount=totalClaimedHotelGSTAmount+Number($("#hotelGSTAmount"+val).val());
	}
	
	for(var index=0;index<foodBillSeq.length;index++){
		var val=foodBillSeq[index].value;
		totalClaimedFoodAmount=totalClaimedFoodAmount+Number($("#foodBillSanAmount"+val).val());
		
	}
	
	for(var index=0;index<localJrySeq.length;index++){
		var val=localJrySeq[index].value;
		totalClaimedTourAmount=totalClaimedTourAmount+Number($("#localJrySanAmount"+val).val());
		
	}
	
	var otherDeduction= Number($("#other_Deduction").val());
	
	var recoveryPassAmt= Number($("#recoveryPassAmt").val());
	
	var totalApprovedAmount=Number(totalDTSBookingAmount)+Number(totalNonDTSBookingAmount)+Number(totalClaimedHotelAmount)
	                       +Number(totalClaimedFoodAmount)+Number(totalClaimedTourAmount)-Number(otherDeduction);
	
	var totalAdvanceApproved=Number(journeyAmount)+Number($("#sanPAOAdvanceAmt").val())+Number($("#sanOtherAdvanceAmt").val())+
	                         Number($("#daAdvanceAmount").val());
	                         
	var totalRefundApproved= Number($("#sanMRORefundAmt").val());
	
	var totalPassedAmount= Number(totalApprovedAmount)+Number(totalRefundApproved)-Number(totalAdvanceApproved)+Number(recoveryPassAmt);                       
	
	$("#total_approved_amount").html(parseInt(totalApprovedAmount));
	$("#total_advance_approved").html(parseInt(totalAdvanceApproved));
	$("#total_refund_approved").html(parseInt(totalRefundApproved));
	$("#total_passed_amount").html(parseInt(totalPassedAmount));
	
	$("#totalApprovedAmount").val(parseInt(totalApprovedAmount));
	$("#totalAdvanceApprovedAmount").val(parseInt(totalAdvanceApproved));
	$("#totalRefundsApprovedAmount").val(parseInt(totalRefundApproved));
	$("#totalPassedAmount").val(parseInt(totalPassedAmount));
	
}


function validateTDUploadedDocument(){
	
	var fileTypeName = $("#voucherSettlementConfirmationForm input[name=fileTypeName]");
	var fileTypePath = $("#voucherSettlementConfirmationForm input[name=fileTypePath]");
	
	if(fileTypeName.length>0){
		for(var i=0;i<fileTypeName.length;i++){
			
			if(fileTypeName[i].value=='ClaimDoc2' && fileTypePath[i].value==''){
				
				var dtsJrnyCanType = $("#voucherSettlementConfirmationForm input[name=dtsJrnyCanType]");
				var nonDTSJrnyCanType = $("#voucherSettlementConfirmationForm input[name=nonDTSJrnyCanType]");
				var flag=false;
				
				if(dtsJrnyCanType.length > 0){
					for(var j=0;j<dtsJrnyCanType.length;j++){
						if(dtsJrnyCanType[j].value=='1'){
							flag=true;
							break;
						}
					}
				}
				
				if(!flag && nonDTSJrnyCanType.length > 0){
					for(var j=0;j<nonDTSJrnyCanType.length;j++){
						if(nonDTSJrnyCanType[j].value=='1'){
							flag=true;
							break;
						}
					}
				}
				
				if(flag){
					if(!confirm("You have cancelled a ticket on official ground and have not uploaded the official cancellation authority document. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
					  return false;
				     }
				}
				
				
			}else if(fileTypeName[i].value=='ClaimDoc3' && fileTypePath[i].value==''){
				
				var FAA_AuthorityNo = $("#voucherSettlementConfirmationForm input[name=FAA_AuthorityNo]");
				var flag=false;
				
				if(FAA_AuthorityNo.length > 0){
					for(var j=0;j<FAA_AuthorityNo.length;j++){
						if(FAA_AuthorityNo[j].value!=''){
							flag=true;
							break;
						}
					}
				}
				if(!flag && $("#is_FABooking").val()=='true'){
					flag=true;
				}
				
				if(flag){
					if(!confirm("You have booked a flight under Approval from Competent Authority and have not uploaded the documents of the approval. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
					  return false;
				     }
				}
				
				
			}else if(fileTypeName[i].value=='ClaimDoc4' && fileTypePath[i].value==''){
				
				var nonDTSFromPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSFromPlace]");
				if(nonDTSFromPlace.length > 0){
					if(!confirm("You have claimed amount for Non-DTS journeys and have not uploaded the ticket/bill for the same. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
					  return false;
				     }
				}
				
			}else if(fileTypeName[i].value=='ClaimDoc8' && fileTypePath[i].value==''){
				
				var nonDrawlCertificate = $("#voucherSettlementConfirmationForm select[name=nonDrawlCertificate]");
				
				if(nonDrawlCertificate.length > 0 && nonDrawlCertificate[0].value=='0'){
					if(!confirm("You have selected Non drawl of Ration Certificate taken from Quarter Master as 'Yes' and have not uploaded the certificate. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
						return false;
					}
				}
				
			}else if(fileTypeName[i].value=='ClaimDoc10' && fileTypePath[i].value==''){
				
				var paoAmount=$("#Adv_From_PAO").val();
				var otherAmount=$("#Adv_From_Other").val();
				
				if(parseInt(paoAmount)>0 || parseInt(otherAmount)>0){
					if(!confirm("You have taken advance from PAO/Other source and have not uploaded the document for the same. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
						return false;
					}
				}
				
			}else if(fileTypeName[i].value=='ClaimDoc11' && fileTypePath[i].value==''){
				
				var mroAmount=$("#Advance_MRO").val();
				if(parseInt(mroAmount)>0){
					if(!confirm("You have entered the details of MRO/eMRO but have not uploaded the documents for the same. This may result in deduction of amounts at PAO level. Do you want to proceed?")){
						return false;
					}
				}
			}else if(fileTypeName[i].value=='ClaimDoc12' && fileTypePath[i].value==''){
				
				var totalClaimedAmount=0;

				var particularClaimAmt = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_0]");
				for(var index=0; index<particularClaimAmt.length; index++){
					totalClaimedAmount=totalClaimedAmount+Number(particularClaimAmt[index].value);
				}
				
				var particularClaimAmt_1 = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_1]");
				for(var index=0; index<particularClaimAmt_1.length; index++){
					totalClaimedAmount=totalClaimedAmount+Number(particularClaimAmt_1[index].value);
				}
				
				if(parseInt(totalClaimedAmount) > 0){
					alert("You have not uploaded the receipt voucher in support of payment made for transporting luggage and other conveyance. Please upload the receipt.");
						return false;
					}
				}
			}
	   }
	return true;		
}

function previewTADAClaimForm(travelType){

var flag=validateTDUploadedDocument();

if(flag){
		
	var innerHTML='';
	
  if(travelType=='100002'){
		
	 innerHTML=innerHTML+previewTravelerDetails();

	 innerHTML=innerHTML+'<table class="filtersearch" style="margin-bottom:2px"><tbody class="all1">';
	 innerHTML=innerHTML+'<tr class="tadaHeading"><td align="left"><font color="#fff"> <b>Expense Details </b></font></td></tr></tbody></table>';
	 
	 var leaveTableIdlen = $("#voucherSettlementConfirmationForm input[name=leaveTableIdlen]");
	 if(leaveTableIdlen.length>0){
	 innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	 innerHTML=innerHTML+'<tr class="tadaHeading"><td>C. Leave during TD Period</td></tr></tbody></table>';
	 innerHTML=innerHTML+'<table  class="filtersearch"><tbody class="all1">';
	 innerHTML=innerHTML+'<tr class="lablevaluebg"><td class="lablevalue" style="width:15%;">Leave Date</td><td class="lablevalue" style="width:10%;">Full/Half</td></tr>';
	 for(var i=0;i<leaveTableIdlen.length;i++){
		 var val=leaveTableIdlen[i].value;    
	  innerHTML=innerHTML+'<tr><td>'+$("#leavePeriodDate"+val).val()+'</td><td>'+$("#leaveFullHalf"+val+" option:selected").text()+'</td></tr>';
			 
	 }	  
		innerHTML=innerHTML+'</tbody></table></div>';
	}
	
	var taTableIdlen = $("#voucherSettlementConfirmationForm input[name=taTableIdlen]");
	if(taTableIdlen.length>0){
		
		 innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	 	 innerHTML=innerHTML+'<tr class="tadaHeading"><td>D. Non DTS Journey Details (Other than Local Travel in Tab G)</td></tr></tbody></table>';
		 innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	     innerHTML=innerHTML+'<tr class="lablevaluebg"><td class="lablevalue" style="width:7%;">From</td><td class="lablevalue" style="width:7%;">To</td><td class="lablevalue" style="width:8%;">';
	     innerHTML=innerHTML+'Travel Mode</td><td class="lablevalue" style="width:8%;">Travel Class</td><td class="lablevalue" style="width:13%;">Departure Date &amp; Time</td>';
	     innerHTML=innerHTML+'<td class="lablevalue" style="width:11%;">Arrival Date &amp; Time</td><td class="lablevalue" style="width:8%;">Distance by Road in Km</td><td class="lablevalue" style="width:10%;">';
	     innerHTML=innerHTML+'Bill/PNR/Ticket No.</td><td class="lablevalue" style="width:7%;">Journey Performed?</td><td class="lablevalue" style="width:7%;">Booking Amount</td>';
	     innerHTML=innerHTML+'<td class="lablevalue" style="width:7%;">Refund Amount</td></tr>';
		
	 	for(var i=0;i<taTableIdlen.length;i++){
		  var val=taTableIdlen[i].value;  
		   
		   innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#nonDTSFromPlace"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSToPlace"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSModeOfTravel"+val+" option:selected").text()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSClassOfTravel"+val+" option:selected").text()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSJryStartDate"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSJryEndDate"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSDistanceKM"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSTicketNo"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSJryPerformed"+val+" option:selected").text()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSJryAmount"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#nonDTSJryRefundAmount"+val).val()+'</td></tr>';
	     
		  }
		 innerHTML=innerHTML+'</tbody></table></div>';
	}
	
	var hotelTableIdlen = $("#voucherSettlementConfirmationForm input[name=hotelTableIdlen]");
	if(hotelTableIdlen.length>0){
		
		innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	    innerHTML=innerHTML+'<tr class="tadaHeading"><td>E. Hotel Bill Details </td></tr></tbody></table>';
	    innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
		innerHTML=innerHTML+'<tr class="lablevaluebg"><td class="lablevalue" style="width:10%;">Hotel/Mess</td><td class="lablevalue" style="width:10%;">Hotel/MESS Name</td>';
		innerHTML=innerHTML+'<td class="lablevalue" style="width:12%;">Hotel/MESS location</td><td class="lablevalue" style="width:10%;">Hotel GST No</td><td class="lablevalue" style="width:10%;">';
		innerHTML=innerHTML+'Check In Date</td><td class="lablevalue" style="width:110px;">Check Out Date</td><td class="lablevalue" style="width:10%;">Number of Days</td>';
		innerHTML=innerHTML+'<td class="lablevalue" style="width:13%;">Hotel Bill (Excluding GST)</td><td class="lablevalue" style="width:10%;">GST Amt</td></tr>';
		
		for(var i=0;i<hotelTableIdlen.length;i++){
		  var val=hotelTableIdlen[i].value; 
		   
		   innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#hotelOrMess"+val+" option:selected").text()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelName"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelCity"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelGSTNo"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#checkInDate"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#checkOutDate"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelNoDays"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelAmount"+val).val()+'</td>';
		   innerHTML=innerHTML+'<td>'+$("#hotelGSTAmount"+val).val()+'</td></tr>';
		  
		}
		 innerHTML=innerHTML+'</tbody></table></div>';
	}	
	
	var foodTableIdlen = $("#voucherSettlementConfirmationForm input[name=foodTableIdlen]"); 
	if(foodTableIdlen.length>0){
		
		innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	    innerHTML=innerHTML+'<tr class="tadaHeading"><td>F. Food Bill Details </td></tr></tbody></table>';
	    innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
		innerHTML=innerHTML+'<tr class="lablevaluebg"><td class="lablevalue" style="width:50%;">';
		
		if($("#userServiceType").val()!='1'){
			
		innerHTML=innerHTML+'Is the Non-drawl for Ration Certificate issued by the Quarter Master of the Parent Unit ?</td>';
		}
		innerHTML=innerHTML+'<td class="lablevalue" style="width:20%;">Number of Days</td><td class="lablevalue" style="width:20%;">Amount</td></tr>';
		
		for(var i=0;i<foodTableIdlen.length;i++){
		  var val=foodTableIdlen[i].value; 
		  
		  innerHTML=innerHTML+'<tr class="lablevaluebg"><td width="50%;">'+$("#nonDrawlCertificate"+val+" option:selected").text()+'</td>';
		  innerHTML=innerHTML+'<td width="20%;">'+$("#foodNoDay"+val).val()+'</td>';
		  innerHTML=innerHTML+'<td width="20%;">'+$("#foodAmount"+val).val()+'</td></tr>';
		  
		}
		innerHTML=innerHTML+'</tbody></table></div>';  
	}
	
	var tourTableIdlen = $("#voucherSettlementConfirmationForm input[name=tourTableIdlen]"); 
	if(tourTableIdlen.length>0){
		
		innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
		innerHTML=innerHTML+'<tr class="tadaHeading"><td>G. Taxi Charges within TD City </td></tr></tbody></table>';
		
		 if($("#is_conveyanceDtls").val()==0){ 
		 	
		 	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
			innerHTML=innerHTML+'<tr class="lablevaluebg" id="tourHeaderId"><td class="lablevalue" style="width:25%;">Vehicle No</td>';
			innerHTML=innerHTML+'<td class="lablevalue" style="width:15%;">Date of Travel</td><td class="lablevalue" style="width:15%;">Distance</td><td class="lablevalue" style="width:15%;">Bill No</td>';
			innerHTML=innerHTML+'<td class="lablevalue" style="width:20%;">Amount</td></tr>';
		
			for(var i=0;i<tourTableIdlen.length;i++){
			  var val=tourTableIdlen[i].value; 
			  
			  innerHTML=innerHTML+'<tr class="lablevaluebg"><td style="width:25%;">'+$("#tourVehicleNo"+val).val()+'</td>';
			  innerHTML=innerHTML+'<td style="width:15%;">'+$("#tourDate"+val).val()+'</td>';
			  innerHTML=innerHTML+'<td style="width:15%;">'+$("#tourDistance"+val).val()+'</td>';
			  innerHTML=innerHTML+'<td style="width:15%;">'+$("#tourBillNo"+val).val()+'</td>';
			  innerHTML=innerHTML+'<td style="width:20%;">'+$("#tourAmount"+val).val()+'</td></tr>';
			  
			}
			innerHTML=innerHTML+'</tbody></table></div>';  
		
		 }else{
		 	
		 	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	        innerHTML=innerHTML+'<tr class="lablevaluebg" ><td class="lablevalue" style="width:25%;">Number of Days</td><td class="lablevalue" style="width:15%;">Amount</td></tr>';
	       
		 	for(var i=0;i<tourTableIdlen.length;i++){
			  var val=tourTableIdlen[i].value; 
			  
			  innerHTML=innerHTML+'<tr class="lablevaluebg"><td style="width:25%;">'+$("#tourNoOfDays"+val).val()+'</td>';
			  innerHTML=innerHTML+'<td style="width:15%;">'+$("#tourAmount"+val).val()+'</td></tr>';
			  
			}
			innerHTML=innerHTML+'</tbody></table></div>';  
		 }
	}
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>H. Advances Drawn </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>DA Advance from DTS</td><td>Advance Drawn from PAO</td>';
	innerHTML=innerHTML+'<td>Date of Advance Drawn from PAO</td><td>Advance Drawn from Other Sources</td>';
	innerHTML=innerHTML+'<td>Voucher No for Advance Drawn from Other Sources</td><td>Date of Advance Drawn from Other Sources</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#daAdvanceAmount").val()+'</td><td>'+$("#Adv_From_PAO").val()+'</td><td>'+$("#Adv_From_PAO_Date").val()+'</td>';
	innerHTML=innerHTML+'<td>'+$("#Adv_From_Other").val()+'</td><td>'+$("#Adv_From_Other_VNo").val()+'</td><td>'+$("#Adv_From_Other_Date").val();
	innerHTML=innerHTML+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>I. Advances Refunded </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>eMRO/MRO/Refund Amount</td><td>eMRO/MRO/Refund No</td>';
	innerHTML=innerHTML+'<td>eMRO/MRO Submission Date/Refund  Date</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#Advance_MRO").val()+'</td><td>'+$("#MRO_Number").val()+'</td><td>'+$("#MRO_Date").val()+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
    innerHTML=innerHTML+'<tr class="tadaHeading"><td colspan="4">Details of Recovery against this Travel (if any)</td></tr>';
 	innerHTML=innerHTML+'<tr><td class="lablevalue">IRLA Recovery Amount</td>';
	innerHTML=innerHTML+'<td>'+$("#irlaRecoveryAmt").val()+'</td></tr></tr></tbody></table></div>';

	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Total Amount of Claim:&nbsp;&nbsp;'+$("#totalSpentAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>less: Advance Taken:&nbsp;&nbsp;'+$("#totalAdvanceAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>add: Refunds:&nbsp;&nbsp;'+$("#totalRefundAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Payable/Recoverable Amount:&nbsp;&nbsp;'+$("#totalClaimedAmount").val()+'</td></tr></tbody></table></div>';
	
	}if(travelType=='100005' || travelType=='100006' || travelType=='100007' || travelType=='100008'){
    
    innerHTML=innerHTML+previewLTCTravelerDetails();
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td align="left"><b>Non DTS Journey Details</b></td><td colspan="11" align="right"></td></tr></tbody></table>';
	
	var ltcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ltcJrnyTableIdlen]");
	 if(ltcJrnyTableIdlen.length>0){
	   for(var i=0;i<ltcJrnyTableIdlen.length;i++){
		  var val=ltcJrnyTableIdlen[i].value;
		  
		  innerHTML=innerHTML+'<table id="ltcNonDtsJrnyTableGrid" class="filtersearch">';
          innerHTML=innerHTML+'<tr><td><table class="filtersearch"><tbody class="all1"><tr class="lablevalue">';
          innerHTML=innerHTML+'<td colspan="5" bgcolor="#AAAAAA" align="left"></td><td class="lablevalue" colspan="3" bgcolor="#AAAAAA" style="text-align:right">';
          innerHTML=innerHTML+'Journey Type:<span style="font-weight:normal">'+$("#nonDTSJourneyType"+val+" option:selected").text()+'</span></td></tr>';
          innerHTML=innerHTML+'<tr><td class="lablevalue">Mode of Travel</td><td>'+$("#nonDTSModeOfTravel"+val+" option:selected").text();
          innerHTML=innerHTML+'</td><td class="lablevalue">Departure Place</td><td>'+$("#nonDTSFromPlace"+val).val()+'</td><td class="lablevalue">Arrival Place</td><td>';
          innerHTML=innerHTML+$("#nonDTSToPlace"+val).val()+'</td><td class="lablevalue">Class of Accomodation</td><td>'+$("#nonDTSClassOfTravel"+val+" option:selected").text();
          innerHTML=innerHTML+'</td></tr><tr><td class="lablevalue">Departure Time</td><td>'+$("#nonDTSJryStartDate"+val).val();
          innerHTML=innerHTML+'</td><td class="lablevalue">Arrival Time</td><td>'+$("#nonDTSJryEndDate"+val).val()+'</td><td class="lablevalue">Sector</td><td>';
          innerHTML=innerHTML+$("#nonDTSSector"+val).val()+'</td><td class="lablevalue">Bill/PNR/ Ticket No.</td><td>'+$("#nonDTSTicketNo"+val).val();
          innerHTML=innerHTML+'</td></tr><tr><td class="lablevalue">Distance (KM)</td><td>'+$("#nonDTSDistanceKM"+val).val()+'</td><td class="lablevalue">Total Fare';
          innerHTML=innerHTML+'</td><td>'+$("#nonDTSJryAmount"+val).val()+'</td><td class="lablevalue">Total Refund</td><td>'+$("#nonDTSJryRefundAmount"+val).val();
          innerHTML=innerHTML+'</td><td class="lablevalue">Total Claimed Amount</td><td>'+$("#nonDTSJryClaimAmount"+val).val();
          innerHTML=innerHTML+'</td></tr><tr><td colspan="10" style="text-align:right"></td></tr><tr><td style="border-bottom:none" colspan="11" bgcolor="#003A70" align="left">';
          innerHTML=innerHTML+'<table class="filtersearch" width="100%" cellspacing="1" cellpadding="4" border="0"><tbody class="all3"><tr class="lablevalue">';
          innerHTML=innerHTML+'<td style="width:10%;">Traveler Name</td><td style="width:10%;">Relation</td><td style="width:5%;">';
          innerHTML=innerHTML+'Age</td><td style="width:10%;">Ticket No</td><td style="width:10%;">Journey Performed?</td>';
          innerHTML=innerHTML+'<td style="width:10%;">Booking Amount</td><td style="width:10%;">Refund Amount</td><td style="width:10%;">';
          innerHTML=innerHTML+'Claimed Amount</td></tr>';
          
          var ltcJrnyPassTableIdlen = $("#voucherSettlementConfirmationForm input[name=ltcJrnyPassTableIdlen"+val+"]");
		 if(ltcJrnyPassTableIdlen.length>0){
		   for(var j=0;j<ltcJrnyPassTableIdlen.length;j++){
			  var val1=ltcJrnyPassTableIdlen[j].value;
			  innerHTML=innerHTML+'<tr><td>'+$("#nonDTSPassDtlsTravellerName"+val+"_"+val1+" option:selected").text();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsRelation"+val+"_"+val1).val()+'</td><td>';
			  innerHTML=innerHTML+$("#nonDTSPassDtlsAge"+val+"_"+val1).val()+'</td><td>'+$("#nonDTSPassDtlsTicketNo"+val+"_"+val1).val();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsJourneyPerformed"+val+"_"+val1+" option:selected").text();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsBookingAmount"+val+"_"+val1).val()+'</td><td>'+$("#nonDTSPassDtlsRefundAmount"+val+"_"+val1).val();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsClaimedAmount"+val+"_"+val1).val()+'</td></tr>';
		   }
		 } 
		 innerHTML=innerHTML+'</tbody></table></td></tr></tbody></table></td></tr></table></div>';
	   }	
	 }
 
    innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>Advances Drawn </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table style="table-layout:fixed" width="100%" cellspacing="2" cellpadding="0" border="0">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Advance Drawn from PAO</td>';
	innerHTML=innerHTML+'<td>Date of Advance Drawn from PAO</td><td>Advance Drawn from Other Sources</td>';
	innerHTML=innerHTML+'<td>Voucher No for Advance Drawn from Other Sources</td><td>Date of Advance Drawn from Other Sources</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#Adv_From_PAO").val()+'</td><td>'+$("#Adv_From_PAO_Date").val()+'</td>';
	innerHTML=innerHTML+'<td>'+$("#Adv_From_Other").val()+'</td><td>'+$("#Adv_From_Other_VNo").val()+'</td><td>'+$("#Adv_From_Other_Date").val();
	innerHTML=innerHTML+'</td></tr></tbody></table></div>';
		
    innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>Advances Refunded </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>eMRO/MRO/Refund Amount</td><td>eMRO/MRO/Refund No</td>';
	innerHTML=innerHTML+'<td>eMRO/MRO Submission Date/Refund  Date</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#Advance_MRO").val()+'</td><td>'+$("#MRO_Number").val()+'</td><td>'+$("#MRO_Date").val()+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
    innerHTML=innerHTML+'<tr class="tadaHeading"><td colspan="4">Details of Recovery against this Travel (if any)</td></tr>';
 	innerHTML=innerHTML+'<tr class="lablevalue"><td><b>IRLA Recovery Amount</b></td>';
	innerHTML=innerHTML+'<td>'+$("#irlaRecoveryAmt").val()+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Total Amount of Claim:&nbsp;&nbsp;'+$("#totalSpentAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>less: Advance Taken:&nbsp;&nbsp;'+$("#totalAdvanceAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>add: Refunds:&nbsp;&nbsp;'+$("#totalRefundAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Payable/Recoverable Amount:&nbsp;&nbsp;'+$("#totalClaimedAmount").val()+'</td></tr><tbody></table></div>';
	
    }if(travelType=='100001'){
    
    innerHTML=innerHTML+previewPTTravelerDetails();
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td align="left"><b>Non DTS Journey Details</b></td><td colspan="11" align="right"></td></tr></tbody></table>';
	
	var ptJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ptJrnyTableIdlen]");
	 if(ptJrnyTableIdlen.length>0){
	   for(var i=0;i<ptJrnyTableIdlen.length;i++){
		  var val=ptJrnyTableIdlen[i].value;
		  
		  innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table id="ptNonDtsJrnyTableGrid" class="filtersearch" width="100%" cellspacing="0" cellpadding="0" border="0">';
          innerHTML=innerHTML+'<tr><td><table class="filtersearch"><tbody class="all1"><tr class="lablevalue">';
          innerHTML=innerHTML+'<td colspan="5" bgcolor="#AAAAAA" align="left"></td><td colspan="3" class="lablevalue" bgcolor="#AAAAAA" style="text-align:right">';
          innerHTML=innerHTML+'Journey Type:<span style="font-weight:normal">'+$("#nonDTSJourneyType"+val+" option:selected").text()+'</td></tr>';
          innerHTML=innerHTML+'<tr><td class="lablevalue">Mode of Travel</td><td>'+$("#nonDTSModeOfTravel"+val+" option:selected").text();
          innerHTML=innerHTML+'</td><td class="lablevalue">Departure Place</td><td>'+$("#nonDTSFromPlace"+val).val()+'</td><td class="lablevalue">Arrival Place</td><td>';
          innerHTML=innerHTML+$("#nonDTSToPlace"+val).val()+'</td><td class="lablevalue">Class of Accomodation</td><td>'+$("#nonDTSClassOfTravel"+val+" option:selected").text();
          innerHTML=innerHTML+'</td></tr><tr><td class="lablevalue">Departure Time</td><td>'+$("#nonDTSJryStartDate"+val).val();
          innerHTML=innerHTML+'</td><td class="lablevalue">Arrival Time</td><td>'+$("#nonDTSJryEndDate"+val).val()+'</td><td class="lablevalue">Sector</td><td>';
          innerHTML=innerHTML+$("#nonDTSSector"+val).val()+'</td><td class="lablevalue">Bill/PNR/ Ticket No.</td><td>'+$("#nonDTSTicketNo"+val).val();
          innerHTML=innerHTML+'</td></tr><tr><td class="lablevalue">Distance (KM)</td><td>'+$("#nonDTSDistanceKM"+val).val()+'</td><td class="lablevalue">Total Fare';
          innerHTML=innerHTML+'</td><td>'+$("#nonDTSJryAmount"+val).val()+'</td><td class="lablevalue">Total Refund</td><td>'+$("#nonDTSJryRefundAmount"+val).val();
          innerHTML=innerHTML+'</td><td class="lablevalue">Total Claimed Amount</td><td>'+$("#nonDTSJryClaimAmount"+val).val();
          innerHTML=innerHTML+'</td></tr><tr><td colspan="10" align="right"></td></tr><tr><td style="border-bottom:none" colspan="11" bgcolor="#003A70" align="left">';
          innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all3"><tr class="lablevalue">';
          innerHTML=innerHTML+'<td style="width:10%;">Traveler Name</td><td style="width:10%;">Relation</td><td style="width:5%;">';
          innerHTML=innerHTML+'Age</td><td style="width:10%;">Ticket No</td><td style="width:10%;">Journey Performed?</td>';
          innerHTML=innerHTML+'<td style="width:10%;">Booking Amount</td><td style="width:10%;">Refund Amount</td><td style="width:10%;">';
          innerHTML=innerHTML+'Claimed Amount</td></tr>';
          
          var ltcJrnyPassTableIdlen = $("#voucherSettlementConfirmationForm input[name=ptJrnyPassTableIdlen"+val+"]");
		 if(ltcJrnyPassTableIdlen.length>0){
		   for(var j=0;j<ltcJrnyPassTableIdlen.length;j++){
			  var val1=ltcJrnyPassTableIdlen[j].value;
			  innerHTML=innerHTML+'<tr><td>'+$("#nonDTSPassDtlsTravellerName"+val+"_"+val1+" option:selected").text();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsRelation"+val+"_"+val1).val()+'</td><td>';
			  innerHTML=innerHTML+$("#nonDTSPassDtlsAge"+val+"_"+val1).val()+'</td><td>'+$("#nonDTSPassDtlsTicketNo"+val+"_"+val1).val();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsJourneyPerformed"+val+"_"+val1+" option:selected").text();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsBookingAmount"+val+"_"+val1).val()+'</td><td>'+$("#nonDTSPassDtlsRefundAmount"+val+"_"+val1).val();
			  innerHTML=innerHTML+'</td><td>'+$("#nonDTSPassDtlsClaimedAmount"+val+"_"+val1).val()+'</td></tr>';
		   }
		 } 
		 innerHTML=innerHTML+'</tbody></table></td></tr></tbody></table></td></tr></table></div></div>';
	   }	
	 }
	 
	 innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	 innerHTML=innerHTML+'<tr class="tadaHeading"><td><b>CTG &amp; Personal Effects</b></td></tr></tbody></table>';
     innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
     innerHTML=innerHTML+'<tr><td style="width:33%;"></td><td class="lablevalue" style="width:33%;">Transfer Type</td>'; 
	 innerHTML=innerHTML+'<td class="lablevalue" style="width:33%;">Claimed Amount</td></tr>';
	 innerHTML=innerHTML+'<tr><td class="lablevalue" style="width:33%;">Composite Transfer and Packing Grant (CTG)</td>'; 
	 innerHTML=innerHTML+'<td style="width:33%;">'+$("#CTGPercentage option:selected").text()+'</td>'; 
	 innerHTML=innerHTML+'<td style="width:33%;">'+$("#Claim_GTC_Amt").val()+'</td></tr></tbody></table>';
	 
	 innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	 innerHTML=innerHTML+'<tr class="lablevalue"><td style="width:20%;" bgcolor="#EBF4FA">Particulars</td>'; 
	 innerHTML=innerHTML+'<td style="width:20%;background-color:#EBF4FA;border-right:1px solid white">Weight (in Kg)</td>';
	 innerHTML=innerHTML+'<td style="width:20%;background-color:#EBF4FA;border-right:1px solid white">Mode of Conveyance</td>'; 
	 innerHTML=innerHTML+'<td style="width:20%;background-color:#EBF4FA;border-right:1px solid white">Distance (in Kms)</td>'; 
     innerHTML=innerHTML+'<td style="width:20%;background-color:#EBF4FA">Claimed Amount</td></tr>';
     
     var particularWeight = $("#voucherSettlementConfirmationForm input[name=particularWeight_0]");
     var particularConveyance = $("#voucherSettlementConfirmationForm select[name=particularConveyance_0]");
     var particularDistance = $("#voucherSettlementConfirmationForm input[name=particularDistance_0]");
     var particularClaimAmt = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_0]");
     var particularName = $("#voucherSettlementConfirmationForm input[name=particularName_0]");
     for(var index=0;index<particularWeight.length;index++){
		 if(particularConveyance[index].value='0'){
			 particularConveyance[index].value='Yes';
		 }else{
			 particularConveyance[index].value='No';
		 }
		 
		 
     	innerHTML=innerHTML+'<tr><td style="width:20%;">'+particularName[index].value+'</td>'; 
	    innerHTML=innerHTML+'<td style="width:20%;">'+particularWeight[index].value+'</td><td style="width:20%;">'+particularConveyance[index].value+'</td>'; 
	    innerHTML=innerHTML+'<td style="width:20%;">'+particularDistance[index].value+'</td><td style="width:20%;">'+particularClaimAmt[index].value+'</td></tr>';
     }
    
    innerHTML=innerHTML+'</tbody></table>';
    
    innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
    innerHTML=innerHTML+'<tr><td class="lablevalue" style="width:25%;">Is Conveyance part of Luggage?</td>'; 
	innerHTML=innerHTML+'<td style="width:25%;">'+$("#conveyancePartOfLuggage option:selected").text()+'</td></tr>';
	
	 if($("#conveyancePartOfLuggage").val()=='1'){
	 	
	 innerHTML=innerHTML+'<tr><td class="lablevalue" style="width:25%;">Transportation of Conveyance</td>'; 
	 innerHTML=innerHTML+'<td style="width:25%;">'+$("#conveyanceType option:selected").text()+'</td></tr>';
	
	 }
	 innerHTML=innerHTML+'</tbody></table></div>';
	
	 if($("#conveyancePartOfLuggage").val()=='1'){	
	 	
	 innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
     innerHTML=innerHTML+'<tr class="lablevalue">';
     innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;border-right:1px solid white">Mode of Conveyance</td>';
     innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;border-right:1px solid white">Distance (in Kms)</td>';
     innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;">Claimed Amount</td></tr>';
   
	     var particularConveyance_1 = $("#voucherSettlementConfirmationForm select[name=particularConveyance_1]");
	     var particularDistance_1 = $("#voucherSettlementConfirmationForm input[name=particularDistance_1]");
	     var particularClaimAmt_1 = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_1]");
	     var particularName_1 = $("#voucherSettlementConfirmationForm input[name=particularName_1]");
	     for(var index=0;index<particularConveyance_1.length;index++){
	     	innerHTML=innerHTML+'<tr>';
			innerHTML=innerHTML+'<td style="width:25%;">'+particularConveyance_1[index].value+'</td>';
			innerHTML=innerHTML+'<td style="width:25%;">'+particularDistance_1[index].value+'</td>';
			innerHTML=innerHTML+'<td style="width:25%;">'+particularClaimAmt_1[index].value+'</td></tr>';
	     }
	      innerHTML=innerHTML+'</tbody></table></div>';
	  }
	
     innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>Advances Drawn </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td style="width:15%;">Advance Drawn From</td><td style="width:15%;">Date of Advance</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">Luggage including Cartage if any</td><td style="width:15%;">Coveyance</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">CTG</td><td style="width:15%;">Total</td></tr>';
	innerHTML=innerHTML+'<tr><td style="width:15%;">DTS</td><td style="width:15%;">'+$("#dtsDateOfAdvance").val()+'</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">'+$("#dtsLuggAmt").val()+'</td><td style="width:15%;">'+$("#dtsCoveyanceAmt").val()+'</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">'+$("#dtsCTGAmt").val()+'</td><td style="width:15%;">'+$("#daAdvanceAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr><td style="width:15%;">PAO</td><td style="width:15%;">'+$("#Adv_From_PAO_Date").val()+'</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">'+$("#Adv_Luggage_PAO").val()+'</td><td style="width:15%;">'+$("#Adv_Coveyance_PAO").val()+'</td>'; 
	innerHTML=innerHTML+'<td style="width:15%;">'+$("#Adv_CTG_PAO").val()+'</td><td style="width:15%;">'+$("#Adv_From_PAO").val()+'</td></tr></tbody></table></div>';
		
    innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>Advances Refunded </td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>eMRO/MRO/Refund Amount</td><td>eMRO/MRO/Refund No</td>';
	innerHTML=innerHTML+'<td>eMRO/MRO Submission Date/Refund  Date</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td>'+$("#Advance_MRO").val()+'</td><td>'+$("#MRO_Number").val()+'</td><td>'+$("#MRO_Date").val()+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
    innerHTML=innerHTML+'<tr class="tadaHeading"><td colspan="4">Details of Recovery against this Travel (if any)</td></tr>';
 	innerHTML=innerHTML+'<tr><td class="lablevalue">IRLA Recovery Amount</td>';
	innerHTML=innerHTML+'<td>'+$("#irlaRecoveryAmt").val()+'</td></tr></tbody></table></div>';
	
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Total Amount of Claim:&nbsp;&nbsp;'+$("#totalSpentAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>less: Advance Taken:&nbsp;&nbsp;'+$("#totalAdvanceAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>add: Refunds:&nbsp;&nbsp;'+$("#totalRefundAmount").val()+'</td></tr>';
	innerHTML=innerHTML+'<tr class="lablevalue"><td>Payable/Recoverable Amount:&nbsp;&nbsp;'+$("#totalClaimedAmount").val()+'</td></tr></tbody></table></div>';
	
    }
    
	innerHTML=innerHTML+'<div id="program" class="programBlock"><div class="welcomebox" style="margin-bottom:2px"><div class="welcomeHead">';
	innerHTML=innerHTML+'<p>Certify that</p></div><div class="welcomecont">';
	innerHTML=innerHTML+'<table class="filtersearch" id="certify_that_tb"><tbody class="all1">';
	
	var claimCertify = $("#voucherSettlementConfirmationForm input[name=claimCertify]"); 
	for(var i=0;i<claimCertify.length;i++){
	 innerHTML=innerHTML+'<tr><td width="5%">'+(i+1)+'</td><td width="95%">'+claimCertify[i].value+'</td></tr>';
	}
	innerHTML=innerHTML+'</tbody></table></div></div></div>';  
	
	var fileTypeName = $("#voucherSettlementConfirmationForm input[name=fileTypeName]");
	var fileTypePath = $("#voucherSettlementConfirmationForm input[name=fileTypePath]");
	
	if(fileTypeName.length>0){
	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:2px"><table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="tadaHeading"><td>Uploaded documents:</td></tr></tbody></table>';
	innerHTML=innerHTML+'<table class="filtersearch"><tbody class="all1">';
	innerHTML=innerHTML+'<tr class="lablevaluebg"><td class="lablevalue">Document Name</td><td class="lablevalue">File</td></tr>';
	 
	 for(var i=0;i<fileTypeName.length;i++){ 
	  
     innerHTML=innerHTML+'<tr><td width="60%">'+$("#innerDocName_"+fileTypeName[i].value).text()+'</td>';
     if(fileTypePath[i].value==''){
     	innerHTML=innerHTML+'<td width="30%"></td>';
     }else{
//		 var path = encodeURIComponent(fileTypePath[i].value);
     	innerHTML=innerHTML+'<td width="30%"><a href="' + $("#context_path").val() + 'com/downloadFile?dwnFilePath=' + fileTypePath[i].value + '">';
     	innerHTML=innerHTML+'<img src="' + $("#context_path").val() + 'images/pdfImage.png" style="width:30px;height:35px"></a></td>';
     }
	innerHTML=innerHTML+'</tr>';
	 }
	  
	innerHTML=innerHTML+'</tbody></table></div>';
	}

	innerHTML=innerHTML+'<div class="welcomebox" style="margin-bottom:1rem"><table class="filtersearch"><tbody class="all1">';
	if($("#userServiceType").val()== '1'){
		innerHTML=innerHTML+'<tr><td class="lablevalue">Counter Singing Authority</td><td colspan="3">'+$("#counterPersonalName").val()+'</td></tr>';
	}else{
	innerHTML=innerHTML+'<tr><td class="lablevalue">Counter Singing Authority Personal No</td><td>'+$("#counterPersonalNo").val()+'&nbsp;&nbsp;</td>';
	innerHTML=innerHTML+'<td><font style="background-color: coral;">'+$("#counterPersonalName").val()+'</font></td>';
	innerHTML=innerHTML+'<td><font style="background-color: coral;">'+$("#counterPersonalLevel").val()+'</font></td></tr>';
	}
	innerHTML=innerHTML+'<tr><td class="lablevalue" colspan="2">Date of Claim preferred by the Individual to the Office</td>';
	innerHTML=innerHTML+'<td colspan="2">'+$("#claimPreferredDate").val()+'&nbsp;&nbsp;</td></tr>';
	innerHTML=innerHTML+'<tr><td colspan="4">';
	innerHTML=innerHTML+'<span class="lablevalue">If Date of Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken?</span>&nbsp;&nbsp;'; 
	innerHTML=innerHTML+$("#claimPreferredSanction option:selected").text()+'</td></tr>';
	innerHTML=innerHTML+'<tr><td><span class="lablevalue">Sanction No:</span>'+$("#claimPreferredSanctionNo").val()+'</td>';
	innerHTML=innerHTML+'<td colspan="3"><span class="lablevalue">Sanction Date:</span>'+$("#claimPreferredSanctionDate").val()+'</td></tr></table></div>';
	
	innerHTML=innerHTML+'<table class="filtersearch">';
	innerHTML=innerHTML+'<tr><td>';
	innerHTML=innerHTML+'<input type="button" value="Edit" onclick="return tadaClaimFormForEdit()" class="butn">';
	innerHTML=innerHTML+'</td><td style="text-align:right">';
	innerHTML=innerHTML+'<input type="button" value="Send to CO" onclick="return validateUpdateClaimDetails(\''+travelType+'\')" class="butn">';
	innerHTML=innerHTML+'</td></tr></table>';
	  
	 $("#input_tada_claim_form").hide()
	 $("#preview_tada_claim_form").html(innerHTML);
	 $("#preview_tada_claim_form").show();
 }
  
 }	

function tadaClaimFormForEdit(){
	$("#claim_documents_form").hide();
	$("#claim_input_form").show();
	$("#input_tada_claim_form").show();
	$("#upload_claim_doc").show();
	$("#save_claim_form").hide();
	$("#preview_tada_claim_form").hide();
	$("#preview_tada_claim_form").html("");
}

function validateLTCPAOEditForm(type,index){
 	
 	var flag=validateLTCPassedAmountData(type,index);
 	
 	calculateLTCPAOEditAmount();
 	return flag;
 }
 
 function validateAuditorLTCPAOEditForm(){
 	
 	var flag=validateLTCPAOEditData();
 	
 	calculateLTCPAOEditAmount();
 	return flag;
 }
 
 function validateLTCPassedAmountData(type,index){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	
	var taLength=0;
	
	if(type=='TAJrny'){
		taLength=parseInt(index);
	}
	
	if(index==''){
		taLength=tadaJrySeq.length;
	}
	
	for(var index=0;index<taLength;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
	}
	
	if(type=='paoAdv'){
		if($("#sanPAOAdvanceAmt").val()==''){
			alert("Advance Drawn from PAO Passed Amount can not be blank");
			$("#sanPAOAdvanceAmt").focus();
			return false;
		}
		
	}
	
	if(type=='otherAdv'){
		if($("#sanOtherAdvanceAmt").val()==''){
			alert("Advance Drawn from Other Sources Passed Amount can not be blank");
			$("#sanPAOAdvanceAmt").focus();
			return false;
		}
		
	}
	
	if(type=='MRORef'){
		if($("#isMROReceived").val()==''){
			alert("Please Select MRO Received");
			return false;
		}
		
		if($("#sanMRORefundAmt").val()==''){
			alert("eMRO/MRO/ Refund Passed Amount can not be blank");
			$("#sanPAOAdvanceAmt").focus();
			return false;
		}
		
		if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
			alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
			$("#sanMRORefundAmt").val(0);
			$("#sanMRORefundAmt").focus();
			return false;
		}
	
	}
	
	if(type=='recovery'){
		
		if($("#recoveryPassAmt").val()==''){
			alert("Recovery Passed Amount can not be blank");
			$("#recoveryPassAmt").focus();
			return false;
		}
		
		var totalRecovery=Number($("#recoveryAmt").val())+Number($("#irlaRecoveryAmt").val());
		
		if(Number($("#recoveryPassAmt").val())>Number(totalRecovery)){
			alert("Recovery Passed Amount can not greater than "+totalRecovery);
			$("#recoveryPassAmt").val(0);
			$("#recoveryPassAmt").focus();
			return false;
		}
	
	}
	
	return true;
 }

 function validateLTCPAOEditData(){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var deductionReason=$("#JrnyDeductionReason"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		var jrnyBKGFrom=$("#jrnyBKGFrom"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(jrnyBKGFrom=='NON-DTS' && parseInt(sanAmount)!=parseInt(actualTaAmount) && deductionReason==''){
			alert("Please Select Journey Details Reason for Deduction");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if($("#userServiceType").val()=='1'){
			var isPaymentMade=$("#isPaymentMade"+val).val();
			if(isPaymentMade == 'undefined' || isPaymentMade == null ){}
			else{
				if(isPaymentMade == ''){
					alert("Please Select Journey Details Payment Made Option.");
					return false;
				}
				var jrnyPaymentTo=$("#jrnyPaymentTo"+val).val();
				if(jrnyPaymentTo == ''){
					alert("Please Select Journey Details Payment To Option.");
					return false;
				}
			}
		}
	}
	
	if($("#sanPAOAdvanceAmt").val()==''){
		alert("Advance Drawn from PAO Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanPAOAdvanceAmt").val()) != parseInt($("#paoAdvanceAmount").val()) && $("#paoAdvDeductionReason").val()==''){
		alert("Please Select Advance Drawn from PAO Reason for Deduction");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if($("#sanOtherAdvanceAmt").val()==''){
		alert("Advance Drawn from Other Sources Passed Amount can not be blank");
		$("#sanOtherAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanOtherAdvanceAmt").val()) != parseInt($("#otherAdvanceAmount").val()) && $("#otherAdvDeductionReason").val()==''){
		alert("Please Select Advance Drawn from Other Sources Reason for Deduction");
		$("#sanOtherAdvanceAmt").focus();
		return false;
	}
	
	if($("#isMROReceived").val()==''){
		alert("Please Select MRO Received");
		return false;
	}
	
	if($("#sanMRORefundAmt").val()==''){
		alert("eMRO/MRO/ Refund Passed Amount can not be blank");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
		alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
		$("#sanMRORefundAmt").val(0);
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val()) != parseInt($("#mroRefundAmount").val()) && $("#mroRefundDeductionReason").val()==''){
		alert("Please Select eMRO/MRO/ Refund Amount Reason for Deduction");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if($("#other_Deduction").val()==''){
		alert("Any other Deduction from Officer can not be blank");
		$("#other_Deduction").focus();
		return false;
	}
	
	if(parseInt($("#other_Deduction").val())>0 && $("#other_DeductionReason").val()==''){
		alert("Please enter the reason for other deduction from officer");
		$("#other_DeductionReason").focus();
		return false;
	}
	
	return true;
 }
 
 function calculateLTCPAOEditAmount(){
	
	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	
	var totalDTSBookingAmount=0;
	var totalNonDTSBookingAmount=0;
	var journeyAmount=0;
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		if($("#jrnyBKGFrom"+val).val()=='DTS'){
			totalDTSBookingAmount=totalDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}else{
			totalNonDTSBookingAmount=totalNonDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}
		
		if($('#jrnyPaymentTo'+val).length){
			if($('#jrnyPaymentTo'+val).val()!='Officer'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}else{
			if($("#jrnyBKGFrom"+val).val()=='DTS'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}
	}
	
	var otherDeduction= Number($("#other_Deduction").val());
	
	var recoveryPassAmt= Number($("#recoveryPassAmt").val());
	
	var totalApprovedAmount=Number(totalDTSBookingAmount)+Number(totalNonDTSBookingAmount)-Number(otherDeduction);
	
	var totalAdvanceApproved=Number(journeyAmount)+Number($("#sanPAOAdvanceAmt").val())+Number($("#sanOtherAdvanceAmt").val());
	                         
	var totalRefundApproved= Number($("#sanMRORefundAmt").val());
	
	var totalPassedAmount= Number(totalApprovedAmount)+Number(totalRefundApproved)-Number(totalAdvanceApproved)+Number(recoveryPassAmt);                       
	
	$("#total_approved_amount").html(parseFloat(totalApprovedAmount).toFixed(2));
	$("#total_advance_approved").html(parseFloat(totalAdvanceApproved).toFixed(2));
	$("#total_refund_approved").html(parseFloat(totalRefundApproved).toFixed(2));
	$("#total_passed_amount").html(parseFloat(totalPassedAmount).toFixed(2));
	
	$("#totalApprovedAmount").val(parseFloat(totalApprovedAmount).toFixed(2));
	$("#totalAdvanceApprovedAmount").val(parseFloat(totalAdvanceApproved).toFixed(2));
	$("#totalRefundsApprovedAmount").val(parseFloat(totalRefundApproved).toFixed(2));
	$("#totalPassedAmount").val(parseFloat(totalPassedAmount).toFixed(2));
	
}

 function createLTCJrnyDetails(){
	
	if(valideteLTCJrnyDetails()){
		
		 var mainTable = document.getElementById("ltcNonDtsJrnyTableGrid");
		 
		 ltcJrnyTableIdlen++;
		 
		 var row = mainTable.insertRow(-1);
		 //row.className = "lablevalue";
		 row.id = "nonDTSLTCJrnyDetls"+ltcJrnyTableIdlen;
		 var cell0 =  row.insertCell(0);
		 cell0.innerHTML ='<table class="filtersearch" width="100%" cellpadding="0" cellspacing="0" id="nonDTSJrnyDetls'+ltcJrnyTableIdlen+'"><tbody class="all1"></tbody></table>';
		 
		 var table = document.getElementById("nonDTSJrnyDetls"+ltcJrnyTableIdlen);
		 
		 var row0 = table.insertRow(-1);
		 //row0.className = "lablevalue";
		 var cell0 =  row0.insertCell(0);
		 cell0.setAttribute("bgcolor","#AAAAAA");
		 cell0.setAttribute("align","left");
		 cell0.setAttribute("colspan","5");
		 cell0.innerHTML = '<a onclick="deleteLTCJrnyDetails('+ltcJrnyTableIdlen+')" style="color: rgb(255, 255, 255); border: medium none; cursor: pointer; padding: 2px 8px; background: rgb(13, 57, 94) none repeat scroll 0% 0%;">Delete Segment</a>';
		 
		 var cell1 =  row0.insertCell(1);
		 cell1.setAttribute("align","right");
		 cell1.setAttribute("bgcolor","#AAAAAA");
		 cell1.setAttribute("colspan","3");
		 cell1.className = "shift";
		 cell1.innerHTML = 'Journey Type:<select name="nonDTSJourneyType" id="nonDTSJourneyType'+ltcJrnyTableIdlen+'" class="combo"  style="width:100px;"><option value="">Select</option><option value="0">Onward</option><option value="1">Return</option></select>' +
		 				   '<input type="hidden"  readonly="true" name="ltcJrnyTableIdlen" value="'+ltcJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="airlineType" id="airlineType'+ltcJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="otherAirlineType" id="otherAirlineType'+ltcJrnyTableIdlen+'"/>' +
		 				   '<input type="hidden"  readonly="true" name="FAA_AuthorityNo" id="FAA_AuthorityNo'+ltcJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="FAA_AuthorityDate" id="FAA_AuthorityDate'+ltcJrnyTableIdlen+'"/>';
		 
		 var row1 = table.insertRow(-1);
		 //row1.className = "lablevalue";
		 var cell0 =  row1.insertCell(0);
		 cell0.innerHTML ='Mode of Travel';
		 cell0.className = "lablevalue";
		 var cell1 =  row1.insertCell(1);
		 cell1.innerHTML ='<select name="nonDTSModeOfTravel" id="nonDTSModeOfTravel'+ltcJrnyTableIdlen+'" class="combo" style="width:125px;" onchange="setClassOfTravel(this.value,'+ltcJrnyTableIdlen+');"><option value="">Select</option></select>';
		 var cell2 =  row1.insertCell(2);
		 cell2.innerHTML ='Departure Place';
		 cell2.className = "lablevalue";
		 var cell3 =  row1.insertCell(3);
		 cell3.setAttribute("id","nonDTS_From"+ltcJrnyTableIdlen);
		 cell3.innerHTML ='<input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="50" autocomplete="off"/>';
		 var cell4 =  row1.insertCell(4);
		 cell4.innerHTML ='Arrival Place';
		 cell4.className = "lablevalue";
		 var cell5 =  row1.insertCell(5);
		 cell5.setAttribute("id","nonDTS_To"+ltcJrnyTableIdlen);
		 cell5.innerHTML ='<input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="50" autocomplete="off"/>';
		 var cell6 =  row1.insertCell(6);
		 cell6.innerHTML ='Class of Accomodation';
		 cell6.className = "lablevalue";
		 var cell7 =  row1.insertCell(7);
		 cell7.innerHTML ='<select name="nonDTSClassOfTravel" id="nonDTSClassOfTravel'+ltcJrnyTableIdlen+'" style="width:175px;" class="combo"><option value="">Select</option></select>';
		 
		 var row2 = table.insertRow(-1);
		 //row2.className = "lablevalue";
		 var cell0 =  row2.insertCell(0);
		 cell0.innerHTML ='Departure Time';
		 cell0.className = "lablevalue";
		 var cell1 =  row2.insertCell(1);
		 cell1.innerHTML ='<input type="text" name="nonDTSJryStartDate" id="nonDTSJryStartDate'+ltcJrnyTableIdlen+'" class="txtfldM" style="width:125px;" maxlength="20" readonly="true" autocomplete="off"/>';
		 var cell2 =  row2.insertCell(2);
		 cell2.innerHTML ='Arrival Time';
		 cell2.className = "lablevalue";
		 var cell3 =  row2.insertCell(3);
		 cell3.innerHTML ='<input type="text" name="nonDTSJryEndDate" id="nonDTSJryEndDate'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="20" readonly="true" autocomplete="off"/>';
		 var cell4 =  row2.insertCell(4);
		 cell4.innerHTML ='Sector';
		 cell4.className = "lablevalue";
		 var cell5 =  row2.insertCell(5);
		 cell5.innerHTML ='<input type="text" name="nonDTSSector" id="nonDTSSector'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="20" autocomplete="off"/>';
		 var cell6 =  row2.insertCell(6);
		 cell6.innerHTML ='Bill/PNR/ Ticket No.';
		 cell6.className = "lablevalue";
		 var cell7 =  row2.insertCell(7);
		 cell7.innerHTML ='<input type="text" name="nonDTSTicketNo" id="nonDTSTicketNo'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="30" autocomplete="off"/>';
		 
		 var row3 = table.insertRow(-1);
		 //row3.className = "lablevalue";
		 var cell0 =  row3.insertCell(0);
		 cell0.innerHTML ='Distance (KM)';
		 cell0.className = "lablevalue";
		 var cell1 =  row3.insertCell(1);
		 cell1.innerHTML ='<input type="text" name="nonDTSDistanceKM" id="nonDTSDistanceKM'+ltcJrnyTableIdlen+'" maxlength="5" class="txtfldM" style="width:125px;" onkeypress="return isNumericOnlyKey(event)" autocomplete="off"/>';
		 var cell2 =  row3.insertCell(2);
		 cell2.innerHTML ='Total Fare';
		 cell2.className = "lablevalue";
		 var cell3 =  row3.insertCell(3);
		 cell3.innerHTML ='<input type="text" name="nonDTSJryAmount" id="nonDTSJryAmount'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="8" value="0" readonly="true" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 var cell4 =  row3.insertCell(4);
		 cell4.innerHTML ='Total Refund';
		 cell4.className = "lablevalue";
		 var cell5 =  row3.insertCell(5);
		 cell5.innerHTML ='<input type="text" name="nonDTSJryRefundAmount" id="nonDTSJryRefundAmount'+ltcJrnyTableIdlen+'" class="txtfldM" value="0" readonly="true" maxlength="8" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 var cell6 =  row3.insertCell(6);
		 cell6.innerHTML ='Total Claimed Amount';
		 cell6.className = "lablevalue";
		 var cell7 =  row3.insertCell(7);
		 cell7.innerHTML ='<input type="text" name="nonDTSJryClaimAmount" id="nonDTSJryClaimAmount'+ltcJrnyTableIdlen+'" class="txtfldM" maxlength="8" value="0" readonly="true" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 
		 var row4 = table.insertRow(-1);
		 var cell0 =  row4.insertCell(0);
		 cell0.setAttribute("align","right");
		 cell0.setAttribute("colspan","10");
		 cell0.className = "shift";
		 cell0.innerHTML = '<a onclick="addLTCJrnyTraveller('+ltcJrnyTableIdlen+')" style="color: rgb(255, 255, 255); border: medium none; cursor: pointer; padding: 2px 8px; background: rgb(13, 57, 94) none repeat scroll 0% 0%;">Add Traveler</a>';
		 
		 var row5 = table.insertRow(-1);
		 var cell0 =  row5.insertCell(0);
		 cell0.setAttribute("bgcolor","#003A70");
		 cell0.setAttribute("style","padding:5px;");
		 cell0.setAttribute("align","left");
		 cell0.setAttribute("colspan","11");
		 
		 var innerHtmlValue= '<table class="filtersearch" cellspacing="1" cellpadding="4" width="100%" border="0" id="nonDTSJrnyPassDetls'+ltcJrnyTableIdlen+'"><tbody class="all3">';
		 innerHtmlValue=innerHtmlValue+'<tr class="lablevalue"><td style="width:10%;">Traveler Name</td><td style="width:10%;">Relation</td><td style="width:5%;">Age</td><td style="width:10%;">Ticket No</td><td style="width:10%;">Journey Performed?</td><td style="width:10%;">Booking Amount</td><td style="width:10%;">Refund Amount</td><td style="width:10%;">Claimed Amount</td></tr>';
		 
		 innerHtmlValue=innerHtmlValue+'<tr>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsTravellerName'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsTravellerName'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="setTravellerRelationAndAge(this.value,'+ltcJrnyTableIdlen+','+ltcJrnyPassTableIdlen+');"><option value="">Select</option></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRelation'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsRelation'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:95px;" onkeypress="return isNumberKey(event)" readonly="true"></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsAge'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsAge'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:40px;" maxlength="3" onkeypress="return isNumberKey(event)" readonly="true"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsTicketNo'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsTicketNo'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:115px;" maxlength="50" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsJourneyPerformed'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsJourneyPerformed'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:65px;" onchange="takeLTCNonDTSCancellation(this.value,'+ltcJrnyTableIdlen+','+ltcJrnyPassTableIdlen+');"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsBookingAmount'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSBookingAmount('+ltcJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsRefundAmount'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSRefundAmount('+ltcJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+ltcJrnyTableIdlen+'" id="nonDTSPassDtlsClaimedAmount'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSClaimedAmount('+ltcJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanType'+ltcJrnyTableIdlen+'" id="nonDTSJrnyCanType'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanNo'+ltcJrnyTableIdlen+'" id="nonDTSJrnyCanSanNo'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanDate'+ltcJrnyTableIdlen+'" id="nonDTSJrnyCanSanDate'+ltcJrnyTableIdlen+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="ltcJrnyPassTableIdlen'+ltcJrnyTableIdlen+'" value="'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'</tr>';
		 
		 innerHtmlValue=innerHtmlValue+'</tbody></table>';
		 
		 cell0.innerHTML=innerHtmlValue; 
		
		
		 setModeOfTravel(ltcJrnyTableIdlen);
		 setTravellerName(ltcJrnyTableIdlen,ltcJrnyPassTableIdlen);
		 
		 
		 $('#nonDTSJryStartDate'+ltcJrnyTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                                scrollInput:false,yearEnd : 2100 ,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateLTCFromDate(3,ltcJrnyTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryStartDate'+ltcJrnyTableIdlen).val("");
		} });
		 $('#nonDTSJryEndDate'+ltcJrnyTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                                scrollInput:false,yearEnd : 2100 ,defaultDate:$("#travel_Start_Date").val(),onSelectDate: function(current_time,$input){validateLTCToDate(3,ltcJrnyTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryEndDate'+ltcJrnyTableIdlen).val("");
		} });
	}
	
}

function addLTCJrnyTraveller(index){
	
	if(valideteNonDtsLTCPassDetails(index)){
		ltcJrnyPassTableIdlen++;
		 var innerHtmlValue=innerHtmlValue+'<tr class="lablevalue">';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsTravellerName'+index+'" id="nonDTSPassDtlsTravellerName'+index+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="setTravellerRelationAndAge(this.value,'+index+','+ltcJrnyPassTableIdlen+');"><option value="">Select</option></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRelation'+index+'" id="nonDTSPassDtlsRelation'+index+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:95px;" onkeypress="return isNumberKey(event)" readonly="true"></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsAge'+index+'" id="nonDTSPassDtlsAge'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:40px;" maxlength="3" onkeypress="return isNumberKey(event)" readonly="true"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsTicketNo'+index+'" id="nonDTSPassDtlsTicketNo'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:115px;;" maxlength="50" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsJourneyPerformed'+index+'" id="nonDTSPassDtlsJourneyPerformed'+index+'_'+ltcJrnyPassTableIdlen+'" class="combo" style="width:65px;" onchange="takeLTCNonDTSCancellation(this.value,'+index+','+ltcJrnyPassTableIdlen+');"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select></td>';
		 
		 if($("#nonDTSModeOfTravel"+index).val()=='Train'){
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+index+'" id="nonDTSPassDtlsBookingAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+index+'" id="nonDTSPassDtlsRefundAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+index+'" id="nonDTSPassDtlsClaimedAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/>&#160;<input type="button"  value="X" class="butn" onclick="deleteLTCJrnyTraveller(this,'+index+')"></td>';
		 }else{
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+index+'" id="nonDTSPassDtlsBookingAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSBookingAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+index+'" id="nonDTSPassDtlsRefundAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSRefundAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+index+'" id="nonDTSPassDtlsClaimedAmount'+index+'_'+ltcJrnyPassTableIdlen+'" class="txtfldM" style="width:70px;" maxlength="8" onblur="addNonDTSClaimedAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/>&#160;<input type="button"  value="X" class="butn" onclick="deleteLTCJrnyTraveller(this,'+index+')"></td>';
		
		 }
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanType'+index+'" id="nonDTSJrnyCanType'+index+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanNo'+index+'" id="nonDTSJrnyCanSanNo'+index+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanDate'+index+'" id="nonDTSJrnyCanSanDate'+index+'_'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="ltcJrnyPassTableIdlen'+index+'" value="'+ltcJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'</tr>';
		 
	    $("#nonDTSJrnyPassDetls"+index).append(innerHtmlValue);
	    setTravellerName(index,ltcJrnyPassTableIdlen);
	    
	} 
}

function valideteNonDtsLTCPassDetails(index){
	
	var passTravellerName = $("#voucherSettlementConfirmationForm select[name=nonDTSPassDtlsTravellerName"+index+"]");
	if(passTravellerName.length > 0){
		for(var i=0;i<passTravellerName.length;i++){
			if(passTravellerName[i].value==""){
				alert("Please select Traveler Name");
				return false;
			}
		}
	}
	
	var passRelation = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRelation"+index+"]");
	if(passRelation.length > 0){
		for(var i=0;i<passRelation.length;i++){
			if(passRelation[i].value==""){
				alert("Please select Relation");
				return false;
			}
		}
	}
	
	var passAge = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsAge"+index+"]");
	if(passAge.length > 0){
		for(var i=0;i<passAge.length;i++){
			if(passAge[i].value==""){
				alert("Please enter Age");
				passAge[i].focus();
				return false;
			}
		}
	}
	
	var passTicketNo = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsTicketNo"+index+"]");
	if(passTicketNo.length > 0){
		for(var i=0;i<passTicketNo.length;i++){
			if(passTicketNo[i].value==""){
				alert("Please enter Ticket No.");
				passTicketNo[i].focus();
				return false;
			}
		}
	}
	
	var passJourneyPerformed = $("#voucherSettlementConfirmationForm select[name=nonDTSPassDtlsJourneyPerformed"+index+"]");
	if(passJourneyPerformed.length > 0){
		for(var i=0;i<passJourneyPerformed.length;i++){
			if(passJourneyPerformed[i].value==""){
				alert("Please select Journey Performed");
				return false;
			}
		}
	}
	
	var passBookingAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
	if(passBookingAmount.length > 0){
		for(var i=0;i<passBookingAmount.length;i++){
			if(passBookingAmount[i].value==""){
				alert("Please enter Booking Amount");
				passBookingAmount[i].focus();
				return false;
			}
		}
	}
	
	var passRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	if(passRefundAmount.length > 0){
		for(var i=0;i<passRefundAmount.length;i++){
			if(passRefundAmount[i].value==""){
				alert("Please enter Refund Amount");
				passRefundAmount[i].focus();
				return false;
			}
		}
	}
	
	var passClaimedAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
	if(passClaimedAmount.length > 0){
		for(var i=0;i<passClaimedAmount.length;i++){
			if(passClaimedAmount[i].value==""){
				alert("Please enter Claimed Amount");
				passClaimedAmount[i].focus();
				return false;
			}
		}
	}
	
	return true;
}

function addNonDTSBookingAmount(index){
	
	var passBookingAmt = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
	var totalAmount=0;
	
	if(passBookingAmt.length > 0){
		for(var i=0;i<passBookingAmt.length;i++){
			totalAmount=totalAmount+Number(passBookingAmt[i].value);
		}
	}
	
	$("#nonDTSJryAmount"+index).val(parseInt(totalAmount));
}

function addNonDTSClaimedAmount(index){
	
	var passClaimedAmt = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
	var totalAmount=0;
	
	if(passClaimedAmt.length > 0){
		for(var i=0;i<passClaimedAmt.length;i++){
			totalAmount=totalAmount+Number(passClaimedAmt[i].value);
		}
	}
	
	$("#nonDTSJryClaimAmount"+index).val(parseInt(totalAmount));
	
	if($("#travel_Type_Id").val()=='100001'){
		calculatePTFinalAmount();
	}else{
	calculateLTCFinalAmount();
}
}

function addNonDTSRefundAmount(index){
	
	var passRefundAmt = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	var totalAmount=0;
	
	if(passRefundAmt.length > 0){
		for(var i=0;i<passRefundAmt.length;i++){
			totalAmount=totalAmount+Number(passRefundAmt[i].value);
		}
	}
	
	$("#nonDTSJryRefundAmount"+index).val(parseInt(totalAmount));
}

function deleteLTCJrnyTraveller(obj,index){
	var delRow = obj.parentNode.parentNode;
	var rIndex = delRow.sectionRowIndex;
	delRow.parentNode.deleteRow(rIndex);
	
	if($("#nonDTSModeOfTravel"+index).val()!='Train'){
	addNonDTSBookingAmount(index);
	addNonDTSClaimedAmount(index);
	addNonDTSRefundAmount(index);
}
}

function deleteLTCJrnyDetails(index){
	$("#nonDTSLTCJrnyDetls"+index).remove();
	calculateLTCFinalAmount();
}

function valideteLTCJrnyDetails(){
	
	var nonDTSJourneyType = $("#voucherSettlementConfirmationForm select[name=nonDTSJourneyType]");
	
	if(nonDTSJourneyType.length==0){
		return true;
	}else{
		
		if(nonDTSJourneyType.length > 0){
			for(var i=0;i<nonDTSJourneyType.length;i++){
				if(nonDTSJourneyType[i].value==""){
					alert("Please select Journey Type");
					nonDTSJourneyType[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSModeOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSModeOfTravel]");
		if(nonDTSModeOfTravel.length > 0){
			for(var i=0;i<nonDTSModeOfTravel.length;i++){
				if(nonDTSModeOfTravel[i].value==""){
					alert("Please select Mode Of Travel");
					nonDTSModeOfTravel[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSFromPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSFromPlace]");
		if(nonDTSFromPlace.length > 0){
			for(var i=0;i<nonDTSFromPlace.length;i++){
				if(nonDTSFromPlace[i].value==""){
					alert("Please enter Departure Place");
					nonDTSFromPlace[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSToPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSToPlace]");
		if(nonDTSToPlace.length > 0){
			for(var i=0;i<nonDTSToPlace.length;i++){
				if(nonDTSToPlace[i].value==""){
					alert("Please enter Arrival Place");
					nonDTSToPlace[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSClassOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSClassOfTravel]");
		if(nonDTSClassOfTravel.length > 0){
			for(var i=0;i<nonDTSClassOfTravel.length;i++){
				if(nonDTSClassOfTravel[i].value==""){
					alert("Please select Class of Accomodation");
					nonDTSClassOfTravel[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryStartDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryStartDate]");
		if(nonDTSJryStartDate.length > 0){
			for(var i=0;i<nonDTSJryStartDate.length;i++){
				if(nonDTSJryStartDate[i].value==""){
					alert("Please select Departure Time");
					nonDTSJryStartDate[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryEndDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryEndDate]");
		if(nonDTSJryEndDate.length > 0){
			for(var i=0;i<nonDTSJryEndDate.length;i++){
				if(nonDTSJryEndDate[i].value==""){
					alert("Please select Arrival Time");
					nonDTSJryEndDate[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSSector = $("#voucherSettlementConfirmationForm input[name=nonDTSSector]");
		if(nonDTSSector.length > 0){
			for(var i=0;i<nonDTSSector.length;i++){
				if(nonDTSSector[i].value==""){
					alert("Please enter Sector");
					nonDTSSector[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSTicketNo = $("#voucherSettlementConfirmationForm input[name=nonDTSTicketNo]");
		if(nonDTSTicketNo.length > 0){
			for(var i=0;i<nonDTSTicketNo.length;i++){
				if(nonDTSTicketNo[i].value==""){
					alert("Please enter Bill/PNR/ Ticket No");
					nonDTSTicketNo[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSDistanceKM = $("#voucherSettlementConfirmationForm input[name=nonDTSDistanceKM]");
		if(nonDTSDistanceKM.length > 0){
			for(var i=0;i<nonDTSDistanceKM.length;i++){
				if(nonDTSDistanceKM[i].value==""){
					alert("Please enter Distance");
					nonDTSDistanceKM[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSJryRefundAmount]");
		if(nonDTSJryRefundAmount.length > 0){
			for(var i=0;i<nonDTSJryRefundAmount.length;i++){
				if(nonDTSJryRefundAmount[i].value==""){
					alert("Please enter Total Refund");
					nonDTSJryRefundAmount[i].focus();
					return false;
				}
			}
		}
		
		
	
	    
	    var ltcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ltcJrnyTableIdlen]");
	    if(ltcJrnyTableIdlen.length > 0){
	    	for(var i=0;i<ltcJrnyTableIdlen.length;i++){
	    		if(!valideteNonDtsLTCPassDetails(ltcJrnyTableIdlen[i].value)){return false;}
	    	}
	    }
	    
		return true;
	}
	
}

function validateLTCToDate(type,index){

	if(1==type)
	{
		var dateFrom = document.getElementById("leave_From").value;
		var dateTo = document.getElementById("leave_To").value;
		
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select Leave from date first.");
	 		document.getElementById("leave_To").value='';
	 	}
	 	 
		 var dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	 
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Leave end date cannot be less than Leave start date.");
	 		document.getElementById("leave_To").value='';
	 		return false;
	 	}
	 	
	 }
    
    if(2==type)
	{
	 	var dateFrom = document.getElementById("journey_date_"+index).value;
	 	var dateTo = document.getElementById("journey_end_date_"+index).value;
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select Journey Start Date first.");
	 		document.getElementById("journey_end_date_"+index).value='';
	 	}
	 	 
		 var dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	 
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Departure date cannot be less than from date.");
	 		document.getElementById("journey_end_date_"+index).value='';
	 	}
	 	
	 }
	 if(3==type)
	{
	 	var dateFrom = document.getElementById("nonDTSJryStartDate"+index).value;
	 	var dateTo = document.getElementById("nonDTSJryEndDate"+index).value;
	 	if(''==dateFrom && ''!=dateTo)
	 	{
	 		alert("Please select departure date first.");
	 		document.getElementById("nonDTSJryEndDate"+index).value='';
	 		document.getElementById("nonDTSJryStartDate"+index).focus();
	 	}
	 	 
		var dateOnwardToTime = calculateNumberOfDays(dateFrom,dateTo);
	 	
	 	if(dateOnwardToTime<1)
	 	{
	 		alert("Journey End Date cannot be less than Journey Start Date.");
	 		document.getElementById("nonDTSJryEndDate"+index).value='';
	 	}
	 	
	 }
}

function validateLTCFromDate(type,index)
{
	if(1==type)
	{
		var dateFrom = document.getElementById("leave_From").value;
		var dateTo = document.getElementById("leave_To").value;
	 	
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Leave Start Date cannot be greater than to Leave End Date.")
				document.getElementById("leave_From").value='';
				return false;
			}
	 	}
	 	
	 }
	 
	if(2==type)
	{
		var dateFrom = document.getElementById("journey_date_"+index).value;
		var dateTo = document.getElementById("journey_end_date_"+index).value;
		
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Journey Start Date cannot be greater than to Journey End Date.")
				document.getElementById("journey_date_"+index).value='';
				return false;
			}	
	 	}
	 	
	 }
	
	
	if(3==type)
	{
		var dateFrom = document.getElementById("nonDTSJryStartDate"+index).value;
		var dateTo = document.getElementById("nonDTSJryEndDate"+index).value;
		
	 	if(''!=dateTo)
	 	{
	 		if(calculateNumberOfDays(dateFrom,dateTo)<0)
			{
				alert("Arrival Date cannot be greater than to Departure Date.")
				document.getElementById("nonDTSJryStartDate"+index).value='';
				return false;
			}	
	 	}
	 	
	 }
}

function validateLTCClaimForm(){
	
	if($("#personalDesignation").val().trim()==''){
		alert("Please enter Designation");
		$("#personalDesignation").focus();
		return false;
	}
	if($("#moveSanctionNo").val().trim()==''){
		alert("Please enter Move Sanction No");
		$("#moveSanctionNo").focus();
		return false;
	}
	if($("#move_Sanction_Date").val().trim()==''){
		alert("Please select Move Sanction Date");
		$("#move_Sanction_Date").focus();
		return false;
	}
	if($("#leave_From").val().trim()==''){
		alert("Please select Leave from Date");
		$("#leave_From").focus();
		return false;
	}
	if($("#leave_To").val().trim()==''){
		alert("Please select Leave to Date");
		$("#leave_To").focus();
		return false;
	}
	if($("#start_date_time").val().trim()==''){
		alert("Please select Date/Time of start");
		$("#start_date_time").focus();
		return false;
	}
	if($("#personalHomeTown").val().trim()==''){
		alert("Please enter Home Town");
		$("#personalHomeTown").focus();
		return false;
	}
	if($("#declaredLeaveStation").val().trim()==''){
		alert("Please enter Declared Leave Station");
		$("#declaredLeaveStation").focus();
		return false;
	}
	if($("#ltcCalendarYear").val().trim()==''){
		alert("Please enter LTC Calendar Year");
		$("#ltcCalendarYear").focus();
		return false;
	}
	
	
	var dtsJourneyDate = $("#voucherSettlementConfirmationForm input[name=dtsJourneyDate]");
	
	for(var index=0;index<dtsJourneyDate.length;index++){
		if(dtsJourneyDate[index].value==''){
		   alert("Please select DTS Journey Start Date");
		   dtsJourneyDate[index].focus();
		   return false;
		}
		
		if($("#journey_end_date_"+index).val().trim()==''){
			alert("Please select DTS Journey End Date");
			$("#journey_end_date_"+index).focus();
		    return false;
		}
	}
	
	var dtsLtcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=dtsLtcJrnyTableIdlen]");
	var jrnyFlag=true;
	for(var index=0;index<dtsLtcJrnyTableIdlen.length;index++){
		var val=dtsLtcJrnyTableIdlen[index].value;
		var dtsJourneyPerformed = $("#voucherSettlementConfirmationForm select[name=dtsJourneyPerformed"+val+"]");
		for(var i=0;i<dtsJourneyPerformed.length;i++){
			if(dtsJourneyPerformed[i].value==''){
			   alert("Please select DTS Journey Performed");
			   jrnyFlag= false;
			   break;
			}
		}
		if(!jrnyFlag){return false;}
	}
	
	if(!valideteLTCJrnyDetails()){return false;}
    
    if($("#Adv_From_PAO").val().trim()==''){
    	alert("Please enter Advance Drawn from PAO");
    	$("#Adv_From_PAO").focus();
		return false;
    }
    
    if(parseInt($("#Adv_From_PAO").val())>0){
	    if($("#Adv_From_PAO_Date").val().trim()==''){
	    	alert("Please enter Date of Advance Drawn from PAO");
	    	$("#Adv_From_PAO_Date").focus();
			return false;
	    }
    }
    
     if($("#Adv_From_Other").val().trim()==''){
    	alert("Please enter Advance Drawn from Other Sources");
    	$("#Adv_From_Other").focus();
		return false;
    }
    
    if(parseInt($("#Adv_From_Other").val())>0){
    	
    	if($("#Adv_From_Other_VNo").val().trim()==''){
    	alert("Please enter Voucher No for Advance Drawn from Other Sources");
    	$("#Adv_From_Other_VNo").focus();
		return false;
       }
    
	    if($("#Adv_From_Other_Date").val().trim()==''){
	    	alert("Please enter Date of Advance Drawn from Other Sources");
	    	$("#Adv_From_Other_Date").focus();
			return false;
	    }
    	
    }
    
     if($("#Advance_MRO").val().trim()==''){
    	alert("Please enter MRO/Refund");
    	$("#Advance_MRO").focus();
		return false;
    }
    
    if(parseInt($("#Advance_MRO").val())>0){
    	
    	if($("#MRO_Number").val().trim()==''){
    	alert("Please enter eMRO/MRO/Refund No");
    	$("#MRO_Number").focus();
		return false;
       }
    
	    if($("#MRO_Date").val().trim()==''){
	    	alert("Please enter eMRO/MRO Submission Date/Refund Date");
	    	$("#MRO_Date").focus();
			return false;
	    }
	    
    }
    
    var flag=true;
    
    $('input:checkbox[name=claimCertify]').each(function(){
             if($(this).is(':checked')){}
             else{
             	alert("Please check on all the checkboxes in Certify that Tab.");
		        flag= false;
		        return false;
             }
    });
    
    if(flag){
    	
    	if($("#userServiceType").val()== '1'){
    		
    	}else{
	    	if($("#counterPersonalNo").val().trim()==''){
	    	alert("Please enter Counter Singing Authority Personal No");
	    	$("#counterPersonalNo").focus();
			return false;
	        }
        }
        if($("#claimPreferredDate").val().trim()==''){
    	alert("Please enter Date of Claim preferred by the Individual to the Office");
    	$("#claimPreferredDate").focus();
		return false;
        }
        if($("#claimPreferredSanction").val().trim()==''){
    	alert("Please Select Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken");
    	$("#claimPreferredSanction").focus();
		return false;
        }
        
        if($("#claimPreferredSanction").val().trim()=='Yes'){
        	if($("#claimPreferredSanctionNo").val().trim()==''){
	    	alert("Please enter Claim preferred Sanction No");
	    	$("#claimPreferredSanctionNo").focus();
			return false;
	        }
	        if($("#claimPreferredSanctionDate").val().trim()==''){
	    	alert("Please enter Date of Claim preferred Sanction");
	    	$("#claimPreferredSanctionDate").focus();
			return false;
	        }
        }
    }
	return flag;
}

function calculateLTCFinalAmount(){
	
	var totalClaimedAmount=0;
	var totalDTSJrnyAmount=0;
	var totalDTSRefund=0;
	
	var dtsLtcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=dtsLtcJrnyTableIdlen]");
	
	if(dtsLtcJrnyTableIdlen.length > 0){
		for(var index=0; index<dtsLtcJrnyTableIdlen.length; index++){
			totalClaimedAmount=totalClaimedAmount+Number($("#jrnyClaimedAmount"+dtsLtcJrnyTableIdlen[index].value).val());
			totalDTSJrnyAmount=totalDTSJrnyAmount+Number($("#jrnyClaimedAmount"+dtsLtcJrnyTableIdlen[index].value).val());
			totalDTSRefund=totalDTSRefund+Number($("#jrnyRefundAmount"+dtsLtcJrnyTableIdlen[index].value).val());
		}
	}
	
	var ltcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ltcJrnyTableIdlen]");
	
	if(ltcJrnyTableIdlen.length > 0){
		for(var index=0; index<ltcJrnyTableIdlen.length; index++){
			totalClaimedAmount=totalClaimedAmount+Number($("#nonDTSJryClaimAmount"+ltcJrnyTableIdlen[index].value).val());
		}
	}

	var recoveryAmount = 0;
	if ($("#irlaRecoveryAmt").length != 0) {
		recoveryAmount = Number($("#irlaRecoveryAmt").val());
	}
//	var recoveryAmount=Number($("#irlaRecoveryAmt").val())+Number($("#recoveryAmt").val());
	var totalSpentAmount=Number(totalClaimedAmount);
	var totalAdvanceTaken=Number(totalDTSJrnyAmount)+Number($("#Adv_From_PAO").val())+Number($("#Adv_From_Other").val());
	var totalRefundAmount=Number($("#Advance_MRO").val());

	var totalPayAmount=Number(totalSpentAmount)+Number(totalRefundAmount)-Number(totalAdvanceTaken)+Number(recoveryAmount);

	$("#total_spent_amount").html(parseFloat(totalSpentAmount).toFixed(2));
	$("#total_advance_amount").html(parseFloat(totalAdvanceTaken).toFixed(2));
	$("#total_refund_amount").html(parseFloat(totalRefundAmount).toFixed(2));
	$("#total_claimed_amount").html(parseFloat(totalPayAmount).toFixed(2));
	
	$("#totalSpentAmount").val(parseFloat(totalSpentAmount).toFixed(2));
	$("#totalAdvanceAmount").val(parseFloat(totalAdvanceTaken).toFixed(2));
	$("#totalRefundAmount").val(parseFloat(totalRefundAmount).toFixed(2));
	$("#totalClaimedAmount").val(parseFloat(totalPayAmount).toFixed(2));
	
}

function setAirportsAndRailStation(index){
	
	$("#nonDTSSector"+index).val('');
	
	if($("#nonDTSModeOfTravel"+index).val()=="Flight"){
		
		var fromHTML='<div id="container"><div>' +
				'<input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off" onkeyup="getClaimAirportList(this.id,'+index+');" onblur="fillClaimAirport(this.id,\'' +'\','+index+');"/>' +
				'</div><div class="suggestionsBox" id="suggestionsnonDTSFromPlace'+index+'" style="display: none;">' +
				'<div class="suggestionList" id="autoSuggestionsListnonDTSFromPlace'+index+'"></div></div></div>'; 
	
	   var toHTML='<div id="container"><div>' +
				'<input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off" onkeyup="getClaimAirportList(this.id,'+index+');" onblur="fillClaimAirport(this.id,\'' +'\','+index+');"/>' +
				'</div><div class="suggestionsBox" id="suggestionsnonDTSToPlace'+index+'" style="display: none;">' +
				'<div class="suggestionList" id="autoSuggestionsListnonDTSToPlace'+index+'"></div></div></div>'; 	
				
	  $("#nonDTS_From"+index).html(fromHTML);
	  $("#nonDTS_To"+index).html(toHTML);	
	  
	  $("#nonDTSJryAmount"+index).prop("readonly", true);	
	  $("#nonDTSJryRefundAmount"+index).prop("readonly", true);	
	  $("#nonDTSJryClaimAmount"+index).prop("readonly", true);	
	  
	   var nonDTSPassDtlsBookingAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
	   var nonDTSPassDtlsRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	   var nonDTSPassDtlsClaimedAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
		if(nonDTSPassDtlsRefundAmount.length > 0){
			for(var i=0;i<nonDTSPassDtlsRefundAmount.length;i++){
				nonDTSPassDtlsBookingAmount[i].removeAttribute("readonly");
				nonDTSPassDtlsRefundAmount[i].removeAttribute("readonly");
				nonDTSPassDtlsClaimedAmount[i].removeAttribute("readonly");
				
				nonDTSPassDtlsBookingAmount[i].setAttribute("onblur","addNonDTSBookingAmount("+index+")");
				nonDTSPassDtlsRefundAmount[i].setAttribute("onblur","addNonDTSRefundAmount("+index+")");
				nonDTSPassDtlsClaimedAmount[i].setAttribute("onblur","addNonDTSClaimedAmount("+index+")");
			}
		}			
	
		
	}else if($("#nonDTSModeOfTravel"+index).val()=="Train"){
		
		var fromHTML='<div id="container"><div>' +
				'<input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off" onkeyup="getClaimRailStationList(this.id,'+index+');" onblur="fillClaimRailStation(this.id,\'' +'\','+index+');"/>' +
				'</div><div class="suggestionsBox" id="suggestionsnonDTSFromPlace'+index+'" style="display: none;">' +
				'<div class="suggestionList" id="autoSuggestionsListnonDTSFromPlace'+index+'"></div></div></div>'; 
	
	   var toHTML='<div id="container"><div>' +
				'<input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off" onkeyup="getClaimRailStationList(this.id,'+index+');" onblur="fillClaimRailStation(this.id,\'' +'\','+index+');"/>' +
				'</div><div class="suggestionsBox" id="suggestionsnonDTSToPlace'+index+'" style="display: none;">' +
				'<div class="suggestionList" id="autoSuggestionsListnonDTSToPlace'+index+'"></div></div></div>'; 
				
	  $("#nonDTS_From"+index).html(fromHTML);
	  $("#nonDTS_To"+index).html(toHTML);	
	  
	  $("#nonDTSJryAmount"+index).prop("readonly", false);	
	  $("#nonDTSJryRefundAmount"+index).prop("readonly", false);
	  $("#nonDTSJryClaimAmount"+index).prop("readonly", false);
	  
	   var nonDTSPassDtlsBookingAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
	    var nonDTSPassDtlsRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	   var nonDTSPassDtlsClaimedAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
		if(nonDTSPassDtlsRefundAmount.length > 0){
			for(var i=0;i<nonDTSPassDtlsRefundAmount.length;i++){
				nonDTSPassDtlsBookingAmount[i].value=0;
				nonDTSPassDtlsBookingAmount[i].setAttribute("readonly","readonly");
				
				nonDTSPassDtlsRefundAmount[i].value=0;
				nonDTSPassDtlsRefundAmount[i].setAttribute("readonly","readonly");
				
				nonDTSPassDtlsClaimedAmount[i].value=0;
				nonDTSPassDtlsClaimedAmount[i].setAttribute("readonly","readonly");
				
				nonDTSPassDtlsBookingAmount[i].removeAttribute("onblur");
				nonDTSPassDtlsRefundAmount[i].removeAttribute("onblur");
				nonDTSPassDtlsClaimedAmount[i].removeAttribute("onblur");
			}
		}
		
	}else{
		$("#nonDTS_From"+index).html('<input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off"/>');
		$("#nonDTS_To"+index).html('<input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+index+'" class="txtfldM" maxlength="50" autocomplete="off"/>');
		
		$("#nonDTSJryAmount"+index).prop("readonly", true);	
		$("#nonDTSJryRefundAmount"+index).prop("readonly", true);	
	    $("#nonDTSJryClaimAmount"+index).prop("readonly", true);	
		
		var nonDTSPassDtlsBookingAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
		 var nonDTSPassDtlsRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	    var nonDTSPassDtlsClaimedAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
		 if(nonDTSPassDtlsRefundAmount.length > 0){
			for(var i=0;i<nonDTSPassDtlsRefundAmount.length;i++){
				
				nonDTSPassDtlsBookingAmount[i].removeAttribute("readonly");
				nonDTSPassDtlsRefundAmount[i].removeAttribute("readonly");
				nonDTSPassDtlsClaimedAmount[i].removeAttribute("readonly");
				
				nonDTSPassDtlsBookingAmount[i].setAttribute("onblur","addNonDTSBookingAmount("+index+")");
				nonDTSPassDtlsRefundAmount[i].setAttribute("onblur","addNonDTSRefundAmount("+index+")");
				nonDTSPassDtlsClaimedAmount[i].setAttribute("onblur","addNonDTSClaimedAmount("+index+")");
			}
		 }
	}
}

function fillClaimAirport(id,thisValue,index) {
 $('#'+id).val(thisValue);
 setTimeout(function (){$('#suggestions'+id).hide();}, 200);
 setNonDTSJrnySector(index);
 } 
 
 function fillClaimRailStation(id,thisValue,index) {
 $('#'+id).val(thisValue);
 setTimeout(function (){$('#suggestions'+id).hide();}, 200);
 setNonDTSJrnySector(index);
 }
 
 function setNonDTSJrnySector(index){
 	
 	var fromStation=$("#nonDTSFromPlace"+index).val();
 	var toStation=$("#nonDTSToPlace"+index).val();
 	
 	if(fromStation.trim()!="" && toStation.trim()!=""){
 		$("#nonDTSSector"+index).val(fromStation.substring(fromStation.indexOf("(")+1,fromStation.indexOf(")"))+'-'+toStation.substring(toStation.indexOf("(")+1,toStation.indexOf(")")));
 	}else{
 		$("#nonDTSSector"+index).val('');
 	}
 } 
 
function getClaimAirportList(id,index) {
	var airportName=$('#'+id).val();
	if(airportName.length>1){
	var response="";
	var AirportList="";
	$.ajax({
			url: $("#context_path").val() + "mb/getNAPForClaim",
			type: "post",
			data: "airportName=" + airportName,
			datatype: "application/json",
			success: function(msg) {
				$.each(msg, function(idx, name) {
//					var name = $(this).find('AirportName').text()
					if (name == "Airport Name Does Not Exist")
						AirportList += '<li>' + name + '</li>';
					else
						AirportList += '<li onClick="fillClaimAirport(\'' + id + '\',\'' + name + '\',' + index + ')">' + name + '</li>';
				});
				$("#autoSuggestionsList" + id).html(AirportList);
				$('#suggestions' + id).show();
	  }});
	}
}

function getClaimRailStationList(id,index) {
	var stationName=$('#'+id).val();

	if(stationName.length>1){
	var response="";
	var StationList="";
	$.ajax({
			url: $("#context_path").val() + "mb/getStationListForClaim",
			type: "post",
			data: "stationName=" + stationName,
			datatype: "application/json",
			success: function(msg) {
//				msg = parseXML(msg);
				$.each(msg, function(idx, name) {
						StationList += '<li onClick="fillClaimRailStation(\'' + id + '\',\'' + name + '\',' + index + ')">' + name + '</li>';
				});

				$("#autoSuggestionsList" + id).html(StationList);
				$('#suggestions' + id).show();
	     }});
	}
}

function isTravellerExist(val,index){
	var passTravellerName = $("#voucherSettlementConfirmationForm select[name=nonDTSPassDtlsTravellerName"+index+"]");
	if(passTravellerName.length > 0){
		for(var i=0;i<passTravellerName.length-1;i++){
			if(passTravellerName[i].value==val){
				alert("Traveler already selected");
				passTravellerName[passTravellerName.length-1].value="";
				return false;
			}
		}
	}
	return true;
}

 function validatePTPAOEditForm(type,index){
 	
 	var flag=validatePTPassedAmountData(type,index);
 	
 	calculatePTPAOEditAmount();
 	return flag;
 }
 
function validateAuditorPTPAOEditForm(){
 	
 	var flag=validatePTPAOEditData();
 	
 	calculatePTPAOEditAmount();
 	return flag;
 } 
 
function validatePTPassedAmountData(type,index){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
 	var personalEffSeq = $("#claimEditPAOForm input[name=personalEffSeq]");
 	var personalEffType = $("#claimEditPAOForm input[name=personalEffType]");
	
	var taLength=0;
	var personalEffLength=0;
	
	if(type=='personalEff'){
		personalEffLength=parseInt(index);
	}
	if(type=='TAJrny'){
		taLength=parseInt(index);
	}
	
	if(index==''){
		taLength=tadaJrySeq.length;
		personalEffLength=personalEffSeq.length;
	}
	
	for(var index=0;index<taLength;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
	}
	
	if(type=='ctgAdv'){
		
		var claimAmt=$("#ctgClaimAmount").val();
		var basicPay=$("#personalBasicPay").val();
		var maxctg=0;
		
	    var trnsType=$("#CTGTransferType").val();
	    
	    if(trnsType=="1"){
	    	maxctg=parseInt(parseInt(basicPay)*0.8);
	    }else if(trnsType=="2"){
	    	maxctg=parseInt(basicPay);
	    }else if(trnsType=="3"){
	    	maxctg=parseInt((parseInt(basicPay)*0.8)/3);
	    }else{}
		
		if($("#sanCTGAmt").val()==''){
			alert("CTG Passed Amount can not be blank");
			$("#sanCTGAmt").focus();
			return false;
		}
		
		if(parseInt(maxctg)>parseInt(claimAmt)){
			maxctg=parseInt(claimAmt);
		}
		
		if(parseInt($("#sanCTGAmt").val())>parseInt(maxctg)){
			alert("CTG Passed Amount can not greater than "+maxctg);
			$("#sanCTGAmt").val(0);
			$("#sanCTGAmt").focus();
			return false;
		}
	}
	
	for(var index=0;index<personalEffLength;index++){
		var val=personalEffSeq[index].value;
		var sanAmount=$("#sanPersonalEffectsAmt_"+val).val();
		var actualTaAmount=$("#personalEffectsAmount_"+val).val();
		
		if(sanAmount==''){
			alert("Personal Effects Passed Amount can not be blank");
			$("#sanPersonalEffectsAmt_"+val).focus();
			return false;
		}
		
			var maxAmount=$("#personalEffectsAllowedAmount_"+val).val();;
			if(parseInt(actualTaAmount)>parseInt(maxAmount)){
				actualTaAmount=parseInt(maxAmount);
			}
		
		
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Personal Effects Passed Amount can not greater than "+actualTaAmount);
			$("#sanPersonalEffectsAmt_"+val).val(0);
			$("#sanPersonalEffectsAmt_"+val).focus();
			return false;
		}
		
	}
	
	if(type=='paoAdv'){
		if($("#sanPAOAdvanceAmt").val()==''){
			alert("Advance Drawn from PAO Passed Amount can not be blank");
			$("#sanPAOAdvanceAmt").focus();
			return false;
		}
		
	}
	
	if(type=='MRORef'){
		if($("#isMROReceived").val()==''){
			alert("Please Select MRO Received");
			return false;
		}
		
		if($("#sanMRORefundAmt").val()==''){
			alert("eMRO/MRO/ Refund Passed Amount can not be blank");
			$("#sanMRORefundAmt").focus();
			return false;
		}
		
		if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
			alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
			$("#sanMRORefundAmt").val(0);
			$("#sanMRORefundAmt").focus();
			return false;
		}
	
	}
	
	if(type=='recovery'){
		
		if($("#recoveryPassAmt").val()==''){
			alert("Recovery Passed Amount can not be blank");
			$("#recoveryPassAmt").focus();
			return false;
		}
		
		var totalRecovery=Number($("#recoveryAmt").val())+Number($("#irlaRecoveryAmt").val())
		
		if(Number($("#recoveryPassAmt").val())>Number(totalRecovery)){
			alert("Recovery Passed Amount can not greater than "+totalRecovery);
			$("#recoveryPassAmt").val(0);
			$("#recoveryPassAmt").focus();
			return false;
		}
	
	}
	
	return true;
 }  
 
 function validatePTPAOEditData(){
 	
 	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		var sanAmount=$("#dtsJrySanAmount"+val).val();
		var deductionReason=$("#JrnyDeductionReason"+val).val();
		var actualTaAmount=$("#actualTaAmount"+val).val();
		var jrnyBKGFrom=$("#jrnyBKGFrom"+val).val();
		
		if(sanAmount==''){
			alert("Journey Details Passed Amount can not be blank");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Journey Details Passed Amount can not greater than "+actualTaAmount);
			$("#dtsJrySanAmount"+val).val(0);
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if(jrnyBKGFrom=='NON-DTS' && parseInt(sanAmount)!=parseInt(actualTaAmount) && deductionReason==''){
			alert("Please Select Journey Details Reason for Deduction");
			$("#dtsJrySanAmount"+val).focus();
			return false;
		}
		
		if($("#userServiceType").val()=='1'){
			var isPaymentMade=$("#isPaymentMade"+val).val();
			if(isPaymentMade == 'undefined' || isPaymentMade == null ){}
			else{
				if(isPaymentMade == ''){
					alert("Please Select Journey Details Payment Made Option.");
					return false;
				}
				var jrnyPaymentTo=$("#jrnyPaymentTo"+val).val();
				if(jrnyPaymentTo == ''){
					alert("Please Select Journey Details Payment To Option.");
					return false;
				}
			}
		}
	}
	
	if($("#sanCTGAmt").val()==''){
		alert("CTG Passed Amount can not be blank");
		$("#sanCTGAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanCTGAmt").val())>parseInt($("#ctgClaimAmount").val())){
		alert("CTG Passed Amount can not greater than "+$("#ctgClaimAmount").val());
		$("#sanCTGAmt").val(0);
		$("#sanCTGAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanCTGAmt").val()) != parseInt($("#ctgClaimAmount").val()) && $("#ctgReason").val()==''){
		alert("Please Select CTG Reason for Deduction");
		$("#sanCTGAmt").focus();
		return false;
	}
	
	var personalEffSeq = $("#claimEditPAOForm input[name=personalEffSeq]");
	
	for(var index=0;index<personalEffSeq.length;index++){
		var val=personalEffSeq[index].value;
		var sanAmount=$("#sanPersonalEffectsAmt_"+val).val();
		var actualTaAmount=$("#personalEffectsAmount_"+val).val();
		var deductionReason=$("#personalEffectsReason_"+val).val();
		
		if(sanAmount==''){
			alert("Personal Effects Passed Amount can not be blank");
			$("#sanPersonalEffectsAmt_"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount)>parseInt(actualTaAmount)){
			alert("Personal Effects Passed Amount can not greater than "+actualTaAmount);
			$("#sanPersonalEffectsAmt_"+val).val(0);
			$("#sanPersonalEffectsAmt_"+val).focus();
			return false;
		}
		
		if(parseInt(sanAmount)!=parseInt(actualTaAmount) && deductionReason==''){
			alert("Please Select Personal Effects Reason for Deduction");
			$("#sanPersonalEffectsAmt_"+val).focus();
			return false;
		}
		
	}
	
	if($("#sanPAOAdvanceAmt").val()==''){
		alert("Advance Drawn from PAO Passed Amount can not be blank");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanPAOAdvanceAmt").val()) != parseInt($("#paoAdvanceAmount").val()) && $("#paoAdvDeductionReason").val()==''){
		alert("Please Select Advance Drawn from PAO Reason for Deduction");
		$("#sanPAOAdvanceAmt").focus();
		return false;
	}
	
	if($("#isMROReceived").val()==''){
		alert("Please Select MRO Received");
		return false;
	}
	
	if($("#sanMRORefundAmt").val()==''){
		alert("eMRO/MRO/ Refund Passed Amount can not be blank");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val())>parseInt($("#mroRefundAmount").val())){
		alert("eMRO/MRO/ Refund Passed Amount can not greater than "+$("#mroRefundAmount").val());
		$("#sanMRORefundAmt").val(0);
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if(parseInt($("#sanMRORefundAmt").val()) != parseInt($("#mroRefundAmount").val()) && $("#mroRefundDeductionReason").val()==''){
		alert("Please Select eMRO/MRO/ Refund Amount Reason for Deduction");
		$("#sanMRORefundAmt").focus();
		return false;
	}
	
	if($("#other_Deduction").val()==''){
		alert("Any other Deduction from Officer can not be blank");
		$("#other_Deduction").focus();
		return false;
	}
	
	if(parseInt($("#other_Deduction").val())>0 && $("#other_DeductionReason").val()==''){
		alert("Please enter the reason for other deduction from officer");
		$("#other_DeductionReason").focus();
		return false;
	}
	
	return true;
 }
 
 
 function calculatePTPAOEditAmount(){
	
	var tadaJrySeq = $("#claimEditPAOForm input[name=tadaJrySeq]");
	var personalEffSeq = $("#claimEditPAOForm input[name=personalEffSeq]");
	
	var totalDTSBookingAmount=0;
	var totalNonDTSBookingAmount=0;
	var totalPersonalEffectsAmount=0;
	var journeyAmount=0;
	
	for(var index=0;index<tadaJrySeq.length;index++){
		var val=tadaJrySeq[index].value;
		if($("#jrnyBKGFrom"+val).val()=='DTS'){
			totalDTSBookingAmount=totalDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}else{
			totalNonDTSBookingAmount=totalNonDTSBookingAmount+Number($("#dtsJrySanAmount"+val).val());
		}
		
		if($('#jrnyPaymentTo'+val).length){
			if($('#jrnyPaymentTo'+val).val()!='Officer'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}else{
			if($("#jrnyBKGFrom"+val).val()=='DTS'){
				journeyAmount=journeyAmount+Number($("#dtsJrySanAmount"+val).val());
			}
		}
	}
	
	for(var index=0;index<personalEffSeq.length;index++){
		var val=personalEffSeq[index].value;
		totalPersonalEffectsAmount=totalPersonalEffectsAmount+Number($("#sanPersonalEffectsAmt_"+val).val());
	}
	
	var otherDeduction= Number($("#other_Deduction").val());
	
	var recoveryPassAmt= Number($("#recoveryPassAmt").val());
	
	var totalApprovedAmount=Number(totalDTSBookingAmount)+Number(totalNonDTSBookingAmount)+Number($("#sanCTGAmt").val())
	                        +Number(totalPersonalEffectsAmount)-Number(otherDeduction);
	
	var totalAdvanceApproved=Number(journeyAmount)+Number($("#daAdvanceAmount").val())+Number($("#sanPAOAdvanceAmt").val());
	                         
	var totalRefundApproved= Number($("#sanMRORefundAmt").val());
	
	var totalPassedAmount= Number(totalApprovedAmount)+Number(totalRefundApproved)-Number(totalAdvanceApproved)+Number(recoveryPassAmt);                       
	
	$("#total_approved_amount").html(parseFloat(totalApprovedAmount).toFixed(2));
	$("#total_advance_approved").html(parseFloat(totalAdvanceApproved).toFixed(2));
	$("#total_refund_approved").html(parseFloat(totalRefundApproved).toFixed(2));
	$("#total_passed_amount").html(parseFloat(totalPassedAmount).toFixed(2));
	
	$("#totalApprovedAmount").val(parseFloat(totalApprovedAmount).toFixed(2));
	$("#totalAdvanceApprovedAmount").val(parseFloat(totalAdvanceApproved).toFixed(2));
	$("#totalRefundsApprovedAmount").val(parseFloat(totalRefundApproved).toFixed(2));
	$("#totalPassedAmount").val(parseFloat(totalPassedAmount).toFixed(2));
	
}

function createPTJrnyDetails(){
	
	if(validetePTJrnyDetails()){
		
		 var mainTable = document.getElementById("ptNonDtsJrnyTableGrid");
		 
		 ptJrnyTableIdlen++;
		 
		 var row = mainTable.insertRow(-1);
		 row.className = "lablevalue";
		 row.id = "nonDTSPTJrnyDetls"+ptJrnyTableIdlen;
		 var cell0 =  row.insertCell(0);
		 cell0.innerHTML ='<table class="filtersearch" id="nonDTSJrnyDetls'+ptJrnyTableIdlen+'"><tbody class="all1"></tbody></table>';
		 
		 var table = document.getElementById("nonDTSJrnyDetls"+ptJrnyTableIdlen);
		 
		 var row0 = table.insertRow(-1);
		 //row0.className = "lablevalue";
		 var cell0 =  row0.insertCell(0);
		 cell0.setAttribute("bgcolor","#AAAAAA");
		 cell0.setAttribute("align","left");
		 cell0.setAttribute("colspan","5");
		 cell0.innerHTML = '<a onclick="deletePTJrnyDetails('+ptJrnyTableIdlen+')" style="color: rgb(255, 255, 255); border: medium none; cursor: pointer; padding: 2px 8px; background: rgb(13, 57, 94) none repeat scroll 0% 0%;">Delete Segment</a>';
		 
		 var cell1 =  row0.insertCell(1);
		 cell1.setAttribute("align","right");
		 cell1.setAttribute("bgcolor","#AAAAAA");
		 cell1.setAttribute("colspan","3");
		 cell1.className="shift";
		 cell1.innerHTML = '<span class="lablevalue">Journey Type:</span><select name="nonDTSJourneyType" id="nonDTSJourneyType'+ptJrnyTableIdlen+'" class="combo"  style="width:100px;"><option value="">Select</option><option value="0">Onward</option><option value="1">Return</option></select>' +
		 				   '<input type="hidden"  readonly="true" name="ptJrnyTableIdlen" value="'+ptJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="airlineType" id="airlineType'+ptJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="otherAirlineType" id="otherAirlineType'+ptJrnyTableIdlen+'"/>' +
		 				   '<input type="hidden"  readonly="true" name="FAA_AuthorityNo" id="FAA_AuthorityNo'+ptJrnyTableIdlen+'"/><input type="hidden"  readonly="true" name="FAA_AuthorityDate" id="FAA_AuthorityDate'+ptJrnyTableIdlen+'"/>';
		 
		 var row1 = table.insertRow(-1);
		 //row1.className = "lablevalue";
		 var cell0 =  row1.insertCell(0);
		 cell0.innerHTML ='Mode of Travel';
		 cell0.className = "lablevalue";
		 var cell1 =  row1.insertCell(1);
		 cell1.innerHTML ='<select name="nonDTSModeOfTravel" id="nonDTSModeOfTravel'+ptJrnyTableIdlen+'" class="combo" style="width:125px;" onchange="setClassOfTravel(this.value,'+ptJrnyTableIdlen+');"><option value="">Select</option></select>';
		 var cell2 =  row1.insertCell(2);
		 cell2.innerHTML ='Departure Place';
		 cell2.className = "lablevalue";
		 var cell3 =  row1.insertCell(3);
		 cell3.setAttribute("id","nonDTS_From"+ptJrnyTableIdlen);
		 cell3.innerHTML ='<input type="text" name="nonDTSFromPlace" id="nonDTSFromPlace'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="50" autocomplete="off"/>';
		 var cell4 =  row1.insertCell(4);
		 cell4.innerHTML ='Arrival Place';
		 cell4.className = "lablevalue";
		 var cell5 =  row1.insertCell(5);
		 cell5.setAttribute("id","nonDTS_To"+ptJrnyTableIdlen);
		 cell5.innerHTML ='<input type="text" name="nonDTSToPlace" id="nonDTSToPlace'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="50" autocomplete="off"/>';
		 var cell6 =  row1.insertCell(6);
		 cell6.innerHTML ='Class of Accomodation';
		 cell6.className = "lablevalue";
		 var cell7 =  row1.insertCell(7);
		 cell7.innerHTML ='<select name="nonDTSClassOfTravel" id="nonDTSClassOfTravel'+ptJrnyTableIdlen+'" style="width:175px;" class="combo"><option value="">Select</option></select>';
		 
		 var row2 = table.insertRow(-1);
		 //row2.className = "lablevalue";
		 var cell0 =  row2.insertCell(0);
		 cell0.innerHTML ='Departure Time';
		 cell0.className = "lablevalue";
		 var cell1 =  row2.insertCell(1);
		 cell1.innerHTML ='<input type="text" name="nonDTSJryStartDate" id="nonDTSJryStartDate'+ptJrnyTableIdlen+'" class="txtfldM" style="width:125px;" maxlength="20" readonly="true" autocomplete="off"/>';
		 var cell2 =  row2.insertCell(2);
		 cell2.innerHTML ='Arrival Time';
		 cell2.className = "lablevalue";
		 var cell3 =  row2.insertCell(3);
		 cell3.innerHTML ='<input type="text" name="nonDTSJryEndDate" id="nonDTSJryEndDate'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="20" readonly="true" autocomplete="off"/>';
		 var cell4 =  row2.insertCell(4);
		 cell4.innerHTML ='Sector';
		 cell4.className = "lablevalue";
		 var cell5 =  row2.insertCell(5);
		 cell5.innerHTML ='<input type="text" name="nonDTSSector" id="nonDTSSector'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="20" autocomplete="off"/>';
		 var cell6 =  row2.insertCell(6);
		 cell6.innerHTML ='Bill/PNR/ Ticket No.';
		 cell6.className = "lablevalue";
		 var cell7 =  row2.insertCell(7);
		 cell7.innerHTML ='<input type="text" name="nonDTSTicketNo" id="nonDTSTicketNo'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="30" autocomplete="off"/>';
		 
		 var row3 = table.insertRow(-1);
		// row3.className = "lablevalue";
		 var cell0 =  row3.insertCell(0);
		 cell0.innerHTML ='Distance (KM)';
		 cell0.className = "lablevalue";
		 var cell1 =  row3.insertCell(1);
		 cell1.innerHTML ='<input type="text" name="nonDTSDistanceKM" id="nonDTSDistanceKM'+ptJrnyTableIdlen+'" maxlength="5" class="txtfldM" style="width:125px;" onkeypress="return isNumericOnlyKey(event)" autocomplete="off"/>';
		 var cell2 =  row3.insertCell(2);
		 cell2.innerHTML ='Total Fare';
		 cell2.className = "lablevalue";
		 var cell3 =  row3.insertCell(3);
		 cell3.innerHTML ='<input type="text" name="nonDTSJryAmount" id="nonDTSJryAmount'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="8" value="0" readonly="true" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 var cell4 =  row3.insertCell(4);
		 cell4.innerHTML ='Total Refund';
		 cell4.className = "lablevalue";
		 var cell5 =  row3.insertCell(5);
		 cell5.innerHTML ='<input type="text" name="nonDTSJryRefundAmount" id="nonDTSJryRefundAmount'+ptJrnyTableIdlen+'" class="txtfldM" value="0" readonly="true" maxlength="8" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 var cell6 =  row3.insertCell(6);
		 cell6.innerHTML ='Total Claimed Amount';
		 cell6.className = "lablevalue";
		 var cell7 =  row3.insertCell(7);
		 cell7.innerHTML ='<input type="text" name="nonDTSJryClaimAmount" id="nonDTSJryClaimAmount'+ptJrnyTableIdlen+'" class="txtfldM" maxlength="8" value="0" readonly="true" onkeypress="return isNumberKey(event)" autocomplete="off"/>';
		 
		 var row4 = table.insertRow(-1);
		 var cell0 =  row4.insertCell(0);
		 cell0.setAttribute("align","right");
		 cell0.setAttribute("colspan","10");
		 cell0.className = "shift";
		 cell0.innerHTML = '<a onclick="addPTJrnyTraveller('+ptJrnyTableIdlen+')" style="color: rgb(255, 255, 255); border: medium none; cursor: pointer; padding: 2px 8px; background: rgb(13, 57, 94) none repeat scroll 0% 0%;">Add Traveler</a>';
		 
		 var row5 = table.insertRow(-1);
		 var cell0 =  row5.insertCell(0);
		 cell0.setAttribute("bgcolor","#003A70");
		 cell0.setAttribute("style","border-bottom:none;");
		 cell0.setAttribute("align","left");
		 cell0.setAttribute("colspan","11");
		 
		 var innerHtmlValue= '<table class="filtersearch"  id="nonDTSJrnyPassDetls'+ptJrnyTableIdlen+'"><tbody class="all3">';
		 innerHtmlValue=innerHtmlValue+'<tr class="lablevalue"><td style="width:10%;">Traveler Name</td><td style="width:10%;">Relation</td><td style="width:5%;">Age</td><td style="width:10%;">Ticket No</td><td style="width:10%;">Journey Performed?</td><td style="width:10%;">Booking Amount</td><td style="width:10%;">Refund Amount</td><td style="width:10%;">Claimed Amount</td></tr>';
		 
		 innerHtmlValue=innerHtmlValue+'<tr>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsTravellerName'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsTravellerName'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="setTravellerRelationAndAge(this.value,'+ptJrnyTableIdlen+','+ptJrnyPassTableIdlen+');"><option value="">Select</option></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRelation'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsRelation'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onkeypress="return isNumberKey(event)" readonly="true"></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsAge'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsAge'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="3" onkeypress="return isNumberKey(event)" readonly="true"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsTicketNo'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsTicketNo'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="50" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsJourneyPerformed'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsJourneyPerformed'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="takePTNonDTSCancellation(this.value,'+ptJrnyTableIdlen+','+ptJrnyPassTableIdlen+');"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsBookingAmount'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSBookingAmount('+ptJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsRefundAmount'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSRefundAmount('+ptJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+ptJrnyTableIdlen+'" id="nonDTSPassDtlsClaimedAmount'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSClaimedAmount('+ptJrnyTableIdlen+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanType'+ptJrnyTableIdlen+'" id="nonDTSJrnyCanType'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanNo'+ptJrnyTableIdlen+'" id="nonDTSJrnyCanSanNo'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanDate'+ptJrnyTableIdlen+'" id="nonDTSJrnyCanSanDate'+ptJrnyTableIdlen+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="ptJrnyPassTableIdlen'+ptJrnyTableIdlen+'" value="'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'</tr>';
		 
		 innerHtmlValue=innerHtmlValue+'</tbody></table>';
		 
		 cell0.innerHTML=innerHtmlValue; 
		
		
		 setModeOfTravel(ptJrnyTableIdlen);
		 setTravellerName(ptJrnyTableIdlen,ptJrnyPassTableIdlen);
		 
		 
		 $('#nonDTSJryStartDate'+ptJrnyTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                                scrollInput:false,yearEnd : 2100 ,defaultDate:$("#start_date_time").val(),onSelectDate: function(current_time,$input){validateLTCFromDate(3,ptJrnyTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryStartDate'+ptJrnyTableIdlen).val("");
		} });
		 $('#nonDTSJryEndDate'+ptJrnyTableIdlen).datetimepicker({lang:'en',scrollMonth:false,timepicker:true,format:'d-m-Y H:i',formatDate:'d-m-Y',maxDate:0,step:5,disabledDates:getLeaveDates(),
			                                               scrollInput:false,yearEnd : 2100, defaultDate:$("#start_date_time").val(),onSelectDate: function(current_time,$input){validateLTCToDate(3,ptJrnyTableIdlen);},
		onShow: function () {
			  $('#nonDTSJryEndDate'+ptJrnyTableIdlen).val("");
		} });
	}
	
}

function addPTJrnyTraveller(index){
	
	if(valideteNonDtsPTPassDetails(index)){
		ptJrnyPassTableIdlen++;
		 var innerHtmlValue=innerHtmlValue+'<tr>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsTravellerName'+index+'" id="nonDTSPassDtlsTravellerName'+index+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="setTravellerRelationAndAge(this.value,'+index+','+ptJrnyPassTableIdlen+');"><option value="">Select</option></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRelation'+index+'" id="nonDTSPassDtlsRelation'+index+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onkeypress="return isNumberKey(event)" readonly="true"></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsAge'+index+'" id="nonDTSPassDtlsAge'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="3" onkeypress="return isNumberKey(event)" readonly="true"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsTicketNo'+index+'" id="nonDTSPassDtlsTicketNo'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="50" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><select name="nonDTSPassDtlsJourneyPerformed'+index+'" id="nonDTSPassDtlsJourneyPerformed'+index+'_'+ptJrnyPassTableIdlen+'" class="combo" style="width:80%;" onchange="takePTNonDTSCancellation(this.value,'+index+','+ptJrnyPassTableIdlen+');"><option value="">Select</option><option value="0">Yes</option><option value="1">No</option></select></td>';
		  
		 if($("#nonDTSModeOfTravel"+index).val()=='Train'){
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+index+'" id="nonDTSPassDtlsBookingAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+index+'" id="nonDTSPassDtlsRefundAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+index+'" id="nonDTSPassDtlsClaimedAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" readonly="true" value="0" onkeypress="return isNumberKey(event)" autocomplete="off"/>&#160;<input type="button"  value="X" class="butn" onclick="deletePTJrnyTraveller(this,'+index+')"></td>';
		 }else{
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsBookingAmount'+index+'" id="nonDTSPassDtlsBookingAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSBookingAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>' ;
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsRefundAmount'+index+'" id="nonDTSPassDtlsRefundAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSRefundAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/></td>';
		 innerHtmlValue=innerHtmlValue+'<td><input type="text" name="nonDTSPassDtlsClaimedAmount'+index+'" id="nonDTSPassDtlsClaimedAmount'+index+'_'+ptJrnyPassTableIdlen+'" class="txtfldM" style="width:80%;" maxlength="8" onblur="addNonDTSClaimedAmount('+index+');" onkeypress="return isNumberKey(event)" autocomplete="off"/>&#160;<input type="button"  value="X" class="butn" onclick="deletePTJrnyTraveller(this,'+index+')"></td>';
		 	
		 }
		 
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanType'+index+'" id="nonDTSJrnyCanType'+index+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanNo'+index+'" id="nonDTSJrnyCanSanNo'+index+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="nonDTSJrnyCanSanDate'+index+'" id="nonDTSJrnyCanSanDate'+index+'_'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'<input type="hidden"  readonly="true" name="ptJrnyPassTableIdlen'+index+'" value="'+ptJrnyPassTableIdlen+'"/>';
		 innerHtmlValue=innerHtmlValue+'</tr>';
		 
	    $("#nonDTSJrnyPassDetls"+index).append(innerHtmlValue);
	    setTravellerName(index,ptJrnyPassTableIdlen);
	    
	} 
}

function valideteNonDtsPTPassDetails(index){
	
	var passTravellerName = $("#voucherSettlementConfirmationForm select[name=nonDTSPassDtlsTravellerName"+index+"]");
	if(passTravellerName.length > 0){
		for(var i=0;i<passTravellerName.length;i++){
			if(passTravellerName[i].value==""){
				alert("Please select Traveler Name");
				return false;
			}
		}
	}
	
	var passRelation = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRelation"+index+"]");
	if(passRelation.length > 0){
		for(var i=0;i<passRelation.length;i++){
			if(passRelation[i].value==""){
				alert("Please select Relation");
				return false;
			}
		}
	}
	
	var passAge = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsAge"+index+"]");
	if(passAge.length > 0){
		for(var i=0;i<passAge.length;i++){
			if(passAge[i].value==""){
				alert("Please enter Age");
				passAge[i].focus();
				return false;
			}
		}
	}
	
	var passTicketNo = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsTicketNo"+index+"]");
	if(passTicketNo.length > 0){
		for(var i=0;i<passTicketNo.length;i++){
			if(passTicketNo[i].value==""){
				alert("Please enter Ticket No.");
				passTicketNo[i].focus();
				return false;
			}
		}
	}
	
	var passJourneyPerformed = $("#voucherSettlementConfirmationForm select[name=nonDTSPassDtlsJourneyPerformed"+index+"]");
	if(passJourneyPerformed.length > 0){
		for(var i=0;i<passJourneyPerformed.length;i++){
			if(passJourneyPerformed[i].value==""){
				alert("Please select Journey Performed");
				return false;
			}
		}
	}
	
	var passBookingAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsBookingAmount"+index+"]");
	if(passBookingAmount.length > 0){
		for(var i=0;i<passBookingAmount.length;i++){
			if(passBookingAmount[i].value==""){
				alert("Please enter Booking Amount");
				passBookingAmount[i].focus();
				return false;
			}
		}
	}
	
	var passRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsRefundAmount"+index+"]");
	if(passRefundAmount.length > 0){
		for(var i=0;i<passRefundAmount.length;i++){
			if(passRefundAmount[i].value==""){
				alert("Please enter Refund Amount");
				passRefundAmount[i].focus();
				return false;
			}
		}
	}
	
	var passClaimedAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSPassDtlsClaimedAmount"+index+"]");
	if(passClaimedAmount.length > 0){
		for(var i=0;i<passClaimedAmount.length;i++){
			if(passClaimedAmount[i].value==""){
				alert("Please enter Claimed Amount");
				passClaimedAmount[i].focus();
				return false;
			}
		}
	}
	
	return true;
}






function deletePTJrnyTraveller(obj,index){
	var delRow = obj.parentNode.parentNode;
	var rIndex = delRow.sectionRowIndex;
	delRow.parentNode.deleteRow(rIndex);
	
	if($("#nonDTSModeOfTravel"+index).val()!='Train'){
		addNonDTSBookingAmount(index);
		addNonDTSClaimedAmount(index);
		addNonDTSRefundAmount(index);
	}
}

function deletePTJrnyDetails(index){
	$("#nonDTSPTJrnyDetls"+index).remove();
	calculatePTFinalAmount();
}

function validetePTJrnyDetails(){
	
	var nonDTSJourneyType = $("#voucherSettlementConfirmationForm select[name=nonDTSJourneyType]");
	
	if(nonDTSJourneyType.length==0){
		return true;
	}else{
		
		if(nonDTSJourneyType.length > 0){
			for(var i=0;i<nonDTSJourneyType.length;i++){
				if(nonDTSJourneyType[i].value==""){
					alert("Please select Journey Type");
					nonDTSJourneyType[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSModeOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSModeOfTravel]");
		if(nonDTSModeOfTravel.length > 0){
			for(var i=0;i<nonDTSModeOfTravel.length;i++){
				if(nonDTSModeOfTravel[i].value==""){
					alert("Please select Mode Of Travel");
					nonDTSModeOfTravel[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSFromPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSFromPlace]");
		if(nonDTSFromPlace.length > 0){
			for(var i=0;i<nonDTSFromPlace.length;i++){
				if(nonDTSFromPlace[i].value==""){
					alert("Please enter Departure Place");
					nonDTSFromPlace[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSToPlace = $("#voucherSettlementConfirmationForm input[name=nonDTSToPlace]");
		if(nonDTSToPlace.length > 0){
			for(var i=0;i<nonDTSToPlace.length;i++){
				if(nonDTSToPlace[i].value==""){
					alert("Please enter Arrival Place");
					nonDTSToPlace[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSClassOfTravel = $("#voucherSettlementConfirmationForm select[name=nonDTSClassOfTravel]");
		if(nonDTSClassOfTravel.length > 0){
			for(var i=0;i<nonDTSClassOfTravel.length;i++){
				if(nonDTSClassOfTravel[i].value==""){
					alert("Please select Class of Accomodation");
					nonDTSClassOfTravel[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryStartDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryStartDate]");
		if(nonDTSJryStartDate.length > 0){
			for(var i=0;i<nonDTSJryStartDate.length;i++){
				if(nonDTSJryStartDate[i].value==""){
					alert("Please select Departure Time");
					nonDTSJryStartDate[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryEndDate = $("#voucherSettlementConfirmationForm input[name=nonDTSJryEndDate]");
		if(nonDTSJryEndDate.length > 0){
			for(var i=0;i<nonDTSJryEndDate.length;i++){
				if(nonDTSJryEndDate[i].value==""){
					alert("Please select Arrival Time");
					nonDTSJryEndDate[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSSector = $("#voucherSettlementConfirmationForm input[name=nonDTSSector]");
		if(nonDTSSector.length > 0){
			for(var i=0;i<nonDTSSector.length;i++){
				if(nonDTSSector[i].value==""){
					alert("Please enter Sector");
					nonDTSSector[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSTicketNo = $("#voucherSettlementConfirmationForm input[name=nonDTSTicketNo]");
		if(nonDTSTicketNo.length > 0){
			for(var i=0;i<nonDTSTicketNo.length;i++){
				if(nonDTSTicketNo[i].value==""){
					alert("Please enter Bill/PNR/ Ticket No");
					nonDTSTicketNo[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSDistanceKM = $("#voucherSettlementConfirmationForm input[name=nonDTSDistanceKM]");
		if(nonDTSDistanceKM.length > 0){
			for(var i=0;i<nonDTSDistanceKM.length;i++){
				if(nonDTSDistanceKM[i].value==""){
					alert("Please enter Distance");
					nonDTSDistanceKM[i].focus();
					return false;
				}
			}
		}
		
		var nonDTSJryRefundAmount = $("#voucherSettlementConfirmationForm input[name=nonDTSJryRefundAmount]");
		if(nonDTSJryRefundAmount.length > 0){
			for(var i=0;i<nonDTSJryRefundAmount.length;i++){
				if(nonDTSJryRefundAmount[i].value==""){
					alert("Please enter Total Refund");
					nonDTSJryRefundAmount[i].focus();
					return false;
				}
			}
		}
		
	    var ptJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ptJrnyTableIdlen]");
	    if(ptJrnyTableIdlen.length > 0){
	    	for(var i=0;i<ptJrnyTableIdlen.length;i++){
	    		if(!valideteNonDtsPTPassDetails(ptJrnyTableIdlen[i].value)){return false;}
	    	}
	    }
	    
		return true;
	}
	
}

function calculatePTFinalAmount(){
	
	var totalClaimedAmount=0;
	var totalDTSJrnyAmount=0;
	var totalDTSRefund=0;
	
	var dtsPTJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=dtsPTJrnyTableIdlen]");
	
	if(dtsPTJrnyTableIdlen.length > 0){
		for(var index=0; index<dtsPTJrnyTableIdlen.length; index++){
			totalClaimedAmount=totalClaimedAmount+Number($("#jrnyClaimedAmount"+dtsPTJrnyTableIdlen[index].value).val());
			totalDTSJrnyAmount=totalDTSJrnyAmount+Number($("#jrnyClaimedAmount"+dtsPTJrnyTableIdlen[index].value).val());
			totalDTSRefund=totalDTSRefund+Number($("#jrnyRefundAmount"+dtsPTJrnyTableIdlen[index].value).val());
		}
	}
	
	var ptJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=ptJrnyTableIdlen]");
	
	if(ptJrnyTableIdlen.length > 0){
		for(var index=0; index<ptJrnyTableIdlen.length; index++){
			totalClaimedAmount=totalClaimedAmount+Number($("#nonDTSJryClaimAmount"+ptJrnyTableIdlen[index].value).val());
		}
	}
	
	var particularClaimAmt = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_0]");
	for(var index=0; index<particularClaimAmt.length; index++){
		totalClaimedAmount=totalClaimedAmount+Number(particularClaimAmt[index].value);
	}
	
	var particularClaimAmt_1 = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_1]");
	for(var index=0; index<particularClaimAmt_1.length; index++){
		totalClaimedAmount=totalClaimedAmount+Number(particularClaimAmt_1[index].value);
	}

	totalClaimedAmount=totalClaimedAmount+Number($("#Claim_GTC_Amt").val());

	var recoveryAmount = Number($("#irlaRecoveryAmt").val());
	var totalSpentAmount=Number(totalClaimedAmount);
	var totalAdvanceTaken=Number(totalDTSJrnyAmount)+Number($("#daAdvanceAmount").val())+Number($("#Adv_From_PAO").val());
	var totalRefundAmount=Number($("#Advance_MRO").val());
	
	var totalPayAmount=Number(totalSpentAmount)+Number(totalRefundAmount)-Number(totalAdvanceTaken)+Number(recoveryAmount);

	$("#total_spent_amount").html(parseFloat(totalSpentAmount).toFixed(2));
	$("#total_advance_amount").html(parseFloat(totalAdvanceTaken).toFixed(2));
	$("#total_refund_amount").html(parseFloat(totalRefundAmount).toFixed(2));
	$("#total_claimed_amount").html(parseFloat(totalPayAmount).toFixed(2));
	
	$("#totalSpentAmount").val(parseFloat(totalSpentAmount).toFixed(2));
	$("#totalAdvanceAmount").val(parseFloat(totalAdvanceTaken).toFixed(2));
	$("#totalRefundAmount").val(parseFloat(totalRefundAmount).toFixed(2));
	$("#totalClaimedAmount").val(parseFloat(totalPayAmount).toFixed(2));
	
}

function setCartageDetails(val){
	
	if(val=="Train" || val=="Steamer"){
		
		var innerHTML="";
		innerHTML=innerHTML+'<td style="width:20%;">Cartage- Old Residence to Booking Office<input type="hidden" name="particularName_0" value="Cartage- Old Residence to Booking Office"/></td>'; 
		innerHTML=innerHTML+'<td style="width:20%;"><input type="text" name="particularWeight_0" id="particularWeight_0_2" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>';
		innerHTML=innerHTML+'<td style="width:20%;">';
		innerHTML=innerHTML+'<select name="particularConveyance_0" class="combo">';
		innerHTML=innerHTML+'<option value="NA">NA</option>';
		innerHTML=innerHTML+'</select>';
		innerHTML=innerHTML+'</td>'; 
		innerHTML=innerHTML+'<td style="width:20%;"><input type="text" name="particularDistance_0" id="particularDistance_0_2" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>';
		innerHTML=innerHTML+'<td style="width:20%;"><input type="text" name="particularClaimAmt_0"  id="particularClaimAmt_0_2" onblur="validateMaxLuggAmount(\'0_2\');calculatePTFinalAmount();" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>';
		
		var innerHTMLTwo="";
		innerHTMLTwo=innerHTMLTwo+'<td style="width:20%;">Cartage-Booking Office to New Residence<input type="hidden" name="particularName_0" value="Cartage-Booking Office to New Residence"/></td>'; 
		innerHTMLTwo=innerHTMLTwo+'<td style="width:20%;"><input type="text" name="particularWeight_0" id="particularWeight_0_3" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>'; 
		innerHTMLTwo=innerHTMLTwo+'<td style="width:20%;">';
		innerHTMLTwo=innerHTMLTwo+'<select name="particularConveyance_0" class="combo">';
		innerHTMLTwo=innerHTMLTwo+'<option value="NA">NA</option>';
		innerHTMLTwo=innerHTMLTwo+'</select>';
		innerHTMLTwo=innerHTMLTwo+'</td>'; 
		innerHTMLTwo=innerHTMLTwo+'<td style="width:20%;"><input type="text" name="particularDistance_0" id="particularDistance_0_3" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>'; 
		innerHTMLTwo=innerHTMLTwo+'<td style="width:20%;"><input type="text" name="particularClaimAmt_0"  id="particularClaimAmt_0_3" onblur="validateMaxLuggAmount(\'0_3\');calculatePTFinalAmount();" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>';
		
		$("#CartageOldResToBkgOff").html(innerHTML);
		$("#CartageBkgOffToNewRes").html(innerHTMLTwo);
		
	}else{
		$("#CartageOldResToBkgOff").html("");
		$("#CartageBkgOffToNewRes").html("");
	}
}

function setConveyanceLuggage(val){
	
	if(val==1){
		
		var innerHTML="";
		innerHTML=innerHTML+'<tbody class="all1"><tr class="lablevalue">';
		innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;border-right:1px solid white">Mode of Conveyance</td>'; 
		innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;border-right:1px solid white">Distance (in Kms)</td>'; 
		innerHTML=innerHTML+'<td style="width:25%;background-color:#EBF4FA;">Claimed Amount</td>'; 
		innerHTML=innerHTML+'</tr>';
		innerHTML=innerHTML+'<tr><input type="hidden" name="particularName_1" value="Transportation of Conveyance"/>';
		innerHTML=innerHTML+'<td style="width:25%;">';
		innerHTML=innerHTML+'<select name="particularConveyance_1" class="combo">';
		innerHTML=innerHTML+'<option value="">Select</option>';
		innerHTML=innerHTML+'<option value="Truck">Truck</option>';
		innerHTML=innerHTML+'<option value="Train">Train</option>';
		innerHTML=innerHTML+'<option value="Steamer">Steamer</option>';
		innerHTML=innerHTML+'</select>';
		innerHTML=innerHTML+'</td>'; 
		innerHTML=innerHTML+'<td style="width:25%;"><input type="text" name="particularDistance_1" id="particularDistance_1_1" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>'; 
		innerHTML=innerHTML+'<td style="width:25%;"><input type="text" name="particularClaimAmt_1" id="particularClaimAmt_1_1" onblur="validateConveyanceAmount();calculatePTFinalAmount();" onkeypress="return isNumberKey(event)" maxlength="10" autocomplete="off"/></td>'; 
		innerHTML=innerHTML+'</tr></tbody>';
		
		$("#transportationOfConveyanceDtls").html(innerHTML);
		$("#transportationOfConveyance").show();
	
	}else{
		$("#transportationOfConveyance").hide();
		$("#transportationOfConveyanceDtls").html("");
		$("#conveyanceType").val("");
	}
}

function validateConveyanceAmount(){
	var maxVTransportAmt=$("#maxVTransportAmt").val();
	var particularClaimAmt_1_1=$("#particularClaimAmt_1_1").val();
	var particularDistance_1_1=$("#particularDistance_1_1").val();
	var maxTransportAmt=Number(maxVTransportAmt)* Number(particularDistance_1_1);
	if(Number(particularClaimAmt_1_1) > maxTransportAmt){
		alert("Maximum Eligible Vehicle transport Amount= "+maxTransportAmt) ;
	}
}

function validatePTClaimForm(){
	
	if($("#personalPayLevel").val().trim()==''){
		alert("Please select Pay Level");
		$("#personalPayLevel").focus();
		return false;
	}
	if($("#personalBasicPay").val().trim()==''){
		alert("Please enter Basic Pay");
		$("#personalBasicPay").focus();
		return false;
	}
	if($("#transferredFrom").val().trim()==''){
		alert("Please enter Transferred from");
		$("#transferredFrom").focus();
		return false;
	}
	if($("#transferredTo").val().trim()==''){
		alert("Please enter Transferred To");
		$("#transferredTo").focus();
		return false;
	}
	if($("#jrnyStartedFrom").val().trim()==''){
		alert("Please enter Departure Place");
		$("#jrnyStartedFrom").focus();
		return false;
	}
	if($("#start_date_time").val().trim()==''){
		alert("Please select Departure Date");
		$("#start_date_time").focus();
		return false;
	}
	if($("#jrnyStartedTo").val().trim()==''){
		alert("Please enter Arrival Place");
		$("#jrnyStartedTo").focus();
		return false;
	}
	if($("#start_to_date").val().trim()==''){
		alert("Please select Arrival Date");
		$("#start_to_date").focus();
		return false;
	}
	if($("#moveSanctionNo").val().trim()==''){
		alert("Please enter Move Sanction No");
		$("#moveSanctionNo").focus();
		return false;
	}
	if($("#move_Sanction_Date").val().trim()==''){
		alert("Please select Move Sanction Date");
		$("#move_Sanction_Date").focus();
		return false;
	}
	if($("#moveAuthority").val().trim()==''){
		alert("Please enter Move Issuing Authority");
		$("#moveAuthority").focus();
		return false;
	}
	
	var dtsJourneyDate = $("#voucherSettlementConfirmationForm input[name=dtsJourneyDate]");
	
	for(var index=0;index<dtsJourneyDate.length;index++){
		if(dtsJourneyDate[index].value==''){
		   alert("Please select DTS Journey Start Date");
		   dtsJourneyDate[index].focus();
		   return false;
		}
		
		if($("#journey_end_date_"+index).val().trim()==''){
			alert("Please select DTS Journey End Date");
			$("#journey_end_date_"+index).focus();
		    return false;
		}
	}
	
	var dtsLtcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=dtsLtcJrnyTableIdlen]");
	var jrnyFlag=true;
	for(var index=0;index<dtsLtcJrnyTableIdlen.length;index++){
		var val=dtsLtcJrnyTableIdlen[index].value;
		var dtsJourneyPerformed = $("#voucherSettlementConfirmationForm select[name=dtsJourneyPerformed"+val+"]");
		for(var i=0;i<dtsJourneyPerformed.length;i++){
			if(dtsJourneyPerformed[i].value==''){
			   alert("Please select DTS Journey Performed");
			   jrnyFlag= false;
			   break;
			}
		}
		if(!jrnyFlag){return false;}
	}
	
	if(!validetePTJrnyDetails()){return false;}
    
    
     if($("#CTGPercentage").val().trim()==''){
    	alert("Please enter Percentage of CTG");
    	$("#CTGPercentage").focus();
		return false;
     }
      if($("#Claim_GTC_Amt").val().trim()==''){
    	alert("Please enter CTG Claimed Amount");
    	$("#Claim_GTC_Amt").focus();
		return false;
     }
      
     var particularWeight = $("#voucherSettlementConfirmationForm input[name=particularWeight_0]");
     var particularConveyance = $("#voucherSettlementConfirmationForm select[name=particularConveyance_0]");
     var particularDistance = $("#voucherSettlementConfirmationForm input[name=particularDistance_0]");
     var particularClaimAmt = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_0]");
     var particularName = $("#voucherSettlementConfirmationForm input[name=particularName_0]");
     for(var index=0;index<particularWeight.length;index++){
     	if(particularWeight[index].value==''){
		   alert("Please enter "+particularName[index].value+" Weight");
		   particularWeight[index].focus();
		   return false;
		}
		
		if(particularConveyance[index].value==''){
		   alert("Please select "+particularName[index].value+" Mode of Conveyance");
		   particularConveyance[index].focus();
		   return false;
		}
		
		if(particularDistance[index].value==''){
		   alert("Please enter "+particularName[index].value+" Distance");
		   particularDistance[index].focus();
		   return false;
		}
		
		if(particularClaimAmt[index].value==''){
		   alert("Please enter "+particularName[index].value+" Claimed Amount");
		   particularClaimAmt[index].focus();
		   return false;
		}
		
     }
     
      if($("#conveyancePartOfLuggage").val().trim()==''){
	    	alert("Please select Is Conveyance part of Luggage");
	    	$("#conveyancePartOfLuggage").focus();
			return false;
	  }
	  
	  if($("#conveyancePartOfLuggage").val()=='1'){
	  	
	  	 if($("#conveyanceType").val().trim()==''){
	    	alert("Please select Transportation of Conveyance");
	    	$("#conveyanceType").focus();
			return false;
	  	}
	  	
	     var particularConveyance_1 = $("#voucherSettlementConfirmationForm select[name=particularConveyance_1]");
	     var particularDistance_1 = $("#voucherSettlementConfirmationForm input[name=particularDistance_1]");
	     var particularClaimAmt_1 = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_1]");
	     var particularName_1 = $("#voucherSettlementConfirmationForm input[name=particularName_1]");
	     for(var index=0;index<particularConveyance_1.length;index++){
			
			if(particularConveyance_1[index].value==''){
			   alert("Please select "+particularName_1[index].value+" Mode of Conveyance");
			   particularConveyance_1[index].focus();
			   return false;
			}
			
			if(particularDistance_1[index].value==''){
			   alert("Please enter "+particularName_1[index].value+" Distance");
			   particularDistance_1[index].focus();
			   return false;
			}
			
			if(particularClaimAmt_1[index].value==''){
			   alert("Please enter "+particularName_1[index].value+" Claimed Amount");
			   particularClaimAmt_1[index].focus();
			   return false;
			}
			
	     }
	  	
	  }
      	
      	
      	
	  	 if(parseInt($("#Adv_From_PAO").val())>0 && $("#Adv_From_PAO_Date").val().trim()==''){
	    	alert("Please select PAO Date of Advance");
	    	$("#Adv_From_PAO_Date").focus();
			return false;
	     }
	      if($("#Adv_Luggage_PAO").val().trim()==''){
	    	alert("Please enter PAO Luggage including Cartage Amount");
	    	$("#Adv_Luggage_PAO").focus();
			return false;
	     }
	      if($("#Adv_Coveyance_PAO").val().trim()==''){
	    	alert("Please enter PAO Coveyance Amount");
	    	$("#Adv_Coveyance_PAO").focus();
			return false;
	     }
	      if($("#Adv_CTG_PAO").val().trim()==''){
	    	alert("Please enter PAO CTG Amount");
	    	$("#Adv_CTG_PAO").focus();
			return false;
	     }
	
   
    
     if($("#Advance_MRO").val().trim()==''){
    	alert("Please enter MRO/Refund");
    	$("#Advance_MRO").focus();
		return false;
     }
    
    if(parseInt($("#Advance_MRO").val())>0){
    	
    	if($("#MRO_Number").val().trim()==''){
    	alert("Please enter eMRO/MRO/Refund No");
    	$("#MRO_Number").focus();
		return false;
       }
    
	    if($("#MRO_Date").val().trim()==''){
	    	alert("Please enter eMRO/MRO Submission Date/Refund Date");
	    	$("#MRO_Date").focus();
			return false;
	    }
	    
    }
    
    var flag=true;
    
    $('input:checkbox[name=claimCertify]').each(function(){
             if($(this).is(':checked')){}
             else{
             	alert("Please check on all the checkboxes in Certify that Tab.");
		        flag= false;
		        return false;
             }
    });
    
    if(flag){
    	
    	if($("#userServiceType").val()== '1'){
    		
    	}else{
	    	if($("#counterPersonalNo").val().trim()==''){
	    	alert("Please enter Counter Singing Authority Personal No");
	    	$("#counterPersonalNo").focus();
			return false;
	        }
        }
        if($("#claimPreferredDate").val().trim()==''){
    	alert("Please enter Date of Claim preferred by the Individual to the Office");
    	$("#claimPreferredDate").focus();
		return false;
        }
        if($("#claimPreferredSanction").val().trim()==''){
    	alert("Please Select Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken");
    	$("#claimPreferredSanction").focus();
		return false;
        }
        
        if($("#claimPreferredSanction").val().trim()=='Yes'){
        	if($("#claimPreferredSanctionNo").val().trim()==''){
	    	alert("Please enter Claim preferred Sanction No");
	    	$("#claimPreferredSanctionNo").focus();
			return false;
	        }
	        if($("#claimPreferredSanctionDate").val().trim()==''){
	    	alert("Please enter Date of Claim preferred Sanction");
	    	$("#claimPreferredSanctionDate").focus();
			return false;
	        }
        }
    }
	return flag;
}

function totalPTPAOAdvanceAmount(){
	
	$("#Adv_From_PAO").val(Number($("#Adv_Luggage_PAO").val())+Number($("#Adv_Coveyance_PAO").val())+Number($("#Adv_CTG_PAO").val()));
}

function validateMaxLuggAmount(id){
	
    var kmRate=$("#maxKmRateAmt").val();
    var weight=$("#maxAllowedWeight").val();
    var distanceInKms = $("#particularDistance_"+id).val();
    var approxLuggWeight = $("#particularWeight_"+id).val();
    var luggageAmt=$("#particularClaimAmt_"+id).val();
	
    if(Number(approxLuggWeight)>Number(weight)){
    	approxLuggWeight=weight;
    }
	
	var maxLuggWeightAmt=parseInt((Number(kmRate)*Number(distanceInKms))*(Number(approxLuggWeight)/Number(weight)));
	
	if(parseInt(luggageAmt) > maxLuggWeightAmt){
    	alert("Maximum Eligible Luggage transport Amount: "+maxLuggWeightAmt);
     }
     return true;
}

function validateCTGAmount(){
	
	var maxctg=0;
	var basicPay = $("#personalBasicPay").val();
    var trnsType = $("#CTGPercentage").val();
    var totalCtg = $("#Claim_GTC_Amt").val();
    
    if(basicPay==""){
   		alert("Please Enter Your Basic Pay.");
    	$("#personalBasicPay").focus();
   		return false
    }
    
     if(trnsType==""){
   		alert("Please select Transfer Type.");
    	$("#CTGPercentage").focus();
   		return false
    }
    
    if(trnsType=="1"){
    	maxctg=parseInt(parseInt(basicPay)*0.8);
    }else if(trnsType=="2"){
    	maxctg=parseInt(basicPay);
    }else if(trnsType=="3"){
    	maxctg=parseInt((parseInt(basicPay)*0.8)/3);
    }else{}
    
    if(parseInt(totalCtg) > maxctg){
    	alert("CTG Claimed Amount should not be more then "+maxctg );
    	$("#Claim_GTC_Amt").val(0);
    	$("#Claim_GTC_Amt").focus();
   		return false
    }
}

function resetCTGPersonalEffects(){
	
	var particularClaimAmt = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_0]");
    for(var index=0;index<particularClaimAmt.length;index++){
     	particularClaimAmt[index].value='';
	}
	
	var particularClaimAmt_1 = $("#voucherSettlementConfirmationForm input[name=particularClaimAmt_1]");
    for(var index=0;index<particularClaimAmt_1.length;index++){
     	particularClaimAmt_1[index].value='';
	}
	
	$("#Claim_GTC_Amt").val('');

}
