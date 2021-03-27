package com.welld.project.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.welld.project.model.ApiResponse;
import com.welld.project.model.Point;
import com.welld.project.service.PointService;
import com.welld.project.util.Entity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PointController {
	
	@Autowired
	private PointService service;
	

	
	@RequestMapping(value = "/space", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<ApiResponse<List<Point>>> getSpace() {
		List<Point> points =  service.getAllPoints();
		ApiResponse<List<Point>> response = new ApiResponse<>(points, Entity.GET_POINT);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lines", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<ApiResponse<Map<Double, Set<Point>>>> getLines() {
		Map<Double, Set<Point>> points =  service.getAllLines();
		ApiResponse<Map<Double, Set<Point>>> response = new ApiResponse<>(points, Entity.GET_LINES);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lines/{n}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<ApiResponse<List<Set<Point>>>> getNLines(@PathVariable("n") int n) {
		List<Set<Point>> listOfNLines =  service.getNLines(n);
		ApiResponse<List<Set<Point>>> response = new ApiResponse<>(listOfNLines, Entity.GET_LINES);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/point", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<ApiResponse<Point>> addPoint(@RequestBody Point point) {
		Point res = service.addPoint(point);
		ApiResponse<Point> response = new ApiResponse<>(res, Entity.ADD_POINT);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/space", method = DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ResponseEntity<ApiResponse<String>> deleteSpace() {
		String res = service.deleteSpace();
		ApiResponse<String> response = new ApiResponse<>(res, Entity.DELETE_POINT);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
}
