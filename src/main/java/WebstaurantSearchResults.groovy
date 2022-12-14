import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement

class WebstaurantSearchResults extends Webstaurant {

    final private By ALL_RESULTS = By.xpath("//div[@id='main']")
    final private By RESULTS_DESCRIPTION = By.xpath("//div[@id='main']//a[@data-testid='itemDescription']")

    WebstaurantSearchResults(WebDriver driver) {
        super(driver)
    }

    List<WebElement> GetPageResultsFromAllPages() {
        List<WebElement> allResultsFromAllPages = getResults()
        if (isPaginationPresent()) {
            int lastPageNumber = getLastPageNumber() //last page number isn't available when on the last page
            int thisPageNumber = getThisPageNumber()
            while (thisPageNumber != lastPageNumber) {
                clickNextPage()
                allResultsFromAllPages.addAll(getResults())
                thisPageNumber = getThisPageNumber()
            }
        }
        return allResultsFromAllPages
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
