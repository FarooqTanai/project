package com.welld.project.exception;


import static com.welld.project.util.Constants.*;
import static org.springframework.http.ResponseEntity.status;

import com.welld.project.model.ErrorResponse;
import com.welld.project.util.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler {
	
	
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorResponse> genericException(GenericException e) {
		return status(HttpStatus.INTERNAL_SERVER_ERROR).
				body(new ErrorResponse(e.getEntity(), ERROR_GENERIC_CODE, ERROR_GENERIC_MESSAGE));
	}
	
	@ExceptionHandler(PointAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> pointAlreadyExistException() {
		return status(HttpStatus.BAD_REQUEST).
				body(new ErrorResponse(Entity.ADD_POINT, ERROR_POINT_ALREADY_EXIST_CODE, ERROR_POINT_ALREADY_EXIST_MESSAGE));
	}
	
	@ExceptionHandler(LinesNotFoundException.class)
	public ResponseEntity<ErrorResponse> linesNotFoundException(LinesNotFoundException e) {
		String numberOfPoints = e.getNumberOfPoints();
		String code = null;
		String message = null; 
		if(numberOfPoints == null) {
			code = ERROR_LINES_NOT_FOUND_CODE;
			message = ERROR_LINES_NOT_FOUND_MESSAGE;
		} else {
			code = ERROR_LINES_WITH_N_POINTS_NOT_FOUND_CODE;
			message = ERROR_LINES_WITH_N_POINTS_NOT_FOUND_MESSAGE.replaceAll("\\[n\\]", numberOfPoints);
		}
		return status(HttpStatus.NOT_FOUND).
				body(new ErrorResponse(Entity.GET_LINES, code, message));
	}
	
	@ExceptionHandler(IncorrectNumberOfPointsException.class)
	public ResponseEntity<ErrorResponse> incorrectNumberOfPointsException() {
		return status(HttpStatus.BAD_REQUEST).
				body(new ErrorResponse(Entity.GET_LINES, ERROR_INCORRECT_NUMBER_OF_POINTS_CODE, ERROR_INCORRECT_NUMBER_OF_POINTS_MESSAGE));
	}
	
	@ExceptionHandler(PointsNotFoundException.class)
	public ResponseEntity<ErrorResponse> pointsNotFoundException() {
		return status(HttpStatus.NOT_FOUND).
				body(new ErrorResponse(Entity.GET_POINT, ERROR_POINTS_NOT_FOUND_CODE, ERROR_POINTS_NOT_FOUND_MESSAGE));
	}
	
	

}
