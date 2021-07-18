Feature: How much a customer can borrow

@FirstTest
Scenario: Borrowing estimate for a Single customer
	Given user is already on the home loan borrow calculator page
	When user fills the form
	Then user clicks the "Work out how much I can borrow" button
	Then user gets an estimate borrowing amount
	
@SecondTest
Scenario: Clearing the form
	Given user has already calculated the borrowing capacity once
	When user clicks the "start over" button
	Then form gets cleared to default values
	
@ThirdTest
Scenario: Validating the message for $1 Living Expenses
	Given the form is empty
	When user enters amount as 1 in Living Expenses and leaving all other fields as 0
	Then user clicks 'Work out how much I can borrow' button
	Then user should get the message as 
		"""
		Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. 
		For questions, call us on 1800 100 641.
		
		"""