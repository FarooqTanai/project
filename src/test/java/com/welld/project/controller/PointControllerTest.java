package com.welld.project.controller;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welld.project.model.Point;
import com.welld.project.service.PointService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PointController.class)
public class PointControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PointService service;
	
	private List<Point> points;
	private Map<Double, Set<Point>> lines;
	
	@Before
	public void setUp() {
		
		Point point1 = new Point(3, 4);
		Point point2 = new Point(4, 6);
		points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		
		lines = new HashMap<Double, Set<Point>>();
		Set<Point> pointsSet = new HashSet<>();
		pointsSet.addAll(points);
		lines.put(1.0, pointsSet);
		
		
		when(service.getAllPoints()).thenReturn(points);
		when(service.getAllLines()).thenReturn(lines);
		when(service.getNLines(2)).thenReturn(Arrays.asList(pointsSet));
		when(service.addPoint(point1)).thenReturn(point1);
		when(service.deleteSpace()).thenReturn("Delete successful!");
		
	}
	
	@Test
	public void getAllPoints() throws Exception {
		MvcResult response = mvc.perform(get("/space")).andExpect(status().isOk()).andReturn();
		assertTrue(response.getResponse().getContentAsString().equals("{\"entity\":\"GET_POINT\",\"data\":[{\"x\":3.0,\"y\":4.0},{\"x\":4.0,\"y\":6.0}]}"));
	}
	
	@Test
	public void getAllLines() throws Exception {
		MvcResult response = mvc.perform(get("/lines")).andExpect(status().isOk()).andReturn();
		assertTrue(response.getResponse().getContentAsString().equals("{\"entity\":\"GET_LINES\",\"data\":{\"1.0\":[{\"x\":3.0,\"y\":4.0},{\"x\":4.0,\"y\":6.0}]}}"));
	}
	
	@Test
	public void getNLines() throws Exception {
		MvcResult response = mvc.perform(get("/lines/2")).andExpect(status().isOk()).andReturn();
		assertTrue(response.getResponse().getContentAsString().equals("{\"entity\":\"GET_LINES\",\"data\":[[{\"x\":3.0,\"y\":4.0},{\"x\":4.0,\"y\":6.0}]]}"));
	}
	
	@Test
	public void addPoint() throws Exception {
		String request = new ObjectMapper().writeValueAsString(new Point(3, 4));
		MvcResult response = mvc.perform(post("/point").contentType(MediaType.APPLICATION_JSON_VALUE).content(request))
				.andExpect(status().isOk()).andReturn();
		assertTrue(response.getResponse().getContentAsString().equals("{\"entity\":\"ADD_POINT\",\"data\":{\"x\":3.0,\"y\":4.0}}"));
	}
	
	@Test
	public void deleteSpace() throws Exception {
		MvcResult response = mvc.perform(delete("/space"))
				.andExpect(status().isOk()).andReturn();
		assertTrue(response.getResponse().getContentAsString().equals("{\"entity\":\"DELETE_POINT\",\"data\":\"Delete successful!\"}"));
	}
	

}
