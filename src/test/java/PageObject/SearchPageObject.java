package PageObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends Main{
    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }
    // выносим локаторы в константы
    private static final String
        searchButton = "//android.widget.TextView[@content-desc=\"Search\"]",
        searchInput ="//*[@resource-id='dev.akat.filmreel:id/search_src_text']",
        currentFilm = "//*[contains(@text, '{substring}')]";

    // вспомогательный метод для добавления substring
    public  String getResultAndReplace(String substring)
    {
        return currentFilm.replace("{substring}", substring);
    }
    // конструктор - ищет иконку поиска на главной странице
    public void  initSearch()
    {
        this.waitForElementAndClick(By.xpath(searchButton),"Не могу найти кнопку поиска", 5);
    }
    //ищет инпут
    public void searchInput(String search_value)
    {
        this.waitForElementAndSendKeys(By.xpath(searchInput),search_value, "Не могу найти инпут", 5);
    }
    //ищет конкретный фильм из списка поиска
    public void clickFilm(String substring)
    {
        String qqq = getResultAndReplace(substring);
        this.waitForElementAndClick(By.xpath(qqq),"Не могу найти фильм" + " " + substring, 5);
    }
}
