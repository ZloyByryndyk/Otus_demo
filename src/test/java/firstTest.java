import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


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
    }

    // After анотация к Junit
    @After
    //tearDown метод исполняющий действия после прохождения теста'ов
    public void tearDown()
    {
        driver.quit();
    }

    // Test анотация для Junit
    @Test
    //тест который жмёт  кнопку поиска, вбивает в input значение "film", а потом выбираем конкретный фильм из появившегося списка
    public  void find_film() {
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc=\"Search\"]"),
                "Не смог найти кнопку поиска",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='dev.akat.filmreel:id/search_src_text']"),
                "film",
                "Не смог найти инпут для ввода текста"
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'A Serbian Film')]"),
                "Не смог найти фильм",
                5
        );
    }

    @Test
    //
    public void cancel_search () {
        waitForElementAndClick(
                By.id("dev.akat.filmreel:id/menu_search_action"),
                "Не смог найти кнопку поиска",
                5
        );
        waitForElementAndClick(
                By.id("dev.akat.filmreel:id/search_src_text"),
                "Не смог найти инпут для ввода текста",
                5
        );
        waitForElementAndClick(
                By.id("Collapse"),
                "Не могу найти кнопку назад",
                5
        );
        waitForElementAndClick(
                By.id("Navigate up"),
                "Не могу найти вторую кнопку назад",
                5
        );
        waitForElementNotPresentById(
                By.id("dev.akat.filmreel:id/search_src_text"),
                "Инпут с предыдущего экрана все еще есть",
                5
        );
    }

    @Test
    public void assertTest () {
        waitForElementAndClick(
                By.id("dev.akat.filmreel:id/menu_search_action"),
                "Не смог найти кнопку поиска",
                5
        );
        waitForElementAndSendKeys(
                By.id("dev.akat.filmreel:id/search_src_text"),
                "film",
                "Не смог найти инпут для ввода текста"
        );
        waitForElementAndClick(
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[1]"),
                "Не могу найти конкретный фильм",
                5
        );
        WebElement film_text = waitForElement(
                By.id("dev.akat.filmreel:id/movie_overview"),
                "Не могу найти описание фильма"
        );

        String actual = film_text.getText();

        Assert.assertEquals(
                "не совпадает",
                "Milos, a retired porn star, leads a normal family life trying to make ends meet. Presented with the opportunity of a lifetime to financially support his family for the rest of their lives, Milos must participate in one last mysterious film. From then on, Milos is drawn into a maelstrom of unbelievable cruelty and mayhem.",
                 actual
        );
    }

    @Test
    public void search_and_clear()
    {

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@content-desc=\"Search\"]"),
                "Не смог найти кнопку поиска",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='dev.akat.filmreel:id/search_src_text']"),
                "film",
                "Не смог найти инпут для ввода текста"
        );

        WebElement clear = waitForElementAndClear(
                By.xpath("//*[@resource-id='dev.akat.filmreel:id/search_src_text']"),
                "Не смог очистить инпут",
                5
        );

        String assert_clear = clear.getText();
        Assert.assertEquals(
                "Текст все еще есть",
                "   ",
                assert_clear
        );
    }

    @Test
    public void swipe_test ()
    {
        swipeUpToFindElement(
                By.xpath("//*[contains(@text, 'The Hunt')]"),
                "Фильма нет в списке",
                10
        );
    }



    // метод который ищет элемент по xpath
    // (при вызове необходимо передать в него 3 аргумента: xpath, сообщение об ошибке и время ожидание элемента)
    private WebElement waitForElement(By by, String error_message, long timeOutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    //перегрузка предыдущего метода, в отличие от первого сюда нужно передать два аргумента (без времени)
    //то есть, когда нам нужно указать конкретное время используем первый метод, если нас устраивает стандартное время
    //в 5 секунд, используем кторой метод
    private WebElement waitForElement(By by, String error_message)
    {
        return waitForElement(by, error_message, 5);
    }
    // метод который ищет элемент по xpath и кликает на него
    private void waitForElementAndClick(By by, String error_message, long timeOutInSecond)
    {
        WebElement element =  waitForElement(by, error_message,timeOutInSecond);
        element.click();

    }
    // перегрузка предыдущего метода,
    private WebElement waitForElementAndClick(By by, String error_message)
    {
        WebElement element =  waitForElement(by, error_message,5);
        element.click();
        return element;
    }
    //метод который ищет элемент по xpath и вводит в него значение
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSecond)
    {
        WebElement element =  waitForElement(by, error_message,timeOutInSecond);
        element.sendKeys(value);
        return element;
    }
    //перегрузка предыдущего метода
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message)
    {
        WebElement element =  waitForElement(by, error_message,5);
        element.sendKeys(value);
        return element;
    }
    //метод который ищет элемент по Id и проверяет что его нету на странице (экране)
    private boolean waitForElementNotPresentById (By by, String error_message, long timeOutInSecond)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear (By by, String error_message, long timeOutInSecond)
    {
        WebElement element = waitForElement(by,error_message,timeOutInSecond);
        element.clear();
        return element;
    }

    public void simple_swipe(int fromX, int fromY,int toX, int toY)
    {
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(fromX,fromY)).waitAction().moveTo(PointOption.point(toX,toY)).release().perform();
    }

    public void swipeUp ()
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width/2;

        int start_y = (int)(size.height*0.8);

        int end_y = (int) (size.height*0.2);

        action.press(PointOption.point(x,start_y)).waitAction().moveTo(PointOption.point(x,end_y)).release().perform();
    }

    public void swipeUpToFindElement (By by, String error_message, int max_swipe)
    {
        int already_swipe = 0;
        while (driver.findElements(by).size() == 0){

            if (already_swipe > max_swipe){
                waitForElement(by,"Закончились свапы" + " " + error_message);
                return;
            }

            swipeUp();
            ++ already_swipe;
        }
    }
}




