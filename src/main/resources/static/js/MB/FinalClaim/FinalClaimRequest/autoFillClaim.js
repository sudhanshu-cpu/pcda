function autoFillLTCClaimAmount() {

	var totalClaimedAmount = 0;
	var totalDTSJrnyAmount = 0;

	var dtsLtcJrnyTableIdlen = $("#voucherSettlementConfirmationForm input[name=dtsLtcJrnyTableIdlen]");

	if (dtsLtcJrnyTableIdlen.length > 0) {
		for (var index = 0; index < dtsLtcJrnyTableIdlen.length; index++) {
			totalClaimedAmount = totalClaimedAmount + Number($("#jrnyClaimedAmount" + dtsLtcJrnyTableIdlen[index].value).val());
			totalDTSJrnyAmount = totalDTSJrnyAmount + Number($("#jrnyClaimedAmount" + dtsLtcJrnyTableIdlen[index].value).val());
		}
	}

	var totalSpentAmount = Number(totalClaimedAmount);
	var totalAdvanceTaken = Number(totalDTSJrnyAmount);
	var totalRefundAmount = 0;

	var totalPayAmount = Number(totalSpentAmount) + Number(totalRefundAmount) - Number(totalAdvanceTaken);

	$("#totalSpentAmount").val(parseFloat(totalSpentAmount).toFixed(2));
	$("#totalAdvanceAmount").val(parseFloat(totalAdvanceTaken).toFixed(2));
	$("#totalRefundAmount").val(parseFloat(totalRefundAmount).toFixed(2));
	$("#totalClaimedAmount").val(parseFloat(totalPayAmount).toFixed(2));

}

function validateAutoFillLTCClaimForm() {

	var dtsJourneyDate = $("#voucherSettlementConfirmationForm input[name=dtsJourneyDate]");

	for (var index = 0; index < dtsJourneyDate.length; index++) {
		if ($("#journey_end_date_" + index).val().trim() == '') {
			alert("Please select DTS Journey End Date");
			$("#journey_end_date_" + index).focus();
			return false;
		}
	}

	if ($("#userServiceType").val() == '1') {

	} else {
		if ($("#counterPersonalNo").val().trim() == '') {
			alert("Please enter Counter Singing Authority Personal No");
			$("#counterPersonalNo").focus();
			return false;
		}
	}
	if ($("#claimPreferredDate").val().trim() == '') {
		alert("Please enter Date of Claim preferred by the Individual to the Office");
		$("#claimPreferredDate").focus();
		return false;
	}
	if ($("#claimPreferredSanction").val().trim() == '') {
		alert("Please Select Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken");
		$("#claimPreferredSanction").focus();
		return false;
	}

	if ($("#claimPreferredSanction").val().trim() == 'Yes') {
		if ($("#claimPreferredSanctionNo").val().trim() == '') {
			alert("Please enter Claim preferred Sanction No");
			$("#claimPreferredSanctionNo").focus();
			return false;
		}
		if ($("#claimPreferredSanctionDate").val().trim() == '') {
			alert("Please enter Date of Claim preferred Sanction");
			$("#claimPreferredSanctionDate").focus();
			return false;
		}
	}

	return true;
}

function validateToDate(index) {
	var dateFrom = "";
	var dateTo = "";
	var dateOnwardToTime = '';

	dateFrom = document.getElementById("journey_date_" + index).value;
	dateTo = document.getElementById("journey_end_date_" + index).value;
	if ('' == dateFrom && '' != dateTo) {
		alert("Please select Journey Start Date first.");
		document.getElementById("journey_end_date_" + index).value = '';
	}

	dateOnwardToTime = calculateNumberOfDays(dateFrom, dateTo);

	if (dateOnwardToTime < 1) {
		
		alert("Arrival Date/Time cannot be before the Departure Date/Time");
		document.getElementById("journey_end_date_" + index).value = '';
	}

}

function getCounterPersonalInfo() {

	$.ajax({
		url: $("#context_path").val() + "mb/counterPersonalInfo",
		type: "post",
		data: "userAlias=" + $("#counterPersonalNo").val(),
		datatype: "application/json",
		beforeSend: function() {
			var calHeight = document.body.offsetHeight + 20;
			$("#screen-freeze").css({ "height": calHeight + "px" });
			$("#screen-freeze").css("display", "block");
		},

		complete: function() {
			$("#screen-freeze").css("display", "none");
		},
		success: function(msg) {
//			var msg = parseXML(msg1);
			if (msg.flag == 'true') {
				var pName = msg.name;
				var pLevel = msg.levelName;
				$("#counter_personal_name").html(pName);
				$("#counter_personal_level").html(pLevel);
				$("#counterPersonalName").val(pName);
				$("#counterPersonalLevel").val(pLevel);
			} else {
				$("#counterPersonalNo").val("");
				$("#counter_personal_name").html("");
				$("#counter_personal_level").html("");
				$("#counterPersonalName").val("");
				$("#counterPersonalLevel").val("");
				alert(msg.message);
			}
		}
	});
}

function fileAutoClaim() {

	if (validateAutoFillLTCClaimForm()) {
		var calHeight = document.body.offsetHeight + 20;
		$("#screen-freeze").css({ "height": calHeight + "px" });
		$("#screen-freeze").css("display", "block");

		$("#voucherSettlementConfirmationForm").submit();
	}
}

function fileLTCClaim() {

	var calHeight = document.body.offsetHeight + 20;
	$("#screen-freeze").css({ "height": calHeight + "px" });
	$("#screen-freeze").css("display", "block");

	$("#fileFullClaimForm").submit();
}

function parseXML(xml) {
	if (window.ActiveXObject && window.GetObject) {
		var dom = new ActiveXObject('Microsoft.XMLDOM');
		dom.loadXML(xml);
		return dom;
	}
	if (window.DOMParser)
		return new DOMParser().parseFromString(xml, 'text/xml');
	throw new Error('No XML parser available');
}

function calculateNumberOfDays(dateFrom, dateTo) {
	var dateFromFormat = '';
	var dateToFormat = '';

	var fromDateFormat = dateFrom.split(' ');
	var toDateFormat = dateTo.split(' ');

	if (fromDateFormat.length > 0 && toDateFormat.length > 0) {
		dateFromFormat = fromDateFormat[0].split('-');
		dateToFormat = toDateFormat[0].split('-');
	}

	var newDateFromFormat = new Date(dateFromFormat[1] + ' ' + dateFromFormat[0] + ' ' + dateFromFormat[2]);
	var dateFromMonth = newDateFromFormat.getMonth();
	var dateFromDay = newDateFromFormat.getDate();
	var dateFromYear = newDateFromFormat.getFullYear();

	var newDateFrom = new Date(dateFromYear, dateFromMonth, dateFromDay, 0, 0, 0)

	var newDateToFormat = new Date(dateToFormat[1] + ' ' + dateToFormat[0] + ' ' + dateToFormat[2]);
	var dateToMonth = newDateToFormat.getMonth();
	var dateToDay = newDateToFormat.getDate();
	var dateToYear = newDateToFormat.getFullYear();

	var newDateTo = new Date(dateToYear, dateToMonth, dateToDay, 0, 0, 0);

	var one_day = 1000 * 60 * 60 * 24;
	var date1_ms = newDateTo.getTime();
	var date2_ms = newDateFrom.getTime();
	var difference_ms = date1_ms - date2_ms;
	var diff = Math.round(difference_ms / one_day);

	return diff + 1;
}
