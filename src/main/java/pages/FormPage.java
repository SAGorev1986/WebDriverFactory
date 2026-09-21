package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы регистрации
    private final By regName = By.xpath("//label[text()='Имя пользователя']/following-sibling::input");
    private final By regEmail = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By regPass = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By regBtn = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");

    // Локаторы входа
    private final By loginName = By.xpath("//label[text()='Имя пользователя']/following-sibling::input");
    private final By loginPass = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginBtn = By.xpath("//button[contains(text(), 'Войти')]");

    // Локатор ошибки
    private final By errorMsg = By.className("alert-danger");

    public FormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void register(String name, String email, String pass) {
        type(regName, name);
        type(regEmail, email);
        type(regPass, pass);
        click(regBtn);

        // Явно ждем редиректа на страницу логина после регистрации
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    public void login(String name, String pass) {
        type(loginName, name);
        type(loginPass, pass);
        click(loginBtn);
    }

    // === АССЕРТЫ ВНУТРИ PAGE OBJECT ===

    public void assertRegisteredSuccessfully() {
        if (!driver.getCurrentUrl().contains("/login")) {
            throw new AssertionError("Ожидался редирект на страницу логина, но мы на: " + driver.getCurrentUrl());
        }
    }

    // НОВЫЙ АССЕРТ: Проверка успешного входа
    public void assertLoginSuccessful() {
        // Ждем, пока URL перестанет содержать "/login" (значит, произошел редирект в личный кабинет)
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        // Дополнительная защита: убеждаемся, что нас не перекинуло обратно на регистрацию
        if (driver.getCurrentUrl().contains("/register")) {
            throw new AssertionError("После входа нас вернуло на страницу регистрации");
        }
    }

    public void assertLoginFailed() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
            if (!error.isDisplayed()) {
                throw new AssertionError("Сообщение об ошибке не отображается на странице");
            }
        } catch (Exception e) {
            throw new AssertionError("Сообщение об ошибке не найдено на странице.");
        }
    }
}