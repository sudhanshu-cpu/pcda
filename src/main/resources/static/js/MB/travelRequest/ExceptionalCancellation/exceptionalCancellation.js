

function checkbox(){
 
	var checkboxArray = $("input[name=recordCheck]");
	var checkboxArraylen = checkboxArray.length;
	
	if(checkboxArraylen>0){
		for(var i=1;i<=checkboxArraylen;i++){
			if($("#checkBox"+i).is(":checked")){
				$("#isOfficial"+i).prop("checked",true);

			}else{
				$("#isOfficial"+i).prop("checked",false);
			}

			}

		}
	}

function checkCheckBox(){
	var checkboxArray = $("input[name=recordCheck]");
	var checkboxArraylen = checkboxArray.length;
	if(checkboxArraylen>0){
		for(var i=1;i<=checkboxArraylen;i++){
			if($("#checkBox"+i).is(":checked")){
				$("#isOfficial"+i).prop("checked",true);
				return true;
			}else{
				$("#isOfficial"+i).prop("checked",false);
			}

			}
		 return false;
		}
}

  function submitForm(event){

 let result = checkCheckBox();


if(!result){
	alert("please Select One CheckBox ");
	event.preventDefault();
}
	  	var checkboxArray = $("input[name=recordCheck]");
	var checkboxArraylen = checkboxArray.length;
	 
	if(checkboxArraylen>0){
   
for(var i=1;i<=checkboxArraylen;i++){

if($("#checkBox"+i).is(":checked")){
	 
let isOfficial =$("#isOfficial"+i).val();
let isGovtOfficial =$("#isOnGovtInt"+i).val();
let reason =$("#cancelReason").val().trim();
if(isOfficial==null || isOfficial==''){
   
  alert("please Select On Official Ground");
   event.preventDefault();
}
  
if(reason == null || reason==''){
	 alert("please Enter Reason");	
	 event.preventDefault();
}
	  
var alphaNumRegEx = /^[a-z A-Z 0-9 ]*$/;

if(!alphaNumRegEx.test(reason)){
   alert("Special characters not allowed in Reason");	
	 event.preventDefault();
}
	  

if(isOfficial!=null && isGovtOfficial!='' && reason!=null && reason!='' && checkbox!=null && !checkbox==''){
	$("#NoOfCheckBox").val(checkboxArraylen);
     $("#bookingDetails").submit();
}

			}
	
}
  

 
  
}

}



