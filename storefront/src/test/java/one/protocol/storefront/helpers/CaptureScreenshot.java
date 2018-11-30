package one.protocol.storefront.helpers;

import org.openqa.selenium.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CaptureScreenshot {
    private WebDriver driver;

    CaptureScreenshot (WebDriver driver) {
        this.driver = driver;
    }

    public void Screenshot(String browser, Integer width, Integer height, String mode, String HeaderSelector) throws IOException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (HeaderSelector != null) {
            WebElement HeaderElement = driver.findElement(By.xpath(HeaderSelector));
            js.executeScript("arguments[0].setAttribute('style', 'position: static !important;')", HeaderElement);
        }
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG", new File("G:\\" + browser + "_" + width + "_" + height + "_" + mode + ".png"));
    }
}
