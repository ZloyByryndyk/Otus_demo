package PageObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class IOSPageObject extends Main {
    public IOSPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // выносим локаторы в константы
    private static final String
            demo = "Демо-режим";

    public void init_demo() {
        Main.waitForElementAndClick(By.id(demo),"Не нашел кнопку закрытия", 5);
    }
}
