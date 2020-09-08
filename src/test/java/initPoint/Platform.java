package initPoint;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    public  String AppiumURL = "http://127.0.0.1:4723/wd/hub";
    public final String PLATFORM_IOS = "ios";
    public final String PLATFORM_ANDROID = "android";

    public AppiumDriver getDriver()throws Exception {
        URL URL = new URL(AppiumURL);
        if (this.isAndroid()){
            return new AndroidDriver(URL, this.getAndroidCapabilies());
        } else if (this.isIOS()){
            return new IOSDriver(URL,this.getiOSCapabilies());
        } else throw new Exception("Не знаю что за драйвер" + this.getPlatformVar());
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }



    public DesiredCapabilities getAndroidCapabilies() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","TestDevice");
        capabilities.setCapability("platformVersion","7.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","dev.akat.filmreel");
        capabilities.setCapability("appActivity","com.akat.filmreel.ui.MainActivity");
        capabilities.setCapability("app","/Users/rinatmachmutov/Desktop/Otus_demo/apk/app-debug.apk");
        return capabilities;
    }

    public DesiredCapabilities getiOSCapabilies() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone X");
        capabilities.setCapability("platformVersion","12.0");
        capabilities.setCapability("app","/Users/rinatmachmutov/Desktop/Otus_demo/app/AlfaStrah.app");
        capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("noReset","true");
        return capabilities;
    }

    public boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }
    public String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}
