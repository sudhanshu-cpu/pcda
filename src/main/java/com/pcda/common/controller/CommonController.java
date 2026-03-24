package com.pcda.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pcda.mb.finalclaim.claimrequest.controller.FinalClaimController;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.FileDownloadException;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Controller
@RequestMapping("/com")
public class CommonController {

	
    public static final String FILE_PATH_PREFIX = "/data/";

	@GetMapping("/downloadFile")
    public ResponseEntity<Object> downloadFile(String dwnFilePath) throws FileDownloadException {

        try {
        	DODLog.info(LogConstant.COMMON_LOG, CommonController.class, " dwnFilePath :::: " + dwnFilePath);
        	if(dwnFilePath==null || dwnFilePath.isBlank()) {
        		throw new FileDownloadException("Invalid file  path: ");
        	}
			boolean validateFilePath = dwnFilePath.startsWith(FILE_PATH_PREFIX);
			if (!validateFilePath) {
				throw new FileDownloadException("Invalid file download path: " + dwnFilePath);
				}

			
            String filename = CommonUtil.validateFilename(dwnFilePath);
            Path baseDirPath = Paths.get(dwnFilePath).normalize();
			CommonUtil.validateFilePath(baseDirPath, filename);
			File file = baseDirPath.toFile();
            CommonUtil.validateFile(file, filename);

            

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = CommonUtil.prepareHeaders(file);

			String contentType = Files.probeContentType(baseDirPath);
            if (contentType == null) {
			 contentType = "application/octet-stream";
		  }

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            DODLog.printStackTrace(e, CommonController.class, LogConstant.COMMON_LOG);
            throw new FileDownloadException("Error occurred while downloading file : ");
        } catch (Exception ex) {
			DODLog.info(LogConstant.COMMON_LOG, CommonController.class, "Error occurred while downloading file: " + ex.getMessage());
            throw new FileDownloadException("Error occurred while downloading file: ");
        }
	}



	@PostMapping("/uplaodFile")
	public ResponseEntity<String> uplaodFile(@RequestParam("file") MultipartFile multipartFile) {
		String fileStoragePath = "";
		String fileName = multipartFile.getOriginalFilename();
		fileStoragePath = PcdaConstant.FILE_CONSTANT_PATH;
		String currentTimeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		fileStoragePath = fileStoragePath.concat(currentTimeStamp);
		File file = new File(fileStoragePath);
		fileStoragePath = fileStoragePath.concat("/") + fileName;
		if (file.mkdirs()) {
			try (FileOutputStream outputStream = new FileOutputStream(new File(fileStoragePath))) {
				multipartFile.transferTo(new File(fileStoragePath));
			} catch (Exception e) {
				DODLog.printStackTrace(e, CommonController.class, LogConstant.COMMON_LOG);
				fileStoragePath = "";
			}
		}
		return ResponseEntity.ok(fileStoragePath);
	}
}
