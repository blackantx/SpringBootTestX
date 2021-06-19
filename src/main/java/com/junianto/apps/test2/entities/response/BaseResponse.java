package com.junianto.apps.test2.entities.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("ALL")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class BaseResponse {
	private String responseCode;
	private String responseMessage;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "BaseResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage + "]";
	}
}
