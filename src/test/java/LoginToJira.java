import Pages.CreateIssuePage;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.TaskPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.WebDriverFactory;

import static org.testng.Assert.assertTrue;

public class LoginToJira {

  WebDriver driver = null;
  LoginPage loginPage = null;
  HomePage homePage = null;
  TaskPage taskPage = null;
  CreateIssuePage createIssuePage = null;

  @BeforeMethod
  public void setUp() {
    WebDriverFactory.createInstance("Chrome");
    driver = WebDriverFactory.getDriver();
    loginPage = new LoginPage(driver);
    homePage = new HomePage(driver);
    taskPage = new TaskPage(driver);
    createIssuePage = new CreateIssuePage(driver);
  }

  @DataProvider(name = "SuccesfulLogins")
  public Object[][] succesfulData(){
    return new Object[][]{
        {"LoiEmmanuel", "LoiEmmanuel"},
        {"webinar5", "webinar5"},
    };
  }

  @DataProvider(name = "UnsuccesfulLogins")
  public Object[][] unsuccesfulData(){
      return new Object[][]{
          {"webinar5", "Password", "'Sorry, your username and password are incorrect - please try again.'"},
          {"web5", "webinar5", "'Sorry, your username and password are incorrect - please try again.'"},
          {"Emma", "webinar54", "'Sorry, your username and password are incorrect - please try again.'"},
      };
  }

  @Test(dataProvider = "SuccesfulLogins")
  public void successfulLoginTest(String name, String password) {
    loginPage.navigateToDashboard();
    loginPage.enterUserName(name);
    loginPage.enterPassword(password);
    loginPage.clickLogin();

    assertTrue(homePage.activityStreamIsPresent());
  }

  @Test(dataProvider = "UnsuccesfulLogins")
  public void unsuccessfulLoginTest(String name, String password, String expectedResult) {
    loginPage.navigateToDashboard();
    loginPage.enterUserName(name);
    loginPage.enterPassword(password);
    loginPage.clickLogin();

    assertTrue(loginPage.errorMessageIsPresent(expectedResult));
  }

  @Test
  public void viewJiraTicket() {
    loginPage.navigateToDashboard();
    loginPage.enterUserName("webinar5");
    loginPage.enterPassword("webinar5");
    loginPage.clickLogin();

    assertTrue(homePage.activityStreamIsPresent());

    homePage.clickIssueKey("'WEBINAR-11542'");

    assertTrue(taskPage.taskNameIsPresent());
    assertTrue(taskPage.isCurrentUrlContainsIssueName("WEBINAR-11542"));
  }

  @Test
  public void createIssue() {
    loginPage.navigateToDashboard();
    loginPage.enterUserName("LoiEmmanuel");
    loginPage.enterPassword("LoiEmmanuel");
    loginPage.clickLogin();
    homePage.activityStreamIsPresent();

    homePage.clickCreateLink();
    createIssuePage.createIssueDialogIsOpened();

    createIssuePage.projectFieldIsDisplayed();
    createIssuePage.clearProjectField();
    createIssuePage.fillProjectField("Webinar (WEBINAR)");
    createIssuePage.clickTabInProjectField();

    createIssuePage.issueTypeFieldIsDisplayed();
    createIssuePage.clearIssueTypeField();
    createIssuePage.fillissueTypeField("Task");
    createIssuePage.clickTabInIssueTypeField();

    createIssuePage.enterSummary("summary");

    createIssuePage.clearReporterField();
    createIssuePage.fillReporterField("LoiEmmanuel");
    createIssuePage.clickTabInReporterField();

    createIssuePage.clickCreateIssueSubmitButton();

    assertTrue(createIssuePage.isPopupContinsProjectType("WEBINAR"));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }
}
