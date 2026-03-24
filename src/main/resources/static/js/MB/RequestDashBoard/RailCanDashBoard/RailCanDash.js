$(document).ready(function(){
	$("#panel").show();
         $("#abortCancelDiv").hide();
});





function nocheck(idx) {
	var chkbox=$("#chk_"+idx);
	
   if(chkbox.is(":checked")){  
      chkbox.prop("checked",true)
        return false;
   }else{
	    alert("Not allowed");  
	   chkbox.prop("checked",true)
	   return true;
   }
}

function abortCancellation() {
         $("#panel").hide();
         $("#abortCancelDiv").show();
        
       }




 function proceedToCancel(totalPxn)
 {
 var isProceed=false;  
 var  ticketType= $("#irctcTckType").val();
  
 var ttlNopxn=parseInt(totalPxn);
 
 var cancelChkBoxID;  
 var totalCheck=0;
    if(ttlNopxn > 0){
    	for(var i=0;i<ttlNopxn;i++){
    		cancelChkBoxID=$("#chk_"+i);
    		
    		if(cancelChkBoxID!=null)
    		{
    		if(cancelChkBoxID.is(":checked"))
	    		{
					isProceed=true;
	    		  totalCheck++;
	    		}
    		}
    	}
    }   
    if(!isProceed){
    	alert("Please select the passenger for cancellation");
    	preventDefault();
    	return false;
    }
    
    if(ticketType=='1')
    {
    	if(totalCheck!=ttlNopxn)
    	{
    		alert("In Case Of I-Ticket All Passenger Should be Selected");
    		preventDefault();
    		return false;
    	}
    }
    

   var cancelofficial;
   var cancheckBox;
    
    if(ttlNopxn > 0){
		
		for(var j=0;j<ttlNopxn;j++){
			cancheckBox=$("#chk_"+j);
			if(cancheckBox.is(":checked")){
			cancelofficial=$("#isOfficial_"+j).val();	
				break;
			}
		}
   
    
    	for(var i=0;i<ttlNopxn;i++){
    		cancelChkBoxID=$("#chk_"+i);
    		
    		if(cancelChkBoxID.is(":checked"))
    		{
		
    	if(cancelofficial==$("#isOfficial_"+i).val()){
			
		   }else{
			alert("On Official Ground Should Be Same For All Selected Passenger");
			  preventDefault();
			 return false;
			
		}	
		}
    		
    		
    }   
    }
    
    $("#railCanDashReq").submit();
   
 }






function submitAbortCancel() {
         $("#isAbortCancel").val("true");
         var abortReason= $("#abortReason").val().trim();
       var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
     
        if(""==abortReason)
        {
        alert("Please enter Abort Cancel Reason");
        preventDefault();
        }else{
			if(alphaNumRegEx.test(abortReason)){
				$('#railCanDashReq').attr('action', $("#context_path").val()+'mb/abortRailCanDash');
                $('#railCanDashReq').submit();
			}else{
				alert("Special Characters Are Not Allowed");
				 preventDefault();
			}
		} 
     
         
   }
   