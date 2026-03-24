package com.pcda.login.model;

import java.math.BigInteger;

import lombok.Data;
@Data
public class PostPreventProcessUpdate {
	private BigInteger loginUserId;
	private String process;
	private String processState;
	private String groupId;
	private int personalId;
	private String actionType;
}
