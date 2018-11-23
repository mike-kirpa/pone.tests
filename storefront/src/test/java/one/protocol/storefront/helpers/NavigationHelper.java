package one.protocol.storefront.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavigationHelper {
    private WebDriver driver;
    WebElement ondesktopicon = driver.findElement(By.cssSelector("button.b-menu-platforms__button:nth-child(3)"));
    WebElement onlaptopicon = driver.findElement(By.cssSelector("button.b-menu-platforms__button:nth-child(2)"));
    WebElement onmobileicon = driver.findElement(By.cssSelector("button.b-menu-platforms__button:nth-child(1)"));

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void gotoEditorPage()  {
        driver.findElement(By.xpath("//*[contains(text(),'Landing1')]")).click();
    }

    public void openDesktopEditorPage () {ondesktopicon.click();}
    public void openLaptopEditorPage () {onlaptopicon.click();}
    public void openMobileEditorPage () {onmobileicon.click();}

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
