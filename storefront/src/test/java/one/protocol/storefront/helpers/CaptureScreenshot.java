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

    public void Screenshot(String browser, Integer width, Integer height) throws IOException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement header = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]"));
        js.executeScript("arguments[0].setAttribute('style', 'position: static !important;')",header);
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG", new File("G:\\" + browser + "_" + width + "_" + height + ".png"));
    }
}
