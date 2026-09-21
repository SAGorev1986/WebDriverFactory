package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Надежные локаторы по тексту label (так как name и id могут отсутствовать)
    private By usernameInputLocator = By.xpath("//label[text()='Имя пользователя']/following-sibling::input");
    private By passwordInputLocator = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By submitButtonLocator = By.xpath("//button[contains(text(), 'Войти')]");
    private By errorMessageLocator = By.className("alert-danger");

    public FormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
    }

    public void fillForm(String username, String password) {
        // Очищаем поля перед вводом, чтобы убрать автозаполнение браузера (например, "SAGorev")
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(passwordInputLocator);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void submit() {
        driver.findElement(submitButtonLocator).click();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}