package com.projetoiLAB.tests;

import com.projetoiLAB.pages.LoginPage;
import com.projetoiLAB.utils.ExtentManager;
import org.junit.jupiter.api.*;

import java.util.Set;

public class LoginTest {

    private LoginPage loginPage;
    private static final String URL_LOGIN_PAGE = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private static final String URL_DASHBOARD_PAGE = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    private static final String URL_RESETPASSWORD_PAGE = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/requestPasswordResetCode";
    private static final String URL_LINKEDIN = "https://www.linkedin.com/company/orangehrm/mycompany/";
    private static final String URL_YOUTUBE = "https://www.youtube.com/c/OrangeHRMInc";
    private static final String URL_FACEBOOK = "https://www.facebook.com/OrangeHRM/";
    private static final String URL_TWITTER = "https://twitter.com/orangehrm?lang=en";
    private static final String URL_ORANGEHRM = "http://www.orangehrm.com";

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        ExtentManager.initReports();
        String testName = testInfo.getDisplayName();

        ExtentManager.createTest(testName);
        ExtentManager.getTest().info("Iniciando o teste: " + testName);
        this.loginPage = new LoginPage();
    }

    @AfterEach
    public void afterEach(){
        loginPage.close();
        ExtentManager.getTest().info("Finalizando o teste.");
        ExtentManager.flushReports();
    }

    @Test
    public void CT01LoginValido(){
        loginPage.fillLoginInputs("Admin", "admin123");
        loginPage.takeScreenShot("CT01_CredenciaisPreenchidas");
        loginPage.submitLogin();

        Assertions.assertFalse(loginPage.isSelectedPage(URL_LOGIN_PAGE));
        loginPage.takeScreenShot("CT01_DashboardPage");
        Assertions.assertTrue(loginPage.isSelectedPage(URL_DASHBOARD_PAGE));
    }

    @Test
    public void CT02LoginComSenhaErrada(){
        loginPage.fillLoginInputs("Admin", "adm123");
        loginPage.takeScreenShot("CT02_CredenciaisPreenchidas");
        loginPage.submitLogin();

        Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.verifyMessage("Invalid credentials"));
        loginPage.takeScreenShot("CT02_MensagemDeErro");
    }

    @Test
    public void CT03LoginComUsuarioErrado(){
        loginPage.fillLoginInputs("Administrador", "adm123");
        loginPage.takeScreenShot("CT03_CredenciaisPreenchidas");
        loginPage.submitLogin();

        Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.verifyMessage("Invalid credentials"));
        loginPage.takeScreenShot("CT03_MensagemDeErro");
    }

    @Test
    public void CT04LoginCredenciaisEmBranco(){
        loginPage.fillLoginInputs("", "");
        loginPage.takeScreenShot("CT04_CredenciaisEmBranco");
        loginPage.submitLogin();

        Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.verifyMessage("Required"));
        loginPage.takeScreenShot("CT04_MensagemDeErro");
        Assertions.assertTrue(loginPage.errorInput().isDisplayed());
    }

    @Test
    public void CT05LinkEsqueciMinhaSenha(){
        loginPage.clickOnLinkForgot();
        loginPage.takeScreenShot("CT05_ForgotMyPassword");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertFalse(loginPage.isSelectedPage(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.isSelectedPage(URL_RESETPASSWORD_PAGE));
        loginPage.takeScreenShot("CT05_ResetPasswordPage");
        Assertions.assertTrue(loginPage.verifyMessage("Reset Password"));
        Assertions.assertTrue(loginPage.verifyMessage("Please enter your username to identify your account to reset your password"));
        Assertions.assertTrue(loginPage.verifyMessage("Reset Password"));
    }

    @Test
    public void CT06LinkLinkedIN(){
        loginPage.clickOnLink(URL_LINKEDIN);
        loginPage.takeScreenShot("CT06_ClickLinkedin");

        Set<String> abasAbertas = loginPage.getWindowHandles();
        Assertions.assertEquals(3, abasAbertas.size());
        Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.switchTab("https://br.linkedin.com/company/orangehrm"));
        loginPage.takeScreenShot("CT06_LinkedinPage");
    }
    @Test
    public void CT07LinkYoutube(){
        loginPage.clickOnLink(URL_YOUTUBE);
        loginPage.takeScreenShot("CT07_ClickYoutube");

        Set<String> abasAbertas = loginPage.getWindowHandles();
        Assertions.assertEquals(3, abasAbertas.size());
        Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.switchTab("https://www.youtube.com/c/OrangeHRMInc"));
        loginPage.takeScreenShot("CT07_YoutubePage");
    }

    @Test
    public void CT08LinkFacebook(){
        loginPage.clickOnLink(URL_FACEBOOK);
        loginPage.takeScreenShot("CT08_ClickFacebook");

        Set<String> abasAbertas = loginPage.getWindowHandles();
        Assertions.assertEquals(3, abasAbertas.size());
        Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.switchTab("https://www.facebook.com/OrangeHRM/"));
        loginPage.takeScreenShot("CT08_FacebookPage");
    }

    @Test
    public void CT09LinkTwitter(){
        loginPage.clickOnLink(URL_TWITTER);
        loginPage.takeScreenShot("CT09_ClickTwitter");

        Set<String> abasAbertas = loginPage.getWindowHandles();
        Assertions.assertEquals(3, abasAbertas.size());
        Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.switchTab("https://x.com/orangehrm?lang=en"));
        loginPage.takeScreenShot("CT09_TwitterPage");
    }

    @Test
    public void CT10LinkOrangeHRM(){
        loginPage.clickOnLink(URL_ORANGEHRM);
        loginPage.takeScreenShot("CT10_ClickOrangeHRM");

        Set<String> abasAbertas = loginPage.getWindowHandles();
        Assertions.assertEquals(3, abasAbertas.size());
        Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
        Assertions.assertTrue(loginPage.switchTab("https://www.orangehrm.com/"));
        loginPage.takeScreenShot("CT10_OrangeHRMPage");
    }
}
