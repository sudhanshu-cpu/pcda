function transferOutReemployment() {
	var alternateService = $("#alternateService").val();
	var unitNo = $("#unitNo").val();
	var categoryId = $("#category_Id").val();
	var rankId = $("#rankId").val();
	var dutyStnNrs = $("#dutyStnNrs").val();
	var payAcOff = $("#payAcOff").val();
	var airAcOff = $("#airAcOff").val();
	var level = $("#levelId").val();

	if (alternateService == "") {
		alert("Please Select Service For Transfer");
		$('#alternateService').focus();
		return false;
	}

	if (unitNo == "-1") {
		alert("Please Select Unit For Transfer");
		return false;
	}
	if (categoryId == "-1" || categoryId == "") {
		alert("Please Select Category For Transfer");
		$('#category_Id').focus();
		return false;
	}
	if (level == "-1" || level == "") {
		alert("Please Select Level For Transfer");
		return false;
	}
	if (rankId == "-1" || rankId == "") {
		alert("Grade Pay is not mapped with selected level");
		return false;
	}
	if (payAcOff == "-1") {
		alert("Please select Rail Accounting office");
		return false;
	}
	if (airAcOff == "-1") {
		alert("Please select Air Accounting office");
		return false;
	}

	if (dutyStnNrs == "") {
		alert("Please Select NRS Duty For Transfer");
		return false;
	}


	$("#saveBulkTransferInReEmpMovement").submit();


}

function ismaxlength(obj, mlength) {
	if (obj.value.length > mlength)
		obj.value = obj.value.substring(0, mlength);
}

$(document).ready(function() {

	$('#personalNo').on("cut copy paste", function(e) {
		e.preventDefault();
	});

	$("#unitRelocationDate").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput: false,
		maxDate: 0,
		yearEnd: 2100,
		onShow: function() {
			$("#unitRelocationDate").val("");
		}
	});

	$("#alternateService").change(function() {

		getCategories($(this));
	});

	$('#category_Id').on('change', function() {
		getLevel($(this));
	});

	$("#levelId").on('change', function() {
		setRankLevel($(this));
	});



});

function getCategories(obj) {
	$('#category_Id').find('option').not(':first').remove();
	$('#levelId').find('option').not(':first').remove();
	let serviceId = $(obj).val();

	$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getTransferINReCateBasedOnService",
		data: "serviceId=" + serviceId,
		datatype: "application/json",
		success: function(data) {

			$.each(data, function(key, value) {
				if (value == 'PBOR') {
				$('#category_Id').append(new Option(value, key));
				}
			});

		}
	});

}

function getLevel(obj) {
	let serviceId = $("#alternateService").val();
	let categoryId = $(obj).val();
	$('#levelId').find('option').not(':first').remove();
	$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getTransferINReLevel",
		data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
		datatype: "application/json",

		success: function(data) {
			$.each(data, function(key, value) {
				$('#levelId').append(new Option(value[0], key + ":" + value[1]));
			});

		}
	});
	if (categoryId != "") {
		$.ajax({
			url: $("#context_path").val() + "mb/getPAOTransfer",
			type: "POST",
			data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
			//	      	dataType: "text", 
			success: function(data) {
				var obj = JSON.parse(JSON.stringify(data));
				var rail_options;
				var air_options;
				var railCount;
				var RailVisitorGroup;
				var airCount;
				var AirVisitorGroup;

				$.each(obj, function(i, v) {
					if (i == 0) {
						railCount = $(v).length;
						RailVisitorGroup = $(v);
					}
					if (i == 1) {
						airCount = $(v).length;
						AirVisitorGroup = $(v);
					}
				});

				if (railCount != 1) {
					rail_options += '<option value="-1">select<\/option>';
				}

				$(RailVisitorGroup).each(function(index) {
					var id = RailVisitorGroup[index].acuntoficeId;
					var name = RailVisitorGroup[index].name;

					rail_options += '<option value="' + id + '">' + name + '<\/option>';
				});

				if (airCount != 1) {
					air_options += '<option value="-1">select<\/option>';
				}

				$(AirVisitorGroup).each(function(index) {
					var id = AirVisitorGroup[index].acuntoficeId;
					var name = AirVisitorGroup[index].name;

					air_options += '<option value="' + id + '">' + name + '<\/option>';
				});

				$("#payAcOff").html(rail_options);
				$("#airAcOff").html(air_options);
			}//END SUCCESS
		});
	}

}


