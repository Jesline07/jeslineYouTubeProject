package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import dev.failsafe.internal.util.Durations;

import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    WebDriver driver;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
        
    }

    public void navigateToYouTube() {
        driver.get("https://www.youtube.com/");
    }

    public void clickOn(WebElement element) {
        element.click();
        // yt-formatted-string Movies
    }

    public void scrollFunction(String num) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String param1 = "window.scrollBy(0," + num + ")";
        js.executeScript(param1);
    }

    public void movieCollection(String movieType) {
        boolean status = false;
        SoftAssert softAssert = new SoftAssert();
        WebElement movieList = driver.findElement(By.xpath("(//span[text()='" + movieType
                + "']/ancestor::h2[@class='style-scope ytd-shelf-renderer']/following::div[@id='contents'][1]/yt-horizontal-list-renderer/div[@id='scroll-outer-container']//div[@id='items']/ytd-grid-movie-renderer[last()])"));
        List<WebElement> movieTextList = movieList.findElements(By.xpath(".//span"));
        for (WebElement element : movieTextList) {
            String title = element.getText();
            if (title.contains("Comedy") || title.contains("Animation") || title.contains("Drama")) {
                status = true;
            }
        }
        softAssert.assertTrue(status, "Movie does not contain any of the categories");
        List<WebElement> contentType = movieList.findElements(By.xpath(".//p"));
        for (WebElement ele : contentType) {
            String content = ele.getText();
            if (content.contains("U")) {
                status = false;
            }
        }
        softAssert.assertFalse(status, "Movie is A rated");
    }

    public void musicCollection(String musicType) throws InterruptedException {
        boolean status = false;
        // Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        SoftAssert softAssert = new SoftAssert();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//span[contains(text(),'Biggest Hits') and @id='title']/ancestor::div[@id='dismissible']/div[@id='contents']/yt-horizontal-list-renderer//div[@id='items']/ytd-compact-station-renderer[last()]")));
        WebElement musicList = driver.findElement(By.xpath(
                "//span[contains(text(),'" + musicType
                        + "') and @id='title']/ancestor::div[@id='dismissible']/div[@id='contents']/yt-horizontal-list-renderer//div[@id='items']/ytd-compact-station-renderer[last()]"));
        WebElement titleOfPlaylist = musicList.findElement(By.xpath(".//h3"));
        String titlePlaylistText = titleOfPlaylist.getText();
        System.out.println("The playlist title name is : " + titlePlaylistText);
        WebElement totalTracks = musicList.findElement(By.xpath(".//p[@id='video-count-text']"));
        String totalTracksText = totalTracks.getText();
        System.out.println("Total tracks text is : " + totalTracksText);
        totalTracksText = totalTracksText.trim();
        String[] arr = totalTracksText.split(" ");
        // System.out.println("total tracks are : " + arr[0]);
        int num = Integer.parseInt(arr[0]);
        if (num <= 50) {
            status = true;
        }
        softAssert.assertTrue(status, "tracks are greater than 50");
    }

    public void topThreeNews(String newsType) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//span[text()='Latest news posts']/ancestor::div[@id='dismissible']/div[@id='contents']/ytd-rich-item-renderer")));
        List<WebElement> newsList = driver.findElements(By.xpath("//span[text()='" + newsType
                + "']/ancestor::div[@id='dismissible']/div[@id='contents']/ytd-rich-item-renderer"));
        List<WebElement> likeList = driver.findElements(By.xpath(
                "//span[text()='" + newsType
                        + "']/ancestor::div[@id='dismissible']/div[@id='contents']/ytd-rich-item-renderer/div[@id='content']/ytd-post-renderer/div[@id='dismissible']/div[@id='toolbar']/ytd-comment-action-buttons-renderer/div[@id='toolbar']/span[@id='vote-count-middle']"));
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            String likeListText = likeList.get(i).getText();
            likeListText = likeListText.trim();
            System.out.println("Likes are : " + likeListText);
            long likesNum = 0;
            if (likeListText.contains("K")) {
                String numericString = likeListText.replace("K", "");
                if (numericString.contains(".")) {
                    likesNum = (long) (Double.parseDouble(numericString) * 1000);
                } else {
                    likesNum = Integer.parseInt(numericString) * 1000;
                }
            } else {
                likesNum = Integer.parseInt(likeListText);
            }
            sum = sum + likesNum;
        }
        System.out.println("Likes total count is : " + sum);

        List<WebElement> titleList = driver.findElements(By.xpath(
                "//span[text()='" + newsType
                        + "']/following::div[@id='contents']/descendant::div[@id='body']/div[@id='post-text']/yt-formatted-string/span[@class='style-scope yt-formatted-string'][1]"));
        int i = 0;
        for (WebElement title : titleList) {
            if (i == 2) {
                break;
            }
            String titleText = title.getText();
            System.out.println("title is : " + titleText);
            i++;
        }
        WebElement title2 = driver.findElement(By.xpath(
                "//span[text()='" + newsType
                        + "']/following::div[@id='contents']/descendant::div[@id='body']/div[@id='post-text']/yt-formatted-string/span[@class='style-scope yt-formatted-string'][2]"));
        String title2Text = title2.getText();
        System.out.println("The title is : " + title2Text);

        List<WebElement> linksList = driver.findElements(By.xpath("//span[text()='" + newsType
                + "']/following::div[@id='contents']/descendant::div[@id='body']/div[@id='post-text']/yt-formatted-string/a"));
        for (int j = 0; j < 2; j++) {
            String linksListText = linksList.get(j).getText();
            System.out.println("Links are : " + linksListText);
        }
    }

}