package reportConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import commons.BaseTest;
import commons.GlobalConstants;

public class ReportNGListener extends BaseTest implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		System.out.println("---------- " + context.getName() + " STARTED test ----------");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("---------- " + context.getName() + " FINISHED test ----------");
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("---------- " + result.getName() + " STARTED test ----------");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("---------- " + result.getName() + " SUCCESS test ----------");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("---------- " + result.getName() + " FAILED test ----------");
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		Object testClass = result.getInstance();
		WebDriver webDriver = ((BaseTest) testClass).getDriverInstance();

		// Image
		// String screenshotPath = captureScreenshot(webDriver, result.getName());

		// Base 64
		String screenshotPath = captureScreenshotBase64(webDriver, result.getName());
		Reporter.getCurrentTestResult();
		// Image file
		// Reporter.log("<br><a target=\"_blank\" href=\"file:///" + screenshotPath + "\">" + "<img src=\"file:///" + screenshotPath + "\" " + "height='100'
		// width='150'/> " + "</a></br>");

		// Base 64
		Reporter.log("<br><a href=\"data:image/png;base64," + screenshotPath + "\">" + "<img src=\"data:image/png;base64," + screenshotPath + "\" " + "height='100' width='150'/> " + "</a></br>");
		Reporter.setCurrentTestResult(null);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("---------- " + result.getName() + " SKIPPED test ----------");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("---------- " + result.getName() + " FAILED WITH SUCCESS PERCENTAGE test ----------");
	}

	// Image file
	public String captureScreenshot(WebDriver driver, String screenshotName) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenPath = GlobalConstants.REPORTNG_SCREENSHOT + screenshotName + "_" + formater.format(calendar.getTime()) + ".png";
			FileUtils.copyFile(source, new File(screenPath));
			return screenPath;
		} catch (IOException e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
			return e.getMessage();
		}
	}

	public String captureScreenshotBase64(WebDriver driver, String screenshotName) {
		String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		return screenshotBase64;
	}

}
