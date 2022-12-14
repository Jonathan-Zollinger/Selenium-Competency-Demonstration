/* groovylint-disable JUnitPublicProperty */


import com.sun.codemodel.JTryBlock
import org.junit.jupiter.api.DisplayName

import java.util.logging.FileHandler
import java.util.logging.SimpleFormatter

import static org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.openqa.selenium.WebDriver


import java.util.logging.Logger

class WebstaurantTest {

    static final Logger log = Logger.getLogger(this.class.getName())
    WebDriver driver
    @Rule
    public ErrorCollector collector = new ErrorCollector()


    @Test
    @DisplayName('Test if Result Titles contains `table`')
    void checkTitlesForTable() {
        driver = MyUtils.getChromeDriver()
        WebstaurantSearchResults WebstaurantSearchResults = new WebstaurantSearchResults(driver)
        WebstaurantSearchResults.searchForString('stainless work table')
        List<String> results = WebstaurantSearchResults.GetPageResultsTitlesFromAllPages()

        myLogger(0, 'to validate results contain `table` in their title')
        int iterator = 1

        results.forEach { String result ->
            String thisAction = String.format('to validate title `%s` (%d of %d)', result, iterator, results.size())
            myLogger(0, thisAction)
            try {
                collector.checkThat(result.toLowerCase(), containsString('table'))
            } catch (SocketException ignore) {}

            myLogger(1, thisAction)
            iterator++
        }

        driver.quit()
    }

    //  ------------ log message formatter ------------

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