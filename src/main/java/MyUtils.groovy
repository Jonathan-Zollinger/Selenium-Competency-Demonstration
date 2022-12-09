import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration;

final class MyUtils {

    static WebDriver getChromeDriver(){
        // check latest version available here: https://chromedriver.storage.googleapis.com/index.html
        WebDriverManager.chromedriver().browserVersion("99.0.4844.51").setup()
        ChromeOptions options = new ChromeOptions()
        options.addArguments("start-maximized")
        options.addArguments("enable-automation")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-infobars")
        options.addArguments("--disable-dev-shm-usage")
        options.addArguments("--disable-browser-side-navigation")
        options.addArguments("--disable-gpu")
        return new ChromeDriver(options)
    }
    static WebDriverWait getWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2));
    }
}
