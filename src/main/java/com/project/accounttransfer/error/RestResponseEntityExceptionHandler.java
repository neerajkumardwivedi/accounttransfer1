package com.project.accounttransfer.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.accounttransfer.entity.ErrorMessage;

@RestControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorMessage> acctNotFoundException(AccountNotFoundException accException, WebRequest request) {

		ErrorMessage errMessage = new ErrorMessage(HttpStatus.NOT_FOUND, accException.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMessage);

	}
}
