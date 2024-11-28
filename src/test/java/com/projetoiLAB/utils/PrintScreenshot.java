package com.projetoiLAB.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintScreenshot {

    public static void takeScreenshot(String test, String name, ExtentTest extentTest){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String screenshotPath = "evidences/screenshots/" + test + "/";
        String screenshotName = screenshotPath + name + "_" + timestamp + ".png";

        File directory = new File(screenshotPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try{
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);

            File imageFile = new File(screenshotName);
            ImageIO.write(capture, "png", imageFile);

            ExtentManager.getTest().info("Print tirado: " + name, MediaEntityBuilder.createScreenCaptureFromPath("/"+screenshotName).build());

        } catch (Exception e) {
            System.err.println("Erro ao capturar o screenshot: " + e.getMessage());
        }
    }
}
