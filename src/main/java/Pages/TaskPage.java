package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class TaskPage {

  WebDriverWait wait;

  private WebDriver driver = null; // хранилище
  private By taskName = By.xpath("//*[@id='key-val']");
  private By footerCommentButton = By.id("footer-comment-button");
  private By commentInput = By.id("comment");
  private By submitCommentButton = By.id("issue-comment-add-submit");
  private By deleteCommentDialog = By.id("delete-comment-dialog");
  private By commentDeleteSubmitButton = By.xpath("//*[@id='comment-delete']//child::*[@id='comment-delete-submit']");
  private By successDeleteMessage = By.className("aui-message-success");

  public TaskPage(WebDriver driver) {
    this.driver = driver;
  }

  public boolean taskNameIsPresent() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    return wait.until(presenceOfElementLocated(taskName)).isDisplayed();
  }

  public void clickCommentButton(){
    driver.findElement(footerCommentButton).click();
  }

  public void enterComment(String comment){
    driver.findElement(commentInput).sendKeys(comment);
  }

  public void clickSubmitCommentButton(){
    driver.findElement(submitCommentButton).click();
  }

  public boolean commentIsDisplayed(String comment){
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    return wait.until(presenceOfElementLocated(By.xpath("//div[@class='action-body flooded']/p[contains(text()," + comment + ")]"))).isDisplayed();
  }

  public void commentFieldIsDisplayed(){
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(commentInput)).isDisplayed();
  }

  public void clickActionLinksDelete(String comment){
    driver.findElement(By.xpath("//div[@class='action-body flooded']/p[contains(text()," + comment + ")]/../../div/*[@class='action-links']/a[starts-with(@id, 'delete')]")).click();
  }

  public void deleteCommentDialogIsDisplayed(){
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(deleteCommentDialog)).isDisplayed();
  }

  public void clickCommentDeleteSubmitButton(){
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(commentDeleteSubmitButton)).click();
  }

  public boolean successDeleteMessageIsDisplayed(){
    return wait.until(presenceOfElementLocated(successDeleteMessage)).isEnabled();
  }
}

