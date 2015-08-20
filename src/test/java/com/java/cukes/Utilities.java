/* 
* File Name: Utilities.java
* Copyright 2015, Opex Software
* Apache License, Version 2.0
* This file contains the utility methods frequently used by the step definitions
*/


package com.java.cukes;

import java.util.Random;

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
				+ midCharInsert	+ 
				Utilities.generateRandomString(random.nextInt(5) + random.nextInt(5) + 1)
				+ domainName;
	}

}
