package com.welld.project.model;

import com.welld.project.util.Entity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorResponse {
	
	private Entity entity;
	private ErrorDetails data;
	
	public ErrorResponse(Entity entity, String errorCode, String message) {
		this.entity = entity;
		this.data = new ErrorDetails(errorCode, message);
	}
	
	public Entity getEntity() {
		return entity;
	}
	public ErrorDetails getData() {
		return data;
	}
	
	public class ErrorDetails{
		
		private String code;
		private String message;
		private String time;
		
		public ErrorDetails(String errorCode, String message) {
			this.code = errorCode;
			this.message = message;
			this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		}
		
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		public String getTime() {
			return time;
		}
	}

}
