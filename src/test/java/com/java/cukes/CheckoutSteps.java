/* 
* File Name: CheckoutSteps.java
* Copyright 2015, Opex Software
* Apache License, Version 2.0
* This file contains the step definitions for steps in checkout.feature cucumber file
*/

package com.java.cukes;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class CheckoutSteps {	
	public WebDriver driver;
	private String magentoURL;
	
	public CheckoutSteps()
	{
		magentoURL = System.getenv("TEST_URL");
		if(magentoURL == null){
			magentoURL = "https://104.131.191.140/";
		}
		driver = Hooks.driver;
	}
	
	@Given("^I am on magento customer page$")
	public void I_am_on_magento_customer_page() throws Throwable {		
		driver.get(magentoURL);
		if(System.getenv("BROWSER").equalsIgnoreCase("IE") && driver.findElements( By.id("overridelink") ).size()!= 0) {
			driver.get("javascript:document.getElementById('overridelink').click();");
		}
	}

	@When("^I do a global search using \"([^\"]*)\" keyword$")
	public void I_do_a_global_search_using_keyword(String arg1) throws Throwable {
		driver.findElement(By.id("search")).sendKeys("Samsung");
		driver.findElement(By.cssSelector("button[title*='Search']")).click();	      
	}

	@Then("^I should see products$")
	public void I_should_see_products() throws Throwable {
		new WebDriverWait(driver, 120)
			.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "SEARCH RESULTS FOR 'SAMSUNG'"));
//		assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "SEARCH RESULTS FOR 'SAMSUNG'");
	}

	@When("^I add to cart the product (?:based on|number) (RUN_INDEX|\\d+)$")
	public void I_add_to_cart_the_product_number(String type) throws Throwable {
		String run_index = System.getenv("RUN_INDEX");
		int product_index;
		Random random = new Random();
		if (type.equalsIgnoreCase("RUN_INDEX")) {
			if (run_index == null) {
				product_index = random.nextInt(3);									
			}
			else {
				product_index = Integer.parseInt(run_index)%3;				
			}
			if (product_index == 0) {
				product_index = 3;
			}
		} 
		else {
			product_index = Integer.parseInt(type);
		}		
		driver.findElement(By.xpath("//ul[contains(@class,'products-grid')]/li["+product_index+"]//button")).click();		
	}

	@Then("^I proceed to checkout$")
	public void I_proceed_to_checkout() throws Throwable {
		driver.findElement(By.cssSelector("ul.top button[title*='Proceed to Checkout']")).click();
	}

	@When("^I select checkout method as Guest$")
	public void I_select_checkout_method_as_Guest() throws Throwable {
		driver.findElement(By.id("login:guest")).click();
	    driver.findElement(By.id("onepage-guest-register-button")).click();
	}

	@When("^I fill all mandatory details in Billing Information as (guest|member)$")
	public void I_fill_all_mandatory_details_in_Billing_Information_as(String userType) throws Throwable {
		new Select(driver.findElement(By.id("billing:country_id"))).selectByVisibleText("India");
		if (userType.equals("guest")) {
			driver.findElement(By.id("billing:firstname")).sendKeys("Kaushal");
			driver.findElement(By.id("billing:lastname")).sendKeys("Rupani");
			driver.findElement(By.id("billing:email")).sendKeys("kaushal.rupani@opexsoftware.com");
		}		
		driver.findElement(By.id("billing:street1")).sendKeys("Turning Point 2");
		driver.findElement(By.id("billing:city")).sendKeys("Pune");
		driver.findElement(By.id("billing:postcode")).sendKeys("411014");
		driver.findElement(By.id("billing:telephone")).sendKeys("9876543210");
		driver.findElement(By.cssSelector("#billing-buttons-container button")).click();
	}

	@When("^I continue with shipping method$")
	public void I_continue_with_shipping_method() throws Throwable {
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#shipping-method-buttons-container button"))).click();
		
	}

	@When("^I select payment method as \"([^\"]*)\"$")
	public void I_select_payment_method_as(String payment_method) throws Throwable {
		if (payment_method.equals("credit_card")) {
			driver.findElement(By.id("p_method_ccsave")).click();
			new Select(driver.findElement(By.id("ccsave_cc_type"))).selectByVisibleText("Visa");
			driver.findElement(By.id("ccsave_cc_owner")).sendKeys("Opex Software");
			driver.findElement(By.id("ccsave_cc_number")).sendKeys("4111111111111111");
			new Select(driver.findElement(By.id("ccsave_expiration"))).selectByValue("11");
			new Select(driver.findElement(By.id("ccsave_expiration_yr"))).selectByValue("2025");			
		}
		else if (payment_method.equals("cash_on_delivery")) {
			driver.findElement(By.id("p_method_cashondelivery")).click();
		}
		else if (payment_method.equals("cheque")) {
			driver.findElement(By.id("p_method_checkmo")).click();
		}
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#payment-buttons-container button"))).click();		
	}

	@When("^I place the order$")
	public void I_place_the_order() throws Throwable {
		new WebDriverWait(driver, 30)
				  .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-checkout"))).click();		
	}

	@Then("^I should see order confirmation message$")
	public void I_should_see_order_confirmation_message() throws Throwable {
		new WebDriverWait(driver, 60).until(ExpectedConditions.urlContains("success"));
		assertEquals(driver.findElement(By.tagName("h1")).getText(), "YOUR ORDER HAS BEEN RECEIVED.");
		assertEquals(driver.findElement(By.tagName("h2")).getText(), "THANK YOU FOR YOUR PURCHASE!");
	}
	
}
