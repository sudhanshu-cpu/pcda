package com.pcda.login.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class GetSessionModel {

	private Date xnsDate;

	private BigInteger userId;

	private String groupId;

	private String sessionId;

	private Integer sessionState;

	private Date sessionCreationTime;

	private Date sessionLastModTime;

	private Date sessionExpirationTime;

}
