$(document).ready(function() {
	
	$('#personalNo').on("cut copy paste",function(e) {
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


});

function ismaxlength(obj, mlength) {
	if (obj.value.length > mlength)
		obj.value = obj.value.substring(0, mlength);
}









function validateUnitMovement() {
	var unitRelocationDate = $("#unitRelocationDate").val();
	var unitRailDutyNrs = $("#dutyStations_0").val();
	var unitDutyStnNa = $("#airportStationText_0").val();
	var unitMoveAuthority = $("#unitMoveAuthority").val();
	var addedPersonalNumber = document.getElementsByName("bulkPersonalNo");
	if(addedPersonalNumber.length<1){
		alert("Kindly Add Atleat one Personal Number")
		return false;
	}

	if (unitRailDutyNrs == '') {
		alert("Please fill nearest railway station (for reservation)");
		$("#dutyStations_0").focus();
		return false;
	}

	if (unitDutyStnNa == '') {
		alert("Please fill nearest airport (duty station)");
		$("#airportStationText_0").focus();
		return false;
	}
	if (unitRelocationDate == '') {
		alert("Please fill unit relocation date");
		$("#unitRelocationDate").focus();
		return false;
	}
	if (unitMoveAuthority == '') {
		alert("Please fill unit movement authority");
		$("#unitMoveAuthority").focus();
		return false;
	}

	$("#bulkTransferInSaveForm").submit();
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
		getDetailOfPersonalForTGransferIn();
			return true;
	   }else{
			alert("Please Enter Only Aplha Numeric");
			e.preventDefault();
		}
}


function getDetailOfPersonalForTGransferIn() {
	var userAlias = $("#personalNo").val().trim();
	var UnitServiceName = "";
	
	
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
				}else if (msg1 == "minMaxLength") {
					$("#personalNoInfo").html("Data Length for field Personal No should be minimum 4 and maximum 11");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "Not Valid") {
					alert("Please Enter Valid Personal Number");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "userNotActive") {
					alert("Personal No marked as non effective.");
					$("#personalNo").val('');
		return false;
				} else if (msg1 == "usernotapproved") {
					alert("Personal No You Have Entered Is Not Approved");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "notPartOfCivilian") {
					alert("Personal No You Have Entered Is Not Part Of Civilian Service");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == "notPartOfArmedForces") {
					alert("Personal No You Have Entered Is Not Part Of Armed Forces");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'dataexist') {
					alert("Personal No You Have Entered Is Under Transfer Process");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'editMode') {
					alert("Personal No You Have Entered Is Under Edit Process");
					$("#personalNo").val('');
					return false;
					} else if (msg1 == 'transferIn') {
					alert("Personal No You Have Entered Is Already Under Transfer In Process");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'activeCO') {
					alert("Unable To Transfer CO User,  User Is Currently Active, Please Delegate");
					$("#personalNo").val('');
					return false;
				} 
				 else if (msg1 == 'duplicate') {
					alert("Traveler Transfer Out' for which their approval request is pending and in process");
					$("#personalNo").val('');
		return false;
	             }else if (msg1 == 'VirtualUser') {
					alert("Transfer In Is Not Allowed For Virtual User");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'personalIsPartOfCostGaurd') {
					alert("Personal Belongs To Coast Guard Service.");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'notPartOfSameUnit') {
					alert("Transfer In Not Allowed Inside Unit .");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'tinNotAllowedFromAnyOtherToDSC') {
					alert("Transfer In From Any Other Service To DSC Service Is Not Allowed.");
					$("#personalNo").val('');
					return false;
				} else if (msg1 == 'noRecordFound') {
					alert("No Detail Found For Given Personal Number");
					$("#personalNo").val('');
					return false;
				} else {
					
					
				
					tdvalue+= '<tr id="user_'+data.userId+'"><input type="hidden" value="' +  data.userId + '" name="userId">';
					tdvalue += '<td  style="width:20%">' + data.personalNo + '</td>';
					tdvalue += '<td  style="width:20%" >' + data.name + '</td>';
					tdvalue += '<td  style="width:20%">' + UnitServiceName + '</td>';
					tdvalue += '<td  style="width:20%"><input type="hidden" value="' +  data.personalNo + '" name="bulkPersonalNo">' + data.serviceName + '</td>';
					tdvalue += '<td  style="width:20%">' +  data.unitName  + '</td>';
                    tdvalue += '<td  align="right"><input type="button" class="butn" value="Delete" onclick="delPersonalInfoRow(\'' + data.userId + '\');"/></td>';
                    tdvalue+= '</tr>';
					

					if( alreadyExist(userAlias)){
					$("#personalNoInfoBulk").append(tdvalue);
					$("#personalNo").val('');
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




