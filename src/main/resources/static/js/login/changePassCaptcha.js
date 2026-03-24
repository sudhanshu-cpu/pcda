
$( document ).ready(function() {
    createCaptcha();

 $("#refreshLink").on('click', function() {
			createCaptcha();
         });

});

function createCaptcha() {

         $('#captchaImage').attr('src', $("#context_path").val()+'fpwd/captcha?_=' + Math.random());
 
}


function validateCaptcha(input) {
	   
	  let flag=false;
	
	
	  $.ajax({
            url: $("#context_path").val()+"fpwd/validateCaptcha",
            type: 'POST',
            async: false,
            data: {
                captchaInput: input,
                
            },
            success: function(status) {
			 flag= status;
             
				
            },
          
        });
       
 return flag;
}


function validatePassword() {
    var newPassword = document.getElementById('newPwd').value;
    var oldPassword= document.getElementById('oldPwd').value;
    var minNumberofChars = 8;
    var maxNumberofChars = 20;
    var regularExpression  = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,20}$/;
   
   
    if(oldPassword.length < minNumberofChars) {
		  alert("Old Password Must Contain Atleast 8 characters");
		  blankField(); 
        return false;
    }
    if(oldPassword.length > maxNumberofChars){
		 alert("Old password Maximum Lenght is 20 characters");
		 blankField();  
        return false;
	}
   
    if(newPassword.length < minNumberofChars) {
		  alert("New Password Must Contain Atleast 8 characters");
		   blankField();  
        return false;
    }
    if(newPassword.length > maxNumberofChars){
		 alert("New password Maximum Lenght is 20 characters");
		  blankField();  
        return false;
	}
    if(!regularExpression.test(newPassword)) {
        alert("New Password should contain atleast one number and one special character and one Capital Alphabet");
        blankField();  
        return false;
    }
     if(!regularExpression.test(oldPassword)) {
        alert("Old Password should contain atleast one number and one special character and one Capital Alphabet");
       blankField();  
        return false;
    }
    return true;
}

function blankField(){
	 document.getElementById("newPwd").value="";
     document.getElementById("oldPwd").value="";
     document.getElementById("conformNewPwd").value="";  
}

function passwdCompair()
{
var newPwd=document.getElementById("newPwd").value;
var oldPwd=document.getElementById("oldPwd").value;
var confirmPwd=document.getElementById("conformNewPwd").value;

 

if(oldPwd==""){
alert("Please Enter Old Password");
return false;
}
if(newPwd==""){
alert("Please Enter New Password");
return false;
}
   
if(newPwd==oldPwd){
alert("New Password And Old Password Can't Be Same.");
$("#newPwd").val("");
$("#oldPwd").val("");
$("#conformNewPwd").val("");
return false;
}
if(confirmPwd==""){
alert("Please Enter Confirm Password");
return false;
}
if(newPwd!=confirmPwd){
alert("New Password And Confirm Password Not Matched.");
$("#newPwd").val("");
$("#conformNewPwd").val("");
return false;
}
if(validatePassword()){
	
	var oldHash =CryptoJS.AES.encrypt(oldPwd,"Hidden Pass");
	var newPassHash =CryptoJS.AES.encrypt(newPwd,"Hidden Pass");
	var confirmPassHash=CryptoJS.AES.encrypt(confirmPwd,"Hidden Pass");
	document.getElementById("oldPwd").value=oldHash;
	document.getElementById("newPwd").value=newPassHash;
	document.getElementById("conformNewPwd").value=confirmPassHash;
return true;
}
}

function doGenerate(){
	var result = passwdCompair();
	
	 if(result){
		 
		  let captchaInput = $("#cpatchaTextBox").val().trim();

	    if ( captchaInput == "") {
		 alert("Please enter captcha code");
		 createCaptcha();
		 decInputs();
		 return false;	
		}
		
		 if(validateCaptcha(captchaInput)){
		   document.getElementById("passwrdChangeForm").submit();
		 }else{
		    alert("Invalid Captcha. Please try again.");
             createCaptcha();
             decInputs();
             $("#cpatchaTextBox").val("");
              return false;
		 }
	
	}
}

function decInputs(){
	
	  if($("#oldPwd").val().trim()!==''){
		  $("#oldPwd").val(CryptoJS.AES.decrypt($("#oldPwd").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8)); 	
		}
		 if($("#newPwd").val().trim()!==''){
		  $("#newPwd").val(CryptoJS.AES.decrypt($("#newPwd").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8)); 	
		}
		 if($("#conformNewPwd").val().trim()!==''){
		  $("#conformNewPwd").val(CryptoJS.AES.decrypt($("#conformNewPwd").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8)); 	
		}
	
}


