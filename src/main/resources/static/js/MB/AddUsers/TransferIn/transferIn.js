function confirmation() {
	
	if ($('#transferIn_Id').valid()){
	var personalNo = $("#personalNo").val().trim();
	var sosDate = $("#sosDate").val().trim();
	var reason = $("#reason").val().trim();
	if (personalNo == "") {
		alert("Please Enter Personal Number");
		return false;
	}

	if (sosDate == "" || sosDate == "dd/mm/yyyy") {
		alert("Please Enter SOS Date");
		return false;
	}

	if (reason == "") {
		alert("You Cannot Proceed Without Entering Reason For Transfer in Transfer Authority field.");
		return false;
	}


	if (confirm("Do you really want to transfer in this personal no. from the selected unit?")){
		$("#transferIn_Id").submit();
	}	
		
	else return false;

	
}
}

function ismaxlength(obj, mlength) {
	if (obj.value.length > mlength)
		obj.value = obj.value.substring(0, mlength);
}

function setgroupIdTOHidden(value) {
	$("#groupId").value = value;
	$("#datediv").style.display = 'block';
}

function getBrowserInfo() {
	var bName = '';
	var useragent = navigator.userAgent;
	bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}

function loadInit() {
	$("#personalNo").onKeyPress = run();
	function run() {
		$("#personalNo").bind('paste', function(e) {
			e.preventDefault();
		});
	}
}

function testForCharacterKey(e) { // KEYPRESS event
	var myBrow = getBrowserInfo();
	if (myBrow.indexOf('Netscape') > -1) {
		var k;
		if ((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which == 8) || (e.which == 0)) {
			return true;
		}
		else {
			alert("Please Enter Characters Only.");
			e.preventDefault();
		}
	}
	else {
		return true;
	}
}

function testForNumericKey(e) { // KEYPRESS event
	var myBrow = getBrowserInfo();
	if (myBrow.indexOf('Netscape') > -1) {
		var k;
		if ((e.which >= 48 && e.which <= 57) || (e.which == 8) || (e.which == 0)) {
			return true;
		}
		else {
			alert("Please Enter Only Numerals");
			e.preventDefault();
		}
	}
	else {
		return true;
	}
}


function chkForGovtAccom(val) {
	var sprPlace = $("#sprStation_0");
	var sprNrs = $("[name='sprNrs']");
	var sprNa = $("[name='sprNa']");
	if (val == 'YES') {
		$(sprPlace).prop('disabled', true);
		$(sprNrs).prop('disabled', true);
		$(sprNa).prop('disabled', true);
		$("#sprStation_0, #sprStationNRS_0, #airPortSPRNAText_0").css("background-color", "grey");
		$("#sprStation_0").val('');
		$("#sprStationNRS_0").val('');
		$("#airPortSPRNAText_0").val('');
	} else {
		$(sprPlace).prop('disabled', false);
		$(sprNrs).prop('disabled', false);
		$(sprNa).prop('disabled', false);
		$("#sprStation_0, #sprStationNRS_0, #airPortSPRNAText_0").css("background-color", "white");
	}
}

function testForAlphaNumericKey(e) { // KEYPRESS event
	
     var pno = $("#personalNo").val().trim();
	 var regex = /^[A-Za-z0-9]+$/;
//	alert("pno" + pno);
if(pno==''){
	alert("Please Enter Personal No.");
	e.preventDefault();
	return false;
}
     var result=regex.test(pno);
//alert(result);
	if(result){
		getDetailOfPersonal();
			return true;
	   }else{
			alert("Please Enter Only Aplha Numeric");
			e.preventDefault();
		}
}

function testForAlphaNumericKeyForTravelRequest(e) { // KEYPRESS event
	var myBrow = getBrowserInfo();
	document.getElementById("getTravelerInfoBtn").value = false;
	//var saveBtn=document.getElementById("saveBtnId");
	//saveBtn.disabled=true;

	if (myBrow.indexOf('Netscape') > -1) {
		var k;
		if ((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which >= 48 && e.which <= 57) || (e.which == 8) || (e.which == 0)) {
			return true;
		}
		else {
			alert("Please Enter Only Aplha Numeric");
			e.preventDefault();
		}
	}
	else {
		return true;
	}
}


