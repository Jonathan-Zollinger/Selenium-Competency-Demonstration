import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.ui.WebDriverWait

import java.util.logging.Logger

class Webstaurant {
    WebDriver driver;
    WebDriverWait wait;
    Logger log = Logger.getLogger(this.class.getName())
    final protected By SEARCH_BAR_INPUT = By.xpath("//input[@id='searchval']")
    final protected By EXECUTE_SEARCH_BUTTON = By.xpath("//button[@value='Search']")
    final protected By PAGINATION_NUMBERS = By.xpath(
            "//nav[@aria-label='pagination']//a[contains(@aria-label,'page') and not(contains(@aria-label,'go to'))]"
    )
    final protected String XPATH_PAGINATION_FORMAT_NUMBER = "//nav[@aria-label='pagination']//a[contains(text(),'%d')]"
    final protected By PAGINATION_LAST_PAGE = By.xpath(
            "//nav[@aria-label='pagination']//a[contains(@aria-label,'last page')]"
    )
    final protected By PAGINATION_CURRENT_PAGE = By.xpath(
            "//nav[@aria-label='pagination']//a[contains(@aria-label,'current page')]"
    )

    Webstaurant(WebDriver driver){
        assert null != driver
        this.driver = driver
        wait = MyUtils.getWebDriverWait(driver)
        driver.get("https://webstaurantstore.com")
    }

    protected searchForString(String findMe){
        logMethod(0, "searh for " + findMe)
        sendKeys(SEARCH_BAR_INPUT, findMe)
        click (EXECUTE_SEARCH_BUTTON)
        logMethod(1, "searh for " + findMe)
        return this
    }

    protected sendKeys(By selector, String text) {
        String thisAction = String.format("to send key(s) %s to", text)
        logMethod(0, thisAction, selector)
        waitForElement (selector)
        try {
            driver.findElement(selector).sendKeys(text)
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,thisAction, selector),generalWebDriverException.stackTrace)
            )
        }
        logMethod(1, thisAction, selector)
        return this
    }

    protected click(By selector) {
        String thisAction = "click"
        logMethod(0,"click", selector)
        waitForElement (selector)
        try{
            driver.findElement (selector).click()
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,thisAction, selector),generalWebDriverException.stackTrace)
            )
        }

        logMethod(1,"click", selector)
        return this
    }

    protected waitForElement(By selector){
        assert null != wait
        String thisAction = "wait to click"
        logMethod(0, thisAction , selector)
        try{
            wait.until (webDriver -> driver.findElement (selector))
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,thisAction, selector),generalWebDriverException.stackTrace)
            )
        }
        logMethod(1, thisAction , selector)
        return this
    }

    protected String logMethod(int startOrEndOrFail, String thisAction, By selector){
        String subject = String.format("the element found by the `%s` Selector", selector)
        return logMethod(startOrEndOrFail, String.format("%s %s", thisAction, subject))
    }

    protected String logMethod(int startOrEndOrFail, String thisAction){
        if (startOrEndOrFail.equals(2)){
            return String.format("Failed to %s.%s", thisAction, "\n\tStackTrace:\n\t%s")
        }
        return String.format("%s %s.", logMethod(startOrEndOrFail), thisAction)
    }

    protected String logMethod(int startOrEndOrFail){
        switch (startOrEndOrFail){
            case 0:
                return "Beginning attempt"
                break
            case 1:
                return "Finished attempt"
                break
            default:
                throw new IllegalStateException("logMethod(int) accepts only 0, 1 or 2.")
        }
    }

}
