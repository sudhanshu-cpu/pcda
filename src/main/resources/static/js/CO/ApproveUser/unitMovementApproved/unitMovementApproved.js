 
 function approvalUnitMovement(movementId,event){

var reqPoolDiv=document.getElementById("reqPoolDivUnit");
var disAppDiv=document.getElementById("disApproveDivUnit");
	if(event=="disapprove")
		{
			//document.getElementById("disApprovedMovementId").value="";
			document.getElementById("unitMovementAction").value=event;
			document.getElementById("approvedMovementId").value=movementId;
			//document.getElementById("approvedMovementId").value="";
			reqPoolDiv.style.display='none';
			disAppDiv.style.display='block';
			return true;
		}
	if(event=="approve")
		{
			document.getElementById("approvedMovementId").value="";
			document.getElementById("unitMovementAction").value=event;
			document.getElementById("approvedMovementId").value=movementId;
		//	document.getElementById("disApprovedMovementId").value="";
			//document.getElementById("event").value="next";
		    $("#approvalUnitMovement").submit();
		}

}

function submitDisapprove()
{
	var prNo=$("#remark").val();
   if(prNo=='' || prNo==null){
	   alert("Please Enter Disapproved Reason")
	   return false;
   }else{
   $("#approvalUnitMovement").submit();
   }
}
