package PageObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SwipePageObject extends Main{
    public SwipePageObject(AppiumDriver driver) {
        super(driver);
    }
    // выносим локаторы в константы
    private static final String
        swipeToFindElement = "//*[contains(@text, '{substring}')]";
    // вспомогательный метод для добавления substring
    public  String getResultAndReplace(String substring)
    {
        return swipeToFindElement.replace("{substring}", substring);
    }
    // конструктор - метод который ищет конкретный фильм
    public void swipeToCurrentElement(String substring)
    {
        String qqq = getResultAndReplace(substring);
        this.swipeUpToFindElement(By.xpath(qqq),"Не могу найти нужный элемент" + " " + substring, 10);
    }
}
