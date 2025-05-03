package Login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Login.*;

public class LoginMain {
	WebDriver driver;
	File file;
	FileInputStream fis;
	Properties prop;
	LoginUtility objLogin;
	XSSFWorkbook wb;
    XSSFSheet sheet;
    int totalRows;
    public static ExtentReports reports;
    public static ExtentTest test;
	
  @Test(dataProvider = "data-provider")
  public void login(Object username, Object password) throws Exception {
	  //ReportUtility.log("Login with username - " + username + " and password - " + password , driver, test, Status.INFO);
	  objLogin.loginToApp(username,password);
	  //Thread.sleep(1000);
  }
  
  @DataProvider(name = "data-provider")
  public Object[][] getLoginDetails() throws IOException {
	        String fPath = "C:\\Capstone project -Staragile\\OrangeHRM\\Excel\\LoginData.xlsx" ;
	        file = new File(fPath);
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheetAt(0);
		    totalRows = sheet.getPhysicalNumberOfRows(); // no of rows inside the file.
			System.out.println("Total rows in file are = " + totalRows);
			Object[][] data = new String[totalRows][2];
			int totalCells = sheet.getRow(0).getPhysicalNumberOfCells(); 
			for (int i = 0; i < totalRows; i++) {
				for (int j = 0; j < totalCells; j++) {
				data[i][j] =sheet.getRow(i).getCell(j).getStringCellValue();
				}
		}
		return data ;
 }
  @BeforeTest
  public void beforeTest() throws IOException, Exception {
       // Launch browser
       driver =new ChromeDriver();
	   driver.manage().window().maximize();
	   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	   
	   reports = Extentreporter.getReports();
	   test = reports.createTest("OrangeHRM Login");
	   
	   objLogin = new LoginUtility(driver, reports, test);
	   	   
	   // Launch application 
	   objLogin.launchApp();
	   //Thread.sleep(1000);
	   ReportUtility.log("App Launched", driver, test, Status.INFO);
  }

  @AfterTest
  public void afterTest() throws IOException {
	  wb.close();
	  driver.close();
	  reports.flush();
  }

}
