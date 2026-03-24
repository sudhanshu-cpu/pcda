
$(document).ready(function() {
	
		$('#air_mm_data_info_list').DataTable( {
        "paging":   false,
        "info":     false,
        "searching": true, 
        "language": {
		    "search": "<b>Search Personal No</b> :"  
		  },
         "order": [[0, 'asc']],
         
         'columnDefs': [
                       {
					   'targets': 0,
					   'searchable': false
					   },
                       {
					   'targets': 1,
					   'searchable': false,
					   'orderable': false,
					   'width': '1%',
					   'className': 'dt-body-center',
					   'render': function (data, type, full, meta){
					      var html=$.parseHTML(data);
					      var fullData=$(html).val().split("##");
					      var dataVal=fullData[0];
					      var type=fullData[1];
					      if(type == 1){
					        return '<input type="checkbox" name="selectForAirMM" id="selectForAirMM'+dataVal+'" value="'+dataVal+'" onclick="showAndHideCommentsForAirMM('+dataVal+');"/>';
					      }else{
					        return '<input type="checkbox" name="selectForAirMM" checked="checked" disabled="disabled" id="selectForAirMM'+dataVal+'" value="'+dataVal+'" onclick="showAndHideCommentsForAirMM('+dataVal+');"/>';
					      }
					    
					      }
   					  },
   					  {
               				 "targets": 2,  
             		   "searchable": true  
           			},
   					  {
					   'targets': 3,
					   'searchable': false,
					   'orderable': false
					  },
   					  {
					   'targets': 4,
					   'searchable': false,
					   'orderable': false
					  },
					  {
					   'targets': 5,
					   'searchable': false,
					   'orderable': false
					  },
   					  {
					   'targets': 6,
					   'searchable': false,
					   'orderable': false,
					   'width': '40%',
					   'hight': '10%'
					  }
   					  ]

      });
      
      if (!$('.dataTables_filter').length) {
        $('#air_mm_data_info_list_wrapper').prepend(
            '<div class="dataTables_filter" style="text-align: left; width: 100%; margin-bottom: 10px;"><label><b>Search Personal No</b>: <input type="text" id="customAirSearchBox"></label></div>'
        );

        // Bind manual search event to input field
        $('#customAirSearchBox').on('keyup', function () {
			val =this.value;
           $('#air_mm_data_info_list').DataTable().search(this.value).draw();
           
        });
    }

// capture search event
	$('#air_mm_data_info_list').on( 'search.dt', function () {
	
	  var val = $('.dataTables_filter input').val();
	  var size= val.trim().length;
	  if(parseInt(size) == 0){
	   $("#btn_air_master_missing").show();
	  }else{
	   $("#btn_air_master_missing").hide();
	  } 
	  
	});
    
});


function backToAirDemandReqPage(){
  $("#airDemandReqPageForm").submit();
}
function showAndHideCommentsForAirMM(userId){
	
 if($("#selectForAirMM"+userId).is(':checked')){
    $("#comments_"+userId).prop("disabled", "");
    $("#comments_"+userId).val("");
 }else{
    $("#comments_"+userId).prop("disabled", "disabled");
    $("#comments_"+userId).val("");
 }
 
}

function submitReqForAirMM(){
   
    $("#sentReqForAirMMForm").html("");
    var count=0;
    var checkVal= true;
    var mmPersonalNo="";
   var airDwnReqId = $("#dwnAirDmndReqIdMm").val();
    $('input:checkbox[name=selectForAirMM]:checked:not(:disabled)').each(function() {
               count=count+1;
			   var userId=$(this).val();
			   var comments=$("#comments_"+userId).val().trim();
			   var personalNo=$("#personalNoMM"+userId).val();
			   if(comments == ""){
			     alert("Plese put comments for "+personalNo); 
			     checkVal=false;
			     return false;
			   }
			    
			   var hiddenHtml="<input type='hidden' name='dataForMM' value='"+personalNo+"##"+comments+"##"+airDwnReqId+"' />"; 
			   
			   $("#sentReqForAirMMForm").append(hiddenHtml);
			   mmPersonalNo=mmPersonalNo+count+". "+personalNo+"  "+comments+"\n";
			});

	if(checkVal){
			if(count == 0){
			   alert("Plese select personal number for Master Missing."); 
			   return false;
			}else{
			   if(confirm("You have selected below Personal No for master missing \n"+mmPersonalNo+"\n Do you want to send request for Master Missing!")){
					$("#sentReqForAirMMForm").submit();
				}
			}
	   }	
  	
}

function submitNoReqForAirMM(){
 var checkLen=$('input:checkbox[name=selectForAirMM]:checked').length;
 if(checkLen > 0){
   alert("Master Missing data has been selected, Please unselect the same if you wish to submit the data with no master missing records.");
   return false;
 }else{
  if(confirm("Are you sure there are no Records for Master Missing!")){
	$("#sentNoReqForAirMMForm").submit();
  }
 } 

}