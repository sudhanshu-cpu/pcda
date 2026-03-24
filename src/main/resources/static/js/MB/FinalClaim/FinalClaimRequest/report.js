
var fullStringTxn = new Array();
var fullStringInvoiceArray = new Array();

var counter = 0;
var invoiceCounter = 0;

var completeInvoiceArray;

function initCompleteInvoiceArray()
{
	var checkboxArray = document.getElementsByName("recordchkbox");
	var checkboxArraylen=checkboxArray.length;
	//alert("checkboxArray = "+checkboxArray.length);
	completeInvoiceArray = new Array();
	//alert(completeInvoiceArray);
	if(checkboxArray.length > 0)
	{    
	    var loopCounter=0;
		for (var index = 0; index < checkboxArraylen; index++)
		{
			if(document.getElementById("record_"+index)==null)
			{
				checkboxArraylen++;
			}
			else if(document.getElementById("record_"+index).value.indexOf('|') > -1)
			{	
			 			
				var valArray = document.getElementById("record_"+index).value.split('|');
						
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

	function doValidateProjectName(projectName,idName)
{   
var flag=false;
		$.ajax(
   		{
		  url: "/pcda/af/pcda/common/DODAjaxHandler.do",
		   method:"get",
		    data: "projectName="+projectName+"&ajaxCallType=validateProjectName",
		   async:false,
		    success: function(msg)
              {
                   $(msg).find('ProjectName').each(function()
                  {
			      	 var name=$(msg).find('YES').text();
			      	 if(name=="Project name already exists")
                     {
                         alert(name)
                         flag=true;
                     }
                     else
                     {
			      	 flag =false;
			      	}
               });  
	     	}
	     });	
	return flag;	
}
function fullStringInvoiceArrayFunction()
{
	var checkboxArray = document.getElementsByName("recordchkbox");
	var checkboxArraylen=checkboxArray.length;
	counter=0;
	invoiceCounter=0;
	for (var index = 0; index < checkboxArraylen; index++)
	{
		if(document.getElementById("record_"+index)==null)
		{
		  checkboxArraylen++;
		}
		else
		{
			if(document.getElementById("record_"+index).value.indexOf('|') > -1)
			{
				var valArray = document.getElementById("record_"+index).value.split('|');
				if(valArray[1] == 'DR'){
					fullStringTxn[counter] = valArray[0];
			 		counter ++;
			 		var docArray =valArray[0].split('#')
				 		fullStringInvoiceArray[invoiceCounter] = docArray[0];
				 		invoiceCounter ++;
					document.getElementById("txnsId").value = fullStringTxn;
					document.getElementById("invoiceId").value = fullStringInvoiceArray;
				}
			}
		}
	}
}

function selectORDeselect(obj)
{
	fullStringTxn.length = 0;
	fullStringInvoiceArray.length = 0;
	counter = 0;
	invoiceCounter = 0
	document.getElementById("txnsId").value = "";
	document.getElementById("invoiceId").value = "";
	
	
	var checkboxArray = document.getElementsByName("recordchkbox");
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
			 			if(document.getElementById("record_"+index)==null)
			 			{
			 				i0++;
			 			}
				 		else
				 		{
				 			if(document.getElementById("record_"+index).value.indexOf('|') > -1)
							{	
				 				var valArray = document.getElementById("record_"+index).value.split('|');
				 				if(valArray[1]=='DR'){
				 					//alert("Record 0"+valArray[0]+" 1 "+valArray[1]+" 2 "+valArray[2]+" 3 "+valArray[3]+" 4 "+valArray[4]);
				 					document.getElementById("record_"+index).checked=true;
				 					checkIndividualBox(index, valArray[0], valArray[1], valArray[2],valArray[3],valArray[4]);
				 				}
				 			}
				 		}
					}
			 	}//i=0
			 	
			 	if(i==1)
			 	{
			 		var i1=checkboxArraylen;
			 		for (var index = 0; index < i1; index++)
			 		{
			 			if(document.getElementById("record_"+index)==null)
			 			{
			 				i1++;
			 			}
			 			else
			 			{	 		
			 				if(document.getElementById("record_"+index).value.indexOf('|') > -1)
							{	
			 						var valArray = document.getElementById("record_"+index).value.split('|');
			 						if(valArray[1]=='CR'){
				 						//alert(counter+"|"+invoiceCounter+" , Record 0"+valArray[0]+" 1 "+valArray[1]+" 2 "+valArray[2]+" 3 "+valArray[3]+" 4 "+valArray[4]);
										document.getElementById("record_"+index).checked=true;
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
			 			if(document.getElementById("record_"+index)==null)
			 			{
			 				checkboxArraylen++;
			 			}
			 			else
			 			{
			 				document.getElementById("record_"+index).checked = false;
			 			}
				 }
				 fullStringTxn.length = 0;
				 fullStringInvoiceArray.length = 0;
				 counter = 0;
				 invoiceCounter = 0
				 document.getElementById("txnsId").value = "";
				 document.getElementById("invoiceId").value = "";
		}
	}	
}


function checkIndividualBox(idx, txnId, docType, docId,bkgVoucherStatus,travelType)
{
	
	//alert("value of idx = "+idx + "|| txnId = "+txnId);
	var invoiceSelected = false;
	var invoiceTwoSelected = false;
	
	if(document.getElementById("record_"+idx).checked)
	{
			if(docType == 'DR'){
					fullStringTxn[counter] = txnId;
			 		counter ++;
			 		fullStringInvoiceArray[invoiceCounter] = docId;
			 		invoiceCounter ++;
					document.getElementById("txnsId").value = fullStringTxn;
					document.getElementById("invoiceId").value = fullStringInvoiceArray;
			}else
			{
					initCompleteInvoiceArray();
					//alert("completeInvoiceArray length ="+completeInvoiceArray.length);
						
					if(docType == 'CR')	
					{
						if(fullStringInvoiceArray.length < 1)
						{
						
							var crRefInvoiceNo= document.getElementById("ref_"+idx).value;
							if(crRefInvoiceNo != null)
							{
								if(crRefInvoiceNo =='yes'){
									// do nothing cr can be checked by voucher amount can not be negative.
								}else
								{
									alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
									document.getElementById("record_"+idx).checked = false;
								}
							}
							
						}else
						{
							var refNoCounter=0;
							
							var refNo = document.getElementById("ref_"+idx).value;
							
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
							
							if(document.getElementById("ref_"+idx).value=='yes')
							{
								fullStringTxn[counter] = txnId;
					 			counter ++;
					 			document.getElementById("txnsId").value = fullStringTxn;
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
						 					document.getElementById("txnsId").value = fullStringTxn;
									 	}else
									 	{
											alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No Complete Transaction.");
											document.getElementById("record_"+idx).checked = false;
									 	}
						           }else{
						           
							            if(invoiceSelected){
											fullStringTxn[counter] = txnId;
							 				counter ++;
							 				document.getElementById("txnsId").value = fullStringTxn;
										 }else
										 {
											alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
											document.getElementById("record_"+idx).checked = false;
										 }
						           }
						       		
						       }
							   
							   if(bkgVoucherStatus==1)
						       {
						       		if(invoiceSelected){
										fullStringTxn[counter] = txnId;
						 				counter ++;
						 				document.getElementById("txnsId").value = fullStringTxn;
									 }else
									 {
										alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
										document.getElementById("record_"+idx).checked = false;
									}
						       }
						        
							}else
							{
							     alert("Credit Note "+docId+" could not be selected for voucher generation until you will select corresponding Invoice No.");
								 document.getElementById("record_"+idx).checked = false;
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
						document.getElementById("txnsId").value = fullStringTxn;
					}
				}
				
				for(var v = 0; v < fullStringInvoiceArray.length; v++){
					
					if(fullStringInvoiceArray[v] == docId){
						
						fullStringInvoiceArray[v] = "";
						
						document.getElementById("txnsId").value = fullStringTxn;
						document.getElementById("invoiceId").value = fullStringInvoiceArray;
					}
				}
				
				var refhiddenArr = document.getElementsByName("refhidden");
				
				if(refhiddenArr.length > 0)
				{
						
						for(var k = 0; k < refhiddenArr.length; k++)
						{
							if(refhiddenArr[k].value != 'NA' && (refhiddenArr[k].value == docId))
							{
								if(document.getElementById("record_"+k).checked){
									creditNoteChecked = true;
									break;
								}
							}
						}
						
						//alert("value of creditNoteChecked == "+creditNoteChecked);
						
						if(creditNoteChecked){
							
							alert("As you have unchecked Invoice No. so, its corresponding credit note will be unchecked by the system.");
							document.getElementById("record_"+k).checked = false;
							
							var valueArry = document.getElementById("record_"+k).value.split('|');
							
							
							if(valueArry.length > 2)
							{
								for(var k = 0; k < fullStringTxn.length; k++){
						
									if(fullStringTxn[k] == valueArry[0]){
										
										fullStringTxn[k] = "";
										
										document.getElementById("txnsId").value = fullStringTxn;
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
						
						document.getElementById("txnsId").value = fullStringTxn;
					}
				}
			} //End of CR Case
		}
		
	}
	
}


var fullStringTrxns;
var check_counter = 0;


function setVoucherDetailComment(inputObj){

	document.getElementById("voucher_detail").value = inputObj.value;	
	
}


function isDocumentSelected(){
	
	var isDocSelected = false;
	var voucherIdxx = document.getElementById("voucher_index").value;
	var txnCheckBoxArray = document.getElementsByName("txn_checkbox_"+voucherIdxx);
					
	if(txnCheckBoxArray.length > 0)
	{
		for(var v = 0; v < txnCheckBoxArray.length; v++){
			if(txnCheckBoxArray[v].checked){
				isDocSelected = true;
				break;
			}
		 }
	}
	return isDocSelected;
}

function enableORDisableInput(voucherNo, dueVoucherAmt, voucherIndex)
{
	
	fullStringTrxns = new Array();
	check_counter = 0;
		
	var radio_array = document.getElementsByName("voucherRadio");
		
	//alert("length of radio_array = "+radio_array);
		
	for(var i = 0; i < radio_array.length; i++)
	{
					
		//alert("inside loop");
					
		if(document.getElementById("radio_"+i).checked){
						
				document.getElementById("button_"+i).style.display = 'block';
				document.getElementById("txn_row_"+i).style.display = 'block';
							
		 		document.getElementById("inputAmount_"+i).value="0.0";
		 		document.getElementById("descOfAmount_"+i).value="";
		 		document.getElementById("descOfAmount_"+i).disabled=false;
		 		document.getElementById("utrDate_"+i).value="";
		 		document.getElementById("utrDate_"+i).disabled=false;
						
		}else{
				document.getElementById("button_"+i).style.display = 'none';
				document.getElementById("txn_row_"+i).style.display = 'none';
		 		document.getElementById("inputAmount_"+i).value="";
		 		document.getElementById("descOfAmount_"+i).value="";
		 		document.getElementById("descOfAmount_"+i).disabled=true;
		 		document.getElementById("utrDate_"+i).value="";
		 		document.getElementById("utrDate_"+i).disabled=true;
		}
					
					
		var checkBoxArray = document.getElementsByName("txn_checkbox_"+i);
		document.getElementById("select_all_"+i).checked=false;
		//alert("length of checkBoxArray length = "+checkBoxArray.length);
					
		if(checkBoxArray.length > 0){
			for(var v = 0; v < checkBoxArray.length; v++){
				checkBoxArray[v].checked = false;
			}
		}
					
	} //End of loop
		
	document.getElementById("amt_to_setteled").value="0.0";
	document.getElementById("voucher_detail").value="";
	document.getElementById("balanced_voucher_amt").value = dueVoucherAmt;
	document.getElementById("voucher_to_setteled").value = voucherNo;
	document.getElementById("voucher_index").value = voucherIndex;
	document.getElementById("transaction_ids").value = fullStringTrxns;

}

var decimalRegex =  /^[0-9]+\.[0-9]+$/;
var inputVal;
var maxValue;

function isNumeric(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}


function onChangeTxnCheckbox(checkObj, voucherIdx, txnId, amount,seqNo)
{

	//alert("value of checkObj == "+checkObj+"  txnId == "+txnId+" amount == "+txnId+ "  voucherIdx == "+voucherIdx+ "  amount == "+amount);
	
	var inputAmt = document.getElementById("amt_to_setteled").value;
	var amt_to_settle = parseFloat(inputAmt);
	
	if(checkObj.checked){
			
			amt_to_settle = amt_to_settle + parseFloat(amount);
			document.getElementById("amt_to_setteled").value = amt_to_settle;
			document.getElementById("inputAmount_"+voucherIdx).value = amt_to_settle;
			
			fullStringTrxns[check_counter] = txnId+":"+seqNo;
			check_counter ++;
			document.getElementById("transaction_ids").value = fullStringTrxns;
			
	}else{
				
			amt_to_settle = amt_to_settle - parseFloat(amount);
			document.getElementById("amt_to_setteled").value = amt_to_settle;
			document.getElementById("inputAmount_"+voucherIdx).value = amt_to_settle;
			
			if(fullStringTrxns.length > 0){
				for(var s = 0; s < fullStringTrxns.length; s++){
					if(fullStringTrxns[s] == txnId+":"+seqNo){
						fullStringTrxns[s] = '';
						break;
					}
				}
			}
			
			document.getElementById("transaction_ids").value = fullStringTrxns;
			
	}
	
	var txn_checkbox=document.getElementsByName("txn_checkbox_"+voucherIdx);
	if(txn_checkbox != null && txn_checkbox.length > 0){
		var flag=true;
		for(var index=0; index < txn_checkbox.length; index++){
			if(!txn_checkbox[index].disabled && !txn_checkbox[index].checked){
				flag=false;
			}
		}
		
		if(flag){
			document.getElementById("select_all_"+voucherIdx).checked=true;
		}else{
			document.getElementById("select_all_"+voucherIdx).checked=false;
		}
	}
	
	
}


var fullStringPaymentIds;
var payment_counter = 0;

function selectVoucherRadio(voucherNo)
{
	
		fullStringPaymentIds = new Array();
		payment_counter = 0;
		
		var radio_array = document.getElementsByName("voucherRadio");
		
		//alert("length of radio_array = "+radio_array);
		
				for(var i = 0; i < radio_array.length; i++){
					
					//alert("inside loop");
					
					if(document.getElementById("radio_"+i).checked){
						
							document.getElementById("button_"+i).style.display = 'block';
							document.getElementById("txn_row_"+i).style.display = 'block';
						
					}else{
							document.getElementById("button_"+i).style.display = 'none';
							document.getElementById("txn_row_"+i).style.display = 'none';
					}
					
				} //End of loop
		
				
			document.getElementById("voucherNo").value = voucherNo;
			document.getElementById("payment_ids").value = fullStringPaymentIds;
			
			var checkBoxArray = document.getElementsByName("check_"+voucherNo);
					//alert("length of checkBoxArray length = "+checkBoxArray.length);
					
					if(checkBoxArray.length > 0){
						
						for(var v = 0; v < checkBoxArray.length; v++){
							
							document.getElementById("ack_"+voucherNo+"_"+v).value = "";
							document.getElementById("file_"+voucherNo+"_"+v).value = "";
							document.getElementById("ack_date_"+voucherNo+"_"+v).value = "";
							
							document.getElementById("ack_"+voucherNo+"_"+v).disabled=true;
							document.getElementById("file_"+voucherNo+"_"+v).disabled=true;
							document.getElementById("ack_date_"+voucherNo+"_"+v).disabled=true;
			
							checkBoxArray[v].checked = false;
						}
					}
						
}


function isPaymentSelected(){
	
	var isPaySelected = false;
	
	var voucher_No = document.getElementById("voucherNo").value;
	
	var checkBoxArr = document.getElementsByName("check_"+voucher_No);
					
		if(checkBoxArr.length > 0){
						
		for(var v = 0; v < checkBoxArr.length; v++){
			
			if(checkBoxArr[v].checked){
				isPaySelected = true;
				break;
			}
		 }
		}
		
	return isPaySelected;
}


function isInputDataOK(){
	
	var voucher_No = document.getElementById("voucherNo").value;
	
	var checkBoxArr = document.getElementsByName("check_"+voucher_No);
					
		if(checkBoxArr.length > 0){
						
		for(var v = 0; v < checkBoxArr.length; v++){
			
			if(checkBoxArr[v].checked){
				
				paymentNo = checkBoxArr[v].value;
				
				if(document.getElementById("ack_"+voucher_No+"_"+v).value == ''){
					emptyInput = 'ack_no';
				}else if(document.getElementById("ack_date_"+voucher_No+"_"+v).value == ''){
					emptyInput = 'ack_date';
				}else if(document.getElementById("ack_date_"+voucher_No+"_"+v).value != '' && isFutureDate(document.getElementById("ack_date_"+voucher_No+"_"+v).value)){
				      emptyInput = 'ack_date_future';
				}else if(document.getElementById("file_"+voucher_No+"_"+v).value == ''){
					emptyInput = 'ack_file';
				}
				}
				
				if(emptyInput != ''){
					break;
				}
				
			}
		 }
	
}



function onChangePaymentCheckbox(checkObj, voucherNo, chkIdx, payID){

	//alert("value of checkObj == "+checkObj+"  txnId == "+txnId+" amount == "+txnId+ "  voucherIdx == "+voucherIdx+ "  amount == "+amount);
	
		if(checkObj.checked){
			
			document.getElementById("ack_"+voucherNo+"_"+chkIdx).value = "";
			document.getElementById("file_"+voucherNo+"_"+chkIdx).value = "";
			
			document.getElementById("ack_"+voucherNo+"_"+chkIdx).disabled=false;
			document.getElementById("file_"+voucherNo+"_"+chkIdx).disabled=false;
			document.getElementById("ack_date_"+voucherNo+"_"+chkIdx).disabled=false;
			new Epoch('cal','popup',document.getElementById("ack_date_"+voucherNo+"_"+chkIdx),false);
			
			fullStringPaymentIds[payment_counter] = payID;
			payment_counter ++;
			document.getElementById("payment_ids").value = fullStringPaymentIds;
			
			}
			else{
			
			document.getElementById("ack_"+voucherNo+"_"+chkIdx).value = "";
			document.getElementById("file_"+voucherNo+"_"+chkIdx).value = "";
			document.getElementById("ack_"+voucherNo+"_"+chkIdx).disabled = true;
			document.getElementById("file_"+voucherNo+"_"+chkIdx).disabled = true;
			document.getElementById("ack_date_"+voucherNo+"_"+chkIdx).disabled=true;
			
			if(fullStringPaymentIds.length > 0){
				
				for(var s = 0; s < fullStringPaymentIds.length; s++){
					
					if(fullStringPaymentIds[s] == payID){
						
						fullStringPaymentIds[s] = '';
						break;
					}
					
				}
			}
			
			document.getElementById("payment_ids").value = fullStringPaymentIds;
			
		}
		
}
		

function doValidateEmpty(){
	document.getElementById("isValidated").value = '';
}


var fullStringUsrs = new Array();
var user_counter = 0;


function selectORDeselectUsrCheckbox(obj){

	var checkboxArr = document.getElementsByName("usr_checkbox");
	
	if(obj.checked){
		
		if(checkboxArr.length > 0){
			
			fullStringUsrs = new Array();
			user_counter = 0;
			
			 for (var index = 0; index < checkboxArr.length; index++) {
			 		document.getElementById("usr_"+index).checked = true;
			 		
			 		fullStringUsrs[user_counter] = document.getElementById("usr_"+index).value;
			 		document.getElementById("remark_"+document.getElementById("usr_"+index).value).value = "";
			 		document.getElementById("remark_"+document.getElementById("usr_"+index).value).disabled = false;
			 		user_counter ++;
			 }
			 
			 document.getElementById("userIds").value = fullStringUsrs;
			
			}
	
	}else{
		if(checkboxArr.length > 0){
			
				 for (var index = 0; index < checkboxArr.length; index++) {
				 		document.getElementById("usr_"+index).checked = false;
				 		document.getElementById("remark_"+document.getElementById("usr_"+index).value).value = "";
			 			document.getElementById("remark_"+document.getElementById("usr_"+index).value).disabled = true;
				 }
				 
				  fullStringUsrs = new Array();
				  user_counter = 0;
				  document.getElementById("userIds").value = "";
			}
	}	
}


function onChangeUserCheckbox(checkObj, userId){

	//alert("value of checkObj == "+checkObj+"  userId == "+userId);
	
		if(checkObj.checked){
			
			fullStringUsrs[user_counter] = userId;
			user_counter ++;
			document.getElementById("userIds").value = fullStringUsrs;
			document.getElementById("remark_"+userId).value = "";
			document.getElementById("remark_"+userId).disabled = false;
			
			}
			else{
		
			if(fullStringUsrs.length > 0){
				
				for(var s = 0; s < fullStringUsrs.length; s++){
					
					if(fullStringUsrs[s] == userId){
						
						fullStringUsrs[s] = '';
						break;
					}
					
				}
			}
			
			document.getElementById("userIds").value = fullStringUsrs;
			document.getElementById("remark_"+userId).value = "";
			document.getElementById("remark_"+userId).disabled = true;
		}
}



function validateDateFilter(){

	var from = document.getElementById('frmBookingDt').value;
	var to = document.getElementById('toBookingDt').value;
	if(from=="" && to=="")
	{
		return true;
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
  	   document.getElementById('toBookingDt').focus();
  	   return false;
  	}
  	
  	var daysApart = Math.round((eDate-sDate)/86400000);
    if(daysApart > 14)
    {   
		  //alert("Report Is Available For 15 Days Only.");
		  //document.getElementById('toBookingDt').focus();
		  //return false;
	}
	
	return true;
}


function validateDateRangeFilter(){

	var from = document.getElementById('frmBookingDt').value;
	var to = document.getElementById('toBookingDt').value;
	if(from=="" && to=="")
	{
		return true;
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
  	   document.getElementById('toBookingDt').focus();
  	   return false;
  	}
  	
  	var daysApart = Math.round((eDate-sDate)/86400000);
    if(daysApart > 60)
    {   
		  alert("Report Is Available For 60 Days Only.");
		  document.getElementById('toBookingDt').focus();
		  return false;
	}
	
	return true;
}
//VOUCHER_BOARDING_PASS_CLAIM_REPORT starts
function validateDate(){

	var from = document.getElementById('frmBookingDt').value;
	var to = document.getElementById('toBookingDt').value;
	if(from=="" && to==""){
		return true;
	}
	
	if(to==""){
		alert("To Date Should Not Empty");
		document.getElementById('toBookingDt').focus();
		return false;
	}
	
	if(to!="" && from==""){
		alert("From Date Should Not Empty");
		document.getElementById('frmBookingDt').focus();
		return false;
	}
	
  	from = from.split("/");
  	to = to.split("/");
  	var sDate = new Date(from[2]+","+(from[1])+","+from[0]);
  
  	var eDate = new Date(to[2]+","+(to[1])+","+to[0]);
  	
  	if(sDate > eDate )
  	{
  	   alert("To Date Should Be Greater Then From Date");
  	   document.getElementById('toBookingDt').focus();
  	   return false;
  	}
  	
	return true;
	
}

function validateCheckbox(){
	
	var checkboxArr = document.getElementsByName("verifyStatus");
	
	var isRecordSelected = false;
	
	if(checkboxArr.length > 0)
	{
	
		for(var x = checkboxArr.length-1 ; x >= 0 ; x--)
		{
			if(checkboxArr[x].checked && !checkboxArr[x].disabled){
				isRecordSelected = true;
				break;
			}
		}
		
		if(isRecordSelected)
		{
			document.getElementById("useraction").value="doClaim";
		 	document.getElementById("event").value="settlement";
		 	var actionOriginal=document.voucherBoardingPassClaimForm.action;
			document.voucherBoardingPassClaimForm.action=actionOriginal.replace('/page', "");
		 	document.voucherBoardingPassClaimForm.submit();	
			return true;
		}
		else{
			alert("No record's selected to claim.");
			return false;
		}
	
	}else{
		alert("No records selected to claim");
		return false;
	}
}

//VOUCHER_BOARDING_PASS_CLAIM_REPORT ends

//TADA_CLAIM_REPORT starts
/*
function validateCheckbox(){
	
	var checkboxArr = document.getElementsByName("verifyStatus");
	
	var isRecordSelected = false;
	
	if(checkboxArr.length > 0)
	{
	
		for(var x = checkboxArr.length-1 ; x >= 0 ; x--)
		{
			if(checkboxArr[x].checked && !checkboxArr[x].disabled){
				isRecordSelected = true;
				break;
			}
		}
		
		if(isRecordSelected)
		{
			document.getElementById("useraction").value="doClaim";
		 	document.getElementById("event").value="claim";
		 	var actionOriginal=document.requestPoolForTADA.action;
			document.requestPoolForTADA.action=actionOriginal.replace('/page', "");
		 	document.requestPoolForTADA.submit();	
			return true;
		}
		else{
			alert("No record's selected to claim.");
			return false;
		}
	
	}else{
		alert("No records selected to claim");
		return false;
	}
}
*/
//TADA_CLAIM_REPORT ends

//AIR_VOUCHER_SETTLEMENT_SELECTALL starts
function selectAllTransactionId(index,size)
{
	var selectAll = '';
	var select_individual = '' ;
	
	var select_individual_string = '' ;
	
	var select_individual_val = '' ;

	selectAll = document.getElementById("select_all_"+index);
	
	if(selectAll.checked)
	{
		for(var i=0 ;i<size ; i++)
		{
			
			select_individual = document.getElementById("txn_"+index+"_"+i);
			if(false==select_individual.disabled && select_individual.checked==false)
			{
				select_individual.checked = true ;
				select_individual_string = document.getElementById("txnVal_"+index+"_"+i).value;
				select_individual_val = select_individual_string.split("::");
				onChangeTxnCheckbox(select_individual, select_individual_val[0],select_individual_val[1],select_individual_val[2],select_individual_val[3]);
			}
				
		}
  }
  else
  {
  		for(var i=0 ;i<size ; i++)
		{
			
			select_individual = document.getElementById("txn_"+index+"_"+i);
			if(false==select_individual.disabled)
			{
				select_individual.checked = false ;
				select_individual_string = document.getElementById("txnVal_"+index+"_"+i).value;
				select_individual_val = select_individual_string.split("::");
				onChangeTxnCheckbox(select_individual, select_individual_val[0],select_individual_val[1],select_individual_val[2],select_individual_val[3]);
			}
				
		}
  	
  }

}

//AIR_VOUCHER_SETTLEMENT_SELECTALL ends

//AIR_VOUCHER_GENERATION_SUMMARY starts


function validateCheckboxForVoucherSummary(obj)
{
		if(obj.checked && !obj.disabled)
		{
			return true ;
		}
}


//AIR_VOUCHER_GENERATION_SUMMARY ends


// by pradeep sharma
function doValidateBudgetAmount(amount)
{   
var flag=false;
		$.ajax(
   		{
		  url: "/pcda/af/pcda/common/DODAjaxHandler.do",
		   method:"get",
		    data: "budgetAmount="+amount+"&ajaxCallType=validateBudgetAmount",
		   async:false,
		    success: function(msg)
              {
                   $(msg).find('BudgetAmount').each(function()
                  {
			      	 var name=$(msg).find('YES').text();
			      	 if(name=="Your Budget Amount is low")
                     {
                         alert(name)
                         flag=true;
                     }
                     else
                     {
			      	 flag =false;
			      	}
               });  
	     	}
	     });	
	return flag;	
}

// by pradeep sharma


function isFutureDate(idate){
    var today = new Date().getTime(),
        idate = idate.split("/");

    idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
     return (today - idate) < 0 ? true : false; 
}
