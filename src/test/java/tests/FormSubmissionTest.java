package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.FormPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormSubmissionTest {
    private WebDriver driver;
    private FormPage formPage;

    // 1. Меняем ссылку на страницу входа
    private static final String LOGIN_URL = "https://wishlist.otus.kartushin.su/login";

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        formPage = new FormPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFormSubmissionWithPasswordCheck() {
        System.out.println("Начало теста заполнения формы для пользователя: DefaultUser");

        // 2. Открываем страницу по новой ссылке
        formPage.open(LOGIN_URL);

        // 3. Заполняем форму
        formPage.fillForm("DefaultUser", "12345678");

        // 4. Отправляем форму
        formPage.submit();

        // 5. Проверка (раскомментируйте нужную):
        // Если данные неверные и мы ждем ошибку:
        // assertTrue(formPage.isErrorMessageDisplayed(), "Сообщение об ошибке не появилось");

        // Если данные верные и мы ждем перехода на другую страницу:
        // assertTrue(driver.getCurrentUrl().contains("wishlists"), "Переход в личный кабинет не произошел");

        System.out.println("Тест завершен");
    }
}