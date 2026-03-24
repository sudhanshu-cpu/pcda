package com.pcda.common.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CodeHeadModel {
	
	//@NotBlank(message = "Service option should not be dis-selected")
		private String service;

		private String serviceId;

		private String codeheadId;

		private String categoryId;

//		@NotBlank(message = "Category option should not be dis-selected")
		private String category;

		@NotBlank(message = "Travel type option should not be dis-selected")
		private String traveltype;

		@NotNull(message = "Rail code should not be blank")
		@Pattern(regexp = "^[0-9/]+$", message = "Only digits are allowed")
		private String railCodehead;

		@NotNull(message = "Air code should not be blank")
		@Pattern(regexp = "^[0-9/]+$", message = "Only digits are allowed")
		private String airCodehead;

		@NotNull(message = "DA code should not be blank")
		@Pattern(regexp = "^[0-9/]+$", message = "Only digits are allowed")
		private String daCodehead;

		@NotNull(message = "International code should not be blank")
		@Pattern(regexp = "^[0-9/]+$", message = "Only digits are allowed")
		private String intlCodehead;

		@NotBlank(message = "Please enter the description")
		@Pattern(message = "Only alphabets are allowed", regexp = "^[a-zA-Z ]+$")
		private String description;

		@NotBlank
		private String minorhead;
		
		private Long loginUserId = 0L;

		private String status;

		private String remarks;

}
