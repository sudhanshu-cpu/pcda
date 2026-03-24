
$( document ).ready(function() {
    createCaptcha();
$("#loginForm").submit(function(e){
    e.preventDefault(); // Prevent form submission by default

    let captchaInput = $("#cpatchaTextBox").val().trim();

	 if ( captchaInput == "") {
		 alert("Please enter captcha code");
		 createCaptcha();
		 if($("#password").val().trim()!==''){
		  $("#password").val(CryptoJS.AES.decrypt($("#password").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8)); 	
		}
		 return false;
		 }

		 if(validateCaptcha(captchaInput)){
		 this.submit();
		 }else{
		    alert("Invalid Captcha. Please try again.");
             createCaptcha();
            if($("#password").val().trim()!==''){
              $("#password").val(CryptoJS.AES.decrypt($("#password").val(),"Hidden Pass").toString(CryptoJS.enc.Utf8));
            }
              $("#cpatchaTextBox").val("");
              return false;
		 }
  });


    $("#refreshLink").on('click', function(e) {
            e.preventDefault(); // Prevent default anchor behavior
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


