import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Random;


public class RegisterTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
    }

    @Test
    public void checkRegister() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        String password = "password" + random.nextInt(10000000);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.inputName("Nikita");
        registerPage.inputEmail(email);
        registerPage.inputPassword(password);
        registerPage.clickRegister();

        Assert.assertEquals(LoginPage.LOGIN_PAGE, driver.getCurrentUrl());

    }

    @Test
    public void checkRegisterWrongPassword() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.inputName("Nikita");
        registerPage.inputEmail(email);
        registerPage.inputPassword("123");
        registerPage.clickRegister();
        Assert.assertTrue(registerPage.loginErrorDisplayed());

    }

    @After
    public void cleanUp() {
        driver.quit();
    }

}
