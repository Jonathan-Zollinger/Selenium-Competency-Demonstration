import com.google.common.collect.ImmutableMap
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import java.util.logging.Logger

import static org.junit.Assert.assertTrue

class WebstaurantTest {
    Map<String, String> testSearchWords = ImmutableMap.of(
            //  "Search Term",  "Search Assertion"
            "stainless work table", "Table"
    )
    WebDriver driver;
    Logger log = Logger.getLogger(this.class.getName())


    @Test
    void AllSearchResultsContainKeyWord() {
        driver = MyUtils.getChromeDriver()
        Webstaurant webstaurant = new Webstaurant(driver);
        for (String searchTerm : testSearchWords.keySet()) {


            webstaurant.searchForString(searchTerm)

            validateWebElementsText(
                    (webstaurant as WebstaurantSearchResults).GetPageResultsFromAllPages(),
                    testSearchWords.get(searchTerm)
            )
            driver.quit()
        }
    }

    void validateWebElementsText(List<WebElement> webElements, String text) {
        if ( null == webElements ) { new IllegalStateException ( "List of elements received is null." ) }

        log.info ( String.format ("Beginning attempt to validate %d results contain `%s` in their titles",
                webElements.size(), text))
        int iterator = 1 //used in logs only

        webElements.forEach { WebElement webElement ->
            log.info(String.format("Beginning attempt to validate result %d of %d contains `%s` in its title" ,
                     iterator,webElements.size(),text))
            assertTrue(webElement.getText().toLowerCase().contains(text.toLowerCase()))
            log.info(String.format("Finished attempt to validate result %d of %d contains `%s` in its title" ,
                    iterator,webElements.size(),text))
            iterator ++
        }

        log.info ( String.format ("Finished attempt to validate %d results contain `%s` in their titles",
                webElements.size(), text))
    }
}