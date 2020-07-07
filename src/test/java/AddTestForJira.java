import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.WebDriverFactory;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class AddTestForJira {
  WebDriver driver = null;
  WebDriverWait wait;

  @BeforeMethod
  public void setUp() {
    WebDriverFactory.createInstance("Chrome");
    driver = WebDriverFactory.getDriver();

    driver.get("https://jira.hillel.it/browse/WEBINAR-12061");
    driver.findElement(By.id("login-form-username")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login-form-password")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login-form-submit")).click();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.xpath("//*[@id='key-val']"))).isDisplayed();
    assertTrue(elementIsPresent);
  }

  @Test
  public void addComment() {
    driver.findElement(By.id("footer-comment-button")).click();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    driver.findElement(By.id("comment")).sendKeys("Comment was created");
    driver.findElement(By.id("issue-comment-add-submit")).click();
    boolean commentAppearance = wait.until(presenceOfElementLocated(By.xpath("//div[@class='action-body flooded']/p[contains(text(), 'Comment was created')]"))).isDisplayed();
    assertTrue(commentAppearance);
  }

  @Test
  public void deleteComment() {
    driver.findElement(By.xpath("//div[@class='action-body flooded']/p[contains(text(), 'Comment was created')]/../../div/*[@class='action-links']/a[starts-with(@id, 'delete')]")).click();
    wait.until(presenceOfElementLocated(By.id("delete-comment-dialog"))).isDisplayed();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    driver.findElement(By.xpath("//*[@id='comment-delete']//child::*[@id='comment-delete-submit']")).click();
    boolean successMessage = wait.until(presenceOfElementLocated(By.className("aui-message-success"))).isEnabled();
    assertTrue(successMessage);
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

}