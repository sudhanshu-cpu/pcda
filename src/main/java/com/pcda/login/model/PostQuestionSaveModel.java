package com.pcda.login.model;

import java.math.BigInteger;
import java.util.Map;

import lombok.Data;

@Data
public class PostQuestionSaveModel {
 private BigInteger loginUserId;
 private Map<String,String> answer;
}
