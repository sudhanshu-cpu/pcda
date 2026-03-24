$(document).ready(function() {

	$("#dob").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollInput:false,
		scrollMonth: false,
		yearEnd: 2100,
		onShow: function () {
			  $("#dob").val("");
		}

	});	
	$("#dateOfCom_join").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd: 2100,
		onShow: function () {
			  $("#dateOfCom_join").val("");
		}

	});

	$('#categoryId').on('change', function() {
		getLevel($(this));
		
		var categoryNameVal = categoryId.options[categoryId.selectedIndex].text;
	
		if(categoryNameVal=='Civilian NonGazetted Personnel' ||categoryNameVal=='Civilian Gazetted Officers'){
			$("#chkAlphaCNGP").hide();
		}
		else{
			$("#chkAlphaCNGP").show();
		}
		
		if(categoryNameVal=='Civilian Gazetted Officers'){
			$("#chkAlphaCGP").hide();
		}
		else{
			$("#chkAlphaCGP").show();
		}
		
	});

	chkForGovtAccom('NO');
	
	
	 $("#mobNo").keyup(function() {
	  
        var mob = this.value;
      
        
        var iChars = "0123456789";
	   if (mob != "") {

		for (var i = 0; i < mob.length; i++) {
			if (iChars.indexOf(mob.charAt(i)) == -1) {
				alert("Please Enter Digits Only");
				this.value="";
				return false;
			}

		}
	}
    });

	$("#savecivillianTravelerForm").submit(function(event) {
		event.preventDefault();
		validateTravellerProfile(event);
	});

});



//function resetAccNumber(){
//	$("#reAccountNumber").val("");
//	$("#isACNumberMatch").html("");
//}

//function resetIFSCCode(){
//	$("#reIFSCCode").val("");
//	$("#isIFSCCodeMatch").html("");
//}

//function matchIFSCCode(){
//	var text="";
//	if($("#ifscCode").val()==$("#reIFSCCode").val()){ 
//	   text="Match";
//	}else{
//	   text="Not Match";
//	}
//	$("#isIFSCCodeMatch").html(text);
//}

//function matchAccNumber(){
//	var text="";
//	if($("#accountNumber").val()==$("#reAccountNumber").val()){
//		text="Match";
//	}else{
//		text="Not Match";
//	}
//	$("#isACNumberMatch").html(text);
//}



function getLevel(obj) {
	let serviceId = $("#loginVisitorServiceId").val();
	let categoryId = $(obj).val();

	$("#rankId").val("");
	$('#rank').empty();
	$("#level").val("");
	$('#retireAge').val("");

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

	if (serviceId == "100015" && categoryId != "") {
		$('#alphaNoId').find('option').not(':first').remove();
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getTravelerPersonalNoPrefix",
			data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
			datatype: "application/json",
	
			success: function(data) {
	
				$.each(data, function(key, value) {
					$('#alphaNoId').append(new Option(value, value));
				});

			}
		});
	}

}

function setRankLevel(obj) {
	if ($(obj).val() != "-1") {
		let serviceId = $("#loginVisitorServiceId").val();


		let strVal = $(obj).find(":selected").val();

		let rankId = strVal.split(":")[1];
		$("#rankId").val(rankId);

		let levelId = strVal.split(":")[0];
		$("#level").val(levelId);

		$('#rank').empty();

		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getTravelerGradePayRank",
			data: "rankId=" + rankId,
			datatype: "application/json",
			success: function(data) {
				$('#rank').append(data);
			}
		});

		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/getRetirementAge",
			data: "serviceId=" + serviceId + "&levelId=" + levelId+"&rankId="+rankId,
			datatype: "application/json",
			success: function(data) {
				$('#retireAge').val(data);
			}
		});
	} else {
		$("#rankId").val("");
		$('#rank').empty();
		$("#level").val("");
		$('#retireAge').val("");
	}
}
