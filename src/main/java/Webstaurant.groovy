import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.ui.WebDriverWait

import java.util.logging.Logger

class Webstaurant {
    WebDriver driver;
    WebDriverWait wait;
    Logger log = Logger.getLogger(this.class.getName())
    private String logForElement = "the element found by the `%s` Selector"
    private String openingLog = "Beginning attempt"
    private String closingLog = "Finished attempt"
    private String formattedLogStatement = "%s %s %s" // log relevance, log verb, log subject
    final protected By SEARCH_BAR_INPUT = By.xpath("//input[@id='searchval']")
    final protected By EXECUTE_SEARCH_BUTTON = By.xpath("//button[@value='Search']")
    final protected By PAGINATION_NUMBER_BUTTONS = By.xpath(
            "//nav[@aria-label='pagination']//a[contains(@aria-label,'page') and not(contains(@aria-label,'go to'))]"
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
        action = String.format("to send key(s) %s to", text)
        logMethod(0, action, selector)
        waitForElement (selector)
        try {
            driver.findElement(selector).sendKeys(text)
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,action, selector),generalWebDriverException.stackTrace)
            )
        }
        logMethod(1, action, selector)
        return this
    }

    protected click(By selector) {
        action = "click"
        logMethod(0,"click", selector)
        waitForElement (selector)
        try{
            driver.findElement (selector).click()
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,action, selector),generalWebDriverException.stackTrace)
            )
        }

        logMethod(1,"click", selector)
        return this
    }

    protected waitForElement(By selector){
        assert null != wait
        String action = "wait to click"
        logMethod(0, action , selector)
        try{
            wait.until (webDriver -> driver.findElement (selector))
        }
        catch(WebDriverException generalWebDriverException){
            log.severe(String.format(logMethod(2,action, selector),generalWebDriverException.stackTrace)
            )
        }
        logMethod(1, action , selector)
        return this
    }

    protected String logMethod(int startOrEndOrFail, String action, By selector){
        String subject = String.format("the element found by the `%s` Selector", selector)
        return logMethod(startOrEndOrFail, String.format("%s %s", action, subject))
    }

    protected String logMethod(int startOrEndOrFail, String action){
        if (startOrEndOrFail.equals(2)){
            return String.format("Failed to %s.%s", action, "\n\tStackTrace:\n\t%s")
        }
        return String.format("%s %s.", logMethod(startOrEndOrFail), action)
    }

    protected String logMethod(int startOrEndOrFail){
        switch (start){
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
