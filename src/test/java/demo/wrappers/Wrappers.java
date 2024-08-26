package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    private WebDriver driver;
    private WebDriverWait wait;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    // Navigate to a specified URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    // Click an element after waiting for it to be clickable
    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    // Get text from an element after waiting for it to be visible
    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    // Scroll horizontally within a specific element
    public void scrollHorizontally(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", element);
    }
    public WebElement findElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            System.out.println("Element not found: " + e.getMessage());
            return null;
        }
    }

    // Wait for an element to be visible and return it
    public WebElement waitForElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public WebElement waitForElement(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
