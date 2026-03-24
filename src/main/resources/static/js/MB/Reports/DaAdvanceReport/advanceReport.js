$( document ).ready(function() {
    
    

  
});

function generateReport(){
	
	
	 //  var actionOriginal=document.advanceReport.action;
	  encryptPNo();
	   document.advanceReport.action=actionOriginal.replace('/page', "");
	  
	   document.advanceReport.submit();	
 }
 
 function encryptPNo(){

	var prsnlNo = $("#personalNo").val().trim();
 
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNo").val(encryptPNo);
}
}
 
/*
 window.onload=function (){
	
 	var selPersonalNo=$("#selPersonalNo").val();
 	var selRequestId=$("#selRequestId").val();
 	var selUnitId=$("#selUnitId").val();
 	var selTravelType=$("#selTravelType").val();
 	var selTravelMode=$("#selTravelMode").val();
 	var selAdvanceType =$("#selAdvanceType").val();
 	
 	if(selPersonalNo!="" ){
 		document.getElementById('personalNo').value=selPersonalNo;
 	}
 	
 	if(selRequestId!="" ){
 		document.getElementById('requestId').value=selRequestId;
 	}
 	if(selUnitId!="" ){
 		document.getElementById('unitId').value=selUnitId;
 	}
 	if(selTravelType!="" ){
 		document.getElementById('travelType').value=selTravelType;
 	}
 	if(selTravelMode!="" ){
 		document.getElementById('travelMode').value=selTravelMode;
 	}
 	if(selAdvanceType!="" ){
 		document.getElementById('advanceType').value=selAdvanceType;
 	}
 }
 */