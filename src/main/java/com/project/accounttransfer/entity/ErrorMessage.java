package com.project.accounttransfer.entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ErrorMessage {
	
	private HttpStatus status;
	private String message;
	
	public ErrorMessage(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ErrorMessage(HttpStatus status) {
		super();
		this.status = status;
	}

	
	
}
