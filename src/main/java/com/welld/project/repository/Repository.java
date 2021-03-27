package com.welld.project.repository;

import com.welld.project.model.Point;
import com.welld.project.util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class Repository {
	
	 //all points
	 private static List<Point> points;
	 
	 //slop as key, set of points as value
	 private static Map<Double, Set<Point>> lines = new HashMap<>();
	 
	 static {
		 points = new ArrayList<Point>(Arrays.asList(new Point(6, 2), new Point(8, 4), new Point(10, 6), new Point(8, 2)));
		 List<Point> tempPoints = new ArrayList<>(points);
		 for(Point point1: points) {
			 //in order to prevent calculating slop for same points twice, the first element is removed from the temporary list.
			 if(!tempPoints.isEmpty()) {
				 tempPoints.remove(0);
			 }
			 for(Point point2: tempPoints) {
				 //check again if the array somehow contains same points inserted.
				 if(point1.equals(point2)) {
					 continue;
				 }
				 double slop = Utils.getSlop(point1.getX(), point1.getY(), point2.getX(), point2.getY());
				 if(lines.containsKey(slop)) {
					 Set<Point> points = lines.get(slop);
					 points.add(point1);
					 points.add(point2);
					 lines.put(slop, points);
				 } else {
					 Set<Point> points = new HashSet<>();
					 points.add(point1);
					 points.add(point2);
					 lines.put(slop, points);
				 }
			 }
		 }
	 }
	 
	 public List<Point> getAllPoints() {
		 return points;
	 }
	 
	 public Map<Double, Set<Point>> getAllLines() {
		 return lines;
	 }
	 
	 public void savePoint(Point newPoint) {
		 
		 for(Point point: points) {
			 double slop = Utils.getSlop(newPoint.getX(), newPoint.getY(), point.getX(), point.getY());
			 //new point is part of the existing line
			 if(lines.containsKey(slop)) {
				 Set<Point> points = lines.get(slop);
				 points.add(newPoint);
				 lines.put(slop, points);
			 } else {
				 //new point is not part of any existing line
				 Set<Point> points = new HashSet<>();
				 points.add(newPoint);
				 points.add(point);
				 lines.put(slop, points);
			 }
		 }
		 points.add(newPoint);
	 }

	public void deleteSpace() throws UnsupportedOperationException{
		points.clear();
		lines.clear();
	}
	

}
