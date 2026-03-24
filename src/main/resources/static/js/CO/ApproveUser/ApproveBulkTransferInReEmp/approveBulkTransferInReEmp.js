 function approvalBulkTransferInReEmp(transferId,event){


	if(event=="disapprove")
		{

			$('#unitMovementAction').val(event);
			$('#approvedMovementId').val(transferId);
			$("#reqPoolDivUnit").hide();
			$("#disApproveDivUnit").show();
			
			
			return true;
		}
	if(event=="approve")
		{
			$('#approvedMovementId').val("");
			$('#unitMovementAction').val(event);
			$('#approvedMovementId').val(transferId);
		    $("#approveBulkTransferInReEmpForm").submit();
		}

}

function submitDisapprove()
{
	var prNo=$("#remark").val();
   if(prNo=='' || prNo==null){
	   alert("Please Enter Disapproved Reason")
	   return false;
   }else{
   $("#approveBulkTransferInReEmpForm").submit();
   }
}