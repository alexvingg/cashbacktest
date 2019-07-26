package com.store.cashback.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Response {

	private String msg;

	private Map<String, Object> data;

	public static Response ok() {
		return new Response();
	}

	public static Response ok(final String message) {
		final Response r = Response.ok();
		r.setMsg(message);
		return r;
	}

	public static Response error(final String message) {
		final Response r = new Response();
		r.setMsg(message);
		return r;
	}

	public static Response internalServerError() {
		return Response.error("Internal Server Error");
	}

	public Response addData(final String key, final Object value) {

		if (this.data == null) {
			this.data = new HashMap<>();
		}

		if (this.data.containsKey(key)) {
			throw new IllegalArgumentException(String.format("Key [%s] exists.", key));
		}

		this.data.put(key, value);

		return this;
	}

	@Override
	public String toString() {
		return "ValidResponse [msg=" + msg + ", data=" + data + "]";
	}

}
