/* 
 * File Name: Hooks.java
 * Copyright 2015, Opex Software
 * Apache License, Version 2.0
 * This file contains the setup and teardown methods, browser initializations and screenshot functionality
 */

package com.java.cukes;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.java.cukes.reporting.UPAReporter;
import com.java.cukes.reporting.UPAResult;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	public static WebDriver driver;
	private String browser;
	private String webDriverPath;
	private String test_url;
	private UPAReporter reporter;

	// Contains declaration of all browsers (FF, Chrome, IE, Opera, Android)
	// This method is a hook which runs before every test
	@Before
	public void beforeEach() throws IOException, URISyntaxException {
		try {

			browser = System.getenv(Constants.BROWSER);
			webDriverPath = System.getenv(Constants.WEB_DRIVER_PATH);
			test_url = System.getenv(Constants.TEST_URL);
			System.out.println("Browser selected is " + browser);
			if (Browser.CHROME.getBrowser().equalsIgnoreCase(browser)) {
				System.setProperty("webdriver.chrome.driver", StringUtils
						.isNotEmpty(webDriverPath) ? webDriverPath
						: "/usr/local/bin/chromedriver");
				/*
				 * ChromeOptions chromeOptions = new ChromeOptions();
				 * chromeOptions.addArguments("--no-sandbox");
				 */
				driver = new ChromeDriver();
				driver.manage().timeouts()
						.pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (Browser.DEVICE.getBrowser().equalsIgnoreCase(browser)) {
				String deviceName = System.getenv("VERSION");
				deviceName = deviceName.replace("_", " ");
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", deviceName);

				Map<String, Object> chromeOptions = new HashMap<String, Object>();
				chromeOptions.put("mobileEmulation", mobileEmulation);
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY,
						chromeOptions);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts()
						.pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);

			} else if (Browser.IE.getBrowser().equalsIgnoreCase(browser)) {
				System.out.println(">>>>>>> DRIVER <<<<<<<");
				DesiredCapabilities cap = new DesiredCapabilities();
					//cap.setCapability("ignoreProtectedModeSettings", true);
				//cap.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://34.199.118.11/");
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//cap.setCapability("ignoreZoomSetting", true);
				//cap.setCapability("unexpectedAlertBehaviour", "accept");
				//cap.setCapability("requireWindowFocus", true);
				//cap.setCapability("enablePersistentHover", true);
				cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				//cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
				//cap.setCapability("disable-popup-blocking", true);
				//cap.setCapability("ignoreProtectedModeSettings", true);
				//cap.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
				//cap.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
				cap.setJavascriptEnabled(true);
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					//cap.setCapability("nativeEvents",true);
					//cap.setCapability("browserFocus",true);
					//cap.setCapability("ignoreZoomSetting", true);
					//cap.setCapability("requireWindowFocus","true");
				File file = new File("C:\\IEDriverServer\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				//driver = new RemoteWebDriver(new URL("http://localhost:5555"),
				//cap);
				//driver.get("about:InPrivate");
				driver = new InternetExplorerDriver(cap);
				driver.manage().timeouts()
						.pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (Browser.OPERA.getBrowser().equalsIgnoreCase(browser)) {
				DesiredCapabilities cap = DesiredCapabilities.operaBlink();
				cap.setBrowserName("opera");
				OperaOptions options = new OperaOptions();
				options.setBinary("/usr/bin/opera");
				options.addArguments("--ignore-certificate-errors");
				cap.setCapability(OperaOptions.CAPABILITY, options);
				OperaDriverService service = new OperaDriverService.Builder()
						.usingDriverExecutable(
								new File(
										StringUtils.isNotEmpty(webDriverPath) ? webDriverPath
												: "/usr/local/bin/operadriver"))
						.usingAnyFreePort().build();
				service.start();
				driver = new RemoteWebDriver(service.getUrl(), cap);
				driver.manage().timeouts()
						.pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else if (Browser.ANDROID.getBrowser().equalsIgnoreCase(browser)) {
				driver = new RemoteWebDriver(DesiredCapabilities.android());
			} else {
				String binaryPath = System
						.getenv(Constants.BROWSER_BINARY_PATH);
				String driverPath = System.getenv(Constants.WEB_DRIVER_PATH);
				if (StringUtils.isNotEmpty(driverPath))
					System.setProperty("webdriver.gecko.driver", driverPath);
				else
					System.out
							.println("Test is using default driver path for execution !!!");
				File pathToBinary = null;
				FirefoxBinary firefoxBinary = null;
				if (StringUtils.isNotEmpty(binaryPath))
					try {
						pathToBinary = new File(binaryPath);
					} catch (Exception e) {
						pathToBinary = null;
						e.printStackTrace();
					}
				else
					System.out
							.println("Test is using default firefox binary path for execution !!!");
				firefoxBinary = pathToBinary != null ? new FirefoxBinary(
						pathToBinary) : new FirefoxBinary();
				FirefoxProfile profile = new FirefoxProfile();
				String isUPA = System.getenv(Constants.IS_UPA);
				if ("true".equalsIgnoreCase(isUPA)) {
					File netexport = new File(this.getClass().getClassLoader()
							.getResource("har/netExport-0.8.xpi").toURI());
					File firebug = new File(this.getClass().getClassLoader()
							.getResource("har/firebug-2.0.13.xpi").toURI());
					profile.addExtension(netexport);
					profile.addExtension(firebug);
					profile.setPreference("app.update.enabled", false);
					String domain = "extensions.firebug.";
					profile.setPreference(domain + "currentVersion", "2.0.13");
					profile.setPreference(domain + "allPagesActivation", "on");
					profile.setPreference(domain + "defaultPanelName", "net");
					profile.setPreference(domain + "net.enableSites", true);
					profile.setPreference(domain
							+ "netexport.alwaysEnableAutoExport", true);
					profile.setPreference(domain + "netexport.showPreview",
							false);
					File harFolder = new File(System.getProperty("user.dir")
							+ "/target/reports/har/");
					profile.setPreference(domain + "netexport.defaultLogDir",
							harFolder.getAbsolutePath());
				}
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setBinary(firefoxBinary);
				firefoxOptions.setProfile(profile);
				firefoxOptions.setLegacy(false);
				driver = new FirefoxDriver(firefoxOptions);
				driver.manage().timeouts()
						.pageLoadTimeout(120, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().timeouts()
						.setScriptTimeout(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				reporter = new UPAReporter();
			}

			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (driver == null) {
				System.out.println("Driver not found !!!");
				System.exit(1);
			}
		}
	}

	// This method kills the browser after the test is over
	// It also takes a screenshot and embeds it in the report if the test fails
	// This method is a hook which runs after every test
	@After
	public void afterEach(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				scenario.write("Current Page URL is " + driver.getCurrentUrl());
				byte[] screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");

			}
			String isUPA = System.getenv(Constants.IS_UPA);
			if ("true".equalsIgnoreCase(isUPA)) {
				String scenarioName = scenario.getName().replace(" ", "_");
				File harFolder = new File(System.getProperty("user.dir")
						+ "/target/reports/har/");
				File[] listOfFiles = harFolder.listFiles();
				int cnt = 0;
				ArrayList<String> upaLinks = new ArrayList<String>();
				for (int i = 0; i < listOfFiles.length; i++) {
					File file = listOfFiles[i];
					if (file.getName().contains("+")) {
						String htmlName = scenarioName + "_" + cnt++;
						File newFile = new File(file.getParent(), htmlName
								+ ".har");
						file.renameTo(newFile);
						String simpleHarExec = "simplehar "
								+ newFile.getAbsolutePath() + " "
								+ newFile.getParent() + File.separator
								+ htmlName + ".html";

						Runtime.getRuntime().exec(simpleHarExec);
						upaLinks.add(htmlName + ".html");
					}
				}
				UPAResult result = new UPAResult();
				result.setScenarioName(scenarioName);
				result.setUpaReportLinks(upaLinks);
				reporter.addDetails(result);
				reporter.writeResults();
			}
		} catch (Exception e) {

		} finally {
			if (driver != null)
				driver.quit();
		}

	}

}
