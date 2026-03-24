package com.pcda.common.model;

import java.math.BigInteger;
import java.util.Set;

import lombok.Data;

@Data
public class GetForgotPassPnoInfo {

private Set<SecurityQuestionModel> securityQuestions;
private String emailId;
private BigInteger userId;

}
