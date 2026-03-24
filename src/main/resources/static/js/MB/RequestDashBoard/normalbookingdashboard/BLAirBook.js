function viewUser()
 {
	  if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
    ajaxRequestFace = new LightFace.Request(
    {
		width: 650,
		height: 600,
		url:  $("#context_path").val()+"mb/getUserAgreement",
		
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		request: { 
			
			method: 'get'
		},
		title: 'User View'
	});
    ajaxRequestFace.open();
 }

function govtOrder()
 {
	  if(parseInt($("#countdown").val())<5){
	  alert("Your Session Has Been Expired. Kindly Re-login");
	   return false;
	   }
    ajaxRequestFace = new LightFace.Request(
    {
		width: 750,
		height: 600,
		url:  $("#context_path").val()+"mb/getGovtOrder",
		
		buttons: [
			{ title: 'Close', event: function() { this.close(); } }
		],
		request: { 
			
			method: 'get'
		},
		title: 'Government Order'
	});
    ajaxRequestFace.open();
 }

	function hideShowTr(index,email,mobileNo)
	{
		
	    var chboxs = document.getElementsByName("leadCheck");
	    
	    for(var i=0;i<chboxs.length;i++) 
	    { 
	        if(chboxs[i].checked)
	        {
	        	if(index!=i)
	        	{
	        		chboxs[i].checked = false;
	        		$(".trClass"+i).hide();
	        	}
	        	else
	        	{
	        		$(".trClass"+index).show();
	        		$("#travellerEmail"+index).val(email);
	        		$("#travellerContact"+index).val(mobileNo);
	        	}
	        }
	        else
	        {
	        	$(".trClass"+i).hide();
	        }
	    }
	    
	  
	    for(var i=0;i<chboxs.length;i++) 
	    { 
	        if(chboxs[i].checked)
	        {
	        	$("#leadIndex").val(index);
	        	$("#leadMobile").val(mobileNo);
	        	$("#leadEmail").val(email);
	        	
	        	break;
	 	}else{
	        	$("#leadIndex").val("");
	        	$("#leadMobile").val("");
	        	$("#leadEmail").val("");
	        }
	    }
	  
	}



function validateBaggage(){
	 var baggageAllow = $("#isBaggageAllowed").val();
	 
	 if(baggageAllow == 'true'){
	 	$("#baggage_Weight").prop("readonly", false);
	 }else{
	   $("#baggage_confirm").prop("checked", false);
	   alert("Extra baggage booking cannot be done online for this booking. Kindly contact DTS Air Helpline for the same.");
	   return false;
	 }
	 
	}
	
	function isNumericOnlyKey(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
	  if (String.fromCharCode(charCode).match(/[^0-9]/g)){
	  	 	return false;
	  }
     else{
     	 	return true;
   	 }
   }



  function checkEmail(str)
   {
   
 	 	var filter=/^(([^<>()[\]\\.,&;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
		if (filter.test(str))
		    return true
		else {
		    alert("Please enter a valid email address")
		    return false
		}
		
	}


	
	function isValidPhoneNumber(txtMobile)
	{
	var mob = /^[1-9]{1}[0-9]{9}$/;
			
    if (mob.test(txtMobile) == false) {
        alert("Please enter valid mobile number.");
					return false;
     			}     
	 	
    return true;
	}
 

	
function submitTravellerDetail(event)
{
	var leadCheck=document.getElementsByName("leadCheck");
	
	 var fname;
	 var lname; 
	 var fullname;
	
	//alert("tripTypeVal="+tripTypeVal+"|leadCheck length="+leadCheck.length+"|retleadCheck length="+retleadCheck.length);
	var passNo;
	var travellerEmail;
	var travellerContact;
	var flag=0;
	var flagCheck=0;
	
	
	for (var i = 0; i < leadCheck.length; i++)
    {    
    	travellerEmail='';
    	travellerContact='';
    	if (leadCheck[i].checked){
    	flag=1;
    		passNo=$("#Check_"+i).val();
    	flagCheck=1
    	}else
    	flag=0;
    
    	if (flag==1)
    	{
    	if($("#travellerEmail"+i) != null && $("#travellerContact"+i) != null)
    	{
    		travellerEmail = $("#travellerEmail"+i).val();
    		travellerContact = $("#travellerContact"+i).val();
    	     	$("#leadIndex").val(passNo);
	        	$("#leadMobile").val(travellerContact);
	        	$("#leadEmail").val(travellerEmail);
    	 	if(travellerEmail==""){
	    	 		alert("Email address can not be Blank.");
    	 		return false;
		    	}else if(!checkEmail(travellerEmail)){
	    		return false;
	    	}    
	    
    		if(travellerContact==""){
	    	 		alert("Contact number can not be Blank.");
    			return false;
	    	}else if(!isValidPhoneNumber(travellerContact)){    
	    		return false;
	    	}    
    	}else{
			alert("Please Enter Email Id And Contact No.");
			return false;
    	}
    	}
	}
	if(flagCheck==0)
	{
	alert("Please Check One Of The Passenger");
	return false;
	}
    

	var Name = [];
	
   for (var i = 0; i < leadCheck.length; i++)
    {
    fname = $("#travellerFirstName"+i).val();
    lname = $("#travellerLastName"+i).val();
    
    fullname = fname+lname;
    
    // alert('\''+fullname+'\'');
    Name.push(fullname);
    
    
    }
	
	//alert("Array Values is" +Name);
	
	
    var sorted_arr = Name.slice().sort(); 
    var results = [];
    for (var i = 0; i < Name.length - 1; i++) {
        
        if (sorted_arr[i + 1] == sorted_arr[i]) {
        results.push(sorted_arr[i]);
    
         }
      }
	//alert(results);
	if(results!='') {
	alert("Two Passengar Can Not Have Same Name. Please Contact Customer Care" );
	return false;
	
	}
	
	
   
	
	
	    var domInt=$("#domInt").val();
	    if(domInt == 'International'){
	      
	      if($("#baggage_confirm").is(":checked")){
	         if(parseInt($("#baggage_Weight").val())==0){
	           alert("Baggage Weight should be greater than 0 Kg");
	           return false;
	         }
	      }
	    
	      if(!$("#int_confirm_1").is(":checked") || 
	         !$("#int_confirm_2").is(":checked") ||
	         !$("#int_confirm_3").is(":checked") || 
	         !$("#int_confirm_4").is(":checked") || 
	         !$("#int_confirm_4").is(":checked") ){
	         
	         alert("Please select all User Agreement");
			 return false;
	      }
	    }
	    
		if(!$("#confirmCheck").is(":checked")){
			alert("Please select User Airline Agreement");
			return false;
		}
		
		$("#travellerDtlDiv").hide();
	    $("#filters").hide();
		if( $("#popUp")!=undefined){
            $("#popUp").show();
		}
		if(  $("#layer1")!=undefined){
        	 $("#layer1").show();
		}
		
		
		$("#screen-freeze").css("display", "block");
		
		$("#BLBookingDetailForm").submit();
}