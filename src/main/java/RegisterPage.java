package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    public static String PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private final By loginButton = By.xpath(".//a[text()='Войти']");
    private final By loginError = By.xpath(".//p[text()='Некорректный пароль']");
    private final By nameField = By.xpath(".//label[text()='Имя']/../input");
    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void inputName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void open() {
        driver.get(PAGE_URL);
    }

    public boolean loginErrorDisplayed() {
        return driver.findElement(loginError).isDisplayed();
    }


}
