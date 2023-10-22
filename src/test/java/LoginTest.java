import apiModel.CreateUser;
import apiModel.CreateUserResponse;
import apiModel.UserClient;
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


import java.time.Duration;
import java.util.Random;

public class LoginTest {
    private WebDriver driver;
    private String email;
    private String password;

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = UserClient.BASE_URL;

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Random random = new Random();
        this.email = "something" + random.nextInt(10000000) + "@yandex.ru";
        this.password = "password" + random.nextInt(10000000);
        CreateUser user = new CreateUser(email, password, "TestBoy");
        Response userBody = UserClient.postApiAuthRegister(user);

        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(userBody.body().asString(), CreateUserResponse.class);
        this.accessToken = createUserResponse.getAccessToken();
    }

    @Test
    public void checkLoginLkButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginLkButton();
        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.FIRST_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginLkLink() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginLkLink();
        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.FIRST_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginOnRegisterPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.clickLogin();
        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        MainPage mainPage = new MainPage(driver);
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.FIRST_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void checkLoginOnForgotPasswordButton() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickForgotPassword();
        Assert.assertEquals(ForgotPasswordPage.FORGOT_PASSWORD_PAGE, driver.getCurrentUrl());
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLogin();
        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLogin();
        MainPage mainPage = new MainPage(driver);
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.FIRST_PAGE, driver.getCurrentUrl());
    }

    @After
    public void cleanUp() {
        driver.quit();
        UserClient.deleteApiAuthUser(accessToken);
    }
}
