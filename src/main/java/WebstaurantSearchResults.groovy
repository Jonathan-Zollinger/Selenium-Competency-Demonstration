import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement

class WebstaurantSearchResults extends Webstaurant {

    final private By RESULTS_DESCRIPTION = By.xpath("//div[@id='main']//a[@data-testid='itemDescription']")

    WebstaurantSearchResults(WebDriver driver) {
        super(driver)
    }

    List<String> GetPageResultsTitlesFromAllPages() {
        List<String> allResultsTitlesFromAllPages = getResultsTitles()
        if (isPaginationPresent()) {
            int lastPageNumber = getLastPageNumber() //last page number isn't available when on the last page
            int thisPageNumber = getThisPageNumber()
            while (thisPageNumber != lastPageNumber) {
                click (PAGINATION_NEXT_PAGE)
                getResultsTitles().addAll(getResultsTitles())
                thisPageNumber = getThisPageNumber()
            }
        }
        return allResultsTitlesFromAllPages
    }


    List<String> getResultsTitles() {
        List<String> resultTitles = new ArrayList<>()
        getElements(RESULTS_DESCRIPTION).forEach { WebElement webElement ->
            resultTitles.add(webElement.getText())
        }
        return resultTitles
    }


}
