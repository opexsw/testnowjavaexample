/* 
* File Name: Hooks.java
* Copyright 2015, Opex Software
* Apache License, Version 2.0
* This file contains the setup and teardown methods, browser initializations and screenshot functionality
*/

package com.java.cukes;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	public static WebDriver driver;
	private String browser;

	// Contains declaration of all browsers (FF, Chrome, IE, Opera, Android)
	// This method is a hook which runs before every test
	@Before
	public void beforeEach() throws IOException {
		browser = System.getenv("BROWSER");
		if (browser == null) {
			browser = "firefox";
		}
		System.out.println("Browser selected is " + browser);
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("ie")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setJavascriptEnabled(true);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new InternetExplorerDriver(cap);
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("opera")) {
			DesiredCapabilities cap = DesiredCapabilities.operaBlink();
			cap.setBrowserName("opera");
			OperaOptions options = new OperaOptions();
			options.setBinary("/usr/bin/opera");
			options.addArguments("--ignore-certificate-errors");
			cap.setCapability(OperaOptions.CAPABILITY, options);			
			OperaDriverService service = new OperaDriverService.Builder()
					.usingDriverExecutable(new File("/usr/local/bin/operadriver"))					
					.usingAnyFreePort().build();
			service.start();			
			driver = new RemoteWebDriver(service.getUrl(),cap);
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("android")) {
			driver = new RemoteWebDriver(DesiredCapabilities.android());
		} else {
			driver = new FirefoxDriver();
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
	}

	// This method kills the browser after the test is over
	// It also takes a screenshot and embeds it in the report if the test fails
	// This method is a hook which runs after every test
	@After
	public void afterEach(Scenario scenario) {
		if (scenario.isFailed()) {
			try {
				scenario.write("Current Page URL is " + driver.getCurrentUrl());
				byte[] screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots
						.getMessage());
			}

		}

		driver.quit();
	}

}
