package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public WebDriver driver;
	public static ThreadLocal<WebDriver> thread = new ThreadLocal<WebDriver>();

	
	/**
	 * Method to initialize a browser using ThreadLocal object
	 * @param browser
	 * @return
	 */
	public WebDriver initBrowser(String browser, String url) {
		System.out.println("Browser is: " + browser);

		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			thread.set(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			thread.set(new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("Safari")) {
			thread.set(new SafariDriver());
		} else {
			System.out.println("Please provide a valid browser");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(url);
		return getDriver();
	}

	
	
	/**
	 * Method to get web-driver
	 * @return
	 */
	public static synchronized WebDriver getDriver() {
		return thread.get();
	}

}
