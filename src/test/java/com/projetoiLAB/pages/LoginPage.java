package com.projetoiLAB.pages;

import com.projetoiLAB.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;


public class LoginPage extends PageObject {
    private static final String URL_LOGIN_PAGE = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public LoginPage() {
        super(null);
        this.webDriver.navigate().to(URL_LOGIN_PAGE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fillLoginInputs (String username, String password){
        this.webDriver.findElement(By.name("username")).sendKeys(username);
        this.webDriver.findElement(By.name("password")).sendKeys(password);
    }

    public void submitLogin(){
        this.webDriver.findElement(By.className("oxd-form")).submit();
    }

    public void clickOnLink (String link){
        this.webDriver.findElement(By.cssSelector("a[href='"+link+"']")).click();
    }

    public boolean isSelectedPage(String page) {
        return this.webDriver.getCurrentUrl().equals(page);
    }

    public boolean verifyMessage(String message) {
        return this.webDriver.getPageSource().contains(message);
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
    }
}
