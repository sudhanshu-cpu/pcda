function confirmation() {
	
	if ($('#transferOut_Id').valid()){
	
	var personalNo = $("#personalNo").val().trim();
	var sosDate = $("#sosDate").val().trim();

	var transferTo = $("#unit").val();
	var reason = $("#reason").val().trim();
	if (personalNo == "") {
		alert("Please Enter Personal Number");
		return false;
	}
	if (transferTo == "") {
		alert("Please Select The Unit For Transfer");
		return false;
	}
	if (sosDate == "" || sosDate == "dd/mm/yyyy") {
		alert("Please Enter SOS Date");
		return false;
	}

	if (reason == "") {
		alert("You Cannot Proceed Without Entering Reason For Transfer in Transfer Authority field");
		return false;
	}

	if (confirm("Do you really want to transfer out this personal no. to the selected unit?")){
		
		alert("Transfer out request of the profile has been sent to CO of your Unit for Approval");
			$("#transferOut_Id").submit();
		
	}
		
	else return false;

	
	}
}

function ismaxlength(obj, mlength) {
	//var mlength=obj.getAttribute("maxlength")? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.value.length > mlength)
		obj.value = obj.value.substring(0, mlength);
}

function setgroupIdTOHidden(value) {
	$("#groupId").val( value);
	$("#datediv").style.display = 'block';
}

function getBrowserInfo() {
	var bName = '';
	var useragent = navigator.userAgent;
	bName = (useragent.indexOf('Opera') > -1) ? 'Opera' : navigator.appName;
	return bName;
}

