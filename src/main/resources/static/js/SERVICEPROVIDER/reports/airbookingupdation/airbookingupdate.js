function getBookingDtls() {
	var bookingId = $("#bookingId").val().trim();

	if (bookingId == '') {
		alert("Please Enter Booking ID");
		$("#bookingId").focus();
		event.preventDefault();

	} else {

		$("#airBookingUpdationform").submit();
	}
}


function saveAirBookingDtls() {
	var passangerCount = parseFloat($("#passangerCount").val());
	var flag = true;
 var result =  checkAllCheckBox();
if(result){
	for (var i = 1; i <= passangerCount; i++) {
		if ($("#checkbox" + i).is(":checked")) {
			var newOnwardPaxId = $("#newOnwardPaxId" + i).val();
			if (newOnwardPaxId == '') {
				alert("Please Enter Pax ID For Row No " + i);
				$('#newOnwardPaxId' + i).focus();
				flag = false;
				return flag;

			}

			var newInvoiceNo = $('#newInvoiceNo' + i).val();
			if (newInvoiceNo == '') {
				alert("Please Enter Invoice Number For Row No " + i);
				$('#newInvoiceNo' + i).focus();
				flag = false;
				return flag;

			}

			var newInvoiceDate = $('#newInvoiceDate' + i).val();
			if (newInvoiceDate == '') {
				alert("Please Enter Invoice Date For Row No " + i);
				$('#newInvoiceDate' + i).focus();
				flag = false;
				return flag;
			}

		}

}
if (flag) {

		$("#airBookingUpdation").submit();
	}
	}else{
		 alert("Please Select Atleast One CheckBox");
		 return false;
	}


	

}


function checkAllCheckBox() {
	var passangerCount = parseFloat($("#passangerCount").val());
	var result = false;
	for (var i = 1; i <= passangerCount; i++) {
		if ($("#checkbox" + i).is(":checked")) {
			result = true;
			return result;
		}
	}
  
   return result;
}



function getCheckBox(sno) {
	if ($('#checkbox' + sno).is(":checked")) {

		$('#newOnwardPaxId' + sno).prop('disabled', false);
		$('#newInvoiceNo' + sno).prop('disabled', false);
		$('#newInvoiceDate' + sno).prop('disabled', false);

		$('#newInvoiceDate' + sno).datetimepicker({
			lang: 'en',
			timepicker: false,
			format: 'd/m/Y',
			formatDate: 'd-m-Y',
			scrollMonth: false,
			scrollInput:false,
			yearEnd : 2100,
		onShow: function () {
			  $('#newInvoiceDate' + sno).val("");
		}
		});

		$('#newBaseFare' + sno).prop('disabled', false);

		$('#newTax' + sno).prop('disabled', false);
		$('#newFuelCharge' + sno).prop('disabled', false);
		$('#newPsfFee' + sno).prop('disabled', false);
		$('#newOtherTax' + sno).prop('disabled', false);
		$('#newCgstTax' + sno).prop('disabled', false);
		$('#newSgstTax' + sno).prop('disabled', false);
		$('#newIgstTax' + sno).prop('disabled', false);
		$('#newGst' + sno).prop('disabled', false);



	}
	else {
		$('#newOnwardPaxId' + sno).prop('disabled', true);
		$('#newInvoiceNo' + sno).prop('disabled', true);
		$('#newInvoiceDate' + sno).prop('disabled', true);
		$('#newBaseFare' + sno).prop('disabled', true);

		$('#newTax' + sno).prop('disabled', true);
		$('#newFuelCharge' + sno).prop('disabled', true);
		$('#newPsfFee' + sno).prop('disabled', true);
		$('#newOtherTax' + sno).prop('disabled', true);
		$('#newCgstTax' + sno).prop('disabled', true);
		$('#newSgstTax' + sno).prop('disabled', true);
		$('#newIgstTax' + sno).prop('disabled', true);
		$('#newGst' + sno).prop('disabled', true);
	}
}

function getCalcInvoiceAmount(sno) {

	var newBaseFare = parseFloat($("#newBaseFare" + sno).val());
	if (newBaseFare == null) {
		newBaseFare = 0.0
	}


	var newTax = parseFloat($("#newTax" + sno).val());

	var newFuelCharge = parseFloat($('#newFuelCharge' + sno).val());

	var newPsfFee = parseFloat($('#newPsfFee' + sno).val());

	var newOtherTax = parseFloat($('#newOtherTax' + sno).val());

	var newCgstTax = parseFloat($('#newCgstTax' + sno).val());

	var newSgstTax = parseFloat($('#newSgstTax' + sno).val());

	var newIgstTax = parseFloat($('#newIgstTax' + sno).val());

	var newGst = parseFloat($('#newGst' + sno).val());


	var totalInvoice = newBaseFare + newTax + newFuelCharge + newPsfFee + newOtherTax + newCgstTax + newSgstTax + newIgstTax + newGst;


	$('#newTotalInvoice' + sno).val(totalInvoice);
	getCalcTotalAmount();
}
function getCalcTotalAmount() {
	var passangerCount = parseFloat($("#passangerCount").val());
	var totalAmount = 0;

	for (var i = 1; i <= passangerCount; i++) {
		var newTotalInvoice = parseFloat($("#newTotalInvoice" + i).val());
		totalAmount = totalAmount + newTotalInvoice;
	}

	$('#newTotalInvoice').val(totalAmount);

}

