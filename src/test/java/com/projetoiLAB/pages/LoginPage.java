package com.projetoiLAB.pages;

import com.projetoiLAB.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Set;

public class LoginPage extends PageObject {
    private static final String URL_LOGIN_PAGE = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public LoginPage() {
        super(null);
        this.webDriver.navigate().to(URL_LOGIN_PAGE);
        this.webDriver.manage().window().maximize();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]")));
    }

    public void fillLoginInputs (String username, String password){
        this.webDriver.findElement(By.name("username")).sendKeys(username);
        this.webDriver.findElement(By.name("password")).sendKeys(password);
    }

    public void submitLogin() throws InterruptedException {
        this.webDriver.findElement(By.className("oxd-form")).submit();
    }

    public void clickOnLink (String link){
        this.webDriver.findElement(By.cssSelector("a[href='"+link+"']")).click();
    }

    public boolean isSelectedPage(String page) {
        this.wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        return this.webDriver.getCurrentUrl().equals(page);
    }

    public boolean verifyMessage(String xpath) {
        try {
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public WebElement errorInput() {
        return this.webDriver.findElement(By.cssSelector(".oxd-input--error"));
    }

    public Set<String> getWindowHandles() {
        return this.webDriver.getWindowHandles();
    }

    public boolean switchTab(String url) {
        Set<String> allWindows = webDriver.getWindowHandles();
        for (String window : allWindows) {
            webDriver.switchTo().window(window);
            if (webDriver.getCurrentUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    public void clickOnLinkForgot() {
        WebElement button = this.webDriver.findElement(By.className("orangehrm-login-forgot-header"));
        button.click();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[1]/div/form/div[2]/button[2]")));
    }

    public boolean dashboardOpen() {
        try {
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[2]/div")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
