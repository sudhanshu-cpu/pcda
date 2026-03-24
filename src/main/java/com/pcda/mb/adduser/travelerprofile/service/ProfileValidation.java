package com.pcda.mb.adduser.travelerprofile.service;

import com.pcda.mb.adduser.travelerprofile.model.TravelerUser;
import com.pcda.util.CommonUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileValidation {
	
	
	public static String validate(TravelerUser travelerUser) {
		
		
		if (travelerUser.getServiceId().equalsIgnoreCase("1000019") && !validateNavyCivilianPrsnlNo(travelerUser.getTravelerProfile().getServiceNo())){
			return "Please Enter Correct Service No.";

		}
		return "OK";
	}
	private static boolean validateNavyCivilianPrsnlNo(String navyCivilianPersonalNo) {
		
		  char[] alpha = { 'A', 'B', 'F', 'H', 'K', 'N', 'R', 'T', 'W', 'Y', 'Z' };
			char alphaNo = navyCivilianPersonalNo.charAt(navyCivilianPersonalNo.length()-1);
			int sum = 0;
			
			if(CommonUtil.isEmpty(navyCivilianPersonalNo)) {
				return false;
			}
			
			for (int index = 0; index < navyCivilianPersonalNo.length()-1; index++) {
				sum += navyCivilianPersonalNo.charAt(index);
			}
			
			return alpha[sum % 11] != alphaNo;

		
	}

}
