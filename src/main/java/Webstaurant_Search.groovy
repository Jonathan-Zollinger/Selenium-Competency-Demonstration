import com.google.common.collect.ImmutableMap;

class Webstaurant_Search {
    Map<String, String> selectors;

    Webstaurant_Search() {
        this.selectors = populateSelectors()
    }

    private Map<String, String> populateSelectors(){
        this.selectors = ImmutableMap.of(
        //  ------ "my-selector-name", "xpath" ------
                "search-bar-input", "input[id='searchval']",
                "search-bar-submit-button", "button[value='Search']"
        )
    }



}
