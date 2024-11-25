package com.projetoiLAB;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.projetoiLAB.utils.ExtentManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PageObject {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    public PageObject(WebDriver webDriver) {
           System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        if (webDriver == null) {
            this.webDriver = new ChromeDriver();
        } else {
            this.webDriver = webDriver;
        }
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    public void takeScreenShot(String test){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
        String screenshotName = "evidences/screenshots/" + test + "_" + timestamp + ".png";

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(screenshot.toPath(), Paths.get(screenshotName), StandardCopyOption.REPLACE_EXISTING);
            ExtentManager.getTest().info("Captura de tela adicionada",
                    MediaEntityBuilder.createScreenCaptureFromPath("/"+screenshotName).build());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o screenshot: " + e.getMessage());
        }

    }

    public void close() {
        this.webDriver.quit();
    }
}
