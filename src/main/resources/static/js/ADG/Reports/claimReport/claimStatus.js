$(document).ready(function() {
	$("#fromDate").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput: false,
		yearEnd: 2100,
		onShow: function() {
			$("#fromDate").val("");
		}

	});

	$("#toDate").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput: false,
		yearEnd: 2100,
		onShow: function() {
			$("#toDate").val("");
		}

	});

}
);
function doGenerate() {


	var result = validateDateFilter();

	if (result) {
		$("#requestClaimStatus").submit();
		return true;

	}
	return false;
}

function validateDateFilter() {
	var fromDate = $("#fromDate").val().trim();
	var toDate = $("#toDate").val().trim();

	if (fromDate == "" && toDate == "") {
		alert("Please provide valid inputs.");
		return false;
	}
	if (fromDate == "" && toDate != "") {
		alert("Please provide From date");
		return false;
	}

	if (fromDate != "" && toDate == "") {
		alert("Please provide To Date");
		return false;
	}

	if ((fromDate != "" && toDate != "")) {
		fromDate = fromDate.split("-");
		toDate = toDate.split("-");

		var eDate = new Date(toDate[2] + "," + (toDate[1]) + "," + toDate[0]);
		var sDate = new Date(fromDate[2] + "," + (fromDate[1]) + "," + fromDate[0]);


		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toDate").focus();
			return false;
		}
		var daysApart = Math.round((eDate - sDate) / 86400000);
		if (daysApart >= 15) {
			alert("Report Is Available For 15 Days Only.");
			$("#toDate").focus();
			return false;
		}
	}
	return true;
}

function viewClaimDetails(serviceId, fromDate, toDate, categoryId) {
	if (parseInt($("#countdown").val()) < 5) {
		alert("Your Session Has Been Expired. Kindly Re-login");
		return false;

	}
	ajaxClaimFace = new LightFace.Request({

		width: 1200,
		height: 500,
		url: $("#context_path").val() + "adg/claimInformation",
		buttons: [{
			title: 'Close', event: function() { this.close(); }
		}],
		request: {
			data: {
				serviceId: serviceId,
				fromDate: fromDate,
				toDate: toDate,
				categoryId: categoryId
			},
			method: "post"
		},
		title: 'Claim Information'

	});
	ajaxClaimFace.open();
}
