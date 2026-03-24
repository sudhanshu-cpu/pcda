package com.pcda.mb.adduser.changeservice.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostChangeServiceModel {

private BigInteger	loginUserId	=BigInteger.ZERO;
	private String loginUserUnitId;
private String	loginUserUnitName;	
	private String loginUserServiceId;	
private String	reason;	
private String	categoryId;	
private String	rankId;	
private String	payAcOff;	
private String	personalNumber;
private String	airAcOff;	
private String	levelName;	
private String	levelId;	
	
	
}
