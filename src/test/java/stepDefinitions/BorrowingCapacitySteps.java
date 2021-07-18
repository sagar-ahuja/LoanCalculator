package stepDefinitions;


import driverFactory.TestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.BorrowingCapacity;
import utilities.ConfigReader;



public class BorrowingCapacitySteps{
	private BorrowingCapacity bcap; 
	private ConfigReader reader = new ConfigReader();
	
	
	@Given("^user is already on the home loan borrow calculator page$")
	public void user_is_already_on_the_home_loan_borrow_calculator_page() {
		bcap = new BorrowingCapacity(TestBase.getDriver());
	}

	@When("^user fills the form$")
	public void user_fills_the_form() {
		bcap.inputValuesForSingleCustomer(reader.initProperties().getProperty("dependants"), reader.initProperties().getProperty("income"), reader.initProperties().getProperty("otherIncome"), reader.initProperties().getProperty("livingExpenses"), reader.initProperties().getProperty("otherLoanRepayments"), reader.initProperties().getProperty("creditCardLimit"));
	}

	@Then("^user clicks the \"Work out how much I can borrow\" button$")
	public void user_clicks_the_Work_out_how_much_I_can_borrow_button(){
		bcap.calculateBorrowingCapacity();
	}

	@Then("^user gets an estimate borrowing amount$")
	public void user_gets_an_estimate_borrowing_amount() throws InterruptedException {
		
		String actual = bcap.validateBorrowingEstimate();
		//Assert.assertEquals("$479,000", actual);
		if(actual.equals(reader.initProperties().getProperty("expectedBorrowingEstimate"))) {
			System.out.println("Borrowing estimate: "+actual+" matches the expected value of "+reader.initProperties().getProperty("expectedBorrowingEstimate"));
		}else
			System.out.println("Borrowing estimate: "+actual+" doesn't match the expected value of "+reader.initProperties().getProperty("expectedBorrowingEstimate"));
	}

	
	@Given("^user has already calculated the borrowing capacity once$")
	public void user_has_already_calculated_the_borrowing_capacity_once() {
		bcap = new BorrowingCapacity(TestBase.getDriver());
		bcap.inputValuesForSingleCustomer(reader.initProperties().getProperty("dependants"), reader.initProperties().getProperty("income"), reader.initProperties().getProperty("otherIncome"), reader.initProperties().getProperty("livingExpenses"), reader.initProperties().getProperty("otherLoanRepayments"), reader.initProperties().getProperty("creditCardLimit"));
		bcap.calculateBorrowingCapacity();
	}

	@When("^user clicks the \"start over\" button$")
	public void user_clicks_the_start_over_button() {
		bcap.clickStartOverBtn();
	}

	@Then("^form gets cleared to default values$")
	public void form_gets_cleared_to_default_values() throws Exception {
		bcap.checkDefaultValues();
	}

	@Given("^the form is empty$")
	public void the_form_is_empty() {
		bcap = new BorrowingCapacity(TestBase.getDriver());
		bcap.clearForm();
	}

	@When("^user enters amount as 1 in Living Expenses and leaving all other fields as 0$")
	public void user_enters_amount_as_1_in_Living_Expenses_and_leaving_all_other_fields_as_0() {
		bcap.enter$1InLivingExpense();
	}

	@Then("^user clicks 'Work out how much I can borrow' button$")
	public void user_clicks_Work_out_how_much_I_can_borrow_button() {
		bcap.calculateBorrowingCapacity();
	}

	@Then("^user should get the message as$")
	public boolean user_should_get_the_message_as(String arg1) {
		 return bcap.validateErrorMessage();
	    
	}



}
