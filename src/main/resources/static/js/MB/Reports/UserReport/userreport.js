$( document ).ready(function() {
    
    
//alert("hello");
  
});

 function generateReport(groupId){
	 
	 //debugger;
//	 console.log(groupId,type);
	 
//	 $("#group_Id").val(groupId);
//	 $("#userType").val(type);
	 //$("#userRptFilter").submit();
	 
//	 
// 	if(document.getElementById("personalNumber").value!="")
//	{
//	var userAlias=document.getElementById("personalNumber").value;
//
//	document.getElementById("personalNumber").value=trim(userAlias);
//	
//	
//	}
     
//      document.getElementById("event").value="next";
//      var actionOriginal=document.userRptFilter.action;
//     document.userRptFilter.action=actionOriginal.replace('/page', "");
//	  document.userRptFilter.submit();	

$("#userRptFilter").submit();
	 
 }
 

 
 function viewUserReports(personalNo,groupId){

	
	 var encryptPNo="";
	if(personalNo.trim()!=''){
		var encryptPNo = CryptoJS.AES.encrypt(personalNo,"Hidden Pass").toString();	
}

 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }


 	ajaxUserFace = new LightFace.Request({
		 
			     width: 1200,
			     height: 500,
		              url: $("#context_path").val()+"reports/userInformation",
		              
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							personalNo: encryptPNo,
							groupId:groupId
							//event:"view"
						},
						method: 'post'
					},
					title: 'User View'
				});
   ajaxUserFace.open();
   
   
   	
 }
 
 function encryptPNo(){
	 
	var prsnlNo = $("#personalNo").val().trim();
	
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNo").val(encryptPNo);
}
}
 
function trim(n)
{
  return n.replace(/^\s+|\s+$/g,'');
}
	
