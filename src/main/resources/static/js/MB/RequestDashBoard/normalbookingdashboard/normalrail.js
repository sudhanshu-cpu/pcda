
$(document).ready(function(){
	
	 $("#dpt_date").datetimepicker({
 				lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                yearEnd : 2100,
		onShow: function () {
			  $('#dpt_date').val("");
		}
				
			});
			
		var isCancelWaitListTicket=$("#isCancelWaitListTicket").val();
		
		
		if(isCancelWaitListTicket==true){
			
			$("#tktType").text("I-Ticket");
		}else{
		    $("#tktType").text("eTicket");
			}
});

var faredetails="false";
var callwait=true;


function irctcSrchSubmit(){
	
	if(!validateCommonPage()){
		return false;
	}else{
		$("#irctcSrchPage").submit();
	}

	
	
} 

var currentDatee=new Date()
var month = currentDatee.getMonth() + 1
var day = currentDatee.getDate()
var year = currentDatee.getFullYear()
var currentDate=day + "-" + month + "-" + year;


// validate search page

function validateCommonPage()
{
	
	
	var jrneyClass=document.getElementById("journeyclass");
	var nAdult=document.getElementById("nadult").value;
	var nChild=document.getElementById("nchild").value;
	var nSenior=document.getElementById("nsenior").value;
	var nWSenior=document.getElementById("nwsenior").value;
	var approvedDepDate=document.getElementById("appDepDate");
	
	
	var totalNoOfPassenger=parseInt(nAdult)+parseInt(nChild)+parseInt(nSenior)+parseInt(nWSenior);
	$("#totalNoOfPassenger").val(totalNoOfPassenger);
	var dptDate=document.getElementById("dpt_date").value;


	
	if(dptDate=="")
	{
		alert("Departure date should not be blank");
	
		return false;
	}
	// validate tatkal date
	 validateDateForTatkal(dptDate)
	
	
	var appDate=getDtObject(approvedDepDate.value,"-");
	var sysDate=getDtObject(currentDate,"-");
	var chgDate=getDtObject(dptDate,"-");
	
	var no=daysElapsed(chgDate, sysDate);

	var diff=daysElapsed(chgDate,appDate);//diffrence between jrny date and the departure date	

	if(diff < -2)
	{
		alert("Journey date should not be less than two days from the approved date ");
		return false;
	}
	
	if(diff > 11)
	{
		alert("Journey date should not be greater than tweleve days from the approved date ");
		return false;
	}
		
	if(no < 0)
	{
		alert("Departure Date can not be less than today");
		return false;
	}

	if(jrneyClass.value=="-1"){
		alert("Please Select Journey Class");
		jrneyClass.focus();
		return false;
	}
		
	if(parseInt(totalNoOfPassenger) < 1){
		alert("No of Passenger should not be less than one")
		return false;
	}
	
	


    $(".outmostT").hide();
	$("#innerwait").show();
	
	
	return true;
}


// validate tatkal date

function validateDateForTatkal(dptDate)
{
	
	
	var isTatkalId=document.getElementById("tatkal");
	
	if(isTatkalId!=null)
	{
		if(isTatkalId.checked==true)
		{
			if(dptDate=="")
			{
				alert("Please select departure date");
				isTatkalId.checked=false;
				return false; 
			}
			var sysDate=getDtObject(currentDate,"-");
			var chgDate=getDtObject(dptDate,"-");
			
			
			var no=daysElapsed(chgDate, sysDate);
			if(no > 2)
			{
				alert("Tatkal Departure Date can not be more than 2 days from today");
				isTatkalId.checked=false;
				preventDefault();
				return false;
			}	
			return true;	
		}
	}
}

// get date object
function getDtObject(dateStr,del){
	var splitedDate=dateStr.split(del);
	var dtDay=splitedDate[0];
	var dtMnth=splitedDate[1];
	var dtYr=splitedDate[2];
	var dt=new Date();
	dt.setFullYear(dtYr,dtMnth-1,dtDay);
	return dt;
}

