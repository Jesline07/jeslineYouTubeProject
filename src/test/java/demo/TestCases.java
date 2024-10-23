package demo;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider {
    private static final long timeoutInSeconds = 50; // Timeout for WebDriverWait
    private WebDriver driver;
    private Wrappers wrapper;
    private SoftAssert softAssert;

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize the Wrapper instance with the driver
        wrapper = new Wrappers(driver);
    }

    @Test
    public void testCase01() {
        softAssert = new SoftAssert();
    
        try {
            // Navigate to YouTube
            wrapper.navigateTo("https://www.youtube.com");
    
            // Wait until the URL contains 'youtube.com' and assert it
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.urlContains("youtube.com"));
            String currentUrl = driver.getCurrentUrl();
            softAssert.assertTrue(currentUrl.contains("youtube.com"), "URL does not contain 'youtube.com'");
            System.out.println("URL contains 'youtube.com'");
    
            // Click on "About" using the Wrapper class with XPath expression
            By aboutLinkXPath = By.xpath("//a[@slot='guide-links-primary' and contains(text(),'About')]");
            wrapper.click(aboutLinkXPath);
    
            // Wait for and print the message on the screen using Wrapper class and XPath
            By messageXPath = By.xpath("//*[@id='content' and contains(@class,'ytabout__main lb-padding-top-xl lb-padding-bottom-xl')]");
            String messageText = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(messageXPath))
                    .getText();
            System.out.println("Message on the screen: " + messageText);
    
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        }
    
        // Assert all
        softAssert.assertAll();
    }
    


    @Test
    public void testCase02() {
        softAssert = new SoftAssert();

        try {
            // Navigate to YouTube Movies tab
            wrapper.navigateTo("https://www.youtube.com/feed/storefront");

            // Wait for the "Top Selling" section to appear
            By topSellingSectionXPath = By.xpath("//span[@id='title' and contains(text(), 'Top selling')]");
            WebElement topSellingSection = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(topSellingSectionXPath));
            System.out.println("Located the Top Selling section.");

            // Scroll to the extreme right by clicking the next arrow button using a for loop
            boolean canScroll = true;
            int maxScrollAttempts = 10;
            for (int i = 0; i < maxScrollAttempts && canScroll; i++) {
                try {
                    WebElement nextArrow = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                            .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Next']")));
                    nextArrow.click();
                    System.out.println("Clicked the next arrow to scroll right.");
                } catch (NoSuchElementException e) {
                    System.out.println("Reached the extreme right of the Top Selling section.");
                    canScroll = false;
                }
            }

            // Find the last movie by using XPath to locate the last visible movie element
            WebElement lastMovie = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ytd-grid-movie-renderer[last()]")));
            System.out.println("Located the last movie in the Top Selling section.");

            // Click on the last movie
            lastMovie.click();
            System.out.println("Clicked on the last movie: " + lastMovie.getText());
            Thread.sleep(3000);

            // Check for maturity label
            WebElement maturityLabel = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'A')]")));
            softAssert.assertNotNull(maturityLabel, "Movie is not marked 'A' for Mature.");

            // Check for movie category
            WebElement categoryElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='title']")));
            softAssert.assertNotNull(categoryElement, "Movie category is not one of the expected categories.");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }

    @Test
    public void testCase03() {
        softAssert = new SoftAssert();

        try {
            // Navigate to YouTube
            wrapper.navigateTo("https://www.youtube.com/");

            // Click on the "Music" tab
            By musicTabXPath = By.xpath("//a[@id='endpoint' and contains(@title, 'Music')]");
            wrapper.click(musicTabXPath);
            System.out.println("Clicked on the Music tab.");
            Thread.sleep(3000);

            // Locate the first section
            By firstSectionXPath = By.xpath("//div[@id='dismissible' and contains(@class,'style-scope ytd-shelf-renderer')]");
            WebElement firstSection = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(firstSectionXPath));
            System.out.println("Located the first section.");

            // Scroll to the extreme right within the first section
            wrapper.scrollHorizontally(firstSection);
            System.out.println("Scrolled to the extreme right in the first section.");

            // Wait for the playlist name to be visible
            By playlistNameXPath = By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-station-renderer']");
            WebElement playlistNameElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(playlistNameXPath));

            if (!playlistNameElement.isDisplayed()) {
                System.out.println("Playlist name element is not visible, trying to scroll again.");
                wrapper.scrollHorizontally(firstSection);
                playlistNameElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                        .until(ExpectedConditions.visibilityOfElementLocated(playlistNameXPath));
            }

            String playlistName = playlistNameElement.getText();
            System.out.println("Playlist Name: " + playlistName);

            // Check the number of tracks listed
            By trackCountXPath = By.xpath("//p[@id='video-count-text']");
            WebElement trackCountElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(trackCountXPath));
            String trackCountText = trackCountElement.getText();
            System.out.println("Track Details: " + trackCountText);

            // Perform soft assert on the number of tracks
            int trackCount = Integer.parseInt(trackCountText.split(" ")[0]);
            softAssert.assertTrue(trackCount <= 50, "The number of tracks is more than 50.");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }

    @Test
    public void testCase04() {
        softAssert = new SoftAssert();

        try {
            // Navigate to YouTube
            wrapper.navigateTo("https://www.youtube.com/");

            // Click on the "News" tab
            By newsTabXPath = By.xpath("//a[@id='endpoint' and contains(@title, 'News')]");
            wrapper.click(newsTabXPath);
            System.out.println("Clicked on the News tab.");

            // Initialize variables to store the total likes and iterate over the first 3 news posts
            int totalLikes = 0;

            for (int i = 1; i <= 3; i++) {
                // Locate the title of the news post
                By newsTitleXPath = By.xpath("//div[@id='author' and contains(@class,'style-scope ytd-post-renderer')][" + i + "]");
                WebElement newsTitleElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                        .until(ExpectedConditions.visibilityOfElementLocated(newsTitleXPath));
                String newsTitle = newsTitleElement.getText();
                System.out.println("News Title: " + newsTitle);

                // Locate the number of likes (if available)
                By likesXPath = By.xpath("//yt-formatted-string[@class='style-scope ytd-toggle-button-renderer style-text']");
                WebElement likesElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                        .until(ExpectedConditions.visibilityOfElementLocated(likesXPath));
                String likesText = likesElement.getText().replaceAll("[^0-9]", "");
                int likes = likesText.isEmpty() ? 0 : Integer.parseInt(likesText);
                totalLikes += likes;
                System.out.println("Number of Likes: " + likes);
            }

            // Output the total number of likes for the three posts
            System.out.println("Total Likes for the first 3 news posts: " + totalLikes);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }
    public class YouTubeTestCases extends ExcelDataProvider {
        private static final long timeoutInSeconds = 10;
        @Test(dataProvider = "getData")
        public void testCase05(String keyword) {
            SoftAssert softAssert = new SoftAssert();
        
            try {
                // Navigate to YouTube
                wrapper.navigateTo("https://www.youtube.com");
        
                // Enter the keyword in the search box and submit
                By searchBoxXPath = By.name("search_query");
                wrapper.click(searchBoxXPath);
                driver.findElement(searchBoxXPath).sendKeys(keyword);
                driver.findElement(searchBoxXPath).submit();
        
                // Wait for the search results to load
                By videoElementsXPath = By.xpath("//ytd-video-renderer//span[@class='view-count style-scope ytd-video-meta-block']");
                new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                        .until(ExpectedConditions.visibilityOfElementLocated(videoElementsXPath));
        
                // Initialize the total view count
                long totalViews = 0;
        
                while (totalViews < 100_000_000) { // 100 million views
                    // Locate the first visible video element with the view count
                    WebElement videoElement = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                            .until(ExpectedConditions.visibilityOfElementLocated(videoElementsXPath));
        
                    // Extract the view count from the video element
                    String viewsText = videoElement.getText().split(" ")[0]
                            .replace(",", "").replace("K", "000").replace("M", "000000");
                    long views = Long.parseLong(viewsText);
        
                    // Accumulate the views
                    totalViews += views;
        
                    // Scroll down to load more videos
                    wrapper.scrollDown();
                }
        
                System.out.println("Total views for keyword '" + keyword + "' reached 10 crore.");
        
            } catch (Exception e) {
                System.out.println("Error occurred while processing keyword '" + keyword + "': " + e.getMessage());
            } finally {
                softAssert.assertAll();
            }
        }
        


    @AfterTest
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
}

    // public Object[][] fetchExcelData(String string) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'fetchExcelData'");
    // }

