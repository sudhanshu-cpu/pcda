
$(document).ready(function() {


});
function doAction(personalNo) {
	
	$("#prnPao").val(personalNo);
	
	$("#saveEditPao").submit();


}


function saveProfile(){
	
	var payAcOff=$("#payAcOff").val();
	var airAcOff=$("#airAcOff").val();
	
	if(payAcOff=='-1' || airAcOff=='-1'){
		alert("Please Select Pay Account Office");
		return false;
	}
	if(payAcOff!='-1' || airAcOff!='-1'){
		
		$("#profileApproval").submit();
	}
	
}


function setPaoOffice() {

	var paoOffId = $('#payAcOff');
	var airOffId = $('#airAcOff');

	var rail_options = ""; var air_options = "";

	 
	$(paoOffId).find('option').not(':first').remove();
	$(airOffId).find('option').not(':first').remove();
	var data = $("#officesList").val();

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

}


