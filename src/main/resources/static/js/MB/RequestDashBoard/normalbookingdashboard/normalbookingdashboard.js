var lightFaceObject = null;
var lightFaceObjectOld = null;
var lightFaceReportObject = null;


function submitRequestForm(reqId,event,reqType,encReqId,statIndex)
{


	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

	var message = "";
	 
	 
	if(event=="airbook")
	{
	var imageurltitle=""
	
		var originStr=$('#originAirport_'+reqId).val();
		var destinationStr=$('#destinationAirport_'+reqId).val();
		
		var originName=originStr.substring(0, originStr.lastIndexOf("("));
		
		var destinationName =destinationStr.substring(0, destinationStr.lastIndexOf("("));
		
		var journeyDate=$('#journeyDate_'+reqId).val();
		var returnJourneyDate=$('#returnJourneyDate_'+reqId).val();
		
		
		
		
		if(returnJourneyDate!=""){
				message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" and returning on - " + returnJourneyDate + "</b> <br/><br/>is being populated... <br/>Please wait ...</center>";
				}
				else{
			message = "<center>Flights From<br/><br/><b>"+originName+"</b> <br/><br/>To<br/><br/><b>"+destinationName+"</b>  <br/><br/><b> For going on - "+journeyDate+" </b> <br/><br/>is being populated... <br/>Please wait ...</center>";
		}
   	 	box = new LightFace({ 
    			title: " Loading ...",
				width: 410,
				content: message,
		});
		lightFaceObjectOld = box;
   		box.open();
   		
   		var calHeight=document.body.offsetHeight+20;
   		$("#screen-freeze").css({"height":calHeight + "px"} );
   		$("#screen-freeze").show();
   		
   		$("#airRequestId"+statIndex).val(encReqId);
	
	    $("#airBook"+statIndex).submit();
   	}
  
  if(event=="railBook"){
	
	   $("#railRequestId"+statIndex).val(encReqId);
	    $("#railBookForm_"+statIndex).submit();
}

}


function viewPersonalNo(personalNo)
 {

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
   ajaxRequestFace = new LightFace.Request(
   {
				   	width: 900,
					height: 400,
					url:  $("#context_path").val()+"mb/getNormalPersonalNoView",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							personalNo: personalNo
						},
						method: 'post'
					},
					title: 'Request View'
	});
  	ajaxRequestFace.open();
 }
function viewExcepPersonalNo(personalNo)
 {

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
   ajaxRequestFace = new LightFace.Request(
   {
				   	width: 700,
					height: 400,
					url:  $("#context_path").val()+"mb/getExceptionalPersonalNoView",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							personalNo: personalNo
							
						},
						method: 'post'
					},
					title: 'Request View'
	});
  	ajaxRequestFace.open();
 }


function viewRequestReports(requestId)
 {

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	   
   ajaxRequestFace = new LightFace.Request(
   {
				   	width: 650,
					height: 400,
					url:  $("#context_path").val()+"mb/getRailBookReqIdView",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							requestViewId: requestId
						},
						method: 'post'
					},
					title: 'Request View'
	});
  	ajaxRequestFace.open();
 }

function viewAirRequestReport(requestId)
{

	if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

	if(lightFaceReportObject!=null)
	{
		lightFaceReportObject.destroy();
		lightFaceReportObject = null;
	}
	
   	ajaxRequestFace = new LightFace.Request
   	({
 		 	width: 950,
			height: 350,
		url: $("#context_path").val()+"mb/getAirBookReqIdView",
		buttons: [{ title: 'Close', event: function() { this.close(); } }],
		request: { 
					data: { 
							requestViewId: requestId,
							requestCondition: 'savedRequestDetail'
						  },
					method: 'post'
				  },
		title: 'Air Request View'
	});
  lightFaceReportObject = ajaxRequestFace;
  ajaxRequestFace.open();
}

