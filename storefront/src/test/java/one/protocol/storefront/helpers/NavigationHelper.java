package one.protocol.storefront.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavigationHelper {
    private WebDriver driver;
    By onDesktopIcon = By.xpath("//*[@tooltip=\"on desktop\"]");
    By onLaptopIcon = By.xpath("//*[@tooltip=\"on laptop\"]");
    By onMobileIcon = By.xpath("//*[@tooltip=\"on mobile\"]");
    By minimizePanelBtn = By.xpath("//*[@title=\"Minimize panel\"]");

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void gotoEditorPage()  {
        driver.findElement(By.xpath("//*[contains(text(),'Landing1')]")).click();
    }


    public void clickOn (By locator) {
        driver.findElement(locator).click();
    }

    public void modeSwitcher(String mode) {
        switch (mode) {
            case "desktop":
                clickOn(onDesktopIcon);
                break;
            case "laptop":
                clickOn(onLaptopIcon);
                break;
            case "mobile":
                clickOn(onMobileIcon);
                break;
            default:
                System.out.println("not a viewmode");
                break;
        }
    }

    public void Url(String Url) {
        driver.get(Url);
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
