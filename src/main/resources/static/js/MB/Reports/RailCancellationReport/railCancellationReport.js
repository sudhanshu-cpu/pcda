
$(document).ready(function() {
    $("#cancellationFromDate").datetimepicker({
        onShow: function() {
            $("#frmBookingDt").val("");
        },
        lang: 'en',
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y',
        scrollMonth: false,
        scrollInput: false
    });
    $("#cancellationToDate").datetimepicker({
        onShow: function() {
            $("#toBookingDt").val("");
        },
        lang: 'en',
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y',
        scrollMonth: false,
        scrollInput: false
    });
});

 function viewCancellationReports(bookingId) {

	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

 	ajaxBookingFace = new LightFace.Request({
					width : 1000,
		 			height:450,
					url: $("#context_path").val()+ "reports/getRailTktCancellationDetails",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: {
						data: {
							bookingId: bookingId,
							//event:"canView"
						},
						method: 'post'
					},
					title: 'Cancellation View'
				});
   ajaxBookingFace.open();

}


function checkValidation() {
    var from = $("#cancellationFromDate").val().trim();
    var to = $("#cancellationToDate").val().trim();
    if ((from != "" && to != "")) {
        from = from.split("-");
        to = to.split("-");
        var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);
        var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);
        if (eDate < sDate) {
            alert("To Cancellation Date Should Be Greater Than From Cancellation Date");
            $("#cancellationToDate").focus();
            return false;
        }
        var daysApart = Math.round((eDate - sDate) / 86400000);
        if (daysApart > 90) {
            alert("Report Is Available For 90 Days Only.");
            $("#cancellationToDate").focus();
            return false;
        }
        return true;
    } else {
            alert("Please fill in valid From Cancellation Date and To Cancellation Date.");
            (!from ? $("#cancellationFromDate") : $("#cancellationToDate")).focus();
            return false;
    }
}


function doGenerate() {

    const formId = "#railCancellationFormId";
    const isValid = (value) => value !== null && value !== undefined && value.trim() !== "";
    const personalNumber = $("#personalNumber").val();
    const pnr = $("#pnr").val();
    const bookingId = $("#bookingId").val();
    const refundId = $("#refundId").val();
    const cancellationFromDate = $("#cancellationFromDate").val();
    const cancellationToDate = $("#cancellationToDate").val();

     if (
            !isValid(personalNumber) &&
            !isValid(pnr) &&
            !isValid(bookingId) &&
            !isValid(refundId) &&
            !isValid(cancellationFromDate) &&
            !isValid(cancellationToDate)
        ) {
            alert("Please provide at least one field.");
            return;
        }


    let shouldSubmit = false;

    if (isValid(personalNumber)) {
        const encryptedPNo = CryptoJS.AES.encrypt(personalNumber.trim(), "Hidden Pass").toString();
        $("#personalNumber").val(encryptedPNo);
        shouldSubmit = true;
    }

    if (cancellationFromDate != '' ||  cancellationToDate != '' ) {
        if (checkValidation()) {
            shouldSubmit = true;
        }else{
           shouldSubmit = false;
        }
    }

    if (isValid(pnr) || isValid(bookingId) || isValid(refundId)) {
        shouldSubmit = true;
    }

    if (shouldSubmit) {
        $(formId).submit();
    }
}






