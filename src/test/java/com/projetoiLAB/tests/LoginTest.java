package com.projetoiLAB.tests;

import atu.testrecorder.exceptions.ATUTestRecorderException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;
import com.projetoiLAB.pages.LoginPage;
import com.projetoiLAB.utils.ExtentManager;
import com.projetoiLAB.utils.PrintScreenshot;
import com.projetoiLAB.utils.ReadData;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    private ExtentReports extentReports;
    private ExtentTest extentTest;

    @BeforeEach
    public void beforeEach(TestInfo testInfo) throws ATUTestRecorderException {
        ExtentManager.initReports();
        String testName = testInfo.getDisplayName();

        extentTest = ExtentManager.createTest(testName);
        ExtentManager.getTest().info("Iniciando o teste: " + testName);
        this.loginPage = new LoginPage();
        loginPage.recordVideo(testName);
    }

    public ExtentTest getExtentTest() {
        return extentTest;
    }

    @AfterEach
    public void afterEach() throws ATUTestRecorderException {
        loginPage.close();
        ExtentManager.getTest().info("Finalizando o teste.");
        ExtentManager.flushReports();
        loginPage.stopVideo();
    }

    private void logAndScreenshotOnFail(String testName, String stepName, String erro, ExtentTest test) {
        ExtentManager.getTest().fail("Erro no teste: " + erro);
        PrintScreenshot.takeScreenshot(testName, stepName + "_Falha", test);
    }

    @Test
    @Order(1)
    public void CT01_ValidarLoginComCredenciaisValidas() {
        ExtentTest test = getExtentTest();
        try {

            ReadData.Logins login = ReadData.lerJson();
            ExtentManager.getTest().info("Preenchendo as credenciais válidas.");
            loginPage.fillLoginInputs(login.loginValido.username, login.loginValido.password);
            PrintScreenshot.takeScreenshot("CT01_ValidarLoginComCredenciaisValidas",
                    "CT01_CredenciaisPreenchidas", test);
            loginPage.submitLogin();

            ExtentManager.getTest().info("Verificando se a página redirecionou para o Dashboard.");
            Assertions.assertFalse(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.isSelectedPage(URL_DASHBOARD_PAGE));
            Assertions.assertTrue(loginPage.dashboardOpen());
            PrintScreenshot.takeScreenshot("CT01_ValidarLoginComCredenciaisValidas",
                    "CT01_DashboardPage", test);

            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (AssertionError | InterruptedException e) {
            logAndScreenshotOnFail("CT01_ValidarLoginComCredenciaisValidas",
                    "CT01_DashboardPage", e.getMessage(), test);
        }
    }

    @Test
    @Order(2)
    public void CT02_ValidarLoginComSenhaIncorreta() {
        ExtentTest test = getExtentTest();
        try {

            ReadData.Logins login = ReadData.lerJson();
            ExtentManager.getTest().info("Preenchendo as credenciais com senha incorreta.");
            loginPage.fillLoginInputs(login.loginValido.username, login.loginInvalido.password);
            PrintScreenshot.takeScreenshot("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_CredenciaisPreenchidas", test);
            loginPage.submitLogin();

            ExtentManager.getTest().info("Verificando mensagem de erro.");
            Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p"));
            PrintScreenshot.takeScreenshot("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_MensagemDeErro", test);

            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (AssertionError e) {
            logAndScreenshotOnFail("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_MensagemDeErro", e.getMessage(), test);
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    public void CT03_ValidarLoginComUsuarioNaoRegistrado() {
        ExtentTest test = getExtentTest();
        try {

            ReadData.Logins login = ReadData.lerJson();
            ExtentManager.getTest().info("Preenchendo as credenciais com usuário não registrado.");
            loginPage.fillLoginInputs(login.loginInvalido.username, login.loginInvalido.password);
            PrintScreenshot.takeScreenshot("CT03_ValidarLoginComUsuarioNaoRegistrado",
                    "CT03_CredenciaisPreenchidas", test);
            loginPage.submitLogin();

            ExtentManager.getTest().info("Verificando mensagem de erro.");
            Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p"));
            PrintScreenshot.takeScreenshot("CT03_ValidarLoginComUsuarioNaoRegistrado",
                    "CT03_MensagemDeErro", test);

            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (AssertionError e) {
            logAndScreenshotOnFail("CT03_ValidarLoginComUsuarioNaoRegistrado", "CT03_MensagemDeErro", e.getMessage(), test);
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void CT04_ValidarCamposObrigatoriosNaoPreenchidos() {
        ExtentTest test = getExtentTest();
        try {
            ExtentManager.getTest().info("Deixando os campos de login vazios.");
            loginPage.fillLoginInputs("", "");
            PrintScreenshot.takeScreenshot("CT04_ValidarCamposObrigatoriosNaoPreenchidos",
                    "CT04_CredenciaisEmBranco", test);
            loginPage.submitLogin();

            ExtentManager.getTest().info("Verificando mensagem de erro e marcação de campos obrigatórios.");
            Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/span"));
            Assertions.assertTrue(loginPage.errorInput().isDisplayed());
            PrintScreenshot.takeScreenshot("CT04_ValidarCamposObrigatoriosNaoPreenchidos",
                    "CT04_MensagemDeErro", test);

            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (AssertionError e) {
            logAndScreenshotOnFail("CT04_ValidarCamposObrigatoriosNaoPreenchidos",
                    "CT04_MensagemDeErro", e.getMessage(), test);
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    public void CT05_ValidarAcessoPaginaForgotYourPassword(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT05_ValidarAcessoPaginaForgotYourPassword",
                    "CT05_ForgotMyPassword", test);
            loginPage.clickOnLinkForgot();

            Assertions.assertFalse(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.isSelectedPage(URL_RESETPASSWORD_PAGE));
            PrintScreenshot.takeScreenshot("CT05_ValidarAcessoPaginaForgotYourPassword",
                    "CT05_ResetPasswordPage", test);
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div[1]/div/form/h6"));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div[1]/div/form/p/p"));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div[1]/div/form/div[2]/button[2]"));
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError e) {
            logAndScreenshotOnFail("CT05_ValidarAcessoPaginaForgotYourPassword",
                    "CT05_ErroPasswordPage", e.getMessage(), test);
            throw e;
        }
    }

    @Test
    @Order(6)
    public void CT06_ValidarAcessoLinkedIn(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT06_ValidarAcessoLinkedIn",
                    "CT06_ClickLinkedin", test);
            Set<String> abasIniciais = loginPage.getWindowHandles();
            loginPage.clickOnLink(URL_LINKEDIN);

            Set<String> abasAbertas = loginPage.getWindowHandles();
            Assertions.assertTrue(abasAbertas.size() > abasIniciais.size());
            Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.switchTab("https://br.linkedin.com/company/orangehrm"));
            PrintScreenshot.takeScreenshot("CT06_ValidarAcessoLinkedIn",
                    "CT06_LinkedinPage", test);
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError | TimeoutException e) {
            logAndScreenshotOnFail("CT06_ValidarAcessoLinkedIn",
                    "CT06_ErroLinkedinPage", e.getMessage(), test);
            throw e;
        }
    }
    @Test
    @Order(7)
    public void CT07_ValidarAcessoYoutube(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT07_ValidarAcessoYoutube",
                    "CT07_ClickYoutube", test);
            Set<String> abasIniciais = loginPage.getWindowHandles();
            loginPage.clickOnLink(URL_YOUTUBE);

            Set<String> abasAbertas = loginPage.getWindowHandles();
            Assertions.assertTrue(abasAbertas.size() > abasIniciais.size());
            Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.switchTab("https://www.youtube.com/c/OrangeHRMInc"));
            PrintScreenshot.takeScreenshot("CT07_ValidarAcessoYoutube",
                    "CT07_YoutubePage", test);
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError | TimeoutException e) {
            logAndScreenshotOnFail("CT07_ValidarAcessoYoutube",
                    "CT07_ErroYoutubePage", e.getMessage(), test);
            throw e;
        }
    }

    @Test
    @Order(8)
    public void CT08_ValidarAcessoFacebook(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT08_ValidarAcessoFacebook",
                    "CT08_ClickFacebook", test);
            Set<String> abasIniciais = loginPage.getWindowHandles();
            loginPage.clickOnLink(URL_FACEBOOK);

            Set<String> abasAbertas = loginPage.getWindowHandles();
            Assertions.assertTrue(abasAbertas.size() > abasIniciais.size());
            Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.switchTab("https://www.facebook.com/OrangeHRM/"));
            PrintScreenshot.takeScreenshot("CT08_ValidarAcessoFacebook",
                    "CT08_FacebookPage", test);
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError | TimeoutException e) {
            logAndScreenshotOnFail("CT08_ValidarAcessoFacebook",
                    "CT08_ErroFacebookPage", e.getMessage(), test);
            throw e;
        }
    }

    @Test
    @Order(9)
    public void CT09_ValidarAcessoTwitter(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT09_ValidarAcessoTwitter",
                    "CT09_ClickTwitter", test);
            Set<String> abasIniciais = loginPage.getWindowHandles();
            loginPage.clickOnLink(URL_TWITTER);

            Set<String> abasAbertas = loginPage.getWindowHandles();
            Assertions.assertTrue(abasAbertas.size() > abasIniciais.size());
            Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.switchTab("https://x.com/orangehrm?lang=en"));
            PrintScreenshot.takeScreenshot("CT09_ValidarAcessoTwitter",
                    "CT09_TwitterPage", test);
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError | TimeoutException e) {
            logAndScreenshotOnFail("CT09_ValidarAcessoTwitter",
                    "CT09_ErroTwitter", e.getMessage(), test);
            throw e;
        }
    }

    @Test
    @Order(10)
    public void CT10_ValidarAcessoOrangeHRM(){
        ExtentTest test = getExtentTest();
        try{
            PrintScreenshot.takeScreenshot("CT10_ValidarAcessoOrangeHRM",
                    "CT10_ClickOrangeHRM", test);
            Set<String> abasIniciais = loginPage.getWindowHandles();
            loginPage.clickOnLink(URL_ORANGEHRM);

            Set<String> abasAbertas = loginPage.getWindowHandles();
            Assertions.assertTrue(abasAbertas.size() > abasIniciais.size());
            Assertions.assertTrue(loginPage.switchTab(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.switchTab("https://www.orangehrm.com/"));
            PrintScreenshot.takeScreenshot("CT10_ValidarAcessoOrangeHRM",
                    "CT10_OrangeHRMPage", test);
            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (NoSuchElementException | AssertionError | TimeoutException e) {
            logAndScreenshotOnFail("CT10_ValidarAcessoOrangeHRM",
                    "CT10_ErroOrangeHRMPage", e.getMessage(), test);
            throw e;
        }
    }
    @Test
    @Order(11)
    public void CT11_SimulacaoDeErro() {
        ExtentTest test = getExtentTest();
        try {

            ReadData.Logins login = ReadData.lerJson();
            ExtentManager.getTest().info("Preenchendo as credenciais com senha incorreta.");
            loginPage.fillLoginInputs(login.loginValido.username, login.loginValido.password);
            PrintScreenshot.takeScreenshot("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_CredenciaisPreenchidas", test);
            loginPage.submitLogin();

            ExtentManager.getTest().info("Verificando mensagem de erro.");
            Assertions.assertTrue(loginPage.isSelectedPage(URL_LOGIN_PAGE));
            Assertions.assertTrue(loginPage.verifyMessage("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p"));
            PrintScreenshot.takeScreenshot("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_MensagemDeErro", test);

            ExtentManager.getTest().pass("Teste concluído com sucesso!");
        } catch (AssertionError e) {
            logAndScreenshotOnFail("CT02_ValidarLoginComSenhaIncorreta",
                    "CT02_MensagemDeErro", e.getLocalizedMessage(), test);
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
