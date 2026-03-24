$(document).ready(function() {

//	getLevel();
//	setPaoOffice();
	initEditProfileRequest();
	showFamilyEdit();

	var ServiceName = $("#ServiceName").val();
	var categoryName = $("#CategoryName").val();
	var serviceId = $("#ServiceId").val();

	if ('100012' == serviceId || serviceId == '100001')
		getCategoryIdOnTraveller();
	if ((serviceId == '100002') && (categoryName.toUpperCase() == 'PBOR')) {
		document.getElementById("male").checked = true;
		document.getElementById("femaleHide").style.display = '';
	} else {
		document.getElementById("femaleHide").style.display = '';
	}

});

function getCategoryIdOnTraveller() {
	var serviceId = $("#ServiceId").val();

		$.ajax({
		type: "post",
		url: $("#context_path").val() + "mb/getCategoryIdOnTraveller",
		data: "serviceId=" + serviceId,
		datatype: "application/json",

		success: function(data) {

			var categoryStr="";
			categoryStr+='<select name=\"category\" class=\"combo\" id=\"categoryId\" onchange=\"getLevelDetailsEdit();\">';
			categoryStr+='<option value=\"-1\">Select</option>';
			$.each(data, function(i) {
				if(data[i].categoryId == $("#CategoryId").val()){
				categoryStr+='<option value=\"'+data[i].categoryId+'\" selected=true>'+data[i].categoryName+'</option>';
				}else{
				categoryStr+='<option value=\"'+data[i].categoryId+'\">'+data[i].categoryName+'</option>';
				}
			});
			categoryStr+='</select>';
			$("#categoryIdTd").html(categoryStr);

		}
	});

}

function getMobileVerify() {


	var mobNo = $("input[name='mobileNo']").val();


	if (mobNo != '') {

		if (!isValidMobileNumber(mobNo)) {
		//	alert("Please Enter Valid Mobile No.");
			 $("input[name='mobileNo']").val("");
			$("input[name='mobileNo']").focus();
			return false;
		}else
		return true;
	}

}

function trim(n) {
	return n.replace(/^\s+|\s+$/g, '');
}

function isEmpty(value) {
	return value == null || "" == value;
}

function setPrintERSName(){

	var fName=$("#fName").val();
	var mName=$("#mName").val();
	var lname=$("#lName").val();

	var name="";
	
	var flag=false;
	if(fName.trim().length > 0){
	 name=name+fName;
	 flag=true;
	}
	
	if(mName.trim().length > 0){
	 if(flag){
	   name=name+" "+mName;
	 }else{
	   name=name+mName;
	   flag=true;
	 }
	}
	if(lname.trim().length > 0){
	 if(flag){
	    name=name+" "+lname;
	    }else{
	    name=name+lname;
	    flag=true;
	    }
	}
	
	if(name.length > 15){
	name=name.substring(0,15);
	}
	
	document.getElementById("ersPrntName").value=name;
	document.getElementById("nameOnBoardingPass").value=lname+" "+fName+" "+mName;

}

$(function() {

//	initProfileEditing();

});

$(function() {
//	initRequestCreation();
});




