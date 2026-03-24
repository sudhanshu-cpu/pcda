 $(document).ready(function(){
	 
	  $("#dataDiv").show();
	  $("#cancelDiv").hide();
	 
 });
 
 
 function personalNoView(personalNo, groupId) {
	 
	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

		ajaxUserFace = new LightFace.Request({
			width : 900,
		 	height:450,
			url: $("#context_path").val() + "mb/userInformationOfflineCan",



			buttons: [
				{title: 'Close', event: function () {this.close();}}
			],
			request: {
				data: {
					personalNo: personalNo,
					groupId: groupId
					
				},
				method: 'post'
			},
			title: 'User View'
		});
		ajaxUserFace.open();

	}



	function viewRequestReports(requestId) {

if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
		ajaxUserFace = new LightFace.Request({
			width: 650,
			height: 500,
			url: $("#context_path").val() + "mb/getOfflineCancelDashboardView",

			buttons: [
				{title: 'Close', event: function () {this.close();}}
			],
			request: {
				data: {
					requestId: requestId,
				},
				method: 'post'
			},
			title: 'Request View'
		});
		ajaxUserFace.open();

	}




	function cancelBtnAction(encReqId){
		$("#requestId").val(encReqId);
		$("#dataDiv").hide();
	  $("#cancelDiv").show();
	}
	
	function submitCancelForm(){
	var reason=	$("#reasonForDisapprove").val().trim();
var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
		if(reason==""){
			alert("Please Enter Reason for DisApprove");
			preventDefault();
			
		}
		if(alphaNumRegEx.test(reason)){
			$("#getOfflineCancelDashboard").submit();
		}else{
			alert("Special Characters Are Not Allowed");
			preventDefault();
		
		}
		
	}
	