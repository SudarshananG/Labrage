package com.deadshot.labrage_v3.TableUserDetails;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String user_id;

	@Column(name = "shop_name")
	private String shop_name;

	@Column(name = "owner_name")
	private String owner_name;

	@Column(name = "mobile_primary")
	private String mobile_primary;

	@Column(name = "mobile_secondary")
	private String mobile_secondary;

	@Column(name = "mail_id")
	private String mail_id;

	@Column(name = "address_line")
	private String address_line;

	@Column(name = "area")
	private String area;

	@Column(name = "city")
	private String city;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "googlemap_link")
	private String googlemap_link;

	@Column(name = "covering_area")
	private String covering_area;
	
	@Column(name = "status")
	private int status;

	protected UserDetails() {
	}

	public UserDetails(String user_id, String shop_name, String owner_name, String mobile_primary, String mobile_secondary,
			String mail_id, String address_line, String area, String city, String pincode, String googlemap_link,
			String covering_area, int status) {
		this.user_id = user_id;
		this.shop_name = shop_name;
		this.owner_name = owner_name;
		this.mobile_primary = mobile_primary;
		this.mobile_secondary = mobile_secondary;
		this.mail_id = mail_id;
		this.address_line = address_line;
		this.area = area;
		this.city = city;
		this.pincode = pincode;
		this.googlemap_link = googlemap_link;
		this.covering_area = covering_area;
		this.status = status;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getMobile_primary() {
		return mobile_primary;
	}

	public void setMobile_primary(String mobile_primary) {
		this.mobile_primary = mobile_primary;
	}

	public String getMobile_secondary() {
		return mobile_secondary;
	}

	public void setMobile_secondary(String mobile_secondary) {
		this.mobile_secondary = mobile_secondary;
	}

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getAddress_line() {
		return address_line;
	}

	public void setAddress_line(String address_line) {
		this.address_line = address_line;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getGooglemap_link() {
		return googlemap_link;
	}

	public void setGooglemap_link(String googlemap_link) {
		this.googlemap_link = googlemap_link;
	}

	public String getCovering_area() {
		return covering_area;
	}

	public void setCovering_area(String covering_area) {
		this.covering_area = covering_area;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

