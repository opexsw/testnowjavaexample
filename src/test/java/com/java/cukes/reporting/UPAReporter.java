package com.java.cukes.reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UPAReporter {

	private  static List<UPAResult> upaDetails;
	private static String placeHolder = "<!-- INSERT_RESULTS -->";
	//private static final String templatePath = "/home/vagrant/testNow/TestNow/testnowjavaexample/src/test/java/com/java/cukes/reporting/report_template.html";
	private static final String templatePath = System.getProperty("user.dir") + "/src/test/java/com/java/cukes/reporting/report_template.html";
	
	public UPAReporter() {
		upaDetails = new ArrayList<UPAResult>();
	}

	public static void addDetails(UPAResult scenarioResult) {
		upaDetails.add(scenarioResult);
	}
	
	public static void writeResults() {
		try {
			String reportPath = System.getProperty("user.dir")+"/target/reports/har/upaReport.html";
			//String reportPath = "/home/vagrant/testNow/TestNow/testnowjavaexample/src/test/reports/har/upaReport.html";
			File file = new File(reportPath);
			String fileToRead = reportPath;
			//if (Files.exists(reportPath, LinkOption.NOFOLLOW_LINKS))
			if (!file.exists()){
				fileToRead = templatePath;
				file.createNewFile();
			}
			String reportIn = new String(Files.readAllBytes(Paths.get(fileToRead)));
			int i = 0;
			for (UPAResult upaResult : upaDetails) {
				//String textToReplace = "<tr><td>" + i++ + "</td><td>";
				String textToReplace = "<tr><td width=\"40%\">";
				textToReplace += upaResult.getScenarioName() + "</td><td width=\"60%\">";
				for (String upaFile : upaResult.getUpaReportLinks()) {
					textToReplace += "<a href=\"" + upaFile + "\"> UPA </a>"; 
				}
				textToReplace += "</td></tr>";
				//System.out.println("TEXT TO REPLACE" + textToReplace);
				reportIn = reportIn.replaceFirst(placeHolder, textToReplace + placeHolder);
				
				Files.write(Paths.get(reportPath), reportIn.getBytes(), StandardOpenOption.CREATE );
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
	}
}
