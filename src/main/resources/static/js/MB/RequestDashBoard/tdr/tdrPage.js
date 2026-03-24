$(document).ready(function(){
	
$("#isTdrGuardSlip_1").prop("checked", true)
		
		$("#tdrGuardSlipAmount").val("");
		$("#tdrGuardSlipNo").val("");
		$("#tdrGuardSlipDate").val("");

		$("#tdrGuardSlipAmount").prop("disabled", true);
		$("#tdrGuardSlipNo").prop("disabled", true);
		$("#tdrGuardSlipDate").prop("disabled", true);
		



	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			return this.optional(element) || regexp.test(value);
		},
		"Please enter valid details."
	);

	$("#tdrPnrForm").validate({

		rules: {
			pnrNo: {
				regex: /^[0-9]+$/,
				 maxlength: 10,
				  minlength: 10
			
			},
		},

		messages: {
			pnrNo: {
				regex: "Only numeric allowed in PNR Number",
				maxlength:"Data Length for field PNR Number should be minimum 10 and maximum 10",
				minlength:"Data Length for field PNR Number should be minimum 10 and maximum 10"
			},
	
		},
		errorElement: "em",
		errorPlacement: function(error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element);
			}
		},
		highlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-success").removeClass("has-error");
		}
	});

																
	
});

function guardClick() {
	
	if ($("#isTdrGuardSlip_0").is(":checked")) {
		$("#tdrGuardSlipAmount").prop("disabled", false);
		$("#tdrGuardSlipNo").prop("disabled", false);
		$("#tdrGuardSlipDate").prop("disabled", false);
		$("#tdrGuardSlipDate").attr("readonly", true);
		
			$("#tdrGuardSlipDate").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd/m/Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $('#tdrGuardSlipDate').val("");
		}
	});
	}

	if ($("#isTdrGuardSlip_1").is(":checked")) {

		$("#tdrGuardSlipAmount").val("");
		$("#tdrGuardSlipNo").val("");
		$("#tdrGuardSlipDate").val("");

		$("#tdrGuardSlipAmount").prop("disabled", true);
		$("#tdrGuardSlipNo").prop("disabled", true);
		$("#tdrGuardSlipDate").prop("disabled", true);
	}
}

var isCheckedForOff = false;
function chkForOfficial()
{
  isCheckedForOff=true;
}  
function submitForm(bookingId, totalPassenger, check, actionType) {

	var isProceed = false;

	if (check == 'false') {
		
			if (!isCheckedForOff) {
			alert("Please select radio button for check official ground");
			preventDefault();
		}

		if ($('input[name=isTdrGuardSlip]:checked').val() == '0') {

			var tdrGuardSlipAmount = $("#tdrGuardSlipAmount").val();
			var tdrGuardSlipNo = $("#tdrGuardSlipNo").val();
			var tdrGuardSlipDate = $("#tdrGuardSlipDate").val();

			if (tdrGuardSlipAmount == "") {
				alert("Please enter Guard Certificate Slip Amount");
				$("#tdrGuardSlipAmount").focus();
				preventDefault();
			}

	      if(checkCurrency(tdrGuardSlipAmount)){
 				alert("Guard Certificate Slip Amount should be numeric");
 				$("#tdrGuardSlipAmount").focus();
 			  preventDefault();
 			}
 			
 			if(tdrGuardSlipNo==""){
 				alert("Please Select Guard Certificate details or select No if Guard Certificate not taken");
 				$("#tdrGuardSlipNo").focus();
 			   preventDefault();
 			}
 			if(checkAlphaNumericWithSpace(tdrGuardSlipNo)){
 				alert("Guard Certificate Slip No should be alphanumeric");
 				$("#tdrGuardSlipNo").focus();
 			  preventDefault();
 			}


			if (tdrGuardSlipDate == "") {
				alert("Please enter Guard Certificate Date");
				$("#tdrGuardSlipDate").focus();
				preventDefault();
			}
		}

		if ($("#tdrReason").val() == '') {
			alert("Please select reason for tdr");
            preventDefault();
		}

	
	}

	var pxnForTdr;

	if (totalPassenger > 0) {
		if (check == 'true') {
			isProceed = true;
		}
		else {
			for (var i = totalPassenger; i >= 1; i--) {
			
				if ($("#chk_" + i).is(":checked")){
					isProceed = true;
					}
					
			}
		}
	}
	
	if (!isProceed) {
		alert("Please select at least one passenger");
		preventDefault();
	}
	 else if(isProceed && !$("#tdrReason").val() == '' && !$("#isTdrOfficial").val() == '') {

	
            $("#tdrBkgForm").submit();

	}
}



function chkForOfficial() {
	isCheckedForOff = true;
}
function checkCurrency(textValue){
	var numberRegEx = /^[0-9.]*$/;
	if(!numberRegEx.test(textValue)){
		return true;
	}else{
		return false;
	} 
}

function setReason(){
 var tdrReason=$("#tdrReason option:selected").text();

 $("#tdrReasonTxt").val(tdrReason);
}

function checkAlphaNumericWithSpace(textValue){
	var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
	if(!alphaNumRegEx.test(textValue)){
		return true;
	}else{
		return false;
	}  
} 
