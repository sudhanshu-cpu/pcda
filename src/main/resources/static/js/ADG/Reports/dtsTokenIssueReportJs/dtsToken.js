$(document).ready(function() {
    $("#fromIssueDate").datetimepicker({
        minDate: new Date("2025-06-01"),
        lang: 'en',
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y',
        scrollMonth: false,
        scrollInput: false
    });

    $("#toIssueDate").datetimepicker({
        minDate: new Date("2025-06-01"),
        lang: 'en',
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y',
        scrollMonth: false,
        scrollInput: false
    });
});


function checkValidation() {
    var from = $("#fromIssueDate").val().trim();
    var to = $("#toIssueDate").val().trim();
    if ((from != "" && to != "")) {
        from = from.split("-");
        to = to.split("-");
        var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);
        var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);
        if (eDate < sDate) {
            alert("To Issue Date Should Be Greater Than From Issue Date");
            $("#toIssueDate").focus();
            return false;
        }
        var daysApart = Math.round((eDate - sDate) / 86400000);
        if (daysApart >= 15) {
            alert("Report Is Available For 15 Days Only.");
            $("#toIssueDate").focus();
            return false;
        }
        return true;
    } else {
            alert("Please fill in valid From Issue Date and To Issue Date.");
            (!from ? $("#fromIssueDate") : $("#toIssueDate")).focus();
            return false;
    }
}


function doGenerate() {

    const formId = "#dtsTokenIssueReportFormId";
    const fromIssueDate = $("#fromIssueDate").val();
    const toIssueDate = $("#toIssueDate").val();
    const isValid = (value) => value !== null && value !== undefined && value.trim() !== "";

     if (
               !isValid(fromIssueDate) &&
               !isValid(toIssueDate)
           ) {
               alert("Please provide at least one field.");
               return;
           }

    let shouldSubmit = false;

    if (fromIssueDate != '' ||  toIssueDate != '' ) {
        if (checkValidation()) {
            shouldSubmit = true;
        }else{
           shouldSubmit = false;
        }
    }

    if (shouldSubmit) {
        $(formId).submit();
    }
}

function viewTokenData(tokenIssueDate){
	$("#tokenIssue_Date").val(tokenIssueDate);
	$("#tokenReportFormId").submit();

}

/* AJAX FUNCTION TO SHOW DTS TOKEN REPORT DETAILS ACCORDING TO ISSUE_DATE */
 function viewDTSTokenIssueReports(tokenIssueDate) {
	 if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }

 	ajaxBookingFace = new LightFace.Request({
					width : 1000,
		 			height:450,
					url: $("#context_path").val()+ "adg/getDtsTokenReportDetails",
					buttons: [
						{ title: 'Close', event: function() { this.close(); } }
					],
					request: {
						data: {
							tokenIssueDate: tokenIssueDate,
							//event:"canView"
						},
						method: 'get'
					},
					title: 'Token Issued Report View'
				});
   ajaxBookingFace.open();

}







