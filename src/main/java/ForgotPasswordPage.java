package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLogin() {
        driver.findElement(loginLink).click();
    }
}
