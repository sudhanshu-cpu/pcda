package com.pcda.login.model;

import java.math.BigInteger;

import lombok.Data;
@Data
public class PostUpdateSessionModel {
	private BigInteger	userId;
	private String	groupId;
	private String sessionId;
	private int	status;
}
