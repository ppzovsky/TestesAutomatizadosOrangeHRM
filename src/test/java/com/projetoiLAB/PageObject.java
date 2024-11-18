package com.projetoiLAB;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PageObject {

    protected WebDriver webDriver;

    public PageObject(WebDriver webDriver) {
           System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        if (webDriver == null) {
            this.webDriver = new ChromeDriver();;
        } else {
            this.webDriver = webDriver;
        }
    }

    public void close() {
        this.webDriver.quit();
    }
}
