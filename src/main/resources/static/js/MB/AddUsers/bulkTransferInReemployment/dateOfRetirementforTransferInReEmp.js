
function getRetirementDateforTansfer(userId) {


	var now = new Date();
	var currentdate = (now.getDate() < 10 ? "0" + now.getDate().toString() : now.getDate().toString()) + "-" +
		(now.getMonth() < 10 ? "0" + (now.getMonth() + 1).toString() : now.getMonth().toString()) + "-" + now.getFullYear().toString();

	var cdate = currentdate.split("-");
	var currYr = cdate[2];
	currYr = parseInt(currYr) + 0;

	var category = $("#categoryId").val();

	var retire_age;
	if (category == '100004') {
		retire_age = $("#retireAgePbor").val();
	}
	else {
		retire_age = $("#retireAgeOther").val();
	}


	if ($("#dob_" + userId).val() != "") {
		var dob = $("#dob_" + userId).val().split("/");
		var year = dob[2];
		year = parseInt(year);
		if (year >= currYr) {
			alert("Please select valid date of birth");
			$("#dob_" + userId).focus();
			return false;
		} else {
			if ((currYr - year) > retire_age || (currYr - year) < 16) {
				alert("It should not be less than 16 and not more than " + retire_age + " years");
				$("#dob_" + userId).focus();
				return false;
			}
		}
	}


	if ($("#dob_" + userId).val() != "") {

		if (retire_age != "") {

			var age = retire_age;
			var year = dob[2];
			var retireYear = parseInt(age) + parseInt(year);
			var birthMonth;

			if (dob[1] == '09')
				birthMonth = 9;
			else if (dob[1] == '08')
				birthMonth = 8;
			else if (dob[1] == '07')
				birthMonth = 7;
			else if (dob[1] == '06')
				birthMonth = 6;
			else if (dob[1] == '05')
				birthMonth = 5;
			else if (dob[1] == '04')
				birthMonth = 4;
			else if (dob[1] == '03')
				birthMonth = 3;
			else if (dob[1] == '02')
				birthMonth = 2;
			else if (dob[1] == '01')
				birthMonth = 1;
			else if (dob[1] != '09' && dob[1] != '08' && dob[1] != '07' && dob[1] != '06' && dob[1] != '05' && dob[1] != '04' && dob[1] != '03' && dob[1] != '02' && dob[1] != '01')
				birthMonth = parseInt(dob[1]);

			var birthDate;

			if (dob[0] == '09')
				birthDate = 9;
			else if (dob[0] == '08')
				birthDate = 8;
			else if (dob[0] == '07')
				birthDate = 7;
			else if (dob[0] == '06')
				birthDate = 6;
			else if (dob[0] == '05')
				birthDate = 5;
			else if (dob[0] == '04')
				birthDate = 4;
			else if (dob[0] == '03')
				birthDate = 3;
			else if (dob[0] == '02')
				birthDate = 2;
			else if (dob[0] == '01')
				birthDate = 1;
			else if (dob[0] != '09' && dob[0] != '08' && dob[0] != '07' && dob[0] != '06' && dob[0] != '05' && dob[0] != '04' && dob[0] != '03' && dob[0] != '02' && dob[0] != '01')
				birthDate = parseInt(dob[0]);

			if (birthMonth == 1 && birthDate == 1) {
				retireYear = retireYear - 1;
				birthMonth = 12;
				birthDate = 31;
			}

			if (birthMonth != 1 && birthDate == 1) {
				if (birthMonth == 5 || birthMonth == 7 || birthMonth == 10 || birthMonth == 12)
					birthDate = 30;
				if (birthMonth == 3) {
					if (retireYear % 4 == 0)
						birthDate = 29;
					else
						birthDate = 28;
				}

				if (birthMonth == 2 || birthMonth == 4 || birthMonth == 8 || birthMonth == 6 || birthMonth == 9 || birthMonth == 11)
					birthDate = 31;

				birthMonth = birthMonth - 1;
			}


			if (birthDate != 1) {
				if (birthMonth == 1 || birthMonth == 3 || birthMonth == 5 || birthMonth == 7 || birthMonth == 8 || birthMonth == 10 || birthMonth == 12)
					birthDate = 31;

				if (birthMonth == 2) {
					if (retireYear % 4 == 0)
						birthDate = 29;
					else
						birthDate = 28;
				}

				if (birthMonth == 4 || birthMonth == 6 || birthMonth == 9 || birthMonth == 11)
					birthDate = 30;
			}

			var reteirementDate = birthDate + '-' + birthMonth + '-' + retireYear;

			$("#dateOfRetirement_" + userId).val(reteirementDate);
		}
	}
}

