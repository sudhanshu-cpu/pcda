
function transferOutReemployment() {
	var alternateService = $("#alternateService").val();
	var unitNo = $("#unitNo").val();
	var categoryId = $("#category_Id").val();
	var rankId = $("#rankId").val();
	var dutyStnNrs = $("#dutyStations_0").val();
	var dutyStnNA=$("#airportStationText_0").val();

	var reason = $("#reason").val();
	var payAcOff = $("#payAcOff").val();
	var airAcOff = $("#airAcOff").val();
	var sosDate = $("#sosDate").val();

	var loginVisitorUnitId = $("#visitorUnitId").val();

	var level = $("#levelId").val();

	if (personalNo == '') {
		alert("Please Enter Personal Number");
		return false;
	}
	if (alternateService == "") {
		alert("Please Select Service For Transfer");
		return false;
	}

	if (unitNo == "-1") {
		alert("Please Select Unit For Transfer");
		return false;
	}


	if (categoryId == "-1" || categoryId == "") {
		alert("Please Select Category For Transfer");
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
	if (sosDate == "" || sosDate == "dd/mm/yyyy") {
		alert("Please Enter SOS Date");
		return false;
	}


	if (dutyStnNrs == "") {
		alert("Please Select NRS Duty For Transfer");
		return false;
	}
	if (dutyStnNA == "") {
		alert("Please Select Nearest Airport For Transfer");
		return false;
	}

	if (reason == "") {
		alert("You Cannot Proceed Without Entering Reason For Transfer");
		return false;
	}


	
	if(confirm("Do You Really want to Transfer This Personal Number To the Selected Unit?")){
	
$("#transferInReemployement").submit();
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



function getCategoryIdOnTraveller() {
	var isCallForCategoryId = true;
	var isCallForGradePay = false;
	var isCallForLevelId = false;

	var serviceName = document.getElementById("alternateService");
	var serviceId = serviceName.options[serviceName.options.selectedIndex].value;

	var url = $("#context_path").val() + "mb/getTransferINReCateBasedOnService";

	//getHttpURLResponse(url);

}


function testForAlphaNumericKey(e) { // KEYPRESS event
	
     var pno = $("#personalNo").val();
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
		getUnitByPrsnlNoforTransfer();
			return true;
	   }else{
			alert("Please Enter Only Aplha Numeric");
			e.preventDefault();
		}
}



function getUnitByPrsnlNoforTransfer(functionality) {
	var userAlias = $("#personalNo").val().trim();
	
	var serviceName = "";
	var UnitServiceName = "";
	var categoryName = "";
	$.ajax({
		url: $("#context_path").val() + "mb/getPrsonalNoDetailsTransferInRe",
		type: "get",
		data: "userAlias=" + userAlias + "&functionality=" + functionality,
		datatype: "application/json",
		success: function(data) {
			var tdvalue = '';
			var msg1 = data.message;
			serviceName = data.serviceName;
			UnitServiceName = data.unitServiceName;
			categoryName = data.categoryName;
			visitorUnitName = data.visitorUnitName;
			dob = data.dob;
			$("#dob").val(dob);
			$("#userId").val(data.userId);
			var unitName = data.unitName;
			
			$("#currentUnit").val(data.currentUnit);
//			var unitList = data.unitList;
//			console.log(unitList);
//			//$("#listUnit").val(unitList.name);
//			$.each(unitList, function(i) {
//
//				var obj = JSON.parse(JSON.stringify(this));
//
//				$('#unitNo')
//					.append($('<option>').val(obj.name).text(obj.name));
//			});


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
			} else if (msg1 == "usernotapproved") {
				$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not Approved<\/option>");
			} else if (msg1 == 'editMode') {
				$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Edit Process<\/option>");
				} else if (msg1 == 'transferInReemployment') {
				$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Already Under Transfer In And Reemployment Process<\/option>");
			} else if (msg1 == 'dataexist') {
				$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Under Transfer Process<\/option>");
			} else if (msg1 == "NoData") {
				$("#personalNoInfo").html("<option value=''>Personal No You Have Entered Is Not From Your Unit<\/option>");
			} else if (msg1 == "sameUnit") {
				$("#personalNoInfo").html("<option value=''>Transfer In And Reemployment Is Not Allowed In Same Unit.<\/option>");
			} else if (msg1 == "activeCO") {
				$("#personalNoInfo").html("<option value=''>Unable To Transfer CO User,  User Is Currently Active, Please Delegate<\/option>");
			} else if (msg1 == "VirtualUser") {
				$("#personalNoInfo").html("<option value=''>Transfer Out Is Not Allowed For Virtual User<\/option>");
			}
			else if (categoryName != "PBOR" && categoryName != "Officer") {
				$("#personalNoInfo").html("<option value=''>This functionality is available for PBOR and Officers<\/option>");
			} else if (serviceName == "") {
				$("#personalNoInfo").html("<option value=''>No Detail Found For Given Personal Number<\/option>");
			}else  {
				tdvalue += '<table width="100%" border="0" cellpadding="0" cellspacing="0" class="filtersearch"><tbody class="all1"><tr><td><table width="100%" border="0" cellpadding="4" cellspacing="1"  class="filtersearch">'
				tdvalue += '<tr align="left" class="showalert lablevalue">';
				tdvalue += '<td width="20%" height="25">Name</td>';
				tdvalue += '<td width="15%">Unit Service</td>';
				tdvalue += '<td width="15%">Traveler Service</td>';
				tdvalue += '<td width="15%">Unit Name</td>';
				tdvalue += '<td width="15%">Category Name</td>';
				tdvalue += '</tr>';
				tdvalue += '<tr align="left" class="">';
				var unitName = data.unitName
				var name = data.name
				tdvalue += '<td height="25">' + name + '</td>';
				tdvalue += '<td height="25">' + UnitServiceName + '</td>';
				tdvalue += '<td><input type="hidden" value="' + personalNo + '" name="personalNo">' + serviceName + '</td>';
				tdvalue += '<td>' + unitName + '</td>';
				tdvalue += '<td height="25"><input type="hidden" value="' + dob + '" name="dob" id="dob">' + categoryName + '</td>';
				tdvalue += '</tr>';

				tdvalue += '</table> </td></tr></tbody></table>';
				$("#personalNoInfo").html(tdvalue);
				$("#transferBtnDiv").show("fast");

				setPayAccountOfficeforCG(serviceName);
			}

			var service_options = "";

			if (categoryName == 'PBOR') {
				service_options += '<option value="">Select </option>';
				service_options += '<option value="100014">DSC </option>';
				
				$("#alternateService").html(service_options);
			} else {
				$.ajax({

					url: $("#context_path").val() + "mb/getServiceDetails",
					type: "get",
					data: "userAlias=" + userAlias,
					success: function(data) {


						$.each(data, function(idx, value) {
							var stringify = JSON.stringify(value);
							var msg1 = JSON.parse(stringify);

							var serviceIdDoc = msg1.serviceId;
							var serviceNameDoc = msg1.serviceName;
							var serviceobj = document.getElementById('serviceTd');

							var serviceStr = "";
							serviceStr += '<select name=\"alternateService\" class=\"combo\" id=\"alternateService\" >';
							serviceStr += '<option value=\"-1\">Select</option>';
							for (var i = 0; i < serviceIdDoc.length; i++) {
								if (serviceIdDoc[i].firstChild != null && serviceNameDoc[i].firstChild != null)
									serviceStr += '<option value=\"' + serviceIdDoc[i].firstChild.nodeValue + '\">' + serviceNameDoc[i].firstChild.nodeValue + '</option>';
							}
							serviceStr += '</select>';

							if (serviceobj != null)
								serviceobj.innerHTML = serviceStr;
						})
					}
				})

				$("#categoryId").children('option:not(:first)').remove();
				$("#levelId").children('option:not(:first)').remove();

			}
			$("#sosDate").val("");
			$("#dateOfRetirement").val("");
			$("#dutyStations_0").val("");
			$("#airportStationText_0").val("");
			$("#sprNrsPlaceID").val("");
			$("#sprStation_0").val("");
			$("#reason").val("");
			$("#isUnitInPeaceLoc").val("");

			//			$("#payAcOff").remove();
			//			addOption(document.getElementById("payAcOff"), "Select", "");
			//($("#payAcOff"), "Select", "");
			//		$("#airAcOff").remove();
			//			addOption(document.getElementById("airAcOff"), "Select", "");
			//			($("#airAcOff"), "Select", "");
		}
	});
	beforeSend: $('#personalNoInfo').html("Getting response from server....");
}

$(document).ready(function() {

$('#personalNo').on("cut copy paste",function(e) {
      e.preventDefault();
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

function getStationAir(obj, idx) {

	var officeNRS = obj;
	var airPortList = "";

	if (officeNRS.length > 1) {
		$.ajax({
			type: "get",
			url: $("#context_path").val() + "mb/getNADutyStationTrans",
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
			url: $("#context_path").val() + "mb/getStationNRSTrans",
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
			url: $("#context_path").val() + "mb/getSPRAirportTrans",
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
			url: $("#context_path").val() + "mb/getSPRStationTrans",
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
			url: $("#context_path").val() + "mb/getSPRNRSTrans",
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
				$('#category_Id').append(new Option(value, key));
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

function setPrintERSName() {
	var fName = $("#fName").val();
	var mName = $("#mName").val();
	var lname = $("#lName").val();
	var name = "";
	var flag = false;
	if (fName.trim().length > 0) {
		name = name + fName;
		flag = true;
	}

	if (mName.trim().length > 0) {
		if (flag) {
			name = name + " " + mName;
		} else {
			name = name + mName;
			flag = true;
		}
	}

	if (lname.trim().length > 0) {
		if (flag) {
			name = name + " " + lname;
		} else {
			name = name + lname;
			flag = true;
		}
	}

	if (name.length > 15) {
		name = name.substring(0, 15);
	}
	$("#ersPrntName").val(name);
}