// days elapsed
function daysElapsed(date1,date2){
	var d1=Date.UTC(y2k(date1.getYear()),date1.getMonth(),date1.getDate(),0,0,0);
	
	var d2=Date.UTC(y2k(date2.getYear()),date2.getMonth(),date2.getDate(),0,0,0);
	
    var difference =Number(d1)-Number(d2);
    difference=difference/1000/60/60/24;
    return difference;
}
function y2k(number){ 
	return (number<1000)?number+1900:number; 
}







var isFareSrch=false;
var isRouteDetail=false;
var isFareFound=false;
var selectedTrainflag = false;

// train fare accomodation and train info 
function trainFareDetails(src,dest,from,to,jrnyDate,jrnyClass,isTatkal,child,adult,senior,trainNo,trainName,depTimeSrc,arrTimeDest,trainType,isCancelWaitListTicket,nInfant,nWSenior,reqId,trainFoodChoiceFlag)
{	
	
	$(".outmostT").show();
	$("#innerwait").hide();
	
  	selectedTrainflag=true;
  	isRouteDetail=false;
  
  $("#trainNo").val(trainNo);
  $("#trainName").val(trainName);
  $("#sourceStnCode").val(src);
  $("#destStnCode").val(dest);
  $("#sourceStnName").val(from);
  $("#destStnName").val(to);
  $("#depTimeSrc").val(depTimeSrc);
  $("#arrTimeDest").val(arrTimeDest);
  $("#trainTypeVal").val(trainType);
 
  $("#trainCategory").val(trainType);
  $("#trainJourneyClass").val(jrnyClass);
  $("#foodChoiceFlag").val(trainFoodChoiceFlag);
    
  	 isFareFound=false;
  	 
  	 // for train info
  	 setTrain(trainNo,trainName,src,from,dest,to,depTimeSrc,arrTimeDest,jrnyDate);
   
  	
    var	fareDetailsTD=document.getElementById("fareDetailsTD");
    var	avialflotingdisplayTD=document.getElementById("avialflotingdisplay");
	var waitingTable='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Fare Details</p></div><div class="welcomecont"><table  class="filtersearch">';
	waitingTable+='<tbody class="all1">';
	waitingTable+="<tr>";
	waitingTable+="<td style='text-align:center' valign='middle' colspan='2' class='lablevalue'><img src='/pcda/images/loading.gif'/>&nbsp;Updating The Fare</td>";
	waitingTable+="</tr>";
	waitingTable+="</tbody></table></div></div></div>";
	fareDetailsTD.innerHTML=waitingTable;
	
	//alert("After  Fare Details Header");
	
	var waitingAvailtable='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Accommodation Details</p></div><div class="welcomecont"><table  class="filtersearch">';
	waitingAvailtable+='<tbody class="all1">';
	waitingAvailtable+="<tr class='tdborder6'>";
	waitingAvailtable+="<td style='text-align:center' valign='middle' colspan='2' class='lablevalue'><img src='/pcda/images/loading.gif'/>&nbsp;Updating The Availability</td>";
	waitingAvailtable+="</tr>";	
	waitingAvailtable+="</tbody></table></div></div></div>";
	//alert("train no--"+trainNo);
	avialflotingdisplayTD.innerHTML=waitingAvailtable;
	
	
	var	clusterFlag=$('#clusterFlag').val();
	var	clusterMainLeg=$('#clusterMainLeg').val();

	var	clusterPnrNo=$('#clusterPnrNo').val();
	var	clusterReservationId=$('#clusterReservationId').val();
	var displayFareDetais="";
	
	
	$.ajax({
		type: "POST",
		url:$("#context_path").val()+"mb/getFareAccomodationDtls",
		data: 
		{
			originStnCode: src ,destStnCode: dest,srcStn: src,destStn: dest,frmStation: from,toStation: to,depDate: jrnyDate,journeyclass:jrnyClass,
			tatkal: isTatkal,child: child,adult: adult,senior: senior,trainNo: trainNo,isCanWtTckt: isCancelWaitListTicket,trainType: trainType,
			foodChoiceFlag: trainFoodChoiceFlag,infant: nInfant,wsenior: nWSenior,reqId: reqId,clusterFlag: clusterFlag,clusterMainLeg: clusterMainLeg,
			clusterPnrNo: clusterPnrNo,clusterReservationId: clusterReservationId
			},
	
	      
		dataType: "text",
		success: function(data)
		      {
				  
				  
				  var obj = JSON.parse(data);
				 
				 var errorMessage = obj.errorMessage;
				
				  var adultcount=obj.nadult;
				  
				  var childCount = obj.nchild;
				 
				  var seniorCount = obj.nsenior;
				 
				  var wseniorCount = obj.nwsenior;
				 
				
				  var totalPassengerServiceTax =obj.totalPassengerServiceTax ;
				  var totalPassengerCateringCharge=obj.totalPassengerCateringCharge ;
				  var totalIrctcFee=obj.totalIrctcFee ;
				  var totalPassengerCollectibleAmount=obj.totalPassengerCollectibleAmount ;
				  var totalPassengerTatkalFare = obj.totalPassengerTatkalFare;
				  var distance =obj.distance ;
				 
			      var fareObj = obj.fareBean;
			        let adultBaseFare=0.0;
			        let childBaseFare=0.0; 
			        let seniorBaseFare=0.0;
			        let wseniorBaseFare=0.0;
			        let reservationCharge=0.0;
				    let otherCharges=0.0;
				    let suparfastCharges=0.0;
				    let wpserviceCharge=0.0;
				    let wpServiceTax=0.0;
				    let dynamicfare =0.0;
			     
			      
			      
			       var bookingConfig = obj.bookingConfig;
                  $("#bookingConfig").val(bookingConfig);

        var adultCvFormDAmt=new Array();
		var childCvFormDAmt=new Array();
		var mSeniorCvFormDAmt=new Array();
		var wSeniorCvFormDAmt=new Array();

for(var i=0;i<fareObj.length;i++){
	var pxnGenderType=fareObj[i].passengerType;
     if(pxnGenderType=="ADULT_AGE")
				{
				
					if(adultCvFormDAmt.length==0)
					{
						adultCvFormDAmt=getCvFormDIRLAAmount(pxnGenderType,fareObj[i],distance);
						
					}
				   
					adultBaseFare=parseFloat(adultBaseFare)+parseFloat(fareObj[i].baseFare);
					
				}
				if(pxnGenderType=="CHILD_AGE")
				{
					
					if(childCvFormDAmt.length==0)
					{
						childCvFormDAmt=getCvFormDIRLAAmount(pxnGenderType,fareObj[i],distance);
						
					}
					childBaseFare=parseFloat(childBaseFare)+ parseFloat(fareObj[i].baseFare);
				}
				if(pxnGenderType=="WSENIOR_AGE")
				{
					
					if(wSeniorCvFormDAmt.length==0)
					{
						wSeniorCvFormDAmt=getCvFormDIRLAAmount(pxnGenderType,fareObj[i],distance);
						
					}
					wseniorBaseFare=parseFloat(wseniorBaseFare)+ parseFloat(fareObj[i].baseFare);
				}
				if(pxnGenderType=="SENIOR_AGE")
				{
				
					if(mSeniorCvFormDAmt.length==0)
					{
						mSeniorCvFormDAmt=getCvFormDIRLAAmount(pxnGenderType,fareObj[i],distance);
						
					}
					seniorBaseFare=parseFloat(seniorBaseFare)+ parseFloat(fareObj[i].baseFare);
				}	
				
				reservationCharge=parseFloat(reservationCharge)+parseFloat(fareObj[i].reservCharge);
				otherCharges=parseFloat(otherCharges)+parseFloat(fareObj[i].otherCharge);
				suparfastCharges=parseFloat(suparfastCharges)+parseFloat(fareObj[i].superfastCharge);
				wpserviceCharge=parseFloat(wpserviceCharge)+parseFloat(fareObj[i].wpServiceCharge);
				wpServiceTax=parseFloat(wpServiceTax)+parseFloat(fareObj[i].wpServiceTax);
				dynamicfare=parseFloat(dynamicfare)+parseFloat(fareObj[i].dynamicfare);
				
				
}


if(obj==null || obj=='null' || obj=='' || errorMessage!=null){
	displayFareDetais+='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Fare Details</p></div><div class="welcomecont"><table  class="filtersearch">';
			displayFareDetais+="<table border='0' cellspacing='1' cellpadding='4' class='filtersearch' width='100%'>";
			displayFareDetais+='<tbody class="all1"><tr>';
			displayFareDetais+="<td colspan='2' align='center' valign='top' class='lablevalue'>" +
							"Communication Problem with servers !! please try again" +
							"</td>";
			displayFareDetais+="</tr></tbody>";
			displayFareDetais+="<table>" +"</div></div></div>";
			isFareFound=false;
			$("#fareDetailsTD").html(displayFareDetais);
}
else{
       	displayFareDetais+='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Fare Details</p></div><div class="welcomecont">';
        displayFareDetais+="<table border='0' cellspacing='1' cellpadding='4' class='filtersearch' width='100%'>";
		displayFareDetais+='<tbody class="all1">';
	    displayFareDetais+="<tr align='left'><td>Adult Base Fare-"+adultcount+"</td><td>"+adultBaseFare+"</td></tr>";
        displayFareDetais+="<tr align='left'><td>Child Base Fare-"+childCount+"</td><td>"+childBaseFare+"</td></tr>";
	    displayFareDetais+="<tr align='left'><td>Senior Base Fare-"+seniorCount+"</td><td>"+seniorBaseFare+"</td></tr>";
		displayFareDetais+="<tr align='left'><td>Women Senior Base Fare-"+wseniorCount+"</td><td>"+wseniorBaseFare+"</td></tr>";
	    displayFareDetais+="<tr align='left'><td>Reservation Charge</td><td>"+reservationCharge+"</td></tr>";
        displayFareDetais+="<tr align='left'><td>Other</td><td>"+otherCharges+"</td></tr>";
   		displayFareDetais+="<tr align='left'><td >Super Fast Charges</td><td>"+suparfastCharges+"</td></tr>";
   		displayFareDetais+="<tr align='left'><td >WP Service Charges</td><td>"+wpserviceCharge+"</td></tr>";
   		displayFareDetais+="<tr align='left'><td >WP Service Tax</td><td>"+wpServiceTax+"</td></tr>";
   		displayFareDetais+="<tr align='left'><td >Dynamic fare</td><td>"+dynamicfare+"</td></tr>";
   		
		displayFareDetais+="<tr align='left'><td>Concession/Tatkal Charges</td><td>"+totalPassengerTatkalFare+"</td></tr>";
		displayFareDetais+="<tr align='left'><td>Total Passenger Service Tax</td><td>"+totalPassengerServiceTax+"</td></tr>";
		displayFareDetais+="<tr align='left'><td>Total Passenger Catering Charge</td><td>"+totalPassengerCateringCharge+"</td></tr>";
		displayFareDetais+="<tr align='left'><td>Irctc Fee(Inclusive Service Tax)</td><td>"+totalIrctcFee+"</td></tr>";
		displayFareDetais+="<tr align='left'><td>Total Railway Fare</td><td>"+totalPassengerCollectibleAmount+"</td></tr>";
        displayFareDetais+="<tr align='left'><td>Distance(Kms)</td><td>"+distance+"</td></tr>";
	    displayFareDetais+="<tr align='left'><td>Train Type</td><td>"+trainType+"</td></tr>";
	  
	   var trvlTypeId = $("#travelTypeId").val();

	  
	   if(trvlTypeId=='100003'){
		            displayFareDetais+="<tr><td colspan='2' align='left'>&nbsp</td></tr>";
					displayFareDetais+="<tr><td class='heading' colspan='2' align='left' nowrap='true'><b>IRLA Amount Per Passenger</b></td></tr>";
		   
		  	if(adultCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Adult </td></tr>";
						displayFareDetais+="<tr align='left'><td>CV Amount</td><td>"+adultCvFormDAmt[1]+"</td></tr>";
					}
					if(childCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Child </td></tr>";
						displayFareDetais+="<tr align='left'><td>CV Amount</td><td>"+childCvFormDAmt[1]+"</td></tr>";
					}
					if(mSeniorCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Senior Citizen Male </td></tr>";
						displayFareDetais+="<tr align='left'><td>CV Amount</td><td>"+mSeniorCvFormDAmt[1]+"</td></tr>";
					}
					if(wSeniorCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Senior Citizen Female </td></tr>";
						displayFareDetais+="<tr align='left'><td>CV Amount</td><td>"+wSeniorCvFormDAmt[1]+"</td></tr>";
					}
					
		    
	   }
	   
	   if(trvlTypeId=='100004')
				{
					
					
					
					displayFareDetais+="<tr><td colspan='2' align='left'>&nbsp</td></tr>";
					displayFareDetais+="<tr><td class='heading' colspan='2' align='left' nowrap='true'><b>IRLA Amount Per Passenger</b></td></tr>";
					
					
					if(adultCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Adult </td></tr>";
						displayFareDetais+="<tr align='left'><td>Form D Amount</td><td>"+adultCvFormDAmt[0]+"</td></tr>";
					}
					if(childCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Child </td></tr>";
						displayFareDetais+="<tr align='left'><td>Form D Amount</td><td>"+childCvFormDAmt[0]+"</td></tr>";
					}
					if(mSeniorCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Senior Citizen Male </td></tr>";
						displayFareDetais+="<tr align='left'><td>Form D Amount</td><td>"+mSeniorCvFormDAmt[0]+"</td></tr>";
					}
					if(wSeniorCvFormDAmt.length==2){
						displayFareDetais+="<tr align='left'><td class='lablevalue' colspan='2'>Senior Citizen Female </td></tr>";
						displayFareDetais+="<tr align='left'><td>Form D Amount</td><td>"+wSeniorCvFormDAmt[0]+"</td></tr>";
					}
					
					
				}
                    displayFareDetais+="</tbody></table></div></div></div>";
                    isFareFound=true;
                      $("#fareDetailsTD").html(displayFareDetais);
                      
                    }  
                     
                     
                     
                      
                   // accomodation html   
                        var approvedDepDate=$("#appDepDate").val();
                        var accomodateList =obj.accmodationBean.accStatus;
                        
                        var displayAvailDetais="";
                        
                      
	                    
	                    
				  if((errorMessage=="" || errorMessage==null || errorMessage=='null') && accomodateList!=null){
					    displayAvailDetais+='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Check Availability</p></div><div class="welcomecont" style="overflow:auto;">';
					    displayAvailDetais+="<table border='0' cellspacing='1' cellpadding='4' class='filtersearch' width='100%'>";
                        displayAvailDetais+='<tbody class="all1">';
					  for(var i=0;i<accomodateList.length;i++){
						  
						 var availJourneyDate= accomodateList[i].date;
						 var status =  accomodateList[i].status;
						 
					
						
						 var approve = approvedDepDate.split("-");
						
					     var journey = availJourneyDate.split("-");
						 
						  if(approve[2]==journey[2] && approve[1]==journey[1] && approve[0]==journey[0]){
							 
						displayAvailDetais+="<tr class='tdborder7'><td width='10'><input type='radio' name='dateSet' onClick='setJourneyDte(\""+availJourneyDate+"\",\""+status+"\")' checked='true'/></td>";
                           setJourneyDte(availJourneyDate,status);
                           }
                           else{
							   
			            displayAvailDetais+="<tr class='tdborder7'><td width='10'><input type='radio' name='dateSet' onClick='setJourneyDte(\""+availJourneyDate+"\",\""+status+"\")' /></td>";
	 
						 }
						  availJourneyDate= changDateFormatFrmMnth(availJourneyDate);
						 displayAvailDetais+="<td>"+availJourneyDate+"</td>";
			             displayAvailDetais+="<td>"+status+"</td>";
			             displayAvailDetais+="</tr>";
					  }
					   displayAvailDetais+="</tbody><table></div></div></div>";
					   
					
		             
		               $("#avialflotingdisplay").html(displayAvailDetais);
				 
				  }
				  
				  else{		
		displayAvailDetais+='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Accommodation Details</p></div><div class="welcomecont"><table  class="filtersearch">';
		displayAvailDetais+='<tbody class="all1"><tr>';		
		displayAvailDetais+="<td colspan='2' align='center' valign='top' class='lablevalue'>Communication Problem with servers !! please try again</td>";
		displayAvailDetais+="</tr></tbody>";
		displayAvailDetais+="<table></div></div></div>";
		 $("#avialflotingdisplay").html(displayAvailDetais);
	}
				  
                 
                
                      
              }//succes close 
              
           
		
	 });
	
	return true;
}





