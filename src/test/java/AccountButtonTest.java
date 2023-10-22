import apiModel.CreateUser;
import apiModel.UserClient;
import apiModel.CreateUserResponse;
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



public class AccountButtonTest {
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
    public void checkAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.waitAccountProfilePage();
        Assert.assertEquals(AccountProfilePage.PROFILE_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void checkConstructorButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.clickConstructorButton();
        mainPage.waitMainPage();
        Assert.assertEquals(MainPage.FIRST_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void checkExitButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginLkLink();
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        accountProfilePage.clickExitButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitLoginPage();
        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());
    }

    @After
    public void cleanUp() {
        driver.quit();
        UserClient.deleteApiAuthUser(accessToken);
    }
}
