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
			$('#frmBookingDt').val("");
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
			$('#toBookingDt').val("");
		}

	});

});


function checkValidation() {

	var from = $("#frmBookingDt").val().trim();
	var to = $("#toBookingDt").val().trim();
	var prsnlNo = $("#personalNo").val().trim();


	if ((from == "" && to == "") && prsnlNo == "") {
		alert("Please provide (from-to date) or personal No")
		return false;
	}


	if (from != "" && to != "") {
		from = from.split("-");
		to = to.split("-");

		var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);
		var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);


		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toBookingDt").focus();
			return false;
		}

		var daysApart = Math.round((eDate - sDate) / 86400000);
		if (daysApart >= 30) {
			alert("Report Is Available For 30 Days Only.");
			$("#toBookingDt").focus();
			return false;
		}

	}


	if (from == "" && !to == "") {
		alert("Please provide From date");
		return false;
	}

	if (!from == "" && to == "") {
		alert("Please provide To Date");
		return false;
	}

	return true;

}

function generateReport() {
	var result = checkValidation();

	if (result) {
		encryptPNo();
		$("#excptnlBkgRptFilter").submit();
	}
	else {
		return false;
	}

}

function encryptPNo() {

	var prsnlNo = $("#personalNo").val().trim();

	if (prsnlNo != '') {
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo, "Hidden Pass").toString();
		$("#personalNo").val(encryptPNo);
	}
}

function viewExpBookingReports(bookingId) {

	if (parseInt($("#countdown").val()) < 5) {
		alert("Your Session Has Been Expired. Kindly Re-login");
		return false;
	}

	ajaxBookingFace = new LightFace.Request({
		width: 900,
		height: 450,
		url: $("#context_path").val() + "reports/expTicketsBkgDetails",
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		request: {
			data: {
				bookingId: bookingId,
				event: "view"
			},
			method: 'post'
		},
		title: 'Booking View'
	});
	ajaxBookingFace.open();

}