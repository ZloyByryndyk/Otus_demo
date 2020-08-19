import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class firstTest {

    private AppiumDriver driver;

    // Before это анотация к Junit
    @Before
    //setUp метод исполняющий базовые настройки перед запуском теста
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","TestDevice");
        capabilities.setCapability("platformVersion","7.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","dev.akat.filmreel");
        capabilities.setCapability("appActivity","com.akat.filmreel.ui.MainActivity");
        capabilities.setCapability("app","/Users/rinatmachmutov/Desktop/Otus_demo/apk/app-debug.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        // эта конструкция задаёт неочевидную задержку перед исполнением след шага, то есть любое действие будет
        // ожидать 30 сек пока не найдет указанный элемент, а потом тест удадет
        // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    // After анотация к Junit
    @After
    //tearDown метод исполняющий действия после прохождения теста'ов
    public void tearDown() throws Exception
    {
        driver.quit();
    }

    // Test анотация для Junit
    @Test
    //тест который жмёт  кнопку поиска, вбивает в input значение "film", а потом выбираем конкретный фильм из появившегося списка
    public  void find_film() {
        waitForElementAndClickByXpath(
                "//android.widget.TextView[@content-desc=\"Search\"]",
                "Не смог найти кнопку поиска"
        );
        waitForElementAndSendKeysByXpath(
                "//*[@resource-id='dev.akat.filmreel:id/search_src_text']",
                "film",
                "Не смог найти инпут для ввода текста"
        );
        waitForElementAndClickByXpath(
                "//*[contains(@text, 'A Serbian Film')]",
                "Не смог найти фильм"
        );
    }


    @Test
    //
    public void cancel_search () {
        waitForElementByIdAndClick(
                "dev.akat.filmreel:id/menu_search_action",
                "Не смог найти кнопку поиска"
        );
        waitForElementByIdAndClick(
                "dev.akat.filmreel:id/search_src_text",
                "Не смог найти инпут для ввода текста"
        );
        waitForElementByIdAndClick(
                "Collapse",
                "Не могу найти кнопку назад"
        );
        waitForElementByIdAndClick(
                "Navigate up",
                "Не могу найти вторую кнопку назад"
        );
        waitForElementNotPresentById(
                "dev.akat.filmreel:id/search_src_text",
                "Инпут с предыдущего экрана все еще есть",
                5
        );
    }


    @Test
    public void asserttest () {
        waitForElementByIdAndClick(
                "dev.akat.filmreel:id/menu_search_action",
                "Не смог найти кнопку поиска"
        );
        waitForElementByIdAndSendKeys(
                "dev.akat.filmreel:id/search_src_text",
                "film",
                "Не смог найти инпут для ввода текста"
        );
        waitForElementAndClickByXpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[1]",
                "Не могу найти конкретный фильм"
        );
        WebElement film_text = waitForElementById(
                "dev.akat.filmreel:id/movie_overview",
                "Не могу найти описание фильма"
        );

        String actual = film_text.getText();

        Assert.assertEquals(
                "не совпадает",
                "Milos, a retired porn star, leads a normal family life trying to make ends meet. Presented with the opportunity of a lifetime to financially support his family for the rest of their lives, Milos must participate in one last mysterious film. From then on, Milos is drawn into a maelstrom of unbelievable cruelty and mayhem.",
                 actual
        );

        new TouchAction(driver).press(PointOption.point(713,2325)).waitAction().press(PointOption.point(799,193)).release().perform();

    }





    // метод который ищет элемент по xpath
    // (при вызове необходимо передать в него 3 аргумента: xpath, сообщение об ошибке и время ожидание элемента)
    private WebElement waitForElementByXpath (String xpath, String error_message, long timeOutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //перегрузка предыдущего метода, в отличие от первого сюда нужно передать два аргумента (без времени)
    //то есть, когда нам нужно указать конкретное время используем первый метод, если нас устраивает стандартное время
    //в 5 секунд, используем кторой метод
    private WebElement waitForElementByXpath (String xpath, String error_message)
    {
        return waitForElementByXpath(xpath, error_message, 5);
    }
    // метод который ищет элемент по xpath и кликает на него
    private WebElement waitForElementAndClickByXpath(String xpath, String error_message, long timeOutInSecond)
    {
        WebElement element =  waitForElementByXpath(xpath, error_message,timeOutInSecond);
        element.click();
        return element;
    }
    // перегрузка предыдущего метода,
    private WebElement waitForElementAndClickByXpath(String xpath, String error_message)
    {
        WebElement element =  waitForElementByXpath(xpath, error_message,5);
        element.click();
        return element;
    }
    //метод который ищет элемент по xpath и вводит в него значение
    private WebElement waitForElementAndSendKeysByXpath(String xpath, String value, String error_message, long timeOutInSecond)
    {
        WebElement element =  waitForElementByXpath(xpath, error_message,timeOutInSecond);
        element.sendKeys(value);
        return element;
    }
    //перегрузка предыдущего метода
    private WebElement waitForElementAndSendKeysByXpath(String xpath, String value, String error_message)
    {
        WebElement element =  waitForElementByXpath(xpath, error_message,5);
        element.sendKeys(value);
        return element;
    }



    //метод который ищет элемент по Id
    private WebElement waitForElementById (String id, String error_message, long timeOutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //перегрузка предыдущего метода
    private WebElement waitForElementById(String id, String error_message)
    {
        WebElement element =  waitForElementById(id, error_message,5);
        element.click();
        return element;
    }
    //метод который ищет элемент по Id и кликает на него
    private WebElement waitForElementByIdAndClick (String id, String error_message, long timeOutInSecond)
    {
        WebElement element = waitForElementById(id, error_message, timeOutInSecond);
        element.click();
        return element;
    }
    //перегрузка предыдущего метода
    private WebElement waitForElementByIdAndClick (String id, String error_message)
    {
        WebElement element = waitForElementById(id, error_message,5);
        element.click();
        return element;
    }
    //метод который ищет элемент по Id и вводит в него значение
    private WebElement waitForElementByIdAndSendKeys (String id,String value, String error_message, long timeOutInSecond)
    {
        WebElement element = waitForElementById(id, error_message, timeOutInSecond);
        element.sendKeys(value);
        return element;
    }
    // перегрузка предыдущего метода
    private WebElement waitForElementByIdAndSendKeys (String id,String value, String error_message)
    {
        WebElement element = waitForElementById(id, error_message,5);
        element.sendKeys(value);
        return element;
    }
    //метод который ищет элемент по Id и проверяет что его нету на странице (экране)
    private boolean waitForElementNotPresentById (String id, String error_message, long timeOutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
}




