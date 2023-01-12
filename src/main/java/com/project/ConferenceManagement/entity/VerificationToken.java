package com.project.ConferenceManagement.entity;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
	
	//Expiratation time 20 munite
	private static final int EXPIRATION_TIME=20;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Date expirationTime;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_USER_VERIFY_TOKEN"))
	private UserEntity user;
	
	public VerificationToken(UserEntity user,String token) {
		super();
		this.token=token;
		this.user=user;
		this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
	}
	
	public VerificationToken(String token) {
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
