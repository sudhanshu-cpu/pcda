$(document).ready(function() {

	$("#datevoucher").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd-m-Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
	    maxDate: new Date(),
		onShow: function () {
			  $("#datevoucher").val("");
		}

	});
	
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


 function validateDateFilter(){

	var from = $("#frmBookingDt").val();
	
	var to = $("#toBookingDt").val();
	
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
		  
  	   alert("To Date Should Be Greater Than From Date");
  	  $("#toBookingDt").focus();
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

function doGenerate()
{
	var result = validateDateFilter();
	
	if(result)
	{
		
		var serviceProviderType=$("#serviceProviderType").val();
		var accountOffice= $("#accountOffice").val();
		if(serviceProviderType==''){
		 	alert("Please Select The Service Provider.");
		 	event.preventDefault();
		}
		
		
		if(accountOffice=='' || accountOffice==null){
	
			alert("Please Select PAO Account Office");
		 	event.preventDefault();
		}
		
		if(!serviceProviderType=='' && !accountOffice == ''){
			
		 	$("#voucherSettlement").submit();	
		}
	}
	else{
		event.preventDefault();
	}
	
}

function formsubmit(event)
{
	
	 var Balanced_Voucher_Amt = $("#balanceAmount").val();
		var amountToSettle=$("#amountToSettle").val();
		var utrNumber=$("#utrNumber").val();
		var datevoucher=$("#datevoucher").val();
		
		if(amountToSettle==''){
		 	alert('Please Select One CheckBox');
		 	event.preventDefault();
		}
		
		if(parseFloat(amountToSettle)<0){
		 	alert('To Be Settled Amount Cannt be Negative');
		 	event.preventDefault();
		}
		
		if(parseFloat(amountToSettle) > parseFloat(Balanced_Voucher_Amt))
			{
				alert("To Be Settled Amount Cannt be Greater then Voucher Outstanding Amount :" + Balanced_Voucher_Amt);
				event.preventDefault();
			}
		
		if(utrNumber=='' ){
		 	alert("Please Enter Utr Number");
		 	event.preventDefault();
		}
		
		if(datevoucher==''){
		 	alert("Please Select Utr Date");
		 	event.preventDefault();
		}
		else{
			
		 	$("#saveSettlement").submit();	
			return true;
		}
	
	
}




function selectORDeselect(obj) {
	var fullStringTxn = new Array();
	var fullStringTotalAmt = new Array();
	fullStringTxn.length = 0;
	fullStringTotalAmt.length = 0;
var total =null;


	var checkboxArray = $("input[name=recordchkbox]");
	var checkboxArraylen = checkboxArray.length;
	

	if (obj.checked) {
		if (checkboxArraylen > 0) {

			var i0 = checkboxArraylen;
			for (var index = 0; index < i0; index++) {
				if ($("#record_" + index) == null) {
					i0++;
				}
				else {
					if ($("#record_" + index).val().indexOf('|') > -1) {
						var valArray = $("#record_" + index).val().split('|');

						fullStringTxn.push(valArray[0]);
						fullStringTotalAmt.push(valArray[1]);
						//alert("Record 0"+valArray[0]+" 1 "+valArray[1]+" 2 "+valArray[2]+" 3 "+valArray[3]+" 4 "+valArray[4]);
						$("#record_" + index).prop('checked', true);

					}
				}
			}

			$("#txnsId").val(fullStringTxn);
			// total of selected check amount
			if (fullStringTotalAmt.length > 0) {
				
				var f0 = fullStringTotalAmt.length;
				for (var index = 0; index < f0; index++) {

					var amt = parseInt(fullStringTotalAmt[index]);
					total = total + amt;

				}

			}
                       	$("#amountToSettle").val(total);

		}
	}

	else {
		if (checkboxArray.length > 0) {
			for (var index = 0; index < checkboxArraylen; index++) {

				$("#record_" + index).prop('checked', false);
				$("#amountToSettle").val(total);

			}
                    $("#txnsId").val("");
		}
	}


}

function checkBoxIndividual() {
	var checkboxArray = $("input[name=recordchkbox]");
	var checkboxArraylen = checkboxArray.length;
	var fullStringAmt = new Array();
	var fullStringTxn = new Array();
	$("#select_all").prop('checked', false);
	if (checkboxArraylen > 0) {
		var total=null;
		var i0 = checkboxArraylen;
		for (var index = 0; index < i0; index++) {

			if ($("#record_" + index).is(":checked")) {
                 
                 if ($("#record_" + index).val().indexOf('|') > -1) {
				var valArray = $("#record_" + index).val().split('|');
				fullStringTxn.push(valArray[0]);
				fullStringAmt.push(valArray[1]);
				
				
		}
			}
		
			
		}
		      $("#txnsId").val(fullStringTxn);
				if (fullStringAmt.length > 0) {
				
				var f0 = fullStringAmt.length;
				for (var index = 0; index < f0; index++) {

					var amt = parseInt(fullStringAmt[index]);
					total = total + amt;

				}
			
			}
				$("#amountToSettle").val(total);
	}
}


function pdfOn(){
	
	alert("Pdf is Not Available for this Voucher");
	return false;
}
