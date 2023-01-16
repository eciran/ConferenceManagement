package com.project.ConferenceManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private String matchingPassword;
	private int refCount;
	private int authorCount;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String phoneNumber) {
		this.role = phoneNumber;
	}
	public int getRefCount() {
		return refCount;
	}
	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}
	public int getAuthorCount() {
		return authorCount;
	}
	public void setAuthorCount(int authorCount) {
		this.authorCount = authorCount;
	}
	
	
}
