
$(document).ready(function() {
	
	$.validator.addMethod(
		"regex",
		function(value, element, regexp) {
			return this.optional(element) || regexp.test(value);
		},
		"Please enter valid details."
	);



	$("#changeServiceForm").validate({

		rules: {
			
			personalNo: {
				required: true,
				regex: /^[a-zA-Z0-9]*$/

			},

		},
		messages: {
			personalNo: {
				required: "Please enter personal No",
				regex: "Special Character are not allowed"
			},

		},
		errorElement: "em",
		errorPlacement: function(error, element) {
			// Add the `help-block` class to the error element
			error.addClass("help-block");

			if (element.prop("type") === "checkbox") {
				error.insertAfter(element.parent("label"));
			} else {
				error.insertAfter(element.parent().last());
			}
		},
		highlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents(".errorMessage").addClass("has-success").removeClass("has-error");
		}
	});

	$("#submitBtn").click(function() {
		browsePersonal();
});

});











