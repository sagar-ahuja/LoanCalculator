package hooks;

import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import driverFactory.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.ConfigReader;

public class Hooks {
	
	private TestBase base;
	private WebDriver driver;
	private ConfigReader reader;
	Properties prop;
	
	
	@Before(order = 0)
	public void getProperties() {
		reader = new ConfigReader();
		prop = reader.initProperties();
	}

	
	@Before(order = 1)
	public void setUp() {
		String browserName = prop.getProperty("browser");
		String url = prop.getProperty("url");
		base = new TestBase();
		driver = base.initBrowser(browserName, url);
	}
	
	@After(order = 1)
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
			String fileName = scenario.getName().replaceAll(" ", "_");
			byte[] pathName = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(pathName, "image/png", fileName);
		}
	}
	
	@After(order = 0)
	public void quitBrowser() {
		driver.quit();
	}
}
