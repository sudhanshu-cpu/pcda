function takeCancellation(val, index, bkgStatus) {

	if (val == 1 && bkgStatus != 15) {
		alert("Please get the ticket cancelled in DTS if journey is not performed.");
		$("#dtsJourneyPerformed" + index).val("");
		return false;
	}

	if (val == 1) {
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table width="100%" class="filtersearch"><tbody class"all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closeCancelDtlsModal(' + index + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right"><input type="button" onclick="setOfficialCanDetails(' + index + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#travel_Start_Date").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function setOfficialCanDetails(index) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#dtsJrnyCanType" + index).val(dtsCanGround);
	$("#dtsJrnyCanSanNo" + index).val(canSanctionNo);
	$("#dtsJrnyCanSanDate" + index).val(canSanDate);
	closeClaimModal();
}

function closeCancelDtlsModal(index) {
	$("#dtsJourneyPerformed" + index).val("");
	closeClaimModal();
}

function takeNonDTSCancellation(val, index) {
	if (val == 1) {
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table width="100%" class="filtersearch"><tbody class="all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closeNonDTSCancelDtlsModal(' + index + ');" class="butn" value="Cancel"/></td>' +
			'<td align="right"><input type="button" onclick="setNONDTSOfficialCanDetails(' + index + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#travel_Start_Date").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function setNONDTSOfficialCanDetails(index) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#nonDTSJrnyCanType" + index).val(dtsCanGround);
	$("#nonDTSJrnyCanSanNo" + index).val(canSanctionNo);
	$("#nonDTSJrnyCanSanDate" + index).val(canSanDate);
	closeClaimModal();
}

function closeNonDTSCancelDtlsModal(index) {

	$("#nonDTSJryPerformed" + index).val("");
	closeClaimModal();
}

function takeNonDTSFlightInfo(val, index) {
	if (val == 'Flight') {
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1">' +
			'<tr><td class="label" style="width:100px;">Booking Agent<span class="mandatory">*</span></td>' +
			'<td><select id="airlineType" style="width:150px;" class="combo" onchange="showOtherAirlineReason(this.value);">' +
			'<option value="">Select</option><option value="AI">Authorized Agents</option><option value="Others">Others</option></select></td>' +
			'<td class="label" id="Other_AI_Info_Lable" style="display:none">Select Reason<span class="mandatory">*</span></td>' +
			'<td id="Other_AI_Info" style="display:none"><select id="otherAirlineType" style="width:190px;" class="combo" onchange="showFAAInput(this.value);">' +
			'<option value="">Select</option>' +
			'<option value="FAA">Approval from Competent Authority</option></select></td></tr><tr id="FAA_Authority_Info" style="display:none">' +
			'<td class="label">Authority No<span class="mandatory">*</span></td><td><input type="text" id="FAA_AuthorityNo" placeholder="Authority No" maxlength="50" size="30" /></td>' +
			'<td class="label">Authority Date<span class="mandatory">*</span></td><td><input type="text" id="FAA_AuthorityDate" readonly="true" placeholder="Authority Date"  maxlength="10" />' +
			'</td></tr><tr><td align="left"><input type="button" onclick="closeNonDTSFlightDtlsModal(' + index + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right" colspan="3"><input type="button" onclick="setNONDTSFlightDetails(' + index + ');" class="butn" value="OK"/></td></tr></tbody></table></div>';
		$("#inner_content").html(innerHtml);
		$('#FAA_AuthorityDate').datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#travel_Start_Date").val(),
	scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#FAA_AuthorityDate').val("");
		} });
		modal.style.display = "block";
	}
}

function showOtherAirlineReason(val) {

	$("#otherAirlineType").val("");
	$("#FAA_AuthorityNo").val("");
	$("#FAA_AuthorityDate").val("");

	if (val == "Others") {
		$("#Other_AI_Info_Lable").show();
		$("#Other_AI_Info").show();
	} else {
		$("#Other_AI_Info_Lable").hide();
		$("#Other_AI_Info").hide();
		$("#FAA_Authority_Info").hide();
	}
}

function showFAAInput(val) {
	if (val == "FAA") {
		$("#FAA_Authority_Info").show();

	} else {
		$("#FAA_Authority_Info").hide();
	}
}

