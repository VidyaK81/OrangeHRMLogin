package Login;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;

import Login.Extentreporter;

public class ReportUtility {
	public static void takeScreenShot(WebDriver driver, ExtentTest test) {
		// fileName of the screenshot
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String filePath = Extentreporter.screenshotFolderPath;
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(filePath + "/" + screenshotFile));
			// put screenshot file in reports
			test.log(Status.INFO, "Screenshot-> " + test.addScreenCaptureFromPath(filePath + "/" + screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void log(String msg, WebDriver driver, ExtentTest test, Status status) {
		try {
			System.out.println(msg);
			takeScreenShot(driver, test);
			test.log(status, msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			test.log(Status.FAIL, "Please verify");
		}

	}

}
