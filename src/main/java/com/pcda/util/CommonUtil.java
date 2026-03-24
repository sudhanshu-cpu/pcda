package com.pcda.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpHeaders;

import com.pcda.login.service.LoginService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

	public static final Pattern FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+$");

	public static String getChangeFormat(String date, String inputFormat, String outputFormat) {

		String changedDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
		try {
			Date dat = sdf.parse(date);
			sdf = new SimpleDateFormat(outputFormat);
			changedDate = sdf.format(dat);
			return changedDate;
		} catch (ParseException e) {
			DODLog.printStackTrace(e, CommonUtil.class, LogConstant.COMMON_LOG);
		}
		return changedDate;
	}




	public static String formatDate(Date dateFormat, String format) {
		
		DateFormat formatter = new SimpleDateFormat(format);
		String date = "";
		if (dateFormat != null) {
			date = formatter.format(dateFormat);
		}
		
		return date;
	}

	public static Date formatString(String date, String format) {
		Date date2= null;
		
		try {
			if(date==null || date.equals("") || date.isBlank()) {
				return null;
			}else {
			date2 = new SimpleDateFormat(format).parse(date);
			}
		} catch (ParseException e) {
			DODLog.printStackTrace(e, CommonUtil.class, LogConstant.COMMON_LOG);
			return null;
		}
		
		return date2;
	}


	public static String getStringParameter(String paramValue,String defaultValue){

		if(paramValue==null || "".equals(paramValue.trim()) || "null".equals(paramValue.trim())) {
			return defaultValue;
		}
		if(paramValue.isBlank()) {
			return defaultValue;
		}
		return paramValue.trim();

	}
public static boolean isEmpty(String inputStr){

		if (null == inputStr || "".equals(inputStr.trim()) || "null".equals(inputStr.trim()) ) {
	   		return true;
	 	}
		return false;
	}

	public static String getStationCode(String stnName) {
		String stnCode = null;
		try {
			if (stnName.indexOf("(") != (-1)) {
				stnCode = stnName.substring(stnName.indexOf("(") + 1, stnName.indexOf(")"));
			}else {
				stnCode = stnName;
			}
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, CommonUtil.class, LogConstant.COMMON_LOG);
		}
		return stnCode;
	}

	public static String removeSpace(String names) {
		if (null != names && names.length() > 0) {
			Pattern ptn = Pattern.compile("\\s+");
			Matcher mtch = ptn.matcher(names);
			return mtch.replaceAll(" ");
		} else {
			return names;
		}

	}

	public static String getDecryptText(String secret,String cipherText) {
		String decryptedText ="";
		try {
			
		if (cipherText == null || cipherText.isBlank()){
			return decryptedText;
		}

		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = generateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

		}catch(Exception e ) {
			DODLog.printStackTrace(e, LoginService.class, LogConstant.COMMON_LOG);
		}
		return decryptedText;

	}
	public static byte[][] generateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch(Exception e ) {
            DODLog.printStackTrace(e, LoginService.class, LogConstant.COMMON_LOG);
			return new  byte[2][];
		} finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}


	public static String validateFilename(String dwnFilePath) {
		String filename = Paths.get(dwnFilePath).getFileName().toString();
		if (!FILENAME_PATTERN.matcher(filename).matches()) {
			throw new FileDownloadException("Invalid file name!!");
		}
		return filename;
	}


	public static void validateFilePath(Path filePath, String filename)  {
		if (filePath == null) {
			throw new FileDownloadException("File not found: " + filename);
		}
	}

	public static void validateFile(File file, String filename) {
		if (!file.exists() || file.isDirectory()) {
			throw new FileDownloadException("File not found: " + filename);
		}
	}


	public static HttpHeaders prepareHeaders(File file) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return headers;
	}
	
	public static Map<String, Object> getYearAndMonthData() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int startYear = 2024;

		String[] monthNames =  {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
				"Nov", "Dec"};
		Map<String, String> monthMap = new LinkedHashMap<>();
		for(int index=0 ; index < monthNames.length ; index++) {
			monthMap.put(String.format("%02d", index+1), monthNames[index]);
		}
		List<Integer> years = new ArrayList<>();
		for (int currentYear = startYear; currentYear <= year; currentYear++) {

			years.add(currentYear);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("month", monthMap);
		result.put("year", years);

		return result;
	}


}
