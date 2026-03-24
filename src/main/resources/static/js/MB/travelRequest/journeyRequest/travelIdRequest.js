function getTravelId(travelTypeId) {
 	
 	 $('#travelID').children('option:not(:first)').remove();
 	 $('#travelID').append($('<option>',{value: "1",text : "New" }));
 	
 	$.ajax({
			url: $("#context_path").val()+"mb/getTravelIDs",
			type: "get",
	      	data: "personalNo="+$("#personalNo").val()+"&travelTypeId="+ travelTypeId ,
	      	dataType: "json",
	      
	      	beforeSend: function() 
	      	{
	        	var calHeight=document.body.offsetHeight+20;
		        $("#screen-freeze").css({"height":calHeight + "px"} );
		        $("#screen-freeze").css("display", "block");
		    },
		 
		  	complete: function(){
		  		$("#screen-freeze").css("display", "none");
		  	},
		  	
		  	success: function(msg)
	      	{
	      		
	      		 var travelIdCount=0;
	      		 
					$.each(msg, function(index,obj){
				 	
				 	var travelStatus = obj.travelStatus;
				 	 if(travelStatus == '1'){
				 	 	travelIdCount=travelIdCount+1;
				 	 	
					 var travelID = obj.traveld;
					
					 $('#travelID').append($('<option>',
					  {
					  value: travelID,
					  text : travelID
					  }));
				 	 }
					 
				});	
				
				 if((travelTypeId == "100002" || travelTypeId == "100009" || travelTypeId == "100010") && travelIdCount > 1){
	      		 	$("#travelID option[value='1']").remove();
	      		 }
	      	}
			
			
		});
			
 }
 
 
 function getTravelRequestIds(){
 	
    var travelID=$('#travelID').val();
	
    if(travelID != "" && travelID != "1"){
	
	$.ajax(
	{
      url: $("#context_path").val()+"mb/getTravelRequestID",
      type: "get",
       async: false,
      data: "travelID="+travelID ,
      dataType: "json",
      beforeSend: function() 
	      	{
	        	var calHeight=document.body.offsetHeight+20;
		        $("#screen-freeze").css({"height":calHeight + "px"} );
		        $("#screen-freeze").css("display", "block");
		    },
		 
	  	complete: function(){
	  		$("#screen-freeze").css("display", "none");
	  	},
			  
      success: function(msg){     
                       
            $("#travelDaCounts").val('0')
           
            
            var travelId =  msg.traveld;
             
			var startDate = msg.travelStartDate;
			var endDate =  msg.travelEndDate;
			var authorityNo =   msg.authorityNo;
			var authorityDate =  msg.authorityDateStr;
			var travelDaCounts=  msg.travelDaCounts;
			
			$('#travelEndDate').val(endDate);
		    $('#travelStartDate').val(startDate);
			$("#authorityNo").val(authorityNo);
			$("#authorityDate").val(authorityDate);
			$("#travelDaCounts").val(travelDaCounts);
			
			$("#authorityNo").css("pointer-events", "");
			$("#authorityDate").css("pointer-events", "");
				
			 }
	   });
  
    }else{
    	
    	$("#travelDaCounts").val('0')
    	$('#travelEndDate').val("");
	    $('#travelStartDate').val("");
		$("#authorityNo").val("");
		$("#authorityDate").val("");
		
		$('#travelStartDate').css("pointer-events", "");
		$('#travelEndDate').css("pointer-events", "");
		$("#authorityNo").css("pointer-events", "");
		$("#authorityDate").css("pointer-events", "");
    }   
    
}

function validateTravelDate(){

 	var travelStartDate=$('#travelStartDate').val();
	var travelEndDate=$('#travelEndDate').val();
	var travelTypeId=$('#TravelTypeDD').val();
	var flag=true;
	
	if(travelTypeId != '100004' && travelTypeId != '100012'){
	
	 	$.ajax({
		    url: $("#context_path").val()+"mb/getTravelIDs",
			type: "get",
			async: false,
	      	data: "personalNo="+$("#personalNo").val()+"&travelTypeId="+ travelTypeId,
	      	dataType: "json",
	      
	      	beforeSend: function() 
	      	{
	        	var calHeight=document.body.offsetHeight+20;
		        $("#screen-freeze").css({"height":calHeight + "px"} );
		        $("#screen-freeze").css("display", "block");
		    },
		 
		  	complete: function(){
		  		$("#screen-freeze").css("display", "none");
		  	},
		  	
		  	success: function(msg)
	      	{
	         var travelID=$("#travelID").val();
		   			     
		     if(travelID !=""){

			   $.each(msg, function(index,obj){

				     var tOldStartDate = this.startDateStr;
					 var tOldEndDate =   this.endDateStr;
					 var oldTravelID = this.traveld;

					 if(travelID != oldTravelID){
					 var startDateDiff=days_between(travelStartDate,tOldStartDate);
				   	 var endDateDiff=days_between(travelEndDate,tOldStartDate);
				   	 
				   	 var startDateDiff1=days_between(travelStartDate,tOldEndDate);
				   	 var endDateDiff1=days_between(travelEndDate,tOldEndDate);
				   	 
						 if((travelTypeId == '100002' && startDateDiff1==0) || (startDateDiff>0 && endDateDiff>0) || (startDateDiff1<0 && endDateDiff1<0)){ }
				     else{
					     	  if(travelTypeId == '100003'){
					     	  	if(confirm("You already have a Travel Id "+oldTravelID+" for (CV) in this duration. Please ensure that duplicate tickets are not booked for any traveller.")){}
					     	  	else{
					     	  		flag=false;
						            return flag;
					     	  	}
					     	  }else{
					       alert("Another Travel Id: "+oldTravelID+" already exists within same travel dates.");
				       flag=false;
					       return flag;
					   	 }
				   	 }
					 }
			   	 });
			   	 
		       }
	      	}
		});
   } 
 	 return flag;
}
