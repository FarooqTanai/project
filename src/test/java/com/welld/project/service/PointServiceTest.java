package com.welld.project.service;

import static org.junit.Assert.assertTrue;

import com.welld.project.exception.IncorrectNumberOfPointsException;
import com.welld.project.exception.LinesNotFoundException;
import com.welld.project.exception.PointAlreadyExistException;
import com.welld.project.exception.PointsNotFoundException;
import com.welld.project.model.Point;
import com.welld.project.repository.Repository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = 
		{ 	PointService.class ,
			Repository.class})
public class PointServiceTest {
	
	@Autowired
	private PointService service;
	
	
	@Test
	public void addPoint() {
		Point point = new Point(2, 4);
		Point response = service.addPoint(point);
		assertTrue(response.equals(point));
	}
	
	@Test(expected = PointAlreadyExistException.class)
	public void addPoint_alreadyPresent() {
		service.addPoint(new Point(2, 2));
		service.addPoint(new Point(2, 2));
	}
	
	@Test
	public void getAllPoints_OK() {
		deleteAndRecreatedData();
		List<Point> response = service.getAllPoints();
		assertTrue(!response.isEmpty());
		assertTrue(response.contains(new Point(6, 2)));
		assertTrue(response.contains(new Point(8, 4)));
		assertTrue(response.contains(new Point(10, 6)));
	}
	
	@Test
	public void getAllLines() {
		
		deleteAndRecreatedData();
		Map<Double, Set<Point>> response = service.getAllLines();
		assertTrue(response.keySet().contains(1.0));
		assertTrue(response.get(1.0).contains(new Point(8, 4)));
		assertTrue(response.get(1.0).contains(new Point(10, 6)));
		assertTrue(response.get(1.0).contains(new Point(6, 2)));
	}
	
	@Test
	public void getNLines() {
		
		deleteAndRecreatedData();
		List<Set<Point>> response = service.getNLines(3);
		assertTrue(!response.isEmpty());
		assertTrue(response.size() == 1);
		assertTrue(response.stream().anyMatch(a -> a.contains(new Point(8, 4))));
		assertTrue(response.stream().anyMatch(a -> a.contains(new Point(10, 6))));
		assertTrue(response.stream().anyMatch(a -> a.contains(new Point(6, 2))));
	}
	
	@Test
	public void deleteSpace() {
		String response = service.deleteSpace();
		assertTrue(response.equals("Delete successful!"));
	}
	
	@Test(expected = LinesNotFoundException.class)
	public void getAllPoints_pointsNotFoundException() {
		service.deleteSpace();
		service.getAllLines();
	}
	
	@Test (expected = LinesNotFoundException.class)
	public void getNLines_LinesNotFoundException() {
		service.getNLines(5);
	}
	
	@Test (expected = IncorrectNumberOfPointsException.class)
	public void getNLines_IncorrectNumberOfPointsException() {
		service.getNLines(-3);
	}
	
	@Test(expected= PointsNotFoundException.class)
	public void getAllPoints_PointsNotFoundException() {
		service.deleteSpace();
		service.getAllPoints();
	}
	
	
	
	// because the data is saved in memory, and to make each test independent form each other, had to delete all previous inserted data and create new data
	private void deleteAndRecreatedData() {
		service.deleteSpace();
		service.addPoint(new Point(6, 2));
		service.addPoint(new Point(8, 4));
		service.addPoint(new Point(10, 6));
	}
	

}
