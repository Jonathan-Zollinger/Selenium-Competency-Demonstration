import com.google.common.collect.ImmutableMap
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions;

class Webstaurant {
    WebDriver driver

    enum SelectorEnum {
        SEARCH_BAR_TEXT_INPUT("search-bar-input", "input[id='searchval']")

        final String name
        final String xpath
        static final Map map

        static {
            map = new HashMap<String,String>()
            values().each{ Selector ->
                printf("Name: %s, xpath: %s", Selector.name, Selector.xpath)
                map.put(Selector.name, Selector.xpath)
            }
        }

        private SelectorEnum(String name, String xpath) {
            this.name   = name
            this.xpath  = xpath
        }

        static getSelector( String name ) {
            return map[name]
        }

    }

    Webstaurant(){
       getChromeDriver()
    }

    WebDriver getChromeDriver(){
        // check latest version available here: https://chromedriver.storage.googleapis.com/index.html
        WebDriverManager.chromedriver().browserVersion("99.0.4844.51").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        driver.get("https://www.webstaurantstore.com/")
    }


    WebstaurantSearchResults searchForString(WebDriver driver, String findMe){
        return driver.findElement(By.xpath(SelectorEnum.getSelector("search-bar-input") as String))
                .sendKeys(findMe)
    }



}
