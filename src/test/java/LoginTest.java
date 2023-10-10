package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.model.CreateUserResponse;
import praktikum.model.LoginUser;

import java.time.Duration;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    private WebDriver driver;
    private String email;
    private String password;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        Random random = new Random();
        this.email = "something" + random.nextInt(10000000) + "@yandex.ru";
        this.password = "password" + random.nextInt(10000000);
        registerPage.inputName("TestName");
        registerPage.inputEmail(email);
        registerPage.inputPassword(password);
        registerPage.clickRegister();
    }

    @Test
    public void checkLoginLkButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginLkButton();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginLkLink() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginLkLink();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginOnRegisterPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.clickLogin();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        MainPage mainPage = new MainPage(driver);
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginOnForgotPasswordButton() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickForgotPassword();
        Assert.assertEquals(ForgotPasswordPage.PAGE_URL, driver.getCurrentUrl());
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLogin();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        MainPage mainPage = new MainPage(driver);
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.PAGE_URL, driver.getCurrentUrl());
    }

    @After
    public void cleanUp() {
        driver.quit();
    }
}
