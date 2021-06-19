package com.junianto.apps.test2.entities;

import javax.persistence.Transient;

public class BaseEntity {
	@Transient
	private String action="";
	@Transient
	private String createBy="";
	@Transient
	private String updateBy="";
	@Transient
	private int id;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
