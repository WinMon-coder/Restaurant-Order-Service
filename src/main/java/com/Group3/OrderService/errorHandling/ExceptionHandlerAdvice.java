package com.Group3.OrderService.errorHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(RecordNotFondException.class)
	public ResponseEntity noRecord(RecordNotFondException e) {
		return ResponseEntity.status(e.getStatus()).body(e.getMsg());
	}
}