function setNONDTSFlightDetails(index) {

	var airlineType = $("#airlineType").val();
	var otherAirlineType = $("#otherAirlineType").val();
	var authorityNo = $("#FAA_AuthorityNo").val();
	var authorityDate = $("#FAA_AuthorityDate").val();

	if (airlineType == '') {
		alert("Please select Airlines.");
		$("#airlineType").focus();
		return false;
	}

	if (airlineType == "Others") {

		if (otherAirlineType == '') {
			alert("Please Select Reason.");
			$("#otherAirlineType").focus();
			return false;
		}

		if (otherAirlineType == 'FAA') {

			if (authorityNo == '') {
				alert("Please enter Authority No.");
				$("#authorityNo").focus();
				return false;
			}

			if (authorityDate == '') {
				alert("Please Select Authority Date.");
				$("#authorityDate").focus();
				return false;
			}
		}
	}

	$("#airlineType" + index).val(airlineType);
	$("#otherAirlineType" + index).val(otherAirlineType);
	$("#FAA_AuthorityNo" + index).val(authorityNo);
	$("#FAA_AuthorityDate" + index).val(authorityDate);
	closeClaimModal();
}

function closeNonDTSFlightDtlsModal(index) {

	$("#nonDTSModeOfTravel" + index).val("");
	var mySelect = $('#nonDTSClassOfTravel' + index);
	mySelect.find('option:not(:first)').remove();
	closeClaimModal();
}

function closeClaimModal() {
	var modal = document.getElementById("myModal");
	modal.style.display = "none";
	$("#inner_content").html("");
}


function takeMessOrNACInfo(val, index) {
	$("#hotelCity" + index).val("");

	if ($("#userServiceType").val() == '1') {
		if (val == 'Hotel') {
			return true;
		}
		if (val == 'Mess') {
			$("#messLevel" + index).val("Level A");
			return true;
		}
	}

	if (val != '') {
		var modal = document.getElementById("myModal");
		/*if(val=='Hotel'){
			var innerHtml='<table border="0" cellpadding="4" cellspacing="1" width="100%" class="formtxt">' +
				'<tr><td class="label" style="width:100px;">NAC Taken<span class="mandatory">*</span></td>' +
				'<td><select id="NAC_Taken" style="width:100px;" class="combo">' +
				'<option value="">Select</option><option value="Yes">Yes</option><option value="No">No</option></select></td>' +
				'<tr><td align="left"><input type="button" onclick="closeNACDtlsModal('+index+');" class="butn" value="Cancel"/></td>' +
				'<td align="right"><input type="button" onclick="setNACDetails('+index+');" class="butn" value="OK"/></td></tr></table>'; 
		$("#inner_content").html(innerHtml);
		modal.style.display = "block";
			
		}*/
		if (val == 'Mess') {

			var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1">' +
				'<tr><td class="label" style="width:100px;">MESS LEVEL<span class="mandatory">*</span></td>' +
				'<td><select id="mess_level" style="width:100px;" class="combo">' +
				'<option value="">Select</option><option value="Level A">Level A</option><option value="Level B">Level B</option><option value="Level C">Level C</option></select></td>' +
				'<tr><td align="left"><input type="button" onclick="closeMessDtlsModal(' + index + ');" class="butn" value="Cancel"/></td>' +
				'<td style="text-align:right"><input type="button" onclick="setMessDetails(' + index + ');" class="butn" value="OK"/></td></tr></tbody></table></div>';
			$("#inner_content").html(innerHtml);
			modal.style.display = "block";

		}
	}
}

function setNACDetails(index) {
	var NACTaken = $("#NAC_Taken").val();
	if (NACTaken == '') {
		alert("Please select NAC Taken.");
		$("#NAC_Taken").focus();
		return false;
	}

	$("#hotelNACTaken" + index).val(NACTaken);
	closeClaimModal();
}

function closeNACDtlsModal(index) {
	$("#hotelOrMess" + index).val("");
	closeClaimModal();
}

function setMessDetails(index) {
	var messLevel = $("#mess_level").val();
	if (messLevel == '') {
		alert("Please select Mess Level.");
		$("#mess_level").focus();
		return false;
	}

	$("#messLevel" + index).val(messLevel);
	closeClaimModal();
}

