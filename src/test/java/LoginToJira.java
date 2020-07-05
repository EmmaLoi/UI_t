import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.WebDriverFactory;
import java.time.Duration;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class LoginToJira {

  WebDriver driver = null;
  WebDriverWait wait;

  @BeforeMethod
  public void setUp() {
    WebDriverFactory.createInstance("Chrome");
    driver = WebDriverFactory.getDriver();
  }

  @Test
  public void successfulLoginTest() {
    driver.get("https://jira.hillel.it/secure/Dashboard.jspa");
    driver.findElement(By.id("login-form-username")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login-form-password")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login")).click();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.xpath("//*[contains(text(), 'Activity Stream')]"))).isDisplayed();
    assertTrue(elementIsPresent);

  }

  @Test
  public void viewJiraTicket() {
    driver.get("https://jira.hillel.it/secure/Dashboard.jspa");
    driver.findElement(By.id("login-form-username")).sendKeys("webinar5");
    driver.findElement(By.id("login-form-password")).sendKeys("webinar5");
    driver.findElement(By.xpath("//*[@id='login']")).click();

    // Explicit Wait for element to appear
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.xpath("//*[contains(text(), 'Activity Stream')]"))).isDisplayed();
    assertTrue(elementIsPresent);

    wait = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds());
    driver.findElement(By.xpath("//*[@id='gadget-10002']//child::*[contains(@class, 'issuekey')]//child::*[contains(@data-issue-key, 'WEBINAR-11541')]")).click();

    String link = driver.getCurrentUrl();
    assertTrue(driver.findElement(By.id("type-val")).isDisplayed());
    assertTrue(link.contains("WEBINAR-11541"));
  }

  @Test
  public void createIssue() {
    driver.get("https://jira.hillel.it/secure/Dashboard.jspa");
    driver.findElement(By.id("login-form-username")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login-form-password")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("login")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.id("create_link"))).isEnabled();
    assertTrue(elementIsPresent);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("create_link")).click();
    wait.until(presenceOfElementLocated(By.id("project-field"))).isDisplayed();
    driver.findElement(By.id("project-field")).clear();
    driver.findElement(By.id("project-field")).sendKeys("Webinar (WEBINAR)");
    driver.findElement(By.id("project-field")).sendKeys(Keys.TAB);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("issuetype-field")).clear();
    driver.findElement(By.id("issuetype-field")).sendKeys("Task");
    driver.findElement(By.id("issuetype-field")).sendKeys(Keys.TAB);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("summary")).sendKeys("new summary");
    driver.findElement(By.id("reporter-field")).clear();
    driver.findElement(By.id("reporter-field")).sendKeys("LoiEmmanuel");
    driver.findElement(By.id("reporter-field")).sendKeys(Keys.TAB);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("create-issue-submit")).click();

    String popupPresent = wait.until(presenceOfElementLocated(By.className("aui-message-success"))).getText();
    assertTrue(popupPresent.contains("WEBINAR"));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }
}
