package initPoint;
import PageObject.SearchPageObject;
import PageObject.SwipePageObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class CoreTestCase extends TestCase {
    public AppiumDriver driver;
    public  String AppiumURL = "http://127.0.0.1:4723/wd/hub";
    public SearchPageObject search = new SearchPageObject(driver);
    public SwipePageObject swipe = new SwipePageObject(driver);

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","TestDevice");
        capabilities.setCapability("platformVersion","7.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","dev.akat.filmreel");
        capabilities.setCapability("appActivity","com.akat.filmreel.ui.MainActivity");
        capabilities.setCapability("app","/Users/rinatmachmutov/Desktop/Otus_demo/apk/app-debug.apk");
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
    }
    @Override
    public void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
}
