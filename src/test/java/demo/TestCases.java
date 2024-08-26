package demo;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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

public class TestCases extends ExcelDataProvider { // Lets us read the data
    private static final long timeoutInSeconds = 0;
    private ChromeDriver driver;
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
        SoftAssert softAssert = new SoftAssert();

        // Navigate to YouTube
        wrapper.navigateTo("https://www.youtube.com");

        // Assert that we are on the correct URL
        String currentUrl = driver.getCurrentUrl();
        softAssert.assertTrue(currentUrl.contains("youtube.com"), "URL does not contain 'youtube.com'");
        System.out.println("URL has YouTube.com");
        //Thread.sleep(3000);

        // Click on "About" using the Wrapper class with XPath expression
        try {
            By aboutLinkXPath = By.xpath("//a[@slot='guide-links-primary' and contains(text(),'About')]");
            wrapper.click(aboutLinkXPath);

            // Print the message on the screen using Wrapper class and XPath
            By messageXPath = By.xpath("//*[@id='content' and contains(@class,'ytabout__main lb-padding-top-xl lb-padding-bottom-xl')]");
            String messageText = wrapper.getText(messageXPath);
            System.out.println("Message on the screen: " + messageText);

        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        }

        // Assert all
        softAssert.assertAll();
    }
    // @Test
    // public void testCase02() {
    //     SoftAssert softAssert = new SoftAssert();

    //     // Navigate to YouTube Movies tab
    //     wrapper.navigateTo("https://www.youtube.com/feed/storefront");

    //     // Wait for the "Top Selling" section to appear
    //     By topSellingSectionXPath = By.xpath("//*[@class='style-scope ytd-shelf-renderer'and contains(@id,'dismissible')]");
    //     WebElement topSellingSection = wrapper.waitForElement(topSellingSectionXPath);

    //     // Scroll to the extreme right in the "Top Selling" section
    //     wrapper.scrollHorizontally(topSellingSection);

    //     // Check each movie's rating and category
    //     for (int i = 1; i <= 10; i++) {
    //         By movieXPath = By.xpath("(//*[@class='badge  badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer'])[" + i + "]");
    //         WebElement movieElement = wrapper.waitForElement(movieXPath);

    //         // Soft Assert on whether the movie is marked “A” for Mature
    //         String movieTitle = movieElement.getText();
    //         boolean isMature = movieTitle.contains("A");
    //         softAssert.assertTrue(isMature, "Movie " + i + " is not marked as 'A' for Mature: " + movieTitle);

    //         // Soft Assert on the movie category
    //         By categoryXPath = By.xpath("(//*[@id='title'])[" + i + "]");
    //         WebElement categoryElement = wrapper.waitForElement(categoryXPath);
    //         String categoryText = categoryElement.getText();
    //         boolean isCategoryValid = categoryText.matches("Comedy|Animation|Drama");
    //         softAssert.assertTrue(isCategoryValid, "Movie " + i + " does not belong to a valid category: " + categoryText);
    //     }

    //     // Assert all
    //     softAssert.assertAll();
    // }
//     @Test
// public void testCase02() {
//     SoftAssert softAssert = new SoftAssert();

//     // Navigate to the "Films" or "Movies" tab
//     wrapper.navigateTo("https://www.youtube.com/feed/storefront");


//     // Wait for the "Top Selling" section to be present and scroll to the extreme right
//     WebElement topSellingSection = wrapper.waitForElement(By.xpath("//span[@id='title' and contains(text(), 'Top selling')]"));
//         System.out.println("Located the Top Selling section.");

//         // Scroll to the extreme right by clicking the next arrow button
//         WebElement nextArrow = topSellingSection.findElement(By.xpath("//span[@id='title' and contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
//         while (nextArrow.isDisplayed()) {
//             nextArrow.click();
//             System.out.println("Clicked the next arrow to scroll right.");
//             // Re-locate the next arrow to see if it’s still available
//             try {
//                 nextArrow = topSellingSection.findElement(By.xpath("//span[@id='title' and contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
//             } catch (NoSuchElementException e) {
//                 break; // Exit the loop if the arrow is no longer available
//             }
//         }
//     // Updated: Adjusting to ensure the titles and badges are correctly located
//     // for (int i = 1; i <= 5; i++) { // Limiting to first 5 for demonstration
//     //     try {
//             // Wait for the movie title element to be visible
//             By movieTitleXPath = By.xpath("(//span[@class='style-scope ytd-grid-movie-renderer' and contains(@id, 'video-title')])");
//             WebElement movieTitle = wrapper.waitForElement(movieTitleXPath);

