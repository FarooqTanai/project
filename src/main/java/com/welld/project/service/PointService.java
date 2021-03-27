package com.welld.project.service;

import com.welld.project.exception.GenericException;
import com.welld.project.exception.IncorrectNumberOfPointsException;
import com.welld.project.exception.LinesNotFoundException;
import com.welld.project.exception.PointAlreadyExistException;
import com.welld.project.exception.PointsNotFoundException;
import com.welld.project.model.Point;
import com.welld.project.repository.Repository;
import com.welld.project.util.Constants;
import com.welld.project.util.Entity;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
	
	private static final Logger log = LogManager.getLogger(PointService.class);
	
	@Autowired
	private Repository repository;
	

	public Point addPoint(Point point) {
		//check if the point is already present.
		if(repository.getAllPoints().contains(point)) {
			log.error("Point: {} already exist, cannot be added", point);
			throw new PointAlreadyExistException();
		}
		repository.savePoint(point);
		return point;
	}
	
	public List<Point> getAllPoints(){
		List<Point> points = repository.getAllPoints();
		if(points == null || points.isEmpty()) {
			throw new PointsNotFoundException();
		}
		return points;
	}
	
	public Map<Double, Set<Point>> getAllLines(){
		Map<Double, Set<Point>> lines = repository.getAllLines();
		if(lines == null || lines.isEmpty()) {
			throw new LinesNotFoundException(null);
		}
		return repository.getAllLines();
	}
	

	public List<Set<Point>> getNLines(int numberOfPoints) {
		if(numberOfPoints <= 0){
			log.error("Incorrect number of points requested, numberOfPoints: {}", numberOfPoints);
			throw new IncorrectNumberOfPointsException();
		}
		Collection<Set<Point>> points = repository.getAllLines().values();
		List<Set<Point>> response = points.stream().filter(a -> a.size() == numberOfPoints).collect(Collectors.toList());
		if(response == null || response.isEmpty()) {
			log.info("Lines with {} points not found", numberOfPoints);
			throw new LinesNotFoundException(String.valueOf(numberOfPoints));
		}
		return response;
	}

	public String deleteSpace() {
		
		try {
			repository.deleteSpace();
		} catch (Exception e) {
			log.error("Error during delete space, exception: {}", e);
			throw new GenericException(Entity.DELETE_POINT);
		}
		return Constants.DELETE_SUCCESSFUL;
	}
	

}