function closeMessDtlsModal(index) {
	$("#hotelOrMess" + index).val("");
	closeClaimModal();
}

function setDeductionReason(val, id) {

	if (val != '') {
		if (val == 'Other') {
			var modal = document.getElementById("myModal");
			var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1">' +
				'<tr><td class="label" style="width:100px;">Reason for Deduction<span class="mandatory">*</span></td>' +
				'<td><input type="text" maxlength="50" size="50" id="other_deduction_reason"/></td>' +
				'<tr><td style="text-align:right" colspan="2"><input type="button" onclick="setDeductionReasonDetails(\'' + id + '\');" class="butn" value="OK"/></td></tr></tbody></table></div>';
			$("#inner_content").html(innerHtml);
			modal.style.display = "block";

		} else {
			$("#" + id).val(val);
		}
	} else {
		$("#" + id).val("");
	}
}


function setDeductionReasonDetails(index) {
	var dedReason = $("#other_deduction_reason").val();
	if (dedReason == '') {
		alert("Please enter Reason for Deduction.");
		$("#other_deduction_reason").focus();
		return false;
	}

	$("#" + index).val(dedReason);
	closeClaimModal();
}


function takeLTCCancellation(val, index, passIndex, bkgStatus) {

	if (val == 1 && bkgStatus != 15) {
		alert("Please get the ticket cancelled in DTS if journey is not performed.");
		$("#dtsJourneyPerformed" + index + "_" + passIndex).val("");
		return false;
	}

	if (val == 1) {
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closeLTCCancelDtlsModal(' + index + ',' + passIndex + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right"><input type="button" onclick="setOfficialLTCCanDetails(' + index + ',' + passIndex + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#leave_From").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function closeLTCCancelDtlsModal(index, passIndex) {
	$("#dtsJourneyPerformed" + index + "_" + passIndex).val("");
	closeClaimModal();
}

function setOfficialLTCCanDetails(index, passIndex) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#dtsJrnyCanType" + index + "_" + passIndex).val(dtsCanGround);
	$("#dtsJrnyCanSanNo" + index + "_" + passIndex).val(canSanctionNo);
	$("#dtsJrnyCanSanDate" + index + "_" + passIndex).val(canSanDate);
	closeClaimModal();
}

function takeLTCNonDTSCancellation(val, index, passIndex) {
	if (val == 0) {
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).val(0);
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).attr('readonly', 'true');

		if ($("#nonDTSModeOfTravel" + index).val() != 'Train') {
			addNonDTSRefundAmount(index);
		}
	}
	if (val == 1) {
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).removeAttr('readonly');
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closeLTCNonDTSCancelDtlsModal(' + index + ',' + passIndex + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right"><input type="button" onclick="setLTCNonDTSOfficialCanDetails(' + index + ',' + passIndex + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#leave_From").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function setLTCNonDTSOfficialCanDetails(index, passIndex) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#nonDTSJrnyCanType" + index + "_" + passIndex).val(dtsCanGround);
	$("#nonDTSJrnyCanSanNo" + index + "_" + passIndex).val(canSanctionNo);
	$("#nonDTSJrnyCanSanDate" + index + "_" + passIndex).val(canSanDate);
	closeClaimModal();
}

function closeLTCNonDTSCancelDtlsModal(index, passIndex) {

	$("#nonDTSPassDtlsJourneyPerformed" + index + "_" + passIndex).val("");
	closeClaimModal();
}

