package praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private final By bulkiButton = By.xpath(".//span[text()='Булки']/..");
    private final By souseButton = By.xpath(".//span[text()='Соусы']/..");
    private final By nachinkiButton = By.xpath(".//span[text()='Начинки']/..");
    private final By loginLkLink = By.xpath(".//p[text()='Личный Кабинет']");
    private final By loginLkButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By currentTabHeader = By.xpath(".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']/span");

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(PAGE_URL);
    }

    public void clickLoginLkLink() {
        driver.findElement(loginLkLink).click();
    }

    public void clickLoginLkButton() {
        driver.findElement(loginLkButton).click();
    }

    public void clickBulkiButton() {
        driver.findElement(bulkiButton).click();
    }

    public void clickSouseButton() {
        driver.findElement(souseButton).click();
    }

    public void clickNachinkiButton() {
        driver.findElement(nachinkiButton).click();
    }

    public String checkCurrentTabHeaderText() {
        return driver.findElement(currentTabHeader).getText();
    }

    public void waitMainPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(bulkiButton));
    }

    public void waitNachinkiHeader() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(nachinkiButton));
    }

    public void waitBulkiHeader() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(bulkiButton));
    }
}
