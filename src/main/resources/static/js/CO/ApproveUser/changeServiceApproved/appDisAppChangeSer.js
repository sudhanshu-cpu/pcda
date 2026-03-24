function approveChngeService(personalNumber,seqNo) {
	$("#personalNumber").val(personalNumber);
	$("#seqNo").val(seqNo);
	$("#approveChangService").submit();
}


function disapproveChangeService(personalNumber,seqNo) {
	$("#personal_Number").val(personalNumber);
	$("#seq_No").val(seqNo);
	
	$("#disApproveForm").submit();
}