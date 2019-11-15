package com.deadshot.labrage_v3.OutputModel;

public class OutputSearch {

	private String user_id;
	private String shop_name;
	private String mobile_primary;
	
	public OutputSearch() {}
	
	public OutputSearch(String user_id, String shop_name, String mobile_primary) {
		this.user_id = user_id;
		this.shop_name = shop_name;
		this.mobile_primary = mobile_primary;
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
	
	public String getMobile_primary() {
		return mobile_primary;
	}
	
	public void setMobile_primary(String mobile_primary) {
		this.mobile_primary = mobile_primary;
	}
}
