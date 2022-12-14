import org.openqa.selenium.*
import org.openqa.selenium.support.ui.WebDriverWait

import java.util.logging.Logger

class Webstaurant {

    static final Logger log = Logger.getLogger(this.class.getName())
    WebDriver driver
    WebDriverWait wait
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
    final protected By PAGINATION_NEXT_PAGE = By.xpath(
            "//nav[@aria-label='pagination']//*[contains(@*,'right-open')]"
    )

    JavascriptExecutor js

    Webstaurant(WebDriver driver) {
        assert null != driver
        this.driver = driver
        wait = MyUtils.getWebDriverWait(driver)
        js = (JavascriptExecutor) driver
        driver.get('https://webstaurantstore.com')
    }

    void clickNextPage() {
        WebElement nextPage = getPageNumberFromPagination(PAGINATION_NEXT_PAGE)
        if (null != nextPage) {
            click(nextPage)
        }
    }

    int getLastPageNumber() {
        if (isPaginationPresent()) {
            // WebElement lastPageButton
            getPageNumberFromPagination (PAGINATION_LAST_PAGE)
        }
    }

    private int getPageNumberFromPagination(By Selector) {
        if (isPaginationPresent()) {
            String stringNumber = getElement(Selector).getText()
            try {
                return Integer.parseInt(stringNumber.strip())
            }
            catch (NumberFormatException numberFormatException) {
                log.severe(String.format('failed to parse string `%s`to an integer', stringNumber))
                throw numberFormatException
            }
        }
        return null
    }

    int getThisPageNumber() {
        return getPageNumberFromPagination(PAGINATION_CURRENT_PAGE)
    }

    boolean isPaginationPresent() {
        if (null != getElement(PAGINATION_NUMBERS)) {
            log.info(String.format('Determined results span more than one page ' +
                    'because WebElements are found with the `%s` selector', PAGINATION_NUMBERS))
            return true
        }
        log.info(String.format('Determined results to span only one page ' +
            'because no WebElements are found with the `%s` selector', PAGINATION_NUMBERS))
        return false
    }

    protected searchForString(String findMe) {
        String action = 'search for ' + findMe
        myLogger(0, action)
        sendKeys(SEARCH_BAR_INPUT, findMe)
        click(EXECUTE_SEARCH_BUTTON)
        myLogger(1, action)
        return this
    }

    //  -------------- fundamental driver methods with logs & built in waits --------------

    protected sendKeys(By selector, String text) {
        String thisAction = String.format('to send key(s) %s to', text)
        myLogger(0, thisAction, selector)
        WebElement thisElement = getElement (selector)
        try {
            thisElement.sendKeys(text)
            myLogger(1, thisAction, selector)
            return this
        }
        catch (WebDriverException generalWebDriverException) {
            myLogger(2, thisAction, selector)
            throw generalWebDriverException
        }
    }

    protected click(By selector) {
        String thisAction = 'click'
        myLogger(0, 'click', selector)
        WebElement thisElement = getElement (selector)
        try {
            thisElement.click()
            myLogger(1, thisAction, selector)
            return this
        }
        catch (WebDriverException generalWebDriverException) {
            myLogger(2, thisAction, selector)
            throw generalWebDriverException
        }
    }

    protected WebElement getElement(By selector) {
        String thisAction = 'getElement'
        myLogger(0, thisAction, selector)
        waitForElement(selector)
        try {
            myLogger(1, thisAction, selector)
            return driver.findElement(selector)
        }
        catch (WebDriverException generalWebDriverException) {
            myLogger(2, thisAction, selector)
            throw generalWebDriverException
        }
    }

    protected waitForElement(By selector) {
        assert null != wait
        String thisAction = 'wait until the driver can find'
        myLogger(0, thisAction , selector)
        try {
            wait.until(webDriver -> driver.findElement(selector))
        }
        catch (WebDriverException generalWebDriverException) {
            myLogger(2, thisAction, selector)
            throw generalWebDriverException
        }
    }

    //  ------------ logger formatter ------------

    def myLogger(int startOrEndOrFail, String thisAction, By selector) {
        String subject = String.format('the element found by the `%s` Selector', selector)
        myLogger(startOrEndOrFail, String.format('%s %s', thisAction, subject))
    }

    def myLogger(int startOrEndOrFail, String thisAction) {
        if (startOrEndOrFail == 2) {
            log.severe(String.format('Failed to %s.%s', thisAction, '\n\tStackTrace:\n\t%s'))
        }
        log.info(String.format('%s %s.', myLogger(startOrEndOrFail), thisAction))
    }

    def myLogger(int startOrEndOrFail) {
        switch (startOrEndOrFail) {
            case 0:
                return 'Beginning attempt'
            case 1:
                return 'Finished attempt'
            default:
                throw new IllegalStateException('myLogger(int) accepts only 0, 1 or 2.')
        }
    }


}
