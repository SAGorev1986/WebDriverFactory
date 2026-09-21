package factory;

import enums.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    // Пример 1 из задания: WebDriver wd = WebDriverFactory.createNewDriver("chrome");
    public static WebDriver createNewDriver(String browserName) {
        return createNewDriver(browserName, null);
    }

    // Пример 2 из задания: WebDriver wd = WebDriverFactory.createNewDriver("firefox", options);
    public static WebDriver createNewDriver(String browserName, Object options) {
        Browser browser = Browser.fromString(browserName);

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return (options != null) ? new ChromeDriver((ChromeOptions) options) : new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return (options != null) ? new FirefoxDriver((FirefoxOptions) options) : new FirefoxDriver();
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return (options != null) ? new EdgeDriver((EdgeOptions) options) : new EdgeDriver();
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }
    }
}