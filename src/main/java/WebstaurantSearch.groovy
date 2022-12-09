package Main

import com.google.common.collect.ImmutableMap
import org.openqa.selenium.WebDriver;

class WebstaurantSearch {

    public enum SelectorEnum {
        SEARCH_BAR_TEXT_INPUT("search-bar-input", "input[id='searchval']")

        final String name
        final String xpath
        static final Map map

        static {
            map = new HashMap<String,String>()
            values().each{ Selector ->
                printf("Name: %s, xpath: %s", Selector.name, Selector.xpath)
                map.put(Selector.name, Selector.xpath)
            }
        }

        private SelectorEnum(String name, String xpath) {
            this.name   = name
            this.xpath  = xpath
        }

        static getSelector( String name ) {
            return map[name]
        }

    }
    Map<String, String> selectors;

    WebstaurantSearch() {
    }


    public searchForString(WebDriver driver, String findMe){
        driver.findElement {selectors.get("search-bar-input")}
    }



}
