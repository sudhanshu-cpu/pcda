package com.pcda.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidation {
	
	private static String alphaNumericRegex= "^[0-9a-zA-Z\\s]+$";
	private static String alphaWithSpaceRegex= "^[a-zA-Z\\s]+$";
	private static String NumericRegex= "^[0-9.]+$";
	
	
	public static boolean isEmpty(String inputStr){
			
			if (null == inputStr || "".equals(inputStr.trim()) || "null".equals(inputStr.trim()) ) 
		    {
		   		return true;
		 	}
			return false;
		}
	
	
	public static boolean allowedSpecialCharacters(String inputStr, String regex){
		
		 if(isEmpty(inputStr)){
	    	   return false;  
	       }
		 
			Pattern pattern = Pattern.compile(regex);
			 
			Matcher matcher = pattern.matcher(inputStr);
	   
			    if(matcher.matches() ) 
			    {
			   		return true;
			 	}
			
			return false;
		}
	
	public static boolean IsNumericString(String inputStr){
		
		 if(isEmpty(inputStr)){
	    	   return false;  
	       }
		
		Pattern pattern = Pattern.compile(NumericRegex);
		 
		Matcher matcher = pattern.matcher(inputStr);
   
		    if(matcher.matches() ) 
		    {
		   		return true;
		 	}
		
		return false;
	}
		
	
	public static boolean isWhiteSpaceContains(String str){
		
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(str);
		boolean found = matcher.find();
		return found;
	}
	
	
	
	
	public static boolean checkLength(String input, int length){
		
		 if(isEmpty(input)){
	    	   return false;  
	       }
	
		if(input.length() > length)
			return false;
		else
		return true;
	}
	
	public static boolean checkExactLength(String input, int length){
		
		 if(isEmpty(input)){
	    	   return false;  
	       }
		
		if(input.length() > length || input.length() < length)
			return false;
		else
		return true;
	}
	
	public static boolean alphaNumeric(String inputStr){
       if(isEmpty(inputStr)){
    	   return false;  
       }
		Pattern pattern = Pattern.compile(alphaNumericRegex);
		 
		 Matcher mtch = pattern.matcher(inputStr);
	        if(mtch.matches()){
	            return true;
	        }
	        return false;
	}
	
	public static boolean alphaWithSpace(String inputStr){
	       if(isEmpty(inputStr)){
	    	   return false;  
	       }
			Pattern pattern = Pattern.compile(alphaWithSpaceRegex);
			 
			 Matcher mtch = pattern.matcher(inputStr);
		        if(mtch.matches()){
		            return true;
		        }
		        return false;
	}
	


}
