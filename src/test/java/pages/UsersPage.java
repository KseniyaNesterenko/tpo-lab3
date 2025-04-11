package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersPage extends BaseSearchPage {
    public UsersPage(WebDriver driver) {
        super(driver, By.xpath("//*[contains(@name, 'userfilter')]"),
                By.xpath("//*[contains(text(), 'Дмитрий')]"));
    }
}
