
var fullStringUsrs = new Array();
var user_counter = 0;



$(document).ready(function() {
	$("#frmBookingDt").datetimepicker({
		timepicker: false,
		format: 'd-m-Y',
		scrollMonth: false ,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#frmBookingDt").val("");
		}
	});

	$("#toBookingDt").datetimepicker({
		timepicker: false,
		format: 'd-m-Y',
		scrollMonth: false ,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#toBookingDt").val("");
		}
	});

});

function checkValidation() {
	
	var from = $("#frmBookingDt").val();
	var to = $("#toBookingDt").val();
	var pNo=$("#personal").val();
	
	if ((from != "" && to != "")) {
		from = from.split("-");
		to = to.split("-");
		var eDate = new Date(to[2] + "," + (to[1]) + "," + to[0]);
		var sDate = new Date(from[2] + "," + (from[1]) + "," + from[0]);

		if (eDate < sDate) {
			alert("To Date Should Be Greater Than From Date");
			$("#toBookingDt").focus();
			return false;
		} else {
			return true;
		}
	}
	if (from != "" && to == '') {
		alert("To Date Should Not be Empty");
		return false;
	}
	if (to != "" && from == '') {
		alert("From Date Should Not be Empty");
		return false;
	}
	
	if(to=="" && from=="" && pNo==""){
		alert("Kindly Provide the Valid Input Before Search");
		return false;
	}
	return true;

}



function doSelectedAction(actionVal) {

	var checkboxArr = document.getElementsByName("usr_checkbox");

	var isRecordSelected = false;
	if (checkboxArr.length > 0) {

		for (var x = 0; x < checkboxArr.length; x++) {
			if ($("#usr_" + x).is(":checked")) {

				isRecordSelected = true;
				break;
			}
		}

		if (isRecordSelected) {
			if (actionVal == 'MasterMissing') {
				for (var x = 0; x < checkboxArr.length; x++) {
					if ($("#usr_" + x).is(":checked")) {


						if ($("#remark_" + x).val() == '' || $("#remark_" + x).val() == ' ') {
							alert("Please Enter Remarks");
                           preventDefault();
						}
					}
				} //End of loop
			}
			if (actionVal == 'Verified') {
				for (var x = 0; x < checkboxArr.length; x++) {
					if ($("#usr_" + x).is(":checked")) {


						if ($("#remark_" + x).val() == '' || $("#remark_" + x).val() == ' ') {
							alert("Please Enter Remarks");
                         preventDefault();
						}
					}
				} //End of loop
			}

			//chkarr
			$("#action").val(actionVal);
			$("#chkarr").val(checkboxArr.length);
			$("#verifiMissDes").submit();
			return true;
		}
		else {
			alert("Please Select Personal Number");
			return false;
		}

	} else {
		alert("No Personal Number Has Been Selected, Please Select Personal Number");
		return false;
	}

}




function onChangeUserCheckbox(checkObj, userId) {

	//alert("value of checkObj == " + checkObj + "  userId == " + userId);

	if (checkObj.checked) {

		fullStringUsrs[user_counter] = userId;
		user_counter++;
		$("#userIds").val(fullStringUsrs);
		document.getElementById("remark_" + userId).value = "";
		document.getElementById("remark_" + userId).disabled = false;

	}
	else {

		if (fullStringUsrs.length > 0) {

			for (var s = 0; s < fullStringUsrs.length; s++) {

				if (fullStringUsrs[s] == userId) {

					fullStringUsrs[s] = '';
					break;
				}

			}
		}

		$("#userIds").val(fullStringUsrs);
		document.getElementById("remark_" + userId).value = "";
		document.getElementById("remark_" + userId).disabled = true;
	}
}


function selectORDeselectUsrCheckbox(obj) {

	var checkboxArr = document.getElementsByName("usr_checkbox");
	//var checkboxArray = $("input[name=recordchkbox]");
	//alert("Please Select Personal Number");
	if (obj.checked) {
		if (checkboxArr.length > 0) {
			fullStringUsrs = new Array();
			user_counter = 0;
			var i0 = checkboxArr.length;
			for (var index = 0; index < i0; index++) {

								 

				$("#usr_" + index).prop("checked",true);

				fullStringUsrs[user_counter] = document.getElementById("usr_" + index).value;
				document.getElementById("remark_" + index).value = "";
				$("#remark_" + index).prop("disabled",false);
				user_counter++;
				}
			 
				$("#userIds").val(fullStringUsrs);
			

		}
	} else {
		if (checkboxArr.length > 0) {
			for (var index = 0; index < checkboxArr.length; index++) {
					$("#usr_" + index).prop("checked",false);
				document.getElementById("remark_" +  index).value = "";
					$("#remark_" + index).prop("disabled",true);
			}

			fullStringUsrs = new Array();
			user_counter = 0;
			$("#userIds").val('');
		}
	}
}


function doGenerate() {
	var result = checkValidation();
	if (result) {
		$("#userVerificationRprtFilter").submit();
		return true;
	}
	else {
		return false;
	}

}



