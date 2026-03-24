
$(document).ready(function() {
	$("#frmBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput: false,
		yearEnd: 2100,
		onShow: function() {
			$("#frmBookingDt").val("");
		}

	});

	$("#toBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput: false,
		yearEnd: 2100,
		onShow: function() {
			$("#toBookingDt").val("");
		}


	});

});
function validateForm() {
	var from = $("#frmBookingDt").val().trim();
	var to = $("#toBookingDt").val().trim();

	if (from == "" && to == "") {
		alert("Please provide Booking Date");
		return false;
	}

	if (from == "" && to != "") {
		alert("Please provide From Date");
		return false;
	}

	if (from != "" && to == "") {
		alert("Please provide To Date");
		return false;
	}

	if (from != "" && to != "") {
		fromDate = from.split("-");
		toDate = to.split("-");

		var eDate = new Date(toDate[2] + "," + (toDate[1]) + "," + toDate[0]);
		var sDate = new Date(fromDate[2] + "," + (fromDate[1]) + "," + fromDate[0]);


		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toBookingDt").focus();
			return false;
		}
		
		var daysApart = Math.round((eDate - sDate) / 86400000);
		if (daysApart >= 15) {
			alert("Report Is Available For 15 Days Only.");
			$("#toBookingDt").focus();
			return false;
	}
	}

	$("#adgRailAirBkgReportForm").submit();
}