// set train data as inner html

function setTrain(trainNo,trainName,sourceStnCode,sourceStnName,destStnCode,destStnName,depTimeSrc,arrTimeDest,jrnyDate){
	

	var selectedTrain="";
	selectedTrain+='<div id="program" class="programBlock"><div class="welcomebox"><div class="welcomeHead"><p>Selected Train</p></div><div class="welcomecont"><table class="filtersearch">';
	selectedTrain+='<tbody class="all1"><tr><td style="width:35%">Train Name</td>';
	selectedTrain+="<td height='25' width='65%'>"+trainName+"</td></tr>";
	selectedTrain+="<tr align='left'><td height='25'>Train No</td>";
	selectedTrain+="<td>"+trainNo+"</td></tr>";
	selectedTrain+="<tr  align='left'><td height='25'  class='txtcont'>From</td>";
	selectedTrain+="<td>"+sourceStnName+"("+sourceStnCode+")"+"</td></tr>";
	selectedTrain+="<tr  align='left'><td height='25'  class='txtcont'>To</td>";
	selectedTrain+="<td>"+destStnName+"("+destStnCode+")"+"</td></tr>";
	selectedTrain+="<tr  align='left'><td height='25'  class='txtcont'>Dept. Time</td>";
	selectedTrain+="<td >"+depTimeSrc+"</td></tr>";
	selectedTrain+="<tr align='left'><td height='25'  class='txtcont'>Arrival Time</td>";
	selectedTrain+="<td >"+arrTimeDest+"</td></tr>";
	selectedTrain+="<tr  align='left'><td height='25'  class='txtcont'>Journey Date</td>";
	selectedTrain+="<td id='LeavingDate'>"+jrnyDate+"</td></tr>";
    selectedTrain+="</tbody></table> </div></div></div>";
	
	$("#selectedTrainDisplay").html(selectedTrain);
		return true;
}

