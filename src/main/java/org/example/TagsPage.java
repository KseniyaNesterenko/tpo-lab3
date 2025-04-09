package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TagsPage extends BaseSearchPage {
    public TagsPage(WebDriver driver) {
        super(driver, By.xpath("//*[contains(@name, 'tagfilter')]"),
                By.xpath("//*[contains(text(), 'selenium')]"));
    }
}
