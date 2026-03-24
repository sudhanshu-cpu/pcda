function getBookingDtls() {
	var bookingId = $("#bookingId").val().trim();

	if (bookingId == '') {
		alert("Please Enter Booking ID");
		$("#bookingId").focus();
		event.preventDefault();

	} else {

		$("#airRefundUpdationform").submit();
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

function saveAirCanDtls() {
	var passangerCount = parseFloat($("#passangerCount").val());
	var flag = true;
 var result =  checkAllCheckBox();
if(result){
	for (var i = 1; i <= passangerCount; i++) {
		if ($("#checkbox" + i).is(":checked")) {
			var cancellationTaxId = $('cancellationTaxId'+i).value;
		  	 if(cancellationTaxId=='')
				{
					alert("Please Enter Cancellation Id For Row No "+i);
					$('cancellationTaxId'+i).focus();
					flag=false;
					return flag;
								
				 }

		var newCreditNoteNo = $('newCreditNoteNo'+i).value;
		  	 if(newCreditNoteNo=='')
				{
					alert("Please Enter Credit Note Number in Row No "+i);
					$('newCreditNoteNo'+i).focus();
					flag=false;
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

		$("#airRefundUpdation").submit();
	}
	}else{
		 alert("Please Select Atleast One CheckBox");
		 event.preventDefault();
	}


	

}


function getCheckBox(sno) {
	if ($('#checkbox' + sno).is(":checked")) {

		$('#cancellationTaxId' + sno).prop('disabled', false);
		$('#newCreditNoteNo' + sno).prop('disabled', false);
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

		$('#newTotalRefund' + sno).prop('disabled', false);

		$('#newBaseFare' + sno).prop('disabled', false);
		$('#newCanYq' + sno).prop('disabled', false);
		$('#newPsfFee' + sno).prop('disabled', false);
		$('#newTax' + sno).prop('disabled', false);
		$('#newOtherTax' + sno).prop('disabled', false);
		$('#newCanAirline' + sno).prop('disabled', false);
		$('#newOtherTaxOnSegment' + sno).prop('disabled', false);
		$('#newCgstTax' + sno).prop('disabled', false);
		
		$('#newIgstTax' + sno).prop('disabled', false);
		$('#newSgstTax' + sno).prop('disabled', false);
		$('#newCanSsrAmount' + sno).prop('disabled', false);
		$('#newCanEducess' + sno).prop('disabled', false);
		$('#newCanHigherEducess' + sno).prop('disabled', false);
		$('#newCanRxChargeAirline' + sno).prop('disabled', false);
		$('#newCanRxPenaltyCharge' + sno).prop('disabled', false);
		$('#newEducessOnAirLine' + sno).prop('disabled', false);
		$('#newHigherEducessOnAirLine' + sno).prop('disabled', false);
		$('#newCanServiceTax' + sno).prop('disabled', false);
		
		



	}
	else {
		$('#cancellationTaxId' + sno).prop('disabled', true);
		$('#newCreditNoteNo' + sno).prop('disabled', true);
		$('#newInvoiceDate' + sno).prop('disabled', true);


		$('#newTotalRefund' + sno).prop('disabled', true);

		$('#newBaseFare' + sno).prop('disabled', true);
		$('#newCanYq' + sno).prop('disabled', true);
		$('#newPsfFee' + sno).prop('disabled', true);
		$('#newTax' + sno).prop('disabled', true);
		$('#newOtherTax' + sno).prop('disabled', true);
		$('#newCanAirline' + sno).prop('disabled', true);
		$('#newOtherTaxOnSegment' + sno).prop('disabled', true);
		$('#newCgstTax' + sno).prop('disabled', true);
			$('#newSgstTax' + sno).prop('disabled', false);
		$('#newIgstTax' + sno).prop('disabled', true);
		$('#newCanSsrAmount' + sno).prop('disabled', true);
		$('#newCanEducess' + sno).prop('disabled', true);
		$('#newCanHigherEducess' + sno).prop('disabled', true);
		$('#newCanRxChargeAirline' + sno).prop('disabled', true);
		$('#newCanRxPenaltyCharge' + sno).prop('disabled', true);
		$('#newEducessOnAirLine' + sno).prop('disabled', true);
		$('#newHigherEducessOnAirLine' + sno).prop('disabled', true);
		$('#newCanServiceTax' + sno).prop('disabled', true);
		
	}
}