function dateValidate() {
	const today = new Date();
	const yyyy = today.getFullYear();
	let mm = today.getMonth() + 1;
	// Months start at 0!
	let dd = today.getDate();
	if (dd < 10) dd = '0' + dd;
	if (mm < 10) mm = '0' + mm;

	const myDate = dd + '-' + mm + '-' + yyyy;
	var newDate = $("#sosDate").val();
	if (newDate > myDate) {
		alert("The Date must be before or today's date.");
		$("#sosDate").val("");
		$('#sosDate').blur();
	}
}

$(document).ready(function() {


$('#personalNo').on("cut copy paste",function(e) {
      e.preventDefault();
   });

	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			return this.optional(element) || regexp.test(value);
		},
		"Please enter valid details."
	);

	$("#transferIn_Id").validate({

		rules: {
			
			sosDateString: {
				required: true
			},
			nrsDutyStn: {
				required: true,
			},
			dutyStnNa: {
				required: true,
			},
			addrisUnitInPeaceLocess: {
				required: true,
			},
			sprSfa: {
				required: true,

			},
			sprNrs: {
				required: true,
			},

			sprNa: {
				required: true
			},

			reason: {
				required: true,
				regex: /^[A-Za-z0-9 .&,-/]+$/
			},

		},

		messages: {
			
			sosDateString: {
				required: "Please Select date "
			},
			nrsDutyStn: {
				required: "Please enter NRS Station",
			},
			dutyStnNa: {
				required: "Please enter Airport",
			},
			addrisUnitInPeaceLocess: {
				required: "Please select One"

			},
			sprSfa: {
				required: "Please enter SPR SFA",

			},
			sprNrs: {
				required: "Please enter SPR NRS",

			},
			sprNa: {
				required: "Please enter SPR NA",
			},

			reason: {
				regex: "In Authority   .&,-/ only these characters allowed",
				required: "Please enter Transfer Authority "
			}
		},
		errorElement: "em",
		errorPlacement: function(error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element.parent().last());
			}
		},
		highlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-success").removeClass("has-error");
		}
	});



	$("#sosDate").datetimepicker({
		        maxDate: 0,
          lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                yearEnd : 2100,
		onShow: function () {
			  $("#sosDate").val("");
		},
                onSelectDate:function(){
				 return true;
				}
	});

});

	


