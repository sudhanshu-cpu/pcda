$(document).ready(function() {


	$('#categoryId').on('change', function() {
		getLevel($(this));
		setPaoOffice();
	});



});



function getLevel(obj) {
	let serviceId = $("#serviceId").val();
	let categoryId = $(obj).val();

	$('#levelId').find('option').not(':first').remove();

	if (categoryId != "") {
			$.ajax({
			type: "post",
			url: $("#context_path").val()+"mb/getChangeServiceLevel",
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
	let strVal = $(obj).find(":selected").val();
	let rankId = strVal.split(":")[1];
	$("#rankId").val(rankId);
	$("#level").val(strVal.split(":")[0]);

	$('#rank').empty();

	$.ajax({
		type: "post",
		url: $("#context_path").val()+"mb/getServiceGradePayRank",
		data: "rankId=" + rankId,
		datatype: "application/json",
		success: function(data) {
		
			$('#rank').append(data);
		}
	});
}

function setPaoOffice(){

   	let serviceId = $("#serviceId").val();
	let categoryId = $("#categoryId").val();
	 var rail_options="";var air_options="";
	
	if (categoryId != "") {
			$.ajax({
	      	url: $("#context_path").val()+"mb/getPAOChangeService",
		    type: "POST",
	      	data: "serviceId="+serviceId+"&categoryId="+categoryId,
	      	dataType:  "text",
  		  	success: function(data){
  		  		var obj = JSON.parse(data);

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

  				if(railCount != 1){
					rail_options += '<option value="-1">select<\/option>';
  				}

 				$(RailVisitorGroup).each(function(index) {
					var id = RailVisitorGroup[index].acuntoficeId;
					var name = RailVisitorGroup[index].name;

					rail_options += '<option value="' + id + '">' +name + '<\/option>';
		 		});

		 		if(airCount != 1){
		 			air_options += '<option value="-1">select<\/option>';
		 		}

		 		$(AirVisitorGroup).each(function(index){
					var id = AirVisitorGroup[index].acuntoficeId;
					var name = AirVisitorGroup[index].name;

						air_options += '<option value="' + id + '">' +name + '<\/option>';
		 		});

		 		$("#payAcOff").html(rail_options);
		  		$("#airAcOff").html(air_options);
			}//END SUCCESS
	    });
	}

}
    






function changeService(formName, event)
{

	var reason=$("#reason").val();
	var payAcOff=$("#payAcOff").val();
	var rankId=$("#rankId").val(); 
	var category=$("#categoryId").val();
	var airAcOff=$("#airAcOff").val();
	var level=$("#levelId").val();

	if(category=="" || category=="-1")	
	{
		alert("Please Select Category Name");
		return false;
	}
	
	if(level=="" || level=="-1")	
	{
		alert("Please select the Level");
		return false;
	}
	
	if(rankId=="" || rankId=="-1")	
	{
		alert("Grade Pay is not mapped with selected level.");
		return false;
	}
	if(payAcOff=="" || payAcOff=="-1")
	{
		alert("Please Select Rail Pay Accounting Office");
		return false;
	}
	if(airAcOff=="" || airAcOff=="-1")
	{
		alert("Please Select Air Pay Accounting Office");
		return false;
	}	
	if(reason=="")
	{
		alert("Please Enter Reason");
		return false;
	}

	if (confirm("Are You Really want to Change Personal Number Service")){
	}else{
		window.location.reload();
		return false;
	}
	

   
  $("#changeServiceForm").submit();	
    
}
