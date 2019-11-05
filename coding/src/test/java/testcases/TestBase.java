package testcases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.sun.javafx.PlatformUtil;
import utilitylibraries.RedConfigProperties;

public class TestBase {

	public static WebDriver driver = null;
	DesiredCapabilities capabilities = null;
	SoftAssert softAssert = null;

/*	public static RedConfigProperties properties = new RedConfigProperties("src\\main\\resources\\config.properties");
	String platformType = properties.getConfigProperty("platform");
	String broweserName = properties.getConfigProperty("browser");
	String browserVersion = "";
	String browserLocation = properties.getConfigProperty("browserlocation");
	String url = properties.getConfigProperty("URL");*/

	@Parameters({"browser","url" })	
	@BeforeTest(alwaysRun = true)
	public void driverInitialize(@Optional("firefox") String broweserName,@Optional("https://www.amazon.com/")String url) throws Exception {
		switch(broweserName.toUpperCase()) {
		case "CHROME":
			System.out.println("Initializing Chrome  Driver");
			 if (PlatformUtil.isMac()) {
		            System.setProperty("webdriver.chrome.driver", "chromedriver");
		        }
		        if (PlatformUtil.isWindows()) {
		            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		        }
		        if (PlatformUtil.isLinux()) {
		            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
		        }
		        driver = new ChromeDriver();
			
			break;
		case "FIREFOX":
			System.out.println("Initializing Firefox  Driver");
			 if (PlatformUtil.isMac()) {
		            System.setProperty("webdriver.firefox.driver", "geckodriver");
		        }
		        if (PlatformUtil.isWindows()) {
		            System.setProperty("webdriver.firefox.driver", "geckodriver.exe");
		        }
		        if (PlatformUtil.isLinux()) {
		            System.setProperty("webdriver.firefox.driver", "geckodriver_linux");
		        }
		        driver = new FirefoxDriver();
			break;
		
		default:
			throw new Exception ("Incorrect Driver name !!! Supported <Chrome, Firefox>");
		}
		


		//Open URL in browser
		//driver.get(properties.getConfigProperty("url"));
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);


	}	


	@AfterMethod(alwaysRun = true)
	public void screenShotCapture(ITestResult result) {

		String screenShotName = null;

		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				if (result.getParameters() != null && result.getParameters().length > 0) {
					screenShotName = (String) result.getMethod().getXmlTest().getName() + "_"
							+ result.getMethod().getMethodName() + "_" + (String) result.getParameters()[0];
				} else {
					screenShotName = (String) result.getMethod().getXmlTest().getName() + "_"
							+ result.getMethod().getMethodName();
				}
				takeScreenshot(screenShotName);
			}

		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

      driver.navigate().refresh();
	}


	@AfterTest(alwaysRun = true)
	public void tearDown() throws IOException, InterruptedException{

		driver.quit();
	}


	public  boolean takeScreenshot(final String name) throws Exception {

		String destDir = "";
		//destDir = "screenshots/failed";
		destDir = "target/surefire-reports/screenshots";
		new File(destDir).mkdirs();
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println("Taken Screenshot");
		return screenshot.renameTo(new File(destDir, String.format("%s.png", name)));


	}
}
