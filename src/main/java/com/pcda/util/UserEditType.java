package com.pcda.util;



public enum UserEditType {

	EDIT("edit"),                         
	TRANSFER_IN("TransferIn"),                  
	TRANSFER_OUT("TransferOut"),                 
	TRANSFER_IN_AND_REEMPLOYMENT("TransferInAndReemployment"), 
	TRANSFER_OUT_AND_REEMPLOYMENT("TransferOutAndReemployment");
private String displayValue;
	
	private UserEditType(String value) {
		this.displayValue=value;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