function showFamilyEdit() {
var ServiceName = $("#ServiceName").val();

	var familyDetailEdit = "";

	familyDetailEdit += '<tr align="center">';

	familyDetailEdit += '<td width="8%">First Name</td>';
	familyDetailEdit += '<td width="8%">Middle Name</td>';
	familyDetailEdit += '<td width="8%">Last Name</td>';

	familyDetailEdit += '<td width="9%">Gender</td>';
	familyDetailEdit += '<td width="10%">Date of Birth</td>';
	familyDetailEdit += '<td width="10%">Relation</td>';
	familyDetailEdit += '<td width="10%">Marital Status</td>';
	 if(ServiceName=='Navy'){
	familyDetailEdit += '<td width="10%">GX No</td>';
	familyDetailEdit += '<td width="10%">GX Date</td>';
	}else if(ServiceName=='AirForce'){
	familyDetailEdit += '<td width="10%">POR No</td>';
	familyDetailEdit += '<td width="10%">POR Date    </td>';
	}else{
	familyDetailEdit += '<td width="10%">DOII Part No</td>';
	familyDetailEdit += '<td width="10%">DOII Date    </td>';
	}
	familyDetailEdit += '<td width="8%">Reason</td>';
	familyDetailEdit += '<td width="10%">ERS Print Name</td>';
	familyDetailEdit += '<td width="7%">Status</td>';
	familyDetailEdit += '<td width="7%">Hostel NRS</td>';
	familyDetailEdit += '<td width="7%">Hostel NAP</td>';
	familyDetailEdit += '<td width="7%">Remarks</td>';
	familyDetailEdit += '<td width="10%">Remove</td></tr>';


	familyDetailEdit += '';

	$("#editFamilyDetails").html(familyDetailEdit);
	getGender();
	getMaritalStatusVal();
//	calendar2 = new Epoch('cal2', 'popup', document.getElementById('dateOfBrth'), false);
//	calendar2 = new Epoch('cal2', 'popup', document.getElementById('dateOfCom_join'), false);
//	calendar2 = new Epoch('cal2', 'popup', document.getElementById('dateOfRtirementEdit'), false);


	var depDob = document.getElementsByName('dob');
	var depDo2Date = document.getElementsByName('doIIDate');

	for (var n = depDob.length; n > 0; n--) {
		calendar2 = new Epoch('cal2', 'popup', depDob[n - 1], false);
		calendar2 = new Epoch('cal2', 'popup', depDo2Date[n - 1], false);
	}

	var FirstName = document.getElementsByName("firstmemberName");
	var MiddleName = document.getElementsByName("middlememberName");
	var LastName = document.getElementsByName("lastmemberName");


	var PrintName = FirstName;

	var idx = PrintName.length;

	for (var z = idx - 1; z >= 0; z--) {

		var depName = PrintName[z].value;
		var fullname = depName.split(" ");

		var firstname = "";
		var middlename = "";
		var lastname = "";
		if (fullname.length == 1) {
			firstname = fullname[0];
		} else if (fullname.length == 2) {
			firstname = fullname[0];
			lastname = fullname[1];
		} else {
			firstname = fullname[0];
			middlename = fullname[1];
			lastname = fullname[2];
		}


		if ((firstname).trim() != "") {
			document.getElementsByName("firstmemberName")[z].value = firstname;
		}
		if ((middlename).trim() != "") {
			document.getElementsByName("middlememberName")[z].value = middlename;
		}
		if ((lastname).trim() != "") {
			document.getElementsByName("lastmemberName")[z].value = lastname;
		}
	}
}

function hideDepDetails() {
	var checkBox = document.getElementsByName('depChkBox');
	var tbl = document.getElementById('editFamilyDetails');
	var opType = document.getElementsByName('opType');
	var rowNumber = document.getElementById('rowNumber');
	var checkedValues = 0;
	for (var k = checkBox.length; k >= 0; k--) {
		if (typeof checkBox[k - 1] != "undefined") {
			if (checkBox[k - 1].checked == true) {
				//tbl.deleteRow(k);
				//tbl.rows[k].style.display = 'none';
				tbl.rows[k].checked;
			}
		}
	}
}

function setRelationFields(value) {

	var options = "";
	options += '<select id="memRelation" class="comboauto" name="memRelation">'
	options += '<option value="">Select</option>'
	var enumRel = document.getElementById('relationFamilyEnum').value;
	var relCode;

	var spltVals = enumRel.split(',');

	var index = spltVals.length;

	for (var i = index - 1; i >= 0; i--) {
		var relArr = spltVals[i].split("::");

		if (value == 1) {
			if (relArr[1] == 6 || relArr[1] == 7 || relArr[1] == 8 || relArr[1] == 9 || relArr[1] == 10 || relArr[1] == 11)
				options += '<option value="' + relArr[1] + '">' + relArr[0] + '</option>'
		}
		else
			options += '<option value="' + relArr[1] + '">' + relArr[0] + '</option>'
	}
	options += '</select>'
	$('#relationDiv').html(options);
}



function setPrintFamilyERSName(value){

var ersFamPrintName="";

ersFamPrintName=document.getElementsByName("ersFamilyPrintName");

fName=document.getElementsByName("firstmemberName");
mName=document.getElementsByName("middlememberName");
lName=document.getElementsByName("lastmemberName");

var PrintName = fName;


var idx=PrintName.length;

for(var z=idx-1;z>=0;z--){

var depName=PrintName[z].value;
if(mName[z].value.trim().length > 0){
 depName=depName+" "+mName[z].value;
}

if(lName[z].value.trim().length > 0){
 depName=depName+" "+lName[z].value;
}

if(depName.length > 15){
  depName=depName.substring(0,15);
}
ersFamPrintName[z].value=depName;
}
}


