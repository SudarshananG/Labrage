package com.deadshot.labrage_v3.TableOtpGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otp_generator")
public class OtpGenerator implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "mobile_number")
	private String mobile_number;
	
	@Column(name = "otp_number")
	private String otp_number;
	
	@Column(name = "created_ts")
	private LocalDateTime created_ts;

	public OtpGenerator() {
	}
	
	public OtpGenerator(String mobile_number, String otp_number, LocalDateTime created_ts) {
		this.mobile_number = mobile_number;
		this.otp_number = otp_number;
		this.created_ts = created_ts;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getOtp_number() {
		return otp_number;
	}

	public void setOtp_number(String otp_number) {
		this.otp_number = otp_number;
	}

	public LocalDateTime getCreated_ts() {
		return created_ts;
	}

	public void setCreated_ts(LocalDateTime created_ts) {
		this.created_ts = created_ts;
	}
	
}
