package Login;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class LoginUtility {
	WebDriver driver;
	File file;
	FileInputStream fis;
	Properties prop;
	String expUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index", actUrl;
	ExtentReports reports;
    ExtentTest test;
	
	LoginUtility(WebDriver d, ExtentReports rept, ExtentTest t) throws IOException
	{
		this.driver = d;
		file = new File("src/Resources/LoginOHRM.properties");
	    fis =new FileInputStream(file);
	    prop= new Properties();
	    prop.load(fis);
	    reports = rept;
	    test = t;
	}
	
	public void launchApp() {driver.get(prop.getProperty("URL"));
		}
	public void loginToApp(Object un,Object psw) throws Exception {
	driver.findElement(By.cssSelector(prop.getProperty("userNameCss"))).sendKeys(String.valueOf(un));
	driver.findElement(By.cssSelector(prop.getProperty("passwordCss"))).sendKeys(String.valueOf(psw));
	ReportUtility.log("Login details filled", driver, test, Status.INFO);
	driver.findElement(By.cssSelector(prop.getProperty("loginBtnCss"))).click();
	Thread.sleep(1500);
	
	//Verify page title for successful login
	String actUrl = driver.getCurrentUrl();
	try {
		Assert.assertEquals(actUrl, expUrl,"Invalid username / password");
		ReportUtility.log("Login Succsessful with username - " + String.valueOf(un) + " and password - " + String.valueOf(psw),
				driver, test, Status.PASS);
		// logout as login is successful
		driver.findElement(By.xpath(prop.getProperty("logOutArrowXP"))).click();
		driver.findElement(By.partialLinkText(prop.getProperty("logOutPLT"))).click();
		ReportUtility.log("Logout Succsessful", driver, test, Status.INFO);
		
	}
	catch(AssertionError error)
	{
		ReportUtility.log("Invalid Login with username - " + String.valueOf(un) + " and password - " + String.valueOf(psw), driver, test, Status.FAIL);
	    launchApp();// Relauch app as login is unsuccessful
	}
	}
}
