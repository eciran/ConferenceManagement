package com.project.ConferenceManagement.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
public class PasswordResetToken {
	public PasswordResetToken() {}
	//Expiratation time 20 minute
	private static final int EXPIRATION_TIME=20;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Date expirationTime;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_USER_PASSWORD_TOKEN"))
	private UserEntity user;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public PasswordResetToken(UserEntity user,String token) {
		super();
		this.token=token;
		this.user=user;
		this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
	}
	
	public PasswordResetToken(String token) {
		super();
		this.token=token;
		this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
	}
	
	private Date calculateExpirationDate(int expirationTime) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}



	
	

}
