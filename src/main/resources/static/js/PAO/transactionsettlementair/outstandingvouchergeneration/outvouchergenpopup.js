 $( document ).ready(function() {
     $(".js__p_start").simplePopup(); 
 });

function doGenerateVoucher()
{

	var checkboxArr = $("input[name=recordchkbox]");
	
	var isRecordSelected = false;
	
	var personal_no_val_array = new Array(); 
	var transaction_id_val_array = new Array();
	var net_outstanding_val_array = new Array(); 
	var due_amount_val_array = new Array(); 
	
	if(checkboxArr.length > 0)
	{
	 
			for(var x = 0 ; x < checkboxArr.length ; x++)
			{
			   if(validateCheckboxForVoucherSummary(checkboxArr[x])){
				   
					personal_no_val_array.push($("#personal_no_"+x).val());
					
					transaction_id_val_array.push($("#transaction_id_"+x).val());
					
					net_outstanding_val_array.push($("#net_outstanding_"+x).val());
					
					due_amount_val_array.push($("#due_amount_"+x).val());
					
					isRecordSelected = true;
				}
				 
		}
		
		
		
		if(isRecordSelected)
		{
			$.ajax({
			   url:  $("#context_path").val()+"pao/popupView",
			   data: {personalNo:personal_no_val_array.join(","),transaction_Id:transaction_id_val_array.join(","),net_Outstanding:net_outstanding_val_array.join(","),due_Amount:due_amount_val_array.join(",")},
			   method:"post",
			   
			   success: function(result){	
				   
		         $("#voucherGenerationSummaryId").html(result);
		          $("#voucher_Summary_Close_Id").show();
		         $(".js__p_start").click();
		}
	     	}); 
		
		}
		else
		{
			alert("No record's selected to generate voucher.");
			
			return false;
		}
	
	}
	else
	{
		alert("No records selected to generate voucher");
		
		return false;
	}
	
}


function validateCheckboxForVoucherSummary(obj)
{
		if(obj.checked && !obj.disabled)
		{
			return true ;
		}
}