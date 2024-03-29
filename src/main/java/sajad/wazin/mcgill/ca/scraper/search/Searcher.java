package sajad.wazin.mcgill.ca.scraper.search;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author Sajad Wazin @ https://github.com/swzn
 * @project FacebookWebScraper
 * @mail sajad.wazin@mail.mcgill.ca
 */

public interface Searcher {
    void add(String link);
    int search();
}
