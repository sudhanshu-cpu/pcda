$(document).ready(function(){

    $("#disApproveDiv").hide();
	
	
});	
	var isChkForGovInt=false;
function chkForGovtInt(){
isChkForGovInt=true;
}
	
  function submitRequestForm(bookingId,event){
            var disAppDiv=$("#disApproveDiv");
	
		   
		   if(!isChkForGovInt){
		    alert("Please select radio button for approving on Government Interest");
		preventDefault();
		   }
		   
	  if(event=="disapprove")
	{
		disAppDiv.show();
		$("#approveDiv").hide();
	  preventDefault();
	}
	$("#event").val("approve");
	       $("#irtdrreqpage").submit();	
	       return true;
  }
  function submitDisapprove()
{
	
	var disapproveDetail=$("#disaproveReason").val();
	
	
	
	if(disapproveDetail==' ' || disapproveDetail=='')
	{
		alert("Please Enter Reason for Disapproval of Request");
		$("#disaproveReason").focus();
		preventDefault();
	}
		$("#event").val("disapprove");
		 $("#irtdrreqpage").submit();	
	       return true;
  }