var isShowPopUpRouteDtls=false;
var isShowChangeStn=false;
var chkOrg="";
var chkDest="";



// train route pop-up

function showSingleTrainRoute(trainNo,jrnyDate){

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

   ajaxRequestFace = new LightFace.Request(
   {
				   	width: 600,
					height: 400,
					url:  $("#context_path").val()+"mb/getSingleTrainRoute",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							trainNo: trainNo,
							journeyDate: jrnyDate
						},
						method: 'post'
					},
					title: 'Train Route View'
	});
  	ajaxRequestFace.open();
	
	
}

// set jpurney date and status
function setJourneyDte(jrnyDate,isAvail)
{
	
$("#dpt_date").val(jrnyDate);
$("#availabilityStatus").val(isAvail);


var jrnyDteID=document.getElementById("LeavingDate");
	jrnyDteID.innerHTML=jrnyDate;



}


// show month in Word form

function changDateFormatFrmMnth(jrnyDte){

	var jrnyDteStr;
	jrnyDteStr=jrnyDte.split("-");
	
	var jrnyDay=jrnyDteStr[0];
	var monthName=jrnyDteStr[1];
	var monthVal;
	if(monthName=="1")		monthVal="Jan";
	if(monthName=="2")		monthVal="Feb";	
	if(monthName=="3")		monthVal="Mar";
	if(monthName=="4")		monthVal="Apr";
	if(monthName=="5")		monthVal="May";
	if(monthName=="6")		monthVal="June";
	if(monthName=="7")		monthVal="July";
	if(monthName=="8")		monthVal="Aug";
	if(monthName=="9")		monthVal="Sep";
	if(monthName=="01")		monthVal="Jan";
	if(monthName=="02")		monthVal="Feb";	
	if(monthName=="03")		monthVal="Mar";
	if(monthName=="04")		monthVal="Apr";
	if(monthName=="05")		monthVal="May";
	if(monthName=="06")		monthVal="June";
	if(monthName=="07")		monthVal="July";
	if(monthName=="08")		monthVal="Aug";
	if(monthName=="09")		monthVal="Sep";
	if(monthName=="10")		monthVal="Oct";
	if(monthName=="11")		monthVal="Nov";
	if(monthName=="12")		monthVal="Dec";
	
	
	jrnyDteStr=jrnyDay+"-"+monthVal+"-"+jrnyDteStr[2];
	return jrnyDteStr;
}


