package initPoint;
import PageObject.SearchPageObject;
import PageObject.SwipePageObject;
import PageObject.IOSPageObject;
import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;

public class CoreTestCase extends TestCase {
    public AppiumDriver driver;
    public Platform Platform;

    public SearchPageObject search = new SearchPageObject(driver);
    public SwipePageObject swipe = new SwipePageObject(driver);
    public IOSPageObject ios = new IOSPageObject(driver);

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        this.Platform = new Platform();
        driver = this.Platform.getDriver();
    }
    @Override
    public void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
}
