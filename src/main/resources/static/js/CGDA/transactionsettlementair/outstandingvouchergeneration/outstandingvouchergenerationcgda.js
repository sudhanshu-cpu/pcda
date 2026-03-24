$(document).ready(function() {


	$("#frmBookingDt").datetimepicker({
	  lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d-m-Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#frmBookingDt").val("");
		}
	});
	
	$("#toBookingDt").datetimepicker({
	  lang:'en',
		timepicker:false,
		format:'d/m/Y',
		formatDate:'d-m-Y',
		scrollMonth : false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#toBookingDt").val("");
		}
	});
	
	
	
	});


var fullStringTxn = new Array();
var fullStringInvoiceArray = new Array();

var counter = 0;
var invoiceCounter = 0;

var completeInvoiceArray;

function doGenerate()
{
	var result = validateDateFilter();
	
	if(result)
	{
		var statusType=$("#statusType").val();
		var accountOffice=$("#accountOffice").val();
		var serviceProviderType=$("#serviceProviderType").val();
		
		if(accountOffice=='' || accountOffice=='-1'){
		 	alert("Please Select The PAO Account Office.");
		 	return false;
		}
		
		if(serviceProviderType==''){
		 	alert("Please Select The Service Provider.");
		 	return false;
		}
	if(statusType==''){
		 	alert("Please Select The Status Type.");
		 	return false;
		}
		if (!accountOffice == '' && !serviceProviderType == '' && !statusType=='' ) {
			
		 	$("#outstandingRprtFilter").submit();	
		
		}
	}
	else{
		return false;
	}
	
}

     
 
 function validateDateFilter(){

	var from = $("#frmBookingDt").val();
	
	
	var to = $("#toBookingDt").val();
	

   
	if(from=="" && to=="")
	{
		alert("Kindly select From Date and To Date");
		return false;
	}
	
	if(from=="")
	{
		alert("From Date Should Not Empty");
		document.getElementById('frmBookingDt').focus();
		return false;
	}
	if(to=="")
	{
		alert("To Date Should Not Empty");
		document.getElementById('toBookingDt').focus();
		return false;
	}
	
   
  	from = from.split("/");
  	to = to.split("/");
  
  	var sDate = new Date(from[2]+","+(from[1])+","+from[0]);
   
  	var eDate = new Date(to[2]+","+(to[1])+","+to[0]);
  	
  	  
  	if(sDate > eDate )
  	{
		  
  	   alert("To Date Should Be Greater Then From Date");
  	  $("#toBookingDt").focus();
  	   return false;
  	}
  	
  	var daysApart = Math.round((eDate-sDate)/86400000);
    if(daysApart > 31)
    {   
		  alert("Report Is Available For 31 Days Only.");
		  document.getElementById('toBookingDt').focus();
		  return false;
	}
	
	return true;
}

   





