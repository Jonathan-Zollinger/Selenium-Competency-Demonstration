import org.openqa.selenium.WebDriver

class Main {

    static void main(String[] args) {
        WebDriver driver = MyUtils.getChromeDriver()

        Webstaurant webstaurant = new Webstaurant(driver);
        webstaurant.searchForString('stainless work table')

        driver.quit()
    }

//    static WebDriver getChromeDriver() {
//        // check latest version available here: https://chromedriver.storage.googleapis.com/index.html
//        WebDriverManager.chromedriver().browserVersion("99.0.4844.51").setup()
//        ChromeOptions options = new ChromeOptions()
//        options.addArguments("start-maximized")
//        options.addArguments("enable-automation")
//        options.addArguments("--no-sandbox")
//        options.addArguments("--disable-infobars")
//        options.addArguments("--disable-dev-shm-usage")
//        options.addArguments("--disable-browser-side-navigation")
//        options.addArguments("--disable-gpu")
//        return new ChromeDriver(options)
//
//    }
}

