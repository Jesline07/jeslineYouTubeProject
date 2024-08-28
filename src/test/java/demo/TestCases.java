package demo;

//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
//import org.junit.Assert;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
//import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow testCase01 testCase02... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @Test
        public void testCase01() throws Exception {
                Wrappers homePage = new Wrappers(driver);
                homePage.navigateToYouTube();
                String currentUrl = driver.getCurrentUrl();
                boolean status = currentUrl.contains("youtube");
                Assert.assertTrue(status);
                homePage.scrollFunction("4000");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='About']")));
                WebElement aboutLink = driver.findElement(By.xpath("//a[text()='About']"));
                homePage.clickOn(aboutLink);
                Thread.sleep(5000);
                String currentUrl1 = driver.getCurrentUrl();

                status = currentUrl1.contains("about");
                System.out.println("Moved to About Page");
                Assert.assertTrue(status);
                homePage.scrollFunction("3000");
                WebElement aboutText = driver
                                .findElement(By.xpath("//p[@class='lb-font-display-3 lb-font-color-text-primary'][2]"));
                String text = aboutText.getText();
                status = text.contains(" and that the world is a better place when we listen");

                if (text.contains(" and that the world is a better place when we listen")
                                && currentUrl1.contains("about")) {
                        status = true;
                        System.out.println("Page contains url about and the message displayed");
                } else {
                        status = false;
                }
                Assert.assertTrue(status);
                System.out.println(text);
                System.out.println("Printed the TEXT");
        }

        @Test
        public void testCase02() throws InterruptedException {
                Wrappers homePage = new Wrappers(driver);
                homePage.navigateToYouTube();
                Thread.sleep(3000);
                homePage.scrollFunction("4000");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//yt-formatted-string[text()='Movies']//ancestor::a")));
                WebElement moviesButton = driver
                                .findElement(By.xpath("//yt-formatted-string[text()='Movies']//ancestor::a"));
                homePage.clickOn(moviesButton);
                Thread.sleep(4000);
                homePage.scrollFunction("4000");
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//span[text()='Top selling']/ancestor::div[@class='grid-subheader style-scope ytd-shelf-renderer']/following-sibling::div[@id='contents']//button[@aria-label='Next']")));
                WebElement nextButton = driver
                                .findElement(By.xpath(
                                                "//span[text()='Top selling']/ancestor::div[@class='grid-subheader style-scope ytd-shelf-renderer']/following-sibling::div[@id='contents']//button[@aria-label='Next']"));
                while (nextButton.isDisplayed()) {
                        Thread.sleep(5000);
                        homePage.clickOn(nextButton);
                }
                homePage.movieCollection("Top selling");
        }

        @Test
        public void testCase03() throws InterruptedException {
                Wrappers homePage = new Wrappers(driver);
                homePage.navigateToYouTube();
                Thread.sleep(3000);
                homePage.scrollFunction("4000");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//yt-formatted-string[text()='Music']/parent::tp-yt-paper-item/parent::a")));
                WebElement musicButton = driver.findElement(
                                By.xpath("//yt-formatted-string[text()='Music']/parent::tp-yt-paper-item/parent::a"));
                homePage.clickOn(musicButton);
                // Thread.sleep(4000);
                homePage.scrollFunction("60000");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//span[contains(text(),'Biggest Hits') and @id='title']/ancestor::div[@class='grid-subheader style-scope ytd-shelf-renderer']/following-sibling::div[@id='contents']//button[@aria-label='Next']")));
                WebElement nextButton = driver.findElement(By.xpath(
                                "//span[contains(text(),'Biggest Hits') and @id='title']/ancestor::div[@class='grid-subheader style-scope ytd-shelf-renderer']/following-sibling::div[@id='contents']//button[@aria-label='Next']"));
                while (nextButton.isDisplayed()) {
                        Thread.sleep(2000);
                        homePage.clickOn(nextButton);
                }
                homePage.musicCollection("Biggest Hits");
        }

        @Test
        public void testCase04() throws InterruptedException {
                Wrappers homePage = new Wrappers(driver);
                homePage.navigateToYouTube();
                Thread.sleep(20000);
                homePage.scrollFunction("4000");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//yt-formatted-string[text()='News']/parent::tp-yt-paper-item/parent::a")));
                WebElement musicButton = driver.findElement(
                                By.xpath("//yt-formatted-string[text()='News']/parent::tp-yt-paper-item/parent::a"));
                homePage.clickOn(musicButton);
                homePage.scrollFunction("60000");
                Thread.sleep(10000);
                homePage.topThreeNews("Latest news posts");

        }

        @Test(dataProvider = "excelData")
        public void testCase05(String tobeSearched) throws InterruptedException {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                Wrappers homePage = new Wrappers(driver);
                homePage.navigateToYouTube();
                WebElement element1 = driver.findElement(By.xpath("//input[@id='search']"));
                element1.sendKeys(tobeSearched);
                element1.sendKeys(Keys.ENTER);
                wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//ytd-video-renderer/div[@id='dismissible']//span[contains(text(),'views')]")));
                List<WebElement> videoList = driver.findElements(By
                                .xpath("//ytd-video-renderer/div[@id='dismissible']//span[contains(text(),'views')]"));
                long sum = 0;
                for (WebElement videos : videoList) {

                        String viewsText = videos.getText();
                        String viewsText1 = viewsText.trim();
                        String arr[] = viewsText1.split(" ");
                        String val = arr[0];
                        System.out.println("view for single video : " + val);
                        long viewsNum = 0;
                        String numericString = "";
                        if (val.contains("M")) {
                                numericString = val.replace("M", "");
                                if (numericString.contains(".")) {
                                        viewsNum = (long) (Double.parseDouble(numericString) * 1000000);
                                } else {
                                        viewsNum = Integer.parseInt(numericString) * 1000000;
                                }
                        } else if (val.contains("K")) {
                                numericString = val.replace("K", "");
                                if (numericString.contains(".")) {
                                        viewsNum = (long) (Double.parseDouble(numericString) * 1000);
                                } else {
                                        viewsNum = Integer.parseInt(numericString) * 1000;
                                }
                        }
                        sum = sum + viewsNum;
                        homePage.scrollFunction("5000");
                        if (sum >= 100000000) {
                                break;
                        }
                }
                System.out.println("Total views are : " + sum);
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}