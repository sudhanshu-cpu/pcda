$(document).ready(function() {


	$("#frmBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $('#frmBookingDt').val("");
		}
	});

	$("#toBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $('#toBookingDt').val("");
		}
	});

	$("#frmTransactionDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $('#frmTransactionDt').val("");
		}
	});

	$("#toTransactionDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $('#toTransactionDt').val("");
		}
	});

});



function doGenerate() {
	var result = validateDateFilter();
	if (result) {
//		var statusType=$("#statusType").val();
		var accountOffice = $("#accountOffice").val();
		var serviceProviderType = $("#serviceProviderType").val();

		if (serviceProviderType == '') {
			alert("Please Select The Service Provider.");
			return false;
		}

   if (accountOffice == '') {
			alert("Please Select The Account Office");
			return false;
		}
		if (!accountOffice == '' && !serviceProviderType == '') {

			$("#ackSearchDataForm").submit();

		}
	}
	else {
	return false;
	}

}



function validateDateFilter() {

	var from = $("#frmBookingDt").val();
	var to = $("#toBookingDt").val();


   var fromTran = $("#frmTransactionDt").val();
	var toTran = $("#toTransactionDt").val();

   from = from.split("-");
	to = to.split("-");
	
	fromTran=fromTran.split("-");
	toTran=toTran.split("-");
	var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);
	var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);

	var tSDate = new Date(fromTran[2] + "," + (fromTran[1]) + "," + fromTran[0]);
	var tToDate = new Date(toTran[2] + "," + (toTran[1]) + "," + toTran[0]);

	if (from == "" && to == "" && fromTran == "" && toTran == "") {
		return true;
	}

	if (from == "" && to != "") {
		
		alert("From Date Should Not Empty");
		document.getElementById('frmBookingDt').focus();
		return false;
	}
	if (from != ""  && to == "" ) {
		alert("To Date Should Not Empty");
		document.getElementById('toBookingDt').focus();
		return false;
	} 
	
	if (sDate > eDate) {

		alert("Vouchers Generation To Date Should Be Greater Than Vouchers Generation From Date");
		$("#toBookingDt").focus();
		return false;
	}
	if (fromTran == "" && toTran != "") {
		alert("Transaction From Date Should Not Empty");
		document.getElementById('frmTransactionDt').focus();
		return false;
	}
	if (fromTran != "" && toTran == "") {
		alert(" Transaction To Date Should Not Empty");
		document.getElementById('toTransactionDt').focus();
		return false;
	} 
	
	if (tSDate > tToDate) {

		alert("Transaction To Date Should Be Greater Than Transaction From Date");
		$("#toTransactionDt").focus();
		return false;
	}


	var daysApart = Math.round((eDate - sDate) / 86400000);
	if (daysApart > 14) {
		//alert("Report Is Available For 15 Days Only.");
		//document.getElementById('toBookingDt').focus();
		//return false;
	}

	return true;
}

var fullStringPaymentIds;
var payment_counter = 0;


function selectVoucherRadio(voucherNo) {

	fullStringPaymentIds = new Array();
	payment_counter = 0;

	var radio_array = $("input[name=voucherRadio]");

	//alert("length of radio_array = "+radio_array.length);

	for (var i = 0; i < radio_array.length; i++) {

		//alert("inside loop");

		if ($("#radio_" + i).is(':checked')) {

			$("#button_" + i).show();
			$("#txn_row_" + i).show();

		} else {
			$("#button_" + i).hide();
			$("#txn_row_" + i).hide();
		}

	} //End of loop


	$("#voucherNo").val(voucherNo);
	$("#payment_ids").val(fullStringPaymentIds);

	var checkBoxArray = document.getElementsByName("check_" + voucherNo);
	//alert("length of checkBoxArray length = "+checkBoxArray.length);

	if (checkBoxArray.length > 0) {

		for (var v = 0; v < checkBoxArray.length; v++) {

			$("#ack_" + voucherNo + "_" + v).val("");
			$("#file_" + voucherNo + "_" + v).val("");
			$("#ack_date_" + voucherNo + "_" + v).val("");

			$("#ack_" + voucherNo + "_" + v).disabled = true;
			$("#file_" + voucherNo + "_" + v).disabled = true;
			$("#ack_date_" + voucherNo + "_" + v).disabled = true;

			checkBoxArray[v].checked = false;
		}
	}

}

var emptyInput = '';
var paymentNo = '';

