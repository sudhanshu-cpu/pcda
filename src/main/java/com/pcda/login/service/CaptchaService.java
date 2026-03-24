package com.pcda.login.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CaptchaService {



	
	private static final String CHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CAPTCHA_LENGTH = 5;

    
@Async("pcdaAsyncExecutor")
    public CompletableFuture<String> generateCaptchaText() {
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();
        try {
        

        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(CHAR_STRING.length());
            captchaText.append(CHAR_STRING.charAt(index));
        }
        }catch(Exception e) {
        	DODLog.printStackTrace(e, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
        }
        return CompletableFuture.completedFuture(captchaText.toString());
    }

    public void generateCaptchaImage(String captchaText,HttpServletResponse response){
    	
    	try {
    	 int width = 120;
         int heigth = 30;
        BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, width, heigth);

        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.setColor(Color.BLACK);
        graphics.drawString(captchaText, 20, 25);

        // Draw some noise lines for added complexity
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < 3; i++) {
            int x1 = (int) (Math.random() * width);
            int y1 = (int) (Math.random() * heigth);
           int x2 = (int) (Math.random() * width);
            int y2 = (int) (Math.random() * heigth);
            graphics.drawLine(x1, x2, y1, y2);
        }

        graphics.dispose();

        
        response.setContentType("image/png");
        
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
        os.flush();
        os.close();
      
    	}catch(Exception e) {
    		DODLog.printStackTrace(e, CaptchaService.class, LogConstant.VISITOR_LOGIN_LOG);
    	}
      
    }
    @Async("pcdaAsyncExecutor")
    public CompletableFuture<Boolean> validateCaptcha(String captchaInput, String captchaText) {
    	boolean flag= false;
    	if(captchaInput.trim().equals(captchaText)) {
    		flag=true;
    	}
    	DODLog.info(LogConstant.VISITOR_LOGIN_LOG, CaptchaService.class,": validate captcha flag  : "+flag); 
        return CompletableFuture.completedFuture(flag);
    }

}