// calculate CV IRLA AMOUNT
	
	function getCvFormDIRLAAmount(passType,fareDetailsTag,journeyDistance){
		
		var passFare=0;
		var passSaftyCharge=0;
		var passReservCharge=0;
		var passSuperfastCharge=0;
		var otherCharge=0;
		var passTatkalCharge=0;
		
		var formDAmount=0;
		var cvAmount=0;
		
		var journeyClass;
		
		var cvFormDArr=new Array();;
		
		var isTatkalApproved=document.getElementById("isTatkalApproved").value;
		
		            passFare=fareDetailsTag.baseFare;
				    passReservCharge=fareDetailsTag.reservCharge;
				    passSuperfastCharge=fareDetailsTag.superfastCharge;
				    otherCharge=fareDetailsTag.otherCharge;
				    passSaftyCharge=fareDetailsTag.saftyCharge;
				    passTatkalCharge=fareDetailsTag.tatkalFare;
			//	    journeyClass=fareDetailsTag.otherCharge;
			journeyClass="EC";
				
		var calulatedSaftyCharge=getSafetyCharge(journeyClass.toUpperCase(),journeyDistance);
		
		
		passFare=parseInt(passFare)-parseInt(calulatedSaftyCharge);
		
		
		
		if(isTatkalApproved=="Yes"){
			
			formDAmount=(passFare*0.6)+parseInt(passReservCharge)+parseInt(passSuperfastCharge)+parseInt(passTatkalCharge)+parseInt(calulatedSaftyCharge);
			cvAmount=(passFare*0.5)+parseInt(passReservCharge)+parseInt(passSuperfastCharge)+parseInt(passTatkalCharge)+parseInt(calulatedSaftyCharge);
		
		}else{
			
			formDAmount=(passFare*0.6)+parseInt(passReservCharge)+parseInt(passSuperfastCharge)+parseInt(calulatedSaftyCharge);
			cvAmount=(passFare*0.5)+parseInt(passReservCharge)+parseInt(passSuperfastCharge)+parseInt(calulatedSaftyCharge);
			
		}
		
		
		if (formDAmount < 0) {
			formDAmount = -(formDAmount);
		}		
		if (cvAmount < 0) {
			cvAmount = -(cvAmount);
		}		
		
		
		
		formDAmount=Math.round(formDAmount*100)/100;
		cvAmount=Math.round(cvAmount*100)/100;
		
	
		
		cvFormDArr[0]=formDAmount;
		cvFormDArr[1]=cvAmount;
		
		return 	cvFormDArr;			
	}


