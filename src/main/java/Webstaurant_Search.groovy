import com.google.common.collect.ImmutableMap
import org.openqa.selenium.WebDriver;

class Webstaurant_Search {
    Map<String, String> selectors;

    Webstaurant_Search() {
        this.selectors = populateSelectors()
    }

    private Map<String, String> populateSelectors(){
        this.selectors = ImmutableMap.of(
        //  ------ "my-selector-name", "xpath" ------
                "search-bar-input", "input[id='searchval']",
        )
    }

    public searchForString(WebDriver driver)



}