function getDetailOfPersonal() {
	var userAlias = $("#personalNo").val().trim();
	

	var response = "";
	var serviceName = "";
	var UnitServiceName = "";
	var loginVisitorUnitPaoId = "";

	if (userAlias!='') {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getDetailBypersonalNo",
			data: "userAlias=" + userAlias,
			datatype: "application/json",
			success: function(data) {
				
				var msg1 = data.message;
				
				var tdvalue = '';
				serviceName = data.serviceName
				UnitServiceName = data.unitServiceName;
				$("#currentUnit").val(UnitServiceName);
				$("#userId").val(data.userId);
				$("#currentUnit").val(data.currentUnit);
				
				//console.log("rrrrr"+val);
				if (msg1.indexOf('Only numeric') > -1) {
					$("#personalNoInfo").html("<option value=''>Personal No Should Have Only Numeric Character<\/option>");
				} else if (msg1.indexOf('Only characters') > -1) {
					$("#personalNoInfo").html("<option value=''>Personal No Should Have Only Characters<\/option>");
				} else if (msg1.indexOf('Special') > -1) {
					$("#personalNoInfo").html("<option value=''>Personal No Should Not Have Special Character<\/option>");
				} else if (msg1.indexOf('Few Special') > -1) {
					$("#personalNoInfo").html("<option value=''>Personal No Should Contain Only Define Special Character(s)<\/option>");
				} else if (msg1.indexOf('Reserve Words') > -1) {
					$("#personalNoInfo").html("<option value=''>Personal No Should Not Have Reserve Words<\/option>");
				} else if (msg1.indexOf('Data Length') > -1) {
					$("#personalNoInfo").html("<option value=''>" + msg1 + "<\/option>");
				}else if (msg1 == "minMaxLength") {
					$("#personalNoInfo").html("Data Length for field Personal No should be minimum 4 and maximum 11");
				} else if (msg1 == "Not Valid") {
					$("#personalNoInfo").html("<option value=''>Please Enter Valid Personal Number<\/option>");
				} else if (msg1 == "userNotActive") {
					$("#personalNoInfo").html("<option value=''>Personal No marked as non effective.<\/option>");
				} else if (msg1 == "usernotapproved") {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not Approved<\/option>");
				} else if (msg1 == "notPartOfCivilian") {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not Part Of Civilian Service<\/option>");
				} else if (msg1 == "notPartOfArmedForces") {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not Part Of Armed Forces<\/option>");
				} else if (msg1 == 'dataexist') {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Transfer Process<\/option>");
				} else if (msg1 == 'editMode') {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Edit Process<\/option>");
					} else if (msg1 == 'transferIn') {
					$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Already Under Transfer In Process<\/option>");
				} else if (msg1 == 'activeCO') {
					$("#personalNoInfo").html("<option value=''>Unable To Transfer CO User,  User Is Currently Active, Please Delegate<\/option>");
				} 
				 else if (msg1 == 'duplicate') {
					$("#personalNoInfo").html("<option value=''>Traveler Transfer Out' for which their approval request is pending and in process<\/option>");
				}
				
				else if (msg1 == 'VirtualUser') {
					$("#personalNoInfo").html("<option value=''>Transfer In Is Not Allowed For Virtual User<\/option>");
				} else if (msg1 == 'personalIsPartOfCostGaurd') {
					$("#personalNoInfo").html("<option value=''>Personal Belongs To Coast Guard Service.<\/option>");
				} else if (msg1 == 'notPartOfSameUnit') {
					$("#personalNoInfo").html("<option value=''>Transfer In Not Allowed Inside Unit .<\/option>");
				} else if (msg1 == 'tinNotAllowedFromAnyOtherToDSC') {
					$("#personalNoInfo").html("<option value=''>Transfer In From Any Other Service To DSC Service Is Not Allowed.<\/option>");
				} else if (msg1 == 'noRecordFound') {
					$("#personalNoInfo").html("<option value=''>No Detail Found For Given Personal Number<\/option>");
				}
				else if(serviceName=''){
				}
				 else {
					var msgVal = $(data);
					tdvalue += '<table width="100%" border="0" cellpadding="0" cellspacing="0" class="filtersearch"><tbody class="all1"><tr><td><Table border="0" width="100%" cellpadding="4" cellspacing="1" class="filtersearch">'
					tdvalue += '<tr align="left" class="showalert lablevalue">';
					tdvalue += '<td width="25%" height="25">Name</td>';
					tdvalue += '<td width="25%">Unit Service</td>';
					tdvalue += '<td width="25%">Traveler Service</td>';
					tdvalue += '<td width="25%">Unit Name</td>';
					tdvalue += '</tr>';

					tdvalue += '<tr align="left">';
					var unitName = data.unitName
					var name = data.name
					var travelerSe=data.serviceName
					tdvalue += '<td width="25%" height="25">' + name + '</td>';
					tdvalue += '<td width="25%" height="25">' + UnitServiceName + '</td>';
					tdvalue += '<td width="25%"><input type="hidden" value="' + personalNo + '" name="personalNo">' + travelerSe + '</td>';
					tdvalue += '<td width="25%">' + unitName + '</td>';

					tdvalue += '</tr>';

					tdvalue += '</Table> </td></tr></tbody></table>';
					$("#personalNoInfo").html(tdvalue);
					document.getElementById("transferBtnDiv").style.display = "block";
				}

				response += data;

			}
		});
		beforeSend: $('#personalNoInfo').html("Getting response from server....");
	}

}

	function filsRailStationNRS(obj, idx) {

		var railNRS = obj;
		var stationList = "";

		if (railNRS.length > 1) {
			$.ajax({
				type: "get",
				url: $("#context_path").val() + "mb/getStationNRSIn",
				data: "station=" + railNRS,
				datatype: "application/json",
				success: function(data) {
					$.each(data, function(index, name) {
						if(name=="Station Name Not Exist"){
							stationList += '<li>' + name + '</li>';
						}else{
						stationList += '<li onClick="fillRecordRailStationNRS(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
						}
					});
					$('#autoSuggestionsListRailStationNRS_' + idx).html(stationList);
					$('#suggestionsRailStationNRS_' + idx).show();
				}
			});

		}
	}

	function fillRecordRailStationNRS(thisValue, idx) {
		$('#dutyStations_' + idx).val(thisValue);
		setTimeout(() => {
			$('#suggestionsRailStationNRS_' + idx).hide();
		}, 200);

	}

	function getStationAir(obj, idx) {

		var officeNRS = obj;
		var airPortList = "";

		if (officeNRS.length > 1) {
			$.ajax({
				type: "get",
				url: $("#context_path").val() + "mb/getNADutyStationIn",
				data: "airPortName=" + officeNRS,
				datatype: "application/json",
				success: function(data) {
					$.each(data, function(index, name) {
						if(name=="Airport Name Not Exist"){
							airPortList += '<li>' + name + '</li>';
						}else{
						airPortList += '<li onClick="fillRecordOffice(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
						}

					});
					$('#autoSuggestionsListOfficeNRS1_' + idx).html(airPortList);

					$('#suggestionsOfficeNRS1_' + idx).show();

				}
			});
		}
	}
	function fillRecordOffice(thisValue, idx) {
		$('#airportStationText_' + idx).val(thisValue);
		setTimeout(() => {
			$('#suggestionsOfficeNRS1_' + idx).hide();
		}, 200);

	}


	function sprStation(obj, idx) {

		var railSPR = obj;
		var stationList = "";

		if (railSPR.length > 1) {
			$.ajax({
				type: "get",
				url: $("#context_path").val() + "mb/getSPRStationIn",
				data: "station=" + railSPR,
				datatype: "application/json",
				success: function(data) {
					$.each(data, function(index, name) {
						if(name=="Station Name Not Exist"){
							stationList += '<li>' + name + '</li>';
						}else{
						stationList += '<li onClick="fillSPRStation(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
						}
					});
					$('#autoSuggestionsListSPR_' + idx).html(stationList);
					$('#suggestionsSPR_' + idx).show();
				}
			});
		}
	}

	function fillSPRStation(thisValue, idx) {
		$('#sprStation_' + idx).val(thisValue);
		setTimeout(() => {
			$('#suggestionsSPR_' + idx).hide();
		}, 200);
	}
	function sprNRS(obj, idx) {

		var railSPRNRS = obj;
		var stationList = "";

		if (railSPRNRS.length > 1) {
			$.ajax({
				type: "get",
				url: $("#context_path").val() + "mb/getSPRNRSIn",
				data: "station=" + railSPRNRS,
				datatype: "application/json",
				success: function(data) {
					$.each(data, function(index, name) {
						if(name=="Station Name Not Exist"){
							stationList += '<li>' + name + '</li>';
						}else{
						stationList += '<li onClick="fillSPRNRS(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
						}
					});
					$('#autoSuggestionsListSPRNRS_' + idx).html(stationList);
					$('#suggestionsSPRNRS_' + idx).show();
				}
			});
		}
	}
	function fillSPRNRS(thisValue, idx) {
		$('#sprStationNRS_' + idx).val(thisValue);
		setTimeout(() => {
			$('#suggestionsSPRNRS_' + idx).hide();
		}, 200);
	}
	function getSPRNA(obj, idx) {

		var officeNRS = obj;
		var airPortList = "";

		if (officeNRS.length > 1) {
			$.ajax({
				type: "get",
				url: $("#context_path").val() + "mb/getSPRAirportIn",
				data: "airPortName=" + officeNRS,
				datatype: "application/json",
				success: function(data) {
					$.each(data, function(index, name) {
						if(name=="Airport Name Not Exist"){
							airPortList += '<li>' + name + '</li>';
						}else{
						airPortList += '<li onClick="fillRecordSPRNA(\'' + name + '\',\'' + idx + '\')">' + name + '</li>';
						}
					});
					$('#autoSuggestionsListSPRNA_' + idx).html(airPortList);

					$('#suggestionsSPRNA_' + idx).show();
				}
			});
		}

	}
	function fillRecordSPRNA(thisValue, idx) {
		$('#airPortSPRNAText_0').val(thisValue);

		setTimeout(() => {
			$('#suggestionsSPRNA_' + idx).hide();
		}, 200);
	}





