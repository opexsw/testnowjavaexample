# :sparkles: TESTNOW JAVA EXAMPLE :sparkles:
Contains cucumber features for very famous magento web application.

## Overview :eyes:

Behavior Driven scenarios written in Cucumber for Magento web application.

Selenium-WebDriver is used as a backend tool for driving the browsers. This automation suite is compatible with following browsers
* Google Chrome
* Mozilla Firefox
* Internet Explorer
* Opera

This suite runs scenarios related to following:

1. Login and Signup related features
2. Buying of a product with different checkout methods

##Purpose :eyes:

Major purpose of this example automation is to understand how one can make maximum usage of [TestNow](https://opexsoftware/testnow) cross browser testing with minimum effort by following some best practices mentioned here. 
Few Best Practices are as follows:

1. How to initialize different browsers
2. How to write appropriate setup and teardown's
3. How to take failed scenario screenshots
4. How to create json and html reports which helps TestNow to create consolidated reports for you.
5. How to organize code in case of Java Cucumber setup
6. and few more which you can relate to ... :)

## Application Under Test (AUT) :eyes:

__Magento__ is an ecommerce platform built on open source technology which provides online merchants with a flexible shopping cart system, as well as control over the look, content and functionality of their online store. Magento offers powerful marketing, search engine optimization, and catalog-management tools.


##How To Use This For Your Application :eyes:

Before you fork or borrow ideas from this example project, it might be useful to know some do's and dont's which will enable you to implement this awesome framework for your project hassle free.

__A walk through the folder structure__

1. src/test/java/features folder is the place where all the test case descriptions are kept in Given-When-Then(GWT) format. Cucumber features
2. src/test/java/com/java/cukes contains all the step definitions where 1-1 mapping is present between cucumber scenarios and its definitions
3. Relation between Scenario and Step_Definitions folder is maintained in CukesRunnerTest.java file along with the reporting formats as CucumberOptions
4. CukesRunnerTest.java file is the entry point to test execution
5. Hooks.java contains the setup and teardown methods
6. Reports folder is created inside of target folder
7. Utilities.java contain common utility methods frequently used by the steps

__Do's__

1. Change the feature files in src/test/features folder as per the test cases in your project
2. Make appropriate step_definitions for the new scenarios added by you in the src/test/java/com/java/cukes folder
3. Add more utility methods as required by your projects in Utilities.java file

__Dont's__

1. Do not delete pom.xml file as it acts as the main config file for java-maven based project
2. Do not delete or be careful while modifying CukeRunnerTest.java, doing so might stop execution and report generation
3. Do not delete anything from Hooks.java as it contains the setup and teardown which has browser initializations processes and screenshots taking methodology. You can add more intemediate methods like BeforeAll, AfterAll etc.


## Requirements :eyes:

1. __Code__
  * TESTNOW JAVA EXAMPLE code 
2. __Programming Language__
  * Java
3. __Dependencies__
  * All mentioned in pom.xml file, no additional dependency installation required
4. __Browsers__
  * Google Chrome
  * Mozilla Firefox
  * Internet Explorer
  * Opera
5. __WebDrivers__
  * chromedriver -- put in any folder inlcuded in PATH variable (777 to avoid permission issues)
  * operadriver -- mandatorily put in /usr/local/bin/operadriver (777 to avoid permission issues)
  * iedriver -- put in any folder inlcuded in PATH variable (777 to avoid permission issues)


## Setup :eyes:

__git clone https://github.com/opexsw/testnowrubyexample.git__

####NOTE: To avoid all the requirement and setup related extra work, use [TESTNOW](https://opexsoftware.com/testnow) :star2:

## Execution :eyes:

__Commmands__ 

1. __mvn clean__ -- This will clean the old compiled code
2. __mvn test__ -- This will recompile the code and execute the tests
3. __mvn clean test__ -- This will do 1 and 2 both

## Reporting :eyes:
Magento automation reports are created in following 2 types of format

1. __HTML__ : index.html
2. __JSON__ : index.json

Reports are created inside the target/reports directory with above mentioned filenames

##Author :eyes:

* Name: __Kaushal Rupani__ :sunglasses:
* Organization: __Opex Software__ :star:
* Email: __kaushal.rupani@opexsoftware.com__

##Licence :eyes:
For Licence information, see [here](https://github.com/opexsw/testnowjavaexample/blob/master/LICENSE.txt)