function takePTCancellation(val, index, passIndex, bkgStatus) {

	if (val == 1 && bkgStatus != 15) {
		alert("Please get the ticket cancelled in DTS if journey is not performed.");
		$("#dtsJourneyPerformed" + index + "_" + passIndex).val("");
		return false;
	}

	if (val == 1) {
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closePTCancelDtlsModal(' + index + ',' + passIndex + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right"><input type="button" onclick="setOfficialPTCanDetails(' + index + ',' + passIndex + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#start_date_time").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function closePTCancelDtlsModal(index, passIndex) {
	$("#dtsJourneyPerformed" + index + "_" + passIndex).val("");
	closeClaimModal();
}

function setOfficialPTCanDetails(index, passIndex) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#dtsJrnyCanType" + index + "_" + passIndex).val(dtsCanGround);
	$("#dtsJrnyCanSanNo" + index + "_" + passIndex).val(canSanctionNo);
	$("#dtsJrnyCanSanDate" + index + "_" + passIndex).val(canSanDate);
	closeClaimModal();
}

function takePTNonDTSCancellation(val, index, passIndex) {
	if (val == 0) {
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).val(0);
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).attr('readonly', 'true');

		if ($("#nonDTSModeOfTravel" + index).val() != 'Train') {
			addNonDTSRefundAmount(index);
		}
	}
	if (val == 1) {
		$("#nonDTSPassDtlsRefundAmount" + index + "_" + passIndex).removeAttr('readonly');
		var modal = document.getElementById("myModal");
		var innerHtml = '<div class="welcomebox"><table class="filtersearch"><tbody class="all1"><tr>' +
			'<td class="lablevalue">Cancellation Ground:</td><td><select id="dtsCanGround' + index + '" class="combo"><option value="">Select</option><option value="0">Personal</option><option value="1">Official</option></select></td></tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction No:</td><td><input type="text" id="canSanctionNo' + index + '" class="txtfldM" maxlength="50"/> </td> </tr>' +
			'<tr><td class="lablevalue">Cancellation Sanction Date:</td><td><input type="text" class="txtfldM" id="can_sanction_date' + index + '" maxlength="12" readonly="true"/></td>' +
			'</tr><tr><td align="left"><input type="button" onclick="closePTNonDTSCancelDtlsModal(' + index + ',' + passIndex + ');" class="butn" value="Cancel"/></td>' +
			'<td style="text-align:right"><input type="button" onclick="setPTNonDTSOfficialCanDetails(' + index + ',' + passIndex + ');" class="butn" value="OK"/></td></tr></tbody><table></div>';
		$("#inner_content").html(innerHtml);
		$('#can_sanction_date' + index).datetimepicker({ lang: 'en', scrollMonth: false, timepicker: false, format: 'd-m-Y', formatDate: 'd-m-Y', maxDate: 0, defaultDate: $("#start_date_time").val(),
		scrollInput:false,yearEnd : 2100,onShow: function () {
			  $('#can_sanction_date' + index).val("");
		} });
		modal.style.display = "block";
	}
}

function setPTNonDTSOfficialCanDetails(index, passIndex) {

	var dtsCanGround = $("#dtsCanGround" + index).val();
	var canSanctionNo = $("#canSanctionNo" + index).val();
	var canSanDate = $("#can_sanction_date" + index).val();

	if (dtsCanGround == '') {
		alert("Please select cancellation ground.");
		$("#canSanctionNo" + index).focus();
		return false;
	}

	if (dtsCanGround == 1) {
		if (canSanctionNo.trim() == '') {
			alert("Please enter Cancellation Sanction No.");
			$("#canSanctionNo" + index).focus();
			return false;
		}

		if (canSanDate == '') {
			alert("Please enter Cancellation Sanction Date");
			$("#can_sanction_date" + index).focus();
			return false;
		}

	}

	$("#nonDTSJrnyCanType" + index + "_" + passIndex).val(dtsCanGround);
	$("#nonDTSJrnyCanSanNo" + index + "_" + passIndex).val(canSanctionNo);
	$("#nonDTSJrnyCanSanDate" + index + "_" + passIndex).val(canSanDate);
	closeClaimModal();
}

function closePTNonDTSCancelDtlsModal(index, passIndex) {

	$("#nonDTSPassDtlsJourneyPerformed" + index + "_" + passIndex).val("");
	closeClaimModal();
}

function approveTADAClaim(claimOId, event, submitType) {

	$("#claimOId").val(claimOId);
	$("#approveDisApproveAction").val(event);
	$("#submitType").val(submitType);

	var modal = document.getElementById("myModal");
	modal.style.display = "block";
}

function approvedFinalTADAClaim() {
	$("#approveDisApproveForm").submit();
}

function closeTADAApprovalModal() {
	closeClaimModal();
}
