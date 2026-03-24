var army_array = new Array("A", "F", "H", "K", "L", "M", "N", "P", "W", "X", "Y");
var navy_array_off = new Array("Z", "A", "B", "F", "H", "K", "N", "R", "T", "W", "Y");
var navy_array_pbor = new Array("A", "B", "F", "H", "K", "N", "R", "T", "W", "Y", "Z");
var airForce_array = new Array("", "A", "B", "F", "G", "H", "K", "L", "N", "R", "S", "T");
var coast_Enroll_Officer = new Array("X", "C", "D", "E", "J", "L", "M", "P", "Q", "S", "V");
var coast_Enroll_Sub_Officer = new Array("Z", "H", "L", "M", "P", "Q", "R", "S", "T", "W", "Y");
var dsc_array = new Array("A", "F", "H", "K", "L", "M", "N", "P", "W", "X", "Y");

var cdaoAcc_array = new Array("A", "F", "H", "K", "L", "M", "N", "P", "W", "X", "Y");



/* -------------   A R M Y    C A D E T S     V A L I D A T I O N    S T A T R T  ----------------------*/
var army_acc_cadets = "ACC Cadets"; var army_acc_cadets_prefix = "A"; var army_acc_cadets_max_lenght = 5;
var army_nda_cadets = "NDA Cadets"; var army_nda_cadets_prefix = "N"; var army_nda_cadets_max_lenght = 5;
var army_direct_entry_cadets = "Direct Entry Cadets"; var army_direct_entry_cadets_prefix = "D"; var army_direct_entry_cadets_max_lenght = 5;
var army_tgc_cadets = "TGC Cadets"; var army_tgc_cadets_prefix = "T"; var army_tgc_cadets_max_lenght = 4;
var army_ues_cadets = "UES Cadets"; var army_ues_cadets_prefix = "U"; var army_ues_cadets_max_lenght = 4;
var army_acc_wing_cadets = "ACC Wing Cadets"; var army_acc_wing_cadets_max_lenght = 4;
var army_afmc_cadets = "AFMC Cadets"; var army_afmc_cadets_max_lenght = 4; var army_afmc_cadets_max_lenghtOr = 5;
/* -------------   A R M Y    C A D E T S     V A L I D A T I O N    E N D  ----------------------------*/



