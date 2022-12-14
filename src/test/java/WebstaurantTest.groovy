/* groovylint-disable JUnitPublicProperty */

import com.google.common.collect.ImmutableMap
import org.openqa.selenium.By

import java.util.logging.Logger
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import MyUtils

class WebstaurantTest {

    static final Logger log = Logger.getLogger(this.class.getName())
    WebDriver driver
    

    @Test
    void AllSearchResultsContainKeyWord() {
        driver = MyUtils.getChromeDriver()
        WebstaurantSearchResults WebstaurantSearchResults = new WebstaurantSearchResults(driver)
        WebstaurantSearchResults.searchForString('stainless work table')
        List<WebElement> results = WebstaurantSearchResults.GetPageResultsFromAllPages()

        myLogger(0, 'to validate results contain `table` in their title')
        int iterator = 1

        results.forEach { WebElement result ->
            myLogger(0, String.format('to validate result %d of %d', iterator, results.size()))
            assert (result.getText().toLowerCase().contains('table'))
            myLogger(1, String.format('to validate result %d of %d', iterator, results.size()))
            iterator ++
        }

        driver.quit()
    }

    //  ------------ logger formatter ------------

    static myLogger(int startOrEndOrFail, String thisAction) {
        if (startOrEndOrFail == 2) {
            log.severe(String.format('Failed to %s.%s', thisAction, '\n\tStackTrace:\n\t%s'))
        }
        log.info(String.format('%s %s.', myLogger(startOrEndOrFail), thisAction))
    }

    static String myLogger(int startOrEndOrFail) {
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