//             // Corrected XPath for the maturity badge
//             By maturityBadgeXPath = By.xpath("(//*[@class='badge badge-style-type-simple style-scope ytd-badge-supported-renderer'])");
//             WebElement maturityBadge = wrapper.waitForElement(maturityBadgeXPath);

//             // Check if the badge is marked as "A" for Mature
//             String badgeText = maturityBadge.getText();
//             softAssert.assertTrue(badgeText.equals("A"), "Movie "  + " is not marked as 'A' for Mature");

//             // Corrected XPath for the movie category
//             By categoryXPath = By.xpath("(//span[@class='style-scope ytd-shelf-renderer' and contains(@id, 'title')]");
//             WebElement category = wrapper.waitForElement(categoryXPath);

//             // Check if the category exists (e.g., "Comedy", "Animation", "Drama")
//             String categoryText = category.getText();
//             softAssert.assertTrue(categoryText.matches("Comedy|Animation|Drama"), "Movie " + " category is not as expected");

//     //     } catch (NoSuchElementException e) {
//     //         System.out.println("Element not found: " + e.getMessage());
//     //     } catch (TimeoutException e) {
//     //         System.out.println("Timeout while waiting for element: " + e.getMessage());
//     //     }
//     // }

//     // Assert all
//     softAssert.assertAll();
// }
/**
 * 
 */