function selectORDeselect(obj)
{
	fullStringTxn.length = 0;
	fullStringInvoiceArray.length = 0;
	counter = 0;
	invoiceCounter = 0
	$("#txnsId").val("");
	$("#invoiceId").val("");
	
	
	var checkboxArray = $("input[name=recordchkbox]");
	var checkboxArraylen=checkboxArray.length;
	//alert("checkboxArray = "+checkboxArray.length);
	
	if(obj.checked)
	{
		
		if(checkboxArraylen > 0)
		{
			 for (var i = 0; i < 2; i++)// FIRST FOR ALL DR AND SECOND FOR ALL CR
			 {
			 	if(i==0)
			 	{
			 		var i0=checkboxArraylen;
			 		for (var index = 0; index < i0; index++)
			 		{
			 			if($("#record_"+index)==null)
			 			{
			 				i0++;
			 			}
				 		else
				 		{
							 
				 				
								var recordValue = $("#record_"+index).val();
				 				var valArray = $("#record_"+index).val().split('|');
				 				if(valArray[1]=='DR'){
				 					//alert("Record 0"+valArray[0]+" 1 "+valArray[1]+" 2 "+valArray[2]+" 3 "+valArray[3]+" 4 "+valArray[4]);
				 					$("#record_"+index).prop('checked', true);
				 					checkIndividualBox(index, valArray[0], valArray[1], valArray[2],valArray[3],valArray[4]);
				 				
				 			}
				 		}
					}
			 	}//i=0
			 	
			 	if(i==1)
			 	{
			 		var i1=checkboxArraylen;
			 		for (var index = 0; index < i1; index++)
			 		{
			 			if($("#record_"+index)==null)
			 			{
			 				i1++;
			 			}
			 			else
			 			{	 		
			 				if($("#record_"+index).val().indexOf('|') > -1)
							{	
			 						var valArray = $("#record_"+index).val().split('|');
			 						if(valArray[1]=='CR'){
				 						//alert(counter+"|"+invoiceCounter+" , Record 0"+valArray[0]+" 1 "+valArray[1]+" 2 "+valArray[2]+" 3 "+valArray[3]+" 4 "+valArray[4]);
										
										$("#record_"+index).prop('checked', true);
				 						checkIndividualBox(index, valArray[0], valArray[1], valArray[2],valArray[3],valArray[4]);
			 						}
			 				}
						}
					}
			 	}//i=1
			 	
			 }//FIRST FOR ALL DR AND SECOND FOR ALL CR
		}
	}
	else
	{
		if(checkboxArray.length > 0)
		{
				 for (var index = 0; index < checkboxArraylen; index++)
			 	 {
			 			if($("#record_"+index)==null)
			 			{
			 				checkboxArraylen++;
			 			}
			 			else
			 			{
			 				$("#record_"+index).prop('checked', false);
			 			}
				 }
				 fullStringTxn.length = 0;
				 fullStringInvoiceArray.length = 0;
				 counter = 0;
				 invoiceCounter = 0
				$("#txnsId").val("");
				 $("#invoiceId").val("");
		}
	}	
}






