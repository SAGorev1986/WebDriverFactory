package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    public static WebDriver createNewDriver(String browserName) {
        return createNewDriver(browserName, null);
    }

    public static WebDriver createNewDriver(String browserName, Object options) {
        WebDriver driver;
        String browser = browserName.toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                if (options instanceof ChromeOptions) {
                    driver = new ChromeDriver((ChromeOptions) options);
                } else {
                    driver = new ChromeDriver();
                }
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                if (options instanceof FirefoxOptions) {
                    driver = new FirefoxDriver((FirefoxOptions) options);
                } else {
                    driver = new FirefoxDriver();
                }
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }
        driver.manage().window().maximize();
        return driver;
    }
}