$(document).ready(function() {
	$("#frmBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#frmBookingDt").val("");
		}

	});

	$("#toBookingDt").datetimepicker({
		lang: 'en',
		timepicker: false,
		format: 'd/m/Y',
		formatDate: 'd-m-Y',
		scrollMonth: false,
		scrollInput:false,
		yearEnd : 2100,
		onShow: function () {
			  $("#toBookingDt").val("");
		}


	});
	
   
  
	var toDtId = $("#toDateId");
	var frmDtId = $("#frmDateId");
	var mnthYrId = $("#mnthYrId");
	


	var reportTypeSlct = $("#reportType");
	  var reportTypeVal='';
	 
	$('#reportType option').each(function() {
    if ($(this).is(':selected')){
      reportTypeVal = reportTypeSlct.val();
       } 
    });
	
	if (reportTypeVal == '') {
		frmDtId.hide();
		toDtId.hide();
		mnthYrId.hide();
	}
	if (reportTypeVal == '1') {
		frmDtId.show();
		toDtId.hide();
		mnthYrId.hide();
	}
	if (reportTypeVal == '2') {
	
		frmDtId.hide();
		toDtId.hide();
		mnthYrId.show();
		showMonth();
	    setMonthYear();
	}
	if (reportTypeVal == '3') {
		frmDtId.show();
		toDtId.show();
		 mnthYrId.hide();
	}
	if (reportTypeVal == '4') {
		frmDtId.show();
		toDtId.show();
		mnthYrId.hide();
	}


    

});

function setMonthYear() {
	if ($("#reportType").val() == 2) {
		$("#mthId option[value='" + $("#monthSel").val() + "']").prop('selected', true);
		$("#yrId option[value='" + $("#yearSel").val() + "']").prop('selected', true);
	}
}

function hideToDate() {
   

	var toDtId = $("#toDateId");
	var frmDtId = $("#frmDateId");
	var mnthYrId = $("#mnthYrId");
	


	 var reportTypeSlct = $("#reportType");
	  var reportTypeVal=reportTypeSlct.val();
	 
	
	
	if (reportTypeVal == '') {
		frmDtId.hide();
		toDtId.hide();
		mnthYrId.hide();
	}
	if (reportTypeVal == '1') {
		frmDtId.show();
		toDtId.hide();
		mnthYrId.hide();
	}
	if (reportTypeVal == '2') {
	    
		frmDtId.hide();
		toDtId.hide();
		showMonth();
		mnthYrId.show();
		
	   
	}
	if (reportTypeVal == '3') {
		frmDtId.show();
		toDtId.show();
		mnthYrId.hide();
	}
	if (reportTypeVal == '4') {
		frmDtId.show();
		toDtId.show();
		mnthYrId.hide();
	}

}

function generateReport() {
	 var flag=true;
	
	var reportTypeSlct = $("#reportType");
	var frmBookingDt = $("#frmBookingDt");
	var toBookingDtId = $("#toBookingDt");
	var mthId = $("#mthId");
	var yrId = $("#yrId");


	if (reportTypeSlct.val() == '') {
		alert("Please select the Report");
		reportTypeSlct.focus();
		
		flag=false;
		
	}
	
	if (reportTypeSlct.val() == '1') {
		
		if(frmBookingDt.val() == ''){
		alert("Please select the From Date");
		frmBookingDt.focus();
		
		flag=false;
	
		}
		
	}

	if (reportTypeSlct.val() == '2') {
		if (mthId.val() == '') {
			alert("Please select the Month");
			mthId.focus();
			
		   flag=false;
		}
		if (yrId.val() == '') {
			alert("Please select the Year");
			yrId.focus();
			
			flag=false;
		}
	}


	if (reportTypeSlct.val() == '3' || reportTypeSlct.val() == '4') {
		if (frmBookingDt.val() == '') {
			alert("Please select the From Date");
			frmBookingDtId.focus();
			
			flag=false;
		}
		if (toBookingDtId.val() == '') {
			alert("Please select the To Date");
			toBookingDtId.focus();
			
			flag=false;
		}
		
	}


     if(flag){
	$("#generateReport").submit();
	}
	
}







function showMonth() {
	var mnthName = new Array('Select', 'JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUNE', 'JULY', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC');
	var monthID = document.getElementById("monthID");
	var mnthStr = "";
	mnthStr += '<select name="month" id="mthId" class="combo">';
	for (var i = 0; i < mnthName.length; i++) {
		if (i < 10)
			mnthStr += '<option value="0' + i + '">' + mnthName[i] + '</option>';
		else
			mnthStr += '<option value="' + i + '">' + mnthName[i] + '</option>';
	}
	mnthStr += '</select>&nbsp;';
	

	var yearStr = "";
	var currentYear = new Date(2100);
	yearStr += '<select name="year" id="yrId" class="combo">';
	yearStr += '<option value="">Select</option>';
	
	
	for (var i = 2009; i <= currentYear; i++) {
		yearStr += '<option value="' + i + '">' + i + '</option>';
	}
	yearStr += '</select>';
	monthID.innerHTML = mnthStr + yearStr;
}






