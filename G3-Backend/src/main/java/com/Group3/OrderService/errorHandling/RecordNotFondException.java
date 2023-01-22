package com.Group3.OrderService.errorHandling;


import org.springframework.http.HttpStatus;

public class RecordNotFondException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	String msg;
	HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	public RecordNotFondException(String msg) {
		super();
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	

}
