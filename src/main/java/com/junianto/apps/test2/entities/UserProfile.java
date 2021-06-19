package com.junianto.apps.test2.entities;

import org.springframework.web.servlet.ModelAndView;

public class UserProfile {
	private static String appVersion;
	private static String username;
	private static String role;
	private static String email;
	private static String fullName;
	private static String branch;
	private static int userId;
	private static ModelAndView baseModelAndView = new ModelAndView();
	private static String branchCode;
	
	
	public static String getUsername() {
		return username;
	}
	public static String getRole() {
		return role;
	}
	public static String getEmail() {
		return email;
	}
	public static String getFullname() {
		return fullName;
	}
	public static String getBranch() {
		return branch;
	}
	public static int getUserid() {
		return userId;
	}
	public static void setUsername(String username) {
		UserProfile.username = username;
	}
	public static void setRole(String role) {
		UserProfile.role = role;
	}
	public static void setEmail(String email) {
		UserProfile.email = email;
	}
	public static void setFullname(String fullname) {
		fullName = fullname;
	}
	public static void setBranch(String branch) {
		UserProfile.branch = branch;
	}
	public static void setUserid(int i) {
		userId = i;
	}
	public static ModelAndView getBaseModelAndView() {
		baseModelAndView.addObject("userName", getUsername());
		baseModelAndView.addObject("fullName", getFullname());
		baseModelAndView.addObject("branchName", getBranch());
		baseModelAndView.addObject("roleName", getRole());
		baseModelAndView.addObject("email", getEmail());
		baseModelAndView.addObject("appVersion", getAppVersion());
		
		return baseModelAndView;
	}
	public static void setBaseModelAndView(ModelAndView baseModelAndView) {
		UserProfile.baseModelAndView = baseModelAndView;
	}
	public static String getAppVersion() {
		return appVersion;
	}
	public static void setAppVersion(String appVersion) {
		UserProfile.appVersion = appVersion;
	}
	public static String getBranchCode() {
		return branchCode;
	}
	public static void setBranchCode(String branchCode) {
		UserProfile.branchCode = branchCode;
	}
	
	
	
}
