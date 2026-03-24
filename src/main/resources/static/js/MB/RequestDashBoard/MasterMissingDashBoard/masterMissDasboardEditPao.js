
$(document).ready(function() {


});
function doAction(personalNo) {
	
	$("#prnPao").val(personalNo);
	
	$("#saveEditPao").submit();


}




function setPaoOffice() {

//	var categoryId = $('#categoryId').val();
//	var categoryName = $("#categoryName").val();

//	var serviceId = $("#serviceId").val();
//	var serviceName = $("#serviceName").val();

//	var alternateServiceId = $("#alternateService").val();
//	var alternateServiceName = $("#alternateServiceName").val();

//	if (alternateServiceName != "select") {
//		serviceId = alternateServiceId;
//		//serviceName = alternateServiceName.toUpperCase();
//	}

     alert("hello");
     alert("categoryId");
	var paoOffId = $('#payAcOff');
	var airOffId = $('#airAcOff');

	var rail_options = ""; var air_options = "";


//	var tempChkForCategory = categoryName.toUpperCase();

	//alert("ServiceId="+serviceId+"||serviceName="+serviceName+"alternateServiceId="+alternateServiceId+"||alternateServiceName="+alternateServiceName+"categoryId="+categoryId+"||categoryName="+categoryName+"|tempChkForCategory-"+tempChkForCategory); 

//	if (serviceName.indexOf('AIRFORCE') > -1 || serviceName.indexOf('AIR FORCE') > -1) {
//		$("#DoIINo").html('POR NO');
//		$("#DoIIDate").html('POR Date');
//	}
//	if (serviceName.indexOf('NAVY') > -1) {
//		$("#DoIINo").html('GX NO');
//		$("#DoIIDate").html('GX Date');
//	}
//
//	if (tempChkForCategory == 'OFFICER' || tempChkForCategory == 'NCC WTLO') {
//		$("#isDateOfCommission").html('Date of Commissioning<span class="mandatory">*</span>');
//
//		if (serviceName.indexOf('MNS') > -1) {
//			$("#isFormD").html('Form G Used<span class="mandatory">*</span>');
//		} else {
//			$("#isFormD").html('Form D Used<span class="mandatory">*</span>');
//		}
//
//
//		if (serviceName.indexOf('ARMY') > -1 || serviceName.indexOf('MNS') > -1) {
//			$("#cdaAccNo").show();
//		}
//		else {
//			$("#cdaAccNo").hide();
//		}
//	} else {
//		$("#isDateOfCommission").html('Date of Enrolment<span class="mandatory">*</span>');
//		$("#isFormD").html('Concession Voucher Used<span class="mandatory">*</span>');
//		$("#cdaAccNo").hide();
//	}

	//Check for Airforce	 
	$(paoOffId).find('option').not(':first').remove();
	$(airOffId).find('option').not(':first').remove();
	var data = $("#officesList").val();
alert("11111111111");
//	if (categoryId != "") {
//		console.log("hello parvess");
//		$.ajax({
//			url: "/mb/getPAO1",
//			type: "POST",
//			data: "serviceId=" + serviceId + "&categoryId=" + categoryId,
//			//	      	dataType: "text", 
//			success: function(data) {
				var obj = JSON.parse(JSON.stringify(data));

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
//			}//END SUCCESS
//		});
//	}

}


