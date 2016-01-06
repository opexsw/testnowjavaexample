package com.java.cukes.reporting;

import java.util.ArrayList;

public class UPAResult {

	private String scenarioName;
	
	private ArrayList<String> upaReportLinks;

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public ArrayList<String> getUpaReportLinks() {
		return upaReportLinks;
	}

	public void setUpaReportLinks(ArrayList<String> upaReportLinks) {
		this.upaReportLinks = upaReportLinks;
	}
	
}
