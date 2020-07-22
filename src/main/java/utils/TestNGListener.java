package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestNGListener implements ITestListener {

  @Override
  //Запускается один раз перед каждым тестом
  public void onTestStart(ITestResult result) {
    String browserName = result.getTestContext().getCurrentXmlTest().getParameter("browserName");
    System.out.println("Browser name is " + browserName);
  }

  @Override
  public void onTestSuccess(ITestResult result) {

  }

  @Override
  public void onTestFailure(ITestResult result) {

    File screenshotsFolder = new File("D:\\Courses\\Java\\HomeWork_UI\\screens");

    if (!screenshotsFolder.exists()) {
      screenshotsFolder.mkdir();
    }
    File screenshot = captureScreenshot();
    Path pathToScreenShot = Paths.get(screenshot.getPath());
    String localTime = java.time.LocalTime.now() + ".png";
    String newLocalTime = localTime.replace(':','_');
    try {
      String screenshotName = screenshotsFolder + "\\" + "Screenshot_" + newLocalTime;
      System.out.println(screenshotName);
      Files.copy(pathToScreenShot, Paths.get(screenshotName), StandardCopyOption.COPY_ATTRIBUTES);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {

  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

  }

  @Override
  public void onStart(ITestContext context) {

  }

  @Override
  public void onFinish(ITestContext context) {

  }

  private File captureScreenshot() {
    return ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
  }
}