function initCompleteInvoiceArray()
{
	var checkboxArray = $("input[name=recordchkbox]");
	var checkboxArraylen=checkboxArray.length;
	//alert("checkboxArray = "+checkboxArray.length);
	completeInvoiceArray = new Array();
	//alert(completeInvoiceArray);
	if(checkboxArray.length > 0)
	{    
	    var loopCounter=0;
		for (var index = 0; index < checkboxArraylen; index++)
		{
			if($("#record_"+index)==null)
			{
				checkboxArraylen++;
			}
			else if($("#record_"+index).val() > -1)
			{	
			 			
				var valArray = $("#record_"+index).val().split('|');
						
				if(valArray.length > 2){
			 		if(valArray[1] == 'DR'){
		 				completeInvoiceArray[loopCounter] = valArray[2];
		 				loopCounter++;
		 			}		
			 	}
			 			
			}//else if loop
		}//for loop
	}
}
	


	function checkIndividualBox(idx, txnId, docType, docId,bkgVoucherStatus,travelType)
    {
	
//	alert("value of idx = "+idx + "|| txnId = "+txnId);
	var invoiceSelected = false;
	var invoiceTwoSelected = false;
	
	
	if($("#record_"+idx).is(":checked"))
	{
	 
			if(docType.trim() == 'DR'){
			
					fullStringTxn[counter] = txnId;
			 		counter ++;
			 		fullStringInvoiceArray[invoiceCounter] = docId;
			 		invoiceCounter ++;
					$("#txnsId").val(fullStringTxn) ;
					
					 $("#invoiceId").val(fullStringInvoiceArray) ;
			}else
			{
					initCompleteInvoiceArray();
					//alert("completeInvoiceArray length ="+completeInvoiceArray.length);
						
					if(docType.trim() == 'CR')	
					{
						if(fullStringInvoiceArray.length < 1)
						{
						
							var crRefInvoiceNo= $("#ref_"+idx).val();
							
							
							if(crRefInvoiceNo != null)
							{
								if(crRefInvoiceNo =='yes'){
									// do nothing cr can be checked by voucher amount can not be negative.
								}else
								{
									alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
									$("#record_"+idx).prop('checked', false);
							}
							}
							
						}else
						{
							var refNoCounter=0;
							
							var refNo = $("#ref_"+idx).val();
							
							for (var ind = 0; ind < completeInvoiceArray.length; ind++) 
							{
								if(completeInvoiceArray[ind].indexOf(refNo) > -1)
								{
									refNoCounter++;
								}
							}		
							//alert("refNoCounter="+refNoCounter);
								
							for (var ind = 0; ind < fullStringInvoiceArray.length; ind++) 
							{
								if(fullStringInvoiceArray[ind].indexOf(refNo) > -1)
								{
									if(invoiceSelected){
										invoiceTwoSelected = true;
									}else{
										invoiceSelected = true;
									}
				 				} 
							
							} //End of loop
							
							//alert("Credit Note"+docId+",refNoCounter="+refNoCounter+",bkgVoucherStatus="+bkgVoucherStatus+",invoiceSelected="+invoiceSelected+",invoiceTwoSelected="+invoiceTwoSelected);
							
							if($("#ref_"+idx).val()=='yes')
							{
								fullStringTxn[counter] = txnId;
					 			counter ++;
					 			$("#txnsId").val(fullStringTxn);
					 			//alert("hello");
								
							}
							else if(refNoCounter>0)
						    {
						       if(bkgVoucherStatus==0)
						       {
							      
						           if(travelType.indexOf('LTC')>-1)
						           {
							          	
							          	if(invoiceSelected && invoiceTwoSelected)
								    	{
											fullStringTxn[counter] = txnId;
						 					counter ++;
						 					$("#txnsId").val(fullStringTxn);
									 	}else
									 	{
											alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No Complete Transaction.");
											$("#record_"+idx).prop('checked', false);
									 	}
						           }else{
						           
							            if(invoiceSelected){
											fullStringTxn[counter] = txnId;
							 				counter ++;
							 				$("#txnsId").val(fullStringTxn);
										 }else
										 {
											alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
											$("#record_"+idx).prop('checked', false);
										 }
						           }
						       		
						       }
							   
							   if(bkgVoucherStatus==1)
						       {
						       		if(invoiceSelected){
										fullStringTxn[counter] = txnId;
						 				counter ++;
						 				$("#txnsId").val(fullStringTxn);
									 }else
									 {
										alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
										$("#record_"+idx).prop('checked', false);
									}
						       }
						        
							}else
							{
							     alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
								$("#record_"+idx).prop('checked', false);
							}
						 }
						
						} //End of docType == 'CR'
					} //End of else
	}else
	{
			
		if(fullStringTxn.length > 0)
		{
				
			if(docType == 'DR')
			{
				var creditNoteChecked = false;
				
				for(var k = 0; k < fullStringTxn.length; k++){
					if(fullStringTxn[k] == txnId){
						fullStringTxn[k] = "";
						$("#txnsId").val(fullStringTxn);
					}
				}
				
				for(var v = 0; v < fullStringInvoiceArray.length; v++){
					
					if(fullStringInvoiceArray[v] == docId){
						
						fullStringInvoiceArray[v] = "";
						
						$("#txnsId").val(fullStringTxn) ;
						$("#invoiceId").val(fullStringInvoiceArray) ;
					}
				}
				
				var refhiddenArr = $("#refhidden").val();
				
				if(refhiddenArr.length > 0)
				{
						
						for(var k = 0; k < refhiddenArr.length; k++)
						{
							if(refhiddenArr[k].value != 'NA' && (refhiddenArr[k].value == docId))
							{
								if($("#record_"+k).checked){
									creditNoteChecked = true;
									break;
								}
							}
						}
						
						//alert("value of creditNoteChecked == "+creditNoteChecked);
						
						if(creditNoteChecked){
							
							alert("As you have unchecked Invoice No. so, its corresponding credit note will be unchecked by the system.");
							$("#record_"+k).checked = false;
							
							var valueArry = $("#record_"+k).val().split('|');
							
							
							if(valueArry.length > 2)
							{
								for(var k = 0; k < fullStringTxn.length; k++){
						
									if(fullStringTxn[k] == valueArry[0]){
										
										fullStringTxn[k] = "";
										
										$("#txnsId").val(fullStringTxn);
									}
								}
							}
				
						} // if corresponding credit note is selected.
				}
			} //End of DR Case
			else
			{
				
				for(var k = 0; k < fullStringTxn.length; k++){
					
					if(fullStringTxn[k] == txnId){
						
						fullStringTxn[k] = "";
						
						$("#txnsId").val(fullStringTxn);
					}
				}
			} //End of CR Case
		}
		
	}
	
}