function setRankLevel(obj) {
	let strVal = $(obj).find(":selected").val();
	let rankId = strVal.split(":")[1];
	$("#rankId").val(rankId);
	$("#level").val(strVal.split(":")[0]);
	$('#rank').empty();
	$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getTransferGradePayRank",
		data: "rankId=" + rankId,
		datatype: "application/json",
		success: function(data) {
			$("#rank").append(data.rankName);
		}
	});
}



function submitBulkTransferInReEmpForm() {

	var flag = false;
	var allAddedUserId;
	allAddedUserId = document.getElementsByName("userId");

	for (i = 0; i < allAddedUserId.length; i++) {

		var sosDate = $('#sosDate_' + allAddedUserId[i].value).val();
		var retirementDate = $('#dateOfRetirement_' + allAddedUserId[i].value);

		if (sosDate == '' && retirementDate == '') {
			alert("Taken on Strength Date and Date Of Retirement can not be Empty");
			return false;
		}
		if (sosDate == '' && retirementDate != '') {
			alert("Taken on Strength Date can not be Empty");
			return false;
		}
		if (sosDate != '' && retirementDate == '') {
			alert("Date Of Retirement can not be Empty");
			return false;
		}
		flag = true;
	}

	if (flag) {
		$('#bulkTransferInReEmpForm').submit();
	} else {
		alert("Please Add Atleast One Personnal Number");
	}
}


function testForAlphaNumericKey(e) { // KEYPRESS event

	var pno = $("#personalNo").val().trim();
	var regex = /^[A-Za-z0-9]+$/;
	//	alert("pno" + pno);
	if (pno == '') {
		alert("Please Enter Personal No.");
		e.preventDefault();
		return false;
	}
	var result = regex.test(pno);
	//alert(result);
	if (result) {
		getDetailOfPersonalForTGransferIn();
		return true;
	} else {
		alert("Please Enter Only Aplha Numeric");
		e.preventDefault();
	}
}


