package com.projetoiLAB;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PageObject {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    ATUTestRecorder gravacao;

    public PageObject(WebDriver webDriver) {
           System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        if (webDriver == null) {
            this.webDriver = new ChromeDriver();
        } else {
            this.webDriver = webDriver;
        }
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    public void recordVideo (String testName) throws ATUTestRecorderException{
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String videoDir = "evidences/videos/";
        String videoName = testName + "_" + timestamp;

        File directory = new File(videoDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        gravacao = new ATUTestRecorder(videoDir, videoName, false);
        gravacao.start();
    }

    public void stopVideo () throws ATUTestRecorderException{
        gravacao.stop();
    }

    public void close() {
        this.webDriver.quit();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
