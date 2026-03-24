package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class TADAExpenseResponse {

	private String errorMessage;

	private int errorCode;

	private List<TaDaExpense> responseList;

	private TaDaExpense response;
}
