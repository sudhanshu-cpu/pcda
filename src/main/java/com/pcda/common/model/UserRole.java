package com.pcda.common.model;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class UserRole implements Serializable{

	private static final long serialVersionUID = 6992621166694530475L;

	private BigInteger roleId;

}