function setPrintFamilyERSName(value) {

	var ersFamPrintName = "";

	ersFamPrintName = document.getElementsByName("ersFamilyPrintName");

	fName = document.getElementsByName("firstmemberName");
	mName = document.getElementsByName("middlememberName");
	lName = document.getElementsByName("lastmemberName");

	var PrintName = fName;



	var idx = PrintName.length;

	for (var z = idx - 1; z >= 0; z--) {

		var depName = PrintName[z].value;
		if (mName[z].value.trim().length > 0) {
			depName = depName + " " + mName[z].value;
		}

		if (lName[z].value.trim().length > 0) {
			depName = depName + " " + lName[z].value;
		}

		if (depName.length > 15) {
			depName = depName.substring(0, 15);
		}
		ersFamPrintName[z].value = depName;
	}
}

//function showGender(famGender,famGendervalue){
// var memGenderOption="";
// var genValue;
//   var idx=$('#famIndex').val();
//  memGenderOption+='<option value="-1">Select</option>';
//  
//  memGenderOption+='<repeat ref="GENDER/Enum">';
//  
//  genValue='<output ref="EnumCode"/>';
//  
//  if(genValue==famGendervalue)
//  memGenderOption+='<option value="${EnumCode}" selected="selected"><output ref="EnumValue"/></option>';
//  else
//  memGenderOption+='<option value="${EnumCode}"><output ref="EnumValue"/></option>';
//  
//	memGenderOption+='</repeat>'
//  famGender.innerHTML=memGenderOption;
//   var isDepen=document.getElementsByName('isDepen');
//   //alert(isDepen[idx].value+idx);
//  isDepen[idx].value="";
//     //alert("in case of gender:"+isDepen[idx].value);
//}

//function showMaritalStatus(famMaritalStatus,famMaritalStatusValue){
// var memMaritalStatusOption="";
// var marStausValue="";
//  var idx=$('#famIndex').val();
//  memMaritalStatusOption+='<option value="-1">Select</option><repeat ref="MARITAL_STATUS/Enum">';
//  marStausValue='<output ref="EnumCode"/>';
//  
//  if(marStausValue==famMaritalStatusValue)
//  memMaritalStatusOption+='<option value="${EnumCode}" selected="selected"><output ref="EnumValue"/></option> ';
//  else
//  memMaritalStatusOption+='<option value="${EnumCode}"><output ref="EnumValue"/></option> ';
//  
//  memMaritalStatusOption+='</repeat>';
// 
//  famMaritalStatus.innerHTML=memMaritalStatusOption;
//   var isDepen=document.getElementsByName('isDepen');
//   //  alert(isDepen[idx].value+idx);
//  isDepen[idx].value="";
//    // alert("in mar status::"+isDepen[idx].value);
//}
//
//function showRelation(famRelation,famRelationValue){
// 
// var memRelationOption="";
// var relValue;
// 
// 
//  memRelationOption+='<option value="-1">Select</option><repeat ref="RELATION_TYPE/Enum">';
//  relValue='<output ref="EnumCode"/>';
//  
//  if(relValue==famRelationValue)
//  memRelationOption+='<option value="${EnumCode}" selected="selected"><output ref="EnumValue"/></option>' ;
//  else
//   memRelationOption+='<option value="${EnumCode}" ><output ref="EnumValue"/></option>' ;
//   
//  memRelationOption+='</repeat>';
//  
//  famRelation.innerHTML=memRelationOption;
//  
//   var isDepen=document.getElementsByName('isDepen');
//   for(var x=isDepen.length;x>=0;x--){
//   
//    // alert(isDepen[x-4].value+x);
//  isDepen[x-4].value="";
// 
//     }
//}

function showGender(famGender, famGendervalue) {
	var memGenderOption = "";
	var genValue;
	var idx = $('#famIndex').val();
	memGenderOption += '<option value="-1">Select</option>';

	memGenderOption += '';

	genValue = '0';

	if (genValue == famGendervalue)
		memGenderOption += '<option value="0" selected="selected">Male</option>';
	else
		memGenderOption += '<option value="0">Male</option>';

	memGenderOption += '';

	genValue = '1';

	if (genValue == famGendervalue)
		memGenderOption += '<option value="1" selected="selected">Female</option>';
	else
		memGenderOption += '<option value="1">Female</option>';

	memGenderOption += ''
	famGender.innerHTML = memGenderOption;
	var isDepen = document.getElementsByName('isDepen');
	//alert(isDepen[idx].value+idx);
	isDepen[idx].value = "";
	//alert("in case of gender:"+isDepen[idx].value);

}