var validatePersonalNo = $("#validatePersonalNo");
var chkForNo;
function checkAlphaNumber1() {
	//var chkForService=document.saveTraveller.services.value;	
	//var sericeName1=document.getElementById("serviceId");

	var sericeName1 = $("#alternateService");
	var chkForService = $("#alternateService option:selected").text();

	var categoryName = $("#categoryId");
	var categoryNameVal = $("#categoryId option:selected").text();

	if (chkForService == 'select') {
		alert("Please select the service");
		sericeName1.focus();
		return false;
	}

	if (categoryNameVal == 'select' || categoryNameVal == 'Select') {
		alert("Please select the category");
		categoryName.focus();
		return false;
	}

	var level = $("#levelId").val();
	if (level == '-1' || level == '') {
		alert("Please select the level");
		return false;
	}


	var rankVal = document.getElementById("rankId").value;

	if (rankVal == '' || rankVal == '-1') {
		alert("Unable to get your rank.");
		return false;
	}

	var alphaNo = $("#alphaNoId option:selected").val().trim();
	var personalNo = $("#personalNo").val();
	var chkAlpha = $("#chkAlpha").val();

	chkForService = chkForService.toUpperCase();
	var categoryNames = categoryNameVal.toUpperCase();
	
		if ((chkForService == 'ARMY') && (categoryNames == 'OFFICER') && (alphaNo == '')) {
	
			alert("Please Select Prefix");
			$("#alphaNoId").focus();
			return false;
		}

	

	if ((chkForService == 'ARMY') && (categoryNames == 'OFFICER') && (alphaNo.toUpperCase() == 'IC')) {
		if (personalNo.length != 5) {
			alert("Please enter correct personal number of 5 digit.");
			$("#personalNo").focus();
			return false;
		}

	}
		/* TES CADET VALIDATION*/
	 if((chkForService=='ARMY')  && (categoryNames.trim() == 'TES CADET')) 
    {
    
    	if(alphaNo==''){
	    	alert("Please select prefix.");
	   		 $("#alphaNoId").focus();
	   		 return false;
	    }
   
	    if(alphaNo.toUpperCase() == 'TES' && (personalNo.length < 4 || personalNo.length > 5 ))
	    {    
	   		 alert("Please enter correct personal number of 4 or 5 digit.");
	   		 $("#personalNo").focus();
	   		 return false;
	    }
	    
	}
/* TES CADET VALIDATION END*/


		/* NURSING STUDENT VALIDATION*/
	 if((chkForService=='ARMY')  && (categoryNames.trim() == 'NURSING STUDENT')) 
    {
    
    	if(alphaNo==''){
	    	alert("Please select prefix.");
	   		 $("#alphaNoId").focus();
	   		 return false;
	    }
	    
	 
   
	    if(alphaNo.toUpperCase() == 'SN' && (personalNo.length !=5))
	    {    
	   		 alert("Please enter correct personal number of 5 digit.");
	   		 $("#personalNo").focus();
	   		 return false;
	    }
	    
	}
	/* END NURSING STUDENT VALIDATION*/

	if ((chkForService == 'ARMY') && (categoryNames == 'NCC WTLO')) {
		if (personalNo.length != 5) {
			alert("Please enter correct personal number of 5 digit.");
			$("#personalNo").focus();
			return false;
		}

	}

	if ((chkForService == 'MNS') && (categoryNames == 'OFFICER') && (alphaNo.toUpperCase() == 'IC' || alphaNo.toUpperCase() == 'NR' || alphaNo.toUpperCase() == 'NS')) {
		if (personalNo.length != 5) {
			alert("Please enter correct personal number of 5 digit.");
			$("#personalNo").focus();
			return false;
		}

	}

	if((chkForService=='ARMY' || chkForService=='NAVY') && categoryNames.indexOf('AGNIVEER') > -1){
		
		 if(personalNo.length!=7){
	   		 alert("Please enter correct personal number of 7 digit.");
	   		 $("#personalNo").focus();
	   		 return false;
	    }
	    
	    if(alphaNo==''){
	    	alert("Please select prefix.");
	   		  $("#personalNo").focus();
	   		 return false;
	    }
	}
	
	if(chkForService=='AIRFORCE' && categoryNames.indexOf('AGNIVEER') > -1){
		
		 if(personalNo.length!=7){
	   		 alert("Please enter correct personal number of 7 digit.");
	   		 $("#personalNo").focus();
	   		 return false;
	    }
	    
	}

	//alert("value of service name = "+chkForService);

	if (chkForService.indexOf('COAST GUARD') > -1) {

		if (personalNo == '') {
			alert("Please fill the personal field");
			$("#personalNo").focus();
			return false;
		}

		if (categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1 || categoryNameVal.indexOf('Civilian Gazetted Officers') > -1) {
			if (categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1) {

				if (chkAlpha == '') {
					alert("Please fill Suffix");
					$("#chkAlpha").focus();
					return false;
				}
				if (chkAlpha != 'S') {
					alert("Please fill Suffix only 'S' character");
					$("#chkAlpha").focus();
					return false;
				}
				//chkForNo=alphaNo+personalNo;

			}
			else if (categoryNameVal.indexOf('Civilian Gazetted Officers') > -1) {
				if (chkAlpha != '') {
					alert("Please do not fill Suffix");
					$("#chkAlpha").focus();
					return false;
				}
				chkForNo = personalNo;
			}

		} else {
			if (alphaNo != '') {
				alert("Please do not fill Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			chkForNo = personalNo + chkAlpha;
		}
	}
	else if (checkNavyAirforceCadets()) {
		var cadetNo = $("#cadetNo").val();
		var cadetChkAlpha = $("#cadetChkAlpha").val();
		var courseSerialNo = $("#courseSerialNo").val();
		var airForceCadetNo = $("#airForceCadetNo").val();

		if (checkAirforceCadet()) {
			chkForNo = airForceCadetNo;
		} else {
			chkForNo = cadetNo + cadetChkAlpha + courseSerialNo;
		}
	} else {
		if (personalNo == '') {
			alert("Please fill the personal field");
			$("#personalNo").focus();
			return false;
		}

		if (chkForService.indexOf('APS') > -1) {
			if (chkAlpha == '') {
				alert("Please fill the alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
			if (!chkForArmy1(personalNo, chkAlpha, categoryNameVal))
				return false;

		}
		chkForNo = alphaNo + personalNo + chkAlpha;

	}

	if (chkForService.indexOf('ARMY') > -1 || chkForService.indexOf('MNS') > -1) {
		/* -------------   A R M Y    C A D E T S     V A L I D A T I O N    S T A T R T  ----------------------*/

		if (categoryNameVal.indexOf(army_acc_cadets) > -1) {
			if (alphaNo == '') {
				alert("Alpha Prefix Cant Not Be Empty.");
				$("#alphaNoId").focus();
				return false;
			}
			if (alphaNo != army_acc_cadets_prefix) {
				alert("Please fill correct Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_acc_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_acc_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}

			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}


		} else if (categoryNameVal.indexOf(army_nda_cadets) > -1) {
			if (alphaNo == '') {
				alert("Alpha Prefix Cant Not Be Empty.");
				$("#alphaNoId").focus();
				return false;
			}
			if (alphaNo != army_nda_cadets_prefix) {
				alert("Please fill correct Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_nda_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_nda_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
		} else if (categoryNameVal.indexOf(army_direct_entry_cadets) > -1) {
			if (alphaNo == '') {
				alert("Alpha Prefix Cant Not Be Empty.");
				$("#alphaNoId").focus();
				return false;
			}
			if (alphaNo != army_direct_entry_cadets_prefix) {
				alert("Please fill correct Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_direct_entry_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_direct_entry_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
		} else if (categoryNameVal.indexOf(army_tgc_cadets) > -1) {
			if (alphaNo == '') {
				alert("Alpha Prefix Cant Not Be Empty.");
				$("#alphaNoId").focus();
				return false;
			}
			if (alphaNo != army_tgc_cadets_prefix) {
				alert("Please fill correct Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_tgc_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_tgc_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}

		} else if (categoryNameVal.indexOf(army_ues_cadets) > -1) {
			if (alphaNo == '') {
				alert("Alpha Prefix Cant Not Be Empty.");
				$("#alphaNoId").focus();
				return false;
			}
			if (alphaNo != army_ues_cadets_prefix) {
				alert("Please fill correct Alpha Prefix");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_ues_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_ues_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}

		} else if (categoryNameVal.indexOf(army_acc_wing_cadets) > -1) {
			if (alphaNo != '') {
				alert("Please Do Not Fill Alpha Prefix.");
				$("#alphaNoId").focus();
				return false;
			}
			if (personalNo == '' || personalNo.length != army_acc_wing_cadets_max_lenght) {
				alert("Please Fill Correct Personal Number Of " + army_acc_wing_cadets_max_lenght + " Digit.");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
		}

		else if (categoryNameVal.indexOf(army_afmc_cadets) > -1) {

			if (personalNo == '') {
				alert("Please Fill Correct Personal Number");
				$("#personalNo").focus();
				return false;
			}
			if (personalNo.length != army_afmc_cadets_max_lenght && personalNo.length != army_afmc_cadets_max_lenghtOr) {
				alert("Please Fill Correct Personal Number");
				$("#personalNo").focus();
				return false;
			}
			if (chkAlpha != '') {
				alert("Please do not fill alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
		}

		/* -------------   A R M Y    C A D E T S     V A L I D A T I O N    E N D  ----------------------*/

		else {
			// for officer and pbor

			var request_form = $("#request_form").val();

			if (typeof request_form === 'undefined') {

			} else {
				if (alphaNo == '') {
					alert("Alpha Prefix Can Not Be Empty.");
					$("#alphaNoId").focus();
					return false;
				}
			}
         if(chkAlpha=='' && !categoryNameVal.toUpperCase().trim()=='TES CADET'){
				alert("Please fill the alpha no field");
				//document.saveTraveller.chkAlpha.focus();
				$("#chkAlpha").focus();
				return false;
			}

		
		
			

			/* TES CADET categoryNameVal.toUpperCase()=='TES CADET') */
			
			if (!chkForArmy1(personalNo, chkAlpha, categoryNameVal))
				return false;
		}

	}

	if (chkForService.indexOf('AIRFORCE') > -1 || chkForService.indexOf('AIR FORCE') > -1) {
		if (checkNavyAirforceCadets()) {
			if (!checkForAFCadet()) {
				return false;
			}
		} else {
			if (chkAlpha == '') {
				alert("Please fill the alpha no field");
				$("#chkAlpha").focus();
				return false;
			}

			if (!checkForAF1(personalNo, chkAlpha, categoryNameVal))
				return false;
		}
	}
	if (chkForService.indexOf('NAVY') > -1) {
		if (checkNavyAirforceCadets()) {
			if (!checkForNavyCadet()) {
				return false;
			}
		} else {
			if (chkAlpha == '') {
				alert("Please fill the alpha no field");
				$("#chkAlpha").focus();
				return false;
			}
			if (!chkForNavy1(personalNo, chkAlpha, categoryNameVal))
				return false;
		}
	}

	if (chkForService.indexOf('COAST GUARD') > -1 || chkForService.indexOf('COASTGUARD') > -1) {
		if (chkAlpha == '') {
			alert("Please fill the alpha no field");
			$("#chkAlpha").focus();
			return false;
		}
		if (!chkForCoastGaurd(personalNo, chkAlpha, categoryNameVal))
			return false;
	}

	if (chkForService.indexOf('DSC') > -1) {
		if (chkAlpha == '') {
			alert("Please fill the alpha no field");
			$("#chkAlpha").focus();
			return false;
		}
		if (!chkForDSC1(personalNo, chkAlpha, categoryNameVal))
			return false;
	}

	chkForDuplicateTraveller1(chkForNo);

}



function chkForNavy1(personalNo, chkAlpha, categoryNameVal) {

	var nayvArrayIndex;
	nayvArrayIndex = personalNo % 11;

	if (categoryNameVal.indexOf('PBOR') > -1 || categoryNameVal.toUpperCase().indexOf('AGNIVEER') > -1) {
		if (navy_array_pbor[nayvArrayIndex] != chkAlpha) {
			alert("Please enter correct check alpha number");
			$("#chkAlpha").value = "";
			$("#chkAlpha").focus();

			if (validatePersonalNo != null)
				validatePersonalNo.value = "false";

			

			return false;

		}

		return true;

	} else {

		if (navy_array_off[nayvArrayIndex] != chkAlpha) {
			alert("Please enter correct check alpha number");
			$("#chkAlpha").value = "";
			$("#chkAlpha").focus();

			if (validatePersonalNo != null)
				validatePersonalNo.value = "false";

			
			return false;

		}
		return true;
	}
}



function chkForArmy1(personalNo, chkAlpha, categoryNameVal) {
/* TES CADET IMPLEMENTATION*/
if(categoryNameVal.toUpperCase().trim()=='TES CADET'|| categoryNameVal.toUpperCase().trim()=='NURSING STUDENT'){
		
		return true;
	}
	
/* TES CADET IMPLEMENTATION*/
	var personalNolength = personalNo.length;
	var startIndex = personalNolength + 1;
	var sum = 0;
	var armyArrayIndex;
	for (var i = 0; i < personalNo.length; i++) {
		sum += personalNo.charAt(i) * startIndex;
		startIndex--;
	}
	armyArrayIndex = sum % 11;

	if (army_array[armyArrayIndex] != chkAlpha) {
		alert("Please enter correct check alpha number");
		$("#chkAlpha").value = "";
       $("#chkAlpha").focus();
		if (validatePersonalNo != null)
			validatePersonalNo.value = "false";

		//$("#personalNo").focus();
		return false;
	}

	return true;
}



function checkForAF1(personalNo, chkAlpha, categoryNameVal) {
	var personalNolength = personalNo.length;
	var startIndex = personalNolength + 1;
	var sum = 0;
	var afIndex;

	if (categoryNameVal.indexOf('PBOR') > -1 || categoryNameVal.toUpperCase().indexOf('AGNIVEER') > -1) {

		if (personalNo.length == 6 || personalNo.length == 7) {
			for (var i = 0; i < personalNo.length; i++) {
				sum += personalNo.charAt(i) * startIndex;
				startIndex--;
			}

			afIndex = 11 - (sum % 11);
		}

	} else {
		if (personalNo.length == 5) {
			for (var i = 0; i < personalNo.length; i++) {
				sum += personalNo.charAt(i) * startIndex;
				startIndex--;
			}

			afIndex = (sum % 11) + 1;
		}

	}

	if (airForce_array[afIndex] != chkAlpha) {
		alert("Please enter correct check alpha number");
		$("#chkAlpha").value = "";
		$("#chkAlpha").focus();

		if (validatePersonalNo != null)
			validatePersonalNo.value = "false";

		//$("#personalNo").focus();
		return false;
	}

	return true;
}



function chkForCoastGaurd(personalNo, chkAlpha, categoryNameVal) {
	var coastGaurdIndex;
	coastGaurdIndex = personalNo % 11;

	//alert("personalNo="+personalNo+"|chkAlpha="+chkAlpha+"|categoryNameVal="+categoryNameVal);

	if (categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1 || categoryNameVal.indexOf('Civilian Gazetted Officers') > -1) {
		if (categoryNameVal.indexOf('Civilian Gazetted Officers') > -1) {
			var minVal = 17001; //Minimum value

			if (personalNo.length < 5) {
				alert("Please enter correct personal number");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}
			else if (parseInt(personalNo) < minVal) {
				alert("Please enter correct personal number");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}

			return true;

		} //End of Civilian Gazetted Officers

		if (categoryNameVal.indexOf('Civilian NonGazetted Personnel') > -1) {
			var minVal = 1; //Minimum value

			if (personalNo.length < 5) {
				alert("Please enter correct personal number");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}
			else if (parseInt(personalNo) < minVal) {

				alert("Please enter personal number in between valid range");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}

			return true;

		} //End of Civilian NonGazetted Personnel

	}// End of Civilian NonGazetted Personnel and Civilian Gazetted Officers
	else if (categoryNameVal.indexOf('Coast Guard Officers') > -1 || categoryNameVal.indexOf('Enrolled Personnel') > -1) {

		if (categoryNameVal.indexOf('Coast Guard Officers') > -1) {

			var minVal = 1; //Minimum value
			var maxVal = 5999; //Maximum value

			if (personalNo.length < 4 || personalNo.length > 4) {
				alert("Please enter correct personal number of 4 digit.");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}


			if ((parseInt(personalNo) < minVal) || (parseInt(personalNo) > maxVal)) {

				alert("Please enter personal number in between valid range");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;

			}

			if (coast_Enroll_Officer[coastGaurdIndex] != chkAlpha) {
				alert("Please enter correct check alpha number");
				$("#chkAlpha").value = "";
				$("#chkAlpha").focus();

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				return false;
			}

			return true;

		} //End of Enroll Officer or new name Coast Guard Officers


		if (categoryNameVal.indexOf('Enrolled Personnel') > -1) {

			var minVal = 1; //Minimum value
			var maxVal = 99999; //Maximum value

			if (personalNo.length < 5 || personalNo.length > 5) {
				alert("Please enter correct personal number of 5 digit.");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;
			}

			if ((parseInt(personalNo) < minVal) || (parseInt(personalNo) > maxVal)) {

				alert("Please enter personal number in between valid range");

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";

				$("#personalNo").focus();
				return false;

			}

			if (coast_Enroll_Sub_Officer[coastGaurdIndex] != chkAlpha) {
				alert("Please enter correct check alpha number");
				$("#chkAlpha").value = "";
				$("#chkAlpha").focus();

				if (validatePersonalNo != null)
					validatePersonalNo.value = "false";


				return false;
			}

			return true;

		} //End of Enroll Sub Officer
	}// End of Enrolled Personnel and Coast Guard Officers
	else {
		//For Others
		if (personalNo.length < 4) {
			alert("Personal number length can not be less then 4 digit.");

			if (validatePersonalNo != null)
				validatePersonalNo.value = "false";

			$("#personalNo").focus();
			return false;
		}

		return true;
	}

}


var http;
var respText;
var isChkforduplicateTraveller = false;
var isChkforCategoryId = false;
var isChkforRankId = false;
var isForOldPersonalNo = false;

function chkForDuplicateTraveller1(personalNo) {
	isChkforduplicateTraveller = true;
	isChkforRankId = false;
	isChkforCategoryId = false;
	isForOldPersonalNo = false;
	var url = $("#context_path").val() + "mb/checkPersonalNo?personalNo=" + personalNo;
	getHttpResponse1(url);
	return true;
}

//checking for old personal no based on DOB and Date of joinning

function chkForOldPersonalNo() {

	isChkforTraveller = false;
	isChkforRankId = false;
	isChkforCategoryId = false;
	isForOldPersonalNo = true;
	var fName = $("#fName").val();
	var lName = $("#lName").val();
	var dob = $("#dob").val();
	var dateOfCom_join = $("#dateOfCom_join").val();

	if (fName == '') {
		alert("Please fill the Employee's first name");
		$("#fName").focus();
		return false;
	}
	if (lName == '') {
		alert("Please fill the Employee's last name");
		$("#lName").focus();
		return false;
	}
	if (dob == '') {
		alert("Please fill the dob field");
		$("#dob").focus();
		return false;
	}
	if (dateOfCom_join == '') {
		alert("Please fill the Date of Commissioning/Enrollment field");
		$("#dateOfCom_join").focus();
		return false;
	}
	getRetirementDate();
	return true;
}

//End of check of old personal no

function getHttpResponse1(url) {
	if (window.XMLHttpRequest) {
		http = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		http = new ActiveXObject("Microsoft.XMLHTTP");
	}
	http.onreadystatechange = getResponse1;
	http.open("POST", url, true);
	http.onreadystatechange = getResponse1;
	http.send(null);
}

function getResponse1() {
	if (http.readyState == 4) {
		if (http.status == 200) {
			chkForTraveller1(http);
		}
	}
}

function getXML(respText) {
	if (window.ActiveXObject) {
		var doc = new ActiveXObject("Microsoft.XMLDOM");
		doc.async = "false";
		doc.loadXML(respText);
	} else {
		var parser = new DOMParser();
		var doc = parser.parseFromString(respText, "text/xml");
	}
	return doc;
}



function chkForTraveller1(http) {
	var respText = http.responseText;
	respText = respText.replace(/&/g, "and");

	if (respText.indexOf('Special') > -1) {

		alert(respText);
		$("#alphaNoId").value = "";
		$("#personalNo").value = "";
		$("#chkAlpha").value = "";
		$("#alphaNoId").focus();

	}
	else if (respText.indexOf('Reserve Words') > -1) {

		alert(respText);
		$("#alphaNoId").value = "";
		$("#personalNo").value = "";
		$("#chkAlpha").value = "";
		$("#alphaNoId").focus();

	}
	else if (respText.indexOf('Reserve Words') > -1) {

		alert(respText);
		$("#alphaNoId").value = "";
		$("#personalNo").value = "";
		$("#chkAlpha").value = "";
		$("#alphaNoId").focus();

	}
	else if (respText == 'Not Valid') {
		alert("Please Enter Valid Personal Number");
		$("#alphaNoId").value = "";
		$("#personalNo").value = "";
		$("#chkAlpha").value = "";
		$("#alphaNoId").focus();
	}
	else if (respText == 'false') {
		alert("Traveler already Created");
		$("#alphaNoId").val("");
		$("#personalNo").val("");
		$("#chkAlpha").val("");
		$("#alphaNoId").focus();
		if ($("#validatePersonalNo") != null)
			$("#validatePersonalNo").val("false");
		$("#alphaNoId").focus();

	}
	else if (respText == 'true') {
		if ($("#validatePersonalNo") != null)
			$("#validatePersonalNo").val("true");
		alert("Traveler Personal Number Is Valid.");

	}
}

function validateTravellerProfile(event) {


	var spouseCheck = false;
	var stepMotherCheck = false;
	var stepFatherCheck = false;
	var motherCheck = false;
	var fatherCheck = false;

	var pNo = $("#personalNo").val();
	var checkAlpha = $("#chkAlpha").val();
	
	var sericeName1 = $("#alternateService");
	var chkForService = $("#alternateService option:selected").text();

	var categoryName = $("#categoryId");
	var categoryNameVal = $("#categoryId option:selected").text();
	
	 if((chkForService.toUpperCase() =='ARMY'  )  && (categoryNameVal.toUpperCase() == 'TES CADET'))
         {
                  if(pNo.length < 4 || pNo.length > 5){
                            alert("Please enter correct personal number of 4 or 5 digit.");
                            document.saveTraveller.personalNo.focus();
                            return false;
            }
            }
	

	if (pNo != '' && checkAlpha != '') {
		if ($("#validatePersonalNo") != null) {
			if (($("#validatePersonalNo").val() == 'false') || ($("#validatePersonalNo").val() == '')) {
				alert("Please Click On Validate Personal Number Button");
				$("#chkAlpha").focus();
				return false;
			}

		}
	}

	var tbl = document.getElementById('tblGrid');
	var lastRow = tbl.rows.length;
	document.getElementById("flag").value = "false";
	//alert("lastRow-"+lastRow);
	var noOfFilyDtls = lastRow;
  	$("#lastRowIndex").val(noOfFilyDtls);

	var services = $("#alternateService").find(":selected").val();
	var serviceText = $("#alternateService").find(":selected").text();
	var category = $("#categoryId").find(":selected").val();
	var rank = $("#rankId").val();
	var level = $("#levelId").val();
	var retireAge = $("#retireAge").val();

	var alphaNo = $("#alphaNoId").val();
	var personalNo = $("#personalNo").val();
	var chkAlpha = $("#chkAlpha").val();
	var fName = trim($("#fName").val());
	var lName = trim($("#lName").val());
	var dob = trim($("#dob").val());
	var dateOfRetirement = trim($("#dateOfRetirement").val());
	var dateOfCom_join = trim($("#dateOfCom_join").val());
	var emailId = trim($("#email").val());
	var mobNo = trim($("#mobNo").val());
	
	var dutyStnNrs = trim($("#fillStnNrs1").val());
	var nrsHomeTown = trim($("#fillStnNrs2").val());
	var nrsReservationHomeTown = trim($("#fillStnNrs3").val());    
	var nearestRailwayStationSpr = trim($("#fillStnNrs4").val());  
	var nrsReservationSpr = trim($("#fillStnNrs5").val());
	 var nrsDutyStnAir = $("#suggestionsAirport1").text().trim();
	  var nrsHomeStnAir = $("#suggestionsAirport2").text().trim();
	
	
	var hmeTwnStnPlace = trim($('[name^=hmeTwnStnPlace]').val());
	var hmeTwnStnNrs = trim($('[name^=hmeTwnStnNrs]').val());
	var sprPlaceID = $('[name^=sprPlace]').val();
	var cv_fd_used = $("#cv_fd_used").val();
	var payAcOff = $("#payAcOff").val();
	var airAcOff = $("#airAcOff").val();
	var persnBlock = $("#persnBlock").val();
	var serviceId = $('#alternateService :selected').text().toUpperCase();

	var categoryId = $('#categoryId :selected').text().toUpperCase();
	var ersPrntName = trim($("#ersPrntName").val());
	var mapPersonalNoFlag = $("#mapPersonalNoFlag").val();


	var airForceCadetNo = $("#airForceCadetNo").val();
	var cadetNo = $("#cadetNo").val();
	var cadetChkAlpha = $("#cadetChkAlpha").val();
	var courseSerialNo = $("#courseSerialNo").val();



	if (services == '') {
		alert("Please select the service");
		return false;
	}
	if (category == '-1' || category == '') {
		alert("Please select the category");
		return false;
	}
	if (level == '-1' || level == '') {
		alert("Please select the level");
		return false;
	}
	if (rank == '-1' || rank == '') {
		alert("Unable to get your rank.");
		return false;
	}
	if (retireAge == '' || parseInt(retireAge) <= 0) {
		alert("Retirement date for selected Level is not assigned. Kindly contact DTS Helpline.");
		return false;
	}

	if (checkNavyAirforceCadets()) { //cadit
		if (checkNavyArchitectOffice()) {
			personalNo = cadetNo;
			chkAlpha = 'A';  //hard coded to bypass JS Check
		} else if (checkAirforceCadet()) {
			personalNo = airForceCadetNo;
			chkAlpha = 'A';  //hard coded to bypass JS Check
		} else {
			personalNo = cadetNo + cadetChkAlpha + courseSerialNo;
			chkAlpha = cadetChkAlpha;
			if (cadetNo == '' || cadetChkAlpha == '' || courseSerialNo == '') {
				personalNo = '';
			}
		}
	}

	if (serviceId.indexOf('ARMY') > -1 && categoryId.indexOf('OFFICER') > -1) {
		if (alphaNo == "") {
			alert("Please Enter Alpha Number")
			$("#alphaNoId").focus();
			return false;
		}
	}
	
	if((serviceId=='ARMY' || serviceId=='NAVY') && categoryId.indexOf('AGNIVEER') > -1){
		
		 if(personalNo.length!=7){
	   		 alert("Please enter correct personal number of 7 digit.");
	   		 document.saveTraveller.personalNo.focus();
	   		 return false;
	    }
	    
	    if(alphaNo==''){
	    	alert("Please select prefix.");
	   		 document.saveTraveller.personalNo.focus();
	   		 return false;
	    }
	}
	
	if(serviceId=='AIRFORCE' && categoryId.indexOf('AGNIVEER') > -1){
		
		 if(personalNo.length!=7){
	   		 alert("Please enter correct personal number of 7 digit.");
	   		 document.saveTraveller.personalNo.focus();
	   		 return false;
	    }
	    
	    
	}
	
	//alert(serviceId+"||"+serviceId.indexOf('COAST GUARD'));
	if (serviceId.indexOf('COAST GUARD') > -1) {

	} else {
		if (!checkSplCharIncludingSpace($("#alphaNoId"))) {
			return false;
		}
	}


	if (personalNo == '') {
		alert("Please fill the Personal No");
		return false;
	}

	if (serviceId.indexOf('COAST GUARD') > -1) {

	} else {
		if (serviceId.indexOf('ARMY') > -1 && (categoryId.indexOf(army_acc_cadets.toUpperCase()) > -1 || categoryId.indexOf(army_nda_cadets.toUpperCase()) > -1
			|| categoryId.indexOf(army_direct_entry_cadets.toUpperCase()) > -1 || categoryId.indexOf(army_tgc_cadets.toUpperCase()) > -1 ||
			categoryId.indexOf(army_ues_cadets.toUpperCase()) > -1 || categoryId.indexOf(army_acc_wing_cadets.toUpperCase()) > -1 ||
			categoryId.indexOf(army_afmc_cadets.toUpperCase()) > -1)) {

		} else {
			if (chkAlpha == '' && !categoryNameVal.toUpperCase() == 'TES CADET') {
				alert("Please fill the Check Alpha");
				return false;
			}
		}
	}

	if (payAcOff == '-1' || payAcOff == '') {
		alert("Please Select Rail Pay Accounting Office");
		$("#payAcOff").focus();
		return false;
	}

	if (airAcOff == '-1' || airAcOff == '') {
		alert("Please Select Air Pay Accounting Office");
		$("#airAcOff").focus();
		return false;
	}
	if (serviceId == 'ARMY' && (payAcOff != airAcOff)) {
		alert("Please Select same Rail Pay Accounting Office and Air Pay Accounting Office");
		$("#payAcOff").focus();
		return false;
	}

if(serviceId.indexOf('ARMY') > -1  && categoryId.indexOf('PBOR') > -1){
	if(persnBlock=='false'){
		alert("Personal No doesn't exists between specified block of Personal No. Please recheck either personal number or Pay Account Office");  
	}
	}

	if (fName == '') {
		alert("Please fill the First Name field");
		$("#fName").focus();
		return false;
	}
	
	if(lName == ''){
		alert("Please fill the Last Name field");
		$("#fName").focus();
		return false;
	}
	if (!checkSplChar($("#fName")))
		return false;


    if($('#mName').val() != ""){    /* Middle Name Validation Special-Char */
      if(!checkSplChar($("#mName")))
		return false;
    }

	if (!checkSplChar($("#lName")))
		return false;

	if (ersPrntName == "") {
		alert("Please fill the ERS Print Name");
		document.getElementById("ersPrntName").value = "";
		document.getElementById("ersPrntName").focus();
		return False;
	} else {
		if (ersPrntName.length > 15) {
			alert("ERS Print Name can't be greater than 15 letters");
			document.getElementById("ersPrntName").focus();
			return false;
		} if (!checkNoneChar(ersPrntName)) {
			document.getElementById("ersPrntName").focus();
			return false;
		}
	}

	if ((dob == '') || (dob == 'dd/mm/yyyy')) {
		alert("Please fill the date of birth");
		$("#dob").focus();
		return false;
	}
	if (!validateDob(dob, serviceId, categoryId))
		return false;

	if ((dateOfRetirement == '') || (dateOfRetirement == 'dd/mm/yyyy')) {
		alert("Please fill the date of retirement");
		$("#dateOfRetirement").focus();
		return false;
	}

	if (!validateDateOfRetirement(dateOfRetirement))
		return false;

	if ((dateOfCom_join == '') || (dateOfCom_join == "dd/mm/yyyy")) {
		alert("Please fill the date of joining field");
		$("#dateOfCom_join").focus();
		return false;
	}
	
//	if (!isGreaterThanTodayDate(document.getElementById("dateOfCom_join")))
//		return false;
		
		
	if (!validateDobAndDateOfComm(dob, dateOfCom_join, serviceId, categoryId))
		return false;

	if (!validateDateOfCommAndDateOfRetire(dateOfCom_join, dateOfRetirement))
		return false;



	if (document.getElementById("email").value != "") {
		if (!checkemail(document.getElementById("email").value)) {
			document.getElementById("email").value = "";
			document.getElementById("email").focus();
			return false;
		}
	}

	if (mobNo != '') {
		if (!isValidMobileNumber(mobNo)) {
			$("#mobNo").focus();
			return false;
		}
	}
	if(emailId == ''){
		alert("Please Enter Email Id");
			$("#email").focus();
			return false;
	}
	if(mobNo == ''){
		alert("Please Enter Mobile No.");
			$("#mobNo").focus();
			return false;
	}
	
	

	if (serviceId.indexOf('ARMY') > -1 && categoryId.indexOf('OFFICER') > -1) {

		var currentDatee = new Date();
		var month = currentDatee.getMonth() + 1
		var day = currentDatee.getDate()
		var year = currentDatee.getFullYear()
		var currentDate = day + "/" + month + "/" + year;
		var diffDays = days_between(dateOfCom_join, currentDate);

		if (diffDays >= 60) {

			if ($("#acNo").val() == '') {
				alert("Please fill the CDA(O) Account Number field");
				$("#acNo").focus();
				return false;
			}

			if (($("#validateCDAOANo").val() == 'false') || ($("#validateCDAOANo").val() == '')) {
				alert("Please Click On Validate Account Number Button");
				$("#acNo").focus();
				return false;
			}
		}

		if ($("#acNo").val() != '') {
			if (($("#validateCDAOANo").val() == 'false') || ($("#validateCDAOANo").val() == '')) {

				alert("Please Click On Validate Account Number Button");
				$("#acNo").focus();
				return false;
			}
		}
                 if ($("#acNo").val() == '') {
				alert("Please fill the CDA(O) Account Number field");
				$("#acNo").focus();
				return false;
			}
	}

	if (cv_fd_used == '') {

		alert("Please Enter Concession Voucher/Form D/Form G Used");
		$("#cv_fd_used").focus();
		return false;
	}
	if (!isNumberic(cv_fd_used)) {
		alert("Numbers are allowed for Concession Voucher/Form D Used Field");
		$("#cv_fd_used").focus();
		return false;
	}

	if (serviceId == 'ARMY' && categoryId == 'OFFICER') {
		if (cv_fd_used < 0 || cv_fd_used > 6) {

			alert("Please enter the number of Form D used this year, Maximum allowed value is 6.");
		$("#cv_fd_used").focus();
			return false;
		}
	}
	
	if (serviceId == 'MNS' && categoryId == 'OFFICER') {
		if (cv_fd_used < 0 || cv_fd_used > 6) {

			alert("Please enter the number of Form G used this year, Maximum allowed value is 6.");
		$("#cv_fd_used").focus();
			return false;
		}
	}
	var formHead = $("#isFormD").text().trim();
//	alert(formHead);
	if(formHead =='Form G already used this year *' || formHead == 'Form D already used this year *'){
	if (cv_fd_used < 0 || cv_fd_used > 6) {

			alert("Please enter the number of " +formHead+ ", Maximum allowed value is 6.");
		$("#cv_fd_used").focus();
			return false;
		}
		}
	
	if (dutyStnNrs == '' || dutyStnNrs =='Station Name Not Exist') {
		alert("Please Enter Nearest Duty Station")
		$("#fillStnNrs1").focus();
		return false;
	}

	if (nrsHomeTown == ''|| nrsHomeTown =='Station Name Not Exist') {
		alert("Nearest Railway Station (Home Town)")
		$("#fillStnNrs2").focus();
		return false;
	}
	
	if (nrsReservationHomeTown == ''|| nrsReservationHomeTown =='Station Name Not Exist') {
		alert("Nearest Railway Station for Reservation (Home Town)")
		$("#fillStnNrs3").focus();
		return false;
	}
	
	if(nrsDutyStnAir=='Airport Name Not Exist'){
		 alert("Please Enter Home Town Airport");
		 return false;
	}
	 if(nrsHomeStnAir=='Airport Name Not Exist'){
		 alert("Please Enter Home Town Airport");
		  return false;
	}


if(nearestRailwayStationSpr !='' || nearestRailwayStationSpr!=undefined || nearestRailwayStationSpr!=' '){
if(dutyStnNrs==nearestRailwayStationSpr){
	alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	 return false;
}
}
if(nrsReservationSpr !='' || nrsReservationSpr!=undefined || nrsReservationSpr!=' '){
if(dutyStnNrs==nrsReservationSpr){
	alert("Nearest Railway Station (Duty Station) And Nearest Railway Station (SPR) cannot be same");
	 return false;
}
}



	var firstmemberName; var lastmemberName; var memGender; var memdob; var memRelation; var memMaritalStatus;
	var doIIPartNo; var doIIDate; var memReson; var isDepen; var countChildDep = 0;

	var ersPrintName;

	var isFnameDuplicate = false;
	var isMnameDuplicate = false;
	var isLnameDuplicate = false;
	var isDobDuplicate = false;

	var childDOB=[];

	if (lastRow!=0) {

		for (var i = 1; i <= lastRow; i++) {
			for (var count = lastRow - 1; count > 0; count--) {
				if (document.getElementById("firstmemberName" + i).value == document.getElementById("firstmemberName" + count).value && count != i) {
					isFnameDuplicate = true;
				}
				if (document.getElementById("middlememberName" + i).value == document.getElementById("middlememberName" + count).value && count != i) {
					isMnameDuplicate = true;
				}
				if (document.getElementById("lastmemberName" + i).value == document.getElementById("lastmemberName" + count).value && count != i) {
					isLnameDuplicate = true;
				}
	
				if (document.getElementById("dob" + i).value == document.getElementById("dob" + count).value && count != i) {
					isDobDuplicate = true;
				}
	
			}
	
	
			isDepen = $("#isDepen" + i).val();
			firstmemberName = $("#firstmemberName" + i).val();
			lastmemberName = $("#lastmemberName" + i).val();
	
			if (firstmemberName == '') {
				alert("Please fill the Family member first name");
				document.getElementById("firstmemberName" + i).focus();
				return false;
			}
	
	
			if (!checkSplChar($("#firstmemberName" + i)))
				return false;
	
			if (!checkSplChar($("#lastmemberName" + i)))
				return false;
	
			memGender = $("#memGender" + i).val();
			if (memGender == 'Select') {
				alert("Please select the Gender");
				document.getElementById("memGender" + i).focus();
				return false;
			}
			memdob = $("#dob" + i).val();
			if (memdob == '') {
				alert("Please select the member's date of birth");
				document.getElementById("dob" + i).focus();
				return false;
			}
			if (!isGreaterThanTodayDate(document.getElementById("dob" + i)))
				return false;
	
			memRelation = document.getElementById("memRelation" + i).value;
			if (memRelation == '') {
				alert("Please select the Relation");
				document.getElementById("memRelation" + i).focus();
				return false;
			}
	
			if (memRelation == 10 && document.getElementById("fatherCheck").value == 'true' && isDepen == '') {
				alert("You have already entered Father Information");
				return false;
			}
	
			if (memRelation == 11 && document.getElementById("motherCheck").value == 'true' && isDepen == '') {
				alert("You have already entered Mother Information");
				return false;
			}
	
			memMaritalStatus = document.getElementById("memMaritalStatus" + i).value;
			if (memMaritalStatus == 'Select' || memMaritalStatus == '') {
				alert("Please select the Marital Status");
				document.getElementById("memMaritalStatus" + i).focus();
				return false;
			}
	
			doIIPartNo = document.getElementById("doIIPartNo" + i).value;
			doIIDate = document.getElementById("doIIDate" + i).value;
			memReson = document.getElementById("memReson" + i).value;
	
			ersPrintName = document.getElementById("ersPrintName" + i).value;
			if (ersPrintName == '') {
				alert("Please fill the ERS Print Name");
				return false;
			} else {
				if (ersPrintName.length > 15) {
					alert("ERS Name can't be greater than 14 letters");
					document.getElementById("ersPrintName" + i).focus();
					return false;
				}
				if (!checkNoneChar(ersPrintName)) {
					alert("ERS Name should be between a-z Or A-Z");
					document.getElementById("ersPrintName" + i).focus();
					return false;
				}
			}
	
	        if(doIIPartNo != ""){
				if(!validateDoIIPartNo(doIIPartNo)){
					alert("Special characters !#$%^*' not allowed"); 
					document.getElementById("doIIPartNo" + i).focus();
					return false;
				}
			}
	
			if ((doIIPartNo == "" || doIIDate == "") && memReson == "") {
	//			 alert("in check aplha add row "+serviceText);
				if(serviceText=="Navy"){
					alert("Enter either ( GX NO & GX Date ) OR Reason . Do not fill all three fields.");
				}else if(serviceText=="AirForce"){
					alert("Enter either ( POR No & POR Date ) OR Reason . Do not fill all three fields.");
				}else{
				alert("Either enter ( DOII Part No & DOII Date ) OR Reason . Do not fill all three fields.");
				}
				$("#memReson" + i).removeAttr('disabled');
				return false;
			}
	
	
		var do2date = doIIDate.split("/");
  	    var dateformatDate2 = new Date(do2date[2]+","+(do2date[1])+","+do2date[0]);
  	    if(dateformatDate2>=new Date()){
	           
	            if(serviceText=="Navy"){
					alert("GX Date should be equal to or  less than today's  date");
				}else if(serviceText=="AirForce"){
					alert("POR Date should be equal to or  less than today's  date");
				}else{	  
		alert("DOII Date should be equal to or  less than today's  date");
		        }
		        
  		$("#memReson"+k1).removeAttr('disabled');
  		return false;
  		}
  		
  	var docdate=dateOfCom_join.split("/");
  var docDateFormat=new Date(docdate[2]+","+(docdate[1])+","+docdate[0]);
  if(docDateFormat>dateformatDate2){
	 
	   if(services=="Navy"){
					alert("GX Date should be greater than Date of Enrollment date");
				}else if(serviceText=="AirForce"){
					alert("POR Date should be greater than Date of Enrollment date");
				}else{	  
	  alert("DOII Date should be greater than Date of Enrollment date");
		        }
  		$("#memReson"+k1).removeAttr('disabled');
  		return false;
  }
			
            var dateOfBirth =trim($("#dob").val());
	  		if(new Date(doIIDate).getTime()<=new Date(dateOfBirth).getTime()){
				  
				     if(serviceText=="Navy"){
					alert("GX Date should be greater than Traveler Date Of Birth");
				}else if(serviceText=="AirForce"){
					alert("POR Date should be greater than Traveler Date Of Birth");
				}else{	  
	             alert("DOII Date should be greater than Traveler Date Of Birth");
		           }
				  
		  		
		  		 return false;
	  		}
			
			if (doIIDate != null && doIIDate.length > 0) {
				var dateJoin = document.getElementById("dateOfCom_join").value;
	
				var dateOfRetirement = document.getElementById("dateOfRetirement").value;
				if (new Date(doIIDate).getTime() > new Date(dateOfRetirement).getTime()) {
					alert("DOII Date Should Be Less Than Traveler Date Of Retirement");
					return false;
				}
	
			}
	
			if (memRelation == 4 || memRelation == 5 || memRelation == 3 || memRelation == 2) {
				var dob = document.getElementById("dob").value;
				if (new Date(memdob).getTime() <= new Date(dob).getTime()) {
					alert("Child date of birth should be less than Traveler date of birth");
					return false;
				}
			}
	
			if (memRelation == 8 || memRelation == 9 || memRelation == 10 || memRelation == 11) {
				var dob = document.getElementById("dob").value;
				if (new Date(memdob).getTime() > new Date(dob).getTime()) {
					alert("Father/Mother date of birth should be greater than Traveler date of birth");
					return false;
				}
			}
	
	
	
			var question = "";
	
			if (isDepen == '') {
	
				if ((memRelation == 4 || memRelation == 5) && memMaritalStatus == 0) {
	
					$("input[name^=incomeRange1]").removeAttr("checked");
					$("input[name^=isSept1]").removeAttr("checked");
					$("input[name^=isDep1]").removeAttr("checked");
					$("input[name^=isDepAsTR1]").removeAttr("checked");
					$('#isSeprt').hide();
					$('#whlyDep1').hide();
					$('#DepPerTR1').hide();
					$('#depQues').show();
					$('#incmRang1').show();
					$('#daughMarried').show();
					$('#famIndex').val(i);
					return false;
				}
	
				else if (memRelation == 7 && memMaritalStatus == 1) {
					$("input[name^=isParAliv3]").removeAttr("checked");
					$("input[name^=isUnm3]").removeAttr("checked");
					$("input[name^=isPartDep3]").removeAttr("checked");
					$("input[name^=isResPer3]").removeAttr("checked");
					$("input[name^=incomeRange3]").removeAttr("checked");
					$('#isUnmarr3').hide();
					$('#isParAlv3').hide();
					$('#isParDep3').hide();
					$('#isResPers3').hide();
					$('#incmRange3').show();
					$('#sister').show();
					$('#depQues').show();
					$('#famIndex').val(i);
					return false;
				}
	
				else if (memRelation == 6 && memMaritalStatus == 1) {
					$("input[name^=incomeRange2]").removeAttr("checked");
					$("input[name^=isParAlive2]").removeAttr("checked");
					$("input[name^=isParsDep2]").removeAttr("checked");
					$("input[name^=isResPer2]").removeAttr("checked");
					$("input[name^=isDepAsTR2]").removeAttr("checked");
	
					$('#isparAlv2').hide();
					$('#isParDep2').hide();
					$('#isResPers2').hide();
					$('#DepPerTR2').hide();
	
					$('#incmrange2').show();
					$('#broUnmarried').show();
					$('#depQues').show();
					$('#famIndex').val(i);
					return false;
				}
	
				else if (memRelation == 6 && memMaritalStatus == 0) {
					closeWindow("", "no")
					return false;
				}
				else if (memRelation == 8 || memRelation == 9 || memRelation == 10 || memRelation == 11) {
					$("input[name^=incomeRange4]").removeAttr("checked");
					$("input[name^=isResPer4]").removeAttr("checked");
					$("input[name^=depAsPerTR4]").removeAttr("checked");
					$('#isResPers4').hide();
					$('#depPerTR4').hide();
	
					$('#incmRange4').show();
					$('#parStepPar').show();
					$('#depQues').show();
					$('#famIndex').val(i);
					return false;
				}
				else if ((memRelation == 2 || memRelation == 3) && memMaritalStatus == 0) {
					closeWindow("", "no")
					return false;
				}
	
				else if (memRelation !== '1') {
	
					$("input[name^=incomeRange6]").removeAttr("checked");
					$("input[name^=depAsPerTR6]").removeAttr("checked");
					$('#depPerTR6').hide();
					$('#incmRange6').show();
					$('#depQues').show();
					$('#famIndex').val(i);
					return false;
				}
			}
	
			if((memRelation==2 || memRelation==3 || memRelation==4 ||memRelation==5) && $.inArray(memdob,childDOB)=== -1){
				childDOB.push(memdob);
			}
			
			if(childDOB.length > 2){
				alert("Children relation should not be more than 2.");
				return false;
			}
	
			if (memRelation == 1 && spouseCheck == true) {
				alert("Spouse relation should be inserted only once");
				return false;
			}
	
			if (memRelation == 10 && fatherCheck == true) {
				alert("Father relation should be inserted only once");
				return false;
			}
	
			if (memRelation == 11 && motherCheck == true) {
				alert("Mother relation should be inserted only once");
				return false;
			}
	
			if (memRelation == 8 && stepMotherCheck == true) {
				alert("Step Mother relation should be inserted only once");
				return false;
			}
	
			if (memRelation == 9 && stepFatherCheck == true) {
				alert("Step Father relation should be inserted only once");
				return false;
			}
	
			if (memRelation == 1)
				spouseCheck = true;
	
			if (memRelation == 10)
				fatherCheck = true;
	
			if (memRelation == 11)
				motherCheck = true;
	
			if (memRelation == 8)
				stepMotherCheck = false;
	
			if (memRelation == 9)
				stepFatherCheck = false;
	
		}
	}



	if (isFnameDuplicate && isMnameDuplicate && isLnameDuplicate && isDobDuplicate) {
		alert("Dependent details are duplicate");
		return false;
	}


	event.currentTarget.submit();


}



function isValidPhoneNumber(name) {
	var iChars = "-0123456789";

	if (name != "") {
		if (name.length < 5)
			return false;

		for (var i = 0; i < name.length; i++) {

			if (iChars.indexOf(name.charAt(i)) == -1) {
				return false;
			}
		}

	}
	return true;

}

function validateDoIIPartNo(doIIPartNo){
	var filter="!#$%^*'";
	if (doIIPartNo != "") {
		for (var i = 0; i < doIIPartNo.length; i++) {
			if (filter.indexOf(doIIPartNo.charAt(i)) > -1) {
				return false;
			}
		}
	}
	return true;
}

function checkemail(str) {

	var filter = /^.+@.+\..{2,3}$/
	if (filter.test(str))
		return true
	else {
		alert("Please enter a valid email address")
		return false
	}
}

function isValidMobileNumber(name) {

	var mob = /^[1-9]{1}[0-9]{9}$/;

	if (mob.test(name) == false) {
		alert("Please enter valid mobile number.");
		return false;
	}
	return true;
}


function isValidStdCode(name) {

	var iChars = "0123456789";
	if (name != "") {
		if (name.length < 3)
			return false;
		for (var i = 0; i < name.length; i++) {
			if (iChars.indexOf(name.charAt(i)) == -1) {
				return false;
			}
		}
	}
	return true;
}

function isNumberic(name) {

	var iChars = "0123456789";

	if (name != "") {

		for (var i = 0; i < name.length; i++) {
			if (iChars.indexOf(name.charAt(i)) == -1) {
				return false;
			}

		}
	}
	return true;

}

function checkNoneChar(value) {
	var flag = true;
	var length = value.length;
	for (var j = 0; j < length; j++) {
		var ch = value.substring(j, j + 1);
		if (((ch < "a" || "z" < ch) && (ch < "A" || "Z" < ch) && (ch != " ") && (ch != "(") && (ch != ")"))) {
			if ((ch < "0" || "9" < ch || ch != "_" || ch != "/" || ch != "*" || ch != "+" || ch != "-" || ch != "%" || ch != "^" || ch != "#" || ch != "@" || ch != "!" || ch != "$" || ch != "<" || ch != ">" || ch != ":")) {
				alert(" Please use only Characters between \n a to z or A to Z , Numbers and special Characters are not accepted.");
				flag = false;
				break;
			}
		}
	}
	return flag;
}

function trim(n) {
	return n.replace(/^\s+|\s+$/g, '');
}

function checkForAFCadet() {
	var flag = true;
	var airForceCadetNo = $("#airForceCadetNo").val();
	if (null != airForceCadetNo && airForceCadetNo.length == 7) {
		var numPart = airForceCadetNo.substring(0, 6);
		var alphaPart = airForceCadetNo.substring(6, 7);
		if (null == numPart || numPart.trim() == "" || !isNumberic(numPart.trim())) {
			flag = false;
		}
		if (null == alphaPart || alphaPart.trim() == "" || !/^[a-zA-Z]*$/g.test(alphaPart.trim())) {
			flag = false;
		}

	} else {
		flag = false;
	}

	if (!flag) {
		alert("Please enter valid Personal Number.");
		$("#airForceCadetNo").val("");
		$("#airForceCadetNo").focus();
		validatePersonalNo.value = "false";
		return false;
	} else {
		return true;
	}
}

function checkForNavyCadet() {
	var cadetNo = $("#cadetNo").val();
	var cadetChkAlpha = $("#cadetChkAlpha").val();
	var courseSerialNo = $("#courseSerialNo").val();
	//var isNavyArc=checkNavyArchitectOffice();
	var zero = cadetNo.charAt(0);

	if (zero != 0 || null == cadetNo || cadetNo.trim().length != 5 || !isNumberic(cadetNo.trim())) {
		alert("Please enter valid Personal Number.");
		$("#cadetNo").val("");
		$("#cadetNo").focus();
		validatePersonalNo.value = "false";
		return false;

	} else if (null == cadetNo || cadetNo.trim().length != 5 || !isNumberic(cadetNo.trim()) || null == cadetChkAlpha || cadetChkAlpha.trim().length != 1 ||
		!/^[a-zA-Z]*$/g.test(cadetChkAlpha.trim()) || null == courseSerialNo || courseSerialNo.trim() == "") {
		alert("Please enter valid Personal Number.");
		$("#cadetNo").val("");
		$("#cadetChkAlpha").val("");
		$("#courseSerialNo").val("");
		$("#cadetNo").focus();
		validatePersonalNo.value = "false";
		return false;
	} else {
		return true;
	}


}


function chkForDSC1(personalNo, chkAlpha, categoryNameVal) {

	var personalNolength = personalNo.length;
	var startIndex = personalNolength + 1;
	var sum = 0;
	var armyArrayIndex;
	for (var i = 0; i < personalNo.length; i++) {
		sum += personalNo.charAt(i) * startIndex;
		startIndex--;
	}
	armyArrayIndex = sum % 11;

	if (dsc_array[armyArrayIndex] != chkAlpha) {
		alert("Please enter correct check alpha number");
		$("#chkAlpha").value = "";
		$("#chkAlpha").focus();

		if (validatePersonalNo != null)
			validatePersonalNo.value = "false";

		//$("#personalNo").focus();
		return false;
	}

	return true;
}
function validateAccountNo(actionType) {
	var oldAccountNo = "";
	var accountNo = document.getElementById("acNo").value;

	var flag = true;
	if (actionType == "edit") {
		oldAccountNo = document.getElementById("oldAcNo").value;
		if (accountNo == oldAccountNo) {
			flag = false;
			alert("CDA(O) Account Number Is Valid.");
			document.getElementById("validateCDAOANo").value = "true";
			document.getElementById("acNo").setAttribute("readonly", true);
			document.getElementById("acNo").style.backgroundColor = "#eeeedd";
			return true;
		}
	}

	if (flag) {
		var accountNolength = accountNo.length - 1;
		var chkAlpha = accountNo.charAt(accountNolength);
		var startIndex = accountNo.length;
		var sum = 0;
		var accountArrayIndex;

		for (var i = 0; i < accountNolength; i++) {
			sum += accountNo.charAt(i) * startIndex;
			startIndex--;
		}
		accountArrayIndex = sum % 11;

		if (cdaoAcc_array[accountArrayIndex] != chkAlpha) {
			alert("Please enter correct CDA(O) account number");
			document.getElementById("acNo").value = "";
			document.getElementById("acNo").focus();
			return false;
		}

		checkDuplicateAccountNo(accountNo);
	}
	return true;

}

function checkDuplicateAccountNo(cdaoAccountNo) {
	$.ajax(
		{
			url: $("#context_path").val() + "mb/checkDuplicateAccountNo",
			type: "post",
			data: "cdaoAccountNo=" + cdaoAccountNo,
			dataType: "text",
			async: false,

			success: function(msg) {
				if ("NOT" != msg) {
					alert("CDA(O) Account Number entered already exist.\nPlease contact DTS at helpdesk@pcdatravel.gov.in or 011-26700300.");
					document.getElementById("acNo").value = "";
					document.getElementById("validateCDAOANo").value = "";
					document.getElementById("acNo").focus();
					return false;
				}
				else {
					alert("CDA(O) Account Number Is Valid.");
					document.getElementById("validateCDAOANo").value = "true";
					document.getElementById("acNo").setAttribute("readonly", true);
					document.getElementById("acNo").style.backgroundColor = "#eeeedd";
					return true;
				}
			}

		});

}



function days_between(date1, date2) {
	var x = date1.split("/");
	var y = date2.split("/");
	var one_day = 1000 * 60 * 60 * 24;
	var date1 = new Date(x[2], (x[1] - 1), (x[0]));
	var date2 = new Date(y[2], (y[1] - 1), (y[0]))
	var month1 = x[1] - 1;
	var month2 = y[1] - 1;
	var Diff = Math.ceil((date2.getTime() - date1.getTime()) / (one_day));
	return (Diff);
} 
