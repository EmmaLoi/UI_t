package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CreateIssuePage {

  WebDriverWait wait;

  private By projectField = By.id("project-field");
  private By issueTypeField = By.id("issuetype-field");
  private By summaryField = By.id("summary");
  private By reporterField = By.id("reporter-field");

  private WebDriver driver = null;

  public CreateIssuePage(WebDriver driver) {
    this.driver = driver;
  }

  public void createIssueDialogIsOpened() {
    driver.findElement(By.className("form-body")).isDisplayed();
  }

  public void projectFieldIsDisplayed() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(projectField)).isDisplayed();
  }

  public void clearProjectField() {
    driver.findElement(projectField).clear();
  }

  public void fillProjectField(String typeProject) {
    driver.findElement(projectField).sendKeys(typeProject);
  }

  public void clickTabInProjectField() {
    driver.findElement(projectField).sendKeys(Keys.TAB);
  }

  public void issueTypeFieldIsDisplayed() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(issueTypeField)).isDisplayed();
  }

  public void clearIssueTypeField() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    for (int i = 0; i < 2; i++) {
      try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(issueTypeField)).clear();
        break;
      } catch (InvalidElementStateException e) {
        wait.until(ExpectedConditions.elementToBeClickable(issueTypeField));
        driver.findElement(issueTypeField).clear();
      }
    }
  }

  public void fillissueTypeField(String typeProject) {
    driver.findElement(issueTypeField).sendKeys(typeProject);
  }

  public void clickTabInIssueTypeField() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
    wait.until(presenceOfElementLocated(issueTypeField)).isSelected();
    driver.findElement(issueTypeField).sendKeys(Keys.TAB);
  }

  public void enterSummary(String summary) {

    for (int i = 0; i < 2; i++) {
      try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField)).sendKeys(summary);
        break;
      } catch (ElementNotInteractableException e) {
        wait.until(ExpectedConditions.elementToBeClickable(summaryField));
        driver.findElement(summaryField).isSelected();
      }
    }
  }

  public void clearReporterField() {
    driver.findElement(reporterField).clear();
  }

  public void fillReporterField(String typeProject) {
    driver.findElement(reporterField).sendKeys(typeProject);
  }

  public void clickTabInReporterField() {
    driver.findElement(reporterField).sendKeys(Keys.TAB);
  }

  public void clickCreateIssueSubmitButton() {
    driver.findElement(By.id("create-issue-submit")).click();
  }

  public boolean isPopupContinsProjectType(String typeProject) { //"WEBINAR"
    String popupPresent = wait.until(presenceOfElementLocated(By.className("aui-message-success"))).getText();
    return popupPresent.contains(typeProject);
  }
}