function showMaritalStatus(famMaritalStatus, famMaritalStatusValue) {
	var memMaritalStatusOption = "";
	var marStausValue = "";
	var idx = $('#famIndex').val();
	memMaritalStatusOption += '<option value="-1">Select</option>';
	marStausValue = '0';

	if (marStausValue == famMaritalStatusValue)
		memMaritalStatusOption += '<option value="0" selected="selected">Married</option> ';
	else
		memMaritalStatusOption += '<option value="0">Married</option> ';

	memMaritalStatusOption += '';
	marStausValue = '1';

	if (marStausValue == famMaritalStatusValue)
		memMaritalStatusOption += '<option value="1" selected="selected">Unmarried</option> ';
	else
		memMaritalStatusOption += '<option value="1">Unmarried</option> ';

	memMaritalStatusOption += '';

	famMaritalStatus.innerHTML = memMaritalStatusOption;
	var isDepen = document.getElementsByName('isDepen');
	//  alert(isDepen[idx].value+idx);
	isDepen[idx].value = "";
	// alert("in mar status::"+isDepen[idx].value);
}

function showRelation(famRelation, famRelationValue) {

	var memRelationOption = "";
	var relValue;


	memRelationOption += '<option value="-1">Select</option>';
	relValue = '0';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="0" selected="selected">Self</option>';
	else
		memRelationOption += '<option value="0">Self</option>';

	memRelationOption += '';
	relValue = '1';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="1" selected="selected">Spouse</option>';
	else
		memRelationOption += '<option value="1">Spouse</option>';

	memRelationOption += '';
	relValue = '2';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="2" selected="selected">Son</option>';
	else
		memRelationOption += '<option value="2">Son</option>';

	memRelationOption += '';
	relValue = '3';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="3" selected="selected">Step Son</option>';
	else
		memRelationOption += '<option value="3">Step Son</option>';

	memRelationOption += '';
	relValue = '4';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="4" selected="selected">Daughter</option>';
	else
		memRelationOption += '<option value="4">Daughter</option>';

	memRelationOption += '';
	relValue = '5';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="5" selected="selected">Step Daughter</option>';
	else
		memRelationOption += '<option value="5">Step Daughter</option>';

	memRelationOption += '';
	relValue = '6';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="6" selected="selected">Brother</option>';
	else
		memRelationOption += '<option value="6">Brother</option>';

	memRelationOption += '';
	relValue = '7';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="7" selected="selected">Sister</option>';
	else
		memRelationOption += '<option value="7">Sister</option>';

	memRelationOption += '';
	relValue = '8';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="8" selected="selected">Step Mother</option>';
	else
		memRelationOption += '<option value="8">Step Mother</option>';

	memRelationOption += '';
	relValue = '9';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="9" selected="selected">Step Father</option>';
	else
		memRelationOption += '<option value="9">Step Father</option>';

	memRelationOption += '';
	relValue = '10';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="10" selected="selected">Father</option>';
	else
		memRelationOption += '<option value="10">Father</option>';

	memRelationOption += '';
	relValue = '11';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="11" selected="selected">Mother</option>';
	else
		memRelationOption += '<option value="11">Mother</option>';

	memRelationOption += '';
	relValue = '12';

	if (relValue == famRelationValue)
		memRelationOption += '<option value="12" selected="selected">Other</option>';
	else
		memRelationOption += '<option value="12">Other</option>';

	memRelationOption += '';

	famRelation.innerHTML = memRelationOption;

	var isDepen = document.getElementsByName('isDepen');
	for (var x = isDepen.length; x >= 0; x--) {

		// alert(isDepen[x-4].value+x);
		isDepen[x - 4].value = "";

	}
}

//function setRetireAgeAndGradePay(){
//  var flag=true;
//  var levelId=$("#levelId").val();
// 
//	<repeat nodeset="ProfileDetails/LEVEL_INFO">
//	 var level='<output ref="Level_id" escapeXml="true" />';
//<![CDATA[ if(flag && levelId == level){ ]]>
//	   flag=false;
//	   var retirAge=<output ref="RetireAge" escapeXml="true" />;
//	   
//	   if(retirAge > 0){
//	     $("#retireAge").val(<output ref="RetireAge" escapeXml="true" />);
//	   }else{
//	     $("#retireAge").val("");
//	   }
//	   
//	   $("#RankName").val('<output ref="Rank_Name" escapeXml="true" />');
//	   $("#rankId").val('<output ref="Rank_Id" escapeXml="true" />');
//	   $("#rankNameInner").html('<output ref="Rank_Name" escapeXml="true" />');
//	 }
//	</repeat>
//	
//	if(flag){
//	   $("#retireAge").val("");
//	   $("#RankName").val("");
//	   $("#rankId").val("");
//	   $("#rankNameInner").html("");
//	 }
//}