function loadInit(e) {
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
		getUnitByPersonalNo();
			
	   }else{
			alert("Please Enter Only Aplha Numeric");
			e.preventDefault();
			return false;
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





function testForAlphaNumericKeyForTravelRequest(e) { // KEYPRESS event
	var myBrow = getBrowserInfo();
	document.getElementById("getTravelerInfoBtn").value = false;
	//var saveBtn=document.getElementById("saveBtnId");
	//saveBtn.disabled=true;

	if (myBrow.indexOf('Netscape') > -1) {
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



function setPayAccountOfficeforCG(serviceName) {
	var showPaoOffice = document.getElementById("showPaoOffice");
	if (showPaoOffice != null && showPaoOffice != undefined) {
		if (showPaoOffice.value == 'YES') {
			if (serviceName.indexOf('COAST GUARD') > -1) {
				var groupString = document.getElementById("groupString").value;
				var groupArr = groupString.split(",");
				var payAccOpt = "";
				for (var n = 0; n <= groupArr.length; n++) {
					if (groupArr[n] != undefined) {
						/*if(groupArr[n].indexOf('PCDA(N)')>-1)*/
						if (
							groupArr[n].indexOf('PCDA(Navy)Mumbai') > -1 ||
							groupArr[n].indexOf('CDA(N)Chennai') > -1 ||
							groupArr[n].indexOf('CDA(N)Kochi') > -1 ||
							groupArr[n].indexOf('CDA(N)Goa') > -1 ||
							groupArr[n].indexOf('CDA(N)Kolkatta') > -1 ||
							groupArr[n].indexOf('CDA(N)Portblair') > -1 ||
							groupArr[n].indexOf('CDA(N)Visakhapattanam') > -1 ||
							groupArr[n].indexOf('CDA(N)Delhi') > -1 ||
							groupArr[n].indexOf('CDA(N)Karwar') > -1
						) {
							var spltArr = groupArr[n].split("::")
							payAccOpt += '<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
						}
					}
				}
				$("#payAccountOffice").html(payAccOpt);

			} else if (serviceName.indexOf('AIRFORCE') > -1 || serviceName.indexOf('AIR FORCE') > -1) {
				var groupString = document.getElementById("groupString").value;
				var groupArr = groupString.split(",");
				var payAccOpt = "";
				for (var n = 0; n <= groupArr.length; n++) {
					if (groupArr[n] != undefined) {
						if (groupArr[n].indexOf('AFCAO, SUBRATO PARK') > -1) {
							var spltArr = groupArr[n].split("::")
							payAccOpt += '<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
						}
					}
				}
				$("#payAccountOffice").html(payAccOpt);

			} else if (serviceName.indexOf('NAVY') > -1) {
				var groupString = document.getElementById("groupString").value;
				var groupArr = groupString.split(",");
				var payAccOpt = "";
				for (var n = 0; n <= groupArr.length; n++) {
					if (groupArr[n] != undefined) {
						if (groupArr[n].indexOf('NPAO, MUMBAI') > -1) {
							var spltArr = groupArr[n].split("::")
							payAccOpt += '<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
						}
					}
				}
				$("#payAccountOffice").html(payAccOpt);

			} else {
				var groupString = document.getElementById("groupString").value;
				var groupArr = groupString.split(",");

				var payAccOpt = "";
				payAccOpt += '<option value="-1">Select<\/option>';
				for (var n = 0; n <= groupArr.length; n++) {

					if (groupArr[n] == undefined || groupArr[n] == 'undefined') { } else {
						if (groupArr[n].indexOf('::') > -1) {
							var spltArr = groupArr[n].split("::")
							payAccOpt += '<option value="' + spltArr[0] + '">' + spltArr[1] + '<\/option>'
						}
					}
				}
				$("#payAccountOffice").html(payAccOpt);
			}
		}
	}
}



function getUnitByPersonalNo() {
	var userAlias = $("#personalNo").val().trim();
	


	var response = "";
	var serviceName = "";
	var UnitServiceName = "";
	if(userAlias!='') {
		$.ajax(
			{
				url: $("#context_path").val() + "mb/getUnitByPersonalNo",
				type: "POST",
				data: "userAlias=" + userAlias,
				datatype: "application/json",
				success: function(data) {
					var tdvalue = '';
					var msg1 = data.message;
					serviceName = data.serviceName;
					UnitServiceName = data.serviceName;
                   $("#currentUnit").val(data.currentUnit);
					$("#userId").val(data.userId);
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
					} else if (msg1 == "Not Valid") {
						$("#personalNoInfo").html("<option value=''>Please Enter Valid Personal Number<\/option>");
					} else if (msg1 == "userNotActive") {
						$("#personalNoInfo").html("<option value=''>Personal No marked as non effective.<\/option>");
					} else if (msg1 == "usernotapproved") {
						$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not Approved<\/option>");
					} else if (msg1 == 'dataexist') {
						$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Transfer Process<\/option>");
					} else if (msg1 == 'editMode') {
						$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Edit Process<\/option>");
							} else if (msg1 == 'transferOut') {
						$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Already Under Transfer Out Process<\/option>");
					} else if (msg1 == "NoData") {
						$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not From Your Unit<\/option>");
					} 
					 else if (msg1 == 'duplicate') {
					$("#personalNoInfo").html("<option value=''>Traveler Transfer Out' for which their approval request is pending and in process<\/option>");
				}
					
					else if (msg1 == "activeCO") {
						$("#personalNoInfo").html("<option value=''>Unable To Transfer CO User,  User Is Currently Active, Please Delegate<\/option>");
					} else if (msg1 == "VirtualUser") {
						$("#personalNoInfo").html("<option value=''>Transfer Out Is Not Allowed For Virtual User<\/option>");
					} else if  (msg1 == 'NoRecordFound'){
						$("#personalNoInfo").html("<option value=''>No Detail Found For Given Personal Number<\/option>");
						}else {
						tdvalue += '<table width="100%" border="0" cellpadding="0" cellspacing="0" class="filtersearch"><tbody class="all1"><tr><td><table width="100%" border="0" cellpadding="4" cellspacing="1"  class="filtersearch">'
						tdvalue += '<tr align="left" class="showalert lablevalue">';
						tdvalue += '<td width="20%" height="25">Name</td>';
						tdvalue += '<td width="15%">Unit Service</td>';
						tdvalue += '<td width="15%">Traveler Service</td>';
						tdvalue += '<td width="15%">Unit Name</td>';
						tdvalue += '<td width="30%">Transfer To</td>';
						tdvalue += '</tr>';
						tdvalue += '<tr align="left">';

						var unitName = data.unitName
						//$("#currentUnit").val(unitName);
						var name = data.name

						tdvalue += '<td height="25">' + name + '</td>';
						tdvalue += '<td height="25">' + UnitServiceName + '</td>';
						tdvalue += '<td><input type="hidden" value="' + personalNo + '" name="personalNo">' + serviceName + '</td>';
						tdvalue += '<td>' + unitName + '</td>';
						tdvalue += '<td><select id="unit" name="trGroup" class="comboauto" onchange="setgroupIdTOHidden(this.value)">';
						tdvalue += '<option value="">Select</option>';
						var unitlitt = data.unitList;
						var targetUnit = unitlitt.find(function(unit) {
							return unit.name === "DUMPUNIT"; // Change this to the desired unit name
						});
						if (targetUnit) {
							var groupId = targetUnit.groupId;
							var groupName = targetUnit.name;
							tdvalue += '<option value="' + groupName + '">' + groupName + '</option>';
						}
						if(targetUnit==undefined){
							
							unitlitt.forEach(function(unit){
							var groupId = unit.groupId;
							var groupName = unit.name;
							tdvalue += '<option value="' + groupName + '">' + groupName + '</option>';
							});
							
						}
						tdvalue += '</select></td>';

						tdvalue += '</tr>';

						tdvalue += '</table> </td></tr></tbody></table>';

						$("#personalNoInfo").html(tdvalue);
						$("#transferBtnDiv").show("fast");
						setPayAccountOfficeforCG(serviceName);
					}
					response += data;
				}
			});
		beforeSend: $('#personalNoInfo').html("Getting response from server....");
	}
}
function setgroupIdTOHidden(value) {
	$("#unitNameToTransfer").val(value);
	//$("#datediv").style.display = 'block';
}

$(document).ready(function() {

//	$("#personalNo").keypress(function(e) {
//		var myBrow = getBrowserInfo();
//		if (myBrow.indexOf('Netscape') > -1) {
//			var k;
//			if ((e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122) || (e.which >= 48 && e.which <= 57) || (e.which == 8) || (e.which == 0)) {
//				return true;
//			}
//			else {
//				alert("Please Enter Only Aplha Numeric");
//				e.preventDefault();
//			}
//		}
//		else {
//			return true;
//		}
//	});
	
	
	$('#personalNo').on("cut copy paste",function(e) {
      e.preventDefault();
   });

	$("#sosDate").datetimepicker({
		minDate: 0,
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd: 2100,
		onShow: function () {
			  $("#sosDate").val("");
		},

          onSelectDate:function(){
				 return true;
				}
	});
	
	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			return this.optional(element) || regexp.test(value);
		},
		"Please enter valid details."
	);

	$("#transferOut_Id").validate({

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
				regex: "Please Enter Valid Reason address ",
				required: "Please enter Reason"
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



});


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
	if (myDate > newDate) {
		alert("The Date must be Today's date or a later date in SOS Date field.");
	$("#sosDate").val("");
		$('#sosDate').blur();	
}
}


function getStationAir(obj, idx) {

	var officeNRS = obj;
	var airPortList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getNADutyStation",
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
function filsRailStationNRS(obj, idx) {

	var railNRS = obj;
	var stationList = "";

	if (railNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getStationNRS",
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



function getSPRNA(obj, idx) {

	var officeNRS = obj;
	var airPortList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getSPRAirport",
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

function sprStation(obj, idx) {

	var railSPR = obj;
	var stationList = "";

	if (railSPR.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getSPRStation",
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
			url: $("#context_path").val() + "mb/getSPRNRS",
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




