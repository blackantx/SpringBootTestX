package com.junianto.apps.test2.entities.response;

import java.util.Arrays;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("ALL")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class RestResponse extends BaseResponse {
	private String officeId;
	private String state;
	private String[] role;
	private String[] centers;
	private String name;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public String[] getCenters() {
		return centers;
	}

	public void setCenters(String[] centers) {
		this.centers = centers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProsperaLoginResponse [officeId=" + officeId + ", state=" + state + ", role=" + Arrays.toString(role)
				+ ", centers=" + Arrays.toString(centers) + ", name=" + name + ", getResponseCode()="
				+ getResponseCode() + ", getResponseMessage()=" + getResponseMessage() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
