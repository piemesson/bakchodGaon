package com.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

@XmlRootElement
@Entity
@Table(name = "Otp_temp")
public class OTP {
	
	@Id
	@GeneratedValue
	private int id;	
	
	private long otpTimeStamp;

	@OneToOne
	@JoinColumn
	@ForeignKey( name = "email")
	Pojo email;


	public Pojo getEmail_id() {
		return email;
	}

	public void setEmail_id(Pojo email_id) {
		this.email = email_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	private String otp;
	
	public String getOtp() {
		return otp;
	}
	
	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getOtpTimeStamp() {
		return otpTimeStamp;
	}

	public void setOtpTimeStamp(long otpTimeStamp) {
		this.otpTimeStamp = otpTimeStamp;
	}

	@Override
	public String toString() {
		return "OTP [id=" + id + ", otpTimeStamp=" + otpTimeStamp + ", email="
				+ email + ", otp=" + otp + "]";
		/*return "OTP [ otpTimeStamp=" + otpTimeStamp + ", email="
		+ email + ", otp=" + otp + "]";*/
	
	}
	
	
	
	
	



}
