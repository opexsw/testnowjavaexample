/* 
* File Name: CukesRunnerTest.java
* Copyright 2015, Opex Software
* Apache License, Version 2.0
* This file acts as an entry point for Cucumber test suite
*/

package com.java.cukes;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.*;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin={"pretty", "html:target/reports", "json:target/reports/index.json"},
		features="src/test/features"
		)
public class CukesRunnerTest {}
