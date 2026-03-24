$(document).ready(function() {



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


function submitUnitForm() {
	let count = 0;
		$("[id='users']").each(function() {
			if ($(this).prop('checked')) {
				count++;
			}
		});
		if(count>0) {
			$('#unitMovementForm').attr('action', $("#context_path").val() + 'mb/submitUnit');
			$('#unitMovementForm').submit();
		} else {
			alert("Please Select atleast one checkbox");
		}
}


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


//function validateUnitMovement() 
//{
//   document.getElementById("event").value="next";
//   var formAction=document.saveUnitMovement.action;
//   document.saveUnitMovement.action=formAction.replace('/page', "");
//   document.saveUnitMovement.submit();
//   
//}





