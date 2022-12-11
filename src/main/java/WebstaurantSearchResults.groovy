import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement

class WebstaurantSearchResults extends Webstaurant{

    final private By ALL_RESULTS         = By.xpath("//div[@id='main']")
    final private By RESULTS_DESCRIPTION = By.xpath("//div[@id='main']//a[@data-testid='itemDescription']")

    WebstaurantSearchResults(WebDriver driver){
        super(driver)
    }

    List<WebElement> GetPageResultsFromAllPages(){
        List<WebElement> allResultsFromAllPages = getResults()
        if (isPaginationPresent()) {
            int lastPageNumber = getLastPageNumber() //last page number isn't available when on the last page
            while (getThisPageNumber() != lastPageNumber) {
                clickNextPageByNumber()
                allResultsFromAllPages.addAll(getResults())
            }
        }
        return allResultsFromAllPages
    }

    boolean isPaginationPresent() {
        try{
            driver.findElement {PAGINATION_NUMBERS}
            log.info(String.format("Determined results span more than one page " +
                    "because WebElements are found with the `%s` selector", PAGINATION_NUMBERS))
            return true
        }
        catch(NoSuchElementException ignored) {
            log.info(String.format("Determined results to span only one page " +
                    "because no WebElements are found with the `%s` selector", PAGINATION_NUMBERS))
            return false
        }
        catch(WebDriverException generalWebDriverException) {
            log.severe(String.format("Encountered an exception other than 'NoSuchElementException'."))
            throw generalWebDriverException
        }
    }
    
    int getThisPageNumber() {
        assert isPaginationPresent()
        try {
            return Integer.parseInt(driver.findElement { PAGINATION_CURRENT_PAGE }.getText())
        } 
        catch (Exception pokemonCatch) { //catch 'em all
            log.severe("Encountered an error when returning the current page number.")
            throw pokemonCatch
        }       
    }
    
    int getLastPageNumber() {
        assert isPaginationPresent()
        try {
            return Integer.parseInt(driver.findElement { PAGINATION_LAST_PAGE }.getText()
            )
        }
        catch (Exception pokemonCatch) { 
            log.severe("Encountered an Error when deriving the last page number.")
            throw pokemonCatch
        }
    }
    
    void clickNextPageByNumber() {
        try {
            driver.findElement(By.xpath(String.format(XPATH_PAGINATION_FORMAT_NUMBER, (getThisPageNumber() + 1))))
                    .click()
        }
        catch (Exception pokemonCatch) {
            log.severe("Encountered an Error when clicking the next page by number.")
            throw pokemonCatch
        }
    }

    List<WebElement> getResults() {
        waitForElement(ALL_RESULTS)
        try {
            return driver.findElements(RESULTS_DESCRIPTION)
        }
        catch (Exception pokemonCatch) {
            log.severe("Encountered an Error when returning a the webElements of all results' descriptions")
            throw pokemonCatch
        }
        
    }
}
