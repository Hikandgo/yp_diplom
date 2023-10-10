package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountProfilePage {
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    private final By profileLink = By.xpath(".//a[text()='Профиль']");
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final WebDriver driver;

    public AccountProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }

    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    public void waitAccountProfilePage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(profileLink));
    }
}
