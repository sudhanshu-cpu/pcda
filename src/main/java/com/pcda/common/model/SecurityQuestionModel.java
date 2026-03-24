package com.pcda.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityQuestionModel implements Serializable{
	
	private static final long serialVersionUID = 8131156819753420408L;
	
	private Integer seqNumber;
	private String question;
	private String answer;
	

}
