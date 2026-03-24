 function showAirTicketDetails(bookingId)
	 {
	    var urlToSet=$("#context_path").val() + "reports/getBookingData";
	    
	   
	    
	 var  ajaxUserFace = new LightFace.Request(
	    {
	 				width: 850,
					height: 400,
					url: urlToSet,
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: { 
						data: { 
							bookingId: bookingId
						},
						method: 'post'
					},
					title: 'Ticket Details View'
		});
	   	ajaxUserFace.open();
	  
	 }	 
	 
	 
function encryptPNo() {
	var prsnlNo = $("#personalNumber").val().trim();

	if (prsnlNo != '') {
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo, "Hidden Pass");
		$("#personalNumber").val(encryptPNo);
	}
}