function getDetailOfPersonalForTGransferIn(functionality) {
	var userAlias = $("#personalNo").val().trim();
	var serviceName = "";
	var categoryName = "";

	if (userAlias != '') {
		$.ajax({
			url: $("#context_path").val() + "mb/getPrsonalNoDetailsTransferInRe",
			type: "get",
			data: "userAlias=" + userAlias + "&functionality=" + functionality,
			datatype: "application/json",
			success: function(data) {
				var tdvalue = '';
				var msg1 = data.message;
				serviceName = data.serviceName;
				categoryName = data.categoryName;
				$("#userId").val(data.userId);
				$("#currentUnit").val(data.currentUnit);

				if (msg1.indexOf('Only numeric') > -1) {
					alert("Personal No Should Have Only Numeric Character");
					$("#personalNo").val('');
					return false;
				} else if (msg1.indexOf('Only characters') > -1) {
					alert("Personal No Should Have Only Characters");
					$("#personalNo").val('');
					return false;
				} else if (msg1.indexOf('Special') > -1) {
					alert("Personal No Should Not Have Special Character");
					$("#personalNo").val('');
					return false;
				} else if (msg1.indexOf('Few Special') > -1) {
					alert("Personal No Should Contain Only Define Special Character(s)");
					$("#personalNo").val('');
					return false;
				} else if (msg1.indexOf('Reserve Words') > -1) {
					alert("Personal No Should Not Have Reserve Words");
					$("#personalNo").val('');
					return false;
				} else if (msg1.indexOf('Data Length') > -1) {
					alert("" + msg1 + "");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "Not Valid") {
					alert("Please Enter Valid Personal Number");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "usernotapproved") {
					alert("Personal No You Have Entered Is Not Approved");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'editMode') {
					alert("Personal No You Have Entered Is Under Edit Process");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'transferInReemployment') {
					alert("Personal No You Have Entered Is Already Under Transfer In And Reemployment Process");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'dataexist') {
					alert("Personal No You Have Entered Is Under Transfer Process");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "NoData") {
					alert("Personal No You Have Entered Is Not From Your Unit");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "sameUnit") {
					alert("Transfer In And Reemployment Is Not Allowed In Same Unit.");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "activeCO") {
					alert("Unable To Transfer CO User,  User Is Currently Active, Please Delegate");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "VirtualUser") {
					alert("Transfer Out Is Not Allowed For Virtual User");
					$("#personalNo").val('');
					return false;
				}
				else if (categoryName != "PBOR") {
					alert("This functionality is available for PBOR Only");
					$("#personalNo").val('');
					return false;

				} else if (serviceName == "") {
					alert("No Detail Found For Given Personal Number");
					$("#personalNo").val('');
					return false;
	} else {

					tdvalue += '<tr id="user_' + data.userId + '"><input type="hidden" value="' + data.userId + '" name="userId">';
					tdvalue += '<input type="hidden" value="' + data.dob + '" id="dob_' + data.userId + '" th:name="dob_' + data.userId + '">';
					tdvalue += '<td  style="width:20%">' + data.personalNo + '</td>';
					tdvalue += '<td  style="width:20%" >' + data.name + '</td>';
					tdvalue += '<td  style="width:20%" ><input type="hidden" value="' + data.personalNo + '" name="bulkPersonalNo">';
					tdvalue += '<div id="calendar1_container"></div> <input type="text" class="txtfldM" name="sosDate_' + data.userId + '" size="15" id="sosDate_' + data.userId + '" placeholder="dd/mm/yyyy" autocomplete="off">';
					tdvalue += '</td>';
					tdvalue += '<td  style="width:20%" ><input type="text" readonly="readonly" class="txtfldM" name="dateOfRetirement_' + data.userId + '"  id="dateOfRetirement_' + data.userId + '" placeholder="dd/mm/yyyy" maxlength="15" size="15" autocomplete="off"></td>';
					tdvalue += '<td  align="right"><input type="button" class="butn" value="Delete" onclick="delPersonalInfoRow(\'' + data.userId + '\');"/></td>';
					tdvalue += '</tr>';


					if (alreadyExist(userAlias)) {
						$("#personalNoInfoBulk").append(tdvalue);
						$("#personalNo").val('');
						$("#sosDate_" + data.userId).datetimepicker({
							lang: 'en',
							timepicker: false,
							format: 'd-m-Y',
							formatDate: 'd-m-Y',
							scrollMonth: false,
							scrollInput: false,
							yearEnd: 2100,
							onShow: function(ct, $i) {


								$("#sosDate_" + data.userId).val("");
								$("#dateOfRetirement_" + data.userId).val("");
							},
							onSelectDate: function(ct, $i) {
								getRetirementDateforTansfer(data.userId);

							}
						});
					}

	}



			}
		});

	}

}

function delPersonalInfoRow(value) {

	$("#user_" + value).remove();


}

function alreadyExist(userAlias) {
	
	var isAlreadyAdded = false;
	var allAddedPersonalNumber;
	allAddedPersonalNumber = document.getElementsByName("bulkPersonalNo");
	for (k = 0; k < allAddedPersonalNumber.length; k++) {


		var personalNo = allAddedPersonalNumber[k].value.toUpperCase();
		if (userAlias == personalNo) {
			isAlreadyAdded = true;

			break;
		}

	}

	if (isAlreadyAdded) {
		alert(personalNo + " Personal Number Has Been Already Added");
		$("#personalNo").val('');
		return false;
	}

	return true;
}



