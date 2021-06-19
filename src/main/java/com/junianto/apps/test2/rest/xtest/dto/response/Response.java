package com.junianto.apps.test2.rest.xtest.dto.response;

public class Response {

	private String reffNumber;
	private String responseCode;
	private String responseDesc;
	private String responseData;
	private Object responseObject;
	
	public String getReffNumber() {
		return reffNumber;
	}
	public void setReffNumber(String reffNumber) {
		this.reffNumber = reffNumber;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	public String getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	
}
