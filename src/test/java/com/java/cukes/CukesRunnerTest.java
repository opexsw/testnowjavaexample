/* 
 * File Name: CukesRunnerTest.java
 * Copyright 2015, Opex Software
 * Apache License, Version 2.0
 * This file acts as an entry point for Cucumber test suite
 */

package com.java.cukes;

import java.util.ArrayList;
import java.util.List;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;

/*@RunWith(Cucumber.class)
 @CucumberOptions(plugin = { "pretty", "html:reports", "json:reports/index.json" }, features = "classpath:features")*/
public class CukesRunnerTest {

	public static void main(String args[]) {
		try {
			Utilities.checkConfiguration();

			ClassLoader classLoader = CukesRunnerTest.class.getClassLoader();
			ResourceLoader resourceLoader = new MultiLoader(classLoader);
			ClassFinder classFinder = new ResourceLoaderClassFinder(
					resourceLoader, classLoader);
			List<String> pluginList = new ArrayList<String>();
			pluginList.add("--plugin");
			pluginList.add("pretty");
			pluginList.add("--plugin");
			pluginList.add("html:reports");
			pluginList.add("--plugin");
			pluginList.add("json:reports/index.json");
			RuntimeOptions ro = new RuntimeOptions(pluginList);
			// ro.getFilters().add("@sanity");
			ro.getFeaturePaths().add("classpath:features");
			ro.getGlue().add("com.java.cukes");
			cucumber.runtime.Runtime runtime = new cucumber.runtime.Runtime(
					resourceLoader, classFinder, classLoader, ro);
			runtime.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
