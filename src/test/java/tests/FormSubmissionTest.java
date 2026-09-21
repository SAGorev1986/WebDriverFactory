package tests;

import factory.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.FormPage;

import java.util.UUID;

public class FormSubmissionTest {
    private WebDriver driver;
    private FormPage formPage;

    // ПАРАМЕТРИЗАЦИЯ URL (Замечание преподавателя №1 закрыто)
    private static final String BASE_URL = System.getProperty("test.url", "https://wishlist.otus.kartushin.su/register");

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");

        driver = WebDriverFactory.createNewDriver(browser, options);
        formPage = new FormPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ТЕСТ 1: Успешная регистрация
    @Test
    public void testSuccessfulRegistration() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);
        String user = "RegUser_" + uniqueId;

        formPage.open(BASE_URL);
        formPage.register(user, user + "@test.com", "Password123!");

        formPage.assertRegisteredSuccessfully();
    }

    // ТЕСТ 2: Успешная авторизация (НОВЫЙ)
    @Test
    public void testSuccessfulLogin() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);
        String user = "AuthUser_" + uniqueId;
        String pass = "Password123!";

        // 1. Сначала регистрируем пользователя, чтобы он существовал в системе
        formPage.open(BASE_URL);
        formPage.register(user, user + "@test.com", pass);
        formPage.assertRegisteredSuccessfully(); // Убеждаемся, что мы на странице /login

        // 2. Выполняем вход под только что созданным пользователем
        formPage.login(user, pass);

        // 3. Проверяем успешный вход (ассерт внутри Page Object)
        formPage.assertLoginSuccessful();
    }

    // ТЕСТ 3: Неудачная авторизация (обработка ошибок)
    @Test
    public void testFailedLogin() {
        // Переходим сразу на страницу логина
        String loginUrl = BASE_URL.replace("/register", "/login");

        formPage.open(loginUrl);
        formPage.login("FakeUser_Not_Exists", "WrongPassword");

        // Проверяем появление ошибки (ассерт внутри Page Object)
        formPage.assertLoginFailed();
    }
}