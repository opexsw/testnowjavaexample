package com.java.cukes;

/**
 * 
 * @author tahir
 *
 */
public enum Browser {
	FIREFOX("firefox"), CHROME("chrome"), IE("ie"), DEVICE("device"), OPERA(
			"opera"), ANDROID("android");

	private String browser;

	private Browser(String browser) {
		this.browser = browser;
	}

	public String getBrowser() {
		return this.browser;
	}
}
