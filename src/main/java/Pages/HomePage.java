package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class HomePage {

  WebDriverWait wait;

  private WebDriver driver = null; // хранилище

  public HomePage(WebDriver driver) {
    this.driver = driver;
  }

  public boolean activityStreamIsPresent(){
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    return wait.until(presenceOfElementLocated(By.xpath("//*[contains(text(), 'Activity Stream')]"))).isDisplayed();
  }

}
