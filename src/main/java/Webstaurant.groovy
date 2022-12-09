import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

import java.util.logging.Logger

class Webstaurant {
    WebDriver driver;
    WebDriverWait wait;
    Logger log = Logger.getLogger(this.class.getName())
    final By SEARCH_BAR_INPUT = By.xpath("//input[@id='searchval']")
    final By EXECUTE_SEARCH_BUTTON = By.xpath("//button[@value='Search']")

    Webstaurant(WebDriver driver){
        assert null != driver
        this.driver = driver
        wait = MyUtils.getWebDriverWait(driver)
        driver.get("https://webstaurantstore.com")
    }

    protected searchForString(String findMe){
        log.info(String.format("Beginning attempt to search for `%s`.", findMe))
        sendKeys(SEARCH_BAR_INPUT, findMe)
        click (EXECUTE_SEARCH_BUTTON)
        log.info(String.format("Finished attempt to search for `%s`.", findMe))
        return this
    }

    protected void sendKeys(By selector, String text) {
        log.info(String.format("Beginning attempt to send keys `%s` to the `%s` element", text, selector))
        waitForElement (selector)
        driver.findElement (selector).sendKeys(text)
        log.info(String.format("Finished attempt to send keys `%s` to the `%s` element", text, selector))
    }

    protected void click(By selector) {
        log.info(String.format("Beginning attempt to click the `%s` element", selector))
        waitForElement (selector)
        driver.findElement (selector).click()
        log.info(String.format("Finished attempt to click the `%s` element", selector))
    }

    protected void waitForElement(By selector){
        assert null != wait
        log.info(String.format("Beginning attempt to wait for the element `%s` to be clickable", selector))
        wait.until (webDriver -> driver.findElement (selector))
        log.info(String.format("Finish attempt to wait for the element `%s` to be clickable", selector))
    }



}
