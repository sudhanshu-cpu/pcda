var chckArr=[];

function decryptPNo(){
	var prsnlNo=$("#personalNumber").val();
	
	if(prsnlNo!=''){
		var encryptPNo = CryptoJS.AES.encrypt(prsnlNo,"Hidden Pass");
		$("#personalNumber").val(encryptPNo);
}
}

function checkbox(i){
			
			if($("#checkBox"+i).is(":checked")){
				
				$("#isOfficial"+i).prop("checked",true);
				chckArr.push(i);
			}else{
				
				$("#isOfficial"+i).prop("checked",false);
				
				let index = chckArr.indexOf(i);
                 if (index > -1) { 
                   chckArr.splice(index, 1); 
			}
          
		
		}
		
}


function submitForm(){
var ticketType=$("#tktType").val();
 let result = 0;
	var checkBox = $("input[type='checkbox']");
   
	checkBox.each(function() {
		if ($(this).prop('checked')) {
			result++;
		}else if(ticketType=='ITICKET'){
				alert("In Case Of I-Ticket All Passenger Should be Selected");
		        return false;		
		}
	});

if(result == 0){
	alert("Please Select One CheckBox ");
	 return false;
    }
	  
var checkboxArraylen = checkBox.length;
let childFlag=false; 
let adultcount=$("#bookAdultCount").val();
let passCheckedArr=[];
let chkdCount=0;

 if(checkboxArraylen>1){
	 
 checkBox.each(function() {
		if($(this).prop('checked') && $(this).val()>12){
		  passCheckedArr.push(chkdCount++);
			}
		if(!$(this).prop('checked') && ( $(this).val()>5 && $(this).val()<=12)){
			childFlag=true; 
			}
			
			
			
		 });


if(adultcount==passCheckedArr.length && childFlag==true) {
	alert("Child should not left alone for  cancel.");
		return false;
}

//if(childFlag==false &&  passCheckedArr.length==0){
//	alert("Select Adult Passenger along with child");
//	return false;
//}
			
}

var ttlNopxn=checkBox.length;
var cancelofficial;
var cancheckBox;
var cancelChkBoxID; 
var off;	


        
for(var j=1;j<=ttlNopxn;j++){
	
			cancheckBox=$("#checkBox"+j);
			
			if(cancheckBox.is(":checked")){
			cancelofficial=$("#isOfficial"+j).val();	
				break;
			}
		}
		
		
		
		for(var i=1;i<=ttlNopxn;i++){
    		cancelChkBoxID=$("#checkBox"+i);
    		
    		if(cancelChkBoxID.is(":checked"))
    		{
				
		   var radioGroup = document.getElementsByName('isOfficial'+ i);
		 
		    for (let i = 0; i < radioGroup.length; i++) {
			
            if (radioGroup[i].checked) {
               off=radioGroup[i].value;
               
            }
            
        }
		
    	if(cancelofficial==off){
			
		   }else{
			alert("On Official Ground Should Be Same For All Selected Passenger");
			 return false;
			
		}	
		}
		
		
    }  	
	 
   

let reason =$("#cancelReason").val().trim();

   var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;
  
if(reason == null || reason==''){
	 alert("Please Enter Reason For Cancellation");	
	return false;
}else{
	  
if(alphaNumRegEx.test(reason)){
	$("#checkBoxArr").val(chckArr);
	
     $("#bookingDetails").submit();
			}else{
				alert("Special characters not allowed in Reason For Cancellation");
				$("#cancelReason").focus();
				return false;
			}
}


  

}