function setRetireAgeAndGradePay() {
	var flag = true;
	var levelId = $("#levelId").val();



	if (flag) {
		$("#retireAge").val("");
		$("#RankName").val("");
		$("#rankId").val("");
		$("#rankNameInner").html("");
	}
}

function resetAccNumber() {
	$("#reAccountNumber").val("");
	$("#isACNumberMatch").html("");
}

function resetIFSCCode() {
	$("#reIFSCCode").val("");
	$("#isIFSCCodeMatch").html("");
}

function matchIFSCCode() {
	var text = "";
	if ($("#ifscCode").val() == $("#reIFSCCode").val()) {
		text = "Match";
	} else {
		text = "Not Match";
	}
	$("#isIFSCCodeMatch").html(text);
}

function matchAccNumber() {
	var text = "";
	if ($("#accountNumber").val() == $("#reAccountNumber").val()) {
		text = "Match";
	} else {
		text = "Not Match";
	}
	$("#isACNumberMatch").html(text);
}





function validateAccountDetails() {

	var isCivilianService = $("#civilianService").val();

	if (isCivilianService == 'true') {

		var accountNumber = $("#accountNumber").val();
		var ifscCode = $("#ifscCode").val();
		if (accountNumber != "" && (accountNumber.length < 5 || accountNumber.length > 20)) {
			alert("Account Number Should Be Minuman 5 And Maximum 20 Character.")
			$("#accountNumber").focus();
			return false;
		}

		if (accountNumber != "" && !checkNumeric(accountNumber)) {
			alert("Account Number Should be Numeric Character.")
			$("#accountNumber").focus();
			return false;
		}
		if (accountNumber != "" && $("#accountNumber").val() != $("#reAccountNumber").val()) {
			alert("Enter And Re-Enter Account Number Not Match.")
			$("#accountNumber").focus();
			return false;
		}

		if (ifscCode != "" && (ifscCode.length < 11 || ifscCode.length > 11)) {
			alert("IFSC Code Should Be 11 Character.")
			$("#ifscCode").focus();
			return false;
		}

		if (ifscCode != "" && !checkAlphaNumeric(ifscCode)) {
			alert("IFSC Code Should be AlphaNumeric Character.")
			$("#ifscCode").focus();
			return false;
		}

		if (ifscCode != "" && $("#ifscCode").val() != $("#reIFSCCode").val()) {
			alert("Enter And Re-Enter IFSC Code Not Match.")
			$("#ifscCode").focus();
			return false;
		}

		if (null != ifscCode && ifscCode != "") {
			let ifsccode = $("#ifscCode").val();
			var reg = /[A-Z]{4}[0][A-Z0-9]{6}$/;
			if (ifsccode.match(reg)) {
				//return true;    
			}
			else {
				$("#ifscCode").val("");
				$("#reIFSCCode").val("");
				alert("Please enter valid IFSC code");
				$("#ifscCode").focus();
				return false;
			}
		}


		return true;

	} else {
		return true;
	}


}

function getLevel() {
	let serviceId = $("#ServiceId").val();
	let categoryId = $($('#CategoryId')).val();

	$("#rank").empty();
	$("#rankId").val("");
	$('#levelId').find('option').not(':first').remove();

	if (categoryId != "") {
			$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getTravelerLevel",
			data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
			datatype: "application/json",
	
			success: function(data) {

				$.each(data, function(key, value) {
					$('#levelId').append(new Option(value[0], key + ":" + value[1]));
				});
	
			}
		});
	}
}

function setRankLevel(obj) {

	if ($(obj).val() != "-1") {
		var var1 = $(obj).val();
		var bobj = {};
		bobj = JSON.parse(var1);

		$("#rankId").val(bobj.rankId);

		$("#level").val(bobj.levelId);
	
		$('#rank').empty();
		$('#rank').append(bobj.rankName);

		$('#retireAge').val(bobj.retireAge);
	} else {
		$("#rankId").val("");
		$('#rank').empty();
		$("#level").val("");
		$('#retireAge').val("");
	}
}


