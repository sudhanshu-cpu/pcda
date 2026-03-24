 function approvalUnitMovement(transferId,event){

var reqPoolDiv=document.getElementById("reqPoolDivUnit");
var disAppDiv=document.getElementById("disApproveDivUnit");

	if(event=="disapprove")
		{

			$('#unitMovementAction').val(event);
			$('#approvedMovementId').val(transferId);
			reqPoolDiv.style.display='none';
			disAppDiv.style.display='block';
			return true;
		}
	if(event=="approve")
		{
			$('#approvedMovementId').val("");
			$('#unitMovementAction').val(event);
			$('#approvedMovementId').val(transferId);
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