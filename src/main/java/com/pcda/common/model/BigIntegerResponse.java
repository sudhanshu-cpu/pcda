package com.pcda.common.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class BigIntegerResponse {

	private String errorMessage;
    private int errorCode;
    private BigInteger response;
    private List<BigInteger> responseList;
}
