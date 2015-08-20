/* 
* File Name: LoginSteps.java
* Copyright 2015, Opex Software
* Apache License, Version 2.0
* This file contains the step definitions for steps in login.feature cucumber file
*/

package com.java.cukes;

import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

	public WebDriver driver;
	public String email = Utilities.generateRandomEmail();
	
	public LoginSteps() {
		driver = Hooks.driver;
	}

	@Given("^I follow (login|register) link in account section$")
	public void I_follow_register_link_in_account_section(String link)
			throws Throwable {
		driver.findElement(By.cssSelector("a.skip-account")).click();
		if (link.equals("login")) {
			driver.findElement(By.cssSelector("a[title*='Log In']")).click();
		} else if (link.equals("register")) {
			driver.findElement(By.cssSelector("a[title*='Register']")).click();
		}
	}

	@Given("^I register (?:a|an) (new|existing) user$")
	public void I_register_a_new_user(String userType) throws Throwable {
		if (userType.equals("new")) {			
			driver.findElement(By.id("email_address")).sendKeys(email);			
		}
		else if (userType.equals("existing")) {		
			driver.findElement(By.id("email_address")).sendKeys("admin@mailinator.com");				
		}
		driver.findElement(By.id("firstname")).sendKeys(Utilities.generateRandomString(10));
		driver.findElement(By.id("lastname")).sendKeys(Utilities.generateRandomString(10));
		driver.findElement(By.id("password")).sendKeys("ZAQ!zaq1");
		driver.findElement(By.id("confirmation")).sendKeys("ZAQ!zaq1");
	}

	@Given("^I click the (register|save|subscribe) button$")
	public void I_click_the_register_button(String buttonType) throws Throwable {
		if (buttonType.equals("register")) {
			driver.findElement(By.cssSelector("button[title*='Register']")).click();			
		}
		else if (buttonType.equals("save")) {
			driver.findElement(By.cssSelector("button[title*='Save']")).click();
		}
	}
	
	@When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void I_login_with_username_and_password(String username, String password) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.id("send2")).click();		
	}

	@Then("^I should be on \"([^\"]*)\" page$")
	public void I_should_be_on_page(String page) throws Throwable {
	    if (page.equals("My Dashboard")) {
	    	assertEquals(driver.findElement(By.cssSelector("div.dashboard h1")).getText(),page.toUpperCase());
	    }
	    else if (page.equals("Logout")) {
	    	new WebDriverWait(driver, 120)
				.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "LOGGED OUT"));
//	    	assertTrue(driver.findElement(By.cssSelector("div.page-title")).getText().contains("LOGGED OUT"));	    	
	    }
	}

	@When("^I logout$")
	public void I_logout() throws Throwable {
	    driver.findElement(By.cssSelector("a.skip-account")).click();
	    driver.findElement(By.cssSelector("a[title*='Log Out']")).click();
	}

	@Then("^I should see \"([^\"]*)\" message for (login|registration)$")
	public void I_should_see_message_for_login_registration(String msg, String action) throws Throwable {
		String expected_mandatory_field_message = "This is a required field.";
		if (action.equals("login")) {
			if (msg.equals("invalid_credentials")) {
				assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(), "Invalid login or password.");
			}
			else if (msg.equals("mandatory_fields")) {
				Thread.sleep(1);
				assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),expected_mandatory_field_message);
				assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),expected_mandatory_field_message);
			}
		}
		else if (action.equals("registration")) {
			if (msg.equals("existing_user")) {
				assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(), "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.");
			}
			else if (msg.equals("mandatory_fields")) {
				assertEquals(driver.findElement(By.id("advice-required-entry-firstname")).getText(),expected_mandatory_field_message);
				assertEquals(driver.findElement(By.id("advice-required-entry-lastname")).getText(),expected_mandatory_field_message);
				assertEquals(driver.findElement(By.id("advice-required-entry-email_address")).getText(),expected_mandatory_field_message);
				assertEquals(driver.findElement(By.id("advice-required-entry-password")).getText(),expected_mandatory_field_message);
				assertEquals(driver.findElement(By.id("advice-required-entry-confirmation")).getText(),expected_mandatory_field_message);				
			}
			else if (msg.equals("new_user")) {
				assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");
			}
		}
	}
}
