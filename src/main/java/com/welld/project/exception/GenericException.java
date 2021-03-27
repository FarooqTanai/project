package com.welld.project.exception;

import com.welld.project.util.Entity;

public class GenericException extends RuntimeException {

	private final Entity entity;
	
	public GenericException(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}