function submitAcknowledgementData() {
	var Voucher_No = $("#voucherNo").val();
	var checkBoxArr = document.getElementsByName("check_" + Voucher_No);
	emptyInput = '';

	if (Voucher_No == '') {
		alert("Please select voucher for saving acknowledgement.");
		return false;
	}
	else if (!isPaymentSelected()) {
		alert("Please select payment.");
		return false;
	}
	else {
		isInputDataOK();

		if (emptyInput != '') {

			if (emptyInput == 'ack_no') {
				alert("Please input acknowledgement No. for payment " + paymentNo + " OR uncheck the payment.");
				return false;
			} else if (emptyInput == 'ack_date') {
				alert("Please input acknowledgement Date for payment " + paymentNo + " OR uncheck the payment.");
				return false;
			} else if (emptyInput == 'ack_date_future') {
				alert("Acknowledgement Date for payment " + paymentNo + " Should Not Be Future Date.");
				return false;
			}
			else if (emptyInput == 'ack_file') {
				alert("Please select file to upload for payment " + paymentNo + " OR uncheck the payment.");
				return false;
			}



		}
		else {
			for(var i=0;i<checkBoxArr.length;i++){
				if($("#file_" + Voucher_No + "_" + i).is(':checked')){
					var fileExt = $("#file_" + Voucher_No + "_" + i).val().toLowerCase().trim();
			var result = fileExt.endsWith(".pdf");
			if (result == false) {

				alert("Please select pdf file to Upload");
				return false;
			}
				}
			
			}
			
				$("#voucherNoo").val(Voucher_No);
				
				$("#voucherSettlementRprtFilter").submit();

			
		}

	}
}


function isPaymentSelected() {

	var isPaySelected = false;

	var voucher_No = $("#voucherNo").val();
	var checkBoxArr = document.getElementsByName("check_" + voucher_No);
//	console.log("checkBoxArr" + checkBoxArr);
	if (checkBoxArr.length > 0) {

		for (var v = 0; v < checkBoxArr.length; v++) {

			if (checkBoxArr[v].checked) {
				isPaySelected = true;
				break;
			}
		}
	}
 
	return isPaySelected;
}


function isInputDataOK() {

	var voucher_No = $("#voucherNo").val();
	var checkBoxArr = document.getElementsByName("check_" + voucher_No);
//	console.log("checkBoxArr input" + checkBoxArr);
	if (checkBoxArr.length > 0) {

		for (var v = 0; v < checkBoxArr.length; v++) {

			if (checkBoxArr[v].checked) {

				paymentNo = checkBoxArr[v].value;

				if ($("#ack_" + voucher_No + "_" + v).val() == '') {
					emptyInput = 'ack_no';
				} else if ($("#ack_date_" + voucher_No + "_" + v).val() == '') {
					emptyInput = 'ack_date';
				} else if ($("#ack_date_" + voucher_No + "_" + v).val() != '' && isFutureDate($("#ack_date_" + voucher_No + "_" + v).val())) {
					emptyInput = 'ack_date_future';
				} else if ($("#file_" + voucher_No + "_" + v).val() == '') {
					emptyInput = 'ack_file';
				}
			}

			if (emptyInput != '') {
				break;
			}

		}
	}

}

function isFutureDate(idate) {
	var today = new Date().getTime(),
		idate = idate.split("/");

	idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
	return (today - idate) < 0 ? true : false;
}

function onChangePaymentCheckbox(checkObj, voucherNo, chkIdx, payID) {

	//alert("value of checkObj == "+checkObj+"  txnId == "+txnId+" amount == "+txnId+ "  voucherIdx == "+voucherIdx+ "  amount == "+amount);

	if ($("#check_" + voucherNo + "_" + chkIdx).is(':checked')) {

		
		$("#ack_" + voucherNo + "_" + chkIdx).val("");
		$("#file_" + voucherNo + "_" + chkIdx).val("");
		$("#ack_date_" + voucherNo + "_" + chkIdx).val("");
		$("#ack_" + voucherNo + "_" + chkIdx).prop("disabled", false);
		$("#file_" + voucherNo + "_" + chkIdx).prop("disabled", false);
		$("#ack_date_" + voucherNo + "_" + chkIdx).prop("disabled", false);

		$("#ack_date_" + voucherNo + "_" + chkIdx).datetimepicker({
			lang: 'en',
			timepicker: false,
			format: 'd-m-Y',
			formatDate: 'd-m-Y',
			scrollMonth: false,
			scrollInput:false,
			yearEnd : 2100,
		onShow: function () {
			  $("#ack_date_" + voucherNo + "_" + chkIdx).val("");
		}
		});

		fullStringPaymentIds[payment_counter] = payID;
		payment_counter++;
		$("#payment_ids").val(fullStringPaymentIds);

	}
	else {

		$("#ack_" + voucherNo + "_" + chkIdx).val("");
		$("#ack_date_" + voucherNo + "_" + chkIdx).val("");
		$("#file_" + voucherNo + "_" + chkIdx).val("");
		$("#ack_" + voucherNo + "_" + chkIdx).prop("disabled", true);
		$("#file_" + voucherNo + "_" + chkIdx).prop("disabled", true);
		$("#ack_date_" + voucherNo + "_" + chkIdx).prop("disabled", true);

		if (fullStringPaymentIds.length > 0) {

			for (var s = 0; s < fullStringPaymentIds.length; s++) {

				if (fullStringPaymentIds[s] == payID) {

					fullStringPaymentIds[s] = '';
					break;
				}

			}
		}

		$("#payment_ids").val(fullStringPaymentIds);

	}

}