// calculate safety charge
function getSafetyCharge(jrnyClass,distanceStr){
		
		var saftyChrgFor_1A="50-100";
	    var saftyChrgFor_2A="40-80";
		var saftyChrgFor_3A="30-60";
	 	var saftyChrgFor_3E="30-60";
	 	var saftyChrgFor_CC="20-40";
	 	var saftyChrgFor_SL="10-20";
		var saftyChrgFor_2S="0-0";   
		
		
		var distanceVal=0;
		var saftyChrgConstRangeValue=0;
		var saftyChrg=0;
		var saftyChrgValueArr=new Array();
		var serChargeVal=0;
		if(jrnyClass!=null && !jrnyClass=="" && distanceStr!=null && !distanceStr==""){
			distanceVal=parseInt(distanceStr);
			if(jrnyClass=="1A" || jrnyClass=="EC"){
				saftyChrgConstRangeValue=saftyChrgFor_1A;
			}			
			if(jrnyClass=="2A"){
				saftyChrgConstRangeValue=saftyChrgFor_2A;
			}			
			if(jrnyClass=="3A"){
				saftyChrgConstRangeValue=saftyChrgFor_3A;
			}
			if(jrnyClass=="3E"){
				saftyChrgConstRangeValue=saftyChrgFor_3E;
			}
			if(jrnyClass=="CC"){
				saftyChrgConstRangeValue=saftyChrgFor_CC;
			}			
			if(jrnyClass=="SL"){
				saftyChrgConstRangeValue=saftyChrgFor_SL;
			}			
			if(jrnyClass=="2S"){
				saftyChrgConstRangeValue=saftyChrgFor_2S;
			}
			
			
			if(saftyChrgConstRangeValue!=null){
				saftyChrgValueArr=saftyChrgConstRangeValue.split("-");				
				if(distanceVal <= 500){
					saftyChrg=saftyChrgValueArr[0];
				}else{
					saftyChrg=saftyChrgValueArr[1];
				}
			}			
		}
	
		
		if(saftyChrg!=null && !saftyChrg==""){
				serChargeVal=parseInt(saftyChrg);
		}else{
					serChargeVal=0;
		}
		
		return serChargeVal;
	}



function bookingDtl(){
		

   var flag=false;
   
	
	var checkName = document.getElementsByName('selectedTrain');
	var checkLenght = checkName.length;
	
	for(var i =0 ;i<checkLenght; i++)
	{
	  if(checkName[i].checked)
	  selectedTrainflag = true;
	  flag=true;
	}
	if(selectedTrainflag == false)
	{
	  alert("Please select the Train");
	  flag=false;
	}
	
	if(!isFareFound)
	{
		alert("Fare details not found please try again with select of train");
		flag=false;
	}
if(!validateCommonPage())
	{
		flag=false;
		
	}

	if(flag==false){
	
	return false;
	}else{
		 $('#irctcSrchPage').attr('action', $("#context_path").val()+'mb/getIRPreConfirmForm');
		 $('#irctcSrchPage').submit();
	}
	
}
