import Pages.HomePage;
import Pages.LoginPage;
import Pages.TaskPage;
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

public class AddDeleteCommentInJira {

  WebDriver driver = null;
  LoginPage loginPage = null;
  TaskPage taskPage = null;

  @BeforeMethod
  public void setUp() {
    WebDriverFactory.createInstance("Chrome");
    driver = WebDriverFactory.getDriver();
    loginPage = new LoginPage(driver);
    taskPage = new TaskPage(driver);

    loginPage.navigateToTask12061();
    loginPage.enterUserName("LoiEmmanuel1");
    loginPage.enterPassword("LoiEmmanuel");
    loginPage.clickLoginFormSubmit();
    assertTrue(taskPage.taskNameIsPresent());
  }

  @Test
  public void addComment() {
    taskPage.clickCommentButton();
    taskPage.commentFieldIsDisplayed();
    taskPage.enterComment("Comment was created1");
    taskPage.clickSubmitCommentButton();
    assertTrue(taskPage.commentIsDisplayed("'Comment was created1'"));
  }

  @Test
  public void deleteComment() {
    taskPage.clickActionLinksDelete("'Comment was created1'");
    taskPage.deleteCommentDialogIsDisplayed();
    taskPage.clickCommentDeleteSubmitButton();
    assertTrue(taskPage.successDeleteMessageIsDisplayed());
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

}