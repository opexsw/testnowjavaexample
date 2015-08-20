/* 
* File Name: CukesRunnerTest.java
* Copyright 2015, Opex Software LLP
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
