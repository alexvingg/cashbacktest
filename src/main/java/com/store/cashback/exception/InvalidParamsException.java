package com.store.cashback.exception;

import com.store.cashback.response.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidParamsException extends IllegalArgumentException {

	private ErrorResponse errorResponse;

	private static final long serialVersionUID = 4611270908576901798L;

	public InvalidParamsException() {
		super();
	}

	public InvalidParamsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParamsException(String s) {
		super(s);
	}

	public InvalidParamsException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public InvalidParamsException(Throwable cause) {
		super(cause);
	}
}
