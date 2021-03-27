package com.welld.project.util;

public class Utils {
	
	
	
	// method used to find slop between two points, the method was copied from: https://www.mathsisfun.com/algebra/line-equation-2points.html
	public static double getSlop(double x1, double y1, double x2, double y2) {
		if(x1-x2 == 0) {
			return x1;
		}
		double slop = (y1-y2)/(x1-x2);
		return slop == -0 ? 0 : slop;
	}

}
