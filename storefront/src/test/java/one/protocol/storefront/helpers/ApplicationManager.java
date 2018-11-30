package one.protocol.storefront.helpers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import static org.testng.Assert.fail;

public class ApplicationManager {

    WebDriver driver;
    private String browser;
    private StringBuffer verificationErrors = new StringBuffer();
    private final Properties properties;
    private NavigationHelper navigationHelper;
    private CaptureScreenshot captureScreenshot;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {



        //получаем значение target или подставляем дефолтное
        String target = System.getProperty("target", "stg");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));


        System.setProperty("webdriver.chrome.driver",getResource("/webdriver/chromedriver.exe"));
        System.setProperty("webdriver.gecko.driver",getResource("/webdriver/geckodriver.exe"));
        System.setProperty("webdriver.opera.driver",getResource("/webdriver/operadriver.exe"));
        System.setProperty("webdriver.edge.driver",getResource("/webdriver/MicrosoftWebDriver.exe"));

        if (browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if (browser.equals(BrowserType.EDGE)) {
            driver = new EdgeDriver();
        } else if (browser.equals(BrowserType.OPERA)) {
            driver = new OperaDriver();
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get(properties.getProperty("web.baseURL"));

        navigationHelper = new NavigationHelper(driver);
        captureScreenshot = new CaptureScreenshot(driver);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public void changeResolution(Integer width, Integer height){
        driver.manage().window().setSize(new Dimension(width,height));
    }


    public String getResource(String resourceName) {
        try {
            return Paths.get(ApplicationManager.class.getResource(resourceName).toURI()).toFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return resourceName;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public  CaptureScreenshot takeScreenshot(){
        return captureScreenshot;
    }

    public void startSetParameters(String key) {
        String value = properties.getProperty(key + ".parameters");
        ((JavascriptExecutor)driver).executeScript(String.format("window.localStorage.setItem('%s','%s');", key, value));
        driver.navigate().refresh();
    }

}
