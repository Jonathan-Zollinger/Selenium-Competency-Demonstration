import org.openqa.selenium.WebDriver

class Main {

    static void main(String[] args) {
        WebDriver driver = MyUtils.getChromeDriver()

        Webstaurant webstaurant = new Webstaurant(driver);
        webstaurant.searchForString('stainless work table')

        driver.quit()
    }

}

