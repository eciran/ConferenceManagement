package com.project.ConferenceManagement.model;

import lombok.Data;

@Data
public class PasswordModel {

	private String email;
	private String oldPassword;
	private String newPassword;
	private String reNewPassword;

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getReNewPassword() {
		return reNewPassword;
	}


	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
}
