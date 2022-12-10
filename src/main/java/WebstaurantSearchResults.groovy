import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class WebstaurantSearchResults extends Webstaurant{

    final private By ALL_RESULTS         = By.xpath("//div[@id='main']")
    final private By RESULTS_DESCRIPTION = By.xpath("//div[@id='main']//a[@data-testid='itemDescription']")

    WebstaurantSearchResults(WebDriver driver){
        super(driver)
    }

    List<WebElement> GetResults() {
        waitForElement(ALL_RESULTS)
        return driver.findElements(RESULTS_DESCRIPTION)

    }
}
