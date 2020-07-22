package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class HomePage {

  WebDriverWait wait;

  private WebDriver driver = null; // хранилище

  public HomePage(WebDriver driver) {
    this.driver = driver;
  }

  public boolean activityStreamIsPresent() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    return wait.until(presenceOfElementLocated(By.xpath("//*[contains(text(), 'Activity Stream')]"))).isDisplayed();
  }

  public void clickIssueKey(String issueKey) { //'WEBINAR-11542'
    wait = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds());
    driver.findElement(By.xpath("//*[@id='gadget-10002']//child::*[contains(@class, 'issuekey')]//child::*[contains(@data-issue-key," + issueKey + ")]")).click();
  }

  public void clickCreateLink() {
    driver.findElement(By.id("create_link")).click();
    for (int i = 0; i < 3; i++) {
      try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@title='Create Issue']"))).isDisplayed();
        break;
      } catch (TimeoutException e) {
        driver.findElement(By.id("create_link")).click();
      }
    }
  }
}
