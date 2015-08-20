/* 
* File Name: CukesRunnerTest.java
* Copyright 2015, Opex Software LLP
*/

package cukes;

import cucumber.api.junit.*;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(
		format={"pretty", "html:target/reports", "json:target/reports/index.json"},
		features="src/test/features"
		)
public class CukesRunnerTest {}
