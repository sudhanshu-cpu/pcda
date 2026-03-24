
$(document).ready(function() {

	 $(".js__p_start").simplePopup();

});

 function takeMasterMissingComment(personalNo){
 

   		$.ajax(
   		{
		   url:  $("#context_path").val()+ "mb/masterMissingCommnentHistory",
		   data: {personalNo :personalNo},
		   method:"post",
		   async:false,
		   success: function(result){
	        $("#voucher_Ack_History_Id").html(result.trim());
	         $("#voucher_Ack_Close_Id").show();
	         $(".js__p_start").click();

	     	}
	     });	
  }

function submitRemark(){
	
	var remark=$("#comments").val();
	
	if(remark=='' || remark==null){
		alert("Please Enter Remark")
		return false;
	}else{
		$("#commentForm").submit();
	}
	
}


/*
function submitRequestFormRemarks(personalNo, eventVal) {
	 $("#prnRemrks").val(personalNo);
         $("#evnt12").val(eventVal);
         $("#settleCommentRemarks").submit();
		
	
}
*/

function submitRequestFormRemarks(personalNo){
	
     
   		$.ajax(
   		{
		   url:  $("#context_path").val()+ "mb/settleMasterMissUser",
		   data: {personalNo :personalNo},
		   method:"post",
		   async:false,
		   
		   success: function(result){
	
	        $("#voucher_Ack_History_Id").html(result.trim());
	         $("#voucher_Ack_Close_Id").show();
	         $(".js__p_start").click();
	         
	     	}
	     });	
  }




function viewUserReports(personalNo)
 {
	
	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
	
   ajaxFace = new LightFace.Request(
   {
		width: 700,
		height: 500,
		overlayAll:false,
		fadeOut:5000,
		url: $("#context_path").val()+ "mb/viewUserDetails",
	
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		
		request: { 
				data: { 
						personalNo: personalNo
						
				},
				method: 'post'
			},
			title: 'User View'
	});
	ajaxFace.open();
 }
 


function trim(n) {
	return n.replace(/^\s+|\s+$/g, '');
}

function isEmpty(value) {
	return value == null || "" == value;
}







