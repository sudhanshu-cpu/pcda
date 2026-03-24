$(document).ready(function() {
	
	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			return this.optional(element) || regexp.test(value);
		},
		"Please enter valid details."
	);
	
	$("#loginForm").validate({
		wrapper: 'div',
       
		rules: {
			username: {
				regex: /^[A-Za-z0-9]+$/,
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 5
			}
		},
		messages: {
			username: {
				regex: "Special Characters Are Not Allowed",
				required: "Please enter  username",
				minlength: "Your username must consist of at least 2 characters"
			},
			password: {
				required: "Please enter password",
				minlength: "Your password must be at least 5 characters long"
			}

		},
		errorElement: "em",
		errorPlacement: function(error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element);
			}
		},
		highlight: function(element, errorClass, validClass) {
			$(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
		}
	});

});

function onLogin(){
	
	   if($("#password").val().trim()!==''){
		$("#password").val(CryptoJS.AES.encrypt($("#password").val(),"Hidden Pass"));
	}
}