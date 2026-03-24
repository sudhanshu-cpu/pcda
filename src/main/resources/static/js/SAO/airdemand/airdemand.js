
$( document ).ready(function() {
	var todate = $('#datetimepicker').val();
   $('#datetimepicker').datetimepicker({
	lang:'en',
	timepicker:false,
	format:'d-m-Y',
	formatDate:'d-m-Y',
	scrollInput:false,
	yearEnd : 2100,
    maxDate:todate,
		onShow: function () {
			  $("#datetimepicker").val("");
		}
   });
   
    updateElementVisibility();

    // Attach event listener to radio buttons
    $('input[name=requestType]').change(function() {
        updateElementVisibility();
	 });
   
});



      // Function to show/hide "From Date" and "To Date" inputs
    function updateElementVisibility() {
        var reqType = $('input[name=requestType]:checked').val();

        if (reqType === "0") {
            $("#form_date").hide();
            $("#datetimepicker").hide();
            $("#hide_req_button").show();
            $("#selected_period").hide();  // Hide the selected_period row
        } else if (reqType === "1") {
            $("#form_date").show();
            $("#hide_req_button").show();
            $("#datetimepicker").show();
            $("#selected_period").show();  // Show the selected_period row
        }
    }

function downloadDemand(dwnReqId,requestType, fromDate, toDate ){
	 if(dwnReqId != null && requestType != null ) {
		confirm("Do you want to download this file");
	 
	$("#form_date").val(fromDate);
	$("#to_date").val(toDate);
	$("#dwnReqId").val(dwnReqId);
	$("#demand").val(requestType);
	$("#saoDownloadReqForm").submit();	
	}
	else {
		return false;
	}
}


function saveSAODownloadRequest(){
   $("#hide_req_button").hide();
  var reqType=$('input[name=requestType]:checked').val();
  
  if(reqType == "0"){
    $("#form_date").val();
    $("#to_date").val();
  }else if(reqType == "1"){
      $("#form_date").val();
      var selectedToDate=$("#datetimepicker").val();
      if(null!=selectedToDate && selectedToDate.trim() == ""){ 
	       alert("Please select To Date.");
	       return false;
      }else{
        $("#to_date").val(selectedToDate);
      }
  }
  
  var fromDate= $("#form_date").val();
  var toDate = $("#to_date").val();
  
  $("#form_date1").val(fromDate);
  
  $.ajax({
   url: $("#context_path").val()+ "sao/checkSAORecordCount",
   type :"post",
   data: {fromDate :fromDate, toDate:toDate},
   async:false,
   
   success: function(result){
	   
	  
	   
	 if(parseInt(result)> 0){	 
	     if(confirm("Total number of Records: "+result+"\n Do you wish to submit your request ?")){
		  var formAction = $("#saoDownloadReqForm")[0].action;
		  $("#saoDownloadReqForm").attr("action", formAction.replace("/page", ""));
		  $("#saoDownloadReqForm").submit();
	     }else{
	       $("#hide_req_button").show();
	     } 
		    
	  }else{
			alert("No demand data found for given time frame.");
			return false;           	 
      }
   }});
  
}