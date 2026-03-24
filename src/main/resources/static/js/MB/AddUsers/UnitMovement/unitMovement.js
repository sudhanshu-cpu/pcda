$(document).ready(function() {


$("#unitRelocationDate").datetimepicker({
          lang:'en',
                timepicker:false,
                format:'d-m-Y',
                formatDate:'d-m-Y',
                scrollMonth : false,
                scrollInput:false,
                maxDate:0,
                yearEnd:2100,
		onShow: function () {
			  $("#unitRelocationDate").val("");
		}                    
			});


//  For Select All CheckBox
	$("#sla").click(function() {
		if ($(this).is(':checked')) {
			$("[id='users']").each(function() {
				$(this).prop('checked', true);
			});
		} else {
			$("[id='users']").each(function() {
				$(this).prop('checked', false);
			});
		}
	});

// After Unchecked one element, unchecked Select All  
 $("[id='users']").on('click',function(){
//	 console.log($("[id='users']:checked"));
        if($("[id='users']:checked").length == $("[id='users']").length){
            $("#sla").prop('checked',true);
        }else{
            $("#sla").prop('checked',false);
        }
    });
});



//--------- for Unit Movement Authority ---------
function ismaxlength(obj, mlength) {
	if (obj.value.length > mlength)
		obj.value = obj.value.substring(0, mlength);
}

function getCategories(obj) {
	$('#categoryId').find('option').not(':first').remove();
	
	let serviceId = $(obj).val();

	if (serviceId != "") {
		$.ajax({
			type: "post",
			url: $("#context_path").val() + "mb/categoryBasedOnService",
			data: "serviceId=" + serviceId,
			datatype: "application/json",
			success: function(data) {
	
				$.each(data, function(key, value) {
					$('#categoryId').append(new Option(value, key));
				});
	
			}
		});
	}
}

 function validateUnitMovement() 
 {
	var unitRelocationDate=$("#unitRelocationDate").val();
	var unitRailDutyNrs=$("#dutyStations_0").val();
	var unitDutyStnNa=$("#airportStationText_0").val();
	var unitMoveAuthority=$("#unitMoveAuthority").val();
   
	 if(unitRelocationDate=='')
	 {
		alert("Please fill unit relocation date");
		$("#unitRelocationDate").focus();
	 return false;
	 }
   
	 if(unitRailDutyNrs=='')
	 {
		alert("Please fill nearest railway station (for reservation)");
		$("#dutyStations_0").focus();
 return false;
 	 }
	  
	 if(unitDutyStnNa=='')
	 {
		alert("Please fill nearest airport (duty station)");
		$("#airportStationText_0").focus();
	return false;
	 }
	 
	 if(unitMoveAuthority=='')
	 {
		alert("Please fill unit movement authority");
		$("#unitMoveAuthority").focus();
		 return false;
	 }
	 
	 $("#saveUnitMovement").submit();
 }
