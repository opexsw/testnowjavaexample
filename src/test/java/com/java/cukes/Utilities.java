/* 
 * File Name: Utilities.java
 * Copyright 2015, Opex Software
 * Apache License, Version 2.0
 * This file contains the utility methods frequently used by the step definitions
 */

package com.java.cukes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class Utilities {

	public static String generateRandomString(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		System.out.println(output);
		return sb.toString();
	}

	public static String generateRandomEmail() {
		String[] domain = { "@gmail.com", "@ymail.com", "@live.com",
				"@hotmail.com", "@yahoo.com", "@msn.com", "@google.com",
				"@rocketmail.com" };
		String[] midChars = { "_", "", ".", "-" };
		Random random = new Random();
		String domainName = domain[random.nextInt(domain.length)];
		String midCharInsert = midChars[random.nextInt(midChars.length)];
		return generateRandomString(random.nextInt(5) + random.nextInt(5) + 1)
				+ midCharInsert
				+ Utilities.generateRandomString(random.nextInt(5)
						+ random.nextInt(5) + 1) + domainName;
	}

	public static void checkConfiguration() {
		String browser = System.getenv(Constants.BROWSER);
		if (StringUtils.isEmpty(browser)) {
			throw new RuntimeException(
					"Browser is not provided as part of system properties or environment variables.\nUsage : \nProvide \"BROWSER\" property. \nSupported values are firefox, chrome, ie, device, opera and android.");
		}
		boolean validBrowser = false;
		for (Browser br : Browser.values()) {
			validBrowser = br.getBrowser().equalsIgnoreCase(browser);
			if (validBrowser)
				break;
		}
		if (!validBrowser) {
			throw new RuntimeException(
					browser
							+ " is not valid or supported browser. \nSupported browsers are : firefox, chrome, ie, device, opera, android");
		}

		String testUrl = System.getenv(Constants.TEST_URL);
		if (StringUtils.isEmpty(testUrl)) {
			throw new RuntimeException(
					"Test url is not provided as part of system properties or environment variables.\nUsage : \nProvide \"TEST_URL\" property.");
		}
	}

	public static File getFileFromInputStream(String fileName, InputStream inputStream) {
		OutputStream outputStream = null;
		File file = null;
		try {
			file = new File(fileName);
			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			System.out.println("Done!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return file;
	}
}
