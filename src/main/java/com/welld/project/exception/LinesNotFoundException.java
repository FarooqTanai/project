package com.welld.project.exception;

public class LinesNotFoundException extends RuntimeException {
	
	private final String numberOfPoints;
	
	
	public LinesNotFoundException(String numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}


	public String getNumberOfPoints() {
		return numberOfPoints;
	}
}
