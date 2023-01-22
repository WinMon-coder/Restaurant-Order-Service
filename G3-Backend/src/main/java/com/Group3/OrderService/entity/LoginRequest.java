package com.Group3.OrderService.entity;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	public LoginRequest() {
		super();
	}

	public LoginRequest(@NotBlank(message = "Required") String userEmail,
			@NotBlank(message = "Required") String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	@NotBlank(message = "Required")
	private String userEmail;

	@NotBlank(message = "Required")
	private String userPassword;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


}
