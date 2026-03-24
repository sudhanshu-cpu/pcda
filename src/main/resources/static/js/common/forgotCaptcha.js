
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


