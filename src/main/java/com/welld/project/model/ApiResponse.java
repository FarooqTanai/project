package com.welld.project.model;

import com.welld.project.util.Entity;

public class ApiResponse<T> {
	
	private final Entity entity;
	private final T data;
	
	public ApiResponse(T data, Entity entity) {
		this.data = data;
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public T getData() {
		return data;
	}
}
