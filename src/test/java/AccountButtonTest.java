package praktikum;

import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.model.CreateUserResponse;
import praktikum.model.LoginUser;
import praktikum.model.UserClient;

import java.time.Duration;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class AccountButtonTest {
    private WebDriver driver;
    private String email;
    private String password;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        Random random = new Random();
        this.email = "something" + random.nextInt(10000000) + "@yandex.ru";
        this.password = "password" + random.nextInt(10000000);
        registerPage.inputName("TestTna");
        registerPage.inputEmail(email);
        registerPage.inputPassword(password);
        registerPage.clickRegister();
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginLkButton();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
    }

    @Test
    public void checkAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.waitAccountProfilePage();
        Assert.assertEquals(AccountProfilePage.PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkConstructorButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.clickConstructorButton();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkExitButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.clickExitButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        Assert.assertEquals(LoginPage.PAGE_URL, driver.getCurrentUrl());
    }

    @After
    public void cleanUp() {
        driver.quit();
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        LoginUser loginUser = new LoginUser(email, password);
        Response response = UserClient.postApiAuthLogin(loginUser);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        String responseString = response.body().asString();
        Gson gson = new Gson();
        CreateUserResponse loginUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        String accessToken = loginUserResponse.getAccessToken();
        UserClient.deleteApiAuthUser(accessToken).then().assertThat().body("success", equalTo(true))
                .and()
                .body("message", equalTo("User successfully removed"))
                .and()
                .statusCode(202);
    }
}
