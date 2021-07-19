package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BorrowingCapacity {
	
	private WebDriver driver;
	
	/* declare the object repositories */
	private By single_rb = By.id("application_type_single");
	private By joint_rb = By.id("application_type_joint");
	private By dependants_dropdown = By.xpath("//select[@title = 'Number of dependants']");
	private By borrowHome_rb = By.id("borrow_type_home");
	private By borrowInvestment_rb = By.id("borrow_type_investment");
	private By income_textbox = By.xpath("//label[contains(text(), 'Your income (before tax)')]/following-sibling::div/input");
	private By otherIncome_textbox = By.xpath("//label[contains(text(), 'Your other income')]/following-sibling::div/input");
	private By livingExpenses_textbox = By.xpath("//label[contains(text(), 'Living expenses')]/following-sibling::div/input");
	private By homeLoanRepayments_textbox = By.xpath("//label[contains(text(), 'Current home loan repayments')]/following-sibling::div/input");
	private By otherLoanRepayments_textbox = By.xpath("//label[contains(text(), 'Other loan repayments')]/following-sibling::div/input");
	private By otherCommitments_textbox = By.xpath("//label[contains(text(), 'Other commitments')]/following-sibling::div/input");
	private By creditCardLimits_textbox = By.xpath("//label[contains(text(), 'Total credit card limits')]/following-sibling::div/input");
	private By borrow_btn = By.xpath("//button[contains(text(), 'Work out how much I could borrow')]");
	private By startOver_btn = By.xpath("//span[@class = 'borrow__result__text']/parent::div/following-sibling::div/button");
	private By startOver_borrowError_btn = By.xpath("//span[@class = 'borrow__error__text']/parent::div/following-sibling::div/button");
	private By borrowResult = By.xpath("//span[@class = 'borrow__result__text__amount']");
	private By errorMsg = By.xpath("//span[@class = 'borrow__error__text']");
	
	private String errorMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions,\r\n" + 
			"call us on 1800 100 641.";
	
	
	
	public BorrowingCapacity(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	/**
	 * Method to enter details in the application form 
	 * @param txt
	 * @param income
	 * @param otherIncome
	 * @param expenses
	 * @param otherLoan
	 * @param ccLimits
	 */
	public void inputValuesForSingleCustomer(String txt, String income, String otherIncome, String expenses, String otherLoan, String ccLimits) {
		Select dependants = new Select(driver.findElement(dependants_dropdown));
		dependants.selectByVisibleText(txt);
		
		driver.findElement(single_rb).click();
		driver.findElement(borrowHome_rb).click();
				
		driver.findElement(income_textbox).sendKeys(income);
		driver.findElement(otherIncome_textbox).sendKeys(otherIncome);
		driver.findElement(livingExpenses_textbox).sendKeys(expenses);
		driver.findElement(otherLoanRepayments_textbox).sendKeys(otherLoan);
		driver.findElement(creditCardLimits_textbox).sendKeys(ccLimits);
			
	}
	
	
	
	/**
	 * Method to click the 'Work out how much I can borrow' button
	 */
	public void calculateBorrowingCapacity() {
		driver.findElement(borrow_btn).click();
	}
		
	
	
	/**
	 * Method to get the text of the borrowed capacity amount result
	 * @return
	 * @throws InterruptedException
	 */
	public String validateBorrowingEstimate() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'borrow__result__text__amount']")));
		//Thread.sleep(2000);
		String result = driver.findElement(borrowResult).getText();
		return result;		
	}
	
	
	
	/**
	 * Method to click the start over button
	 */
	public void clickStartOverBtn() {
		if(driver.findElement(borrowResult).isDisplayed()) {
			driver.findElement(startOver_btn).click();
		}
		if(driver.findElement(errorMsg).isDisplayed()) {
			driver.findElement(startOver_borrowError_btn).click();
		}
	}
	
	
	
	/**
	 * Method to clear the application form
	 */	
	public void clearForm() {
		Select dependants = new Select(driver.findElement(dependants_dropdown));
		dependants.selectByVisibleText("0");
		
		driver.findElement(single_rb).click();
		driver.findElement(borrowHome_rb).click();
	
		driver.findElement(income_textbox).clear();
		driver.findElement(otherIncome_textbox).clear();
		driver.findElement(livingExpenses_textbox).clear();
		driver.findElement(homeLoanRepayments_textbox).clear();
		driver.findElement(otherLoanRepayments_textbox).clear();
		driver.findElement(otherCommitments_textbox).clear();
		driver.findElement(creditCardLimits_textbox).clear();	
	}
	
	
	
	/**
	 * Method to check the default values in the application form
	 * @throws Exception
	 */
	public void checkDefaultValues() throws Exception {
		if(driver.findElement(single_rb).isSelected())
			System.out.println("Application Type value is defaulted to 'Single'");
		else
			throw new Exception();
		
		Select dep = new Select(driver.findElement(dependants_dropdown));
		if(dep.getFirstSelectedOption().isSelected())
			System.out.println("Number of dependants value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(borrowHome_rb).isSelected())
			System.out.println("Property you would like to buy value is defaulted to 'Home to live in'");
		else
			throw new Exception();
		
		if(driver.findElement(livingExpenses_textbox).getAttribute("value").contentEquals("0"))
			System.out.println("Living expenses value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(homeLoanRepayments_textbox).getAttribute("value").contentEquals("0"))
			System.out.println("Current home loan repayments value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(otherLoanRepayments_textbox).getAttribute("value").contentEquals("0"))
			System.out.println("Other loan repayments value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(otherCommitments_textbox).getAttribute("value").contentEquals("0"))
			System.out.println("Other commitments value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(creditCardLimits_textbox).getAttribute("value").contentEquals("0"))
			System.out.println("Total credit card limits value is defaulted to '0'");
		else
			throw new Exception();
		
		if(driver.findElement(borrow_btn).isDisplayed())
			System.out.println("'Work out how much I could borrow' button is visible");
		else
			throw new Exception();
	}
	
	
	
	/**
	 * Method to enter $1 in the living expenses
	 */
	public void enter$1InLivingExpense() {
		driver.findElement(livingExpenses_textbox).sendKeys("1");
	}
	
	
	
	/**
	 * Method that validates whether an error message is displayed
	 * @return
	 */
	public boolean validateErrorMessage() {
		if(driver.findElement(errorMsg).getText().equals(errorMessage)) {
			return true;
		}else
			return false;
	}

}