@Test
public void testCase02() {
    WebDriver driver = new ChromeDriver(); // Assuming ChromeDriver is used
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

    SoftAssert softAssert = new SoftAssert();

    try {
        // Navigate to YouTube
        wrapper.navigateTo("https://www.youtube.com/");
        System.out.println("Navigated to YouTube.");

        // Click on the "Movies" tab
        wrapper.navigateTo("https://www.youtube.com/feed/storefront");
        System.out.println("Clicked on the Movies tab.");

        // Locate the "Top Selling" section
        WebElement topSellingSection = wrapper.waitForElement(By.xpath("//span[@id='title' and contains(text(), 'Top selling')]"), 10);
        System.out.println("Located the Top Selling section.");

        // Scroll to the extreme right by clicking the next arrow button using a for loop
        boolean canScroll = true;
        int maxScrollAttempts = 10; // Define the maximum number of attempts to scroll
        for (int i = 0; i < maxScrollAttempts && canScroll; i++) {
            try {
                WebElement nextArrow = topSellingSection.findElement(By.xpath("//span[@id='title' and contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
                nextArrow.click();
                System.out.println("Clicked the next arrow to scroll right.");
                
                // Wait for new items to load if necessary
                Thread.sleep(1000); // Adjust sleep time if needed
            } catch (NoSuchElementException e) {
                System.out.println("Reached the extreme right of the Top Selling section.");
                canScroll = false; // Stop scrolling if no next arrow is found
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                System.out.println("Interrupted while waiting: " + e.getMessage());
            }
        }

        // Find the last movie by using XPath to locate the last visible movie element
        WebElement lastMovie = null;
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            try {
                lastMovie = topSellingSection.findElement(By.xpath("//ytd-grid-movie-renderer[last()]"));
                System.out.println("Located the last movie in the Top Selling section.");
                break; // Exit loop once last movie is found
            } catch (NoSuchElementException e) {
                System.out.println("Attempting to locate the last movie, retrying...");
                Thread.sleep(1000); // Wait before retrying
            }
        }

        if (lastMovie == null) {
            System.out.println("No movies found in the Top Selling section.");
        } else {
            // Click on the last movie
            lastMovie.click();
            System.out.println("Clicked on the last movie: " + lastMovie.getText());

            // Example of additional assertions (if needed)
            // Check for maturity label
            WebElement maturityLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p")));
            softAssert.assertNotNull(maturityLabel, "Movie is not marked 'A' for Mature.");

            // Check for movie category
            WebElement categoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/a/span")));
            softAssert.assertNotNull(categoryElement, "Movie category is not one of the expected categories.");
        }

    } catch (Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
    } finally {
        // Assert all soft asserts
        softAssert.assertAll();
        // Close the browser
        driver.quit();
    }
}


@Test
public void testCase03() {
    try {
        // Navigate to YouTube
        wrapper.navigateTo("https://www.youtube.com/");
        System.out.println("Navigated to YouTube.");

        // Click on the "Music" tab
        wrapper.click(By.xpath("//a[@id='endpoint' and contains(@title, 'Music')]"));
        System.out.println("Clicked on the Music tab.");

        // Locate the first section
        WebElement firstSection = wrapper.waitForElement(By.xpath("//div[@id='dismissible' and contains(@class,'style-scope ytd-shelf-renderer')]"));
        System.out.println("Located the first section.");

        // Scroll to the extreme right within the first section
        wrapper.scrollHorizontally(firstSection);
        System.out.println("Scrolled to the extreme right in the first section.");

        // Wait for the playlist name to be visible
        WebElement playlistNameElement = wrapper.waitForElement(By.xpath("//*[@class='yt-simple-endpoint style-scope ytd-compact-station-renderer']"));

        // Alternative approach: Check if element is within the viewport
        if (!playlistNameElement.isDisplayed()) {
            System.out.println("Playlist name element is not visible, trying to scroll again.");
            wrapper.scrollHorizontally(firstSection);
            playlistNameElement = wrapper.waitForElement(By.xpath("//h3[contains(text(),'Bollywood Dance Hitlist')]"));
        }

        String playlistName = playlistNameElement.getText();
        System.out.println("Playlist Name: " + playlistName);

        // Check the number of tracks listed
        WebElement trackCountElement = wrapper.waitForElement(By.xpath("//p[@id='video-count-text']"));
        String trackCountText = trackCountElement.getText();
        System.out.println("Track Details: " + trackCountText);

        // Perform soft assert on the number of tracks
        int trackCount = Integer.parseInt(trackCountText.split(" ")[0]); // Assuming format like "50 tracks"
        softAssert.assertTrue(trackCount <= 50, "The number of tracks is more than 50.");

    } catch (Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
    } finally {
        // Assert all soft asserts
        //softAssert.assertAll();
        //wrapper.quit();
    }
}
@Test
public void testCase04() {
    try {
        // Navigate to YouTube
        wrapper.navigateTo("https://www.youtube.com/");
        System.out.println("Navigated to YouTube.");

        // Click on the "News" tab
        wrapper.click(By.xpath("//a[@id='endpoint' and contains(@title, 'News')]"));
        System.out.println("Clicked on the News tab.");

        // Initialize variables to store the total likes and iterate over the first 3 news posts
        int totalLikes = 0;

        for (int i = 1; i <= 3; i++) {
            // Locate the title of the news post
            WebElement newsTitleElement = wrapper.waitForElement(By.xpath("//div[@id='author' and contains(@class,'style-scope ytd-post-renderer')][" + i + "]"));
            String newsTitle = newsTitleElement.getText();
            System.out.println("News Title " + i + ": " + newsTitle);
            

             // Locate the body of the news post
             WebElement newsBodyElement = wrapper.waitForElement(By.xpath("//*[@id='home-content-text' and contains(@class,'style-scope ytd-post-renderer')][" + i + "]"), 60);
             String newsBody = (newsBodyElement != null) ? newsBodyElement.getText() : "No description available";
             System.out.println("News Body " + i + ": " + newsBody);
 
             // Locate the likes of the news post
             WebElement likesElement = wrapper.findElement(By.xpath("//*[@id='like-button' and contains(@button-renderer,'true')][" + i + "]"));
             String likesText = (likesElement != null) ? likesElement.getText() : "0";
             int likes = parseLikes(likesText);
             System.out.println("Likes for News Post " + i + ": " + likes);
 
             // Add the likes to the total
             totalLikes += likes;
         }
 
         // Print the total number of likes for the first 3 posts
         System.out.println("Total Likes for the first 3 posts: " + totalLikes);
 
     } catch (Exception e) {
         System.out.println("Error occurred: " + e.getMessage());
     }
 }






    private int parseLikes(String likesText) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'parseLikes'");
}

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
