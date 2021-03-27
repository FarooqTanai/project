package com.welld.project.exception;

import static org.junit.Assert.assertTrue;

import com.welld.project.model.ErrorResponse;
import com.welld.project.util.Constants;
import com.welld.project.util.Entity;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProjectExceptionHandlerTest {

	private ProjectExceptionHandler handler = new ProjectExceptionHandler();
	
	
	@Test
	public void invalidCountryException() {
		ResponseEntity<ErrorResponse> response = handler.genericException(new GenericException(Entity.DELETE_POINT));
		assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assertTrue(response.getBody().getEntity().equals(Entity.DELETE_POINT));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_GENERIC_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_GENERIC_MESSAGE));
	}
	
	@Test
	public void pointAlreadyExistException() {
		ResponseEntity<ErrorResponse> response = handler.pointAlreadyExistException();
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getEntity().equals(Entity.ADD_POINT));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_POINT_ALREADY_EXIST_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_POINT_ALREADY_EXIST_MESSAGE));
	}
	
	@Test
	public void linesNotFoundException_3() {
		ResponseEntity<ErrorResponse> response = handler.linesNotFoundException(new LinesNotFoundException("3"));
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assertTrue(response.getBody().getEntity().equals(Entity.GET_LINES));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_LINES_WITH_N_POINTS_NOT_FOUND_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_LINES_WITH_N_POINTS_NOT_FOUND_MESSAGE.replaceAll("\\[n\\]", "3")));
	}
	
	@Test
	public void linesNotFoundException_null() {
		ResponseEntity<ErrorResponse> response = handler.linesNotFoundException(new LinesNotFoundException(null));
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assertTrue(response.getBody().getEntity().equals(Entity.GET_LINES));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_LINES_NOT_FOUND_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_LINES_NOT_FOUND_MESSAGE));
	}
	
	@Test
	public void incorrectNumberOfPointsException() {
		ResponseEntity<ErrorResponse> response = handler.incorrectNumberOfPointsException();
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getEntity().equals(Entity.GET_LINES));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_INCORRECT_NUMBER_OF_POINTS_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_INCORRECT_NUMBER_OF_POINTS_MESSAGE));
	}
	
	@Test
	public void pointsNotFoundException() {
		ResponseEntity<ErrorResponse> response = handler.pointsNotFoundException();
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assertTrue(response.getBody().getEntity().equals(Entity.GET_POINT));
		assertTrue(response.getBody().getData().getCode().equals(Constants.ERROR_POINTS_NOT_FOUND_CODE));
		assertTrue(response.getBody().getData().getMessage().equals(Constants.ERROR_POINTS_NOT_FOUND_MESSAGE));
	}
}
